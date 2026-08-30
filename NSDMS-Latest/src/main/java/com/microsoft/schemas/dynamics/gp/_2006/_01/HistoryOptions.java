
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HistoryOptions complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HistoryOptions"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="KeepCalendarHistory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="KeepFiscalHistory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="KeepTransactionHistory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="KeepDistributionHistory" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HistoryOptions", propOrder = {
    "keepCalendarHistory",
    "keepFiscalHistory",
    "keepTransactionHistory",
    "keepDistributionHistory"
})
public class HistoryOptions {

    @XmlElement(name = "KeepCalendarHistory", required = true, type = Boolean.class, nillable = true)
    protected Boolean keepCalendarHistory;
    @XmlElement(name = "KeepFiscalHistory", required = true, type = Boolean.class, nillable = true)
    protected Boolean keepFiscalHistory;
    @XmlElement(name = "KeepTransactionHistory", required = true, type = Boolean.class, nillable = true)
    protected Boolean keepTransactionHistory;
    @XmlElement(name = "KeepDistributionHistory", required = true, type = Boolean.class, nillable = true)
    protected Boolean keepDistributionHistory;

    /**
     * Gets the value of the keepCalendarHistory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeepCalendarHistory() {
        return keepCalendarHistory;
    }

    /**
     * Sets the value of the keepCalendarHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepCalendarHistory(Boolean value) {
        this.keepCalendarHistory = value;
    }

    /**
     * Gets the value of the keepFiscalHistory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeepFiscalHistory() {
        return keepFiscalHistory;
    }

    /**
     * Sets the value of the keepFiscalHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepFiscalHistory(Boolean value) {
        this.keepFiscalHistory = value;
    }

    /**
     * Gets the value of the keepTransactionHistory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeepTransactionHistory() {
        return keepTransactionHistory;
    }

    /**
     * Sets the value of the keepTransactionHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepTransactionHistory(Boolean value) {
        this.keepTransactionHistory = value;
    }

    /**
     * Gets the value of the keepDistributionHistory property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isKeepDistributionHistory() {
        return keepDistributionHistory;
    }

    /**
     * Sets the value of the keepDistributionHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setKeepDistributionHistory(Boolean value) {
        this.keepDistributionHistory = value;
    }

}
