
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PaymentCardType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentCardType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PaymentCardTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="ReceivableCardType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivableCardType"/&gt;
 *         &lt;element name="UsedByCompany" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AcceptedFromCustomers" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="CustomerCardBankAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BankAccountKey" minOccurs="0"/&gt;
 *         &lt;element name="GLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *         &lt;element name="PayableCardType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayableCardType"/&gt;
 *         &lt;element name="CompanyCardBankAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BankAccountKey" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentCardType", propOrder = {
    "key",
    "receivableCardType",
    "usedByCompany",
    "acceptedFromCustomers",
    "customerCardBankAccountKey",
    "glAccountKey",
    "vendorKey",
    "payableCardType",
    "companyCardBankAccountKey",
    "modifiedBy",
    "createdDate",
    "modifiedDate"
})
public class PaymentCardType
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected PaymentCardTypeKey key;
    @XmlElement(name = "ReceivableCardType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ReceivableCardType receivableCardType;
    @XmlElement(name = "UsedByCompany", required = true, type = Boolean.class, nillable = true)
    protected Boolean usedByCompany;
    @XmlElement(name = "AcceptedFromCustomers", required = true, type = Boolean.class, nillable = true)
    protected Boolean acceptedFromCustomers;
    @XmlElement(name = "CustomerCardBankAccountKey")
    protected BankAccountKey customerCardBankAccountKey;
    @XmlElement(name = "GLAccountKey")
    protected GLAccountNumberKey glAccountKey;
    @XmlElement(name = "VendorKey")
    protected VendorKey vendorKey;
    @XmlElement(name = "PayableCardType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PayableCardType payableCardType;
    @XmlElement(name = "CompanyCardBankAccountKey")
    protected BankAccountKey companyCardBankAccountKey;
    @XmlElement(name = "ModifiedBy")
    protected String modifiedBy;
    @XmlElement(name = "CreatedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentCardTypeKey }
     *     
     */
    public PaymentCardTypeKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentCardTypeKey }
     *     
     */
    public void setKey(PaymentCardTypeKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the receivableCardType property.
     * 
     * @return
     *     possible object is
     *     {@link ReceivableCardType }
     *     
     */
    public ReceivableCardType getReceivableCardType() {
        return receivableCardType;
    }

    /**
     * Sets the value of the receivableCardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivableCardType }
     *     
     */
    public void setReceivableCardType(ReceivableCardType value) {
        this.receivableCardType = value;
    }

    /**
     * Gets the value of the usedByCompany property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUsedByCompany() {
        return usedByCompany;
    }

    /**
     * Sets the value of the usedByCompany property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUsedByCompany(Boolean value) {
        this.usedByCompany = value;
    }

    /**
     * Gets the value of the acceptedFromCustomers property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAcceptedFromCustomers() {
        return acceptedFromCustomers;
    }

    /**
     * Sets the value of the acceptedFromCustomers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAcceptedFromCustomers(Boolean value) {
        this.acceptedFromCustomers = value;
    }

    /**
     * Gets the value of the customerCardBankAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link BankAccountKey }
     *     
     */
    public BankAccountKey getCustomerCardBankAccountKey() {
        return customerCardBankAccountKey;
    }

    /**
     * Sets the value of the customerCardBankAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAccountKey }
     *     
     */
    public void setCustomerCardBankAccountKey(BankAccountKey value) {
        this.customerCardBankAccountKey = value;
    }

    /**
     * Gets the value of the glAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getGLAccountKey() {
        return glAccountKey;
    }

    /**
     * Sets the value of the glAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setGLAccountKey(GLAccountNumberKey value) {
        this.glAccountKey = value;
    }

    /**
     * Gets the value of the vendorKey property.
     * 
     * @return
     *     possible object is
     *     {@link VendorKey }
     *     
     */
    public VendorKey getVendorKey() {
        return vendorKey;
    }

    /**
     * Sets the value of the vendorKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorKey }
     *     
     */
    public void setVendorKey(VendorKey value) {
        this.vendorKey = value;
    }

    /**
     * Gets the value of the payableCardType property.
     * 
     * @return
     *     possible object is
     *     {@link PayableCardType }
     *     
     */
    public PayableCardType getPayableCardType() {
        return payableCardType;
    }

    /**
     * Sets the value of the payableCardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayableCardType }
     *     
     */
    public void setPayableCardType(PayableCardType value) {
        this.payableCardType = value;
    }

    /**
     * Gets the value of the companyCardBankAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link BankAccountKey }
     *     
     */
    public BankAccountKey getCompanyCardBankAccountKey() {
        return companyCardBankAccountKey;
    }

    /**
     * Sets the value of the companyCardBankAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAccountKey }
     *     
     */
    public void setCompanyCardBankAccountKey(BankAccountKey value) {
        this.companyCardBankAccountKey = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
    }

}
