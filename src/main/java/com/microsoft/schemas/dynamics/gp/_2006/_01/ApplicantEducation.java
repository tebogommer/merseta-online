
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ApplicantEducation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantEducation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicantEducationKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantSequenceKey" minOccurs="0"/&gt;
 *         &lt;element name="School" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Degree" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Major" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="GradePointAverage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="YearGraduated" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="GPABase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
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
@XmlType(name = "ApplicantEducation", propOrder = {
    "applicantEducationKey",
    "school",
    "degree",
    "major",
    "gradePointAverage",
    "yearGraduated",
    "gpaBase",
    "notes",
    "lastModifiedBy",
    "lastModifiedDate",
    "deleteOnUpdate"
})
public class ApplicantEducation
    extends BusinessObject
{

    @XmlElement(name = "ApplicantEducationKey")
    protected ApplicantSequenceKey applicantEducationKey;
    @XmlElement(name = "School")
    protected String school;
    @XmlElement(name = "Degree")
    protected String degree;
    @XmlElement(name = "Major")
    protected String major;
    @XmlElement(name = "GradePointAverage")
    protected String gradePointAverage;
    @XmlElement(name = "YearGraduated")
    protected String yearGraduated;
    @XmlElement(name = "GPABase")
    protected String gpaBase;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "LastModifiedBy")
    protected String lastModifiedBy;
    @XmlElement(name = "LastModifiedDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "DeleteOnUpdate", required = true, type = Boolean.class, nillable = true)
    protected Boolean deleteOnUpdate;

    /**
     * Gets the value of the applicantEducationKey property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantSequenceKey }
     *     
     */
    public ApplicantSequenceKey getApplicantEducationKey() {
        return applicantEducationKey;
    }

    /**
     * Sets the value of the applicantEducationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantSequenceKey }
     *     
     */
    public void setApplicantEducationKey(ApplicantSequenceKey value) {
        this.applicantEducationKey = value;
    }

    /**
     * Gets the value of the school property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchool() {
        return school;
    }

    /**
     * Sets the value of the school property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchool(String value) {
        this.school = value;
    }

    /**
     * Gets the value of the degree property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDegree() {
        return degree;
    }

    /**
     * Sets the value of the degree property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDegree(String value) {
        this.degree = value;
    }

    /**
     * Gets the value of the major property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMajor() {
        return major;
    }

    /**
     * Sets the value of the major property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMajor(String value) {
        this.major = value;
    }

    /**
     * Gets the value of the gradePointAverage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGradePointAverage() {
        return gradePointAverage;
    }

    /**
     * Sets the value of the gradePointAverage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGradePointAverage(String value) {
        this.gradePointAverage = value;
    }

    /**
     * Gets the value of the yearGraduated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYearGraduated() {
        return yearGraduated;
    }

    /**
     * Sets the value of the yearGraduated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYearGraduated(String value) {
        this.yearGraduated = value;
    }

    /**
     * Gets the value of the gpaBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGPABase() {
        return gpaBase;
    }

    /**
     * Sets the value of the gpaBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGPABase(String value) {
        this.gpaBase = value;
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
