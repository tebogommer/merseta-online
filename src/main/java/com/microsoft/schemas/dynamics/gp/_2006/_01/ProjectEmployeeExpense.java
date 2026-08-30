
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProjectEmployeeExpense complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectEmployeeExpense"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectHeaderBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectEmployeeExpenseKey" minOccurs="0"/&gt;
 *         &lt;element name="EmployeeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="PersonalDataKeeperEmployeeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="AddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}AddressKey" minOccurs="0"/&gt;
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PaymentTermsKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentTermsKey" minOccurs="0"/&gt;
 *         &lt;element name="ExtendedCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ReimbursableTax" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TradeDiscountAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="FreightTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectTaxBasis"/&gt;
 *         &lt;element name="FreightTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="FreightAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="FreightTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectTaxBasis"/&gt;
 *         &lt;element name="MiscellaneousTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="DocumentAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Amount1099" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Unapplied1099Amount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ChargeAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="TaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ReimbursableAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="DoesPostToPayablesManagement" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="TaxDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IsTaxInvoiceReceived" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsTaxInvoiceRequired" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ShippingMethodKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ShippingMethodKey" minOccurs="0"/&gt;
 *         &lt;element name="Payment" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectPayment" minOccurs="0"/&gt;
 *         &lt;element name="Lines" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectEmployeeExpenseLine" minOccurs="0"/&gt;
 *         &lt;element name="Distributions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectEmployeeExpenseDistribution" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectEmployeeExpense", propOrder = {
    "key",
    "employeeKey",
    "personalDataKeeperEmployeeKey",
    "addressKey",
    "startDate",
    "endDate",
    "paymentTermsKey",
    "extendedCost",
    "reimbursableTax",
    "tradeDiscountAmount",
    "freightTaxBasis",
    "freightTaxScheduleKey",
    "freightAmount",
    "freightTaxAmount",
    "miscellaneousTaxBasis",
    "miscellaneousTaxScheduleKey",
    "miscellaneousAmount",
    "miscellaneousTaxAmount",
    "documentAmount",
    "amount1099",
    "unapplied1099Amount",
    "chargeAmount",
    "taxScheduleKey",
    "taxAmount",
    "reimbursableAmount",
    "doesPostToPayablesManagement",
    "taxDate",
    "isTaxInvoiceReceived",
    "isTaxInvoiceRequired",
    "shippingMethodKey",
    "payment",
    "lines",
    "distributions"
})
public class ProjectEmployeeExpense
    extends ProjectHeaderBase
{

    @XmlElement(name = "Key")
    protected ProjectEmployeeExpenseKey key;
    @XmlElement(name = "EmployeeKey")
    protected EmployeeKey employeeKey;
    @XmlElement(name = "PersonalDataKeeperEmployeeKey")
    protected EmployeeKey personalDataKeeperEmployeeKey;
    @XmlElement(name = "AddressKey")
    protected AddressKey addressKey;
    @XmlElement(name = "StartDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "EndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "PaymentTermsKey")
    protected PaymentTermsKey paymentTermsKey;
    @XmlElement(name = "ExtendedCost")
    protected MoneyAmount extendedCost;
    @XmlElement(name = "ReimbursableTax")
    protected MoneyAmount reimbursableTax;
    @XmlElement(name = "TradeDiscountAmount")
    protected MoneyAmount tradeDiscountAmount;
    @XmlElement(name = "FreightTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectTaxBasis freightTaxBasis;
    @XmlElement(name = "FreightTaxScheduleKey")
    protected TaxScheduleKey freightTaxScheduleKey;
    @XmlElement(name = "FreightAmount")
    protected MoneyAmount freightAmount;
    @XmlElement(name = "FreightTaxAmount")
    protected MoneyAmount freightTaxAmount;
    @XmlElement(name = "MiscellaneousTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectTaxBasis miscellaneousTaxBasis;
    @XmlElement(name = "MiscellaneousTaxScheduleKey")
    protected TaxScheduleKey miscellaneousTaxScheduleKey;
    @XmlElement(name = "MiscellaneousAmount")
    protected MoneyAmount miscellaneousAmount;
    @XmlElement(name = "MiscellaneousTaxAmount")
    protected MoneyAmount miscellaneousTaxAmount;
    @XmlElement(name = "DocumentAmount")
    protected MoneyAmount documentAmount;
    @XmlElement(name = "Amount1099")
    protected MoneyAmount amount1099;
    @XmlElement(name = "Unapplied1099Amount")
    protected MoneyAmount unapplied1099Amount;
    @XmlElement(name = "ChargeAmount")
    protected MoneyAmount chargeAmount;
    @XmlElement(name = "TaxScheduleKey")
    protected TaxScheduleKey taxScheduleKey;
    @XmlElement(name = "TaxAmount")
    protected MoneyAmount taxAmount;
    @XmlElement(name = "ReimbursableAmount")
    protected MoneyAmount reimbursableAmount;
    @XmlElement(name = "DoesPostToPayablesManagement", required = true, type = Boolean.class, nillable = true)
    protected Boolean doesPostToPayablesManagement;
    @XmlElement(name = "TaxDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar taxDate;
    @XmlElement(name = "IsTaxInvoiceReceived", required = true, type = Boolean.class, nillable = true)
    protected Boolean isTaxInvoiceReceived;
    @XmlElement(name = "IsTaxInvoiceRequired", required = true, type = Boolean.class, nillable = true)
    protected Boolean isTaxInvoiceRequired;
    @XmlElement(name = "ShippingMethodKey")
    protected ShippingMethodKey shippingMethodKey;
    @XmlElement(name = "Payment")
    protected ProjectPayment payment;
    @XmlElement(name = "Lines")
    protected ArrayOfProjectEmployeeExpenseLine lines;
    @XmlElement(name = "Distributions")
    protected ArrayOfProjectEmployeeExpenseDistribution distributions;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectEmployeeExpenseKey }
     *     
     */
    public ProjectEmployeeExpenseKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectEmployeeExpenseKey }
     *     
     */
    public void setKey(ProjectEmployeeExpenseKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the employeeKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeKey }
     *     
     */
    public EmployeeKey getEmployeeKey() {
        return employeeKey;
    }

    /**
     * Sets the value of the employeeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeKey }
     *     
     */
    public void setEmployeeKey(EmployeeKey value) {
        this.employeeKey = value;
    }

    /**
     * Gets the value of the personalDataKeeperEmployeeKey property.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeKey }
     *     
     */
    public EmployeeKey getPersonalDataKeeperEmployeeKey() {
        return personalDataKeeperEmployeeKey;
    }

    /**
     * Sets the value of the personalDataKeeperEmployeeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeKey }
     *     
     */
    public void setPersonalDataKeeperEmployeeKey(EmployeeKey value) {
        this.personalDataKeeperEmployeeKey = value;
    }

    /**
     * Gets the value of the addressKey property.
     * 
     * @return
     *     possible object is
     *     {@link AddressKey }
     *     
     */
    public AddressKey getAddressKey() {
        return addressKey;
    }

    /**
     * Sets the value of the addressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressKey }
     *     
     */
    public void setAddressKey(AddressKey value) {
        this.addressKey = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
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
     * Gets the value of the paymentTermsKey property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTermsKey }
     *     
     */
    public PaymentTermsKey getPaymentTermsKey() {
        return paymentTermsKey;
    }

    /**
     * Sets the value of the paymentTermsKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTermsKey }
     *     
     */
    public void setPaymentTermsKey(PaymentTermsKey value) {
        this.paymentTermsKey = value;
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
     * Gets the value of the reimbursableTax property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getReimbursableTax() {
        return reimbursableTax;
    }

    /**
     * Sets the value of the reimbursableTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setReimbursableTax(MoneyAmount value) {
        this.reimbursableTax = value;
    }

    /**
     * Gets the value of the tradeDiscountAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTradeDiscountAmount() {
        return tradeDiscountAmount;
    }

    /**
     * Sets the value of the tradeDiscountAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTradeDiscountAmount(MoneyAmount value) {
        this.tradeDiscountAmount = value;
    }

    /**
     * Gets the value of the freightTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectTaxBasis }
     *     
     */
    public ProjectTaxBasis getFreightTaxBasis() {
        return freightTaxBasis;
    }

    /**
     * Sets the value of the freightTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectTaxBasis }
     *     
     */
    public void setFreightTaxBasis(ProjectTaxBasis value) {
        this.freightTaxBasis = value;
    }

    /**
     * Gets the value of the freightTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getFreightTaxScheduleKey() {
        return freightTaxScheduleKey;
    }

    /**
     * Sets the value of the freightTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setFreightTaxScheduleKey(TaxScheduleKey value) {
        this.freightTaxScheduleKey = value;
    }

    /**
     * Gets the value of the freightAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getFreightAmount() {
        return freightAmount;
    }

    /**
     * Sets the value of the freightAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setFreightAmount(MoneyAmount value) {
        this.freightAmount = value;
    }

    /**
     * Gets the value of the freightTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getFreightTaxAmount() {
        return freightTaxAmount;
    }

    /**
     * Sets the value of the freightTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setFreightTaxAmount(MoneyAmount value) {
        this.freightTaxAmount = value;
    }

    /**
     * Gets the value of the miscellaneousTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectTaxBasis }
     *     
     */
    public ProjectTaxBasis getMiscellaneousTaxBasis() {
        return miscellaneousTaxBasis;
    }

    /**
     * Sets the value of the miscellaneousTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectTaxBasis }
     *     
     */
    public void setMiscellaneousTaxBasis(ProjectTaxBasis value) {
        this.miscellaneousTaxBasis = value;
    }

    /**
     * Gets the value of the miscellaneousTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getMiscellaneousTaxScheduleKey() {
        return miscellaneousTaxScheduleKey;
    }

    /**
     * Sets the value of the miscellaneousTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setMiscellaneousTaxScheduleKey(TaxScheduleKey value) {
        this.miscellaneousTaxScheduleKey = value;
    }

    /**
     * Gets the value of the miscellaneousAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getMiscellaneousAmount() {
        return miscellaneousAmount;
    }

    /**
     * Sets the value of the miscellaneousAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setMiscellaneousAmount(MoneyAmount value) {
        this.miscellaneousAmount = value;
    }

    /**
     * Gets the value of the miscellaneousTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getMiscellaneousTaxAmount() {
        return miscellaneousTaxAmount;
    }

    /**
     * Sets the value of the miscellaneousTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setMiscellaneousTaxAmount(MoneyAmount value) {
        this.miscellaneousTaxAmount = value;
    }

    /**
     * Gets the value of the documentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDocumentAmount() {
        return documentAmount;
    }

    /**
     * Sets the value of the documentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDocumentAmount(MoneyAmount value) {
        this.documentAmount = value;
    }

    /**
     * Gets the value of the amount1099 property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getAmount1099() {
        return amount1099;
    }

    /**
     * Sets the value of the amount1099 property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setAmount1099(MoneyAmount value) {
        this.amount1099 = value;
    }

    /**
     * Gets the value of the unapplied1099Amount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getUnapplied1099Amount() {
        return unapplied1099Amount;
    }

    /**
     * Sets the value of the unapplied1099Amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setUnapplied1099Amount(MoneyAmount value) {
        this.unapplied1099Amount = value;
    }

    /**
     * Gets the value of the chargeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getChargeAmount() {
        return chargeAmount;
    }

    /**
     * Sets the value of the chargeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setChargeAmount(MoneyAmount value) {
        this.chargeAmount = value;
    }

    /**
     * Gets the value of the taxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getTaxScheduleKey() {
        return taxScheduleKey;
    }

    /**
     * Sets the value of the taxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setTaxScheduleKey(TaxScheduleKey value) {
        this.taxScheduleKey = value;
    }

    /**
     * Gets the value of the taxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxAmount(MoneyAmount value) {
        this.taxAmount = value;
    }

    /**
     * Gets the value of the reimbursableAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getReimbursableAmount() {
        return reimbursableAmount;
    }

    /**
     * Sets the value of the reimbursableAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setReimbursableAmount(MoneyAmount value) {
        this.reimbursableAmount = value;
    }

    /**
     * Gets the value of the doesPostToPayablesManagement property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDoesPostToPayablesManagement() {
        return doesPostToPayablesManagement;
    }

    /**
     * Sets the value of the doesPostToPayablesManagement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDoesPostToPayablesManagement(Boolean value) {
        this.doesPostToPayablesManagement = value;
    }

    /**
     * Gets the value of the taxDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTaxDate() {
        return taxDate;
    }

    /**
     * Sets the value of the taxDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTaxDate(XMLGregorianCalendar value) {
        this.taxDate = value;
    }

    /**
     * Gets the value of the isTaxInvoiceReceived property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTaxInvoiceReceived() {
        return isTaxInvoiceReceived;
    }

    /**
     * Sets the value of the isTaxInvoiceReceived property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTaxInvoiceReceived(Boolean value) {
        this.isTaxInvoiceReceived = value;
    }

    /**
     * Gets the value of the isTaxInvoiceRequired property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTaxInvoiceRequired() {
        return isTaxInvoiceRequired;
    }

    /**
     * Sets the value of the isTaxInvoiceRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTaxInvoiceRequired(Boolean value) {
        this.isTaxInvoiceRequired = value;
    }

    /**
     * Gets the value of the shippingMethodKey property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingMethodKey }
     *     
     */
    public ShippingMethodKey getShippingMethodKey() {
        return shippingMethodKey;
    }

    /**
     * Sets the value of the shippingMethodKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingMethodKey }
     *     
     */
    public void setShippingMethodKey(ShippingMethodKey value) {
        this.shippingMethodKey = value;
    }

    /**
     * Gets the value of the payment property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectPayment }
     *     
     */
    public ProjectPayment getPayment() {
        return payment;
    }

    /**
     * Sets the value of the payment property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectPayment }
     *     
     */
    public void setPayment(ProjectPayment value) {
        this.payment = value;
    }

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectEmployeeExpenseLine }
     *     
     */
    public ArrayOfProjectEmployeeExpenseLine getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectEmployeeExpenseLine }
     *     
     */
    public void setLines(ArrayOfProjectEmployeeExpenseLine value) {
        this.lines = value;
    }

    /**
     * Gets the value of the distributions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectEmployeeExpenseDistribution }
     *     
     */
    public ArrayOfProjectEmployeeExpenseDistribution getDistributions() {
        return distributions;
    }

    /**
     * Sets the value of the distributions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectEmployeeExpenseDistribution }
     *     
     */
    public void setDistributions(ArrayOfProjectEmployeeExpenseDistribution value) {
        this.distributions = value;
    }

}
