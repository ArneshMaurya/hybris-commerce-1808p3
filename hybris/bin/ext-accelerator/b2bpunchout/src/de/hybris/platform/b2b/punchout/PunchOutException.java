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
package de.hybris.platform.b2b.punchout;

/**
 * Business exception representing PunchOut issues
 */
public class PunchOutException extends RuntimeException
{
	public static final String PUNCHOUT_EXCEPTION_MESSAGE = "PunchOut Exception";

	private final String errorCode;

	public PunchOutException(final String errorCode, final String message)
	{
		this(errorCode, message, null);
	}

	public PunchOutException(final String errorCode, final String message, final Throwable cause)
	{
		super(message, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode()
	{
		return errorCode;
	}
}
