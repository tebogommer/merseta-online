
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceivablesDocumentCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceivablesDocumentCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesDocumentCriteriaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfReceivablesDocumentType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceivablesDocumentCriteria", propOrder = {
    "type"
})
public class ReceivablesDocumentCriteria
    extends ReceivablesDocumentCriteriaBase
{

    @XmlElement(name = "Type")
    protected ListRestrictionOfNullableOfReceivablesDocumentType type;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfReceivablesDocumentType }
     *     
     */
    public ListRestrictionOfNullableOfReceivablesDocumentType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfReceivablesDocumentType }
     *     
     */
    public void setType(ListRestrictionOfNullableOfReceivablesDocumentType value) {
        this.type = value;
    }

}
