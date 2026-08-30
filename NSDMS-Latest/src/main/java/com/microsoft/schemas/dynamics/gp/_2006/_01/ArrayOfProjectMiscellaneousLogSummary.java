
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfProjectMiscellaneousLogSummary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfProjectMiscellaneousLogSummary"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectMiscellaneousLogSummary" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectMiscellaneousLogSummary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProjectMiscellaneousLogSummary", propOrder = {
    "projectMiscellaneousLogSummary"
})
public class ArrayOfProjectMiscellaneousLogSummary {

    @XmlElement(name = "ProjectMiscellaneousLogSummary", nillable = true)
    protected List<ProjectMiscellaneousLogSummary> projectMiscellaneousLogSummary;

    /**
     * Gets the value of the projectMiscellaneousLogSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the projectMiscellaneousLogSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProjectMiscellaneousLogSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProjectMiscellaneousLogSummary }
     * 
     * 
     */
    public List<ProjectMiscellaneousLogSummary> getProjectMiscellaneousLogSummary() {
        if (projectMiscellaneousLogSummary == null) {
            projectMiscellaneousLogSummary = new ArrayList<ProjectMiscellaneousLogSummary>();
        }
        return this.projectMiscellaneousLogSummary;
    }

}
