package haj.com.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.CompanyHistoryDAO;
import haj.com.entity.Address;
import haj.com.entity.AddressHistory;
import haj.com.entity.Company;
import haj.com.entity.CompanyHistory;
import haj.com.framework.AbstractService;

public class CompanyHistoryService extends AbstractService {
	/** The dao. */
	private CompanyHistoryDAO dao = new CompanyHistoryDAO();
	private static CompanyHistoryService companyHistoryService = null;

	/**
	 * Instance.
	 *
	 * @return the tasks service
	 */
	public static synchronized CompanyHistoryService instance() {
		if (companyHistoryService == null) {
			companyHistoryService = new CompanyHistoryService();
		}
		return companyHistoryService;
	}

	/**
	 * Get all CompanyHistory
	 * 
	 * @author TechFinium
	 * @see CompanyHistory
	 * @return a list of {@link haj.com.entity.CompanyHistory}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyHistory> allCompanyHistory() throws Exception {
		return dao.allCompanyHistory();
	}

	/**
	 * Create or update CompanyHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyHistory
	 */
	public void create(CompanyHistory entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update CompanyHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyHistory
	 */
	public void update(CompanyHistory entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyHistory
	 */
	public void delete(CompanyHistory entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyHistory}
	 * @throws Exception
	 *             the exception
	 * @see CompanyHistory
	 */
	public CompanyHistory findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find CompanyHistory by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyHistory}
	 * @throws Exception
	 *             the exception
	 * @see CompanyHistory
	 */
	public List<CompanyHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load CompanyHistory
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyHistory}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyHistory> allCompanyHistory(int first, int pageSize) throws Exception {
		return dao.allCompanyHistory(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of CompanyHistory for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the CompanyHistory
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyHistory.class);
	}

	/**
	 * Lazy load CompanyHistory with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            CompanyHistory class
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
	 * @return a list of {@link haj.com.entity.CompanyHistory} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyHistory> allCompanyHistory(Class<CompanyHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CompanyHistory>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of CompanyHistory for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            CompanyHistory class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyHistory entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyHistory> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public Company findCompanyByKey(Long id) throws Exception {
		return dao.findCompanyByKey(id);
	}

	public List<CompanyHistory> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company);
	}

	public List<CompanyHistory> findByCompanyLatest(Company company) throws Exception {
		return dao.findByCompanyLatest(company);
	}

	public List<CompanyHistory> findByCompanyOldest(Company company) throws Exception {
		return dao.findByCompanyOldest(company);
	}
	
	public void createCompanyAndAddressHistory(Long companyId) {
		try {
			if (companyId != null) {

				CompanyService companyService = new CompanyService();
				Company entity = companyService.findByKey(companyId);
				if (entity != null && entity.getEmail() != null) {
					if(entity.getEmail().equals("") || entity.getEmail().isEmpty()){
						entity.setEmail(null);
					}
				}
				// Company History
				CompanyHistory ch = new CompanyHistory(entity);
				BeanUtils.copyProperties(ch, entity);
				this.dao.create(ch);
				// Residential Address History
				if (entity.getResidentialAddress() != null) {
					Address residentialAddress = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
					AddressHistory addressHistory = new AddressHistory(residentialAddress);
					BeanUtils.copyProperties(addressHistory, residentialAddress);
					addressHistory.setId(null);
					AddressHistoryService.instance().create(addressHistory);
				}
				if (entity.getPostalAddress() != null) {
					Address postalAddress = AddressService.instance().findByKey(entity.getPostalAddress().getId());
					// Postal Address History
					AddressHistory postalAddressHistory = new AddressHistory(postalAddress);
					BeanUtils.copyProperties(postalAddressHistory, postalAddress);
					postalAddressHistory.setId(null);
					AddressHistoryService.instance().create(postalAddressHistory);
				}

			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createHistory(Long companyId) {
		try {
			if (companyId != null) {

				CompanyService companyService = new CompanyService();
				Company entity = companyService.findByKey(companyId);
				if (entity != null && entity.getEmail() != null) {
					if(entity.getEmail().equals("") || entity.getEmail().isEmpty()){
						entity.setEmail(null);
					}
				}
				// Company History
				CompanyHistory ch = new CompanyHistory(entity);
				//NEED TO FIX THIS
//				BeanUtils.copyProperties(ch, entity);
//				this.dao.create(ch);
				// Residential Address History
//				if (entity.getResidentialAddress() != null) {
//					Address residentialAddress = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
//					AddressHistory addressHistory = new AddressHistory(residentialAddress);
//					BeanUtils.copyProperties(addressHistory, residentialAddress);
//					addressHistory.setId(null);// So New entity will be creates
//					AddressHistoryService.instance().create(addressHistory);
//				}
//				if (entity.getPostalAddress() != null) {
//					Address postalAddress = AddressService.instance().findByKey(entity.getPostalAddress().getId());
//					// Postal Address History
//					AddressHistory postalAddressHistory = new AddressHistory(postalAddress);
//					BeanUtils.copyProperties(postalAddressHistory, postalAddress);
//					postalAddressHistory.setId(null);// So New entity will be creates
//					AddressHistoryService.instance().create(postalAddressHistory);
//				}

			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeCompanyHistory(Company linkedComp) throws Exception{
		//Delete Company History
		ArrayList<CompanyHistory>  companyHistories=(ArrayList<CompanyHistory>) findByCompany(linkedComp);
		for(CompanyHistory comp:companyHistories)
		{
			comp.setForCompany(null);
			update(comp);
			delete(comp);
		}
		
	}
}
