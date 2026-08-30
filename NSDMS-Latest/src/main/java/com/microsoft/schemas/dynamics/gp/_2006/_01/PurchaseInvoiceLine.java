
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseInvoiceLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseInvoiceLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionLineKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseOrderKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseOrderLineKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseTransactionLineKey" minOccurs="0"/&gt;
 *         &lt;element name="QuantityInvoiced" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="VendorItemNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="IsNonInventory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="VarianceGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorItemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchasingTaxBasis"/&gt;
 *         &lt;element name="ItemTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemTaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="WarehouseTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="ProjectKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectKey" minOccurs="0"/&gt;
 *         &lt;element name="CostCategoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CostCategoryKey" minOccurs="0"/&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ExtendedCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="IsLandedCost" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="TaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="BackoutTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Receipts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPurchaseInvoiceApplyReceipt" minOccurs="0"/&gt;
 *         &lt;element name="Taxes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfPurchaseInvoiceTax" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseInvoiceLine", propOrder = {
    "key",
    "purchaseOrderKey",
    "purchaseOrderLineKey",
    "quantityInvoiced",
    "vendorItemNumber",
    "itemKey",
    "isNonInventory",
    "varianceGLAccountKey",
    "vendorItemDescription",
    "uofM",
    "taxBasis",
    "itemTaxScheduleKey",
    "warehouseTaxScheduleKey",
    "projectKey",
    "costCategoryKey",
    "unitCost",
    "extendedCost",
    "isLandedCost",
    "taxAmount",
    "backoutTaxAmount",
    "receipts",
    "taxes"
})
public class PurchaseInvoiceLine
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected PurchaseTransactionLineKey key;
    @XmlElement(name = "PurchaseOrderKey")
    protected PurchaseTransactionKey purchaseOrderKey;
    @XmlElement(name = "PurchaseOrderLineKey")
    protected PurchaseTransactionLineKey purchaseOrderLineKey;
    @XmlElement(name = "QuantityInvoiced")
    protected Quantity quantityInvoiced;
    @XmlElement(name = "VendorItemNumber")
    protected String vendorItemNumber;
    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "IsNonInventory", required = true, type = Boolean.class, nillable = true)
    protected Boolean isNonInventory;
    @XmlElement(name = "VarianceGLAccountKey")
    protected GLAccountNumberKey varianceGLAccountKey;
    @XmlElement(name = "VendorItemDescription")
    protected String vendorItemDescription;
    @XmlElement(name = "UofM")
    protected String uofM;
    @XmlElement(name = "TaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchasingTaxBasis taxBasis;
    @XmlElement(name = "ItemTaxScheduleKey")
    protected ItemTaxScheduleKey itemTaxScheduleKey;
    @XmlElement(name = "WarehouseTaxScheduleKey")
    protected TaxScheduleKey warehouseTaxScheduleKey;
    @XmlElement(name = "ProjectKey")
    protected ProjectKey projectKey;
    @XmlElement(name = "CostCategoryKey")
    protected CostCategoryKey costCategoryKey;
    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "ExtendedCost")
    protected MoneyAmount extendedCost;
    @XmlElement(name = "IsLandedCost", required = true, type = Boolean.class, nillable = true)
    protected Boolean isLandedCost;
    @XmlElement(name = "TaxAmount")
    protected MoneyAmount taxAmount;
    @XmlElement(name = "BackoutTaxAmount")
    protected MoneyAmount backoutTaxAmount;
    @XmlElement(name = "Receipts")
    protected ArrayOfPurchaseInvoiceApplyReceipt receipts;
    @XmlElement(name = "Taxes")
    protected ArrayOfPurchaseInvoiceTax taxes;

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
     * Gets the value of the quantityInvoiced property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantityInvoiced() {
        return quantityInvoiced;
    }

    /**
     * Sets the value of the quantityInvoiced property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantityInvoiced(Quantity value) {
        this.quantityInvoiced = value;
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
     * Gets the value of the varianceGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getVarianceGLAccountKey() {
        return varianceGLAccountKey;
    }

    /**
     * Sets the value of the varianceGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setVarianceGLAccountKey(GLAccountNumberKey value) {
        this.varianceGLAccountKey = value;
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
     * Gets the value of the taxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public PurchasingTaxBasis getTaxBasis() {
        return taxBasis;
    }

    /**
     * Sets the value of the taxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public void setTaxBasis(PurchasingTaxBasis value) {
        this.taxBasis = value;
    }

    /**
     * Gets the value of the itemTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link ItemTaxScheduleKey }
     *     
     */
    public ItemTaxScheduleKey getItemTaxScheduleKey() {
        return itemTaxScheduleKey;
    }

    /**
     * Sets the value of the itemTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemTaxScheduleKey }
     *     
     */
    public void setItemTaxScheduleKey(ItemTaxScheduleKey value) {
        this.itemTaxScheduleKey = value;
    }

    /**
     * Gets the value of the warehouseTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getWarehouseTaxScheduleKey() {
        return warehouseTaxScheduleKey;
    }

    /**
     * Sets the value of the warehouseTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setWarehouseTaxScheduleKey(TaxScheduleKey value) {
        this.warehouseTaxScheduleKey = value;
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
     * Gets the value of the isLandedCost property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsLandedCost() {
        return isLandedCost;
    }

    /**
     * Sets the value of the isLandedCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsLandedCost(Boolean value) {
        this.isLandedCost = value;
    }

    /**
     * Gets the value of the taxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTaxAmount(MoneyAmount value) {
        this.taxAmount = value;
    }

    /**
     * Gets the value of the backoutTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getBackoutTaxAmount() {
        return backoutTaxAmount;
    }

    /**
     * Sets the value of the backoutTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setBackoutTaxAmount(MoneyAmount value) {
        this.backoutTaxAmount = value;
    }

    /**
     * Gets the value of the receipts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPurchaseInvoiceApplyReceipt }
     *     
     */
    public ArrayOfPurchaseInvoiceApplyReceipt getReceipts() {
        return receipts;
    }

    /**
     * Sets the value of the receipts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPurchaseInvoiceApplyReceipt }
     *     
     */
    public void setReceipts(ArrayOfPurchaseInvoiceApplyReceipt value) {
        this.receipts = value;
    }

    /**
     * Gets the value of the taxes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPurchaseInvoiceTax }
     *     
     */
    public ArrayOfPurchaseInvoiceTax getTaxes() {
        return taxes;
    }

    /**
     * Sets the value of the taxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPurchaseInvoiceTax }
     *     
     */
    public void setTaxes(ArrayOfPurchaseInvoiceTax value) {
        this.taxes = value;
    }

}
