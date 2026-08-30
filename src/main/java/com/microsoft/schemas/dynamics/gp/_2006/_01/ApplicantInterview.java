
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ApplicantInterview complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantInterview"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CompanyCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InterviewItems" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfApplicantInterviewItem" minOccurs="0"/&gt;
 *         &lt;element name="ApplicantInterviewKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantInterviewTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DepartmentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DepartmentKey" minOccurs="0"/&gt;
 *         &lt;element name="DivisionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DivisionKey" minOccurs="0"/&gt;
 *         &lt;element name="PositionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PositionKey" minOccurs="0"/&gt;
 *         &lt;element name="ReviewRating" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ReviewRange" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="WeightedScore" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="TotalWeight" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InterviewTypeCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
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
@XmlType(name = "ApplicantInterview", propOrder = {
    "companyCode",
    "interviewItems",
    "applicantInterviewKey",
    "effectiveDate",
    "departmentKey",
    "divisionKey",
    "positionKey",
    "reviewRating",
    "reviewRange",
    "weightedScore",
    "totalWeight",
    "notes",
    "interviewTypeCode",
    "deleteOnUpdate"
})
public class ApplicantInterview
    extends BusinessObject
{

    @XmlElement(name = "CompanyCode")
    protected String companyCode;
    @XmlElement(name = "InterviewItems")
    protected ArrayOfApplicantInterviewItem interviewItems;
    @XmlElement(name = "ApplicantInterviewKey")
    protected ApplicantInterviewTypeKey applicantInterviewKey;
    @XmlElement(name = "EffectiveDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar effectiveDate;
    @XmlElement(name = "DepartmentKey")
    protected DepartmentKey departmentKey;
    @XmlElement(name = "DivisionKey")
    protected DivisionKey divisionKey;
    @XmlElement(name = "PositionKey")
    protected PositionKey positionKey;
    @XmlElement(name = "ReviewRating", required = true)
    protected BigDecimal reviewRating;
    @XmlElement(name = "ReviewRange")
    protected int reviewRange;
    @XmlElement(name = "WeightedScore")
    protected int weightedScore;
    @XmlElement(name = "TotalWeight")
    protected int totalWeight;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "InterviewTypeCode", required = true, type = Integer.class, nillable = true)
    protected Integer interviewTypeCode;
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
     * Gets the value of the interviewItems property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfApplicantInterviewItem }
     *     
     */
    public ArrayOfApplicantInterviewItem getInterviewItems() {
        return interviewItems;
    }

    /**
     * Sets the value of the interviewItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfApplicantInterviewItem }
     *     
     */
    public void setInterviewItems(ArrayOfApplicantInterviewItem value) {
        this.interviewItems = value;
    }

    /**
     * Gets the value of the applicantInterviewKey property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantInterviewTypeKey }
     *     
     */
    public ApplicantInterviewTypeKey getApplicantInterviewKey() {
        return applicantInterviewKey;
    }

    /**
     * Sets the value of the applicantInterviewKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantInterviewTypeKey }
     *     
     */
    public void setApplicantInterviewKey(ApplicantInterviewTypeKey value) {
        this.applicantInterviewKey = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
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
     * Gets the value of the reviewRating property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReviewRating() {
        return reviewRating;
    }

    /**
     * Sets the value of the reviewRating property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReviewRating(BigDecimal value) {
        this.reviewRating = value;
    }

    /**
     * Gets the value of the reviewRange property.
     * 
     */
    public int getReviewRange() {
        return reviewRange;
    }

    /**
     * Sets the value of the reviewRange property.
     * 
     */
    public void setReviewRange(int value) {
        this.reviewRange = value;
    }

    /**
     * Gets the value of the weightedScore property.
     * 
     */
    public int getWeightedScore() {
        return weightedScore;
    }

    /**
     * Sets the value of the weightedScore property.
     * 
     */
    public void setWeightedScore(int value) {
        this.weightedScore = value;
    }

    /**
     * Gets the value of the totalWeight property.
     * 
     */
    public int getTotalWeight() {
        return totalWeight;
    }

    /**
     * Sets the value of the totalWeight property.
     * 
     */
    public void setTotalWeight(int value) {
        this.totalWeight = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the interviewTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInterviewTypeCode() {
        return interviewTypeCode;
    }

    /**
     * Sets the value of the interviewTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInterviewTypeCode(Integer value) {
        this.interviewTypeCode = value;
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
