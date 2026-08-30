
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnMaterialAuthorizationSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnMaterialAuthorizationSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceDocumentSummary"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ReturnTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="ReturnStatusCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationStatusCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnMaterialAuthorizationSummary", propOrder = {
    "returnTypeKey",
    "returnStatusCodeKey",
    "type"
})
public class ReturnMaterialAuthorizationSummary
    extends ServiceDocumentSummary
{

    @XmlElement(name = "ReturnTypeKey")
    protected ReturnMaterialAuthorizationTypeKey returnTypeKey;
    @XmlElement(name = "ReturnStatusCodeKey")
    protected ReturnMaterialAuthorizationStatusCodeKey returnStatusCodeKey;
    @XmlElement(name = "Type", required = true)
    @XmlSchemaType(name = "string")
    protected ReturnMaterialAuthorizationType type;

    /**
     * Gets the value of the returnTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationTypeKey }
     *     
     */
    public ReturnMaterialAuthorizationTypeKey getReturnTypeKey() {
        return returnTypeKey;
    }

    /**
     * Sets the value of the returnTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationTypeKey }
     *     
     */
    public void setReturnTypeKey(ReturnMaterialAuthorizationTypeKey value) {
        this.returnTypeKey = value;
    }

    /**
     * Gets the value of the returnStatusCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationStatusCodeKey }
     *     
     */
    public ReturnMaterialAuthorizationStatusCodeKey getReturnStatusCodeKey() {
        return returnStatusCodeKey;
    }

    /**
     * Sets the value of the returnStatusCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationStatusCodeKey }
     *     
     */
    public void setReturnStatusCodeKey(ReturnMaterialAuthorizationStatusCodeKey value) {
        this.returnStatusCodeKey = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationType }
     *     
     */
    public ReturnMaterialAuthorizationType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationType }
     *     
     */
    public void setType(ReturnMaterialAuthorizationType value) {
        this.type = value;
    }

}
