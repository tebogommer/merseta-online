
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLAccountCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLAccountCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountCriteriaBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AccountType" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ListRestrictionOfNullableOfGLAccountSummaryType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLAccountCriteria", propOrder = {
    "accountType"
})
public class GLAccountCriteria
    extends GLAccountCriteriaBase
{

    @XmlElement(name = "AccountType")
    protected ListRestrictionOfNullableOfGLAccountSummaryType accountType;

    /**
     * Gets the value of the accountType property.
     * 
     * @return
     *     possible object is
     *     {@link ListRestrictionOfNullableOfGLAccountSummaryType }
     *     
     */
    public ListRestrictionOfNullableOfGLAccountSummaryType getAccountType() {
        return accountType;
    }

    /**
     * Sets the value of the accountType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ListRestrictionOfNullableOfGLAccountSummaryType }
     *     
     */
    public void setAccountType(ListRestrictionOfNullableOfGLAccountSummaryType value) {
        this.accountType = value;
    }

}
