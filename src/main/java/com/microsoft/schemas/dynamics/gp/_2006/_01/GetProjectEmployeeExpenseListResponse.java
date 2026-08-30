
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="GetProjectEmployeeExpenseListResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ArrayOfProjectEmployeeExpenseSummary" minOccurs="0"/&gt;
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
    "getProjectEmployeeExpenseListResult"
})
@XmlRootElement(name = "GetProjectEmployeeExpenseListResponse")
public class GetProjectEmployeeExpenseListResponse {

    @XmlElement(name = "GetProjectEmployeeExpenseListResult")
    protected ArrayOfProjectEmployeeExpenseSummary getProjectEmployeeExpenseListResult;

    /**
     * Gets the value of the getProjectEmployeeExpenseListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProjectEmployeeExpenseSummary }
     *     
     */
    public ArrayOfProjectEmployeeExpenseSummary getGetProjectEmployeeExpenseListResult() {
        return getProjectEmployeeExpenseListResult;
    }

    /**
     * Sets the value of the getProjectEmployeeExpenseListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProjectEmployeeExpenseSummary }
     *     
     */
    public void setGetProjectEmployeeExpenseListResult(ArrayOfProjectEmployeeExpenseSummary value) {
        this.getProjectEmployeeExpenseListResult = value;
    }

}
