
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlannedOrderCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlannedOrderCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}PlannedOrderCriteriaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ReplenishmentMethod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfPlannedOrderReplenishment" minOccurs="0"/&gt;
 *         &lt;element name="Transferred" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *         &lt;element name="RunNumber" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlannedOrderCriteria", propOrder = {
    "replenishmentMethod",
    "transferred",
    "runNumber"
})
public class PlannedOrderCriteria
    extends PlannedOrderCriteriaBase
{

    @XmlElement(name = "ReplenishmentMethod")
    protected ListRestrictionOfNullableOfPlannedOrderReplenishment replenishmentMethod;
    @XmlElement(name = "Transferred")
    protected RestrictionOfNullableOfBoolean transferred;
    @XmlElement(name = "RunNumber")
    protected BetweenRestrictionOfNullableOfInt32 runNumber;

    /**
     * Gets the value of the replenishmentMethod property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfPlannedOrderReplenishment }
     *     
     */
    public ListRestrictionOfNullableOfPlannedOrderReplenishment getReplenishmentMethod() {
        return replenishmentMethod;
    }

    /**
     * Sets the value of the replenishmentMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfPlannedOrderReplenishment }
     *     
     */
    public void setReplenishmentMethod(ListRestrictionOfNullableOfPlannedOrderReplenishment value) {
        this.replenishmentMethod = value;
    }

    /**
     * Gets the value of the transferred property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getTransferred() {
        return transferred;
    }

    /**
     * Sets the value of the transferred property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setTransferred(RestrictionOfNullableOfBoolean value) {
        this.transferred = value;
    }

    /**
     * Gets the value of the runNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfInt32 }
     *     
     */
    public BetweenRestrictionOfNullableOfInt32 getRunNumber() {
        return runNumber;
    }

    /**
     * Sets the value of the runNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfInt32 }
     *     
     */
    public void setRunNumber(BetweenRestrictionOfNullableOfInt32 value) {
        this.runNumber = value;
    }

}
