
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceDocumentSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceDocumentSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceDocumentKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="CustomerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CustomerPONumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OfficeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}OfficeKey" minOccurs="0"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceTransactionState"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceDocumentSummary", propOrder = {
    "key",
    "customerKey",
    "customerName",
    "customerPONumber",
    "officeKey",
    "transactionState"
})
@XmlSeeAlso({
    ServiceQuoteSummary.class,
    ReturnMaterialAuthorizationSummary.class,
    ServiceCallSummary.class
})
public abstract class ServiceDocumentSummary {

    @XmlElement(name = "Key")
    protected ServiceDocumentKey key;
    @XmlElement(name = "CustomerKey")
    protected CustomerKey customerKey;
    @XmlElement(name = "CustomerName")
    protected String customerName;
    @XmlElement(name = "CustomerPONumber")
    protected String customerPONumber;
    @XmlElement(name = "OfficeKey")
    protected OfficeKey officeKey;
    @XmlElement(name = "TransactionState", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ServiceTransactionState transactionState;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceDocumentKey }
     *     
     */
    public ServiceDocumentKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceDocumentKey }
     *     
     */
    public void setKey(ServiceDocumentKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the customerKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getCustomerKey() {
        return customerKey;
    }

    /**
     * Sets the value of the customerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setCustomerKey(CustomerKey value) {
        this.customerKey = value;
    }

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the customerPONumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerPONumber() {
        return customerPONumber;
    }

    /**
     * Sets the value of the customerPONumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerPONumber(String value) {
        this.customerPONumber = value;
    }

    /**
     * Gets the value of the officeKey property.
     * 
     * @return
     *     possible object is
     *     {@link OfficeKey }
     *     
     */
    public OfficeKey getOfficeKey() {
        return officeKey;
    }

    /**
     * Sets the value of the officeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link OfficeKey }
     *     
     */
    public void setOfficeKey(OfficeKey value) {
        this.officeKey = value;
    }

    /**
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceTransactionState }
     *     
     */
    public ServiceTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceTransactionState }
     *     
     */
    public void setTransactionState(ServiceTransactionState value) {
        this.transactionState = value;
    }

}
