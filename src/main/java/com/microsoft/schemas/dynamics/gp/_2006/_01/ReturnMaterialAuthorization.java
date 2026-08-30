
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ReturnMaterialAuthorization complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnMaterialAuthorization"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BillOfLading" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ClosedDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CommitDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IsFromServiceCall" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="OriginatingDocumentId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OriginatingDocumentType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationOriginatingDocumentType"/&gt;
 *         &lt;element name="ReasonCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationReasonCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="ReturnDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ReturnStatusCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationStatusCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="ReturnToAddress" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationReturnToAddress" minOccurs="0"/&gt;
 *         &lt;element name="ReturnTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="ReturnWarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="ShippingMethodKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ShippingMethodKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddress" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationShipToAddress" minOccurs="0"/&gt;
 *         &lt;element name="CreatedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsReceived" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ReasonCodeDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ReturnStatus" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationReturnStatus"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationType"/&gt;
 *         &lt;element name="Lines" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfReturnMaterialAuthorizationLine" minOccurs="0"/&gt;
 *         &lt;element name="Audits" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfReturnMaterialAuthorizationAudit" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnMaterialAuthorization", propOrder = {
    "billOfLading",
    "closedDateTime",
    "commitDateTime",
    "isFromServiceCall",
    "originatingDocumentId",
    "originatingDocumentType",
    "reasonCodeKey",
    "returnDateTime",
    "returnStatusCodeKey",
    "returnToAddress",
    "returnTypeKey",
    "returnWarehouseKey",
    "shippingMethodKey",
    "shipToAddress",
    "createdBy",
    "isReceived",
    "reasonCodeDescription",
    "returnStatus",
    "type",
    "lines",
    "audits"
})
public class ReturnMaterialAuthorization
    extends ServiceDocument
{

    @XmlElement(name = "BillOfLading")
    protected String billOfLading;
    @XmlElement(name = "ClosedDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar closedDateTime;
    @XmlElement(name = "CommitDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar commitDateTime;
    @XmlElement(name = "IsFromServiceCall", required = true, type = Boolean.class, nillable = true)
    protected Boolean isFromServiceCall;
    @XmlElement(name = "OriginatingDocumentId")
    protected String originatingDocumentId;
    @XmlElement(name = "OriginatingDocumentType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ReturnMaterialAuthorizationOriginatingDocumentType originatingDocumentType;
    @XmlElement(name = "ReasonCodeKey")
    protected ReturnMaterialAuthorizationReasonCodeKey reasonCodeKey;
    @XmlElement(name = "ReturnDateTime", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar returnDateTime;
    @XmlElement(name = "ReturnStatusCodeKey")
    protected ReturnMaterialAuthorizationStatusCodeKey returnStatusCodeKey;
    @XmlElement(name = "ReturnToAddress")
    protected ReturnMaterialAuthorizationReturnToAddress returnToAddress;
    @XmlElement(name = "ReturnTypeKey")
    protected ReturnMaterialAuthorizationTypeKey returnTypeKey;
    @XmlElement(name = "ReturnWarehouseKey")
    protected WarehouseKey returnWarehouseKey;
    @XmlElement(name = "ShippingMethodKey")
    protected ShippingMethodKey shippingMethodKey;
    @XmlElement(name = "ShipToAddress")
    protected ReturnMaterialAuthorizationShipToAddress shipToAddress;
    @XmlElement(name = "CreatedBy")
    protected String createdBy;
    @XmlElement(name = "IsReceived", required = true, type = Boolean.class, nillable = true)
    protected Boolean isReceived;
    @XmlElement(name = "ReasonCodeDescription")
    protected String reasonCodeDescription;
    @XmlElement(name = "ReturnStatus", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ReturnMaterialAuthorizationReturnStatus returnStatus;
    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected ReturnMaterialAuthorizationType type;
    @XmlElement(name = "Lines")
    protected ArrayOfReturnMaterialAuthorizationLine lines;
    @XmlElement(name = "Audits")
    protected ArrayOfReturnMaterialAuthorizationAudit audits;

    /**
     * Gets the value of the billOfLading property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillOfLading() {
        return billOfLading;
    }

    /**
     * Sets the value of the billOfLading property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillOfLading(String value) {
        this.billOfLading = value;
    }

    /**
     * Gets the value of the closedDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getClosedDateTime() {
        return closedDateTime;
    }

    /**
     * Sets the value of the closedDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setClosedDateTime(XMLGregorianCalendar value) {
        this.closedDateTime = value;
    }

    /**
     * Gets the value of the commitDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCommitDateTime() {
        return commitDateTime;
    }

    /**
     * Sets the value of the commitDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCommitDateTime(XMLGregorianCalendar value) {
        this.commitDateTime = value;
    }

    /**
     * Gets the value of the isFromServiceCall property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsFromServiceCall() {
        return isFromServiceCall;
    }

    /**
     * Sets the value of the isFromServiceCall property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsFromServiceCall(Boolean value) {
        this.isFromServiceCall = value;
    }

    /**
     * Gets the value of the originatingDocumentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginatingDocumentId() {
        return originatingDocumentId;
    }

    /**
     * Sets the value of the originatingDocumentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginatingDocumentId(String value) {
        this.originatingDocumentId = value;
    }

    /**
     * Gets the value of the originatingDocumentType property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationOriginatingDocumentType }
     *     
     */
    public ReturnMaterialAuthorizationOriginatingDocumentType getOriginatingDocumentType() {
        return originatingDocumentType;
    }

    /**
     * Sets the value of the originatingDocumentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationOriginatingDocumentType }
     *     
     */
    public void setOriginatingDocumentType(ReturnMaterialAuthorizationOriginatingDocumentType value) {
        this.originatingDocumentType = value;
    }

    /**
     * Gets the value of the reasonCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationReasonCodeKey }
     *     
     */
    public ReturnMaterialAuthorizationReasonCodeKey getReasonCodeKey() {
        return reasonCodeKey;
    }

    /**
     * Sets the value of the reasonCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationReasonCodeKey }
     *     
     */
    public void setReasonCodeKey(ReturnMaterialAuthorizationReasonCodeKey value) {
        this.reasonCodeKey = value;
    }

    /**
     * Gets the value of the returnDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReturnDateTime() {
        return returnDateTime;
    }

    /**
     * Sets the value of the returnDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReturnDateTime(XMLGregorianCalendar value) {
        this.returnDateTime = value;
    }

    /**
     * Gets the value of the returnStatusCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationStatusCodeKey }
     *     
     */
    public ReturnMaterialAuthorizationStatusCodeKey getReturnStatusCodeKey() {
        return returnStatusCodeKey;
    }

    /**
     * Sets the value of the returnStatusCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationStatusCodeKey }
     *     
     */
    public void setReturnStatusCodeKey(ReturnMaterialAuthorizationStatusCodeKey value) {
        this.returnStatusCodeKey = value;
    }

    /**
     * Gets the value of the returnToAddress property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationReturnToAddress }
     *     
     */
    public ReturnMaterialAuthorizationReturnToAddress getReturnToAddress() {
        return returnToAddress;
    }

    /**
     * Sets the value of the returnToAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationReturnToAddress }
     *     
     */
    public void setReturnToAddress(ReturnMaterialAuthorizationReturnToAddress value) {
        this.returnToAddress = value;
    }

    /**
     * Gets the value of the returnTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationTypeKey }
     *     
     */
    public ReturnMaterialAuthorizationTypeKey getReturnTypeKey() {
        return returnTypeKey;
    }

    /**
     * Sets the value of the returnTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationTypeKey }
     *     
     */
    public void setReturnTypeKey(ReturnMaterialAuthorizationTypeKey value) {
        this.returnTypeKey = value;
    }

    /**
     * Gets the value of the returnWarehouseKey property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getReturnWarehouseKey() {
        return returnWarehouseKey;
    }

    /**
     * Sets the value of the returnWarehouseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setReturnWarehouseKey(WarehouseKey value) {
        this.returnWarehouseKey = value;
    }

    /**
     * Gets the value of the shippingMethodKey property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingMethodKey }
     *     
     */
    public ShippingMethodKey getShippingMethodKey() {
        return shippingMethodKey;
    }

    /**
     * Sets the value of the shippingMethodKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingMethodKey }
     *     
     */
    public void setShippingMethodKey(ShippingMethodKey value) {
        this.shippingMethodKey = value;
    }

    /**
     * Gets the value of the shipToAddress property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationShipToAddress }
     *     
     */
    public ReturnMaterialAuthorizationShipToAddress getShipToAddress() {
        return shipToAddress;
    }

    /**
     * Sets the value of the shipToAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationShipToAddress }
     *     
     */
    public void setShipToAddress(ReturnMaterialAuthorizationShipToAddress value) {
        this.shipToAddress = value;
    }

    /**
     * Gets the value of the createdBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the value of the createdBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedBy(String value) {
        this.createdBy = value;
    }

    /**
     * Gets the value of the isReceived property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsReceived() {
        return isReceived;
    }

    /**
     * Sets the value of the isReceived property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsReceived(Boolean value) {
        this.isReceived = value;
    }

    /**
     * Gets the value of the reasonCodeDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonCodeDescription() {
        return reasonCodeDescription;
    }

    /**
     * Sets the value of the reasonCodeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonCodeDescription(String value) {
        this.reasonCodeDescription = value;
    }

    /**
     * Gets the value of the returnStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationReturnStatus }
     *     
     */
    public ReturnMaterialAuthorizationReturnStatus getReturnStatus() {
        return returnStatus;
    }

    /**
     * Sets the value of the returnStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationReturnStatus }
     *     
     */
    public void setReturnStatus(ReturnMaterialAuthorizationReturnStatus value) {
        this.returnStatus = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationType }
     *     
     */
    public ReturnMaterialAuthorizationType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationType }
     *     
     */
    public void setType(ReturnMaterialAuthorizationType value) {
        this.type = value;
    }

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfReturnMaterialAuthorizationLine }
     *     
     */
    public ArrayOfReturnMaterialAuthorizationLine getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfReturnMaterialAuthorizationLine }
     *     
     */
    public void setLines(ArrayOfReturnMaterialAuthorizationLine value) {
        this.lines = value;
    }

    /**
     * Gets the value of the audits property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfReturnMaterialAuthorizationAudit }
     *     
     */
    public ArrayOfReturnMaterialAuthorizationAudit getAudits() {
        return audits;
    }

    /**
     * Sets the value of the audits property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfReturnMaterialAuthorizationAudit }
     *     
     */
    public void setAudits(ArrayOfReturnMaterialAuthorizationAudit value) {
        this.audits = value;
    }

}
