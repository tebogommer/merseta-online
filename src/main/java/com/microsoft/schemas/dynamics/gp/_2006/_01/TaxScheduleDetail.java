
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxScheduleDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxScheduleDetail"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TaxScheduleDetailKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleDetailKey" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailBase" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxDetailBase"/&gt;
 *         &lt;element name="IsTaxDetailTaxable" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsAutoCalculate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxScheduleDetail", propOrder = {
    "taxScheduleDetailKey",
    "taxDetailBase",
    "isTaxDetailTaxable",
    "isAutoCalculate"
})
public class TaxScheduleDetail
    extends BusinessObject
{

    @XmlElement(name = "TaxScheduleDetailKey")
    protected TaxScheduleDetailKey taxScheduleDetailKey;
    @XmlElement(name = "TaxDetailBase", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected TaxDetailBase taxDetailBase;
    @XmlElement(name = "IsTaxDetailTaxable", required = true, type = Boolean.class, nillable = true)
    protected Boolean isTaxDetailTaxable;
    @XmlElement(name = "IsAutoCalculate", required = true, type = Boolean.class, nillable = true)
    protected Boolean isAutoCalculate;

    /**
     * Gets the value of the taxScheduleDetailKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleDetailKey }
     *     
     */
    public TaxScheduleDetailKey getTaxScheduleDetailKey() {
        return taxScheduleDetailKey;
    }

    /**
     * Sets the value of the taxScheduleDetailKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleDetailKey }
     *     
     */
    public void setTaxScheduleDetailKey(TaxScheduleDetailKey value) {
        this.taxScheduleDetailKey = value;
    }

    /**
     * Gets the value of the taxDetailBase property.
     * 
     * @return
     *     possible object is
     *     {@link TaxDetailBase }
     *     
     */
    public TaxDetailBase getTaxDetailBase() {
        return taxDetailBase;
    }

    /**
     * Sets the value of the taxDetailBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxDetailBase }
     *     
     */
    public void setTaxDetailBase(TaxDetailBase value) {
        this.taxDetailBase = value;
    }

    /**
     * Gets the value of the isTaxDetailTaxable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTaxDetailTaxable() {
        return isTaxDetailTaxable;
    }

    /**
     * Sets the value of the isTaxDetailTaxable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTaxDetailTaxable(Boolean value) {
        this.isTaxDetailTaxable = value;
    }

    /**
     * Gets the value of the isAutoCalculate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAutoCalculate() {
        return isAutoCalculate;
    }

    /**
     * Sets the value of the isAutoCalculate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAutoCalculate(Boolean value) {
        this.isAutoCalculate = value;
    }

}
