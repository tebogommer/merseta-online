
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectLineBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectLineBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ProjectKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectKey" minOccurs="0"/&gt;
 *         &lt;element name="CostCategoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CostCategoryKey" minOccurs="0"/&gt;
 *         &lt;element name="Quantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="UofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RoundAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ExtendedCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="OverheadAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="OverheadPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="TotalOverheadAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="MarkupPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="AccruedRevenue" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ReferenceDocumentSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="ProjectContractKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectContractKey" minOccurs="0"/&gt;
 *         &lt;element name="ProfitType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProfitType"/&gt;
 *         &lt;element name="ProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalProfitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="WorkInProgressGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="UnbilledAccountReceivableGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="CostOfGoodsSoldGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="ContraGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="OverheadGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="UnbilledProjectRevenueGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="RoundingGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="OriginalDocumentSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectLineBase", propOrder = {
    "date",
    "projectKey",
    "costCategoryKey",
    "quantity",
    "uofM",
    "roundAmount",
    "unitCost",
    "extendedCost",
    "totalCost",
    "overheadAmount",
    "overheadPercent",
    "totalOverheadAmount",
    "markupPercent",
    "accruedRevenue",
    "referenceDocumentSequenceNumber",
    "projectContractKey",
    "profitType",
    "profitAmount",
    "totalProfitAmount",
    "workInProgressGLAccountKey",
    "unbilledAccountReceivableGLAccountKey",
    "costOfGoodsSoldGLAccountKey",
    "contraGLAccountKey",
    "overheadGLAccountKey",
    "unbilledProjectRevenueGLAccountKey",
    "roundingGLAccountKey",
    "originalDocumentSequenceNumber"
})
@XmlSeeAlso({
    ProjectTimesheetLine.class,
    ProjectEmployeeExpenseLine.class,
    ProjectMiscellaneousLogLine.class
})
public abstract class ProjectLineBase
    extends BusinessObject
{

    @XmlElement(name = "Date", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar date;
    @XmlElement(name = "ProjectKey")
    protected ProjectKey projectKey;
    @XmlElement(name = "CostCategoryKey")
    protected CostCategoryKey costCategoryKey;
    @XmlElement(name = "Quantity")
    protected Quantity quantity;
    @XmlElement(name = "UofM")
    protected String uofM;
    @XmlElement(name = "RoundAmount")
    protected MoneyAmount roundAmount;
    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "ExtendedCost")
    protected MoneyAmount extendedCost;
    @XmlElement(name = "TotalCost")
    protected MoneyAmount totalCost;
    @XmlElement(name = "OverheadAmount")
    protected MoneyAmount overheadAmount;
    @XmlElement(name = "OverheadPercent")
    protected Percent overheadPercent;
    @XmlElement(name = "TotalOverheadAmount")
    protected MoneyAmount totalOverheadAmount;
    @XmlElement(name = "MarkupPercent")
    protected Percent markupPercent;
    @XmlElement(name = "AccruedRevenue")
    protected MoneyAmount accruedRevenue;
    @XmlElement(name = "ReferenceDocumentSequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer referenceDocumentSequenceNumber;
    @XmlElement(name = "ProjectContractKey")
    protected ProjectContractKey projectContractKey;
    @XmlElement(name = "ProfitType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProfitType profitType;
    @XmlElement(name = "ProfitAmount")
    protected MoneyAmount profitAmount;
    @XmlElement(name = "TotalProfitAmount")
    protected MoneyAmount totalProfitAmount;
    @XmlElement(name = "WorkInProgressGLAccountKey")
    protected GLAccountNumberKey workInProgressGLAccountKey;
    @XmlElement(name = "UnbilledAccountReceivableGLAccountKey")
    protected GLAccountNumberKey unbilledAccountReceivableGLAccountKey;
    @XmlElement(name = "CostOfGoodsSoldGLAccountKey")
    protected GLAccountNumberKey costOfGoodsSoldGLAccountKey;
    @XmlElement(name = "ContraGLAccountKey")
    protected GLAccountNumberKey contraGLAccountKey;
    @XmlElement(name = "OverheadGLAccountKey")
    protected GLAccountNumberKey overheadGLAccountKey;
    @XmlElement(name = "UnbilledProjectRevenueGLAccountKey")
    protected GLAccountNumberKey unbilledProjectRevenueGLAccountKey;
    @XmlElement(name = "RoundingGLAccountKey")
    protected GLAccountNumberKey roundingGLAccountKey;
    @XmlElement(name = "OriginalDocumentSequenceNumber", required = true, type = Integer.class, nillable = true)
    protected Integer originalDocumentSequenceNumber;

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDate(XMLGregorianCalendar value) {
        this.date = value;
    }

    /**
     * Gets the value of the projectKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectKey }
     *     
     */
    public ProjectKey getProjectKey() {
        return projectKey;
    }

    /**
     * Sets the value of the projectKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectKey }
     *     
     */
    public void setProjectKey(ProjectKey value) {
        this.projectKey = value;
    }

    /**
     * Gets the value of the costCategoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link CostCategoryKey }
     *     
     */
    public CostCategoryKey getCostCategoryKey() {
        return costCategoryKey;
    }

    /**
     * Sets the value of the costCategoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CostCategoryKey }
     *     
     */
    public void setCostCategoryKey(CostCategoryKey value) {
        this.costCategoryKey = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantity(Quantity value) {
        this.quantity = value;
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
     * Gets the value of the roundAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getRoundAmount() {
        return roundAmount;
    }

    /**
     * Sets the value of the roundAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setRoundAmount(MoneyAmount value) {
        this.roundAmount = value;
    }

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
     * Gets the value of the extendedCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getExtendedCost() {
        return extendedCost;
    }

    /**
     * Sets the value of the extendedCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setExtendedCost(MoneyAmount value) {
        this.extendedCost = value;
    }

    /**
     * Gets the value of the totalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the value of the totalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalCost(MoneyAmount value) {
        this.totalCost = value;
    }

    /**
     * Gets the value of the overheadAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getOverheadAmount() {
        return overheadAmount;
    }

    /**
     * Sets the value of the overheadAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setOverheadAmount(MoneyAmount value) {
        this.overheadAmount = value;
    }

    /**
     * Gets the value of the overheadPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getOverheadPercent() {
        return overheadPercent;
    }

    /**
     * Sets the value of the overheadPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setOverheadPercent(Percent value) {
        this.overheadPercent = value;
    }

    /**
     * Gets the value of the totalOverheadAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalOverheadAmount() {
        return totalOverheadAmount;
    }

    /**
     * Sets the value of the totalOverheadAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalOverheadAmount(MoneyAmount value) {
        this.totalOverheadAmount = value;
    }

    /**
     * Gets the value of the markupPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getMarkupPercent() {
        return markupPercent;
    }

    /**
     * Sets the value of the markupPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setMarkupPercent(Percent value) {
        this.markupPercent = value;
    }

    /**
     * Gets the value of the accruedRevenue property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getAccruedRevenue() {
        return accruedRevenue;
    }

    /**
     * Sets the value of the accruedRevenue property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setAccruedRevenue(MoneyAmount value) {
        this.accruedRevenue = value;
    }

    /**
     * Gets the value of the referenceDocumentSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReferenceDocumentSequenceNumber() {
        return referenceDocumentSequenceNumber;
    }

    /**
     * Sets the value of the referenceDocumentSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReferenceDocumentSequenceNumber(Integer value) {
        this.referenceDocumentSequenceNumber = value;
    }

    /**
     * Gets the value of the projectContractKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectContractKey }
     *     
     */
    public ProjectContractKey getProjectContractKey() {
        return projectContractKey;
    }

    /**
     * Sets the value of the projectContractKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectContractKey }
     *     
     */
    public void setProjectContractKey(ProjectContractKey value) {
        this.projectContractKey = value;
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
     * Gets the value of the profitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getProfitAmount() {
        return profitAmount;
    }

    /**
     * Sets the value of the profitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setProfitAmount(MoneyAmount value) {
        this.profitAmount = value;
    }

    /**
     * Gets the value of the totalProfitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalProfitAmount() {
        return totalProfitAmount;
    }

    /**
     * Sets the value of the totalProfitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalProfitAmount(MoneyAmount value) {
        this.totalProfitAmount = value;
    }

    /**
     * Gets the value of the workInProgressGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getWorkInProgressGLAccountKey() {
        return workInProgressGLAccountKey;
    }

    /**
     * Sets the value of the workInProgressGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setWorkInProgressGLAccountKey(GLAccountNumberKey value) {
        this.workInProgressGLAccountKey = value;
    }

    /**
     * Gets the value of the unbilledAccountReceivableGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getUnbilledAccountReceivableGLAccountKey() {
        return unbilledAccountReceivableGLAccountKey;
    }

    /**
     * Sets the value of the unbilledAccountReceivableGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setUnbilledAccountReceivableGLAccountKey(GLAccountNumberKey value) {
        this.unbilledAccountReceivableGLAccountKey = value;
    }

    /**
     * Gets the value of the costOfGoodsSoldGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getCostOfGoodsSoldGLAccountKey() {
        return costOfGoodsSoldGLAccountKey;
    }

    /**
     * Sets the value of the costOfGoodsSoldGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setCostOfGoodsSoldGLAccountKey(GLAccountNumberKey value) {
        this.costOfGoodsSoldGLAccountKey = value;
    }

    /**
     * Gets the value of the contraGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getContraGLAccountKey() {
        return contraGLAccountKey;
    }

    /**
     * Sets the value of the contraGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setContraGLAccountKey(GLAccountNumberKey value) {
        this.contraGLAccountKey = value;
    }

    /**
     * Gets the value of the overheadGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getOverheadGLAccountKey() {
        return overheadGLAccountKey;
    }

    /**
     * Sets the value of the overheadGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setOverheadGLAccountKey(GLAccountNumberKey value) {
        this.overheadGLAccountKey = value;
    }

    /**
     * Gets the value of the unbilledProjectRevenueGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getUnbilledProjectRevenueGLAccountKey() {
        return unbilledProjectRevenueGLAccountKey;
    }

    /**
     * Sets the value of the unbilledProjectRevenueGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setUnbilledProjectRevenueGLAccountKey(GLAccountNumberKey value) {
        this.unbilledProjectRevenueGLAccountKey = value;
    }

    /**
     * Gets the value of the roundingGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getRoundingGLAccountKey() {
        return roundingGLAccountKey;
    }

    /**
     * Sets the value of the roundingGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setRoundingGLAccountKey(GLAccountNumberKey value) {
        this.roundingGLAccountKey = value;
    }

    /**
     * Gets the value of the originalDocumentSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOriginalDocumentSequenceNumber() {
        return originalDocumentSequenceNumber;
    }

    /**
     * Sets the value of the originalDocumentSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOriginalDocumentSequenceNumber(Integer value) {
        this.originalDocumentSequenceNumber = value;
    }

}
