
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxDetailCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxDetailCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TaxDetailKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfTaxDetailType" minOccurs="0"/&gt;
 *         &lt;element name="TaxIdNumber" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailTaxable" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxDetailCriteria", propOrder = {
    "taxDetailKeyId",
    "taxDetailType",
    "taxIdNumber",
    "taxDetailTaxable"
})
public class TaxDetailCriteria
    extends Criteria
{

    @XmlElement(name = "TaxDetailKeyId")
    protected LikeRestrictionOfString taxDetailKeyId;
    @XmlElement(name = "TaxDetailType")
    protected ListRestrictionOfNullableOfTaxDetailType taxDetailType;
    @XmlElement(name = "TaxIdNumber")
    protected LikeRestrictionOfString taxIdNumber;
    @XmlElement(name = "TaxDetailTaxable")
    protected RestrictionOfNullableOfBoolean taxDetailTaxable;

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
     * Gets the value of the taxDetailType property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfTaxDetailType }
     *     
     */
    public ListRestrictionOfNullableOfTaxDetailType getTaxDetailType() {
        return taxDetailType;
    }

    /**
     * Sets the value of the taxDetailType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfTaxDetailType }
     *     
     */
    public void setTaxDetailType(ListRestrictionOfNullableOfTaxDetailType value) {
        this.taxDetailType = value;
    }

    /**
     * Gets the value of the taxIdNumber property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getTaxIdNumber() {
        return taxIdNumber;
    }

    /**
     * Sets the value of the taxIdNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setTaxIdNumber(LikeRestrictionOfString value) {
        this.taxIdNumber = value;
    }

    /**
     * Gets the value of the taxDetailTaxable property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getTaxDetailTaxable() {
        return taxDetailTaxable;
    }

    /**
     * Sets the value of the taxDetailTaxable property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setTaxDetailTaxable(RestrictionOfNullableOfBoolean value) {
        this.taxDetailTaxable = value;
    }

}
