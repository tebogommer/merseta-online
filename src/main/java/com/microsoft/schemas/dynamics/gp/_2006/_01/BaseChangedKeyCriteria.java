
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BaseChangedKeyCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseChangedKeyCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LastModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="Action" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfDataModificationAction" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseChangedKeyCriteria", propOrder = {
    "lastModifiedDate",
    "action"
})
@XmlSeeAlso({
    CurrencyChangedKeyCriteria.class,
    InternetAddressKeyChangedCriteria.class,
    TaxScheduleDetailChangedKeyCriteria.class,
    TaxDetailChangedKeyCriteria.class,
    ShippingMethodChangedKeyCriteria.class,
    UofMScheduleChangedKeyCriteria.class,
    PriceLevelChangedKeyCriteria.class,
    PricingChangedKeyCriteria.class,
    ItemChangedKeyCriteria.class,
    SalespersonChangedKeyCriteria.class,
    CustomerAddressChangedKeyCriteria.class,
    CustomerChangedKeyCriteria.class,
    BaseChangedSalesDocumentKeyCriteria.class
})
public class BaseChangedKeyCriteria
    extends Criteria
{

    @XmlElement(name = "LastModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime lastModifiedDate;
    @XmlElement(name = "Action")
    protected RestrictionOfNullableOfDataModificationAction action;

    /**
     * Gets the value of the lastModifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the value of the lastModifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setLastModifiedDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.lastModifiedDate = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfDataModificationAction }
     *     
     */
    public RestrictionOfNullableOfDataModificationAction getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfDataModificationAction }
     *     
     */
    public void setAction(RestrictionOfNullableOfDataModificationAction value) {
        this.action = value;
    }

}
