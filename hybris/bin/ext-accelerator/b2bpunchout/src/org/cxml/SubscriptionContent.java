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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cifContentOrIndexOrContract"
})
@XmlRootElement(name = "SubscriptionContent")
public class SubscriptionContent {

    @XmlAttribute(name = "filename")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String filename;
    @XmlElements({
        @XmlElement(name = "CIFContent", required = true, type = CIFContent.class),
        @XmlElement(name = "Index", required = true, type = Index.class),
        @XmlElement(name = "Contract", required = true, type = Contract.class)
    })
    protected List<Object> cifContentOrIndexOrContract;

    /**
     * Gets the value of the filename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the value of the filename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
    }

    /**
     * Gets the value of the cifContentOrIndexOrContract property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cifContentOrIndexOrContract property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCIFContentOrIndexOrContract().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CIFContent }
     * {@link Index }
     * {@link Contract }
     * 
     * 
     */
    public List<Object> getCIFContentOrIndexOrContract() {
        if (cifContentOrIndexOrContract == null) {
            cifContentOrIndexOrContract = new ArrayList<Object>();
        }
        return this.cifContentOrIndexOrContract;
    }

}
