
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ApplicantSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicantKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantKey" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MiddleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxIdentifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DivisionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DivisionKey" minOccurs="0"/&gt;
 *         &lt;element name="DepartmentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DepartmentKey" minOccurs="0"/&gt;
 *         &lt;element name="PositionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PositionKey" minOccurs="0"/&gt;
 *         &lt;element name="ApplicationStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicationStatus"/&gt;
 *         &lt;element name="DateApplied" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RequisitionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}HRRequisitionNumberKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantSummary", propOrder = {
    "applicantKey",
    "lastModifiedDate",
    "firstName",
    "lastName",
    "middleName",
    "taxIdentifier",
    "divisionKey",
    "departmentKey",
    "positionKey",
    "applicationStatus",
    "dateApplied",
    "requisitionKey"
})
public class ApplicantSummary {

    @XmlElement(name = "ApplicantKey")
    protected ApplicantKey applicantKey;
    @XmlElement(name = "LastModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "FirstName")
    protected String firstName;
    @XmlElement(name = "LastName")
    protected String lastName;
    @XmlElement(name = "MiddleName")
    protected String middleName;
    @XmlElement(name = "TaxIdentifier")
    protected String taxIdentifier;
    @XmlElement(name = "DivisionKey")
    protected DivisionKey divisionKey;
    @XmlElement(name = "DepartmentKey")
    protected DepartmentKey departmentKey;
    @XmlElement(name = "PositionKey")
    protected PositionKey positionKey;
    @XmlElement(name = "ApplicationStatus", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ApplicationStatus applicationStatus;
    @XmlElement(name = "DateApplied", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateApplied;
    @XmlElement(name = "RequisitionKey")
    protected HRRequisitionNumberKey requisitionKey;

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
     * Gets the value of the requisitionKey property.
     * 
     * @return
     *     possible object is
     *     {@link HRRequisitionNumberKey }
     *     
     */
    public HRRequisitionNumberKey getRequisitionKey() {
        return requisitionKey;
    }

    /**
     * Sets the value of the requisitionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link HRRequisitionNumberKey }
     *     
     */
    public void setRequisitionKey(HRRequisitionNumberKey value) {
        this.requisitionKey = value;
    }

}
