package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.ActiveContracts;
import haj.com.entity.DgContractingBulkItems;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.BulkApprovalEnum;

public class DgContractingBulkItemsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DgContractingBulkItems
 	 * @author TechFinium 
 	 * @see    DgContractingBulkItems
 	 * @return a list of {@link haj.com.entity.DgContractingBulkItems}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkItems> allDgContractingBulkItems() throws Exception {
		return (List<DgContractingBulkItems>)super.getList("select o from DgContractingBulkItems o");
	}

	/**
	 * Get all DgContractingBulkItems between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DgContractingBulkItems
 	 * @return a list of {@link haj.com.entity.DgContractingBulkItems}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkItems> allDgContractingBulkItems(Long from, int noRows) throws Exception {
	 	String hql = "select o from DgContractingBulkItems o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DgContractingBulkItems>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DgContractingBulkItems
 	 * @return a {@link haj.com.entity.DgContractingBulkItems}
 	 * @throws Exception global exception
 	 */
	public DgContractingBulkItems findByKey(Long id) throws Exception {
	 	String hql = "select o from DgContractingBulkItems o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DgContractingBulkItems)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DgContractingBulkItems by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DgContractingBulkItems
  	 * @return a list of {@link haj.com.entity.DgContractingBulkItems}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkItems> findByName(String description) throws Exception {
	 	String hql = "select o from DgContractingBulkItems o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DgContractingBulkItems>)super.getList(hql, parameters);
	}
	
	public Integer countEntriesAgainstDgContractingBulkEntry(Long dgContractingBulkEntryId) throws Exception {
	 	String hql = "select count(o) from DgContractingBulkItems o where o.dgContractingBulkEntry.id = :dgContractingBulkEntryId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgContractingBulkEntryId", dgContractingBulkEntryId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkItems> findByDgContractingBulkEntryAllEntries(Long dgContractingBulkEntryId) throws Exception {
		String hql = "select o from DgContractingBulkItems o where o.dgContractingBulkEntry.id = :dgContractingBulkEntryId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgContractingBulkEntryId", dgContractingBulkEntryId);
		return (List<DgContractingBulkItems>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkItems> findByDgContractingBulkEntryByBulkStatus(Long dgContractingBulkEntryId, BulkApprovalEnum bulkApprovalEnum, Boolean processedValue) throws Exception {
		String hql = "select o from DgContractingBulkItems o where o.dgContractingBulkEntry.id = :dgContractingBulkEntryId and o.bulkApproval = :bulkApprovalEnum and (o.processed is null or o.processed = :processedValue)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgContractingBulkEntryId", dgContractingBulkEntryId);
	    parameters.put("bulkApprovalEnum", bulkApprovalEnum);
	    parameters.put("processedValue", processedValue);
		return (List<DgContractingBulkItems>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkItems> findByDgContractingBulkEntryWithoutStatus(Long dgContractingBulkEntryId) throws Exception {
		String hql = "select o from DgContractingBulkItems o where o.dgContractingBulkEntry.id = :dgContractingBulkEntryId and o.statusAssigned is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgContractingBulkEntryId", dgContractingBulkEntryId);
		return (List<DgContractingBulkItems>)super.getList(hql, parameters);
	}
	
	public Integer countEntriesWithoutStatus(Long dgContractingBulkEntryId) throws Exception {
		String hql = "select count(o) from DgContractingBulkItems o where o.dgContractingBulkEntry.id = :dgContractingBulkEntryId and o.statusAssigned is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgContractingBulkEntryId", dgContractingBulkEntryId);
	    return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countEntriesByStatus(Long dgContractingBulkEntryId, ApprovalEnum approvalEnum) throws Exception {
		String hql = "select count(o) from DgContractingBulkItems o where o.dgContractingBulkEntry.id = :dgContractingBulkEntryId and o.statusAssigned = :approvalEnum" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgContractingBulkEntryId", dgContractingBulkEntryId);
	    parameters.put("approvalEnum", approvalEnum);
	    return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countEntriesWithoutNewStatus(Long dgContractingBulkEntryId) throws Exception {
		String hql = "select count(o) from DgContractingBulkItems o where o.dgContractingBulkEntry.id = :dgContractingBulkEntryId and o.bulkApproval is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgContractingBulkEntryId", dgContractingBulkEntryId);
	    return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countEntriesByNewStatusAssigned(Long dgContractingBulkEntryId, BulkApprovalEnum bulkApprovalEnum) throws Exception {
		String hql = "select count(o) from DgContractingBulkItems o where o.dgContractingBulkEntry.id = :dgContractingBulkEntryId and o.bulkApproval = :bulkApprovalEnum" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgContractingBulkEntryId", dgContractingBulkEntryId);
	    parameters.put("bulkApprovalEnum", bulkApprovalEnum);
	    return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContracts> findActiveContractsWithDgContractingBulkEntryAndByStatus(Long dgContractingBulkEntryId, ApprovalEnum status) throws Exception {
		String hql = "select o.activeContracts from DgContractingBulkItems o where o.dgContractingBulkEntry.id = :dgContractingBulkEntryId and o.statusAssigned = :status";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgContractingBulkEntryId", dgContractingBulkEntryId);
	    parameters.put("status", status);
		return (List<ActiveContracts>)super.getList(hql, parameters);
	}
	
	public ActiveContracts findActiveContractsByDgContractingBulkItemsId(Long dgContractingBulkItemsId) throws Exception {
		String hql = "select o.activeContracts from DgContractingBulkItems o where o.id = :dgContractingBulkItemsId";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("dgContractingBulkItemsId", dgContractingBulkItemsId);
	    return (ActiveContracts) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkItems> findApprovedBulkItemsToGenerateTranchPayments() throws Exception {
		String hql = "select o from DgContractingBulkItems o where o.processed = :processed and o.statusAssigned = :statusAssigned and o.dgContractingBulkEntry.status = :status" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("processed", true);
	    parameters.put("statusAssigned", ApprovalEnum.Approved);
	    parameters.put("status", ApprovalEnum.Completed);
		return (List<DgContractingBulkItems>)super.getList(hql, parameters);
	}
	
	public Integer countApprovedActiveContractsByActiveContractId(Long activeContractId) throws Exception {
		String hql = "select count(o) from DgContractingBulkItems o where o.activeContracts.id = :activeContractId and o.statusAssigned = :statusAssigned and o.bulkApproval = :bulkApproval and o.approvalUser is not null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("activeContractId", activeContractId);
	    parameters.put("statusAssigned", ApprovalEnum.Approved);
	    parameters.put("bulkApproval", BulkApprovalEnum.FinalApproved);
	    return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<ActiveContracts> allApprovedActiveContracts() throws Exception {
		String hql = "select distinct(o.activeContracts) from DgContractingBulkItems o where o.statusAssigned = :status and o.approvalUser is not null";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("status", ApprovalEnum.Approved);
		return (List<ActiveContracts>)super.getList(hql, parameters);
	}
	
	public Integer countByActiveContractId(Long activeContractId) throws Exception {
		String hql = "select count(o) from DgContractingBulkItems o where o.activeContracts.id = :activeContractId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("activeContractId", activeContractId);
	    return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkItems> findByActiveContractId(Long activeContractId) throws Exception {
		String hql = "select o from DgContractingBulkItems o where o.activeContracts.id = :activeContractId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("activeContractId", activeContractId);
		return (List<DgContractingBulkItems>)super.getList(hql, parameters);
	}
	
}