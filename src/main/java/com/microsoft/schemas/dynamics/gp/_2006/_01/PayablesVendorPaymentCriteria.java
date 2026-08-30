
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayablesVendorPaymentCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayablesVendorPaymentCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesDocumentCriteriaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Scope" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesVendorPaymentScope"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayablesVendorPaymentCriteria", propOrder = {
    "scope"
})
@XmlSeeAlso({
    PostedPayablesVendorPaymentCriteria.class
})
public class PayablesVendorPaymentCriteria
    extends PayablesDocumentCriteriaBase
{

    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected PayablesVendorPaymentScope scope;

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesVendorPaymentScope }
     *     
     */
    public PayablesVendorPaymentScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesVendorPaymentScope }
     *     
     */
    public void setScope(PayablesVendorPaymentScope value) {
        this.scope = value;
    }

}
