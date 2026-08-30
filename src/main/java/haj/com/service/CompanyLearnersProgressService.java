package haj.com.service;

import java.util.List;

import haj.com.dao.CompanyLearnersProgressDAO;
import haj.com.entity.CompanyLearnersProgress;
import haj.com.entity.CompanyLearnersTermination;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class CompanyLearnersProgressService extends AbstractService {
	/** The dao. */
	private CompanyLearnersProgressDAO dao = new CompanyLearnersProgressDAO();
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();

	/** Instance of service level */
	private static CompanyLearnersProgressService companyLearnersProgressService;
	public static synchronized CompanyLearnersProgressService instance() {
		if (companyLearnersProgressService == null) {
			companyLearnersProgressService = new CompanyLearnersProgressService();
		}
		return companyLearnersProgressService;
	}
	
	/**
	 * Populates additional information against CompanyLearnersProgress
	 * refer to method: populateAdditionalInformation
	 * @param companyLearnersProgressList
	 * @return companyLearnersProgressList
	 * @throws Exception
	 */
	public List<CompanyLearnersProgress> populateAdditionalInformationList(List<CompanyLearnersProgress> companyLearnersProgressList) throws Exception{
		for (CompanyLearnersProgress companyLearnersProgress : companyLearnersProgressList) {
			populateAdditionalInformation(companyLearnersProgress);
		}
		return companyLearnersProgressList;
	}
	
	/**
	 * Populates additional information against CompanyLearnersProgress
	 * @param companyLearnersProgress
	 * @return companyLearnersProgress
	 * @throws Exception
	 */
	private CompanyLearnersProgress populateAdditionalInformation(CompanyLearnersProgress companyLearnersProgress) throws Exception{
		if (companyLearnersProgress.getCompanyLearners() != null && companyLearnersProgress.getCompanyLearners().getId() != null) {
			companyLearnersProgress.setCompanyLearners(companyLearnersService.findByKey(companyLearnersProgress.getCompanyLearners().getId())) ;
		}
		return companyLearnersProgress;
	}

	/**
	 * Get all CompanyLearnersProgress
 	 * @author TechFinium 
 	 * @see   CompanyLearnersProgress
 	 * @return a list of {@link haj.com.entity.CompanyLearnersProgress}
	 * @throws Exception the exception
 	 */
	public List<CompanyLearnersProgress> allCompanyLearnersProgress() throws Exception {
	  	return populateAdditionalInformationList(dao.allCompanyLearnersProgress());
	}


	/**
	 * Create or update CompanyLearnersProgress.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     CompanyLearnersProgress
	 */
	public void create(CompanyLearnersProgress entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  CompanyLearnersProgress.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompanyLearnersProgress
	 */
	public void update(CompanyLearnersProgress entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  CompanyLearnersProgress.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompanyLearnersProgress
	 */
	public void delete(CompanyLearnersProgress entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CompanyLearnersProgress}
	 * @throws Exception the exception
	 * @see    CompanyLearnersProgress
	 */
	public CompanyLearnersProgress findByKey(long id) throws Exception {
       return populateAdditionalInformation(dao.findByKey(id));
	}

	/**
	 * Find CompanyLearnersProgress by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.CompanyLearnersProgress}
	 * @throws Exception the exception
	 * @see    CompanyLearnersProgress
	 */
	public List<CompanyLearnersProgress> findByName(String desc) throws Exception {
		return populateAdditionalInformationList(dao.findByName(desc));
	}
	
	/**
	 * Lazy load CompanyLearnersProgress
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyLearnersProgress}
	 * @throws Exception the exception
	 */
	public List<CompanyLearnersProgress> allCompanyLearnersProgress(int first, int pageSize) throws Exception {
		return populateAdditionalInformationList(dao.allCompanyLearnersProgress(Long.valueOf(first), pageSize));
	}
		
	
	/**
	 * Get count of CompanyLearnersProgress for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the CompanyLearnersProgress
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(CompanyLearnersProgress.class);
	}
	
    /**
     * Lazy load CompanyLearnersProgress with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 CompanyLearnersProgress class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.CompanyLearnersProgress} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersProgress> allCompanyLearnersProgress(Class<CompanyLearnersProgress> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList(( List<CompanyLearnersProgress>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));
	
	}
	
    /**
     * Get count of CompanyLearnersProgress for lazy load with filters
     * @author TechFinium 
     * @param entity CompanyLearnersProgress class
     * @param filters the filters
     * @return Number of rows in the CompanyLearnersProgress entity
     * @throws Exception the exception     
     */	
	public int count(Class<CompanyLearnersProgress> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
