
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserAssignableBusinessObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserAssignableBusinessObject"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BusinessObjectTypeId" type="{http://microsoft.com/wsdl/types/}guid"/&gt;
 *         &lt;element name="BusinessObjectTypeDisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="IsKeyEqualToDisplayId" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="SupportedContainment" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}SupportedContainment"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserAssignableBusinessObject", propOrder = {
    "businessObjectTypeId",
    "businessObjectTypeDisplayName",
    "isKeyEqualToDisplayId",
    "supportedContainment"
})
public class UserAssignableBusinessObject {

    @XmlElement(name = "BusinessObjectTypeId", required = true)
    protected String businessObjectTypeId;
    @XmlElement(name = "BusinessObjectTypeDisplayName")
    protected String businessObjectTypeDisplayName;
    @XmlElement(name = "IsKeyEqualToDisplayId")
    protected boolean isKeyEqualToDisplayId;
    @XmlList
    @XmlElement(name = "SupportedContainment", required = true)
    protected List<String> supportedContainment;

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
     * Gets the value of the businessObjectTypeDisplayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessObjectTypeDisplayName() {
        return businessObjectTypeDisplayName;
    }

    /**
     * Sets the value of the businessObjectTypeDisplayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessObjectTypeDisplayName(String value) {
        this.businessObjectTypeDisplayName = value;
    }

    /**
     * Gets the value of the isKeyEqualToDisplayId property.
     * 
     */
    public boolean isIsKeyEqualToDisplayId() {
        return isKeyEqualToDisplayId;
    }

    /**
     * Sets the value of the isKeyEqualToDisplayId property.
     * 
     */
    public void setIsKeyEqualToDisplayId(boolean value) {
        this.isKeyEqualToDisplayId = value;
    }

    /**
     * Gets the value of the supportedContainment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supportedContainment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupportedContainment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSupportedContainment() {
        if (supportedContainment == null) {
            supportedContainment = new ArrayList<String>();
        }
        return this.supportedContainment;
    }

}
