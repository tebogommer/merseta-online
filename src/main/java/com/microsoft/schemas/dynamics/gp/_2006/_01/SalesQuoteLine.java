
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesQuoteLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesQuoteLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesLine"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="QuantityCanceled" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityToInvoice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityToOrder" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="Components" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesQuoteComponent" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesQuoteLine", propOrder = {
    "quantityCanceled",
    "quantityToInvoice",
    "quantityToOrder",
    "components"
})
public class SalesQuoteLine
    extends SalesLine
{

    @XmlElement(name = "QuantityCanceled")
    protected Quantity quantityCanceled;
    @XmlElement(name = "QuantityToInvoice")
    protected Quantity quantityToInvoice;
    @XmlElement(name = "QuantityToOrder")
    protected Quantity quantityToOrder;
    @XmlElement(name = "Components")
    protected ArrayOfSalesQuoteComponent components;

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

    /**
     * Gets the value of the components property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesQuoteComponent }
     *     
     */
    public ArrayOfSalesQuoteComponent getComponents() {
        return components;
    }

    /**
     * Sets the value of the components property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesQuoteComponent }
     *     
     */
    public void setComponents(ArrayOfSalesQuoteComponent value) {
        this.components = value;
    }

}
