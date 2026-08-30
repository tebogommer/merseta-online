
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemCriteriaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfItemType" minOccurs="0"/&gt;
 *         &lt;element name="IsDiscontinued" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemCriteria", propOrder = {
    "type",
    "isDiscontinued"
})
public class ItemCriteria
    extends ItemCriteriaBase
{

    @XmlElement(name = "Type")
    protected ListRestrictionOfNullableOfItemType type;
    @XmlElement(name = "IsDiscontinued")
    protected RestrictionOfNullableOfBoolean isDiscontinued;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfItemType }
     *     
     */
    public ListRestrictionOfNullableOfItemType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfItemType }
     *     
     */
    public void setType(ListRestrictionOfNullableOfItemType value) {
        this.type = value;
    }

    /**
     * Gets the value of the isDiscontinued property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getIsDiscontinued() {
        return isDiscontinued;
    }

    /**
     * Sets the value of the isDiscontinued property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setIsDiscontinued(RestrictionOfNullableOfBoolean value) {
        this.isDiscontinued = value;
    }

}
