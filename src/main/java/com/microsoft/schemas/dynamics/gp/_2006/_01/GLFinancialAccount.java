
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLFinancialAccount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLFinancialAccount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccount"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PostInventoryIn" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PostingLevel"/&gt;
 *         &lt;element name="PostPayrollIn" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PostingLevel"/&gt;
 *         &lt;element name="PostPurchasingIn" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PostingLevel"/&gt;
 *         &lt;element name="PostSalesIn" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PostingLevel"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLFinancialAccount", propOrder = {
    "postInventoryIn",
    "postPayrollIn",
    "postPurchasingIn",
    "postSalesIn"
})
@XmlSeeAlso({
    GLFixedAllocationAccount.class,
    GLPostingAccount.class,
    GLVariableAllocationAccount.class
})
public abstract class GLFinancialAccount
    extends GLAccount
{

    @XmlElement(name = "PostInventoryIn", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PostingLevel postInventoryIn;
    @XmlElement(name = "PostPayrollIn", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PostingLevel postPayrollIn;
    @XmlElement(name = "PostPurchasingIn", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PostingLevel postPurchasingIn;
    @XmlElement(name = "PostSalesIn", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PostingLevel postSalesIn;

    /**
     * Gets the value of the postInventoryIn property.
     * 
     * @return
     *     possible object is
     *     {@link PostingLevel }
     *     
     */
    public PostingLevel getPostInventoryIn() {
        return postInventoryIn;
    }

    /**
     * Sets the value of the postInventoryIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostingLevel }
     *     
     */
    public void setPostInventoryIn(PostingLevel value) {
        this.postInventoryIn = value;
    }

    /**
     * Gets the value of the postPayrollIn property.
     * 
     * @return
     *     possible object is
     *     {@link PostingLevel }
     *     
     */
    public PostingLevel getPostPayrollIn() {
        return postPayrollIn;
    }

    /**
     * Sets the value of the postPayrollIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostingLevel }
     *     
     */
    public void setPostPayrollIn(PostingLevel value) {
        this.postPayrollIn = value;
    }

    /**
     * Gets the value of the postPurchasingIn property.
     * 
     * @return
     *     possible object is
     *     {@link PostingLevel }
     *     
     */
    public PostingLevel getPostPurchasingIn() {
        return postPurchasingIn;
    }

    /**
     * Sets the value of the postPurchasingIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostingLevel }
     *     
     */
    public void setPostPurchasingIn(PostingLevel value) {
        this.postPurchasingIn = value;
    }

    /**
     * Gets the value of the postSalesIn property.
     * 
     * @return
     *     possible object is
     *     {@link PostingLevel }
     *     
     */
    public PostingLevel getPostSalesIn() {
        return postSalesIn;
    }

    /**
     * Sets the value of the postSalesIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostingLevel }
     *     
     */
    public void setPostSalesIn(PostingLevel value) {
        this.postSalesIn = value;
    }

}
