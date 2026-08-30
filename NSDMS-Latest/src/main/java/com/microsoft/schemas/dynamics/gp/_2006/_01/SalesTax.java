
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesTax complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesTax"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Tax"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="IsTaxableTax" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="TotalTaxPotentialAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}MoneyAmount" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesTax", propOrder = {
    "glAccountKey",
    "isTaxableTax",
    "totalTaxPotentialAmount"
})
@XmlSeeAlso({
    SalesDocumentTax.class,
    SalesLineTax.class
})
public abstract class SalesTax
    extends Tax
{

    @XmlElement(name = "GLAccountKey")
    protected GLAccountNumberKey glAccountKey;
    @XmlElement(name = "IsTaxableTax", required = true, type = Boolean.class, nillable = true)
    protected Boolean isTaxableTax;
    @XmlElement(name = "TotalTaxPotentialAmount")
    protected MoneyAmount totalTaxPotentialAmount;

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
     * Gets the value of the isTaxableTax property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTaxableTax() {
        return isTaxableTax;
    }

    /**
     * Sets the value of the isTaxableTax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTaxableTax(Boolean value) {
        this.isTaxableTax = value;
    }

    /**
     * Gets the value of the totalTaxPotentialAmount property.
     * 
     * @return
     *     possible object is
     *     {@link MoneyAmount }
     *     
     */
    public MoneyAmount getTotalTaxPotentialAmount() {
        return totalTaxPotentialAmount;
    }

    /**
     * Sets the value of the totalTaxPotentialAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link MoneyAmount }
     *     
     */
    public void setTotalTaxPotentialAmount(MoneyAmount value) {
        this.totalTaxPotentialAmount = value;
    }

}
