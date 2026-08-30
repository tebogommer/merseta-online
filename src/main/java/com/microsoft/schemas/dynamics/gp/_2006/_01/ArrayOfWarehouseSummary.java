
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfWarehouseSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfWarehouseSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="WarehouseSummary" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}WarehouseSummary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfWarehouseSummary", propOrder = {
    "warehouseSummary"
})
public class ArrayOfWarehouseSummary {

    @XmlElement(name = "WarehouseSummary", nillable = true)
    protected List<WarehouseSummary> warehouseSummary;

    /**
     * Gets the value of the warehouseSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the warehouseSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWarehouseSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WarehouseSummary }
     * 
     * 
     */
    public List<WarehouseSummary> getWarehouseSummary() {
        if (warehouseSummary == null) {
            warehouseSummary = new ArrayList<WarehouseSummary>();
        }
        return this.warehouseSummary;
    }

}
