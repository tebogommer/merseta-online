
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectBudgetKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectBudgetKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectKey" minOccurs="0"/&gt;
 *         &lt;element name="CostCategoryKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}CostCategoryKey" minOccurs="0"/&gt;
 *         &lt;element name="TransactionUsage" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectBudgetKey", propOrder = {
    "projectKey",
    "costCategoryKey",
    "transactionUsage"
})
public class ProjectBudgetKey
    extends ReferenceKey
{

    @XmlElement(name = "ProjectKey")
    protected ProjectKey projectKey;
    @XmlElement(name = "CostCategoryKey")
    protected CostCategoryKey costCategoryKey;
    @XmlElement(name = "TransactionUsage")
    protected int transactionUsage;

    /**
     * Gets the value of the projectKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectKey }
     *     
     */
    public ProjectKey getProjectKey() {
        return projectKey;
    }

    /**
     * Sets the value of the projectKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectKey }
     *     
     */
    public void setProjectKey(ProjectKey value) {
        this.projectKey = value;
    }

    /**
     * Gets the value of the costCategoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link CostCategoryKey }
     *     
     */
    public CostCategoryKey getCostCategoryKey() {
        return costCategoryKey;
    }

    /**
     * Sets the value of the costCategoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link CostCategoryKey }
     *     
     */
    public void setCostCategoryKey(CostCategoryKey value) {
        this.costCategoryKey = value;
    }

    /**
     * Gets the value of the transactionUsage property.
     * 
     */
    public int getTransactionUsage() {
        return transactionUsage;
    }

    /**
     * Sets the value of the transactionUsage property.
     * 
     */
    public void setTransactionUsage(int value) {
        this.transactionUsage = value;
    }

}
