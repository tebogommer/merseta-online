
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHRRequisitionSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHRRequisitionSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="HRRequisitionSummary" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}HRRequisitionSummary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHRRequisitionSummary", propOrder = {
    "hrRequisitionSummary"
})
public class ArrayOfHRRequisitionSummary {

    @XmlElement(name = "HRRequisitionSummary", nillable = true)
    protected List<HRRequisitionSummary> hrRequisitionSummary;

    /**
     * Gets the value of the hrRequisitionSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hrRequisitionSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHRRequisitionSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HRRequisitionSummary }
     * 
     * 
     */
    public List<HRRequisitionSummary> getHRRequisitionSummary() {
        if (hrRequisitionSummary == null) {
            hrRequisitionSummary = new ArrayList<HRRequisitionSummary>();
        }
        return this.hrRequisitionSummary;
    }

}
