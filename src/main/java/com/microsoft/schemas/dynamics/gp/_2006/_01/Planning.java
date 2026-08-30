
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Planning complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Planning"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OrderPolicy" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}OrderPolicy"/&gt;
 *         &lt;element name="FixedOrderQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="OrderPointQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="OrderUpToLevelQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ReplenishmentMethod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReplenishmentMethod"/&gt;
 *         &lt;element name="ShrinkageFactor" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="PurchasingLeadTime" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ManufacturingLeadTime" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="TimeFence" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="MinimumOrderQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="MaximumOrderQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="OrderMultipleQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ReorderVarianceQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="SafetyStockQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Planning", propOrder = {
    "orderPolicy",
    "fixedOrderQuantity",
    "orderPointQuantity",
    "orderUpToLevelQuantity",
    "replenishmentMethod",
    "shrinkageFactor",
    "purchasingLeadTime",
    "manufacturingLeadTime",
    "timeFence",
    "minimumOrderQuantity",
    "maximumOrderQuantity",
    "orderMultipleQuantity",
    "reorderVarianceQuantity",
    "safetyStockQuantity"
})
public class Planning {

    @XmlElement(name = "OrderPolicy", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected OrderPolicy orderPolicy;
    @XmlElement(name = "FixedOrderQuantity")
    protected Quantity fixedOrderQuantity;
    @XmlElement(name = "OrderPointQuantity")
    protected Quantity orderPointQuantity;
    @XmlElement(name = "OrderUpToLevelQuantity")
    protected Quantity orderUpToLevelQuantity;
    @XmlElement(name = "ReplenishmentMethod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ReplenishmentMethod replenishmentMethod;
    @XmlElement(name = "ShrinkageFactor")
    protected Percent shrinkageFactor;
    @XmlElement(name = "PurchasingLeadTime", required = true, nillable = true)
    protected BigDecimal purchasingLeadTime;
    @XmlElement(name = "ManufacturingLeadTime", required = true, nillable = true)
    protected BigDecimal manufacturingLeadTime;
    @XmlElement(name = "TimeFence", required = true, type = Short.class, nillable = true)
    protected Short timeFence;
    @XmlElement(name = "MinimumOrderQuantity")
    protected Quantity minimumOrderQuantity;
    @XmlElement(name = "MaximumOrderQuantity")
    protected Quantity maximumOrderQuantity;
    @XmlElement(name = "OrderMultipleQuantity")
    protected Quantity orderMultipleQuantity;
    @XmlElement(name = "ReorderVarianceQuantity")
    protected Quantity reorderVarianceQuantity;
    @XmlElement(name = "SafetyStockQuantity")
    protected Quantity safetyStockQuantity;

    /**
     * Gets the value of the orderPolicy property.
     * 
     * @return
     *     possible object is
     *     {@link OrderPolicy }
     *     
     */
    public OrderPolicy getOrderPolicy() {
        return orderPolicy;
    }

    /**
     * Sets the value of the orderPolicy property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderPolicy }
     *     
     */
    public void setOrderPolicy(OrderPolicy value) {
        this.orderPolicy = value;
    }

    /**
     * Gets the value of the fixedOrderQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getFixedOrderQuantity() {
        return fixedOrderQuantity;
    }

    /**
     * Sets the value of the fixedOrderQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setFixedOrderQuantity(Quantity value) {
        this.fixedOrderQuantity = value;
    }

    /**
     * Gets the value of the orderPointQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getOrderPointQuantity() {
        return orderPointQuantity;
    }

    /**
     * Sets the value of the orderPointQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setOrderPointQuantity(Quantity value) {
        this.orderPointQuantity = value;
    }

    /**
     * Gets the value of the orderUpToLevelQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getOrderUpToLevelQuantity() {
        return orderUpToLevelQuantity;
    }

    /**
     * Sets the value of the orderUpToLevelQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setOrderUpToLevelQuantity(Quantity value) {
        this.orderUpToLevelQuantity = value;
    }

    /**
     * Gets the value of the replenishmentMethod property.
     * 
     * @return
     *     possible object is
     *     {@link ReplenishmentMethod }
     *     
     */
    public ReplenishmentMethod getReplenishmentMethod() {
        return replenishmentMethod;
    }

    /**
     * Sets the value of the replenishmentMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReplenishmentMethod }
     *     
     */
    public void setReplenishmentMethod(ReplenishmentMethod value) {
        this.replenishmentMethod = value;
    }

    /**
     * Gets the value of the shrinkageFactor property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getShrinkageFactor() {
        return shrinkageFactor;
    }

    /**
     * Sets the value of the shrinkageFactor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setShrinkageFactor(Percent value) {
        this.shrinkageFactor = value;
    }

    /**
     * Gets the value of the purchasingLeadTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPurchasingLeadTime() {
        return purchasingLeadTime;
    }

    /**
     * Sets the value of the purchasingLeadTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPurchasingLeadTime(BigDecimal value) {
        this.purchasingLeadTime = value;
    }

    /**
     * Gets the value of the manufacturingLeadTime property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getManufacturingLeadTime() {
        return manufacturingLeadTime;
    }

    /**
     * Sets the value of the manufacturingLeadTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setManufacturingLeadTime(BigDecimal value) {
        this.manufacturingLeadTime = value;
    }

    /**
     * Gets the value of the timeFence property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getTimeFence() {
        return timeFence;
    }

    /**
     * Sets the value of the timeFence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setTimeFence(Short value) {
        this.timeFence = value;
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

    /**
     * Gets the value of the reorderVarianceQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getReorderVarianceQuantity() {
        return reorderVarianceQuantity;
    }

    /**
     * Sets the value of the reorderVarianceQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setReorderVarianceQuantity(Quantity value) {
        this.reorderVarianceQuantity = value;
    }

    /**
     * Gets the value of the safetyStockQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getSafetyStockQuantity() {
        return safetyStockQuantity;
    }

    /**
     * Sets the value of the safetyStockQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setSafetyStockQuantity(Quantity value) {
        this.safetyStockQuantity = value;
    }

}
