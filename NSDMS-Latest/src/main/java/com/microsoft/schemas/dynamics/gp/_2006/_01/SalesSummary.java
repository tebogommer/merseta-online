
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SalesSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SalesSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="YearToDate" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CommissionSummary" minOccurs="0"/&gt;
 *         &lt;element name="LastYear" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CommissionSummary" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SalesSummary", propOrder = {
    "yearToDate",
    "lastYear"
})
public class SalesSummary
    extends BusinessObject
{

    @XmlElement(name = "YearToDate")
    protected CommissionSummary yearToDate;
    @XmlElement(name = "LastYear")
    protected CommissionSummary lastYear;

    /**
     * Gets the value of the yearToDate property.
     * 
     * @return
     *     possible object is
     *     {@link CommissionSummary }
     *     
     */
    public CommissionSummary getYearToDate() {
        return yearToDate;
    }

    /**
     * Sets the value of the yearToDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommissionSummary }
     *     
     */
    public void setYearToDate(CommissionSummary value) {
        this.yearToDate = value;
    }

    /**
     * Gets the value of the lastYear property.
     * 
     * @return
     *     possible object is
     *     {@link CommissionSummary }
     *     
     */
    public CommissionSummary getLastYear() {
        return lastYear;
    }

    /**
     * Sets the value of the lastYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommissionSummary }
     *     
     */
    public void setLastYear(CommissionSummary value) {
        this.lastYear = value;
    }

}
