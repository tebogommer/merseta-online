package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Loader;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity(name = "SoftDeleteExample")
@Table(name = "soft_delete_example")
@SQLDelete(sql =
    "UPDATE soft_delete_example " +
    "SET deleted = true " +
    "WHERE id = ?")
@Loader(namedQuery = "findSoftDeleteExampleById")
@NamedQuery(name = "findSoftDeleteExampleById", query =
    "SELECT t " +
    "FROM SoftDeleteExample t " +
    "WHERE " +
    "   t.id = ?1 AND " +
    "   t.deleted = false")
@Where(clause = "deleted = false")

public class SoftDeleteExample extends BaseEntity {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	
	@Column(name="description", length=20)
    private String description;

	@OneToMany(
		        mappedBy = "softDeleteExample",
		        cascade = CascadeType.ALL,
		        orphanRemoval = true
		    )
	private List<SoftDeleteChildExample> softDeleteChildExamples = new ArrayList<SoftDeleteChildExample>();
		 
	
	public SoftDeleteExample() {
		super();		// TODO Auto-generated constructor stub
	}

	public SoftDeleteExample(String description) {
		super();
		this.description = description;
	}


	  public void addSoftDeleteChildExample(SoftDeleteChildExample softDeleteChildExample) {
		  	softDeleteChildExamples.add(softDeleteChildExample);
	        softDeleteChildExample.setSoftDeleteExample(this);
	    }
	 
	    public void removeSoftDeleteChildExample(SoftDeleteChildExample softDeleteChildExample) {
	    	 softDeleteChildExamples.remove(softDeleteChildExample);
	     softDeleteChildExample.setSoftDeleteExample(null);
	    }
	 
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		SoftDeleteExample other = (SoftDeleteExample) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	public List<SoftDeleteChildExample> getSoftDeleteChildExamples() {
		return softDeleteChildExamples;
	}



	public void setSoftDeleteChildExamples(List<SoftDeleteChildExample> softDeleteChildExamples) {
		this.softDeleteChildExamples = softDeleteChildExamples;
	}
	
	
}
