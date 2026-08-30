
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceCallCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceCallCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallDocumentCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfNullableOfServiceCallType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceCallCriteria", propOrder = {
    "type"
})
public class ServiceCallCriteria
    extends ServiceCallDocumentCriteria
{

    @XmlElement(name = "Type")
    protected LikeRestrictionOfNullableOfServiceCallType type;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfNullableOfServiceCallType }
     *     
     */
    public LikeRestrictionOfNullableOfServiceCallType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfNullableOfServiceCallType }
     *     
     */
    public void setType(LikeRestrictionOfNullableOfServiceCallType value) {
        this.type = value;
    }

}
