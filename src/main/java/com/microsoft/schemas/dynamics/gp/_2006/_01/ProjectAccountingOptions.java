
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectAccountingOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectAccountingOptions"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DefaultPurchaseOrderFormat" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseOrderFormat"/&gt;
 *         &lt;element name="UnitOfMeasure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TimeAndMaterial" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TimeAndMaterial" minOccurs="0"/&gt;
 *         &lt;element name="FixedPrice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}FixedPrice" minOccurs="0"/&gt;
 *         &lt;element name="CostPlus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CostPlus" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectAccountingOptions", propOrder = {
    "defaultPurchaseOrderFormat",
    "unitOfMeasure",
    "unitCost",
    "timeAndMaterial",
    "fixedPrice",
    "costPlus",
    "userDefined1",
    "userDefined2"
})
public class ProjectAccountingOptions {

    @XmlElement(name = "DefaultPurchaseOrderFormat", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchaseOrderFormat defaultPurchaseOrderFormat;
    @XmlElement(name = "UnitOfMeasure")
    protected String unitOfMeasure;
    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "TimeAndMaterial")
    protected TimeAndMaterial timeAndMaterial;
    @XmlElement(name = "FixedPrice")
    protected FixedPrice fixedPrice;
    @XmlElement(name = "CostPlus")
    protected CostPlus costPlus;
    @XmlElement(name = "UserDefined1")
    protected String userDefined1;
    @XmlElement(name = "UserDefined2")
    protected String userDefined2;

    /**
     * Gets the value of the defaultPurchaseOrderFormat property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseOrderFormat }
     *     
     */
    public PurchaseOrderFormat getDefaultPurchaseOrderFormat() {
        return defaultPurchaseOrderFormat;
    }

    /**
     * Sets the value of the defaultPurchaseOrderFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseOrderFormat }
     *     
     */
    public void setDefaultPurchaseOrderFormat(PurchaseOrderFormat value) {
        this.defaultPurchaseOrderFormat = value;
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
     * Gets the value of the unitCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the value of the unitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setUnitCost(MoneyAmount value) {
        this.unitCost = value;
    }

    /**
     * Gets the value of the timeAndMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link TimeAndMaterial }
     *     
     */
    public TimeAndMaterial getTimeAndMaterial() {
        return timeAndMaterial;
    }

    /**
     * Sets the value of the timeAndMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeAndMaterial }
     *     
     */
    public void setTimeAndMaterial(TimeAndMaterial value) {
        this.timeAndMaterial = value;
    }

    /**
     * Gets the value of the fixedPrice property.
     * 
     * @return
     *     possible object is
     *     {@link FixedPrice }
     *     
     */
    public FixedPrice getFixedPrice() {
        return fixedPrice;
    }

    /**
     * Sets the value of the fixedPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link FixedPrice }
     *     
     */
    public void setFixedPrice(FixedPrice value) {
        this.fixedPrice = value;
    }

    /**
     * Gets the value of the costPlus property.
     * 
     * @return
     *     possible object is
     *     {@link CostPlus }
     *     
     */
    public CostPlus getCostPlus() {
        return costPlus;
    }

    /**
     * Sets the value of the costPlus property.
     * 
     * @param value
     *     allowed object is
     *     {@link CostPlus }
     *     
     */
    public void setCostPlus(CostPlus value) {
        this.costPlus = value;
    }

    /**
     * Gets the value of the userDefined1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined1() {
        return userDefined1;
    }

    /**
     * Sets the value of the userDefined1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined1(String value) {
        this.userDefined1 = value;
    }

    /**
     * Gets the value of the userDefined2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined2() {
        return userDefined2;
    }

    /**
     * Sets the value of the userDefined2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined2(String value) {
        this.userDefined2 = value;
    }

}
