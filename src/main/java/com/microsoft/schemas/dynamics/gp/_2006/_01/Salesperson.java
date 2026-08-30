
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Salesperson complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Salesperson"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesTerritoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTerritoryKey" minOccurs="0"/&gt;
 *         &lt;element name="EmployeeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}EmployeeKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *         &lt;element name="FirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="MiddleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="LastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Address" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Address" minOccurs="0"/&gt;
 *         &lt;element name="IsActive" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="CommissionPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *         &lt;element name="CommissionBasedOn" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CommissionBasedOn"/&gt;
 *         &lt;element name="HistoryOptions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesHistoryOptions" minOccurs="0"/&gt;
 *         &lt;element name="SalesSummary" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesSummary" minOccurs="0"/&gt;
 *         &lt;element name="SalesHistory" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalespersonHistory" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="InternetAddresses" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}InternetAddresses" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Salesperson", propOrder = {
    "key",
    "salesTerritoryKey",
    "employeeKey",
    "vendorKey",
    "firstName",
    "middleName",
    "lastName",
    "address",
    "isActive",
    "commissionPercent",
    "commissionBasedOn",
    "historyOptions",
    "salesSummary",
    "salesHistory",
    "createdDate",
    "modifiedDate",
    "internetAddresses"
})
public class Salesperson
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected SalespersonKey key;
    @XmlElement(name = "SalesTerritoryKey")
    protected SalesTerritoryKey salesTerritoryKey;
    @XmlElement(name = "EmployeeKey")
    protected EmployeeKey employeeKey;
    @XmlElement(name = "VendorKey")
    protected VendorKey vendorKey;
    @XmlElement(name = "FirstName")
    protected String firstName;
    @XmlElement(name = "MiddleName")
    protected String middleName;
    @XmlElement(name = "LastName")
    protected String lastName;
    @XmlElement(name = "Address")
    protected Address address;
    @XmlElement(name = "IsActive", required = true, type = Boolean.class, nillable = true)
    protected Boolean isActive;
    @XmlElement(name = "CommissionPercent")
    protected Percent commissionPercent;
    @XmlElement(name = "CommissionBasedOn", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CommissionBasedOn commissionBasedOn;
    @XmlElement(name = "HistoryOptions")
    protected SalesHistoryOptions historyOptions;
    @XmlElement(name = "SalesSummary")
    protected SalesSummary salesSummary;
    @XmlElement(name = "SalesHistory")
    protected ArrayOfSalespersonHistory salesHistory;
    @XmlElement(name = "CreatedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "InternetAddresses")
    protected InternetAddresses internetAddresses;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link SalespersonKey }
     *     
     */
    public SalespersonKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalespersonKey }
     *     
     */
    public void setKey(SalespersonKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the salesTerritoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTerritoryKey }
     *     
     */
    public SalesTerritoryKey getSalesTerritoryKey() {
        return salesTerritoryKey;
    }

    /**
     * Sets the value of the salesTerritoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTerritoryKey }
     *     
     */
    public void setSalesTerritoryKey(SalesTerritoryKey value) {
        this.salesTerritoryKey = value;
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
     * Gets the value of the firstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the value of the firstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the lastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the value of the lastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastName(String value) {
        this.lastName = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setAddress(Address value) {
        this.address = value;
    }

    /**
     * Gets the value of the isActive property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsActive() {
        return isActive;
    }

    /**
     * Sets the value of the isActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsActive(Boolean value) {
        this.isActive = value;
    }

    /**
     * Gets the value of the commissionPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getCommissionPercent() {
        return commissionPercent;
    }

    /**
     * Sets the value of the commissionPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setCommissionPercent(Percent value) {
        this.commissionPercent = value;
    }

    /**
     * Gets the value of the commissionBasedOn property.
     * 
     * @return
     *     possible object is
     *     {@link CommissionBasedOn }
     *     
     */
    public CommissionBasedOn getCommissionBasedOn() {
        return commissionBasedOn;
    }

    /**
     * Sets the value of the commissionBasedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommissionBasedOn }
     *     
     */
    public void setCommissionBasedOn(CommissionBasedOn value) {
        this.commissionBasedOn = value;
    }

    /**
     * Gets the value of the historyOptions property.
     * 
     * @return
     *     possible object is
     *     {@link SalesHistoryOptions }
     *     
     */
    public SalesHistoryOptions getHistoryOptions() {
        return historyOptions;
    }

    /**
     * Sets the value of the historyOptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesHistoryOptions }
     *     
     */
    public void setHistoryOptions(SalesHistoryOptions value) {
        this.historyOptions = value;
    }

    /**
     * Gets the value of the salesSummary property.
     * 
     * @return
     *     possible object is
     *     {@link SalesSummary }
     *     
     */
    public SalesSummary getSalesSummary() {
        return salesSummary;
    }

    /**
     * Sets the value of the salesSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesSummary }
     *     
     */
    public void setSalesSummary(SalesSummary value) {
        this.salesSummary = value;
    }

    /**
     * Gets the value of the salesHistory property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalespersonHistory }
     *     
     */
    public ArrayOfSalespersonHistory getSalesHistory() {
        return salesHistory;
    }

    /**
     * Sets the value of the salesHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalespersonHistory }
     *     
     */
    public void setSalesHistory(ArrayOfSalespersonHistory value) {
        this.salesHistory = value;
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

    /**
     * Gets the value of the internetAddresses property.
     * 
     * @return
     *     possible object is
     *     {@link InternetAddresses }
     *     
     */
    public InternetAddresses getInternetAddresses() {
        return internetAddresses;
    }

    /**
     * Sets the value of the internetAddresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternetAddresses }
     *     
     */
    public void setInternetAddresses(InternetAddresses value) {
        this.internetAddresses = value;
    }

}
