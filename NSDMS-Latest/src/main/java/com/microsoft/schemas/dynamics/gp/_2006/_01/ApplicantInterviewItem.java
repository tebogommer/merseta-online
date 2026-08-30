
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicantInterviewItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicantInterviewItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CategoryName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CategoryWeight" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Score" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SequenceKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SequenceKey" minOccurs="0"/&gt;
 *         &lt;element name="CategoryCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicantInterviewItem", propOrder = {
    "categoryName",
    "categoryWeight",
    "score",
    "notes",
    "sequenceKey",
    "categoryCode"
})
public class ApplicantInterviewItem {

    @XmlElement(name = "CategoryName")
    protected String categoryName;
    @XmlElement(name = "CategoryWeight")
    protected int categoryWeight;
    @XmlElement(name = "Score")
    protected int score;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "SequenceKey")
    protected SequenceKey sequenceKey;
    @XmlElement(name = "CategoryCode")
    protected int categoryCode;

    /**
     * Gets the value of the categoryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the value of the categoryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoryName(String value) {
        this.categoryName = value;
    }

    /**
     * Gets the value of the categoryWeight property.
     * 
     */
    public int getCategoryWeight() {
        return categoryWeight;
    }

    /**
     * Sets the value of the categoryWeight property.
     * 
     */
    public void setCategoryWeight(int value) {
        this.categoryWeight = value;
    }

    /**
     * Gets the value of the score property.
     * 
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     */
    public void setScore(int value) {
        this.score = value;
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
     * Gets the value of the categoryCode property.
     * 
     */
    public int getCategoryCode() {
        return categoryCode;
    }

    /**
     * Sets the value of the categoryCode property.
     * 
     */
    public void setCategoryCode(int value) {
        this.categoryCode = value;
    }

}
