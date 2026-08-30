
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemSummaryBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemType"/&gt;
 *         &lt;element name="IsDiscontinued" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemSummary", propOrder = {
    "type",
    "isDiscontinued"
})
public class ItemSummary
    extends ItemSummaryBase
{

    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected ItemType type;
    @XmlElement(name = "IsDiscontinued")
    protected boolean isDiscontinued;

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
     * Gets the value of the isDiscontinued property.
     * 
     */
    public boolean isIsDiscontinued() {
        return isDiscontinued;
    }

    /**
     * Sets the value of the isDiscontinued property.
     * 
     */
    public void setIsDiscontinued(boolean value) {
        this.isDiscontinued = value;
    }

}
