
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceCallShipToAddress complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceCallShipToAddress"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationShipToAddress"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Phone1" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PhoneNumber" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceCallShipToAddress", propOrder = {
    "phone1"
})
public class ServiceCallShipToAddress
    extends ReturnMaterialAuthorizationShipToAddress
{

    @XmlElement(name = "Phone1")
    protected PhoneNumber phone1;

    /**
     * Gets the value of the phone1 property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumber }
     *     
     */
    public PhoneNumber getPhone1() {
        return phone1;
    }

    /**
     * Sets the value of the phone1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumber }
     *     
     */
    public void setPhone1(PhoneNumber value) {
        this.phone1 = value;
    }

}
