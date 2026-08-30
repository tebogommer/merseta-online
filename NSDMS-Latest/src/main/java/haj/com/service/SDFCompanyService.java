package haj.com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jsoup.Jsoup;
import org.primefaces.model.SortOrder;

import haj.com.bean.CompanySdfReportBean;
import haj.com.bean.ExtensionRequestReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.SDFCompanyDAO;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.SDFCompany;
import haj.com.entity.SDFCompanyHistory;
import haj.com.entity.Sites;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.lookup.SDFType;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.SDFTypeService;
import haj.com.service.lookup.SizeOfCompanyService;
import haj.com.ui.CompanyInfoUI;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class SDFCompanyService.
 */
public class SDFCompanyService extends AbstractService {
	/** The dao. */
	private SDFCompanyDAO dao = new SDFCompanyDAO();

	/** The register service. */
	private RegisterService registerService = new RegisterService();

	/** The doc service. */
	private DocService docService = new DocService();

	/** The sdf type service. */
	private SDFTypeService sdfTypeService = new SDFTypeService();

	/** The company service. */
	private CompanyService companyService = new CompanyService();

	/** The banking details service. */
	private BankingDetailsService bankingDetailsService = new BankingDetailsService();

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/** The training comittee service. */
	private TrainingComitteeService trainingComitteeService = new TrainingComitteeService();
	private SitesService sitesService = new SitesService();

	private HostingCompanyService hostingCompanyService = new HostingCompanyService();
	private EmployeesService employeesService = new EmployeesService();

	public static final Long LABOR_ID = 3l;
	public static final Long EMPLOYEE_ID = 4l;

	private static SDFCompanyService sdfCompanyService = null;

	/**
	 * Instance.
	 *
	 * @return the SitesHistoryService
	 */
	public static synchronized SDFCompanyService instance() {
		if (sdfCompanyService == null) {
			sdfCompanyService = new SDFCompanyService();
		}
		return sdfCompanyService;
	}

	/**
	 * Get all SDFCompany.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SDFCompany}
	 * @throws Exception
	 *             the exception
	 * @see SDFCompany
	 */
	public List<SDFCompany> allSDFCompany() throws Exception {
		return dao.allSDFCompany();
	}

	/**
	 * Create or update SDFCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SDFCompany
	 */
	public void create(SDFCompany entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update SDFCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SDFCompany
	 */
	public void update(IDataEntity entity) throws Exception {
		this.dao.update(entity);
	}

	public void changeDetails(Company entity, Users users) throws Exception {
//		companyService.update(entity);
		companyService.updateBatch(entity);
		String desc = "";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.COMPANY_CHANGE_APPROVAL, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
	}
	
	public void changeDetailsNoTask(Company entity, Users users) throws Exception {
	System.out.println("changeDetailsNoTask");
//		companyService.update(entity);
		companyService.updateBatch(entity);
//		String desc = "";
//		TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.COMPANY_CHANGE_APPROVAL, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
	}
	
	public void updateDetails(Company entity, Users users) throws Exception {
		companyService.updateBatch(entity);
	}

	/**
	 * Delete SDFCompany.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SDFCompany
	 */
	public void delete(SDFCompany entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SDFCompany}
	 * @throws Exception
	 *             the exception
	 * @see SDFCompany
	 */
	public SDFCompany findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SDFCompany by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SDFCompany}
	 * @throws Exception
	 *             the exception
	 * @see SDFCompany
	 */
	public List<SDFCompany> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SDFCompany.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SDFCompany}
	 * @throws Exception
	 *             the exception
	 */
	public List<SDFCompany> allSDFCompany(int first, int pageSize) throws Exception {
		return dao.allSDFCompany(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SDFCompany for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SDFCompany
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SDFCompany.class);
	}

	/**
	 * Lazy load SDFCompany with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            SDFCompany class
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
	 * @return a list of {@link haj.com.entity.SDFCompany} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SDFCompany> allSDFCompany(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveSizeOfCompany((List<SDFCompany>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));

	}
	
	@SuppressWarnings("unchecked")
	public List<SDFCompany> allSDFCompanySearch(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveSizeOfCompany((List<SDFCompany>) dao.sortAndFilterSearch(class1, first, pageSize, sortField, sortOrder, filters));

	}

	@SuppressWarnings("unchecked")
	public List<SDFCompany> allSDFCompanyPrimaryOnly(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveSizeOfCompany((List<SDFCompany>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}

	/**
	 * Get count of SDFCompany for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            SDFCompany class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SDFCompany entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SDFCompany> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public int countSearch(Class<SDFCompany> entity, Map<String, Object> filters) throws Exception {
		return dao.countSearch(entity, filters);
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
	public List<SDFCompany> allCompanyFromSDF(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		return resolveSizeOfCompany(dao.allCompanyFromSDF(class1, first, pageSize, sortField, sortOrder, filters, formUser));
	}

	public List<SDFCompany> allCompanyFromSDFWherePrimary(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		return resolveSizeOfCompany(dao.allCompanyFromSDFWherePrimary(class1, first, pageSize, sortField, sortOrder, filters, formUser));
	}
	
	public List<SDFCompany> allCompanyFromSDFWherePrimarySearch(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		return resolveSizeOfCompany(dao.allCompanyFromSDFWherePrimarySearch(class1, first, pageSize, sortField, sortOrder, filters, formUser));
	}
	
	public List<SDFCompany> allWorkplaceApprovalSDFCompany(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveSizeOfCompany(dao.allWorkplaceApprovalSDFCompany(class1, first, pageSize, sortField, sortOrder, filters));
	}

	public List<SDFCompany> allCompanyFromSDFWherePrimaryAndSecondary(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		return resolveSizeOfCompany(dao.allCompanyFromSDFWherePrimaryAndSecondary(class1, first, pageSize, sortField, sortOrder, filters, formUser));
	}

	public List<SDFCompany> allCompanyFromSDFWherePrimaryAndSecondaryAndLabAndEmp(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users formUser) throws Exception {
		return resolveSizeOfCompany(dao.allCompanyFromSDFWherePrimaryAndSecondaryAndLabAndEmp(class1, first, pageSize, sortField, sortOrder, filters, formUser));
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
	public List<SDFCompany> allSDFForCompany(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company, Users formUser) throws Exception {
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

	public List<SDFCompany> allCompanyFromSDFWherePrimary(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveSizeOfCompany(dao.allCompanyFromSDFWherePrimary(class1, first, pageSize, sortField, sortOrder, filters));
	}
	
	public List<SDFCompany> allActiveCompanyFromSDFWherePrimary(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveSizeOfCompany(dao.allActiveCompanyFromSDFWherePrimary(class1, first, pageSize, sortField, sortOrder, filters));
	}
	
	public int allActiveCompanyFromSDFCountWherePrimary(Map<String, Object> filters) throws Exception {
		return dao.allActiveCompanyFromSDFCountWherePrimary(filters);
	}

	public Long allCompanyFromSDFCountWherePrimary(Map<String, Object> filters, Users formUser) throws Exception {
		return dao.allCompanyFromSDFCountWherePrimary(filters, formUser);
	}
	
	public Long allCompanyFromSDFCountWherePrimarySearch(Map<String, Object> filters, Users formUser) throws Exception {
		return dao.allCompanyFromSDFCountWherePrimarySearch(filters, formUser);
	}
	
	public int allWorkplaceCompanySDFCount(Map<String, Object> filters) throws Exception {
		return (int) dao.allWorkplaceCompanySDFCount(filters);
	}

	public int allCompanyFromSDFCountWherePrimary(Map<String, Object> filters) throws Exception {
		return dao.allCompanyFromSDFCountWherePrimary(filters);
	}

	public int allCompanyFromSDFCountWherePrimaryAndSecondary(Map<String, Object> filters, Users formUser) throws Exception {
		return dao.allCompanyFromSDFCountWherePrimaryAndSecondary(filters, formUser);
	}

	public int allCompanyFromSDFCountWherePrimaryAndSecondaryLabAndEmpAndAltEmp(Map<String, Object> filters, Users formUser) throws Exception {
		return dao.allCompanyFromSDFCountWherePrimaryAndSecondaryLabAndEmpAndAltEmp(filters, formUser);
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
			SDFCompany sdfCompany = new SDFCompany(company, sdf, sdfType);
			iDataEntities.add(sdfCompany);
			CompanyUsers cu = companyUsersService.findByUserAndCompany(company.getId(), sdf.getId());
			if (cu == null) {
				cu = new CompanyUsers(sdf, company);
				cu.setCompanyUserType(ConfigDocProcessEnum.SDF);
				iDataEntities.add(cu);
			}
			dao.createBatch(iDataEntities);
		} else {
			SDFCompany sc = findByCompanyAndUser(company, sdf);
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
		SDFCompany sdfCompany = findByCompanyAndUser(company, sdf);
		sdfCompany.setSdfType(sdfType);
		update(sdfCompany);
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
	public List<SDFCompany> bySDF(Long sdfId) throws Exception {
		return resolveEverything(dao.bySDF(sdfId));
	}

	public List<SDFCompany> bySDFPrimary(Long sdfId) throws Exception {
		return resolveEverything(dao.bySDFPrimary(sdfId));
	}

	public List<SDFCompany> resolveEverything(List<SDFCompany> sdfs) throws Exception {
		sdfs.forEach(a -> {

			try {
				a.getCompany().setDocs(docService.searchByCompany(a.getCompany()));
				companyService.prepareDocs(ConfigDocProcessEnum.SDF, a.getCompany(), CompanyUserTypeEnum.Company);
				a.getCompany().setLinkedCompanies(dao.linkedCompany(a));
				a.getCompany().setSites(dao.sitesByCompany(a));
				a.getCompany().setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(a.getCompany()));
				a.getCompany().setCompanyHistories(CompanyHistoryService.instance().findByCompany(a.getCompany()));
			} catch (Exception e) {
				logger.fatal(e);
			}
		});

		return sdfs;

	}

	private List<SDFCompany> resolveSizeOfCompany(List<SDFCompany> sdfs) throws Exception {
		for (SDFCompany a : sdfs) {
			if (a.getCompany() != null) {
				a.getCompany().setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(a.getCompany()));
			}
		}
		return sdfs;
	}

	/**
	 * Resolve links.
	 *
	 * @param sdfs
	 *            the sdfs
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	private List<SDFCompany> resolveLinks(List<SDFCompany> sdfs) throws Exception {
		for (SDFCompany sdfCompany : sdfs) {
			sdfCompany.getCompany().setLinkedCompanies(resolveDocs(dao.linkedCompany(sdfCompany)));
		}
		return sdfs;
	}

	public List<SDFCompany> resolveSizeOfCompanyWithRegion(List<SDFCompany> l) throws Exception {
		// List<WspSignoff> l = dao.findCompletedSignOffs();
		for (SDFCompany a : l) {
			try {
				a.getCompany().setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(a.getCompany()));
				if(a.getCompany() !=null && a.getCompany().getResidentialAddress()!=null&&a.getCompany().getResidentialAddress().getTown()!=null) {
					a.setRegionTown(RegionTownService.instance().findByTown(a.getCompany().getResidentialAddress().getTown()));	
				}
			} catch (org.hibernate.NonUniqueResultException ex) {

			} catch (Exception e) {
				logger.fatal(e.getMessage(), e);
			}

		}
		return l;
	}

	/**
	 * Resolve sites.
	 *
	 * @param sdfs
	 *            the sdfs
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	private List<SDFCompany> resolveSites(List<SDFCompany> sdfs) throws Exception {
		for (SDFCompany sdfCompany : sdfs) {
			sdfCompany.getCompany().setSites(dao.sitesByCompany(sdfCompany));
		}
		return sdfs;
	}
	
	public List<SDFCompany> resolveSDFs(List<SDFCompany> sdfs) throws Exception {
		for (SDFCompany sdfCompany : sdfs) {
			sdfCompany.setSdfType(sdfTypeService.findByKey(sdfCompany.getSdfType().getId()));
		}
		return sdfs;
	}
	

	/**
	 * Resolve docs.
	 *
	 * @param sdfs
	 *            the sdfs
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<SDFCompany> resolveDocs(List<SDFCompany> sdfs) throws Exception {
		sdfs.forEach(a -> {

			try {
				a.getCompany().setDocs(docService.searchByCompany(a.getCompany()));
				companyService.prepareDocs(ConfigDocProcessEnum.SDF, a.getCompany(), CompanyUserTypeEnum.Company);
			} catch (Exception e) {
				logger.fatal(e);
			}
		});

		return sdfs;
	}

	public SDFCompany resolveDocs(SDFCompany a) throws Exception {

		try {
			a.getCompany().setDocs(docService.searchByCompany(a.getCompany()));
			companyService.prepareDocs(ConfigDocProcessEnum.SDF, a.getCompany(), CompanyUserTypeEnum.Company);
		} catch (Exception e) {
			logger.fatal(e);
		}

		return a;
	}

	/**
	 * Resolve docs not reg.
	 *
	 * @param sdfs
	 *            the sdfs
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	private List<SDFCompany> resolveDocsNotReg(List<SDFCompany> sdfs) throws Exception {
		sdfs.forEach(a -> {
			try {
				a.getCompany().setDocs(docService.searchByCompany(a.getCompany()));
			} catch (Exception e) {
				logger.fatal(e);
			}
		});
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
		
		// Enhancement: remove union if recognition agreement is no and union previously selected
		if (company.getRecognitionAgreement() != null && company.getRecognitionAgreement().getId() == HAJConstants.NO_ID) {
			if (company.getMajorityUnion() != null) {
				company.setMajorityUnion(null);
			}
		}

		AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
		CompanyHistoryService.instance().createHistory(company.getId());
		dao.update(company);
	}

	/**
	 * Approve task.
	 *
	 * @param sdf
	 *            the sdf
	 * @param task
	 *            the task
	 * @param user
	 *            the user
	 * @param details
	 *            the details
	 * @throws Exception
	 *             the exception
	 */
	public void approveTask(Users sdf, Tasks task, Users user, List<SDFCompany> details) throws Exception {
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload) {
			doChecksUserCompany(sdf, details, true, true, false, true, true, true, false, true);
		}
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.Edit) {
			doChecksUserCompany(null, details, false, true, false, true, true, true, false, true);
		}
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval) {
			doChecksUserCompany(null, details, false, false, false, false, false, false, false, false);

			if (sdf.getApprovedDate() == null) {
				sdf.setApprovalEnum(ApprovalEnum.Approved);
				sdf.setApproved(true);
				sdf.setApprovedDate(getSynchronizedDate());
			}

			dao.update(sdf);

			for (SDFCompany cs : details) {
				cs.getCompany().setCompanyStatus(CompanyStatusEnum.Approved);
				approveCompany(sdf, task, cs);
				List<Sites> sites = sitesService.findByCompany(cs.getCompany());
				for (Sites site : sites) {
					site.setSiteStatus(CompanyStatusEnum.Approved);
				}
			}
		}

		sdf.setActive(true);
		sdf.setStatus(UsersStatusEnum.Active);
		dao.update(sdf);

		TasksService.instance().findNextInProcessAndCreateTask("New SDF for approval", user, task.getTargetKey(), task.getTargetClass(), true, task, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, sdf.getFirstName(), MailTagsEnum.LastName, sdf.getLastName()), null);
	}

	private void approveCompany(Users sdf, Tasks task, SDFCompany cs) throws Exception {

		cs.getCompany().setCompanyStatus(CompanyStatusEnum.Active);

		if (cs.getCompany().getNonLevyPaying() != null && cs.getCompany().getNonLevyPaying() && cs.getCompany().getLevyNumber() == null) {
			companyService.assignNNumber(cs.getCompany());
		}

		List<CompanyUsers> cu = companyUsersService.findByCompanyWithPrimary(cs.getCompany());
		try {
			Doc d = genApplicationForm(cs.getCompany());
			for (CompanyUsers companyUsers : cu) {
				GenericUtility.sendMailWithAttachement(companyUsers.getUser().getEmail(), "Application finished processing", "Your company " + cs.getCompany().getCompanyName() + " has been approved on the merSETA NSDMS system.", d.getData(), d.getNewFname(), d.getExtension(), null);
			}
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
		}

		cs.getCompany().setApprovalDate(getSynchronizedDate());
		if (cs.getCompany().getPreviousCompany() != null) {
			deregisterCompany(sdf, cs.getCompany().getPreviousCompany());
		}
		companyService.update(cs.getCompany());

		if (cs.getCompany().getLinkedCompanies().size() > 0) {
			for (SDFCompany sdfCompany2 : cs.getCompany().getLinkedCompanies()) {
				sdfCompany2.getCompany().setCompanyStatus(CompanyStatusEnum.Active);
				if (sdfCompany2.getCompany().getNonLevyPaying() != null && sdfCompany2.getCompany().getNonLevyPaying() && sdfCompany2.getCompany().getLevyNumber() == null) {
					companyService.assignNNumber(sdfCompany2.getCompany());
				}
				cu = companyUsersService.findByCompanyWithPrimary(sdfCompany2.getCompany());
				Doc d = genApplicationForm(sdfCompany2.getCompany());
				for (CompanyUsers companyUsers : cu) {
					GenericUtility.sendMailWithAttachement(companyUsers.getUser().getEmail(), "Application finished processing", "Your company " + cs.getCompany().getCompanyName() + " has been approved on the merSETA NSDMS system.", d.getData(), d.getNewFname(), d.getExtension(), null);
				}
				sdfCompany2.getCompany().setApprovalDate(getSynchronizedDate());
				companyService.update(sdfCompany2.getCompany());
			}
		}
	}
	
	private void deregisterCompany(Users sdf,  Company c) throws Exception{
		c.setLocked(true);
		c.setCompanyStatus(CompanyStatusEnum.DeRegistered);
		c.setDeregisterDate(getSynchronizedDate());
		c.setDeregisterUser(sdf);
		companyService.updateNoHistory(c);
	}

	public Doc genApplicationForm(Company company) {
		Doc d = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("company_id", company.getId());
		try {
			JasperService.addLogo(params);
			JasperService.addApplicationSubreport(params);
			d = JasperService.instance().createReportFromDBtoDoc("MerSETAReport.jasper", params, company.getLevyNumber() + "-(" + GenericUtility.sdf6.format(getSynchronizedDate()) + ")-Application.pdf");
			// JasperService.instance().createReportFromDBtoServletOutputStream("MerSETAReport.jasper",
			// params, "Doc.pdf");
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
		}
		return d;
	}

	/**
	 * Complete task.
	 *
	 * @param sdf
	 *            the sdf
	 * @param task
	 *            the task
	 * @param user
	 *            the user
	 * @param companies
	 *            the companies
	 * @throws Exception
	 *             the exception
	 */
	public void completeTask(Users sdf, Tasks task, Users user, List<SDFCompany> companies) throws Exception {
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload) {
			doChecksUserCompany(sdf, companies, true, true, false, true, true, true, false, true);
		}
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.Upload) {
			doChecksUserCompany(sdf, companies, true, false, false, false, false, false, false, false);
		}
		if (task.getProcessRole().getRolePermission() == UserPermissionEnum.Edit) {
			doChecksUserCompany(null, companies, false, true, false, true, true, true, false, true);
		}
		TasksService.instance().findNextInProcessAndCreateTask("New SDF for approval", user, task.getTargetKey(), task.getTargetClass(), true, task, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, sdf.getFirstName(), MailTagsEnum.LastName, sdf.getLastName()), null);
	}

	public void completeToFirst(Users formUser, Tasks task) throws Exception {
		TasksService.instance().completeTask(task);
		TasksService.instance().findFirstInProcessAndCreateTask("SDF has resubmitted please review.", formUser, task.getTargetKey(), task.getTargetClass(), true, ConfigDocProcessEnum.SDF, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, formUser.getFirstName(), MailTagsEnum.LastName, formUser.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.SDF.getTaskDescription()), null);
	}

	/**
	 * Do checks user company.
	 *
	 * @param user
	 *            the user
	 * @param sdfCompanies
	 *            the sdf companies
	 * @param companyDocCheck
	 *            the company doc check
	 * @param companyInfoCheck
	 *            the company info check
	 * @param bankingDetailsCheck
	 *            the banking details check
	 * @param trainingComitteeChecks
	 *            the training comittee checks
	 * @param checkSDF
	 *            the check SDF
	 * @param checkUsers
	 *            the check users
	 * @param checkapproval
	 *            the checkapproval
	 * @throws Exception
	 *             the exception
	 */
	private void doChecksUserCompany(Users user, List<SDFCompany> sdfCompanies, Boolean companyDocCheck, Boolean companyInfoCheck, Boolean bankingDetailsCheck, Boolean trainingComitteeChecks, Boolean checkSDF, Boolean checkUsers, Boolean checkapproval, Boolean checkemployees) throws Exception {
		StringBuilder exceptions = new StringBuilder("");
		if (user != null && user.getDocs() != null) {
			for (Doc doc : user.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						exceptions.append("Upload Document: <i>" + doc.getConfigDoc().getName() + "</i> for user:<i>" + user.getFirstName() + " " + user.getLastName() + "</i><br/>");
					}
				}
			}
		}
		if (sdfCompanies != null) {
			for (SDFCompany sdfCompany : sdfCompanies) {

				doChecks(companyDocCheck, companyInfoCheck, bankingDetailsCheck, trainingComitteeChecks, checkSDF, checkUsers, checkapproval, checkemployees, exceptions, sdfCompany);

				if (sdfCompany.getCompany().getLinkedCompanies().size() > 0) {

					for (SDFCompany sdfCompany2 : sdfCompany.getCompany().getLinkedCompanies()) {
						doChecks(false, companyInfoCheck, false, false, false, false, false, false, exceptions, sdfCompany2);
					}

				}

			}
		}

		if (exceptions.length() > 0) {
			throw new Exception(exceptions.toString());
		}
	}

	/**
	 * Do checks.
	 *
	 * @param companyDocCheck
	 *            the company doc check
	 * @param companyInfoCheck
	 *            the company info check
	 * @param bankingDetailsCheck
	 *            the banking details check
	 * @param trainingComitteeChecks
	 *            the training comittee checks
	 * @param checkSDF
	 *            the check SDF
	 * @param checkUsers
	 *            the check users
	 * @param checkapproval
	 *            the checkapproval
	 * @param exceptions
	 *            the exceptions
	 * @param sdfCompany
	 *            the sdf company
	 * @throws Exception
	 *             the exception
	 */
	private void doChecks(Boolean companyDocCheck, Boolean companyInfoCheck, Boolean bankingDetailsCheck, Boolean trainingComitteeChecks, Boolean checkSDF, Boolean checkUsers, Boolean checkapproval, Boolean checkemployees, StringBuilder exceptions, SDFCompany sdfCompany) throws Exception {
		String company = "<h3>" + sdfCompany.getCompany().getCompanyName() + "</h3><ul>";
		if (companyInfoCheck) {
			
			String ex = sdfCompany.getCompany().getValidCompany();
			if (ex.length() > 0) {
				if (exceptions.indexOf(company) == -1) {
					exceptions.append(company);
				}
				exceptions.append(ex);
			}
			/*
			 * // enhancement on reg agreement
			if (sdfCompany.getCompany().getRecognitionAgreement() != null && sdfCompany.getCompany().getRecognitionAgreement().getId() == HAJConstants.NO_ID) {
				if (sdfCompany.getCompany().getMajorityUnion() != null) {
					sdfCompany.getCompany().setMajorityUnion(null);
				}
			}
			 */
		}
		if (companyDocCheck) {
			for (Doc doc : sdfCompany.getCompany().getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						if (exceptions.indexOf(company) == -1) {
							exceptions.append(company);
						}
						exceptions.append("<li>Document " + doc.getConfigDoc().getName() + " is still outstanding</li>");
					}
				}
			}
		}
		if (bankingDetailsCheck) {
			if (bankingDetailsService.findByCompanyCount(sdfCompany.getCompany()) == 0) {
				if (exceptions.indexOf(company) == -1) {
					exceptions.append(company);
				}
				exceptions.append("<li>No banking details have been created.</li>");
			}
		}
		if (trainingComitteeChecks) {
			if (sdfCompany.getCompany().getTrainingCommitteeInPlace() != null && sdfCompany.getCompany().getTrainingCommitteeInPlace() && trainingComitteeService.findByCompanyCount(sdfCompany.getCompany()) < 2) {
				if (exceptions.indexOf(company) == -1) {
					exceptions.append(company);
				}
				exceptions.append("<li>Training comittees is required</li>");
			}
		}
		if (checkSDF) {
			if (sdfCompany.getCompany().getNonLevyPaying() == null || !sdfCompany.getCompany().getNonLevyPaying()) {
				checkRequired(exceptions, sdfCompany, company);
			} else {
				if (sdfCompany.getCompany().getSicCode() != null && !"Unknown".equals(sdfCompany.getCompany().getSicCode().getChamber().getCode())) checkRequired(exceptions, sdfCompany, company);

			}
		}
		if (checkUsers) {
			if (dao.findByCompanyUser(sdfCompany.getCompany().getId()) == 0) {

				if (exceptions.indexOf(company) == -1) exceptions.append(company);

				exceptions.append("<li>At least one non-sdf user is requred for the company</li>");
			}
		}
		if (checkapproval) {
			if (sdfCompany.getCompany().getCompanyStatus() == CompanyStatusEnum.Pending) {
				if (exceptions.indexOf(company) == -1) exceptions.append(company);
				exceptions.append("<li>Approval/Rejection is required</li>");
			}
		}
		if (checkemployees) {
			if (employeesService.countByCompany(sdfCompany.getCompany()) == 0) exceptions.append("<li>Employee information is required</li>");
			if (sdfCompany.getCompany().getNumberOfEmployees() != null) if (employeesService.countByCompany(sdfCompany.getCompany()).intValue() != sdfCompany.getCompany().getNumberOfEmployees()) exceptions.append("<li>Number of employees does not match the captured amount for the company</li>");
		}
		if (exceptions.indexOf(company) > -1) {
			exceptions.append("</ul>");
		}
	}

	public void checkRequired(StringBuilder exceptions, SDFCompany sdfCompany, String company) throws Exception {
		String sdfRequired = "";
		Long requiredCount = 0l;
		if (sdfCompany.getCompany().getNumberOfEmployees() != null && sdfCompany.getCompany().getNumberOfEmployees() < 50 && sdfCompany.getCompany().getRecognitionAgreement() != null) {
			if (sdfCompany.getCompany().getRecognitionAgreement().getId() == HAJConstants.YES_ID) {
				requiredCount = sdfTypeService.allSDFTypeUsedCount(sdfCompany.getCompany(), LABOR_ID);
				sdfRequired += "A labour SDF is required since you have a recognition agreement in place";
			}
		} else if (sdfCompany.getCompany().getNumberOfEmployees() != null && sdfCompany.getCompany().getNumberOfEmployees() > 49 && sdfCompany.getCompany().getRecognitionAgreement() != null) {

			if (sdfCompany.getCompany().getRecognitionAgreement().getId() == HAJConstants.YES_ID) {
				requiredCount = sdfTypeService.allSDFTypeUsedCount(sdfCompany.getCompany(), LABOR_ID);
				sdfRequired += "A labour SDF is required since you have a recognition agreement in place";
			} else {
				requiredCount = sdfTypeService.allSDFTypeUsedCount(sdfCompany.getCompany(), EMPLOYEE_ID);
				sdfRequired += "A employee SDF is required since you dont have a recognition agreement in place and have more than 50 employees";
			}
		}
		if (requiredCount > 0) {
			if (exceptions.indexOf(company) == -1) {
				exceptions.append(company);
			}
			exceptions.append("<li>" + sdfRequired + "</li>");
		}
	}

	/**
	 * Do check.
	 *
	 * @param companies
	 *            the companies
	 * @throws Exception
	 *             the exception
	 */
	public void doCheck(List<SDFCompany> companies) throws Exception {
		StringBuilder exceptions = new StringBuilder("");
		for (SDFCompany sdfCompany : companies) {
			String ex = sdfCompany.getCompany().getValidCompany();
			if (ex.length() > 0) {
				exceptions.append("<h3>" + sdfCompany.getCompany().getCompanyName() + "</h3><ul>" + ex + "</ul>");
			}
			if (sdfTypeService.allSDFTypeUsedCount(sdfCompany.getCompany()) == 0) {
				exceptions.append("Please configure all SDFs for company " + sdfCompany.getCompany().getCompanyName());
			}
		}
		if (exceptions.length() > 0) {
			throw new Exception(exceptions.toString());
		}
	}

	/**
	 * Reject task.
	 *
	 * @param sdf
	 *            the sdf
	 * @param task
	 *            the task
	 * @param user
	 *            the user
	 * @throws Exception
	 *             the exception
	 */
	public void rejectTask(Users sdf, Tasks task, Users user) throws Exception {
		if (task.getFirstInProcess()) {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(sdf);
			TasksService.instance().createTaskUser(signoffs, task.getTargetClass(), task.getTargetKey(), "Your NSDMS application was rejected please login and view the reason and make the relevant changes.", user, true, true, task, ConfigDocProcessEnum.SDF, false);
			TasksService.instance().completeTask(task);
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask("New SDF for approval", user, task.getTargetKey(), task.getTargetClass(), true, task, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, sdf.getFirstName(), MailTagsEnum.LastName, sdf.getLastName()), null);
		}
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
	public List<SDFCompany> findByCompanySignOff(Company company, boolean signOff) throws Exception {
		return dao.findByCompanySignOff(company.getId(), signOff);
	}

	public List<SDFCompany> findByCompany(Company company) throws Exception {
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

	public long findByCompanyCountPrimary(Company company) throws Exception {
		if (company.getId() == null) return 0;
		return dao.findByCompanyCountPrimary(company.getId());

	}

	/**
	 * Creates the secondary SDF And Create Task.
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
	public void createSecondarySDFAndCreateTask(Company company, Users sdf, SDFType sdfType, Users user) throws Exception {
		List<IDataEntity> iDataEntities = new ArrayList<>();
		boolean createCompanyUsertask = false;
		SDFCompany sc = null;

		if (sdf.getId() != null) sc = findByCompanyAndUser(company, sdf);

		if (sc == null || sc.getId() == null) {

			if (sdf.getId() == null) sdf = registerService.register(sdf, true);

			if (findByCompanyAndUser(company, sdf) != null) {
				throw new Exception("The selected user is already assigned to the company");
			}

			SDFCompany sdfCompany = new SDFCompany(company, sdf, sdfType);
			sdfCompany.setApprovalStatus(ApprovalEnum.PendingApproval);
			iDataEntities.add(sdfCompany);
			CompanyUsers cu = companyUsersService.findByUserAndCompany(company.getId(), sdf.getId());
			if (cu == null) {
				cu = new CompanyUsers(sdf, company);
				cu.setCompanyUserType(ConfigDocProcessEnum.SDF);
				cu.setApprovalStatus(ApprovalEnum.PendingApproval);
				iDataEntities.add(cu);
				// createCompanyUsertask = true;
			}
			dao.createBatch(iDataEntities);

			if (createCompanyUsertask) {
				// Creating workflow
				String desc = "Company User has been added, please approve the information provided";
				TasksService.instance().findFirstInProcessAndCreateTask(desc, user, cu.getId(), cu.getClass().getName(), true, ConfigDocProcessEnum.NEW_COMPANY_USER, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
				// Adding Change Reason
				ChangeReasonService.instance().createChangeReason(sdfCompany.getId(), sdfCompany.getClass().getName(), CompanyInfoUI.changeReason);
			}

			// Creating CompanyUser Task
			String desc = "New SDF added, please approve the information provided";
			TasksService.instance().findFirstInProcessAndCreateTask(desc, user, sdfCompany.getId(), sdfCompany.getClass().getName(), true, ConfigDocProcessEnum.NEW_SDF, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			// Adding Change Reason for Creating CompanyUser
			ChangeReasonService.instance().createChangeReason(sdfCompany.getId(), sdfCompany.getClass().getName(), CompanyInfoUI.changeReason);

		} else {
			UsersService usersService=new UsersService();
			SDFCompany sdfc = findByKey(sc.getId());
			
			 /*if (sdfc.getSdf().getEmail().trim().equals(sdf.getEmail().trim())) {
				 registerService.checkUserException(sdf); 
			 }*/
			usersService.updateUserValidation(sdfc.getSdf(), sdf);
			
			// Creating SDFCompanyHistory
			SDFCompanyHistoryService.instance().createHistory(sc.getId());
			sc.setApprovalStatus(ApprovalEnum.PendingApproval);
			sc.setSdfType(sdfType);
			dao.update(sc);
			dao.update(sdf);
			// Adding Change Reason for Creating CompanyUser
			ChangeReasonService.instance().createChangeReason(sc.getId(), sc.getClass().getName(), CompanyInfoUI.changeReason);
			String desc = "SDF deatils has been updated, please approve the information provided";
			TasksService.instance().findFirstInProcessAndCreateTask(desc, user, sc.getId(), sc.getClass().getName(), true, ConfigDocProcessEnum.SDF_CHANGE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		}
		// Clearing change reason
		CompanyInfoUI.changeReason = new ChangeReason();
	}

	/**
	 * Approve new SDF
	 * 
	 * @param SDFCompany
	 *            the SDFCompany
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void approveNewSDFTask(SDFCompany sdfCompany, Tasks task) throws Exception {
		sdfCompany.setApprovalStatus(ApprovalEnum.Approved);
		update(sdfCompany);
		TasksService.instance().completeTask(task);
		// Sending Email to task creator
		UsersService usersService = new UsersService();
		// Sending email to user who created the SDF
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "SDF Approval", " New Skills Development Facilitator(SDF) (" + sdfCompany.getSdf().getFirstName() + " " + sdfCompany.getSdf().getLastName() + ") has been approved on the merSETA NSDMS system.", null);
		// Sending email to SDF
		GenericUtility.sendMail(sdfCompany.getSdf().getEmail(), "SDF Approval", "You have been added as  Skills Development Facilitator(SDF) for " + sdfCompany.getCompany().getCompanyName(), null);
	}

	/**
	 * Approve SDF Changes
	 * 
	 * @param SDFCompany
	 *            the SDFCompany
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void approveSDFChangesTask(SDFCompany sdfCompany, Tasks task) throws Exception {
		sdfCompany.setApprovalStatus(ApprovalEnum.Approved);
		update(sdfCompany);
		TasksService.instance().completeTask(task);
		// Sending Email to task creator
		UsersService usersService = new UsersService();
		// Sending email to user who created the SDF
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "SDF Approval", "Skills Development Facilitator (" + sdfCompany.getSdf().getFirstName() + " " + sdfCompany.getSdf().getLastName() + ") changes has been approved on the merSETA NSDMS system.", null);
		// Sending email to SDF
		GenericUtility.sendMail(sdfCompany.getSdf().getEmail(), "SDF Approval", "Your Skills Development Facilitator(SDF) changes have been approved", null);
	}

	/**
	 * Reject SDF Changes
	 * 
	 * @param SDFCompany
	 *            the SDFCompany
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void rejectSDFChangesTask(SDFCompany sdfCompany, SDFCompanyHistory history, Tasks task) throws Exception {
		Long tempId = sdfCompany.getId();
		BeanUtils.copyProperties(sdfCompany, history);
		sdfCompany.setId(tempId);
		update(sdfCompany);

		TasksService.instance().completeTask(task);

		UsersService usersService = new UsersService();
		// Sending email to user who created the SDF
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "SDF Rejection", "Skills Development Facilitator (" + sdfCompany.getSdf().getFirstName() + " " + sdfCompany.getSdf().getLastName() + ") changes has been rejected on the merSETA NSDMS system.", null);
		// Sending email to SDF
		GenericUtility.sendMail(sdfCompany.getSdf().getEmail(), "SDF Rejection", "Your Skills Development Facilitator(SDF) changes have been rejected ", null);
	}

	/**
	 * Reject new SDF
	 * 
	 * @param SDFCompany
	 *            the SDFCompany
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void rejectNewSDFTask(SDFCompany sdfCompany, Tasks task) throws Exception {
		sdfCompany.setApprovalStatus(ApprovalEnum.Rejected);
		update(sdfCompany);
		TasksService.instance().completeTask(task);
		// Sending Email to task creator
		UsersService usersService = new UsersService();
		// Sending email to user who created the SDF
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "SDF Rejection", " New Skills Development Facilitator (" + sdfCompany.getSdf().getFirstName() + " " + sdfCompany.getSdf().getLastName() + ") has been rejected on the merSETA NSDMS system.", null);
		// Sending email to SDF
		GenericUtility.sendMail(sdfCompany.getSdf().getEmail(), "SDF Rejection", "You have been rejected as  Skills Development Facilitator(SDF) for " + sdfCompany.getCompany().getCompanyName(), null);
	}

	@SuppressWarnings("unchecked")
	public void approveDeleteSDFTask(SDFCompany sdfCompany, Tasks task) throws Exception {
		TasksService.instance().completeTask(task);
		if (sdfCompany != null && sdfCompany.getId() != null) {
			SDFCompanyHistoryService.instance().createHistory(sdfCompany.getId());
			SDFCompanyHistoryService.instance().deleteBySDF(sdfCompany.getId());
		}
		dao.delete(sdfCompany);
		List<CompanyUsers> cu = companyUsersService.findByUserAndCompanyList(sdfCompany.getCompany().getId(), sdfCompany.getSdf().getId());
		if (cu.size() > 0) dao.deleteBatch((List<IDataEntity>) (List<?>) cu);
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "SDF Approval", " Your request to delete SDF (" + sdfCompany.getSdf().getFirstName() + " " + sdfCompany.getSdf().getLastName() + ") has been approved on the merSETA NSDMS system.", null);

	}

	public void rejectDeleteSDFTask(SDFCompany sdfCompany, Tasks task) throws Exception {
		sdfCompany.setApprovalStatus(null);
		update(sdfCompany);
		TasksService.instance().completeTask(task);
		UsersService usersService = new UsersService();
		// Sending email to user who created the SDF
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "SDF Rejection", " Your request to delete SDF (" + sdfCompany.getSdf().getFirstName() + " " + sdfCompany.getSdf().getLastName() + ") has been rejected on the merSETA NSDMS system.", null);

	}

	public SDFCompany findPrimarySDF(Company company) throws Exception {
		return dao.findPrimarySDF(company);
	}

	public List<SDFCompany> findAllPrimarySDF(Company company) throws Exception {
		return dao.findAllPrimarySDF(company);
	}

	public SDFCompany locateFirstPrimarySDF(Company company) throws Exception {
		List<SDFCompany> sdfList = findAllPrimarySDF(company);
		SDFCompany selectedSdf = null;
		for (SDFCompany sdfCompany : sdfList) {
			selectedSdf = findByKey(sdfCompany.getId());
			break;
		}
		return selectedSdf;
	}

	public List<SDFCompany> findPrimaryAndLabourSDF(Company company) throws Exception {
		return dao.findPrimaryAndLabourSDF(company);
	}

	public List<Users> findPrimaryAndLabourSDFUsers(Company company) throws Exception {
		return dao.findPrimaryAndLabourSDFUsers(company);
	}
	
	public List<Users> findSDFUsers(Company company) throws Exception {
		return dao.findSDFUsers(company);
	}

	public List<SDFCompany> findNotCompletedWsp(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveSizeOfCompanyWithRegion(dao.findNotCompletedWsp(class1, first, pageSize, sortField, sortOrder, filters));
	}

	public List<SDFCompany> findNotCompletedWsp(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users u) throws Exception {
		return resolveSizeOfCompanyWithRegion(dao.findNotCompletedWsp(class1, first, pageSize, sortField, sortOrder, filters, u));
	}
	
	public List<SDFCompany> findNotCompletedWspByFinYear(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear) throws Exception {
		List<SDFCompany> list = resolveSizeOfCompanyWithRegion(dao.findNotCompletedWspByFinYear(class1, first, pageSize, sortField, sortOrder, filters, finYear));
		resolveWOfCompanyWithYearlist(list, finYear);
		return list;
	}
	
	public List<SDFCompany> findNotCompletedWspByUserRegionAndByFinYear(Class<SDFCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users u, Integer finYear) throws Exception {
		List<SDFCompany> list = resolveSizeOfCompanyWithRegion(dao.findNotCompletedWspByUserRegionAndByFinYear(class1, first, pageSize, sortField, sortOrder, filters, u, finYear));
		resolveWOfCompanyWithYearlist(list, finYear);
		return list;
	}

	public Long countNotCompletedWsp(Map<String, Object> filters, Users u) throws Exception {
		return dao.countNotCompletedWsp(filters, u);
	}

	public Long countNotCompletedWsp(Map<String, Object> filters) throws Exception {
		return dao.countNotCompletedWsp(filters);
	}
	
	public Long countNotCompletedWspByFinYear(Map<String, Object> filters, Integer finYear) throws Exception {
		return dao.countNotCompletedWspByFinYear(filters, finYear);
	}
	
	public Long countNotCompletedWspByUserRegionAndByFinYear(Map<String, Object> filters, Users u, Integer finYear) throws Exception {
		return dao.countNotCompletedWspByUserRegionAndByFinYear(filters, u, finYear);
	}
	

	/**
	 * Locates the company's SDF and by type
	 * 
	 * @param company
	 * @param sdfTypeId
	 * @see SDFType.java
	 * @see Company.java
	 * @return List<SDFCompany>
	 * @throws Exception
	 */
	public List<SDFCompany> findByCompanyAndSdfType(Company company, Long sdfTypeId) throws Exception {
		return dao.findByCompanyAndSdfType(company.getId(), sdfTypeId);
	}

	/**
	 * Checks if user already exists, checks which field is duplicated
	 * 
	 * @param newSDF
	 * @param sdfCompany
	 * @throws Exception
	 */
	public void checkByCriteria(Users newSDF, Company sdfCompany) throws Exception {
		List<Users> userErrorsList = dao.findByUserAttributes(newSDF, sdfCompany.getId());
		String errors = "";
		for (Users user : userErrorsList) {
			if (user.getEmail().toLowerCase().equals(newSDF.getEmail().toLowerCase())) {
				errors += " email  ";
			}

			if (user.getRsaIDNumber() == newSDF.getRsaIDNumber()) {
				errors += " RSA Id  ";
			}

			if (user.getPassportNumber().equals(newSDF.getPassportNumber())) {
				if (user.getPassportNumber().length() != 0 && newSDF.getPassportNumber().length() != 0) {
					errors += " passport  ";
				}
			}
			break;
		}
		userErrorsList = null;
		if (!errors.isEmpty() && errors.length() != 0) {
			throw new Exception("The following info is used already:" + errors);
		}
	}
	
	private List<SDFCompany> resolveWOfCompanyWithYearlist(List<SDFCompany> list, Integer finYear) throws Exception {
		for(SDFCompany sDFCompany:list) {
			findByCompanyAndFinancialYear(sDFCompany.getCompany().getId(), finYear, sDFCompany);
			findExtensionRequestCompanyAndFinancialYear(sDFCompany,finYear);
		}
		return list;
	}
	
	public void findByCompanyAndFinancialYear(Long companyId, int finYear, SDFCompany sDFCompany)throws Exception {		
		WspService wspService = new WspService();
		Wsp wsp = wspService.findByCompanyAndFinancialYear(companyId, finYear).get(0);
		if(wsp!= null && wsp.getWspStatusEnum() != null) {
			sDFCompany.setWspStatusEnum(wsp.getWspStatusEnum());
		}
	}

	private void findExtensionRequestCompanyAndFinancialYear(SDFCompany sDFCompany, Integer finYear) throws Exception {	
		if(ExtensionRequestService.instance().findByCompanyNoWSPWithFinYear(sDFCompany.getCompany(), finYear).size() > 0) {
			ExtensionRequest extensionRequest = ExtensionRequestService.instance().findByCompanyNoWSPWithFinYear(sDFCompany.getCompany(), finYear).get(0);
			if(extensionRequest!= null) {
				sDFCompany.setExtensionRequest(extensionRequest);
			}
		}		
	}
	
	public Boolean checkIfPrimarOrSecondaryCanRegisterLearners(Users user, Company company) throws Exception {
		Boolean isValid = false;
		SDFCompany sDFCompany = dao.findByCompanyAndUser(company.getId(), user.getId());
		if(sDFCompany != null && sDFCompany.getSdfType() != null && sDFCompany.getSdfType().getRegisterLearners() != null && sDFCompany.getSdfType().getRegisterLearners()) {
			isValid= true;
		}
		return isValid;
	}
	
	public List<CompanySdfReportBean> populateCompanySdfReport() throws Exception {
		return dao.populateCompanySdfReport();
	}
	
	public void downloadCompanySdfReport() throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "Entity ID", "Name Of Company", "Trading Name", "Region", "CLO Name and Surname", "SDF Name", "SDF Surname", "SDF Email", "SDF ID Number", "SDF Passport Number", "SDF Contact Number", "SDF Type", "Status", "Reject Reason" });
		counter++;
		// data population
		populateDataForCompanySdfReport(data, counter);
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
					cell.setCellValue((Double) obj);;
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
			String fileName = "Comapany SDF Report.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private void populateDataForCompanySdfReport(Map<String, Object[]> data, Integer counter) throws Exception{
		int counterForPopulation = counter;
		List<CompanySdfReportBean> resultsList = populateCompanySdfReport();
		// populate data found into report
		for (CompanySdfReportBean entry : resultsList) {
			populateDataCompanySdfReport(data, entry, counterForPopulation);
			counterForPopulation++;
		}
		resultsList.clear();
		counter = counterForPopulation;
	}

	private void populateDataCompanySdfReport(Map<String, Object[]> data, CompanySdfReportBean entry, Integer counter) throws Exception{
		// "Entity ID", "Name Of Company", "Trading Name", "Region", "CLO Name and Surname", "SDF Name", "SDF Surname", "SDF Email", "SDF ID Number", "SDF Passport Number", "SDF Contact Number", "SDF Type", "Status", "Reject Reason" 
		data.put(counter.toString(), new Object[] { entry.getEntityId(), entry.getCompanyName(), entry.getTradingName(), entry.getRegion(), entry.getCloUserFullName(), entry.getSdfFirstName(), entry.getSdfLastName(), entry.getSdfEmail(), entry.getSdfRsaIdNumber(), entry.getSdfPassportNumber(), entry.getSdfContactNumber(), entry.getSdfType(), entry.getCompanyStatus(), entry.getRejectionReasons() });
	}

}
