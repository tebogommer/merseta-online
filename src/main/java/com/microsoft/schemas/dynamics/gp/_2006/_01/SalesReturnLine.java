
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesReturnLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesReturnLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesLine"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ReturnQuantity" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SalesReturnQuantities" minOccurs="0"/&gt;
 *         &lt;element name="Components" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesReturnComponent" minOccurs="0"/&gt;
 *         &lt;element name="Serials" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesLineSerial" minOccurs="0"/&gt;
 *         &lt;element name="Lots" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesLineLot" minOccurs="0"/&gt;
 *         &lt;element name="Bins" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfSalesLineBin" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesReturnLine", propOrder = {
    "returnQuantity",
    "components",
    "serials",
    "lots",
    "bins"
})
public class SalesReturnLine
    extends SalesLine
{

    @XmlElement(name = "ReturnQuantity")
    protected SalesReturnQuantities returnQuantity;
    @XmlElement(name = "Components")
    protected ArrayOfSalesReturnComponent components;
    @XmlElement(name = "Serials")
    protected ArrayOfSalesLineSerial serials;
    @XmlElement(name = "Lots")
    protected ArrayOfSalesLineLot lots;
    @XmlElement(name = "Bins")
    protected ArrayOfSalesLineBin bins;

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
     * Gets the value of the components property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesReturnComponent }
     *     
     */
    public ArrayOfSalesReturnComponent getComponents() {
        return components;
    }

    /**
     * Sets the value of the components property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesReturnComponent }
     *     
     */
    public void setComponents(ArrayOfSalesReturnComponent value) {
        this.components = value;
    }

    /**
     * Gets the value of the serials property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesLineSerial }
     *     
     */
    public ArrayOfSalesLineSerial getSerials() {
        return serials;
    }

    /**
     * Sets the value of the serials property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesLineSerial }
     *     
     */
    public void setSerials(ArrayOfSalesLineSerial value) {
        this.serials = value;
    }

    /**
     * Gets the value of the lots property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesLineLot }
     *     
     */
    public ArrayOfSalesLineLot getLots() {
        return lots;
    }

    /**
     * Sets the value of the lots property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesLineLot }
     *     
     */
    public void setLots(ArrayOfSalesLineLot value) {
        this.lots = value;
    }

    /**
     * Gets the value of the bins property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSalesLineBin }
     *     
     */
    public ArrayOfSalesLineBin getBins() {
        return bins;
    }

    /**
     * Sets the value of the bins property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSalesLineBin }
     *     
     */
    public void setBins(ArrayOfSalesLineBin value) {
        this.bins = value;
    }

}
