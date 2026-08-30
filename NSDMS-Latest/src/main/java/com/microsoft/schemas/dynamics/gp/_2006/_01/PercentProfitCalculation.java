
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PercentProfitCalculation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PercentProfitCalculation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProfitCalculation"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProfitPercent" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Percent" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PercentProfitCalculation", propOrder = {
    "profitPercent"
})
@XmlSeeAlso({
    PercentOfActual.class,
    PercentOfBaseline.class,
    MarkupPercentage.class
})
public abstract class PercentProfitCalculation
    extends ProfitCalculation
{

    @XmlElement(name = "ProfitPercent")
    protected Percent profitPercent;

    /**
     * Gets the value of the profitPercent property.
     * 
     * @return
     *     possible object is
     *     {@link Percent }
     *     
     */
    public Percent getProfitPercent() {
        return profitPercent;
    }

    /**
     * Sets the value of the profitPercent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Percent }
     *     
     */
    public void setProfitPercent(Percent value) {
        this.profitPercent = value;
    }

}
