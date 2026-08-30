
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReturnMaterialAuthorizationAudit complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnMaterialAuthorizationAudit"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceAudit"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceLineDetailKey" minOccurs="0"/&gt;
 *         &lt;element name="LineSequenceNumber" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="FromReturnStatusCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationStatusCodeKey" minOccurs="0"/&gt;
 *         &lt;element name="ToReturnStatusCodeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationStatusCodeKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnMaterialAuthorizationAudit", propOrder = {
    "key",
    "lineSequenceNumber",
    "fromReturnStatusCodeKey",
    "toReturnStatusCodeKey"
})
public class ReturnMaterialAuthorizationAudit
    extends ServiceAudit
{

    @XmlElement(name = "Key")
    protected ServiceLineDetailKey key;
    @XmlElement(name = "LineSequenceNumber", required = true, nillable = true)
    protected BigDecimal lineSequenceNumber;
    @XmlElement(name = "FromReturnStatusCodeKey")
    protected ReturnMaterialAuthorizationStatusCodeKey fromReturnStatusCodeKey;
    @XmlElement(name = "ToReturnStatusCodeKey")
    protected ReturnMaterialAuthorizationStatusCodeKey toReturnStatusCodeKey;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLineDetailKey }
     *     
     */
    public ServiceLineDetailKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLineDetailKey }
     *     
     */
    public void setKey(ServiceLineDetailKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the lineSequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getLineSequenceNumber() {
        return lineSequenceNumber;
    }

    /**
     * Sets the value of the lineSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setLineSequenceNumber(BigDecimal value) {
        this.lineSequenceNumber = value;
    }

    /**
     * Gets the value of the fromReturnStatusCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationStatusCodeKey }
     *     
     */
    public ReturnMaterialAuthorizationStatusCodeKey getFromReturnStatusCodeKey() {
        return fromReturnStatusCodeKey;
    }

    /**
     * Sets the value of the fromReturnStatusCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationStatusCodeKey }
     *     
     */
    public void setFromReturnStatusCodeKey(ReturnMaterialAuthorizationStatusCodeKey value) {
        this.fromReturnStatusCodeKey = value;
    }

    /**
     * Gets the value of the toReturnStatusCodeKey property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnMaterialAuthorizationStatusCodeKey }
     *     
     */
    public ReturnMaterialAuthorizationStatusCodeKey getToReturnStatusCodeKey() {
        return toReturnStatusCodeKey;
    }

    /**
     * Sets the value of the toReturnStatusCodeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnMaterialAuthorizationStatusCodeKey }
     *     
     */
    public void setToReturnStatusCodeKey(ReturnMaterialAuthorizationStatusCodeKey value) {
        this.toReturnStatusCodeKey = value;
    }

}
