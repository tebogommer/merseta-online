
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RestrictionOfNullableOfReceivablesDocumentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RestrictionOfNullableOfReceivablesDocumentType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EqualValue" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesDocumentType"/&gt;
 *         &lt;element name="NotEqualValue" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesDocumentType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestrictionOfNullableOfReceivablesDocumentType", propOrder = {
    "equalValue",
    "notEqualValue"
})
@XmlSeeAlso({
    ListRestrictionOfNullableOfReceivablesDocumentType.class
})
public class RestrictionOfNullableOfReceivablesDocumentType {

    @XmlElement(name = "EqualValue", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ReceivablesDocumentType equalValue;
    @XmlElement(name = "NotEqualValue", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ReceivablesDocumentType notEqualValue;

    /**
     * Gets the value of the equalValue property.
     * 
     * @return
     *     possible object is
     *     {@link ReceivablesDocumentType }
     *     
     */
    public ReceivablesDocumentType getEqualValue() {
        return equalValue;
    }

    /**
     * Sets the value of the equalValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivablesDocumentType }
     *     
     */
    public void setEqualValue(ReceivablesDocumentType value) {
        this.equalValue = value;
    }

    /**
     * Gets the value of the notEqualValue property.
     * 
     * @return
     *     possible object is
     *     {@link ReceivablesDocumentType }
     *     
     */
    public ReceivablesDocumentType getNotEqualValue() {
        return notEqualValue;
    }

    /**
     * Sets the value of the notEqualValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivablesDocumentType }
     *     
     */
    public void setNotEqualValue(ReceivablesDocumentType value) {
        this.notEqualValue = value;
    }

}
