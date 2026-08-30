
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ManufacturingOrderSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManufacturingOrderSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderDocumentSummary"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OutSourced" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderOutSourced"/&gt;
 *         &lt;element name="PostToSite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsArchivedMO" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="RoutingName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Priority" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderPriority"/&gt;
 *         &lt;element name="DateCompleted" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="PickNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsQuickMO" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManufacturingOrderSummary", propOrder = {
    "outSourced",
    "postToSite",
    "isArchivedMO",
    "routingName",
    "endDate",
    "lastModifiedDate",
    "priority",
    "dateCompleted",
    "pickNumber",
    "isQuickMO"
})
public class ManufacturingOrderSummary
    extends ManufacturingOrderDocumentSummary
{

    @XmlElement(name = "OutSourced", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ManufacturingOrderOutSourced outSourced;
    @XmlElement(name = "PostToSite")
    protected String postToSite;
    @XmlElement(name = "IsArchivedMO", required = true, type = Boolean.class, nillable = true)
    protected Boolean isArchivedMO;
    @XmlElement(name = "RoutingName")
    protected String routingName;
    @XmlElement(name = "EndDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "LastModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "Priority", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ManufacturingOrderPriority priority;
    @XmlElement(name = "DateCompleted", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateCompleted;
    @XmlElement(name = "PickNumber")
    protected String pickNumber;
    @XmlElement(name = "IsQuickMO", required = true, type = Boolean.class, nillable = true)
    protected Boolean isQuickMO;

    /**
     * Gets the value of the outSourced property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturingOrderOutSourced }
     *     
     */
    public ManufacturingOrderOutSourced getOutSourced() {
        return outSourced;
    }

    /**
     * Sets the value of the outSourced property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturingOrderOutSourced }
     *     
     */
    public void setOutSourced(ManufacturingOrderOutSourced value) {
        this.outSourced = value;
    }

    /**
     * Gets the value of the postToSite property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostToSite() {
        return postToSite;
    }

    /**
     * Sets the value of the postToSite property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostToSite(String value) {
        this.postToSite = value;
    }

    /**
     * Gets the value of the isArchivedMO property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsArchivedMO() {
        return isArchivedMO;
    }

    /**
     * Sets the value of the isArchivedMO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsArchivedMO(Boolean value) {
        this.isArchivedMO = value;
    }

    /**
     * Gets the value of the routingName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoutingName() {
        return routingName;
    }

    /**
     * Sets the value of the routingName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoutingName(String value) {
        this.routingName = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the lastModifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the value of the lastModifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastModifiedDate(XMLGregorianCalendar value) {
        this.lastModifiedDate = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturingOrderPriority }
     *     
     */
    public ManufacturingOrderPriority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturingOrderPriority }
     *     
     */
    public void setPriority(ManufacturingOrderPriority value) {
        this.priority = value;
    }

    /**
     * Gets the value of the dateCompleted property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateCompleted() {
        return dateCompleted;
    }

    /**
     * Sets the value of the dateCompleted property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateCompleted(XMLGregorianCalendar value) {
        this.dateCompleted = value;
    }

    /**
     * Gets the value of the pickNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPickNumber() {
        return pickNumber;
    }

    /**
     * Sets the value of the pickNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPickNumber(String value) {
        this.pickNumber = value;
    }

    /**
     * Gets the value of the isQuickMO property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsQuickMO() {
        return isQuickMO;
    }

    /**
     * Sets the value of the isQuickMO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsQuickMO(Boolean value) {
        this.isQuickMO = value;
    }

}
