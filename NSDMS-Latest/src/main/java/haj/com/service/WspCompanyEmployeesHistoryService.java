package haj.com.service;

import java.util.ArrayList;
import java.util.List;

import haj.com.dao.WspCompanyEmployeesHistoryDAO;
import haj.com.entity.Wsp;
import haj.com.entity.WspCompanyAddressHistory;
import haj.com.entity.WspCompanyEmployeesHistory;
import haj.com.entity.WspCompanyHistory;
import haj.com.entity.WspCompanyMainHistory;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class WspCompanyEmployeesHistoryService extends AbstractService {
	
	/** The dao. */
	private WspCompanyEmployeesHistoryDAO dao = new WspCompanyEmployeesHistoryDAO();
	
	private static WspCompanyEmployeesHistoryService wspCompanyEmployeesHistoryService;
	public static synchronized WspCompanyEmployeesHistoryService instance() {
		if (wspCompanyEmployeesHistoryService == null) {
			wspCompanyEmployeesHistoryService = new WspCompanyEmployeesHistoryService();
		}
		return wspCompanyEmployeesHistoryService;
	}

	/**
	 * Get all WspCompanyEmployeesHistory
 	 * @author TechFinium 
 	 * @see   WspCompanyEmployeesHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyEmployeesHistory}
	 * @throws Exception the exception
 	 */
	public List<WspCompanyEmployeesHistory> allWspCompanyEmployeesHistory() throws Exception {
	  	return dao.allWspCompanyEmployeesHistory();
	}


	/**
	 * Create or update WspCompanyEmployeesHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WspCompanyEmployeesHistory
	 */
	public void create(WspCompanyEmployeesHistory entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WspCompanyEmployeesHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspCompanyEmployeesHistory
	 */
	public void update(WspCompanyEmployeesHistory entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WspCompanyEmployeesHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspCompanyEmployeesHistory
	 */
	public void delete(WspCompanyEmployeesHistory entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspCompanyEmployeesHistory}
	 * @throws Exception the exception
	 * @see    WspCompanyEmployeesHistory
	 */
	public WspCompanyEmployeesHistory findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WspCompanyEmployeesHistory by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WspCompanyEmployeesHistory}
	 * @throws Exception the exception
	 * @see    WspCompanyEmployeesHistory
	 */
	public List<WspCompanyEmployeesHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WspCompanyEmployeesHistory
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspCompanyEmployeesHistory}
	 * @throws Exception the exception
	 */
	public List<WspCompanyEmployeesHistory> allWspCompanyEmployeesHistory(int first, int pageSize) throws Exception {
		return dao.allWspCompanyEmployeesHistory(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WspCompanyEmployeesHistory for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WspCompanyEmployeesHistory
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WspCompanyEmployeesHistory.class);
	}
	
    /**
     * Lazy load WspCompanyEmployeesHistory with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WspCompanyEmployeesHistory class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WspCompanyEmployeesHistory} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WspCompanyEmployeesHistory> allWspCompanyEmployeesHistory(Class<WspCompanyEmployeesHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WspCompanyEmployeesHistory>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WspCompanyEmployeesHistory for lazy load with filters
     * @author TechFinium 
     * @param entity WspCompanyEmployeesHistory class
     * @param filters the filters
     * @return Number of rows in the WspCompanyEmployeesHistory entity
     * @throws Exception the exception     
     */	
	public int count(Class<WspCompanyEmployeesHistory> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyEmployeesHistory> allWspCompanyEmployeesHistoryByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass) throws Exception {
		String hql = "select o from WspCompanyEmployeesHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		return (List<WspCompanyEmployeesHistory>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllWspCompanyEmployeesHistoryByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspCompanyEmployeesHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
	
	public void deleteEntriesByWsp(Wsp wsp) throws Exception {
		List<IDataEntity> deleteList = new ArrayList<IDataEntity>();
		deleteList.addAll(findByWspId(wsp.getId()));
		if (deleteList.size() != 0) {
			dao.deleteBatch(deleteList);
		}
	}
	
	public void deleteEntriesByTargetKeyAndClass(String targetClass, Long targetKey) throws Exception{
		List<IDataEntity> deleteList = new ArrayList<IDataEntity>();
		deleteList.addAll(findByTargetClassAndKey(targetClass, targetKey));
		if (deleteList.size() != 0) {
			dao.deleteBatch(deleteList);
		}
	}
	
	public List<WspCompanyEmployeesHistory> findByWspId(Long wspId) throws Exception {
		return dao.findByWspId(wspId);
	}
	
	public List<WspCompanyEmployeesHistory> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		return dao.findByTargetClassAndKey(targetClass, targetKey);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyEmployeesHistory> allWspCompanyEmployeesHistoryByTargetClassTargetKeyandMainId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass, WspCompanyMainHistory wspCompanyMainHistory) throws Exception {
		String hql = "select o from WspCompanyEmployeesHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.wspCompanyMainHistory.id = :wspCompanyMainHistoryId ";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		filters.put("wspCompanyMainHistoryId", wspCompanyMainHistory.getId());
		return (List<WspCompanyEmployeesHistory>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllWspCompanyEmployeesHistoryByTargetClassTargetKeyandMainId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspCompanyEmployeesHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.wspCompanyMainHistory.id = :wspCompanyMainHistoryId";
		return dao.countWhere(filters, hql);
	}
}
