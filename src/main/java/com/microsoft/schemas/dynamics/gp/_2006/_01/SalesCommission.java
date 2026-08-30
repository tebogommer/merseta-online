
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesCommission complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesCommission"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesCommissionKey" minOccurs="0"/&gt;
 *         &lt;element name="SalespersonKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesTerritoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTerritoryKey" minOccurs="0"/&gt;
 *         &lt;element name="CommissionPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="CommissionAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PercentOfSale" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="CommissionSaleAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="NoncommissionedAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="SalesAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesCommission", propOrder = {
    "key",
    "salespersonKey",
    "salesTerritoryKey",
    "commissionPercent",
    "commissionAmount",
    "percentOfSale",
    "commissionSaleAmount",
    "noncommissionedAmount",
    "salesAmount"
})
public class SalesCommission
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected SalesCommissionKey key;
    @XmlElement(name = "SalespersonKey")
    protected SalespersonKey salespersonKey;
    @XmlElement(name = "SalesTerritoryKey")
    protected SalesTerritoryKey salesTerritoryKey;
    @XmlElement(name = "CommissionPercent")
    protected Percent commissionPercent;
    @XmlElement(name = "CommissionAmount")
    protected MoneyAmount commissionAmount;
    @XmlElement(name = "PercentOfSale")
    protected Percent percentOfSale;
    @XmlElement(name = "CommissionSaleAmount")
    protected MoneyAmount commissionSaleAmount;
    @XmlElement(name = "NoncommissionedAmount")
    protected MoneyAmount noncommissionedAmount;
    @XmlElement(name = "SalesAmount")
    protected MoneyAmount salesAmount;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link SalesCommissionKey }
     *     
     */
    public SalesCommissionKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesCommissionKey }
     *     
     */
    public void setKey(SalesCommissionKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the salespersonKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalespersonKey }
     *     
     */
    public SalespersonKey getSalespersonKey() {
        return salespersonKey;
    }

    /**
     * Sets the value of the salespersonKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalespersonKey }
     *     
     */
    public void setSalespersonKey(SalespersonKey value) {
        this.salespersonKey = value;
    }

    /**
     * Gets the value of the salesTerritoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTerritoryKey }
     *     
     */
    public SalesTerritoryKey getSalesTerritoryKey() {
        return salesTerritoryKey;
    }

    /**
     * Sets the value of the salesTerritoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTerritoryKey }
     *     
     */
    public void setSalesTerritoryKey(SalesTerritoryKey value) {
        this.salesTerritoryKey = value;
    }

    /**
     * Gets the value of the commissionPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getCommissionPercent() {
        return commissionPercent;
    }

    /**
     * Sets the value of the commissionPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setCommissionPercent(Percent value) {
        this.commissionPercent = value;
    }

    /**
     * Gets the value of the commissionAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCommissionAmount() {
        return commissionAmount;
    }

    /**
     * Sets the value of the commissionAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCommissionAmount(MoneyAmount value) {
        this.commissionAmount = value;
    }

    /**
     * Gets the value of the percentOfSale property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getPercentOfSale() {
        return percentOfSale;
    }

    /**
     * Sets the value of the percentOfSale property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setPercentOfSale(Percent value) {
        this.percentOfSale = value;
    }

    /**
     * Gets the value of the commissionSaleAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCommissionSaleAmount() {
        return commissionSaleAmount;
    }

    /**
     * Sets the value of the commissionSaleAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCommissionSaleAmount(MoneyAmount value) {
        this.commissionSaleAmount = value;
    }

    /**
     * Gets the value of the noncommissionedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getNoncommissionedAmount() {
        return noncommissionedAmount;
    }

    /**
     * Sets the value of the noncommissionedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setNoncommissionedAmount(MoneyAmount value) {
        this.noncommissionedAmount = value;
    }

    /**
     * Gets the value of the salesAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getSalesAmount() {
        return salesAmount;
    }

    /**
     * Sets the value of the salesAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setSalesAmount(MoneyAmount value) {
        this.salesAmount = value;
    }

}
