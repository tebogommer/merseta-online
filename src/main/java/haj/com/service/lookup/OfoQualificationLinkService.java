package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.OfoQualificationLinkDAO;
import haj.com.entity.OfoCodes;
import haj.com.entity.lookup.OfoQualificationLink;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.service.OfoCodesService;

public class OfoQualificationLinkService extends AbstractService {
	/** The dao. */
	private OfoQualificationLinkDAO dao = new OfoQualificationLinkDAO();
	
	/** The Service Level */
	private OfoCodesService ofoCodesService;
	private QualificationService qualificationService;

	/**
	 * Get all OfoQualificationLink
 	 * @author TechFinium 
 	 * @see   OfoQualificationLink
 	 * @return a list of {@link haj.com.entity.OfoQualificationLink}
	 * @throws Exception the exception
 	 */
	public List<OfoQualificationLink> allOfoQualificationLink() throws Exception {
	  	return dao.allOfoQualificationLink();
	}


	/**
	 * Create or update OfoQualificationLink.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     OfoQualificationLink
	 */
	public void create(OfoQualificationLink entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  OfoQualificationLink.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    OfoQualificationLink
	 */
	public void update(OfoQualificationLink entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  OfoQualificationLink.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    OfoQualificationLink
	 */
	public void delete(OfoQualificationLink entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.OfoQualificationLink}
	 * @throws Exception the exception
	 * @see    OfoQualificationLink
	 */
	public OfoQualificationLink findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find OfoQualificationLink by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.OfoQualificationLink}
	 * @throws Exception the exception
	 * @see    OfoQualificationLink
	 */
	public List<OfoQualificationLink> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load OfoQualificationLink
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.OfoQualificationLink}
	 * @throws Exception the exception
	 */
	public List<OfoQualificationLink> allOfoQualificationLink(int first, int pageSize) throws Exception {
		return dao.allOfoQualificationLink(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of OfoQualificationLink for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the OfoQualificationLink
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(OfoQualificationLink.class);
	}
	
    /**
     * Lazy load OfoQualificationLink with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 OfoQualificationLink class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.OfoQualificationLink} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<OfoQualificationLink> allOfoQualificationLink(Class<OfoQualificationLink> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList((List<OfoQualificationLink>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));
	}
	
    /**
     * Get count of OfoQualificationLink for lazy load with filters
     * @author TechFinium 
     * @param entity OfoQualificationLink class
     * @param filters the filters
     * @return Number of rows in the OfoQualificationLink entity
     * @throws Exception the exception     
     */	
	public int count(Class<OfoQualificationLink> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * 
	 * @param ofoQualificationLink
	 * @return Integer the count of entries found
	 * @throws Exception
	 */
	public Integer countFindByOfoQualificationId(OfoQualificationLink ofoQualificationLink) throws Exception {
		return dao.countFindByOfoQualificationId(ofoQualificationLink.getOfoCodes().getId(), ofoQualificationLink.getQualification().getId(), ofoQualificationLink.getId());
	}
	
	/**
	 * Populates additional information and lazy fields on look up
	 * @param entityList
	 * @return
	 * @throws Exception
	 */
	public List<OfoQualificationLink> populateAdditionalInformationList(List<OfoQualificationLink> entityList) throws Exception{
		for (OfoQualificationLink entity : entityList) {
			populateAdditionalInformation(entity);
		}
		return entityList;
	}
	
	public OfoQualificationLink populateAdditionalInformation(OfoQualificationLink entity) throws Exception{
		resolveOfoCode(entity);
		resolveQualification(entity);
		return entity;
	}
	
	public List<OfoQualificationLink> resolveOfoCodeList(List<OfoQualificationLink> entityList) throws Exception{
		for (OfoQualificationLink entity : entityList) {
			resolveOfoCode(entity);
		}
		return entityList;
	}
	
	public List<OfoQualificationLink> resolveQualificationList(List<OfoQualificationLink> entityList) throws Exception{
		for (OfoQualificationLink entity : entityList) {
			resolveQualification(entity);
		}
		return entityList;
	}
	
	/**
	 * Sets the ofo code
	 * @see OfoCodes
	 * @param entity
	 * @return OfoQualificationLink
	 * @throws Exception
	 */
	public OfoQualificationLink resolveOfoCode(OfoQualificationLink entity) throws Exception{
		if (ofoCodesService == null) ofoCodesService = new OfoCodesService();
		entity.setOfoCodes(ofoCodesService.findByKey(entity.getOfoCodes().getId()));
		return entity;
	}
	
	/**
	 * Sets the qualification
	 * @see Qualification
	 * @param entity
	 * @return OfoQualificationLink
	 * @throws Exception
	 */
	public OfoQualificationLink resolveQualification(OfoQualificationLink entity) throws Exception{
		if (qualificationService == null) qualificationService = new QualificationService();
		entity.setQualification(qualificationService.findByKey(entity.getQualification().getId()));
		return entity;
	}
}
