
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectBudgetBaseline complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectBudgetBaseline"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBudgetBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="NetProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="OverheadPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="ProfitPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectTaxBasis"/&gt;
 *         &lt;element name="PurchaseTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTaxBasis"/&gt;
 *         &lt;element name="SalesTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectBudgetBaseline", propOrder = {
    "unitCost",
    "netProfitAmount",
    "overheadPercent",
    "profitPercent",
    "purchaseTaxBasis",
    "purchaseTaxScheduleKey",
    "salesTaxBasis",
    "salesTaxScheduleKey"
})
public class ProjectBudgetBaseline
    extends ProjectBudgetBase
{

    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "NetProfitAmount")
    protected MoneyAmount netProfitAmount;
    @XmlElement(name = "OverheadPercent")
    protected Quantity overheadPercent;
    @XmlElement(name = "ProfitPercent")
    protected Percent profitPercent;
    @XmlElement(name = "PurchaseTaxBasis", required = true)
    @XmlSchemaType(name = "string")
    protected ProjectTaxBasis purchaseTaxBasis;
    @XmlElement(name = "PurchaseTaxScheduleKey")
    protected TaxScheduleKey purchaseTaxScheduleKey;
    @XmlElement(name = "SalesTaxBasis", required = true)
    @XmlSchemaType(name = "string")
    protected SalesTaxBasis salesTaxBasis;
    @XmlElement(name = "SalesTaxScheduleKey")
    protected TaxScheduleKey salesTaxScheduleKey;

    /**
     * Gets the value of the unitCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the value of the unitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setUnitCost(MoneyAmount value) {
        this.unitCost = value;
    }

    /**
     * Gets the value of the netProfitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getNetProfitAmount() {
        return netProfitAmount;
    }

    /**
     * Sets the value of the netProfitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setNetProfitAmount(MoneyAmount value) {
        this.netProfitAmount = value;
    }

    /**
     * Gets the value of the overheadPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getOverheadPercent() {
        return overheadPercent;
    }

    /**
     * Sets the value of the overheadPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setOverheadPercent(Quantity value) {
        this.overheadPercent = value;
    }

    /**
     * Gets the value of the profitPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getProfitPercent() {
        return profitPercent;
    }

    /**
     * Sets the value of the profitPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setProfitPercent(Percent value) {
        this.profitPercent = value;
    }

    /**
     * Gets the value of the purchaseTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectTaxBasis }
     *     
     */
    public ProjectTaxBasis getPurchaseTaxBasis() {
        return purchaseTaxBasis;
    }

    /**
     * Sets the value of the purchaseTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectTaxBasis }
     *     
     */
    public void setPurchaseTaxBasis(ProjectTaxBasis value) {
        this.purchaseTaxBasis = value;
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
     * Gets the value of the salesTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTaxBasis }
     *     
     */
    public SalesTaxBasis getSalesTaxBasis() {
        return salesTaxBasis;
    }

    /**
     * Sets the value of the salesTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTaxBasis }
     *     
     */
    public void setSalesTaxBasis(SalesTaxBasis value) {
        this.salesTaxBasis = value;
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

}
