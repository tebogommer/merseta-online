package haj.com.gptransations;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import haj.com.constants.HAJConstants;


/**
 * <p>Java class for RecentPayments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RecentPayments"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OrgID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OrgName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Pdoctype" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="AnType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ADate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Anumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ADescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Pamount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Aamount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Pbalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Abalance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DocAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="PApliedAmount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecentPayments", propOrder = {
    "orgID",
    "orgName",
    "pdoctype",
    "anType",
    "pDate",
    "aDate",
    "pNumber",
    "anumber",
    "pDescription",
    "aDescription",
    "pamount",
    "aamount",
    "pbalance",
    "abalance",
    "docAmount",
    "pApliedAmount"
})
public class RecentPayments {

    @XmlElement(name = "OrgID")
    protected String orgID;
    @XmlElement(name = "OrgName")
    protected String orgName;
    @XmlElement(name = "Pdoctype")
    protected String pdoctype;
    @XmlElement(name = "AnType")
    protected String anType;
    @XmlElement(name = "PDate")
    protected String pDate;
    @XmlElement(name = "ADate")
    protected String aDate;
    @XmlElement(name = "PNumber")
    protected String pNumber;
    @XmlElement(name = "Anumber")
    protected String anumber;
    @XmlElement(name = "PDescription")
    protected String pDescription;
    @XmlElement(name = "ADescription")
    protected String aDescription;
    @XmlElement(name = "Pamount")
    protected String pamount;
    @XmlElement(name = "Aamount")
    protected String aamount;
    @XmlElement(name = "Pbalance")
    protected String pbalance;
    @XmlElement(name = "Abalance")
    protected String abalance;
    @XmlElement(name = "DocAmount")
    protected String docAmount;
    @XmlElement(name = "PApliedAmount")
    protected String pApliedAmount;

    /**
     * Gets the value of the orgID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgID() {
        return orgID;
    }

    /**
     * Sets the value of the orgID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgID(String value) {
        this.orgID = value;
    }

    /**
     * Gets the value of the orgName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * Sets the value of the orgName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgName(String value) {
        this.orgName = value;
    }

    /**
     * Gets the value of the pdoctype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPdoctype() {
        return pdoctype;
    }

    /**
     * Sets the value of the pdoctype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPdoctype(String value) {
        this.pdoctype = value;
    }

    /**
     * Gets the value of the anType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnType() {
        return anType;
    }

    /**
     * Sets the value of the anType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnType(String value) {
        this.anType = value;
    }

    /**
     * Gets the value of the pDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPDate() {
        return pDate;
    }
    
    public Date getPDateReturnAsDate() {
    	if (pDate == null || pDate.isEmpty()) {
			return null;
		} else {
			try {
				return HAJConstants.sdfRemintanceService.parse(pDate);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
    }

    /**
     * Sets the value of the pDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPDate(String value) {
        this.pDate = value;
    }

    /**
     * Gets the value of the aDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADate() {
        return aDate;
    }
    
    public Date getADateReturnAsDate() {
    	if (aDate == null || aDate.isEmpty()) {
			return null;
		} else {
			try {
				return HAJConstants.sdfRemintanceService.parse(aDate);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
    }

    /**
     * Sets the value of the aDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADate(String value) {
        this.aDate = value;
    }

    /**
     * Gets the value of the pNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPNumber() {
        return pNumber;
    }

    /**
     * Sets the value of the pNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPNumber(String value) {
        this.pNumber = value;
    }

    /**
     * Gets the value of the anumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnumber() {
        return anumber;
    }

    /**
     * Sets the value of the anumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnumber(String value) {
        this.anumber = value;
    }

    /**
     * Gets the value of the pDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPDescription() {
        return pDescription;
    }

    /**
     * Sets the value of the pDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPDescription(String value) {
        this.pDescription = value;
    }

    /**
     * Gets the value of the aDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADescription() {
        return aDescription;
    }

    /**
     * Sets the value of the aDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADescription(String value) {
        this.aDescription = value;
    }

    /**
     * Gets the value of the pamount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPamount() {
        return pamount;
    }
    
    public Double getPamountAsDoubleValue() {
	    if (pamount == null || pamount.trim().isEmpty()) {
	        return null;
	    } else {
			String pamount1 = pamount.replace(",", ".");
	        return Double.valueOf(pamount1);
	    }
    }

    /**
     * Sets the value of the pamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPamount(String value) {
        this.pamount = value;
    }

    /**
     * Gets the value of the aamount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAamount() {
        return aamount;
    }
    
    public Double getAamountAsDoubleValue() {
	    if (aamount == null || aamount.trim().isEmpty()) {
	        return null;
	    } else {
			String aamount1 = aamount.replace(",", ".");
	        return Double.valueOf(aamount1);
	    }
    }

    /**
     * Sets the value of the aamount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAamount(String value) {
        this.aamount = value;
    }

    /**
     * Gets the value of the pbalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPbalance() {
        return pbalance;
    }
    
    public Double getPbalanceAsDoubleValue() {
	    if (pbalance == null || pbalance.trim().isEmpty()) {
	        return null;
	    } else {
			String pbalance1 = pbalance.replace(",", ".");
	        return Double.valueOf(pbalance1);
	    }
    }

    /**
     * Sets the value of the pbalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPbalance(String value) {
        this.pbalance = value;
    }

    /**
     * Gets the value of the abalance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbalance() {
        return abalance;
    }
    
    public Double getAbalanceAsDoubleValue() {
	    if (abalance == null || abalance.trim().isEmpty()) {
	        return null;
	    } else {
			String abalance1 = abalance.replace(",", ".");
	        return Double.valueOf(abalance1);
	    }
    }

    /**
     * Sets the value of the abalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbalance(String value) {
        this.abalance = value;
    }

    /**
     * Gets the value of the docAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocAmount() {
        return docAmount;
    }
    
    public Double getDocAmountAsDoubleValue() {
	    if (docAmount == null || docAmount.trim().isEmpty()) {
	        return null;
	    } else {
			String docAmount1 = docAmount.replace(",", ".");
	        return Double.valueOf(docAmount1);
	    }
    }

    /**
     * Sets the value of the docAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocAmount(String value) {
        this.docAmount = value;
    }

    /**
     * Gets the value of the pApliedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPApliedAmount() {
        return pApliedAmount;
    }
    
    public Double getPApliedAmountAsDoubleValue() {
	    if (pApliedAmount == null || pApliedAmount.trim().isEmpty()) {
	        return null;
	    } else {
			String pApliedAmount1 = pApliedAmount.replace(",", ".");
	        return Double.valueOf(pApliedAmount1);
	    }
    }

    /**
     * Sets the value of the pApliedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPApliedAmount(String value) {
        this.pApliedAmount = value;
    }

}
