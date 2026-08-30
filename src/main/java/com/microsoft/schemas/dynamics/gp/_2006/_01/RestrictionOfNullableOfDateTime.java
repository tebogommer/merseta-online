
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RestrictionOfNullableOfDateTime complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RestrictionOfNullableOfDateTime"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EqualValue" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="NotEqualValue" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestrictionOfNullableOfDateTime", propOrder = {
    "equalValue",
    "notEqualValue"
})
@XmlSeeAlso({
    ListRestrictionOfNullableOfDateTime.class
})
public class RestrictionOfNullableOfDateTime {

    @XmlElement(name = "EqualValue", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar equalValue;
    @XmlElement(name = "NotEqualValue", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar notEqualValue;

    /**
     * Gets the value of the equalValue property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEqualValue() {
        return equalValue;
    }

    /**
     * Sets the value of the equalValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEqualValue(XMLGregorianCalendar value) {
        this.equalValue = value;
    }

    /**
     * Gets the value of the notEqualValue property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNotEqualValue() {
        return notEqualValue;
    }

    /**
     * Sets the value of the notEqualValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNotEqualValue(XMLGregorianCalendar value) {
        this.notEqualValue = value;
    }

}
