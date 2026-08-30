package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.AbstractLookup;

/**
 * TO BE DELETED
 * 
 * Excel name: DTTC.xlsx
 * Tab Name: Approval
 */
@Entity
@Table(name = "RefEntity")
public class RefEntity extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of AbetBand. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
//	@CSVAnnotation(name = "Name of row on excel", className = String.class)
//	@Column(name = "keep to standard")
//	private String keep to standard;
	
	/*
	 * Filed Sizes:
	 * Book: 				@Column(name = "dttc_id" , columnDefinition = "LONGTEXT")
	 * 500 or so characters 	@Column(name = "dttc_id" , length = 500)
	 */
	
	/*
	 * PN: 1
	 * DTTCId 
	 */
	@CSVAnnotation(name = "DTTCId", className = String.class)
	@Column(name = "dttc_id")
	private String dttcId;
	
	/* 
	 * PN: 2
	 * SDLNo
	 */
	@CSVAnnotation(name = "SDLNo", className = String.class)
	@Column(name = "sdl_no")
	private String sdlNo;
	
	
	
	

	public RefEntity() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RefEntity other = (RefEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/* Getters and setters  */

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
