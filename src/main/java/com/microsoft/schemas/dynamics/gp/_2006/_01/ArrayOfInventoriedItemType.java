
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfInventoriedItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfInventoriedItemType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InventoriedItemType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}InventoriedItemType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfInventoriedItemType", propOrder = {
    "inventoriedItemType"
})
public class ArrayOfInventoriedItemType {

    @XmlElement(name = "InventoriedItemType", nillable = true)
    @XmlSchemaType(name = "string")
    protected List<InventoriedItemType> inventoriedItemType;

    /**
     * Gets the value of the inventoriedItemType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inventoriedItemType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInventoriedItemType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InventoriedItemType }
     * 
     * 
     */
    public List<InventoriedItemType> getInventoriedItemType() {
        if (inventoriedItemType == null) {
            inventoriedItemType = new ArrayList<InventoriedItemType>();
        }
        return this.inventoriedItemType;
    }

}
