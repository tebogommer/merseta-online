
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListRestrictionOfNullableOfPlannedOrderReplenishment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListRestrictionOfNullableOfPlannedOrderReplenishment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfPlannedOrderReplenishment"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Items" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPlannedOrderReplenishment" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListRestrictionOfNullableOfPlannedOrderReplenishment", propOrder = {
    "items"
})
public class ListRestrictionOfNullableOfPlannedOrderReplenishment
    extends RestrictionOfNullableOfPlannedOrderReplenishment
{

    @XmlElement(name = "Items")
    protected ArrayOfPlannedOrderReplenishment items;

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPlannedOrderReplenishment }
     *     
     */
    public ArrayOfPlannedOrderReplenishment getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPlannedOrderReplenishment }
     *     
     */
    public void setItems(ArrayOfPlannedOrderReplenishment value) {
        this.items = value;
    }

}
