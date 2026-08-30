
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SalesLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesLineKey" minOccurs="0"/&gt;
 *         &lt;element name="UnitPrice" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TotalAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="Quantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Quantity" minOccurs="0"/&gt;
 *         &lt;element name="Discount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyPercentChoice" minOccurs="0"/&gt;
 *         &lt;element name="UnitCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ItemDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsNonInventory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsDropShip" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="RequestedShipDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="Comment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DeleteOnUpdate" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ExtendedCost" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="ItemTaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="TaxBasis" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTaxBasis"/&gt;
 *         &lt;element name="TaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="TaxScheduleKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleKey" minOccurs="0"/&gt;
 *         &lt;element name="CostOfSalesGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="DamagedGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="InServiceGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="InUseGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="InventoryGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="DiscountGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="ReturnsGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="FrontOfficeIntegrationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IntegrationSource" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="IntegrationId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="WarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="ShippingMethodKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ShippingMethodKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesTerritoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesTerritoryKey" minOccurs="0"/&gt;
 *         &lt;element name="SalespersonKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalespersonKey" minOccurs="0"/&gt;
 *         &lt;element name="CommentKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CommentKey" minOccurs="0"/&gt;
 *         &lt;element name="PriceLevelKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PriceLevelKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerAddressKey" minOccurs="0"/&gt;
 *         &lt;element name="ShipToAddress" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessAddress" minOccurs="0"/&gt;
 *         &lt;element name="Taxes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesLineTax" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesLine", propOrder = {
    "key",
    "unitPrice",
    "totalAmount",
    "quantity",
    "discount",
    "unitCost",
    "itemDescription",
    "isNonInventory",
    "isDropShip",
    "requestedShipDate",
    "comment",
    "uofM",
    "deleteOnUpdate",
    "extendedCost",
    "itemTaxScheduleKey",
    "taxBasis",
    "taxAmount",
    "taxScheduleKey",
    "costOfSalesGLAccountKey",
    "damagedGLAccountKey",
    "inServiceGLAccountKey",
    "inUseGLAccountKey",
    "inventoryGLAccountKey",
    "discountGLAccountKey",
    "returnsGLAccountKey",
    "salesGLAccountKey",
    "frontOfficeIntegrationId",
    "integrationSource",
    "integrationId",
    "warehouseKey",
    "itemKey",
    "shippingMethodKey",
    "salesTerritoryKey",
    "salespersonKey",
    "commentKey",
    "priceLevelKey",
    "shipToAddressKey",
    "shipToAddress",
    "taxes"
})
@XmlSeeAlso({
    SalesBackorderLine.class,
    SalesQuoteLine.class,
    SalesReturnLine.class,
    SalesInvoiceLine.class,
    SalesOrderLine.class,
    SalesFulfillmentOrderLine.class
})
public abstract class SalesLine
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected SalesLineKey key;
    @XmlElement(name = "UnitPrice")
    protected MoneyAmount unitPrice;
    @XmlElement(name = "TotalAmount")
    protected MoneyAmount totalAmount;
    @XmlElement(name = "Quantity")
    protected Quantity quantity;
    @XmlElement(name = "Discount")
    protected MoneyPercentChoice discount;
    @XmlElement(name = "UnitCost")
    protected MoneyAmount unitCost;
    @XmlElement(name = "ItemDescription")
    protected String itemDescription;
    @XmlElement(name = "IsNonInventory", required = true, type = Boolean.class, nillable = true)
    protected Boolean isNonInventory;
    @XmlElement(name = "IsDropShip", required = true, type = Boolean.class, nillable = true)
    protected Boolean isDropShip;
    @XmlElement(name = "RequestedShipDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar requestedShipDate;
    @XmlElement(name = "Comment")
    protected String comment;
    @XmlElement(name = "UofM")
    protected String uofM;
    @XmlElement(name = "DeleteOnUpdate", required = true, type = Boolean.class, nillable = true)
    protected Boolean deleteOnUpdate;
    @XmlElement(name = "ExtendedCost")
    protected MoneyAmount extendedCost;
    @XmlElement(name = "ItemTaxScheduleKey")
    protected TaxScheduleKey itemTaxScheduleKey;
    @XmlElement(name = "TaxBasis", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected SalesTaxBasis taxBasis;
    @XmlElement(name = "TaxAmount")
    protected MoneyAmount taxAmount;
    @XmlElement(name = "TaxScheduleKey")
    protected TaxScheduleKey taxScheduleKey;
    @XmlElement(name = "CostOfSalesGLAccountKey")
    protected GLAccountNumberKey costOfSalesGLAccountKey;
    @XmlElement(name = "DamagedGLAccountKey")
    protected GLAccountNumberKey damagedGLAccountKey;
    @XmlElement(name = "InServiceGLAccountKey")
    protected GLAccountNumberKey inServiceGLAccountKey;
    @XmlElement(name = "InUseGLAccountKey")
    protected GLAccountNumberKey inUseGLAccountKey;
    @XmlElement(name = "InventoryGLAccountKey")
    protected GLAccountNumberKey inventoryGLAccountKey;
    @XmlElement(name = "DiscountGLAccountKey")
    protected GLAccountNumberKey discountGLAccountKey;
    @XmlElement(name = "ReturnsGLAccountKey")
    protected GLAccountNumberKey returnsGLAccountKey;
    @XmlElement(name = "SalesGLAccountKey")
    protected GLAccountNumberKey salesGLAccountKey;
    @XmlElement(name = "FrontOfficeIntegrationId")
    protected String frontOfficeIntegrationId;
    @XmlElement(name = "IntegrationSource", required = true, type = Integer.class, nillable = true)
    protected Integer integrationSource;
    @XmlElement(name = "IntegrationId")
    protected String integrationId;
    @XmlElement(name = "WarehouseKey")
    protected WarehouseKey warehouseKey;
    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "ShippingMethodKey")
    protected ShippingMethodKey shippingMethodKey;
    @XmlElement(name = "SalesTerritoryKey")
    protected SalesTerritoryKey salesTerritoryKey;
    @XmlElement(name = "SalespersonKey")
    protected SalespersonKey salespersonKey;
    @XmlElement(name = "CommentKey")
    protected CommentKey commentKey;
    @XmlElement(name = "PriceLevelKey")
    protected PriceLevelKey priceLevelKey;
    @XmlElement(name = "ShipToAddressKey")
    protected CustomerAddressKey shipToAddressKey;
    @XmlElement(name = "ShipToAddress")
    protected BusinessAddress shipToAddress;
    @XmlElement(name = "Taxes")
    protected ArrayOfSalesLineTax taxes;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link SalesLineKey }
     *     
     */
    public SalesLineKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesLineKey }
     *     
     */
    public void setKey(SalesLineKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the unitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the value of the unitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setUnitPrice(MoneyAmount value) {
        this.unitPrice = value;
    }

    /**
     * Gets the value of the totalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the value of the totalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalAmount(MoneyAmount value) {
        this.totalAmount = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link Quantity }
     *     
     */
    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Quantity }
     *     
     */
    public void setQuantity(Quantity value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the discount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyPercentChoice }
     *     
     */
    public MoneyPercentChoice getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyPercentChoice }
     *     
     */
    public void setDiscount(MoneyPercentChoice value) {
        this.discount = value;
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
     * Gets the value of the isDropShip property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDropShip() {
        return isDropShip;
    }

    /**
     * Sets the value of the isDropShip property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDropShip(Boolean value) {
        this.isDropShip = value;
    }

    /**
     * Gets the value of the requestedShipDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRequestedShipDate() {
        return requestedShipDate;
    }

    /**
     * Sets the value of the requestedShipDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRequestedShipDate(XMLGregorianCalendar value) {
        this.requestedShipDate = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
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
     * Gets the value of the deleteOnUpdate property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDeleteOnUpdate() {
        return deleteOnUpdate;
    }

    /**
     * Sets the value of the deleteOnUpdate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDeleteOnUpdate(Boolean value) {
        this.deleteOnUpdate = value;
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
     * Gets the value of the itemTaxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getItemTaxScheduleKey() {
        return itemTaxScheduleKey;
    }

    /**
     * Sets the value of the itemTaxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setItemTaxScheduleKey(TaxScheduleKey value) {
        this.itemTaxScheduleKey = value;
    }

    /**
     * Gets the value of the taxBasis property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTaxBasis }
     *     
     */
    public SalesTaxBasis getTaxBasis() {
        return taxBasis;
    }

    /**
     * Sets the value of the taxBasis property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTaxBasis }
     *     
     */
    public void setTaxBasis(SalesTaxBasis value) {
        this.taxBasis = value;
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
     * Gets the value of the taxScheduleKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleKey }
     *     
     */
    public TaxScheduleKey getTaxScheduleKey() {
        return taxScheduleKey;
    }

    /**
     * Sets the value of the taxScheduleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleKey }
     *     
     */
    public void setTaxScheduleKey(TaxScheduleKey value) {
        this.taxScheduleKey = value;
    }

    /**
     * Gets the value of the costOfSalesGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getCostOfSalesGLAccountKey() {
        return costOfSalesGLAccountKey;
    }

    /**
     * Sets the value of the costOfSalesGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setCostOfSalesGLAccountKey(GLAccountNumberKey value) {
        this.costOfSalesGLAccountKey = value;
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
     * Gets the value of the discountGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getDiscountGLAccountKey() {
        return discountGLAccountKey;
    }

    /**
     * Sets the value of the discountGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setDiscountGLAccountKey(GLAccountNumberKey value) {
        this.discountGLAccountKey = value;
    }

    /**
     * Gets the value of the returnsGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getReturnsGLAccountKey() {
        return returnsGLAccountKey;
    }

    /**
     * Sets the value of the returnsGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setReturnsGLAccountKey(GLAccountNumberKey value) {
        this.returnsGLAccountKey = value;
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
     * Gets the value of the frontOfficeIntegrationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrontOfficeIntegrationId() {
        return frontOfficeIntegrationId;
    }

    /**
     * Sets the value of the frontOfficeIntegrationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrontOfficeIntegrationId(String value) {
        this.frontOfficeIntegrationId = value;
    }

    /**
     * Gets the value of the integrationSource property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIntegrationSource() {
        return integrationSource;
    }

    /**
     * Sets the value of the integrationSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIntegrationSource(Integer value) {
        this.integrationSource = value;
    }

    /**
     * Gets the value of the integrationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntegrationId() {
        return integrationId;
    }

    /**
     * Sets the value of the integrationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntegrationId(String value) {
        this.integrationId = value;
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
     * Gets the value of the salesTerritoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalesTerritoryKey }
     *     
     */
    public SalesTerritoryKey getSalesTerritoryKey() {
        return salesTerritoryKey;
    }

    /**
     * Sets the value of the salesTerritoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesTerritoryKey }
     *     
     */
    public void setSalesTerritoryKey(SalesTerritoryKey value) {
        this.salesTerritoryKey = value;
    }

    /**
     * Gets the value of the salespersonKey property.
     * 
     * @return
     *     possible object is
     *     {@link SalespersonKey }
     *     
     */
    public SalespersonKey getSalespersonKey() {
        return salespersonKey;
    }

    /**
     * Sets the value of the salespersonKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalespersonKey }
     *     
     */
    public void setSalespersonKey(SalespersonKey value) {
        this.salespersonKey = value;
    }

    /**
     * Gets the value of the commentKey property.
     * 
     * @return
     *     possible object is
     *     {@link CommentKey }
     *     
     */
    public CommentKey getCommentKey() {
        return commentKey;
    }

    /**
     * Sets the value of the commentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommentKey }
     *     
     */
    public void setCommentKey(CommentKey value) {
        this.commentKey = value;
    }

    /**
     * Gets the value of the priceLevelKey property.
     * 
     * @return
     *     possible object is
     *     {@link PriceLevelKey }
     *     
     */
    public PriceLevelKey getPriceLevelKey() {
        return priceLevelKey;
    }

    /**
     * Sets the value of the priceLevelKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceLevelKey }
     *     
     */
    public void setPriceLevelKey(PriceLevelKey value) {
        this.priceLevelKey = value;
    }

    /**
     * Gets the value of the shipToAddressKey property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerAddressKey }
     *     
     */
    public CustomerAddressKey getShipToAddressKey() {
        return shipToAddressKey;
    }

    /**
     * Sets the value of the shipToAddressKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerAddressKey }
     *     
     */
    public void setShipToAddressKey(CustomerAddressKey value) {
        this.shipToAddressKey = value;
    }

    /**
     * Gets the value of the shipToAddress property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessAddress }
     *     
     */
    public BusinessAddress getShipToAddress() {
        return shipToAddress;
    }

    /**
     * Sets the value of the shipToAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessAddress }
     *     
     */
    public void setShipToAddress(BusinessAddress value) {
        this.shipToAddress = value;
    }

    /**
     * Gets the value of the taxes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesLineTax }
     *     
     */
    public ArrayOfSalesLineTax getTaxes() {
        return taxes;
    }

    /**
     * Sets the value of the taxes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesLineTax }
     *     
     */
    public void setTaxes(ArrayOfSalesLineTax value) {
        this.taxes = value;
    }

}
