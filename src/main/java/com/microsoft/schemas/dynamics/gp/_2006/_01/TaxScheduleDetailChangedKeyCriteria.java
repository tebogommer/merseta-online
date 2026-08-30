
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxScheduleDetailChangedKeyCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxScheduleDetailChangedKeyCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BaseChangedKeyCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TaxScheduleDetailKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxScheduleDetailChangedKeyCriteria", propOrder = {
    "taxScheduleDetailKeyId",
    "taxDetailKeyId"
})
public class TaxScheduleDetailChangedKeyCriteria
    extends BaseChangedKeyCriteria
{

    @XmlElement(name = "TaxScheduleDetailKeyId")
    protected BetweenRestrictionOfString taxScheduleDetailKeyId;
    @XmlElement(name = "TaxDetailKeyId")
    protected BetweenRestrictionOfString taxDetailKeyId;

    /**
     * Gets the value of the taxScheduleDetailKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getTaxScheduleDetailKeyId() {
        return taxScheduleDetailKeyId;
    }

    /**
     * Sets the value of the taxScheduleDetailKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setTaxScheduleDetailKeyId(BetweenRestrictionOfString value) {
        this.taxScheduleDetailKeyId = value;
    }

    /**
     * Gets the value of the taxDetailKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getTaxDetailKeyId() {
        return taxDetailKeyId;
    }

    /**
     * Sets the value of the taxDetailKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setTaxDetailKeyId(BetweenRestrictionOfString value) {
        this.taxDetailKeyId = value;
    }

}
