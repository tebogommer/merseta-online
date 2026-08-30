
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLAllocationDistributionAccountKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLAllocationDistributionAccountKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="DistributionAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLAllocationDistributionAccountKey", propOrder = {
    "accountKey",
    "distributionAccountKey"
})
public class GLAllocationDistributionAccountKey
    extends ReferenceKey
{

    @XmlElement(name = "AccountKey")
    protected GLAccountNumberKey accountKey;
    @XmlElement(name = "DistributionAccountKey")
    protected GLAccountNumberKey distributionAccountKey;

    /**
     * Gets the value of the accountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getAccountKey() {
        return accountKey;
    }

    /**
     * Sets the value of the accountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setAccountKey(GLAccountNumberKey value) {
        this.accountKey = value;
    }

    /**
     * Gets the value of the distributionAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getDistributionAccountKey() {
        return distributionAccountKey;
    }

    /**
     * Sets the value of the distributionAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setDistributionAccountKey(GLAccountNumberKey value) {
        this.distributionAccountKey = value;
    }

}
