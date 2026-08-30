
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VendorManufacturingOrderCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VendorManufacturingOrderCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ManufacturingOrderDocumentCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="VendorKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Scope" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorManufacturingOrderScope"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VendorManufacturingOrderCriteria", propOrder = {
    "vendorKeyId",
    "scope"
})
public class VendorManufacturingOrderCriteria
    extends ManufacturingOrderDocumentCriteria
{

    @XmlElement(name = "VendorKeyId")
    protected LikeRestrictionOfString vendorKeyId;
    @XmlElement(name = "Scope", required = true)
    @XmlSchemaType(name = "string")
    protected VendorManufacturingOrderScope scope;

    /**
     * Gets the value of the vendorKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getVendorKeyId() {
        return vendorKeyId;
    }

    /**
     * Sets the value of the vendorKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setVendorKeyId(LikeRestrictionOfString value) {
        this.vendorKeyId = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link VendorManufacturingOrderScope }
     *     
     */
    public VendorManufacturingOrderScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorManufacturingOrderScope }
     *     
     */
    public void setScope(VendorManufacturingOrderScope value) {
        this.scope = value;
    }

}
