
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectUnpostedBase complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectUnpostedBase"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectAmount"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="LossAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectUnpostedBase", propOrder = {
    "lossAmount"
})
@XmlSeeAlso({
    ProjectBudgetUnposted.class,
    ProjectContractUnposted.class,
    ProjectUnposted.class
})
public abstract class ProjectUnpostedBase
    extends ProjectAmount
{

    @XmlElement(name = "LossAmount")
    protected MoneyAmount lossAmount;

    /**
     * Gets the value of the lossAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getLossAmount() {
        return lossAmount;
    }

    /**
     * Sets the value of the lossAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setLossAmount(MoneyAmount value) {
        this.lossAmount = value;
    }

}
