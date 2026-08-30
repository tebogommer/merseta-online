
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ServiceEquipmentReading complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceEquipmentReading"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentReadingKey" minOccurs="0"/&gt;
 *         &lt;element name="Meter1" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentMeter" minOccurs="0"/&gt;
 *         &lt;element name="Meter2" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentMeter" minOccurs="0"/&gt;
 *         &lt;element name="Meter3" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentMeter" minOccurs="0"/&gt;
 *         &lt;element name="Meter4" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentMeter" minOccurs="0"/&gt;
 *         &lt;element name="Meter5" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceEquipmentMeter" minOccurs="0"/&gt;
 *         &lt;element name="ModifiedDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="ModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ReadingEnteredFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceEquipmentReading", propOrder = {
    "key",
    "meter1",
    "meter2",
    "meter3",
    "meter4",
    "meter5",
    "modifiedDate",
    "modifiedBy",
    "readingEnteredFrom"
})
public class ServiceEquipmentReading
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ServiceEquipmentReadingKey key;
    @XmlElement(name = "Meter1")
    protected ServiceEquipmentMeter meter1;
    @XmlElement(name = "Meter2")
    protected ServiceEquipmentMeter meter2;
    @XmlElement(name = "Meter3")
    protected ServiceEquipmentMeter meter3;
    @XmlElement(name = "Meter4")
    protected ServiceEquipmentMeter meter4;
    @XmlElement(name = "Meter5")
    protected ServiceEquipmentMeter meter5;
    @XmlElement(name = "ModifiedDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedDate;
    @XmlElement(name = "ModifiedBy")
    protected String modifiedBy;
    @XmlElement(name = "ReadingEnteredFrom")
    protected String readingEnteredFrom;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEquipmentReadingKey }
     *     
     */
    public ServiceEquipmentReadingKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEquipmentReadingKey }
     *     
     */
    public void setKey(ServiceEquipmentReadingKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the meter1 property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEquipmentMeter }
     *     
     */
    public ServiceEquipmentMeter getMeter1() {
        return meter1;
    }

    /**
     * Sets the value of the meter1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEquipmentMeter }
     *     
     */
    public void setMeter1(ServiceEquipmentMeter value) {
        this.meter1 = value;
    }

    /**
     * Gets the value of the meter2 property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEquipmentMeter }
     *     
     */
    public ServiceEquipmentMeter getMeter2() {
        return meter2;
    }

    /**
     * Sets the value of the meter2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEquipmentMeter }
     *     
     */
    public void setMeter2(ServiceEquipmentMeter value) {
        this.meter2 = value;
    }

    /**
     * Gets the value of the meter3 property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEquipmentMeter }
     *     
     */
    public ServiceEquipmentMeter getMeter3() {
        return meter3;
    }

    /**
     * Sets the value of the meter3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEquipmentMeter }
     *     
     */
    public void setMeter3(ServiceEquipmentMeter value) {
        this.meter3 = value;
    }

    /**
     * Gets the value of the meter4 property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEquipmentMeter }
     *     
     */
    public ServiceEquipmentMeter getMeter4() {
        return meter4;
    }

    /**
     * Sets the value of the meter4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEquipmentMeter }
     *     
     */
    public void setMeter4(ServiceEquipmentMeter value) {
        this.meter4 = value;
    }

    /**
     * Gets the value of the meter5 property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceEquipmentMeter }
     *     
     */
    public ServiceEquipmentMeter getMeter5() {
        return meter5;
    }

    /**
     * Sets the value of the meter5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceEquipmentMeter }
     *     
     */
    public void setMeter5(ServiceEquipmentMeter value) {
        this.meter5 = value;
    }

    /**
     * Gets the value of the modifiedDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedDate() {
        return modifiedDate;
    }

    /**
     * Sets the value of the modifiedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedDate(XMLGregorianCalendar value) {
        this.modifiedDate = value;
    }

    /**
     * Gets the value of the modifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Sets the value of the modifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Gets the value of the readingEnteredFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReadingEnteredFrom() {
        return readingEnteredFrom;
    }

    /**
     * Sets the value of the readingEnteredFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReadingEnteredFrom(String value) {
        this.readingEnteredFrom = value;
    }

}
