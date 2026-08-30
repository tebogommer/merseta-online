
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListRestrictionOfNullableOfInventoryTransactionState complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListRestrictionOfNullableOfInventoryTransactionState"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfInventoryTransactionState"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Items" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfInventoryTransactionState" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListRestrictionOfNullableOfInventoryTransactionState", propOrder = {
    "items"
})
public class ListRestrictionOfNullableOfInventoryTransactionState
    extends RestrictionOfNullableOfInventoryTransactionState
{

    @XmlElement(name = "Items")
    protected ArrayOfInventoryTransactionState items;

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInventoryTransactionState }
     *     
     */
    public ArrayOfInventoryTransactionState getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInventoryTransactionState }
     *     
     */
    public void setItems(ArrayOfInventoryTransactionState value) {
        this.items = value;
    }

}
