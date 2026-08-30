
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UofMScheduleChangedKeyCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UofMScheduleChangedKeyCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BaseChangedKeyCriteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="UofMScheduleKeyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UofMScheduleChangedKeyCriteria", propOrder = {
    "uofMScheduleKeyId"
})
public class UofMScheduleChangedKeyCriteria
    extends BaseChangedKeyCriteria
{

    @XmlElement(name = "UofMScheduleKeyId")
    protected BetweenRestrictionOfString uofMScheduleKeyId;

    /**
     * Gets the value of the uofMScheduleKeyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public BetweenRestrictionOfString getUofMScheduleKeyId() {
        return uofMScheduleKeyId;
    }

    /**
     * Sets the value of the uofMScheduleKeyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfString }
     *     
     */
    public void setUofMScheduleKeyId(BetweenRestrictionOfString value) {
        this.uofMScheduleKeyId = value;
    }

}
