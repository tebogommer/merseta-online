package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.GPCreditorsDAO;
import haj.com.entity.Company;
import haj.com.entity.lookup.GPCreditors;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class GPCreditorsService.
 */
public class GPCreditorsService extends AbstractService {
	/** The dao. */
	private GPCreditorsDAO dao = new GPCreditorsDAO();

	/**
	 * Get all GPCreditors.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.lookup.GPCreditors}
	 * @throws Exception the exception
	 * @see   GPCreditors
	 */
	public List<GPCreditors> allGPCreditors() throws Exception {
	  	return dao.allGPCreditors();
	}


	/**
	 * Create or update GPCreditors.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     GPCreditors
	 */
	public void create(GPCreditors entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  GPCreditors.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    GPCreditors
	 */
	public void update(GPCreditors entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  GPCreditors.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    GPCreditors
	 */
	public void delete(GPCreditors entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.lookup.GPCreditors}
	 * @throws Exception the exception
	 * @see    GPCreditors
	 */
	public GPCreditors findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find GPCreditors by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.lookup.GPCreditors}
	 * @throws Exception the exception
	 * @see    GPCreditors
	 */
	public List<GPCreditors> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load GPCreditors.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.lookup.GPCreditors}
	 * @throws Exception the exception
	 */
	public List<GPCreditors> allGPCreditors(int first, int pageSize) throws Exception {
		return dao.allGPCreditors(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of GPCreditors for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the GPCreditors
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(GPCreditors.class);
	}
	
    /**
     * Lazy load GPCreditors with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 GPCreditors class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.lookup.GPCreditors} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<GPCreditors> allGPCreditors(Class<GPCreditors> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<GPCreditors>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of GPCreditors for lazy load with filters.
     *
     * @author TechFinium
     * @param entity GPCreditors class
     * @param filters the filters
     * @return Number of rows in the GPCreditors entity
     * @throws Exception the exception
     */	
	public int count(Class<GPCreditors> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by ref no.
	 *
	 * @param refNo the ref no
	 * @return the GP creditors
	 * @throws Exception the exception
	 */
	public GPCreditors findByRefNo(String refNo) throws Exception { 
		return dao.findByRefNo(refNo);
	}
	
	/**
	 * Exist in GP.
	 *
	 * @param refNo the ref no
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean existInGP(String refNo) throws Exception { 
		return dao.findByRefNo(refNo)==null?false:true;
	}
	
	/**
	 * Creates the.
	 *
	 * @param comp the comp
	 * @throws Exception the exception
	 */
	public void create(Company comp) throws Exception { 
		if (!existInGP(comp.getLevyNumber())) {
			GPCreditors gp = new GPCreditors();
			gp.setVendorId(comp.getLevyNumber());
			gp.setVendorClassId(comp.getSicCode().getChamber().getGpVendorClass().getGPName());
			gp.setVendorName(comp.getCompanyName());
			gp.setVendorStatus("");
			gp.setHold("");
			gp.setCurrentBalance(0.0);
			dao.create(gp);
		}
	}
	
	
}
