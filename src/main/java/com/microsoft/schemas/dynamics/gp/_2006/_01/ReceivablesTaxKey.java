
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceivablesTaxKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceivablesTaxKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}TransactionKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ReceivablesDocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesDocumentKey" minOccurs="0"/&gt;
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
@XmlType(name = "ReceivablesTaxKey", propOrder = {
    "receivablesDocumentKey",
    "taxDetailKey"
})
public class ReceivablesTaxKey
    extends TransactionKey
{

    @XmlElement(name = "ReceivablesDocumentKey")
    protected ReceivablesDocumentKey receivablesDocumentKey;
    @XmlElement(name = "TaxDetailKey")
    protected TaxDetailKey taxDetailKey;

    /**
     * Gets the value of the receivablesDocumentKey property.
     * 
     * @return
     *     possible object is
     *     {@link ReceivablesDocumentKey }
     *     
     */
    public ReceivablesDocumentKey getReceivablesDocumentKey() {
        return receivablesDocumentKey;
    }

    /**
     * Sets the value of the receivablesDocumentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivablesDocumentKey }
     *     
     */
    public void setReceivablesDocumentKey(ReceivablesDocumentKey value) {
        this.receivablesDocumentKey = value;
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
