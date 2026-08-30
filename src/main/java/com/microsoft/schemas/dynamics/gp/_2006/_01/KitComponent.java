
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for KitComponent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="KitComponent"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}KitComponentKey" minOccurs="0"/&gt;
 *         &lt;element name="Quantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemType"/&gt;
 *         &lt;element name="UofMScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}UofMScheduleKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KitComponent", propOrder = {
    "key",
    "quantity",
    "description",
    "type",
    "uofMScheduleKey"
})
public class KitComponent
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected KitComponentKey key;
    @XmlElement(name = "Quantity")
    protected Quantity quantity;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Type", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ItemType type;
    @XmlElement(name = "UofMScheduleKey")
    protected UofMScheduleKey uofMScheduleKey;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link KitComponentKey }
     *     
     */
    public KitComponentKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link KitComponentKey }
     *     
     */
    public void setKey(KitComponentKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantity(Quantity value) {
        this.quantity = value;
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
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ItemType }
     *     
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemType }
     *     
     */
    public void setType(ItemType value) {
        this.type = value;
    }

    /**
     * Gets the value of the uofMScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link UofMScheduleKey }
     *     
     */
    public UofMScheduleKey getUofMScheduleKey() {
        return uofMScheduleKey;
    }

    /**
     * Sets the value of the uofMScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link UofMScheduleKey }
     *     
     */
    public void setUofMScheduleKey(UofMScheduleKey value) {
        this.uofMScheduleKey = value;
    }

}
