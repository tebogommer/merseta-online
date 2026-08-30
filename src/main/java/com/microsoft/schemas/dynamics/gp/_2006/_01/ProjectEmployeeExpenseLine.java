
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectEmployeeExpenseLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectEmployeeExpenseLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectLineBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectEmployeeExpenseLineKey" minOccurs="0"/&gt;
 *         &lt;element name="TaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectTaxBasis"/&gt;
 *         &lt;element name="TaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="TaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BackoutTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="PaymentMethodType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentMethod"/&gt;
 *         &lt;element name="ExpenseType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ExpenseType"/&gt;
 *         &lt;element name="ReimbursableAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Billing" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBilling" minOccurs="0"/&gt;
 *         &lt;element name="Taxes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectEmployeeExpenseLineTax" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectEmployeeExpenseLine", propOrder = {
    "key",
    "taxBasis",
    "taxScheduleKey",
    "taxAmount",
    "backoutTaxAmount",
    "paymentMethodType",
    "expenseType",
    "reimbursableAmount",
    "description",
    "billing",
    "taxes"
})
public class ProjectEmployeeExpenseLine
    extends ProjectLineBase
{

    @XmlElement(name = "Key")
    protected ProjectEmployeeExpenseLineKey key;
    @XmlElement(name = "TaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ProjectTaxBasis taxBasis;
    @XmlElement(name = "TaxScheduleKey")
    protected TaxScheduleKey taxScheduleKey;
    @XmlElement(name = "TaxAmount")
    protected MoneyAmount taxAmount;
    @XmlElement(name = "BackoutTaxAmount")
    protected MoneyAmount backoutTaxAmount;
    @XmlElement(name = "PaymentMethodType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PaymentMethod paymentMethodType;
    @XmlElement(name = "ExpenseType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ExpenseType expenseType;
    @XmlElement(name = "ReimbursableAmount")
    protected MoneyAmount reimbursableAmount;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Billing")
    protected ProjectBilling billing;
    @XmlElement(name = "Taxes")
    protected ArrayOfProjectEmployeeExpenseLineTax taxes;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectEmployeeExpenseLineKey }
     *     
     */
    public ProjectEmployeeExpenseLineKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectEmployeeExpenseLineKey }
     *     
     */
    public void setKey(ProjectEmployeeExpenseLineKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the taxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectTaxBasis }
     *     
     */
    public ProjectTaxBasis getTaxBasis() {
        return taxBasis;
    }

    /**
     * Sets the value of the taxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectTaxBasis }
     *     
     */
    public void setTaxBasis(ProjectTaxBasis value) {
        this.taxBasis = value;
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
     * Gets the value of the backoutTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBackoutTaxAmount() {
        return backoutTaxAmount;
    }

    /**
     * Sets the value of the backoutTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBackoutTaxAmount(MoneyAmount value) {
        this.backoutTaxAmount = value;
    }

    /**
     * Gets the value of the paymentMethodType property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentMethod }
     *     
     */
    public PaymentMethod getPaymentMethodType() {
        return paymentMethodType;
    }

    /**
     * Sets the value of the paymentMethodType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentMethod }
     *     
     */
    public void setPaymentMethodType(PaymentMethod value) {
        this.paymentMethodType = value;
    }

    /**
     * Gets the value of the expenseType property.
     * 
     * @return
     *     possible object is
     *     {@link ExpenseType }
     *     
     */
    public ExpenseType getExpenseType() {
        return expenseType;
    }

    /**
     * Sets the value of the expenseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExpenseType }
     *     
     */
    public void setExpenseType(ExpenseType value) {
        this.expenseType = value;
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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the billing property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBilling }
     *     
     */
    public ProjectBilling getBilling() {
        return billing;
    }

    /**
     * Sets the value of the billing property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBilling }
     *     
     */
    public void setBilling(ProjectBilling value) {
        this.billing = value;
    }

    /**
     * Gets the value of the taxes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectEmployeeExpenseLineTax }
     *     
     */
    public ArrayOfProjectEmployeeExpenseLineTax getTaxes() {
        return taxes;
    }

    /**
     * Sets the value of the taxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectEmployeeExpenseLineTax }
     *     
     */
    public void setTaxes(ArrayOfProjectEmployeeExpenseLineTax value) {
        this.taxes = value;
    }

}
