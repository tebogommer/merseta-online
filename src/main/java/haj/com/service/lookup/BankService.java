package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.BankDAO;
import haj.com.entity.lookup.Bank;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class BankService.
 */
public class BankService extends AbstractService {
	/** The dao. */
	private BankDAO dao = new BankDAO();

	/**
	 * Get all Bank.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Bank}
	 * @throws Exception the exception
	 * @see   Bank
	 */
	public List<Bank> allBank() throws Exception {
	  	return dao.allBank();
	}


	/**
	 * Create or update Bank.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Bank
	 */
	public void create(Bank entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Bank.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Bank
	 */
	public void update(Bank entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Bank.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Bank
	 */
	public void delete(Bank entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Bank}
	 * @throws Exception the exception
	 * @see    Bank
	 */
	public Bank findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Bank by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Bank}
	 * @throws Exception the exception
	 * @see    Bank
	 */
	public List<Bank> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Bank.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Bank}
	 * @throws Exception the exception
	 */
	public List<Bank> allBank(int first, int pageSize) throws Exception {
		return dao.allBank(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Bank for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Bank
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Bank.class);
	}
	
    /**
     * Lazy load Bank with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Bank class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Bank} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Bank> allBank(Class<Bank> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Bank>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Bank for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Bank class
     * @param filters the filters
     * @return Number of rows in the Bank entity
     * @throws Exception the exception
     */	
	public int count(Class<Bank> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
