
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesHistoryOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesHistoryOptions"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KeepCalendar" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="KeepPeriod" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesHistoryOptions", propOrder = {
    "keepCalendar",
    "keepPeriod"
})
public class SalesHistoryOptions
    extends BusinessObject
{

    @XmlElement(name = "KeepCalendar", required = true, type = Boolean.class, nillable = true)
    protected Boolean keepCalendar;
    @XmlElement(name = "KeepPeriod", required = true, type = Boolean.class, nillable = true)
    protected Boolean keepPeriod;

    /**
     * Gets the value of the keepCalendar property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeepCalendar() {
        return keepCalendar;
    }

    /**
     * Sets the value of the keepCalendar property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepCalendar(Boolean value) {
        this.keepCalendar = value;
    }

    /**
     * Gets the value of the keepPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeepPeriod() {
        return keepPeriod;
    }

    /**
     * Sets the value of the keepPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepPeriod(Boolean value) {
        this.keepPeriod = value;
    }

}
