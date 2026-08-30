
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BetweenRestrictionOfNullableOfServiceCallType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BetweenRestrictionOfNullableOfServiceCallType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfServiceCallType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="From" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallType"/&gt;
 *         &lt;element name="To" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallType"/&gt;
 *         &lt;element name="GreaterThan" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallType"/&gt;
 *         &lt;element name="LessThan" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BetweenRestrictionOfNullableOfServiceCallType", propOrder = {
    "from",
    "to",
    "greaterThan",
    "lessThan"
})
@XmlSeeAlso({
    LikeRestrictionOfNullableOfServiceCallType.class
})
public class BetweenRestrictionOfNullableOfServiceCallType
    extends ListRestrictionOfNullableOfServiceCallType
{

    @XmlElement(name = "From", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ServiceCallType from;
    @XmlElement(name = "To", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ServiceCallType to;
    @XmlElement(name = "GreaterThan", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ServiceCallType greaterThan;
    @XmlElement(name = "LessThan", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ServiceCallType lessThan;

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallType }
     *     
     */
    public ServiceCallType getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallType }
     *     
     */
    public void setFrom(ServiceCallType value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallType }
     *     
     */
    public ServiceCallType getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallType }
     *     
     */
    public void setTo(ServiceCallType value) {
        this.to = value;
    }

    /**
     * Gets the value of the greaterThan property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallType }
     *     
     */
    public ServiceCallType getGreaterThan() {
        return greaterThan;
    }

    /**
     * Sets the value of the greaterThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallType }
     *     
     */
    public void setGreaterThan(ServiceCallType value) {
        this.greaterThan = value;
    }

    /**
     * Gets the value of the lessThan property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCallType }
     *     
     */
    public ServiceCallType getLessThan() {
        return lessThan;
    }

    /**
     * Sets the value of the lessThan property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCallType }
     *     
     */
    public void setLessThan(ServiceCallType value) {
        this.lessThan = value;
    }

}
