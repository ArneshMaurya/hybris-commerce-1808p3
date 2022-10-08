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
        <cart>
            <entries>
                <xsl:for-each select="cart/entries/c/orderEntry">
                    <!-- Copy only specific child nodes of orderEntry element -->
                    <xsl:copy>
                        <xsl:apply-templates select="quantity | product | updateable | bundleNo | component | removeable | editable | addable" />
                    </xsl:copy>
                </xsl:for-each>
            </entries>
        </cart>
    </xsl:template>

    <xsl:template match="cart/entries/c/orderEntry/product">
        <!-- copy orderEntry/product element with only code and disabled nodes -->
        <xsl:copy>
            <xsl:apply-templates select="code | disabled" />
        </xsl:copy>
    </xsl:template>

    <xsl:template match="cart/entries/c/orderEntry/component">
        <!-- copy orderEntry/component element with only id node -->
        <xsl:copy>
            <xsl:apply-templates select="id" />
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>
