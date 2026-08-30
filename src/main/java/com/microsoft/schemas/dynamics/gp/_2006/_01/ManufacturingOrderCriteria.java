
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManufacturingOrderCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManufacturingOrderCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderDocumentCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Priority" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfManufacturingOrderPriority" minOccurs="0"/&gt;
 *         &lt;element name="RoutingName" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="Archived" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *         &lt;element name="QuickMo" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RestrictionOfNullableOfBoolean" minOccurs="0"/&gt;
 *         &lt;element name="DateCompleted" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *         &lt;element name="OutSourced" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfManufacturingOrderOutSourced" minOccurs="0"/&gt;
 *         &lt;element name="PostToSite" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="EndDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfDateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManufacturingOrderCriteria", propOrder = {
    "priority",
    "routingName",
    "lastModifiedDate",
    "archived",
    "quickMo",
    "dateCompleted",
    "outSourced",
    "postToSite",
    "endDate"
})
public class ManufacturingOrderCriteria
    extends ManufacturingOrderDocumentCriteria
{

    @XmlElement(name = "Priority")
    protected ListRestrictionOfNullableOfManufacturingOrderPriority priority;
    @XmlElement(name = "RoutingName")
    protected LikeRestrictionOfString routingName;
    @XmlElement(name = "LastModifiedDate")
    protected BetweenRestrictionOfNullableOfDateTime lastModifiedDate;
    @XmlElement(name = "Archived")
    protected RestrictionOfNullableOfBoolean archived;
    @XmlElement(name = "QuickMo")
    protected RestrictionOfNullableOfBoolean quickMo;
    @XmlElement(name = "DateCompleted")
    protected BetweenRestrictionOfNullableOfDateTime dateCompleted;
    @XmlElement(name = "OutSourced")
    protected ListRestrictionOfNullableOfManufacturingOrderOutSourced outSourced;
    @XmlElement(name = "PostToSite")
    protected LikeRestrictionOfString postToSite;
    @XmlElement(name = "EndDate")
    protected BetweenRestrictionOfNullableOfDateTime endDate;

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfManufacturingOrderPriority }
     *     
     */
    public ListRestrictionOfNullableOfManufacturingOrderPriority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfManufacturingOrderPriority }
     *     
     */
    public void setPriority(ListRestrictionOfNullableOfManufacturingOrderPriority value) {
        this.priority = value;
    }

    /**
     * Gets the value of the routingName property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getRoutingName() {
        return routingName;
    }

    /**
     * Sets the value of the routingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setRoutingName(LikeRestrictionOfString value) {
        this.routingName = value;
    }

    /**
     * Gets the value of the lastModifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the value of the lastModifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setLastModifiedDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.lastModifiedDate = value;
    }

    /**
     * Gets the value of the archived property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getArchived() {
        return archived;
    }

    /**
     * Sets the value of the archived property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setArchived(RestrictionOfNullableOfBoolean value) {
        this.archived = value;
    }

    /**
     * Gets the value of the quickMo property.
     * 
     * @return
     *     possible object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public RestrictionOfNullableOfBoolean getQuickMo() {
        return quickMo;
    }

    /**
     * Sets the value of the quickMo property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictionOfNullableOfBoolean }
     *     
     */
    public void setQuickMo(RestrictionOfNullableOfBoolean value) {
        this.quickMo = value;
    }

    /**
     * Gets the value of the dateCompleted property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getDateCompleted() {
        return dateCompleted;
    }

    /**
     * Sets the value of the dateCompleted property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setDateCompleted(BetweenRestrictionOfNullableOfDateTime value) {
        this.dateCompleted = value;
    }

    /**
     * Gets the value of the outSourced property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfManufacturingOrderOutSourced }
     *     
     */
    public ListRestrictionOfNullableOfManufacturingOrderOutSourced getOutSourced() {
        return outSourced;
    }

    /**
     * Sets the value of the outSourced property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfManufacturingOrderOutSourced }
     *     
     */
    public void setOutSourced(ListRestrictionOfNullableOfManufacturingOrderOutSourced value) {
        this.outSourced = value;
    }

    /**
     * Gets the value of the postToSite property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getPostToSite() {
        return postToSite;
    }

    /**
     * Sets the value of the postToSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setPostToSite(LikeRestrictionOfString value) {
        this.postToSite = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public BetweenRestrictionOfNullableOfDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfDateTime }
     *     
     */
    public void setEndDate(BetweenRestrictionOfNullableOfDateTime value) {
        this.endDate = value;
    }

}
