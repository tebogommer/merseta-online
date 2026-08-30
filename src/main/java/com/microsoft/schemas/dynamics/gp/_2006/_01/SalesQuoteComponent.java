
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesQuoteComponent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesQuoteComponent"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesComponent"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="QuantityCanceled" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityToInvoice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityToOrder" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesQuoteComponent", propOrder = {
    "quantityCanceled",
    "quantityToInvoice",
    "quantityToOrder"
})
public class SalesQuoteComponent
    extends SalesComponent
{

    @XmlElement(name = "QuantityCanceled")
    protected Quantity quantityCanceled;
    @XmlElement(name = "QuantityToInvoice")
    protected Quantity quantityToInvoice;
    @XmlElement(name = "QuantityToOrder")
    protected Quantity quantityToOrder;

    /**
     * Gets the value of the quantityCanceled property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityCanceled() {
        return quantityCanceled;
    }

    /**
     * Sets the value of the quantityCanceled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityCanceled(Quantity value) {
        this.quantityCanceled = value;
    }

    /**
     * Gets the value of the quantityToInvoice property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityToInvoice() {
        return quantityToInvoice;
    }

    /**
     * Sets the value of the quantityToInvoice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityToInvoice(Quantity value) {
        this.quantityToInvoice = value;
    }

    /**
     * Gets the value of the quantityToOrder property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityToOrder() {
        return quantityToOrder;
    }

    /**
     * Sets the value of the quantityToOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityToOrder(Quantity value) {
        this.quantityToOrder = value;
    }

}
