
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfChangedSalesOrderKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfChangedSalesOrderKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ChangedSalesOrderKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ChangedSalesOrderKey" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfChangedSalesOrderKey", propOrder = {
    "changedSalesOrderKey"
})
public class ArrayOfChangedSalesOrderKey {

    @XmlElement(name = "ChangedSalesOrderKey", nillable = true)
    protected List<ChangedSalesOrderKey> changedSalesOrderKey;

    /**
     * Gets the value of the changedSalesOrderKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the changedSalesOrderKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChangedSalesOrderKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChangedSalesOrderKey }
     * 
     * 
     */
    public List<ChangedSalesOrderKey> getChangedSalesOrderKey() {
        if (changedSalesOrderKey == null) {
            changedSalesOrderKey = new ArrayList<ChangedSalesOrderKey>();
        }
        return this.changedSalesOrderKey;
    }

}
