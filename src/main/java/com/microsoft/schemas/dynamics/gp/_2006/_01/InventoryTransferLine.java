
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InventoryTransferLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InventoryTransferLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}InventoryLineBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="WarehouseFromKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="WarehouseToKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="QuantityTypeFrom" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}QuantityType"/&gt;
 *         &lt;element name="QuantityTypeTo" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}QuantityType"/&gt;
 *         &lt;element name="InventoryFromGLAccount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="InventoryToGLAccount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="Bins" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfInventoryTransferBin" minOccurs="0"/&gt;
 *         &lt;element name="Lots" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfInventoryTransferLot" minOccurs="0"/&gt;
 *         &lt;element name="Serials" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfInventoryTransferSerial" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InventoryTransferLine", propOrder = {
    "warehouseFromKey",
    "warehouseToKey",
    "quantityTypeFrom",
    "quantityTypeTo",
    "inventoryFromGLAccount",
    "inventoryToGLAccount",
    "bins",
    "lots",
    "serials"
})
public class InventoryTransferLine
    extends InventoryLineBase
{

    @XmlElement(name = "WarehouseFromKey")
    protected WarehouseKey warehouseFromKey;
    @XmlElement(name = "WarehouseToKey")
    protected WarehouseKey warehouseToKey;
    @XmlElement(name = "QuantityTypeFrom", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected QuantityType quantityTypeFrom;
    @XmlElement(name = "QuantityTypeTo", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected QuantityType quantityTypeTo;
    @XmlElement(name = "InventoryFromGLAccount")
    protected GLAccountNumberKey inventoryFromGLAccount;
    @XmlElement(name = "InventoryToGLAccount")
    protected GLAccountNumberKey inventoryToGLAccount;
    @XmlElement(name = "Bins")
    protected ArrayOfInventoryTransferBin bins;
    @XmlElement(name = "Lots")
    protected ArrayOfInventoryTransferLot lots;
    @XmlElement(name = "Serials")
    protected ArrayOfInventoryTransferSerial serials;

    /**
     * Gets the value of the warehouseFromKey property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getWarehouseFromKey() {
        return warehouseFromKey;
    }

    /**
     * Sets the value of the warehouseFromKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setWarehouseFromKey(WarehouseKey value) {
        this.warehouseFromKey = value;
    }

    /**
     * Gets the value of the warehouseToKey property.
     * 
     * @return
     *     possible object is
     *     {@link WarehouseKey }
     *     
     */
    public WarehouseKey getWarehouseToKey() {
        return warehouseToKey;
    }

    /**
     * Sets the value of the warehouseToKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarehouseKey }
     *     
     */
    public void setWarehouseToKey(WarehouseKey value) {
        this.warehouseToKey = value;
    }

    /**
     * Gets the value of the quantityTypeFrom property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getQuantityTypeFrom() {
        return quantityTypeFrom;
    }

    /**
     * Sets the value of the quantityTypeFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setQuantityTypeFrom(QuantityType value) {
        this.quantityTypeFrom = value;
    }

    /**
     * Gets the value of the quantityTypeTo property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getQuantityTypeTo() {
        return quantityTypeTo;
    }

    /**
     * Sets the value of the quantityTypeTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setQuantityTypeTo(QuantityType value) {
        this.quantityTypeTo = value;
    }

    /**
     * Gets the value of the inventoryFromGLAccount property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getInventoryFromGLAccount() {
        return inventoryFromGLAccount;
    }

    /**
     * Sets the value of the inventoryFromGLAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setInventoryFromGLAccount(GLAccountNumberKey value) {
        this.inventoryFromGLAccount = value;
    }

    /**
     * Gets the value of the inventoryToGLAccount property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getInventoryToGLAccount() {
        return inventoryToGLAccount;
    }

    /**
     * Sets the value of the inventoryToGLAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setInventoryToGLAccount(GLAccountNumberKey value) {
        this.inventoryToGLAccount = value;
    }

    /**
     * Gets the value of the bins property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInventoryTransferBin }
     *     
     */
    public ArrayOfInventoryTransferBin getBins() {
        return bins;
    }

    /**
     * Sets the value of the bins property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInventoryTransferBin }
     *     
     */
    public void setBins(ArrayOfInventoryTransferBin value) {
        this.bins = value;
    }

    /**
     * Gets the value of the lots property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInventoryTransferLot }
     *     
     */
    public ArrayOfInventoryTransferLot getLots() {
        return lots;
    }

    /**
     * Sets the value of the lots property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInventoryTransferLot }
     *     
     */
    public void setLots(ArrayOfInventoryTransferLot value) {
        this.lots = value;
    }

    /**
     * Gets the value of the serials property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInventoryTransferSerial }
     *     
     */
    public ArrayOfInventoryTransferSerial getSerials() {
        return serials;
    }

    /**
     * Sets the value of the serials property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInventoryTransferSerial }
     *     
     */
    public void setSerials(ArrayOfInventoryTransferSerial value) {
        this.serials = value;
    }

}
