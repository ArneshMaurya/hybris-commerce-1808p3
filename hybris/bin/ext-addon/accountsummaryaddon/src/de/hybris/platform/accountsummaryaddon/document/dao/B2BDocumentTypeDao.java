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
package de.hybris.platform.accountsummaryaddon.document.dao;


import de.hybris.platform.servicelayer.search.SearchResult;

import de.hybris.platform.accountsummaryaddon.model.B2BDocumentTypeModel;


public interface B2BDocumentTypeDao
{

	/**
	 * Gets all document types.
	 * 
	 * @return a SearchResult<B2BDocumentTypeModel> containing a list of all document types.
	 */
	SearchResult<B2BDocumentTypeModel> getAllDocumentTypes();
}
