
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChangedTaxDetailKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChangedTaxDetailKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ChangedBusinessObjectKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TaxDetailKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxDetailKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangedTaxDetailKey", propOrder = {
    "taxDetailKey"
})
public class ChangedTaxDetailKey
    extends ChangedBusinessObjectKey
{

    @XmlElement(name = "TaxDetailKey")
    protected TaxDetailKey taxDetailKey;

    /**
     * Gets the value of the taxDetailKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxDetailKey }
     *     
     */
    public TaxDetailKey getTaxDetailKey() {
        return taxDetailKey;
    }

    /**
     * Sets the value of the taxDetailKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxDetailKey }
     *     
     */
    public void setTaxDetailKey(TaxDetailKey value) {
        this.taxDetailKey = value;
    }

}
