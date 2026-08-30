
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InventoryAdjustment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InventoryAdjustment"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}InventoryBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Lines" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfInventoryAdjustmentLine" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InventoryAdjustment", propOrder = {
    "lines"
})
public class InventoryAdjustment
    extends InventoryBase
{

    @XmlElement(name = "Lines")
    protected ArrayOfInventoryAdjustmentLine lines;

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfInventoryAdjustmentLine }
     *     
     */
    public ArrayOfInventoryAdjustmentLine getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfInventoryAdjustmentLine }
     *     
     */
    public void setLines(ArrayOfInventoryAdjustmentLine value) {
        this.lines = value;
    }

}
