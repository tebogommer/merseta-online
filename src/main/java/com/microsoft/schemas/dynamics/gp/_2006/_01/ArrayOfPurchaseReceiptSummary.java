
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfPurchaseReceiptSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPurchaseReceiptSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PurchaseReceiptSummary" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PurchaseReceiptSummary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPurchaseReceiptSummary", propOrder = {
    "purchaseReceiptSummary"
})
public class ArrayOfPurchaseReceiptSummary {

    @XmlElement(name = "PurchaseReceiptSummary", nillable = true)
    protected List<PurchaseReceiptSummary> purchaseReceiptSummary;

    /**
     * Gets the value of the purchaseReceiptSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the purchaseReceiptSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPurchaseReceiptSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PurchaseReceiptSummary }
     * 
     * 
     */
    public List<PurchaseReceiptSummary> getPurchaseReceiptSummary() {
        if (purchaseReceiptSummary == null) {
            purchaseReceiptSummary = new ArrayList<PurchaseReceiptSummary>();
        }
        return this.purchaseReceiptSummary;
    }

}
