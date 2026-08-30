package haj.com.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.primefaces.model.SortOrder;

import haj.com.bean.BulkMailBean;
import haj.com.bean.CompanyRegionReportBean;
import haj.com.bean.CompanyStatusVsSarsBean;
import haj.com.bean.CompanyUpdateInfoBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.CompanyDAO;
import haj.com.entity.Address;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.AssessorModeratorCompany;
import haj.com.entity.AssessorModeratorCompanySites;
import haj.com.entity.BankingDetails;
import haj.com.entity.CategorizationData;
import haj.com.entity.Company;
import haj.com.entity.CompanyHistory;
import haj.com.entity.CompanyHostingCompany;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.CompanyUsers;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DgVerification;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.HostingCompany;
import haj.com.entity.InterSetaTransfer;
import haj.com.entity.Municipality;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Province;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.entity.SDFCompany;
import haj.com.entity.SDPCompany;
import haj.com.entity.SDPReAccreditation;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderDocParent;
import haj.com.entity.TrainingProviderLearnership;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.TrainingSite;
import haj.com.entity.UserHostingCompany;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserUnitStandard;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyTypeEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.SdfRegistartionDocEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.Designation;
import haj.com.entity.lookup.LegacyProviderLearnership;
import haj.com.entity.lookup.LegacyProviderQualification;
import haj.com.entity.lookup.LegacyProviderSkillsProgramme;
import haj.com.entity.lookup.LegacyProviderUnitStandard;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.DesignationService;
import haj.com.service.lookup.ProviderStatusService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.RolesService;
import haj.com.service.lookup.SDFTypeService;
import haj.com.service.lookup.SizeOfCompanyService;
import haj.com.service.lookup.TownService;
import haj.com.ui.CompanyInfoUI;
import haj.com.utils.GenericUtility;
import haj.com.validators.exports.services.CompanyValidationService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyService.
 */
public class CompanyService extends AbstractService {
	
	/** The dao. */
	private CompanyDAO dao = new CompanyDAO();

	/** The register service. */
	private RegisterService registerService = new RegisterService();
	private TrainingComitteeService trainingComitteeService = new TrainingComitteeService();

	/** The hosting company service. */
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();

	/** The config doc service. */
	private ConfigDocService configDocService = new ConfigDocService();

	/** The doc service. */
	private DocService docService = new DocService();

	/** The sdf type service. */
	private SDFTypeService sdfTypeService = new SDFTypeService();

	/** The user hosting company service. */
	private UserHostingCompanyService userHostingCompanyService = new UserHostingCompanyService();

	/** The company hosting company service. */
	private CompanyHostingCompanyService companyHostingCompanyService = new CompanyHostingCompanyService();
	private HostingCompanyEmployeesService companyEmployeesService = new HostingCompanyEmployeesService();

	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
	/** The Constant leftJoins. */
	private static final String leftJoins = "left join fetch o.residentialAddress left join fetch o.postalAddress left join fetch o.chamber left join fetch o.sicCode left join fetch o.institutionType";
	private static final String nonSetaCompanyFalse = " o.nonSetaCompany = false ";
	private static final String nonSetaCompanyTrue = " o.nonSetaCompany = true ";

	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();

	UsersService usersService = new UsersService();

	/**
	 * Instantiates a new company service.
	 */
	public CompanyService() {
		super();
	}

	/**
	 * Instantiates a new company service.
	 *
	 * @param auditlog
	 *            the auditlog
	 * @param resourceBundle
	 *            the resource bundle
	 */
	public CompanyService(Map<String, Object> auditlog, ResourceBundle resourceBundle) {
		super(auditlog, resourceBundle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new company service.
	 *
	 * @param auditlog
	 *            the auditlog
	 */
	public CompanyService(Map<String, Object> auditlog) {
		super(auditlog);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new company service.
	 *
	 * @param resourceBundle
	 *            the resource bundle
	 */
	public CompanyService(ResourceBundle resourceBundle) {
		super(resourceBundle);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get all Company.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Company}
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	public List<Company> allCompany() throws Exception {
		return resolveCompanyData(dao.allCompany());
	}

	/**
	 * Create or update Company.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	public void create(Company entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else {
			update(entity);
		}
	}

	/**
	 * Update Company.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	public void update(Company entity) throws Exception {
		// Updating Company Info
		CompanyHistoryService.instance().createHistory(entity.getId());
		this.dao.update(entity);
		// Updating Company Address Info;
		if (entity.getResidentialAddress() != null) {
			AddressService.instance().update(entity.getResidentialAddress());
			if (entity.getPostalAddress() != null) {
				if (entity.getPostalAddress().getSameAddress() != null && !entity.getPostalAddress().getSameAddress()) {
					AddressService.instance().update(entity.getPostalAddress());
				} else {
					Address postalAddress = new Address();
					Long tempID = entity.getPostalAddress().getId();
					BeanUtils.copyProperties(postalAddress, entity.getResidentialAddress());
					postalAddress.setId(tempID);
					AddressService.instance().update(postalAddress);
				}
			}
		}
	}
	
	public void updateIgnoreAddressUpdate(Company entity) throws Exception {
		CompanyHistoryService.instance().createHistory(entity.getId());
		this.dao.update(entity);
	}
	
	public void updateBatch(Company entity) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		// Updating Company Info
		CompanyHistoryService.instance().createHistory(entity.getId());
		updateList.add(entity);
		// Updating Company Address Info;
		if (entity.getResidentialAddress() != null) {
			updateList.add(entity.getResidentialAddress());
			if (entity.getPostalAddress() != null) {
				if (entity.getPostalAddress().getSameAddress() != null && !entity.getPostalAddress().getSameAddress()) {
					updateList.add(entity.getPostalAddress());
				} else {
					Address postalAddress = new Address();
					Long tempID = entity.getPostalAddress().getId();
					BeanUtils.copyProperties(postalAddress, entity.getResidentialAddress());
					postalAddress.setId(tempID);
					updateList.add(postalAddress);
				}
			}
		}
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
	}
	
	/**
	 * Update Company.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	public void updateAndCreateCompanyAndAddress(Company entity) throws Exception {
		// Updating Company Info
		CompanyHistoryService.instance().createCompanyAndAddressHistory(entity.getId());
		
		this.dao.update(entity);
		// Updating Company Address Info;
		if (entity.getResidentialAddress() != null) {
			AddressHistoryService.instance().createHistory(entity.getResidentialAddress().getId());
			AddressService.instance().update(entity.getResidentialAddress());
		}
		
		if (entity.getPostalAddress() != null) {
			if (entity.getPostalAddress().getSameAddress() != null || !entity.getPostalAddress().getSameAddress()) {
				AddressHistoryService.instance().createHistory(entity.getPostalAddress().getId());
				AddressService.instance().update(entity.getPostalAddress());
			} else {
				AddressHistoryService.instance().createHistory(entity.getPostalAddress().getId());
				Address postalAddress = new Address();
				Long tempID = entity.getPostalAddress().getId();
				BeanUtils.copyProperties(postalAddress, entity.getResidentialAddress());
				postalAddress.setId(tempID);
				AddressService.instance().update(postalAddress);

			}
		}
	}
	
	

	public void updateNoHistory(Company entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Company.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	public void delete(Company entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @param long1
	 * @param string
	 * @return a {@link haj.com.entity.Company}
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	public Company findByKeyAndResolveDoc(long id, String targetClass, Long targetKey, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return resolveDocs(resolveCompanyData(dao.findByKey(id)), targetKey, targetClass, configDocProcessEnum);
	}
	
	public Company findByKeyAndOnlyResolveDoc(long id, String targetClass, Long targetKey, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return resolveDocs(dao.findByKey(id), targetKey, targetClass, configDocProcessEnum);
	}

	public Company resolveDocs(Company company, Long targetKey, String targetClass, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		company.setDocs(docService.searchCompanyDocByTargetKeyAndClass(targetClass, targetKey, configDocProcessEnum, company.getId()));
		return company;
	}

	public Company findByKey(long id) throws Exception {
		return resolveCompanyData(dao.findByKey(id));
	}
	
	public Company findByKeyNoResolveData(long id) throws Exception {
		return dao.findByKey(id);
	}

	public Company findByKeyFoReview(long id) throws Exception {
		return resolveContactPerson(dao.findByKey(id));
	}

	public Company resolveContactPerson(Company company) throws Exception {
		CompanyUsersService cus = new CompanyUsersService();
		company.setContactPerson(cus.findCompanyContactPerson(company.getId()));
		resolveCompanyData(company);
		return company;
	}

	public Company findByKey1(long id) throws Exception {
		return (dao.findByKey(id));
	}

	/**
	 * Find by GUID.
	 *
	 * @param guid
	 *            the guid
	 * @return the company
	 * @throws Exception
	 *             the exception
	 */
	public Company findByGUID(String guid) throws Exception {
		return dao.findByGUID(guid.trim());
	}

	/**
	 * Find object by primary key and resolve documents.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Company}
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	public Company findByKeyResolveDoc(long id) throws Exception {
		return resolveDocsCompany(dao.findByKey(id));
	}

	public Integer countByRegNumber(String companyRegNumber) throws Exception {
		return dao.countByRegNumber(companyRegNumber);
	}

	/**
	 * Find Company by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Company}
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	public List<Company> findByName(String desc) throws Exception {
		return resolveCompanyData(dao.findByName(desc));
	}

	/**
	 * Find Company by company name or levy number.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Company}
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	public List<Company> findByNameOrLevyNum(String desc) throws Exception {
		return resolveCompanyData(dao.findByNameOrLevyNum(desc));
	}

	public List<Company> findByActiveNameOrLevyNum(String desc) throws Exception {
		return resolveCompanyData(dao.findByActiveNameOrLevyNum(desc));
	}

	public List<Company> findByLevyNum(String desc) throws Exception {
		return resolveCompanyData(dao.findByLevyNum(desc));
	}

	public Company findByLevyNumber(String levyNumber) throws Exception {
		return resolveCompanyData(dao.findByLevyNumber(levyNumber));
	}
	
	public Company findByLevyNumberNoResolve(String levyNumber) throws Exception {
		return dao.findByLevyNumber(levyNumber);
	}
	
	public Company findByLevyNumberIncludingNonSeta(String levyNumber) throws Exception {
		return dao.findByLevyNumberIncludingNonSeta(levyNumber);
	}
	
	public Company findByLevyNumberWhereStatusNotInList(String levyNumber, List<CompanyStatusEnum> companyStatusList) throws Exception {
		return dao.findByLevyNumberWhereStatusNotInList(levyNumber, companyStatusList);
	}
	
	public List<Company> findByNameOrLevyNum(String desc, Qualification qualification,  ApprovalEnum approvalEnum) throws Exception {
		return resolveCompanyData(dao.findByNameOrLevyNum(desc, qualification,approvalEnum));
	}

	/**
	 * Find Company by company name or registration number.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Company}
	 * @throws Exception
	 *             the exception
	 * @see Company
	 */
	public List<Company> findByNameOrRegNumber(String desc) throws Exception {
		return resolveCompanyData(dao.findByNameOrRegNumber(desc));
	}
	
	public List<Company> findByNameOrRegNumberNoResolve(String desc) throws Exception {
		return dao.findByNameOrRegNumber(desc);
	}

	/**
	 * Lazy load Company.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.Company}
	 * @throws Exception
	 *             the exception
	 */
	public List<Company> allCompany(int first, int pageSize) throws Exception {
		return resolveCompanyData(dao.allCompany(Long.valueOf(first), pageSize));
	}

	/**
	 * Get count of Company for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Company
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Company.class);
	}

	/**
	 * Lazy load Company with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Company class
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
	 * @return a list of {@link haj.com.entity.Company} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Company> allCompany(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Company o " + leftJoins;
		return resolveCompanyData((List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	@SuppressWarnings("unchecked")
	public List<Company> allCompanysLevyNotNull(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveCompanyData((List<Company>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> allCompanysLevyNotNullNoResolveData(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Company>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> allCompaniesByID(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long comapnyId) throws Exception {
		String hql = "select o from Company o where o.id = :id ";
		filters.put("id", comapnyId);
		return resolveCompanyData((List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllCompaniesByID(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Company o where o.id = :id";
		return dao.countWhere(filters,hql);
	}
	
	public int countAllCompanysLevyNotNull(Class<Company> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * test Method
	 * 
	 * @param class1
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Company> allCompanyByWspYearForDg(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear) throws Exception {
		String hql = "select o.wsp.company from DgVerification o where o.wsp.finYear = :FinYear";
		filters.put("FinYear", finYear);
		return resolveCompanyData((List<Company>) dao.sortAndFilterWhereDG(class1, first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllCompanyByWspYearForDg(Class<Company> entity, Map<String, Object> filters) throws Exception {
		// String hql = "select count(o) from Company o where o.id in (select
		// x.wsp.company.id from DgVerification x where x.wsp.finYear =
		// :FinYear)";
		String hql = "select count(o.wsp.company) from DgVerification o where o.wsp.finYear = :FinYear";
		return dao.countWhereDG(entity, filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Company> allCompanyByCloCrmByDgFinYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users u, Integer finYear) throws Exception {
		// String hql = "select o from Company o left join fetch
		// o.residentialAddress
		// left join fetch o.postalAddress "
		// + " where "
		// + " o.id in (select x.wsp.company.id from DgVerification x where
		// x.wsp.finYear = :FinYear) "
		// + " and "
		// + " o.residentialAddress.town.id in (select x.town.id from RegionTown
		// x where
		// x.clo.users.id = :userID or x.crm.users.id = :userID) ";
		String hql = " select o.wsp.company from DgVerification o " + " where " + " o.wsp.finYear = :FinYear " + " and " + " o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		filters.put("userID", u.getId());
		filters.put("FinYear", finYear);
		return resolveCompanyData((List<Company>) dao.sortAndFilterWhereDG(Company.class, first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countRegionDgFinYear(Class<Company> entity, Map<String, Object> filters) throws Exception {
		// String hql = "select count(o) from Company o "
		// + " where "
		// + " o.id in (select x.wsp.company.id from DgVerification x where
		// x.wsp.finYear = :FinYear) "
		// + " and "
		// + " o.residentialAddress.town.id in (select x.town.id from RegionTown
		// x where
		// x.clo.users.id = :userID or x.crm.users.id = :userID) ";
		String hql = " select count(o.wsp.company) from DgVerification o " + " where " + " o.wsp.finYear = :FinYear " + " and " + " o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		return dao.countWhereDG(entity, filters, hql);
	}

	/**
	 * Lazy load Company with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Company class
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
	 * @return a list of {@link haj.com.entity.Company} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Company> allCompany2(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveCompanyData((List<Company>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> allCompanyNoDataResolved(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Company>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	@SuppressWarnings("unchecked")
	public List<Company> allCompanySetaCompanies(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Company o where " + nonSetaCompanyFalse; 
		return resolveCompanyData((List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllCompanySetaCompaniesn( Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Company o where " + nonSetaCompanyFalse; 
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> allCompanySetaCompaniesNew(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Company o where " + nonSetaCompanyFalse; 
		return (List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countSetaCompaniesNew(Class<Company> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Company o where " + nonSetaCompanyFalse;
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Company> allCompanyByCloCrm(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users u) throws Exception {
		// String hql = "select o from Company o where
		// o.residentialAddress.town.id in
		// (select x.town.id from RegionTown x where x.crm.users.id = :userID or
		// x.clo.users.id = :userID)";
		String hql = "select o from Company o left join fetch o.residentialAddress left join fetch o.postalAddress where o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID) and " + nonSetaCompanyFalse + " ";
		filters.put("userID", u.getId());
		return resolveCompanyData((List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> allCompanyByCloCrmNoResolveData(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users u) throws Exception {
		String hql = "select o from Company o left join fetch o.residentialAddress left join fetch o.postalAddress where o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID) and " + nonSetaCompanyFalse + " ";
		filters.put("userID", u.getId());
		return (List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Company> allCompanyRegion(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users user) throws Exception {
		String hql = "select o from Company o left join fetch o.residentialAddress left join fetch o.postalAddress where o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID) and " + nonSetaCompanyFalse + " ";
		filters.put("userID", user.getId());
		return resolveCompanyData((List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countRegion(Class<Company> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Company o where o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID) and " + nonSetaCompanyFalse + " ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Company> allCompanyRegionForUser(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users user) throws Exception {
		String hql = "select o from Company o left join fetch o.residentialAddress left join fetch o.postalAddress where o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID) and " + nonSetaCompanyFalse + " ";
		filters.put("userID", user.getId());
		return resolveCompanyData((List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countRegionForUser(Class<Company> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Company o where o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID) and " + nonSetaCompanyFalse + " ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Company> allCompanyRegions(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Company o left join fetch o.residentialAddress left join fetch o.postalAddress where o.residentialAddress.town.id in (select x.town.id from RegionTown x) and " + nonSetaCompanyFalse + " ";

		return resolveCompanyData((List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countRegions(Class<Company> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Company o where o.residentialAddress.town.id in (select x.town.id from RegionTown x) and " + nonSetaCompanyFalse + " ";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Lazy load all companies by region
	 * 
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param region
	 * @return List<Company>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Company> allCompaniesByRegion(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Region region) throws Exception {
		String hql = "select o from Company o left join fetch o.residentialAddress left join fetch o.postalAddress where o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId) and " + nonSetaCompanyFalse + " ";
		filters.put("regionId", region.getId());
		return resolveCompanyData((List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	/**
	 * Count all companies by region
	 * 
	 * @param entity
	 * @param filters
	 * @return int
	 * @throws Exception
	 */
	public int countAllCompaniesByRegion(Class<Company> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Company o where o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId) and " + nonSetaCompanyFalse + " ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> allCompaniesWhereUserLinkAsSdp(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long userId) throws Exception {
		String hql = "select o from Company o where o.id in (select x.company.id from SDPCompany x where x.sdp.id = :userId) ";
		filters.put("userId", userId);
		return (List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllCompaniesWhereUserLinkAsSdp(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Company o where o.id in (select x.company.id from SDPCompany x where x.sdp.id = :userId) ";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Lazy load Company with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Company class
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
	 * @return a list of {@link haj.com.entity.Company} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Company> allCompanyByStatusThaIsNotActive(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "";
		String joins = "left join fetch o.residentialAddress left join fetch o.postalAddress";
		if (filters.containsKey("companyStatus")) {
			String searchValue = (String.valueOf(filters.get("companyStatus")).replace("%", ""));
			if (!searchValue.isEmpty()) {
				if (searchValue.toLowerCase().startsWith(CompanyStatusEnum.PendingChangeApproval.toString().toLowerCase().substring(0, searchValue.length() - Math.max(0, searchValue.length() - CompanyStatusEnum.Active.toString().length())))) {
					if (searchValue.length() <= CompanyStatusEnum.Pending.toString().length()) {
						filters.put("companyStatus", CompanyStatusEnum.Pending);
					} else {
						filters.put("companyStatus", CompanyStatusEnum.PendingChangeApproval);
					}
				} else if (searchValue.toLowerCase().startsWith(CompanyStatusEnum.InActive.toString().toLowerCase().substring(0, searchValue.length() - Math.max(0, searchValue.length() - CompanyStatusEnum.Active.toString().length())))) {
					filters.put("companyStatus", CompanyStatusEnum.InActive);
				} else if (searchValue.toLowerCase().startsWith(CompanyStatusEnum.Rejected.toString().toLowerCase().substring(0, searchValue.length() - Math.max(0, searchValue.length() - CompanyStatusEnum.Active.toString().length())))) {
					filters.put("companyStatus", CompanyStatusEnum.Rejected);
				} else if (searchValue.toLowerCase().startsWith(CompanyStatusEnum.Approved.toString().toLowerCase().substring(0, searchValue.length() - Math.max(0, searchValue.length() - CompanyStatusEnum.Active.toString().length())))) {
					filters.put("companyStatus", CompanyStatusEnum.Approved);
				} else if (searchValue.toLowerCase().startsWith(CompanyStatusEnum.Active.toString().toLowerCase().substring(0, searchValue.length() - Math.max(0, searchValue.length() - CompanyStatusEnum.Active.toString().length())))) {
					filters.put("companyStatus", CompanyStatusEnum.Active);
				} else {
					filters.put("companyStatus", null);
				}

				hql = "select o from Company o " + joins + " where o.companyStatus =:companyStatus and " + nonSetaCompanyFalse + " ";
			} else {
				filters.put("companyStatus", CompanyStatusEnum.Active);
				hql = "select o from Company o " + joins + " where o.companyStatus <>:companyStatus and " + nonSetaCompanyFalse + " ";
			}
		} else {
			filters.put("companyStatus", CompanyStatusEnum.Active);
			hql = "select o from Company o " + joins + " where o.companyStatus <>:companyStatus and " + nonSetaCompanyFalse + " ";
		}

		return (List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	/**
	 * All company for learner reg.
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
	 * @param parameters
	 *            the parameters
	 * @param whereHql
	 *            the where hql
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<Company> allCompanyForLearnerReg(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters, String whereHql) throws Exception {
		String hql = "select o from Company o " + leftJoins + " " + whereHql;
		return resolveCompanyData((List<Company>) dao.hqlAndParamOnly(class1, first, pageSize, sortField, sortOrder, parameters, hql));
	}

	/**
	 * Lazy load Company with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Company class
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
	 * @return a list of {@link haj.com.entity.Company} containing data
	 * @throws Exception
	 *             the exception
	 */
	public List<Company> allCompany(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		return resolveCompanyData(dao.allCompany(class1, first, pageSize, sortField, sortOrder, filters, formUser));
	}

	/**
	 * All company count.
	 *
	 * @param formUser
	 *            the form user
	 * @return the int
	 * @throws Exception
	 *             the exception
	 */
	public int allCompanyCount(Users formUser) throws Exception {
		return ((Long) dao.allCompanyCount(formUser)).intValue();
	}

	/**
	 * Get count of Company for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            Company class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the Company entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<Company> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public int countSetaCompanies(Class<Company> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Company o where " + nonSetaCompanyFalse;
		return dao.countWhere(filters, hql);
	}

	/**
	 * Creates the company and send task.
	 *
	 * @param entity
	 *            the entity
	 * @param users
	 *            the users
	 * @param createTask
	 *            the create task
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @param hostingCompany
	 *            the hosting company
	 * @throws Exception
	 *             the exception
	 */
	private void createCompanyAndSendTask(Company entity, Users users, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany) throws Exception {
		String desc = "";

		CompanyHistoryService.instance().createHistory(entity.getId());

		entity.setCompanyStatus(CompanyStatusEnum.Pending);

		if (entity.getId() == null) {
			desc = getEntryLanguage(configDocProcessEnum.getTaskDescription());
			this.dao.create(entity);

		} else {
			desc = getEntryLanguage(configDocProcessEnum.getTaskDescription());
			this.dao.update(entity);
		}

		// Check if SDF Config Process
		if (configDocProcessEnum == ConfigDocProcessEnum.SDF) {
			// Links SDF user to company

			SDFCompany sdfc = findByCompanyAndUser(entity, users);
			if (sdfc == null) {
				sdfc = new SDFCompany(entity, users);
				sdfc.setSdfType(sdfTypeService.findByKey(1l));
				this.dao.create(sdfc);
			} else {
				SDFCompany priSdf = dao.findPrimarySDF(entity.getId());
				if (priSdf == null) {
					sdfc.setSdfType(sdfTypeService.findByKey(1l));
					this.dao.update(sdfc);
				}
			}
			if (createTask) {
				String extraInfo = "";
				TasksService.instance().findFirstInProcessAndCreateTask(desc, sdfc.getSdf(), sdfc.getId(), sdfc.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
			}
		}

		// Check if Assessor Moderator Config Process
		if (configDocProcessEnum == ConfigDocProcessEnum.AM) {
			// Links Assessor Moderator user to company
			AssessorModeratorCompany amCompany = new AssessorModeratorCompany(entity, users);
			this.dao.create(amCompany);
		}

		// now do documents
		List<IDataEntity> dataEntities = new ArrayList<>();

		if (users != null) {
			CompanyUsers companyUsers = null;
			if (configDocProcessEnum == ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {

				companyUsers = new CompanyUsers(users, entity, ConfigDocProcessEnum.TP);
				if (users.getDesignation() == null) {
					companyUsers.setDesignation(users.getDesignation());
				} else {
					DesignationService designationService = new DesignationService();
					Designation designation = designationService.findByCode("PRI_SDP");
					companyUsers.setDesignation(designation);
				}

			} else if (configDocProcessEnum == ConfigDocProcessEnum.TP) {

				companyUsers = new CompanyUsers(users, entity, configDocProcessEnum);
				if (users.getDesignation() == null) {
					companyUsers.setDesignation(users.getDesignation());
				} else {
					DesignationService designationService = new DesignationService();
					Designation designation = designationService.findByCode("PRI_SDP");
					companyUsers.setDesignation(designation);
				}

			} else {
				companyUsers = new CompanyUsers(users, entity, configDocProcessEnum);
			}
			dataEntities.add(companyUsers);
			if (userHostingCompanyService.findCountByUserAndHoustingCompany(hostingCompany, users) == 0) {
				UserHostingCompany uhc = new UserHostingCompany(hostingCompany, users);
				dataEntities.add(uhc);
			}
			if (companyHostingCompanyService.findCountByCompanyAndHoustingCompany(hostingCompany, entity) == 0) {
				CompanyHostingCompany chc = new CompanyHostingCompany(hostingCompany, entity);
				dataEntities.add(chc);
			}
		}

		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				doc.setCompany(entity);
				doc.setVersionNo(1);
				doc.setUsers(users);

				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, users, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		this.dao.createBatch(dataEntities);

		if (createTask && configDocProcessEnum != ConfigDocProcessEnum.SDF) {
			if (configDocProcessEnum == ConfigDocProcessEnum.TP) {

				HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
				TownService townService = new TownService();
				List<Users> qualityAssurorUsers = new ArrayList<>();
				Address address = null;
				if (entity.getResidentialAddress().getId() != null) {
					address = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
				}
				if (address != null && address.getTown() != null) {
					qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
				}

				if (qualityAssurorUsers.size() == 0) {
					TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
				} else {
					TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), qualityAssurorUsers);
				}
				hces = null;
				townService = null;
			} else if (configDocProcessEnum == ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
				TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			} else {
				TasksService.instance().findFirstInProcessAndCreateTask(desc, users, users.getId(), users.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			}
		}
	}

	/**
	 * Creates the company and send task.
	 *
	 * @param entity
	 *            the entity
	 * @param users
	 *            the users
	 * @param createTask
	 *            the create task
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @param hostingCompany
	 *            the hosting company
	 * @throws Exception
	 *             the exception
	 */
	private void createTPCompanyAndSendTask(Company entity, Users users, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany, TrainingProviderApplication trainingProviderApplication) throws Exception {
		String desc = "";

		CompanyHistoryService.instance().createHistory(entity.getId());
		entity.setCompanyStatus(CompanyStatusEnum.Pending);
		if (entity.getId() == null) {
			desc = getEntryLanguage(configDocProcessEnum.getTaskDescription());
			this.dao.create(entity);
		} else {
			desc = getEntryLanguage(configDocProcessEnum.getTaskDescription());
			this.dao.update(entity);
		}
		List<IDataEntity> dataEntities = new ArrayList<>();
		if (users != null) {
			CompanyUsers companyUsers = null;
			if (configDocProcessEnum == ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
				companyUsers = new CompanyUsers(users, entity, ConfigDocProcessEnum.TP);
				if (users.getDesignation() == null) {
					companyUsers.setDesignation(users.getDesignation());
				} else {
					DesignationService designationService = new DesignationService();
					Designation designation = designationService.findByCode("PRI_SDP");
					companyUsers.setDesignation(designation);
				}

			} else if (configDocProcessEnum == ConfigDocProcessEnum.TP) {

				companyUsers = new CompanyUsers(users, entity, configDocProcessEnum);
				if (users.getDesignation() == null) {
					companyUsers.setDesignation(users.getDesignation());
				} else {
					DesignationService designationService = new DesignationService();
					Designation designation = designationService.findByCode("PRI_SDP");
					companyUsers.setDesignation(designation);
				}

			} else {
				companyUsers = new CompanyUsers(users, entity, configDocProcessEnum);
			}
			dataEntities.add(companyUsers);
			
			if (userHostingCompanyService.findCountByUserAndHoustingCompany(hostingCompany, users) == 0) {
				UserHostingCompany uhc = new UserHostingCompany(hostingCompany, users);
				dataEntities.add(uhc);
			}
			if (companyHostingCompanyService.findCountByCompanyAndHoustingCompany(hostingCompany, entity) == 0) {
				CompanyHostingCompany chc = new CompanyHostingCompany(hostingCompany, entity);
				dataEntities.add(chc);
			}
		}

		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				doc.setCompany(entity);
				doc.setVersionNo(1);
				doc.setUsers(users);
				doc.setTargetClass(trainingProviderApplication.getClass().getName());
				doc.setTargetKey(trainingProviderApplication.getId());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, users, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		this.dao.createBatch(dataEntities);

		if (createTask) {
			if (configDocProcessEnum == ConfigDocProcessEnum.TP) {
				HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
				TownService townService = new TownService();
				List<Users> qualityAssurorUsers = new ArrayList<>();
				Address address = null;
				if(trainingProviderApplication != null && trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null){
					if (trainingProviderApplication.getTrainingSite().getResidentialAddress().getId() != null) {
						address = AddressService.instance().findByKey(trainingProviderApplication.getTrainingSite().getResidentialAddress().getId());
					}
				}else {
					if (entity.getResidentialAddress().getId() != null) {
						address = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
					}
				}
				
				if (address != null && address.getTown() != null) {
					qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
				}
				if (qualityAssurorUsers.size() == 0) {
					TasksService.instance().findFirstInProcessAndCreateTask(desc, users, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
				} else {
					TasksService.instance().findFirstInProcessAndCreateTask(desc, users, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), qualityAssurorUsers);
				}
				hces = null;
				townService = null;
				/*if (qualityAssurorUsers.size() == 0) {
					TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
				} else {
					TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), qualityAssurorUsers);
				}*/
			} else if (configDocProcessEnum == ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
				HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
				TownService townService = new TownService();
				List<Users> qualityAssurorUsers = new ArrayList<>();
				Address address = null;
				if (entity.getResidentialAddress().getId() != null) {
					address = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
				}
				if (address != null && address.getTown() != null) {
					qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
				}
				TasksService.instance().findFirstInProcessAndCreateTask(desc, users, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), qualityAssurorUsers);
				//TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), qualityAssurorUsers);
			} else {
				TasksService.instance().findFirstInProcessAndCreateTask(desc, users, users.getId(), users.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			}
		}
	}
	
	private void createTPCompanyAndSendTaskVersionTwo(Company entity, Users users, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany, TrainingProviderApplication trainingProviderApplication) throws Exception {
		String desc = "";
		desc = getEntryLanguage(configDocProcessEnum.getTaskDescription());
		List<IDataEntity> dataEntities = new ArrayList<>();
		if (users != null) {
			CompanyUsers companyUsers = null;
			if (configDocProcessEnum == ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
				companyUsers = new CompanyUsers(users, entity, ConfigDocProcessEnum.TP);
				if (users.getDesignation() == null) {
					companyUsers.setDesignation(users.getDesignation());
				} else {
					DesignationService designationService = new DesignationService();
					Designation designation = designationService.findByCode("PRI_SDP");
					companyUsers.setDesignation(designation);
				}
			} else if (configDocProcessEnum == ConfigDocProcessEnum.TP) {

				companyUsers = new CompanyUsers(users, entity, configDocProcessEnum);
				if (users.getDesignation() == null) {
					companyUsers.setDesignation(users.getDesignation());
				} else {
					DesignationService designationService = new DesignationService();
					Designation designation = designationService.findByCode("PRI_SDP");
					companyUsers.setDesignation(designation);
				}

			} else {
				companyUsers = new CompanyUsers(users, entity, configDocProcessEnum);
			}
			dataEntities.add(companyUsers);
			
			if (userHostingCompanyService.findCountByUserAndHoustingCompany(hostingCompany, users) == 0) {
				UserHostingCompany uhc = new UserHostingCompany(hostingCompany, users);
				dataEntities.add(uhc);
			}
			if (companyHostingCompanyService.findCountByCompanyAndHoustingCompany(hostingCompany, entity) == 0) {
				CompanyHostingCompany chc = new CompanyHostingCompany(hostingCompany, entity);
				dataEntities.add(chc);
			}
		}

		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				doc.setCompany(entity);
				doc.setVersionNo(1);
				doc.setUsers(users);
				doc.setTargetClass(trainingProviderApplication.getClass().getName());
				doc.setTargetKey(trainingProviderApplication.getId());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, users, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		this.dao.createBatch(dataEntities);

		if (createTask) {
			if (configDocProcessEnum == ConfigDocProcessEnum.TP) {
				HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
				TownService townService = new TownService();
				List<Users> qualityAssurorUsers = new ArrayList<>();
				Address address = null;
				
				if (trainingProviderApplication != null ) {
					if (trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
						if (trainingProviderApplication.getTrainingSite().getResidentialAddress() != null && trainingProviderApplication.getTrainingSite().getResidentialAddress().getId() != null) {
							address = AddressService.instance().findByKey(trainingProviderApplication.getTrainingSite().getResidentialAddress().getId());
						} else {
							if (entity.getResidentialAddress().getId() != null) {
								address = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
							}
						}
					} else {
						if (entity.getResidentialAddress().getId() != null) {
							address = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
						}
					}
				} else {
					if (entity.getResidentialAddress().getId() != null) {
						address = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
					}
				}
				if (address != null && address.getTown() != null) {
					qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
				}
				if (qualityAssurorUsers.size() == 0) {
					TasksService.instance().findFirstInProcessAndCreateTask(desc, users, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
				} else {
					TasksService.instance().findFirstInProcessAndCreateTask(desc, users, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), qualityAssurorUsers);
				}
				hces = null;
				townService = null;
				/*if (qualityAssurorUsers.size() == 0) {
					TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
				} else {
					TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), qualityAssurorUsers);
				}*/
			} else if (configDocProcessEnum == ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
				HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
				TownService townService = new TownService();
				List<Users> qualityAssurorUsers = new ArrayList<>();
				Address address = null;
				if (trainingProviderApplication != null) {
					if (trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
						if (trainingProviderApplication.getTrainingSite().getResidentialAddress() != null && trainingProviderApplication.getTrainingSite().getResidentialAddress().getId() != null) {
							address = AddressService.instance().findByKey(trainingProviderApplication.getTrainingSite().getResidentialAddress().getId());
						} else {
							if (entity.getResidentialAddress().getId() != null) {
								address = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
							}
						}
					} else {
						if (entity.getResidentialAddress().getId() != null) {
							address = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
						}
					}
				} else {
					if (entity.getResidentialAddress().getId() != null) {
						address = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
					}
				}
				if (address != null && address.getTown() != null) {
					qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
				}
				TasksService.instance().findFirstInProcessAndCreateTask(desc, users, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), qualityAssurorUsers);
				//TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), qualityAssurorUsers);
			} else {
				TasksService.instance().findFirstInProcessAndCreateTask(desc, users, users.getId(), users.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			}
		}
	}

	// Transfer OUt
	private void sendTaskOut(Company entity, Users users, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany) throws Exception {
		String desc = "";

		entity.setCompanyStatus(CompanyStatusEnum.Pending);

		if (entity.getId() == null) {
			desc = getEntryLanguage(configDocProcessEnum.getTaskDescription());
			this.dao.create(entity);

		} else {
			desc = getEntryLanguage(configDocProcessEnum.getTaskDescription());
			this.dao.update(entity);
		}

		List<IDataEntity> dataEntities = new ArrayList<>();

		if (users != null) {

			if (userHostingCompanyService.findCountByUserAndHoustingCompany(hostingCompany, users) == 0) {
				UserHostingCompany uhc = new UserHostingCompany(hostingCompany, users);
				dataEntities.add(uhc);
			}
			if (companyHostingCompanyService.findCountByCompanyAndHoustingCompany(hostingCompany, entity) == 0) {
				CompanyHostingCompany chc = new CompanyHostingCompany(hostingCompany, entity);
				dataEntities.add(chc);
			}
		}

		this.dao.createBatch(dataEntities);

		if (createTask && configDocProcessEnum != ConfigDocProcessEnum.SDF) {
			if (configDocProcessEnum == ConfigDocProcessEnum.TP) {

				HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
				TownService townService = new TownService();
				List<Users> qualityAssurorUsers = new ArrayList<>();
				Address address = null;
				if (entity.getResidentialAddress().getId() != null) {
					address = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
				}
				if (address != null && address.getTown() != null) {
					qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
				}

				if (qualityAssurorUsers.size() == 0) {
					TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
				} else {
					TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), qualityAssurorUsers);
				}
				hces = null;
				townService = null;
			} else if (configDocProcessEnum == ConfigDocProcessEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
				TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			} else {
				TasksService.instance().findFirstInProcessAndCreateTask(desc, users, users.getId(), users.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			}
		}
	}

	/**
	 * Creates the company and send task.
	 *
	 * @param entity
	 *            the entity
	 * @param formUser
	 *            the form user
	 * @param createTask
	 *            the create task
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @param hostingCompany
	 *            the hosting company
	 * @param unitStandards
	 *            the unit standards
	 * @param qualifications
	 *            the qualifications
	 * @throws Exception
	 *             the exception
	 */
	public void createCompanyAndSendTask(List<Company> entity, Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany, List<UnitStandards> unitStandards, List<Qualification> qualifications) throws Exception {
		if (entity.size() > 0) {
			// register check
			preRegisterChecks(entity, formUser);

			if (configDocProcessEnum != ConfigDocProcessEnum.Learner && formUser != null) {
				preUserRegisterChecks(formUser);
			}

			// registers user
			if (formUser != null && formUser.getId() == null) {
				formUser = registerService.register(formUser, true);
			}

			if (companyEmployeesService.findByUserCount(formUser.getId(), HAJConstants.HOSTING_MERSETA) > 0) throw new Exception("Employees cannot register companies or register as an SDF.");

			for (Company company : entity) {
				company.setLocked(true);
				// if config doc type SDF or Assessor Moderator it wont generate
				// a task
				if (configDocProcessEnum == ConfigDocProcessEnum.AM) {
					createCompanyAndSendTask(company, formUser, false, configDocProcessEnum, hostingCompany);
				} else {
					createCompanyAndSendTask(company, formUser, createTask, configDocProcessEnum, hostingCompany);
				}
				if (company.isAddToTrainingComittee()) {
					trainingComitteeService.copyUser(formUser, company);
				}
			}

			// Check if Assessor Moderator Config Process
			if (configDocProcessEnum != null && configDocProcessEnum == ConfigDocProcessEnum.AM) {
				// links Assessor Moderator to qualifications and unit standards
				linkUserOrCompanyToQualificationUnitStandards(unitStandards, qualifications, formUser, null, configDocProcessEnum);
			}

			// Creates documents assigned to user
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			if (formUser.getDocs() != null) {
				for (Doc doc : formUser.getDocs()) {
					doc.setCompany(null);
					doc.setVersionNo(1);
					doc.setForUsers(formUser);
					doc.setUsers(formUser);
					dataEntities.add(doc);
					dataEntities.add(new DocByte(doc.getData(), doc));
					dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}

			if (dataEntities.size() > 0) {
				dao.createBatch(dataEntities);
			}

			// check if task generation is true
			if (createTask) {
				// Check if Assessor Moderator Config Process or SDF Config
				// Process
				if (configDocProcessEnum == ConfigDocProcessEnum.AM) {
					String desc = getEntryLanguage(configDocProcessEnum.getTaskDescription());
					String extraInfo = "";
					if (configDocProcessEnum == ConfigDocProcessEnum.SDF) extraInfo = " SDF Name: " + formUser.getFirstName() + " " + formUser.getLastName() + ((formUser.getRsaIDNumber() != null && !formUser.getRsaIDNumber().isEmpty()) ? ". Identity Number: " + formUser.getRsaIDNumber() : ". Passport Number: " + formUser.getPassportNumber()) + ".";

					TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, formUser.getId(), formUser.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
				}
			}

		} else {
			throw new ValidationException("company.registration.list.validation.error");
		}
	}

	public void createCompanyAndSendTask(int position, Company company, Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		if (formUser != null) formUser = registerService.register(formUser, true);
		if (companyEmployeesService.findByUserCount(formUser.getId(), HAJConstants.HOSTING_MERSETA) > 0) throw new Exception("Employees cannot register companies or register as an SDF.");
		company.setLocked(true);
		createCompanyAndSendTask(company, formUser, false, configDocProcessEnum, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA));
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		if (formUser.getDocs() != null) {
			for (Doc doc : formUser.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				doc.setForUsers(formUser);
				doc.setUsers(formUser);
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}
		if (createTask) {
			SDFCompany sdfCompany = findByCompanyAndUser(company, formUser);
			String desc = "You are an approved SDF. Please login and complete the company information for the new company: " + company.getCompanyName() + " (" + company.getLevyNumber() + ")";
			String extraInfo = "";
			// if (configDocProcessEnum == ConfigDocProcessEnum.SDF)
			// extraInfo = " SDF Name: " + formUser.getFirstName() +
			// formUser.getLastName();
			TasksService.instance().findByPositionAndCreateTask(position, desc, formUser, sdfCompany.getId(), sdfCompany.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), configDocProcessEnum, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), extraInfo, false);
		}
	}

	public void createCompanyAndSendTask(InterSetaTransfer interSetaTransfer, Company comp, Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany, List<UnitStandards> unitStandards, List<Qualification> qualifications) throws Exception {

		List<Doc> userDocs = formUser.getDocs();
		List<Company> entity = new ArrayList<>();
		entity.add(comp);

		if (entity.size() > 0) {

			// register check
			preRegisterChecks(entity, formUser);
			preUserRegisterChecks(formUser);

			// registers user
			if (formUser != null) {
				formUser = registerService.register(formUser, true);
			}

			createCompanyAndSendTask(comp, formUser, false, configDocProcessEnum, hostingCompany);
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			if (userDocs != null) {
				for (Doc doc : userDocs) {
					doc.setCompany(null);
					doc.setVersionNo(1);
					doc.setForUsers(formUser);
					doc.setUsers(formUser);
					dataEntities.add(doc);
					dataEntities.add(new DocByte(doc.getData(), doc));
					dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}
			interSetaTransfer.setCompany(comp);
			interSetaTransfer.setUsers(formUser);
			dataEntities.add(interSetaTransfer);

			if (dataEntities.size() > 0) {
				dao.createBatch(dataEntities);
			}

			if (createTask) {
				String desc = getEntryLanguage(configDocProcessEnum.getTaskDescription());
				TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, interSetaTransfer.getId(), interSetaTransfer.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
			}

		} else {
			throw new ValidationException("company.registration.list.validation.error");
		}
	}

	public void sendTaskOut(InterSetaTransfer interSetaTransfer, Company comp, Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany, List<UnitStandards> unitStandards, List<Qualification> qualifications) throws Exception {

		sendTaskOut(comp, formUser, false, configDocProcessEnum, hostingCompany);
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();

		interSetaTransfer.setCompany(comp);
		interSetaTransfer.setUsers(formUser);
		dataEntities.add(interSetaTransfer);

		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}

		if (createTask) {
			String desc = getEntryLanguage(configDocProcessEnum.getTaskDescription());
			TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, interSetaTransfer.getId(), interSetaTransfer.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		}
	}

	/**
	 * Creates the company and send task.
	 *
	 * @param unitStandardsList
	 *            the unit standards list
	 * @param qualificationsList
	 *            the qualifications list
	 * @param company
	 *            the company
	 * @param formUser
	 *            the form user
	 * @param createTask
	 *            the create task
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @param hostingCompany
	 *            the hosting company
	 * @throws Exception
	 *             the exception
	 */
	public void createCompanyAndSendTask(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList, Company company, Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany) throws Exception {
		if (qualificationsList.size() > 0 || unitStandardsList.size() > 0) {
			List<Company> companies = new ArrayList<>();
			companies.add(company);
			preRegisterChecks(companies, formUser);
			preUserRegisterChecks(formUser);
			formUser = registerService.register(formUser, true);

			createCompanyAndSendTask(company, formUser, createTask, configDocProcessEnum, hostingCompany);

			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			if (formUser.getDocs() != null) {
				for (Doc doc : formUser.getDocs()) {
					doc.setCompany(null);
					doc.setVersionNo(1);
					doc.setForUsers(formUser);
					doc.setUsers(formUser);
					dataEntities.add(doc);
					dataEntities.add(new DocByte(doc.getData(), doc));
					dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}

			if (dataEntities.size() > 0) {
				dao.createBatch(dataEntities);
			}
			// creates link to company qualifications and unit standards
			linkUserOrCompanyToQualificationUnitStandards(unitStandardsList, qualificationsList, formUser, company, configDocProcessEnum);
			// auditorMonitorReviewService.prepNewReview(Company.class.getName(),
			// company.getId(), configDocProcessEnum);
		} else {
			throw new ValidationException("company.registration.list.validation.error");
		}
	}

	/**
	 * Creates the company and send task.
	 *
	 * @param unitStandardsList
	 *            the unit standards list
	 * @param qualificationsList
	 *            the qualifications list
	 * @param company
	 *            the company
	 * @param formUser
	 *            the form user
	 * @param createTask
	 *            the create task
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @param hostingCompany
	 *            the hosting company
	 * @param contactPersonList
	 * @param skillsProgramList
	 * @param skillsSetList
	 * @param sitesSmes
	 * @throws Exception
	 *             the exception
	 */
	public void createTPAndCompanyAndSendTask(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList, Company company, Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany, TrainingProviderApplication trainingProviderApplication, Address tpResidentialAddress, Address tpPostalAddress, ArrayList<Users> contactPersonList, ArrayList<SkillsProgram> skillsProgramList, ArrayList<SkillsSet> skillsSetList) throws Exception {

		if (qualificationsList.size() > 0 || unitStandardsList.size() > 0 || skillsProgramList.size() > 0 || skillsSetList.size() > 0) {
			
			//Checking if there is a Quality Assuror
			if(company.getResidentialAddress()==null){
				//tpResidentialAddress.setAddessInfoLocked(false);
				company.setResidentialAddress(tpResidentialAddress);
			}
			if(company.getPostalAddress()==null){
				//tpPostalAddress.setAddessInfoLocked(false);
				company.setPostalAddress(tpPostalAddress);
			}
			checkQA(company);
			
			List<Company> companies = new ArrayList<>();
			companies.add(company);
			preRegisterChecks(companies, formUser);
			preUserRegisterChecks(formUser);
			if (formUser.getId() == null) {
				formUser = registerService.register(formUser, true);
			} else {
				usersService.update(formUser);
			}
			// Saving Company Addresses
			AddressService.instance().create(tpResidentialAddress);
			AddressService.instance().create(tpPostalAddress);

			company.setResidentialAddress(tpResidentialAddress);
			company.setPostalAddress(tpPostalAddress);
			// Creating Training Provider Application
			trainingProviderApplication.setUsers(formUser);
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
			if(company.getId()==null){
				create(company);
			}
			trainingProviderApplicationService.create(trainingProviderApplication);
			// Creating Company And Sent Task
			createTPCompanyAndSendTask(company, formUser, createTask, configDocProcessEnum, hostingCompany, trainingProviderApplication);

			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			if (formUser.getDocs() != null) {
				for (Doc doc : formUser.getDocs()) {
					doc.setCompany(null);
					doc.setVersionNo(1);
					doc.setForUsers(formUser);
					doc.setUsers(formUser);
					doc.setTargetKey(trainingProviderApplication.getId());
					doc.setTargetClass(TrainingProviderApplication.class.getName());
					dataEntities.add(doc);
					dataEntities.add(new DocByte(doc.getData(), doc));
					dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}
			// creates link to company qualifications and unit standards
			linkUserOrCompanyToQualificationUnitStandards(unitStandardsList, qualificationsList, formUser, company, configDocProcessEnum, TrainingProviderApplication.class.getName(), trainingProviderApplication.getId());

			if (dataEntities.size() > 0) {
				dao.createBatch(dataEntities);
			}

			// Creating Self Evaluation For merSETA company
			auditorMonitorReviewService.prepSelfEvaluation(TrainingProviderApplication.class.getName(), trainingProviderApplication.getId(), configDocProcessEnum, trainingProviderApplication);

			// Creating TP Contacts
			dataEntities = new ArrayList<IDataEntity>();
			for (Users u : contactPersonList) {
				if (!u.equals(formUser)) {
					CompanyUsersService companyUsersService=new CompanyUsersService();
					List<CompanyUsers>  cuList=null;
					if(u.getId() !=null)
					{
						if(u.getDesignation() != null)
						{
							cuList=companyUsersService.findByUserAndCompanyAndTypeAndDesignation(company.getId(), u.getId(),ConfigDocProcessEnum.TP,u.getDesignation().getId());
						}
						else if(u.getAssessorModType() != null){
							cuList=companyUsersService.findByUserAndCompanyAndTypeAndAssessorModType(company.getId(), u.getId(),ConfigDocProcessEnum.TP,u.getAssessorModType());
						}
					}
					if(cuList==null || cuList.size()<1)
					{
						CompanyUsers companyUsers = new CompanyUsers(u, company);
						companyUsers.setCompanyUserType(ConfigDocProcessEnum.TP);
						if (u.getDesignation() != null) {
							companyUsers.setDesignation(u.getDesignation());
						}
						if (u.getAssessorModType() != null) {
							companyUsers.setAssessorModType(u.getAssessorModType());

						}
						if (u.getId() == null) {
							companyUsers.setExistingUser(false);
							registerService.register(u, true);
						} else {
							companyUsers.setExistingUser(true);
							usersService.update(u);
						}
						dataEntities.add(companyUsers);
					}
				}

			}
			/* Creating training Provider Skills Program **/
			for (SkillsProgram sp : skillsProgramList) {
				TrainingProviderSkillsProgramme tpSp = new TrainingProviderSkillsProgramme(sp, trainingProviderApplication);
				dataEntities.add(tpSp);
			}

			/* Creating training Provider Skills Set **/
			for (SkillsSet ss : skillsSetList) {
				TrainingProviderSkillsSet tpSs = new TrainingProviderSkillsSet(ss, trainingProviderApplication);
				dataEntities.add(tpSs);
			}

			if (dataEntities.size() > 0) {
				dao.createBatch(dataEntities);
			}

			/* Sending Application form via email */
			trainingProviderApplicationService.sendTPApplicationEmail(unitStandardsList, qualificationsList, company, formUser, trainingProviderApplication, skillsProgramList, skillsSetList);

		} else {
			throw new ValidationException("Please enter select qualification details");
		}
	}
	
	
	public void createTPAndCompanyAndSendTaskVersionTwo(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList, Company company, TrainingSite trainingSite, List<SDPCompany> sdpContactList, List<AssessorModeratorCompanySites> assmodSiteList, Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany, TrainingProviderApplication trainingProviderApplication, Address tpResidentialAddress, Address tpPostalAddress, ArrayList<Users> contactPersonList, ArrayList<SkillsProgram> skillsProgramList, ArrayList<SkillsSet> skillsSetList) throws Exception {
		if (qualificationsList.size() > 0 || unitStandardsList.size() > 0 || skillsProgramList.size() > 0 || skillsSetList.size() > 0) {
			checkQAVersionTwo(company);
			
			List<Company> companies = new ArrayList<>();
			companies.add(company);
			preRegisterChecks(companies, formUser);
			preUserRegisterChecks(formUser);
			if (trainingSite != null) {
				trainingProviderApplication.setTrainingSite(TrainingSiteService.instance().createUpdateSite(trainingSite));
			}else {
				trainingProviderApplication.setTrainingSite(null);
			}
			if (formUser.getId() == null) {
				formUser = registerService.register(formUser, true);
			} else {
				usersService.update(formUser);
			}
			// Creating Training Provider Application
			trainingProviderApplication.setUsers(formUser);
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
			trainingProviderApplicationService.create(trainingProviderApplication);
			// Creating Company And Sent Task
			createTPCompanyAndSendTaskVersionTwo(company, formUser, createTask, configDocProcessEnum, hostingCompany, trainingProviderApplication);
//			createTPCompanyAndSendTask(company, formUser, createTask, configDocProcessEnum, hostingCompany, trainingProviderApplication);

			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			if (formUser.getDocs() != null) {
				for (Doc doc : formUser.getDocs()) {
					doc.setCompany(null);
					doc.setVersionNo(1);
					doc.setForUsers(formUser);
					doc.setUsers(formUser);
					doc.setTargetKey(trainingProviderApplication.getId());
					doc.setTargetClass(TrainingProviderApplication.class.getName());
					dataEntities.add(doc);
					dataEntities.add(new DocByte(doc.getData(), doc));
					dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}
			
			// creates link to company qualifications and unit standards
			linkUserOrCompanyToQualificationUnitStandards(unitStandardsList, qualificationsList, formUser, company, configDocProcessEnum, TrainingProviderApplication.class.getName(), trainingProviderApplication.getId());

			if (dataEntities.size() > 0) {
				dao.createBatch(dataEntities);
			}

			// Creating Self Evaluation For merSETA company
			auditorMonitorReviewService.prepSelfEvaluation(TrainingProviderApplication.class.getName(), trainingProviderApplication.getId(), configDocProcessEnum, trainingProviderApplication);

			// Creating SDP Contacts
			dataEntities = new ArrayList<IDataEntity>();
			for (SDPCompany newSdp : sdpContactList) {
				CompanyUsersService companyUsersService=new CompanyUsersService();
				List<CompanyUsers>  cuList = null;
				// SDP company Links
				if (newSdp.getSdp().getId() == null) {
					registerService.register(newSdp.getSdp(), true);
					newSdp.setSdp(newSdp.getSdp());
				} else {
					usersService.update(newSdp.getSdp());
				}
				if (trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
					newSdp.setTrainingSite(trainingSite);
				}
				newSdp.setApprovalStatus(ApprovalEnum.Approved);
				dataEntities.add(newSdp);
				// Company Users Link
				if (newSdp.getSdpType().getDesignation() != null) {
					cuList = companyUsersService.findByUserAndCompanyAndTypeAndDesignation(company.getId(), newSdp.getSdp().getId(), ConfigDocProcessEnum.TP, newSdp.getSdpType().getDesignation().getId());
					if (!cuList.isEmpty()) {
						CompanyUsers newCompUser = new CompanyUsers(newSdp.getSdp(), company);
						newCompUser.setCompanyUserType(ConfigDocProcessEnum.TP);
						if (newSdp.getSdpType().getDesignation() != null) {
							newCompUser.setDesignation(newSdp.getSdpType().getDesignation());
						}
						newCompUser.setExistingUser(true);
						dataEntities.add(newSdp);
					}
				}
			}
			// Creating Ass/Mod Links
			for (AssessorModeratorCompanySites assModSite : assmodSiteList) {
				if (trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
					assModSite.setTrainingSite(trainingSite);
				}
				assModSite.setApprovalStatus(ApprovalEnum.Approved);
				dataEntities.add(assModSite);
				CompanyUsersService companyUsersService=new CompanyUsersService();
				List<CompanyUsers>  cuList = null;
				if (assModSite.getAssessorModType() != null) {
					cuList=companyUsersService.findByUserAndCompanyAndTypeAndAssessorModType(company.getId(), assModSite.getAssessorModerator().getId(),ConfigDocProcessEnum.TP,assModSite.getAssessorModType());
					if (cuList.isEmpty()) {
						CompanyUsers companyUsers = new CompanyUsers(assModSite.getAssessorModerator(), company);
						companyUsers.setCompanyUserType(ConfigDocProcessEnum.TP);
						if (assModSite.getAssessorModType() != null) {
							companyUsers.setAssessorModType(assModSite.getAssessorModType());
						}
						if (assModSite.getAssessorModerator().getId() == null) {
							companyUsers.setExistingUser(false);
							registerService.register(assModSite.getAssessorModerator(), true);
						} else {
							companyUsers.setExistingUser(true);
							usersService.update(assModSite.getAssessorModerator());
						}
						dataEntities.add(companyUsers);
					}
				}
			}
			// old version of links
			for (Users u : contactPersonList) {
				if (!u.equals(formUser)) {
					CompanyUsersService companyUsersService=new CompanyUsersService();
					List<CompanyUsers>  cuList=null;
					if(u.getId() !=null)
					{
						if(u.getDesignation() != null)
						{
							cuList=companyUsersService.findByUserAndCompanyAndTypeAndDesignation(company.getId(), u.getId(),ConfigDocProcessEnum.TP,u.getDesignation().getId());
						}else if(u.getAssessorModType() != null){
							cuList=companyUsersService.findByUserAndCompanyAndTypeAndAssessorModType(company.getId(), u.getId(),ConfigDocProcessEnum.TP,u.getAssessorModType());
						}
					}
					if(cuList==null || cuList.size()<1)
					{
						CompanyUsers companyUsers = new CompanyUsers(u, company);
						companyUsers.setCompanyUserType(ConfigDocProcessEnum.TP);
						if (u.getDesignation() != null) {
							companyUsers.setDesignation(u.getDesignation());
						}
						if (u.getAssessorModType() != null) {
							companyUsers.setAssessorModType(u.getAssessorModType());
						}
						if (u.getId() == null) {
							companyUsers.setExistingUser(false);
							registerService.register(u, true);
						} else {
							companyUsers.setExistingUser(true);
							usersService.update(u);
						}
						dataEntities.add(companyUsers);
					}
				}

			}
			/* Creating training Provider Skills Program **/
			for (SkillsProgram sp : skillsProgramList) {
				TrainingProviderSkillsProgramme tpSp = new TrainingProviderSkillsProgramme(sp, trainingProviderApplication);
				dataEntities.add(tpSp);
			}

			/* Creating training Provider Skills Set **/
			for (SkillsSet ss : skillsSetList) {
				TrainingProviderSkillsSet tpSs = new TrainingProviderSkillsSet(ss, trainingProviderApplication);
				dataEntities.add(tpSs);
			}

			if (dataEntities.size() > 0) {
				dao.createBatch(dataEntities);
			}

			/* Sending Application form via email */
			trainingProviderApplicationService.sendTPApplicationEmail(unitStandardsList, qualificationsList, company, formUser, trainingProviderApplication, skillsProgramList, skillsSetList);

		} else {
			throw new ValidationException("Please enter select qualification details");
		}
	}

	
	public void checkQA(Company entity) throws Exception
	{
		HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
		TownService townService = new TownService();
		List<Users> qualityAssurorUsers = new ArrayList<>();
		Address address = null;
		if (entity != null && entity.getResidentialAddress() != null) {
			if(entity.getResidentialAddress().getId() != null){
				address = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
			}
			else{
				address=entity.getResidentialAddress();
			}
		}
		if (address != null && address.getTown() != null) {
			qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
		}
		
		if(qualityAssurorUsers==null || qualityAssurorUsers.size()<1)
		{
			throw new Exception("We are unable to locate Quality Assuror for this company ("+entity.getCompanyName()+"), Please contact support.");
		}
	}
	
	public void checkQAVersionTwo(Company entity) throws Exception
	{
		HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
		TownService townService = new TownService();
		List<Users> qualityAssurorUsers = new ArrayList<>();
		Address address = null;
		if (entity != null && entity.getResidentialAddress() != null) {
			if(entity.getResidentialAddress().getId() != null){
				address = AddressService.instance().findByKey(entity.getResidentialAddress().getId());
			}
			else{
				address=entity.getResidentialAddress();
			}
		}
		if (address != null && address.getTown() != null) {
			qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
		}
		
		if(qualityAssurorUsers==null || qualityAssurorUsers.size()<1)
		{
			throw new Exception("We are unable to locate Quality Assuror for this company ("+entity.getCompanyName()+"), Please contact support.");
		}
	}
	public void createNonSetaCompany_old(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList, Company company, Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany, TrainingProviderApplication trainingProviderApplication, Address tpResidentialAddress, Address tpPostalAddress, List<Users> contactPersonList, List<SkillsProgram> skillsProgramList, ArrayList<SkillsSet> skillsSetList,
			List<TrainingProviderDocParent> docParentList) throws Exception {

		if (qualificationsList.size() > 0 || unitStandardsList.size() > 0 || skillsProgramList.size() > 0 || skillsSetList.size() > 0) {
			List<Company> companies = new ArrayList<>();
			companies.add(company);
			preRegisterChecks(companies, formUser);
			preUserRegisterChecks(formUser);
			if (formUser.getId() == null) {
				formUser = registerService.register(formUser, true);
			} else {
				usersService.update(formUser);
			}

			// Saving Company Addresses
			AddressService.instance().create(tpResidentialAddress);
			AddressService.instance().create(tpPostalAddress);

			company.setResidentialAddress(tpResidentialAddress);
			company.setPostalAddress(tpPostalAddress);
			createCompanyAndSendTask(company, formUser, createTask, configDocProcessEnum, hostingCompany);

			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			if (formUser.getDocs() != null) {
				for (Doc doc : formUser.getDocs()) {
					doc.setCompany(null);
					doc.setVersionNo(1);
					doc.setForUsers(formUser);
					doc.setUsers(formUser);
					doc.setTargetKey(trainingProviderApplication.getId());
					doc.setTargetClass(TrainingProviderApplication.class.getName());
					dataEntities.add(doc);
					dataEntities.add(new DocByte(doc.getData(), doc));
					dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}

			for (TrainingProviderDocParent docParent : docParentList) {
				dataEntities.add(docParent);
				Doc doc = docParent.getDoc();
				doc.setVersionNo(1);
				doc.setUsers(formUser);
				doc.setTargetKey(docParent.getId());
				doc.setTargetClass(TrainingProviderDocParent.class.getName());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}

			// Creating Training Provider Application
			trainingProviderApplication.setUsers(formUser);
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
			trainingProviderApplicationService.create(trainingProviderApplication);

			// creates link to company qualifications and unit standards
			linkUserOrCompanyToQualificationUnitStandards(unitStandardsList, qualificationsList, formUser, company, configDocProcessEnum, TrainingProviderApplication.class.getName(), trainingProviderApplication.getId());

			if (dataEntities.size() > 0) {
				dao.createBatch(dataEntities);
			}

			// Creating TP Contacts
			dataEntities = new ArrayList<IDataEntity>();
			for (Users u : contactPersonList) {
				CompanyUsers companyUsers = new CompanyUsers(u, company);
				companyUsers.setCompanyUserType(ConfigDocProcessEnum.TP);
				if (u.getDesignation() != null) {
					companyUsers.setDesignation(u.getDesignation());
				}
				if (u.getAssessorModType() != null) {
					companyUsers.setAssessorModType(u.getAssessorModType());

				}

				if (u.getId() == null) {
					companyUsers.setExistingUser(false);
					registerService.register(u, true);
				} else {
					companyUsers.setExistingUser(true);
					usersService.update(u);
				}

				dataEntities.add(companyUsers);

			}
			/* Creating training Provider Skills Program **/
			for (SkillsProgram sp : skillsProgramList) {
				TrainingProviderSkillsProgramme tpSp = new TrainingProviderSkillsProgramme(sp, trainingProviderApplication);
				dataEntities.add(tpSp);
			}

			/* Creating training Provider Skills Set **/
			for (SkillsSet ss : skillsSetList) {
				TrainingProviderSkillsSet tpSs = new TrainingProviderSkillsSet(ss, trainingProviderApplication);
				dataEntities.add(tpSs);
			}

			if (dataEntities.size() > 0) {
				dao.createBatch(dataEntities);
			}

		} else {
			throw new ValidationException("company.registration.list.validation.error");
		}
	}

	public void createNonSetaTPSendTask(List<CompanyUnitStandard> companyUnitStandardsList, List<CompanyQualifications> companyQualificationsList, Company company, Users formUser, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany, TrainingProviderApplication trainingProviderApplication, Address tpResidentialAddress, Address tpPostalAddress, ArrayList<Users> contactPersonList, List<TrainingProviderSkillsProgramme> tpSkillsProgramme,
			List<TrainingProviderSkillsSet> tpSkillsSetList, List<TrainingProviderDocParent> docParentList) throws Exception {
		preUserRegisterChecks(formUser);
		if (formUser.getId() == null) {
			formUser = registerService.register(formUser, true);
		} else {
			usersService.update(formUser);
		}
		AddressService.instance().create(tpResidentialAddress);
		AddressService.instance().create(tpPostalAddress);
		company.setResidentialAddress(tpResidentialAddress);
		company.setPostalAddress(tpPostalAddress);
		if(company.getCompanyType()==null){company.setCompanyType(CompanyTypeEnum.TP);}
		company.setCompanyStatus(CompanyStatusEnum.Pending);
		// Creating Non Seta Company
		create(company);
		// Creating Training Provider Application
		trainingProviderApplication.setUsers(formUser);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
		trainingProviderApplication.setCompany(company);
		trainingProviderApplicationService.create(trainingProviderApplication);

		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		if (formUser.getDocs() != null) {
			for (Doc doc : formUser.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				doc.setForUsers(formUser);
				doc.setUsers(formUser);
				doc.setTargetKey(trainingProviderApplication.getId());
				doc.setTargetClass(TrainingProviderApplication.class.getName());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		
		if (company.getDocs() != null) {
			for (Doc doc : company.getDocs()) {
				doc.setCompany(company);
				doc.setVersionNo(1);
				doc.setUsers(formUser);
				doc.setTargetClass(trainingProviderApplication.getClass().getName());
				doc.setTargetKey(trainingProviderApplication.getId());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		
		for (TrainingProviderDocParent docParent : docParentList) {
			dataEntities.add(docParent);
			Doc doc = docParent.getDoc();
			doc.setVersionNo(1);
			doc.setUsers(formUser);
			doc.setTargetKey(docParent.getId());
			doc.setTargetClass(TrainingProviderDocParent.class.getName());
			dataEntities.add(doc);
			dataEntities.add(new DocByte(doc.getData(), doc));
			dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
		}
		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}

		// Creating TP Contacts
		dataEntities = new ArrayList<IDataEntity>();
		for (Users u : contactPersonList) {
			CompanyUsersService companyUsersService=new CompanyUsersService();
			List<CompanyUsers>  cuList=null;
			if(u.getId() !=null)
			{
				if(u.getDesignation() != null)
				{
					cuList=companyUsersService.findByUserAndCompanyAndTypeAndDesignation(company.getId(), u.getId(),ConfigDocProcessEnum.TP,u.getDesignation().getId());
				}
				else if(u.getAssessorModType() != null){
					cuList=companyUsersService.findByUserAndCompanyAndTypeAndAssessorModType(company.getId(), u.getId(),ConfigDocProcessEnum.TP,u.getAssessorModType());
				}
			}
			if(cuList==null || cuList.size()<1)
			{
				CompanyUsers companyUsers = new CompanyUsers(u, company);
				companyUsers.setCompanyUserType(ConfigDocProcessEnum.TP);
				if (u.getDesignation() != null) {
					companyUsers.setDesignation(u.getDesignation());
				}
				if (u.getAssessorModType() != null) {
					companyUsers.setAssessorModType(u.getAssessorModType());

				}

				if (u.getId() == null) {
					companyUsers.setExistingUser(false);
					registerService.register(u, true);
				} else {
					companyUsers.setExistingUser(true);
					usersService.update(u);
				}

				dataEntities.add(companyUsers);
			}

		}
		// Creating qualifications
		for (CompanyQualifications cp : companyQualificationsList) {
			cp.setTargetClass(TrainingProviderApplication.class.getName());
			cp.setTargetKey(trainingProviderApplication.getId());
			cp.setCompany(company);
			dataEntities.add(cp);
		}
		// Creating unit standards
		for (CompanyUnitStandard us : companyUnitStandardsList) {
			us.setTargetClass(TrainingProviderApplication.class.getName());
			us.setTargetKey(trainingProviderApplication.getId());
			us.setCompany(company);
			dataEntities.add(us);
		}
		// List<TrainingProviderSkillsProgramme> tpSkillsProgramme,
		for (TrainingProviderSkillsProgramme tpSkillsProg : tpSkillsProgramme) {
			tpSkillsProg.setTrainingProviderApplication(trainingProviderApplication);
			dataEntities.add(tpSkillsProg);
		}
		// List<TrainingProviderSkillsSet> tpSkillsSetList
		for (TrainingProviderSkillsSet tpSkillsSet : tpSkillsSetList) {
			tpSkillsSet.setTrainingProviderApplication(trainingProviderApplication);
			dataEntities.add(tpSkillsSet);
		}
		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}

		/** Creating task */
		String desc = ConfigDocProcessEnum.NON_SETA_PROVIDERS.getTaskDescription();
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		/* Sending acknowledgement  email */
		trainingProviderApplicationService.sendNonMerSetaAcknowledgementEmail(formUser, trainingProviderApplication);
	}
	
	public void createNonSetaTPSendTaskVersionTwo(List<CompanyUnitStandard> companyUnitStandardsList, List<CompanyQualifications> companyQualificationsList, Company company, Users formUser, List<SDPCompany> sdpContactList, List<AssessorModeratorCompanySites> assmodSiteList, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany, TrainingProviderApplication trainingProviderApplication, Address tpResidentialAddress, Address tpPostalAddress, ArrayList<Users> contactPersonList, List<TrainingProviderSkillsProgramme> tpSkillsProgramme,
			List<TrainingProviderSkillsSet> tpSkillsSetList, List<TrainingProviderDocParent> docParentList) throws Exception {
		preUserRegisterChecks(formUser);
		if (formUser.getId() == null) {
			formUser = registerService.register(formUser, true);
		} else {
			usersService.update(formUser);
		}
		// Creating Training Provider Application
		trainingProviderApplication.setUsers(formUser);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
		trainingProviderApplication.setCompany(company);
		trainingProviderApplicationService.create(trainingProviderApplication);

		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		if (formUser.getDocs() != null) {
			for (Doc doc : formUser.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				doc.setForUsers(formUser);
				doc.setUsers(formUser);
				doc.setTargetKey(trainingProviderApplication.getId());
				doc.setTargetClass(TrainingProviderApplication.class.getName());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		
		if (company.getDocs() != null) {
			for (Doc doc : company.getDocs()) {
				doc.setCompany(company);
				doc.setVersionNo(1);
				doc.setUsers(formUser);
				doc.setTargetClass(trainingProviderApplication.getClass().getName());
				doc.setTargetKey(trainingProviderApplication.getId());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		
		for (TrainingProviderDocParent docParent : docParentList) {
			dataEntities.add(docParent);
			Doc doc = docParent.getDoc();
			doc.setVersionNo(1);
			doc.setUsers(formUser);
			doc.setTargetKey(docParent.getId());
			doc.setTargetClass(TrainingProviderDocParent.class.getName());
			dataEntities.add(doc);
			dataEntities.add(new DocByte(doc.getData(), doc));
			dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
		}
		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}
		// creating new SDP company Users
		dataEntities = new ArrayList<IDataEntity>();
		for (SDPCompany newSdp : sdpContactList) {
			CompanyUsersService companyUsersService=new CompanyUsersService();
			List<CompanyUsers>  cuList = null;
			// SDP company Links
			if (newSdp.getSdp().getId() == null) {
				registerService.register(newSdp.getSdp(), true);
				newSdp.setSdp(newSdp.getSdp());
			} else {
				usersService.update(newSdp.getSdp());
			}
			newSdp.setApprovalStatus(ApprovalEnum.Approved);
			dataEntities.add(newSdp);
			// Company Users Link
			if (newSdp.getSdpType().getDesignation() != null) {
				cuList = companyUsersService.findByUserAndCompanyAndTypeAndDesignation(company.getId(), newSdp.getSdp().getId(), ConfigDocProcessEnum.TP, newSdp.getSdpType().getDesignation().getId());
				if (!cuList.isEmpty()) {
					CompanyUsers newCompUser = new CompanyUsers(newSdp.getSdp(), company);
					newCompUser.setCompanyUserType(ConfigDocProcessEnum.TP);
					if (newSdp.getSdpType().getDesignation() != null) {
						newCompUser.setDesignation(newSdp.getSdpType().getDesignation());
					}
					newCompUser.setExistingUser(true);
					dataEntities.add(newSdp);
				}
			}
		}
		
		// Creating Ass/Mod Links
		for (AssessorModeratorCompanySites assModSite : assmodSiteList) {
			assModSite.setApprovalStatus(ApprovalEnum.Approved);
			dataEntities.add(assModSite);
			CompanyUsersService companyUsersService=new CompanyUsersService();
			List<CompanyUsers>  cuList = null;
			if (assModSite.getAssessorModType() != null) {
				cuList=companyUsersService.findByUserAndCompanyAndTypeAndAssessorModType(company.getId(), assModSite.getAssessorModerator().getId(),ConfigDocProcessEnum.TP,assModSite.getAssessorModType());
				if (cuList.isEmpty()) {
					CompanyUsers companyUsers = new CompanyUsers(assModSite.getAssessorModerator(), company);
					companyUsers.setCompanyUserType(ConfigDocProcessEnum.TP);
					if (assModSite.getAssessorModType() != null) {
						companyUsers.setAssessorModType(assModSite.getAssessorModType());
					}
					if (assModSite.getAssessorModerator().getId() == null) {
						companyUsers.setExistingUser(false);
						registerService.register(assModSite.getAssessorModerator(), true);
					} else {
						companyUsers.setExistingUser(true);
						usersService.update(assModSite.getAssessorModerator());
					}
					dataEntities.add(companyUsers);
				}
			}
		}
		
		// Creating TP Contacts
		for (Users u : contactPersonList) {
			CompanyUsersService companyUsersService=new CompanyUsersService();
			List<CompanyUsers>  cuList=null;
			if(u.getId() !=null)
			{
				if(u.getDesignation() != null)
				{
					cuList=companyUsersService.findByUserAndCompanyAndTypeAndDesignation(company.getId(), u.getId(),ConfigDocProcessEnum.TP,u.getDesignation().getId());
				}
				else if(u.getAssessorModType() != null){
					cuList=companyUsersService.findByUserAndCompanyAndTypeAndAssessorModType(company.getId(), u.getId(),ConfigDocProcessEnum.TP,u.getAssessorModType());
				}
			}
			if(cuList==null || cuList.size()<1)
			{
				CompanyUsers companyUsers = new CompanyUsers(u, company);
				companyUsers.setCompanyUserType(ConfigDocProcessEnum.TP);
				if (u.getDesignation() != null) {
					companyUsers.setDesignation(u.getDesignation());
				}
				if (u.getAssessorModType() != null) {
					companyUsers.setAssessorModType(u.getAssessorModType());

				}

				if (u.getId() == null) {
					companyUsers.setExistingUser(false);
					registerService.register(u, true);
				} else {
					companyUsers.setExistingUser(true);
					usersService.update(u);
				}

				dataEntities.add(companyUsers);
			}

		}
		// Creating qualifications
		for (CompanyQualifications cp : companyQualificationsList) {
			cp.setTargetClass(TrainingProviderApplication.class.getName());
			cp.setTargetKey(trainingProviderApplication.getId());
			cp.setCompany(company);
			dataEntities.add(cp);
		}
		// Creating unit standards
		for (CompanyUnitStandard us : companyUnitStandardsList) {
			us.setTargetClass(TrainingProviderApplication.class.getName());
			us.setTargetKey(trainingProviderApplication.getId());
			us.setCompany(company);
			dataEntities.add(us);
		}
		// List<TrainingProviderSkillsProgramme> tpSkillsProgramme,
		for (TrainingProviderSkillsProgramme tpSkillsProg : tpSkillsProgramme) {
			tpSkillsProg.setTrainingProviderApplication(trainingProviderApplication);
			dataEntities.add(tpSkillsProg);
		}
		// List<TrainingProviderSkillsSet> tpSkillsSetList
		for (TrainingProviderSkillsSet tpSkillsSet : tpSkillsSetList) {
			tpSkillsSet.setTrainingProviderApplication(trainingProviderApplication);
			dataEntities.add(tpSkillsSet);
		}
		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}

		/** Creating task */
		String desc = ConfigDocProcessEnum.NON_SETA_PROVIDERS.getTaskDescription();
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		/* Sending acknowledgement  email */
		trainingProviderApplicationService.sendNonMerSetaAcknowledgementEmail(formUser, trainingProviderApplication);
	}
	
	
	public void createLegacyTPSendTask( List<LegacyProviderQualification> legacyProviderQualificationList,
			List<LegacyProviderLearnership> legacyProviderLearnershipList, 
			List<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeList,
			List<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeSkillsSetList,
			List<LegacyProviderUnitStandard> legacyProviderUnitStandardList,
			Company company, Users formUser, boolean createTask, 
			ConfigDocProcessEnum configDocProcessEnum,
			TrainingProviderApplication trainingProviderApplication, 
			Address tpResidentialAddress, 
			Address tpPostalAddress, 
			ArrayList<Users> contactPersonList) throws Exception {
		preUserRegisterChecks(formUser);
		if (formUser.getId() == null) {
			formUser = registerService.register(formUser, true);
		} else {
			usersService.update(formUser);
		}
		// check if company exists first before creating
		if (company.getId() != null) {
			// updates address linked
			boolean comapnyUpdate = false;
			if (tpResidentialAddress.getId() == null) {
				AddressService.instance().create(tpResidentialAddress);
				company.setResidentialAddress(tpResidentialAddress);
				comapnyUpdate = true;
			} else {
				AddressService.instance().update(tpResidentialAddress);
			}
			
			if (tpPostalAddress.getId() == null) {
				AddressService.instance().create(tpPostalAddress);
				company.setPostalAddress(tpPostalAddress);
				comapnyUpdate = true;
			} else {
				AddressService.instance().update(tpPostalAddress);
			}
			if (comapnyUpdate) {
				create(company);
			}
		} else {
			AddressService.instance().create(tpResidentialAddress);
			AddressService.instance().create(tpPostalAddress);
			company.setResidentialAddress(tpResidentialAddress);
			company.setPostalAddress(tpPostalAddress);
			if(company.getCompanyType()==null){company.setCompanyType(CompanyTypeEnum.TP);}
			company.setCompanyStatus(CompanyStatusEnum.Pending);
			// Creating Non Seta Company
			create(company);
		}
		// Creating Training Provider Application
		trainingProviderApplication.setUsers(formUser);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
		trainingProviderApplication.setProviderStatus(ProviderStatusService.instance().findByCode("506"));
		trainingProviderApplication.setCompany(company);
		trainingProviderApplicationService.create(trainingProviderApplication);

		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		List<IDataEntity> dataEntitiesUpdate = new ArrayList<IDataEntity>();
		if (formUser.getDocs() != null) {
			for (Doc doc : formUser.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				doc.setForUsers(formUser);
				doc.setUsers(formUser);
				doc.setTargetKey(trainingProviderApplication.getId());
				doc.setTargetClass(TrainingProviderApplication.class.getName());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		
		if (company.getDocs() != null) {
			for (Doc doc : company.getDocs()) {
				doc.setCompany(company);
				doc.setVersionNo(1);
				doc.setUsers(formUser);
				doc.setTargetClass(trainingProviderApplication.getClass().getName());
				doc.setTargetKey(trainingProviderApplication.getId());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		
		for (Users u : contactPersonList) {
			CompanyUsersService companyUsersService=new CompanyUsersService();
			List<CompanyUsers>  cuList=null;
			if(u.getId() !=null) {
				if(u.getDesignation() != null) {
					cuList=companyUsersService.findByUserAndCompanyAndTypeAndDesignation(company.getId(), u.getId(),ConfigDocProcessEnum.TP,u.getDesignation().getId());
				} else if(u.getAssessorModType() != null){
					cuList=companyUsersService.findByUserAndCompanyAndTypeAndAssessorModType(company.getId(), u.getId(),ConfigDocProcessEnum.TP,u.getAssessorModType());
				}
			}
			if(cuList==null || cuList.size()<1) {
				CompanyUsers companyUsers = new CompanyUsers(u, company);
				companyUsers.setCompanyUserType(ConfigDocProcessEnum.TP);
				if (u.getDesignation() != null) {
					companyUsers.setDesignation(u.getDesignation());
				}
				if (u.getAssessorModType() != null) {
					companyUsers.setAssessorModType(u.getAssessorModType());

				}
				if (u.getId() == null) {
					companyUsers.setExistingUser(false);
					registerService.register(u, true);
				} else {
					companyUsers.setExistingUser(true);
					usersService.update(u);
				}
				dataEntities.add(companyUsers);
			}

		}
		
		// Creating qualifications
		for (LegacyProviderQualification lq : legacyProviderQualificationList) {
			if(lq.getQualification() !=null && lq.getQualificationExpired() !=null && !lq.getQualificationExpired()){
				CompanyQualifications cq=new CompanyQualifications(company, lq.getQualification());
				cq.setManuallyAdded(false);
				cq.setTargetClass(TrainingProviderApplication.class.getName());
				cq.setTargetKey(trainingProviderApplication.getId());
				cq.setCompany(company);
				dataEntities.add(cq);
				lq.setApplicationSubmited(true);
				dataEntitiesUpdate.add(lq);
			}
		}
		// Creating unit standards
		for (LegacyProviderUnitStandard us : legacyProviderUnitStandardList) {
			if(us.getUnitStandard() !=null && us.getUnitStandardExpired() !=null && !us.getUnitStandardExpired()){
				CompanyUnitStandard cu=new CompanyUnitStandard();
				cu.setManuallyAdded(false);
				cu.setTargetClass(TrainingProviderApplication.class.getName());
				cu.setTargetKey(trainingProviderApplication.getId());
				cu.setCompany(company);
				cu.setUnitStandard(us.getUnitStandard());
				dataEntities.add(cu);
				us.setApplicationSubmited(true);
				dataEntitiesUpdate.add(us);
			}
		}
		// Creating Skills Programme
		for (LegacyProviderSkillsProgramme lsp : legacyProviderSkillsProgrammeList) {
			if(lsp.getSkillsProgram() !=null && lsp.getQualificationExpired() !=null && !lsp.getQualificationExpired()) {
				TrainingProviderSkillsProgramme tpSkillsProg = new TrainingProviderSkillsProgramme(lsp.getSkillsProgram(), trainingProviderApplication);
				tpSkillsProg.setManuallyAdded(false);
				tpSkillsProg.setTrainingProviderApplication(trainingProviderApplication);
				dataEntities.add(tpSkillsProg);
				lsp.setApplicationSubmited(true);
				dataEntitiesUpdate.add(lsp);
			}
		}
		// Creating Skills Set
		for (LegacyProviderSkillsProgramme lspss : legacyProviderSkillsProgrammeSkillsSetList) {
			if(lspss.getSkillsSet() !=null && lspss.getQualificationExpired() !=null && !lspss.getQualificationExpired()) {
				TrainingProviderSkillsSet trainingProviderSkillsSet = new TrainingProviderSkillsSet(lspss.getSkillsSet(), trainingProviderApplication);
				trainingProviderSkillsSet.setManuallyAdded(false);
				trainingProviderSkillsSet.setTrainingProviderApplication(trainingProviderApplication);
				dataEntities.add(trainingProviderSkillsSet);
				lspss.setApplicationSubmited(true);
				dataEntitiesUpdate.add(lspss);
			}
		}
		// Creating Learnership
		for (LegacyProviderLearnership ltpLearnership : legacyProviderLearnershipList) {
			if(ltpLearnership.getLearnership() !=null && ltpLearnership.getQualificationExpired() !=null && !ltpLearnership.getQualificationExpired()){
				TrainingProviderLearnership tpLearnership=new TrainingProviderLearnership(ltpLearnership.getLearnership(), trainingProviderApplication);
				tpLearnership.setManuallyAdded(false);
				dataEntities.add(tpLearnership);
				ltpLearnership.setApplicationSubmited(true);
				dataEntitiesUpdate.add(ltpLearnership);
			}	
		}
		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}

		/** Creating task */
		String desc = ConfigDocProcessEnum.SDP_LEGACY_APPLICATION.getTaskDescription();
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		/* Sending acknowledgement  email */
		trainingProviderApplicationService.sendNonMerSetaAcknowledgementEmail(formUser, trainingProviderApplication);
	}
	
	public void createLegacyTPSendTaskVersionTwo(List<SDPCompany> sdpContactList, 
			List<AssessorModeratorCompanySites> assmodSiteList, 
			List<LegacyProviderQualification> legacyProviderQualificationList,
			List<LegacyProviderLearnership> legacyProviderLearnershipList, 
			List<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeList,
			List<LegacyProviderSkillsProgramme> legacyProviderSkillsProgrammeSkillsSetList,
			List<LegacyProviderUnitStandard> legacyProviderUnitStandardList,
			Company company, Users formUser, boolean createTask, 
			ConfigDocProcessEnum configDocProcessEnum,
			TrainingProviderApplication trainingProviderApplication, 
			Address tpResidentialAddress, 
			Address tpPostalAddress, 
			ArrayList<Users> contactPersonList) throws Exception {
		preUserRegisterChecks(formUser);
		if (formUser.getId() == null) {
			formUser = registerService.register(formUser, true);
		} else {
			usersService.update(formUser);
		}
		// check if company exists first before creating
		if (company.getId() != null) {
			// updates address linked
			boolean comapnyUpdate = false;
			if (tpResidentialAddress.getId() == null) {
				AddressService.instance().create(tpResidentialAddress);
				company.setResidentialAddress(tpResidentialAddress);
				comapnyUpdate = true;
			} else {
				AddressService.instance().update(tpResidentialAddress);
			}
			
			if (tpPostalAddress.getId() == null) {
				AddressService.instance().create(tpPostalAddress);
				company.setPostalAddress(tpPostalAddress);
				comapnyUpdate = true;
			} else {
				AddressService.instance().update(tpPostalAddress);
			}
			if (comapnyUpdate) {
				create(company);
			}
		} else {
			AddressService.instance().create(tpResidentialAddress);
			AddressService.instance().create(tpPostalAddress);
			company.setResidentialAddress(tpResidentialAddress);
			company.setPostalAddress(tpPostalAddress);
			if(company.getCompanyType()==null){company.setCompanyType(CompanyTypeEnum.TP);}
			company.setCompanyStatus(CompanyStatusEnum.Pending);
			// Creating Non Seta Company
			create(company);
		}
		// Creating Training Provider Application
		trainingProviderApplication.setUsers(formUser);
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
		trainingProviderApplication.setProviderStatus(ProviderStatusService.instance().findByCode("506"));
		trainingProviderApplication.setCompany(company);
		trainingProviderApplicationService.create(trainingProviderApplication);

		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		List<IDataEntity> dataEntitiesUpdate = new ArrayList<IDataEntity>();
		if (formUser.getDocs() != null) {
			for (Doc doc : formUser.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				doc.setForUsers(formUser);
				doc.setUsers(formUser);
				doc.setTargetKey(trainingProviderApplication.getId());
				doc.setTargetClass(TrainingProviderApplication.class.getName());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		
		if (company.getDocs() != null) {
			for (Doc doc : company.getDocs()) {
				doc.setCompany(company);
				doc.setVersionNo(1);
				doc.setUsers(formUser);
				doc.setTargetClass(trainingProviderApplication.getClass().getName());
				doc.setTargetKey(trainingProviderApplication.getId());
				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, formUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		
		for (SDPCompany newSdp : sdpContactList) {
			CompanyUsersService companyUsersService=new CompanyUsersService();
			List<CompanyUsers>  cuList = null;
			// SDP company Links
			if (newSdp.getSdp().getId() == null) {
				registerService.register(newSdp.getSdp(), true);
				newSdp.setSdp(newSdp.getSdp());
			} else {
				usersService.update(newSdp.getSdp());
			}
			newSdp.setApprovalStatus(ApprovalEnum.Approved);
			dataEntities.add(newSdp);
			// Company Users Link
			if (newSdp.getSdpType().getDesignation() != null) {
				cuList = companyUsersService.findByUserAndCompanyAndTypeAndDesignation(company.getId(), newSdp.getSdp().getId(), ConfigDocProcessEnum.TP, newSdp.getSdpType().getDesignation().getId());
				if (!cuList.isEmpty()) {
					CompanyUsers newCompUser = new CompanyUsers(newSdp.getSdp(), company);
					newCompUser.setCompanyUserType(ConfigDocProcessEnum.TP);
					if (newSdp.getSdpType().getDesignation() != null) {
						newCompUser.setDesignation(newSdp.getSdpType().getDesignation());
					}
					newCompUser.setExistingUser(true);
					dataEntities.add(newSdp);
				}
			}
		}
		// Creating Ass/Mod Links
		for (AssessorModeratorCompanySites assModSite : assmodSiteList) {
			assModSite.setApprovalStatus(ApprovalEnum.Approved);
			dataEntities.add(assModSite);
			CompanyUsersService companyUsersService=new CompanyUsersService();
			List<CompanyUsers>  cuList = null;
			if (assModSite.getAssessorModType() != null) {
				cuList=companyUsersService.findByUserAndCompanyAndTypeAndAssessorModType(company.getId(), assModSite.getAssessorModerator().getId(),ConfigDocProcessEnum.TP,assModSite.getAssessorModType());
				if (cuList.isEmpty()) {
					CompanyUsers companyUsers = new CompanyUsers(assModSite.getAssessorModerator(), company);
					companyUsers.setCompanyUserType(ConfigDocProcessEnum.TP);
					if (assModSite.getAssessorModType() != null) {
						companyUsers.setAssessorModType(assModSite.getAssessorModType());
					}
					if (assModSite.getAssessorModerator().getId() == null) {
						companyUsers.setExistingUser(false);
						registerService.register(assModSite.getAssessorModerator(), true);
					} else {
						companyUsers.setExistingUser(true);
						usersService.update(assModSite.getAssessorModerator());
					}
					dataEntities.add(companyUsers);
				}
			}
		}
		// Creating qualifications
		for (LegacyProviderQualification lq : legacyProviderQualificationList) {
			if(lq.getQualification() !=null && lq.getQualificationExpired() !=null && !lq.getQualificationExpired()){
				CompanyQualifications cq=new CompanyQualifications(company, lq.getQualification());
				cq.setManuallyAdded(false);
				cq.setTargetClass(TrainingProviderApplication.class.getName());
				cq.setTargetKey(trainingProviderApplication.getId());
				cq.setCompany(company);
				dataEntities.add(cq);
				lq.setApplicationSubmited(true);
				dataEntitiesUpdate.add(lq);
			}
		}
		// Creating unit standards
		for (LegacyProviderUnitStandard us : legacyProviderUnitStandardList) {
			if(us.getUnitStandard() !=null && us.getUnitStandardExpired() !=null && !us.getUnitStandardExpired()){
				CompanyUnitStandard cu=new CompanyUnitStandard();
				cu.setManuallyAdded(false);
				cu.setTargetClass(TrainingProviderApplication.class.getName());
				cu.setTargetKey(trainingProviderApplication.getId());
				cu.setCompany(company);
				cu.setUnitStandard(us.getUnitStandard());
				dataEntities.add(cu);
				us.setApplicationSubmited(true);
				dataEntitiesUpdate.add(us);
			}
		}
		// Creating Skills Programme
		for (LegacyProviderSkillsProgramme lsp : legacyProviderSkillsProgrammeList) {
			if(lsp.getSkillsProgram() !=null && lsp.getQualificationExpired() !=null && !lsp.getQualificationExpired()) {
				TrainingProviderSkillsProgramme tpSkillsProg = new TrainingProviderSkillsProgramme(lsp.getSkillsProgram(), trainingProviderApplication);
				tpSkillsProg.setManuallyAdded(false);
				tpSkillsProg.setTrainingProviderApplication(trainingProviderApplication);
				dataEntities.add(tpSkillsProg);
				lsp.setApplicationSubmited(true);
				dataEntitiesUpdate.add(lsp);
			}
		}
		// Creating Skills Set
		for (LegacyProviderSkillsProgramme lspss : legacyProviderSkillsProgrammeSkillsSetList) {
			if(lspss.getSkillsSet() !=null && lspss.getQualificationExpired() !=null && !lspss.getQualificationExpired()) {
				TrainingProviderSkillsSet trainingProviderSkillsSet = new TrainingProviderSkillsSet(lspss.getSkillsSet(), trainingProviderApplication);
				trainingProviderSkillsSet.setManuallyAdded(false);
				trainingProviderSkillsSet.setTrainingProviderApplication(trainingProviderApplication);
				dataEntities.add(trainingProviderSkillsSet);
				lspss.setApplicationSubmited(true);
				dataEntitiesUpdate.add(lspss);
			}
		}
		// Creating Learnership
		for (LegacyProviderLearnership ltpLearnership : legacyProviderLearnershipList) {
			if(ltpLearnership.getLearnership() !=null && ltpLearnership.getQualificationExpired() !=null && !ltpLearnership.getQualificationExpired()){
				TrainingProviderLearnership tpLearnership=new TrainingProviderLearnership(ltpLearnership.getLearnership(), trainingProviderApplication);
				tpLearnership.setManuallyAdded(false);
				dataEntities.add(tpLearnership);
				ltpLearnership.setApplicationSubmited(true);
				dataEntitiesUpdate.add(ltpLearnership);
			}	
		}
		if (dataEntities.size() > 0) {
			dao.createBatch(dataEntities);
		}

		/** Creating task */
		String desc = ConfigDocProcessEnum.SDP_LEGACY_APPLICATION.getTaskDescription();
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), null);
		/* Sending acknowledgement  email */
		trainingProviderApplicationService.sendNonMerSetaAcknowledgementEmail(formUser, trainingProviderApplication);
	}

	/**
	 * Adds link to unit standards and qualifications for a company or assessor and
	 * moderator.
	 * 
	 * Based on config will link the appropriate items
	 *
	 * @param unitStandards
	 *            the unit standards
	 * @param qualifications
	 *            the qualifications
	 * @param formUser
	 *            the form user
	 * @param company
	 *            the company
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @throws Exception
	 *             the exception
	 */
	private void linkUserOrCompanyToQualificationUnitStandards(List<UnitStandards> unitStandards, List<Qualification> qualifications, Users formUser, Company company, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();

		// assigns qualifications
		for (Qualification qualification : qualifications) {
			// if assessor moderator will assign to user else to company
			if (configDocProcessEnum == ConfigDocProcessEnum.AM) {
				UserQualifications userQualifications = new UserQualifications(formUser, qualification);
				dataEntities.add(userQualifications);
			} else {
				CompanyQualifications companyQualifications = new CompanyQualifications(company, qualification);
				dataEntities.add(companyQualifications);
			}
		}

		// assigns unit standards
		for (UnitStandards unitStandard : unitStandards) {
			// if assessor moderator will assign to user else to company
			if (configDocProcessEnum == ConfigDocProcessEnum.AM) {
				UserUnitStandard userUnitStandard = new UserUnitStandard(formUser, unitStandard);
				dataEntities.add(userUnitStandard);
			} else {
				CompanyUnitStandard companyUnitStandard = new CompanyUnitStandard(company, unitStandard);
				dataEntities.add(companyUnitStandard);
			}
		}

		// creates batch
		this.dao.createBatch(dataEntities);
	}

	/**
	 * Adds link to unit standards and qualifications for a company or assessor and
	 * moderator.
	 * 
	 * Based on config will link the appropriate items
	 *
	 * @param unitStandards
	 *            the unit standards
	 * @param qualifications
	 *            the qualifications
	 * @param formUser
	 *            the form user
	 * @param company
	 *            the company
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @param targetClass
	 *            the targetClass
	 * @param targetKey
	 *            the targetKey
	 * @throws Exception
	 *             the exception
	 */
	private void linkUserOrCompanyToQualificationUnitStandards(List<UnitStandards> unitStandards, List<Qualification> qualifications, Users formUser, Company company, ConfigDocProcessEnum configDocProcessEnum, String targetClass, Long targetKey) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();

		// assigns qualifications
		for (Qualification qualification : qualifications) {
			// if assessor moderator will assign to user else to company
			if (configDocProcessEnum == ConfigDocProcessEnum.AM) {
				UserQualifications userQualifications = new UserQualifications(formUser, qualification);
				dataEntities.add(userQualifications);
			} else {
				CompanyQualifications companyQualifications = new CompanyQualifications(company, qualification, targetClass, targetKey);
				dataEntities.add(companyQualifications);
			}
		}

		// assigns unit standards
		for (UnitStandards unitStandard : unitStandards) {
			
			// if assessor moderator will assign to user else to company
			if (configDocProcessEnum == ConfigDocProcessEnum.AM) {
				UserUnitStandard userUnitStandard = new UserUnitStandard(formUser, unitStandard);
				dataEntities.add(userUnitStandard);
			} else {
				CompanyUnitStandard companyUnitStandard = new CompanyUnitStandard(company, unitStandard, targetClass, targetKey);
				if(unitStandard.getQualification() !=null){
					companyUnitStandard.setForQualification(unitStandard.getQualification());
				}
				dataEntities.add(companyUnitStandard);
			}
		}

		// creates batch
		this.dao.createBatch(dataEntities);
	}

	/**
	 * Pre register checks.
	 *
	 * @param entity
	 *            the entity
	 * @param formUser
	 *            the form user
	 * @throws Exception
	 *             the exception
	 */
	private void preRegisterChecks(List<Company> entity, Users formUser) throws Exception {
		boolean error = false;
		String err = "";
		if (formUser != null && StringUtils.isBlank(formUser.getEmail())) {
			error = true;
			err = err + "Enter your email address!<br/>";
		}
		for (Company company : entity) {
			if (company.getDocs() != null) {
				for (Doc doc : company.getDocs()) {
					if (doc.getId() != null) {
						DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
						if (docByte != null) doc.setData(docByte.getData());
						if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
							error = true;
							err = err + "Upload <i>" + doc.getConfigDoc().getName() + "</i> for <i>" + company.getCompanyName() + "</i><br/>";
						}
					}
				}
			}
		}
		if (error) throw new Exception(err);
	}

	/**
	 * Pre user register checks.
	 *
	 * @param formUser
	 *            the form user
	 * @throws Exception
	 *             the exception
	 */
	public void preUserRegisterChecks(Users formUser) throws Exception {
		boolean error = false;
		String err = "";
		if (formUser.getDocs() != null) {
			for (Doc doc : formUser.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						error = true;
						err = err + "Upload " + doc.getConfigDoc().getName() + " for " + formUser.getFirstName() + " " + formUser.getLastName() + "\n";
					}
				}
			}
		}
		if (error) throw new Exception(err);
	}

	/**
	 * Search by reg.
	 *
	 * @param criteria
	 *            the criteria
	 * @return the company
	 * @throws Exception
	 *             the exception
	 */
	public Company searchByReg(String criteria) throws Exception {
		return dao.searchByReg(criteria);
	}
	
	public Company findNonMersetaCompany(String criteria) throws Exception {
		return dao.findNonMersetaCompany(criteria);
	}
	

	public Company searchNonSetaByReg(String criteria) throws Exception {
		return dao.searchNonSetaByReg(criteria);
	}

	/**
	 * Search by SDL.
	 *
	 * @param criteria
	 *            the criteria
	 * @return the company
	 * @throws Exception
	 *             the exception
	 */
	public Company searchBySDL(String criteria) throws Exception {
		System.out.println("Inside CompanyService:: searchBySDL :: criteria ---" + criteria);
		
		return dao.searchBySDL(criteria);
	}

	public Company searchBySDLNoJoins(String criteria) throws Exception {
		return dao.searchBySDLNoJoins(criteria);
	}

	/**
	 * JMB 20 08 2018 Search by Accreditation Number with joins
	 * 
	 * @param criteria
	 *            the criteria
	 * @return the company
	 * @see Company
	 * @throws Exception
	 */
	public Company searchByAccreditationNumber(String criteria) throws Exception {
		return dao.searchByAccreditationNumber(criteria);
	}

	/**
	 * JMB 20 08 2018 Search by Accreditation Number with no joins
	 * 
	 * @param criteria
	 *            the criteria
	 * @return the company
	 * @see Company
	 * @throws Exception
	 */
	public Company searchByAccreditationNumberNoJoins(String criteria) throws Exception {
		return dao.searchByAccreditationNumberNoJoins(criteria);
	}

	/**
	 * Search by reg and institution type.
	 *
	 * @param criteria
	 *            the criteria
	 * @param institutionTypeId
	 *            the institution type id
	 * @return the company
	 * @throws Exception
	 *             the exception
	 */
	public Company searchByRegAndInstitutionType(String criteria, Long institutionTypeId) throws Exception {
		return dao.searchByRegAndInstitutionType(criteria, institutionTypeId);
	}

	/**
	 * Prepare new registration.
	 *
	 * @param configDocProcess
	 *            the config doc process
	 * @param company
	 *            the company
	 * @throws Exception
	 *             the exception
	 */
	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, Company company) throws Exception {
		List<ConfigDoc> l = configDocService.findByProcessForReg(configDocProcess);
		if (l != null && l.size() > 0) {
			company.setDocs(new ArrayList<Doc>());
			l.forEach(a -> {
				company.getDocs().add(new Doc(a, company));
			});
		}
	}

	/**
	 * Prepare new registration type.
	 *
	 * @param configDocProcess
	 *            the config doc process
	 * @param company
	 *            the company
	 * @param user
	 *            the user
	 * @param companyUserType
	 *            the company user type
	 * @throws Exception
	 *             the exception
	 */
	public void prepareNewRegistrationType(ConfigDocProcessEnum configDocProcess, Company company, Users user, CompanyUserTypeEnum companyUserType) throws Exception {
		List<ConfigDoc> l = configDocService.findByProcessForRegByCompanyUserTypeEnum(configDocProcess, companyUserType);
		if (company != null) {
			if (l != null && l.size() > 0) {
				company.setDocs(new ArrayList<Doc>());
				l.forEach(a -> {
					company.getDocs().add(new Doc(a, company));
				});
			}
		} else {
			user.setDocs(new ArrayList<Doc>());
			if (l != null && l.size() > 0) {
				user.setDocs(new ArrayList<Doc>());
				l.forEach(a -> {
					user.getDocs().add(new Doc(a, company));
				});
			}
		}
	}
	
	/**
	 * Prepare new registration type now including company registartion documents.
	 *
	 * @param configDocProcess
	 *            the config doc process
	 * @param company
	 *            the company
	 * @param user
	 *            the user
	 * @param companyUserType
	 *            the company user type
	 * @throws Exception
	 *             the exception
	 */
	public void prepareNewRegistrationTypeRegistartionCertificate(ConfigDocProcessEnum configDocProcess, Company company, Users user, CompanyUserTypeEnum companyUserType) throws Exception {
		List<ConfigDoc> l = configDocService.findByProcessForRegByCompanyUserTypeEnum(configDocProcess, companyUserType);
		if (company != null) {
			if (l != null && l.size() > 0) {
				company.setDocs(new ArrayList<Doc>());
				l.forEach(a -> {
					if(company.getOrganisationType() == null || Boolean.FALSE.equals(company.getOrganisationType().getProvideRegistrationCertificateOnReg())){
						if (a.getSdfRegistartionDoc() == null || a.getSdfRegistartionDoc() != SdfRegistartionDocEnum.RegistrationCertificate) {
							company.getDocs().add(new Doc(a, company));
						}
					}else{
						company.getDocs().add(new Doc(a, company));
					}
				});
			}
		} else {
			user.setDocs(new ArrayList<Doc>());
			if (l != null && l.size() > 0) {
				user.setDocs(new ArrayList<Doc>());
				l.forEach(a -> {
					if (a.getSdfRegistartionDoc() == null || a.getSdfRegistartionDoc() != SdfRegistartionDocEnum.RegistrationCertificate) {
						user.getDocs().add(new Doc(a, company));
					}
				});
			}
		}
	}

	/**
	 * Prepare docs.
	 *
	 * @param configDocProcess
	 *            the config doc process
	 * @param company
	 *            the company
	 * @param companyUserType
	 *            the company user type
	 * @throws Exception
	 *             the exception
	 */
	public void prepareDocs(ConfigDocProcessEnum configDocProcess, Company company, CompanyUserTypeEnum companyUserType) throws Exception {
		if (company.getDocs() == null) {
			company.setDocs(new ArrayList<Doc>());
		}
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(company, configDocProcess, companyUserType);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				company.getDocs().add(new Doc(a, company));
			});
		}
	}

	public void prepareDocs(ConfigDocProcessEnum configDocProcess, Company company) throws Exception {
		company.setDocs(DocService.instance().searchByCompany(company, configDocProcess));
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
		company.getResidentialAddress().setCompany(null);
		company.getPostalAddress().setCompany(null);

		CompanyHistoryService.instance().createHistory(company.getId());
		if (company.getId() != null) {
			AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
			dao.update(company);
		} else {
			AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
			dao.create(company);
		}
	}

	public void upadeCompanyInfo(Company company) throws Exception {
		dao.update(company);
	}

	public void saveWithLinkedTask(Users users, Company company) throws Exception {
		company.getResidentialAddress().setCompany(null);
		company.getPostalAddress().setCompany(null);

		if (company.getId() == null) {
			AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
			company.setCompanyStatus(CompanyStatusEnum.Pending);
			dao.create(company);
			// Creating change reason
			ChangeReasonService.instance().createChangeReason(company.getId(), company.getClass().getName(), CompanyInfoUI.changeReason);
			// New Linked Company
			String desc = "Linked Company has been added,Please approve the information provided";
			TasksService.instance().findFirstInProcessAndCreateTask(desc, users, company.getId(), company.getClass().getName(), true, ConfigDocProcessEnum.NEW_LINKED_COMPANY, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, ""), null);

		} else {
			CompanyHistoryService.instance().createHistory(company.getId());
			AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
			company.setCompanyStatus(CompanyStatusEnum.Pending);
			dao.update(company);
			// Creating change reason
			ChangeReasonService.instance().createChangeReason(company.getId(), company.getClass().getName(), CompanyInfoUI.changeReason);
			// Linked Company Changes
			String desc = "Linked Company details has been updated,Please approve the information provided";
			TasksService.instance().findFirstInProcessAndCreateTask(desc, users, company.getId(), company.getClass().getName(), true, ConfigDocProcessEnum.LINKED_COMPANY_CHANGE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, ""), null);

		}
	}

	public void rejectWithLinkedTask(Users users, Company company) throws Exception {
		company.getResidentialAddress().setCompany(null);
		company.getPostalAddress().setCompany(null);
		CompanyHistoryService.instance().createHistory(company.getId());
		if (company.getId() != null) {
			AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
			dao.update(company);
		} else {
			AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
			dao.create(company);
		}
		TasksService.instance().findFirstInProcessAndCreateTask("", users, company.getId(), company.getClass().getName(), true, ConfigDocProcessEnum.REMOVE_LINKED_COMPANY, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, ""), null);
	}

	public void rejectDeregisterWithLinkedTask(Users users, Company company) throws Exception {
		company.getResidentialAddress().setCompany(null);
		company.getPostalAddress().setCompany(null);
		CompanyHistoryService.instance().createHistory(company.getId());
		if (company.getId() != null) {
			AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
			dao.update(company);
		} else {
			AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
			dao.create(company);
		}
		TasksService.instance().findFirstInProcessAndCreateTask("", users, company.getId(), company.getClass().getName(), true, ConfigDocProcessEnum.REMOVE_LINKED_COMPANY, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, ""), null);
	}

	/**
	 * Check if already registered.
	 *
	 * @param user
	 *            the user
	 * @param company
	 *            the company
	 * @throws ValidationException
	 *             the validation exception
	 * @throws Exception
	 *             the exception
	 */
	public void checkIfAlreadyRegistered(Users user, Company company) throws ValidationException, Exception {
		if (findByCompanyAndUser(company, user) != null) {
			throw new ValidationException("Company " + company.getCompanyName() + " is already registered for this SDF");
		}
	}

	public void regNumberValidiation(Company company) throws Exception {
		if (countByRegNumber(company.getCompanyRegistrationNumber()) != 0) {
			throw new Exception("Company: " + company.getCompanyName() + "'s Registration Number Has Already Been Registered On The NSDMS");
		}
	}

	/**
	 * Locates documents uploaded against to company.
	 *
	 * @param companyList
	 *            the company list
	 * @return List<Company>
	 * @throws Exception
	 *             the exception
	 */
	public List<Company> resolveDocsCompanyList(List<Company> companyList) throws Exception {
		companyList.forEach(a -> {
			try {
				a.setDocs(docService.searchByCompany(a));
			} catch (Exception e) {
				logger.fatal(e);
			}
		});
		return companyList;
	}

	/**
	 * Locates documents uploaded against to company.
	 *
	 * @param company
	 *            the company
	 * @return Company
	 * @throws Exception
	 *             the exception
	 */
	public Company resolveDocsCompany(Company company) throws Exception {
		company.setDocs(docService.searchByCompany(company));
		return company;
	}

	/**
	 * Approve the task.
	 *
	 * @param task
	 *            the task
	 * @param user
	 *            the user
	 * @param tp
	 *            the tp
	 * @throws Exception
	 *             the exception
	 */
	public void approveTask(Tasks task, Users user, Company tp, TrainingProviderApplication trainingProviderApplication, ReviewCommitteeMeeting reviewCommitteeMeeting) throws Exception {
		/** Setting user who action the task */
		setTrainingProviderUsers(task, user, trainingProviderApplication);

		List<IDataEntity> iDataEntities = new ArrayList<>();
		if (tp.getCompanyStatus() == CompanyStatusEnum.Pending) {
			tp.setCompanyStatus(CompanyStatusEnum.Active);
		}
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
			trainingProviderApplication.setReviewCommitteeMeeting(reviewCommitteeMeeting);
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingCommitteeApproval);
			iDataEntities.add(trainingProviderApplication);
		} else if (trainingProviderApplication.getApprovalStatus() == ApprovalEnum.Rejected) {
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
			iDataEntities.add(trainingProviderApplication);
		}

		assignMeetingAgender(trainingProviderApplication, user);
		iDataEntities.add(tp);
		dao.updateBatch(iDataEntities);
		if (task.getProcessRole().getRoleOrder() == 1) {
			ArrayList<Users> usersList = new ArrayList<>();
			usersList.add(trainingProviderApplication.getUsers());
			completeTask("New Training Provider for approval", trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), task, user, MailDef.instance(MailEnum.TPNewRequiresReview, MailTagsEnum.CompanyName, tp.getCompanyName()), null, usersList);
		} else {
			completeTask("New Training Provider for approval", trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), task, user, MailDef.instance(MailEnum.TPNewRequiresReview, MailTagsEnum.CompanyName, tp.getCompanyName()), null);
		}

		/*if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
			if (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
				trainingProviderApplicationService.sendETQTP009LearningProgrammeApproval(trainingProviderApplication, true, trainingProviderApplication.getUsers());
			}
		}*/
	}

	/**
	 * Approve the task.
	 *
	 * @param task
	 *            the task
	 * @param user
	 *            the user
	 * @param tp
	 *            the tp
	 * @throws Exception
	 *             the exception
	 */
	public void approveSDPReAccreditationTask(Tasks task, Users user, Company tp, TrainingProviderApplication trainingProviderApplication, ReviewCommitteeMeeting reviewCommitteeMeeting, SDPReAccreditation sdpReAccreditation) throws Exception {
		/** Setting user who action the task */
		setTrainingProviderUsers(task, user, trainingProviderApplication);

		List<IDataEntity> iDataEntities = new ArrayList<>();
		if (tp.getCompanyStatus() == CompanyStatusEnum.Pending) {
			tp.setCompanyStatus(CompanyStatusEnum.Active);
		}
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
			trainingProviderApplication.setReviewCommitteeMeeting(reviewCommitteeMeeting);
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingCommitteeApproval);
			sdpReAccreditation.setStatus(ApprovalEnum.PendingCommitteeApproval);
			iDataEntities.add(trainingProviderApplication);
			iDataEntities.add(sdpReAccreditation);
		} else if (trainingProviderApplication.getApprovalStatus() == ApprovalEnum.Rejected) {
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
			iDataEntities.add(trainingProviderApplication);
			sdpReAccreditation.setStatus(ApprovalEnum.PendingApproval);
			iDataEntities.add(sdpReAccreditation);
		}

		assignMeetingAgender(trainingProviderApplication, user);
		iDataEntities.add(tp);
		dao.updateBatch(iDataEntities);
		if (task.getProcessRole().getRoleOrder() == 1) {
			ArrayList<Users> usersList = new ArrayList<>();
			usersList.add(trainingProviderApplication.getUsers());
			completeTask("Training Provider Re-Accreditation for approval", sdpReAccreditation.getId(), sdpReAccreditation.getClass().getName(), task, user, MailDef.instance(MailEnum.Task, MailTagsEnum.CompanyName, tp.getCompanyName()), null, usersList);
		} else {
			completeTask("Training Provider Re-Accreditation for approval", sdpReAccreditation.getId(), sdpReAccreditation.getClass().getName(), task, user, MailDef.instance(MailEnum.Task, MailTagsEnum.CompanyName, tp.getCompanyName()), null);
		}

		/*
		 * if(task.getProcessRole().getRolePermission() ==
		 * UserPermissionEnum.FinalApproval) {
		 * if(trainingProviderApplication.getAccreditationApplicationTypeEnum()==
		 * AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
		 * trainingProviderApplicationService.sendETQTP009LearningProgrammeApproval(
		 * trainingProviderApplication, true, trainingProviderApplication.getUsers()); }
		 * }
		 */
	}

	public void setTrainingProviderUsers(Tasks task, Users user, TrainingProviderApplication trainingProviderApplication) throws Exception {
		if (task.getProcessRole().getRoleOrder() == 1)// Quality Assuror
		{
			trainingProviderApplication.setQualityAssuranceUser(user);
		} else if (task.getProcessRole().getRoleOrder() == 3)// Manager: Quality Assurance
		{
			trainingProviderApplication.setManagerQualityAssurance(user);
			trainingProviderApplication.setRecommendedDate(new Date());
		} else if (task.getProcessRole().getRoleOrder() == 4)// Approved for Recommendation to Review Committee by(Senior Manager: Quality
															 // Assurance & Partnerships )
		{
			trainingProviderApplication.setSeniorManagerQualityAssurance(user);
			trainingProviderApplication.setRecommendedToReviewCommDate(new Date());
		}
		TrainingProviderApplicationService.instance().update(trainingProviderApplication);
	}

	public void assignMeetingAgender(TrainingProviderApplication trainingProviderApplication, Users user) throws Exception {
		ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaSevice = new ReviewCommitteeMeetingAgendaService();
		ScheduledEventService scheduledEventService = new ScheduledEventService();
		ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService = new ReviewCommitteeMeetingUsersService();
		if (trainingProviderApplication.getReviewCommitteeMeeting() != null) {
			Long meetingAgendaID = HAJConstants.SDP_MEETING_SCHEDULE_TYPE_ID;// TrainingProviderApplication Approval ID
			ReviewCommitteeMeetingAgenda meetingAgender = reviewCommitteeMeetingAgendaSevice.findByMeetingAgendaAndReviewCommitteeMeeting(meetingAgendaID, trainingProviderApplication.getReviewCommitteeMeeting().getId());
			if (meetingAgender != null) {
				trainingProviderApplication.setReviewCommitteeMeetingAgenda(meetingAgender);
			} else {
				throw new Exception("Please add TrainingProviderApplication Approval to the agenda of the selected Review Committee meeting");
			}
			// Adding meeting details to Events schedule
			List<Users> userList = reviewCommitteeMeetingUsersService.findUsersByReviewCommitteeMeeting(trainingProviderApplication.getReviewCommitteeMeeting().getId());
			scheduledEventService.addSheduleInfo(TrainingProviderApplication.class.getName(), trainingProviderApplication.getId(), user, userList, null, null, meetingAgender.getMeetingAgenda().getDescription(), trainingProviderApplication.getReviewCommitteeMeeting());
		}

	}

	/**
	 * Complete task.
	 *
	 * @param desc
	 *            the desc
	 * @param targetKey
	 *            the target key
	 * @param targetClass
	 *            the target class
	 * @param task
	 *            the task
	 * @param user
	 *            the user
	 * @throws Exception
	 *             the exception
	 */
	public void completeTask(String desc, Long targetKey, String targetClass, Tasks task, Users user, MailDef mailDef, List<AuditorMonitorReview> auditorMonitorReviews) throws Exception {
		if (auditorMonitorReviews != null) this.dao.updateBatch((List<IDataEntity>) (List<?>) auditorMonitorReviews);
		TasksService.instance().findNextInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, null);
	}

	public void completeTpTask(String desc, Long targetKey, String targetClass, Tasks task, Users user, MailDef mailDef, List<AuditorMonitorReview> auditorMonitorReviews) throws Exception {
		if (auditorMonitorReviews != null) this.dao.updateBatch((List<IDataEntity>) (List<?>) auditorMonitorReviews);
		TasksService.instance().findNextInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, null);
	}

	public void completeTask(String desc, Long targetKey, String targetClass, Tasks task, Users user, MailDef mailDef, List<AuditorMonitorReview> auditorMonitorReviews, ArrayList<Users> userList) throws Exception {
		if (auditorMonitorReviews != null) this.dao.updateBatch((List<IDataEntity>) (List<?>) auditorMonitorReviews);
		TasksService.instance().findNextInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, userList);
	}

	
	public void completeTPTask(String desc, Long targetKey, String targetClass, Tasks task, Users user, MailDef mailDef, List<AuditorMonitorReview> auditorMonitorReviews, TrainingProviderApplication trainingProviderApplication) throws Exception {
		/** Setting user who action the task */
		setTrainingProviderUsers(task, user, trainingProviderApplication);
		Boolean uploadEvidence=false;
		if (auditorMonitorReviews != null) this.dao.updateBatch((List<IDataEntity>) (List<?>) auditorMonitorReviews);
		if(task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload)
		{
			List<Users> qualityAssurorUsers = new ArrayList<>();
			
			if(trainingProviderApplication.getTrainingSite() == null || trainingProviderApplication.getTrainingSite().getId() == null){
				qualityAssurorUsers =getQualityAssurorUsers(trainingProviderApplication.getCompany());
			} else {
				qualityAssurorUsers =getQualityAssurorUsers(trainingProviderApplication.getTrainingSite());
			}
			
			if(trainingProviderApplication.getApprovalStatus() == ApprovalEnum.PendingResubmition)
			{ 
				ProcessRolesService processRolesService= new ProcessRolesService();
				List<ProcessRoles> first = processRolesService.findNextProcessRoles(task.getProcessRole());
				if(first!=null && first.size()>0)
				{
					task.setProcessRole(first.get(0));
				}
			}
			TasksService.instance().findNextInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, qualityAssurorUsers);
		}
		else
		{

			if(trainingProviderApplication.getApprovalStatus() == ApprovalEnum.PendingResubmition)
			{ 
				ProcessRolesService processRolesService= new ProcessRolesService();
				List<ProcessRoles> first = processRolesService.findNextProcessRoles(task.getProcessRole());
				if(first!=null && first.size()>0)
				{
					task.setProcessRole(first.get(0));
				}
			}
			else if(task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit){
				
				if (auditorMonitorReviewService.countByTargetClassKeyWhereOutcomeIsType(targetClass, targetKey, YesNoEnum.No) > 0) {
					uploadEvidence=true;
				}
//				for(AuditorMonitorReview selfEv: auditorMonitorReviews)
//				{
//					if(selfEv.getEvidenceRequiredEvaluatorOutcome() !=null && selfEv.getEvidenceRequiredEvaluatorOutcome()==YesNoEnum.No && (selfEv.getDocs()==null || selfEv.getDocs().size()<1))
//					{
//						uploadEvidence=true;
//						break;
//					}
//				}
				if(uploadEvidence){
					/**
					 * If Evidence Required,
					 * should get sent back to the SDP to upload evidence.
					 *  */
					ArrayList<Users> userList=new ArrayList<>();
					userList.add(trainingProviderApplication.getUsers());
					String description="";
					TasksService.instance().findPreviousInProcessAndCreateTask(description, user, targetKey, targetClass, true, task, mailDef, userList);
				}
				else{
				
					TasksService.instance().findNextInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, null);
				}
			}
			else
			{
				TasksService.instance().findNextInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, null);
			}
		}
		
		/** Sending Site Visit Report */
		if (trainingProviderApplication.getSiteVisitDone() && trainingProviderApplication.getApprovalStatus() != ApprovalEnum.PendingResubmition) {
			trainingProviderApplicationService.sendETQTP004AccreditationSiteVisitReport(trainingProviderApplication, true, trainingProviderApplication.getUsers(),uploadEvidence);
			/*if (trainingProviderApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL) {
				trainingProviderApplicationService.sendETQTP004AccreditationSiteVisitReport(trainingProviderApplication, true, trainingProviderApplication.getUsers(),uploadEvidence);
			}*/

		}

		if (trainingProviderApplication.getApprovalStatus() == ApprovalEnum.Rejected || trainingProviderApplication.getApprovalStatus() == ApprovalEnum.PendingResubmition) {
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
			trainingProviderApplicationService.update(trainingProviderApplication);
		} else {
			trainingProviderApplicationService.update(trainingProviderApplication);
		}
	
	}

	public void completeTPTask(String desc, Long targetKey, String targetClass, Tasks task, Users user, MailDef mailDef, List<AuditorMonitorReview> auditorMonitorReviews, TrainingProviderApplication trainingProviderApplication, SDPReAccreditation sdpReAccreditation) throws Exception {
		SDPReAccreditationService sdpReAccreditationSevice = new SDPReAccreditationService();
		/** Setting user who action the task */
		setTrainingProviderUsers(task, user, trainingProviderApplication);
		List<Users> usersByRegion = new ArrayList<>();

		ProcessRolesService processRolesService = new ProcessRolesService();
		ProcessRoles pr = processRolesService.findByKey(task.getProcessRole().getId());
		if (pr != null && pr.getNextTaskRole() != null) {
			HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
			TownService townService = new TownService();
			Address address = null;
			if (trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null) {
				if (trainingProviderApplication.getTrainingSite().getResidentialAddress().getId() != null) {
					address = AddressService.instance().findByKey(trainingProviderApplication.getTrainingSite().getResidentialAddress().getId());
				}else {
					if (trainingProviderApplication.getCompany().getResidentialAddress().getId() != null) {
						address = AddressService.instance().findByKey(trainingProviderApplication.getCompany().getResidentialAddress().getId());
					}
				}
			}else {
				if (trainingProviderApplication.getCompany().getResidentialAddress().getId() != null) {
					address = AddressService.instance().findByKey(trainingProviderApplication.getCompany().getResidentialAddress().getId());
				}
			}
			
			if (address != null && address.getTown() != null) {
				usersByRegion = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(pr.getNextTaskRole().getId()));
			}
		}

		if (auditorMonitorReviews != null) this.dao.updateBatch((List<IDataEntity>) (List<?>) auditorMonitorReviews);

		if (usersByRegion.size() == 0) {
			TasksService.instance().findNextInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, null);
		} else {
			TasksService.instance().findNextInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, usersByRegion);
		}

		if (trainingProviderApplication.getApprovalStatus() == ApprovalEnum.Rejected) {
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingApproval);
			trainingProviderApplicationService.update(trainingProviderApplication);
		} else {
			trainingProviderApplicationService.update(trainingProviderApplication);
		}
		if (sdpReAccreditation.getStatus() == ApprovalEnum.Rejected) {
			sdpReAccreditation.setStatus(ApprovalEnum.PendingApproval);
			sdpReAccreditationSevice.update(sdpReAccreditation);
		}
		/** Sending Site Visit Report */
		if (trainingProviderApplication.getSiteVisitDone()) {
			trainingProviderApplicationService.sendETQTP060ReAccreditationReport(trainingProviderApplication, true, trainingProviderApplication.getUsers());
		}
	}

	public void completeToFirst(Users formUser, Tasks task, AssessorModeratorApplication amApplication) throws Exception {
		TasksService.instance().completeTask(task);
		TasksService.instance().findFirstInProcessAndCreateTask(formUser, amApplication.getId(), amApplication.getClass().getName(), ConfigDocProcessEnum.AM, false);
	}

	public void completeChangeRequest(Company company, Tasks task, Users user) throws Exception {
		company.setCompanyStatus(CompanyStatusEnum.Active);
		updateNoHistory(company);
		SDFCompany cu = findPrimarySDF(company);
		GenericUtility.sendMail(cu.getSdf().getEmail(), "Company Change Approved", "Your change to company " + company.getCompanyName() + " (" + company.getLevyNumber() + ") has been approved on the merSETA NSDMS system.", null);
		completeTask("Company Change was approved.", company.getId(), company.getClass().getName(), task, user, null, null);
	}

	public void approveLinkedCompanyTask(Company company, Tasks task) throws Exception {
		company.setCompanyStatus(CompanyStatusEnum.Approved);
		update(company);
		TasksService.instance().completeTask(task);
		GenericUtility.sendMail(task.getActionUser().getEmail(), "Linked Company Approval", "Linked company (" + company.getCompanyName() + ") has been approved on the merSETA NSDMS system.", null);
	}

	public void approveLinkedCompanyChangesTask(Company company, Tasks task) throws Exception {
		company.setCompanyStatus(CompanyStatusEnum.Approved);
		update(company);
		TasksService.instance().completeTask(task);
		GenericUtility.sendMail(task.getActionUser().getEmail(), "Linked Company Changes Approval", "Linked company (" + company.getCompanyName() + ") changes has been approved on the merSETA NSDMS system.", null);
	}

	public void rejectLinkedCompanyChangesTask(Company company, CompanyHistory companyHistory, Tasks task) throws Exception {

		Long tempId = company.getId();
		BeanUtils.copyProperties(company, companyHistory);
		company.setId(tempId);
		update(company);
		SDFCompany cu = findPrimarySDF(company);
		dao.delete(cu);
		TasksService.instance().completeTask(task);
		GenericUtility.sendMail(task.getActionUser().getEmail(), "Linked Company Changes Rejection", "Linked company (" + company.getCompanyName() + ") changes has been rejected on the merSETA NSDMS system.", null);
	}

	public void rejectLinkedCompanyTask(Company company, Tasks task) throws Exception {
		company.setCompanyStatus(CompanyStatusEnum.Rejected);
		update(company);
		TasksService.instance().completeTask(task);
		GenericUtility.sendMail(task.getActionUser().getEmail(), "Linked Company Rejection", "Linked company (" + company.getCompanyName() + ") has been rejected on the merSETA NSDMS system.", null);
	}

	public void approveRemoveLinkedCompany(Company linkedComp, Tasks task) throws Exception {
		// Delete Company History
		CompanyHistoryService.instance().removeCompanyHistory(linkedComp);
		// Deleting Company
		delete(linkedComp);
		TasksService.instance().completeTask(task);
		// Sending email to task creator
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Linked Company Approval", "The attempt to delete linked company (" + linkedComp.getCompanyName() + ") has been approve on the merSETA NSDMS system.", null);
	}

	public void rejectRemoveLinkedCompany(Company linkedComp, Tasks task) throws Exception {
		linkedComp.setCompanyStatus(CompanyStatusEnum.Rejected);
		update(linkedComp);
		TasksService.instance().completeTask(task);
		UsersService usersService = new UsersService();
		// Sending email to user who created the SDF
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Linked Company Rejection", " Your attempt to delete linked company (" + linkedComp.getCompanyName() + ") has been rejected on the merSETA NSDMS system.", null);

	}

	public void rejectChangeRequest(Company company, Tasks task, Users user) throws Exception {
		CompanyHistory ch = null;
		for (CompanyHistory iterable_element : CompanyHistoryService.instance().findByCompanyLatest(company)) {
			ch = iterable_element;
			break;
		}

		long id = company.getId();
		if (ch != null) BeanUtils.copyProperties(company, ch);
		company.setId(id);
		company.setCompanyStatus(CompanyStatusEnum.Active);
		updateNoHistory(company);
		SDFCompany cu = findPrimarySDF(company);
		GenericUtility.sendMail(cu.getSdf().getEmail(), "Company Change rejected", "Your change to company " + company.getCompanyName() + " (" + company.getLevyNumber() + ") has been rejected on the merSETA NSDMS system.", null);
		completeTask("Company Change was rejected.", company.getId(), company.getClass().getName(), task, user, null, null);
	}

	public void completeRemoveLinkedRequest(Company company, Tasks task, Users user) throws Exception {
		CompanyHistory ch = null;
		for (CompanyHistory iterable_element : CompanyHistoryService.instance().findByCompanyOldest(company)) {
			ch = iterable_element;
			break;
		}
		long id = company.getId();
		if (ch != null) BeanUtils.copyProperties(company, ch);
		company.setId(id);
		company.setCompanyStatus(CompanyStatusEnum.Pending);
		updateNoHistory(company);
		SDFCompany cu = findPrimarySDF(company);
		GenericUtility.sendMail(cu.getSdf().getEmail(), "Linked Company Change Approved", "Your change to linked company " + company.getCompanyName() + " (" + company.getLevyNumber() + ") has been approved on the merSETA NSDMS system.", null);
		TasksService.instance().findByPositionAndCreateTask(1, "", user, cu.getId(), cu.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), ConfigDocProcessEnum.SDF, null, "", true);
	}

	public void completeDeregisterLinkedCompanyTask(Company parentCmpany, Company company, Tasks task, Users user) throws Exception {
		SDFCompany cu = removeLink(company);
		if (cu != null) {
			GenericUtility.sendMail(cu.getSdf().getEmail(), "Linked Company Change Approved", "Your change to linked company " + company.getCompanyName() + " (" + company.getLevyNumber() + ") has been approved and the company has been removed from your profile on the merSETA NSDMS system.", null);

		}
		completeTask("Linked Company Change was approved.", company.getId(), company.getClass().getName(), task, user, null, null);
	}

	public SDFCompany removeLink(Company company) throws Exception, IllegalAccessException, InvocationTargetException {
		// CompanyHistory ch = null;
		// for (CompanyHistory iterable_element :
		// CompanyHistoryService.instance().findByCompanyOldest(company)) {
		// ch = iterable_element;
		// break;
		// }
		long id = company.getId();
		// if (ch != null) BeanUtils.copyProperties(company, ch);
		company.setId(id);
		company.setCompanyStatus(CompanyStatusEnum.Pending);
		company.setLinkedCompany(null);
		SDFCompany cu = findPrimarySDF(company);
		if (cu != null) {
			CompanyUsers compUser = new CompanyUsersService().findByUserAndCompany(company.getId(), cu.getSdf().getId());
			dao.delete(cu);
			dao.delete(compUser);

		}
		updateNoHistory(company);
		return cu;
	}

	public void rejectRemoveLinkedRequest(Company company, Tasks task, Users user) throws Exception {
		CompanyHistory ch = null;
		for (CompanyHistory iterable_element : CompanyHistoryService.instance().findByCompanyLatest(company)) {
			ch = iterable_element;
			break;
		}
		long id = company.getId();
		if (ch != null) BeanUtils.copyProperties(company, ch);
		company.setId(id);
		company.setCompanyStatus(CompanyStatusEnum.Active);
		updateNoHistory(company);
		SDFCompany cu = findPrimarySDF(company);
		GenericUtility.sendMail(cu.getSdf().getEmail(), "Linked Company Change rejected", "Your change to linked company " + company.getCompanyName() + " (" + company.getLevyNumber() + ") has been rejected on the merSETA NSDMS system.", null);
		completeTask("Linked Company Change was rejected.", company.getId(), company.getClass().getName(), task, user, null, null);
	}

	public SDFCompany findPrimarySDF(Company company) throws Exception {
		return dao.findPrimarySDF(company.getId());
	}

	/**
	 * Rejects the task.
	 *
	 * @param desc
	 *            the desc
	 * @param targetKey
	 *            the target key
	 * @param targetClass
	 *            the target class
	 * @param task
	 *            the task
	 * @param user
	 *            the user
	 * @param trainingProviderApplication
	 * @throws Exception
	 *             the exception
	 */
	public void rejectTask(String desc, Long targetKey, String targetClass, Tasks task, Users user, MailDef mailDef) throws Exception {
		TasksService.instance().findPreviousInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, null);

	}

	public void rejectNonSetaTask(String desc, Long targetKey, String targetClass, Tasks task, Users user, MailDef mailDef, ArrayList<RejectReasons> selectedRejectReason, TrainingProviderApplication trainingProviderApplication, Boolean finalReject) throws Exception {

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(targetKey, targetClass, selectedRejectReason, user, ConfigDocProcessEnum.TP);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}

		}
		if (finalReject) {
			trainingProviderApplication.setFinalRejected(true);
			trainingProviderApplication.setFinalRejectDate(new Date());
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.Rejected);
			trainingProviderApplication.setProviderStatus(ProviderStatusService.instance().findByCode("515"));
			trainingProviderApplicationService.update(trainingProviderApplication);
			//trainingProviderApplicationService.sendFinalRejectEmail(trainingProviderApplication.getUsers(), selectedRejectReason);
			trainingProviderApplicationService.sendNonMerSetaFinalRejectEmail(trainingProviderApplication.getUsers(), selectedRejectReason, trainingProviderApplication);
			TasksService.instance().completeTask(task);
		} else {
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.Rejected);
			trainingProviderApplicationService.update(trainingProviderApplication);
			List<Users> list = new ArrayList<>();
			list.add(trainingProviderApplication.getUsers());
			// TasksService.instance().findPreviousInProcessAndCreateTask(user,task,list);
			TasksService.instance().findFirstInProcessAndCreateTask(desc, user, trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), true, ConfigDocProcessEnum.NON_SETA_PROVIDERS, mailDef, list);
			TasksService.instance().completeTask(task);
		}

	}

	public void rejectTPTask(String desc, Long targetKey, String targetClass, Tasks task, Users user, MailDef mailDef, ArrayList<RejectReasons> selectedRejectReason, TrainingProviderApplication trainingProviderApplication) throws Exception {

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), selectedRejectReason, user, ConfigDocProcessEnum.TP);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}

		}
		if (trainingProviderApplication.getApprovalStatus() == ApprovalEnum.RejectedByEQTA) {
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.Rejected);
			trainingProviderApplication.setFinalRejected(true);
			trainingProviderApplication.setFinalRejectDate(new Date());
			trainingProviderApplication.setProviderStatus(ProviderStatusService.instance().findByCode("515"));
			trainingProviderApplicationService.update(trainingProviderApplication);
			//trainingProviderApplicationService.sendFinalRejectEmail(trainingProviderApplication.getUsers(), selectedRejectReason);
			trainingProviderApplicationService.sendTPFinalRejection(trainingProviderApplication.getUsers(), selectedRejectReason, trainingProviderApplication);
			TasksService.instance().completeTask(task);
		} else {
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.Rejected);
			trainingProviderApplicationService.update(trainingProviderApplication);
			
			if(task.getProcessRole().getRoleOrder()==1)
			{
				List<Users> usersList=new ArrayList<>();
				usersList.add(trainingProviderApplication.getUsers());
				TasksService.instance().findFirstInProcessAndCreateRejectTask(desc, user, targetKey, targetClass, true, task.getWorkflowProcess(), mailDef, usersList);
				TasksService.instance().completeTask(task);
			}
			else
			{
				List<Users> usersList=findReleventUsers(task, trainingProviderApplication);
				TasksService.instance().findPreviousInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, usersList);
				TasksService.instance().completeTask(task);
			}
		}
	}
	
	public List<Users> findReleventUsers(Tasks task,TrainingProviderApplication trainingProviderApplication) throws Exception
	{
		List<Users> usersList=new ArrayList<>();
		if(task.getProcessRole().getRoleOrder().equals(1))//To Applicant
		{
			usersList.add(trainingProviderApplication.getUsers());
		}
		else if(task.getProcessRole().getRoleOrder().equals(2))//To Quality Assuror
		{
//			usersList=getQualityAssurorUsers(trainingProviderApplication.getCompany());
			if(trainingProviderApplication.getTrainingSite() == null || trainingProviderApplication.getTrainingSite().getId() == null){
				usersList =getQualityAssurorUsers(trainingProviderApplication.getCompany());
			} else {
				usersList =getQualityAssurorUsers(trainingProviderApplication.getTrainingSite());
			}
		}
		else if(task.getProcessRole().getRoleOrder().equals(3))//To Applicant
		{
			usersList.add(trainingProviderApplication.getUsers());
		}
		else if(task.getProcessRole().getRoleOrder().equals(4))//To Quality Assuror
		{
//			usersList=getQualityAssurorUsers(trainingProviderApplication.getCompany());
			if(trainingProviderApplication.getTrainingSite() == null || trainingProviderApplication.getTrainingSite().getId() == null){
				usersList =getQualityAssurorUsers(trainingProviderApplication.getCompany());
			} else {
				usersList =getQualityAssurorUsers(trainingProviderApplication.getTrainingSite());
			}
		}
		
		return usersList;
	}
	
	
	public List<Users> getQualityAssurorUsers(Company company) throws Exception
	{
		HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
		TownService townService = new TownService();
		List<Users> qualityAssurorUsers = new ArrayList<>();
		Address address = null;
		if (company.getResidentialAddress().getId() != null) {
			address = AddressService.instance().findByKey(company.getResidentialAddress().getId());
		}
		if (address != null && address.getTown() != null) {
			qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
		}
		
		if(qualityAssurorUsers==null || qualityAssurorUsers.size()<1)
		{
			throw new Exception("We are unable to locate CLO for this company ("+company.getCompanyName()+"), Please contact support.");
		}
		
		return qualityAssurorUsers;
	}
	
	public List<Users> getQualityAssurorUsers(TrainingSite trainingSite) throws Exception
	{
		HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
		TownService townService = new TownService();
		List<Users> qualityAssurorUsers = new ArrayList<>();
		Address address = null;
		if (trainingSite.getResidentialAddress().getId() != null) {
			address = AddressService.instance().findByKey(trainingSite.getResidentialAddress().getId());
		}
		if (address != null && address.getTown() != null) {
			qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
		}
		
		if(qualityAssurorUsers==null || qualityAssurorUsers.size()<1)
		{
			throw new Exception("We are unable to locate CLO for this company ("+trainingSite.getCompany().getCompanyName()+"), Please contact support.");
		}
		
		return qualityAssurorUsers;
	}
	
	public void finalRejectTPTask(String desc, Tasks task, Users user, MailDef mailDef, ArrayList<RejectReasons> selectedRejectReason, TrainingProviderApplication trainingProviderApplication) throws Exception {

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(trainingProviderApplication.getId(), trainingProviderApplication.getClass().getName(), selectedRejectReason, user, ConfigDocProcessEnum.TP);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}

		}
		trainingProviderApplication.setApprovalStatus(ApprovalEnum.Rejected);
		trainingProviderApplication.setProviderStatus(ProviderStatusService.instance().findByCode("515"));
		trainingProviderApplication.setFinalRejected(true);
		trainingProviderApplication.setFinalRejectDate(new Date());
		trainingProviderApplicationService.update(trainingProviderApplication);
		//trainingProviderApplicationService.sendFinalRejectEmail(trainingProviderApplication.getUsers(), selectedRejectReason);
		trainingProviderApplicationService.sendTPFinalRejectionBeforeETQA(trainingProviderApplication.getUsers(), selectedRejectReason, trainingProviderApplication);
		TasksService.instance().completeTask(task);

	}

	public void rejectTPReAccreditationTask(String desc, Long targetKey, String targetClass, Tasks task, Users user, MailDef mailDef, ArrayList<RejectReasons> selectedRejectReason, TrainingProviderApplication trainingProviderApplication, SDPReAccreditation sdpReAccreditation) throws Exception {
		SDPReAccreditationService sdpReAccreditationService = new SDPReAccreditationService();
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(targetKey, targetClass, selectedRejectReason, user, ConfigDocProcessEnum.TP);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		sdpReAccreditation.setStatus(ApprovalEnum.Rejected);
		sdpReAccreditationService.update(sdpReAccreditation);

		if (trainingProviderApplication.getApprovalStatus() == ApprovalEnum.RejectedByEQTA) {
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.Rejected);
			trainingProviderApplication.setFinalRejected(true);
			trainingProviderApplication.setFinalRejectDate(new Date());
			trainingProviderApplication.setProviderStatus(ProviderStatusService.instance().findByCode("515"));
			trainingProviderApplicationService.update(trainingProviderApplication);
			trainingProviderApplicationService.sendFinalRejectEmail(trainingProviderApplication.getUsers(), selectedRejectReason);
			TasksService.instance().completeTask(task);
		} else {
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.Rejected);
			trainingProviderApplicationService.update(trainingProviderApplication);
			TasksService.instance().findPreviousInProcessAndCreateTask(desc, user, targetKey, targetClass, true, task, mailDef, null);
		}
	}
	
	
	public void rejectTaskWorkflow(Tasks task, List<RejectReasons> selectedRejectReason, TrainingProviderApplication trainingProviderApplication, SDPReAccreditation sdpReAccreditation, Users user) throws Exception {
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(sdpReAccreditation.getId(), sdpReAccreditation.getClass().getName(), selectedRejectReason, user, ConfigDocProcessEnum.TP);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalEdit) {
			ArrayList<Users> usersList = new ArrayList<>();
			usersList.add(trainingProviderApplication.getUsers());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, task, usersList);
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask(user, task, null);
		}
	}

	/**
	 * Approve task for assessor and Moderator.
	 *
	 * @param assessorModerator
	 *            the assessor moderator
	 * @param task
	 *            the task
	 * @param user
	 *            the user
	 * @param company
	 *            the company
	 * @throws Exception
	 *             the exception
	 */
	public void approveTaskAssessorModerator(Users assessorModerator, Tasks task, Users user, Company company) throws Exception {
		List<IDataEntity> iDataEntities = new ArrayList<>();
		assessorModerator.setActive(true);
		assessorModerator.setStatus(UsersStatusEnum.Active);
		iDataEntities.add(assessorModerator);
		if (company.getCompanyStatus() == CompanyStatusEnum.Pending) {
			company.setCompanyStatus(CompanyStatusEnum.Active);
		}
		iDataEntities.add(company);
		dao.updateBatch(iDataEntities);
		completeTask("New Training Provider for approval", assessorModerator.getId(), assessorModerator.getClass().getName(), task, user, MailDef.instance(MailEnum.TPNewRequiresApproval, MailTagsEnum.CompanyName, company.getCompanyName()), null);
	}

	/**
	 * Do check.
	 *
	 * @param companies
	 *            the companies
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public String doCheck(List<Company> companies) throws Exception {
		StringBuilder exceptions = new StringBuilder("");
		for (Company sdfCompany : companies) {
			exceptions.append(doCheck(sdfCompany));
		}
		return exceptions.toString();
	}

	/**
	 * Do check.
	 *
	 * @param sdfCompany
	 *            the sdf company
	 * @return the string
	 */
	public String doCheck(Company sdfCompany) {
		StringBuilder exceptions = new StringBuilder("");
		String ex = sdfCompany.getValidCompany();
		if (ex.length() > 0) {
			exceptions.append("<h3>" + sdfCompany.getCompanyName() + "</h3><ul>" + ex + "</ul>");
		}
		return exceptions.toString();
	}

	/**
	 * Do checks user company.
	 *
	 * @param user
	 *            the user
	 * @param companyList
	 *            the company list
	 * @param companyDocCheck
	 *            the company doc check
	 * @param companyInfoCheck
	 *            the company info check
	 * @throws Exception
	 *             the exception
	 */
	public void doChecksUserCompany(Users user, List<Company> companyList, Boolean companyDocCheck, Boolean companyInfoCheck) throws Exception {
		StringBuilder exceptions = new StringBuilder("");
		if (user != null) {
			for (Doc doc : user.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						exceptions.append("Upload Document: <i>" + doc.getConfigDoc().getName() + "</i> for user:<i>" + user.getFirstName() + " " + user.getLastName() + "</i><br/>");
					}
				}
			}
		}
		if (companyList != null) {
			if (companyInfoCheck) {
				for (Company company : companyList) {
					String ex = company.getValidProviderCompany();
					if (ex.length() > 0) {
						exceptions.append("<h3>" + company.getCompanyName() + "</h3><ul>" + ex + "</ul>");
					}
				}
			}
			if (companyDocCheck) {
				for (Company company : companyList) {
					if (company.getDocs() != null) {

						for (Doc doc : company.getDocs()) {
							if (doc.getId() != null) {
								DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
								if (docByte != null) doc.setData(docByte.getData());
								if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
									exceptions.append("Upload Document:<i>" + doc.getConfigDoc().getName() + "</i> for <i>" + company.getCompanyName() + "</i><br/>");
								}
							}

						}
					}
				}
			}
		}
		if (exceptions.length() > 0) {
			throw new Exception(exceptions.toString());
		}
	}

	/**
	 * Document upload.
	 *
	 * @param entity
	 *            the entity
	 * @param users
	 *            the users
	 * @throws Exception
	 *             the exception
	 */
	public void documentUpload(Company entity, Users users) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() == null && doc.getData() != null) {
					doc.setCompany(entity);
					doc.setVersionNo(1);
					doc.setUsers(users);
					dataEntities.add(doc);
					dataEntities.add(new DocByte(doc.getData(), doc));
					dataEntities.add(new DocumentTracker(doc, users, new java.util.Date(), DocumentTrackerEnum.Upload));
				}
			}
		}
		this.dao.createBatch(dataEntities);
	}

	/**
	 * Upload new version.
	 *
	 * @param doc
	 *            the doc
	 * @param users
	 *            the users
	 * @throws Exception
	 *             the exception
	 */
	public void uploadNewVersion(Doc doc, Users users) throws Exception {
		DocService.instance().uploadNewVersion(doc, users);
	}
	
	public void uploadCompanyDocs(Doc doc,Company company,Users trainingProviderUser,TrainingProviderApplication trainingProviderApplication) throws Exception
	{
		List<IDataEntity> dataEntities = new ArrayList<>();
		doc.setCompany(company);
		doc.setVersionNo(1);
		doc.setUsers(trainingProviderUser);
		doc.setTargetClass(trainingProviderApplication.getClass().getName());
		doc.setTargetKey(trainingProviderApplication.getId());
		dataEntities.add(doc);
		dataEntities.add(new DocByte(doc.getData(), doc));
		dataEntities.add(new DocumentTracker(doc, trainingProviderUser, new java.util.Date(), DocumentTrackerEnum.Upload));
		this.dao.createBatch(dataEntities);
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
	public SDFCompany findByCompanyAndUser(Company company, Users user) throws Exception {
		return dao.findByCompanyAndUser(company.getId(), user.getId());
	}

	/**
	 * Find linked company.
	 *
	 * @param company
	 *            the company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Company> findLinkedCompany(Company company) throws Exception {
		return dao.findLinkedCompany(company.getId());
	}

	private List<Company> resolveCompanyData(List<Company> companies) throws Exception {
		for (Company company : companies)
			resolveCompanyData(company);
		return companies;
	}

	public Company resolveCompanyData(Company company) throws Exception {
		if(company!= null && company.getId() != null) {
			List<TrainingProviderApplication>  list=TrainingProviderApplicationService.instance().findByCompanyAndStatusAndTypeList(company, ApprovalEnum.Approved, AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL);
			if(list != null && list.size() > 0) {
				company.setTrainingProviderApplication(list.get(0));
			}
			//company.setTrainingProviderApplication(TrainingProviderApplicationService.instance().findByCompanyAndStatusAndType(company, ApprovalEnum.Approved, AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL));
			company.setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(company));
			// company.setCompanyHistories(CompanyHistoryService.instance().findByCompanyLatest(company));
	
			// checks the latest grant application status
			Date today = new Date();
	
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			Integer lastestVerificationYear = cal.get(Calendar.YEAR) + 1;
			WspService wspService = new WspService();
	
			List<Wsp> lastestWspList = wspService.findByCompanyAndFinancialYear(company.getId(), lastestVerificationYear);
			Wsp lastestWsp = null;
	
			for (Wsp wsp : lastestWspList) {
				lastestWsp = wspService.findByKey(wsp.getId());
				DgVerification dgVerification = DgVerificationService.instance().findByWspId(lastestWsp);
				if (lastestWsp != null) {
					if (dgVerification != null && dgVerification.getStatus() != null) {
						break;
					}
				}
			}
	
			if (company.getPostalAddress() != null && company.getPostalAddress().getId() != null) {
	
				company.setPostalAddress(AddressService.instance().findByKey(company.getPostalAddress().getId()));
	
			}
	
			if (lastestWsp != null) {
	
				if (lastestWsp.getFinYear() != null) {
					lastestVerificationYear = lastestWsp.getFinYear();
				}
				company.setLastestGrantStatus(lastestVerificationYear + " - " + lastestWsp.getWspStatusEnum().getFriendlyName());
				DgVerification dgVerification = DgVerificationService.instance().findByWspId(lastestWsp);
				if (dgVerification != null && dgVerification.getStatus() != null) {
					String message = lastestVerificationYear + " - " + dgVerification.getStatus().getFriendlyName();
					company.setLastestDgVerificationStatus(lastestVerificationYear + " - " + dgVerification.getStatus().getFriendlyName());
					if(dgVerification.getStatus() == ApprovalEnum.Rejected) {
						
						RejectReasonsService rejectReasonsService = new RejectReasonsService();
						List<RejectReasons> rrList = rejectReasonsService.locateReasonsSelectedByTargetKeyAndClass(dgVerification.getId(), DgVerification.class.getName());
						message += " (";
						int counter = 1;
						for (RejectReasons rejectReasons : rrList) {
							if (counter == rrList.size()) {
								message += rejectReasons.getDescription();
							} else {
								message += rejectReasons.getDescription() + ", ";
							}
						}
						message += ")";
					}
				} else if (dgVerification != null && dgVerification.getStatus() == null) {
					company.setLastestDgVerificationStatus(lastestVerificationYear + " - Not Yet Started");
				} else {
					company.setLastestDgVerificationStatus(lastestVerificationYear + " - N/A");
				}
	
			} else {
	
				company.setLastestGrantStatus(lastestVerificationYear + " - N/A");
				company.setLastestDgVerificationStatus(lastestVerificationYear + " - N/A");
	
			}
	
			wspService = null;
		}
		return company;
	}
	
	public void resolveCompanyAddresses(Company company) throws Exception{
		if (company != null) {
			if (company.getRegisteredAddress() != null && company.getRegisteredAddress().getId() != null) {
				company.setRegisteredAddress(AddressService.instance().findByKey(company.getRegisteredAddress().getId()));
			}
			if (company.getPostalAddress() != null && company.getPostalAddress().getId() != null) {
				company.setPostalAddress(AddressService.instance().findByKey(company.getPostalAddress().getId()));
			}
			if (company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
				company.setResidentialAddress(AddressService.instance().findByKey(company.getResidentialAddress().getId()));
			}
		}
	}
	
	public Company resolveCompanyAddressesReturnCompany(Company company) throws Exception{
		if (company != null) {
			if (company.getRegisteredAddress() != null && company.getRegisteredAddress().getId() != null) {
				company.setRegisteredAddress(AddressService.instance().findByKey(company.getRegisteredAddress().getId()));
			}
			if (company.getPostalAddress() != null && company.getPostalAddress().getId() != null) {
				company.setPostalAddress(AddressService.instance().findByKey(company.getPostalAddress().getId()));
			}
			if (company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
				company.setResidentialAddress(AddressService.instance().findByKey(company.getResidentialAddress().getId()));
			}
			return company;
		}else {
			return null;
		}
	}

	public Company populateReportInfo(Company company) throws Exception {
		CategorizationDataService categorizationDataService = new CategorizationDataService();
		company.setRegionTown(RegionTownService.instance().findByTown(company.getResidentialAddress().getTown()));
		company.setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(company));
		CategorizationData cd = categorizationDataService.findByCompany(company.getId());
		if (cd != null) {
			company.setCategorizationLookUp(cd.getCategorization());
		}
		categorizationDataService = null;

		// AddressService addressService = new AddressService();
		// Address addressPostalCompany =
		// addressService.findByKey(company.getPostalAddress().getId());
		// company.setPostalAddress(addressPostalCompany);
		// addressService = null;

		return company;
	}

	public String findLastNNumber() throws Exception {
		List<String> lastN = dao.findLastNNumber();
		return (lastN.size() > 0) ? lastN.get(0) : "N000100000";
	}

	public void assignNNumber(Company company) throws Exception {
		String lastN = findLastNNumber();
		int lastNint = Integer.parseInt(lastN.substring(1, lastN.length()));
		System.out.println("company.getPayeSDLNumber()---"+company.getPayeSDLNumber());
		if(company.getPayeSDLNumber()!=null && !company.getPayeSDLNumber().trim().equalsIgnoreCase("")) {
			company.setLevyNumber(company.getPayeSDLNumber().trim());
		}else {
			company.setLevyNumber("N000" + (++lastNint));
		}
		System.out.println("New Levy Number ---"+company.getLevyNumber());
		populateCompanySiteNumber(company);
	}

	/**
	 * Approve task for assessor and Moderator.
	 */
	public void approveAMTask(Users assessorModerator, Tasks task, Users user, AssessorModeratorApplication amApplication) throws Exception {
		List<IDataEntity> iDataEntities = new ArrayList<>();
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
			amApplication.setStatus(ApprovalEnum.Approved);
			
		} else {
			amApplication.setStatus(ApprovalEnum.PendingFinalApproval);
		}
		if(amApplication.getAssessorModReRegistration() !=null){
			amApplication.getAssessorModReRegistration().setStatus(amApplication.getStatus());
			iDataEntities.add(amApplication.getAssessorModReRegistration());
		}
		assessorModerator.setActive(true);
		assessorModerator.setStatus(UsersStatusEnum.Active);
		iDataEntities.add(assessorModerator);
		iDataEntities.add(amApplication);
		dao.updateBatch(iDataEntities);
		if(task.getWorkflowProcess()==ConfigDocProcessEnum.AM)
		{
			String desc = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.AM.getTaskDescription());
			TasksService.instance().findNextInProcessAndCreateTask(desc, user, amApplication.getId(), AssessorModeratorApplication.class.getName(), true, task, null, null);
		}
		else
		{
			String desc = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.AM_RE_REGISTRATION.getTaskDescription());
			TasksService.instance().findNextInProcessAndCreateTask(desc, user, amApplication.getAssessorModReRegistration().getId(), amApplication.getAssessorModReRegistration().getClass().getName(), true, task, null, null);
		}
	}

	/**
	 * Reject task for assessor and Moderator.
	 */
	public void rejectAMTask(Users assessorModerator, Tasks task, Users user, List<AssessorModeratorCompany> assessorModeratorCompaniesList, AssessorModeratorApplication amApplication, ArrayList<RejectReasons> rejectReasonsList, String additionalInformation) throws Exception {
		List<IDataEntity> iDataEntities = new ArrayList<>();
		amApplication.setStatus(ApprovalEnum.Rejected);

		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
			assessorModerator.setActive(true);
			// assessorModerator.setStatus(UsersStatusEnum.InActive);
		}
		iDataEntities.add(assessorModerator);
		iDataEntities.add(amApplication);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> list = RejectReasonMultipleSelectionService.instance().removeCreateLinks(amApplication.getId(), AssessorModeratorApplication.class.getName(), rejectReasonsList, user, ConfigDocProcessEnum.AM);

		// for (AssessorModeratorCompany amCompany :
		// assessorModeratorCompaniesList) {
		// Company company = null;
		// if (amCompany.getCompany().getCompanyStatus() ==
		// CompanyStatusEnum.Pending) {
		// company = amCompany.getCompany();
		// company.setCompanyStatus(CompanyStatusEnum.Rejected);
		// iDataEntities.add(company);
		// }
		// }

		dao.updateBatch(iDataEntities);
		if (task.getFirstInProcess()) {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(assessorModerator);
			TasksService.instance().createTaskUser(signoffs, task.getTargetClass(), task.getTargetKey(), "Your NSDMS application was rejected please login and view the reason and make the relevant changes.", user, true, true, task, ConfigDocProcessEnum.AM, false);
		} else {
			String desc = "Assessor / Moderator application was rejected";
			TasksService.instance().findPreviousInProcessAndCreateTask("", user, amApplication.getId(), AssessorModeratorApplication.class.getName(), true, task, null, null);
		}
	}

	public void finalRejectAMTask(Users assessorModerator, Tasks task, Users user, List<AssessorModeratorCompany> assessorModeratorCompaniesList, AssessorModeratorApplication amApplication) throws Exception {
		List<IDataEntity> iDataEntities = new ArrayList<>();
		amApplication.setStatus(ApprovalEnum.Rejected);
		assessorModerator.setActive(true);
		// assessorModerator.setStatus(UsersStatusEnum.InActive);
		iDataEntities.add(assessorModerator);
		iDataEntities.add(amApplication);

		for (AssessorModeratorCompany amCompany : assessorModeratorCompaniesList) {
			Company company = null;
			if (amCompany.getCompany().getCompanyStatus() == CompanyStatusEnum.Pending) {
				company = amCompany.getCompany();
				company.setCompanyStatus(CompanyStatusEnum.Rejected);
				iDataEntities.add(company);
			}

		}
		dao.updateBatch(iDataEntities);
	}

	/**
	 * Creates the company and send task.
	 *
	 * @param entity
	 *            the entity
	 * @param users
	 *            the users
	 * @param createTask
	 *            the create task
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @param hostingCompany
	 *            the hosting company
	 * @throws Exception
	 *             the exception
	 */
	public void createAMCompanyAndSendTask(List<IDataEntity> dataEntities, Company entity, Users users, boolean createTask, ConfigDocProcessEnum configDocProcessEnum, HostingCompany hostingCompany, AssessorModeratorApplication amApplication) throws Exception {
		String desc = "";
		entity.setCompanyStatus(CompanyStatusEnum.Pending);
		if (entity.getId() == null) {
			desc = getEntryLanguage(configDocProcessEnum.getTaskDescription());
			this.dao.create(entity);
		} else {
			desc = getEntryLanguage(configDocProcessEnum.getTaskDescription());
			this.dao.update(entity);
		}
		// Links Assessor Moderator user to company
		AssessorModeratorCompany amCompany = new AssessorModeratorCompany(entity, users);
		amCompany.setForAssessorModeratorApplication(amApplication);
		dataEntities.add(amCompany);

		if (users != null) {
			CompanyUsers companyUsers = new CompanyUsers(users, entity, configDocProcessEnum);
			dataEntities.add(companyUsers);
			if (userHostingCompanyService.findCountByUserAndHoustingCompany(hostingCompany, users) == 0) {
				UserHostingCompany uhc = new UserHostingCompany(hostingCompany, users);
				dataEntities.add(uhc);
			}
			if (companyHostingCompanyService.findCountByCompanyAndHoustingCompany(hostingCompany, entity) == 0) {
				CompanyHostingCompany chc = new CompanyHostingCompany(hostingCompany, entity);
				dataEntities.add(chc);
			}
		}

		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				doc.setCompany(entity);
				doc.setVersionNo(1);
				doc.setUsers(users);

				dataEntities.add(doc);
				dataEntities.add(new DocByte(doc.getData(), doc));
				dataEntities.add(new DocumentTracker(doc, users, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
		}
		if (createTask) {
			TasksService.instance().findFirstInProcessAndCreateTask(desc, users, users.getId(), users.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		}
	}

	/**
	 * Check if already registered.
	 *
	 * @param user
	 *            the user
	 * @param company
	 *            the company
	 * @throws ValidationException
	 *             the validation exception
	 * @throws Exception
	 *             the exception
	 * @return true id user is registered
	 */
	public boolean isUserRegisteredToTheCompany(Users user, Company company) throws ValidationException, Exception {
		boolean isRegistered = false;
		;
		if (findByCompanyAndUser(company, user) != null) {
			isRegistered = true;
		}
		return isRegistered;

	}

	public Company resolveDocsForProcessAndUserType(Company company, ConfigDocProcessEnum process, CompanyUserTypeEnum userType) throws Exception {
		try {
			company.setDocs(docService.searchByCompany(company));
			prepareDocs(ConfigDocProcessEnum.SDF, company, CompanyUserTypeEnum.Company);
		} catch (Exception e) {
			logger.fatal(e);
		}
		return company;
	}

	public void sendBulkMailToSelectedCompanies(List<Company> companiesSelecetd, String emailContents) throws Exception {
		List<BulkMailBean> users = new ArrayList<>();
		for (Company company : companiesSelecetd) {
			SDFCompany primarySdf = findPrimarySDF(company);
			if (primarySdf != null && primarySdf.getSdf() != null) {
				BulkMailBean bean = new BulkMailBean(primarySdf.getSdf(), replaceText(emailContents, company, primarySdf.getSdf()));
				users.add(bean);
			}
		}
		if (users.size() != 0) {
			GenericUtility.sendMailToSelectedUsers("NSDMS Notification", emailContents, null, users);
		}
	}

	public String replaceText(String mailContents, Company company, Users user) {
		String newContents = mailContents;
		// user info
		newContents = newContents.replace("#FIRST_NAME#", user.getFirstName());
		newContents = newContents.replace("#LAST_NAME#", user.getLastName());

		// company info
		newContents = newContents.replace("#COMPANY_NAME#", company.getCompanyName());
		newContents = newContents.replace("#TRADING_NAME#", company.getTradingName() != null ? company.getTradingName() : "N/A");
		newContents = newContents.replace("#ENTITY_ID#", company.getLevyNumber() != null ? company.getLevyNumber() : "N/A");
		newContents = newContents.replace("#TEL_NUMBER#", company.getTelNumber() != null ? company.getTelNumber() : "N/A");
		newContents = newContents.replace("#COMPANY_EMAIL#", company.getEmail() != null ? company.getEmail() : "N/A");
		return newContents;
	}

	@SuppressWarnings("unchecked")
	public List<Company> sortAndFilterWhere(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select distinct o.company from CompanyUsers o where o.id is not null ";
		return (List<Company>) (dao.sortAndFilterSearch1(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countWhere(Class<Company> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(distinct o.company) from CompanyUsers o where o.id is not null ";
		return dao.countSearch1(filters, hql);
	}

	/**
	 * Lazy load Company with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Company class
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
	 * @return a list of {@link haj.com.entity.Company} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Company> allCompanyBankingDetails(Class<Company> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveCompanyBankingData((List<Company>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}

	private List<Company> resolveCompanyBankingData(List<Company> companies) throws Exception {
		BankingDetailsService bankingDetailsService = new BankingDetailsService();
		for (Company company : companies) {
			// resolveCompanyData(company);
			BankingDetails bankingDetails = bankingDetailsService.findByCompanyLates(company);
			if (bankingDetails != null && bankingDetails.getId() != null) {
				company.setBankingDetails(bankingDetails);
			}

		}
		return companies;
	}

	public List<Company> findByLevyNumberForStatusCheck(String levyNumber) throws Exception {
		return dao.findByLevyNumberForStatusCheck(levyNumber);
	}

	public void validateSDF(Company company) throws Exception {
		SDFCompany primarySDF = findPrimarySDF(company);
		if (primarySDF == null) {
			throw new Exception("Unable to locate Primary SDF for: " + company.getLevyNumber());
		}
	}

	public int countAllWpaWhereWpaNumberAplliedForCompany() throws Exception {
		return dao.countAllWpaWhereWpaNumberAplliedForCompany();
	}
	
	public Integer countAllCompaniesByCompanySiteNumberAssigned(String levyNumberStart) throws Exception {
		return dao.countAllCompaniesByCompanySiteNumberAssigned(levyNumberStart);
	}
	
	public List<Company> allCompaniesByCompanyWhereSiteNumberNotAssigned(String levyNumberStart) throws Exception {
		return dao.allCompaniesByCompanyWhereSiteNumberNotAssigned(levyNumberStart);
	}
	
	public Integer countAllCompaniesByCompanyWhereSiteNumberNotAssigned(String levyNumberStart) throws Exception {
		return dao.countAllCompaniesByCompanyWhereSiteNumberNotAssigned(levyNumberStart);
	}
	
	public void updateLevyNumberCompanySitesNumbersWhereNoSiteNumberAssigned() {
		try {
			List<Company> companyList = allCompaniesByCompanyWhereSiteNumberNotAssigned("L");
			for (Company company : companyList) {
				populateCompanySiteNumber(company);
				upadeCompanyInfo(company);
			}
		} catch (Exception e) {
			logger.fatal(e);
			GenericUtility.mailError("Levy Paying (L-Number) Site Number Assignment Error", "Error: haj.com.service.CompanyService.updateLevyNumberCompanySitesNumbersWhereNoSiteNumberAssigned() <br/> Exception: " + e.getMessage());
		}
	}
	
	public void updateNonLevyNumberCompanySitesNumbersWhereNoSiteNumberAssigned() {
		try {
			List<Company> companyList = allCompaniesByCompanyWhereSiteNumberNotAssigned("N");
			for (Company company : companyList) {
				populateCompanySiteNumber(company);
				upadeCompanyInfo(company);
			}
		} catch (Exception e) {
			logger.fatal(e);
			GenericUtility.mailError("Non-Levy Paying (N-Number) Site Number Assignment Error", "Error: haj.com.service.CompanyService.updateNonLevyNumberCompanySitesNumbersWhereNoSiteNumberAssigned() <br/> Exception: " + e.getMessage());
		}
	}
	
	public void populateCompanySiteNumber(Company company) throws Exception{
		if (company.getLevyNumber() != null) {
			if (company.getLevyNumber().contains("L") && (company.getCompanySiteNumber() == null || company.getCompanySiteNumber().isEmpty())) {
				// syntax: SL10000000
				Integer count = countAllCompaniesByCompanySiteNumberAssigned("L");
				Integer baseCount = 10000001;
				if (count != 0) {
					baseCount = baseCount + count;
				}
				String siteNumber = "SL" + baseCount;
				company.setCompanySiteNumber(siteNumber);
			} else if (company.getLevyNumber().contains("N") && (company.getCompanySiteNumber() == null || company.getCompanySiteNumber().isEmpty())) {
				// syntax: SN10000000
				Integer count = countAllCompaniesByCompanySiteNumberAssigned("N");
				Integer baseCount = 10000001;
				if (count != 0) {
					baseCount = baseCount + count;
				}
				String siteNumber = "SN" + baseCount;
				company.setCompanySiteNumber(siteNumber);
			}
		}
	}
	
	public void updateCompanyBatchInformation(Company company) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		if (company != null && company.getId() != null) {
			updateList.add(company);
		}
		if (company.getPostalAddress() != null && company.getPostalAddress().getId() != null) {
			updateList.add(company.getPostalAddress());
		}
		if (company.getRegisteredAddress() != null && company.getRegisteredAddress().getId() != null) {
			updateList.add(company.getRegisteredAddress());
		}
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
			updateList.add(company.getResidentialAddress());
		}	
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
	}
	
	public Integer countCompaniesByLevyNumber(String levyNumber) throws Exception {
		return dao.countCompaniesByLevyNumber(levyNumber);
	}
	
	public Integer countCompaniesByLevyNumberAndNotCompanyId(String levyNumber, Long companyId) throws Exception {
		return dao.countCompaniesByLevyNumberAndNotCompanyId(levyNumber, companyId);
	}
	
	public Integer countCompaniesByRegistartionNumber(String regNumber) throws Exception {
		return dao.countCompaniesByRegistartionNumber(regNumber);
	}
	
	public Integer countCompaniesByRegistartionNumberAndNotCompanyId(String regNumber, Long companyId) throws Exception {
		return dao.countCompaniesByRegistartionNumberAndNotCompanyId(regNumber, companyId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> cloCrmCompanyRegionReport(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long userID) throws Exception {
		String hql = "select o from Company o where o.nonSetaCompany = false and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		filters.put("userID", userID);
		return resolveCompanyDataCloCrmRegionReport((List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countCloCrmCompanyRegionReport(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Company o where o.nonSetaCompany = false and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		return dao.countWhere(filters, hql);
	}
	
	public int countCompaniesLinkedByCloCrmUser(Long userID) throws Exception {
		return dao.countCompaniesLinkedByCloCrmUser(userID);
	}
	
	private List<Company> resolveCompanyDataCloCrmRegionReport(List<Company> companyList) throws Exception {
		for (Company company : companyList) {
			resolveCompanyDataCloCrmRegionReport(company);
		}
		return companyList;
	}
	
	public Company resolveCompanyDataCloCrmRegionReport(Company company) throws Exception {
		CategorizationDataService categorizationDataService = new CategorizationDataService();
//		company.setRegionTown(RegionTownService.instance().findByTown(company.getResidentialAddress().getTown()));
//		company.setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(company));
		CategorizationData cd = categorizationDataService.findByCompany(company.getId());
		if (cd != null) {
			company.setCategorizationLookUp(cd.getCategorization());
		}
		categorizationDataService = null;
		return company;
	}
	
	public Region populateCompanyRegion(Company company) throws Exception {
		RegionService regionService = new RegionService();
		Region r = new Region();
		if(company.getResidentialAddress() != null && company.getResidentialAddress().getTown()!=null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			r = regionService.findByKey(rt.getRegion().getId());
		}
		return r;
	}
	
	public RegionTown populateCompanyRegionTown(Company company) throws Exception{
		RegionTown rt = null;
		if (company != null && company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null && company.getResidentialAddress().getTown().getId() != null) {
			rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		}
		return rt;
	} 
	
	public Province populateCompanyProvince(Company company) throws Exception{
		Province p = null;
		if (company != null && company.getResidentialAddress() != null && company.getResidentialAddress().getMunicipality() != null && company.getResidentialAddress().getMunicipality().getId() != null) {
			MunicipalityService mService = new MunicipalityService();
			Municipality m = mService.findByKey(company.getResidentialAddress().getMunicipality().getId());
			if (m != null && m.getProvince() != null && m.getProvince().getId() != null) {
				ProvinceService pService = new ProvinceService();
				p = pService.findByKey(m.getProvince().getId());
			}
		}
		return p;
	}
	
	/* Additional Company Validations Start */
	
	public void validateLevyNumberByCompany(Company company, String levyNumber) throws Exception {
		Integer companiesFound = 0;
		if (company.getId() == null) {
			companiesFound = countCompaniesByLevyNumber(levyNumber);
		} else {
			companiesFound = countCompaniesByLevyNumberAndNotCompanyId(levyNumber, company.getId());
		}
		if (companiesFound > 0) {
			throw new Exception("Levy number already in use, please provide a different levy number or contact support!");
		}
	}
	
	public String validateLevyNumberByCompanyReturnError(Company company, String levyNumber) throws Exception {
		Integer companiesFound = 0;
		if (company.getId() == null) {
			companiesFound = countCompaniesByLevyNumber(levyNumber);
		} else {
			companiesFound = countCompaniesByLevyNumberAndNotCompanyId(levyNumber, company.getId());
		}
		if (companiesFound > 0) {
			return "Levy number: "+levyNumber.trim()+" already in use, contact support!";
		} else {
			return null;
		}
	}
	
	public void validateRegistartionNumberByCompany(Company company, String regNumber) throws Exception {
		Integer companiesFound = 0;
		if (company.getId() == null) {
			companiesFound = countCompaniesByRegistartionNumber(regNumber);
		} else {
			companiesFound = countCompaniesByRegistartionNumberAndNotCompanyId(regNumber, company.getId());
		}
		if (companiesFound > 0) {
			throw new Exception("Company registartion number already in use, please provide a different registartion number or contact support!");
		}
	}
	
	public String validateRegistartionNumberByCompanyReturnError(Company company, String regNumber) throws Exception {
		Integer companiesFound = 0;
		if (company.getId() == null) {
			companiesFound = countCompaniesByRegistartionNumber(regNumber);
		} else {
			companiesFound = countCompaniesByRegistartionNumberAndNotCompanyId(regNumber, company.getId());
		}
		if (companiesFound > 0) {
			return "Registartion number already in use, contact support!";
		} else {
			return null;
		}
	}
	
	/* Additional Company Validations End */
	
	/* SETMIS VAlidiation START */
	public StringBuilder validiateCompanyInformation(Company company) {
		
		StringBuilder errors = new StringBuilder("");
		if (company.getCompanyName() != null && !company.getCompanyName().trim().isEmpty()) {
			if (!CompanyValidationService.instance().companyNameValidation(company.getCompanyName())) {
				errors.append("Validation Failed For SETMIS Company Name failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\\\:._,`'-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getTradingName() != null && !company.getTradingName().trim().isEmpty()) {
			if (!CompanyValidationService.instance().companyTradingNameValidation(company.getTradingName())) {
				errors.append("Validation Failed For SETMIS Trading Name failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\\\:._,`'-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getResidentialAddress() != null) {
			StringBuilder residentialAddressErrors = new StringBuilder("");
			residentialAddressErrors = AddressService.instance().validiateAddressInformation(company.getResidentialAddress());
			if (!residentialAddressErrors.toString().isEmpty()) {
				errors.append("Residential Address Validation Errors:");
				errors.append("<br/>");
				errors.append(residentialAddressErrors.toString());
				errors.append("<br/>");
			}
		}
		
		if (company.getPostalAddress() != null) {
			StringBuilder postalAddressErrors = new StringBuilder("");
			postalAddressErrors = AddressService.instance().validiateAddressInformation(company.getPostalAddress());
			if (!postalAddressErrors.toString().isEmpty()) {
				errors.append("Postal Address Validation Errors:");
				errors.append("<br/>");
				errors.append(postalAddressErrors.toString());
				errors.append("<br/>");
			}
		}
		
		if (company.getRegisteredAddress() != null) {
			StringBuilder registeredAddressErrors = new StringBuilder("");
			registeredAddressErrors = AddressService.instance().validiateAddressInformation(company.getRegisteredAddress());
			if (!registeredAddressErrors.toString().isEmpty()) {
				errors.append("Registered Address Validation Errors:");
				errors.append("<br/>");
				errors.append(registeredAddressErrors.toString());
				errors.append("<br/>");
			}
		}

		if (company.getCompanyRegistrationNumber() != null && !company.getCompanyRegistrationNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().companyRegValidation(company.getCompanyRegistrationNumber())) {
				errors.append("Validation Failed For SETMIS Company Registration Number failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\\\:._,`'-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getTelNumber() != null && !company.getTelNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().contactNumberValidation(company.getTelNumber())) {
				errors.append("Validation Failed For SETMIS Company Tel Number failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters 1234567890 ()/-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getFaxNumber() != null && !company.getFaxNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().faxNumberValidation(company.getFaxNumber())) {
				errors.append("Validation Failed For SETMIS Company FAX Number failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters 1234567890 ()/-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getPayeSDLNumber() != null && !company.getPayeSDLNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().payeSDLNumberValidation(company.getPayeSDLNumber())) {
				errors.append("Validation Failed For SETMIS Company PAYE SDL Number failed.<ul> <li>Field may not start with a space.</li><li>Value must have a length of exactly 10</li><li>Value must start with 'L' followed by 9 digits or 'N' followed by 9 digits </li><li>Field may not equal: 'L000000000'</li> <li>Field must contain value '7' or '8' in the 5 position (LNNN*7*NNNNN 0R LNNN*8*NNNNN)</li> </ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getLevyNumber() != null && !company.getLevyNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().levyNumberValidation(company.getLevyNumber())) {
				errors.append("Validation Failed For SETMIS Company Levy Number failed.<ul> <li>Field may not start with a space.</li><li>Value must have a length of exactly 10</li><li>Value must start with 'L' followed by 9 digits or 'N' followed by 9 digits  </li><li>Field may not equal: 'L000000000'</li> <li>Field must contain value '7' or '8' in the 5 position (LNNN*7*NNNNN 0R LNNN*8*NNNNN)</li>  </ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getAccreditationNumber() != null && !company.getAccreditationNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().accreditationNumberValidation(company.getAccreditationNumber())) {
				errors.append("Validation Failed For SETMIS Company Accreditation Number failed.<ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getCompanySiteNumber() != null && !company.getCompanySiteNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().siteNumberValidation(company.getCompanySiteNumber())) {
				errors.append("Validation Failed For SETMIS Company Site Number Validation failed.<ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getCompanyWebsite() != null && !company.getCompanyWebsite().trim().isEmpty()) {
			if (!CompanyValidationService.instance().companyWebsiteValidation(company.getCompanyWebsite())) {
				errors.append("Validation Failed For SETMIS Company Website failed.<ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li></ul");
				errors.append("<br/>");
			}
		}
		return errors;
	}
	
	public StringBuilder validiateCompanyInformationIgnoreAddresses(Company company) {
		
		StringBuilder errors = new StringBuilder("");
		if (company.getCompanyName() != null && !company.getCompanyName().trim().isEmpty()) {
			if (!CompanyValidationService.instance().companyNameValidation(company.getCompanyName())) {
				errors.append("Validation Failed For SETMIS Company Name failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\\\:._,`'-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getTradingName() != null && !company.getTradingName().trim().isEmpty()) {
			if (!CompanyValidationService.instance().companyTradingNameValidation(company.getTradingName())) {
				errors.append("Validation Failed For SETMIS Trading Name failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\\\:._,`'-</li></ul>");
				errors.append("<br/>");
			}
		}

		if (company.getCompanyRegistrationNumber() != null && !company.getCompanyRegistrationNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().companyRegValidation(company.getCompanyRegistrationNumber())) {
				errors.append("Validation Failed For SETMIS Company Registration Number failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space. If the value provided contains leading spaces then all leading spaces will be trimmed from the value during further validation of the data value provided</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\\\:._,`'-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getTelNumber() != null && !company.getTelNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().contactNumberValidation(company.getTelNumber())) {
				errors.append("Validation Failed For SETMIS Company Tel Number failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters 1234567890 ()/-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getFaxNumber() != null && !company.getFaxNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().faxNumberValidation(company.getFaxNumber())) {
				errors.append("Validation Failed For SETMIS Company FAX Number failed.<ul><li>This field may not be left blank.</li><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters 1234567890 ()/-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getPayeSDLNumber() != null && !company.getPayeSDLNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().payeSDLNumberValidation(company.getPayeSDLNumber())) {
				errors.append("Validation Failed For SETMIS Company PAYE SDL Number failed.<ul> <li>Field may not start with a space.</li><li>Value must have a length of exactly 10</li><li>Value must start with 'L' followed by 9 digits or 'N' followed by 9 digits </li><li>Field may not equal: 'L000000000'</li> <li>Field must contain value '7' or '8' in the 5 position (LNNN*7*NNNNN 0R LNNN*8*NNNNN)</li> </ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getLevyNumber() != null && !company.getLevyNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().levyNumberValidation(company.getLevyNumber())) {
				errors.append("Validation Failed For SETMIS Company Levy Number failed.<ul> <li>Field may not start with a space.</li><li>Value must have a length of exactly 10</li><li>Value must start with 'L' followed by 9 digits or 'N' followed by 9 digits  </li><li>Field may not equal: 'L000000000'</li> <li>Field must contain value '7' or '8' in the 5 position (LNNN*7*NNNNN 0R LNNN*8*NNNNN)</li>  </ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getAccreditationNumber() != null && !company.getAccreditationNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().accreditationNumberValidation(company.getAccreditationNumber())) {
				errors.append("Validation Failed For SETMIS Company Accreditation Number failed.<ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getCompanySiteNumber() != null && !company.getCompanySiteNumber().trim().isEmpty()) {
			if (!CompanyValidationService.instance().siteNumberValidation(company.getCompanySiteNumber())) {
				errors.append("Validation Failed For SETMIS Company Site Number Validation failed.<ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li></ul>");
				errors.append("<br/>");
			}
		}
		
		if (company.getCompanyWebsite() != null && !company.getCompanyWebsite().trim().isEmpty()) {
			if (!CompanyValidationService.instance().companyWebsiteValidation(company.getCompanyWebsite())) {
				errors.append("Validation Failed For SETMIS Company Website failed.<ul><li>Field may not start with a space.</li><li>Uppercase value in field may only contain characters ABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890@#&+() /\\:._-</li></ul");
				errors.append("<br/>");
			}
		}
		return errors;
	}
	/* SETMIS VAlidiation END   */
	
	
	/* Determine company information alteration by config process */
	public void determainAlterationsByConfigProcess(Company company, ConfigDocProcessEnum processEnum) throws Exception {
		CompanyUpdateInfoBean bean = new CompanyUpdateInfoBean();
		if (company.getId() == null) {
			bean.setCompanyName(true);
			bean.setTradingName(true);
			bean.setLevyNumber(true);
			bean.setEmail(true);
			bean.setSiccode(true);
			bean.setTellNumber(true);
			bean.setFaxNumber(true);
			bean.setRegistrationNumber(true);
			bean.setSetaSelection(true);
		} else {
			switch (processEnum) {
			case SDP_LEGACY_APPLICATION:
				setTrainingProviderRegsitartionExistingCompany(company, bean);
				break;
			case TP:
				setTrainingProviderRegsitartionExistingCompany(company, bean);
				break;
			default:
				break;
			}
		}
		company.setCompanyUpdateInfoBean(bean);
	}

	/**
	 * @param company
	 * @param bean
	 */
	public void setTrainingProviderRegsitartionExistingCompany(Company company, CompanyUpdateInfoBean bean) {
		if (company.getCompanyName() == null || company.getCompanyName().trim().isEmpty()) {
			bean.setCompanyName(true);
		}
		if (company.getTradingName() == null || company.getTradingName().trim().isEmpty()) {
			bean.setTradingName(true);
		}
		if (company.getLevyNumber() == null || company.getLevyNumber().trim().isEmpty()) {
			bean.setLevyNumber(true);
		}
		if (company.getEmail() == null || company.getEmail().trim().isEmpty()) {
			bean.setEmail(true);
		}
		if (company.getSicCode() == null) {
			bean.setSiccode(true);
		}
		if (company.getTelNumber() == null || company.getTelNumber().trim().isEmpty()) {
			bean.setTellNumber(true);
		}
		if (company.getFaxNumber() == null || company.getFaxNumber().trim().isEmpty()) {
			bean.setFaxNumber(true);
		}
		if (company.getCompanyRegistrationNumber() == null || company.getCompanyRegistrationNumber().trim().isEmpty()) {
			bean.setRegistrationNumber(true);
		}
		if (company.getNonSetaCompany() != null && company.getNonSetaCompany() && company.getSeta() == null) {
			bean.setSetaSelection(true);
		}
	}
	
	public List<CompanyStatusVsSarsBean> populateDistinctHostingCompanyProcessByUserId(Long sarsFileId) throws Exception {	
		return dao.populateDistinctHostingCompanyProcessByUserId(sarsFileId);
	}
	
	public List<ByChamberReportBean> reportActiveMersetaCompaniesReport() throws Exception {
		return dao.reportActiveMersetaCompaniesReport();
	}
	
	public List<ByChamberReportBean> generateReportActiveMersetaCompaniesReport() throws Exception {
		List<ByChamberReportBean> results = reportActiveMersetaCompaniesReport();
		ByChamberReportBean grandTotal = new ByChamberReportBean();
		grandTotal.setDescription("Total");
		for (ByChamberReportBean byChamberReportBean : results) {
			if (grandTotal.getAuto() == null) {
				grandTotal.setAuto(byChamberReportBean.getAuto());
				grandTotal.setMetal(byChamberReportBean.getMetal());
				grandTotal.setMotor(byChamberReportBean.getMotor());
				grandTotal.setNewTyre(byChamberReportBean.getNewTyre());
				grandTotal.setPlastic(byChamberReportBean.getPlastic());
				grandTotal.setUnknown(byChamberReportBean.getUnknown());
			} else {
				grandTotal.setAuto(grandTotal.getAuto().add(byChamberReportBean.getAuto()));
				grandTotal.setMetal(grandTotal.getMetal().add(byChamberReportBean.getMetal()));
				grandTotal.setMotor(grandTotal.getMotor().add(byChamberReportBean.getMotor()));
				grandTotal.setNewTyre(grandTotal.getNewTyre().add(byChamberReportBean.getNewTyre()));
				grandTotal.setPlastic(grandTotal.getPlastic().add(byChamberReportBean.getPlastic()));
				grandTotal.setUnknown(grandTotal.getUnknown().add(byChamberReportBean.getUnknown()));
			}
		}
		results.add(grandTotal);
		return results;
	}
	
	public List<CompanyRegionReportBean> generateCompanyRegionReport() throws Exception {
		return dao.generateCompanyRegionReport();
	}
	
	public void downloadCompanyRegionReport(Boolean employeeOrAdmin, Users sessionUser) throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "Company Name", "Trading Name", "Entity ID", "Region/Office", "Town", "Client Relations Manager Details","Client Relations Manager Email", "Client Liaison Officer Details", "Client Liaison Officer Email" });
		counter++;
		// data population
		populateDataForCompanyRegionReport(data, employeeOrAdmin, sessionUser, counter);
		counter = null;
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sh.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
			String fileName = "Comapny Region Report.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private void populateDataForCompanyRegionReport(Map<String, Object[]> data, Boolean employeeOrAdmin, Users sessionUser, Integer counter) throws Exception{
		int counterForPopulation = counter;
		List<CompanyRegionReportBean> resultsList = new ArrayList<>();
		if (employeeOrAdmin != null && employeeOrAdmin) {
			resultsList.addAll(generateCompanyRegionReport());
		} else {
			if (sessionUser == null) {
				throw new Exception("Unable to locate user when required for company region reporting. Contact support!");
			}else {
				 resultsList = new ArrayList<>();
			}
		}
		// populate data found into report
		for (CompanyRegionReportBean entry : resultsList) {
			populateDataRegionReport(data, entry, counterForPopulation);
			counterForPopulation++;
		}
		resultsList.clear();
		counter = counterForPopulation;
	}

	private void populateDataRegionReport(Map<String, Object[]> data, CompanyRegionReportBean entry, Integer counter) throws Exception{
		// new Object[] { "Company Name", "Trading Name", "Entity ID", "Region/Office", "Town", "Client Relations Manager Details","Client Relations Manager Email", "Client Liaison Officer Details", "Client Liaison Officer Email"})
		data.put(counter.toString(), new Object[] { entry.getCompanyName(), entry.getTradingName(), entry.getLevyNumber(), entry.getRegion(), entry.getTown(), entry.getCrmUserFullName(), entry.getCrmUserEmail(), entry.getCloUserFullName(), entry.getCloUserEmail() });
	}

	
}