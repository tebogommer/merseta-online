
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfProjectTimesheetDistribution complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfProjectTimesheetDistribution"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectTimesheetDistribution" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectTimesheetDistribution" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProjectTimesheetDistribution", propOrder = {
    "projectTimesheetDistribution"
})
public class ArrayOfProjectTimesheetDistribution {

    @XmlElement(name = "ProjectTimesheetDistribution", nillable = true)
    protected List<ProjectTimesheetDistribution> projectTimesheetDistribution;

    /**
     * Gets the value of the projectTimesheetDistribution property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the projectTimesheetDistribution property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProjectTimesheetDistribution().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProjectTimesheetDistribution }
     * 
     * 
     */
    public List<ProjectTimesheetDistribution> getProjectTimesheetDistribution() {
        if (projectTimesheetDistribution == null) {
            projectTimesheetDistribution = new ArrayList<ProjectTimesheetDistribution>();
        }
        return this.projectTimesheetDistribution;
    }

}
