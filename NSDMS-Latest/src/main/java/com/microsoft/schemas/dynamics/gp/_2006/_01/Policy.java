
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Policy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Policy"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PolicyKey" minOccurs="0"/&gt;
 *         &lt;element name="Behaviors" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfBehavior" minOccurs="0"/&gt;
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RootBusinessObjectName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Policy", propOrder = {
    "key",
    "behaviors",
    "name",
    "rootBusinessObjectName"
})
public class Policy {

    @XmlElement(name = "Key")
    protected PolicyKey key;
    @XmlElement(name = "Behaviors")
    protected ArrayOfBehavior behaviors;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "RootBusinessObjectName")
    protected String rootBusinessObjectName;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link PolicyKey }
     *     
     */
    public PolicyKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolicyKey }
     *     
     */
    public void setKey(PolicyKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the behaviors property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfBehavior }
     *     
     */
    public ArrayOfBehavior getBehaviors() {
        return behaviors;
    }

    /**
     * Sets the value of the behaviors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfBehavior }
     *     
     */
    public void setBehaviors(ArrayOfBehavior value) {
        this.behaviors = value;
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
     * Gets the value of the rootBusinessObjectName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRootBusinessObjectName() {
        return rootBusinessObjectName;
    }

    /**
     * Sets the value of the rootBusinessObjectName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRootBusinessObjectName(String value) {
        this.rootBusinessObjectName = value;
    }

}
