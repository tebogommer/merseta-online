
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SalesOrderLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesOrderLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesLine"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="QuantityToBackorder" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityToInvoice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityCanceled" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityFulfilled" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantityAllocated" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="FulfillDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ActualShipDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Components" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesOrderComponent" minOccurs="0"/&gt;
 *         &lt;element name="Serials" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesLineSerial" minOccurs="0"/&gt;
 *         &lt;element name="Lots" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesLineLot" minOccurs="0"/&gt;
 *         &lt;element name="Bins" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesLineBin" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesOrderLine", propOrder = {
    "quantityToBackorder",
    "quantityToInvoice",
    "quantityCanceled",
    "quantityFulfilled",
    "quantityAllocated",
    "fulfillDate",
    "actualShipDate",
    "components",
    "serials",
    "lots",
    "bins"
})
public class SalesOrderLine
    extends SalesLine
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
    @XmlElement(name = "FulfillDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fulfillDate;
    @XmlElement(name = "ActualShipDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar actualShipDate;
    @XmlElement(name = "Components")
    protected ArrayOfSalesOrderComponent components;
    @XmlElement(name = "Serials")
    protected ArrayOfSalesLineSerial serials;
    @XmlElement(name = "Lots")
    protected ArrayOfSalesLineLot lots;
    @XmlElement(name = "Bins")
    protected ArrayOfSalesLineBin bins;

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
     * Gets the value of the fulfillDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFulfillDate() {
        return fulfillDate;
    }

    /**
     * Sets the value of the fulfillDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFulfillDate(XMLGregorianCalendar value) {
        this.fulfillDate = value;
    }

    /**
     * Gets the value of the actualShipDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActualShipDate() {
        return actualShipDate;
    }

    /**
     * Sets the value of the actualShipDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActualShipDate(XMLGregorianCalendar value) {
        this.actualShipDate = value;
    }

    /**
     * Gets the value of the components property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesOrderComponent }
     *     
     */
    public ArrayOfSalesOrderComponent getComponents() {
        return components;
    }

    /**
     * Sets the value of the components property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesOrderComponent }
     *     
     */
    public void setComponents(ArrayOfSalesOrderComponent value) {
        this.components = value;
    }

    /**
     * Gets the value of the serials property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesLineSerial }
     *     
     */
    public ArrayOfSalesLineSerial getSerials() {
        return serials;
    }

    /**
     * Sets the value of the serials property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesLineSerial }
     *     
     */
    public void setSerials(ArrayOfSalesLineSerial value) {
        this.serials = value;
    }

    /**
     * Gets the value of the lots property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesLineLot }
     *     
     */
    public ArrayOfSalesLineLot getLots() {
        return lots;
    }

    /**
     * Sets the value of the lots property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesLineLot }
     *     
     */
    public void setLots(ArrayOfSalesLineLot value) {
        this.lots = value;
    }

    /**
     * Gets the value of the bins property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesLineBin }
     *     
     */
    public ArrayOfSalesLineBin getBins() {
        return bins;
    }

    /**
     * Sets the value of the bins property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesLineBin }
     *     
     */
    public void setBins(ArrayOfSalesLineBin value) {
        this.bins = value;
    }

}
