
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BetweenRestrictionOfNullableOfDecimal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BetweenRestrictionOfNullableOfDecimal"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfDecimal"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="From" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="To" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="GreaterThan" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="LessThan" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BetweenRestrictionOfNullableOfDecimal", propOrder = {
    "from",
    "to",
    "greaterThan",
    "lessThan"
})
public class BetweenRestrictionOfNullableOfDecimal
    extends ListRestrictionOfNullableOfDecimal
{

    @XmlElement(name = "From", required = true, nillable = true)
    protected BigDecimal from;
    @XmlElement(name = "To", required = true, nillable = true)
    protected BigDecimal to;
    @XmlElement(name = "GreaterThan", required = true, nillable = true)
    protected BigDecimal greaterThan;
    @XmlElement(name = "LessThan", required = true, nillable = true)
    protected BigDecimal lessThan;

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setFrom(BigDecimal value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTo(BigDecimal value) {
        this.to = value;
    }

    /**
     * Gets the value of the greaterThan property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGreaterThan() {
        return greaterThan;
    }

    /**
     * Sets the value of the greaterThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGreaterThan(BigDecimal value) {
        this.greaterThan = value;
    }

    /**
     * Gets the value of the lessThan property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLessThan() {
        return lessThan;
    }

    /**
     * Sets the value of the lessThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLessThan(BigDecimal value) {
        this.lessThan = value;
    }

}
