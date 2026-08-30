
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfReceivablesDebitMemoSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfReceivablesDebitMemoSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ReceivablesDebitMemoSummary" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesDebitMemoSummary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfReceivablesDebitMemoSummary", propOrder = {
    "receivablesDebitMemoSummary"
})
public class ArrayOfReceivablesDebitMemoSummary {

    @XmlElement(name = "ReceivablesDebitMemoSummary", nillable = true)
    protected List<ReceivablesDebitMemoSummary> receivablesDebitMemoSummary;

    /**
     * Gets the value of the receivablesDebitMemoSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receivablesDebitMemoSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceivablesDebitMemoSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReceivablesDebitMemoSummary }
     * 
     * 
     */
    public List<ReceivablesDebitMemoSummary> getReceivablesDebitMemoSummary() {
        if (receivablesDebitMemoSummary == null) {
            receivablesDebitMemoSummary = new ArrayList<ReceivablesDebitMemoSummary>();
        }
        return this.receivablesDebitMemoSummary;
    }

}
