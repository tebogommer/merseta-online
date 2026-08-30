
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLFixedAllocationDistributionAccount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLFixedAllocationDistributionAccount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAllocationDistributionAccountKey" minOccurs="0"/&gt;
 *         &lt;element name="Percentage" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLFixedAllocationDistributionAccount", propOrder = {
    "key",
    "percentage"
})
public class GLFixedAllocationDistributionAccount
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected GLAllocationDistributionAccountKey key;
    @XmlElement(name = "Percentage")
    protected Percent percentage;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link GLAllocationDistributionAccountKey }
     *     
     */
    public GLAllocationDistributionAccountKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAllocationDistributionAccountKey }
     *     
     */
    public void setKey(GLAllocationDistributionAccountKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the percentage property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getPercentage() {
        return percentage;
    }

    /**
     * Sets the value of the percentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setPercentage(Percent value) {
        this.percentage = value;
    }

}
