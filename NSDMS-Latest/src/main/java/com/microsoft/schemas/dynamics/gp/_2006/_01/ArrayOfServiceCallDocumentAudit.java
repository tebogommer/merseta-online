
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfServiceCallDocumentAudit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfServiceCallDocumentAudit"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ServiceCallDocumentAudit" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallDocumentAudit" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfServiceCallDocumentAudit", propOrder = {
    "serviceCallDocumentAudit"
})
public class ArrayOfServiceCallDocumentAudit {

    @XmlElement(name = "ServiceCallDocumentAudit", nillable = true)
    protected List<ServiceCallDocumentAudit> serviceCallDocumentAudit;

    /**
     * Gets the value of the serviceCallDocumentAudit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the serviceCallDocumentAudit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getServiceCallDocumentAudit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceCallDocumentAudit }
     * 
     * 
     */
    public List<ServiceCallDocumentAudit> getServiceCallDocumentAudit() {
        if (serviceCallDocumentAudit == null) {
            serviceCallDocumentAudit = new ArrayList<ServiceCallDocumentAudit>();
        }
        return this.serviceCallDocumentAudit;
    }

}
