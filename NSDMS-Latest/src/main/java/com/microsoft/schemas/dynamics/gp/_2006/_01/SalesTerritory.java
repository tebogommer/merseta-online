
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SalesTerritory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesTerritory"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTerritoryKey" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsActive" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="SalesManagerKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesManagerFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SalesManagerMiddleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SalesManagerLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="HistoryOptions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesHistoryOptions" minOccurs="0"/&gt;
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="SalesSummary" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesSummary" minOccurs="0"/&gt;
 *         &lt;element name="SalesHistory" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesTerritoryHistory" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesTerritory", propOrder = {
    "key",
    "description",
    "isActive",
    "salesManagerKey",
    "salesManagerFirstName",
    "salesManagerMiddleName",
    "salesManagerLastName",
    "country",
    "historyOptions",
    "createdDate",
    "modifiedDate",
    "salesSummary",
    "salesHistory"
})
public class SalesTerritory
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected SalesTerritoryKey key;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "IsActive", required = true, type = Boolean.class, nillable = true)
    protected Boolean isActive;
    @XmlElement(name = "SalesManagerKey")
    protected SalespersonKey salesManagerKey;
    @XmlElement(name = "SalesManagerFirstName")
    protected String salesManagerFirstName;
    @XmlElement(name = "SalesManagerMiddleName")
    protected String salesManagerMiddleName;
    @XmlElement(name = "SalesManagerLastName")
    protected String salesManagerLastName;
    @XmlElement(name = "Country")
    protected String country;
    @XmlElement(name = "HistoryOptions")
    protected SalesHistoryOptions historyOptions;
    @XmlElement(name = "CreatedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "SalesSummary")
    protected SalesSummary salesSummary;
    @XmlElement(name = "SalesHistory")
    protected ArrayOfSalesTerritoryHistory salesHistory;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTerritoryKey }
     *     
     */
    public SalesTerritoryKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTerritoryKey }
     *     
     */
    public void setKey(SalesTerritoryKey value) {
        this.key = value;
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
     * Gets the value of the salesManagerKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalespersonKey }
     *     
     */
    public SalespersonKey getSalesManagerKey() {
        return salesManagerKey;
    }

    /**
     * Sets the value of the salesManagerKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalespersonKey }
     *     
     */
    public void setSalesManagerKey(SalespersonKey value) {
        this.salesManagerKey = value;
    }

    /**
     * Gets the value of the salesManagerFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesManagerFirstName() {
        return salesManagerFirstName;
    }

    /**
     * Sets the value of the salesManagerFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesManagerFirstName(String value) {
        this.salesManagerFirstName = value;
    }

    /**
     * Gets the value of the salesManagerMiddleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesManagerMiddleName() {
        return salesManagerMiddleName;
    }

    /**
     * Sets the value of the salesManagerMiddleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesManagerMiddleName(String value) {
        this.salesManagerMiddleName = value;
    }

    /**
     * Gets the value of the salesManagerLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSalesManagerLastName() {
        return salesManagerLastName;
    }

    /**
     * Sets the value of the salesManagerLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSalesManagerLastName(String value) {
        this.salesManagerLastName = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
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
     *     {@link ArrayOfSalesTerritoryHistory }
     *     
     */
    public ArrayOfSalesTerritoryHistory getSalesHistory() {
        return salesHistory;
    }

    /**
     * Sets the value of the salesHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesTerritoryHistory }
     *     
     */
    public void setSalesHistory(ArrayOfSalesTerritoryHistory value) {
        this.salesHistory = value;
    }

}
