
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InventoriedItemCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InventoriedItemCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemCriteriaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfInventoriedItemType" minOccurs="0"/&gt;
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
@XmlType(name = "InventoriedItemCriteria", propOrder = {
    "type",
    "isDiscontinued"
})
public class InventoriedItemCriteria
    extends ItemCriteriaBase
{

    @XmlElement(name = "Type")
    protected ListRestrictionOfNullableOfInventoriedItemType type;
    @XmlElement(name = "IsDiscontinued")
    protected RestrictionOfNullableOfBoolean isDiscontinued;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfInventoriedItemType }
     *     
     */
    public ListRestrictionOfNullableOfInventoriedItemType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfInventoriedItemType }
     *     
     */
    public void setType(ListRestrictionOfNullableOfInventoriedItemType value) {
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
