
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectBudget complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectBudget"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBudgetKey" minOccurs="0"/&gt;
 *         &lt;element name="LineSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IsInventoryItem" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Status" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectStatus"/&gt;
 *         &lt;element name="ProfitType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProfitType"/&gt;
 *         &lt;element name="BillingType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BillingType"/&gt;
 *         &lt;element name="POCommittedCosts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="POCommittedQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="CostCompletedPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="CostOfEarningsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="DoesAcceptEquipmentRateTableReplacement" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DoesAcceptLaborRateTableReplacement" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="EarningsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="EquipmentRateTableKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectEquipmentRateTableKey" minOccurs="0"/&gt;
 *         &lt;element name="LaborRateTableKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectLaborRateTableKey" minOccurs="0"/&gt;
 *         &lt;element name="LaborRateTableType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectLaborRateTableType"/&gt;
 *         &lt;element name="OverheadRateMethod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectOverheadRateMethod"/&gt;
 *         &lt;element name="PayCodeHourlyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="PayCodeSalaryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="QuantityCompletedPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="ReceiptsAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxPaidAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="UofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UofMScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}UofMScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="WriteoffAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="WriteoffTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="WriteUpDownAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TransactionalCurrencyCode" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="Forecast" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBudgetForecast" minOccurs="0"/&gt;
 *         &lt;element name="Baseline" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBudgetBaseline" minOccurs="0"/&gt;
 *         &lt;element name="Billed" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBudgetBilled" minOccurs="0"/&gt;
 *         &lt;element name="Posted" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBudgetPosted" minOccurs="0"/&gt;
 *         &lt;element name="Unposted" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBudgetUnposted" minOccurs="0"/&gt;
 *         &lt;element name="Actual" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBudgetActual" minOccurs="0"/&gt;
 *         &lt;element name="Uncommitted" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBudgetUncommitted" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectBudget", propOrder = {
    "key",
    "lineSequenceNumber",
    "isInventoryItem",
    "status",
    "profitType",
    "billingType",
    "poCommittedCosts",
    "poCommittedQuantity",
    "costCompletedPercent",
    "costOfEarningsAmount",
    "doesAcceptEquipmentRateTableReplacement",
    "doesAcceptLaborRateTableReplacement",
    "earningsAmount",
    "equipmentRateTableKey",
    "laborRateTableKey",
    "laborRateTableType",
    "overheadRateMethod",
    "payCodeHourlyKey",
    "payCodeSalaryKey",
    "projectAmount",
    "quantityCompletedPercent",
    "receiptsAmount",
    "taxPaidAmount",
    "uofM",
    "uofMScheduleKey",
    "writeoffAmount",
    "writeoffTaxAmount",
    "writeUpDownAmount",
    "transactionalCurrencyCode",
    "forecast",
    "baseline",
    "billed",
    "posted",
    "unposted",
    "actual",
    "uncommitted"
})
public class ProjectBudget
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ProjectBudgetKey key;
    @XmlElement(name = "LineSequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer lineSequenceNumber;
    @XmlElement(name = "IsInventoryItem", required = true, type = Boolean.class, nillable = true)
    protected Boolean isInventoryItem;
    @XmlElement(name = "Status", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectStatus status;
    @XmlElement(name = "ProfitType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProfitType profitType;
    @XmlElement(name = "BillingType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected BillingType billingType;
    @XmlElement(name = "POCommittedCosts")
    protected MoneyAmount poCommittedCosts;
    @XmlElement(name = "POCommittedQuantity")
    protected Quantity poCommittedQuantity;
    @XmlElement(name = "CostCompletedPercent")
    protected Percent costCompletedPercent;
    @XmlElement(name = "CostOfEarningsAmount")
    protected MoneyAmount costOfEarningsAmount;
    @XmlElement(name = "DoesAcceptEquipmentRateTableReplacement", required = true, type = Boolean.class, nillable = true)
    protected Boolean doesAcceptEquipmentRateTableReplacement;
    @XmlElement(name = "DoesAcceptLaborRateTableReplacement", required = true, type = Boolean.class, nillable = true)
    protected Boolean doesAcceptLaborRateTableReplacement;
    @XmlElement(name = "EarningsAmount")
    protected MoneyAmount earningsAmount;
    @XmlElement(name = "EquipmentRateTableKey")
    protected ProjectEquipmentRateTableKey equipmentRateTableKey;
    @XmlElement(name = "LaborRateTableKey")
    protected ProjectLaborRateTableKey laborRateTableKey;
    @XmlElement(name = "LaborRateTableType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectLaborRateTableType laborRateTableType;
    @XmlElement(name = "OverheadRateMethod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectOverheadRateMethod overheadRateMethod;
    @XmlElement(name = "PayCodeHourlyKey")
    protected PayCodeKey payCodeHourlyKey;
    @XmlElement(name = "PayCodeSalaryKey")
    protected PayCodeKey payCodeSalaryKey;
    @XmlElement(name = "ProjectAmount")
    protected MoneyAmount projectAmount;
    @XmlElement(name = "QuantityCompletedPercent")
    protected Percent quantityCompletedPercent;
    @XmlElement(name = "ReceiptsAmount")
    protected MoneyAmount receiptsAmount;
    @XmlElement(name = "TaxPaidAmount")
    protected MoneyAmount taxPaidAmount;
    @XmlElement(name = "UofM")
    protected String uofM;
    @XmlElement(name = "UofMScheduleKey")
    protected UofMScheduleKey uofMScheduleKey;
    @XmlElement(name = "WriteoffAmount")
    protected MoneyAmount writeoffAmount;
    @XmlElement(name = "WriteoffTaxAmount")
    protected MoneyAmount writeoffTaxAmount;
    @XmlElement(name = "WriteUpDownAmount")
    protected MoneyAmount writeUpDownAmount;
    @XmlElement(name = "TransactionalCurrencyCode")
    protected CurrencyKey transactionalCurrencyCode;
    @XmlElement(name = "Forecast")
    protected ProjectBudgetForecast forecast;
    @XmlElement(name = "Baseline")
    protected ProjectBudgetBaseline baseline;
    @XmlElement(name = "Billed")
    protected ProjectBudgetBilled billed;
    @XmlElement(name = "Posted")
    protected ProjectBudgetPosted posted;
    @XmlElement(name = "Unposted")
    protected ProjectBudgetUnposted unposted;
    @XmlElement(name = "Actual")
    protected ProjectBudgetActual actual;
    @XmlElement(name = "Uncommitted")
    protected ProjectBudgetUncommitted uncommitted;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBudgetKey }
     *     
     */
    public ProjectBudgetKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBudgetKey }
     *     
     */
    public void setKey(ProjectBudgetKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the lineSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLineSequenceNumber() {
        return lineSequenceNumber;
    }

    /**
     * Sets the value of the lineSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLineSequenceNumber(Integer value) {
        this.lineSequenceNumber = value;
    }

    /**
     * Gets the value of the isInventoryItem property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsInventoryItem() {
        return isInventoryItem;
    }

    /**
     * Sets the value of the isInventoryItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsInventoryItem(Boolean value) {
        this.isInventoryItem = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectStatus }
     *     
     */
    public ProjectStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectStatus }
     *     
     */
    public void setStatus(ProjectStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the profitType property.
     * 
     * @return
     *     possible object is
     *     {@link ProfitType }
     *     
     */
    public ProfitType getProfitType() {
        return profitType;
    }

    /**
     * Sets the value of the profitType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfitType }
     *     
     */
    public void setProfitType(ProfitType value) {
        this.profitType = value;
    }

    /**
     * Gets the value of the billingType property.
     * 
     * @return
     *     possible object is
     *     {@link BillingType }
     *     
     */
    public BillingType getBillingType() {
        return billingType;
    }

    /**
     * Sets the value of the billingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link BillingType }
     *     
     */
    public void setBillingType(BillingType value) {
        this.billingType = value;
    }

    /**
     * Gets the value of the poCommittedCosts property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPOCommittedCosts() {
        return poCommittedCosts;
    }

    /**
     * Sets the value of the poCommittedCosts property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPOCommittedCosts(MoneyAmount value) {
        this.poCommittedCosts = value;
    }

    /**
     * Gets the value of the poCommittedQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getPOCommittedQuantity() {
        return poCommittedQuantity;
    }

    /**
     * Sets the value of the poCommittedQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setPOCommittedQuantity(Quantity value) {
        this.poCommittedQuantity = value;
    }

    /**
     * Gets the value of the costCompletedPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getCostCompletedPercent() {
        return costCompletedPercent;
    }

    /**
     * Sets the value of the costCompletedPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setCostCompletedPercent(Percent value) {
        this.costCompletedPercent = value;
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
     * Gets the value of the doesAcceptEquipmentRateTableReplacement property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDoesAcceptEquipmentRateTableReplacement() {
        return doesAcceptEquipmentRateTableReplacement;
    }

    /**
     * Sets the value of the doesAcceptEquipmentRateTableReplacement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDoesAcceptEquipmentRateTableReplacement(Boolean value) {
        this.doesAcceptEquipmentRateTableReplacement = value;
    }

    /**
     * Gets the value of the doesAcceptLaborRateTableReplacement property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDoesAcceptLaborRateTableReplacement() {
        return doesAcceptLaborRateTableReplacement;
    }

    /**
     * Sets the value of the doesAcceptLaborRateTableReplacement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDoesAcceptLaborRateTableReplacement(Boolean value) {
        this.doesAcceptLaborRateTableReplacement = value;
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
     * Gets the value of the equipmentRateTableKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectEquipmentRateTableKey }
     *     
     */
    public ProjectEquipmentRateTableKey getEquipmentRateTableKey() {
        return equipmentRateTableKey;
    }

    /**
     * Sets the value of the equipmentRateTableKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectEquipmentRateTableKey }
     *     
     */
    public void setEquipmentRateTableKey(ProjectEquipmentRateTableKey value) {
        this.equipmentRateTableKey = value;
    }

    /**
     * Gets the value of the laborRateTableKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectLaborRateTableKey }
     *     
     */
    public ProjectLaborRateTableKey getLaborRateTableKey() {
        return laborRateTableKey;
    }

    /**
     * Sets the value of the laborRateTableKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectLaborRateTableKey }
     *     
     */
    public void setLaborRateTableKey(ProjectLaborRateTableKey value) {
        this.laborRateTableKey = value;
    }

    /**
     * Gets the value of the laborRateTableType property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectLaborRateTableType }
     *     
     */
    public ProjectLaborRateTableType getLaborRateTableType() {
        return laborRateTableType;
    }

    /**
     * Sets the value of the laborRateTableType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectLaborRateTableType }
     *     
     */
    public void setLaborRateTableType(ProjectLaborRateTableType value) {
        this.laborRateTableType = value;
    }

    /**
     * Gets the value of the overheadRateMethod property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectOverheadRateMethod }
     *     
     */
    public ProjectOverheadRateMethod getOverheadRateMethod() {
        return overheadRateMethod;
    }

    /**
     * Sets the value of the overheadRateMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectOverheadRateMethod }
     *     
     */
    public void setOverheadRateMethod(ProjectOverheadRateMethod value) {
        this.overheadRateMethod = value;
    }

    /**
     * Gets the value of the payCodeHourlyKey property.
     * 
     * @return
     *     possible object is
     *     {@link PayCodeKey }
     *     
     */
    public PayCodeKey getPayCodeHourlyKey() {
        return payCodeHourlyKey;
    }

    /**
     * Sets the value of the payCodeHourlyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayCodeKey }
     *     
     */
    public void setPayCodeHourlyKey(PayCodeKey value) {
        this.payCodeHourlyKey = value;
    }

    /**
     * Gets the value of the payCodeSalaryKey property.
     * 
     * @return
     *     possible object is
     *     {@link PayCodeKey }
     *     
     */
    public PayCodeKey getPayCodeSalaryKey() {
        return payCodeSalaryKey;
    }

    /**
     * Sets the value of the payCodeSalaryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayCodeKey }
     *     
     */
    public void setPayCodeSalaryKey(PayCodeKey value) {
        this.payCodeSalaryKey = value;
    }

    /**
     * Gets the value of the projectAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getProjectAmount() {
        return projectAmount;
    }

    /**
     * Sets the value of the projectAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setProjectAmount(MoneyAmount value) {
        this.projectAmount = value;
    }

    /**
     * Gets the value of the quantityCompletedPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getQuantityCompletedPercent() {
        return quantityCompletedPercent;
    }

    /**
     * Sets the value of the quantityCompletedPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setQuantityCompletedPercent(Percent value) {
        this.quantityCompletedPercent = value;
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
     * Gets the value of the taxPaidAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxPaidAmount() {
        return taxPaidAmount;
    }

    /**
     * Sets the value of the taxPaidAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxPaidAmount(MoneyAmount value) {
        this.taxPaidAmount = value;
    }

    /**
     * Gets the value of the uofM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUofM() {
        return uofM;
    }

    /**
     * Sets the value of the uofM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUofM(String value) {
        this.uofM = value;
    }

    /**
     * Gets the value of the uofMScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link UofMScheduleKey }
     *     
     */
    public UofMScheduleKey getUofMScheduleKey() {
        return uofMScheduleKey;
    }

    /**
     * Sets the value of the uofMScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link UofMScheduleKey }
     *     
     */
    public void setUofMScheduleKey(UofMScheduleKey value) {
        this.uofMScheduleKey = value;
    }

    /**
     * Gets the value of the writeoffAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getWriteoffAmount() {
        return writeoffAmount;
    }

    /**
     * Sets the value of the writeoffAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setWriteoffAmount(MoneyAmount value) {
        this.writeoffAmount = value;
    }

    /**
     * Gets the value of the writeoffTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getWriteoffTaxAmount() {
        return writeoffTaxAmount;
    }

    /**
     * Sets the value of the writeoffTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setWriteoffTaxAmount(MoneyAmount value) {
        this.writeoffTaxAmount = value;
    }

    /**
     * Gets the value of the writeUpDownAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getWriteUpDownAmount() {
        return writeUpDownAmount;
    }

    /**
     * Sets the value of the writeUpDownAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setWriteUpDownAmount(MoneyAmount value) {
        this.writeUpDownAmount = value;
    }

    /**
     * Gets the value of the transactionalCurrencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyKey }
     *     
     */
    public CurrencyKey getTransactionalCurrencyCode() {
        return transactionalCurrencyCode;
    }

    /**
     * Sets the value of the transactionalCurrencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyKey }
     *     
     */
    public void setTransactionalCurrencyCode(CurrencyKey value) {
        this.transactionalCurrencyCode = value;
    }

    /**
     * Gets the value of the forecast property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBudgetForecast }
     *     
     */
    public ProjectBudgetForecast getForecast() {
        return forecast;
    }

    /**
     * Sets the value of the forecast property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBudgetForecast }
     *     
     */
    public void setForecast(ProjectBudgetForecast value) {
        this.forecast = value;
    }

    /**
     * Gets the value of the baseline property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBudgetBaseline }
     *     
     */
    public ProjectBudgetBaseline getBaseline() {
        return baseline;
    }

    /**
     * Sets the value of the baseline property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBudgetBaseline }
     *     
     */
    public void setBaseline(ProjectBudgetBaseline value) {
        this.baseline = value;
    }

    /**
     * Gets the value of the billed property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBudgetBilled }
     *     
     */
    public ProjectBudgetBilled getBilled() {
        return billed;
    }

    /**
     * Sets the value of the billed property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBudgetBilled }
     *     
     */
    public void setBilled(ProjectBudgetBilled value) {
        this.billed = value;
    }

    /**
     * Gets the value of the posted property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBudgetPosted }
     *     
     */
    public ProjectBudgetPosted getPosted() {
        return posted;
    }

    /**
     * Sets the value of the posted property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBudgetPosted }
     *     
     */
    public void setPosted(ProjectBudgetPosted value) {
        this.posted = value;
    }

    /**
     * Gets the value of the unposted property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBudgetUnposted }
     *     
     */
    public ProjectBudgetUnposted getUnposted() {
        return unposted;
    }

    /**
     * Sets the value of the unposted property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBudgetUnposted }
     *     
     */
    public void setUnposted(ProjectBudgetUnposted value) {
        this.unposted = value;
    }

    /**
     * Gets the value of the actual property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBudgetActual }
     *     
     */
    public ProjectBudgetActual getActual() {
        return actual;
    }

    /**
     * Sets the value of the actual property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBudgetActual }
     *     
     */
    public void setActual(ProjectBudgetActual value) {
        this.actual = value;
    }

    /**
     * Gets the value of the uncommitted property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBudgetUncommitted }
     *     
     */
    public ProjectBudgetUncommitted getUncommitted() {
        return uncommitted;
    }

    /**
     * Sets the value of the uncommitted property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBudgetUncommitted }
     *     
     */
    public void setUncommitted(ProjectBudgetUncommitted value) {
        this.uncommitted = value;
    }

}
