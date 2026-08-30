
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Applicant complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Applicant"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsDisabledVeteran" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="SequenceKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SequenceKey" minOccurs="0"/&gt;
 *         &lt;element name="Address" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantAddress" minOccurs="0"/&gt;
 *         &lt;element name="TaxIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MiddleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DateApplied" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="HRRequisistionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}HRRequisitionNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="CompanyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DivisionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DivisionKey" minOccurs="0"/&gt;
 *         &lt;element name="DepartmentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DepartmentKey" minOccurs="0"/&gt;
 *         &lt;element name="PositionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PositionKey" minOccurs="0"/&gt;
 *         &lt;element name="Location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicationStatus"/&gt;
 *         &lt;element name="RejectionInformation" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicationRejectionInformation" minOccurs="0"/&gt;
 *         &lt;element name="IsWillRelocate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsReplyLetterSent" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ApplicationColor" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicationColor" minOccurs="0"/&gt;
 *         &lt;element name="ReferenceInformation" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicationReferenceInformation" minOccurs="0"/&gt;
 *         &lt;element name="Gender" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Gender"/&gt;
 *         &lt;element name="Ethnicity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Ethnicity"/&gt;
 *         &lt;element name="IsDisabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsVeteran" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsVietnamEraVeteran" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsOtherVeteran" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AgeCode" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AgeCode"/&gt;
 *         &lt;element name="Applications" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfApplicantApplication" minOccurs="0"/&gt;
 *         &lt;element name="Skills" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfApplicantSkill" minOccurs="0"/&gt;
 *         &lt;element name="References" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfApplicantReference" minOccurs="0"/&gt;
 *         &lt;element name="Schools" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfApplicantEducation" minOccurs="0"/&gt;
 *         &lt;element name="Tests" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfApplicantTest" minOccurs="0"/&gt;
 *         &lt;element name="Interviews" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfApplicantInterview" minOccurs="0"/&gt;
 *         &lt;element name="PreviousEmployers" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfApplicantWorkHistory" minOccurs="0"/&gt;
 *         &lt;element name="ApplicantKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantKey" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Applicant", propOrder = {
    "isDisabledVeteran",
    "sequenceKey",
    "address",
    "taxIdentifier",
    "firstName",
    "lastName",
    "middleName",
    "dateApplied",
    "hrRequisistionKey",
    "companyCode",
    "divisionKey",
    "departmentKey",
    "positionKey",
    "location",
    "applicationStatus",
    "rejectionInformation",
    "isWillRelocate",
    "isReplyLetterSent",
    "applicationColor",
    "referenceInformation",
    "gender",
    "ethnicity",
    "isDisabled",
    "isVeteran",
    "isVietnamEraVeteran",
    "isOtherVeteran",
    "ageCode",
    "applications",
    "skills",
    "references",
    "schools",
    "tests",
    "interviews",
    "previousEmployers",
    "applicantKey",
    "lastModifiedBy",
    "lastModifiedDate"
})
public class Applicant
    extends BusinessObject
{

    @XmlElement(name = "IsDisabledVeteran", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDisabledVeteran;
    @XmlElement(name = "SequenceKey")
    protected SequenceKey sequenceKey;
    @XmlElement(name = "Address")
    protected ApplicantAddress address;
    @XmlElement(name = "TaxIdentifier")
    protected String taxIdentifier;
    @XmlElement(name = "FirstName")
    protected String firstName;
    @XmlElement(name = "LastName")
    protected String lastName;
    @XmlElement(name = "MiddleName")
    protected String middleName;
    @XmlElement(name = "DateApplied", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateApplied;
    @XmlElement(name = "HRRequisistionKey")
    protected HRRequisitionNumberKey hrRequisistionKey;
    @XmlElement(name = "CompanyCode")
    protected String companyCode;
    @XmlElement(name = "DivisionKey")
    protected DivisionKey divisionKey;
    @XmlElement(name = "DepartmentKey")
    protected DepartmentKey departmentKey;
    @XmlElement(name = "PositionKey")
    protected PositionKey positionKey;
    @XmlElement(name = "Location")
    protected String location;
    @XmlElement(name = "ApplicationStatus", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ApplicationStatus applicationStatus;
    @XmlElement(name = "RejectionInformation")
    protected ApplicationRejectionInformation rejectionInformation;
    @XmlElement(name = "IsWillRelocate", required = true, type = Boolean.class, nillable = true)
    protected Boolean isWillRelocate;
    @XmlElement(name = "IsReplyLetterSent", required = true, type = Boolean.class, nillable = true)
    protected Boolean isReplyLetterSent;
    @XmlElement(name = "ApplicationColor")
    protected ApplicationColor applicationColor;
    @XmlElement(name = "ReferenceInformation")
    protected ApplicationReferenceInformation referenceInformation;
    @XmlElement(name = "Gender", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected Gender gender;
    @XmlElement(name = "Ethnicity", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected Ethnicity ethnicity;
    @XmlElement(name = "IsDisabled", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDisabled;
    @XmlElement(name = "IsVeteran", required = true, type = Boolean.class, nillable = true)
    protected Boolean isVeteran;
    @XmlElement(name = "IsVietnamEraVeteran", required = true, type = Boolean.class, nillable = true)
    protected Boolean isVietnamEraVeteran;
    @XmlElement(name = "IsOtherVeteran", required = true, type = Boolean.class, nillable = true)
    protected Boolean isOtherVeteran;
    @XmlElement(name = "AgeCode", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected AgeCode ageCode;
    @XmlElement(name = "Applications")
    protected ArrayOfApplicantApplication applications;
    @XmlElement(name = "Skills")
    protected ArrayOfApplicantSkill skills;
    @XmlElement(name = "References")
    protected ArrayOfApplicantReference references;
    @XmlElement(name = "Schools")
    protected ArrayOfApplicantEducation schools;
    @XmlElement(name = "Tests")
    protected ArrayOfApplicantTest tests;
    @XmlElement(name = "Interviews")
    protected ArrayOfApplicantInterview interviews;
    @XmlElement(name = "PreviousEmployers")
    protected ArrayOfApplicantWorkHistory previousEmployers;
    @XmlElement(name = "ApplicantKey")
    protected ApplicantKey applicantKey;
    @XmlElement(name = "LastModifiedBy")
    protected String lastModifiedBy;
    @XmlElement(name = "LastModifiedDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;

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
     * Gets the value of the sequenceKey property.
     * 
     * @return
     *     possible object is
     *     {@link SequenceKey }
     *     
     */
    public SequenceKey getSequenceKey() {
        return sequenceKey;
    }

    /**
     * Sets the value of the sequenceKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SequenceKey }
     *     
     */
    public void setSequenceKey(SequenceKey value) {
        this.sequenceKey = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantAddress }
     *     
     */
    public ApplicantAddress getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantAddress }
     *     
     */
    public void setAddress(ApplicantAddress value) {
        this.address = value;
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
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the dateApplied property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateApplied() {
        return dateApplied;
    }

    /**
     * Sets the value of the dateApplied property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateApplied(XMLGregorianCalendar value) {
        this.dateApplied = value;
    }

    /**
     * Gets the value of the hrRequisistionKey property.
     * 
     * @return
     *     possible object is
     *     {@link HRRequisitionNumberKey }
     *     
     */
    public HRRequisitionNumberKey getHRRequisistionKey() {
        return hrRequisistionKey;
    }

    /**
     * Sets the value of the hrRequisistionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link HRRequisitionNumberKey }
     *     
     */
    public void setHRRequisistionKey(HRRequisitionNumberKey value) {
        this.hrRequisistionKey = value;
    }

    /**
     * Gets the value of the companyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * Sets the value of the companyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompanyCode(String value) {
        this.companyCode = value;
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
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocation(String value) {
        this.location = value;
    }

    /**
     * Gets the value of the applicationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationStatus }
     *     
     */
    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    /**
     * Sets the value of the applicationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationStatus }
     *     
     */
    public void setApplicationStatus(ApplicationStatus value) {
        this.applicationStatus = value;
    }

    /**
     * Gets the value of the rejectionInformation property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationRejectionInformation }
     *     
     */
    public ApplicationRejectionInformation getRejectionInformation() {
        return rejectionInformation;
    }

    /**
     * Sets the value of the rejectionInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationRejectionInformation }
     *     
     */
    public void setRejectionInformation(ApplicationRejectionInformation value) {
        this.rejectionInformation = value;
    }

    /**
     * Gets the value of the isWillRelocate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsWillRelocate() {
        return isWillRelocate;
    }

    /**
     * Sets the value of the isWillRelocate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsWillRelocate(Boolean value) {
        this.isWillRelocate = value;
    }

    /**
     * Gets the value of the isReplyLetterSent property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsReplyLetterSent() {
        return isReplyLetterSent;
    }

    /**
     * Sets the value of the isReplyLetterSent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsReplyLetterSent(Boolean value) {
        this.isReplyLetterSent = value;
    }

    /**
     * Gets the value of the applicationColor property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationColor }
     *     
     */
    public ApplicationColor getApplicationColor() {
        return applicationColor;
    }

    /**
     * Sets the value of the applicationColor property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationColor }
     *     
     */
    public void setApplicationColor(ApplicationColor value) {
        this.applicationColor = value;
    }

    /**
     * Gets the value of the referenceInformation property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationReferenceInformation }
     *     
     */
    public ApplicationReferenceInformation getReferenceInformation() {
        return referenceInformation;
    }

    /**
     * Sets the value of the referenceInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationReferenceInformation }
     *     
     */
    public void setReferenceInformation(ApplicationReferenceInformation value) {
        this.referenceInformation = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link Gender }
     *     
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link Gender }
     *     
     */
    public void setGender(Gender value) {
        this.gender = value;
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
     * Gets the value of the ageCode property.
     * 
     * @return
     *     possible object is
     *     {@link AgeCode }
     *     
     */
    public AgeCode getAgeCode() {
        return ageCode;
    }

    /**
     * Sets the value of the ageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link AgeCode }
     *     
     */
    public void setAgeCode(AgeCode value) {
        this.ageCode = value;
    }

    /**
     * Gets the value of the applications property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfApplicantApplication }
     *     
     */
    public ArrayOfApplicantApplication getApplications() {
        return applications;
    }

    /**
     * Sets the value of the applications property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfApplicantApplication }
     *     
     */
    public void setApplications(ArrayOfApplicantApplication value) {
        this.applications = value;
    }

    /**
     * Gets the value of the skills property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfApplicantSkill }
     *     
     */
    public ArrayOfApplicantSkill getSkills() {
        return skills;
    }

    /**
     * Sets the value of the skills property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfApplicantSkill }
     *     
     */
    public void setSkills(ArrayOfApplicantSkill value) {
        this.skills = value;
    }

    /**
     * Gets the value of the references property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfApplicantReference }
     *     
     */
    public ArrayOfApplicantReference getReferences() {
        return references;
    }

    /**
     * Sets the value of the references property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfApplicantReference }
     *     
     */
    public void setReferences(ArrayOfApplicantReference value) {
        this.references = value;
    }

    /**
     * Gets the value of the schools property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfApplicantEducation }
     *     
     */
    public ArrayOfApplicantEducation getSchools() {
        return schools;
    }

    /**
     * Sets the value of the schools property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfApplicantEducation }
     *     
     */
    public void setSchools(ArrayOfApplicantEducation value) {
        this.schools = value;
    }

    /**
     * Gets the value of the tests property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfApplicantTest }
     *     
     */
    public ArrayOfApplicantTest getTests() {
        return tests;
    }

    /**
     * Sets the value of the tests property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfApplicantTest }
     *     
     */
    public void setTests(ArrayOfApplicantTest value) {
        this.tests = value;
    }

    /**
     * Gets the value of the interviews property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfApplicantInterview }
     *     
     */
    public ArrayOfApplicantInterview getInterviews() {
        return interviews;
    }

    /**
     * Sets the value of the interviews property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfApplicantInterview }
     *     
     */
    public void setInterviews(ArrayOfApplicantInterview value) {
        this.interviews = value;
    }

    /**
     * Gets the value of the previousEmployers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfApplicantWorkHistory }
     *     
     */
    public ArrayOfApplicantWorkHistory getPreviousEmployers() {
        return previousEmployers;
    }

    /**
     * Sets the value of the previousEmployers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfApplicantWorkHistory }
     *     
     */
    public void setPreviousEmployers(ArrayOfApplicantWorkHistory value) {
        this.previousEmployers = value;
    }

    /**
     * Gets the value of the applicantKey property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantKey }
     *     
     */
    public ApplicantKey getApplicantKey() {
        return applicantKey;
    }

    /**
     * Sets the value of the applicantKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantKey }
     *     
     */
    public void setApplicantKey(ApplicantKey value) {
        this.applicantKey = value;
    }

    /**
     * Gets the value of the lastModifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Sets the value of the lastModifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastModifiedBy(String value) {
        this.lastModifiedBy = value;
    }

    /**
     * Gets the value of the lastModifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the value of the lastModifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastModifiedDate(XMLGregorianCalendar value) {
        this.lastModifiedDate = value;
    }

}
