package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.SDFCompanyHistoryDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.SDFCompany;
import haj.com.entity.SDFCompanyHistory;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.SDFType;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.SDFTypeService;
import haj.com.service.lookup.SizeOfCompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class SDFCompanyHistoryService.
 */
public class SDFCompanyHistoryService extends AbstractService {
	/** The dao. */
	private SDFCompanyHistoryDAO dao = new SDFCompanyHistoryDAO();

	/** The register service. */
	private RegisterService registerService = new RegisterService();
	private UsersService usersService = new UsersService();
	private SDFTypeService sdfTypeService = new SDFTypeService();

	/** The company service. */
	private CompanyService companyService = new CompanyService();

	
	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();

	public static final Long LABOR_ID = 3l;
	public static final Long EMPLOYEE_ID = 4l;

	
	 private static SDFCompanyHistoryService sdfCompanyHistoryService = null;
		
		/**
		 * Instance.
		 *
		 * @return the SitesHistoryService
		 */
		public static synchronized SDFCompanyHistoryService instance() {
			if (sdfCompanyHistoryService == null) {
				sdfCompanyHistoryService = new SDFCompanyHistoryService();
			}
			return sdfCompanyHistoryService;
		}
	/**
	 * Get all SDFCompanyHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SDFCompanyHistory}
	 * @throws Exception
	 *             the exception
	 * @see SDFCompanyHistory
	 */
	public List<SDFCompanyHistory> allSDFCompanyHistory() throws Exception {
		return dao.allSDFCompanyHistory();
	}
	
	public List<SDFCompanyHistory> allSDFCompanyHistoryWithresolve(Company company) throws Exception {
		return resolveSDFCompanyHistoryList(dao.allSDFCompanyHistoryByCompany(company));
	}
	
	public List<SDFCompanyHistory> resolveSDFCompanyHistoryList(List<SDFCompanyHistory> list) throws Exception{
		for(SDFCompanyHistory sDFCompanyHistory:list) {
			resolveSDFCompanyHistory(sDFCompanyHistory);
		}
		return list;
	}

	private void resolveSDFCompanyHistory(SDFCompanyHistory sDFCompanyHistory) throws Exception{
		sDFCompanyHistory.setSdf(usersService.findByKey(sDFCompanyHistory.getSdf().getId()));
		if(sDFCompanyHistory.getSdfType()!=null && sDFCompanyHistory.getSdfType().getId()!=null) {
			sDFCompanyHistory.setSdfType(sdfTypeService.findByKey(sDFCompanyHistory.getSdfType().getId()));
		}else {
			sDFCompanyHistory.setSdfType(new SDFType());
		}
	}
	/**
	 * Create or update SDFCompanyHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SDFCompanyHistory
	 */
	public void create(SDFCompanyHistory entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update SDFCompanyHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SDFCompanyHistory
	 */
	public void update(IDataEntity entity) throws Exception {
		this.dao.update(entity);
	}

	public void changeDetails(Company entity, Users users) throws Exception {
		companyService.update(entity);
		String desc = "";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.COMPANY_CHANGE_APPROVAL, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
	}

	/**
	 * Delete SDFCompanyHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SDFCompanyHistory
	 */
	public void delete(SDFCompanyHistory entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SDFCompanyHistory}
	 * @throws Exception
	 *             the exception
	 * @see SDFCompanyHistory
	 */
	public SDFCompanyHistory findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SDFCompanyHistory by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SDFCompanyHistory}
	 * @throws Exception
	 *             the exception
	 * @see SDFCompanyHistory
	 */
	public List<SDFCompanyHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SDFCompanyHistory.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SDFCompanyHistory}
	 * @throws Exception
	 *             the exception
	 */
	public List<SDFCompanyHistory> allSDFCompanyHistory(int first, int pageSize) throws Exception {
		return dao.allSDFCompanyHistory(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SDFCompanyHistory for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SDFCompanyHistory
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SDFCompanyHistory.class);
	}

	/**
	 * Lazy load SDFCompanyHistory with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            SDFCompanyHistory class
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
	 * @return a list of {@link haj.com.entity.SDFCompanyHistory} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompanyHistory> allSDFCompanyHistory(Class<SDFCompanyHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveSizeOfCompany((List<SDFCompanyHistory>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));

	}

	/**
	 * Get count of SDFCompanyHistory for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            SDFCompanyHistory class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SDFCompanyHistory entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SDFCompanyHistory> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * All company from SDF.
	 *
	 * @param class1
	 *            the class 1
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
	 * @param formUser
	 *            the form user
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<SDFCompanyHistory> allCompanyFromSDF(Class<SDFCompanyHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		return resolveSizeOfCompany(dao.allCompanyFromSDF(class1, first, pageSize, sortField, sortOrder, filters, formUser));
	}

	/**
	 * All SDF for company.
	 *
	 * @param class1
	 *            the class 1
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
	 * @param company
	 *            the company
	 * @param formUser
	 *            the form user
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<SDFCompanyHistory> allSDFForCompany(Class<SDFCompanyHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company, Users formUser) throws Exception {
		return dao.allSDFForCompany(class1, first, pageSize, sortField, sortOrder, filters, company, formUser);
	}

	/**
	 * All SDF for company count.
	 *
	 * @param filters
	 *            the filters
	 * @param company
	 *            the company
	 * @param formUser
	 *            the form user
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long allSDFForCompanyCount(Map<String, Object> filters, Company company, Users formUser) throws Exception {
		return dao.allSDFForCompanyCount(filters, company, formUser);
	}

	/**
	 * All company from SDF count.
	 *
	 * @param filters
	 *            the filters
	 * @param formUser
	 *            the form user
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long allCompanyFromSDFCount(Map<String, Object> filters, Users formUser) throws Exception {
		return dao.allCompanyFromSDFCount(filters, formUser);
	}

	/**
	 * Creates the secondary SDF.
	 *
	 * @param company
	 *            the company
	 * @param sdf
	 *            the sdf
	 * @param sdfType
	 *            the sdf type
	 * @throws Exception
	 *             the exception
	 */
	public void createSecondarySDF(Company company, Users sdf, SDFType sdfType, boolean newSDF) throws Exception {
		List<IDataEntity> iDataEntities = new ArrayList<>();
		if (newSDF) {
			sdf = registerService.register(sdf, true);
			if (findByCompanyAndUser(company, sdf) != null) {
				throw new Exception("The selected user is already assigned to the company");
			}
			SDFCompanyHistory SDFCompanyHistory = new SDFCompanyHistory(company, sdf, sdfType);
			iDataEntities.add(SDFCompanyHistory);
			CompanyUsers cu = companyUsersService.findByUserAndCompany(company.getId(), sdf.getId());
			if (cu == null) {
				cu = new CompanyUsers(sdf, company);
				cu.setCompanyUserType(ConfigDocProcessEnum.SDF);
				iDataEntities.add(cu);
			}
			dao.createBatch(iDataEntities);
		} else {
			SDFCompanyHistory sc = findByCompanyAndUser(company, sdf);
			if (sc != null) {
				sc.setSdfType(sdfType);
				dao.update(sc);
				dao.update(sdf);
			}
		}
	}
	
	
	/**
	 * Update the SDF.
	 *
	 * @param company
	 *            the company
	 * @param sdf
	 *            the sdf
	 * @param sdfType
	 *            the sdf type
	 * @throws Exception
	 *             the exception
	 */
	public void updateSDF(Company company, Users sdf, SDFType sdfType) throws Exception {
		UsersService usersService = new UsersService();
		SDFCompanyHistory SDFCompanyHistory = findByCompanyAndUser(company, sdf);
		SDFCompanyHistory.setSdfType(sdfType);
		update(SDFCompanyHistory);
		usersService.update(sdf);
	}

	/**
	 * By SDF.
	 *
	 * @param sdfId
	 *            the sdf id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<SDFCompanyHistory> bySDF(Long sdfId) throws Exception {
		return dao.bySDF(sdfId);
	}

	public List<SDFCompanyHistory> bySDFPrimary(Long sdfId) throws Exception {
		return dao.bySDFPrimary(sdfId);
	}

	

	private List<SDFCompanyHistory> resolveSizeOfCompany(List<SDFCompanyHistory> sdfs) throws Exception {
		for (SDFCompanyHistory a : sdfs) {
			a.getCompany().setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(a.getCompany()));
		}
		return sdfs;
	}


	/**
	 * Save.
	 *
	 * @param company
	 *            the company
	 * @throws Exception
	 *             the exception
	 */
	public void save(Company company) throws Exception {
		if ((company.getNonLevyPaying() == null || !company.getNonLevyPaying()) || (company.getNonLevyPaying() != null && company.getNonLevyPaying() && company.getSicCode() != null && !"Unknown".equals(company.getSicCode().getChamber().getCode()))) {
			if (company.getRecognitionAgreement().getId() == HAJConstants.YES_ID) {
				company.setTrainingCommitteeInPlace(true);
			} else if (company.getRecognitionAgreement().getId() == HAJConstants.NO_ID && company.getNumberOfEmployees() != null && company.getNumberOfEmployees() > 49) {
				company.setTrainingCommitteeInPlace(true);
			} else {
				company.setTrainingCommitteeInPlace(false);
			}
		} else {
			company.setTrainingCommitteeInPlace(false);

		}

		AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
		CompanyHistoryService.instance().createHistory(company.getId());
		dao.update(company);
	}



	/**
	 * Find by company and user.
	 *
	 * @param company
	 *            the company
	 * @param user
	 *            the user
	 * @return the SDF company
	 * @throws Exception
	 *             the exception
	 */
	public SDFCompanyHistory findByCompanyAndUser(Company company, Users user) throws Exception {
		if (company == null || user == null) return null;
		return dao.findByCompanyAndUser(company.getId(), user.getId());
	}

	/**
	 * Find by company sign off.
	 *
	 * @param company
	 *            the company
	 * @param signOff
	 *            the sign off
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<SDFCompanyHistory> findByCompanySignOff(Company company, boolean signOff) throws Exception {
		return dao.findByCompanySignOff(company.getId(), signOff);
	}

	public List<SDFCompanyHistory> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company.getId());
	}

	public List<Company> findByUser(Users users, List<Company> companies) throws Exception {
		companies.addAll(dao.findByUser(users));
		companies = companies.stream().distinct().collect(Collectors.toList());
		return companies;
	}

	public List<Company> findByUser(Users users) throws Exception {
		return dao.findByUser(users);
	}

	public long findByCompanyCount(Company company) throws Exception {
		if (company.getId() == null) return 0;
		return dao.findByCompanyCount(company.getId());
	}
	
	public void createHistory(Long sdfCompanyID) throws Exception
	{
		SDFCompanyService sdfCompanyService=new SDFCompanyService();
		SDFCompany sdfCompany=sdfCompanyService.findByKey(sdfCompanyID);
		SDFCompanyHistory history=new SDFCompanyHistory(sdfCompany);
		BeanUtils.copyProperties(history, sdfCompany);
		history.setId(null);
		create(history);
		
	}
	
	public void createHistoryNoFK(Long sdfCompanyID) throws Exception
	{
		SDFCompanyService sdfCompanyService=new SDFCompanyService();
		SDFCompany sdfCompany=sdfCompanyService.findByKey(sdfCompanyID);
		SDFCompanyHistory history=new SDFCompanyHistory();
		BeanUtils.copyProperties(history, sdfCompany);
		history.setId(null);
		create(history);
		
	}
	
	public void deleteBySDF(Long id) throws Exception
	{
		List<SDFCompanyHistory> list = findByForSDFCompanyID(id);
		for(SDFCompanyHistory sdfCompanyHistory : list)
		{
			sdfCompanyHistory.setForSdfCompany(null);
			dao.update(sdfCompanyHistory);
//			dao.delete(sdfCompanyHistory);
		}
	}
	private List<SDFCompanyHistory> findByForSDFCompanyID(Long id) throws Exception {
		return  dao.findByForSDFCompanyID(id);
	}
}
