
package com.microsoft.schemas.dynamics.gp._2006._01;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfReturnMaterialAuthorizationLine complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfReturnMaterialAuthorizationLine"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ReturnMaterialAuthorizationLine" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ReturnMaterialAuthorizationLine" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfReturnMaterialAuthorizationLine", propOrder = {
    "returnMaterialAuthorizationLine"
})
public class ArrayOfReturnMaterialAuthorizationLine {

    @XmlElement(name = "ReturnMaterialAuthorizationLine", nillable = true)
    protected List<ReturnMaterialAuthorizationLine> returnMaterialAuthorizationLine;

    /**
     * Gets the value of the returnMaterialAuthorizationLine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the returnMaterialAuthorizationLine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReturnMaterialAuthorizationLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReturnMaterialAuthorizationLine }
     * 
     * 
     */
    public List<ReturnMaterialAuthorizationLine> getReturnMaterialAuthorizationLine() {
        if (returnMaterialAuthorizationLine == null) {
            returnMaterialAuthorizationLine = new ArrayList<ReturnMaterialAuthorizationLine>();
        }
        return this.returnMaterialAuthorizationLine;
    }

}
