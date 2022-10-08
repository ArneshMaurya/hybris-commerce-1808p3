/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package de.hybris.platform.selectivecartaddon.controllers.misc;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.selectivecartaddon.controllers.SelectivecartaddonControllerConstants;
import de.hybris.platform.selectivecartaddon.controllers.imported.MiniCartController;
import de.hybris.platform.selectivecartfacades.SelectiveCartFacade;
import de.hybris.platform.selectivecartfacades.data.Wishlist2Data;
import de.hybris.platform.selectivecartfacades.data.Wishlist2EntryData;
import de.hybris.platform.selectivecartfacades.strategies.SelectiveCartUpdateStrategy;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Controller to Override the MiniCart Controller to add wish list quantity into checked list total items add wish list
 * entry into checked list.
 */
@Controller
public class MiniCartControllerForSelectiveCart extends MiniCartController
{
	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a Uri value is incorrectly extracted if it
	 * contains on or more '.' characters. Please see https://jira.springsource.org/browse/SPR-6164 for a discussion on
	 * the issue and future resolution.
	 */
	private static final String TOTAL_DISPLAY_PATH_VARIABLE_PATTERN = "{totalDisplay:.*}";
	private static final String COMPONENT_UID_PATH_VARIABLE_PATTERN = "{componentUid:.*}";

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "cmsComponentService")
	private CMSComponentService cmsComponentService;

	@Resource
	private SelectiveCartFacade selectiveCartFacade;

	@Resource(name = "selectiveCartUpdateStrategy")
	private SelectiveCartUpdateStrategy selectiveCartUpdateStrategy;

	@Override
	@RequestMapping(value = "/cart/miniCart/" + TOTAL_DISPLAY_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public String getMiniCart(@PathVariable final String totalDisplay, final Model model)
	{
		selectiveCartUpdateStrategy.update();
		super.getMiniCart(totalDisplay, model);

		final Integer counts = calculateTotalQuantityNumber();
		model.addAttribute("totalItems", counts);

		return SelectivecartaddonControllerConstants.Views.Fragments.Cart.MiniCartPanel;
	}

	@Override
	@RequestMapping(value = "/cart/rollover/" + COMPONENT_UID_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public String rolloverMiniCartPopup(@PathVariable final String componentUid, final Model model)
			throws CMSItemNotFoundException
	{
		selectiveCartUpdateStrategy.update();
		super.rolloverMiniCartPopup(componentUid, model);

		final Integer counts = calculateTotalNumber();
		model.addAttribute("numberItemsInCart", counts);

		return SelectivecartaddonControllerConstants.Views.Fragments.Cart.CartPopup;
	}

	protected Integer calculateTotalNumber()
	{
		final CartData cartData = getCartFacade().getSessionCart();

		final int cartEntriesCount = CollectionUtils.isEmpty(cartData.getEntries()) ? 0 : cartData.getEntries().size();

		final Wishlist2Data wishList = getSelectiveCartFacade().getWishlistForSelectiveCart();
		int counts = 0;
		if (wishList != null && CollectionUtils.isNotEmpty(wishList.getEntries()))
		{
			counts = wishList.getEntries().size();
		}
		counts = counts + cartEntriesCount;
		return Integer.valueOf(counts);
	}

	protected Integer calculateTotalQuantityNumber()
	{
		final CartData cartData = getCartFacade().getSessionCart();

		final Wishlist2Data wishList = getSelectiveCartFacade().getWishlistForSelectiveCart();
		int counts = 0;
		if (wishList != null)
		{
			for (final Wishlist2EntryData entry : wishList.getEntries())
			{
				counts += entry.getQuantity().intValue();
			}
		}
		counts = counts + cartData.getTotalUnitCount().intValue();
		return Integer.valueOf(counts);
	}

	protected SelectiveCartFacade getSelectiveCartFacade()
	{
		return selectiveCartFacade;
	}

	protected CartFacade getCartFacade()
	{
		return cartFacade;
	}

	protected CMSComponentService getCmsComponentService()
	{
		return cmsComponentService;
	}
}
