
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectMiscellaneousLogDistribution complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectMiscellaneousLogDistribution"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectDistributionBase"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectMiscellaneousLogDistributionKey" minOccurs="0"/&gt;
 *         &lt;element name="UserKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}UserKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectMiscellaneousLogDistribution", propOrder = {
    "key",
    "userKey"
})
public class ProjectMiscellaneousLogDistribution
    extends ProjectDistributionBase
{

    @XmlElement(name = "Key")
    protected ProjectMiscellaneousLogDistributionKey key;
    @XmlElement(name = "UserKey")
    protected UserKey userKey;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectMiscellaneousLogDistributionKey }
     *     
     */
    public ProjectMiscellaneousLogDistributionKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectMiscellaneousLogDistributionKey }
     *     
     */
    public void setKey(ProjectMiscellaneousLogDistributionKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the userKey property.
     * 
     * @return
     *     possible object is
     *     {@link UserKey }
     *     
     */
    public UserKey getUserKey() {
        return userKey;
    }

    /**
     * Sets the value of the userKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserKey }
     *     
     */
    public void setUserKey(UserKey value) {
        this.userKey = value;
    }

}
