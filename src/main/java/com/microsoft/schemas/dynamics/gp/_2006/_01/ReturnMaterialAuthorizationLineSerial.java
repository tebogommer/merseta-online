
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnMaterialAuthorizationLineSerial complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnMaterialAuthorizationLineSerial"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceSerial"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IsForReplacement" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsMarked" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="IsOriginalSerial" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnMaterialAuthorizationLineSerial", propOrder = {
    "isForReplacement",
    "isMarked",
    "isOriginalSerial"
})
public class ReturnMaterialAuthorizationLineSerial
    extends ServiceSerial
{

    @XmlElement(name = "IsForReplacement", required = true, type = Boolean.class, nillable = true)
    protected Boolean isForReplacement;
    @XmlElement(name = "IsMarked", required = true, type = Boolean.class, nillable = true)
    protected Boolean isMarked;
    @XmlElement(name = "IsOriginalSerial", required = true, type = Boolean.class, nillable = true)
    protected Boolean isOriginalSerial;

    /**
     * Gets the value of the isForReplacement property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsForReplacement() {
        return isForReplacement;
    }

    /**
     * Sets the value of the isForReplacement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsForReplacement(Boolean value) {
        this.isForReplacement = value;
    }

    /**
     * Gets the value of the isMarked property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsMarked() {
        return isMarked;
    }

    /**
     * Sets the value of the isMarked property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMarked(Boolean value) {
        this.isMarked = value;
    }

    /**
     * Gets the value of the isOriginalSerial property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOriginalSerial() {
        return isOriginalSerial;
    }

    /**
     * Sets the value of the isOriginalSerial property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOriginalSerial(Boolean value) {
        this.isOriginalSerial = value;
    }

}
