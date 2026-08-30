
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChangedTaxScheduleDetailKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChangedTaxScheduleDetailKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ChangedBusinessObjectKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="TaxScheduleDetailKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}TaxScheduleDetailKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangedTaxScheduleDetailKey", propOrder = {
    "taxScheduleDetailKey"
})
public class ChangedTaxScheduleDetailKey
    extends ChangedBusinessObjectKey
{

    @XmlElement(name = "TaxScheduleDetailKey")
    protected TaxScheduleDetailKey taxScheduleDetailKey;

    /**
     * Gets the value of the taxScheduleDetailKey property.
     * 
     * @return
     *     possible object is
     *     {@link TaxScheduleDetailKey }
     *     
     */
    public TaxScheduleDetailKey getTaxScheduleDetailKey() {
        return taxScheduleDetailKey;
    }

    /**
     * Sets the value of the taxScheduleDetailKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxScheduleDetailKey }
     *     
     */
    public void setTaxScheduleDetailKey(TaxScheduleDetailKey value) {
        this.taxScheduleDetailKey = value;
    }

}
