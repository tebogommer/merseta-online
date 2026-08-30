
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemCurrency complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemCurrency"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemCurrencyKey" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyDecimalPlaces" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyDecimalPlaces"/&gt;
 *         &lt;element name="ListPrice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemCurrency", propOrder = {
    "key",
    "currencyDecimalPlaces",
    "listPrice"
})
public class ItemCurrency
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ItemCurrencyKey key;
    @XmlElement(name = "CurrencyDecimalPlaces", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CurrencyDecimalPlaces currencyDecimalPlaces;
    @XmlElement(name = "ListPrice")
    protected MoneyAmount listPrice;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ItemCurrencyKey }
     *     
     */
    public ItemCurrencyKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemCurrencyKey }
     *     
     */
    public void setKey(ItemCurrencyKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the currencyDecimalPlaces property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyDecimalPlaces }
     *     
     */
    public CurrencyDecimalPlaces getCurrencyDecimalPlaces() {
        return currencyDecimalPlaces;
    }

    /**
     * Sets the value of the currencyDecimalPlaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyDecimalPlaces }
     *     
     */
    public void setCurrencyDecimalPlaces(CurrencyDecimalPlaces value) {
        this.currencyDecimalPlaces = value;
    }

    /**
     * Gets the value of the listPrice property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getListPrice() {
        return listPrice;
    }

    /**
     * Sets the value of the listPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setListPrice(MoneyAmount value) {
        this.listPrice = value;
    }

}
