
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceCallPartLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceCallPartLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServicePartLine"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ConsolidateOnPO" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="QuantityAllocated" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="QuantitySold" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="IsOnReturnDocument" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsOnTransfer" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ReturnMaterialAuthorizationLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLineKey" minOccurs="0"/&gt;
 *         &lt;element name="TotalCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TransferFromWarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="TransferLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLineKey" minOccurs="0"/&gt;
 *         &lt;element name="TransferQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseOrderLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionLineKey" minOccurs="0"/&gt;
 *         &lt;element name="LotDetails" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceLot" minOccurs="0"/&gt;
 *         &lt;element name="SerialDetails" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceSerial" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceCallPartLine", propOrder = {
    "consolidateOnPO",
    "quantityAllocated",
    "quantitySold",
    "isOnReturnDocument",
    "isOnTransfer",
    "returnMaterialAuthorizationLineKey",
    "totalCost",
    "transferFromWarehouseKey",
    "transferLineKey",
    "transferQuantity",
    "purchaseOrderLineKey",
    "lotDetails",
    "serialDetails"
})
public class ServiceCallPartLine
    extends ServicePartLine
{

    @XmlElement(name = "ConsolidateOnPO", required = true, type = Boolean.class, nillable = true)
    protected Boolean consolidateOnPO;
    @XmlElement(name = "QuantityAllocated")
    protected Quantity quantityAllocated;
    @XmlElement(name = "QuantitySold")
    protected Quantity quantitySold;
    @XmlElement(name = "IsOnReturnDocument", required = true, type = Boolean.class, nillable = true)
    protected Boolean isOnReturnDocument;
    @XmlElement(name = "IsOnTransfer", required = true, type = Boolean.class, nillable = true)
    protected Boolean isOnTransfer;
    @XmlElement(name = "ReturnMaterialAuthorizationLineKey")
    protected ServiceLineKey returnMaterialAuthorizationLineKey;
    @XmlElement(name = "TotalCost")
    protected MoneyAmount totalCost;
    @XmlElement(name = "TransferFromWarehouseKey")
    protected WarehouseKey transferFromWarehouseKey;
    @XmlElement(name = "TransferLineKey")
    protected ServiceLineKey transferLineKey;
    @XmlElement(name = "TransferQuantity")
    protected Quantity transferQuantity;
    @XmlElement(name = "PurchaseOrderLineKey")
    protected PurchaseTransactionLineKey purchaseOrderLineKey;
    @XmlElement(name = "LotDetails")
    protected ArrayOfServiceLot lotDetails;
    @XmlElement(name = "SerialDetails")
    protected ArrayOfServiceSerial serialDetails;

    /**
     * Gets the value of the consolidateOnPO property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isConsolidateOnPO() {
        return consolidateOnPO;
    }

    /**
     * Sets the value of the consolidateOnPO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setConsolidateOnPO(Boolean value) {
        this.consolidateOnPO = value;
    }

    /**
     * Gets the value of the quantityAllocated property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityAllocated() {
        return quantityAllocated;
    }

    /**
     * Sets the value of the quantityAllocated property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityAllocated(Quantity value) {
        this.quantityAllocated = value;
    }

    /**
     * Gets the value of the quantitySold property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantitySold() {
        return quantitySold;
    }

    /**
     * Sets the value of the quantitySold property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantitySold(Quantity value) {
        this.quantitySold = value;
    }

    /**
     * Gets the value of the isOnReturnDocument property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOnReturnDocument() {
        return isOnReturnDocument;
    }

    /**
     * Sets the value of the isOnReturnDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOnReturnDocument(Boolean value) {
        this.isOnReturnDocument = value;
    }

    /**
     * Gets the value of the isOnTransfer property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOnTransfer() {
        return isOnTransfer;
    }

    /**
     * Sets the value of the isOnTransfer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOnTransfer(Boolean value) {
        this.isOnTransfer = value;
    }

    /**
     * Gets the value of the returnMaterialAuthorizationLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLineKey }
     *     
     */
    public ServiceLineKey getReturnMaterialAuthorizationLineKey() {
        return returnMaterialAuthorizationLineKey;
    }

    /**
     * Sets the value of the returnMaterialAuthorizationLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLineKey }
     *     
     */
    public void setReturnMaterialAuthorizationLineKey(ServiceLineKey value) {
        this.returnMaterialAuthorizationLineKey = value;
    }

    /**
     * Gets the value of the totalCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the value of the totalCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalCost(MoneyAmount value) {
        this.totalCost = value;
    }

    /**
     * Gets the value of the transferFromWarehouseKey property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getTransferFromWarehouseKey() {
        return transferFromWarehouseKey;
    }

    /**
     * Sets the value of the transferFromWarehouseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setTransferFromWarehouseKey(WarehouseKey value) {
        this.transferFromWarehouseKey = value;
    }

    /**
     * Gets the value of the transferLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLineKey }
     *     
     */
    public ServiceLineKey getTransferLineKey() {
        return transferLineKey;
    }

    /**
     * Sets the value of the transferLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLineKey }
     *     
     */
    public void setTransferLineKey(ServiceLineKey value) {
        this.transferLineKey = value;
    }

    /**
     * Gets the value of the transferQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getTransferQuantity() {
        return transferQuantity;
    }

    /**
     * Sets the value of the transferQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setTransferQuantity(Quantity value) {
        this.transferQuantity = value;
    }

    /**
     * Gets the value of the purchaseOrderLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseTransactionLineKey }
     *     
     */
    public PurchaseTransactionLineKey getPurchaseOrderLineKey() {
        return purchaseOrderLineKey;
    }

    /**
     * Sets the value of the purchaseOrderLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseTransactionLineKey }
     *     
     */
    public void setPurchaseOrderLineKey(PurchaseTransactionLineKey value) {
        this.purchaseOrderLineKey = value;
    }

    /**
     * Gets the value of the lotDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceLot }
     *     
     */
    public ArrayOfServiceLot getLotDetails() {
        return lotDetails;
    }

    /**
     * Sets the value of the lotDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceLot }
     *     
     */
    public void setLotDetails(ArrayOfServiceLot value) {
        this.lotDetails = value;
    }

    /**
     * Gets the value of the serialDetails property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceSerial }
     *     
     */
    public ArrayOfServiceSerial getSerialDetails() {
        return serialDetails;
    }

    /**
     * Sets the value of the serialDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceSerial }
     *     
     */
    public void setSerialDetails(ArrayOfServiceSerial value) {
        this.serialDetails = value;
    }

}
