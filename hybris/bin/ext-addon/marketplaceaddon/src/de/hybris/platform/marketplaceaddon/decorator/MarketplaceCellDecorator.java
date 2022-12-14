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
package de.hybris.platform.marketplaceaddon.decorator;

import de.hybris.platform.acceleratorcms.model.actions.AbstractCMSActionModel;
import de.hybris.platform.acceleratorcms.model.components.JspIncludeComponentModel;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.Registry;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.CSVCellDecorator;
import de.hybris.platform.util.FlexibleSearchUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;


/**
 * An implementation for resolving that multiple extensions want to insert action to a same component.
 */
public class MarketplaceCellDecorator implements CSVCellDecorator
{

	@Override
	public String decorate(final int position, final Map<Integer, String> impexLine)
	{
		final FlexibleSearchService flexibleSearchService = (FlexibleSearchService) Registry.getApplicationContext().getBean(
				"flexibleSearchService");
		final CatalogVersionService catalogVersionService = (CatalogVersionService) Registry.getApplicationContext().getBean(
				"catalogVersionService");

		final String impexContents = impexLine.get(Integer.valueOf(position));
		final String[] impexContent = StringUtils.split(impexContents, ",");

		final StringBuilder fql = new StringBuilder();
		final Map<String, Object> params = new HashMap<>();
		final Collection<CatalogVersionModel> catalogVersions = new ArrayList<>();

		final CatalogVersionModel catalogVersionModel = catalogVersionService.getCatalogVersion("marketplaceContentCatalog",
				"Staged");
		catalogVersions.add(catalogVersionModel);

		fql.append("SELECT {pk} FROM {JspIncludeComponent} WHERE {uid} =?uid AND ");
		fql.append(FlexibleSearchUtils.buildOracleCompatibleCollectionStatement("{catalogVersion} in (?catalogVersions)",
				"catalogVersions", "OR", catalogVersions, params));
		params.put("uid", "AccountOrderDetailsItemsComponent");

		final SearchResult<JspIncludeComponentModel> result = flexibleSearchService.search(fql.toString(), params);
		if (!CollectionUtils.isEmpty(result.getResult()))
		{
			final JspIncludeComponentModel accountOrderDetailsItemsComponent = result.getResult().get(0);
			final List<String> actions = accountOrderDetailsItemsComponent.getActions().stream()
					.map(AbstractCMSActionModel::getUid).collect(Collectors.toList());

			if (actions == null)
			{
				return impexContents;
			}

			for (final String action : impexContent)
			{
				if (!actions.contains(action))
				{
					actions.add(action);
				}
			}

			return StringUtils.join(actions, ",");
		}

		return "";
	}

}
