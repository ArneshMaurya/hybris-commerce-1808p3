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
package de.hybris.platform.acceleratorcms.component.container.impl;

import de.hybris.platform.acceleratorcms.component.container.CMSComponentContainerStrategy;
import de.hybris.platform.cms2.model.contents.components.AbstractCMSComponentModel;
import de.hybris.platform.cms2.model.contents.containers.AbstractCMSComponentContainerModel;

import java.util.Collections;
import java.util.List;

/**
 */
public class IdentityCMSComponentContainerStrategy implements CMSComponentContainerStrategy
{
	@Override
	public List<AbstractCMSComponentModel> getDisplayComponentsForContainer(final AbstractCMSComponentContainerModel container)
	{
		return Collections.<AbstractCMSComponentModel>singletonList(container);
	}
}
