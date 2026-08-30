
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxDetail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxDetail"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TaxDetailKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxDetailKey" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxDetailType"/&gt;
 *         &lt;element name="TaxDetailDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Account" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="TaxIdNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailBase" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxDetailBase"/&gt;
 *         &lt;element name="TaxDetailPercent" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="TaxDetailAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailRounding" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxDetailRounding"/&gt;
 *         &lt;element name="TaxDetailBasedOnDetailKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxDetailKey" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailTaxableMinimum" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailTaxableMaximum" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailTaxMinimum" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailTaxMaximum" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailRangeType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxDetailRangeType"/&gt;
 *         &lt;element name="TaxDetailBaseQualifiers" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxDetailBaseQualifers"/&gt;
 *         &lt;element name="IsTaxDetailTaxable" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsTaxDetailPrintOnDocuments" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="TaxDetailPrintCharacter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxDetailTaxablePercent" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxDetail", propOrder = {
    "taxDetailKey",
    "taxDetailType",
    "taxDetailDescription",
    "account",
    "taxIdNumber",
    "taxDetailBase",
    "taxDetailPercent",
    "taxDetailAmount",
    "taxDetailRounding",
    "taxDetailBasedOnDetailKey",
    "taxDetailTaxableMinimum",
    "taxDetailTaxableMaximum",
    "taxDetailTaxMinimum",
    "taxDetailTaxMaximum",
    "taxDetailRangeType",
    "taxDetailBaseQualifiers",
    "isTaxDetailTaxable",
    "isTaxDetailPrintOnDocuments",
    "taxDetailPrintCharacter",
    "taxDetailTaxablePercent"
})
public class TaxDetail
    extends BusinessObject
{

    @XmlElement(name = "TaxDetailKey")
    protected TaxDetailKey taxDetailKey;
    @XmlElement(name = "TaxDetailType", required = true)
    @XmlSchemaType(name = "string")
    protected TaxDetailType taxDetailType;
    @XmlElement(name = "TaxDetailDescription")
    protected String taxDetailDescription;
    @XmlElement(name = "Account")
    protected GLAccountNumberKey account;
    @XmlElement(name = "TaxIdNumber")
    protected String taxIdNumber;
    @XmlElement(name = "TaxDetailBase", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected TaxDetailBase taxDetailBase;
    @XmlElement(name = "TaxDetailPercent", required = true, nillable = true)
    protected BigDecimal taxDetailPercent;
    @XmlElement(name = "TaxDetailAmount")
    protected MoneyAmount taxDetailAmount;
    @XmlElement(name = "TaxDetailRounding", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected TaxDetailRounding taxDetailRounding;
    @XmlElement(name = "TaxDetailBasedOnDetailKey")
    protected TaxDetailKey taxDetailBasedOnDetailKey;
    @XmlElement(name = "TaxDetailTaxableMinimum")
    protected MoneyAmount taxDetailTaxableMinimum;
    @XmlElement(name = "TaxDetailTaxableMaximum")
    protected MoneyAmount taxDetailTaxableMaximum;
    @XmlElement(name = "TaxDetailTaxMinimum")
    protected MoneyAmount taxDetailTaxMinimum;
    @XmlElement(name = "TaxDetailTaxMaximum")
    protected MoneyAmount taxDetailTaxMaximum;
    @XmlElement(name = "TaxDetailRangeType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected TaxDetailRangeType taxDetailRangeType;
    @XmlElement(name = "TaxDetailBaseQualifiers", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected TaxDetailBaseQualifers taxDetailBaseQualifiers;
    @XmlElement(name = "IsTaxDetailTaxable", required = true, type = Boolean.class, nillable = true)
    protected Boolean isTaxDetailTaxable;
    @XmlElement(name = "IsTaxDetailPrintOnDocuments", required = true, type = Boolean.class, nillable = true)
    protected Boolean isTaxDetailPrintOnDocuments;
    @XmlElement(name = "TaxDetailPrintCharacter")
    protected String taxDetailPrintCharacter;
    @XmlElement(name = "TaxDetailTaxablePercent", required = true, nillable = true)
    protected BigDecimal taxDetailTaxablePercent;

    /**
     * Gets the value of the taxDetailKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxDetailKey }
     *     
     */
    public TaxDetailKey getTaxDetailKey() {
        return taxDetailKey;
    }

    /**
     * Sets the value of the taxDetailKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxDetailKey }
     *     
     */
    public void setTaxDetailKey(TaxDetailKey value) {
        this.taxDetailKey = value;
    }

    /**
     * Gets the value of the taxDetailType property.
     * 
     * @return
     *     possible object is
     *     {@link TaxDetailType }
     *     
     */
    public TaxDetailType getTaxDetailType() {
        return taxDetailType;
    }

    /**
     * Sets the value of the taxDetailType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxDetailType }
     *     
     */
    public void setTaxDetailType(TaxDetailType value) {
        this.taxDetailType = value;
    }

    /**
     * Gets the value of the taxDetailDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxDetailDescription() {
        return taxDetailDescription;
    }

    /**
     * Sets the value of the taxDetailDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxDetailDescription(String value) {
        this.taxDetailDescription = value;
    }

    /**
     * Gets the value of the account property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getAccount() {
        return account;
    }

    /**
     * Sets the value of the account property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setAccount(GLAccountNumberKey value) {
        this.account = value;
    }

    /**
     * Gets the value of the taxIdNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxIdNumber() {
        return taxIdNumber;
    }

    /**
     * Sets the value of the taxIdNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxIdNumber(String value) {
        this.taxIdNumber = value;
    }

    /**
     * Gets the value of the taxDetailBase property.
     * 
     * @return
     *     possible object is
     *     {@link TaxDetailBase }
     *     
     */
    public TaxDetailBase getTaxDetailBase() {
        return taxDetailBase;
    }

    /**
     * Sets the value of the taxDetailBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxDetailBase }
     *     
     */
    public void setTaxDetailBase(TaxDetailBase value) {
        this.taxDetailBase = value;
    }

    /**
     * Gets the value of the taxDetailPercent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTaxDetailPercent() {
        return taxDetailPercent;
    }

    /**
     * Sets the value of the taxDetailPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTaxDetailPercent(BigDecimal value) {
        this.taxDetailPercent = value;
    }

    /**
     * Gets the value of the taxDetailAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxDetailAmount() {
        return taxDetailAmount;
    }

    /**
     * Sets the value of the taxDetailAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxDetailAmount(MoneyAmount value) {
        this.taxDetailAmount = value;
    }

    /**
     * Gets the value of the taxDetailRounding property.
     * 
     * @return
     *     possible object is
     *     {@link TaxDetailRounding }
     *     
     */
    public TaxDetailRounding getTaxDetailRounding() {
        return taxDetailRounding;
    }

    /**
     * Sets the value of the taxDetailRounding property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxDetailRounding }
     *     
     */
    public void setTaxDetailRounding(TaxDetailRounding value) {
        this.taxDetailRounding = value;
    }

    /**
     * Gets the value of the taxDetailBasedOnDetailKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxDetailKey }
     *     
     */
    public TaxDetailKey getTaxDetailBasedOnDetailKey() {
        return taxDetailBasedOnDetailKey;
    }

    /**
     * Sets the value of the taxDetailBasedOnDetailKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxDetailKey }
     *     
     */
    public void setTaxDetailBasedOnDetailKey(TaxDetailKey value) {
        this.taxDetailBasedOnDetailKey = value;
    }

    /**
     * Gets the value of the taxDetailTaxableMinimum property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxDetailTaxableMinimum() {
        return taxDetailTaxableMinimum;
    }

    /**
     * Sets the value of the taxDetailTaxableMinimum property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxDetailTaxableMinimum(MoneyAmount value) {
        this.taxDetailTaxableMinimum = value;
    }

    /**
     * Gets the value of the taxDetailTaxableMaximum property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxDetailTaxableMaximum() {
        return taxDetailTaxableMaximum;
    }

    /**
     * Sets the value of the taxDetailTaxableMaximum property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxDetailTaxableMaximum(MoneyAmount value) {
        this.taxDetailTaxableMaximum = value;
    }

    /**
     * Gets the value of the taxDetailTaxMinimum property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxDetailTaxMinimum() {
        return taxDetailTaxMinimum;
    }

    /**
     * Sets the value of the taxDetailTaxMinimum property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxDetailTaxMinimum(MoneyAmount value) {
        this.taxDetailTaxMinimum = value;
    }

    /**
     * Gets the value of the taxDetailTaxMaximum property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxDetailTaxMaximum() {
        return taxDetailTaxMaximum;
    }

    /**
     * Sets the value of the taxDetailTaxMaximum property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxDetailTaxMaximum(MoneyAmount value) {
        this.taxDetailTaxMaximum = value;
    }

    /**
     * Gets the value of the taxDetailRangeType property.
     * 
     * @return
     *     possible object is
     *     {@link TaxDetailRangeType }
     *     
     */
    public TaxDetailRangeType getTaxDetailRangeType() {
        return taxDetailRangeType;
    }

    /**
     * Sets the value of the taxDetailRangeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxDetailRangeType }
     *     
     */
    public void setTaxDetailRangeType(TaxDetailRangeType value) {
        this.taxDetailRangeType = value;
    }

    /**
     * Gets the value of the taxDetailBaseQualifiers property.
     * 
     * @return
     *     possible object is
     *     {@link TaxDetailBaseQualifers }
     *     
     */
    public TaxDetailBaseQualifers getTaxDetailBaseQualifiers() {
        return taxDetailBaseQualifiers;
    }

    /**
     * Sets the value of the taxDetailBaseQualifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxDetailBaseQualifers }
     *     
     */
    public void setTaxDetailBaseQualifiers(TaxDetailBaseQualifers value) {
        this.taxDetailBaseQualifiers = value;
    }

    /**
     * Gets the value of the isTaxDetailTaxable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTaxDetailTaxable() {
        return isTaxDetailTaxable;
    }

    /**
     * Sets the value of the isTaxDetailTaxable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTaxDetailTaxable(Boolean value) {
        this.isTaxDetailTaxable = value;
    }

    /**
     * Gets the value of the isTaxDetailPrintOnDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTaxDetailPrintOnDocuments() {
        return isTaxDetailPrintOnDocuments;
    }

    /**
     * Sets the value of the isTaxDetailPrintOnDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTaxDetailPrintOnDocuments(Boolean value) {
        this.isTaxDetailPrintOnDocuments = value;
    }

    /**
     * Gets the value of the taxDetailPrintCharacter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxDetailPrintCharacter() {
        return taxDetailPrintCharacter;
    }

    /**
     * Sets the value of the taxDetailPrintCharacter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxDetailPrintCharacter(String value) {
        this.taxDetailPrintCharacter = value;
    }

    /**
     * Gets the value of the taxDetailTaxablePercent property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTaxDetailTaxablePercent() {
        return taxDetailTaxablePercent;
    }

    /**
     * Sets the value of the taxDetailTaxablePercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTaxDetailTaxablePercent(BigDecimal value) {
        this.taxDetailTaxablePercent = value;
    }

}
