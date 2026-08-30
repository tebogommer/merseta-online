
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for BetweenRestrictionOfNullableOfDateTime complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BetweenRestrictionOfNullableOfDateTime"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfDateTime"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="From" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="To" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="GreaterThan" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LessThan" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BetweenRestrictionOfNullableOfDateTime", propOrder = {
    "from",
    "to",
    "greaterThan",
    "lessThan"
})
public class BetweenRestrictionOfNullableOfDateTime
    extends ListRestrictionOfNullableOfDateTime
{

    @XmlElement(name = "From", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar from;
    @XmlElement(name = "To", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar to;
    @XmlElement(name = "GreaterThan", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar greaterThan;
    @XmlElement(name = "LessThan", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lessThan;

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFrom(XMLGregorianCalendar value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTo(XMLGregorianCalendar value) {
        this.to = value;
    }

    /**
     * Gets the value of the greaterThan property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getGreaterThan() {
        return greaterThan;
    }

    /**
     * Sets the value of the greaterThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setGreaterThan(XMLGregorianCalendar value) {
        this.greaterThan = value;
    }

    /**
     * Gets the value of the lessThan property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLessThan() {
        return lessThan;
    }

    /**
     * Sets the value of the lessThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLessThan(XMLGregorianCalendar value) {
        this.lessThan = value;
    }

}
