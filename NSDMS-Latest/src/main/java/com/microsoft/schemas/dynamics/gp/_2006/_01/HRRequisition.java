
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for HRRequisition complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HRRequisition"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CompanyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PositionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PositionKey" minOccurs="0"/&gt;
 *         &lt;element name="ManagerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManagerKey" minOccurs="0"/&gt;
 *         &lt;element name="RequisitionNumber" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}HRRequisitionNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}HRRequisitionStatus"/&gt;
 *         &lt;element name="DepartmentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DepartmentKey" minOccurs="0"/&gt;
 *         &lt;element name="DivisionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DivisionKey" minOccurs="0"/&gt;
 *         &lt;element name="SupervisorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SupervisorKey" minOccurs="0"/&gt;
 *         &lt;element name="InternalPostDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="InternalCloseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="OpeningDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Recruiter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="JobPostingType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}HRRequisitionPostingType"/&gt;
 *         &lt;element name="AdvertisingList" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}HRRequisitionAdvertisingList" minOccurs="0"/&gt;
 *         &lt;element name="PositionsAvailable" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="PositionsFilled" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ApplicantsApplied" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ApplicantsInterviewed" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="RequisitionCosts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}HRRequisitionCosts" minOccurs="0"/&gt;
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
@XmlType(name = "HRRequisition", propOrder = {
    "companyCode",
    "positionKey",
    "managerKey",
    "requisitionNumber",
    "status",
    "departmentKey",
    "divisionKey",
    "supervisorKey",
    "internalPostDate",
    "internalCloseDate",
    "openingDate",
    "recruiter",
    "jobPostingType",
    "advertisingList",
    "positionsAvailable",
    "positionsFilled",
    "applicantsApplied",
    "applicantsInterviewed",
    "requisitionCosts",
    "lastModifiedBy",
    "lastModifiedDate"
})
public class HRRequisition
    extends BusinessObject
{

    @XmlElement(name = "CompanyCode")
    protected String companyCode;
    @XmlElement(name = "PositionKey")
    protected PositionKey positionKey;
    @XmlElement(name = "ManagerKey")
    protected ManagerKey managerKey;
    @XmlElement(name = "RequisitionNumber")
    protected HRRequisitionNumberKey requisitionNumber;
    @XmlElement(name = "Status", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected HRRequisitionStatus status;
    @XmlElement(name = "DepartmentKey")
    protected DepartmentKey departmentKey;
    @XmlElement(name = "DivisionKey")
    protected DivisionKey divisionKey;
    @XmlElement(name = "SupervisorKey")
    protected SupervisorKey supervisorKey;
    @XmlElement(name = "InternalPostDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar internalPostDate;
    @XmlElement(name = "InternalCloseDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar internalCloseDate;
    @XmlElement(name = "OpeningDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar openingDate;
    @XmlElement(name = "Recruiter")
    protected String recruiter;
    @XmlElement(name = "JobPostingType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected HRRequisitionPostingType jobPostingType;
    @XmlElement(name = "AdvertisingList")
    protected HRRequisitionAdvertisingList advertisingList;
    @XmlElement(name = "PositionsAvailable", required = true, type = Integer.class, nillable = true)
    protected Integer positionsAvailable;
    @XmlElement(name = "PositionsFilled", required = true, type = Integer.class, nillable = true)
    protected Integer positionsFilled;
    @XmlElement(name = "ApplicantsApplied", required = true, type = Integer.class, nillable = true)
    protected Integer applicantsApplied;
    @XmlElement(name = "ApplicantsInterviewed", required = true, type = Integer.class, nillable = true)
    protected Integer applicantsInterviewed;
    @XmlElement(name = "RequisitionCosts")
    protected HRRequisitionCosts requisitionCosts;
    @XmlElement(name = "LastModifiedBy")
    protected String lastModifiedBy;
    @XmlElement(name = "LastModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;

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
     * Gets the value of the managerKey property.
     * 
     * @return
     *     possible object is
     *     {@link ManagerKey }
     *     
     */
    public ManagerKey getManagerKey() {
        return managerKey;
    }

    /**
     * Sets the value of the managerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManagerKey }
     *     
     */
    public void setManagerKey(ManagerKey value) {
        this.managerKey = value;
    }

    /**
     * Gets the value of the requisitionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link HRRequisitionNumberKey }
     *     
     */
    public HRRequisitionNumberKey getRequisitionNumber() {
        return requisitionNumber;
    }

    /**
     * Sets the value of the requisitionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link HRRequisitionNumberKey }
     *     
     */
    public void setRequisitionNumber(HRRequisitionNumberKey value) {
        this.requisitionNumber = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link HRRequisitionStatus }
     *     
     */
    public HRRequisitionStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link HRRequisitionStatus }
     *     
     */
    public void setStatus(HRRequisitionStatus value) {
        this.status = value;
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
     * Gets the value of the internalPostDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInternalPostDate() {
        return internalPostDate;
    }

    /**
     * Sets the value of the internalPostDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInternalPostDate(XMLGregorianCalendar value) {
        this.internalPostDate = value;
    }

    /**
     * Gets the value of the internalCloseDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInternalCloseDate() {
        return internalCloseDate;
    }

    /**
     * Sets the value of the internalCloseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInternalCloseDate(XMLGregorianCalendar value) {
        this.internalCloseDate = value;
    }

    /**
     * Gets the value of the openingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOpeningDate() {
        return openingDate;
    }

    /**
     * Sets the value of the openingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOpeningDate(XMLGregorianCalendar value) {
        this.openingDate = value;
    }

    /**
     * Gets the value of the recruiter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecruiter() {
        return recruiter;
    }

    /**
     * Sets the value of the recruiter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecruiter(String value) {
        this.recruiter = value;
    }

    /**
     * Gets the value of the jobPostingType property.
     * 
     * @return
     *     possible object is
     *     {@link HRRequisitionPostingType }
     *     
     */
    public HRRequisitionPostingType getJobPostingType() {
        return jobPostingType;
    }

    /**
     * Sets the value of the jobPostingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link HRRequisitionPostingType }
     *     
     */
    public void setJobPostingType(HRRequisitionPostingType value) {
        this.jobPostingType = value;
    }

    /**
     * Gets the value of the advertisingList property.
     * 
     * @return
     *     possible object is
     *     {@link HRRequisitionAdvertisingList }
     *     
     */
    public HRRequisitionAdvertisingList getAdvertisingList() {
        return advertisingList;
    }

    /**
     * Sets the value of the advertisingList property.
     * 
     * @param value
     *     allowed object is
     *     {@link HRRequisitionAdvertisingList }
     *     
     */
    public void setAdvertisingList(HRRequisitionAdvertisingList value) {
        this.advertisingList = value;
    }

    /**
     * Gets the value of the positionsAvailable property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPositionsAvailable() {
        return positionsAvailable;
    }

    /**
     * Sets the value of the positionsAvailable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPositionsAvailable(Integer value) {
        this.positionsAvailable = value;
    }

    /**
     * Gets the value of the positionsFilled property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPositionsFilled() {
        return positionsFilled;
    }

    /**
     * Sets the value of the positionsFilled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPositionsFilled(Integer value) {
        this.positionsFilled = value;
    }

    /**
     * Gets the value of the applicantsApplied property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApplicantsApplied() {
        return applicantsApplied;
    }

    /**
     * Sets the value of the applicantsApplied property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApplicantsApplied(Integer value) {
        this.applicantsApplied = value;
    }

    /**
     * Gets the value of the applicantsInterviewed property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getApplicantsInterviewed() {
        return applicantsInterviewed;
    }

    /**
     * Sets the value of the applicantsInterviewed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setApplicantsInterviewed(Integer value) {
        this.applicantsInterviewed = value;
    }

    /**
     * Gets the value of the requisitionCosts property.
     * 
     * @return
     *     possible object is
     *     {@link HRRequisitionCosts }
     *     
     */
    public HRRequisitionCosts getRequisitionCosts() {
        return requisitionCosts;
    }

    /**
     * Sets the value of the requisitionCosts property.
     * 
     * @param value
     *     allowed object is
     *     {@link HRRequisitionCosts }
     *     
     */
    public void setRequisitionCosts(HRRequisitionCosts value) {
        this.requisitionCosts = value;
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
