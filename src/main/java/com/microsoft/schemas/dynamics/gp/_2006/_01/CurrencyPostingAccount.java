
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CurrencyPostingAccount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurrencyPostingAccount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CurrencyPostingAccountKey" minOccurs="0"/&gt;
 *         &lt;element name="RealizedGainGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="RealizedLossGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="UnrealizedGainGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="UnrealizedLossGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="FinancialOffsetGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="PurchasingOffsetGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="SalesOffsetGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="RoundingWriteOffGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="RoundingDifferenceGLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrencyPostingAccount", propOrder = {
    "key",
    "realizedGainGLAccountKey",
    "realizedLossGLAccountKey",
    "unrealizedGainGLAccountKey",
    "unrealizedLossGLAccountKey",
    "financialOffsetGLAccountKey",
    "purchasingOffsetGLAccountKey",
    "salesOffsetGLAccountKey",
    "roundingWriteOffGLAccountKey",
    "roundingDifferenceGLAccountKey"
})
public class CurrencyPostingAccount
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected CurrencyPostingAccountKey key;
    @XmlElement(name = "RealizedGainGLAccountKey")
    protected GLAccountNumberKey realizedGainGLAccountKey;
    @XmlElement(name = "RealizedLossGLAccountKey")
    protected GLAccountNumberKey realizedLossGLAccountKey;
    @XmlElement(name = "UnrealizedGainGLAccountKey")
    protected GLAccountNumberKey unrealizedGainGLAccountKey;
    @XmlElement(name = "UnrealizedLossGLAccountKey")
    protected GLAccountNumberKey unrealizedLossGLAccountKey;
    @XmlElement(name = "FinancialOffsetGLAccountKey")
    protected GLAccountNumberKey financialOffsetGLAccountKey;
    @XmlElement(name = "PurchasingOffsetGLAccountKey")
    protected GLAccountNumberKey purchasingOffsetGLAccountKey;
    @XmlElement(name = "SalesOffsetGLAccountKey")
    protected GLAccountNumberKey salesOffsetGLAccountKey;
    @XmlElement(name = "RoundingWriteOffGLAccountKey")
    protected GLAccountNumberKey roundingWriteOffGLAccountKey;
    @XmlElement(name = "RoundingDifferenceGLAccountKey")
    protected GLAccountNumberKey roundingDifferenceGLAccountKey;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyPostingAccountKey }
     *     
     */
    public CurrencyPostingAccountKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyPostingAccountKey }
     *     
     */
    public void setKey(CurrencyPostingAccountKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the realizedGainGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getRealizedGainGLAccountKey() {
        return realizedGainGLAccountKey;
    }

    /**
     * Sets the value of the realizedGainGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setRealizedGainGLAccountKey(GLAccountNumberKey value) {
        this.realizedGainGLAccountKey = value;
    }

    /**
     * Gets the value of the realizedLossGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getRealizedLossGLAccountKey() {
        return realizedLossGLAccountKey;
    }

    /**
     * Sets the value of the realizedLossGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setRealizedLossGLAccountKey(GLAccountNumberKey value) {
        this.realizedLossGLAccountKey = value;
    }

    /**
     * Gets the value of the unrealizedGainGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getUnrealizedGainGLAccountKey() {
        return unrealizedGainGLAccountKey;
    }

    /**
     * Sets the value of the unrealizedGainGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setUnrealizedGainGLAccountKey(GLAccountNumberKey value) {
        this.unrealizedGainGLAccountKey = value;
    }

    /**
     * Gets the value of the unrealizedLossGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getUnrealizedLossGLAccountKey() {
        return unrealizedLossGLAccountKey;
    }

    /**
     * Sets the value of the unrealizedLossGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setUnrealizedLossGLAccountKey(GLAccountNumberKey value) {
        this.unrealizedLossGLAccountKey = value;
    }

    /**
     * Gets the value of the financialOffsetGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getFinancialOffsetGLAccountKey() {
        return financialOffsetGLAccountKey;
    }

    /**
     * Sets the value of the financialOffsetGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setFinancialOffsetGLAccountKey(GLAccountNumberKey value) {
        this.financialOffsetGLAccountKey = value;
    }

    /**
     * Gets the value of the purchasingOffsetGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getPurchasingOffsetGLAccountKey() {
        return purchasingOffsetGLAccountKey;
    }

    /**
     * Sets the value of the purchasingOffsetGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setPurchasingOffsetGLAccountKey(GLAccountNumberKey value) {
        this.purchasingOffsetGLAccountKey = value;
    }

    /**
     * Gets the value of the salesOffsetGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getSalesOffsetGLAccountKey() {
        return salesOffsetGLAccountKey;
    }

    /**
     * Sets the value of the salesOffsetGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setSalesOffsetGLAccountKey(GLAccountNumberKey value) {
        this.salesOffsetGLAccountKey = value;
    }

    /**
     * Gets the value of the roundingWriteOffGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getRoundingWriteOffGLAccountKey() {
        return roundingWriteOffGLAccountKey;
    }

    /**
     * Sets the value of the roundingWriteOffGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setRoundingWriteOffGLAccountKey(GLAccountNumberKey value) {
        this.roundingWriteOffGLAccountKey = value;
    }

    /**
     * Gets the value of the roundingDifferenceGLAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getRoundingDifferenceGLAccountKey() {
        return roundingDifferenceGLAccountKey;
    }

    /**
     * Sets the value of the roundingDifferenceGLAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setRoundingDifferenceGLAccountKey(GLAccountNumberKey value) {
        this.roundingDifferenceGLAccountKey = value;
    }

}
