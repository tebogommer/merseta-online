
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayablesTax complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayablesTax"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Tax"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesTaxKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchasesTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="FreightTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="MiscellaneousTaxAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *         &lt;element name="GLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="IsPosted" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayablesTax", propOrder = {
    "key",
    "purchasesTaxAmount",
    "freightTaxAmount",
    "miscellaneousTaxAmount",
    "glAccountKey",
    "isPosted"
})
public class PayablesTax
    extends Tax
{

    @XmlElement(name = "Key")
    protected PayablesTaxKey key;
    @XmlElement(name = "PurchasesTaxAmount")
    protected MoneyAmount purchasesTaxAmount;
    @XmlElement(name = "FreightTaxAmount")
    protected MoneyAmount freightTaxAmount;
    @XmlElement(name = "MiscellaneousTaxAmount")
    protected MoneyAmount miscellaneousTaxAmount;
    @XmlElement(name = "GLAccountKey")
    protected GLAccountNumberKey glAccountKey;
    @XmlElement(name = "IsPosted", required = true, type = Boolean.class, nillable = true)
    protected Boolean isPosted;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesTaxKey }
     *     
     */
    public PayablesTaxKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesTaxKey }
     *     
     */
    public void setKey(PayablesTaxKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the purchasesTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getPurchasesTaxAmount() {
        return purchasesTaxAmount;
    }

    /**
     * Sets the value of the purchasesTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setPurchasesTaxAmount(MoneyAmount value) {
        this.purchasesTaxAmount = value;
    }

    /**
     * Gets the value of the freightTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getFreightTaxAmount() {
        return freightTaxAmount;
    }

    /**
     * Sets the value of the freightTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setFreightTaxAmount(MoneyAmount value) {
        this.freightTaxAmount = value;
    }

    /**
     * Gets the value of the miscellaneousTaxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getMiscellaneousTaxAmount() {
        return miscellaneousTaxAmount;
    }

    /**
     * Sets the value of the miscellaneousTaxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setMiscellaneousTaxAmount(MoneyAmount value) {
        this.miscellaneousTaxAmount = value;
    }

    /**
     * Gets the value of the glAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getGLAccountKey() {
        return glAccountKey;
    }

    /**
     * Sets the value of the glAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setGLAccountKey(GLAccountNumberKey value) {
        this.glAccountKey = value;
    }

    /**
     * Gets the value of the isPosted property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsPosted() {
        return isPosted;
    }

    /**
     * Sets the value of the isPosted property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsPosted(Boolean value) {
        this.isPosted = value;
    }

}
