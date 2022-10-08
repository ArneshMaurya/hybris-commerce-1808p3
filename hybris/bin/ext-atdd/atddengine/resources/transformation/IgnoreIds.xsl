<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <!--
     [y] hybris Platform

     Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.

     This software is the confidential and proprietary information of SAP
     ("Confidential Information"). You shall not disclose such Confidential
     Information and shall use it only in accordance with the terms of the
     license agreement you entered into with SAP.
    -->

	<xsl:output method="xml" indent="yes"/>
	
	<xsl:template match="node() | @*">
  		<xsl:copy>
	        <xsl:apply-templates select="node() | @*" />
	    </xsl:copy>
	</xsl:template>

	<!-- ignore all id attributes and elements by always setting their value to -1 -->
	<xsl:template match="@id">
   		<xsl:attribute name="id">-1</xsl:attribute> 
	</xsl:template>
	
	<xsl:template match="id">
   		<id>-1</id> 
	</xsl:template>
	
</xsl:stylesheet>
