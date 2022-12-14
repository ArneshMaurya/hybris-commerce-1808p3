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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "itemOutRetail",
    "referenceDocumentInfo",
    "priority"
})
@XmlRootElement(name = "ItemOutIndustry")
public class ItemOutIndustry {

    @XmlAttribute(name = "planningType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String planningType;
    @XmlElement(name = "ItemOutRetail")
    protected ItemOutRetail itemOutRetail;
    @XmlElement(name = "ReferenceDocumentInfo")
    protected List<ReferenceDocumentInfo> referenceDocumentInfo;
    @XmlElement(name = "Priority")
    protected Priority priority;

    /**
     * Gets the value of the planningType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanningType() {
        return planningType;
    }

    /**
     * Sets the value of the planningType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanningType(String value) {
        this.planningType = value;
    }

    /**
     * Gets the value of the itemOutRetail property.
     * 
     * @return
     *     possible object is
     *     {@link ItemOutRetail }
     *     
     */
    public ItemOutRetail getItemOutRetail() {
        return itemOutRetail;
    }

    /**
     * Sets the value of the itemOutRetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemOutRetail }
     *     
     */
    public void setItemOutRetail(ItemOutRetail value) {
        this.itemOutRetail = value;
    }

    /**
     * Gets the value of the referenceDocumentInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the referenceDocumentInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReferenceDocumentInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferenceDocumentInfo }
     * 
     * 
     */
    public List<ReferenceDocumentInfo> getReferenceDocumentInfo() {
        if (referenceDocumentInfo == null) {
            referenceDocumentInfo = new ArrayList<ReferenceDocumentInfo>();
        }
        return this.referenceDocumentInfo;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Priority }
     *     
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority }
     *     
     */
    public void setPriority(Priority value) {
        this.priority = value;
    }

}
