
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VendorManufacturingOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VendorManufacturingOrder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Route" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfManufacturingOrderRouteStep" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VendorManufacturingOrder", propOrder = {
    "route"
})
public class VendorManufacturingOrder
    extends ManufacturingOrderDocument
{

    @XmlElement(name = "Route")
    protected ArrayOfManufacturingOrderRouteStep route;

    /**
     * Gets the value of the route property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfManufacturingOrderRouteStep }
     *     
     */
    public ArrayOfManufacturingOrderRouteStep getRoute() {
        return route;
    }

    /**
     * Sets the value of the route property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfManufacturingOrderRouteStep }
     *     
     */
    public void setRoute(ArrayOfManufacturingOrderRouteStep value) {
        this.route = value;
    }

}
