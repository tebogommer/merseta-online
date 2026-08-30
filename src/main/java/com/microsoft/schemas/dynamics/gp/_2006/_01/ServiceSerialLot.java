
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceSerialLot complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceSerialLot"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesSerialLotBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceSerialLotKey" minOccurs="0"/&gt;
 *         &lt;element name="AuditTrailCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DeleteOnUpdate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceSerialLot", propOrder = {
    "key",
    "auditTrailCode",
    "deleteOnUpdate"
})
@XmlSeeAlso({
    ServiceLot.class,
    ServiceSerial.class
})
public abstract class ServiceSerialLot
    extends SalesSerialLotBase
{

    @XmlElement(name = "Key")
    protected ServiceSerialLotKey key;
    @XmlElement(name = "AuditTrailCode")
    protected String auditTrailCode;
    @XmlElement(name = "DeleteOnUpdate", required = true, type = Boolean.class, nillable = true)
    protected Boolean deleteOnUpdate;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceSerialLotKey }
     *     
     */
    public ServiceSerialLotKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceSerialLotKey }
     *     
     */
    public void setKey(ServiceSerialLotKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the auditTrailCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuditTrailCode() {
        return auditTrailCode;
    }

    /**
     * Sets the value of the auditTrailCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuditTrailCode(String value) {
        this.auditTrailCode = value;
    }

    /**
     * Gets the value of the deleteOnUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeleteOnUpdate() {
        return deleteOnUpdate;
    }

    /**
     * Sets the value of the deleteOnUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeleteOnUpdate(Boolean value) {
        this.deleteOnUpdate = value;
    }

}
