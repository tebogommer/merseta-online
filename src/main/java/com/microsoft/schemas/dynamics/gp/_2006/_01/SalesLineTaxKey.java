
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesLineTaxKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesLineTaxKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SalesLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesLineKey" minOccurs="0"/&gt;
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
@XmlType(name = "SalesLineTaxKey", propOrder = {
    "salesLineKey",
    "taxDetailKey"
})
public class SalesLineTaxKey
    extends TransactionKey
{

    @XmlElement(name = "SalesLineKey")
    protected SalesLineKey salesLineKey;
    @XmlElement(name = "TaxDetailKey")
    protected TaxDetailKey taxDetailKey;

    /**
     * Gets the value of the salesLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesLineKey }
     *     
     */
    public SalesLineKey getSalesLineKey() {
        return salesLineKey;
    }

    /**
     * Sets the value of the salesLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesLineKey }
     *     
     */
    public void setSalesLineKey(SalesLineKey value) {
        this.salesLineKey = value;
    }

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
