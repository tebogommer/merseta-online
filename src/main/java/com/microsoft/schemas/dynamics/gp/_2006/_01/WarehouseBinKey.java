
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for WarehouseBinKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WarehouseBinKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="WarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseKey" minOccurs="0"/&gt;
 *         &lt;element name="Bin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WarehouseBinKey", propOrder = {
    "warehouseKey",
    "bin"
})
public class WarehouseBinKey
    extends ReferenceKey
{

    @XmlElement(name = "WarehouseKey")
    protected WarehouseKey warehouseKey;
    @XmlElement(name = "Bin")
    protected String bin;

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
     * Gets the value of the bin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBin() {
        return bin;
    }

    /**
     * Sets the value of the bin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBin(String value) {
        this.bin = value;
    }

}
