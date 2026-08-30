
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EmployeePayCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmployeePayCode"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EmployeePayCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeePayCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="IsInactive" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PayType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeePayTypes"/&gt;
 *         &lt;element name="PayPeriod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CompensationPeriod"/&gt;
 *         &lt;element name="IsTaxable" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PayRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="PayUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="W2BoxesLabels" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeW2Boxes" minOccurs="0"/&gt;
 *         &lt;element name="BasePayRecord" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="PayUnitPeriod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CompensationPeriod"/&gt;
 *         &lt;element name="PayPerPeriod" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="MaxPayPerPeriod" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="TipType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeTipType"/&gt;
 *         &lt;element name="PayAdvance" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="IsReportAsWages" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ApplicableTaxes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeApplicableTaxes" minOccurs="0"/&gt;
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsDataEntry" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PayStep" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeePayStep" minOccurs="0"/&gt;
 *         &lt;element name="FlatTaxRates" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeFlatTaxRates" minOccurs="0"/&gt;
 *         &lt;element name="IsVacationAccrued" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsSickTimeAccrued" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ShiftCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PayFactor" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="WorkersComp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PayAdvanceTaken" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="BasedOnRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="IsPrimaryPayRecord" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmployeePayCode", propOrder = {
    "employeePayCodeKey",
    "isInactive",
    "payType",
    "payPeriod",
    "isTaxable",
    "payRate",
    "payUnit",
    "w2BoxesLabels",
    "basePayRecord",
    "payUnitPeriod",
    "payPerPeriod",
    "maxPayPerPeriod",
    "tipType",
    "payAdvance",
    "isReportAsWages",
    "applicableTaxes",
    "notes",
    "isDataEntry",
    "payStep",
    "flatTaxRates",
    "isVacationAccrued",
    "isSickTimeAccrued",
    "shiftCode",
    "payFactor",
    "workersComp",
    "payAdvanceTaken",
    "basedOnRate",
    "isPrimaryPayRecord"
})
public class EmployeePayCode
    extends BusinessObject
{

    @XmlElement(name = "EmployeePayCodeKey")
    protected EmployeePayCodeKey employeePayCodeKey;
    @XmlElement(name = "IsInactive", required = true, type = Boolean.class, nillable = true)
    protected Boolean isInactive;
    @XmlElement(name = "PayType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected EmployeePayTypes payType;
    @XmlElement(name = "PayPeriod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CompensationPeriod payPeriod;
    @XmlElement(name = "IsTaxable", required = true, type = Boolean.class, nillable = true)
    protected Boolean isTaxable;
    @XmlElement(name = "PayRate", required = true, nillable = true)
    protected BigDecimal payRate;
    @XmlElement(name = "PayUnit")
    protected String payUnit;
    @XmlElement(name = "W2BoxesLabels")
    protected EmployeeW2Boxes w2BoxesLabels;
    @XmlElement(name = "BasePayRecord")
    protected PayCodeKey basePayRecord;
    @XmlElement(name = "PayUnitPeriod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CompensationPeriod payUnitPeriod;
    @XmlElement(name = "PayPerPeriod", required = true, nillable = true)
    protected BigDecimal payPerPeriod;
    @XmlElement(name = "MaxPayPerPeriod", required = true, nillable = true)
    protected BigDecimal maxPayPerPeriod;
    @XmlElement(name = "TipType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected EmployeeTipType tipType;
    @XmlElement(name = "PayAdvance", required = true, nillable = true)
    protected BigDecimal payAdvance;
    @XmlElement(name = "IsReportAsWages", required = true, type = Boolean.class, nillable = true)
    protected Boolean isReportAsWages;
    @XmlElement(name = "ApplicableTaxes")
    protected EmployeeApplicableTaxes applicableTaxes;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "IsDataEntry", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDataEntry;
    @XmlElement(name = "PayStep")
    protected EmployeePayStep payStep;
    @XmlElement(name = "FlatTaxRates")
    protected EmployeeFlatTaxRates flatTaxRates;
    @XmlElement(name = "IsVacationAccrued", required = true, type = Boolean.class, nillable = true)
    protected Boolean isVacationAccrued;
    @XmlElement(name = "IsSickTimeAccrued", required = true, type = Boolean.class, nillable = true)
    protected Boolean isSickTimeAccrued;
    @XmlElement(name = "ShiftCode")
    protected String shiftCode;
    @XmlElement(name = "PayFactor", required = true, nillable = true)
    protected BigDecimal payFactor;
    @XmlElement(name = "WorkersComp")
    protected String workersComp;
    @XmlElement(name = "PayAdvanceTaken", required = true, nillable = true)
    protected BigDecimal payAdvanceTaken;
    @XmlElement(name = "BasedOnRate", required = true, nillable = true)
    protected BigDecimal basedOnRate;
    @XmlElement(name = "IsPrimaryPayRecord")
    protected boolean isPrimaryPayRecord;

    /**
     * Gets the value of the employeePayCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeePayCodeKey }
     *     
     */
    public EmployeePayCodeKey getEmployeePayCodeKey() {
        return employeePayCodeKey;
    }

    /**
     * Sets the value of the employeePayCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeePayCodeKey }
     *     
     */
    public void setEmployeePayCodeKey(EmployeePayCodeKey value) {
        this.employeePayCodeKey = value;
    }

    /**
     * Gets the value of the isInactive property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsInactive() {
        return isInactive;
    }

    /**
     * Sets the value of the isInactive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsInactive(Boolean value) {
        this.isInactive = value;
    }

    /**
     * Gets the value of the payType property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeePayTypes }
     *     
     */
    public EmployeePayTypes getPayType() {
        return payType;
    }

    /**
     * Sets the value of the payType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeePayTypes }
     *     
     */
    public void setPayType(EmployeePayTypes value) {
        this.payType = value;
    }

    /**
     * Gets the value of the payPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link CompensationPeriod }
     *     
     */
    public CompensationPeriod getPayPeriod() {
        return payPeriod;
    }

    /**
     * Sets the value of the payPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompensationPeriod }
     *     
     */
    public void setPayPeriod(CompensationPeriod value) {
        this.payPeriod = value;
    }

    /**
     * Gets the value of the isTaxable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTaxable() {
        return isTaxable;
    }

    /**
     * Sets the value of the isTaxable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTaxable(Boolean value) {
        this.isTaxable = value;
    }

    /**
     * Gets the value of the payRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPayRate() {
        return payRate;
    }

    /**
     * Sets the value of the payRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPayRate(BigDecimal value) {
        this.payRate = value;
    }

    /**
     * Gets the value of the payUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayUnit() {
        return payUnit;
    }

    /**
     * Sets the value of the payUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayUnit(String value) {
        this.payUnit = value;
    }

    /**
     * Gets the value of the w2BoxesLabels property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeW2Boxes }
     *     
     */
    public EmployeeW2Boxes getW2BoxesLabels() {
        return w2BoxesLabels;
    }

    /**
     * Sets the value of the w2BoxesLabels property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeW2Boxes }
     *     
     */
    public void setW2BoxesLabels(EmployeeW2Boxes value) {
        this.w2BoxesLabels = value;
    }

    /**
     * Gets the value of the basePayRecord property.
     * 
     * @return
     *     possible object is
     *     {@link PayCodeKey }
     *     
     */
    public PayCodeKey getBasePayRecord() {
        return basePayRecord;
    }

    /**
     * Sets the value of the basePayRecord property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayCodeKey }
     *     
     */
    public void setBasePayRecord(PayCodeKey value) {
        this.basePayRecord = value;
    }

    /**
     * Gets the value of the payUnitPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link CompensationPeriod }
     *     
     */
    public CompensationPeriod getPayUnitPeriod() {
        return payUnitPeriod;
    }

    /**
     * Sets the value of the payUnitPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompensationPeriod }
     *     
     */
    public void setPayUnitPeriod(CompensationPeriod value) {
        this.payUnitPeriod = value;
    }

    /**
     * Gets the value of the payPerPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPayPerPeriod() {
        return payPerPeriod;
    }

    /**
     * Sets the value of the payPerPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPayPerPeriod(BigDecimal value) {
        this.payPerPeriod = value;
    }

    /**
     * Gets the value of the maxPayPerPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxPayPerPeriod() {
        return maxPayPerPeriod;
    }

    /**
     * Sets the value of the maxPayPerPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxPayPerPeriod(BigDecimal value) {
        this.maxPayPerPeriod = value;
    }

    /**
     * Gets the value of the tipType property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeTipType }
     *     
     */
    public EmployeeTipType getTipType() {
        return tipType;
    }

    /**
     * Sets the value of the tipType property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeTipType }
     *     
     */
    public void setTipType(EmployeeTipType value) {
        this.tipType = value;
    }

    /**
     * Gets the value of the payAdvance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPayAdvance() {
        return payAdvance;
    }

    /**
     * Sets the value of the payAdvance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPayAdvance(BigDecimal value) {
        this.payAdvance = value;
    }

    /**
     * Gets the value of the isReportAsWages property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsReportAsWages() {
        return isReportAsWages;
    }

    /**
     * Sets the value of the isReportAsWages property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsReportAsWages(Boolean value) {
        this.isReportAsWages = value;
    }

    /**
     * Gets the value of the applicableTaxes property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeApplicableTaxes }
     *     
     */
    public EmployeeApplicableTaxes getApplicableTaxes() {
        return applicableTaxes;
    }

    /**
     * Sets the value of the applicableTaxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeApplicableTaxes }
     *     
     */
    public void setApplicableTaxes(EmployeeApplicableTaxes value) {
        this.applicableTaxes = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the isDataEntry property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDataEntry() {
        return isDataEntry;
    }

    /**
     * Sets the value of the isDataEntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDataEntry(Boolean value) {
        this.isDataEntry = value;
    }

    /**
     * Gets the value of the payStep property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeePayStep }
     *     
     */
    public EmployeePayStep getPayStep() {
        return payStep;
    }

    /**
     * Sets the value of the payStep property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeePayStep }
     *     
     */
    public void setPayStep(EmployeePayStep value) {
        this.payStep = value;
    }

    /**
     * Gets the value of the flatTaxRates property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeFlatTaxRates }
     *     
     */
    public EmployeeFlatTaxRates getFlatTaxRates() {
        return flatTaxRates;
    }

    /**
     * Sets the value of the flatTaxRates property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeFlatTaxRates }
     *     
     */
    public void setFlatTaxRates(EmployeeFlatTaxRates value) {
        this.flatTaxRates = value;
    }

    /**
     * Gets the value of the isVacationAccrued property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsVacationAccrued() {
        return isVacationAccrued;
    }

    /**
     * Sets the value of the isVacationAccrued property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsVacationAccrued(Boolean value) {
        this.isVacationAccrued = value;
    }

    /**
     * Gets the value of the isSickTimeAccrued property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsSickTimeAccrued() {
        return isSickTimeAccrued;
    }

    /**
     * Sets the value of the isSickTimeAccrued property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsSickTimeAccrued(Boolean value) {
        this.isSickTimeAccrued = value;
    }

    /**
     * Gets the value of the shiftCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShiftCode() {
        return shiftCode;
    }

    /**
     * Sets the value of the shiftCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShiftCode(String value) {
        this.shiftCode = value;
    }

    /**
     * Gets the value of the payFactor property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPayFactor() {
        return payFactor;
    }

    /**
     * Sets the value of the payFactor property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPayFactor(BigDecimal value) {
        this.payFactor = value;
    }

    /**
     * Gets the value of the workersComp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkersComp() {
        return workersComp;
    }

    /**
     * Sets the value of the workersComp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkersComp(String value) {
        this.workersComp = value;
    }

    /**
     * Gets the value of the payAdvanceTaken property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPayAdvanceTaken() {
        return payAdvanceTaken;
    }

    /**
     * Sets the value of the payAdvanceTaken property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPayAdvanceTaken(BigDecimal value) {
        this.payAdvanceTaken = value;
    }

    /**
     * Gets the value of the basedOnRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBasedOnRate() {
        return basedOnRate;
    }

    /**
     * Sets the value of the basedOnRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBasedOnRate(BigDecimal value) {
        this.basedOnRate = value;
    }

    /**
     * Gets the value of the isPrimaryPayRecord property.
     * 
     */
    public boolean isIsPrimaryPayRecord() {
        return isPrimaryPayRecord;
    }

    /**
     * Sets the value of the isPrimaryPayRecord property.
     * 
     */
    public void setIsPrimaryPayRecord(boolean value) {
        this.isPrimaryPayRecord = value;
    }

}
