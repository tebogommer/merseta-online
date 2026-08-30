
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BatchCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BatchCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *         &lt;element name="Source" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BatchCriteria", propOrder = {
    "id",
    "source"
})
public class BatchCriteria
    extends Criteria
{

    @XmlElement(name = "Id")
    protected LikeRestrictionOfString id;
    @XmlElement(name = "Source")
    protected LikeRestrictionOfString source;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setId(LikeRestrictionOfString value) {
        this.id = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setSource(LikeRestrictionOfString value) {
        this.source = value;
    }

}
