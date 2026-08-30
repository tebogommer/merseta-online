
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CorporateAccount complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CorporateAccount"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CustomerKey" minOccurs="0"/&gt;
 *         &lt;element name="AllowReceiptsForMembers" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="BaseCreditCheckOnConsolidatedCorporateAccount" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="BaseFinanceChargeOnConsolidatedCorporateAccount" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="ApplyHoldActiveStatusOfParentAcrossCorporateAccount" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="DefaultNationalAccountVendorForMemberRefunds" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="Members" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfCorporateAccountMember" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CorporateAccount", propOrder = {
    "key",
    "allowReceiptsForMembers",
    "baseCreditCheckOnConsolidatedCorporateAccount",
    "baseFinanceChargeOnConsolidatedCorporateAccount",
    "applyHoldActiveStatusOfParentAcrossCorporateAccount",
    "defaultNationalAccountVendorForMemberRefunds",
    "members"
})
public class CorporateAccount
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected CustomerKey key;
    @XmlElement(name = "AllowReceiptsForMembers", required = true, type = Boolean.class, nillable = true)
    protected Boolean allowReceiptsForMembers;
    @XmlElement(name = "BaseCreditCheckOnConsolidatedCorporateAccount", required = true, type = Boolean.class, nillable = true)
    protected Boolean baseCreditCheckOnConsolidatedCorporateAccount;
    @XmlElement(name = "BaseFinanceChargeOnConsolidatedCorporateAccount", required = true, type = Boolean.class, nillable = true)
    protected Boolean baseFinanceChargeOnConsolidatedCorporateAccount;
    @XmlElement(name = "ApplyHoldActiveStatusOfParentAcrossCorporateAccount", required = true, type = Boolean.class, nillable = true)
    protected Boolean applyHoldActiveStatusOfParentAcrossCorporateAccount;
    @XmlElement(name = "DefaultNationalAccountVendorForMemberRefunds", required = true, type = Boolean.class, nillable = true)
    protected Boolean defaultNationalAccountVendorForMemberRefunds;
    @XmlElement(name = "Members")
    protected ArrayOfCorporateAccountMember members;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerKey }
     *     
     */
    public CustomerKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerKey }
     *     
     */
    public void setKey(CustomerKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the allowReceiptsForMembers property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllowReceiptsForMembers() {
        return allowReceiptsForMembers;
    }

    /**
     * Sets the value of the allowReceiptsForMembers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllowReceiptsForMembers(Boolean value) {
        this.allowReceiptsForMembers = value;
    }

    /**
     * Gets the value of the baseCreditCheckOnConsolidatedCorporateAccount property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBaseCreditCheckOnConsolidatedCorporateAccount() {
        return baseCreditCheckOnConsolidatedCorporateAccount;
    }

    /**
     * Sets the value of the baseCreditCheckOnConsolidatedCorporateAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBaseCreditCheckOnConsolidatedCorporateAccount(Boolean value) {
        this.baseCreditCheckOnConsolidatedCorporateAccount = value;
    }

    /**
     * Gets the value of the baseFinanceChargeOnConsolidatedCorporateAccount property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBaseFinanceChargeOnConsolidatedCorporateAccount() {
        return baseFinanceChargeOnConsolidatedCorporateAccount;
    }

    /**
     * Sets the value of the baseFinanceChargeOnConsolidatedCorporateAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBaseFinanceChargeOnConsolidatedCorporateAccount(Boolean value) {
        this.baseFinanceChargeOnConsolidatedCorporateAccount = value;
    }

    /**
     * Gets the value of the applyHoldActiveStatusOfParentAcrossCorporateAccount property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isApplyHoldActiveStatusOfParentAcrossCorporateAccount() {
        return applyHoldActiveStatusOfParentAcrossCorporateAccount;
    }

    /**
     * Sets the value of the applyHoldActiveStatusOfParentAcrossCorporateAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setApplyHoldActiveStatusOfParentAcrossCorporateAccount(Boolean value) {
        this.applyHoldActiveStatusOfParentAcrossCorporateAccount = value;
    }

    /**
     * Gets the value of the defaultNationalAccountVendorForMemberRefunds property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDefaultNationalAccountVendorForMemberRefunds() {
        return defaultNationalAccountVendorForMemberRefunds;
    }

    /**
     * Sets the value of the defaultNationalAccountVendorForMemberRefunds property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDefaultNationalAccountVendorForMemberRefunds(Boolean value) {
        this.defaultNationalAccountVendorForMemberRefunds = value;
    }

    /**
     * Gets the value of the members property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCorporateAccountMember }
     *     
     */
    public ArrayOfCorporateAccountMember getMembers() {
        return members;
    }

    /**
     * Sets the value of the members property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCorporateAccountMember }
     *     
     */
    public void setMembers(ArrayOfCorporateAccountMember value) {
        this.members = value;
    }

}
