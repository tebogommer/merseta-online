package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.TrainingSiteDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.TrainingSite;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RegionTownService;
import haj.com.validators.exports.services.CompanyValidationService;

public class TrainingSiteService extends AbstractService {

	/** The dao. */
	private TrainingSiteDAO dao = new TrainingSiteDAO();
	/* Instance */
	private static TrainingSiteService trainingSiteService;

	public static synchronized TrainingSiteService instance() {
		if (trainingSiteService == null) {
			trainingSiteService = new TrainingSiteService();
		}
		return trainingSiteService;
	}

	/**
	 * Get all TrainingSite
	 * 
	 * @author TechFinium
	 * @see TrainingSite
	 * @return a list of {@link haj.com.entity.TrainingSite}
	 * @throws Exception
	 *             the exception
	 */
	public List<TrainingSite> allTrainingSite() throws Exception {
		return dao.allTrainingSite();
	}

	/**
	 * Create or update TrainingSite.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingSite
	 */
	public void create(TrainingSite entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update TrainingSite.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingSite
	 */
	public void update(TrainingSite entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete TrainingSite.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingSite
	 */
	public void delete(TrainingSite entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.TrainingSite}
	 * @throws Exception
	 *             the exception
	 * @see TrainingSite
	 */
	public TrainingSite findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find TrainingSite by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.TrainingSite}
	 * @throws Exception
	 *             the exception
	 * @see TrainingSite
	 */
	public List<TrainingSite> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load TrainingSite
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingSite}
	 * @throws Exception
	 *             the exception
	 */
	public List<TrainingSite> allTrainingSite(int first, int pageSize) throws Exception {
		return dao.allTrainingSite(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of TrainingSite for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the TrainingSite
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(TrainingSite.class);
	}

	/**
	 * Lazy load TrainingSite with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            TrainingSite class
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
	 * @return a list of {@link haj.com.entity.TrainingSite} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingSite> allTrainingSite(Class<TrainingSite> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<TrainingSite>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of TrainingSite for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            TrainingSite class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the TrainingSite entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<TrainingSite> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingSite> allTrainingSiteLinkedToCompany(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from TrainingSite o where o.company.id = :companyId";
		filters.put("companyId", companyId);
		return (List<TrainingSite>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingSite> allTrainingSiteLinkedToCompanyResolveRegionData(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from TrainingSite o where o.company.id = :companyId";
		filters.put("companyId", companyId);
		return resolveAddressInformatioAndRegion(
				(List<TrainingSite>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllTrainingSiteLinkedToCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingSite o where o.company.id = :companyId";
		return dao.countWhere(filters, hql);
	}

	public void resolveSiteAddresses(TrainingSite trainingSite) throws Exception {
		if (trainingSite != null) {
			if (trainingSite.getPostalAddress() != null && trainingSite.getPostalAddress().getId() != null) {
				trainingSite
						.setPostalAddress(AddressService.instance().findByKey(trainingSite.getPostalAddress().getId()));
			}
			if (trainingSite.getResidentialAddress() != null && trainingSite.getResidentialAddress().getId() != null) {
				trainingSite.setResidentialAddress(
						AddressService.instance().findByKey(trainingSite.getResidentialAddress().getId()));
			}
		}
	}

	public void resolveTrainingSiteRegion(TrainingSite trainingSite) throws Exception {
		if (trainingSite != null && trainingSite.getResidentialAddress() != null
				&& trainingSite.getResidentialAddress().getId() != null) {
			trainingSite.setResidentialAddress(
					AddressService.instance().findByKey(trainingSite.getResidentialAddress().getId()));
			if (trainingSite.getResidentialAddress().getTown() != null) {
				trainingSite.setRegionTown(
						RegionTownService.instance().findByTown(trainingSite.getResidentialAddress().getTown()));
			}
		}
	}

	public List<TrainingSite> resolveAddressInformatioAndRegion(List<TrainingSite> trainingSiteList) throws Exception {
		for (TrainingSite entity : trainingSiteList) {
			resolveAddressInformatioAndRegion(entity);
		}
		return trainingSiteList;
	}

	public TrainingSite resolveAddressInformatioAndRegion(TrainingSite trainingSite) throws Exception {
		if (trainingSite != null) {
			if (trainingSite.getPostalAddress() != null && trainingSite.getPostalAddress().getId() != null) {
				trainingSite
						.setPostalAddress(AddressService.instance().findByKey(trainingSite.getPostalAddress().getId()));
			}
			if (trainingSite.getResidentialAddress() != null && trainingSite.getResidentialAddress().getId() != null) {
				trainingSite.setResidentialAddress(
						AddressService.instance().findByKey(trainingSite.getResidentialAddress().getId()));
				if (trainingSite.getResidentialAddress().getTown() != null) {
					trainingSite.setRegionTown(RegionTownService.instance().findByTown(trainingSite.getResidentialAddress().getTown()));
				}
			}
		}
		return trainingSite;
	}

	public TrainingSite createUpdateSite(TrainingSite trainingSite) throws Exception {
		if (trainingSite != null) {
			if (trainingSite.getResidentialAddress() != null && trainingSite.getPostalAddress() != null && trainingSite.getPostalAddress().getSameAddress() != null && trainingSite.getPostalAddress().getSameAddress()) {
				AddressService.instance().copyFromToAddress(trainingSite.getResidentialAddress(), trainingSite.getPostalAddress());
				trainingSite.getPostalAddress().setSameAddress(false);
			}
			if (trainingSite.getResidentialAddress() != null) {
				AddressService.instance().create(trainingSite.getResidentialAddress());
				trainingSite.setResidentialAddress(trainingSite.getResidentialAddress());
			}
			if (trainingSite.getPostalAddress() != null) {
				AddressService.instance().create(trainingSite.getPostalAddress());
				trainingSite.setPostalAddress(trainingSite.getPostalAddress());
			}
			create(trainingSite);
			return findByKey(trainingSite.getId());
		} else {
			return null;
		}

	}

	public StringBuilder validiateSiteInformation(TrainingSite site) {
		StringBuilder errors = new StringBuilder("");
		if (site.getSiteName() != null && !site.getSiteName().trim().isEmpty()) {
			if (!CompanyValidationService.instance().companyNameValidation(site.getSiteName())) {
				errors.append("Validation Failed For SETMIS Site Name failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\\\:._,`'-</li></ul>");
				errors.append("<br/>");
			}
		}
		if (site.getResidentialAddress() != null) {
			StringBuilder residentialAddressErrors = new StringBuilder("");
			residentialAddressErrors = AddressService.instance()
					.validiateAddressInformation(site.getResidentialAddress());
			if (!residentialAddressErrors.toString().isEmpty()) {
				errors.append("Residential Address Validation Errors:");
				errors.append("<br/>");
				errors.append(residentialAddressErrors.toString());
				errors.append("<br/>");
			}
		}
		if (site.getPostalAddress() != null) {
			StringBuilder postalAddressErrors = new StringBuilder("");
			postalAddressErrors = AddressService.instance().validiateAddressInformation(site.getPostalAddress());
			if (!postalAddressErrors.toString().isEmpty()) {
				errors.append("Postal Address Validation Errors:");
				errors.append("<br/>");
				errors.append(postalAddressErrors.toString());
				errors.append("<br/>");
			}
		}
		return errors;
	}
}
