package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.NsdpReportConfigDAO;
import haj.com.entity.Users;
import haj.com.entity.lookup.NsdpReportConfig;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class NsdpReportConfigService extends AbstractService {
	
	/** The dao. */
	private NsdpReportConfigDAO dao = new NsdpReportConfigDAO();
	
	/* Service Instance */
	private static NsdpReportConfigService nsdpReportConfigService;
	public static synchronized NsdpReportConfigService instance() {
		if (nsdpReportConfigService == null) {
			nsdpReportConfigService = new NsdpReportConfigService();
		}
		return nsdpReportConfigService;
	}

	/**
	 * Get all NsdpReportConfig
 	 * @author TechFinium 
 	 * @see   NsdpReportConfig
 	 * @return a list of {@link haj.com.entity.NsdpReportConfig}
	 * @throws Exception the exception
 	 */
	public List<NsdpReportConfig> allNsdpReportConfig() throws Exception {
	  	return dao.allNsdpReportConfig();
	}


	/**
	 * Create or update NsdpReportConfig.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     NsdpReportConfig
	 */
	public void create(NsdpReportConfig entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}
	
	public void createUpdateWithUserInfo(NsdpReportConfig entity, Users sessionUser) throws Exception{
		if (entity.getManualCapture() != null && entity.getManualCapture()) {
			entity.setNsdpReportRunType(null);
		}
		if (sessionUser != null) {
			entity.setLastActionUser(sessionUser);
		}
		entity.setLastActionDate(getSynchronizedDate());
		create(entity);
	}

	/**
	 * Update  NsdpReportConfig.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NsdpReportConfig
	 */
	public void update(NsdpReportConfig entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  NsdpReportConfig.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NsdpReportConfig
	 */
	public void delete(NsdpReportConfig entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NsdpReportConfig}
	 * @throws Exception the exception
	 * @see    NsdpReportConfig
	 */
	public NsdpReportConfig findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find NsdpReportConfig by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.NsdpReportConfig}
	 * @throws Exception the exception
	 * @see    NsdpReportConfig
	 */
	public List<NsdpReportConfig> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load NsdpReportConfig
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.NsdpReportConfig}
	 * @throws Exception the exception
	 */
	public List<NsdpReportConfig> allNsdpReportConfig(int first, int pageSize) throws Exception {
		return dao.allNsdpReportConfig(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of NsdpReportConfig for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the NsdpReportConfig
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(NsdpReportConfig.class);
	}
	
    /**
     * Lazy load NsdpReportConfig with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 NsdpReportConfig class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.NsdpReportConfig} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<NsdpReportConfig> allNsdpReportConfig(Class<NsdpReportConfig> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<NsdpReportConfig>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of NsdpReportConfig for lazy load with filters
     * @author TechFinium 
     * @param entity NsdpReportConfig class
     * @param filters the filters
     * @return Number of rows in the NsdpReportConfig entity
     * @throws Exception the exception     
     */	
	public int count(Class<NsdpReportConfig> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<NsdpReportConfig> allNsdpReportConfigByFinancialYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long financialYearId) throws Exception {
		String hql = "select o from NsdpReportConfig o where o.financialYears.id = :financialYearId";
		filters.put("financialYearId", financialYearId);
		return ( List<NsdpReportConfig>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllNsdpReportConfigByFinancialYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from NsdpReportConfig o where o.financialYears.id = :financialYearId";
		return  dao.countWhere(filters, hql);
	}
	
	public List<NsdpReportConfig> findByFinancialYearsId(Long financialYearsId) throws Exception {
		return dao.findByFinancialYearsId(financialYearsId);
	}
	
	public List<NsdpReportConfig> findByFinancialYearsIdWithOrderBy(Long financialYearsId) throws Exception {
		return dao.findByFinancialYearsIdWithOrderBy(financialYearsId);
	}
	
	public void updateOrderForConfig(List<NsdpReportConfig> orderedList, Users sessionUser) throws Exception{
		List<IDataEntity> updateList = new ArrayList<>();
		for (NsdpReportConfig entity : orderedList) {
			if (entity.getManualCapture() != null && entity.getManualCapture()) {
				entity.setNsdpReportRunType(null);
			}
			if (sessionUser != null) {
				entity.setLastActionUser(sessionUser);
			}
			entity.setLastActionDate(getSynchronizedDate());
			entity.setOrderNumber(orderedList.indexOf(entity) + 1);
			updateList.add(entity);
		}
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
	}
}
