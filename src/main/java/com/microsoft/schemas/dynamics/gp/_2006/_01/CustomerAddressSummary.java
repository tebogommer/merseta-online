
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomerAddressSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomerAddressSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessAddress"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="SalespersonKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesTerritoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTerritoryKey" minOccurs="0"/&gt;
 *         &lt;element name="WarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomerAddressSummary", propOrder = {
    "key",
    "salespersonKey",
    "salesTerritoryKey",
    "warehouseKey"
})
public class CustomerAddressSummary
    extends BusinessAddress
{

    @XmlElement(name = "Key")
    protected CustomerAddressKey key;
    @XmlElement(name = "SalespersonKey")
    protected SalespersonKey salespersonKey;
    @XmlElement(name = "SalesTerritoryKey")
    protected SalesTerritoryKey salesTerritoryKey;
    @XmlElement(name = "WarehouseKey")
    protected WarehouseKey warehouseKey;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerAddressKey }
     *     
     */
    public CustomerAddressKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerAddressKey }
     *     
     */
    public void setKey(CustomerAddressKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the salespersonKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalespersonKey }
     *     
     */
    public SalespersonKey getSalespersonKey() {
        return salespersonKey;
    }

    /**
     * Sets the value of the salespersonKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalespersonKey }
     *     
     */
    public void setSalespersonKey(SalespersonKey value) {
        this.salespersonKey = value;
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
     * Gets the value of the warehouseKey property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getWarehouseKey() {
        return warehouseKey;
    }

    /**
     * Sets the value of the warehouseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setWarehouseKey(WarehouseKey value) {
        this.warehouseKey = value;
    }

}
