
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectContractUnposted complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectContractUnposted"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectUnpostedBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RetainerFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ServiceFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RetentionAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BilledRetentionAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ReceiptsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="EarningsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CostOfEarningsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="POCosts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="POQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectContractUnposted", propOrder = {
    "projectFeeAmount",
    "retainerFeeAmount",
    "serviceFeeAmount",
    "retentionAmount",
    "billedRetentionAmount",
    "receiptsAmount",
    "earningsAmount",
    "costOfEarningsAmount",
    "poCosts",
    "poQuantity"
})
public class ProjectContractUnposted
    extends ProjectUnpostedBase
{

    @XmlElement(name = "ProjectFeeAmount")
    protected MoneyAmount projectFeeAmount;
    @XmlElement(name = "RetainerFeeAmount")
    protected MoneyAmount retainerFeeAmount;
    @XmlElement(name = "ServiceFeeAmount")
    protected MoneyAmount serviceFeeAmount;
    @XmlElement(name = "RetentionAmount")
    protected MoneyAmount retentionAmount;
    @XmlElement(name = "BilledRetentionAmount")
    protected MoneyAmount billedRetentionAmount;
    @XmlElement(name = "ReceiptsAmount")
    protected MoneyAmount receiptsAmount;
    @XmlElement(name = "EarningsAmount")
    protected MoneyAmount earningsAmount;
    @XmlElement(name = "CostOfEarningsAmount")
    protected MoneyAmount costOfEarningsAmount;
    @XmlElement(name = "POCosts")
    protected MoneyAmount poCosts;
    @XmlElement(name = "POQuantity")
    protected Quantity poQuantity;

    /**
     * Gets the value of the projectFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getProjectFeeAmount() {
        return projectFeeAmount;
    }

    /**
     * Sets the value of the projectFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setProjectFeeAmount(MoneyAmount value) {
        this.projectFeeAmount = value;
    }

    /**
     * Gets the value of the retainerFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRetainerFeeAmount() {
        return retainerFeeAmount;
    }

    /**
     * Sets the value of the retainerFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRetainerFeeAmount(MoneyAmount value) {
        this.retainerFeeAmount = value;
    }

    /**
     * Gets the value of the serviceFeeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getServiceFeeAmount() {
        return serviceFeeAmount;
    }

    /**
     * Sets the value of the serviceFeeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setServiceFeeAmount(MoneyAmount value) {
        this.serviceFeeAmount = value;
    }

    /**
     * Gets the value of the retentionAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRetentionAmount() {
        return retentionAmount;
    }

    /**
     * Sets the value of the retentionAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRetentionAmount(MoneyAmount value) {
        this.retentionAmount = value;
    }

    /**
     * Gets the value of the billedRetentionAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBilledRetentionAmount() {
        return billedRetentionAmount;
    }

    /**
     * Sets the value of the billedRetentionAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBilledRetentionAmount(MoneyAmount value) {
        this.billedRetentionAmount = value;
    }

    /**
     * Gets the value of the receiptsAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getReceiptsAmount() {
        return receiptsAmount;
    }

    /**
     * Sets the value of the receiptsAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setReceiptsAmount(MoneyAmount value) {
        this.receiptsAmount = value;
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

    /**
     * Gets the value of the costOfEarningsAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCostOfEarningsAmount() {
        return costOfEarningsAmount;
    }

    /**
     * Sets the value of the costOfEarningsAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCostOfEarningsAmount(MoneyAmount value) {
        this.costOfEarningsAmount = value;
    }

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

}
