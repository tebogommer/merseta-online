
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemVendorSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemVendorSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemVendorKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorItemNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VendorItemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AverageLeadTime" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="MinimumOrderQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="MaximumOrderQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemVendorSummary", propOrder = {
    "key",
    "vendorItemNumber",
    "vendorItemDescription",
    "averageLeadTime",
    "minimumOrderQuantity",
    "maximumOrderQuantity"
})
public class ItemVendorSummary {

    @XmlElement(name = "Key")
    protected ItemVendorKey key;
    @XmlElement(name = "VendorItemNumber")
    protected String vendorItemNumber;
    @XmlElement(name = "VendorItemDescription")
    protected String vendorItemDescription;
    @XmlElement(name = "AverageLeadTime", required = true, nillable = true)
    protected BigDecimal averageLeadTime;
    @XmlElement(name = "MinimumOrderQuantity")
    protected Quantity minimumOrderQuantity;
    @XmlElement(name = "MaximumOrderQuantity")
    protected Quantity maximumOrderQuantity;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ItemVendorKey }
     *     
     */
    public ItemVendorKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemVendorKey }
     *     
     */
    public void setKey(ItemVendorKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the vendorItemNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorItemNumber() {
        return vendorItemNumber;
    }

    /**
     * Sets the value of the vendorItemNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorItemNumber(String value) {
        this.vendorItemNumber = value;
    }

    /**
     * Gets the value of the vendorItemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorItemDescription() {
        return vendorItemDescription;
    }

    /**
     * Sets the value of the vendorItemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorItemDescription(String value) {
        this.vendorItemDescription = value;
    }

    /**
     * Gets the value of the averageLeadTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAverageLeadTime() {
        return averageLeadTime;
    }

    /**
     * Sets the value of the averageLeadTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAverageLeadTime(BigDecimal value) {
        this.averageLeadTime = value;
    }

    /**
     * Gets the value of the minimumOrderQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getMinimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    /**
     * Sets the value of the minimumOrderQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setMinimumOrderQuantity(Quantity value) {
        this.minimumOrderQuantity = value;
    }

    /**
     * Gets the value of the maximumOrderQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getMaximumOrderQuantity() {
        return maximumOrderQuantity;
    }

    /**
     * Sets the value of the maximumOrderQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setMaximumOrderQuantity(Quantity value) {
        this.maximumOrderQuantity = value;
    }

}
