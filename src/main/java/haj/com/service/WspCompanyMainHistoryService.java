package haj.com.service;

import java.util.List;

import haj.com.dao.WspCompanyMainHistoryDAO;
import haj.com.entity.WspCompanyAddressHistory;
import haj.com.entity.WspCompanyMainHistory;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class WspCompanyMainHistoryService extends AbstractService {
	
	/** The dao. */
	private WspCompanyMainHistoryDAO dao = new WspCompanyMainHistoryDAO();
	
	private static WspCompanyMainHistoryService wspCompanyMainHistoryService;
	public static synchronized WspCompanyMainHistoryService instance() {
		if (wspCompanyMainHistoryService == null) {
			wspCompanyMainHistoryService = new WspCompanyMainHistoryService();
		}
		return wspCompanyMainHistoryService;
	}

	/**
	 * Get all WspCompanyMainHistory
 	 * @author TechFinium 
 	 * @see   WspCompanyMainHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyMainHistory}
	 * @throws Exception the exception
 	 */
	public List<WspCompanyMainHistory> allWspCompanyMainHistory() throws Exception {
	  	return dao.allWspCompanyMainHistory();
	}


	/**
	 * Create or update WspCompanyMainHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WspCompanyMainHistory
	 */
	public void create(WspCompanyMainHistory entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WspCompanyMainHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspCompanyMainHistory
	 */
	public void update(WspCompanyMainHistory entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WspCompanyMainHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspCompanyMainHistory
	 */
	public void delete(WspCompanyMainHistory entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspCompanyMainHistory}
	 * @throws Exception the exception
	 * @see    WspCompanyMainHistory
	 */
	public WspCompanyMainHistory findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WspCompanyMainHistory by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WspCompanyMainHistory}
	 * @throws Exception the exception
	 * @see    WspCompanyMainHistory
	 */
	public List<WspCompanyMainHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WspCompanyMainHistory
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspCompanyMainHistory}
	 * @throws Exception the exception
	 */
	public List<WspCompanyMainHistory> allWspCompanyMainHistory(int first, int pageSize) throws Exception {
		return dao.allWspCompanyMainHistory(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WspCompanyMainHistory for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WspCompanyMainHistory
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WspCompanyMainHistory.class);
	}
	
    /**
     * Lazy load WspCompanyMainHistory with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WspCompanyMainHistory class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WspCompanyMainHistory} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WspCompanyMainHistory> allWspCompanyMainHistory(Class<WspCompanyMainHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WspCompanyMainHistory>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of WspCompanyMainHistory for lazy load with filters
     * @author TechFinium 
     * @param entity WspCompanyMainHistory class
     * @param filters the filters
     * @return Number of rows in the WspCompanyMainHistory entity
     * @throws Exception the exception     
     */	
	public int count(Class<WspCompanyMainHistory> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyMainHistory> allWspCompanyMainHistoryByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass) throws Exception {
		String hql = "select o from WspCompanyMainHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		return (List<WspCompanyMainHistory>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllWspCompanyMainHistoryByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspCompanyMainHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
	
	public List<WspCompanyMainHistory> findByTargetClassAndTargetKeyOrderByLatest(Long targetKey, String targetClass) throws Exception {
		return dao.findByTargetClassAndTargetKeyOrderByLatest(targetKey, targetClass);
	}
	
	public WspCompanyMainHistory findByTargetClassAndTargetKeyReturnLastest(Long targetKey, String targetClass) throws Exception {
		return dao.findByTargetClassAndTargetKeyReturnLastest(targetKey, targetClass);
	}
}
