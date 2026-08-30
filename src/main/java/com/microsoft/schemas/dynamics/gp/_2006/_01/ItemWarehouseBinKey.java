
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemWarehouseBinKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemWarehouseBinKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ItemWarehouseKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemWarehouseKey" minOccurs="0"/&gt;
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
@XmlType(name = "ItemWarehouseBinKey", propOrder = {
    "itemWarehouseKey",
    "bin"
})
public class ItemWarehouseBinKey
    extends ReferenceKey
{

    @XmlElement(name = "ItemWarehouseKey")
    protected ItemWarehouseKey itemWarehouseKey;
    @XmlElement(name = "Bin")
    protected String bin;

    /**
     * Gets the value of the itemWarehouseKey property.
     * 
     * @return
     *     possible object is
     *     {@link ItemWarehouseKey }
     *     
     */
    public ItemWarehouseKey getItemWarehouseKey() {
        return itemWarehouseKey;
    }

    /**
     * Sets the value of the itemWarehouseKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemWarehouseKey }
     *     
     */
    public void setItemWarehouseKey(ItemWarehouseKey value) {
        this.itemWarehouseKey = value;
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
