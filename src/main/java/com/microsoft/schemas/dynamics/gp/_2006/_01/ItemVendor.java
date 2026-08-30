
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemVendor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemVendor"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemVendorKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorItemNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RequisitionedQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="AverageLeadTime" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="NumberOfReceipts" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="MinimumOrderQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="MaximumOrderQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="EconomicOrderQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="VendorItemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastOriginatingCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="LastCurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="FreeOnBoard" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}FreeOnBoard"/&gt;
 *         &lt;element name="PurchasingUofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PlanningLeadTime" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="OrderMultipleQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemVendor", propOrder = {
    "key",
    "vendorItemNumber",
    "requisitionedQuantity",
    "averageLeadTime",
    "numberOfReceipts",
    "minimumOrderQuantity",
    "maximumOrderQuantity",
    "economicOrderQuantity",
    "vendorItemDescription",
    "lastOriginatingCost",
    "lastCurrencyKey",
    "freeOnBoard",
    "purchasingUofM",
    "planningLeadTime",
    "orderMultipleQuantity"
})
public class ItemVendor
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ItemVendorKey key;
    @XmlElement(name = "VendorItemNumber")
    protected String vendorItemNumber;
    @XmlElement(name = "RequisitionedQuantity")
    protected Quantity requisitionedQuantity;
    @XmlElement(name = "AverageLeadTime", required = true, nillable = true)
    protected BigDecimal averageLeadTime;
    @XmlElement(name = "NumberOfReceipts", required = true, type = Short.class, nillable = true)
    protected Short numberOfReceipts;
    @XmlElement(name = "MinimumOrderQuantity")
    protected Quantity minimumOrderQuantity;
    @XmlElement(name = "MaximumOrderQuantity")
    protected Quantity maximumOrderQuantity;
    @XmlElement(name = "EconomicOrderQuantity")
    protected Quantity economicOrderQuantity;
    @XmlElement(name = "VendorItemDescription")
    protected String vendorItemDescription;
    @XmlElement(name = "LastOriginatingCost")
    protected MoneyAmount lastOriginatingCost;
    @XmlElement(name = "LastCurrencyKey")
    protected CurrencyKey lastCurrencyKey;
    @XmlElement(name = "FreeOnBoard", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected FreeOnBoard freeOnBoard;
    @XmlElement(name = "PurchasingUofM")
    protected String purchasingUofM;
    @XmlElement(name = "PlanningLeadTime", required = true, type = Short.class, nillable = true)
    protected Short planningLeadTime;
    @XmlElement(name = "OrderMultipleQuantity")
    protected Quantity orderMultipleQuantity;

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
     * Gets the value of the requisitionedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getRequisitionedQuantity() {
        return requisitionedQuantity;
    }

    /**
     * Sets the value of the requisitionedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setRequisitionedQuantity(Quantity value) {
        this.requisitionedQuantity = value;
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
     * Gets the value of the numberOfReceipts property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getNumberOfReceipts() {
        return numberOfReceipts;
    }

    /**
     * Sets the value of the numberOfReceipts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNumberOfReceipts(Short value) {
        this.numberOfReceipts = value;
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

    /**
     * Gets the value of the economicOrderQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getEconomicOrderQuantity() {
        return economicOrderQuantity;
    }

    /**
     * Sets the value of the economicOrderQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setEconomicOrderQuantity(Quantity value) {
        this.economicOrderQuantity = value;
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
     * Gets the value of the lastOriginatingCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getLastOriginatingCost() {
        return lastOriginatingCost;
    }

    /**
     * Sets the value of the lastOriginatingCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setLastOriginatingCost(MoneyAmount value) {
        this.lastOriginatingCost = value;
    }

    /**
     * Gets the value of the lastCurrencyKey property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyKey }
     *     
     */
    public CurrencyKey getLastCurrencyKey() {
        return lastCurrencyKey;
    }

    /**
     * Sets the value of the lastCurrencyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyKey }
     *     
     */
    public void setLastCurrencyKey(CurrencyKey value) {
        this.lastCurrencyKey = value;
    }

    /**
     * Gets the value of the freeOnBoard property.
     * 
     * @return
     *     possible object is
     *     {@link FreeOnBoard }
     *     
     */
    public FreeOnBoard getFreeOnBoard() {
        return freeOnBoard;
    }

    /**
     * Sets the value of the freeOnBoard property.
     * 
     * @param value
     *     allowed object is
     *     {@link FreeOnBoard }
     *     
     */
    public void setFreeOnBoard(FreeOnBoard value) {
        this.freeOnBoard = value;
    }

    /**
     * Gets the value of the purchasingUofM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurchasingUofM() {
        return purchasingUofM;
    }

    /**
     * Sets the value of the purchasingUofM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurchasingUofM(String value) {
        this.purchasingUofM = value;
    }

    /**
     * Gets the value of the planningLeadTime property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getPlanningLeadTime() {
        return planningLeadTime;
    }

    /**
     * Sets the value of the planningLeadTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setPlanningLeadTime(Short value) {
        this.planningLeadTime = value;
    }

    /**
     * Gets the value of the orderMultipleQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getOrderMultipleQuantity() {
        return orderMultipleQuantity;
    }

    /**
     * Sets the value of the orderMultipleQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setOrderMultipleQuantity(Quantity value) {
        this.orderMultipleQuantity = value;
    }

}
