
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListRestrictionOfNullableOfGLLedgerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListRestrictionOfNullableOfGLLedgerType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfGLLedgerType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Items" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfGLLedgerType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListRestrictionOfNullableOfGLLedgerType", propOrder = {
    "items"
})
public class ListRestrictionOfNullableOfGLLedgerType
    extends RestrictionOfNullableOfGLLedgerType
{

    @XmlElement(name = "Items")
    protected ArrayOfGLLedgerType items;

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGLLedgerType }
     *     
     */
    public ArrayOfGLLedgerType getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGLLedgerType }
     *     
     */
    public void setItems(ArrayOfGLLedgerType value) {
        this.items = value;
    }

}
