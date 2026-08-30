package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.DgContractingBulkEntry;

public class DgContractingBulkEntryDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DgContractingBulkEntry
 	 * @author TechFinium 
 	 * @see    DgContractingBulkEntry
 	 * @return a list of {@link haj.com.entity.DgContractingBulkEntry}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkEntry> allDgContractingBulkEntry() throws Exception {
		return (List<DgContractingBulkEntry>)super.getList("select o from DgContractingBulkEntry o");
	}

	/**
	 * Get all DgContractingBulkEntry between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DgContractingBulkEntry
 	 * @return a list of {@link haj.com.entity.DgContractingBulkEntry}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkEntry> allDgContractingBulkEntry(Long from, int noRows) throws Exception {
	 	String hql = "select o from DgContractingBulkEntry o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<DgContractingBulkEntry>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DgContractingBulkEntry
 	 * @return a {@link haj.com.entity.DgContractingBulkEntry}
 	 * @throws Exception global exception
 	 */
	public DgContractingBulkEntry findByKey(Long id) throws Exception {
	 	String hql = "select o from DgContractingBulkEntry o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DgContractingBulkEntry)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DgContractingBulkEntry by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DgContractingBulkEntry
  	 * @return a list of {@link haj.com.entity.DgContractingBulkEntry}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkEntry> findByName(String description) throws Exception {
	 	String hql = "select o from DgContractingBulkEntry o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DgContractingBulkEntry>)super.getList(hql, parameters);
	}
	
	/*
	 * Count All Entries for batch number
	 */
	public Integer countAllEntries() throws Exception {
	 	String hql = "select count(o) from DgContractingBulkEntry o " ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}
	
	/*
	 * count open bulk entries
	 */
	public Integer countOpenBulkEntries() throws Exception {
	 	String hql = "select count(o) from DgContractingBulkEntry o where o.status is null " ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}
	
	/*
	 * Count entries with status assigned
	 */
	public Integer countBulkEntriesWithStatus() throws Exception {
	 	String hql = "select count(o) from DgContractingBulkEntry o where o.status <> null " ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}
	
	/*
	 * Locates the open DG Contracting Bulk Entry that has not been processed
	 */
	public DgContractingBulkEntry findOpenBatchEntry() throws Exception {
		String hql = "select o from DgContractingBulkEntry o where o.status is null " ;
		return (DgContractingBulkEntry)super.getUniqueResult(hql);
	}
}
