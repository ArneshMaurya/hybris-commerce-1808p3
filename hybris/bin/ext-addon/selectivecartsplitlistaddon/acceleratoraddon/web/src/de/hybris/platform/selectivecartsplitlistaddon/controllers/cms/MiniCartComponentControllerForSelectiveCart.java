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
package de.hybris.platform.selectivecartsplitlistaddon.controllers.cms;

import de.hybris.platform.acceleratorcms.model.components.MiniCartComponentModel;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.selectivecartfacades.SelectiveCartFacade;
import de.hybris.platform.selectivecartfacades.data.Wishlist2Data;
import de.hybris.platform.selectivecartfacades.data.Wishlist2EntryData;
import de.hybris.platform.selectivecartsplitlistaddon.controllers.SelectivecartsplitlistaddonControllerConstants;
import de.hybris.platform.selectivecartsplitlistaddon.controllers.imported.MiniCartComponentController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller for Override the MiniCartComponentControllerForSelectiveCart to add wish list quantity into checked list
 * total items.
 */
@Controller
@RequestMapping(value = SelectivecartsplitlistaddonControllerConstants.Actions.Cms.MiniCartComponent)
public class MiniCartComponentControllerForSelectiveCart extends MiniCartComponentController
{
	public static final String TOTAL_ITEMS = "totalItems";

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource
	private SelectiveCartFacade selectiveCartFacade;

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final MiniCartComponentModel component)
	{
		super.fillModel(request, model, component);

		final Integer counts = calculateTotalNumber();
		model.addAttribute(TOTAL_ITEMS, counts);
	}
	
	protected Integer calculateTotalNumber()
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


	protected CartFacade getCartFacade()
	{
		return cartFacade;
	}

	protected SelectiveCartFacade getSelectiveCartFacade()
	{
		return selectiveCartFacade;
	}

}