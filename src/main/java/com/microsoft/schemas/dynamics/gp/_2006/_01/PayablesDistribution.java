
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;


/**
 * <p>Java class for PayablesDistribution complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayablesDistribution"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Distribution"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}PayablesDistributionKey" minOccurs="0"/&gt;
 *         &lt;element name="CompanyKey" type="{http://schemas.microsoft.com/dynamics/2006/01}CompanyKey" minOccurs="0"/&gt;
 *         &lt;element name="PostingDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
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
@XmlType(name = "PayablesDistribution", propOrder = {
    "key",
    "companyKey",
    "postingDate",
    "isPosted"
})
public class PayablesDistribution
    extends Distribution
{

    @XmlElement(name = "Key")
    protected PayablesDistributionKey key;
    @XmlElement(name = "CompanyKey")
    protected CompanyKey companyKey;
    @XmlElement(name = "PostingDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar postingDate;
    @XmlElement(name = "IsPosted", required = true, type = Boolean.class, nillable = true)
    protected Boolean isPosted;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link PayablesDistributionKey }
     *     
     */
    public PayablesDistributionKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayablesDistributionKey }
     *     
     */
    public void setKey(PayablesDistributionKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the companyKey property.
     * 
     * @return
     *     possible object is
     *     {@link CompanyKey }
     *     
     */
    public CompanyKey getCompanyKey() {
        return companyKey;
    }

    /**
     * Sets the value of the companyKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyKey }
     *     
     */
    public void setCompanyKey(CompanyKey value) {
        this.companyKey = value;
    }

    /**
     * Gets the value of the postingDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPostingDate() {
        return postingDate;
    }

    /**
     * Sets the value of the postingDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPostingDate(XMLGregorianCalendar value) {
        this.postingDate = value;
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
