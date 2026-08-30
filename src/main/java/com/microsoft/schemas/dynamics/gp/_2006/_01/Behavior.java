
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Behavior complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Behavior"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BehaviorKey" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Internal" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Options" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfBehaviorOption" minOccurs="0"/&gt;
 *         &lt;element name="SelectedOption" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BehaviorOption" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Behavior", propOrder = {
    "key",
    "name",
    "description",
    "internal",
    "options",
    "selectedOption"
})
public class Behavior {

    @XmlElement(name = "Key")
    protected BehaviorKey key;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Internal", required = true, type = Boolean.class, nillable = true)
    protected Boolean internal;
    @XmlElement(name = "Options")
    protected ArrayOfBehaviorOption options;
    @XmlElement(name = "SelectedOption")
    protected BehaviorOption selectedOption;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link BehaviorKey }
     *     
     */
    public BehaviorKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link BehaviorKey }
     *     
     */
    public void setKey(BehaviorKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the internal property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInternal() {
        return internal;
    }

    /**
     * Sets the value of the internal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInternal(Boolean value) {
        this.internal = value;
    }

    /**
     * Gets the value of the options property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfBehaviorOption }
     *     
     */
    public ArrayOfBehaviorOption getOptions() {
        return options;
    }

    /**
     * Sets the value of the options property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfBehaviorOption }
     *     
     */
    public void setOptions(ArrayOfBehaviorOption value) {
        this.options = value;
    }

    /**
     * Gets the value of the selectedOption property.
     * 
     * @return
     *     possible object is
     *     {@link BehaviorOption }
     *     
     */
    public BehaviorOption getSelectedOption() {
        return selectedOption;
    }

    /**
     * Sets the value of the selectedOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link BehaviorOption }
     *     
     */
    public void setSelectedOption(BehaviorOption value) {
        this.selectedOption = value;
    }

}
