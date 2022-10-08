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

package de.hybris.platform.atddengine.keywords.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import de.hybris.platform.atddengine.keywords.ImpExAdaptor;
import de.hybris.platform.impex.jalo.ImpExException;
import de.hybris.platform.impex.jalo.Importer;
import de.hybris.platform.impex.jalo.imp.DefaultDumpHandler;
import de.hybris.platform.impex.jalo.media.DefaultMediaDataHandler;
import de.hybris.platform.impex.jalo.media.MediaDataTranslator;
import de.hybris.platform.util.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.log4j.Logger;


public class DefaultImpExAdaptor implements ImpExAdaptor
{
	private static final Logger LOG = Logger.getLogger(DefaultImpExAdaptor.class);

	@Override
	public void importStream(final InputStream inputStream, final String encoding, final String resourceName)
			throws ImpExException
	{
		//create stream reader
		CSVReader reader = null;
		try
		{
			reader = new CSVReader(inputStream, encoding);
		}
		catch (final UnsupportedEncodingException e)
		{
			LOG.debug("Caught UnsupportedEncodingException", e);
			fail("Given encoding " + encoding + " is not supported");
		}

		// import
		MediaDataTranslator.setMediaDataHandler(new DefaultMediaDataHandler());
		Importer importer = null;
		try
		{
			importer = new Importer(reader);
			importer.getReader().enableCodeExecution(true);
			importer.setMaxPass(-1);
			importer.setDumpHandler(new FirstLinesDumpReader());
			importer.importAll();
		}
		finally
		{
			MediaDataTranslator.unsetMediaDataHandler();
		}

		// failure handling
		if (importer.hasUnresolvedLines())
		{
			fail("Import has " + importer.getDumpedLineCountPerPass() + "+unresolved lines, first lines are:\n"
					+ importer.getDumpHandler().getDumpAsString());
		}
		assertFalse("Import of resource " + resourceName + " failed", importer.hadError());
	}

	private static class FirstLinesDumpReader extends DefaultDumpHandler
	{
		@Override
		public String getDumpAsString()
		{
			final StringBuilder result = new StringBuilder(100);
			try (final BufferedReader reader = new BufferedReader(new FileReader(getDumpAsFile()));)
			{
				result.append(reader.readLine()).append('\n');
				result.append(reader.readLine()).append('\n');
				result.append(reader.readLine()).append('\n');
			}
			catch (final IOException e)
			{
				LOG.debug("Caught IOException", e);
				result.append("Error while reading dump ").append(e.getMessage());
			}
			return result.toString();
		}
	}

}
