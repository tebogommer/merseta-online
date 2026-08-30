
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.OrganizationKey;


/**
 * <p>Java class for BusinessObjectUserAssignment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessObjectUserAssignment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="User" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BusinessObjectTypeId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="OrganizationKey" type="{http://schemas.microsoft.com/dynamics/2006/01}OrganizationKey" minOccurs="0"/&gt;
 *         &lt;element name="BusinessObjectKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessObjectUserAssignment", propOrder = {
    "user",
    "businessObjectTypeId",
    "organizationKey",
    "businessObjectKey"
})
public class BusinessObjectUserAssignment {

    @XmlElement(name = "User")
    protected String user;
    @XmlElement(name = "BusinessObjectTypeId", required = true)
    protected String businessObjectTypeId;
    @XmlElement(name = "OrganizationKey")
    protected OrganizationKey organizationKey;
    @XmlElement(name = "BusinessObjectKey")
    protected String businessObjectKey;

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Gets the value of the businessObjectTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessObjectTypeId() {
        return businessObjectTypeId;
    }

    /**
     * Sets the value of the businessObjectTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessObjectTypeId(String value) {
        this.businessObjectTypeId = value;
    }

    /**
     * Gets the value of the organizationKey property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationKey }
     *     
     */
    public OrganizationKey getOrganizationKey() {
        return organizationKey;
    }

    /**
     * Sets the value of the organizationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationKey }
     *     
     */
    public void setOrganizationKey(OrganizationKey value) {
        this.organizationKey = value;
    }

    /**
     * Gets the value of the businessObjectKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessObjectKey() {
        return businessObjectKey;
    }

    /**
     * Sets the value of the businessObjectKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessObjectKey(String value) {
        this.businessObjectKey = value;
    }

}
