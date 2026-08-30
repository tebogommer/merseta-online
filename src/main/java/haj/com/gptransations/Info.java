package haj.com.gptransations;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import haj.com.constants.HAJConstants;


/**
 * <p>Java class for info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="info"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="VendID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Vendname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="VoucherNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocFunctionalAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DockBalance1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocHold1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocHoldReason1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "info", propOrder = {
    "vendID",
    "vendname",
    "voucherNumber",
    "docType",
    "docDate",
    "docNumber",
    "docDescription",
    "docFunctionalAmount",
    "dockBalance1",
    "docHold1",
    "docHoldReason1"
})
public class Info {

    @XmlElement(name = "VendID")
    protected String vendID;
    @XmlElement(name = "Vendname")
    protected String vendname;
    @XmlElement(name = "VoucherNumber")
    protected String voucherNumber;
    @XmlElement(name = "DocType")
    protected String docType;
    @XmlElement(name = "DocDate")
    protected String docDate;
    @XmlElement(name = "DocNumber")
    protected String docNumber;
    @XmlElement(name = "DocDescription")
    protected String docDescription;
    @XmlElement(name = "DocFunctionalAmount")
    protected String docFunctionalAmount;
    @XmlElement(name = "DockBalance1")
    protected String dockBalance1;
    @XmlElement(name = "DocHold1")
    protected String docHold1;
    @XmlElement(name = "DocHoldReason1")
    protected String docHoldReason1;

    /**
     * Gets the value of the vendID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendID() {
        return vendID;
    }

    /**
     * Sets the value of the vendID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendID(String value) {
        this.vendID = value;
    }

    /**
     * Gets the value of the vendname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendname() {
        return vendname;
    }

    /**
     * Sets the value of the vendname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendname(String value) {
        this.vendname = value;
    }

    /**
     * Gets the value of the voucherNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVoucherNumber() {
        return voucherNumber;
    }

    /**
     * Sets the value of the voucherNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVoucherNumber(String value) {
        this.voucherNumber = value;
    }

    /**
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocType() {
        return docType;
    }
    
    public Date getDocDateReturnAsDate() {
    	if (docDate == null || docDate.isEmpty()) {
			return null;
		} else {
			try {
				return HAJConstants.sdfRemintanceService.parse(docDate);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocType(String value) {
        this.docType = value;
    }

    /**
     * Gets the value of the docDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocDate() {
        return docDate;
    }

    /**
     * Sets the value of the docDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocDate(String value) {
        this.docDate = value;
    }

    /**
     * Gets the value of the docNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocNumber() {
        return docNumber;
    }

    /**
     * Sets the value of the docNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocNumber(String value) {
        this.docNumber = value;
    }

    /**
     * Gets the value of the docDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocDescription() {
        return docDescription;
    }

    /**
     * Sets the value of the docDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocDescription(String value) {
        this.docDescription = value;
    }

    /**
     * Gets the value of the docFunctionalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocFunctionalAmount() {
        return docFunctionalAmount;
    }
    
    public Double getDocFunctionalAmountAsDoubleValue() {
        if (docFunctionalAmount == null || docFunctionalAmount.trim().isEmpty()) {
            return null;
        } else {
        	String docFunctionalAmount1 = docFunctionalAmount.replace(",", ".");
        	return Double.valueOf(docFunctionalAmount1);
        }
    }


    /**
     * Sets the value of the docFunctionalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocFunctionalAmount(String value) {
        this.docFunctionalAmount = value;
    }

    /**
     * Gets the value of the dockBalance1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDockBalance1() {
        return dockBalance1;
    }
    
    public Double getDockBalance1AsDoubleValue() {
        if (dockBalance1 == null || dockBalance1.trim().isEmpty()) {
            return null;
        } else {
        	String dockBalance2 = dockBalance1.replace(",", ".");
            return Double.valueOf(dockBalance2);
        }
    }


    /**
     * Sets the value of the dockBalance1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDockBalance1(String value) {
        this.dockBalance1 = value;
    }

    /**
     * Gets the value of the docHold1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocHold1() {
        return docHold1;
    }

    /**
     * Sets the value of the docHold1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocHold1(String value) {
        this.docHold1 = value;
    }

    /**
     * Gets the value of the docHoldReason1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocHoldReason1() {
        return docHoldReason1;
    }

    /**
     * Sets the value of the docHoldReason1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocHoldReason1(String value) {
        this.docHoldReason1 = value;
    }

}
