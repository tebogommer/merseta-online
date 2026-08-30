
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CostPlus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CostPlus"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Profit"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="ProfitFixed" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProfitFixed" minOccurs="0"/&gt;
 *           &lt;element name="ProfitVariable" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProfitVariable" minOccurs="0"/&gt;
 *           &lt;element name="TotalProfit" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TotalProfit" minOccurs="0"/&gt;
 *           &lt;element name="PercentOfBaseline" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PercentOfBaseline" minOccurs="0"/&gt;
 *           &lt;element name="PercentOfActual" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PercentOfActual" minOccurs="0"/&gt;
 *           &lt;element name="None" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}NoProfit" minOccurs="0"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CostPlus", propOrder = {
    "profitFixed",
    "profitVariable",
    "totalProfit",
    "percentOfBaseline",
    "percentOfActual",
    "none"
})
public class CostPlus
    extends Profit
{

    @XmlElement(name = "ProfitFixed")
    protected ProfitFixed profitFixed;
    @XmlElement(name = "ProfitVariable")
    protected ProfitVariable profitVariable;
    @XmlElement(name = "TotalProfit")
    protected TotalProfit totalProfit;
    @XmlElement(name = "PercentOfBaseline")
    protected PercentOfBaseline percentOfBaseline;
    @XmlElement(name = "PercentOfActual")
    protected PercentOfActual percentOfActual;
    @XmlElement(name = "None")
    protected NoProfit none;

    /**
     * Gets the value of the profitFixed property.
     * 
     * @return
     *     possible object is
     *     {@link ProfitFixed }
     *     
     */
    public ProfitFixed getProfitFixed() {
        return profitFixed;
    }

    /**
     * Sets the value of the profitFixed property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfitFixed }
     *     
     */
    public void setProfitFixed(ProfitFixed value) {
        this.profitFixed = value;
    }

    /**
     * Gets the value of the profitVariable property.
     * 
     * @return
     *     possible object is
     *     {@link ProfitVariable }
     *     
     */
    public ProfitVariable getProfitVariable() {
        return profitVariable;
    }

    /**
     * Sets the value of the profitVariable property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfitVariable }
     *     
     */
    public void setProfitVariable(ProfitVariable value) {
        this.profitVariable = value;
    }

    /**
     * Gets the value of the totalProfit property.
     * 
     * @return
     *     possible object is
     *     {@link TotalProfit }
     *     
     */
    public TotalProfit getTotalProfit() {
        return totalProfit;
    }

    /**
     * Sets the value of the totalProfit property.
     * 
     * @param value
     *     allowed object is
     *     {@link TotalProfit }
     *     
     */
    public void setTotalProfit(TotalProfit value) {
        this.totalProfit = value;
    }

    /**
     * Gets the value of the percentOfBaseline property.
     * 
     * @return
     *     possible object is
     *     {@link PercentOfBaseline }
     *     
     */
    public PercentOfBaseline getPercentOfBaseline() {
        return percentOfBaseline;
    }

    /**
     * Sets the value of the percentOfBaseline property.
     * 
     * @param value
     *     allowed object is
     *     {@link PercentOfBaseline }
     *     
     */
    public void setPercentOfBaseline(PercentOfBaseline value) {
        this.percentOfBaseline = value;
    }

    /**
     * Gets the value of the percentOfActual property.
     * 
     * @return
     *     possible object is
     *     {@link PercentOfActual }
     *     
     */
    public PercentOfActual getPercentOfActual() {
        return percentOfActual;
    }

    /**
     * Sets the value of the percentOfActual property.
     * 
     * @param value
     *     allowed object is
     *     {@link PercentOfActual }
     *     
     */
    public void setPercentOfActual(PercentOfActual value) {
        this.percentOfActual = value;
    }

    /**
     * Gets the value of the none property.
     * 
     * @return
     *     possible object is
     *     {@link NoProfit }
     *     
     */
    public NoProfit getNone() {
        return none;
    }

    /**
     * Sets the value of the none property.
     * 
     * @param value
     *     allowed object is
     *     {@link NoProfit }
     *     
     */
    public void setNone(NoProfit value) {
        this.none = value;
    }

}
