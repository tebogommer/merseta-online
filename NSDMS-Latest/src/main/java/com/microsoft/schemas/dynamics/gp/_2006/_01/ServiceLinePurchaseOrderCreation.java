
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ServiceLinePurchaseOrderCreation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceLinePurchaseOrderCreation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ConsolidateOnPO" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="CreatePO" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="PromiseDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="VendorKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}VendorKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceLinePurchaseOrderCreation", propOrder = {
    "consolidateOnPO",
    "createPO",
    "promiseDate",
    "vendorKey"
})
public class ServiceLinePurchaseOrderCreation {

    @XmlElement(name = "ConsolidateOnPO", required = true, type = Boolean.class, nillable = true)
    protected Boolean consolidateOnPO;
    @XmlElement(name = "CreatePO", required = true, type = Boolean.class, nillable = true)
    protected Boolean createPO;
    @XmlElement(name = "PromiseDate", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar promiseDate;
    @XmlElement(name = "VendorKey")
    protected VendorKey vendorKey;

    /**
     * Gets the value of the consolidateOnPO property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isConsolidateOnPO() {
        return consolidateOnPO;
    }

    /**
     * Sets the value of the consolidateOnPO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setConsolidateOnPO(Boolean value) {
        this.consolidateOnPO = value;
    }

    /**
     * Gets the value of the createPO property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCreatePO() {
        return createPO;
    }

    /**
     * Sets the value of the createPO property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCreatePO(Boolean value) {
        this.createPO = value;
    }

    /**
     * Gets the value of the promiseDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPromiseDate() {
        return promiseDate;
    }

    /**
     * Sets the value of the promiseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPromiseDate(XMLGregorianCalendar value) {
        this.promiseDate = value;
    }

    /**
     * Gets the value of the vendorKey property.
     * 
     * @return
     *     possible object is
     *     {@link VendorKey }
     *     
     */
    public VendorKey getVendorKey() {
        return vendorKey;
    }

    /**
     * Sets the value of the vendorKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link VendorKey }
     *     
     */
    public void setVendorKey(VendorKey value) {
        this.vendorKey = value;
    }

}
