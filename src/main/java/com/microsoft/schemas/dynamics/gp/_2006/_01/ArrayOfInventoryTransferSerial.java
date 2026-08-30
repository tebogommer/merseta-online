
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfInventoryTransferSerial complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfInventoryTransferSerial"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InventoryTransferSerial" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}InventoryTransferSerial" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInventoryTransferSerial", propOrder = {
    "inventoryTransferSerial"
})
public class ArrayOfInventoryTransferSerial {

    @XmlElement(name = "InventoryTransferSerial", nillable = true)
    protected List<InventoryTransferSerial> inventoryTransferSerial;

    /**
     * Gets the value of the inventoryTransferSerial property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inventoryTransferSerial property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInventoryTransferSerial().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InventoryTransferSerial }
     * 
     * 
     */
    public List<InventoryTransferSerial> getInventoryTransferSerial() {
        if (inventoryTransferSerial == null) {
            inventoryTransferSerial = new ArrayList<InventoryTransferSerial>();
        }
        return this.inventoryTransferSerial;
    }

}
