
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxScheduleDetailCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxScheduleDetailCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TaxScheduleKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailBase" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfTaxDetailBase" minOccurs="0"/&gt;
 *         &lt;element name="IsTaxDetailTaxable" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *         &lt;element name="IsAutoCalculate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxScheduleDetailCriteria", propOrder = {
    "taxScheduleKeyId",
    "taxDetailKeyId",
    "taxDetailBase",
    "isTaxDetailTaxable",
    "isAutoCalculate"
})
public class TaxScheduleDetailCriteria
    extends Criteria
{

    @XmlElement(name = "TaxScheduleKeyId")
    protected LikeRestrictionOfString taxScheduleKeyId;
    @XmlElement(name = "TaxDetailKeyId")
    protected LikeRestrictionOfString taxDetailKeyId;
    @XmlElement(name = "TaxDetailBase")
    protected ListRestrictionOfNullableOfTaxDetailBase taxDetailBase;
    @XmlElement(name = "IsTaxDetailTaxable")
    protected RestrictionOfNullableOfBoolean isTaxDetailTaxable;
    @XmlElement(name = "IsAutoCalculate")
    protected RestrictionOfNullableOfBoolean isAutoCalculate;

    /**
     * Gets the value of the taxScheduleKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getTaxScheduleKeyId() {
        return taxScheduleKeyId;
    }

    /**
     * Sets the value of the taxScheduleKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setTaxScheduleKeyId(LikeRestrictionOfString value) {
        this.taxScheduleKeyId = value;
    }

    /**
     * Gets the value of the taxDetailKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getTaxDetailKeyId() {
        return taxDetailKeyId;
    }

    /**
     * Sets the value of the taxDetailKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setTaxDetailKeyId(LikeRestrictionOfString value) {
        this.taxDetailKeyId = value;
    }

    /**
     * Gets the value of the taxDetailBase property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfTaxDetailBase }
     *     
     */
    public ListRestrictionOfNullableOfTaxDetailBase getTaxDetailBase() {
        return taxDetailBase;
    }

    /**
     * Sets the value of the taxDetailBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfTaxDetailBase }
     *     
     */
    public void setTaxDetailBase(ListRestrictionOfNullableOfTaxDetailBase value) {
        this.taxDetailBase = value;
    }

    /**
     * Gets the value of the isTaxDetailTaxable property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getIsTaxDetailTaxable() {
        return isTaxDetailTaxable;
    }

    /**
     * Sets the value of the isTaxDetailTaxable property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setIsTaxDetailTaxable(RestrictionOfNullableOfBoolean value) {
        this.isTaxDetailTaxable = value;
    }

    /**
     * Gets the value of the isAutoCalculate property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getIsAutoCalculate() {
        return isAutoCalculate;
    }

    /**
     * Sets the value of the isAutoCalculate property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setIsAutoCalculate(RestrictionOfNullableOfBoolean value) {
        this.isAutoCalculate = value;
    }

}
