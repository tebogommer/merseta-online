
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InventoryTransferSerial complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InventoryTransferSerial"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}InventorySerialBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BinFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="BinTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InventoryTransferSerial", propOrder = {
    "binFrom",
    "binTo"
})
public class InventoryTransferSerial
    extends InventorySerialBase
{

    @XmlElement(name = "BinFrom")
    protected String binFrom;
    @XmlElement(name = "BinTo")
    protected String binTo;

    /**
     * Gets the value of the binFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBinFrom() {
        return binFrom;
    }

    /**
     * Sets the value of the binFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBinFrom(String value) {
        this.binFrom = value;
    }

    /**
     * Gets the value of the binTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBinTo() {
        return binTo;
    }

    /**
     * Sets the value of the binTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBinTo(String value) {
        this.binTo = value;
    }

}
