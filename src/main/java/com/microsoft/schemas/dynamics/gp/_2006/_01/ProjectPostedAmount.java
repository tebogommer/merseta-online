
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectPostedAmount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectPostedAmount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectAmount"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TaxPaidAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectPostedAmount", propOrder = {
    "taxPaidAmount"
})
public class ProjectPostedAmount
    extends ProjectAmount
{

    @XmlElement(name = "TaxPaidAmount")
    protected MoneyAmount taxPaidAmount;

    /**
     * Gets the value of the taxPaidAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxPaidAmount() {
        return taxPaidAmount;
    }

    /**
     * Sets the value of the taxPaidAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxPaidAmount(MoneyAmount value) {
        this.taxPaidAmount = value;
    }

}
