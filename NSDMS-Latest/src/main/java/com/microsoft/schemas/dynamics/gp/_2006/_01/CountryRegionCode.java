
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CountryRegionCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CountryRegionCode"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CountryRegionCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsEuropeanUnionMember" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CountryRegionCode", propOrder = {
    "key",
    "description",
    "isEuropeanUnionMember"
})
public class CountryRegionCode
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected CountryRegionCodeKey key;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "IsEuropeanUnionMember", required = true, type = Boolean.class, nillable = true)
    protected Boolean isEuropeanUnionMember;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link CountryRegionCodeKey }
     *     
     */
    public CountryRegionCodeKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryRegionCodeKey }
     *     
     */
    public void setKey(CountryRegionCodeKey value) {
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
     * Gets the value of the isEuropeanUnionMember property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsEuropeanUnionMember() {
        return isEuropeanUnionMember;
    }

    /**
     * Sets the value of the isEuropeanUnionMember property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsEuropeanUnionMember(Boolean value) {
        this.isEuropeanUnionMember = value;
    }

}
