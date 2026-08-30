
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfPayablesTransactionState complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPayablesTransactionState"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PayablesTransactionState" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesTransactionState" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPayablesTransactionState", propOrder = {
    "payablesTransactionState"
})
public class ArrayOfPayablesTransactionState {

    @XmlElement(name = "PayablesTransactionState", nillable = true)
    @XmlSchemaType(name = "string")
    protected List<PayablesTransactionState> payablesTransactionState;

    /**
     * Gets the value of the payablesTransactionState property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the payablesTransactionState property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPayablesTransactionState().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PayablesTransactionState }
     * 
     * 
     */
    public List<PayablesTransactionState> getPayablesTransactionState() {
        if (payablesTransactionState == null) {
            payablesTransactionState = new ArrayList<PayablesTransactionState>();
        }
        return this.payablesTransactionState;
    }

}
