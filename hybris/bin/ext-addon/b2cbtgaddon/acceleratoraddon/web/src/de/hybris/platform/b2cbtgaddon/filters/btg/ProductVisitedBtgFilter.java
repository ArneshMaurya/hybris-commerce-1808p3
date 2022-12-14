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
package de.hybris.platform.b2cbtgaddon.filters.btg;

import de.hybris.platform.btg.events.AbstractBTGRuleDataEvent;
import de.hybris.platform.btg.events.ProductVisitedBTGRuleDataEvent;


/**
 * FilterBean to produce the BTG event {@link ProductVisitedBTGRuleDataEvent} This is a spring configured filter that is
 * executed by the PlatformFilterChain.
 */
public class ProductVisitedBtgFilter extends AbstractPkResolvingBtgFilter
{
	@Override
	protected AbstractBTGRuleDataEvent internalGetEvent(final String pk)
	{
		return new ProductVisitedBTGRuleDataEvent(pk);
	}
}
