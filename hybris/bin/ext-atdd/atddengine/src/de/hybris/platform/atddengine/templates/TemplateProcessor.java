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

package de.hybris.platform.atddengine.templates;

import java.io.Writer;
import java.util.Map;


public interface TemplateProcessor
{
	void processTemplate(final Writer writer, final String templatePath, final Map<String, Object> binding);
}
