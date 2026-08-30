
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceDocumentCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceDocumentCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CustomerId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CustomerName" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CustomerPONumber" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="OfficeId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfServiceTransactionState" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceDocumentCriteria", propOrder = {
    "id",
    "customerId",
    "customerName",
    "customerPONumber",
    "officeId",
    "transactionState"
})
@XmlSeeAlso({
    ReturnMaterialAuthorizationCriteria.class,
    ServiceCallDocumentCriteria.class
})
public abstract class ServiceDocumentCriteria
    extends Criteria
{

    @XmlElement(name = "Id")
    protected LikeRestrictionOfString id;
    @XmlElement(name = "CustomerId")
    protected LikeRestrictionOfString customerId;
    @XmlElement(name = "CustomerName")
    protected LikeRestrictionOfString customerName;
    @XmlElement(name = "CustomerPONumber")
    protected LikeRestrictionOfString customerPONumber;
    @XmlElement(name = "OfficeId")
    protected LikeRestrictionOfString officeId;
    @XmlElement(name = "TransactionState")
    protected ListRestrictionOfNullableOfServiceTransactionState transactionState;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setId(LikeRestrictionOfString value) {
        this.id = value;
    }

    /**
     * Gets the value of the customerId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setCustomerId(LikeRestrictionOfString value) {
        this.customerId = value;
    }

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setCustomerName(LikeRestrictionOfString value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the customerPONumber property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getCustomerPONumber() {
        return customerPONumber;
    }

    /**
     * Sets the value of the customerPONumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setCustomerPONumber(LikeRestrictionOfString value) {
        this.customerPONumber = value;
    }

    /**
     * Gets the value of the officeId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getOfficeId() {
        return officeId;
    }

    /**
     * Sets the value of the officeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setOfficeId(LikeRestrictionOfString value) {
        this.officeId = value;
    }

    /**
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfServiceTransactionState }
     *     
     */
    public ListRestrictionOfNullableOfServiceTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfServiceTransactionState }
     *     
     */
    public void setTransactionState(ListRestrictionOfNullableOfServiceTransactionState value) {
        this.transactionState = value;
    }

}
