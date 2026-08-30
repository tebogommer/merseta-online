
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics._2006._01.Context;
import com.microsoft.schemas.dynamics._2006._01.OrganizationKey;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="businessObjectTypeId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="organizationKey" type="{http://schemas.microsoft.com/dynamics/2006/01}OrganizationKey" minOccurs="0"/&gt;
 *         &lt;element name="criteria" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObjectSummaryCriteria" minOccurs="0"/&gt;
 *         &lt;element name="context" type="{http://schemas.microsoft.com/dynamics/2006/01}Context" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "businessObjectTypeId",
    "organizationKey",
    "criteria",
    "context"
})
@XmlRootElement(name = "GetBusinessObjectSummaryList")
public class GetBusinessObjectSummaryList {

    @XmlElement(required = true)
    protected String businessObjectTypeId;
    protected OrganizationKey organizationKey;
    protected BusinessObjectSummaryCriteria criteria;
    protected Context context;

    /**
     * Gets the value of the businessObjectTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessObjectTypeId() {
        return businessObjectTypeId;
    }

    /**
     * Sets the value of the businessObjectTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessObjectTypeId(String value) {
        this.businessObjectTypeId = value;
    }

    /**
     * Gets the value of the organizationKey property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationKey }
     *     
     */
    public OrganizationKey getOrganizationKey() {
        return organizationKey;
    }

    /**
     * Sets the value of the organizationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationKey }
     *     
     */
    public void setOrganizationKey(OrganizationKey value) {
        this.organizationKey = value;
    }

    /**
     * Gets the value of the criteria property.
     * 
     * @return
     *     possible object is
     *     {@link BusinessObjectSummaryCriteria }
     *     
     */
    public BusinessObjectSummaryCriteria getCriteria() {
        return criteria;
    }

    /**
     * Sets the value of the criteria property.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessObjectSummaryCriteria }
     *     
     */
    public void setCriteria(BusinessObjectSummaryCriteria value) {
        this.criteria = value;
    }

    /**
     * Gets the value of the context property.
     * 
     * @return
     *     possible object is
     *     {@link Context }
     *     
     */
    public Context getContext() {
        return context;
    }

    /**
     * Sets the value of the context property.
     * 
     * @param value
     *     allowed object is
     *     {@link Context }
     *     
     */
    public void setContext(Context value) {
        this.context = value;
    }

}
