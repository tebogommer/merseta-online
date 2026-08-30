
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfCashReceiptSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCashReceiptSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CashReceiptSummary" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CashReceiptSummary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCashReceiptSummary", propOrder = {
    "cashReceiptSummary"
})
public class ArrayOfCashReceiptSummary {

    @XmlElement(name = "CashReceiptSummary", nillable = true)
    protected List<CashReceiptSummary> cashReceiptSummary;

    /**
     * Gets the value of the cashReceiptSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cashReceiptSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCashReceiptSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CashReceiptSummary }
     * 
     * 
     */
    public List<CashReceiptSummary> getCashReceiptSummary() {
        if (cashReceiptSummary == null) {
            cashReceiptSummary = new ArrayList<CashReceiptSummary>();
        }
        return this.cashReceiptSummary;
    }

}
