
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesReturnComponent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesReturnComponent"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesComponent"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ReturnQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesReturnQuantities" minOccurs="0"/&gt;
 *         &lt;element name="Serials" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesComponentSerial" minOccurs="0"/&gt;
 *         &lt;element name="Lots" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesComponentLot" minOccurs="0"/&gt;
 *         &lt;element name="Bins" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesComponentBin" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesReturnComponent", propOrder = {
    "returnQuantity",
    "serials",
    "lots",
    "bins"
})
public class SalesReturnComponent
    extends SalesComponent
{

    @XmlElement(name = "ReturnQuantity")
    protected SalesReturnQuantities returnQuantity;
    @XmlElement(name = "Serials")
    protected ArrayOfSalesComponentSerial serials;
    @XmlElement(name = "Lots")
    protected ArrayOfSalesComponentLot lots;
    @XmlElement(name = "Bins")
    protected ArrayOfSalesComponentBin bins;

    /**
     * Gets the value of the returnQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link SalesReturnQuantities }
     *     
     */
    public SalesReturnQuantities getReturnQuantity() {
        return returnQuantity;
    }

    /**
     * Sets the value of the returnQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link SalesReturnQuantities }
     *     
     */
    public void setReturnQuantity(SalesReturnQuantities value) {
        this.returnQuantity = value;
    }

    /**
     * Gets the value of the serials property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesComponentSerial }
     *     
     */
    public ArrayOfSalesComponentSerial getSerials() {
        return serials;
    }

    /**
     * Sets the value of the serials property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesComponentSerial }
     *     
     */
    public void setSerials(ArrayOfSalesComponentSerial value) {
        this.serials = value;
    }

    /**
     * Gets the value of the lots property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesComponentLot }
     *     
     */
    public ArrayOfSalesComponentLot getLots() {
        return lots;
    }

    /**
     * Sets the value of the lots property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesComponentLot }
     *     
     */
    public void setLots(ArrayOfSalesComponentLot value) {
        this.lots = value;
    }

    /**
     * Gets the value of the bins property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesComponentBin }
     *     
     */
    public ArrayOfSalesComponentBin getBins() {
        return bins;
    }

    /**
     * Sets the value of the bins property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesComponentBin }
     *     
     */
    public void setBins(ArrayOfSalesComponentBin value) {
        this.bins = value;
    }

}
