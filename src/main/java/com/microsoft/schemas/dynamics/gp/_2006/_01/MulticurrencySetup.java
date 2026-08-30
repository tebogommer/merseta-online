
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for MulticurrencySetup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MulticurrencySetup"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MulticurrencySetupKey" minOccurs="0"/&gt;
 *         &lt;element name="ReportingCurrencyKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="ReportingCurrencyExchangeRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ReportingCurrencyRateCalculationMethod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RateCalculation"/&gt;
 *         &lt;element name="AllowNewRates" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AllowModifyRates" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AllowOverrideExchangeRates" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AllowOverrideRateVariance" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AllowOverrideReportingRate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AverageExchangeRateCalculationMethodDisplay" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RateCalculation"/&gt;
 *         &lt;element name="DefaultFinancialRateTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RateTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="DefaultSalesRateTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RateTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="DefaultPurchasingRateTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RateTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="KeepGeneralLedgerAccountHistory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="LastFinancialRevaluationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastPurchasingRevaluationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastSalesRevaluationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastTransactionRemovalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastSummaryRemovalDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MulticurrencySetup", propOrder = {
    "key",
    "reportingCurrencyKey",
    "reportingCurrencyExchangeRate",
    "reportingCurrencyRateCalculationMethod",
    "allowNewRates",
    "allowModifyRates",
    "allowOverrideExchangeRates",
    "allowOverrideRateVariance",
    "allowOverrideReportingRate",
    "averageExchangeRateCalculationMethodDisplay",
    "defaultFinancialRateTypeKey",
    "defaultSalesRateTypeKey",
    "defaultPurchasingRateTypeKey",
    "keepGeneralLedgerAccountHistory",
    "lastFinancialRevaluationDate",
    "lastPurchasingRevaluationDate",
    "lastSalesRevaluationDate",
    "lastTransactionRemovalDate",
    "lastSummaryRemovalDate"
})
public class MulticurrencySetup
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected MulticurrencySetupKey key;
    @XmlElement(name = "ReportingCurrencyKey")
    protected CurrencyKey reportingCurrencyKey;
    @XmlElement(name = "ReportingCurrencyExchangeRate", required = true, nillable = true)
    protected BigDecimal reportingCurrencyExchangeRate;
    @XmlElement(name = "ReportingCurrencyRateCalculationMethod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected RateCalculation reportingCurrencyRateCalculationMethod;
    @XmlElement(name = "AllowNewRates", required = true, type = Boolean.class, nillable = true)
    protected Boolean allowNewRates;
    @XmlElement(name = "AllowModifyRates", required = true, type = Boolean.class, nillable = true)
    protected Boolean allowModifyRates;
    @XmlElement(name = "AllowOverrideExchangeRates", required = true, type = Boolean.class, nillable = true)
    protected Boolean allowOverrideExchangeRates;
    @XmlElement(name = "AllowOverrideRateVariance", required = true, type = Boolean.class, nillable = true)
    protected Boolean allowOverrideRateVariance;
    @XmlElement(name = "AllowOverrideReportingRate", required = true, type = Boolean.class, nillable = true)
    protected Boolean allowOverrideReportingRate;
    @XmlElement(name = "AverageExchangeRateCalculationMethodDisplay", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected RateCalculation averageExchangeRateCalculationMethodDisplay;
    @XmlElement(name = "DefaultFinancialRateTypeKey")
    protected RateTypeKey defaultFinancialRateTypeKey;
    @XmlElement(name = "DefaultSalesRateTypeKey")
    protected RateTypeKey defaultSalesRateTypeKey;
    @XmlElement(name = "DefaultPurchasingRateTypeKey")
    protected RateTypeKey defaultPurchasingRateTypeKey;
    @XmlElement(name = "KeepGeneralLedgerAccountHistory", required = true, type = Boolean.class, nillable = true)
    protected Boolean keepGeneralLedgerAccountHistory;
    @XmlElement(name = "LastFinancialRevaluationDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastFinancialRevaluationDate;
    @XmlElement(name = "LastPurchasingRevaluationDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastPurchasingRevaluationDate;
    @XmlElement(name = "LastSalesRevaluationDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastSalesRevaluationDate;
    @XmlElement(name = "LastTransactionRemovalDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastTransactionRemovalDate;
    @XmlElement(name = "LastSummaryRemovalDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastSummaryRemovalDate;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link MulticurrencySetupKey }
     *     
     */
    public MulticurrencySetupKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link MulticurrencySetupKey }
     *     
     */
    public void setKey(MulticurrencySetupKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the reportingCurrencyKey property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyKey }
     *     
     */
    public CurrencyKey getReportingCurrencyKey() {
        return reportingCurrencyKey;
    }

    /**
     * Sets the value of the reportingCurrencyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyKey }
     *     
     */
    public void setReportingCurrencyKey(CurrencyKey value) {
        this.reportingCurrencyKey = value;
    }

    /**
     * Gets the value of the reportingCurrencyExchangeRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReportingCurrencyExchangeRate() {
        return reportingCurrencyExchangeRate;
    }

    /**
     * Sets the value of the reportingCurrencyExchangeRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReportingCurrencyExchangeRate(BigDecimal value) {
        this.reportingCurrencyExchangeRate = value;
    }

    /**
     * Gets the value of the reportingCurrencyRateCalculationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link RateCalculation }
     *     
     */
    public RateCalculation getReportingCurrencyRateCalculationMethod() {
        return reportingCurrencyRateCalculationMethod;
    }

    /**
     * Sets the value of the reportingCurrencyRateCalculationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateCalculation }
     *     
     */
    public void setReportingCurrencyRateCalculationMethod(RateCalculation value) {
        this.reportingCurrencyRateCalculationMethod = value;
    }

    /**
     * Gets the value of the allowNewRates property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowNewRates() {
        return allowNewRates;
    }

    /**
     * Sets the value of the allowNewRates property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowNewRates(Boolean value) {
        this.allowNewRates = value;
    }

    /**
     * Gets the value of the allowModifyRates property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowModifyRates() {
        return allowModifyRates;
    }

    /**
     * Sets the value of the allowModifyRates property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowModifyRates(Boolean value) {
        this.allowModifyRates = value;
    }

    /**
     * Gets the value of the allowOverrideExchangeRates property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowOverrideExchangeRates() {
        return allowOverrideExchangeRates;
    }

    /**
     * Sets the value of the allowOverrideExchangeRates property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowOverrideExchangeRates(Boolean value) {
        this.allowOverrideExchangeRates = value;
    }

    /**
     * Gets the value of the allowOverrideRateVariance property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowOverrideRateVariance() {
        return allowOverrideRateVariance;
    }

    /**
     * Sets the value of the allowOverrideRateVariance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowOverrideRateVariance(Boolean value) {
        this.allowOverrideRateVariance = value;
    }

    /**
     * Gets the value of the allowOverrideReportingRate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowOverrideReportingRate() {
        return allowOverrideReportingRate;
    }

    /**
     * Sets the value of the allowOverrideReportingRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowOverrideReportingRate(Boolean value) {
        this.allowOverrideReportingRate = value;
    }

    /**
     * Gets the value of the averageExchangeRateCalculationMethodDisplay property.
     * 
     * @return
     *     possible object is
     *     {@link RateCalculation }
     *     
     */
    public RateCalculation getAverageExchangeRateCalculationMethodDisplay() {
        return averageExchangeRateCalculationMethodDisplay;
    }

    /**
     * Sets the value of the averageExchangeRateCalculationMethodDisplay property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateCalculation }
     *     
     */
    public void setAverageExchangeRateCalculationMethodDisplay(RateCalculation value) {
        this.averageExchangeRateCalculationMethodDisplay = value;
    }

    /**
     * Gets the value of the defaultFinancialRateTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link RateTypeKey }
     *     
     */
    public RateTypeKey getDefaultFinancialRateTypeKey() {
        return defaultFinancialRateTypeKey;
    }

    /**
     * Sets the value of the defaultFinancialRateTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateTypeKey }
     *     
     */
    public void setDefaultFinancialRateTypeKey(RateTypeKey value) {
        this.defaultFinancialRateTypeKey = value;
    }

    /**
     * Gets the value of the defaultSalesRateTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link RateTypeKey }
     *     
     */
    public RateTypeKey getDefaultSalesRateTypeKey() {
        return defaultSalesRateTypeKey;
    }

    /**
     * Sets the value of the defaultSalesRateTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateTypeKey }
     *     
     */
    public void setDefaultSalesRateTypeKey(RateTypeKey value) {
        this.defaultSalesRateTypeKey = value;
    }

    /**
     * Gets the value of the defaultPurchasingRateTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link RateTypeKey }
     *     
     */
    public RateTypeKey getDefaultPurchasingRateTypeKey() {
        return defaultPurchasingRateTypeKey;
    }

    /**
     * Sets the value of the defaultPurchasingRateTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateTypeKey }
     *     
     */
    public void setDefaultPurchasingRateTypeKey(RateTypeKey value) {
        this.defaultPurchasingRateTypeKey = value;
    }

    /**
     * Gets the value of the keepGeneralLedgerAccountHistory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeepGeneralLedgerAccountHistory() {
        return keepGeneralLedgerAccountHistory;
    }

    /**
     * Sets the value of the keepGeneralLedgerAccountHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepGeneralLedgerAccountHistory(Boolean value) {
        this.keepGeneralLedgerAccountHistory = value;
    }

    /**
     * Gets the value of the lastFinancialRevaluationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastFinancialRevaluationDate() {
        return lastFinancialRevaluationDate;
    }

    /**
     * Sets the value of the lastFinancialRevaluationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastFinancialRevaluationDate(XMLGregorianCalendar value) {
        this.lastFinancialRevaluationDate = value;
    }

    /**
     * Gets the value of the lastPurchasingRevaluationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastPurchasingRevaluationDate() {
        return lastPurchasingRevaluationDate;
    }

    /**
     * Sets the value of the lastPurchasingRevaluationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastPurchasingRevaluationDate(XMLGregorianCalendar value) {
        this.lastPurchasingRevaluationDate = value;
    }

    /**
     * Gets the value of the lastSalesRevaluationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastSalesRevaluationDate() {
        return lastSalesRevaluationDate;
    }

    /**
     * Sets the value of the lastSalesRevaluationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastSalesRevaluationDate(XMLGregorianCalendar value) {
        this.lastSalesRevaluationDate = value;
    }

    /**
     * Gets the value of the lastTransactionRemovalDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastTransactionRemovalDate() {
        return lastTransactionRemovalDate;
    }

    /**
     * Sets the value of the lastTransactionRemovalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastTransactionRemovalDate(XMLGregorianCalendar value) {
        this.lastTransactionRemovalDate = value;
    }

    /**
     * Gets the value of the lastSummaryRemovalDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastSummaryRemovalDate() {
        return lastSummaryRemovalDate;
    }

    /**
     * Sets the value of the lastSummaryRemovalDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastSummaryRemovalDate(XMLGregorianCalendar value) {
        this.lastSummaryRemovalDate = value;
    }

}
