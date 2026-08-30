
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ListRestrictionOfNullableOfReceivablesDocumentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ListRestrictionOfNullableOfReceivablesDocumentType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfReceivablesDocumentType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Items" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfReceivablesDocumentType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ListRestrictionOfNullableOfReceivablesDocumentType", propOrder = {
    "items"
})
public class ListRestrictionOfNullableOfReceivablesDocumentType
    extends RestrictionOfNullableOfReceivablesDocumentType
{

    @XmlElement(name = "Items")
    protected ArrayOfReceivablesDocumentType items;

    /**
     * Gets the value of the items property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfReceivablesDocumentType }
     *     
     */
    public ArrayOfReceivablesDocumentType getItems() {
        return items;
    }

    /**
     * Sets the value of the items property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfReceivablesDocumentType }
     *     
     */
    public void setItems(ArrayOfReceivablesDocumentType value) {
        this.items = value;
    }

}
