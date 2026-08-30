
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.Key;


/**
 * <p>Java class for ParameterKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParameterKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/2006/01}Key"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BehaviorOptionKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BehaviorOptionKey" minOccurs="0"/&gt;
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParameterKey", propOrder = {
    "behaviorOptionKey",
    "id"
})
public class ParameterKey
    extends Key
{

    @XmlElement(name = "BehaviorOptionKey")
    protected BehaviorOptionKey behaviorOptionKey;
    @XmlElement(name = "Id")
    protected int id;

    /**
     * Gets the value of the behaviorOptionKey property.
     * 
     * @return
     *     possible object is
     *     {@link BehaviorOptionKey }
     *     
     */
    public BehaviorOptionKey getBehaviorOptionKey() {
        return behaviorOptionKey;
    }

    /**
     * Sets the value of the behaviorOptionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link BehaviorOptionKey }
     *     
     */
    public void setBehaviorOptionKey(BehaviorOptionKey value) {
        this.behaviorOptionKey = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

}
