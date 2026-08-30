
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GLUnitAccountSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLUnitAccountSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountSummaryBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DecimalPlaces" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLUnitAccountDecimalPlaces"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLUnitAccountSummary", propOrder = {
    "decimalPlaces"
})
public class GLUnitAccountSummary
    extends GLAccountSummaryBase
{

    @XmlElement(name = "DecimalPlaces", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected GLUnitAccountDecimalPlaces decimalPlaces;

    /**
     * Gets the value of the decimalPlaces property.
     * 
     * @return
     *     possible object is
     *     {@link GLUnitAccountDecimalPlaces }
     *     
     */
    public GLUnitAccountDecimalPlaces getDecimalPlaces() {
        return decimalPlaces;
    }

    /**
     * Sets the value of the decimalPlaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLUnitAccountDecimalPlaces }
     *     
     */
    public void setDecimalPlaces(GLUnitAccountDecimalPlaces value) {
        this.decimalPlaces = value;
    }

}
