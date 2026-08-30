
package com.microsoft.schemas.dynamics.gp._2006._01;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProjectAccountType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProjectAccountType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://schemas.microsoft.com/dynamics/gp/2006/01}BusinessObject"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Key" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}ProjectAccountTypeKey" minOccurs="0"/&gt;
 *         &lt;element name="GLAccountKey" type="{http://schemas.microsoft.com/dynamics/gp/2006/01}GLAccountNumberKey" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProjectAccountType", propOrder = {
    "key",
    "glAccountKey"
})
public class ProjectAccountType
    extends BusinessObject
{

    @XmlElement(name = "Key")
    protected ProjectAccountTypeKey key;
    @XmlElement(name = "GLAccountKey")
    protected GLAccountNumberKey glAccountKey;

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link ProjectAccountTypeKey }
     *     
     */
    public ProjectAccountTypeKey getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProjectAccountTypeKey }
     *     
     */
    public void setKey(ProjectAccountTypeKey value) {
        this.key = value;
    }

    /**
     * Gets the value of the glAccountKey property.
     * 
     * @return
     *     possible object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public GLAccountNumberKey getGLAccountKey() {
        return glAccountKey;
    }

    /**
     * Sets the value of the glAccountKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link GLAccountNumberKey }
     *     
     */
    public void setGLAccountKey(GLAccountNumberKey value) {
        this.glAccountKey = value;
    }

}
