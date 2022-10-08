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
package de.hybris.platform.b2bacceleratorfacades.company.impl;

import de.hybris.platform.b2b.model.B2BBudgetModel;
import de.hybris.platform.b2bacceleratorfacades.company.B2BCommerceBudgetFacade;
import de.hybris.platform.b2bacceleratorfacades.company.refactoring.impl.DefaultB2BBudgetFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BBudgetData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;


/**
 * @deprecated Since 6.0. Use {@link DefaultB2BBudgetFacade} instead.
 */
@Deprecated
public class DefaultB2BCommerceBudgetFacade extends DefaultCompanyB2BCommerceFacade implements B2BCommerceBudgetFacade
{
	@Override
	public void updateBudgetDetails(final B2BBudgetData b2BBudgetData) throws DuplicateUidException
	{
		final B2BBudgetModel b2BBudgetModel = getB2BCommerceBudgetService().getBudgetModelForCode(b2BBudgetData.getOriginalCode());
		if (b2BBudgetModel != null)
		{
			getB2BBudgetReversePopulator().populate(b2BBudgetData, b2BBudgetModel);
			getCompanyB2BCommerceService().saveModel(b2BBudgetModel);
		}
	}

	@Override
	public void addBudget(final B2BBudgetData b2BBudgetData) throws DuplicateUidException
	{
		final B2BBudgetModel b2BBudgetModel = this.getModelService().create(B2BBudgetModel.class);
		getB2BBudgetReversePopulator().populate(b2BBudgetData, b2BBudgetModel);
		getCompanyB2BCommerceService().saveModel(b2BBudgetModel);

	}

	@Override
	public void enableDisableBudget(final String b2BudgetCode, final boolean active) throws DuplicateUidException
	{
		final B2BBudgetModel b2BBudgetModel = getB2BCommerceBudgetService().getBudgetModelForCode(b2BudgetCode);
		if (b2BBudgetModel != null)
		{
			b2BBudgetModel.setActive(Boolean.valueOf(active));
			getCompanyB2BCommerceService().saveModel(b2BBudgetModel);
		}
	}
}
