
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PurchaseReceiptLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseReceiptLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionLineKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseOrderKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionKey" minOccurs="0"/&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="ItemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VendorItemNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VendorItemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ActualShipDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="InventoryGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="UofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ExtendedCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="IsNonInventory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="BillOfLadingNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="QuantityShipped" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="LandedCostGroupKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LandedCostGroupKey" minOccurs="0"/&gt;
 *         &lt;element name="WarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectKey" minOccurs="0"/&gt;
 *         &lt;element name="CostCategoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CostCategoryKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseOrderLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionLineKey" minOccurs="0"/&gt;
 *         &lt;element name="QuantityRejected" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="Lots" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPurchaseLotDetail" minOccurs="0"/&gt;
 *         &lt;element name="Serials" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPurchaseSerialDetail" minOccurs="0"/&gt;
 *         &lt;element name="Bins" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPurchaseBinDetail" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseReceiptLine", propOrder = {
    "key",
    "purchaseOrderKey",
    "itemKey",
    "itemDescription",
    "vendorItemNumber",
    "vendorItemDescription",
    "actualShipDate",
    "inventoryGLAccountKey",
    "uofM",
    "unitCost",
    "extendedCost",
    "isNonInventory",
    "billOfLadingNumber",
    "quantityShipped",
    "landedCostGroupKey",
    "warehouseKey",
    "projectKey",
    "costCategoryKey",
    "purchaseOrderLineKey",
    "quantityRejected",
    "lots",
    "serials",
    "bins"
})
public class PurchaseReceiptLine
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected PurchaseTransactionLineKey key;
    @XmlElement(name = "PurchaseOrderKey")
    protected PurchaseTransactionKey purchaseOrderKey;
    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "ItemDescription")
    protected String itemDescription;
    @XmlElement(name = "VendorItemNumber")
    protected String vendorItemNumber;
    @XmlElement(name = "VendorItemDescription")
    protected String vendorItemDescription;
    @XmlElement(name = "ActualShipDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar actualShipDate;
    @XmlElement(name = "InventoryGLAccountKey")
    protected GLAccountNumberKey inventoryGLAccountKey;
    @XmlElement(name = "UofM")
    protected String uofM;
    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "ExtendedCost")
    protected MoneyAmount extendedCost;
    @XmlElement(name = "IsNonInventory", required = true, type = Boolean.class, nillable = true)
    protected Boolean isNonInventory;
    @XmlElement(name = "BillOfLadingNumber")
    protected String billOfLadingNumber;
    @XmlElement(name = "QuantityShipped")
    protected Quantity quantityShipped;
    @XmlElement(name = "LandedCostGroupKey")
    protected LandedCostGroupKey landedCostGroupKey;
    @XmlElement(name = "WarehouseKey")
    protected WarehouseKey warehouseKey;
    @XmlElement(name = "ProjectKey")
    protected ProjectKey projectKey;
    @XmlElement(name = "CostCategoryKey")
    protected CostCategoryKey costCategoryKey;
    @XmlElement(name = "PurchaseOrderLineKey")
    protected PurchaseTransactionLineKey purchaseOrderLineKey;
    @XmlElement(name = "QuantityRejected")
    protected Quantity quantityRejected;
    @XmlElement(name = "Lots")
    protected ArrayOfPurchaseLotDetail lots;
    @XmlElement(name = "Serials")
    protected ArrayOfPurchaseSerialDetail serials;
    @XmlElement(name = "Bins")
    protected ArrayOfPurchaseBinDetail bins;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseTransactionLineKey }
     *     
     */
    public PurchaseTransactionLineKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseTransactionLineKey }
     *     
     */
    public void setKey(PurchaseTransactionLineKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the purchaseOrderKey property.
     * 
     * @return
     *     possible object is
     *     {@link PurchaseTransactionKey }
     *     
     */
    public PurchaseTransactionKey getPurchaseOrderKey() {
        return purchaseOrderKey;
    }

    /**
     * Sets the value of the purchaseOrderKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchaseTransactionKey }
     *     
     */
    public void setPurchaseOrderKey(PurchaseTransactionKey value) {
        this.purchaseOrderKey = value;
    }

    /**
     * Gets the value of the itemKey property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getItemKey() {
        return itemKey;
    }

    /**
     * Sets the value of the itemKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setItemKey(ItemKey value) {
        this.itemKey = value;
    }

    /**
     * Gets the value of the itemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Sets the value of the itemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemDescription(String value) {
        this.itemDescription = value;
    }

    /**
     * Gets the value of the vendorItemNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorItemNumber() {
        return vendorItemNumber;
    }

    /**
     * Sets the value of the vendorItemNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorItemNumber(String value) {
        this.vendorItemNumber = value;
    }

    /**
     * Gets the value of the vendorItemDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendorItemDescription() {
        return vendorItemDescription;
    }

    /**
     * Sets the value of the vendorItemDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendorItemDescription(String value) {
        this.vendorItemDescription = value;
    }

    /**
     * Gets the value of the actualShipDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActualShipDate() {
        return actualShipDate;
    }

    /**
     * Sets the value of the actualShipDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActualShipDate(XMLGregorianCalendar value) {
        this.actualShipDate = value;
    }

    /**
     * Gets the value of the inventoryGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getInventoryGLAccountKey() {
        return inventoryGLAccountKey;
    }

    /**
     * Sets the value of the inventoryGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setInventoryGLAccountKey(GLAccountNumberKey value) {
        this.inventoryGLAccountKey = value;
    }

    /**
     * Gets the value of the uofM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUofM() {
        return uofM;
    }

    /**
     * Sets the value of the uofM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUofM(String value) {
        this.uofM = value;
    }

    /**
     * Gets the value of the unitCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getUnitCost() {
        return unitCost;
    }

    /**
     * Sets the value of the unitCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setUnitCost(MoneyAmount value) {
        this.unitCost = value;
    }

    /**
     * Gets the value of the extendedCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getExtendedCost() {
        return extendedCost;
    }

    /**
     * Sets the value of the extendedCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setExtendedCost(MoneyAmount value) {
        this.extendedCost = value;
    }

    /**
     * Gets the value of the isNonInventory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsNonInventory() {
        return isNonInventory;
    }

    /**
     * Sets the value of the isNonInventory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsNonInventory(Boolean value) {
        this.isNonInventory = value;
    }

    /**
     * Gets the value of the billOfLadingNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillOfLadingNumber() {
        return billOfLadingNumber;
    }

    /**
     * Sets the value of the billOfLadingNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillOfLadingNumber(String value) {
        this.billOfLadingNumber = value;
    }

    /**
     * Gets the value of the quantityShipped property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityShipped() {
        return quantityShipped;
    }

    /**
     * Sets the value of the quantityShipped property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityShipped(Quantity value) {
        this.quantityShipped = value;
    }

    /**
     * Gets the value of the landedCostGroupKey property.
     * 
     * @return
     *     possible object is
     *     {@link LandedCostGroupKey }
     *     
     */
    public LandedCostGroupKey getLandedCostGroupKey() {
        return landedCostGroupKey;
    }

    /**
     * Sets the value of the landedCostGroupKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link LandedCostGroupKey }
     *     
     */
    public void setLandedCostGroupKey(LandedCostGroupKey value) {
        this.landedCostGroupKey = value;
    }

    /**
     * Gets the value of the warehouseKey property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getWarehouseKey() {
        return warehouseKey;
    }

    /**
     * Sets the value of the warehouseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setWarehouseKey(WarehouseKey value) {
        this.warehouseKey = value;
    }

    /**
     * Gets the value of the projectKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectKey }
     *     
     */
    public ProjectKey getProjectKey() {
        return projectKey;
    }

    /**
     * Sets the value of the projectKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectKey }
     *     
     */
    public void setProjectKey(ProjectKey value) {
        this.projectKey = value;
    }

    /**
     * Gets the value of the costCategoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link CostCategoryKey }
     *     
     */
    public CostCategoryKey getCostCategoryKey() {
        return costCategoryKey;
    }

    /**
     * Sets the value of the costCategoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CostCategoryKey }
     *     
     */
    public void setCostCategoryKey(CostCategoryKey value) {
        this.costCategoryKey = value;
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
     * Gets the value of the quantityRejected property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityRejected() {
        return quantityRejected;
    }

    /**
     * Sets the value of the quantityRejected property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityRejected(Quantity value) {
        this.quantityRejected = value;
    }

    /**
     * Gets the value of the lots property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPurchaseLotDetail }
     *     
     */
    public ArrayOfPurchaseLotDetail getLots() {
        return lots;
    }

    /**
     * Sets the value of the lots property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPurchaseLotDetail }
     *     
     */
    public void setLots(ArrayOfPurchaseLotDetail value) {
        this.lots = value;
    }

    /**
     * Gets the value of the serials property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPurchaseSerialDetail }
     *     
     */
    public ArrayOfPurchaseSerialDetail getSerials() {
        return serials;
    }

    /**
     * Sets the value of the serials property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPurchaseSerialDetail }
     *     
     */
    public void setSerials(ArrayOfPurchaseSerialDetail value) {
        this.serials = value;
    }

    /**
     * Gets the value of the bins property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPurchaseBinDetail }
     *     
     */
    public ArrayOfPurchaseBinDetail getBins() {
        return bins;
    }

    /**
     * Sets the value of the bins property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPurchaseBinDetail }
     *     
     */
    public void setBins(ArrayOfPurchaseBinDetail value) {
        this.bins = value;
    }

}
