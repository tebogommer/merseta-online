
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfPriceLevel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfPriceLevel"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PriceLevel" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PriceLevel" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPriceLevel", propOrder = {
    "priceLevel"
})
public class ArrayOfPriceLevel {

    @XmlElement(name = "PriceLevel", nillable = true)
    protected List<PriceLevel> priceLevel;

    /**
     * Gets the value of the priceLevel property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the priceLevel property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPriceLevel().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PriceLevel }
     * 
     * 
     */
    public List<PriceLevel> getPriceLevel() {
        if (priceLevel == null) {
            priceLevel = new ArrayList<PriceLevel>();
        }
        return this.priceLevel;
    }

}
