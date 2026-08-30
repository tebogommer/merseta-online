
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;
import com.microsoft.schemas.dynamics._2006._01.Key;


/**
 * <p>Java class for BackOfficeRoleAssignmentKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BackOfficeRoleAssignmentKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/2006/01}Key"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RoleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BackOfficeRoleKey" minOccurs="0"/&gt;
 *         &lt;element name="CompanyKey" type="{http://schemas.microsoft.com/dynamics/2006/01}CompanyKey" minOccurs="0"/&gt;
 *         &lt;element name="UserKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}UserKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BackOfficeRoleAssignmentKey", propOrder = {
    "roleKey",
    "companyKey",
    "userKey"
})
public class BackOfficeRoleAssignmentKey
    extends Key
{

    @XmlElement(name = "RoleKey")
    protected BackOfficeRoleKey roleKey;
    @XmlElement(name = "CompanyKey")
    protected CompanyKey companyKey;
    @XmlElement(name = "UserKey")
    protected UserKey userKey;

    /**
     * Gets the value of the roleKey property.
     * 
     * @return
     *     possible object is
     *     {@link BackOfficeRoleKey }
     *     
     */
    public BackOfficeRoleKey getRoleKey() {
        return roleKey;
    }

    /**
     * Sets the value of the roleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BackOfficeRoleKey }
     *     
     */
    public void setRoleKey(BackOfficeRoleKey value) {
        this.roleKey = value;
    }

    /**
     * Gets the value of the companyKey property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyKey }
     *     
     */
    public CompanyKey getCompanyKey() {
        return companyKey;
    }

    /**
     * Sets the value of the companyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyKey }
     *     
     */
    public void setCompanyKey(CompanyKey value) {
        this.companyKey = value;
    }

    /**
     * Gets the value of the userKey property.
     * 
     * @return
     *     possible object is
     *     {@link UserKey }
     *     
     */
    public UserKey getUserKey() {
        return userKey;
    }

    /**
     * Sets the value of the userKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserKey }
     *     
     */
    public void setUserKey(UserKey value) {
        this.userKey = value;
    }

}
