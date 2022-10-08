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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.12 at 07:19:30 PM EDT 
//



package org.cxml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "xadesIssuer",
    "xadesIssueTime",
    "xadesNumber"
})
@XmlRootElement(name = "xades:CRLIdentifier")
public class XadesCRLIdentifier {

    @XmlAttribute(name = "URI")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String uri;
    @XmlElement(name = "xades:Issuer", required = true)
    protected String xadesIssuer;
    @XmlElement(name = "xades:IssueTime", required = true)
    protected String xadesIssueTime;
    @XmlElement(name = "xades:Number")
    protected String xadesNumber;

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURI() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURI(String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the xadesIssuer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXadesIssuer() {
        return xadesIssuer;
    }

    /**
     * Sets the value of the xadesIssuer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXadesIssuer(String value) {
        this.xadesIssuer = value;
    }

    /**
     * Gets the value of the xadesIssueTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXadesIssueTime() {
        return xadesIssueTime;
    }

    /**
     * Sets the value of the xadesIssueTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXadesIssueTime(String value) {
        this.xadesIssueTime = value;
    }

    /**
     * Gets the value of the xadesNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXadesNumber() {
        return xadesNumber;
    }

    /**
     * Sets the value of the xadesNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXadesNumber(String value) {
        this.xadesNumber = value;
    }

}
