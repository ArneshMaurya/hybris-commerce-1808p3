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
package de.hybris.platform.addonsupport.config.cms.dataattributeproviders;

import de.hybris.platform.acceleratorcms.data.CmsPageRequestContextData;
import de.hybris.platform.cms2.model.contents.components.AbstractCMSComponentModel;

import java.util.Map;



/**
 * 
 */
public interface TagDataAttributesProvider
{
	Map<String, String> getAttributes(AbstractCMSComponentModel comContainer, AbstractCMSComponentModel currentComponent,
			CmsPageRequestContextData currentCmsPageRequestContextData);
}
