
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfApplicantApplicationSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfApplicantApplicationSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ApplicantApplicationSummary" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantApplicationSummary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfApplicantApplicationSummary", propOrder = {
    "applicantApplicationSummary"
})
public class ArrayOfApplicantApplicationSummary {

    @XmlElement(name = "ApplicantApplicationSummary", nillable = true)
    protected List<ApplicantApplicationSummary> applicantApplicationSummary;

    /**
     * Gets the value of the applicantApplicationSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applicantApplicationSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplicantApplicationSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ApplicantApplicationSummary }
     * 
     * 
     */
    public List<ApplicantApplicationSummary> getApplicantApplicationSummary() {
        if (applicantApplicationSummary == null) {
            applicantApplicationSummary = new ArrayList<ApplicantApplicationSummary>();
        }
        return this.applicantApplicationSummary;
    }

}
