
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ManufacturingOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManufacturingOrder"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PickList" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfManufacturingOrderPickListItem" minOccurs="0"/&gt;
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
@XmlType(name = "ManufacturingOrder", propOrder = {
    "pickList",
    "route"
})
public class ManufacturingOrder
    extends ManufacturingOrderDocument
{

    @XmlElement(name = "PickList")
    protected ArrayOfManufacturingOrderPickListItem pickList;
    @XmlElement(name = "Route")
    protected ArrayOfManufacturingOrderRouteStep route;

    /**
     * Gets the value of the pickList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfManufacturingOrderPickListItem }
     *     
     */
    public ArrayOfManufacturingOrderPickListItem getPickList() {
        return pickList;
    }

    /**
     * Sets the value of the pickList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfManufacturingOrderPickListItem }
     *     
     */
    public void setPickList(ArrayOfManufacturingOrderPickListItem value) {
        this.pickList = value;
    }

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
