
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RestrictionOfNullableOfReturnMaterialAuthorizationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RestrictionOfNullableOfReturnMaterialAuthorizationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EqualValue" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationType"/&gt;
 *         &lt;element name="NotEqualValue" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestrictionOfNullableOfReturnMaterialAuthorizationType", propOrder = {
    "equalValue",
    "notEqualValue"
})
@XmlSeeAlso({
    ListRestrictionOfNullableOfReturnMaterialAuthorizationType.class
})
public class RestrictionOfNullableOfReturnMaterialAuthorizationType {

    @XmlElement(name = "EqualValue", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ReturnMaterialAuthorizationType equalValue;
    @XmlElement(name = "NotEqualValue", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected ReturnMaterialAuthorizationType notEqualValue;

    /**
     * Gets the value of the equalValue property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationType }
     *     
     */
    public ReturnMaterialAuthorizationType getEqualValue() {
        return equalValue;
    }

    /**
     * Sets the value of the equalValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationType }
     *     
     */
    public void setEqualValue(ReturnMaterialAuthorizationType value) {
        this.equalValue = value;
    }

    /**
     * Gets the value of the notEqualValue property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationType }
     *     
     */
    public ReturnMaterialAuthorizationType getNotEqualValue() {
        return notEqualValue;
    }

    /**
     * Sets the value of the notEqualValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationType }
     *     
     */
    public void setNotEqualValue(ReturnMaterialAuthorizationType value) {
        this.notEqualValue = value;
    }

}
