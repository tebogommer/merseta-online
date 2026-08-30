
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ApplicantApplication complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantApplication"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CompanyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ApplicantApplicationKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantSequenceKey" minOccurs="0"/&gt;
 *         &lt;element name="RequisitionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}HRRequisitionNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="DivisionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DivisionKey" minOccurs="0"/&gt;
 *         &lt;element name="DepartmentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DepartmentKey" minOccurs="0"/&gt;
 *         &lt;element name="PositionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PositionKey" minOccurs="0"/&gt;
 *         &lt;element name="DateApplied" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicationStatus"/&gt;
 *         &lt;element name="Location" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsWillRelocate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsReplyLetterSent" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="LastModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="RejectionInfomation" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicationRejectionInformation" minOccurs="0"/&gt;
 *         &lt;element name="ReferenceInformation" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicationReferenceInformation" minOccurs="0"/&gt;
 *         &lt;element name="Color" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicationColor" minOccurs="0"/&gt;
 *         &lt;element name="DeleteOnUpdate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantApplication", propOrder = {
    "companyCode",
    "applicantApplicationKey",
    "requisitionKey",
    "divisionKey",
    "departmentKey",
    "positionKey",
    "dateApplied",
    "status",
    "location",
    "isWillRelocate",
    "isReplyLetterSent",
    "lastModifiedBy",
    "lastModifiedDate",
    "rejectionInfomation",
    "referenceInformation",
    "color",
    "deleteOnUpdate"
})
public class ApplicantApplication
    extends BusinessObject
{

    @XmlElement(name = "CompanyCode")
    protected String companyCode;
    @XmlElement(name = "ApplicantApplicationKey")
    protected ApplicantSequenceKey applicantApplicationKey;
    @XmlElement(name = "RequisitionKey")
    protected HRRequisitionNumberKey requisitionKey;
    @XmlElement(name = "DivisionKey")
    protected DivisionKey divisionKey;
    @XmlElement(name = "DepartmentKey")
    protected DepartmentKey departmentKey;
    @XmlElement(name = "PositionKey")
    protected PositionKey positionKey;
    @XmlElement(name = "DateApplied", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateApplied;
    @XmlElement(name = "Status", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ApplicationStatus status;
    @XmlElement(name = "Location")
    protected String location;
    @XmlElement(name = "IsWillRelocate", required = true, type = Boolean.class, nillable = true)
    protected Boolean isWillRelocate;
    @XmlElement(name = "IsReplyLetterSent", required = true, type = Boolean.class, nillable = true)
    protected Boolean isReplyLetterSent;
    @XmlElement(name = "LastModifiedBy")
    protected String lastModifiedBy;
    @XmlElement(name = "LastModifiedDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "RejectionInfomation")
    protected ApplicationRejectionInformation rejectionInfomation;
    @XmlElement(name = "ReferenceInformation")
    protected ApplicationReferenceInformation referenceInformation;
    @XmlElement(name = "Color")
    protected ApplicationColor color;
    @XmlElement(name = "DeleteOnUpdate", required = true, type = Boolean.class, nillable = true)
    protected Boolean deleteOnUpdate;

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
     * Gets the value of the applicantApplicationKey property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantSequenceKey }
     *     
     */
    public ApplicantSequenceKey getApplicantApplicationKey() {
        return applicantApplicationKey;
    }

    /**
     * Sets the value of the applicantApplicationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantSequenceKey }
     *     
     */
    public void setApplicantApplicationKey(ApplicantSequenceKey value) {
        this.applicantApplicationKey = value;
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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationStatus }
     *     
     */
    public ApplicationStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationStatus }
     *     
     */
    public void setStatus(ApplicationStatus value) {
        this.status = value;
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

    /**
     * Gets the value of the rejectionInfomation property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationRejectionInformation }
     *     
     */
    public ApplicationRejectionInformation getRejectionInfomation() {
        return rejectionInfomation;
    }

    /**
     * Sets the value of the rejectionInfomation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationRejectionInformation }
     *     
     */
    public void setRejectionInfomation(ApplicationRejectionInformation value) {
        this.rejectionInfomation = value;
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
     * Gets the value of the color property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationColor }
     *     
     */
    public ApplicationColor getColor() {
        return color;
    }

    /**
     * Sets the value of the color property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationColor }
     *     
     */
    public void setColor(ApplicationColor value) {
        this.color = value;
    }

    /**
     * Gets the value of the deleteOnUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeleteOnUpdate() {
        return deleteOnUpdate;
    }

    /**
     * Sets the value of the deleteOnUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeleteOnUpdate(Boolean value) {
        this.deleteOnUpdate = value;
    }

}
