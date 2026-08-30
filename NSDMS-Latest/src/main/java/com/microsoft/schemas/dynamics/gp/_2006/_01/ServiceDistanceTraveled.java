
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceDistanceTraveled complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceDistanceTraveled"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Ending" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Starting" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="Used" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceDistanceTraveled", propOrder = {
    "ending",
    "starting",
    "used"
})
public class ServiceDistanceTraveled
    extends BusinessObject
{

    @XmlElement(name = "Ending", required = true, type = Integer.class, nillable = true)
    protected Integer ending;
    @XmlElement(name = "Starting", required = true, type = Integer.class, nillable = true)
    protected Integer starting;
    @XmlElement(name = "Used", required = true, type = Integer.class, nillable = true)
    protected Integer used;

    /**
     * Gets the value of the ending property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getEnding() {
        return ending;
    }

    /**
     * Sets the value of the ending property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setEnding(Integer value) {
        this.ending = value;
    }

    /**
     * Gets the value of the starting property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getStarting() {
        return starting;
    }

    /**
     * Sets the value of the starting property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setStarting(Integer value) {
        this.starting = value;
    }

    /**
     * Gets the value of the used property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUsed() {
        return used;
    }

    /**
     * Sets the value of the used property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUsed(Integer value) {
        this.used = value;
    }

}
