package haj.com.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.SortOrder;

import com.google.code.geocoder.model.LatLng;

import haj.com.dao.AddressHistoryDAO;
import haj.com.entity.Address;
import haj.com.entity.AddressHistory;
import haj.com.entity.Municipality;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import haj.com.utils.GeoCoderUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressService.
 */
public class AddressHistoryService extends AbstractService {

	/** The dao. */
	private AddressHistoryDAO dao = new AddressHistoryDAO();

	/** The address service. */
	private static AddressHistoryService addressService = null;
	
	/** The municipality service. */
	private MunicipalityService municipalityService = new MunicipalityService();

	/**
	 * Instance.
	 *
	 * @return the address service
	 */
	public static synchronized AddressHistoryService instance() {
		if (addressService == null) {
			addressService = new AddressHistoryService();
		}
		return addressService;
	}

	/**
	 * Get all AddressHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.AddressHistory}
	 * @throws Exception
	 *             the exception
	 * @see AddressHistory
	 */
	public List<AddressHistory> allAddress() throws Exception {
		return dao.allAddressHistory();
	}

	/**
	 * Create or update AddressHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AddressHistory
	 */
	public void create(AddressHistory entity) throws Exception {
		if (entity.getId() == null)
			this.dao.create(entity);
		else
			this.dao.update(entity);
	}

	/**
	 * Update AddressHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AddressHistory
	 */
	public void update(AddressHistory entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete AddressHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see AddressHistory
	 */
	public void delete(AddressHistory entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a Address object
	 * @throws Exception
	 *             the exception
	 * @see AddressHistory
	 */
	public AddressHistory findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a Address object
	 * @throws Exception
	 *             the exception
	 * @see AddressHistory
	 */
	public AddressHistory findByHostingCompanyId(long id) throws Exception {
		return dao.findByHostingCompanyId(id);
	}

	/**
	 * Find AddressHistory by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 * @see AddressHistory
	 */
	public List<AddressHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load AddressHistory.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.AddressHistory}
	 * @throws Exception
	 *             the exception
	 */
	public List<AddressHistory> allAddressHistory(int first, int pageSize) throws Exception {
		return dao.allAddressHistory(Long.valueOf(first), pageSize);
	}

	/**
	 * Count.
	 *
	 * @author TechFinium
	 * @return Number of rows in the AddressHistory
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(AddressHistory.class);
	}

	/**
	 * All AddressHistory.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Address class
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
	 * @return a list of {@link haj.com.entity.AddressHistory} containing data
	 */
	@SuppressWarnings("unchecked")
	public List<AddressHistory> allAddressHistory(Class<AddressHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		return (List<AddressHistory>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Count.
	 *
	 * @param entity
	 *            AddressHistory class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the AddressHistory entity
	 */
	public int count(Class<AddressHistory> entity, Map<String, Object> filters) {
		return dao.count(entity, filters);
	}

	// public List<Address> findByCompany(Company company) throws Exception {
	// return dao.findByCompany(company.getId());
	// }

	/**
	 * AddressHistory for a users.
	 *
	 * @param user
	 *            the user
	 * @return a list of {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 */
	public List<AddressHistory> findByUser(Users user) throws Exception {
		return dao.findByUser(user.getId());
	}

	/**
	 * Lookup longitude latitude.
	 *
	 * @param addr
	 *            the addr
	 */
	public void lookupLongitudeLatitude(AddressHistory addr) {
		try {
			String address = addr.getAddressLine1() == null ? ""
					: addr.getAddressLine1() + " " + addr.getAddressLine2() == null ? ""
							: addr.getAddressLine2() + " " + addr.getAddressLine3() == null ? "" : addr.getAddressLine3() + " " + addr.getAddressLine4() == null ? "" : addr.getAddressLine4() + " " + addr.getPostcode() == null ? "" : addr.getPostcode();
			if (address.trim().length() > 3) {
				LatLng ll = GeoCoderUtil.calcLatLng(address.trim());
				if (ll != null) {
					addr.setLatitude(ll.getLat().doubleValue());
					addr.setLongitude(ll.getLng().doubleValue());
				}
			}
		} catch (Exception e) {
			logger.fatal(e);
		}

	}

	/**
	 * Find municipalities autocomplete.
	 *
	 * @param desc the desc
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<Municipality> findMunicipalitiesAutocomplete(String desc) throws Exception {
		return municipalityService.findByName(desc);
	}
	
	public void createHistory(Long addressId) throws Exception {
		if (addressId != null){
			Address entity = AddressService.instance().findByKey(addressId);
			AddressHistory addressHistory = new AddressHistory(entity);
			BeanUtils.copyProperties(addressHistory, entity);
			this.dao.create(addressHistory);
		}
	}
	

	/**
	 * Find by forAddress.
	 *
	 * @param forAddressID
	 *            the forAddressID
	 * @return a list of {@link haj.com.entity.AddressHistory}
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<AddressHistory> findByForAddress(Long forAddressID) throws Exception {
		return dao.findByForAddress(forAddressID);
	}

	public Integer countByForAddress(Long forAddressID) throws Exception {
		return dao.countByForAddress(forAddressID);
	}
	
}
