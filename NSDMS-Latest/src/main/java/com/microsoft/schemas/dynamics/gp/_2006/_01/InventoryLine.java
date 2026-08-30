
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InventoryLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InventoryLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}InventoryLineBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UofM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="WarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="InventoryGLAccount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="InventoryOffsetGLAccount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="Bins" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfInventoryBin" minOccurs="0"/&gt;
 *         &lt;element name="Lots" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfInventoryLot" minOccurs="0"/&gt;
 *         &lt;element name="Serials" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfInventorySerial" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InventoryLine", propOrder = {
    "uofM",
    "warehouseKey",
    "inventoryGLAccount",
    "inventoryOffsetGLAccount",
    "bins",
    "lots",
    "serials"
})
@XmlSeeAlso({
    InventoryAdjustmentLine.class,
    InventoryVarianceLine.class
})
public abstract class InventoryLine
    extends InventoryLineBase
{

    @XmlElement(name = "UofM")
    protected String uofM;
    @XmlElement(name = "WarehouseKey")
    protected WarehouseKey warehouseKey;
    @XmlElement(name = "InventoryGLAccount")
    protected GLAccountNumberKey inventoryGLAccount;
    @XmlElement(name = "InventoryOffsetGLAccount")
    protected GLAccountNumberKey inventoryOffsetGLAccount;
    @XmlElement(name = "Bins")
    protected ArrayOfInventoryBin bins;
    @XmlElement(name = "Lots")
    protected ArrayOfInventoryLot lots;
    @XmlElement(name = "Serials")
    protected ArrayOfInventorySerial serials;

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
     * Gets the value of the inventoryGLAccount property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getInventoryGLAccount() {
        return inventoryGLAccount;
    }

    /**
     * Sets the value of the inventoryGLAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setInventoryGLAccount(GLAccountNumberKey value) {
        this.inventoryGLAccount = value;
    }

    /**
     * Gets the value of the inventoryOffsetGLAccount property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getInventoryOffsetGLAccount() {
        return inventoryOffsetGLAccount;
    }

    /**
     * Sets the value of the inventoryOffsetGLAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setInventoryOffsetGLAccount(GLAccountNumberKey value) {
        this.inventoryOffsetGLAccount = value;
    }

    /**
     * Gets the value of the bins property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInventoryBin }
     *     
     */
    public ArrayOfInventoryBin getBins() {
        return bins;
    }

    /**
     * Sets the value of the bins property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInventoryBin }
     *     
     */
    public void setBins(ArrayOfInventoryBin value) {
        this.bins = value;
    }

    /**
     * Gets the value of the lots property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInventoryLot }
     *     
     */
    public ArrayOfInventoryLot getLots() {
        return lots;
    }

    /**
     * Sets the value of the lots property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInventoryLot }
     *     
     */
    public void setLots(ArrayOfInventoryLot value) {
        this.lots = value;
    }

    /**
     * Gets the value of the serials property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInventorySerial }
     *     
     */
    public ArrayOfInventorySerial getSerials() {
        return serials;
    }

    /**
     * Sets the value of the serials property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInventorySerial }
     *     
     */
    public void setSerials(ArrayOfInventorySerial value) {
        this.serials = value;
    }

}
