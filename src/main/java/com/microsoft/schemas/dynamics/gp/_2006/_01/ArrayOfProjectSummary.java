
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfProjectSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfProjectSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectSummary" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectSummary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProjectSummary", propOrder = {
    "projectSummary"
})
public class ArrayOfProjectSummary {

    @XmlElement(name = "ProjectSummary", nillable = true)
    protected List<ProjectSummary> projectSummary;

    /**
     * Gets the value of the projectSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the projectSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProjectSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProjectSummary }
     * 
     * 
     */
    public List<ProjectSummary> getProjectSummary() {
        if (projectSummary == null) {
            projectSummary = new ArrayList<ProjectSummary>();
        }
        return this.projectSummary;
    }

}
