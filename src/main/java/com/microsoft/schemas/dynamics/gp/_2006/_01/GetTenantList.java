
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="activeTenantsOnly" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
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
    "activeTenantsOnly"
})
@XmlRootElement(name = "GetTenantList")
public class GetTenantList {

    protected boolean activeTenantsOnly;

    /**
     * Gets the value of the activeTenantsOnly property.
     * 
     */
    public boolean isActiveTenantsOnly() {
        return activeTenantsOnly;
    }

    /**
     * Sets the value of the activeTenantsOnly property.
     * 
     */
    public void setActiveTenantsOnly(boolean value) {
        this.activeTenantsOnly = value;
    }

}
