
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalespersonCommissionsCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalespersonCommissionsCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfSalesCommissionTransactionState" minOccurs="0"/&gt;
 *         &lt;element name="DocumentType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfSalespersonCommissionsDocumentType" minOccurs="0"/&gt;
 *         &lt;element name="DocumentNumber" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="CustomerId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="SalespersonId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="SalesTerritoryId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Scope" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonScope"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalespersonCommissionsCriteria", propOrder = {
    "transactionState",
    "documentType",
    "documentNumber",
    "customerId",
    "salespersonId",
    "salesTerritoryId",
    "scope"
})
public class SalespersonCommissionsCriteria
    extends Criteria
{

    @XmlElement(name = "TransactionState")
    protected ListRestrictionOfNullableOfSalesCommissionTransactionState transactionState;
    @XmlElement(name = "DocumentType")
    protected ListRestrictionOfNullableOfSalespersonCommissionsDocumentType documentType;
    @XmlElement(name = "DocumentNumber")
    protected LikeRestrictionOfString documentNumber;
    @XmlElement(name = "CustomerId")
    protected LikeRestrictionOfString customerId;
    @XmlElement(name = "SalespersonId")
    protected LikeRestrictionOfString salespersonId;
    @XmlElement(name = "SalesTerritoryId")
    protected LikeRestrictionOfString salesTerritoryId;
    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected SalespersonScope scope;

    /**
     * Gets the value of the transactionState property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfSalesCommissionTransactionState }
     *     
     */
    public ListRestrictionOfNullableOfSalesCommissionTransactionState getTransactionState() {
        return transactionState;
    }

    /**
     * Sets the value of the transactionState property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfSalesCommissionTransactionState }
     *     
     */
    public void setTransactionState(ListRestrictionOfNullableOfSalesCommissionTransactionState value) {
        this.transactionState = value;
    }

    /**
     * Gets the value of the documentType property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfSalespersonCommissionsDocumentType }
     *     
     */
    public ListRestrictionOfNullableOfSalespersonCommissionsDocumentType getDocumentType() {
        return documentType;
    }

    /**
     * Sets the value of the documentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfSalespersonCommissionsDocumentType }
     *     
     */
    public void setDocumentType(ListRestrictionOfNullableOfSalespersonCommissionsDocumentType value) {
        this.documentType = value;
    }

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setDocumentNumber(LikeRestrictionOfString value) {
        this.documentNumber = value;
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
     * Gets the value of the salespersonId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getSalespersonId() {
        return salespersonId;
    }

    /**
     * Sets the value of the salespersonId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setSalespersonId(LikeRestrictionOfString value) {
        this.salespersonId = value;
    }

    /**
     * Gets the value of the salesTerritoryId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getSalesTerritoryId() {
        return salesTerritoryId;
    }

    /**
     * Sets the value of the salesTerritoryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setSalesTerritoryId(LikeRestrictionOfString value) {
        this.salesTerritoryId = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link SalespersonScope }
     *     
     */
    public SalespersonScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalespersonScope }
     *     
     */
    public void setScope(SalespersonScope value) {
        this.scope = value;
    }

}
