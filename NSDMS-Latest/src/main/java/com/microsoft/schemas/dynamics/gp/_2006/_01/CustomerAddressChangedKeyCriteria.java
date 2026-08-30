
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerAddressChangedKeyCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerAddressChangedKeyCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BaseChangedKeyCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AddressKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CustomerKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAddressChangedKeyCriteria", propOrder = {
    "addressKeyId",
    "customerKeyId"
})
public class CustomerAddressChangedKeyCriteria
    extends BaseChangedKeyCriteria
{

    @XmlElement(name = "AddressKeyId")
    protected BetweenRestrictionOfString addressKeyId;
    @XmlElement(name = "CustomerKeyId")
    protected BetweenRestrictionOfString customerKeyId;

    /**
     * Gets the value of the addressKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getAddressKeyId() {
        return addressKeyId;
    }

    /**
     * Sets the value of the addressKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setAddressKeyId(BetweenRestrictionOfString value) {
        this.addressKeyId = value;
    }

    /**
     * Gets the value of the customerKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getCustomerKeyId() {
        return customerKeyId;
    }

    /**
     * Sets the value of the customerKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setCustomerKeyId(BetweenRestrictionOfString value) {
        this.customerKeyId = value;
    }

}
