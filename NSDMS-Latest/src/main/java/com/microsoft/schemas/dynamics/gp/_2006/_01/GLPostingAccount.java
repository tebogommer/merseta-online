
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLPostingAccount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLPostingAccount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLFinancialAccount"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GLAccountCategoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountCategoryKey" minOccurs="0"/&gt;
 *         &lt;element name="AllowAccountEntry" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Currencies" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfGLAccountCurrency" minOccurs="0"/&gt;
 *         &lt;element name="IsRevalued" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PostRevaluationResultsTo" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PostRevaluationResultsTo"/&gt;
 *         &lt;element name="PostingType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PostingType"/&gt;
 *         &lt;element name="RevaluationMethod" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}RevaluationMethod"/&gt;
 *         &lt;element name="TypicalBalance" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TypicalBalance"/&gt;
 *         &lt;element name="UserDefined1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="UserDefined4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLPostingAccount", propOrder = {
    "glAccountCategoryKey",
    "allowAccountEntry",
    "currencies",
    "isRevalued",
    "postRevaluationResultsTo",
    "postingType",
    "revaluationMethod",
    "typicalBalance",
    "userDefined1",
    "userDefined2",
    "userDefined3",
    "userDefined4"
})
public class GLPostingAccount
    extends GLFinancialAccount
{

    @XmlElement(name = "GLAccountCategoryKey")
    protected GLAccountCategoryKey glAccountCategoryKey;
    @XmlElement(name = "AllowAccountEntry", required = true, type = Boolean.class, nillable = true)
    protected Boolean allowAccountEntry;
    @XmlElement(name = "Currencies")
    protected ArrayOfGLAccountCurrency currencies;
    @XmlElement(name = "IsRevalued", required = true, type = Boolean.class, nillable = true)
    protected Boolean isRevalued;
    @XmlElement(name = "PostRevaluationResultsTo", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PostRevaluationResultsTo postRevaluationResultsTo;
    @XmlElement(name = "PostingType", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PostingType postingType;
    @XmlElement(name = "RevaluationMethod", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected RevaluationMethod revaluationMethod;
    @XmlElement(name = "TypicalBalance", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected TypicalBalance typicalBalance;
    @XmlElement(name = "UserDefined1")
    protected String userDefined1;
    @XmlElement(name = "UserDefined2")
    protected String userDefined2;
    @XmlElement(name = "UserDefined3")
    protected String userDefined3;
    @XmlElement(name = "UserDefined4")
    protected String userDefined4;

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
     * Gets the value of the currencies property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGLAccountCurrency }
     *     
     */
    public ArrayOfGLAccountCurrency getCurrencies() {
        return currencies;
    }

    /**
     * Sets the value of the currencies property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGLAccountCurrency }
     *     
     */
    public void setCurrencies(ArrayOfGLAccountCurrency value) {
        this.currencies = value;
    }

    /**
     * Gets the value of the isRevalued property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsRevalued() {
        return isRevalued;
    }

    /**
     * Sets the value of the isRevalued property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsRevalued(Boolean value) {
        this.isRevalued = value;
    }

    /**
     * Gets the value of the postRevaluationResultsTo property.
     * 
     * @return
     *     possible object is
     *     {@link PostRevaluationResultsTo }
     *     
     */
    public PostRevaluationResultsTo getPostRevaluationResultsTo() {
        return postRevaluationResultsTo;
    }

    /**
     * Sets the value of the postRevaluationResultsTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostRevaluationResultsTo }
     *     
     */
    public void setPostRevaluationResultsTo(PostRevaluationResultsTo value) {
        this.postRevaluationResultsTo = value;
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
     * Gets the value of the revaluationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link RevaluationMethod }
     *     
     */
    public RevaluationMethod getRevaluationMethod() {
        return revaluationMethod;
    }

    /**
     * Sets the value of the revaluationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link RevaluationMethod }
     *     
     */
    public void setRevaluationMethod(RevaluationMethod value) {
        this.revaluationMethod = value;
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

    /**
     * Gets the value of the userDefined1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined1() {
        return userDefined1;
    }

    /**
     * Sets the value of the userDefined1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined1(String value) {
        this.userDefined1 = value;
    }

    /**
     * Gets the value of the userDefined2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined2() {
        return userDefined2;
    }

    /**
     * Sets the value of the userDefined2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined2(String value) {
        this.userDefined2 = value;
    }

    /**
     * Gets the value of the userDefined3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined3() {
        return userDefined3;
    }

    /**
     * Sets the value of the userDefined3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined3(String value) {
        this.userDefined3 = value;
    }

    /**
     * Gets the value of the userDefined4 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserDefined4() {
        return userDefined4;
    }

    /**
     * Sets the value of the userDefined4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserDefined4(String value) {
        this.userDefined4 = value;
    }

}
