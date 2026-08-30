package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.CompanyTradeTestEmployerDAO;
import haj.com.entity.CompanyTradeTestEmployer;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyTradeTestEmployerService.
 */
public class CompanyTradeTestEmployerService extends AbstractService {
	/** The dao. */
	private CompanyTradeTestEmployerDAO dao = new CompanyTradeTestEmployerDAO();

	/**
	 * Get all CompanyTradeTestEmployer.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyTradeTestEmployer}
	 * @throws Exception
	 *             the exception
	 * @see CompanyTradeTestEmployer
	 */
	public List<CompanyTradeTestEmployer> allCompanyTradeTestEmployer() throws Exception {
		return dao.allCompanyTradeTestEmployer();
	}

	/**
	 * Create or update CompanyTradeTestEmployer.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyTradeTestEmployer
	 */
	public void create(CompanyTradeTestEmployer entity) throws Exception {
		if (entity.getId() == null) {
			entity.setCreateDate(new java.util.Date());
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update CompanyTradeTestEmployer.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyTradeTestEmployer
	 */
	public void update(CompanyTradeTestEmployer entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyTradeTestEmployer.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyTradeTestEmployer
	 */
	public void delete(CompanyTradeTestEmployer entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyTradeTestEmployer}
	 * @throws Exception
	 *             the exception
	 * @see CompanyTradeTestEmployer
	 */
	public CompanyTradeTestEmployer findByKey(long id) throws Exception {
		return populateAdditioanlInformation(dao.findByKey(id));
	}

	/**
	 * Find CompanyTradeTestEmployer by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyTradeTestEmployer}
	 * @throws Exception
	 *             the exception
	 * @see CompanyTradeTestEmployer
	 */
	public List<CompanyTradeTestEmployer> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load CompanyTradeTestEmployer.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyTradeTestEmployer}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyTradeTestEmployer> allCompanyTradeTestEmployer(int first, int pageSize) throws Exception {
		return dao.allCompanyTradeTestEmployer(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of CompanyTradeTestEmployer for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the CompanyTradeTestEmployer
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyTradeTestEmployer.class);
	}

	/**
	 * Lazy load CompanyTradeTestEmployer with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            CompanyTradeTestEmployer class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.CompanyTradeTestEmployer}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyTradeTestEmployer> allCompanyTradeTestEmployer(Class<CompanyTradeTestEmployer> class1, int first,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CompanyTradeTestEmployer>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of CompanyTradeTestEmployer for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            CompanyTradeTestEmployer class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyTradeTestEmployer entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyTradeTestEmployer> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void createUpdateEmployerWithAddress(CompanyTradeTestEmployer entity, boolean copyAddress) throws Exception {
		if (entity.getId() == null) {
			entity.setCreateDate(new java.util.Date());
			if (entity.getResidentialAddress() != null && entity.getResidentialAddress().getId() != null) {
				if (copyAddress) {
					AddressService.instance().copyFromToAddress(entity.getResidentialAddress(), entity.getPostalAddress());
					AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
				} else {
					AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
				}
			} else if (entity.getResidentialAddress() != null && entity.getResidentialAddress().getId() == null) {
				if (copyAddress) {
					AddressService.instance().copyFromToAddress(entity.getResidentialAddress(), entity.getPostalAddress());
					AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
				} else {
					AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
				}
				entity.setResidentialAddress(AddressService.instance().findByKey(entity.getResidentialAddress().getId()));
				entity.setPostalAddress(AddressService.instance().findByKey(entity.getPostalAddress().getId()));
			}
			entity.setRegisteredAddress(null);
			this.dao.create(entity);
		} else {
			if (entity.getResidentialAddress() != null) {
				if (entity.getResidentialAddress() != null && entity.getResidentialAddress().getId() != null) {
					if (copyAddress) {
						AddressService.instance().copyFromToAddress(entity.getResidentialAddress(), entity.getPostalAddress());
						AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
					} else {
						AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
					}
				} else if (entity.getResidentialAddress() != null && entity.getResidentialAddress().getId() == null) {
					if (copyAddress) {
						AddressService.instance().copyFromToAddress(entity.getResidentialAddress(), entity.getPostalAddress());
						AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
					} else {
						AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
					}
					entity.setResidentialAddress(AddressService.instance().findByKey(entity.getResidentialAddress().getId()));
					entity.setPostalAddress(AddressService.instance().findByKey(entity.getPostalAddress().getId()));
				}
			}
			entity.setRegisteredAddress(null);
			this.dao.update(entity);
		}	
	}
	
	public List<CompanyTradeTestEmployer> populateAdditioanlInformationList(List<CompanyTradeTestEmployer> companyTradeTestEmployerList) throws Exception{
		for (CompanyTradeTestEmployer companyTradeTestEmployer : companyTradeTestEmployerList) {
			populateAdditioanlInformation(companyTradeTestEmployer);
		}
		return companyTradeTestEmployerList;
	}
	
	public CompanyTradeTestEmployer populateAdditioanlInformation(CompanyTradeTestEmployer companyTradeTestEmployer) throws Exception{
		if (companyTradeTestEmployer.getId() != null) {
			if (companyTradeTestEmployer.getResidentialAddress() != null) {
				companyTradeTestEmployer.setResidentialAddress(AddressService.instance().findByKey(companyTradeTestEmployer.getResidentialAddress().getId()));
			} 
			if (companyTradeTestEmployer.getPostalAddress() != null) {
				companyTradeTestEmployer.setPostalAddress(AddressService.instance().findByKey(companyTradeTestEmployer.getPostalAddress().getId()));
			} 
		}
		return companyTradeTestEmployer;
	}
}
