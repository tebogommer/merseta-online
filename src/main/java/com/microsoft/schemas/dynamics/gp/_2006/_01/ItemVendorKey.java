
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ItemVendorKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemVendorKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ItemKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ItemKey" minOccurs="0"/&gt;
 *         &lt;element name="VendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemVendorKey", propOrder = {
    "itemKey",
    "vendorKey"
})
public class ItemVendorKey
    extends ReferenceKey
{

    @XmlElement(name = "ItemKey")
    protected ItemKey itemKey;
    @XmlElement(name = "VendorKey")
    protected VendorKey vendorKey;

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
     * Gets the value of the vendorKey property.
     * 
     * @return
     *     possible object is
     *     {@link VendorKey }
     *     
     */
    public VendorKey getVendorKey() {
        return vendorKey;
    }

    /**
     * Sets the value of the vendorKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorKey }
     *     
     */
    public void setVendorKey(VendorKey value) {
        this.vendorKey = value;
    }

}
