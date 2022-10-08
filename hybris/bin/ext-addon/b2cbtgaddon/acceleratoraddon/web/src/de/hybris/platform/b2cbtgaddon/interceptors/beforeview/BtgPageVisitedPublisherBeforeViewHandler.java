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
package de.hybris.platform.b2cbtgaddon.interceptors.beforeview;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.interceptors.BeforeViewHandler;
import de.hybris.platform.btg.events.ContentPageVisitedBTGRuleDataEvent;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.servicelayer.event.EventService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;


/**
 * Interceptor to create BTG events for visited content pages. Using a filter is not appropriate since pages are
 * retrieved by controllers and populated in the model.
 */
public class BtgPageVisitedPublisherBeforeViewHandler implements BeforeViewHandler
{
	private static final Logger LOG = Logger.getLogger(BtgPageVisitedPublisherBeforeViewHandler.class);

	@Resource(name = "eventService")
	private EventService eventService;

	@Override
	public void beforeView(final HttpServletRequest request, final HttpServletResponse response, final ModelAndView modelAndView)
	{
		final AbstractPageModel page = (AbstractPageModel) modelAndView.getModel().get(AbstractPageController.CMS_PAGE_MODEL);
		if (page != null && page.getPk() != null)
		{
			try
			{
				eventService.publishEvent(new ContentPageVisitedBTGRuleDataEvent(page.getPk().getLongValueAsString()));
			}
			catch (final Exception e)
			{
				LOG.error("Could not publish event", e);
			}
		}
	}
}
