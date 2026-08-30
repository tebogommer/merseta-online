
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesOrder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PaymentAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="DepositAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ShippingProcessStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ShippingProcessStatus"/&gt;
 *         &lt;element name="IsRepeating" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DocumentFrequency" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Frequency"/&gt;
 *         &lt;element name="TimesToRepeat" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DaysToIncrement" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="WorkflowPriority" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WorkflowPriority"/&gt;
 *         &lt;element name="Lines" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesOrderLine" minOccurs="0"/&gt;
 *         &lt;element name="Payments" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesPayment" minOccurs="0"/&gt;
 *         &lt;element name="Workflows" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfWorkflow" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesOrder", propOrder = {
    "paymentAmount",
    "depositAmount",
    "shippingProcessStatus",
    "isRepeating",
    "documentFrequency",
    "timesToRepeat",
    "daysToIncrement",
    "workflowPriority",
    "lines",
    "payments",
    "workflows"
})
public class SalesOrder
    extends SalesDocument
{

    @XmlElement(name = "PaymentAmount")
    protected MoneyAmount paymentAmount;
    @XmlElement(name = "DepositAmount")
    protected MoneyAmount depositAmount;
    @XmlElement(name = "ShippingProcessStatus", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ShippingProcessStatus shippingProcessStatus;
    @XmlElement(name = "IsRepeating", required = true, type = Boolean.class, nillable = true)
    protected Boolean isRepeating;
    @XmlElement(name = "DocumentFrequency", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected Frequency documentFrequency;
    @XmlElement(name = "TimesToRepeat", required = true, type = Integer.class, nillable = true)
    protected Integer timesToRepeat;
    @XmlElement(name = "DaysToIncrement", required = true, type = Integer.class, nillable = true)
    protected Integer daysToIncrement;
    @XmlElement(name = "WorkflowPriority", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected WorkflowPriority workflowPriority;
    @XmlElement(name = "Lines")
    protected ArrayOfSalesOrderLine lines;
    @XmlElement(name = "Payments")
    protected ArrayOfSalesPayment payments;
    @XmlElement(name = "Workflows")
    protected ArrayOfWorkflow workflows;

    /**
     * Gets the value of the paymentAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * Sets the value of the paymentAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPaymentAmount(MoneyAmount value) {
        this.paymentAmount = value;
    }

    /**
     * Gets the value of the depositAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDepositAmount() {
        return depositAmount;
    }

    /**
     * Sets the value of the depositAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDepositAmount(MoneyAmount value) {
        this.depositAmount = value;
    }

    /**
     * Gets the value of the shippingProcessStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingProcessStatus }
     *     
     */
    public ShippingProcessStatus getShippingProcessStatus() {
        return shippingProcessStatus;
    }

    /**
     * Sets the value of the shippingProcessStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingProcessStatus }
     *     
     */
    public void setShippingProcessStatus(ShippingProcessStatus value) {
        this.shippingProcessStatus = value;
    }

    /**
     * Gets the value of the isRepeating property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsRepeating() {
        return isRepeating;
    }

    /**
     * Sets the value of the isRepeating property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsRepeating(Boolean value) {
        this.isRepeating = value;
    }

    /**
     * Gets the value of the documentFrequency property.
     * 
     * @return
     *     possible object is
     *     {@link Frequency }
     *     
     */
    public Frequency getDocumentFrequency() {
        return documentFrequency;
    }

    /**
     * Sets the value of the documentFrequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link Frequency }
     *     
     */
    public void setDocumentFrequency(Frequency value) {
        this.documentFrequency = value;
    }

    /**
     * Gets the value of the timesToRepeat property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTimesToRepeat() {
        return timesToRepeat;
    }

    /**
     * Sets the value of the timesToRepeat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimesToRepeat(Integer value) {
        this.timesToRepeat = value;
    }

    /**
     * Gets the value of the daysToIncrement property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDaysToIncrement() {
        return daysToIncrement;
    }

    /**
     * Sets the value of the daysToIncrement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDaysToIncrement(Integer value) {
        this.daysToIncrement = value;
    }

    /**
     * Gets the value of the workflowPriority property.
     * 
     * @return
     *     possible object is
     *     {@link WorkflowPriority }
     *     
     */
    public WorkflowPriority getWorkflowPriority() {
        return workflowPriority;
    }

    /**
     * Sets the value of the workflowPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link WorkflowPriority }
     *     
     */
    public void setWorkflowPriority(WorkflowPriority value) {
        this.workflowPriority = value;
    }

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesOrderLine }
     *     
     */
    public ArrayOfSalesOrderLine getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesOrderLine }
     *     
     */
    public void setLines(ArrayOfSalesOrderLine value) {
        this.lines = value;
    }

    /**
     * Gets the value of the payments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesPayment }
     *     
     */
    public ArrayOfSalesPayment getPayments() {
        return payments;
    }

    /**
     * Sets the value of the payments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesPayment }
     *     
     */
    public void setPayments(ArrayOfSalesPayment value) {
        this.payments = value;
    }

    /**
     * Gets the value of the workflows property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfWorkflow }
     *     
     */
    public ArrayOfWorkflow getWorkflows() {
        return workflows;
    }

    /**
     * Sets the value of the workflows property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfWorkflow }
     *     
     */
    public void setWorkflows(ArrayOfWorkflow value) {
        this.workflows = value;
    }

}
