
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfCustomerAddressSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfCustomerAddressSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CustomerAddressSummary" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerAddressSummary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfCustomerAddressSummary", propOrder = {
    "customerAddressSummary"
})
public class ArrayOfCustomerAddressSummary {

    @XmlElement(name = "CustomerAddressSummary", nillable = true)
    protected List<CustomerAddressSummary> customerAddressSummary;

    /**
     * Gets the value of the customerAddressSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customerAddressSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomerAddressSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomerAddressSummary }
     * 
     * 
     */
    public List<CustomerAddressSummary> getCustomerAddressSummary() {
        if (customerAddressSummary == null) {
            customerAddressSummary = new ArrayList<CustomerAddressSummary>();
        }
        return this.customerAddressSummary;
    }

}
