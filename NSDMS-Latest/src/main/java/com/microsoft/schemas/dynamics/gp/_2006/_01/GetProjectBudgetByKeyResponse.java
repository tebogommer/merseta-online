
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
 *         &lt;element name="GetProjectBudgetByKeyResult" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectBudget" minOccurs="0"/&gt;
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
    "getProjectBudgetByKeyResult"
})
@XmlRootElement(name = "GetProjectBudgetByKeyResponse")
public class GetProjectBudgetByKeyResponse {

    @XmlElement(name = "GetProjectBudgetByKeyResult")
    protected ProjectBudget getProjectBudgetByKeyResult;

    /**
     * Gets the value of the getProjectBudgetByKeyResult property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectBudget }
     *     
     */
    public ProjectBudget getGetProjectBudgetByKeyResult() {
        return getProjectBudgetByKeyResult;
    }

    /**
     * Sets the value of the getProjectBudgetByKeyResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectBudget }
     *     
     */
    public void setGetProjectBudgetByKeyResult(ProjectBudget value) {
        this.getProjectBudgetByKeyResult = value;
    }

}
