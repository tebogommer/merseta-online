package haj.com.gptransations;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRecentPayments complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRecentPayments"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="RecentPayments" type="{http://Finance.merseta.org.za/}RecentPayments" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRecentPayments", propOrder = {
    "recentPayments"
})
public class ArrayOfRecentPayments {

    @XmlElement(name = "RecentPayments", nillable = true)
    protected List<RecentPayments> recentPayments;

    /**
     * Gets the value of the recentPayments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recentPayments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecentPayments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RecentPayments }
     * 
     * 
     */
    public List<RecentPayments> getRecentPayments() {
        if (recentPayments == null) {
            recentPayments = new ArrayList<RecentPayments>();
        }
        return this.recentPayments;
    }

}
