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
package de.hybris.platform.b2bacceleratoraddon.event;

import de.hybris.platform.b2bacceleratorservices.event.OrderApprovalRejectionEvent;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import org.springframework.beans.factory.annotation.Required;


public class OrderApprovalRejectionEventListener extends AbstractEventListener<OrderApprovalRejectionEvent>
{
	private ModelService modelService;
	private BusinessProcessService businessProcessService;

	protected BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	@Required
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	protected ModelService getModelService()
	{
		return modelService;
	}

	@Required
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	@Override
	protected void onEvent(final OrderApprovalRejectionEvent orderApprovalRejectionEvent)
	{
		final OrderModel orderModel = orderApprovalRejectionEvent.getProcess().getOrder();
		final OrderProcessModel orderProcessModel = (OrderProcessModel) getBusinessProcessService().createProcess(
				"orderApprovalRejectionEmailProcess" + "-" + orderModel.getCode() + "-" + System.currentTimeMillis(),
				"orderApprovalRejectionEmailProcess");
		orderProcessModel.setOrder(orderModel);
		getModelService().save(orderProcessModel);
		getBusinessProcessService().startProcess(orderProcessModel);
	}
}
