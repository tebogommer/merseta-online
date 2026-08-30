
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLVariableAllocationBreakdownAccountKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLVariableAllocationBreakdownAccountKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="AllocationDistributionAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAllocationDistributionAccountKey" minOccurs="0"/&gt;
 *         &lt;element name="BreakdownAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLVariableAllocationBreakdownAccountKey", propOrder = {
    "allocationDistributionAccountKey",
    "breakdownAccountKey"
})
public class GLVariableAllocationBreakdownAccountKey
    extends ReferenceKey
{

    @XmlElement(name = "AllocationDistributionAccountKey")
    protected GLAllocationDistributionAccountKey allocationDistributionAccountKey;
    @XmlElement(name = "BreakdownAccountKey")
    protected GLAccountNumberKey breakdownAccountKey;

    /**
     * Gets the value of the allocationDistributionAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAllocationDistributionAccountKey }
     *     
     */
    public GLAllocationDistributionAccountKey getAllocationDistributionAccountKey() {
        return allocationDistributionAccountKey;
    }

    /**
     * Sets the value of the allocationDistributionAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAllocationDistributionAccountKey }
     *     
     */
    public void setAllocationDistributionAccountKey(GLAllocationDistributionAccountKey value) {
        this.allocationDistributionAccountKey = value;
    }

    /**
     * Gets the value of the breakdownAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getBreakdownAccountKey() {
        return breakdownAccountKey;
    }

    /**
     * Sets the value of the breakdownAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setBreakdownAccountKey(GLAccountNumberKey value) {
        this.breakdownAccountKey = value;
    }

}
