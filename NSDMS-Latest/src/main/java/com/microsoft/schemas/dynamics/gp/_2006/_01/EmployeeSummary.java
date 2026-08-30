
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeeSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeeSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EmployeeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="ClassKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeClassKey" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Name" minOccurs="0"/&gt;
 *         &lt;element name="DivisionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DivisionKey" minOccurs="0"/&gt;
 *         &lt;element name="DepartmentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DepartmentKey" minOccurs="0"/&gt;
 *         &lt;element name="PositionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PositionKey" minOccurs="0"/&gt;
 *         &lt;element name="SupervisorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SupervisorKey" minOccurs="0"/&gt;
 *         &lt;element name="WorkersCompensationKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WorkersCompensationKey" minOccurs="0"/&gt;
 *         &lt;element name="CompanyAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CompanyAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="IsActive" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeeSummary", propOrder = {
    "employeeKey",
    "classKey",
    "name",
    "divisionKey",
    "departmentKey",
    "positionKey",
    "supervisorKey",
    "workersCompensationKey",
    "companyAddressKey",
    "isActive"
})
public class EmployeeSummary {

    @XmlElement(name = "EmployeeKey")
    protected EmployeeKey employeeKey;
    @XmlElement(name = "ClassKey")
    protected EmployeeClassKey classKey;
    @XmlElement(name = "Name")
    protected Name name;
    @XmlElement(name = "DivisionKey")
    protected DivisionKey divisionKey;
    @XmlElement(name = "DepartmentKey")
    protected DepartmentKey departmentKey;
    @XmlElement(name = "PositionKey")
    protected PositionKey positionKey;
    @XmlElement(name = "SupervisorKey")
    protected SupervisorKey supervisorKey;
    @XmlElement(name = "WorkersCompensationKey")
    protected WorkersCompensationKey workersCompensationKey;
    @XmlElement(name = "CompanyAddressKey")
    protected CompanyAddressKey companyAddressKey;
    @XmlElement(name = "IsActive", required = true, type = Boolean.class, nillable = true)
    protected Boolean isActive;

    /**
     * Gets the value of the employeeKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeKey }
     *     
     */
    public EmployeeKey getEmployeeKey() {
        return employeeKey;
    }

    /**
     * Sets the value of the employeeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeKey }
     *     
     */
    public void setEmployeeKey(EmployeeKey value) {
        this.employeeKey = value;
    }

    /**
     * Gets the value of the classKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeClassKey }
     *     
     */
    public EmployeeClassKey getClassKey() {
        return classKey;
    }

    /**
     * Sets the value of the classKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeClassKey }
     *     
     */
    public void setClassKey(EmployeeClassKey value) {
        this.classKey = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Name }
     *     
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Name }
     *     
     */
    public void setName(Name value) {
        this.name = value;
    }

    /**
     * Gets the value of the divisionKey property.
     * 
     * @return
     *     possible object is
     *     {@link DivisionKey }
     *     
     */
    public DivisionKey getDivisionKey() {
        return divisionKey;
    }

    /**
     * Sets the value of the divisionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link DivisionKey }
     *     
     */
    public void setDivisionKey(DivisionKey value) {
        this.divisionKey = value;
    }

    /**
     * Gets the value of the departmentKey property.
     * 
     * @return
     *     possible object is
     *     {@link DepartmentKey }
     *     
     */
    public DepartmentKey getDepartmentKey() {
        return departmentKey;
    }

    /**
     * Sets the value of the departmentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepartmentKey }
     *     
     */
    public void setDepartmentKey(DepartmentKey value) {
        this.departmentKey = value;
    }

    /**
     * Gets the value of the positionKey property.
     * 
     * @return
     *     possible object is
     *     {@link PositionKey }
     *     
     */
    public PositionKey getPositionKey() {
        return positionKey;
    }

    /**
     * Sets the value of the positionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PositionKey }
     *     
     */
    public void setPositionKey(PositionKey value) {
        this.positionKey = value;
    }

    /**
     * Gets the value of the supervisorKey property.
     * 
     * @return
     *     possible object is
     *     {@link SupervisorKey }
     *     
     */
    public SupervisorKey getSupervisorKey() {
        return supervisorKey;
    }

    /**
     * Sets the value of the supervisorKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupervisorKey }
     *     
     */
    public void setSupervisorKey(SupervisorKey value) {
        this.supervisorKey = value;
    }

    /**
     * Gets the value of the workersCompensationKey property.
     * 
     * @return
     *     possible object is
     *     {@link WorkersCompensationKey }
     *     
     */
    public WorkersCompensationKey getWorkersCompensationKey() {
        return workersCompensationKey;
    }

    /**
     * Sets the value of the workersCompensationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkersCompensationKey }
     *     
     */
    public void setWorkersCompensationKey(WorkersCompensationKey value) {
        this.workersCompensationKey = value;
    }

    /**
     * Gets the value of the companyAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyAddressKey }
     *     
     */
    public CompanyAddressKey getCompanyAddressKey() {
        return companyAddressKey;
    }

    /**
     * Sets the value of the companyAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyAddressKey }
     *     
     */
    public void setCompanyAddressKey(CompanyAddressKey value) {
        this.companyAddressKey = value;
    }

    /**
     * Gets the value of the isActive property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsActive() {
        return isActive;
    }

    /**
     * Sets the value of the isActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsActive(Boolean value) {
        this.isActive = value;
    }

}
