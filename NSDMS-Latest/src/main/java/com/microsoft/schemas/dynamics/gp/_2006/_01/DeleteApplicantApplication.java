
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.Context;


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
 *         &lt;element name="businessObjectKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ApplicantSequenceKey" minOccurs="0"/&gt;
 *         &lt;element name="context" type="{http://schemas.microsoft.com/dynamics/2006/01}Context" minOccurs="0"/&gt;
 *         &lt;element name="policy" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Policy" minOccurs="0"/&gt;
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
    "businessObjectKey",
    "context",
    "policy"
})
@XmlRootElement(name = "DeleteApplicantApplication")
public class DeleteApplicantApplication {

    protected ApplicantSequenceKey businessObjectKey;
    protected Context context;
    protected Policy policy;

    /**
     * Gets the value of the businessObjectKey property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicantSequenceKey }
     *     
     */
    public ApplicantSequenceKey getBusinessObjectKey() {
        return businessObjectKey;
    }

    /**
     * Sets the value of the businessObjectKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicantSequenceKey }
     *     
     */
    public void setBusinessObjectKey(ApplicantSequenceKey value) {
        this.businessObjectKey = value;
    }

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link Context }
     *     
     */
    public Context getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link Context }
     *     
     */
    public void setContext(Context value) {
        this.context = value;
    }

    /**
     * Gets the value of the policy property.
     * 
     * @return
     *     possible object is
     *     {@link Policy }
     *     
     */
    public Policy getPolicy() {
        return policy;
    }

    /**
     * Sets the value of the policy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Policy }
     *     
     */
    public void setPolicy(Policy value) {
        this.policy = value;
    }

}
