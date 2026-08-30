
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ReceivablesCreditDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceivablesCreditDocument"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReceivablesDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DiscountReturned" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceivablesCreditDocument", propOrder = {
    "discountReturned"
})
@XmlSeeAlso({
    ReceivablesCreditMemo.class,
    ReceivablesReturn.class
})
public abstract class ReceivablesCreditDocument
    extends ReceivablesDocument
{

    @XmlElement(name = "DiscountReturned")
    protected MoneyAmount discountReturned;

    /**
     * Gets the value of the discountReturned property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getDiscountReturned() {
        return discountReturned;
    }

    /**
     * Sets the value of the discountReturned property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setDiscountReturned(MoneyAmount value) {
        this.discountReturned = value;
    }

}
