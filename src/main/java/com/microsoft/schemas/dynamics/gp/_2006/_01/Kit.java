
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Kit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Kit"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}InventoriedItem"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CostOfGoodsSoldAccountSource" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CostOfGoodsSoldAccountSource"/&gt;
 *         &lt;element name="Components" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfKitComponent" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Kit", propOrder = {
    "costOfGoodsSoldAccountSource",
    "components"
})
public class Kit
    extends InventoriedItem
{

    @XmlElement(name = "CostOfGoodsSoldAccountSource", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected CostOfGoodsSoldAccountSource costOfGoodsSoldAccountSource;
    @XmlElement(name = "Components")
    protected ArrayOfKitComponent components;

    /**
     * Gets the value of the costOfGoodsSoldAccountSource property.
     * 
     * @return
     *     possible object is
     *     {@link CostOfGoodsSoldAccountSource }
     *     
     */
    public CostOfGoodsSoldAccountSource getCostOfGoodsSoldAccountSource() {
        return costOfGoodsSoldAccountSource;
    }

    /**
     * Sets the value of the costOfGoodsSoldAccountSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link CostOfGoodsSoldAccountSource }
     *     
     */
    public void setCostOfGoodsSoldAccountSource(CostOfGoodsSoldAccountSource value) {
        this.costOfGoodsSoldAccountSource = value;
    }

    /**
     * Gets the value of the components property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfKitComponent }
     *     
     */
    public ArrayOfKitComponent getComponents() {
        return components;
    }

    /**
     * Sets the value of the components property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfKitComponent }
     *     
     */
    public void setComponents(ArrayOfKitComponent value) {
        this.components = value;
    }

}
