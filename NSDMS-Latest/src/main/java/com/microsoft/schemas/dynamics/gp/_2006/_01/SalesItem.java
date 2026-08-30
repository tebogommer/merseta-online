
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}InventoriedItem"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ValuationMethod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ValuationMethod"/&gt;
 *         &lt;element name="TrackingOption" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TrackingOption"/&gt;
 *         &lt;element name="LotCategoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LotCategoryKey" minOccurs="0"/&gt;
 *         &lt;element name="MinimumShelfLifeDays1" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="MinimumShelfLifeDays2" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="RevalueInventory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="TolerancePercentage" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="Quantities" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesItemWarehouse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesItem", propOrder = {
    "valuationMethod",
    "trackingOption",
    "lotCategoryKey",
    "minimumShelfLifeDays1",
    "minimumShelfLifeDays2",
    "revalueInventory",
    "tolerancePercentage",
    "quantities"
})
public class SalesItem
    extends InventoriedItem
{

    @XmlElement(name = "ValuationMethod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ValuationMethod valuationMethod;
    @XmlElement(name = "TrackingOption", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected TrackingOption trackingOption;
    @XmlElement(name = "LotCategoryKey")
    protected LotCategoryKey lotCategoryKey;
    @XmlElement(name = "MinimumShelfLifeDays1", required = true, type = Short.class, nillable = true)
    protected Short minimumShelfLifeDays1;
    @XmlElement(name = "MinimumShelfLifeDays2", required = true, type = Short.class, nillable = true)
    protected Short minimumShelfLifeDays2;
    @XmlElement(name = "RevalueInventory", required = true, type = Boolean.class, nillable = true)
    protected Boolean revalueInventory;
    @XmlElement(name = "TolerancePercentage")
    protected Percent tolerancePercentage;
    @XmlElement(name = "Quantities")
    protected ArrayOfSalesItemWarehouse quantities;

    /**
     * Gets the value of the valuationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link ValuationMethod }
     *     
     */
    public ValuationMethod getValuationMethod() {
        return valuationMethod;
    }

    /**
     * Sets the value of the valuationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ValuationMethod }
     *     
     */
    public void setValuationMethod(ValuationMethod value) {
        this.valuationMethod = value;
    }

    /**
     * Gets the value of the trackingOption property.
     * 
     * @return
     *     possible object is
     *     {@link TrackingOption }
     *     
     */
    public TrackingOption getTrackingOption() {
        return trackingOption;
    }

    /**
     * Sets the value of the trackingOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrackingOption }
     *     
     */
    public void setTrackingOption(TrackingOption value) {
        this.trackingOption = value;
    }

    /**
     * Gets the value of the lotCategoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link LotCategoryKey }
     *     
     */
    public LotCategoryKey getLotCategoryKey() {
        return lotCategoryKey;
    }

    /**
     * Sets the value of the lotCategoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link LotCategoryKey }
     *     
     */
    public void setLotCategoryKey(LotCategoryKey value) {
        this.lotCategoryKey = value;
    }

    /**
     * Gets the value of the minimumShelfLifeDays1 property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getMinimumShelfLifeDays1() {
        return minimumShelfLifeDays1;
    }

    /**
     * Sets the value of the minimumShelfLifeDays1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setMinimumShelfLifeDays1(Short value) {
        this.minimumShelfLifeDays1 = value;
    }

    /**
     * Gets the value of the minimumShelfLifeDays2 property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getMinimumShelfLifeDays2() {
        return minimumShelfLifeDays2;
    }

    /**
     * Sets the value of the minimumShelfLifeDays2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setMinimumShelfLifeDays2(Short value) {
        this.minimumShelfLifeDays2 = value;
    }

    /**
     * Gets the value of the revalueInventory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRevalueInventory() {
        return revalueInventory;
    }

    /**
     * Sets the value of the revalueInventory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRevalueInventory(Boolean value) {
        this.revalueInventory = value;
    }

    /**
     * Gets the value of the tolerancePercentage property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getTolerancePercentage() {
        return tolerancePercentage;
    }

    /**
     * Sets the value of the tolerancePercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setTolerancePercentage(Percent value) {
        this.tolerancePercentage = value;
    }

    /**
     * Gets the value of the quantities property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesItemWarehouse }
     *     
     */
    public ArrayOfSalesItemWarehouse getQuantities() {
        return quantities;
    }

    /**
     * Sets the value of the quantities property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesItemWarehouse }
     *     
     */
    public void setQuantities(ArrayOfSalesItemWarehouse value) {
        this.quantities = value;
    }

}
