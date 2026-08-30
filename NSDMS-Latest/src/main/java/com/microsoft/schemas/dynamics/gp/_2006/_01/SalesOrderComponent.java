
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesOrderComponent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesOrderComponent"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesComponent"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="QuantityToBackorder" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityToInvoice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityCanceled" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityFulfilled" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityAllocated" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="Serials" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesComponentSerial" minOccurs="0"/&gt;
 *         &lt;element name="Lots" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesComponentLot" minOccurs="0"/&gt;
 *         &lt;element name="Bins" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesComponentBin" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesOrderComponent", propOrder = {
    "quantityToBackorder",
    "quantityToInvoice",
    "quantityCanceled",
    "quantityFulfilled",
    "quantityAllocated",
    "serials",
    "lots",
    "bins"
})
public class SalesOrderComponent
    extends SalesComponent
{

    @XmlElement(name = "QuantityToBackorder")
    protected Quantity quantityToBackorder;
    @XmlElement(name = "QuantityToInvoice")
    protected Quantity quantityToInvoice;
    @XmlElement(name = "QuantityCanceled")
    protected Quantity quantityCanceled;
    @XmlElement(name = "QuantityFulfilled")
    protected Quantity quantityFulfilled;
    @XmlElement(name = "QuantityAllocated")
    protected Quantity quantityAllocated;
    @XmlElement(name = "Serials")
    protected ArrayOfSalesComponentSerial serials;
    @XmlElement(name = "Lots")
    protected ArrayOfSalesComponentLot lots;
    @XmlElement(name = "Bins")
    protected ArrayOfSalesComponentBin bins;

    /**
     * Gets the value of the quantityToBackorder property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityToBackorder() {
        return quantityToBackorder;
    }

    /**
     * Sets the value of the quantityToBackorder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityToBackorder(Quantity value) {
        this.quantityToBackorder = value;
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
     * Gets the value of the quantityFulfilled property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityFulfilled() {
        return quantityFulfilled;
    }

    /**
     * Sets the value of the quantityFulfilled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityFulfilled(Quantity value) {
        this.quantityFulfilled = value;
    }

    /**
     * Gets the value of the quantityAllocated property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityAllocated() {
        return quantityAllocated;
    }

    /**
     * Sets the value of the quantityAllocated property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityAllocated(Quantity value) {
        this.quantityAllocated = value;
    }

    /**
     * Gets the value of the serials property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesComponentSerial }
     *     
     */
    public ArrayOfSalesComponentSerial getSerials() {
        return serials;
    }

    /**
     * Sets the value of the serials property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesComponentSerial }
     *     
     */
    public void setSerials(ArrayOfSalesComponentSerial value) {
        this.serials = value;
    }

    /**
     * Gets the value of the lots property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesComponentLot }
     *     
     */
    public ArrayOfSalesComponentLot getLots() {
        return lots;
    }

    /**
     * Sets the value of the lots property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesComponentLot }
     *     
     */
    public void setLots(ArrayOfSalesComponentLot value) {
        this.lots = value;
    }

    /**
     * Gets the value of the bins property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesComponentBin }
     *     
     */
    public ArrayOfSalesComponentBin getBins() {
        return bins;
    }

    /**
     * Sets the value of the bins property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesComponentBin }
     *     
     */
    public void setBins(ArrayOfSalesComponentBin value) {
        this.bins = value;
    }

}
