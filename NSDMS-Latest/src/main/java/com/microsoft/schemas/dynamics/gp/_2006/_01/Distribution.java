
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Distribution complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Distribution"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DistributionTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DistributionTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="Reference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="GLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="DebitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Amount" minOccurs="0"/&gt;
 *         &lt;element name="CreditAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Amount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Distribution", propOrder = {
    "distributionTypeKey",
    "reference",
    "glAccountKey",
    "debitAmount",
    "creditAmount"
})
@XmlSeeAlso({
    ReturnMaterialAuthorizationDistribution.class,
    ServiceDistribution.class,
    PayablesDistribution.class,
    ReceivablesDistribution.class,
    CashReceiptDistribution.class,
    SalesDistribution.class
})
public abstract class Distribution
    extends BusinessObject
{

    @XmlElement(name = "DistributionTypeKey")
    protected DistributionTypeKey distributionTypeKey;
    @XmlElement(name = "Reference")
    protected String reference;
    @XmlElement(name = "GLAccountKey")
    protected GLAccountNumberKey glAccountKey;
    @XmlElement(name = "DebitAmount")
    protected Amount debitAmount;
    @XmlElement(name = "CreditAmount")
    protected Amount creditAmount;

    /**
     * Gets the value of the distributionTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link DistributionTypeKey }
     *     
     */
    public DistributionTypeKey getDistributionTypeKey() {
        return distributionTypeKey;
    }

    /**
     * Sets the value of the distributionTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link DistributionTypeKey }
     *     
     */
    public void setDistributionTypeKey(DistributionTypeKey value) {
        this.distributionTypeKey = value;
    }

    /**
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
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
     * Gets the value of the debitAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Amount }
     *     
     */
    public Amount getDebitAmount() {
        return debitAmount;
    }

    /**
     * Sets the value of the debitAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Amount }
     *     
     */
    public void setDebitAmount(Amount value) {
        this.debitAmount = value;
    }

    /**
     * Gets the value of the creditAmount property.
     * 
     * @return
     *     possible object is
     *     {@link Amount }
     *     
     */
    public Amount getCreditAmount() {
        return creditAmount;
    }

    /**
     * Sets the value of the creditAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Amount }
     *     
     */
    public void setCreditAmount(Amount value) {
        this.creditAmount = value;
    }

}
