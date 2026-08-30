
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ManufacturingOrderDocumentSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManufacturingOrderDocumentSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObjectSummary"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ManufacturingOrderDocumentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="OrderStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderStatus"/&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManufacturingOrderDocumentSummary", propOrder = {
    "description",
    "manufacturingOrderDocumentKey",
    "orderStatus",
    "itemKey",
    "startDate"
})
@XmlSeeAlso({
    ManufacturingOrderSummary.class,
    VendorManufacturingOrderSummary.class
})
public class ManufacturingOrderDocumentSummary
    extends BusinessObjectSummary
{

    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "ManufacturingOrderDocumentKey")
    protected ManufacturingOrderDocumentKey manufacturingOrderDocumentKey;
    @XmlElement(name = "OrderStatus", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ManufacturingOrderStatus orderStatus;
    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "StartDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

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
     * Gets the value of the orderStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturingOrderStatus }
     *     
     */
    public ManufacturingOrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the value of the orderStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturingOrderStatus }
     *     
     */
    public void setOrderStatus(ManufacturingOrderStatus value) {
        this.orderStatus = value;
    }

    /**
     * Gets the value of the itemKey property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getItemKey() {
        return itemKey;
    }

    /**
     * Sets the value of the itemKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setItemKey(ItemKey value) {
        this.itemKey = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

}
