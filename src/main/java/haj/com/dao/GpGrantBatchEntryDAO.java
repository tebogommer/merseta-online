package haj.com.dao;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.GpGrantBatchEntry;
import haj.com.entity.enums.GpDocumentType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.utils.GenericUtility;

public class GpGrantBatchEntryDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all GpGrantBatchEntry
 	 * @author TechFinium 
 	 * @see    GpGrantBatchEntry
 	 * @return a list of {@link haj.com.entity.GpGrantBatchEntry}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchEntry> allGpGrantBatchEntry() throws Exception {
		return (List<GpGrantBatchEntry>)super.getList("select o from GpGrantBatchEntry o");
	}

	/**
	 * Get all GpGrantBatchEntry between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    GpGrantBatchEntry
 	 * @return a list of {@link haj.com.entity.GpGrantBatchEntry}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchEntry> allGpGrantBatchEntry(Long from, int noRows) throws Exception {
	 	String hql = "select o from GpGrantBatchEntry o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<GpGrantBatchEntry>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    GpGrantBatchEntry
 	 * @return a {@link haj.com.entity.GpGrantBatchEntry}
 	 * @throws Exception global exception
 	 */
	public GpGrantBatchEntry findByKey(Long id) throws Exception {
	 	String hql = "select o from GpGrantBatchEntry o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (GpGrantBatchEntry)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find GpGrantBatchEntry by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    GpGrantBatchEntry
  	 * @return a list of {@link haj.com.entity.GpGrantBatchEntry}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchEntry> findByName(String description) throws Exception {
	 	String hql = "select o from GpGrantBatchEntry o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<GpGrantBatchEntry>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchEntry> allEntriesByBatchListToBeRemoved(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
	 	String hql = "select o from GpGrantBatchEntry o where o.gpGrantBatchList.id = :gpGrantBatchListId and o.toBeRemoved = :toBeRemoved " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("gpGrantBatchListId", gpGrantBatchListId);
	    parameters.put("toBeRemoved", toBeRemoved);
	    return (List<GpGrantBatchEntry>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchEntry> allEntriesToBeProcessesed(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
	 	String hql = "select o from GpGrantBatchEntry o "
	 			+ "where o.gpGrantBatchList.id = :gpGrantBatchListId and o.toBeRemoved = :toBeRemoved" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("gpGrantBatchListId", gpGrantBatchListId);
	    parameters.put("toBeRemoved", toBeRemoved);
	    return (List<GpGrantBatchEntry>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchEntry> allEntriesToBeProcessesedGrouping(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
	 	String hql = "select new  haj.com.entity.GpGrantBatchEntry(o.levyNumber, o.schemeYear,  sum(o.mandatoryLevy)) from GpGrantBatchEntry o "
	 			+ "where o.gpGrantBatchList.id = :gpGrantBatchListId and o.toBeRemoved = :toBeRemoved group by o.levyNumber, o.schemeYear " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("gpGrantBatchListId", gpGrantBatchListId);
	    parameters.put("toBeRemoved", toBeRemoved);
	    return (List<GpGrantBatchEntry>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchEntry> allEntriesByBatchCompoundKey(String levyNumber, Integer schemeYear, Long gpGrantBatchListId) throws Exception {
		String hql = "select o from GpGrantBatchEntry o where o.gpGrantBatchList.id = :gpGrantBatchListId and o.schemeYear = :schemeYear and o.levyNumber = :levyNumber" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("gpGrantBatchListId", gpGrantBatchListId);
	    parameters.put("schemeYear", schemeYear);
	    parameters.put("levyNumber", levyNumber);
	    return (List<GpGrantBatchEntry>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> allDistinctLevyNumbersByBatchIdAndToBeRemoved(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
		String hql = "select distinct(o.levyNumber) from GpGrantBatchEntry o where o.gpGrantBatchList.id = :gpGrantBatchListId and o.toBeRemoved = :toBeRemoved " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("gpGrantBatchListId", gpGrantBatchListId);
	    parameters.put("toBeRemoved", toBeRemoved);
	    return (List<String>)super.getList(hql, parameters);
	}
	
//	
	
	public Integer countAllEntriesByBatchList(Long gpGrantBatchListId) throws Exception {
	 	String hql = "select count(o) from GpGrantBatchEntry o where o.gpGrantBatchList.id = :gpGrantBatchListId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("gpGrantBatchListId", gpGrantBatchListId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countAllEntriesByBatchListToBeRemoved(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
	 	String hql = "select count(o) from GpGrantBatchEntry o where o.gpGrantBatchList.id = :gpGrantBatchListId and o.toBeRemoved = :toBeRemoved " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("gpGrantBatchListId", gpGrantBatchListId);
	    parameters.put("toBeRemoved", toBeRemoved);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Double sumMandatoryLevyAllEntriesByBatchList(Long gpGrantBatchListId) throws Exception {
	 	String hql = "select sum(o.mandatoryLevy) from GpGrantBatchEntry o where o.gpGrantBatchList.id = :gpGrantBatchListId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("gpGrantBatchListId", gpGrantBatchListId);
		return (Double) super.getUniqueResult(hql, parameters);
	}
	
	public Double sumDiscretionaryLevyAllEntriesByBatchList(Long gpGrantBatchListId) throws Exception {
	 	String hql = "select sum(o.discretionaryLevy) from GpGrantBatchEntry o where o.gpGrantBatchList.id = :gpGrantBatchListId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("gpGrantBatchListId", gpGrantBatchListId);
		return (Double) super.getUniqueResult(hql, parameters);
	}
	
	public Double sumMandatoryLevyAllEntriesByBatchListToBeRemoved(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
	 	String hql = "select sum(o.mandatoryLevy) from GpGrantBatchEntry o where o.gpGrantBatchList.id = :gpGrantBatchListId and o.toBeRemoved = :toBeRemoved " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("gpGrantBatchListId", gpGrantBatchListId);
	    parameters.put("toBeRemoved", toBeRemoved);
		return GenericUtility.roundToPrecision((Double) super.getUniqueResult(hql, parameters), 2);
	}
	
	public Double sumMandatoryLevyAllEntriesByBatchListToBeRemovedAndType(Long gpGrantBatchListId, Boolean toBeRemoved, GpDocumentType gpDocumentType) throws Exception {
	 	String hql = "select sum(o.mandatoryLevy) from GpGrantBatchEntry o where o.gpGrantBatchList.id = :gpGrantBatchListId and o.toBeRemoved = :toBeRemoved and o.documentTypeMandatory = :gpDocumentType" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("gpGrantBatchListId", gpGrantBatchListId);
	    parameters.put("gpDocumentType", gpDocumentType);
	    parameters.put("toBeRemoved", toBeRemoved);
		return (Double) super.getUniqueResult(hql, parameters);
	}
	
	public Double runningTotalForPaymentsDG(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
	 	String hql = "select sum(o.discretionaryLevy) from GpGrantBatchEntry o where o.gpGrantBatchList.id = :gpGrantBatchListId and o.toBeRemoved = :toBeRemoved " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("gpGrantBatchListId", gpGrantBatchListId);
	    parameters.put("toBeRemoved", toBeRemoved);
		return (Double) super.getUniqueResult(hql, parameters);
	}
	
}