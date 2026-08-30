
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayablesDocumentSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayablesDocumentSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesDocumentSummaryBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Type" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesDocumentType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayablesDocumentSummary", propOrder = {
    "type"
})
public class PayablesDocumentSummary
    extends PayablesDocumentSummaryBase
{

    @XmlElement(name = "Type", required = true, nillable = true)
    @XmlSchemaType(name = "string")
    protected PayablesDocumentType type;

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesDocumentType }
     *     
     */
    public PayablesDocumentType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesDocumentType }
     *     
     */
    public void setType(PayablesDocumentType value) {
        this.type = value;
    }

}
