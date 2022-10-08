/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company. All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package de.hybris.platform.secaddon.controllers.cms;

import static de.hybris.platform.secaddon.controllers.SecaddonControllerConstants.Cms.SecChatComponent;

import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.secaddon.model.components.SecChatComponentModel;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.localization.Localization;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller("SecChatComponentController")
@RequestMapping(value = SecChatComponent)
public class SecChatComponentController extends AbstractCMSAddOnComponentController<SecChatComponentModel>
{
	public static final String SECADDON_CHAT_TITLE_TEXT = "secaddon.chat.title.text";
	public static final String SECADDON_CHAT_TITLE_VIDEO = "secaddon.chat.title.video";
	public static final String CHAT_ADDON_FRAGMENT_PATH = "addon:/secaddon/cms/secchatwindow";

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "storeSessionFacade")
	private StoreSessionFacade storeSessionFacade;

	@Resource(name = "userService")
	private UserService userService;




	/**
	 * @return the userService
	 */
	public UserService getUserService()
	{
		return userService;
	}

	@Override
	protected void fillModel(final HttpServletRequest request, final Model model, final SecChatComponentModel component)
	{
		final String customerName = customerFacade.getCurrentCustomer().getName();
		final String customerEmail = customerFacade.getCurrentCustomer().getUid();
		final UserModel user = getUserService().getCurrentUser();
		if (getUserService().isAnonymousUser(user))
		{
			model.addAttribute("customerName", "");
			model.addAttribute("customerEmail", "");

		}
		else
		{

			model.addAttribute("customerName", customerName);
			model.addAttribute("customerEmail", customerEmail);
		}
		model.addAttribute("chatQueue", component.getChatQueue());
		model.addAttribute("chatEcfModulePath", component.getChatEcfModulePath());
		model.addAttribute("chatCctrUrl", component.getChatCctrUrl());
		model.addAttribute("currentLanguage", storeSessionFacade.getCurrentLanguage().getIsocode());
		model.addAttribute("textChatTitle", Localization.getLocalizedString(SECADDON_CHAT_TITLE_TEXT));
		model.addAttribute("videoChatTitle", Localization.getLocalizedString(SECADDON_CHAT_TITLE_VIDEO));
		model.addAttribute("videoChatEnabled", component.getVideoChatEnabled());
	}

	/**
	 * Method for getting fragment's JSP renderer as response on GET or POSTs request
	 *
	 * @param model
	 * @param allRequestParams
	 *           all request parameters
	 * @return fragment with populated data and renderer
	 */
	@RequestMapping(value = "/chatFragment", method = { RequestMethod.POST, RequestMethod.GET })
	public String getChatFragment(final Model model, @RequestParam final Map<String, String> allRequestParams)
	{
		return CHAT_ADDON_FRAGMENT_PATH;
	}

}
