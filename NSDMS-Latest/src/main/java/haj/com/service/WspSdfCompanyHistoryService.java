package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WspSdfCompanyHistoryDAO;
import haj.com.entity.Wsp;
import haj.com.entity.WspCompanyMainHistory;
import haj.com.entity.WspCompanyTrainingComitteeHistory;
import haj.com.entity.WspSdfCompanyHistory;
import haj.com.entity.lookup.SDFType;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.EquityService;
import haj.com.service.lookup.OrganisedLabourUnionService;
import haj.com.service.lookup.SDFTypeService;
import haj.com.service.lookup.TitleService;

public class WspSdfCompanyHistoryService extends AbstractService {
	
	/** The dao. */
	private WspSdfCompanyHistoryDAO dao = new WspSdfCompanyHistoryDAO();
	
	/** The Service Levels */
	private SDFTypeService sdfTypeService;
	
	private static WspSdfCompanyHistoryService wspSdfCompanyHistoryService;
	public static synchronized WspSdfCompanyHistoryService instance() {
		if (wspSdfCompanyHistoryService == null) {
			wspSdfCompanyHistoryService = new WspSdfCompanyHistoryService();
		}
		return wspSdfCompanyHistoryService;
	}

	/**
	 * Get all WspSdfCompanyHistory
 	 * @author TechFinium 
 	 * @see   WspSdfCompanyHistory
 	 * @return a list of {@link haj.com.entity.WspSdfCompanyHistory}
	 * @throws Exception the exception
 	 */
	public List<WspSdfCompanyHistory> allWspSdfCompanyHistory() throws Exception {
	  	return dao.allWspSdfCompanyHistory();
	}


	/**
	 * Create or update WspSdfCompanyHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WspSdfCompanyHistory
	 */
	public void create(WspSdfCompanyHistory entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WspSdfCompanyHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspSdfCompanyHistory
	 */
	public void update(WspSdfCompanyHistory entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WspSdfCompanyHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspSdfCompanyHistory
	 */
	public void delete(WspSdfCompanyHistory entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspSdfCompanyHistory}
	 * @throws Exception the exception
	 * @see    WspSdfCompanyHistory
	 */
	public WspSdfCompanyHistory findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WspSdfCompanyHistory by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WspSdfCompanyHistory}
	 * @throws Exception the exception
	 * @see    WspSdfCompanyHistory
	 */
	public List<WspSdfCompanyHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WspSdfCompanyHistory
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspSdfCompanyHistory}
	 * @throws Exception the exception
	 */
	public List<WspSdfCompanyHistory> allWspSdfCompanyHistory(int first, int pageSize) throws Exception {
		return dao.allWspSdfCompanyHistory(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WspSdfCompanyHistory for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WspSdfCompanyHistory
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WspSdfCompanyHistory.class);
	}
	
    /**
     * Lazy load WspSdfCompanyHistory with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WspSdfCompanyHistory class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WspSdfCompanyHistory} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WspSdfCompanyHistory> allWspSdfCompanyHistory(Class<WspSdfCompanyHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WspSdfCompanyHistory>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WspSdfCompanyHistory for lazy load with filters
     * @author TechFinium 
     * @param entity WspSdfCompanyHistory class
     * @param filters the filters
     * @return Number of rows in the WspSdfCompanyHistory entity
     * @throws Exception the exception     
     */	
	public int count(Class<WspSdfCompanyHistory> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspSdfCompanyHistory> allWspSdfCompanyHistoryByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass) throws Exception {
		String hql = "select o from WspSdfCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		return (List<WspSdfCompanyHistory>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllWspSdfCompanyHistoryByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspSdfCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey";
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
	
	public List<WspSdfCompanyHistory> findByWspId(Long wspId) throws Exception {
		return dao.findByWspId(wspId);
	}
	
	public List<WspSdfCompanyHistory> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		return dao.findByTargetClassAndKey(targetClass, targetKey);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspSdfCompanyHistory> allWspSdfCompanyHistoryHistoryByTargetClassTargetKeyandMainId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass, WspCompanyMainHistory wspCompanyMainHistory) throws Exception {
		String hql = "select o from WspSdfCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.wspCompanyMainHistory.id = :wspCompanyMainHistoryId ";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		filters.put("wspCompanyMainHistoryId", wspCompanyMainHistory.getId());
		return populateAdditionalInformationList((List<WspSdfCompanyHistory>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllWspSdfCompanyHistoryHistoryHistoryByTargetClassTargetKeyandMainId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspSdfCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.wspCompanyMainHistory.id = :wspCompanyMainHistoryId";
		return dao.countWhere(filters, hql);
	}
	
	public List<WspSdfCompanyHistory> populateAdditionalInformationList(List<WspSdfCompanyHistory> wspSdfCompanyHistoryList) throws Exception{
		for (WspSdfCompanyHistory wspSdfCompanyHistory : wspSdfCompanyHistoryList) {
			populateAdditionalInformation(wspSdfCompanyHistory);
		}
		return wspSdfCompanyHistoryList;
	}

	public WspSdfCompanyHistory populateAdditionalInformation(WspSdfCompanyHistory wspSdfCompanyHistory) throws Exception {
		// SDF type
		if (wspSdfCompanyHistory.getSdfType() != null && wspSdfCompanyHistory.getSdfType().getId() != null) {
			if (sdfTypeService == null) {
				sdfTypeService = new SDFTypeService();
			}
			wspSdfCompanyHistory.setSdfType(sdfTypeService.findByKey(wspSdfCompanyHistory.getSdfType().getId()));
		}
		return wspSdfCompanyHistory;
	}
}
