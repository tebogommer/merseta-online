
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Warehouse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Warehouse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Address" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}InternationalAddress" minOccurs="0"/&gt;
 *         &lt;element name="SalesTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="IncludeInPlanning" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Bins" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfWarehouseBin" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Warehouse", propOrder = {
    "key",
    "description",
    "address",
    "salesTaxScheduleKey",
    "purchaseTaxScheduleKey",
    "includeInPlanning",
    "bins"
})
public class Warehouse
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected WarehouseKey key;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Address")
    protected InternationalAddress address;
    @XmlElement(name = "SalesTaxScheduleKey")
    protected TaxScheduleKey salesTaxScheduleKey;
    @XmlElement(name = "PurchaseTaxScheduleKey")
    protected TaxScheduleKey purchaseTaxScheduleKey;
    @XmlElement(name = "IncludeInPlanning", required = true, type = Boolean.class, nillable = true)
    protected Boolean includeInPlanning;
    @XmlElement(name = "Bins")
    protected ArrayOfWarehouseBin bins;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setKey(WarehouseKey value) {
        this.key = value;
    }

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
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalAddress }
     *     
     */
    public InternationalAddress getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalAddress }
     *     
     */
    public void setAddress(InternationalAddress value) {
        this.address = value;
    }

    /**
     * Gets the value of the salesTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getSalesTaxScheduleKey() {
        return salesTaxScheduleKey;
    }

    /**
     * Sets the value of the salesTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setSalesTaxScheduleKey(TaxScheduleKey value) {
        this.salesTaxScheduleKey = value;
    }

    /**
     * Gets the value of the purchaseTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getPurchaseTaxScheduleKey() {
        return purchaseTaxScheduleKey;
    }

    /**
     * Sets the value of the purchaseTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setPurchaseTaxScheduleKey(TaxScheduleKey value) {
        this.purchaseTaxScheduleKey = value;
    }

    /**
     * Gets the value of the includeInPlanning property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeInPlanning() {
        return includeInPlanning;
    }

    /**
     * Sets the value of the includeInPlanning property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeInPlanning(Boolean value) {
        this.includeInPlanning = value;
    }

    /**
     * Gets the value of the bins property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfWarehouseBin }
     *     
     */
    public ArrayOfWarehouseBin getBins() {
        return bins;
    }

    /**
     * Sets the value of the bins property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWarehouseBin }
     *     
     */
    public void setBins(ArrayOfWarehouseBin value) {
        this.bins = value;
    }

}
