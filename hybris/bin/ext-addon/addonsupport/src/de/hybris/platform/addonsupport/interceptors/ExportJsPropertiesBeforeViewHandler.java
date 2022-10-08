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
package de.hybris.platform.addonsupport.interceptors;

import de.hybris.platform.addonsupport.config.javascript.BeforeViewJsPropsHandlerAdaptee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

public class ExportJsPropertiesBeforeViewHandler extends BeforeViewJsPropsHandlerAdaptee
{
	@Override
	public String beforeViewJsProps(final HttpServletRequest request, final HttpServletResponse response, final ModelMap model,
			final String viewName)
	{
		return viewName;
	}
}
