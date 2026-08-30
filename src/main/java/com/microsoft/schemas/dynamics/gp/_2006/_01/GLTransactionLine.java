
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;


/**
 * <p>Java class for GLTransactionLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GLTransactionLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLTransactionLineKey" minOccurs="0"/&gt;
 *         &lt;element name="CreditAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Amount" minOccurs="0"/&gt;
 *         &lt;element name="DebitAmount" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}Amount" minOccurs="0"/&gt;
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="GLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *         &lt;element name="ExchangeRate" type="{http://www.w3.org/2001/XMLSchema}decimal"/&gt;
 *         &lt;element name="ExchangeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="IntercompanyKey" type="{http://schemas.microsoft.com/dynamics/2006/01}CompanyKey" minOccurs="0"/&gt;
 *         &lt;element name="OriginatingDocument" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLOriginatingDocumentLine" minOccurs="0"/&gt;
 *         &lt;element name="Tax" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLTax" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GLTransactionLine", propOrder = {
    "key",
    "creditAmount",
    "debitAmount",
    "description",
    "glAccountKey",
    "exchangeRate",
    "exchangeDate",
    "intercompanyKey",
    "originatingDocument",
    "tax"
})
public class GLTransactionLine
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected GLTransactionLineKey key;
    @XmlElement(name = "CreditAmount")
    protected Amount creditAmount;
    @XmlElement(name = "DebitAmount")
    protected Amount debitAmount;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "GLAccountKey")
    protected GLAccountNumberKey glAccountKey;
    @XmlElement(name = "ExchangeRate", required = true, nillable = true)
    protected BigDecimal exchangeRate;
    @XmlElement(name = "ExchangeDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar exchangeDate;
    @XmlElement(name = "IntercompanyKey")
    protected CompanyKey intercompanyKey;
    @XmlElement(name = "OriginatingDocument")
    protected GLOriginatingDocumentLine originatingDocument;
    @XmlElement(name = "Tax")
    protected GLTax tax;

    
    
    public GLTransactionLine() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GLTransactionLine(GLTransactionLineKey key) {
		super();
		this.key = key;
	}

	/**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link GLTransactionLineKey }
     *     
     */
    public GLTransactionLineKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLTransactionLineKey }
     *     
     */
    public void setKey(GLTransactionLineKey value) {
        this.key = value;
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
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
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
     * Gets the value of the exchangeRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * Sets the value of the exchangeRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setExchangeRate(BigDecimal value) {
        this.exchangeRate = value;
    }

    /**
     * Gets the value of the exchangeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExchangeDate() {
        return exchangeDate;
    }

    /**
     * Sets the value of the exchangeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExchangeDate(XMLGregorianCalendar value) {
        this.exchangeDate = value;
    }

    /**
     * Gets the value of the intercompanyKey property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyKey }
     *     
     */
    public CompanyKey getIntercompanyKey() {
        return intercompanyKey;
    }

    /**
     * Sets the value of the intercompanyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyKey }
     *     
     */
    public void setIntercompanyKey(CompanyKey value) {
        this.intercompanyKey = value;
    }

    /**
     * Gets the value of the originatingDocument property.
     * 
     * @return
     *     possible object is
     *     {@link GLOriginatingDocumentLine }
     *     
     */
    public GLOriginatingDocumentLine getOriginatingDocument() {
        return originatingDocument;
    }

    /**
     * Sets the value of the originatingDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLOriginatingDocumentLine }
     *     
     */
    public void setOriginatingDocument(GLOriginatingDocumentLine value) {
        this.originatingDocument = value;
    }

    /**
     * Gets the value of the tax property.
     * 
     * @return
     *     possible object is
     *     {@link GLTax }
     *     
     */
    public GLTax getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLTax }
     *     
     */
    public void setTax(GLTax value) {
        this.tax = value;
    }

}
