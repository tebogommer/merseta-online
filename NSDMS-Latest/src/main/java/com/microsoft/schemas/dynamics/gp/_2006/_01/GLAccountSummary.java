
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLAccountSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLAccountSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountSummaryBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AccountType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountSummaryType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLAccountSummary", propOrder = {
    "accountType"
})
public class GLAccountSummary
    extends GLAccountSummaryBase
{

    @XmlElement(name = "AccountType", required = true)
    @XmlSchemaType(name = "string")
    protected GLAccountSummaryType accountType;

    /**
     * Gets the value of the accountType property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountSummaryType }
     *     
     */
    public GLAccountSummaryType getAccountType() {
        return accountType;
    }

    /**
     * Sets the value of the accountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountSummaryType }
     *     
     */
    public void setAccountType(GLAccountSummaryType value) {
        this.accountType = value;
    }

}
