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
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "unitOfMeasure",
    "shipNoticeReleaseInfo",
    "extrinsic"
})
@XmlRootElement(name = "ReleaseInfo")
public class ReleaseInfo {

    @XmlAttribute(name = "releaseType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String releaseType;
    @XmlAttribute(name = "cumulativeReceivedQuantity", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String cumulativeReceivedQuantity;
    @XmlAttribute(name = "productionGoAheadEndDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String productionGoAheadEndDate;
    @XmlAttribute(name = "materialGoAheadEndDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String materialGoAheadEndDate;
    @XmlElement(name = "UnitOfMeasure", required = true)
    protected String unitOfMeasure;
    @XmlElement(name = "ShipNoticeReleaseInfo")
    protected ShipNoticeReleaseInfo shipNoticeReleaseInfo;
    @XmlElement(name = "Extrinsic")
    protected List<Extrinsic> extrinsic;

    /**
     * Gets the value of the releaseType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReleaseType() {
        return releaseType;
    }

    /**
     * Sets the value of the releaseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReleaseType(String value) {
        this.releaseType = value;
    }

    /**
     * Gets the value of the cumulativeReceivedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCumulativeReceivedQuantity() {
        return cumulativeReceivedQuantity;
    }

    /**
     * Sets the value of the cumulativeReceivedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCumulativeReceivedQuantity(String value) {
        this.cumulativeReceivedQuantity = value;
    }

    /**
     * Gets the value of the productionGoAheadEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductionGoAheadEndDate() {
        return productionGoAheadEndDate;
    }

    /**
     * Sets the value of the productionGoAheadEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductionGoAheadEndDate(String value) {
        this.productionGoAheadEndDate = value;
    }

    /**
     * Gets the value of the materialGoAheadEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaterialGoAheadEndDate() {
        return materialGoAheadEndDate;
    }

    /**
     * Sets the value of the materialGoAheadEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaterialGoAheadEndDate(String value) {
        this.materialGoAheadEndDate = value;
    }

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitOfMeasure(String value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the shipNoticeReleaseInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ShipNoticeReleaseInfo }
     *     
     */
    public ShipNoticeReleaseInfo getShipNoticeReleaseInfo() {
        return shipNoticeReleaseInfo;
    }

    /**
     * Sets the value of the shipNoticeReleaseInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipNoticeReleaseInfo }
     *     
     */
    public void setShipNoticeReleaseInfo(ShipNoticeReleaseInfo value) {
        this.shipNoticeReleaseInfo = value;
    }

    /**
     * Gets the value of the extrinsic property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extrinsic property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtrinsic().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Extrinsic }
     * 
     * 
     */
    public List<Extrinsic> getExtrinsic() {
        if (extrinsic == null) {
            extrinsic = new ArrayList<Extrinsic>();
        }
        return this.extrinsic;
    }

}
