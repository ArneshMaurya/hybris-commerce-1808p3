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
package de.hybris.platform.commerceservicesatddtests.keywords;

import static org.junit.Assert.assertNotNull;

import de.hybris.platform.atddengine.keywords.AbstractKeywordLibrary;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.site.BaseSiteService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * ATDD keywords for checkout process.
 */
public class CheckoutKeywordLibrary extends AbstractKeywordLibrary
{
	public static final String ROBOT_LIBRARY_SCOPE = "GLOBAL";

	private static final Logger LOG = Logger.getLogger(CheckoutKeywordLibrary.class);

	@Autowired
	private CheckoutFacade acceleratorCheckoutFacade;

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private BaseSiteService baseSiteService;

	@Autowired
	private CartFacade cartFacade;

	private final Random random = new Random();

	public void updateUserDetails() throws DuplicateUidException
	{
		final Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTime(new Date());

		final AddressData address = new AddressData();
		address.setId(UUID.randomUUID().toString());
		address.setShippingAddress(true);
		address.setDefaultAddress(true);
		address.setBillingAddress(true);
		address.setVisibleInAddressBook(true);

		final CCPaymentInfoData creditCard = new CCPaymentInfoData();
		creditCard.setId(String.valueOf(random.nextInt(Integer.MAX_VALUE)));
		creditCard.setAccountHolderName("HOLDER NAME");
		creditCard.setIssueNumber("123");
		creditCard.setBillingAddress(address);
		creditCard.setCardNumber("1111111111111111");
		creditCard.setCardType("visa");
		creditCard.setDefaultPaymentInfo(true);
		creditCard.setExpiryMonth(String.valueOf(calendar.get(Calendar.MONTH)));
		creditCard.setExpiryYear(String.valueOf(calendar.get(Calendar.YEAR) + 3));

		userFacade.setDefaultAddress(address);
		userFacade.setDefaultPaymentInfo(creditCard);
	}

	public OrderData doCheckout() throws InvalidCartException
	{
		baseSiteService.setCurrentBaseSite("testSite", true);
		final OrderData order = acceleratorCheckoutFacade.placeOrder();
		assertNotNull("CheckoutFacade#placeOrder returned null", order);
		return order;
	}

	public void validateSessionCart() throws InvalidCartException, CommerceCartModificationException
	{
		final List<CartModificationData> cartModificationData = cartFacade.validateCartData();
		if (CollectionUtils.isNotEmpty(cartModificationData))
		{
			throw new InvalidCartException(
					"Cart is not valid:\n" + cartModificationData.stream()
							.map(data -> "[Product: " + getProductCode(data) + "] " + data.getStatusCode() + ": "
									+ data.getStatusMessage())
							.collect(Collectors.joining("\n"))
			);
		}
	}

	protected String getProductCode(final CartModificationData data)
	{
		if (data.getEntry() == null)
		{
			return "null entry";
		}
		if (data.getEntry().getProduct() == null)
		{
			return "null";
		}
		return data.getEntry().getProduct().getCode();
	}
}