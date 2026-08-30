
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceCallDocumentCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceCallDocumentCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceDocumentCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ServiceTypeId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="TechnicianId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="StatusCodeId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Scope" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallDocumentScope"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceCallDocumentCriteria", propOrder = {
    "serviceTypeId",
    "technicianId",
    "statusCodeId",
    "scope"
})
@XmlSeeAlso({
    ServiceQuoteCriteria.class,
    ServiceCallCriteria.class
})
public abstract class ServiceCallDocumentCriteria
    extends ServiceDocumentCriteria
{

    @XmlElement(name = "ServiceTypeId")
    protected LikeRestrictionOfString serviceTypeId;
    @XmlElement(name = "TechnicianId")
    protected LikeRestrictionOfString technicianId;
    @XmlElement(name = "StatusCodeId")
    protected LikeRestrictionOfString statusCodeId;
    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected ServiceCallDocumentScope scope;

    /**
     * Gets the value of the serviceTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getServiceTypeId() {
        return serviceTypeId;
    }

    /**
     * Sets the value of the serviceTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setServiceTypeId(LikeRestrictionOfString value) {
        this.serviceTypeId = value;
    }

    /**
     * Gets the value of the technicianId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getTechnicianId() {
        return technicianId;
    }

    /**
     * Sets the value of the technicianId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setTechnicianId(LikeRestrictionOfString value) {
        this.technicianId = value;
    }

    /**
     * Gets the value of the statusCodeId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getStatusCodeId() {
        return statusCodeId;
    }

    /**
     * Sets the value of the statusCodeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setStatusCodeId(LikeRestrictionOfString value) {
        this.statusCodeId = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallDocumentScope }
     *     
     */
    public ServiceCallDocumentScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallDocumentScope }
     *     
     */
    public void setScope(ServiceCallDocumentScope value) {
        this.scope = value;
    }

}
