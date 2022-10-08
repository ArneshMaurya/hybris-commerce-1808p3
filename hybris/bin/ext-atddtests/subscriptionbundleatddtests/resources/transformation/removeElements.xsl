<!--
 [y] hybris Platform

 Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.

 This software is the confidential and proprietary information of SAP
 ("Confidential Information"). You shall not disclose such Confidential
 Information and shall use it only in accordance with the terms of the
 license agreement you entered into with SAP.
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output omit-xml-declaration="yes" indent="yes"/>

    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>

    <xsl:template match="/">
        <xsl:apply-templates select="//bundleTemplates"/>
    </xsl:template>

    <xsl:template match="bundleTemplates">
        <xsl:element name="bundleTemplates">
            <xsl:for-each select="bundleTemplate">
                <xsl:copy>
                    <xsl:for-each select="products/product">
                        <xsl:element name="product">
                            <xsl:element name="code">
                                <xsl:value-of select="code"/>
                            </xsl:element>
                            <xsl:element name="disabled">
                                <xsl:value-of select="disabled"/>
                            </xsl:element>
                        </xsl:element>
                    </xsl:for-each>
                    <xsl:apply-templates select="id | type | version | maxItemsAllowed"/>
                </xsl:copy>
            </xsl:for-each>
        </xsl:element>
    </xsl:template>
</xsl:stylesheet>
