
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GetApplicantReferenceByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantReference" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getApplicantReferenceByKeyResult"
})
@XmlRootElement(name = "GetApplicantReferenceByKeyResponse")
public class GetApplicantReferenceByKeyResponse {

    @XmlElement(name = "GetApplicantReferenceByKeyResult")
    protected ApplicantReference getApplicantReferenceByKeyResult;

    /**
     * Gets the value of the getApplicantReferenceByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantReference }
     *     
     */
    public ApplicantReference getGetApplicantReferenceByKeyResult() {
        return getApplicantReferenceByKeyResult;
    }

    /**
     * Sets the value of the getApplicantReferenceByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantReference }
     *     
     */
    public void setGetApplicantReferenceByKeyResult(ApplicantReference value) {
        this.getApplicantReferenceByKeyResult = value;
    }

}
