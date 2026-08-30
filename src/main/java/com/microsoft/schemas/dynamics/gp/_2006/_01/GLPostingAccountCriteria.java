
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLPostingAccountCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLPostingAccountCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountCriteriaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GLAccountCategoryId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLPostingAccountCriteria", propOrder = {
    "glAccountCategoryId"
})
public class GLPostingAccountCriteria
    extends GLAccountCriteriaBase
{

    @XmlElement(name = "GLAccountCategoryId")
    protected LikeRestrictionOfString glAccountCategoryId;

    /**
     * Gets the value of the glAccountCategoryId property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getGLAccountCategoryId() {
        return glAccountCategoryId;
    }

    /**
     * Sets the value of the glAccountCategoryId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setGLAccountCategoryId(LikeRestrictionOfString value) {
        this.glAccountCategoryId = value;
    }

}
