package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.google.code.geocoder.model.LatLng;

import haj.com.bean.AddressUpdateInfoBean;
import haj.com.bean.CompanyUpdateInfoBean;
import haj.com.dao.AddressDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.Municipality;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractService;
import haj.com.utils.GeoCoderUtil;
import haj.com.validators.exports.services.AddressValidationService;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressService.
 */
public class AddressService extends AbstractService {

	/** The dao. */
	private AddressDAO dao = new AddressDAO();

	/** The address service. */
	private static AddressService addressService = null;
	
	/** The municipality service. */
	private MunicipalityService municipalityService = new MunicipalityService();

	/**
	 * Instance.
	 *
	 * @return the address service
	 */
	public static synchronized AddressService instance() {
		if (addressService == null) {
			addressService = new AddressService();
		}
		return addressService;
	}

	/**
	 * Get all Address.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 * @see Address
	 */
	public List<Address> allAddress() throws Exception {
		return dao.allAddress();
	}

	/**
	 * Create or update Address.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Address
	 */
	public void create(Address entity) throws Exception {
		if (entity.getId() == null)
			this.dao.create(entity);
		else
			this.dao.update(entity);
	}

	/**
	 * Update Address.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Address
	 */
	public void update(Address entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Address.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Address
	 */
	public void delete(Address entity) throws Exception {
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
	 * @see Address
	 */
	public Address findByKey(long id) throws Exception {
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
	 * @see Address
	 */
	public Address findByKeyJoinDistric(long id) throws Exception {
		return dao.findByKeyJoinDistric(id);
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
	 * @see Address
	 */
	public Address findByHostingCompanyId(long id) throws Exception {
		return dao.findByHostingCompanyId(id);
	}

	/**
	 * Find Address by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 * @see Address
	 */
	public List<Address> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Address.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 */
	public List<Address> allAddress(int first, int pageSize) throws Exception {
		return dao.allAddress(Long.valueOf(first), pageSize);
	}

	/**
	 * Count.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Address
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Address.class);
	}

	/**
	 * All address.
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
	 * @return a list of {@link haj.com.entity.Address} containing data
	 */
	@SuppressWarnings("unchecked")
	public List<Address> allAddress(Class<Address> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		return (List<Address>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Count.
	 *
	 * @param entity
	 *            Address class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the Address entity
	 */
	public int count(Class<Address> entity, Map<String, Object> filters) {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Address> allAddressByID(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long addressId) throws Exception {
		String hql = "select o from Address o where o.id = :id ";
		filters.put("id", addressId);
		return (List<Address>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllAddressByID(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Address o where o.id = :id";
		return dao.countWhere(filters,hql);
	}

	// public List<Address> findByCompany(Company company) throws Exception {
	// return dao.findByCompany(company.getId());
	// }

	/**
	 * Addresses for a users.
	 *
	 * @param user
	 *            the user
	 * @return a list of {@link haj.com.entity.Address}
	 * @throws Exception
	 *             the exception
	 */
	public List<Address> findByUser(Users user) throws Exception {
		return dao.findByUser(user.getId());
	}

	/**
	 * Lookup longitude latitude.
	 *
	 * @param addr
	 *            the addr
	 */
	public void lookupLongitudeLatitude(Address addr) {
		System.out.println("in lookupLongitudeLatitude");
		System.out.println("in getAddressLine1----"+addr.getAddressLine1());
		System.out.println("in getAddressLine2----"+addr.getAddressLine2());
		System.out.println("in getAddressLine3----"+addr.getAddressLine3());
		try {
			String address = addr.getAddressLine1() == null ? ""
					: addr.getAddressLine1() + " " + addr.getAddressLine2() == null ? ""
							: addr.getAddressLine2() + " " + addr.getAddressLine3() == null ? "" : addr.getAddressLine3() + " " + addr.getAddressLine4() == null ? "" : addr.getAddressLine4() + " " + addr.getPostcode() == null ? "" : addr.getPostcode();
			System.out.println("address---"+address);
			System.out.println("address.trim().length()---"+address.trim().length());
//			String address2 = addr.getAddressLine1()+ " " + addr.getAddressLine2()+" " + addr.getAddressLine3()+ " " + addr.getAddressLine4() + " " + addr.getPostcode();
//			System.out.println("address2---"+address2);
//			System.out.println("address.trim().length()---"+address2.trim().length());
			if (address.trim().length() > 3) {
				LatLng ll = GeoCoderUtil.calcLatLng(address.trim());
				System.out.println("ll---"+ll.toString());
				if (ll != null) {
					addr.setLatitude(ll.getLat().doubleValue());
					addr.setLongitude(ll.getLng().doubleValue());
					System.out.println("addr.setLatitude:"+addr.getLatitude());
					System.out.println("addr.setLongitude:"+addr.getLongitude());
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

	/**
	 * Copy from to address.
	 *
	 * @param from the from
	 * @param to the to
	 */
	public void copyFromToAddress(Address from, Address to) {
		to.setPostcode(from.getPostcode());
		to.setAddressLine1(from.getAddressLine1());
		to.setAddressLine2(from.getAddressLine2());
		to.setAddressLine3(from.getAddressLine3());
		to.setAddressLine4(from.getAddressLine4());
		to.setTown(from.getTown());
		to.setLongitude(from.getLongitude());
		to.setLatitude(from.getLatitude());
		to.setMunicipality(from.getMunicipality());
	}

	/**
	 * Clear address.
	 *
	 * @param address the address
	 */
	public void clearAddress(Address address) {
		address.setPostcode(null);
		address.setAddressLine1(null);
		address.setAddressLine2(null);
		address.setAddressLine3(null);
		address.setAddressLine4(null);
		address.setLongitude(null);
		address.setLatitude(null);
		address.setMunicipality(null);
		address.setTown(null);
	}

	/**
	 * Save addresses.
	 *
	 * @param addr1 the addr 1
	 * @param addr2 the addr 2
	 * @throws Exception the exception
	 */
	public void saveAddresses(Address addr1, Address addr2) throws Exception {
		if (addr1 != null) {
			lookupLongitudeLatitude(addr1);
			if (addr1.getId() == null)
				dao.create(addr1);
			else
				dao.update(addr1);
		}
		if (addr2 != null) {
			lookupLongitudeLatitude(addr2);
			if (addr2.getId() == null)
				dao.create(addr2);
			else
				dao.update(addr2);
		}
	}
	
	
	public void updateAddressForValidiation(Address address) throws Exception{
		lookupLongitudeLatitude(address);
		update(address);
	}

	/**
	 * Save address by object.
	 *
	 * @param addressRes the address res
	 * @param addressPostal the address postal
	 * @param object the object
	 */
	public void saveAddressByObject(Address addressRes, Address addressPostal, Object object) {
		if (object instanceof Users) {

		} else if (object instanceof Company) {

		}
	}
	
	/**
	 * Converts Longitude / Latitude to its degree, minutes and seconds 
	 * @param input
	 * @param booleanLongitude
	 * @return Address
	 */
	public void coordinateToDegreesMinutesSeconds(double value, Boolean booleanLongitude) throws Exception{
		
		
		
        int[] toReturn = new int[3];
        toReturn[0] = (int)value;
        
        // degree
        // minutes
        // seconds
        
        value = value > 0 ? value - toReturn[0]: (value - toReturn[0]) * -1;
        toReturn[1] = (int) ((value) * 60);
        toReturn[2] = (int) (((value) * 3600) % 60);
        
        if (booleanLongitude) {
			// converts Longitude
		}else {
			// converts Latitude
		}
    }
    
	/**
	 * Converts Longitude / Latitude degree, minutes seconds to Longitude / Latitude
	 * @param degrees
	 * @param minutes
	 * @param seconds
	 * @return
	 */
    public static double degreesMinutesSecondsToCoordinate(int degrees, int minutes, int seconds) throws Exception{
        double toReturn = degrees > 0 ? degrees : (degrees * -1) + ((double)minutes / 60.0) + ((double)seconds / 3600.0);
        return degrees > 0 ? toReturn : (toReturn * -1);
    }
    
    
    /* SETMIS VALIDIATION START */
	public StringBuilder validiateAddressInformation(Address entity) {
		StringBuilder errors = new StringBuilder("");
		
		if (entity.getAddressLine1() != null && !entity.getAddressLine1().isEmpty()) {
			if (!AddressValidationService.instance().validatingAdressLine1(entity.getAddressLine1())) {
				errors.append("Validation Failed For SETMIS Address Line 1 <ul><li>This field may not be left blank</li><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ -1234567890#&()/\\:._`',</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (entity.getAddressLine2() != null && !entity.getAddressLine2().isEmpty()) {
			if (!AddressValidationService.instance().validatingAdressLine2(entity.getAddressLine2())) {
				errors.append("Validation Failed For SETMIS Address Line 2 <ul><li>This field may not be left blank</li><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ -1234567890#&()/\\:._`',</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (entity.getAddressLine3() != null && !entity.getAddressLine3().isEmpty()) {
			if (!AddressValidationService.instance().validatingAdressLine3(entity.getAddressLine3())) {
				errors.append("Validation Failed For SETMIS Address Line 3 <ul><li>This field may not be left blank</li><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ -1234567890#&()/\\\\:._`',</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (entity.getPostcode() != null && !entity.getPostcode().isEmpty()) {
			if (!AddressValidationService.instance().validatingPostCode(entity.getPostcode())) {
				errors.append("Validation Failed For SETMIS Post Code <ul><li>This field may not be left blank</li><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters 1234567890</li><li>Length of field must be exactly 4 characters</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (entity.getLatitudeDegrees() != null) {
			if (!AddressValidationService.instance().validatingLatitudeDegrees(entity.getLatitudeDegrees())) {
				errors.append("Validation Failed For SETMIS Latitude Degrees <ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters 1234567890-</li><li>Field must be a negative value, may not be greater than -22 and may not have a value less than -35</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (entity.getLatitudeMinutes() != null) {
			if (!AddressValidationService.instance().validatingLatitudeMinutes(entity.getLatitudeMinutes())) {
				errors.append("Validation Failed For SETMIS Latitude Minutes <ul><li>Uppercase value in field may only contain characters 1234567890-</li><li>Value must have a length of exactly 2 (leading zeros) and may not be greater than 59</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (entity.getLatitudeSeconds() != null) {
			if (!AddressValidationService.instance().validatingLatitudeSeconds(entity.getLatitudeSeconds())) {
				errors.append("Validation Failed For SETMIS Latitude Seconds <ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters 1234567890.</li><li>Value must have a length of exactly 6 (nn.nnn) and may not be greater than 59.999</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (entity.getLongitudeDegrees() != null) {
			if (!AddressValidationService.instance().validatingLongitudeDegrees(entity.getLongitudeDegrees())) {
				errors.append("Validation Failed For SETMIS Longitude Degrees <ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters 1234567890-</li><li>Field must not be a negative value, may not be greater than 33 and may not have a value less than 16</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (entity.getLongitudeMinutes() != null) {
			if (!AddressValidationService.instance().validatingLongitudeMinutes(entity.getLongitudeMinutes())) {
				errors.append("Validation Failed For SETMIS Longitude Minutes <ul><li>Uppercase value in field may only contain characters 1234567890-</li><li>Value must have a length of exactly 2 (leading zeros) and may not be greater than 59</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (entity.getLongitudeSeconds() != null) {
			if (!AddressValidationService.instance().validatingLongitudeSeconds(entity.getLongitudeSeconds())) {
				errors.append("Validation Failed For SETMIS Longitude Seconds <ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters 1234567890.</li><li>Value must have a length of exactly 6 (nn.nnn) and may not be greater than 59.999</li></ul>");
				errors.append("<br/>");
			}
		}
		
		return errors;
	}
    /*SETMIS VALIDIATION END*/
	
	/* Determine address information alteration by config process */
	public void determainAlterationsByConfigProcess(Address address, ConfigDocProcessEnum processEnum, boolean postalAddress) throws Exception {
		AddressUpdateInfoBean bean = new AddressUpdateInfoBean();
		if (address.getId() == null) {
			bean.setLineOne(true);
			bean.setLineTwo(true);
			bean.setLineThree(true);
			bean.setLineFour(true);
			bean.setPostCode(true);
			bean.setTown(true);
			bean.setMuncipality(true);
			if (postalAddress) {
				bean.setLongitudeDegrees(false);
				bean.setLatitudeDegrees(false);
			}else {
				bean.setLongitudeDegrees(true);
				bean.setLatitudeDegrees(true);
			}
		} else {
			switch (processEnum) {
			case SDP_LEGACY_APPLICATION:
				setTrainingProviderRegsitartionExistingAddress(address, bean, postalAddress);
				break;
			case TP:
				setTrainingProviderRegsitartionExistingAddress(address, bean, postalAddress);
				break;
			default:
				break;
			}
		}
		address.setAddressUpdateInfoBean(bean);
	}

	/**
	 * @param address
	 * @param bean
	 */
	public void setTrainingProviderRegsitartionExistingAddress(Address address, AddressUpdateInfoBean bean, boolean postalAddress) {
		if (address.getAddressLine1() == null || address.getAddressLine1().trim().isEmpty()) {
			bean.setLineOne(true);
		}
		if (address.getAddressLine2() == null || address.getAddressLine2().trim().isEmpty()) {
			bean.setLineTwo(true);
		}
		if (address.getAddressLine3() == null || address.getAddressLine3().trim().isEmpty()) {
			bean.setLineThree(true);
		}
		if (address.getAddressLine4() == null || address.getAddressLine4().trim().isEmpty()) {
			bean.setLineFour(true);
		}
		if (address.getTown() == null) {
			bean.setTown(true);
		}
		if (address.getPostcode() == null || address.getPostcode().trim().isEmpty()) {
			bean.setPostCode(true);
		}
		if (address.getMunicipality() == null) {
			bean.setMuncipality(true);
		}
		if (postalAddress) {
			bean.setLongitudeDegrees(false);
			bean.setLatitudeDegrees(false);
		} else {
			// Longitude
			if (address.getLongitudeDegrees() == null || address.getLongitudeMinutes() == null || address.getLongitudeSeconds() == null) {
				bean.setLongitudeDegrees(true);
			}
			// Latitude
			if (address.getLatitudeDegrees() == null || address.getLatitudeMinutes() == null || address.getLatitudeSeconds() == null) {
				bean.setLatitudeDegrees(true);
			}
		}
	}
	
	public void setTrainingProviderRegsitartionExistingAddressWithTryCatch(Address address, AddressUpdateInfoBean bean, boolean postalAddress) {
		try {
			if (address.getAddressLine1() == null || address.getAddressLine1().trim().isEmpty()) {
				bean.setLineOne(true);
			}
			if (address.getAddressLine2() == null || address.getAddressLine2().trim().isEmpty()) {
				bean.setLineTwo(true);
			}
			if (address.getAddressLine3() == null || address.getAddressLine3().trim().isEmpty()) {
				bean.setLineThree(true);
			}
			if (address.getAddressLine4() == null || address.getAddressLine4().trim().isEmpty()) {
				bean.setLineFour(true);
			}
			if (address.getTown() == null) {
				bean.setTown(true);
			}
			if (address.getPostcode() == null || address.getPostcode().trim().isEmpty()) {
				bean.setPostCode(true);
			}
			if (address.getMunicipality() == null) {
				bean.setMuncipality(true);
			}
			if (address.getStatsSaAreaCode() == null) {
				bean.setStatsSaAreaCode(true);
			}
			if (postalAddress) {
				bean.setLongitudeDegrees(false);
				bean.setLatitudeDegrees(false);
			} else {
				// Longitude
				if (address.getLongitudeDegrees() == null || address.getLongitudeMinutes() == null || address.getLongitudeSeconds() == null) {
					bean.setLongitudeDegrees(true);
				}
				// Latitude
				if (address.getLatitudeDegrees() == null || address.getLatitudeMinutes() == null || address.getLatitudeSeconds() == null) {
					bean.setLatitudeDegrees(true);
				}
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
	}
}
