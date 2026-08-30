
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLVariableAllocationAccount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLVariableAllocationAccount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLFinancialAccount"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BalanceForCalculation" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLVariableAllocationAccountBalanceCalculationType"/&gt;
 *         &lt;element name="Distributions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfGLVariableAllocationDistributionAccount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLVariableAllocationAccount", propOrder = {
    "balanceForCalculation",
    "distributions"
})
public class GLVariableAllocationAccount
    extends GLFinancialAccount
{

    @XmlElement(name = "BalanceForCalculation", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected GLVariableAllocationAccountBalanceCalculationType balanceForCalculation;
    @XmlElement(name = "Distributions")
    protected ArrayOfGLVariableAllocationDistributionAccount distributions;

    /**
     * Gets the value of the balanceForCalculation property.
     * 
     * @return
     *     possible object is
     *     {@link GLVariableAllocationAccountBalanceCalculationType }
     *     
     */
    public GLVariableAllocationAccountBalanceCalculationType getBalanceForCalculation() {
        return balanceForCalculation;
    }

    /**
     * Sets the value of the balanceForCalculation property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLVariableAllocationAccountBalanceCalculationType }
     *     
     */
    public void setBalanceForCalculation(GLVariableAllocationAccountBalanceCalculationType value) {
        this.balanceForCalculation = value;
    }

    /**
     * Gets the value of the distributions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGLVariableAllocationDistributionAccount }
     *     
     */
    public ArrayOfGLVariableAllocationDistributionAccount getDistributions() {
        return distributions;
    }

    /**
     * Sets the value of the distributions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGLVariableAllocationDistributionAccount }
     *     
     */
    public void setDistributions(ArrayOfGLVariableAllocationDistributionAccount value) {
        this.distributions = value;
    }

}
