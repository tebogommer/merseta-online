
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.microsoft.schemas.dynamics.security._2006._01.RoleKey;


/**
 * <p>Java class for ArrayOfRoleKey complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRoleKey"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RoleKey" type="{http://schemas.microsoft.com/dynamics/security/2006/01}RoleKey" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRoleKey", propOrder = {
    "roleKey"
})
public class ArrayOfRoleKey {

    @XmlElement(name = "RoleKey", nillable = true)
    protected List<RoleKey> roleKey;

    /**
     * Gets the value of the roleKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the roleKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RoleKey }
     * 
     * 
     */
    public List<RoleKey> getRoleKey() {
        if (roleKey == null) {
            roleKey = new ArrayList<RoleKey>();
        }
        return this.roleKey;
    }

}
