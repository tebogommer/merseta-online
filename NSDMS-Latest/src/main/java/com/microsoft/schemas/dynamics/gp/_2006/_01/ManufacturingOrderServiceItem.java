
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ManufacturingOrderServiceItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManufacturingOrderServiceItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ManufacturingOrderDocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="RouteSequenceNum" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ServiceItem" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="QtyToOrder" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="SuggestedQty" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="RequiredDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="DateReleased" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManufacturingOrderServiceItem", propOrder = {
    "manufacturingOrderDocumentKey",
    "routeSequenceNum",
    "serviceItem",
    "qtyToOrder",
    "suggestedQty",
    "requiredDate",
    "dateReleased"
})
public class ManufacturingOrderServiceItem {

    @XmlElement(name = "ManufacturingOrderDocumentKey")
    protected ManufacturingOrderDocumentKey manufacturingOrderDocumentKey;
    @XmlElement(name = "RouteSequenceNum")
    protected int routeSequenceNum;
    @XmlElement(name = "ServiceItem")
    protected ItemKey serviceItem;
    @XmlElement(name = "QtyToOrder")
    protected Quantity qtyToOrder;
    @XmlElement(name = "SuggestedQty")
    protected Quantity suggestedQty;
    @XmlElement(name = "RequiredDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requiredDate;
    @XmlElement(name = "DateReleased", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateReleased;

    /**
     * Gets the value of the manufacturingOrderDocumentKey property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturingOrderDocumentKey }
     *     
     */
    public ManufacturingOrderDocumentKey getManufacturingOrderDocumentKey() {
        return manufacturingOrderDocumentKey;
    }

    /**
     * Sets the value of the manufacturingOrderDocumentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturingOrderDocumentKey }
     *     
     */
    public void setManufacturingOrderDocumentKey(ManufacturingOrderDocumentKey value) {
        this.manufacturingOrderDocumentKey = value;
    }

    /**
     * Gets the value of the routeSequenceNum property.
     * 
     */
    public int getRouteSequenceNum() {
        return routeSequenceNum;
    }

    /**
     * Sets the value of the routeSequenceNum property.
     * 
     */
    public void setRouteSequenceNum(int value) {
        this.routeSequenceNum = value;
    }

    /**
     * Gets the value of the serviceItem property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getServiceItem() {
        return serviceItem;
    }

    /**
     * Sets the value of the serviceItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setServiceItem(ItemKey value) {
        this.serviceItem = value;
    }

    /**
     * Gets the value of the qtyToOrder property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQtyToOrder() {
        return qtyToOrder;
    }

    /**
     * Sets the value of the qtyToOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQtyToOrder(Quantity value) {
        this.qtyToOrder = value;
    }

    /**
     * Gets the value of the suggestedQty property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getSuggestedQty() {
        return suggestedQty;
    }

    /**
     * Sets the value of the suggestedQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setSuggestedQty(Quantity value) {
        this.suggestedQty = value;
    }

    /**
     * Gets the value of the requiredDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequiredDate() {
        return requiredDate;
    }

    /**
     * Sets the value of the requiredDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequiredDate(XMLGregorianCalendar value) {
        this.requiredDate = value;
    }

    /**
     * Gets the value of the dateReleased property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateReleased() {
        return dateReleased;
    }

    /**
     * Sets the value of the dateReleased property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateReleased(XMLGregorianCalendar value) {
        this.dateReleased = value;
    }

}
