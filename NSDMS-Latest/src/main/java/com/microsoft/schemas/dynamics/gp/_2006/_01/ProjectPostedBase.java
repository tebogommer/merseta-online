
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectPostedBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectPostedBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectAmount"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="POCosts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="POQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="EarningsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectPostedBase", propOrder = {
    "poCosts",
    "poQuantity",
    "earningsAmount"
})
@XmlSeeAlso({
    ProjectBudgetPosted.class,
    ProjectPosted.class
})
public abstract class ProjectPostedBase
    extends ProjectAmount
{

    @XmlElement(name = "POCosts")
    protected MoneyAmount poCosts;
    @XmlElement(name = "POQuantity")
    protected Quantity poQuantity;
    @XmlElement(name = "EarningsAmount")
    protected MoneyAmount earningsAmount;

    /**
     * Gets the value of the poCosts property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPOCosts() {
        return poCosts;
    }

    /**
     * Sets the value of the poCosts property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPOCosts(MoneyAmount value) {
        this.poCosts = value;
    }

    /**
     * Gets the value of the poQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getPOQuantity() {
        return poQuantity;
    }

    /**
     * Sets the value of the poQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setPOQuantity(Quantity value) {
        this.poQuantity = value;
    }

    /**
     * Gets the value of the earningsAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getEarningsAmount() {
        return earningsAmount;
    }

    /**
     * Sets the value of the earningsAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setEarningsAmount(MoneyAmount value) {
        this.earningsAmount = value;
    }

}
