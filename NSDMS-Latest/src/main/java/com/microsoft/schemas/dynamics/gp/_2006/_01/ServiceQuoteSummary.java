
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceQuoteSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceQuoteSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceDocumentSummary"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ServiceTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="TechnicianKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceTechnicianKey" minOccurs="0"/&gt;
 *         &lt;element name="StatusCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallStatusCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="DocumentTotal" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceQuoteSummary", propOrder = {
    "serviceTypeKey",
    "technicianKey",
    "statusCodeKey",
    "documentTotal"
})
public class ServiceQuoteSummary
    extends ServiceDocumentSummary
{

    @XmlElement(name = "ServiceTypeKey")
    protected ServiceTypeKey serviceTypeKey;
    @XmlElement(name = "TechnicianKey")
    protected ServiceTechnicianKey technicianKey;
    @XmlElement(name = "StatusCodeKey")
    protected ServiceCallStatusCodeKey statusCodeKey;
    @XmlElement(name = "DocumentTotal")
    protected MoneyAmount documentTotal;

    /**
     * Gets the value of the serviceTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceTypeKey }
     *     
     */
    public ServiceTypeKey getServiceTypeKey() {
        return serviceTypeKey;
    }

    /**
     * Sets the value of the serviceTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceTypeKey }
     *     
     */
    public void setServiceTypeKey(ServiceTypeKey value) {
        this.serviceTypeKey = value;
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
     * Gets the value of the statusCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallStatusCodeKey }
     *     
     */
    public ServiceCallStatusCodeKey getStatusCodeKey() {
        return statusCodeKey;
    }

    /**
     * Sets the value of the statusCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallStatusCodeKey }
     *     
     */
    public void setStatusCodeKey(ServiceCallStatusCodeKey value) {
        this.statusCodeKey = value;
    }

    /**
     * Gets the value of the documentTotal property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDocumentTotal() {
        return documentTotal;
    }

    /**
     * Sets the value of the documentTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDocumentTotal(MoneyAmount value) {
        this.documentTotal = value;
    }

}
