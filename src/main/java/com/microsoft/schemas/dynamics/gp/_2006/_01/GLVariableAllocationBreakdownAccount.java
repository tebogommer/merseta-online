
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLVariableAllocationBreakdownAccount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLVariableAllocationBreakdownAccount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLVariableAllocationBreakdownAccountKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLVariableAllocationBreakdownAccount", propOrder = {
    "key"
})
public class GLVariableAllocationBreakdownAccount
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected GLVariableAllocationBreakdownAccountKey key;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link GLVariableAllocationBreakdownAccountKey }
     *     
     */
    public GLVariableAllocationBreakdownAccountKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLVariableAllocationBreakdownAccountKey }
     *     
     */
    public void setKey(GLVariableAllocationBreakdownAccountKey value) {
        this.key = value;
    }

}
