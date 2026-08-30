
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceCallDocumentAudit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceCallDocumentAudit"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceAudit"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLineKey" minOccurs="0"/&gt;
 *         &lt;element name="TechnicianKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceTechnicianKey" minOccurs="0"/&gt;
 *         &lt;element name="FromStatusCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallStatusCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="ToStatusCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallStatusCodeKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceCallDocumentAudit", propOrder = {
    "key",
    "technicianKey",
    "fromStatusCodeKey",
    "toStatusCodeKey"
})
public class ServiceCallDocumentAudit
    extends ServiceAudit
{

    @XmlElement(name = "Key")
    protected ServiceLineKey key;
    @XmlElement(name = "TechnicianKey")
    protected ServiceTechnicianKey technicianKey;
    @XmlElement(name = "FromStatusCodeKey")
    protected ServiceCallStatusCodeKey fromStatusCodeKey;
    @XmlElement(name = "ToStatusCodeKey")
    protected ServiceCallStatusCodeKey toStatusCodeKey;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLineKey }
     *     
     */
    public ServiceLineKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLineKey }
     *     
     */
    public void setKey(ServiceLineKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the technicianKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceTechnicianKey }
     *     
     */
    public ServiceTechnicianKey getTechnicianKey() {
        return technicianKey;
    }

    /**
     * Sets the value of the technicianKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceTechnicianKey }
     *     
     */
    public void setTechnicianKey(ServiceTechnicianKey value) {
        this.technicianKey = value;
    }

    /**
     * Gets the value of the fromStatusCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallStatusCodeKey }
     *     
     */
    public ServiceCallStatusCodeKey getFromStatusCodeKey() {
        return fromStatusCodeKey;
    }

    /**
     * Sets the value of the fromStatusCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallStatusCodeKey }
     *     
     */
    public void setFromStatusCodeKey(ServiceCallStatusCodeKey value) {
        this.fromStatusCodeKey = value;
    }

    /**
     * Gets the value of the toStatusCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallStatusCodeKey }
     *     
     */
    public ServiceCallStatusCodeKey getToStatusCodeKey() {
        return toStatusCodeKey;
    }

    /**
     * Sets the value of the toStatusCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallStatusCodeKey }
     *     
     */
    public void setToStatusCodeKey(ServiceCallStatusCodeKey value) {
        this.toStatusCodeKey = value;
    }

}
