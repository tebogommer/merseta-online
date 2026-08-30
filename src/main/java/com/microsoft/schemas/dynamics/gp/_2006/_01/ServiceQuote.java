
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ServiceQuote complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceQuote"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ServiceCallDocument"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Parts" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceQuotePartLine" minOccurs="0"/&gt;
 *         &lt;element name="Labor" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceQuoteLaborLine" minOccurs="0"/&gt;
 *         &lt;element name="AdditionalCharges" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceQuoteAdditionalChargeLine" minOccurs="0"/&gt;
 *         &lt;element name="Expenses" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceQuoteExpenseLine" minOccurs="0"/&gt;
 *         &lt;element name="EquipmentCodes" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfServiceEquipmentCode" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceQuote", propOrder = {
    "parts",
    "labor",
    "additionalCharges",
    "expenses",
    "equipmentCodes"
})
public class ServiceQuote
    extends ServiceCallDocument
{

    @XmlElement(name = "Parts")
    protected ArrayOfServiceQuotePartLine parts;
    @XmlElement(name = "Labor")
    protected ArrayOfServiceQuoteLaborLine labor;
    @XmlElement(name = "AdditionalCharges")
    protected ArrayOfServiceQuoteAdditionalChargeLine additionalCharges;
    @XmlElement(name = "Expenses")
    protected ArrayOfServiceQuoteExpenseLine expenses;
    @XmlElement(name = "EquipmentCodes")
    protected ArrayOfServiceEquipmentCode equipmentCodes;

    /**
     * Gets the value of the parts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceQuotePartLine }
     *     
     */
    public ArrayOfServiceQuotePartLine getParts() {
        return parts;
    }

    /**
     * Sets the value of the parts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceQuotePartLine }
     *     
     */
    public void setParts(ArrayOfServiceQuotePartLine value) {
        this.parts = value;
    }

    /**
     * Gets the value of the labor property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceQuoteLaborLine }
     *     
     */
    public ArrayOfServiceQuoteLaborLine getLabor() {
        return labor;
    }

    /**
     * Sets the value of the labor property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceQuoteLaborLine }
     *     
     */
    public void setLabor(ArrayOfServiceQuoteLaborLine value) {
        this.labor = value;
    }

    /**
     * Gets the value of the additionalCharges property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceQuoteAdditionalChargeLine }
     *     
     */
    public ArrayOfServiceQuoteAdditionalChargeLine getAdditionalCharges() {
        return additionalCharges;
    }

    /**
     * Sets the value of the additionalCharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceQuoteAdditionalChargeLine }
     *     
     */
    public void setAdditionalCharges(ArrayOfServiceQuoteAdditionalChargeLine value) {
        this.additionalCharges = value;
    }

    /**
     * Gets the value of the expenses property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceQuoteExpenseLine }
     *     
     */
    public ArrayOfServiceQuoteExpenseLine getExpenses() {
        return expenses;
    }

    /**
     * Sets the value of the expenses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceQuoteExpenseLine }
     *     
     */
    public void setExpenses(ArrayOfServiceQuoteExpenseLine value) {
        this.expenses = value;
    }

    /**
     * Gets the value of the equipmentCodes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfServiceEquipmentCode }
     *     
     */
    public ArrayOfServiceEquipmentCode getEquipmentCodes() {
        return equipmentCodes;
    }

    /**
     * Sets the value of the equipmentCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfServiceEquipmentCode }
     *     
     */
    public void setEquipmentCodes(ArrayOfServiceEquipmentCode value) {
        this.equipmentCodes = value;
    }

}
