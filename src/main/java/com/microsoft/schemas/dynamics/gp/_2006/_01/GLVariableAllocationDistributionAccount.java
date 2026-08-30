
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLVariableAllocationDistributionAccount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLVariableAllocationDistributionAccount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAllocationDistributionAccountKey" minOccurs="0"/&gt;
 *         &lt;element name="Breakdowns" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfGLVariableAllocationBreakdownAccount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLVariableAllocationDistributionAccount", propOrder = {
    "key",
    "breakdowns"
})
public class GLVariableAllocationDistributionAccount
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected GLAllocationDistributionAccountKey key;
    @XmlElement(name = "Breakdowns")
    protected ArrayOfGLVariableAllocationBreakdownAccount breakdowns;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link GLAllocationDistributionAccountKey }
     *     
     */
    public GLAllocationDistributionAccountKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAllocationDistributionAccountKey }
     *     
     */
    public void setKey(GLAllocationDistributionAccountKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the breakdowns property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGLVariableAllocationBreakdownAccount }
     *     
     */
    public ArrayOfGLVariableAllocationBreakdownAccount getBreakdowns() {
        return breakdowns;
    }

    /**
     * Sets the value of the breakdowns property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGLVariableAllocationBreakdownAccount }
     *     
     */
    public void setBreakdowns(ArrayOfGLVariableAllocationBreakdownAccount value) {
        this.breakdowns = value;
    }

}
