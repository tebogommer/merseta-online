
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceSerial complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceSerial"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceSerialLot"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SerialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ServiceEquipmentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceSerial", propOrder = {
    "serialNumber",
    "serviceEquipmentKey"
})
@XmlSeeAlso({
    ReturnMaterialAuthorizationLineSerial.class
})
public class ServiceSerial
    extends ServiceSerialLot
{

    @XmlElement(name = "SerialNumber")
    protected String serialNumber;
    @XmlElement(name = "ServiceEquipmentKey")
    protected ServiceEquipmentKey serviceEquipmentKey;

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the serviceEquipmentKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEquipmentKey }
     *     
     */
    public ServiceEquipmentKey getServiceEquipmentKey() {
        return serviceEquipmentKey;
    }

    /**
     * Sets the value of the serviceEquipmentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEquipmentKey }
     *     
     */
    public void setServiceEquipmentKey(ServiceEquipmentKey value) {
        this.serviceEquipmentKey = value;
    }

}
