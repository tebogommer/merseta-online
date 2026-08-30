package haj.com.service;

import java.util.List;

import haj.com.dao.SarsLevyDetailCalculationDAO;
import haj.com.entity.SarsLevyDetailCalculation;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class SarsLevyDetailCalculationService extends AbstractService {
	/** The dao. */
	private SarsLevyDetailCalculationDAO dao = new SarsLevyDetailCalculationDAO();

	/**
	 * Get all SarsLevyDetailCalculation
 	 * @author TechFinium 
 	 * @see   SarsLevyDetailCalculation
 	 * @return a list of {@link haj.com.entity.SarsLevyDetailCalculation}
	 * @throws Exception the exception
 	 */
	public List<SarsLevyDetailCalculation> allSarsLevyDetailCalculation() throws Exception {
	  	return dao.allSarsLevyDetailCalculation();
	}
	
	public Integer countByYear(Integer year) throws Exception {
		return dao.countByYear(year);
	}
	
	public Integer countByYearExcludingId(Integer year, Long id) throws Exception {
		return dao.countByYearExcludingId(year, id);
	}

	public void validiateInformation(SarsLevyDetailCalculation sarsLevyDetailCalculation) throws Exception {
		
		// check if all information provided
		if (sarsLevyDetailCalculation.getForSchemeYear() == null) {
			throw new Exception("Provide Year Before Proceeding");
		}
		if (sarsLevyDetailCalculation.getAdminPercentage() == null) {
			throw new Exception("Provide Admin Percentage Before Proceeding");
		}
		if (sarsLevyDetailCalculation.getDiscretionaryPercentage() == null) {
			throw new Exception("Provide Discretionary Percentage Before Proceeding");
		}
		if (sarsLevyDetailCalculation.getMandatoryPercentage() == null) {
			throw new Exception("Provide Mandatory Percentage Before Proceeding");
		}
		if (sarsLevyDetailCalculation.getQctoPercentage() == null) {
			throw new Exception("Provide QCTO Percentage Before Proceeding");
		}
		
		// check if year already assigned
		if (sarsLevyDetailCalculation.getId() != null) {
			if (countByYearExcludingId(sarsLevyDetailCalculation.getForSchemeYear(), sarsLevyDetailCalculation.getId()) > 0) {
				throw new Exception("Year already assigned, please provide a different year");
			}
		} else {
			if (countByYear(sarsLevyDetailCalculation.getForSchemeYear()) > 0) {
				throw new Exception("Year already assigned, please provide a different year");
			}
		}

		// check if all information equal 100
		sarsLevyDetailCalculation.setTotalPercentage(0.0);
		sarsLevyDetailCalculation.setTotalPercentage(sarsLevyDetailCalculation.getTotalPercentage() + sarsLevyDetailCalculation.getAdminPercentage());
		sarsLevyDetailCalculation.setTotalPercentage(sarsLevyDetailCalculation.getTotalPercentage() + sarsLevyDetailCalculation.getDiscretionaryPercentage());
		sarsLevyDetailCalculation.setTotalPercentage(sarsLevyDetailCalculation.getTotalPercentage() + sarsLevyDetailCalculation.getMandatoryPercentage());
		sarsLevyDetailCalculation.setTotalPercentage(sarsLevyDetailCalculation.getTotalPercentage() + sarsLevyDetailCalculation.getQctoPercentage());
		
		Double amount = 80.00;
		if (!sarsLevyDetailCalculation.getTotalPercentage().equals(amount)) {
			throw new Exception("Total percentage does not equal 80. Current amount is: "+sarsLevyDetailCalculation.getTotalPercentage()+", please alter the percentage amounts.");
		}
		
	}

	/**
	 * Create or update SarsLevyDetailCalculation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SarsLevyDetailCalculation
	 */
	public void create(SarsLevyDetailCalculation entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SarsLevyDetailCalculation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsLevyDetailCalculation
	 */
	public void update(SarsLevyDetailCalculation entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SarsLevyDetailCalculation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsLevyDetailCalculation
	 */
	public void delete(SarsLevyDetailCalculation entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SarsLevyDetailCalculation}
	 * @throws Exception the exception
	 * @see    SarsLevyDetailCalculation
	 */
	public SarsLevyDetailCalculation findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SarsLevyDetailCalculation by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SarsLevyDetailCalculation}
	 * @throws Exception the exception
	 * @see    SarsLevyDetailCalculation
	 */
	public List<SarsLevyDetailCalculation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SarsLevyDetailCalculation
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SarsLevyDetailCalculation}
	 * @throws Exception the exception
	 */
	public List<SarsLevyDetailCalculation> allSarsLevyDetailCalculation(int first, int pageSize) throws Exception {
		return dao.allSarsLevyDetailCalculation(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SarsLevyDetailCalculation for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SarsLevyDetailCalculation
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SarsLevyDetailCalculation.class);
	}
	
    /**
     * Lazy load SarsLevyDetailCalculation with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SarsLevyDetailCalculation class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SarsLevyDetailCalculation} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SarsLevyDetailCalculation> allSarsLevyDetailCalculation(Class<SarsLevyDetailCalculation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SarsLevyDetailCalculation>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SarsLevyDetailCalculation for lazy load with filters
     * @author TechFinium 
     * @param entity SarsLevyDetailCalculation class
     * @param filters the filters
     * @return Number of rows in the SarsLevyDetailCalculation entity
     * @throws Exception the exception     
     */	
	public int count(Class<SarsLevyDetailCalculation> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
