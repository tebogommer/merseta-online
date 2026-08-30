
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Employee complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Employee"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EmployeeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="ClassKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeClassKey" minOccurs="0"/&gt;
 *         &lt;element name="IsActive" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Name" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Name" minOccurs="0"/&gt;
 *         &lt;element name="DefaultAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="TaxIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BirthDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="GenderCode" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Gender"/&gt;
 *         &lt;element name="Ethnicity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Ethnicity"/&gt;
 *         &lt;element name="DoesCalculateMinimumWageBalance" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DivisionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DivisionKey" minOccurs="0"/&gt;
 *         &lt;element name="DepartmentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DepartmentKey" minOccurs="0"/&gt;
 *         &lt;element name="PositionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PositionKey" minOccurs="0"/&gt;
 *         &lt;element name="SupervisorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SupervisorKey" minOccurs="0"/&gt;
 *         &lt;element name="CompanyAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CompanyAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="DefaultCashAccountFromType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CashAccountFromType"/&gt;
 *         &lt;element name="GLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="WorkHoursPerYear" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="EmploymentStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EmployeeInactivatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ReasonEmployeeInactivated" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MinimumNetPay" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="SUTAStateKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SUTAStateKey" minOccurs="0"/&gt;
 *         &lt;element name="WorkersCompensationKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WorkersCompensationKey" minOccurs="0"/&gt;
 *         &lt;element name="Vacation" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Vacation" minOccurs="0"/&gt;
 *         &lt;element name="SickTime" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SickTime" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EmploymentType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmploymentType"/&gt;
 *         &lt;element name="MaritalStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MaritalStatus"/&gt;
 *         &lt;element name="BenefitStartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastWorkedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DayOfBirth" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="MonthOfBirth" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ISO8061Month"/&gt;
 *         &lt;element name="Spouse" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Spouse" minOccurs="0"/&gt;
 *         &lt;element name="HRStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}HRStatus"/&gt;
 *         &lt;element name="Review" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Review" minOccurs="0"/&gt;
 *         &lt;element name="IsDisabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsVeteran" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsVietnamEraVeteran" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsDisabledVeteran" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsUnionEmployee" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsSmoker" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsUnitedStatesCitizen" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsI9Verified" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="I9RenewDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PrimaryPayCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PrimaryPayCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="UnionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}UnionKey" minOccurs="0"/&gt;
 *         &lt;element name="IsOtherVeteran" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="MilitaryDischargeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="BenefitExpiration" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RateClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FederalClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Addresses" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfEmployeeAddress" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Employee", propOrder = {
    "employeeKey",
    "classKey",
    "isActive",
    "name",
    "defaultAddressKey",
    "taxIdentifier",
    "birthDate",
    "genderCode",
    "ethnicity",
    "doesCalculateMinimumWageBalance",
    "divisionKey",
    "departmentKey",
    "positionKey",
    "supervisorKey",
    "companyAddressKey",
    "defaultCashAccountFromType",
    "glAccountKey",
    "workHoursPerYear",
    "employmentStartDate",
    "employeeInactivatedDate",
    "reasonEmployeeInactivated",
    "minimumNetPay",
    "sutaStateKey",
    "workersCompensationKey",
    "vacation",
    "sickTime",
    "userDefined1",
    "userDefined2",
    "employmentType",
    "maritalStatus",
    "benefitStartDate",
    "lastWorkedDate",
    "dayOfBirth",
    "monthOfBirth",
    "spouse",
    "hrStatus",
    "review",
    "isDisabled",
    "isVeteran",
    "isVietnamEraVeteran",
    "isDisabledVeteran",
    "isUnionEmployee",
    "isSmoker",
    "isUnitedStatesCitizen",
    "isI9Verified",
    "i9RenewDate",
    "primaryPayCodeKey",
    "modifiedBy",
    "modifiedDate",
    "unionKey",
    "isOtherVeteran",
    "militaryDischargeDate",
    "benefitExpiration",
    "rateClass",
    "federalClass",
    "status",
    "addresses"
})
public class Employee
    extends BusinessObject
{

    @XmlElement(name = "EmployeeKey")
    protected EmployeeKey employeeKey;
    @XmlElement(name = "ClassKey")
    protected EmployeeClassKey classKey;
    @XmlElement(name = "IsActive", required = true, type = Boolean.class, nillable = true)
    protected Boolean isActive;
    @XmlElement(name = "Name")
    protected Name name;
    @XmlElement(name = "DefaultAddressKey")
    protected EmployeeAddressKey defaultAddressKey;
    @XmlElement(name = "TaxIdentifier")
    protected String taxIdentifier;
    @XmlElement(name = "BirthDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar birthDate;
    @XmlElement(name = "GenderCode", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected Gender genderCode;
    @XmlElement(name = "Ethnicity", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected Ethnicity ethnicity;
    @XmlElement(name = "DoesCalculateMinimumWageBalance", required = true, type = Boolean.class, nillable = true)
    protected Boolean doesCalculateMinimumWageBalance;
    @XmlElement(name = "DivisionKey")
    protected DivisionKey divisionKey;
    @XmlElement(name = "DepartmentKey")
    protected DepartmentKey departmentKey;
    @XmlElement(name = "PositionKey")
    protected PositionKey positionKey;
    @XmlElement(name = "SupervisorKey")
    protected SupervisorKey supervisorKey;
    @XmlElement(name = "CompanyAddressKey")
    protected CompanyAddressKey companyAddressKey;
    @XmlElement(name = "DefaultCashAccountFromType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CashAccountFromType defaultCashAccountFromType;
    @XmlElement(name = "GLAccountKey")
    protected GLAccountNumberKey glAccountKey;
    @XmlElement(name = "WorkHoursPerYear", required = true, type = Integer.class, nillable = true)
    protected Integer workHoursPerYear;
    @XmlElement(name = "EmploymentStartDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar employmentStartDate;
    @XmlElement(name = "EmployeeInactivatedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar employeeInactivatedDate;
    @XmlElement(name = "ReasonEmployeeInactivated")
    protected String reasonEmployeeInactivated;
    @XmlElement(name = "MinimumNetPay")
    protected MoneyAmount minimumNetPay;
    @XmlElement(name = "SUTAStateKey")
    protected SUTAStateKey sutaStateKey;
    @XmlElement(name = "WorkersCompensationKey")
    protected WorkersCompensationKey workersCompensationKey;
    @XmlElement(name = "Vacation")
    protected Vacation vacation;
    @XmlElement(name = "SickTime")
    protected SickTime sickTime;
    @XmlElement(name = "UserDefined1")
    protected String userDefined1;
    @XmlElement(name = "UserDefined2")
    protected String userDefined2;
    @XmlElement(name = "EmploymentType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected EmploymentType employmentType;
    @XmlElement(name = "MaritalStatus", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected MaritalStatus maritalStatus;
    @XmlElement(name = "BenefitStartDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar benefitStartDate;
    @XmlElement(name = "LastWorkedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastWorkedDate;
    @XmlElement(name = "DayOfBirth", required = true, type = Integer.class, nillable = true)
    protected Integer dayOfBirth;
    @XmlElement(name = "MonthOfBirth", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ISO8061Month monthOfBirth;
    @XmlElement(name = "Spouse")
    protected Spouse spouse;
    @XmlElement(name = "HRStatus", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected HRStatus hrStatus;
    @XmlElement(name = "Review")
    protected Review review;
    @XmlElement(name = "IsDisabled", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDisabled;
    @XmlElement(name = "IsVeteran", required = true, type = Boolean.class, nillable = true)
    protected Boolean isVeteran;
    @XmlElement(name = "IsVietnamEraVeteran", required = true, type = Boolean.class, nillable = true)
    protected Boolean isVietnamEraVeteran;
    @XmlElement(name = "IsDisabledVeteran", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDisabledVeteran;
    @XmlElement(name = "IsUnionEmployee", required = true, type = Boolean.class, nillable = true)
    protected Boolean isUnionEmployee;
    @XmlElement(name = "IsSmoker", required = true, type = Boolean.class, nillable = true)
    protected Boolean isSmoker;
    @XmlElement(name = "IsUnitedStatesCitizen", required = true, type = Boolean.class, nillable = true)
    protected Boolean isUnitedStatesCitizen;
    @XmlElement(name = "IsI9Verified", required = true, type = Boolean.class, nillable = true)
    protected Boolean isI9Verified;
    @XmlElement(name = "I9RenewDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar i9RenewDate;
    @XmlElement(name = "PrimaryPayCodeKey")
    protected PrimaryPayCodeKey primaryPayCodeKey;
    @XmlElement(name = "ModifiedBy")
    protected String modifiedBy;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "UnionKey")
    protected UnionKey unionKey;
    @XmlElement(name = "IsOtherVeteran", required = true, type = Boolean.class, nillable = true)
    protected Boolean isOtherVeteran;
    @XmlElement(name = "MilitaryDischargeDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar militaryDischargeDate;
    @XmlElement(name = "BenefitExpiration", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar benefitExpiration;
    @XmlElement(name = "RateClass")
    protected String rateClass;
    @XmlElement(name = "FederalClass")
    protected String federalClass;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "Addresses")
    protected ArrayOfEmployeeAddress addresses;

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
     * Gets the value of the defaultAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeAddressKey }
     *     
     */
    public EmployeeAddressKey getDefaultAddressKey() {
        return defaultAddressKey;
    }

    /**
     * Sets the value of the defaultAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeAddressKey }
     *     
     */
    public void setDefaultAddressKey(EmployeeAddressKey value) {
        this.defaultAddressKey = value;
    }

    /**
     * Gets the value of the taxIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxIdentifier() {
        return taxIdentifier;
    }

    /**
     * Sets the value of the taxIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxIdentifier(String value) {
        this.taxIdentifier = value;
    }

    /**
     * Gets the value of the birthDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthDate(XMLGregorianCalendar value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the genderCode property.
     * 
     * @return
     *     possible object is
     *     {@link Gender }
     *     
     */
    public Gender getGenderCode() {
        return genderCode;
    }

    /**
     * Sets the value of the genderCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Gender }
     *     
     */
    public void setGenderCode(Gender value) {
        this.genderCode = value;
    }

    /**
     * Gets the value of the ethnicity property.
     * 
     * @return
     *     possible object is
     *     {@link Ethnicity }
     *     
     */
    public Ethnicity getEthnicity() {
        return ethnicity;
    }

    /**
     * Sets the value of the ethnicity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Ethnicity }
     *     
     */
    public void setEthnicity(Ethnicity value) {
        this.ethnicity = value;
    }

    /**
     * Gets the value of the doesCalculateMinimumWageBalance property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDoesCalculateMinimumWageBalance() {
        return doesCalculateMinimumWageBalance;
    }

    /**
     * Sets the value of the doesCalculateMinimumWageBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDoesCalculateMinimumWageBalance(Boolean value) {
        this.doesCalculateMinimumWageBalance = value;
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
     * Gets the value of the defaultCashAccountFromType property.
     * 
     * @return
     *     possible object is
     *     {@link CashAccountFromType }
     *     
     */
    public CashAccountFromType getDefaultCashAccountFromType() {
        return defaultCashAccountFromType;
    }

    /**
     * Sets the value of the defaultCashAccountFromType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccountFromType }
     *     
     */
    public void setDefaultCashAccountFromType(CashAccountFromType value) {
        this.defaultCashAccountFromType = value;
    }

    /**
     * Gets the value of the glAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getGLAccountKey() {
        return glAccountKey;
    }

    /**
     * Sets the value of the glAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setGLAccountKey(GLAccountNumberKey value) {
        this.glAccountKey = value;
    }

    /**
     * Gets the value of the workHoursPerYear property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getWorkHoursPerYear() {
        return workHoursPerYear;
    }

    /**
     * Sets the value of the workHoursPerYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setWorkHoursPerYear(Integer value) {
        this.workHoursPerYear = value;
    }

    /**
     * Gets the value of the employmentStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEmploymentStartDate() {
        return employmentStartDate;
    }

    /**
     * Sets the value of the employmentStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEmploymentStartDate(XMLGregorianCalendar value) {
        this.employmentStartDate = value;
    }

    /**
     * Gets the value of the employeeInactivatedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEmployeeInactivatedDate() {
        return employeeInactivatedDate;
    }

    /**
     * Sets the value of the employeeInactivatedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEmployeeInactivatedDate(XMLGregorianCalendar value) {
        this.employeeInactivatedDate = value;
    }

    /**
     * Gets the value of the reasonEmployeeInactivated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonEmployeeInactivated() {
        return reasonEmployeeInactivated;
    }

    /**
     * Sets the value of the reasonEmployeeInactivated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonEmployeeInactivated(String value) {
        this.reasonEmployeeInactivated = value;
    }

    /**
     * Gets the value of the minimumNetPay property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getMinimumNetPay() {
        return minimumNetPay;
    }

    /**
     * Sets the value of the minimumNetPay property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setMinimumNetPay(MoneyAmount value) {
        this.minimumNetPay = value;
    }

    /**
     * Gets the value of the sutaStateKey property.
     * 
     * @return
     *     possible object is
     *     {@link SUTAStateKey }
     *     
     */
    public SUTAStateKey getSUTAStateKey() {
        return sutaStateKey;
    }

    /**
     * Sets the value of the sutaStateKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SUTAStateKey }
     *     
     */
    public void setSUTAStateKey(SUTAStateKey value) {
        this.sutaStateKey = value;
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
     * Gets the value of the vacation property.
     * 
     * @return
     *     possible object is
     *     {@link Vacation }
     *     
     */
    public Vacation getVacation() {
        return vacation;
    }

    /**
     * Sets the value of the vacation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Vacation }
     *     
     */
    public void setVacation(Vacation value) {
        this.vacation = value;
    }

    /**
     * Gets the value of the sickTime property.
     * 
     * @return
     *     possible object is
     *     {@link SickTime }
     *     
     */
    public SickTime getSickTime() {
        return sickTime;
    }

    /**
     * Sets the value of the sickTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link SickTime }
     *     
     */
    public void setSickTime(SickTime value) {
        this.sickTime = value;
    }

    /**
     * Gets the value of the userDefined1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined1() {
        return userDefined1;
    }

    /**
     * Sets the value of the userDefined1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined1(String value) {
        this.userDefined1 = value;
    }

    /**
     * Gets the value of the userDefined2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined2() {
        return userDefined2;
    }

    /**
     * Sets the value of the userDefined2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined2(String value) {
        this.userDefined2 = value;
    }

    /**
     * Gets the value of the employmentType property.
     * 
     * @return
     *     possible object is
     *     {@link EmploymentType }
     *     
     */
    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    /**
     * Sets the value of the employmentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmploymentType }
     *     
     */
    public void setEmploymentType(EmploymentType value) {
        this.employmentType = value;
    }

    /**
     * Gets the value of the maritalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link MaritalStatus }
     *     
     */
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the value of the maritalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaritalStatus }
     *     
     */
    public void setMaritalStatus(MaritalStatus value) {
        this.maritalStatus = value;
    }

    /**
     * Gets the value of the benefitStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBenefitStartDate() {
        return benefitStartDate;
    }

    /**
     * Sets the value of the benefitStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBenefitStartDate(XMLGregorianCalendar value) {
        this.benefitStartDate = value;
    }

    /**
     * Gets the value of the lastWorkedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastWorkedDate() {
        return lastWorkedDate;
    }

    /**
     * Sets the value of the lastWorkedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastWorkedDate(XMLGregorianCalendar value) {
        this.lastWorkedDate = value;
    }

    /**
     * Gets the value of the dayOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDayOfBirth() {
        return dayOfBirth;
    }

    /**
     * Sets the value of the dayOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDayOfBirth(Integer value) {
        this.dayOfBirth = value;
    }

    /**
     * Gets the value of the monthOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link ISO8061Month }
     *     
     */
    public ISO8061Month getMonthOfBirth() {
        return monthOfBirth;
    }

    /**
     * Sets the value of the monthOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link ISO8061Month }
     *     
     */
    public void setMonthOfBirth(ISO8061Month value) {
        this.monthOfBirth = value;
    }

    /**
     * Gets the value of the spouse property.
     * 
     * @return
     *     possible object is
     *     {@link Spouse }
     *     
     */
    public Spouse getSpouse() {
        return spouse;
    }

    /**
     * Sets the value of the spouse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Spouse }
     *     
     */
    public void setSpouse(Spouse value) {
        this.spouse = value;
    }

    /**
     * Gets the value of the hrStatus property.
     * 
     * @return
     *     possible object is
     *     {@link HRStatus }
     *     
     */
    public HRStatus getHRStatus() {
        return hrStatus;
    }

    /**
     * Sets the value of the hrStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link HRStatus }
     *     
     */
    public void setHRStatus(HRStatus value) {
        this.hrStatus = value;
    }

    /**
     * Gets the value of the review property.
     * 
     * @return
     *     possible object is
     *     {@link Review }
     *     
     */
    public Review getReview() {
        return review;
    }

    /**
     * Sets the value of the review property.
     * 
     * @param value
     *     allowed object is
     *     {@link Review }
     *     
     */
    public void setReview(Review value) {
        this.review = value;
    }

    /**
     * Gets the value of the isDisabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDisabled() {
        return isDisabled;
    }

    /**
     * Sets the value of the isDisabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDisabled(Boolean value) {
        this.isDisabled = value;
    }

    /**
     * Gets the value of the isVeteran property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsVeteran() {
        return isVeteran;
    }

    /**
     * Sets the value of the isVeteran property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsVeteran(Boolean value) {
        this.isVeteran = value;
    }

    /**
     * Gets the value of the isVietnamEraVeteran property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsVietnamEraVeteran() {
        return isVietnamEraVeteran;
    }

    /**
     * Sets the value of the isVietnamEraVeteran property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsVietnamEraVeteran(Boolean value) {
        this.isVietnamEraVeteran = value;
    }

    /**
     * Gets the value of the isDisabledVeteran property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDisabledVeteran() {
        return isDisabledVeteran;
    }

    /**
     * Sets the value of the isDisabledVeteran property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDisabledVeteran(Boolean value) {
        this.isDisabledVeteran = value;
    }

    /**
     * Gets the value of the isUnionEmployee property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUnionEmployee() {
        return isUnionEmployee;
    }

    /**
     * Sets the value of the isUnionEmployee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUnionEmployee(Boolean value) {
        this.isUnionEmployee = value;
    }

    /**
     * Gets the value of the isSmoker property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSmoker() {
        return isSmoker;
    }

    /**
     * Sets the value of the isSmoker property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSmoker(Boolean value) {
        this.isSmoker = value;
    }

    /**
     * Gets the value of the isUnitedStatesCitizen property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUnitedStatesCitizen() {
        return isUnitedStatesCitizen;
    }

    /**
     * Sets the value of the isUnitedStatesCitizen property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUnitedStatesCitizen(Boolean value) {
        this.isUnitedStatesCitizen = value;
    }

    /**
     * Gets the value of the isI9Verified property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsI9Verified() {
        return isI9Verified;
    }

    /**
     * Sets the value of the isI9Verified property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsI9Verified(Boolean value) {
        this.isI9Verified = value;
    }

    /**
     * Gets the value of the i9RenewDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getI9RenewDate() {
        return i9RenewDate;
    }

    /**
     * Sets the value of the i9RenewDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setI9RenewDate(XMLGregorianCalendar value) {
        this.i9RenewDate = value;
    }

    /**
     * Gets the value of the primaryPayCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link PrimaryPayCodeKey }
     *     
     */
    public PrimaryPayCodeKey getPrimaryPayCodeKey() {
        return primaryPayCodeKey;
    }

    /**
     * Sets the value of the primaryPayCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrimaryPayCodeKey }
     *     
     */
    public void setPrimaryPayCodeKey(PrimaryPayCodeKey value) {
        this.primaryPayCodeKey = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
    }

    /**
     * Gets the value of the unionKey property.
     * 
     * @return
     *     possible object is
     *     {@link UnionKey }
     *     
     */
    public UnionKey getUnionKey() {
        return unionKey;
    }

    /**
     * Sets the value of the unionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link UnionKey }
     *     
     */
    public void setUnionKey(UnionKey value) {
        this.unionKey = value;
    }

    /**
     * Gets the value of the isOtherVeteran property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOtherVeteran() {
        return isOtherVeteran;
    }

    /**
     * Sets the value of the isOtherVeteran property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOtherVeteran(Boolean value) {
        this.isOtherVeteran = value;
    }

    /**
     * Gets the value of the militaryDischargeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getMilitaryDischargeDate() {
        return militaryDischargeDate;
    }

    /**
     * Sets the value of the militaryDischargeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setMilitaryDischargeDate(XMLGregorianCalendar value) {
        this.militaryDischargeDate = value;
    }

    /**
     * Gets the value of the benefitExpiration property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBenefitExpiration() {
        return benefitExpiration;
    }

    /**
     * Sets the value of the benefitExpiration property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBenefitExpiration(XMLGregorianCalendar value) {
        this.benefitExpiration = value;
    }

    /**
     * Gets the value of the rateClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateClass() {
        return rateClass;
    }

    /**
     * Sets the value of the rateClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateClass(String value) {
        this.rateClass = value;
    }

    /**
     * Gets the value of the federalClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFederalClass() {
        return federalClass;
    }

    /**
     * Sets the value of the federalClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFederalClass(String value) {
        this.federalClass = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the addresses property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEmployeeAddress }
     *     
     */
    public ArrayOfEmployeeAddress getAddresses() {
        return addresses;
    }

    /**
     * Sets the value of the addresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEmployeeAddress }
     *     
     */
    public void setAddresses(ArrayOfEmployeeAddress value) {
        this.addresses = value;
    }

}
