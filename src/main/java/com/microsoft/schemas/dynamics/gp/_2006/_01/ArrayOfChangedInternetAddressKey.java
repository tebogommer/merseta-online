
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfChangedInternetAddressKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfChangedInternetAddressKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ChangedInternetAddressKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ChangedInternetAddressKey" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfChangedInternetAddressKey", propOrder = {
    "changedInternetAddressKey"
})
public class ArrayOfChangedInternetAddressKey {

    @XmlElement(name = "ChangedInternetAddressKey", nillable = true)
    protected List<ChangedInternetAddressKey> changedInternetAddressKey;

    /**
     * Gets the value of the changedInternetAddressKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the changedInternetAddressKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChangedInternetAddressKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChangedInternetAddressKey }
     * 
     * 
     */
    public List<ChangedInternetAddressKey> getChangedInternetAddressKey() {
        if (changedInternetAddressKey == null) {
            changedInternetAddressKey = new ArrayList<ChangedInternetAddressKey>();
        }
        return this.changedInternetAddressKey;
    }

}
