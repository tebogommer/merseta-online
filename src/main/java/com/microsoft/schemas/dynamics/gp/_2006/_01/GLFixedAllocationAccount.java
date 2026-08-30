
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLFixedAllocationAccount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLFixedAllocationAccount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLFinancialAccount"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Distributions" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfGLFixedAllocationDistributionAccount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLFixedAllocationAccount", propOrder = {
    "distributions"
})
public class GLFixedAllocationAccount
    extends GLFinancialAccount
{

    @XmlElement(name = "Distributions")
    protected ArrayOfGLFixedAllocationDistributionAccount distributions;

    /**
     * Gets the value of the distributions property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGLFixedAllocationDistributionAccount }
     *     
     */
    public ArrayOfGLFixedAllocationDistributionAccount getDistributions() {
        return distributions;
    }

    /**
     * Sets the value of the distributions property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGLFixedAllocationDistributionAccount }
     *     
     */
    public void setDistributions(ArrayOfGLFixedAllocationDistributionAccount value) {
        this.distributions = value;
    }

}
