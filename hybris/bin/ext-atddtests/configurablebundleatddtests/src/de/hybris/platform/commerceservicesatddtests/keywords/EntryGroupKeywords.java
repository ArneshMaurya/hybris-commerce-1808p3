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

package de.hybris.platform.commerceservicesatddtests.keywords;


import de.hybris.platform.atddengine.keywords.AbstractKeywordLibrary;
import de.hybris.platform.commercefacades.order.CommerceEntryGroupUtils;
import de.hybris.platform.commercefacades.order.EntryGroupData;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.servicelayer.util.ServicesUtil;

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * ATDD keywords for entry group support.
 */
public class EntryGroupKeywords extends AbstractKeywordLibrary
{
	@Autowired
	private CommerceEntryGroupUtils commerceEntryGroupUtils;

	/**
	 * Find entry group data by groupNumber.
	 *
	 * @param order order that contains the desired entry group
	 * @param groupNumber group number
	 * @return entry group data
	 * @throws IllegalArgumentException if there is no such group
	 */
	public EntryGroupData getEntryGroup(@Nonnull final AbstractOrderData order, @Nonnull final Integer groupNumber)
	{
		ServicesUtil.validateParameterNotNullStandardMessage("order", order);
		ServicesUtil.validateParameterNotNullStandardMessage("groupNum	ber", groupNumber);

		return getCommerceEntryGroupUtils().getGroup(order, groupNumber);
	}

	/**
	 * Find entry within an order by externalReferenceId.
	 *
	 * @param order order data
	 * @param externalReferenceId reference id
	 * @return entry group data
	 * @throws IllegalArgumentException if entry group was not found
	 */
	public EntryGroupData findEntryGroupByRefInOrder(@Nonnull final AbstractOrderData order, final String externalReferenceId)
	{
		ServicesUtil.validateParameterNotNullStandardMessage("order", order);
		ServicesUtil.validateParameterNotNullStandardMessage("order.rootGroups", order.getRootGroups());
		return order.getRootGroups().stream()
				.map(getCommerceEntryGroupUtils()::getNestedGroups)
				.flatMap(Collection::stream)
				.filter(entry -> Objects.equals(entry.getExternalReferenceId(), externalReferenceId))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(
						"The order does not have a group with ref id '" + externalReferenceId + "'"));
	}

	/**
	 * Find entry within a tree by external reference id.
	 * @param rootGroupData root group of the tree
	 * @param externalReferenceId reference id
	 * @return entry group data
	 * @throws IllegalArgumentException if entry group was not found
	 */
	public EntryGroupData findEntryGroupByRefInTree(@Nonnull final EntryGroupData rootGroupData, final String externalReferenceId)
	{
		return getCommerceEntryGroupUtils().getNestedGroups(rootGroupData).stream()
				.filter(entry -> Objects.equals(entry.getExternalReferenceId(), externalReferenceId))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException(
						"The entry group tree does not have a group with ref id '" + externalReferenceId + "'"));
	}

	protected CommerceEntryGroupUtils getCommerceEntryGroupUtils()
	{
		return commerceEntryGroupUtils;
	}
}
