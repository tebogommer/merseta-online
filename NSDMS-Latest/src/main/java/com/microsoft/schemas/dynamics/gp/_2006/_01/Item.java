
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Item complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Item"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ShortDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="GenericDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ClassKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemClassKey" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemType"/&gt;
 *         &lt;element name="SalesTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTaxBasis"/&gt;
 *         &lt;element name="SalesTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="UofMScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}UofMScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="ShippingWeight" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="QuantityDecimalPlaces" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}QuantityDecimalPlaces"/&gt;
 *         &lt;element name="CurrencyDecimalPlaces" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyDecimalPlaces"/&gt;
 *         &lt;element name="PurchaseTaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchasingTaxBasis"/&gt;
 *         &lt;element name="PurchaseTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="StandardCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="CurrentCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="SubstituteItem1Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="SubstituteItem2Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="IncludeInDemandPlanning" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="AllowBackOrder" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="WarrantyDays" type="{http://www.w3.org/2001/XMLSchema}short"/&gt;
 *         &lt;element name="ABCCode" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ABCCode"/&gt;
 *         &lt;element name="UserCategoryList1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserCategoryList2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserCategoryList3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserCategoryList4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserCategoryList5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserCategoryList6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="KeepCalendarYearHistory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="KeepFiscalYearHistory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="KeepTransactionHistory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="KeepDistributionHistory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="InventoryGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="InventoryOffsetGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="CostofGoodsSoldGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="MarkdownGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesReturnGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="InUseGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="InServiceGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="DamagedGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="VarianceGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="DropShipGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchasePriceVarianceGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="UnrealizedPurchasePriceVarianceGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="InventoryReturnGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="AssemblyVarianceGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchaseUofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DefaultWarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="PriceMethod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PriceMethod"/&gt;
 *         &lt;element name="FunctionalCurrencyDecimalPlaces" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyDecimalPlaces"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="LastModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="CreatedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IsDiscontinued" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DefaultPriceLevelKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PriceLevelKey" minOccurs="0"/&gt;
 *         &lt;element name="DefaultSellingUofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="InternetAddresses" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}InternetAddresses" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Item", propOrder = {
    "key",
    "description",
    "shortDescription",
    "genericDescription",
    "classKey",
    "type",
    "salesTaxBasis",
    "salesTaxScheduleKey",
    "uofMScheduleKey",
    "shippingWeight",
    "quantityDecimalPlaces",
    "currencyDecimalPlaces",
    "purchaseTaxBasis",
    "purchaseTaxScheduleKey",
    "standardCost",
    "currentCost",
    "substituteItem1Key",
    "substituteItem2Key",
    "includeInDemandPlanning",
    "allowBackOrder",
    "warrantyDays",
    "abcCode",
    "userCategoryList1",
    "userCategoryList2",
    "userCategoryList3",
    "userCategoryList4",
    "userCategoryList5",
    "userCategoryList6",
    "keepCalendarYearHistory",
    "keepFiscalYearHistory",
    "keepTransactionHistory",
    "keepDistributionHistory",
    "inventoryGLAccountKey",
    "inventoryOffsetGLAccountKey",
    "costofGoodsSoldGLAccountKey",
    "salesGLAccountKey",
    "markdownGLAccountKey",
    "salesReturnGLAccountKey",
    "inUseGLAccountKey",
    "inServiceGLAccountKey",
    "damagedGLAccountKey",
    "varianceGLAccountKey",
    "dropShipGLAccountKey",
    "purchasePriceVarianceGLAccountKey",
    "unrealizedPurchasePriceVarianceGLAccountKey",
    "inventoryReturnGLAccountKey",
    "assemblyVarianceGLAccountKey",
    "purchaseUofM",
    "defaultWarehouseKey",
    "priceMethod",
    "functionalCurrencyDecimalPlaces",
    "modifiedDate",
    "lastModifiedDate",
    "createdDate",
    "isDiscontinued",
    "defaultPriceLevelKey",
    "defaultSellingUofM",
    "internetAddresses"
})
@XmlSeeAlso({
    Fee.class,
    Service.class,
    InventoriedItem.class
})
public abstract class Item
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ItemKey key;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "ShortDescription")
    protected String shortDescription;
    @XmlElement(name = "GenericDescription")
    protected String genericDescription;
    @XmlElement(name = "ClassKey")
    protected ItemClassKey classKey;
    @XmlElement(name = "Type", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ItemType type;
    @XmlElement(name = "SalesTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalesTaxBasis salesTaxBasis;
    @XmlElement(name = "SalesTaxScheduleKey")
    protected TaxScheduleKey salesTaxScheduleKey;
    @XmlElement(name = "UofMScheduleKey")
    protected UofMScheduleKey uofMScheduleKey;
    @XmlElement(name = "ShippingWeight", required = true, nillable = true)
    protected BigDecimal shippingWeight;
    @XmlElement(name = "QuantityDecimalPlaces", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected QuantityDecimalPlaces quantityDecimalPlaces;
    @XmlElement(name = "CurrencyDecimalPlaces", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CurrencyDecimalPlaces currencyDecimalPlaces;
    @XmlElement(name = "PurchaseTaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PurchasingTaxBasis purchaseTaxBasis;
    @XmlElement(name = "PurchaseTaxScheduleKey")
    protected TaxScheduleKey purchaseTaxScheduleKey;
    @XmlElement(name = "StandardCost")
    protected MoneyAmount standardCost;
    @XmlElement(name = "CurrentCost")
    protected MoneyAmount currentCost;
    @XmlElement(name = "SubstituteItem1Key")
    protected ItemKey substituteItem1Key;
    @XmlElement(name = "SubstituteItem2Key")
    protected ItemKey substituteItem2Key;
    @XmlElement(name = "IncludeInDemandPlanning", required = true, type = Boolean.class, nillable = true)
    protected Boolean includeInDemandPlanning;
    @XmlElement(name = "AllowBackOrder", required = true, type = Boolean.class, nillable = true)
    protected Boolean allowBackOrder;
    @XmlElement(name = "WarrantyDays", required = true, type = Short.class, nillable = true)
    protected Short warrantyDays;
    @XmlElement(name = "ABCCode", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ABCCode abcCode;
    @XmlElement(name = "UserCategoryList1")
    protected String userCategoryList1;
    @XmlElement(name = "UserCategoryList2")
    protected String userCategoryList2;
    @XmlElement(name = "UserCategoryList3")
    protected String userCategoryList3;
    @XmlElement(name = "UserCategoryList4")
    protected String userCategoryList4;
    @XmlElement(name = "UserCategoryList5")
    protected String userCategoryList5;
    @XmlElement(name = "UserCategoryList6")
    protected String userCategoryList6;
    @XmlElement(name = "KeepCalendarYearHistory", required = true, type = Boolean.class, nillable = true)
    protected Boolean keepCalendarYearHistory;
    @XmlElement(name = "KeepFiscalYearHistory", required = true, type = Boolean.class, nillable = true)
    protected Boolean keepFiscalYearHistory;
    @XmlElement(name = "KeepTransactionHistory", required = true, type = Boolean.class, nillable = true)
    protected Boolean keepTransactionHistory;
    @XmlElement(name = "KeepDistributionHistory", required = true, type = Boolean.class, nillable = true)
    protected Boolean keepDistributionHistory;
    @XmlElement(name = "InventoryGLAccountKey")
    protected GLAccountNumberKey inventoryGLAccountKey;
    @XmlElement(name = "InventoryOffsetGLAccountKey")
    protected GLAccountNumberKey inventoryOffsetGLAccountKey;
    @XmlElement(name = "CostofGoodsSoldGLAccountKey")
    protected GLAccountNumberKey costofGoodsSoldGLAccountKey;
    @XmlElement(name = "SalesGLAccountKey")
    protected GLAccountNumberKey salesGLAccountKey;
    @XmlElement(name = "MarkdownGLAccountKey")
    protected GLAccountNumberKey markdownGLAccountKey;
    @XmlElement(name = "SalesReturnGLAccountKey")
    protected GLAccountNumberKey salesReturnGLAccountKey;
    @XmlElement(name = "InUseGLAccountKey")
    protected GLAccountNumberKey inUseGLAccountKey;
    @XmlElement(name = "InServiceGLAccountKey")
    protected GLAccountNumberKey inServiceGLAccountKey;
    @XmlElement(name = "DamagedGLAccountKey")
    protected GLAccountNumberKey damagedGLAccountKey;
    @XmlElement(name = "VarianceGLAccountKey")
    protected GLAccountNumberKey varianceGLAccountKey;
    @XmlElement(name = "DropShipGLAccountKey")
    protected GLAccountNumberKey dropShipGLAccountKey;
    @XmlElement(name = "PurchasePriceVarianceGLAccountKey")
    protected GLAccountNumberKey purchasePriceVarianceGLAccountKey;
    @XmlElement(name = "UnrealizedPurchasePriceVarianceGLAccountKey")
    protected GLAccountNumberKey unrealizedPurchasePriceVarianceGLAccountKey;
    @XmlElement(name = "InventoryReturnGLAccountKey")
    protected GLAccountNumberKey inventoryReturnGLAccountKey;
    @XmlElement(name = "AssemblyVarianceGLAccountKey")
    protected GLAccountNumberKey assemblyVarianceGLAccountKey;
    @XmlElement(name = "PurchaseUofM")
    protected String purchaseUofM;
    @XmlElement(name = "DefaultWarehouseKey")
    protected WarehouseKey defaultWarehouseKey;
    @XmlElement(name = "PriceMethod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PriceMethod priceMethod;
    @XmlElement(name = "FunctionalCurrencyDecimalPlaces", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CurrencyDecimalPlaces functionalCurrencyDecimalPlaces;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "LastModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifiedDate;
    @XmlElement(name = "CreatedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdDate;
    @XmlElement(name = "IsDiscontinued", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDiscontinued;
    @XmlElement(name = "DefaultPriceLevelKey")
    protected PriceLevelKey defaultPriceLevelKey;
    @XmlElement(name = "DefaultSellingUofM")
    protected String defaultSellingUofM;
    @XmlElement(name = "InternetAddresses")
    protected InternetAddresses internetAddresses;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setKey(ItemKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the shortDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortDescription(String value) {
        this.shortDescription = value;
    }

    /**
     * Gets the value of the genericDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenericDescription() {
        return genericDescription;
    }

    /**
     * Sets the value of the genericDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenericDescription(String value) {
        this.genericDescription = value;
    }

    /**
     * Gets the value of the classKey property.
     * 
     * @return
     *     possible object is
     *     {@link ItemClassKey }
     *     
     */
    public ItemClassKey getClassKey() {
        return classKey;
    }

    /**
     * Sets the value of the classKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemClassKey }
     *     
     */
    public void setClassKey(ItemClassKey value) {
        this.classKey = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ItemType }
     *     
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemType }
     *     
     */
    public void setType(ItemType value) {
        this.type = value;
    }

    /**
     * Gets the value of the salesTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTaxBasis }
     *     
     */
    public SalesTaxBasis getSalesTaxBasis() {
        return salesTaxBasis;
    }

    /**
     * Sets the value of the salesTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTaxBasis }
     *     
     */
    public void setSalesTaxBasis(SalesTaxBasis value) {
        this.salesTaxBasis = value;
    }

    /**
     * Gets the value of the salesTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getSalesTaxScheduleKey() {
        return salesTaxScheduleKey;
    }

    /**
     * Sets the value of the salesTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setSalesTaxScheduleKey(TaxScheduleKey value) {
        this.salesTaxScheduleKey = value;
    }

    /**
     * Gets the value of the uofMScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link UofMScheduleKey }
     *     
     */
    public UofMScheduleKey getUofMScheduleKey() {
        return uofMScheduleKey;
    }

    /**
     * Sets the value of the uofMScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link UofMScheduleKey }
     *     
     */
    public void setUofMScheduleKey(UofMScheduleKey value) {
        this.uofMScheduleKey = value;
    }

    /**
     * Gets the value of the shippingWeight property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getShippingWeight() {
        return shippingWeight;
    }

    /**
     * Sets the value of the shippingWeight property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setShippingWeight(BigDecimal value) {
        this.shippingWeight = value;
    }

    /**
     * Gets the value of the quantityDecimalPlaces property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityDecimalPlaces }
     *     
     */
    public QuantityDecimalPlaces getQuantityDecimalPlaces() {
        return quantityDecimalPlaces;
    }

    /**
     * Sets the value of the quantityDecimalPlaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityDecimalPlaces }
     *     
     */
    public void setQuantityDecimalPlaces(QuantityDecimalPlaces value) {
        this.quantityDecimalPlaces = value;
    }

    /**
     * Gets the value of the currencyDecimalPlaces property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyDecimalPlaces }
     *     
     */
    public CurrencyDecimalPlaces getCurrencyDecimalPlaces() {
        return currencyDecimalPlaces;
    }

    /**
     * Sets the value of the currencyDecimalPlaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyDecimalPlaces }
     *     
     */
    public void setCurrencyDecimalPlaces(CurrencyDecimalPlaces value) {
        this.currencyDecimalPlaces = value;
    }

    /**
     * Gets the value of the purchaseTaxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public PurchasingTaxBasis getPurchaseTaxBasis() {
        return purchaseTaxBasis;
    }

    /**
     * Sets the value of the purchaseTaxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link PurchasingTaxBasis }
     *     
     */
    public void setPurchaseTaxBasis(PurchasingTaxBasis value) {
        this.purchaseTaxBasis = value;
    }

    /**
     * Gets the value of the purchaseTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getPurchaseTaxScheduleKey() {
        return purchaseTaxScheduleKey;
    }

    /**
     * Sets the value of the purchaseTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setPurchaseTaxScheduleKey(TaxScheduleKey value) {
        this.purchaseTaxScheduleKey = value;
    }

    /**
     * Gets the value of the standardCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getStandardCost() {
        return standardCost;
    }

    /**
     * Sets the value of the standardCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setStandardCost(MoneyAmount value) {
        this.standardCost = value;
    }

    /**
     * Gets the value of the currentCost property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getCurrentCost() {
        return currentCost;
    }

    /**
     * Sets the value of the currentCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setCurrentCost(MoneyAmount value) {
        this.currentCost = value;
    }

    /**
     * Gets the value of the substituteItem1Key property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getSubstituteItem1Key() {
        return substituteItem1Key;
    }

    /**
     * Sets the value of the substituteItem1Key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setSubstituteItem1Key(ItemKey value) {
        this.substituteItem1Key = value;
    }

    /**
     * Gets the value of the substituteItem2Key property.
     * 
     * @return
     *     possible object is
     *     {@link ItemKey }
     *     
     */
    public ItemKey getSubstituteItem2Key() {
        return substituteItem2Key;
    }

    /**
     * Sets the value of the substituteItem2Key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemKey }
     *     
     */
    public void setSubstituteItem2Key(ItemKey value) {
        this.substituteItem2Key = value;
    }

    /**
     * Gets the value of the includeInDemandPlanning property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIncludeInDemandPlanning() {
        return includeInDemandPlanning;
    }

    /**
     * Sets the value of the includeInDemandPlanning property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeInDemandPlanning(Boolean value) {
        this.includeInDemandPlanning = value;
    }

    /**
     * Gets the value of the allowBackOrder property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowBackOrder() {
        return allowBackOrder;
    }

    /**
     * Sets the value of the allowBackOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowBackOrder(Boolean value) {
        this.allowBackOrder = value;
    }

    /**
     * Gets the value of the warrantyDays property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getWarrantyDays() {
        return warrantyDays;
    }

    /**
     * Sets the value of the warrantyDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setWarrantyDays(Short value) {
        this.warrantyDays = value;
    }

    /**
     * Gets the value of the abcCode property.
     * 
     * @return
     *     possible object is
     *     {@link ABCCode }
     *     
     */
    public ABCCode getABCCode() {
        return abcCode;
    }

    /**
     * Sets the value of the abcCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ABCCode }
     *     
     */
    public void setABCCode(ABCCode value) {
        this.abcCode = value;
    }

    /**
     * Gets the value of the userCategoryList1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserCategoryList1() {
        return userCategoryList1;
    }

    /**
     * Sets the value of the userCategoryList1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserCategoryList1(String value) {
        this.userCategoryList1 = value;
    }

    /**
     * Gets the value of the userCategoryList2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserCategoryList2() {
        return userCategoryList2;
    }

    /**
     * Sets the value of the userCategoryList2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserCategoryList2(String value) {
        this.userCategoryList2 = value;
    }

    /**
     * Gets the value of the userCategoryList3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserCategoryList3() {
        return userCategoryList3;
    }

    /**
     * Sets the value of the userCategoryList3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserCategoryList3(String value) {
        this.userCategoryList3 = value;
    }

    /**
     * Gets the value of the userCategoryList4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserCategoryList4() {
        return userCategoryList4;
    }

    /**
     * Sets the value of the userCategoryList4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserCategoryList4(String value) {
        this.userCategoryList4 = value;
    }

    /**
     * Gets the value of the userCategoryList5 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserCategoryList5() {
        return userCategoryList5;
    }

    /**
     * Sets the value of the userCategoryList5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserCategoryList5(String value) {
        this.userCategoryList5 = value;
    }

    /**
     * Gets the value of the userCategoryList6 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserCategoryList6() {
        return userCategoryList6;
    }

    /**
     * Sets the value of the userCategoryList6 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserCategoryList6(String value) {
        this.userCategoryList6 = value;
    }

    /**
     * Gets the value of the keepCalendarYearHistory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeepCalendarYearHistory() {
        return keepCalendarYearHistory;
    }

    /**
     * Sets the value of the keepCalendarYearHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepCalendarYearHistory(Boolean value) {
        this.keepCalendarYearHistory = value;
    }

    /**
     * Gets the value of the keepFiscalYearHistory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeepFiscalYearHistory() {
        return keepFiscalYearHistory;
    }

    /**
     * Sets the value of the keepFiscalYearHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepFiscalYearHistory(Boolean value) {
        this.keepFiscalYearHistory = value;
    }

    /**
     * Gets the value of the keepTransactionHistory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeepTransactionHistory() {
        return keepTransactionHistory;
    }

    /**
     * Sets the value of the keepTransactionHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepTransactionHistory(Boolean value) {
        this.keepTransactionHistory = value;
    }

    /**
     * Gets the value of the keepDistributionHistory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeepDistributionHistory() {
        return keepDistributionHistory;
    }

    /**
     * Sets the value of the keepDistributionHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepDistributionHistory(Boolean value) {
        this.keepDistributionHistory = value;
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
     * Gets the value of the inventoryOffsetGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getInventoryOffsetGLAccountKey() {
        return inventoryOffsetGLAccountKey;
    }

    /**
     * Sets the value of the inventoryOffsetGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setInventoryOffsetGLAccountKey(GLAccountNumberKey value) {
        this.inventoryOffsetGLAccountKey = value;
    }

    /**
     * Gets the value of the costofGoodsSoldGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getCostofGoodsSoldGLAccountKey() {
        return costofGoodsSoldGLAccountKey;
    }

    /**
     * Sets the value of the costofGoodsSoldGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setCostofGoodsSoldGLAccountKey(GLAccountNumberKey value) {
        this.costofGoodsSoldGLAccountKey = value;
    }

    /**
     * Gets the value of the salesGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getSalesGLAccountKey() {
        return salesGLAccountKey;
    }

    /**
     * Sets the value of the salesGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setSalesGLAccountKey(GLAccountNumberKey value) {
        this.salesGLAccountKey = value;
    }

    /**
     * Gets the value of the markdownGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getMarkdownGLAccountKey() {
        return markdownGLAccountKey;
    }

    /**
     * Sets the value of the markdownGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setMarkdownGLAccountKey(GLAccountNumberKey value) {
        this.markdownGLAccountKey = value;
    }

    /**
     * Gets the value of the salesReturnGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getSalesReturnGLAccountKey() {
        return salesReturnGLAccountKey;
    }

    /**
     * Sets the value of the salesReturnGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setSalesReturnGLAccountKey(GLAccountNumberKey value) {
        this.salesReturnGLAccountKey = value;
    }

    /**
     * Gets the value of the inUseGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getInUseGLAccountKey() {
        return inUseGLAccountKey;
    }

    /**
     * Sets the value of the inUseGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setInUseGLAccountKey(GLAccountNumberKey value) {
        this.inUseGLAccountKey = value;
    }

    /**
     * Gets the value of the inServiceGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getInServiceGLAccountKey() {
        return inServiceGLAccountKey;
    }

    /**
     * Sets the value of the inServiceGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setInServiceGLAccountKey(GLAccountNumberKey value) {
        this.inServiceGLAccountKey = value;
    }

    /**
     * Gets the value of the damagedGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getDamagedGLAccountKey() {
        return damagedGLAccountKey;
    }

    /**
     * Sets the value of the damagedGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setDamagedGLAccountKey(GLAccountNumberKey value) {
        this.damagedGLAccountKey = value;
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
     * Gets the value of the dropShipGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getDropShipGLAccountKey() {
        return dropShipGLAccountKey;
    }

    /**
     * Sets the value of the dropShipGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setDropShipGLAccountKey(GLAccountNumberKey value) {
        this.dropShipGLAccountKey = value;
    }

    /**
     * Gets the value of the purchasePriceVarianceGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getPurchasePriceVarianceGLAccountKey() {
        return purchasePriceVarianceGLAccountKey;
    }

    /**
     * Sets the value of the purchasePriceVarianceGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setPurchasePriceVarianceGLAccountKey(GLAccountNumberKey value) {
        this.purchasePriceVarianceGLAccountKey = value;
    }

    /**
     * Gets the value of the unrealizedPurchasePriceVarianceGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getUnrealizedPurchasePriceVarianceGLAccountKey() {
        return unrealizedPurchasePriceVarianceGLAccountKey;
    }

    /**
     * Sets the value of the unrealizedPurchasePriceVarianceGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setUnrealizedPurchasePriceVarianceGLAccountKey(GLAccountNumberKey value) {
        this.unrealizedPurchasePriceVarianceGLAccountKey = value;
    }

    /**
     * Gets the value of the inventoryReturnGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getInventoryReturnGLAccountKey() {
        return inventoryReturnGLAccountKey;
    }

    /**
     * Sets the value of the inventoryReturnGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setInventoryReturnGLAccountKey(GLAccountNumberKey value) {
        this.inventoryReturnGLAccountKey = value;
    }

    /**
     * Gets the value of the assemblyVarianceGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getAssemblyVarianceGLAccountKey() {
        return assemblyVarianceGLAccountKey;
    }

    /**
     * Sets the value of the assemblyVarianceGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setAssemblyVarianceGLAccountKey(GLAccountNumberKey value) {
        this.assemblyVarianceGLAccountKey = value;
    }

    /**
     * Gets the value of the purchaseUofM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurchaseUofM() {
        return purchaseUofM;
    }

    /**
     * Sets the value of the purchaseUofM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurchaseUofM(String value) {
        this.purchaseUofM = value;
    }

    /**
     * Gets the value of the defaultWarehouseKey property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getDefaultWarehouseKey() {
        return defaultWarehouseKey;
    }

    /**
     * Sets the value of the defaultWarehouseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setDefaultWarehouseKey(WarehouseKey value) {
        this.defaultWarehouseKey = value;
    }

    /**
     * Gets the value of the priceMethod property.
     * 
     * @return
     *     possible object is
     *     {@link PriceMethod }
     *     
     */
    public PriceMethod getPriceMethod() {
        return priceMethod;
    }

    /**
     * Sets the value of the priceMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceMethod }
     *     
     */
    public void setPriceMethod(PriceMethod value) {
        this.priceMethod = value;
    }

    /**
     * Gets the value of the functionalCurrencyDecimalPlaces property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyDecimalPlaces }
     *     
     */
    public CurrencyDecimalPlaces getFunctionalCurrencyDecimalPlaces() {
        return functionalCurrencyDecimalPlaces;
    }

    /**
     * Sets the value of the functionalCurrencyDecimalPlaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyDecimalPlaces }
     *     
     */
    public void setFunctionalCurrencyDecimalPlaces(CurrencyDecimalPlaces value) {
        this.functionalCurrencyDecimalPlaces = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
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
     * Gets the value of the createdDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the value of the createdDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedDate(XMLGregorianCalendar value) {
        this.createdDate = value;
    }

    /**
     * Gets the value of the isDiscontinued property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDiscontinued() {
        return isDiscontinued;
    }

    /**
     * Sets the value of the isDiscontinued property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDiscontinued(Boolean value) {
        this.isDiscontinued = value;
    }

    /**
     * Gets the value of the defaultPriceLevelKey property.
     * 
     * @return
     *     possible object is
     *     {@link PriceLevelKey }
     *     
     */
    public PriceLevelKey getDefaultPriceLevelKey() {
        return defaultPriceLevelKey;
    }

    /**
     * Sets the value of the defaultPriceLevelKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceLevelKey }
     *     
     */
    public void setDefaultPriceLevelKey(PriceLevelKey value) {
        this.defaultPriceLevelKey = value;
    }

    /**
     * Gets the value of the defaultSellingUofM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultSellingUofM() {
        return defaultSellingUofM;
    }

    /**
     * Sets the value of the defaultSellingUofM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultSellingUofM(String value) {
        this.defaultSellingUofM = value;
    }

    /**
     * Gets the value of the internetAddresses property.
     * 
     * @return
     *     possible object is
     *     {@link InternetAddresses }
     *     
     */
    public InternetAddresses getInternetAddresses() {
        return internetAddresses;
    }

    /**
     * Sets the value of the internetAddresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternetAddresses }
     *     
     */
    public void setInternetAddresses(InternetAddresses value) {
        this.internetAddresses = value;
    }

}
