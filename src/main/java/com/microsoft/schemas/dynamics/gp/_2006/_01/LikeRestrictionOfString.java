
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LikeRestrictionOfString complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LikeRestrictionOfString"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfString"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Like" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LikeRestrictionOfString", propOrder = {
    "like"
})
public class LikeRestrictionOfString
    extends BetweenRestrictionOfString
{

    @XmlElement(name = "Like")
    protected String like;

    /**
     * Gets the value of the like property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLike() {
        return like;
    }

    /**
     * Sets the value of the like property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLike(String value) {
        this.like = value;
    }

}
