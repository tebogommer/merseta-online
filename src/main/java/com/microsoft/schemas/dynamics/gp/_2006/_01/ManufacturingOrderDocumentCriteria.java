
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManufacturingOrderDocumentCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManufacturingOrderDocumentCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ManufacturingOrderDocumentKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="ItemKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="OrderStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfManufacturingOrderStatus" minOccurs="0"/&gt;
 *         &lt;element name="StartDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManufacturingOrderDocumentCriteria", propOrder = {
    "manufacturingOrderDocumentKeyId",
    "itemKeyId",
    "orderStatus",
    "startDate"
})
@XmlSeeAlso({
    ManufacturingOrderCriteria.class,
    VendorManufacturingOrderCriteria.class
})
public class ManufacturingOrderDocumentCriteria
    extends Criteria
{

    @XmlElement(name = "ManufacturingOrderDocumentKeyId")
    protected LikeRestrictionOfString manufacturingOrderDocumentKeyId;
    @XmlElement(name = "ItemKeyId")
    protected LikeRestrictionOfString itemKeyId;
    @XmlElement(name = "OrderStatus")
    protected ListRestrictionOfNullableOfManufacturingOrderStatus orderStatus;
    @XmlElement(name = "StartDate")
    protected BetweenRestrictionOfNullableOfDateTime startDate;

    /**
     * Gets the value of the manufacturingOrderDocumentKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getManufacturingOrderDocumentKeyId() {
        return manufacturingOrderDocumentKeyId;
    }

    /**
     * Sets the value of the manufacturingOrderDocumentKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setManufacturingOrderDocumentKeyId(LikeRestrictionOfString value) {
        this.manufacturingOrderDocumentKeyId = value;
    }

    /**
     * Gets the value of the itemKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getItemKeyId() {
        return itemKeyId;
    }

    /**
     * Sets the value of the itemKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setItemKeyId(LikeRestrictionOfString value) {
        this.itemKeyId = value;
    }

    /**
     * Gets the value of the orderStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfManufacturingOrderStatus }
     *     
     */
    public ListRestrictionOfNullableOfManufacturingOrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the value of the orderStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfManufacturingOrderStatus }
     *     
     */
    public void setOrderStatus(ListRestrictionOfNullableOfManufacturingOrderStatus value) {
        this.orderStatus = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setStartDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.startDate = value;
    }

}
