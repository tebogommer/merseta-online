
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HRRequisitionCosts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HRRequisitionCosts"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Advertising" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="RecruiterFees" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Travel" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Lodging" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="MovingExpenses" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="Other" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HRRequisitionCosts", propOrder = {
    "advertising",
    "recruiterFees",
    "travel",
    "lodging",
    "movingExpenses",
    "other"
})
public class HRRequisitionCosts {

    @XmlElement(name = "Advertising", required = true, nillable = true)
    protected BigDecimal advertising;
    @XmlElement(name = "RecruiterFees", required = true, nillable = true)
    protected BigDecimal recruiterFees;
    @XmlElement(name = "Travel", required = true, nillable = true)
    protected BigDecimal travel;
    @XmlElement(name = "Lodging", required = true, nillable = true)
    protected BigDecimal lodging;
    @XmlElement(name = "MovingExpenses", required = true, nillable = true)
    protected BigDecimal movingExpenses;
    @XmlElement(name = "Other", required = true, nillable = true)
    protected BigDecimal other;

    /**
     * Gets the value of the advertising property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAdvertising() {
        return advertising;
    }

    /**
     * Sets the value of the advertising property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAdvertising(BigDecimal value) {
        this.advertising = value;
    }

    /**
     * Gets the value of the recruiterFees property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRecruiterFees() {
        return recruiterFees;
    }

    /**
     * Sets the value of the recruiterFees property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRecruiterFees(BigDecimal value) {
        this.recruiterFees = value;
    }

    /**
     * Gets the value of the travel property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTravel() {
        return travel;
    }

    /**
     * Sets the value of the travel property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTravel(BigDecimal value) {
        this.travel = value;
    }

    /**
     * Gets the value of the lodging property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLodging() {
        return lodging;
    }

    /**
     * Sets the value of the lodging property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLodging(BigDecimal value) {
        this.lodging = value;
    }

    /**
     * Gets the value of the movingExpenses property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMovingExpenses() {
        return movingExpenses;
    }

    /**
     * Sets the value of the movingExpenses property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMovingExpenses(BigDecimal value) {
        this.movingExpenses = value;
    }

    /**
     * Gets the value of the other property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getOther() {
        return other;
    }

    /**
     * Sets the value of the other property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setOther(BigDecimal value) {
        this.other = value;
    }

}
