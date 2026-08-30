package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.CompanyCommunicationDAO;
import haj.com.entity.CompanyCommunication;
import haj.com.entity.Doc;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;

public class CompanyCommunicationService extends AbstractService {
	/** The dao. */
	private CompanyCommunicationDAO dao = new CompanyCommunicationDAO();

	/**
	 * Get all CompanyCommunication
 	 * @author TechFinium 
 	 * @see   CompanyCommunication
 	 * @return a list of {@link haj.com.entity.CompanyCommunication}
	 * @throws Exception the exception
 	 */
	public List<CompanyCommunication> allCompanyCommunication() throws Exception {
	  	return dao.allCompanyCommunication();
	}


	/**
	 * Create or update CompanyCommunication.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     CompanyCommunication
	 */
	public void create(CompanyCommunication entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  CompanyCommunication.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompanyCommunication
	 */
	public void update(CompanyCommunication entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  CompanyCommunication.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    CompanyCommunication
	 */
	public void delete(CompanyCommunication entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CompanyCommunication}
	 * @throws Exception the exception
	 * @see    CompanyCommunication
	 */
	public CompanyCommunication findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find CompanyCommunication by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.CompanyCommunication}
	 * @throws Exception the exception
	 * @see    CompanyCommunication
	 */
	public List<CompanyCommunication> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load CompanyCommunication
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyCommunication}
	 * @throws Exception the exception
	 */
	public List<CompanyCommunication> allCompanyCommunication(int first, int pageSize) throws Exception {
		return dao.allCompanyCommunication(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of CompanyCommunication for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the CompanyCommunication
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(CompanyCommunication.class);
	}
	
    /**
     * Lazy load CompanyCommunication with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 CompanyCommunication class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.CompanyCommunication} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<CompanyCommunication> allCompanyCommunication(Class<CompanyCommunication> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CompanyCommunication>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of CompanyCommunication for lazy load with filters
     * @author TechFinium 
     * @param entity CompanyCommunication class
     * @param filters the filters
     * @return Number of rows in the CompanyCommunication entity
     * @throws Exception the exception     
     */	
	public int count(Class<CompanyCommunication> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyCommunication> allCompanyCommunicationByCompanyId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from CompanyCommunication o where o.company.id = :companyId";
		filters.put("companyId", companyId);
		return populateAdditionalInformationList((List<CompanyCommunication>) dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters,hql));
	}
	
	public int countAllCompanyCommunicationByCompanyId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyCommunication o where o.company.id = :companyId";
		return  dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyCommunication> allCompanyCommunicationByCompanyIdAndVisible(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId, Boolean visible) throws Exception {
		String hql = "select o from CompanyCommunication o where o.company.id = :companyId and o.visible = :visible";
		filters.put("companyId", companyId);
		filters.put("visible", visible);
		return populateAdditionalInformationList((List<CompanyCommunication>) dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters,hql));
	}
	
	public int countAllCompanyCommunicationByCompanyIdAndVisible(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyCommunication o where o.company.id = :companyId and o.visible = :visible";
		return  dao.countWhere(filters, hql);
	}
	
	public void createUpdateCompanyCommunication(CompanyCommunication companyCommunication, Users user) throws Exception {
		if (companyCommunication.getId() != null) {
			companyCommunication.setLastUpdateUser(user);
			companyCommunication.setLastUpdateDate(getSynchronizedDate());
			create(companyCommunication);
		} else {
			companyCommunication.setCreateUsers(user);
			create(companyCommunication);
			if (companyCommunication.getDocs() != null && !companyCommunication.getDocs().isEmpty()) {
				for (Doc doc : companyCommunication.getDocs()) {
					if (doc.getId() != null) {
						doc.setTargetClass(companyCommunication.getClass().getName());
						doc.setTargetKey(companyCommunication.getId());
						DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
					}
				}
			}
		}
	}
	
	public List<CompanyCommunication> populateAdditionalInformationList(List<CompanyCommunication> entityList) throws Exception{
		for (CompanyCommunication entity : entityList) {
			populateAdditionalInformation(entity);
		}
		return entityList;
	}
	
	public CompanyCommunication populateAdditionalInformation(CompanyCommunication entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClassNoConfigDoc(entity.getClass().getName(), entity.getId()));
		} else {
			entity.setDocs(new ArrayList<>());
		}
		return entity;
	}
}
