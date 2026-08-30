package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.TransactionsCompanyValidiationDAO;
import haj.com.entity.TransactionsCompanyValidiation;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class TransactionsCompanyValidiationService extends AbstractService {
	/** The dao. */
	private TransactionsCompanyValidiationDAO dao = new TransactionsCompanyValidiationDAO();

	/**
	 * Get all TransactionsCompanyValidiation
 	 * @author TechFinium 
 	 * @see   TransactionsCompanyValidiation
 	 * @return a list of {@link haj.com.entity.TransactionsCompanyValidiation}
	 * @throws Exception the exception
 	 */
	public List<TransactionsCompanyValidiation> allTransactionsCompanyValidiation() throws Exception {
	  	return dao.allTransactionsCompanyValidiation();
	}


	/**
	 * Create or update TransactionsCompanyValidiation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TransactionsCompanyValidiation
	 */
	public void create(TransactionsCompanyValidiation entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TransactionsCompanyValidiation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TransactionsCompanyValidiation
	 */
	public void update(TransactionsCompanyValidiation entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TransactionsCompanyValidiation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TransactionsCompanyValidiation
	 */
	public void delete(TransactionsCompanyValidiation entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TransactionsCompanyValidiation}
	 * @throws Exception the exception
	 * @see    TransactionsCompanyValidiation
	 */
	public TransactionsCompanyValidiation findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}
	
	public List<TransactionsCompanyValidiation> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		return dao.findByTargetClassAndKey(targetClass, targetKey);
	}

	/**
	 * Find TransactionsCompanyValidiation by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TransactionsCompanyValidiation}
	 * @throws Exception the exception
	 * @see    TransactionsCompanyValidiation
	 */
	public List<TransactionsCompanyValidiation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TransactionsCompanyValidiation
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TransactionsCompanyValidiation}
	 * @throws Exception the exception
	 */
	public List<TransactionsCompanyValidiation> allTransactionsCompanyValidiation(int first, int pageSize) throws Exception {
		return dao.allTransactionsCompanyValidiation(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TransactionsCompanyValidiation for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the TransactionsCompanyValidiation
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	    return dao.count(TransactionsCompanyValidiation.class);
	}
	
	public List<TransactionsCompanyValidiation> findByTargetClassKeyAndErrorEntry(String targetClass, Long targetKey, Boolean errorentry) throws Exception {
		return dao.findByTargetClassKeyAndErrorEntry(targetClass, targetKey, errorentry);
	}
	
	public void deleteAllEntriesByTargetClassAndKey(String targetClass, Long targetKey) throws Exception{
		List<IDataEntity> deleteList = new ArrayList<>(); 
		deleteList.addAll(findByTargetClassAndKey(targetClass, targetKey));
		dao.deleteBatch(deleteList, 500);
	}
	
	public TransactionsCompanyValidiation prepNewTransactionsCompanyValidiation(Users users, String levyNumber, String targetClass, Long targetKey, Boolean newCompany, Boolean errorEntry, String errorMessage) throws Exception{
		TransactionsCompanyValidiation entity = new TransactionsCompanyValidiation();
		if (users != null) {
			entity.setCreateUsers(users);
		}
		entity.setLevyNumber(levyNumber);
		entity.setTargetClass(targetClass);
		entity.setTargetKey(targetKey);
		if (newCompany != null) {
			entity.setNewCompany(newCompany);
		}
		if (errorEntry != null) {
			entity.setErrorEntry(errorEntry);
		}
		if (errorMessage != null && !errorMessage.trim().isEmpty()) {
			entity.setErrorMessage(errorMessage.trim());
		}
		return entity;
	}
	
	public void createNewTransactionsCompanyValidiation(Users users, String levyNumber, String targetClass, Long targetKey, Boolean newCompany, Boolean errorEntry, String errorMessage) throws Exception{
		TransactionsCompanyValidiation entity = prepNewTransactionsCompanyValidiation(users, levyNumber, targetClass, targetKey, newCompany, errorEntry, errorMessage);
		create(entity);
	}
	
	public Integer countTransactionsCompanyValidiationByTargetClassKeyAndErrorEntry(String targetClass, Long targetKey, Boolean errorentry) throws Exception {
		return dao.countTransactionsCompanyValidiationByTargetClassKeyAndErrorEntry(targetClass, targetKey, errorentry);
	}
	
    /**
     * Lazy load TransactionsCompanyValidiation with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 TransactionsCompanyValidiation class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TransactionsCompanyValidiation} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TransactionsCompanyValidiation> allTransactionsCompanyValidiation(Class<TransactionsCompanyValidiation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TransactionsCompanyValidiation>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TransactionsCompanyValidiation for lazy load with filters
     * @author TechFinium 
     * @param entity TransactionsCompanyValidiation class
     * @param filters the filters
     * @return Number of rows in the TransactionsCompanyValidiation entity
     * @throws Exception the exception     
     */	
	public int count(Class<TransactionsCompanyValidiation> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<TransactionsCompanyValidiation> allTransactionsCompanyValidiationByTargetClassKeyAndErrorEntry(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey, Boolean errorEntry) throws Exception {
		String hql = "select o from TransactionsCompanyValidiation o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.errorEntry = :errorEntry";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		filters.put("errorEntry", errorEntry);
		return ( List<TransactionsCompanyValidiation>) dao.sortAndFilterWhere(first,  pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllTransactionsCompanyValidiationByTargetClassKeyAndErrorEntry(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TransactionsCompanyValidiation o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.errorEntry = :errorEntry";
		return  dao.countWhere(filters, hql);
	}
}
