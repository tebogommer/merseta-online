
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CurrencyAccessCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CurrencyAccessCriteria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}Criteria"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CompanyId" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BetweenRestrictionOfNullableOfInt32" minOccurs="0"/&gt;
 *         &lt;element name="CurrencyISOCode" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}LikeRestrictionOfString" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrencyAccessCriteria", propOrder = {
    "companyId",
    "currencyISOCode"
})
public class CurrencyAccessCriteria
    extends Criteria
{

    @XmlElement(name = "CompanyId")
    protected BetweenRestrictionOfNullableOfInt32 companyId;
    @XmlElement(name = "CurrencyISOCode")
    protected LikeRestrictionOfString currencyISOCode;

    /**
     * Gets the value of the companyId property.
     * 
     * @return
     *     possible object is
     *     {@link BetweenRestrictionOfNullableOfInt32 }
     *     
     */
    public BetweenRestrictionOfNullableOfInt32 getCompanyId() {
        return companyId;
    }

    /**
     * Sets the value of the companyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BetweenRestrictionOfNullableOfInt32 }
     *     
     */
    public void setCompanyId(BetweenRestrictionOfNullableOfInt32 value) {
        this.companyId = value;
    }

    /**
     * Gets the value of the currencyISOCode property.
     * 
     * @return
     *     possible object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public LikeRestrictionOfString getCurrencyISOCode() {
        return currencyISOCode;
    }

    /**
     * Sets the value of the currencyISOCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link LikeRestrictionOfString }
     *     
     */
    public void setCurrencyISOCode(LikeRestrictionOfString value) {
        this.currencyISOCode = value;
    }

}
