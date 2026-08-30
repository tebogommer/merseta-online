
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLPostingAccountSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLPostingAccountSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountSummaryBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GLAccountCategoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountCategoryKey" minOccurs="0"/&gt;
 *         &lt;element name="AllowAccountEntry" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PostingType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PostingType"/&gt;
 *         &lt;element name="TypicalBalance" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TypicalBalance"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLPostingAccountSummary", propOrder = {
    "glAccountCategoryKey",
    "allowAccountEntry",
    "postingType",
    "typicalBalance"
})
public class GLPostingAccountSummary
    extends GLAccountSummaryBase
{

    @XmlElement(name = "GLAccountCategoryKey")
    protected GLAccountCategoryKey glAccountCategoryKey;
    @XmlElement(name = "AllowAccountEntry", required = true, type = Boolean.class, nillable = true)
    protected Boolean allowAccountEntry;
    @XmlElement(name = "PostingType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PostingType postingType;
    @XmlElement(name = "TypicalBalance", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected TypicalBalance typicalBalance;

    /**
     * Gets the value of the glAccountCategoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountCategoryKey }
     *     
     */
    public GLAccountCategoryKey getGLAccountCategoryKey() {
        return glAccountCategoryKey;
    }

    /**
     * Sets the value of the glAccountCategoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountCategoryKey }
     *     
     */
    public void setGLAccountCategoryKey(GLAccountCategoryKey value) {
        this.glAccountCategoryKey = value;
    }

    /**
     * Gets the value of the allowAccountEntry property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowAccountEntry() {
        return allowAccountEntry;
    }

    /**
     * Sets the value of the allowAccountEntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowAccountEntry(Boolean value) {
        this.allowAccountEntry = value;
    }

    /**
     * Gets the value of the postingType property.
     * 
     * @return
     *     possible object is
     *     {@link PostingType }
     *     
     */
    public PostingType getPostingType() {
        return postingType;
    }

    /**
     * Sets the value of the postingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostingType }
     *     
     */
    public void setPostingType(PostingType value) {
        this.postingType = value;
    }

    /**
     * Gets the value of the typicalBalance property.
     * 
     * @return
     *     possible object is
     *     {@link TypicalBalance }
     *     
     */
    public TypicalBalance getTypicalBalance() {
        return typicalBalance;
    }

    /**
     * Sets the value of the typicalBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypicalBalance }
     *     
     */
    public void setTypicalBalance(TypicalBalance value) {
        this.typicalBalance = value;
    }

}
