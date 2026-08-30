
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnMaterialAuthorizationCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnMaterialAuthorizationCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceDocumentCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfReturnMaterialAuthorizationType" minOccurs="0"/&gt;
 *         &lt;element name="ReturnTypeId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="ReturnStatusCodeId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Scope" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnScope"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnMaterialAuthorizationCriteria", propOrder = {
    "type",
    "returnTypeId",
    "returnStatusCodeId",
    "scope"
})
public class ReturnMaterialAuthorizationCriteria
    extends ServiceDocumentCriteria
{

    @XmlElement(name = "Type")
    protected ListRestrictionOfNullableOfReturnMaterialAuthorizationType type;
    @XmlElement(name = "ReturnTypeId")
    protected LikeRestrictionOfString returnTypeId;
    @XmlElement(name = "ReturnStatusCodeId")
    protected LikeRestrictionOfString returnStatusCodeId;
    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected ReturnScope scope;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfReturnMaterialAuthorizationType }
     *     
     */
    public ListRestrictionOfNullableOfReturnMaterialAuthorizationType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfReturnMaterialAuthorizationType }
     *     
     */
    public void setType(ListRestrictionOfNullableOfReturnMaterialAuthorizationType value) {
        this.type = value;
    }

    /**
     * Gets the value of the returnTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getReturnTypeId() {
        return returnTypeId;
    }

    /**
     * Sets the value of the returnTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setReturnTypeId(LikeRestrictionOfString value) {
        this.returnTypeId = value;
    }

    /**
     * Gets the value of the returnStatusCodeId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getReturnStatusCodeId() {
        return returnStatusCodeId;
    }

    /**
     * Sets the value of the returnStatusCodeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setReturnStatusCodeId(LikeRestrictionOfString value) {
        this.returnStatusCodeId = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnScope }
     *     
     */
    public ReturnScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnScope }
     *     
     */
    public void setScope(ReturnScope value) {
        this.scope = value;
    }

}
