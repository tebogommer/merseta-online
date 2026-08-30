
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeeCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeeCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EmployeeKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="FamilyName" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="GivenName" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="EmployeeClassId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="PositionId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="DivisionId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="WorkersCompensationId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="DepartmentId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="SupervisorId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CompanyAddressId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="IsActive" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *         &lt;element name="Scope" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeScope"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeCriteria", propOrder = {
    "employeeKeyId",
    "familyName",
    "givenName",
    "employeeClassId",
    "positionId",
    "divisionId",
    "workersCompensationId",
    "departmentId",
    "supervisorId",
    "companyAddressId",
    "isActive",
    "scope"
})
public class EmployeeCriteria
    extends Criteria
{

    @XmlElement(name = "EmployeeKeyId")
    protected LikeRestrictionOfString employeeKeyId;
    @XmlElement(name = "FamilyName")
    protected LikeRestrictionOfString familyName;
    @XmlElement(name = "GivenName")
    protected LikeRestrictionOfString givenName;
    @XmlElement(name = "EmployeeClassId")
    protected LikeRestrictionOfString employeeClassId;
    @XmlElement(name = "PositionId")
    protected LikeRestrictionOfString positionId;
    @XmlElement(name = "DivisionId")
    protected LikeRestrictionOfString divisionId;
    @XmlElement(name = "WorkersCompensationId")
    protected LikeRestrictionOfString workersCompensationId;
    @XmlElement(name = "DepartmentId")
    protected LikeRestrictionOfString departmentId;
    @XmlElement(name = "SupervisorId")
    protected LikeRestrictionOfString supervisorId;
    @XmlElement(name = "CompanyAddressId")
    protected LikeRestrictionOfString companyAddressId;
    @XmlElement(name = "IsActive")
    protected RestrictionOfNullableOfBoolean isActive;
    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected EmployeeScope scope;

    /**
     * Gets the value of the employeeKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getEmployeeKeyId() {
        return employeeKeyId;
    }

    /**
     * Sets the value of the employeeKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setEmployeeKeyId(LikeRestrictionOfString value) {
        this.employeeKeyId = value;
    }

    /**
     * Gets the value of the familyName property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getFamilyName() {
        return familyName;
    }

    /**
     * Sets the value of the familyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setFamilyName(LikeRestrictionOfString value) {
        this.familyName = value;
    }

    /**
     * Gets the value of the givenName property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getGivenName() {
        return givenName;
    }

    /**
     * Sets the value of the givenName property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setGivenName(LikeRestrictionOfString value) {
        this.givenName = value;
    }

    /**
     * Gets the value of the employeeClassId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getEmployeeClassId() {
        return employeeClassId;
    }

    /**
     * Sets the value of the employeeClassId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setEmployeeClassId(LikeRestrictionOfString value) {
        this.employeeClassId = value;
    }

    /**
     * Gets the value of the positionId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getPositionId() {
        return positionId;
    }

    /**
     * Sets the value of the positionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setPositionId(LikeRestrictionOfString value) {
        this.positionId = value;
    }

    /**
     * Gets the value of the divisionId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the value of the divisionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setDivisionId(LikeRestrictionOfString value) {
        this.divisionId = value;
    }

    /**
     * Gets the value of the workersCompensationId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getWorkersCompensationId() {
        return workersCompensationId;
    }

    /**
     * Sets the value of the workersCompensationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setWorkersCompensationId(LikeRestrictionOfString value) {
        this.workersCompensationId = value;
    }

    /**
     * Gets the value of the departmentId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getDepartmentId() {
        return departmentId;
    }

    /**
     * Sets the value of the departmentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setDepartmentId(LikeRestrictionOfString value) {
        this.departmentId = value;
    }

    /**
     * Gets the value of the supervisorId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getSupervisorId() {
        return supervisorId;
    }

    /**
     * Sets the value of the supervisorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setSupervisorId(LikeRestrictionOfString value) {
        this.supervisorId = value;
    }

    /**
     * Gets the value of the companyAddressId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getCompanyAddressId() {
        return companyAddressId;
    }

    /**
     * Sets the value of the companyAddressId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setCompanyAddressId(LikeRestrictionOfString value) {
        this.companyAddressId = value;
    }

    /**
     * Gets the value of the isActive property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getIsActive() {
        return isActive;
    }

    /**
     * Sets the value of the isActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setIsActive(RestrictionOfNullableOfBoolean value) {
        this.isActive = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeScope }
     *     
     */
    public EmployeeScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeScope }
     *     
     */
    public void setScope(EmployeeScope value) {
        this.scope = value;
    }

}
