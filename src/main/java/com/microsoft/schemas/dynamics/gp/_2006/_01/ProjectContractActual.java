
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectContractActual complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectContractActual"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectAmount"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CommittedPOQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="CommittedPOCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CommittedPOTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ProjectFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RetainerFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ServiceFeeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="RetentionAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BilledRetentionAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="LossAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BeginDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ReceiptsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="POCosts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="POQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="EarningsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CostOfEarningsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectContractActual", propOrder = {
    "committedPOQuantity",
    "committedPOCost",
    "committedPOTaxAmount",
    "projectFeeAmount",
    "retainerFeeAmount",
    "serviceFeeAmount",
    "retentionAmount",
    "billedRetentionAmount",
    "lossAmount",
    "beginDate",
    "endDate",
    "receiptsAmount",
    "poCosts",
    "poQuantity",
    "earningsAmount",
    "costOfEarningsAmount"
})
public class ProjectContractActual
    extends ProjectAmount
{

    @XmlElement(name = "CommittedPOQuantity")
    protected Quantity committedPOQuantity;
    @XmlElement(name = "CommittedPOCost")
    protected MoneyAmount committedPOCost;
    @XmlElement(name = "CommittedPOTaxAmount")
    protected MoneyAmount committedPOTaxAmount;
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
    @XmlElement(name = "LossAmount")
    protected MoneyAmount lossAmount;
    @XmlElement(name = "BeginDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar beginDate;
    @XmlElement(name = "EndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "ReceiptsAmount")
    protected MoneyAmount receiptsAmount;
    @XmlElement(name = "POCosts")
    protected MoneyAmount poCosts;
    @XmlElement(name = "POQuantity")
    protected Quantity poQuantity;
    @XmlElement(name = "EarningsAmount")
    protected MoneyAmount earningsAmount;
    @XmlElement(name = "CostOfEarningsAmount")
    protected MoneyAmount costOfEarningsAmount;

    /**
     * Gets the value of the committedPOQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getCommittedPOQuantity() {
        return committedPOQuantity;
    }

    /**
     * Sets the value of the committedPOQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setCommittedPOQuantity(Quantity value) {
        this.committedPOQuantity = value;
    }

    /**
     * Gets the value of the committedPOCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCommittedPOCost() {
        return committedPOCost;
    }

    /**
     * Sets the value of the committedPOCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCommittedPOCost(MoneyAmount value) {
        this.committedPOCost = value;
    }

    /**
     * Gets the value of the committedPOTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCommittedPOTaxAmount() {
        return committedPOTaxAmount;
    }

    /**
     * Sets the value of the committedPOTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCommittedPOTaxAmount(MoneyAmount value) {
        this.committedPOTaxAmount = value;
    }

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
     * Gets the value of the lossAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getLossAmount() {
        return lossAmount;
    }

    /**
     * Sets the value of the lossAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setLossAmount(MoneyAmount value) {
        this.lossAmount = value;
    }

    /**
     * Gets the value of the beginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBeginDate() {
        return beginDate;
    }

    /**
     * Sets the value of the beginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBeginDate(XMLGregorianCalendar value) {
        this.beginDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
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

}
