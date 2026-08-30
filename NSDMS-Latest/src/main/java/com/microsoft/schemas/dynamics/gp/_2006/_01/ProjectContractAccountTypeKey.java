
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectContractAccountTypeKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectContractAccountTypeKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReferenceKey"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SourceFile" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectSourceFile"/&gt;
 *         &lt;element name="ProjectContractKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectContractKey" minOccurs="0"/&gt;
 *         &lt;element name="CostTransaction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="DistributionTypeKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}DistributionTypeKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectContractAccountTypeKey", propOrder = {
    "sourceFile",
    "projectContractKey",
    "costTransaction",
    "distributionTypeKey"
})
public class ProjectContractAccountTypeKey
    extends ReferenceKey
{

    @XmlElement(name = "SourceFile", required = true)
    @XmlSchemaType(name = "string")
    protected ProjectSourceFile sourceFile;
    @XmlElement(name = "ProjectContractKey")
    protected ProjectContractKey projectContractKey;
    @XmlElement(name = "CostTransaction")
    protected String costTransaction;
    @XmlElement(name = "DistributionTypeKey")
    protected DistributionTypeKey distributionTypeKey;

    /**
     * Gets the value of the sourceFile property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectSourceFile }
     *     
     */
    public ProjectSourceFile getSourceFile() {
        return sourceFile;
    }

    /**
     * Sets the value of the sourceFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectSourceFile }
     *     
     */
    public void setSourceFile(ProjectSourceFile value) {
        this.sourceFile = value;
    }

    /**
     * Gets the value of the projectContractKey property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectContractKey }
     *     
     */
    public ProjectContractKey getProjectContractKey() {
        return projectContractKey;
    }

    /**
     * Sets the value of the projectContractKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectContractKey }
     *     
     */
    public void setProjectContractKey(ProjectContractKey value) {
        this.projectContractKey = value;
    }

    /**
     * Gets the value of the costTransaction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCostTransaction() {
        return costTransaction;
    }

    /**
     * Sets the value of the costTransaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCostTransaction(String value) {
        this.costTransaction = value;
    }

    /**
     * Gets the value of the distributionTypeKey property.
     * 
     * @return
     *     possible object is
     *     {@link DistributionTypeKey }
     *     
     */
    public DistributionTypeKey getDistributionTypeKey() {
        return distributionTypeKey;
    }

    /**
     * Sets the value of the distributionTypeKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link DistributionTypeKey }
     *     
     */
    public void setDistributionTypeKey(DistributionTypeKey value) {
        this.distributionTypeKey = value;
    }

}
