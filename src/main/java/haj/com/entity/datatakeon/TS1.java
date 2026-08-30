package haj.com.entity.datatakeon;
// Generated Oct 16, 2017 5:23:37 PM by Hibernate Tools 4.3.1


import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.annotations.TechFiniumColumn;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * WSP approval list.
 */
@Entity
@Table(name="t_s1"
    
)
public class TS1  implements IDataEntity {


     /** The id. */
	@TechFiniumColumn(suppress=true)
     private Long id;
     
     /** The scheme year. */
	@TechFiniumColumn(suppress=true)
     private Integer schemeYear;
     
     /** The levy number. */
	@TechFiniumColumn(suppress=true)
     private String levyNumber;
     
     /** The status description. */
	@TechFiniumColumn(suppress=true)
     private String statusDescription;

    /**
     * Instantiates a new ts1.
     */
    public TS1() {
    }


   
     /**
      * Gets the id.
      *
      * @return the id
      */
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="id", unique=true, nullable=false)
    public Long getId() {
        return this.id;
    }
    
    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /**
     * Gets the scheme year.
     *
     * @return the scheme year
     */
    @Column(name="scheme_year")
    public Integer getSchemeYear() {
        return this.schemeYear;
    }
    
    /**
     * Sets the scheme year.
     *
     * @param schemeYear the new scheme year
     */
    public void setSchemeYear(Integer schemeYear) {
        this.schemeYear = schemeYear;
    }

    
    /**
     * Gets the levy number.
     *
     * @return the levy number
     */
    @Column(name="levy_number")
    public String getLevyNumber() {
        return this.levyNumber;
    }
    
    /**
     * Sets the levy number.
     *
     * @param levyNumber the new levy number
     */
    public void setLevyNumber(String levyNumber) {
        this.levyNumber = levyNumber;
    }

    
    /**
     * Gets the status description.
     *
     * @return the status description
     */
    @Column(name="status_description")
    public String getStatusDescription() {
        return this.statusDescription;
    }
    
    /**
     * Sets the status description.
     *
     * @param statusDescription the new status description
     */
    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }




}


