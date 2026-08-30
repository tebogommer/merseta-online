
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfProjectFeeSchedule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfProjectFeeSchedule"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProjectFeeSchedule" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectFeeSchedule" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfProjectFeeSchedule", propOrder = {
    "projectFeeSchedule"
})
public class ArrayOfProjectFeeSchedule {

    @XmlElement(name = "ProjectFeeSchedule", nillable = true)
    protected List<ProjectFeeSchedule> projectFeeSchedule;

    /**
     * Gets the value of the projectFeeSchedule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the projectFeeSchedule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProjectFeeSchedule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProjectFeeSchedule }
     * 
     * 
     */
    public List<ProjectFeeSchedule> getProjectFeeSchedule() {
        if (projectFeeSchedule == null) {
            projectFeeSchedule = new ArrayList<ProjectFeeSchedule>();
        }
        return this.projectFeeSchedule;
    }

}
