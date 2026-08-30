
package com.microsoft.schemas.dynamics._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics.security._2006._01.RoleKey;


/**
 * <p>Java class for Context complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Context"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="WorkOnBehalfOf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OrganizationKey" type="{http://schemas.microsoft.com/dynamics/2006/01}OrganizationKey" minOccurs="0"/&gt;
 *         &lt;element name="CultureName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyType" type="{http://schemas.microsoft.com/dynamics/2006/01}CurrencyType"/&gt;
 *         &lt;element name="RoleKey" type="{http://schemas.microsoft.com/dynamics/security/2006/01}RoleKey" minOccurs="0"/&gt;
 *         &lt;element name="TenantName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Context", propOrder = {
    "workOnBehalfOf",
    "organizationKey",
    "cultureName",
    "currencyType",
    "roleKey",
    "tenantName"
})
public class Context {

    @XmlElement(name = "WorkOnBehalfOf")
    protected String workOnBehalfOf;
    @XmlElement(name = "OrganizationKey")
    protected OrganizationKey organizationKey;
    @XmlElement(name = "CultureName")
    protected String cultureName;
    @XmlElement(name = "CurrencyType", required = true)
    @XmlSchemaType(name = "string")
    protected CurrencyType currencyType;
    @XmlElement(name = "RoleKey")
    protected RoleKey roleKey;
    @XmlElement(name = "TenantName")
    protected String tenantName;

    /**
     * Gets the value of the workOnBehalfOf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkOnBehalfOf() {
        return workOnBehalfOf;
    }

    /**
     * Sets the value of the workOnBehalfOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkOnBehalfOf(String value) {
        this.workOnBehalfOf = value;
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
     * Gets the value of the cultureName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCultureName() {
        return cultureName;
    }

    /**
     * Sets the value of the cultureName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCultureName(String value) {
        this.cultureName = value;
    }

    /**
     * Gets the value of the currencyType property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyType }
     *     
     */
    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    /**
     * Sets the value of the currencyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyType }
     *     
     */
    public void setCurrencyType(CurrencyType value) {
        this.currencyType = value;
    }

    /**
     * Gets the value of the roleKey property.
     * 
     * @return
     *     possible object is
     *     {@link RoleKey }
     *     
     */
    public RoleKey getRoleKey() {
        return roleKey;
    }

    /**
     * Sets the value of the roleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleKey }
     *     
     */
    public void setRoleKey(RoleKey value) {
        this.roleKey = value;
    }

    /**
     * Gets the value of the tenantName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTenantName() {
        return tenantName;
    }

    /**
     * Sets the value of the tenantName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTenantName(String value) {
        this.tenantName = value;
    }

}
