
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BetweenRestrictionOfNullableOfInt32 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BetweenRestrictionOfNullableOfInt32"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfInt32"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="From" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="To" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="GreaterThan" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="LessThan" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BetweenRestrictionOfNullableOfInt32", propOrder = {
    "from",
    "to",
    "greaterThan",
    "lessThan"
})
@XmlSeeAlso({
    LikeRestrictionOfNullableOfInt32 .class
})
public class BetweenRestrictionOfNullableOfInt32
    extends ListRestrictionOfNullableOfInt32
{

    @XmlElement(name = "From", required = true, type = Integer.class, nillable = true)
    protected Integer from;
    @XmlElement(name = "To", required = true, type = Integer.class, nillable = true)
    protected Integer to;
    @XmlElement(name = "GreaterThan", required = true, type = Integer.class, nillable = true)
    protected Integer greaterThan;
    @XmlElement(name = "LessThan", required = true, type = Integer.class, nillable = true)
    protected Integer lessThan;

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFrom(Integer value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTo(Integer value) {
        this.to = value;
    }

    /**
     * Gets the value of the greaterThan property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGreaterThan() {
        return greaterThan;
    }

    /**
     * Sets the value of the greaterThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGreaterThan(Integer value) {
        this.greaterThan = value;
    }

    /**
     * Gets the value of the lessThan property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getLessThan() {
        return lessThan;
    }

    /**
     * Sets the value of the lessThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLessThan(Integer value) {
        this.lessThan = value;
    }

}
