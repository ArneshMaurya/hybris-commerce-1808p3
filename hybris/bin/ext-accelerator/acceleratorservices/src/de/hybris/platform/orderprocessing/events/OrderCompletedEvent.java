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
package de.hybris.platform.orderprocessing.events;

import de.hybris.platform.orderprocessing.model.OrderProcessModel;


/**
 * Event representing completion of order process.
 */
public class OrderCompletedEvent extends OrderProcessingEvent
{
	private static final long serialVersionUID = -293422455711438189L;

	public OrderCompletedEvent(final OrderProcessModel process)
	{
		super(process);
	}
}
