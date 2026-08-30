package haj.com.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import haj.com.entity.lookup.*;
import haj.com.service.lookup.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jsoup.Jsoup;
import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.bean.ExtensionRequestReportBean;
import haj.com.bean.WorkplaceApprovalBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.WorkPlaceApprovalDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyUsers;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DateChangeMultipleSelection;
import haj.com.entity.Doc;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.OfoCodes;
import haj.com.entity.Province;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SDFCompany;
import haj.com.entity.Signoff;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.SmeQualifications;
import haj.com.entity.Tasks;
import haj.com.entity.TradeAppraisalsChecklist;
import haj.com.entity.Users;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.WorkPlaceApprovalMentors;
import haj.com.entity.WorkPlaceApprovalSites;
import haj.com.entity.WorkPlaceApprovalSkillsProgramme;
import haj.com.entity.WorkPlaceApprovalToolList;
import haj.com.entity.WorkPlaceApprovalTrades;
import haj.com.entity.WorkPlaceApprovalVisitDateLog;
import haj.com.entity.WorkplaceApprovalSkillsSet;
import haj.com.entity.WorkplaceApprovalUnitStandart;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CloRecommendationEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.TerminationTypeEnum;
import haj.com.entity.enums.WorkplaceApprovalTypeEnum;
import haj.com.entity.enums.WpaDocRequirements;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class WorkPlaceApprovalService extends AbstractService {
	/** The dao. */
	private WorkPlaceApprovalDAO dao = new WorkPlaceApprovalDAO();
	
	/** The Service Level */
	private CompanyService companyServiceWPA;
	private SitesService sitesServiceWPA;
	private ConfigDocService configDocService = new ConfigDocService();
	private AddressService addressService = new AddressService();
	
	private SignoffService signoffService = new SignoffService();
	private AppraisalChecklistService appraisalChecklistService= new AppraisalChecklistService();
	private AppraisalsService appraisalsService=new AppraisalsService();
	private RegionService regionService;
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	SDFCompanyService sdfCompanyService = new SDFCompanyService();
	HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	WorkPlaceApprovalToolListService workPlaceApprovalToolListService = new WorkPlaceApprovalToolListService();
	/**
	 * Get all WorkPlaceApproval
	 * 
	 * @author TechFinium
	 * @see WorkPlaceApproval
	 * @return a list of {@link haj.com.entity.WorkPlaceApproval}
	 * @throws Exception
	 *             the exception
	 */
	public List<WorkPlaceApproval> allWorkPlaceApproval() throws Exception {
		return dao.allWorkPlaceApproval();
	}

	/**
	 * Create or update WorkPlaceApproval.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkPlaceApproval
	 */
	public void create(WorkPlaceApproval entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}
	
	/**
	 * Create or update WorkPlaceApprovalSites.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkPlaceApprovalSites
	 */
	public void create(WorkPlaceApprovalSites entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}
	
	/**
	 * Create or update WorkPlaceApprovalTrades.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkPlaceApprovalTrades
	 */
	public void create(WorkPlaceApprovalTrades entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update WorkPlaceApproval.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkPlaceApproval
	 */
	public void update(WorkPlaceApproval entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete WorkPlaceApproval.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkPlaceApproval
	 */
	public void delete(WorkPlaceApproval entity) throws Exception {
		this.dao.delete(entity);
	}
	
	public void delete(WorkPlaceApprovalSites entity) throws Exception {
		this.dao.delete(entity);
	}
	
	/**
	 * All WorkPlaceApproval by Company Id
	 * 
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param company
	 * @return List<WorkPlaceApproval>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> allWorkPlaceApprovalByCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.company.id = :companyId";
		filters.put("companyId", company.getId());
		return (List<WorkPlaceApproval>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> allWorkPlaceApprovalByCompanyAndSkillProgran(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.company.id = :companyId";
		filters.put("companyId", company.getId());
		return populateSkillsProgram((List<WorkPlaceApproval>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	private List<WorkPlaceApproval> populateSkillsProgram(List<WorkPlaceApproval> wpas) throws Exception {
		for (WorkPlaceApproval workPlaceApproval : wpas) {
			if(workPlaceApproval.getApprovalEnum() == ApprovalEnum.Approved) {
				populateWorkPlaceApproval(workPlaceApproval);
			}
		}
		return wpas;
	}

	private void populateWorkPlaceApproval(WorkPlaceApproval workPlaceApproval) throws Exception {
		if(workPlaceApproval!=null && workPlaceApproval.getQualification()!=null) {
			SkillsProgramService skillsProgramService = new SkillsProgramService();
			List<SkillsProgram>list = new ArrayList<>();
			if(workPlaceApproval.getWorkplaceApprovalTypeEnum() != null && workPlaceApproval.getWorkplaceApprovalTypeEnum() == WorkplaceApprovalTypeEnum.SkillsProgram) {
				if(workPlaceApproval.getSkillsProgram() != null) {
					SkillsProgram skillsProgram = skillsProgramService.findByKey(workPlaceApproval.getSkillsProgram().getId());
					list.add(skillsProgram);
				}
			}else {
				list = skillsProgramService.findByQualification(workPlaceApproval.getQualification());
			}
			
			workPlaceApproval.setSkillsProgramlist(list);
		}		
	}

	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> sortAndFilterWhereWPAInfo(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters,Company company) throws Exception {
			String hql = "select o from WorkPlaceApproval o where o.company.id = :companyId";
			filters.put("companyId", company.getId());
		return (List<WorkPlaceApproval>) dao.sortAndFilterWhereWPAInfo(first, pageSize, sortField, sortOrder, filters, hql);
	}

	/**
	 * Count All WorkPlaceApproval by Company Id
	 * 
	 * @param filters
	 * @return int the count
	 * @throws Exception
	 */
	public int countAllWorkPlaceApprovalByCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.id = :companyId";
		return dao.countWhere(filters, hql);
	}
	
	public int countWhereWPAInfo(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.id = :companyId";
		return dao.countWhereWPAInfo(filters, hql);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.WorkPlaceApproval}
	 * @throws Exception
	 *             the exception
	 * @see WorkPlaceApproval
	 */
	public WorkPlaceApproval findByKey(long id) throws Exception {
		return populateSignOffs(prepareNewDocs(dao.findByKey(id), ConfigDocProcessEnum.WORKPLACE_APPROVAL, CompanyUserTypeEnum.Company, false));
	}

	public WorkPlaceApproval prepareNewDocs(WorkPlaceApproval siteVisit, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType, boolean canShowDocument) throws Exception {
		siteVisit.setDocs(DocService.instance().searchByTargetKeyAndClass(siteVisit.getClass().getName(), siteVisit.getId()));
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(siteVisit.getClass().getName(), siteVisit.getId(), configDocProcess, companyUserType);
		/*
		 * if (l != null && l.size() > 0) { l.forEach(a -> { siteVisit.getDocs().add(new
		 * Doc(a)); }); }
		 */
		
		if(canShowDocument) {
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					siteVisit.getDocs().add(new Doc(a));
				});
			}
		}else {
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					if(!a.getName().matches("Proof Of Agreement Between Entities")) {
						siteVisit.getDocs().add(new Doc(a));
					}						
				});
			}
		}
		return siteVisit;
	}

	public void prepareNewDocs(WorkPlaceApproval siteVisit) throws Exception {
		siteVisit.setDocs(new ArrayList<Doc>());
		List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.WORKPLACE_APPROVAL, CompanyUserTypeEnum.Company);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				siteVisit.getDocs().add(new Doc(a));
			});
		}
	}

	/**
	 * Find WorkPlaceApproval by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.WorkPlaceApproval}
	 * @throws Exception
	 *             the exception
	 * @see WorkPlaceApproval
	 */
	public List<WorkPlaceApproval> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load WorkPlaceApproval
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkPlaceApproval}
	 * @throws Exception
	 *             the exception
	 */
	public List<WorkPlaceApproval> allWorkPlaceApproval(int first, int pageSize) throws Exception {
		return dao.allWorkPlaceApproval(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of WorkPlaceApproval for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the WorkPlaceApproval
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(WorkPlaceApproval.class);
	}

	/**
	 * Lazy load WorkPlaceApproval with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            WorkPlaceApproval class
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
	 * @return a list of {@link haj.com.entity.WorkPlaceApproval} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> allWorkPlaceApproval(Class<WorkPlaceApproval> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<WorkPlaceApproval>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of WorkPlaceApproval for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            WorkPlaceApproval class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the WorkPlaceApproval entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<WorkPlaceApproval> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public WorkPlaceApproval findByDiscretionaryGrant(MandatoryGrant mandatoryGrant) throws Exception {
		return dao.findByDiscretionaryGrant(mandatoryGrant.getId());
	}

	public List<WorkPlaceApproval> findByCompanyandQualification(Company company, Qualification qualification) throws Exception {
		return dao.findByCompanyandQualification(company, qualification);
	}
	
	public WorkPlaceApproval findWorkPlaceApprovalByCompanyandQualification(Company company, Qualification qualification) throws Exception {
		return dao.findWorkPlaceApprovalByCompanyandQualification(company, qualification);
	}
	
	public WorkPlaceApproval findApprovedWorkPlaceApprovalByCompanyAndQualificationAndSites(Company company, Qualification qualification,  ApprovalEnum approvalEnum, Long sitesId) throws Exception {
		return dao.findApprovedWorkPlaceApprovalByCompanyAndQualificationAndSites(company, qualification,  approvalEnum, sitesId);
	}
	
	public WorkPlaceApproval findApprovedWorkPlaceApprovalByCompanyAndQualification(Company company, Qualification qualification,  ApprovalEnum approvalEnum) throws Exception {
		return dao.findApprovedWorkPlaceApprovalByCompanyAndQualification(company, qualification,  approvalEnum);
	}
	
	public List<WorkPlaceApproval> findApprovedByCompany(Company company) throws Exception {
		return dao.findApprovedByCompany(company);
	}
	
	public WorkPlaceApproval findApprovedFirstByCompany(Company company) throws Exception {
		List<WorkPlaceApproval> list = findApprovedByCompany(company);
		if(list!=null && list.size()>0) {
			return findApprovedByCompany(company).get(0);
		}else {
			return null;
		}
	}
	
	public WorkPlaceApproval findWorkPlaceApprovalByCompanyandOFOCode(Company company, OfoCodes ofoCodes) throws Exception {
		return dao.findWorkPlaceApprovalByCompanyandOFOCode(company, ofoCodes);
	}

	public long findByCompanyandQualificationCount(Company company, Qualification qualification) throws Exception {
		return dao.findByCompanyandQualificationCount(company, qualification);
	}

	public long findByCompanyandOfoCodeCount(Company company, OfoCodes ofoCodes) throws Exception {
		return dao.findByCompanyandOfoCodeCount(company, ofoCodes);
	}
	
	public long findByCompanySiteandOfoCodeCount(Company company, OfoCodes ofoCodes, Sites site) throws Exception {
		return dao.findByCompanySiteandOfoCodeCount(company, ofoCodes, site.getId());
	}
	
	public long findByCompanySiteandQualificationCount(Company company, Qualification qualification, Sites site) throws Exception {
		return dao.findByCompanySiteandQualificationCount(company, qualification, site.getId());
	}
	
	public long findByCompanyQualificationAndNotRejectedCount(Company company, Qualification qualification) throws Exception {
		return dao.findByCompanyQualificationAndNotRejectedCount(company, qualification);
	}
	
	public long findByCompanyOfoCodeAndNotRejectedCount(Company company, OfoCodes ofoCodes) throws Exception {
		return dao.findByCompanyOfoCodeAndNotRejectedCount(company, ofoCodes);
	}
	
	public long findByCompanySiteOfoCodeAndNotRejectedCount(Company company, OfoCodes ofoCodes, Long siteId) throws Exception {
		return dao.findByCompanySiteOfoCodeAndNotRejectedCount(company, ofoCodes, siteId);
	}
	
	public long findByCompanySiteQualificationAndNotRejectedCount(Company company, Qualification qualification, Long siteId) throws Exception {
		return dao.findByCompanySiteQualificationAndNotRejectedCount(company, qualification, siteId);
	}

	public long findByCompanyCount(Company company) throws Exception {
		return dao.findByCompanyCount(company);
	}

	public List<WorkPlaceApproval> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company);
	}
	
	public List<WorkPlaceApprovalSites> findSitesByApproval(WorkPlaceApproval workPlaceApproval, ApprovalEnum approvalEnum) throws Exception {
		return dao.findSitesByApproval(workPlaceApproval, approvalEnum);
	}
	
	public List<WorkPlaceApprovalSites> findSitesByApproval(WorkPlaceApproval workPlaceApproval) throws Exception {
		return dao.findSitesByApproval(workPlaceApproval);
	}
	
	public List<WorkPlaceApprovalSites> findSitesByApproval(WorkPlaceApproval workPlaceApproval, ConfigDocProcessEnum configEnum, CompanyUserTypeEnum companyUserType) throws Exception {
		return populateAdditionalInformation(dao.findSitesByApproval(workPlaceApproval), configEnum, companyUserType, workPlaceApproval);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalSites> avalaibleSiteSmeForSelection(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, WorkPlaceApproval workPlaceApproval) throws Exception {
		String hql = "select o from WorkPlaceApprovalSites o where o.workPlaceApproval.id = :workPlaceApprovalID";	
		filters.put("workPlaceApprovalID", workPlaceApproval.getId());
		return (List<WorkPlaceApprovalSites>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAvalaibleSiteSmeForSelection(Map<String, Object> filters, WorkPlaceApproval workPlaceApproval)
			throws Exception {
		String hql = "select count(o) from WorkPlaceApprovalSites o where o.workPlaceApproval.id = :workPlaceApprovalID ";
		return dao.countWhere(filters, hql);
	}
	
	public List<WorkPlaceApprovalSites> findSitesByApproval(WorkPlaceApproval workPlaceApproval, ApprovalEnum approvalEnum, ConfigDocProcessEnum configEnum, CompanyUserTypeEnum companyUserType) throws Exception {
		return populateAdditionalInformation(dao.findSitesByApproval(workPlaceApproval, approvalEnum), configEnum, companyUserType, workPlaceApproval);
	}
	
	public List<WorkPlaceApprovalSites> findSitesByWorkPlaceApprovalMentors(WorkPlaceApprovalMentors workPlaceApprovalMentors, ConfigDocProcessEnum configEnum, CompanyUserTypeEnum companyUserType) throws Exception {
		return populateAdditionalInformation(dao.findSitesByWorkPlaceApprovalMentors(workPlaceApprovalMentors), configEnum, companyUserType, findByKey(workPlaceApprovalMentors.getWorkPlaceApproval().getId()));
	}
	
	public List<WorkPlaceApprovalSites> findSitesByWorkPlaceApprovalMentors(WorkPlaceApprovalMentors workPlaceApprovalMentors) throws Exception {
		return dao.findSitesByWorkPlaceApprovalMentors(workPlaceApprovalMentors);
	}
	
	/**
	 * Locates WorkPlaceApprovalVistDateLog by workPlaceApproval id
	 * @param workPlaceApproval
	 * @return List<WorkPlaceApprovalVistDateLog>
	 * @throws Exception
	 */
	public List<WorkPlaceApprovalVisitDateLog> findVistDateLogByWorkplaceApproval(WorkPlaceApproval workPlaceApproval, ConfigDocProcessEnum configDocProcess) throws Exception {
		return populateAdditionalInformationVisitDateLogList(dao.findVisitDateLogByWorkplaceApproval(workPlaceApproval),configDocProcess);
	}
	
	/**
	 * Populates additional information List assigned to WorkPlaceApprovalVistDateLog
	 * @param findVistDateLogByWorkplaceApprovalList
	 * @param configDocProcess
	 * @see DateChangeReasons
	 * @see DateChangeMultipleSelection
	 * @see Users
	 * @see Method: populateAdditionalInformationVistDateLog
	 * @return List<WorkPlaceApprovalVistDateLog>
	 * @throws Exception
	 */
	private List<WorkPlaceApprovalVisitDateLog> populateAdditionalInformationVisitDateLogList(List<WorkPlaceApprovalVisitDateLog> findVisitDateLogByWorkplaceApprovalList, ConfigDocProcessEnum configDocProcess) throws Exception{
		for (WorkPlaceApprovalVisitDateLog workPlaceApprovalVisitDateLog : findVisitDateLogByWorkplaceApprovalList) {
			populateAdditionalInformationVisitDateLog(configDocProcess, workPlaceApprovalVisitDateLog);
		}
		return findVisitDateLogByWorkplaceApprovalList;
	}

	/**
	 * Populates additional information assigned to WorkPlaceApprovalVistDateLog
	 * @param findVistDateLogByWorkplaceApproval
	 * @param configDocProcess
	 * @see DateChangeReasons
	 * @see DateChangeMultipleSelection
	 * @see Users
	 * @see Method: populateAdditionalInformationVistDateLog
	 * @return List<WorkPlaceApprovalVistDateLog>
	 * @throws Exception
	 */
	public WorkPlaceApprovalVisitDateLog populateAdditionalInformationVisitDateLog(ConfigDocProcessEnum configDocProcess, WorkPlaceApprovalVisitDateLog workPlaceApprovalVistDateLog) throws Exception {
		// populates date changes reasons against WorkPlaceApprovalVistDateLog
		if (workPlaceApprovalVistDateLog.getId() != null && configDocProcess != null) {
			workPlaceApprovalVistDateLog.setDateChangeReasonsList(DateChangeReasonsService.instance().locateReasonsSelectedByTargetKeyClassAndProcess(workPlaceApprovalVistDateLog.getId(), workPlaceApprovalVistDateLog.getClass().getName(), configDocProcess));
		} else {
			workPlaceApprovalVistDateLog.setDateChangeReasonsList(new ArrayList<>());
		}
		/*
		 *  sets the user assigned to the log
		 *  Would have done a left join however work place approval at
		 *  Maximum with left joins  
		 */
		if (workPlaceApprovalVistDateLog.getUser() != null) {
			UsersService usersService = new UsersService();
			workPlaceApprovalVistDateLog.setUser(usersService.findByKey(workPlaceApprovalVistDateLog.getUser().getId()));
		}
		return workPlaceApprovalVistDateLog;
	}
	
	/**
	 * Creates new WorkPlaceApprovalSites against WorkPlaceApproval
	 * @param workPlaceApproval
	 * @param sitesSme
	 * @throws Exception
	 */
	public void createWorkPlaceApprovalSitesWithSme(WorkPlaceApproval workPlaceApproval, SitesSme sitesSme) throws Exception{
		WorkPlaceApprovalSites workPlaceApprovalSites = null;
		if (workPlaceApproval.getSites() != null) {
			workPlaceApprovalSites= new WorkPlaceApprovalSites(workPlaceApproval, workPlaceApproval.getSites());
			workPlaceApprovalSites.setUseCompanyAddress(false);
		} else {
			workPlaceApprovalSites= new WorkPlaceApprovalSites(workPlaceApproval);
			workPlaceApprovalSites.setUseCompanyAddress(true);
			workPlaceApprovalSites.setResidentialAddress(workPlaceApproval.getCompany().getResidentialAddress());
		}
		workPlaceApprovalSites.setApprovalEnum(ApprovalEnum.NA);
		workPlaceApprovalSites.setFirstName(sitesSme.getFirstName());
		workPlaceApprovalSites.setLastName(sitesSme.getLastName());
		if (sitesSme.getIdentityNumber() != null && !sitesSme.getIdentityNumber().isEmpty()) {
			workPlaceApprovalSites.setIdentityNumber(sitesSme.getIdentityNumber());
		}
		if (sitesSme.getPassportNumber() != null && !sitesSme.getPassportNumber().isEmpty()) {
			workPlaceApprovalSites.setPassportNumber(sitesSme.getPassportNumber());
		}
		workPlaceApprovalSites.setSitesSme(sitesSme);
		create(workPlaceApprovalSites);
	}
	
	public void createWorkPlaceApprovalSitesWithSme(WorkPlaceApproval workPlaceApproval, SitesSme sitesSme, WorkPlaceApprovalMentors workPlaceApprovalMentors) throws Exception{
		WorkPlaceApprovalSites workPlaceApprovalSites = null;
		if (workPlaceApproval.getSites() != null) {
			workPlaceApprovalSites= new WorkPlaceApprovalSites(workPlaceApproval, workPlaceApproval.getSites());
			workPlaceApprovalSites.setUseCompanyAddress(false);
		} else {
			workPlaceApprovalSites= new WorkPlaceApprovalSites(workPlaceApproval);
			workPlaceApprovalSites.setUseCompanyAddress(true);
			workPlaceApprovalSites.setResidentialAddress(workPlaceApproval.getCompany().getResidentialAddress());
		}
		workPlaceApprovalSites.setApprovalEnum(ApprovalEnum.NA);
		workPlaceApprovalSites.setFirstName(sitesSme.getFirstName());
		workPlaceApprovalSites.setLastName(sitesSme.getLastName());
		if (sitesSme.getIdentityNumber() != null && !sitesSme.getIdentityNumber().isEmpty()) {
			workPlaceApprovalSites.setIdentityNumber(sitesSme.getIdentityNumber());
		}
		if (sitesSme.getPassportNumber() != null && !sitesSme.getPassportNumber().isEmpty()) {
			workPlaceApprovalSites.setPassportNumber(sitesSme.getPassportNumber());
		}
		workPlaceApprovalSites.setWorkPlaceApprovalMentor(workPlaceApprovalMentors);
		workPlaceApprovalSites.setSitesSme(sitesSme);
		create(workPlaceApprovalSites);
	}
	
	/**
	 * Creates a new instance of WorkPlaceApprovalVisitDateLog and Date changes reasons against WorkPlaceApprovalVisitDateLog
	 * 
	 * @param workplaceApproval
	 * @param sessionUser - can be null
	 * @param additionalInfo - can be null
	 * @param systemGenerated - if it was user or system generated
	 * @param changeReasonsList - list of change reasons - can be null
	 * @param dateEntry - the new date for the visit
	 * @param config - the process for the reason can be null
	 * 
	 * @see WorkPlaceApprovalVisitDateLog
	 * @see DateChangeReasons
	 * @see DateChangeMultipleSelection
	 * @throws Exception
	 */
	public void addNewWorkPlaceApprovalVisitDateLog(WorkPlaceApproval workplaceApproval, Users sessionUser, String additionalInfo, boolean systemGenerated, List<DateChangeReasons> changeReasonsList, Date dateEntry, ConfigDocProcessEnum config) throws Exception{
		new ArrayList<IDataEntity>();
		WorkPlaceApprovalVisitDateLog log = new WorkPlaceApprovalVisitDateLog(dateEntry, workplaceApproval, systemGenerated, sessionUser, additionalInfo);
		dao.create(log);
		if (changeReasonsList != null && changeReasonsList.size() != 0 && config != null) {
			for (DateChangeReasons reason : changeReasonsList) {
				DateChangeMultipleSelection dateChangeMultipleSelection = new DateChangeMultipleSelection(log.getId(), log.getClass().getName(), reason, sessionUser, config);
				dao.create(dateChangeMultipleSelection);
			}
		}
//		if (createList.size() != 0) {
//			dao.createBatch(createList);
//		}
		//sendReviewDateEmail(workplaceApproval, sessionUser);
		sendUpdateReviewDateEmail(workplaceApproval, sessionUser);
	}

	/**
	 * 
	 * @param workPlaceApproval
	 * @param qualification
	 * @param rsaId
	 * @param workPlaceApprovalSites
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countSiteByApprovalQualificationRsaIdAndId(WorkPlaceApproval workPlaceApproval, Qualification qualification, String rsaId, WorkPlaceApprovalSites workPlaceApprovalSites) throws Exception {
		return dao.countSiteByApprovalQualificationRsaIdAndId(workPlaceApproval, qualification.getId(), rsaId, workPlaceApprovalSites.getId());
	}
	
	public List<WorkPlaceApprovalSites> populateAdditionalInformation(List<WorkPlaceApprovalSites> workPlaceApprovalSites,  ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType, WorkPlaceApproval workPlaceApproval) throws Exception{
		for (WorkPlaceApprovalSites workPlaceApprovalSite : workPlaceApprovalSites) {
			if (configDocProcess != null && companyUserType != null) {
				prepareNewDoc(workPlaceApprovalSite, configDocProcess, companyUserType);
			}
			if ((workPlaceApprovalSite != null && workPlaceApprovalSite.getSitesSme() != null) && (workPlaceApproval != null && workPlaceApproval.getCompany() != null)) {
				popoulateSiteSmeInformationAgainstworkPlaceApprovalSite(workPlaceApprovalSite, workPlaceApproval);
			}
		}
		return workPlaceApprovalSites;
	}
	
	/**
	 * Populates SiteSme Information against WorkPlaceApprovalSites
	 * @param workPlaceApprovalSite
	 * @param workPlaceApproval
	 */
	public void popoulateSiteSmeInformationAgainstworkPlaceApprovalSite(WorkPlaceApprovalSites workPlaceApprovalSite, WorkPlaceApproval workPlaceApproval) throws Exception {
		workPlaceApprovalSite.setSitesSme(SitesSmeService.instance().findByKeyPopulateInformation(workPlaceApprovalSite.getSitesSme().getId(), null, null, workPlaceApproval.getCompany()));
	}

	public WorkPlaceApprovalSites prepareNewDoc(WorkPlaceApprovalSites workPlaceApprovalSites, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		if (workPlaceApprovalSites.getId() != null) {
			// if created WorkPlaceApprovalSites
			workPlaceApprovalSites.setDocs(DocService.instance().searchByTargetKeyAndClass(workPlaceApprovalSites.getClass().getName(), workPlaceApprovalSites.getId()));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(workPlaceApprovalSites.getClass().getName(), workPlaceApprovalSites.getId(), configDocProcess, companyUserType);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					workPlaceApprovalSites.getDocs().add(new Doc(a));
				});
			}
		} else {
			// if new instance of WorkPlaceApprovalSites
			List<ConfigDoc> l = configDocService.findByProcess(configDocProcess, companyUserType);
			workPlaceApprovalSites.setDocs(new ArrayList<>());
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					workPlaceApprovalSites.getDocs().add(new Doc(a));
				});
			}
		}
		return workPlaceApprovalSites;
	}
	
	public WorkPlaceApprovalSites prepareNewDoc(WorkPlaceApprovalSites workPlaceApprovalSites) throws Exception {
		if (workPlaceApprovalSites.getId() != null) {
			workPlaceApprovalSites.setDocs(DocService.instance().searchByTargetKeyAndClass(workPlaceApprovalSites.getClass().getName(), workPlaceApprovalSites.getId()));			
		} 
		return workPlaceApprovalSites;
	}
	/**
	 * Creates a new instance of WorkPlaceApprovalSites and creates docs assigned 
	 * @param entity
	 * @param sessionUser
	 * @throws Exception
	 */
	public void createWorkPlaceApprovalSites(WorkPlaceApprovalSites entity, Users sessionUser) throws Exception{
		create(entity);
		// new documents upload against WorkPlaceApprovalSites
		for (Doc approvalSitesDoc : entity.getDocs()) {
			if (approvalSitesDoc.getId() == null && approvalSitesDoc.getData() != null) {
				approvalSitesDoc.setTargetKey(entity.getId());
				approvalSitesDoc.setTargetClass(WorkPlaceApprovalSites.class.getName());
				DocService.instance().save(approvalSitesDoc, approvalSitesDoc.getData(), approvalSitesDoc.getOriginalFname(), sessionUser);
			}
		}
	}

	public List<WorkPlaceApprovalTrades> findTradesByApproval(WorkPlaceApproval workPlaceApproval) throws Exception {
		return dao.findTradesByApproval(workPlaceApproval);
	}
	
	
	
	/**
	 * 
	 * @param workPlaceApproval
	 * @param ofoCode
	 * @param workPlaceApprovalTrades
	 * @return List<WorkPlaceApprovalTrades>
	 * @throws Exception
	 */
	public Integer countTradesByApprovalOfoAndId(WorkPlaceApproval workPlaceApproval, OfoCodes ofoCode, WorkPlaceApprovalTrades workPlaceApprovalTrades) throws Exception {
		return dao.countTradesByApprovalOfoAndId(workPlaceApproval, ofoCode.getId(), workPlaceApprovalTrades.getId());
	}
	
	
	public void createWorkPlaceApprovalTrade(WorkPlaceApprovalTrades entity, Users sessionUser) throws Exception{
		create(entity);
	}
	
	/**
	 * Locates sign offs for WorkPlaceApproval
	 * @param workPlaceApproval
	 * @throws Exception
	 */
	public WorkPlaceApproval populateSignOffs(WorkPlaceApproval workPlaceApproval) throws Exception {
		if (workPlaceApproval.getId() != null) workPlaceApproval.setSignOffs(signoffService.findByTargetKeyAndClass(workPlaceApproval.getId(), WorkPlaceApproval.class.getName()));
		else workPlaceApproval.setSignOffs(new ArrayList<>());
		return workPlaceApproval;
	}

	public void checkWorkplaceApproval(Company company, Qualification qualification, OfoCodes ofoCodes, Sites site, Users user) {
		try {

			
				WorkPlaceApproval wpa = null;
				if (site == null) {
					if (checkForExistingRecords(company, qualification, ofoCodes, null) == 0) {
						if (ofoCodes != null && ofoCodes.getTrade()) wpa = new WorkPlaceApproval(company, ofoCodes);
						else if (qualification != null && qualification.getWorkplaceApprovalRequired()) wpa = new WorkPlaceApproval(company, qualification);
					}
		//				if (ofoCodes.getTrade() && findByCompanyandOfoCodeCount(company, ofoCodes) == 0) wpa = new WorkPlaceApproval(company, ofoCodes);
		//				else if (qualification != null && findByCompanyandQualificationCount(company, qualification) == 0) wpa = new WorkPlaceApproval(company, qualification);
				} else {
					if (checkForExistingRecords(company, qualification, ofoCodes, site) == 0) {
						if (ofoCodes != null && ofoCodes.getTrade()) wpa = new WorkPlaceApproval(company, ofoCodes, site);
						else if (qualification != null && qualification.getWorkplaceApprovalRequired()) wpa = new WorkPlaceApproval(company, qualification, site);
					}
		//				if (ofoCodes.getTrade() && findByCompanySiteandOfoCodeCount(company, ofoCodes, site) == 0) wpa = new WorkPlaceApproval(company, ofoCodes, site);
		//				else if (qualification != null && findByCompanySiteandQualificationCount(company, qualification, site) == 0) wpa = new WorkPlaceApproval(company, qualification, site);
				}
				
				if (wpa != null) {
					boolean byQualification = true;
					if(qualification != null && qualification.getQualificationtypeid() != null && qualification.getQualificationtypeid().matches(HAJConstants.TRADE_QUALIFICATION_CODE)) {
						byQualification = false;
					}
					createWorkplaceApproval(company, qualification, ofoCodes, site, user, byQualification);
					// old method
		//				wpa.setApprovalEnum(ApprovalEnum.PendingApproval);
		//				create(wpa);
		//				String desc = "Workplace Approval for review";
		//				TasksService.instance().findFirstInProcessAndCreateTask(desc, null, wpa.getId(), wpa.getClass().getName(), true, ConfigDocProcessEnum.WORKPLACE_APPROVAL, null, null);
		//				
		//				/*
		//				TasksService.instance().createTaskUser(sdfCompanies, SiteVisitReport.class.getName(), siteVisitReport.getId(), "Site Visit Report was submitted and requires your sign off.", user, true, true, null, ConfigDocProcessEnum.SITE_VISIT_REPORT, false);
		//				if (task != null) {
		//					TasksService.instance().completeTask(task);
		//				}
		//				*/
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Boolean workplaceApprovedCheck(Company company, Qualification qualification, OfoCodes ofoCodes, Sites site, Users user) throws Exception{
		WorkPlaceApproval wpa = null;
		if (site == null) {
			if (checkForExistingRecords(company, qualification, ofoCodes, null) == 0) {
				if (ofoCodes != null && ofoCodes.getTrade()) {
					wpa = new WorkPlaceApproval(company, ofoCodes);
				}else if (qualification != null && qualification.getWorkplaceApprovalRequired()) {
					wpa = new WorkPlaceApproval(company, qualification);
				}
			}
		} else {
			if (checkForExistingRecords(company, qualification, ofoCodes, site) == 0) {
				if (ofoCodes != null && ofoCodes.getTrade()) {
					wpa = new WorkPlaceApproval(company, ofoCodes, site);
				}else if (qualification != null && qualification.getWorkplaceApprovalRequired()) {
					wpa = new WorkPlaceApproval(company, qualification, site);
				}
			}
		}
		if (wpa != null) {
			if (wpa.getApprovalEnum() == ApprovalEnum.Approved) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	public Boolean checkIfWorkplaceApproved(Company company, Qualification qualification, OfoCodes ofoCodes, Sites site, Users user) throws Exception{
		WorkPlaceApproval wpa = null;
		if (site == null) {
			if (checkForExistingRecords(company, qualification, ofoCodes, null) == 0) {
				if (ofoCodes != null && ofoCodes.getTrade()) {
					wpa = findWorkPlaceApprovalByCompanyandOFOCode(company, ofoCodes);
				}else if (qualification != null && qualification.getWorkplaceApprovalRequired()) {
					wpa = findWorkPlaceApprovalByCompanyandQualification(company, qualification);
				}
			}
		} else {
			if (checkForExistingRecords(company, qualification, ofoCodes, site) == 0) {
				if (ofoCodes != null && ofoCodes.getTrade()) {
					wpa = findWpaByCompanySiteOfoCode(company,ofoCodes, site);
				}else if (qualification != null && qualification.getWorkplaceApprovalRequired()) {
					wpa = findWpaBycompanyQualificationSite(company, qualification, site);
				}
			}
		}
		if (wpa != null) {
			if (wpa.getApprovalEnum() == ApprovalEnum.Approved) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	private WorkPlaceApproval findWpaBycompanyQualificationSite(Company company, Qualification qualification,
			Sites site) {
		return dao.findWpaBycompanyQualificationSite(company, qualification, site);
	}

	private WorkPlaceApproval findWpaByCompanySiteOfoCode(Company company, OfoCodes ofoCodes, Sites site) {
		// TODO Auto-generated method stub
		return dao.findWpaByCompanySiteOfoCode(company, ofoCodes, site);
	}

	/**
	 * Validation check to see if workplace approval already created
	 * @param company
	 * @param qualification
	 * @param ofoCodes
	 * @param site
	 * @return entriesFound
	 * @throws Exception
	 */
	public Integer checkForExistingRecords(Company company, Qualification qualification, OfoCodes ofoCodes, Sites site) throws Exception{
		Long entriesFound = null;
		if (qualification != null) {
			if (site != null) {
				if(company!= null && company.getId()!=null && qualification!=null && qualification.getId()!=null) {
					entriesFound = findByCompanySiteQualificationAndNotRejectedCount(company, qualification, site.getId());
				}
			} else {
				if(company!= null && company.getId()!=null && qualification!=null && qualification.getId()!=null) {
					entriesFound = findByCompanyQualificationAndNotRejectedCount(company, qualification);
				}
			}
		} else {
			if (site != null) {
				if(company!= null && company.getId()!=null && ofoCodes!=null && ofoCodes.getId()!=null) {
					entriesFound = findByCompanySiteOfoCodeAndNotRejectedCount(company, ofoCodes, site.getId());
				}
			} else {
				if(company!= null && company.getId()!=null && ofoCodes!=null && ofoCodes.getId()!=null) {
					entriesFound =  findByCompanyOfoCodeAndNotRejectedCount(company, ofoCodes);
				}				
			}
		}
		if(entriesFound !=null) {
			return entriesFound.intValue();
		}else {
			return 0;
		}
	}
	
	public Integer checkForExistingRecords(Company company, Qualification qualification, CompanyLearners cl) throws Exception{
		Sites site = null;
		if(cl.getSite() != null){
			site= cl.getSite();
		}
		Long entriesFound = null;
		if (qualification != null) {
			if (site != null) {
				if(company!= null && company.getId()!=null && qualification!=null && qualification.getId()!=null) {
					entriesFound = findByCompanySiteQualificationAndNotRejectedCount(company, qualification, site.getId());
				}
			} else {
				if(company!= null && company.getId()!=null && qualification!=null && qualification.getId()!=null) {
					entriesFound = findByCompanyQualificationAndNotRejectedCount(company, qualification);
				}
			}
		} 
		if(entriesFound !=null) {
			return entriesFound.intValue();
		}else {
			return 0;
		}
	}
	
	/**
	 * The create of a work place approval 
	 * @param company
	 * @param qualification
	 * @param ofoCodes
	 * @param site
	 * @throws Exception
	 */
	public void createWorkplaceApproval(Company company, Qualification qualification, OfoCodes ofoCodes, Sites site, Users user, boolean byQualification) throws Exception {

		// A workplace approval application for Trade: Abattoir Veterinarian has been
		// submitted by Company Name (Entity ID). Please review the application.
		String taskDescription = "A workplace approval application for ";
		if (qualification != null) {
			taskDescription += "Qualification: " + qualification.getDescription();
		}
		if (qualification == null && ofoCodes != null) {
			taskDescription += "Trade: " + ofoCodes.getDescription();
		}
		taskDescription += " has been submitted by " + company.getCompanyName() + " (" + company.getLevyNumber() + "). Please go to Outstanding Tasks on the Dashboard to complete the application.";

		WorkPlaceApproval workPlaceApproval = new WorkPlaceApproval();
		workPlaceApproval.setCompany(company);
		workPlaceApproval.setApprovalEnum(ApprovalEnum.PendingApproval);
		workPlaceApproval.setWithClientCompany(true);
		workPlaceApproval.setAmendmentsRequired(false);
		if (qualification != null) {
			workPlaceApproval.setQualification(qualification);
			// taskDescription += " Qualification: " + qualification.getDescription()+ ".";
		}
		if (qualification == null && ofoCodes != null) {
			workPlaceApproval.setOfoCodes(ofoCodes);
			// taskDescription += " Trade: " + ofoCodes.getDescription() + ".";
		}
		if (site != null) {
			workPlaceApproval.setSites(site);
		}

		if (!byQualification) {
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			dataEntities.add(workPlaceApproval);
			Appraisals appraisal = appraisalsService.findByQualification(qualification);
			if (appraisal != null) {
				List<AppraisalChecklist> appraisalChecklist = appraisalChecklistService.findAppraisalCategoryCodeByAppraisal(appraisal);
				for (AppraisalChecklist apraisalChecklist : appraisalChecklist) {
					dataEntities.add(new TradeAppraisalsChecklist(workPlaceApproval, apraisalChecklist));
				}
				this.dao.createBatch(dataEntities);
			} else {
				create(workPlaceApproval);
			}
		} else {
			create(workPlaceApproval);
		}

		if (workPlaceApproval.getQualification() != null && workPlaceApproval.getQualification().getQualificationtypeid() != null && workPlaceApproval.getQualification().getQualificationtypeid().equalsIgnoreCase(HAJConstants.TRADE_QUALIFICATION_CODE)) {
			createWorkplaceApprovalToolList(workPlaceApproval);
		}

		createWorkPlaceApprovalSkills(workPlaceApproval);
		TasksService.instance().createTaskUser(locateClintUsersForTasks(company), WorkPlaceApproval.class.getName(), workPlaceApproval.getId(), taskDescription, user, true, true, null, ConfigDocProcessEnum.WORKPLACE_APPROVAL, false);

	}
	
	public void createLegacyWorkplaceApproval(WorkPlaceApproval workPlaceApproval, Company company, Qualification qualification, OfoCodes ofoCodes, Sites site, Users user, boolean byQualification, LegacyEmployerWA2Workplace legacyEmployerWA2Workplace,
			LegacyEmployerWA2Learnership legacyEmployerWA2Learnership, LegacyEmployerWA2Qualification legacyEmployerWA2Qualification, LegacyEmployerWA2SkillsProgramme legacyEmployerWA2SkillsProgramme, LegacyEmployerWA2Trade legacyEmployerWA2Trade,LegacyEmployerWA2UnitStandard legacyEmployerWA2UnitStandard) throws Exception {

		// A workplace approval application for Trade: Abattoir Veterinarian has been
		// submitted by Company Name (Entity ID). Please review the application.
		String taskDescription = "A workplace approval application for ";
		if (qualification != null) {
			taskDescription += "Qualification: " + qualification.getDescription();
		}
		if (qualification == null && ofoCodes != null) {
			taskDescription += "Trade: " + ofoCodes.getDescription();
		}
		taskDescription += " has been submitted by " + company.getCompanyName() + " (" + company.getLevyNumber() + "). Please go to Outstanding Tasks on the Dashboard to complete the application.";

		workPlaceApproval.setLegacyEmployerWA2Workplace(legacyEmployerWA2Workplace);
		workPlaceApproval.setCompany(company);
		workPlaceApproval.setApprovalEnum(ApprovalEnum.PendingApproval);
		workPlaceApproval.setWithClientCompany(true);
		workPlaceApproval.setAmendmentsRequired(false);
		if (qualification != null) {
			workPlaceApproval.setQualification(qualification);
			// taskDescription += " Qualification: " + qualification.getDescription()+ ".";
		}
		if (qualification == null && ofoCodes != null) {
			workPlaceApproval.setOfoCodes(ofoCodes);
			// taskDescription += " Trade: " + ofoCodes.getDescription() + ".";
		}
		if (site != null) {
			workPlaceApproval.setSites(site);
		}

		if (!byQualification) {
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			dataEntities.add(workPlaceApproval);
			Appraisals appraisal = appraisalsService.findByQualification(qualification);
			if (appraisal != null) {
				List<AppraisalChecklist> appraisalChecklist = appraisalChecklistService.findAppraisalCategoryCodeByAppraisal(appraisal);
				for (AppraisalChecklist apraisalChecklist : appraisalChecklist) {
					dataEntities.add(new TradeAppraisalsChecklist(workPlaceApproval, apraisalChecklist));
				}
				this.dao.createBatch(dataEntities);
			} else {
				create(workPlaceApproval);
			}
		} else {
			create(workPlaceApproval);
		}
		
		if(legacyEmployerWA2Qualification != null) {
			legacyEmployerWA2Qualification.setSubmitted(true);
			LegacyEmployerWA2QualificationService legacyEmployerWA2QualificationService = new LegacyEmployerWA2QualificationService();
			legacyEmployerWA2QualificationService.update(legacyEmployerWA2Qualification);
			legacyEmployerWA2QualificationService = null;
			
			workPlaceApproval.setTargetClass(legacyEmployerWA2Qualification.getClass().getName());
			workPlaceApproval.setTargetKey(legacyEmployerWA2Qualification.getId());
			dao.update(workPlaceApproval);
		}else if(legacyEmployerWA2Learnership != null) {
			legacyEmployerWA2Learnership.setSubmitted(true);
			LegacyEmployerWA2LearnershipService legacyEmployerWA2LearnershipService = new LegacyEmployerWA2LearnershipService();
			legacyEmployerWA2LearnershipService.update(legacyEmployerWA2Learnership);
			legacyEmployerWA2LearnershipService=null;
			
			workPlaceApproval.setTargetClass(legacyEmployerWA2Learnership.getClass().getName());
			workPlaceApproval.setTargetKey(legacyEmployerWA2Learnership.getId());
			dao.update(workPlaceApproval);
		}else if(legacyEmployerWA2SkillsProgramme != null) {
			legacyEmployerWA2SkillsProgramme.setSubmitted(true);
			LegacyEmployerWA2SkillsProgrammeService legacyEmployerWA2SkillsProgrammeService = new LegacyEmployerWA2SkillsProgrammeService();
			legacyEmployerWA2SkillsProgrammeService.update(legacyEmployerWA2SkillsProgramme);
			legacyEmployerWA2SkillsProgrammeService=null;
		
			workPlaceApproval.setTargetClass(legacyEmployerWA2SkillsProgramme.getClass().getName());
			workPlaceApproval.setTargetKey(legacyEmployerWA2SkillsProgramme.getId());
			dao.update(workPlaceApproval);
		}else if(legacyEmployerWA2Trade != null) {
			legacyEmployerWA2Trade.setSubmitted(true);
			LegacyEmployerWA2TradeService legacyEmployerWA2TradeService = new LegacyEmployerWA2TradeService();
			legacyEmployerWA2TradeService.update(legacyEmployerWA2Trade);
			legacyEmployerWA2TradeService= null;
			
			workPlaceApproval.setTargetClass(legacyEmployerWA2Trade.getClass().getName());
			workPlaceApproval.setTargetKey(legacyEmployerWA2Trade.getId());
			dao.update(workPlaceApproval);
		}else if(legacyEmployerWA2UnitStandard != null) {
			legacyEmployerWA2UnitStandard.setSubmitted(true);
			LegacyEmployerWA2UnitStandardService legacyEmployerWA2UnitStandardService = new LegacyEmployerWA2UnitStandardService();
			legacyEmployerWA2UnitStandardService.update(legacyEmployerWA2UnitStandard);
			legacyEmployerWA2UnitStandardService=null;
			
			workPlaceApproval.setTargetClass(legacyEmployerWA2UnitStandard.getClass().getName());
			workPlaceApproval.setTargetKey(legacyEmployerWA2UnitStandard.getId());
			dao.update(workPlaceApproval);
		}
		
		List<Users>list = new ArrayList<>();
		list.add(user);		
		//createWorkPlaceApprovalSkills(workPlaceApproval);
		TasksService.instance().findFirstInProcessAndCreateTask(taskDescription, user, workPlaceApproval.getId(), workPlaceApproval.getClass().getName(), true, ConfigDocProcessEnum.LEGACY_WORKPLACE_APPROVAL, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, taskDescription), list);
		//TasksService.instance().createTaskUser(locateClintUsersForTasks(company), WorkPlaceApproval.class.getName(), workPlaceApproval.getId(), taskDescription, user, true, true, null, ConfigDocProcessEnum.LEGACY_WORKPLACE_APPROVAL, false);
	}
	
	private void createWorkplaceApprovalToolList(WorkPlaceApproval workPlaceApproval) throws Exception {
		QualificationToolListService qualificationToolListService = new QualificationToolListService();
		ToolListService toolListService = new ToolListService();
		QualificationToolList qualificationToolList = qualificationToolListService.findByQualification(workPlaceApproval.getQualification());
		if(qualificationToolList!=null && qualificationToolList.getId()!=null) {
			List<ToolListTools>list = toolListService.allToolListTools(qualificationToolList.getToolList());
			if(list!=null && list.size()>0) {
				List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
				for(ToolListTools toolListTools:list) {
					WorkPlaceApprovalToolList workPlaceApprovalToolList = new WorkPlaceApprovalToolList(qualificationToolList, workPlaceApproval, toolListTools.getToolList(), toolListTools);
					dataEntities.add(workPlaceApprovalToolList);
				}
				this.dao.createBatch(dataEntities);
			}
		}
	}

	private void createWorkPlaceApprovalSkills(WorkPlaceApproval workPlaceApproval) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		SkillsProgramService skillsProgramService = new SkillsProgramService();
		SkillsSetService skillsSetService = new SkillsSetService();
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		
		if(workPlaceApproval != null && workPlaceApproval.getQualification() != null) {
			
			List<SkillsSet>skillsSetList = skillsSetService.findByQualification(workPlaceApproval.getQualification());
			List<SkillsProgram>skillsProgramList = skillsProgramService.findByQualification(workPlaceApproval.getQualification());
			List<UnitStandards>unitStandardsList = unitStandardsService.findByQualification(workPlaceApproval.getQualification());
			
			if(skillsSetList!=null && skillsSetList.size() >0) {
				List<WorkplaceApprovalSkillsSet>workplaceApprovalSkillsSetList = new ArrayList<>();
				for(SkillsSet skillsSet:skillsSetList) {
					WorkplaceApprovalSkillsSet workplaceApprovalSkillsSet = new WorkplaceApprovalSkillsSet();
					workplaceApprovalSkillsSet.setSkillsSet(skillsSet);
					workplaceApprovalSkillsSet.setWorkPlaceApproval(workPlaceApproval);
					workplaceApprovalSkillsSetList.add(workplaceApprovalSkillsSet);
				}
				dataEntities.addAll(workplaceApprovalSkillsSetList);
			}
			
			if(skillsProgramList!=null && skillsProgramList.size() >0) {
				List<WorkPlaceApprovalSkillsProgramme>workplaceApprovalSkillsPrograList = new ArrayList<>();
				for(SkillsProgram skillsProgram:skillsProgramList) {
					WorkPlaceApprovalSkillsProgramme workPlaceApprovalSkillsProgramme = new WorkPlaceApprovalSkillsProgramme();
					workPlaceApprovalSkillsProgramme.setSkillsProgram(skillsProgram);
					workPlaceApprovalSkillsProgramme.setWorkPlaceApproval(workPlaceApproval);
					workplaceApprovalSkillsPrograList.add(workPlaceApprovalSkillsProgramme);
				}
				dataEntities.addAll(workplaceApprovalSkillsPrograList);
			}
			
			if(unitStandardsList!=null && unitStandardsList.size() >0) {
				List<WorkplaceApprovalUnitStandart>workPlaceApprovalUnitStandartList = new ArrayList<>();
				for(UnitStandards UnitStandards: unitStandardsList) {
					WorkplaceApprovalUnitStandart workplaceApprovalUnitStandart = new WorkplaceApprovalUnitStandart();
					workplaceApprovalUnitStandart.setUnitStandards(UnitStandards);
					workplaceApprovalUnitStandart.setWorkPlaceApproval(workPlaceApproval);
					workPlaceApprovalUnitStandartList.add(workplaceApprovalUnitStandart);
				}
				
				dataEntities.addAll(workPlaceApprovalUnitStandartList);
			}
			
			this.dao.createBatch(dataEntities);
		}
	}
	
	public void updateWorkPlaceApprovalSkillsList(List<WorkplaceApprovalSkillsSet>workplaceApprovalSkillsSetList, List<WorkPlaceApprovalSkillsProgramme>workplaceApprovalSkillsPrograList, List<WorkplaceApprovalUnitStandart>workPlaceApprovalUnitStandartList, List<WorkPlaceApprovalToolList>workPlaceApprovalToolList) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		if(workplaceApprovalSkillsSetList != null && workplaceApprovalSkillsSetList.size() >0) {
			dataEntities.addAll(workplaceApprovalSkillsSetList);
		}
		if(workplaceApprovalSkillsPrograList != null && workplaceApprovalSkillsPrograList.size() >0) {
			dataEntities.addAll(workplaceApprovalSkillsPrograList);
		}
		if(workPlaceApprovalUnitStandartList != null && workPlaceApprovalUnitStandartList.size() >0) {
			dataEntities.addAll(workPlaceApprovalUnitStandartList);
		}
		if(workPlaceApprovalToolList != null && workPlaceApprovalToolList.size() >0) {
			dataEntities.addAll(workPlaceApprovalToolList);
		}
		this.dao.updateBatch(dataEntities);
	}

	public String validateChecks(WorkPlaceApproval workplaceapproval, List<WorkPlaceApprovalSites> sites){
		String errors = "";
		if (sites.size() == 0) {
			errors += "At Least One Mentor";
		}
		for (Doc doc : workplaceapproval.getDocs()) {
			// checks if all required documents provided, appeal document is not required at this stage 
			if (doc.getConfigDoc().getRequiredDocument()) {
				if (doc.getId() == null && doc.getData() == null) {
					if (errors == "") {
						errors += "Document: " + doc.getConfigDoc().getName();
					} else {
						errors += ", Document: " + doc.getConfigDoc().getName();
					}
				}
			}
			/*if (workplaceapproval.getFormalAgreementAprrovedWorkpalces() != null && workplaceapproval.getFormalAgreementAprrovedWorkpalces().getId() == HAJConstants.NO_ID) {
				if (doc.getConfigDoc().getWpaDocRequirements() == WpaDocRequirements.ScopeEvidence && doc.getId() == null && doc.getData() == null) {
					errors += "Upload: " + doc.getConfigDoc().getName() + " Before Proceeding.";
				}
			}*/
			/*if (workplaceapproval.getWorkplaceAbleToCoverScope() != null && workplaceapproval.getWorkplaceAbleToCoverScope().getId() == HAJConstants.NO_ID) {
				if (doc.getConfigDoc().getWpaDocRequirements() == WpaDocRequirements.ProofAgreement && doc.getId() == null && doc.getData() == null) {
					errors += "Upload: "+ doc.getConfigDoc().getName() + " Before Proceeding.";
				}
			}*/
			if(workplaceapproval.getWorkplaceAbleToCoverTradeOrQualificationSdf() !=null && workplaceapproval.getWorkplaceAbleToCoverTradeOrQualificationSdf().getId() == HAJConstants.NO_ID){
				if(workplaceapproval.getFormalAgreementWithOtherSdf() != null && workplaceapproval.getFormalAgreementWithOtherSdf().getId() == HAJConstants.YES_ID){
					//doc.getConfigDoc().setWpaDocRequirements(WpaDocRequirements.ProofAgreement);
					if (doc.getConfigDoc().getWpaDocRequirements() == WpaDocRequirements.ProofAgreement) {
						if(doc.getId() == null && doc.getData() == null){
							errors += "Upload: "+ doc.getConfigDoc().getName() + " Before Proceeding.";
						}
					}
				}
			}
		}
		return errors;
	}
	
	public String validateChecks(WorkPlaceApproval workplaceapproval) throws Exception{
		String errors = "";
		List<WorkPlaceApprovalSites> sites= dao.findSitesByApproval(workplaceapproval);
		if (sites.size() == 0) {
			errors += "At Least One Mentor";
		}
		for (Doc doc : workplaceapproval.getDocs()) {
			// checks if all required documents provided, appeal document is not required at this stage 
			if (doc.getConfigDoc().getRequiredDocument()) {
				if (doc.getId() == null && doc.getData() == null) {
					if (errors == "") {
						errors += "Document: " + doc.getConfigDoc().getName();
					} else {
						errors += ", Document: " + doc.getConfigDoc().getName();
					}
				}
			}
			
			if(workplaceapproval.getWorkplaceAbleToCoverTradeOrQualificationSdf() !=null && workplaceapproval.getWorkplaceAbleToCoverTradeOrQualificationSdf().getId() == HAJConstants.NO_ID){
				if(workplaceapproval.getFormalAgreementWithOtherSdf() != null && workplaceapproval.getFormalAgreementWithOtherSdf().getId() == HAJConstants.YES_ID){
					//doc.getConfigDoc().setWpaDocRequirements(WpaDocRequirements.ProofAgreement);
					if (doc.getConfigDoc().getWpaDocRequirements() == WpaDocRequirements.ProofAgreement) {
						if(doc.getId() == null && doc.getData() == null){
							errors += "Upload: "+ doc.getConfigDoc().getName() + " Before Proceeding.";
						}
					}
				}
			}
		}
		return errors;
	}
	/**
	 * When the client user submits the work place approval for review to the clo
	 * @param workPlaceApproval
	 * @param user
	 * @param task
	 * @throws Exception
	 */
	public void submitToCloForReview(WorkPlaceApproval workPlaceApproval, Users user, Tasks task, List<WorkPlaceApprovalTrades> approvalTrades) throws Exception{
		
			//List<Users> cloUsers = locateCloForTask(workPlaceApproval.getCompany(), workPlaceApproval.getSites());
			List<Users> qualityAssuresUsers = locateQualityAssuresForTask(workPlaceApproval.getCompany(), workPlaceApproval.getSites());
			workPlaceApproval.setWithClientCompany(false);
			workPlaceApproval.setWithManager(false);
			create(workPlaceApproval);
			if (approvalTrades != null) {
				for (WorkPlaceApprovalTrades workPlaceApprovalTrades : approvalTrades) {
					if (workPlaceApprovalTrades.getId() == null) {
						dao.create(workPlaceApprovalTrades);
					}
				}
			}
			for (Doc doc : workPlaceApproval.getDocs()) {
				if (doc.getId() == null && doc.getData() != null) {
					doc.setTargetKey(workPlaceApproval.getId());
					doc.setTargetClass(WorkPlaceApproval.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
				}
			}
			
			// A workplace approval application for Trade: Abattoir Veterinarian has been submitted by Company Name (Entity ID). Please review the application.
			String taskDescription = "A workplace approval application for ";
			if (workPlaceApproval.getQualification() != null) {
				taskDescription += "Qualification: " + workPlaceApproval.getQualification().getDescription();
			}
			if (workPlaceApproval.getQualification() == null && workPlaceApproval.getOfoCodes() != null) {
				taskDescription += "Trade: " + workPlaceApproval.getOfoCodes().getDescription();
			}
			taskDescription += " has been submitted by " + workPlaceApproval.getCompany().getCompanyName() + " (" + workPlaceApproval.getCompany().getLevyNumber() + "). Please review the application.";
			TasksService.instance().completeTaskByTargetKey(WorkPlaceApproval.class.getName(), workPlaceApproval.getId());
			TasksService.instance().createTaskUser(qualityAssuresUsers, WorkPlaceApproval.class.getName(), workPlaceApproval.getId(), taskDescription, user, true, true, null, ConfigDocProcessEnum.WORKPLACE_APPROVAL, false);
			sendAcknoledgementEmail(workPlaceApproval.getCompany(),  workPlaceApproval.getQualification(),  workPlaceApproval.getOfoCodes(),  workPlaceApproval.getSites(),  user);
			RejectReasonMultipleSelectionService.instance().clearEntries(workPlaceApproval.getId(), workPlaceApproval.getClass().getName(), ConfigDocProcessEnum.WORKPLACE_APPROVAL);
		
	}
	
	public void clientSubmitToManager(WorkPlaceApproval workPlaceApproval, Users user, Tasks task, List<WorkPlaceApprovalTrades> approvalTrades) throws Exception{
		
		//List<Users> cloUsers = locateCloForTask(workPlaceApproval.getCompany(), workPlaceApproval.getSites());
		List<Users> managers=hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Manager_Quality_Assurance_ID);
		workPlaceApproval.setWithClientCompany(false);
		workPlaceApproval.setWithManager(true);
		create(workPlaceApproval);
		if (approvalTrades != null) {
			for (WorkPlaceApprovalTrades workPlaceApprovalTrades : approvalTrades) {
				if (workPlaceApprovalTrades.getId() == null) {
					dao.create(workPlaceApprovalTrades);
				}
			}
		}
		for (Doc doc : workPlaceApproval.getDocs()) {
			if (doc.getId() == null && doc.getData() != null) {
				doc.setTargetKey(workPlaceApproval.getId());
				doc.setTargetClass(WorkPlaceApproval.class.getName());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
			}
		}
		
		// A workplace approval application for Trade: Abattoir Veterinarian has been submitted by Company Name (Entity ID). Please review the application.
		String taskDescription = "A workplace approval application for ";
		if (workPlaceApproval.getQualification() != null) {
			taskDescription += "Qualification: " + workPlaceApproval.getQualification().getDescription();
		}
		if (workPlaceApproval.getQualification() == null && workPlaceApproval.getOfoCodes() != null) {
			taskDescription += "Trade: " + workPlaceApproval.getOfoCodes().getDescription();
		}
		taskDescription += " has been submitted by " + workPlaceApproval.getCompany().getCompanyName() + " (" + workPlaceApproval.getCompany().getLevyNumber() + "). Please review the application.";
		TasksService.instance().completeTaskByTargetKey(WorkPlaceApproval.class.getName(), workPlaceApproval.getId());
		TasksService.instance().createTaskUser(managers, WorkPlaceApproval.class.getName(), workPlaceApproval.getId(), taskDescription, user, true, true, null, ConfigDocProcessEnum.WORKPLACE_APPROVAL, false);
		sendAcknoledgementEmail(workPlaceApproval.getCompany(),  workPlaceApproval.getQualification(),  workPlaceApproval.getOfoCodes(),  workPlaceApproval.getSites(),  user);
		RejectReasonMultipleSelectionService.instance().clearEntries(workPlaceApproval.getId(), workPlaceApproval.getClass().getName(), ConfigDocProcessEnum.WORKPLACE_APPROVAL);
	
	}
	
	
	/**
	 * Clo Rejects task to SDF to make amendments
	 * @param workPlaceApproval
	 * @param user
	 * @param task
	 * @throws Exception
	 */
	public void cloRejectionToSDF(WorkPlaceApproval workPlaceApproval, Users sessionUser, Tasks task, List<RejectReasons> rejectionReaonsList) throws Exception{
		List<Users> clientUsers = locateClintUsersForTasks(workPlaceApproval.getCompany());
		workPlaceApproval.setWithClientCompany(true);
		workPlaceApproval.setWithManager(false);
		create(workPlaceApproval);
		String taskDescription = "The workplace approval application for "  ;
		if (workPlaceApproval.getQualification() != null) {
			taskDescription += " Qualification: " + workPlaceApproval.getQualification().getDescription();
		}
		if (workPlaceApproval.getQualification() == null && workPlaceApproval.getOfoCodes() != null) {
			taskDescription += " Trade: " + workPlaceApproval.getOfoCodes().getDescription() ;
		}
		taskDescription += " for "+workPlaceApproval.getCompany().getCompanyName() + " (" +  workPlaceApproval.getCompany().getLevyNumber() + ") has been rejected. Please review the application.";
		TasksService.instance().createTaskUser(clientUsers, WorkPlaceApproval.class.getName(), workPlaceApproval.getId(), taskDescription, sessionUser, true, true, null, ConfigDocProcessEnum.WORKPLACE_APPROVAL, false);
		TasksService.instance().completeTask(task);
		List<IDataEntity> createList = new ArrayList<>();
		createList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(workPlaceApproval.getId(), workPlaceApproval.getClass().getName(), rejectionReaonsList, sessionUser, ConfigDocProcessEnum.WORKPLACE_APPROVAL));
		if (createList.size() != 0) {
			dao.createBatch(createList);
		}
		sendRejectEmail(workPlaceApproval, sessionUser, task, rejectionReaonsList);
	}
	
	public void rejectionToSDF(WorkPlaceApproval workPlaceApproval, Users sessionUser, Tasks task, List<RejectReasons> rejectionReaonsList) throws Exception{
		List<Users> clientUsers = locateClintUsersForTasks(workPlaceApproval.getCompany());
		workPlaceApproval.setWithClientCompany(true);
		workPlaceApproval.setWithManager(false);
		create(workPlaceApproval);
		String taskDescription = "The workplace approval application for "  ;
		if (workPlaceApproval.getQualification() != null) {
			taskDescription += " Qualification: " + workPlaceApproval.getQualification().getDescription();
		}
		if (workPlaceApproval.getQualification() == null && workPlaceApproval.getOfoCodes() != null) {
			taskDescription += " Trade: " + workPlaceApproval.getOfoCodes().getDescription() ;
		}
		taskDescription += " for "+workPlaceApproval.getCompany().getCompanyName() + " (" +  workPlaceApproval.getCompany().getLevyNumber() + ") has been rejected. Please review the application.";
		TasksService.instance().findPreviousInProcessAndCreateTask(taskDescription, sessionUser, workPlaceApproval.getId(), WorkPlaceApproval.class.getName(), true, task, null, clientUsers);
		List<IDataEntity> createList = new ArrayList<>();
		createList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(workPlaceApproval.getId(), workPlaceApproval.getClass().getName(), rejectionReaonsList, sessionUser, ConfigDocProcessEnum.WORKPLACE_APPROVAL));
		if (createList.size() != 0) {
			dao.createBatch(createList);
		}
		sendRejectEmail(workPlaceApproval, sessionUser, task, rejectionReaonsList);
	}
	
	public void managerReject(WorkPlaceApproval workPlaceApproval, Users sessionUser, Tasks task, List<RejectReasons> rejectionReaonsList) throws Exception{
		List<Users> clientUsers = locateClintUsersForTasks(workPlaceApproval.getCompany());
		workPlaceApproval.setWithClientCompany(false);
		workPlaceApproval.setWithManager(false);
		workPlaceApproval.setApprovalEnum(ApprovalEnum.Rejected);
		workPlaceApproval.setApprovalDate(getSynchronizedDate());
		create(workPlaceApproval);
		//String taskDescription = "The workplace approval application for "  ;
		/*if (workPlaceApproval.getQualification() != null) {
			taskDescription += " Qualification: " + workPlaceApproval.getQualification().getDescription();
		}
		if (workPlaceApproval.getQualification() == null && workPlaceApproval.getOfoCodes() != null) {
			taskDescription += " Trade: " + workPlaceApproval.getOfoCodes().getDescription() ;
		}*/
		//taskDescription += " for "+workPlaceApproval.getCompany().getCompanyName() + " (" +  workPlaceApproval.getCompany().getLevyNumber() + ") has been rejected. Please review the application.";
		//TasksService.instance().createTaskUser(clientUsers, WorkPlaceApproval.class.getName(), workPlaceApproval.getId(), taskDescription, sessionUser, true, true, null, ConfigDocProcessEnum.WORKPLACE_APPROVAL, false);
		TasksService.instance().completeTask(task);
		List<IDataEntity> createList = new ArrayList<>();
		createList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(workPlaceApproval.getId(), workPlaceApproval.getClass().getName(), rejectionReaonsList, sessionUser, ConfigDocProcessEnum.WORKPLACE_APPROVAL));
		if (createList.size() != 0) {
			dao.createBatch(createList);
		}
		managerSendRejectEmail(workPlaceApproval, sessionUser, task, rejectionReaonsList);
	}
	
	public void submitForSignOff(WorkPlaceApproval workPlaceApproval, Users sessionUser, Tasks task, List<RejectReasons> rejectionReasonsList) throws Exception{
		for (Doc document : workPlaceApproval.getDocs()) {
			if (workPlaceApproval.getFormalAgreementAprrovedWorkpalces() != null && workPlaceApproval.getFormalAgreementAprrovedWorkpalces().getId() == HAJConstants.NO_ID) {
				if (document.getConfigDoc().getWpaDocRequirements() == WpaDocRequirements.ScopeEvidence && document.getId() == null && document.getData() == null) {
					throw new Exception("Upload: "+ document.getConfigDoc().getName() + " Before Proceeding.");
				}
			}
			if (workPlaceApproval.getWorkplaceAbleToCoverScope() != null && workPlaceApproval.getWorkplaceAbleToCoverScope().getId() == HAJConstants.NO_ID) {
				if (document.getConfigDoc().getWpaDocRequirements() == WpaDocRequirements.ProofAgreement && document.getId() == null && document.getData() == null) {
					throw new Exception("Upload: "+ document.getConfigDoc().getName() + " Before Proceeding.");
				}
			}
		}
		
		for (Doc document : workPlaceApproval.getDocs()) {
			if (document.getId() == null && document.getData() != null) {
				document.setTargetKey(workPlaceApproval.getId());
				document.setTargetClass(WorkPlaceApproval.class.getName());
				DocService.instance().save(document, document.getData(), document.getOriginalFname(), sessionUser);
			}
		}
		
		
		// locate users
		List<Users> signoffUsers = new ArrayList<Users>();
		
		for(Signoff signoff: workPlaceApproval.getSignOffs()) {
			signoffUsers.add(signoff.getUser());
		}
		signoffUsers.add(sessionUser);
		// signoffUsers.addAll(locateClintUsersForTasks(workPlaceApproval.getCompany()));
		// signoffUsers.addAll(locateQualityAssuresForTask(workPlaceApproval.getCompany(), workPlaceApproval.getSites()));
		workPlaceApproval.setWithClientCompany(false);
		workPlaceApproval.setApprovalEnum(ApprovalEnum.PendingSignOff);
		create(workPlaceApproval);
		
		// creates sign off
		List<IDataEntity> dataEntities = new ArrayList<>();
		if (workPlaceApproval.getSignOffs() == null || workPlaceApproval.getSignOffs().size() == 0) {
			workPlaceApproval.setSignOffs(new ArrayList<>());
		}
		for (Users user : signoffUsers) {
			Signoff signoff = new Signoff();
			signoff.setAccept(false);
			signoff.setUser(user);
			signoff.setTargetClass(WorkPlaceApproval.class.getName());
			signoff.setTargetKey(workPlaceApproval.getId());
			dataEntities.add(signoff);
		}
		if (dataEntities.size() != 0) {
			if (workPlaceApproval.getCloRecommendation() == CloRecommendationEnum.Approval) {
				RejectReasonMultipleSelectionService.instance().clearEntries(workPlaceApproval.getId(), workPlaceApproval.getClass().getName(), ConfigDocProcessEnum.WORKPLACE_APPROVAL);
			} else {
				dataEntities.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(workPlaceApproval.getId(), workPlaceApproval.getClass().getName(), rejectionReasonsList, sessionUser, ConfigDocProcessEnum.WORKPLACE_APPROVAL));
			}
			this.dao.createBatch(dataEntities);
			String taskDescription = "The recommended outcome of the workplace approval application for ";
			if (workPlaceApproval.getQualification() != null) {
				taskDescription += " Qualification: " + workPlaceApproval.getQualification().getDescription();
			}
			if (workPlaceApproval.getQualification() == null && workPlaceApproval.getOfoCodes() != null) {
				taskDescription += " Trade: " + workPlaceApproval.getOfoCodes().getDescription() ;
			}
			taskDescription += " for "+ workPlaceApproval.getCompany().getCompanyName() + " (" + workPlaceApproval.getCompany().getLevyNumber() + ") is:";
			if (workPlaceApproval.getCloRecommendation() == CloRecommendationEnum.Approval) {
				taskDescription += " Approval.";
			} else {
				taskDescription += " Rejection.";
			}
			taskDescription += " Please review and sign off the work place approval application";
			TasksService.instance().createTaskUser(signoffUsers, WorkPlaceApproval.class.getName(), workPlaceApproval.getId(), taskDescription, sessionUser, true, true, null, ConfigDocProcessEnum.WORKPLACE_APPROVAL, false);
			if (task != null) {
				TasksService.instance().completeTask(task);
			}
		}
	}
	
	
	
	/**
	 * @param entity
	 * @param user
	 * @param task
	 * @throws Exception
	 */
	public void signOff(WorkPlaceApproval entity, Users user, Tasks task) throws Exception {
		
			String taskDescription = "A workplace approval application for ";
			if (entity.getQualification() != null) {
				taskDescription += "Qualification: " + entity.getQualification().getDescription();
			}
			if (entity.getQualification() == null && entity.getOfoCodes() != null) {
				taskDescription += "Trade: " + entity.getOfoCodes().getDescription();
			}
			taskDescription += " has been submitted by " + entity.getCompany().getCompanyName() + " (" + entity.getCompany().getLevyNumber() + "). Please review the application.";
			
			List<IDataEntity> dataEntities = new ArrayList<>();
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() == null && doc.getData() != null && doc.getConfigDoc().getRequiredForAppeal()) {
					doc.setTargetKey(entity.getId());
					doc.setTargetClass(WorkPlaceApproval.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
				}
			}
			dataEntities.add(entity);
			boolean changePending = true;
			for (Signoff signoff : entity.getSignOffs()) {
				if (signoff.getUser().equals(user)) {
					signoff.setSignOffDate(getSynchronizedDate());
				}
				dataEntities.add(signoff);
				if ((signoff.getAccept() == null || !signoff.getAccept()) && (signoff.getDispute() == null || !signoff.getDispute())) {
					changePending = false;
				}
			}
			TasksService.instance().completeTask(task);
			if (changePending) {
				//TasksService.instance().completeTaskByTargetKey(WorkPlaceApproval.class.getName(), entity.getId());
				entity.setApprovalEnum(ApprovalEnum.PendingFinalApproval);
				RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
				List<Users> signoffs = new ArrayList<>();
				//signoffs.add(rt.getCrm().getUsers());
				//TasksService.instance().findFirstInProcessAndCreateTask("", user, enWorkPlaceApprovaltity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.WORKPLACE_APPROVAL, null, locateCrmForTask(entity.getCompany(), entity.getSites()));
				//TasksService.instance().createTaskUser(signoffUsers, .class.getName(), workPlaceApproval.getId(), taskDescription, sessionUser, true, true, null, ConfigDocProcessEnum.WORKPLACE_APPROVAL, false);		
				TasksService.instance().findFirstInProcessAndCreateTask(null, entity.getId(), WorkPlaceApproval.class.getName(), ConfigDocProcessEnum.WORKPLACE_APPROVAL, false);
//				List<Users> managers = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_QUALITY_ASSURANCE_ID);
//				TasksService.instance().createTaskUser(managers, WorkPlaceApproval.class.getName(), entity.getId(), taskDescription, user, true, true, null, ConfigDocProcessEnum.WORKPLACE_APPROVAL, false);
			}
			dao.updateBatch(dataEntities);
		
	}
	
	/**
	 * Locates the client users to send the task to 
	 * @param company
	 * @return List<Users>
	 * @throws Exception
	 */
	public List<Users> locateClintUsersForTasks(Company company) throws Exception{
		// checks for SDF Users
		List<Users> users = new ArrayList<>();
		SDFCompanyService sdfCompanyService = new SDFCompanyService();
		users.addAll(sdfCompanyService.findPrimaryAndLabourSDFUsers(company));
		// add users with responsibly 
		CompanyUsersService companyUsersService = new CompanyUsersService();
		if (users.size() == 0) {
			users.addAll(companyUsersService.findUsersByCompanyNotInResponsibilityAndUserPosition(company, ConfigDocProcessEnum.WORKPLACE_APPROVAL, (long) 4));
		}
		// add training provider users
		if (users.size() == 0) {
			users.addAll(companyUsersService.findUsersByCompanyResponsibility(company, ConfigDocProcessEnum.TP));
		}
		companyUsersService = null;
		sdfCompanyService = null;
		return users;
	}
	
	/**
	 * Locates the CLO for work flow
	 * @param company
	 * @param site
	 * @return List<Users>
	 * @throws Exception
	 */
	public List<Users> locateCloForTask(Company company, Sites site) throws Exception{
		List<Users> users = new ArrayList<>();
		RegionTown rt = null;
		if (site == null) {
			if (company.getResidentialAddress() == null || company.getResidentialAddress().getTown() == null) {
				throw new Exception("Address Not Configured/Set. Contact Support!");
			}
			rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		} else {
			if (site.getRegisteredAddress() == null || site.getRegisteredAddress().getTown() == null) {
				throw new Exception("Address Not Configured/Set. Contact Support!");
			}
			rt = RegionTownService.instance().findByTown(site.getRegisteredAddress().getTown());
		}
		UsersService usersService = new UsersService();
		Users cloUser = rt.getClo().getUsers();
		if (cloUser != null && cloUser.getId() != null) {
			users.add(usersService.findByKey(cloUser.getId()));
		}
		usersService = null;
		if (users.size() == 0) {
			throw new Exception("Unable to locate CLO for next process. Contact support!");
		} else {
			return users;
		}
	}
	
	public List<Users> locateCrmForTask(Company company, Sites site) throws Exception{
		List<Users> users = new ArrayList<>();
		RegionTown rt = null;
		if (site == null) {
			if (company.getResidentialAddress() == null || company.getResidentialAddress().getTown() == null) {
				throw new Exception("Address Not Configured/Set. Contact Support!");
			}
			rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		} else {
			if (site.getRegisteredAddress() == null || site.getRegisteredAddress().getTown() == null) {
				throw new Exception("Address Not Configured/Set. Contact Support!");
			}
			rt = RegionTownService.instance().findByTown(site.getRegisteredAddress().getTown());
		}
		UsersService usersService = new UsersService();
		Users cloUser = rt.getCrm().getUsers();
		if (cloUser != null && cloUser.getId() != null) {
			users.add(usersService.findByKey(cloUser.getId()));
		}
		usersService = null;
		if (users.size() == 0) {
			throw new Exception("Unable to locate CRM for next process. Contact support!");
		} else {
			return users;
		}
	}

	public List<Users> locateQualityAssuresForTask(Company company, Sites site) throws Exception{
		RegionTown rt;
		if (site == null) {
			if (company.getResidentialAddress() == null || company.getResidentialAddress().getTown() == null) {
				throw new Exception("Address Not Configured/Set. Contact Support!");
			}
			rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		} else {
			if (site.getRegisteredAddress() == null || site.getRegisteredAddress().getTown() == null) {
				throw new Exception("Address Not Configured/Set. Contact Support!");
			}
			rt = RegionTownService.instance().findByTown(site.getRegisteredAddress().getTown());
		}
		List<Users> usersList = dao.findUserByRegionAndRole(HAJConstants.QUALITY_ASSUROR_ROLE_ID, rt.getRegion().getId());
        if (CollectionUtils.isEmpty(usersList)) {
			throw new Exception("Unable to locate CLO for next process. Contact support!");
		} else {
			return usersList;
		}
	}
	public void completeRegistration(WorkPlaceApproval registration, Users user, Tasks tasks, List<WorkPlaceApprovalSites> approvalSites, List<WorkPlaceApprovalTrades> approvalTrades) throws Exception {
		create(registration);
		for (WorkPlaceApprovalSites workPlaceApprovalSites : approvalSites) {
			if (workPlaceApprovalSites.getId() == null) {
				dao.create(workPlaceApprovalSites);
				// new documents upload against WorkPlaceApprovalSites
				if (registration.getDocs() != null && registration.getDocs().size() != 0) {
					for (Doc approvalSitesDoc : workPlaceApprovalSites.getDocs()) {
						if (approvalSitesDoc.getId() == null && approvalSitesDoc.getData() != null) {
							approvalSitesDoc.setTargetKey(workPlaceApprovalSites.getId());
							approvalSitesDoc.setTargetClass(WorkPlaceApprovalSites.class.getName());
							DocService.instance().save(approvalSitesDoc, approvalSitesDoc.getData(), approvalSitesDoc.getOriginalFname(), user);
						}
					}
				}
			}
		}
		if (approvalTrades != null) {
			for (WorkPlaceApprovalTrades workPlaceApprovalTrades : approvalTrades) {
				if (workPlaceApprovalTrades.getId() == null) {
					dao.create(workPlaceApprovalTrades);
				}
			}
		}

		for (Doc doc : registration.getDocs()) {
			if (doc.getId() == null && doc.getData() != null) {
				doc.setTargetKey(registration.getId());
				doc.setTargetClass(WorkPlaceApproval.class.getName());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
			}
		}
		TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), WorkPlaceApproval.class.getName(), true, tasks, null, null);
	}
	public void completeRegistration(WorkPlaceApproval registration, Users user, Tasks tasks, List<WorkPlaceApprovalTrades> approvalTrades) throws Exception {
		create(registration);
		List<WorkPlaceApprovalSites> approvalSites = dao.findSitesByApproval(registration);
		for (WorkPlaceApprovalSites workPlaceApprovalSites : approvalSites) {
			if (workPlaceApprovalSites.getId() == null) {
				dao.create(workPlaceApprovalSites);
				// new documents upload against WorkPlaceApprovalSites
				if (registration.getDocs() != null && registration.getDocs().size() != 0) {
					for (Doc approvalSitesDoc : workPlaceApprovalSites.getDocs()) {
						if (approvalSitesDoc.getId() == null && approvalSitesDoc.getData() != null) {
							approvalSitesDoc.setTargetKey(workPlaceApprovalSites.getId());
							approvalSitesDoc.setTargetClass(WorkPlaceApprovalSites.class.getName());
							DocService.instance().save(approvalSitesDoc, approvalSitesDoc.getData(), approvalSitesDoc.getOriginalFname(), user);
						}
					}
				}
			}
		}
		if (approvalTrades != null) {
			for (WorkPlaceApprovalTrades workPlaceApprovalTrades : approvalTrades) {
				if (workPlaceApprovalTrades.getId() == null) {
					dao.create(workPlaceApprovalTrades);
				}
			}
		}

		for (Doc doc : registration.getDocs()) {
			if (doc.getId() == null && doc.getData() != null) {
				doc.setTargetKey(registration.getId());
				doc.setTargetClass(WorkPlaceApproval.class.getName());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
			}
		}
		TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), WorkPlaceApproval.class.getName(), true, tasks, null, null);
	}

	public void completeLegacyRegistration(WorkPlaceApproval registration, Users user, Tasks tasks, List<WorkPlaceApprovalSites> approvalSites, List<WorkPlaceApprovalTrades> approvalTrades) throws Exception {
		List<Users> userList = new ArrayList<>();
		
		RegionTown rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		if (rt != null && rt.getClo() != null) {
			userList.add(rt.getClo().getUsers());
			//userList.add(rt.getCrm().getUsers());
		}
		
		if(userList.size() ==0) {
			throw new Exception("No CLO assigned to the company region");
		}
		
		create(registration);
		for (WorkPlaceApprovalSites workPlaceApprovalSites : approvalSites) {
			if (workPlaceApprovalSites.getId() == null) {
				dao.create(workPlaceApprovalSites);
				// new documents upload against WorkPlaceApprovalSites
				if (registration.getDocs() != null && registration.getDocs().size() != 0) {
					for (Doc approvalSitesDoc : workPlaceApprovalSites.getDocs()) {
						if (approvalSitesDoc.getId() == null && approvalSitesDoc.getData() != null) {
							approvalSitesDoc.setTargetKey(workPlaceApprovalSites.getId());
							approvalSitesDoc.setTargetClass(WorkPlaceApprovalSites.class.getName());
							DocService.instance().save(approvalSitesDoc, approvalSitesDoc.getData(), approvalSitesDoc.getOriginalFname(), user);
						}
					}
				}
			}
		}
		if (approvalTrades != null) {
			for (WorkPlaceApprovalTrades workPlaceApprovalTrades : approvalTrades) {
				if (workPlaceApprovalTrades.getId() == null) {
					dao.create(workPlaceApprovalTrades);
				}
			}
		}

		for (Doc doc : registration.getDocs()) {
			if (doc.getId() == null && doc.getData() != null) {
				doc.setTargetKey(registration.getId());
				doc.setTargetClass(WorkPlaceApproval.class.getName());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
			}
		}
		TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), WorkPlaceApproval.class.getName(), true, tasks, null, userList);
		sendAcknoledgementEmail(registration.getCompany(), registration.getQualification(), registration.getOfoCodes(), registration.getSites(), user);
	}
	
	public void approveRegistration(WorkPlaceApproval registration, Users user, Tasks tasks) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.WaitingForManager);
		registration.setApprovalDate(getSynchronizedDate());
		create(registration);
		TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), WorkPlaceApproval.class.getName(), true, tasks, null, null);
	}

	public void finalApproveRegistration(WorkPlaceApproval registration, Users user, Tasks tasks) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.Approved);
		registration.setApprovalDate(getSynchronizedDate());
		registration.setWorkPlaceApprovalNumber(countAllWpaWhereWpaNumberAplliedForCompany(getSynchronizedDate()));		
		if(registration.getQualification() != null && registration.getQualification().getQualregistrationendDate() != null) {
			registration.setEndDate(registration.getQualification().getQualregistrationendDate());
		}		
		Company company = registration.getCompany();
		if(company!=null) {	
			CompanyService companyService = new CompanyService();			
			SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");//30 06 2022
			Date date = format.parse("2022-06-30");
			registration.setApprovalReviewDate(date);
			
			company.setWorkplaceApprovalNumber(generateCompanyWorkPlaceApprovalNumber(getSynchronizedDate(), registration));				
			companyService.updateNoHistory(company);		
		
			if(registration.getApprovalReviewDate() == null) {					
				registration.setApprovalReviewDate(date);
			}
			
		}
		
		create(registration);	
		updateWorkPlaceApprovalMentorStatus(registration, ApprovalEnum.Approved);
		TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), WorkPlaceApproval.class.getName(), true, tasks, null, null);	
		sendApprovalEmail(registration, tasks);	
	}
	
	private void updateWorkPlaceApprovalMentorStatus(WorkPlaceApproval registration, ApprovalEnum approvalEnum) throws Exception {
		List<IDataEntity> updateBatch = new ArrayList<>();
		List<WorkPlaceApprovalSites>workPlaceApprovalSites = findSitesByApproval(registration);
		for(WorkPlaceApprovalSites w:workPlaceApprovalSites) {
			w.setApprovalEnum(approvalEnum);
			updateBatch.add(w);
		}
		dao.updateBatch(updateBatch);
	}

	public void finalApproveLegacyRegistration(WorkPlaceApproval registration, Users user, Tasks tasks) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.Approved);
		registration.setApprovalDate(getSynchronizedDate());
		registration.setWorkPlaceApprovalNumber(countAllWpaWhereWpaNumberAplliedForCompany(getSynchronizedDate()));		
		if(registration.getQualification() != null && registration.getQualification().getQualregistrationendDate() != null) {
			registration.setEndDate(registration.getQualification().getQualregistrationendDate());
		}		
		Company company = registration.getCompany();
		if(company!=null) {	
			CompanyService companyService = new CompanyService();			
			SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");//30 06 2022
			Date date = format.parse("2022-06-30");
			registration.setApprovalReviewDate(date);
			
			company.setWorkplaceApprovalNumber(generateCompanyWorkPlaceApprovalNumber(getSynchronizedDate(), registration));				
			companyService.updateNoHistory(company);		
		
			if(registration.getApprovalReviewDate() == null) {					
				registration.setApprovalReviewDate(date);
			}
			
		}
		create(registration);	
		updateWorkPlaceApprovalMentorStatus(registration, ApprovalEnum.Approved);
		TasksService.instance().findNextInProcessAndCreateTask("", user, registration.getId(), WorkPlaceApproval.class.getName(), true, tasks, null, null);	
		sendLegacyWorkPlaceApprovalApprovalEmail(registration, tasks);	
	}

	public void rejectRegistration(WorkPlaceApproval registration, Users user, Tasks tasks,List<RejectReasons> rejectReasonsList) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.Rejected);
		create(registration);		
		TasksService.instance().findPreviousInProcessAndCreateTask("", user, registration.getId(), WorkPlaceApproval.class.getName(), true, tasks, null, null);
		sendRejectEmail(registration, user, tasks, rejectReasonsList);
		
	}

	public void finalRejectRegistration(WorkPlaceApproval registration, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.Rejected);
		registration.setApprovalDate(getSynchronizedDate());
		create(registration);	
		updateWorkPlaceApprovalMentorStatus(registration, ApprovalEnum.Rejected);
		TasksService.instance().completeTask(tasks);
		RejectReasonMultipleSelectionService.instance().removeCreateLinks(registration.getId(), WorkPlaceApproval.class.getName(), rejectReasonsList, user, ConfigDocProcessEnum.WORKPLACE_APPROVAL);
		sendRejectEmail(registration, user, tasks, rejectReasonsList);
		
	}
	
	public void finalRejectLegacyRegistration(WorkPlaceApproval registration, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception {
		registration.setApprovalEnum(ApprovalEnum.Rejected);
		registration.setApprovalDate(getSynchronizedDate());
		create(registration);		
		TasksService.instance().completeTask(tasks);
		RejectReasonMultipleSelectionService.instance().removeCreateLinks(registration.getId(), WorkPlaceApproval.class.getName(), rejectReasonsList, user, ConfigDocProcessEnum.WORKPLACE_APPROVAL);
			
		
		if(registration.getTargetClass() != null) {		
			if(registration.getTargetClass().matches(LegacyEmployerWA2Learnership.class.getName())) {
				LegacyEmployerWA2LearnershipService legacyEmployerWA2LearnershipService = new LegacyEmployerWA2LearnershipService();
				LegacyEmployerWA2Learnership legacyEmployerWA2Learnership = legacyEmployerWA2LearnershipService.findByKey(registration.getTargetKey());
				legacyEmployerWA2Learnership.setSubmitted(false);
				legacyEmployerWA2LearnershipService.update(legacyEmployerWA2Learnership);
				legacyEmployerWA2LearnershipService = null;
			}else if(registration.getTargetClass().matches(LegacyEmployerWA2Qualification.class.getName())) {
				LegacyEmployerWA2QualificationService legacyEmployerWA2QualificationService = new LegacyEmployerWA2QualificationService();
				LegacyEmployerWA2Qualification legacyEmployerWA2Qualification = legacyEmployerWA2QualificationService.findByKey(registration.getTargetKey());
				legacyEmployerWA2Qualification.setSubmitted(false);
				legacyEmployerWA2QualificationService.update(legacyEmployerWA2Qualification);
				legacyEmployerWA2QualificationService =null;
			}else if(registration.getTargetClass().matches(LegacyEmployerWA2SkillsProgramme.class.getName())) {
				LegacyEmployerWA2SkillsProgrammeService legacyEmployerWA2SkillsProgrammeService = new LegacyEmployerWA2SkillsProgrammeService();
				LegacyEmployerWA2SkillsProgramme legacyEmployerWA2SkillsProgramme = legacyEmployerWA2SkillsProgrammeService.findByKey(registration.getTargetKey());
				legacyEmployerWA2SkillsProgramme.setSubmitted(false);
				legacyEmployerWA2SkillsProgrammeService.update(legacyEmployerWA2SkillsProgramme);
				legacyEmployerWA2SkillsProgrammeService = null;
			}else if(registration.getTargetClass().matches(LegacyEmployerWA2Trade.class.getName())) {
				LegacyEmployerWA2TradeService legacyEmployerWA2TradeService = new LegacyEmployerWA2TradeService();
				LegacyEmployerWA2Trade legacyEmployerWA2Trade = legacyEmployerWA2TradeService.findByKey(registration.getTargetKey());
				legacyEmployerWA2Trade.setSubmitted(false);
				legacyEmployerWA2TradeService.update(legacyEmployerWA2Trade);
				legacyEmployerWA2TradeService = null;
			}else if(registration.getTargetClass().matches(LegacyEmployerWA2UnitStandard.class.getName())) {
				LegacyEmployerWA2UnitStandardService legacyEmployerWA2UnitStandardService = new LegacyEmployerWA2UnitStandardService();
				LegacyEmployerWA2UnitStandard legacyEmployerWA2UnitStandard = legacyEmployerWA2UnitStandardService.findByKey(registration.getTargetKey());
				legacyEmployerWA2UnitStandard.setSubmitted(false);
				legacyEmployerWA2UnitStandardService.update(legacyEmployerWA2UnitStandard);
				legacyEmployerWA2UnitStandardService = null;
			}
		}	
		updateWorkPlaceApprovalMentorStatus(registration, ApprovalEnum.Rejected);
		sendLegacyWorkPlaceApprovalRejectEmail(registration, user, tasks, rejectReasonsList);	
	}
	/**
	 * The first set of the Work Place Approval Visit Date
	 * @param workplaceapproval
	 * @throws Exception
	 */
	public void setFirstVisitDate(WorkPlaceApproval workplaceapproval, Users sessionUser) throws Exception{
		workplaceapproval.setDateForVisitProvided(true);
		workplaceapproval.setReviewUser(sessionUser);
		create(workplaceapproval);
		
		
		//addNewWorkPlaceApprovalVisitDateLog(workplaceapproval, sessionUser, "System Generated: First Entry", true, null, workplaceapproval.getReviewDate(), null);
		// emails to be added
		// close task for the session user and create a new one with the due date as the new date
		sendReviewDateEmail(workplaceapproval, sessionUser);
	}


	/**
	 * Deletes WorkPlaceApprovalSites and all documents linked to it
	 * @param entity
	 * @throws Exception
	 */
	public void deleteWorkPlaceApprovalSites(WorkPlaceApprovalSites entity, Users sessionUser) throws Exception{
		entity.setDeleteUser(sessionUser);
		create(entity);
		List<IDataEntity> deleteList = new ArrayList<>();
		deleteList.add(entity);
		if (deleteList.size() != 0) {
			dao.deleteBatch(deleteList);
		}
	}
	
	/**
	 * Deletes WorkPlaceApprovalTrades and all documents linked to it
	 * @param entity
	 * @throws Exception
	 */
	public void deleteWorkPlaceApprovalTrade(WorkPlaceApprovalTrades entity, Users sessionUser) throws Exception{
		entity.setDeleteUser(sessionUser);
		create(entity);
		List<IDataEntity> deleteList = new ArrayList<>();
		deleteList.add(entity);
		if (deleteList.size() != 0) {
			dao.deleteBatch(deleteList);
		}
	}

	/** Reporting Start */
	
	/**
	 * Populates list of status used on CoursewareDistibution life cycle
	 * @return
	 * @throws Exception
	 */
	
	public List<ApprovalEnum> getStatusUsed(List<ApprovalEnum> selectedStatuses) throws Exception{
		List<ApprovalEnum> statusUsed = new ArrayList<>();
		statusUsed.add(ApprovalEnum.Approved);
		statusUsed.add(ApprovalEnum.Rejected);
		statusUsed.add(ApprovalEnum.PendingSignOff);
		statusUsed.add(ApprovalEnum.PendingApproval);
		statusUsed.add(ApprovalEnum.Withdrawn);
		if (selectedStatuses != null && selectedStatuses.size() != 0) {
			for (ApprovalEnum approvalEnum : selectedStatuses) {
				statusUsed.remove(approvalEnum);
			}
		}
		return statusUsed;
	}
	
	public int checkForResultsForFilterReport(boolean dateFilter, boolean createApproveDatefilter, Date fromDate, Date toDate, 
			boolean filterByStatus, List<ApprovalEnum> statusSelectedList,
			boolean filterTradeQualification, List<Qualification> selectedQualifications, List<OfoCodes> selectedOfoCodes,
			boolean filterByProvince, List<Province> provinceList) throws Exception {
		
		return dao.checkForResultsForFilterReport(dateFilter, createApproveDatefilter, fromDate, toDate, filterByStatus, statusSelectedList, filterTradeQualification, selectedQualifications, selectedOfoCodes, filterByProvince, provinceList);
		
	}
	
	public String validateParamaters(boolean dateFilter, boolean createApproveDatefilter, Date fromDate, Date toDate,  
			boolean filterByStatus, List<ApprovalEnum> statusSelectedList, 
			boolean filterTradeQualification, List<Qualification> selectedQualifications, List<OfoCodes> selectedOfoCodes,
			boolean filterByProvince, List<Province> provinceList)  throws Exception{
		
		String error = "";
		// date filter check
		if (dateFilter) {
			if (fromDate == null) {
				error += " Provide From Date";
			}
			if (toDate == null) {
				if (error == "") {
					error += " Provide From To Date";
				}else {
					error += ", Provide From Date";
				}	
			}
		}
		// status filter check
		if (filterByStatus) {
			if (statusSelectedList == null || statusSelectedList.size() == 0) {
				if (error == "") {
					error += " Provide Atleast One Filter Status";
				}else {
					error += ", Provide Atleast One Filter Status";
				}	
			}
		}
		// trade qualification
		if (filterTradeQualification) {
			if (selectedQualifications.size() == 0 && selectedOfoCodes.size() == 0) {
				if (error == "") {
					error += " Provide Atleast One Filter By Qualification or Trade";
				}else {
					error += ", Provide Atleast One Filter By Qualification or Trade";
				}
			}
		}
		if (filterByProvince) {
			if (provinceList.size() == 0) {
				if (error == "") {
					error += " Provide Atleast One Province Filter";
				}else {
					error += ", Provide Atleast One Province Filter";
				}
			}
		}
		if (error == "") {
			if (checkForResultsForFilterReport(dateFilter, createApproveDatefilter, fromDate, toDate, filterByStatus, statusSelectedList, filterTradeQualification, selectedQualifications, selectedOfoCodes, filterByProvince, provinceList) == 0) {
				error += " No Results Found: Select Differenet Filter Criteria";
			}
		}
		return error;
	}
	
	public void witdrawWorkPlaceApproval(WorkPlaceApproval workPlaceApproval, Users createUser, Tasks previousTask, Doc doc) throws Exception {
		
		Users user = getCLO(workPlaceApproval.getCompany());
		
		if(user == null) {
			throw new Exception("No Client Liaison Office Assigned To Company Region");
		}
		List<Users>users = new ArrayList<>();
		users.add(user);
		doc.setVersionNo(1);
		DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), createUser);
		//DocService.instance().create(doc);
		workPlaceApproval.setOrigionalApprovalEnum(workPlaceApproval.getApprovalEnum());
		workPlaceApproval.setApprovalEnum(ApprovalEnum.PendingWithdrawal);
		dao.update(workPlaceApproval);
		String desc = "";
		List<Tasks>tasks = TasksService.instance().findTasksByTypeAndKey( ConfigDocProcessEnum.WORKPLACE_APPROVAL, workPlaceApproval.getClass().getName(), workPlaceApproval.getId());
		for(Tasks t:tasks) {
			TasksService.instance().completeTask(t);
		}
		TasksService.instance().findFirstInProcessAndCreateTask(desc, createUser, workPlaceApproval.getId(), workPlaceApproval.getClass().getName(), true, ConfigDocProcessEnum.WITHDRAW_WORKPLACE_APPROVAL, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), users);
		sendwithdrawalAcknoledgementEmail(workPlaceApproval, createUser);
	}
	
	public void createwithdarawalWorkPlaceApproval(WorkPlaceApproval workPlaceApproval, Users createUser, Tasks previousTask, Doc doc) throws Exception {
		//Users user = getCLO(workPlaceApproval.getCompany());
		List<Users> usersList = getQualityAssures(workPlaceApproval.getCompany());
		if(CollectionUtils.isEmpty(usersList)) {
			throw new Exception("No Client Liaison Office Assigned To Company Region");
		}
		if(doc!=null) {
			DocService.instance().create(doc);
		}
		
		workPlaceApproval.setApprovalEnum(ApprovalEnum.PendingWithdrawal);
		dao.update(workPlaceApproval);
		String desc = "";
		TasksService.instance().completeTask(previousTask);		
		TasksService.instance().findFirstInProcessAndCreateTask(desc, createUser, workPlaceApproval.getId(), workPlaceApproval.getClass().getName(), true, ConfigDocProcessEnum.WITHDRAW_WORKPLACE_APPROVAL, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), usersList);
	}
	
	
	public void rejectWorkPlaceApprovalWithdrawal(WorkPlaceApproval workPlaceApproval, Users user, Tasks tasks,List<RejectReasons> rejectReasons) throws Exception {
		//workPlaceApproval.setApprovalEnum(ApprovalEnum.Rejected);
		update(workPlaceApproval);	
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(workPlaceApproval.getId(), WorkPlaceApproval.class.getName(), rejectReasons, user, ConfigDocProcessEnum.WITHDRAW_WORKPLACE_APPROVAL);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		
		if(tasks.getFirstInProcess()) {
			//TasksService.instance().completeTask(tasks);
			String emailMessage = "The request to withdraw a workplace approval application for "+workPlaceApproval.getCompany().getCompanyName()+"("+workPlaceApproval.getCompany().getLevyNumber()+") has not been approved. Please review.";
			String subject = "Workplace Withdrawal application";
			String description = "The request to withdraw a workplace approval application for "+workPlaceApproval.getCompany().getCompanyName()+"("+workPlaceApproval.getCompany().getLevyNumber()+") has not been approved. Please review.";
			List<Users> users = new ArrayList<>();
			SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(workPlaceApproval.getCompany());		
			Users createUser = sdfCom.getSdf();
			if(createUser !=null) {
				users.add(createUser);
				TasksService.instance().createTask(WorkPlaceApproval.class.getName(), null, emailMessage, subject, description, user, workPlaceApproval.getId(), true, true, tasks, users, ConfigDocProcessEnum.WITHDRAW_WORKPLACE_APPROVAL, null);
			}else {
				throw new Exception("Error Please Contact Administrator");
			}
			
		}else {
			List<Users> users = new ArrayList<>();
			users.add(getCLO(workPlaceApproval.getCompany()));
			TasksService.instance().findPreviousInProcessAndCreateTask("", user, workPlaceApproval.getId(), WorkPlaceApproval.class.getName(), true, tasks, null, users);
		}
		sendWithdrawalRejectEmail(workPlaceApproval, user, rejectReasons);		
	}
	
	public void finalRejectWorkPlaceApprovalWithdrawal(WorkPlaceApproval workPlaceApproval, Users user, Tasks tasks,List<RejectReasons> rejectReasons) throws Exception {
		workPlaceApproval.setApprovalEnum(workPlaceApproval.getOrigionalApprovalEnum());
		update(workPlaceApproval);	
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(workPlaceApproval.getId(), WorkPlaceApproval.class.getName(), rejectReasons, user, ConfigDocProcessEnum.WITHDRAW_WORKPLACE_APPROVAL);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		TasksService.instance().completeTask(tasks);	
		sendWithdrawalRejectEmail(workPlaceApproval, user, rejectReasons);
	}
	
	public void approveWorkPlaceApprovalWithdrawal(WorkPlaceApproval workPlaceApproval, Users user, Tasks tasks) throws Exception {
		List<Users>users = new ArrayList<>();
		Users u = getCRM(workPlaceApproval.getCompany());
		if(u == null) {
			throw new Exception("No CRM found, please contact you administrator");
		}
		users.add(u);
		TasksService.instance().findNextInProcessAndCreateTask("", user, workPlaceApproval.getId(), WorkPlaceApproval.class.getName(), true, tasks, null, users);
	}
	
	public void finalApproveWorkPlaceApprovalWithdrawal(WorkPlaceApproval workPlaceApproval, Users user, Tasks tasks) throws Exception {
		workPlaceApproval.setApprovalEnum(ApprovalEnum.Withdrawn);
		workPlaceApproval.setApprovalDate(getSynchronizedDate());
		create(workPlaceApproval);
		TasksService.instance().completeTask(tasks);
		sendWithdrawalApprovalEmail(workPlaceApproval, user);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> workplaceApprovalReportByFilters(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters,
			boolean dateFilter, boolean createApproveDatefilter, Date fromDate, Date toDate, boolean filterByStatus, List<ApprovalEnum> statusSelectedList,
			boolean filterTradeQualification, List<Qualification> selectedQualifications, List<OfoCodes> selectedOfoCodes,
			boolean filterByProvince, List<Province> provinceList) throws Exception {
		
		String hql = "select o from WorkPlaceApproval o ";
		
		if (filterByProvince) {
			hql += " left join o.company c left join c.residentialAddress cr left join cr.municipality crm left join crm.province crmp ";
			hql += " left join o.sites s left join s.registeredAddress sr left join sr.municipality srm left join srm.province srmp ";
		}

		if (dateFilter) {
			if (createApproveDatefilter) {
				hql += " where date(o.createDate) between date(:fromDate) and date(:toDate) ";
			} else {
				hql += " where date(o.approvalDate) between date(:fromDate) and date(:toDate) ";
			}
			filters.put("fromDate", fromDate);
			filters.put("toDate", toDate);
		}
		if (filterByStatus) {
			if (dateFilter) {
				hql += " and ";
			} else {
				hql += " where ";
			}
			hql += " ( ";
			int count = 1;
			for (ApprovalEnum approvalEnum : statusSelectedList) {
				if (count == statusSelectedList.size()) {
					hql += " o.approvalEnum = :selectedStatus" + count + " ";
				} else {
					hql += " o.approvalEnum = :selectedStatus" + count + " or ";
				}
				filters.put("selectedStatus" + count, approvalEnum);
				count++;
			}
			hql += " ) ";
		}
		if (filterTradeQualification) {
			if (dateFilter || filterByStatus) {
				hql += " and ";
			} else {
				hql += " where ";
			}
			hql += " ( ";
			int count = 1;
			for (Qualification qualificationSelected : selectedQualifications) {
				if (count == selectedQualifications.size()) {
					hql += " o.qualification.id = :qualificationId" + count + " ";
				} else {
					hql += " o.qualification.id = :qualificationId" + count + " or ";
				}
				filters.put("qualificationId" + count, qualificationSelected.getId());
				count++;
			}
			if (selectedQualifications.size() != 0 && selectedOfoCodes.size() != 0) {
				hql += " or ";
			}
			count = 1;
			for (OfoCodes ofoCodes : selectedOfoCodes) {
				if (count == selectedOfoCodes.size()) {
					hql += " o.ofoCodes.id = :ofoCodesId" + count + " ";
				} else {
					hql += " o.ofoCodes.id = :ofoCodesId" + count + " or ";
				}
				filters.put("ofoCodesId" + count, ofoCodes.getId());
				count++;
			}
			hql += " ) ";
		}
		if (filterByProvince) {
			if (dateFilter || filterByStatus || filterTradeQualification) {
				hql += " and ";
			} else {
				hql += " where ";
			}
			hql += " ( ";
			int count = 1;
			String provinces = "(";
			for (Province provinceSelected : provinceList) {
				if (count == provinceList.size()) {
					provinces += ":provinceId" + count + ")";
				} else {
					provinces += ":provinceId" + count + ",";
				}
				filters.put("provinceId" + count, provinceSelected.getId());
				count++;
			}
			hql += "CASE WHEN o.sites IS NULL THEN crmp.id ELSE srmp.id END in " + provinces;
			hql += " ) ";
		}
		return (List<WorkPlaceApproval>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countWorkplaceApprovalReportByFilters(Map<String, Object> filters, boolean dateFilter, boolean createApproveDatefilter, Date fromDate, Date toDate, 
			boolean filterByStatus, List<ApprovalEnum> statusSelectedList,
			boolean filterTradeQualification, List<Qualification> selectedQualifications, List<OfoCodes> selectedOfoCodes,
			boolean filterByProvince, List<Province> provinceList) throws Exception {
		
		String hql = "select count(o) from WorkPlaceApproval o ";
		
		if (filterByProvince) {
			hql += " left join o.company c left join c.residentialAddress cr left join cr.municipality crm left join crm.province crmp ";
			hql += " left join o.sites s left join s.registeredAddress sr left join sr.municipality srm left join srm.province srmp ";
		}
		if (dateFilter) {
			if (createApproveDatefilter) {
				hql += " where date(o.createDate) between date(:fromDate) and date(:toDate) ";
			} else {
				hql += " where date(o.approvalDate) between date(:fromDate) and date(:toDate) ";
			}
		}
		if (filterByStatus) {
			if (dateFilter) {
				hql += " and ";
			} else {
				hql += " where ";
			}
			hql += " ( ";
			int count = 1;
			for (ApprovalEnum approvalEnum : statusSelectedList) {
				if (count == statusSelectedList.size()) {
					hql += " o.approvalEnum = :selectedStatus" + count + " ";
				} else {
					hql += " o.approvalEnum = :selectedStatus" + count + " or ";
				}
				count++;
			}
			hql += " ) ";
		}
		if (filterTradeQualification) {
			if (dateFilter || filterByStatus) {
				hql += " and ";
			} else {
				hql += " where ";
			}
			hql += " ( ";
			int count = 1;
			for (Qualification qualificationSelected : selectedQualifications) {
				if (count == selectedQualifications.size()) {
					hql += " o.qualification.id = :qualificationId" + count + " ";
				} else {
					hql += " o.qualification.id = :qualificationId" + count + " or ";
				}
				count++;
			}
			if (selectedQualifications.size() != 0 && selectedOfoCodes.size() != 0) {
				hql += " or ";
			}
			count = 1;
			for (OfoCodes ofoCodes : selectedOfoCodes) {
				if (count == selectedOfoCodes.size()) {
					hql += " o.ofoCodes.id = :ofoCodesId" + count + " ";
				} else {
					hql += " o.ofoCodes.id = :ofoCodesId" + count + " or ";
				}
				count++;
			}
			hql += " ) ";
		}
		if (filterByProvince) {
			if (dateFilter || filterByStatus || filterTradeQualification) {
				hql += " and ";
			} else {
				hql += " where ";
			}
			hql += " ( ";
			int count = 1;
			String provinces = "(";
			for (Province provinceSelected : provinceList) {
				if (count == provinceList.size()) {
					provinces += ":provinceId" + count + ")";
				} else {
					provinces += ":provinceId" + count + ",";
				}
				count++;
			}
			hql += "CASE WHEN o.sites IS NULL THEN crmp.id ELSE srmp.id END in " + provinces;
			hql += " ) ";
		}
		return dao.countWhere(filters, hql);
	}
	
	/** Reporting End */
	public void sendRejectEmail(WorkPlaceApproval registration, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception
	{
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		RegionTown rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		String sitename = "";
		String rejectReason = convertToHTML(rejectReasonsList);
		Address address = registration.getCompany().getResidentialAddress();
		if(address !=null && address.getId()!=null) {
			address = AddressService.instance().findByKey(registration.getCompany().getPostalAddress().getId());
		}else {
			address = new Address();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		
		params.put("user", u);
		params.put("sitename", sitename);
		params.put("company", registration.getCompany());		
		params.put("sitesme", registration);
		params.put("qualifications", rejectReason);
		params.put("address", address);
		params.put("regional_office", r.getDescription());
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasonsList));
		
		AttachmentBean beanAttachment=new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		
		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-003a-WorkplaceApprovalLetter.jasper", params);
		
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("Workplace_Approval.pdf");
		attachmentBeans.add(beanAttachment);
		
		String welcome = "<p>Dear #FirstName# #Surname#,</p>" 
				+ "<p>WORKPLACE APPROVAL APPLICATION FOR #COMPANYNAME# (#ENTITYID#): #SITENAME# </p>" 
				+ "<p>The merSETA regrets to inform you that your workplace approval application has not been granted due to the following: </p>"
				+ "<p>"+rejectReason+"</p>"
				+ "<p>Please do not hesitate to contact the merSETA #RegionalOfficeName# Office for further assistance</p>"
				+ "<p>Yours sincerely,</p>" 
				+ "<p>Manager: Quality Assurance</p>" 
				+ "<br/>";
				welcome = welcome.replaceAll("#title#", title);
				welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
				welcome = welcome.replaceAll("#Surname#", u.getLastName());
				welcome = welcome.replaceAll("#COMPANYNAME#", registration.getCompany().getCompanyName());	
				welcome = welcome.replaceAll("#ENTITYID#", registration.getCompany().getLevyNumber());
				welcome = welcome.replaceAll("#SITENAME#", sitename);
				welcome = welcome.replaceAll("#RegionalOfficeName#", r.getDescription());
				//GenericUtility.sendMail(u.getEmail(), "WORKPLACE APPROVAL APPLICATION ", welcome, null);
				//GenericUtility.sendMail(u.getEmail(), "WORKPLACE APPROVAL APPLICATION ", welcome, null);
				GenericUtility.sendMailWithAttachementTempWithLog(u.getEmail(), "WORKPLACE APPROVAL APPLICATION OUTCOME", welcome, attachmentBeans, null);
	}
	
	/** Reporting End */
	public void managerSendRejectEmail(WorkPlaceApproval registration, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception
	{
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		RegionTown rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		String sitename = "";
		String rejectReason = convertToHTML(rejectReasonsList);
		Address address = registration.getCompany().getResidentialAddress();
		if(address !=null && address.getId()!=null) {
			address = AddressService.instance().findByKey(registration.getCompany().getPostalAddress().getId());
		}else {
			address = new Address();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		
		params.put("user", u);
		params.put("sitename", sitename);
		params.put("company", registration.getCompany());		
		params.put("sitesme", registration);
		params.put("qualifications", rejectReason);
		params.put("address", address);
		params.put("regional_office", r.getDescription());
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasonsList));
		
		AttachmentBean beanAttachment=new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		
		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-003a-WorkplaceApprovalLetter.jasper", params);
		
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("Workplace_Approval.pdf");
		attachmentBeans.add(beanAttachment);
		
		String welcome = "<p>Dear #FirstName# #Surname#,</p>" 
				+ "<p>WORKPLACE APPROVAL APPLICATION FOR #COMPANYNAME# (#ENTITYID#): #SITENAME# </p>" 
				+ "<p>The merSETA regrets to inform you that your workplace approval application has not been granted due to the following: </p>"
				+ "<p>"+rejectReason+"</p>"
				+ "<p>"+registration.getRejectMessage()+"</p>"
				+ "<p>Please do not hesitate to contact the merSETA #RegionalOfficeName# Office for further assistance</p>"
				+ "<p>Yours sincerely,</p>" 
				+ "<p>Manager: Quality Assurance</p>" 
				+ "<br/>";
				welcome = welcome.replaceAll("#title#", title);
				welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
				welcome = welcome.replaceAll("#Surname#", u.getLastName());
				welcome = welcome.replaceAll("#COMPANYNAME#", registration.getCompany().getCompanyName());	
				welcome = welcome.replaceAll("#ENTITYID#", registration.getCompany().getLevyNumber());
				welcome = welcome.replaceAll("#SITENAME#", sitename);
				welcome = welcome.replaceAll("#RegionalOfficeName#", r.getDescription());
				//GenericUtility.sendMail(u.getEmail(), "WORKPLACE APPROVAL APPLICATION ", welcome, null);
				//GenericUtility.sendMail(u.getEmail(), "WORKPLACE APPROVAL APPLICATION ", welcome, null);
				GenericUtility.sendMailWithAttachementTempWithLog(u.getEmail(), "WORKPLACE APPROVAL APPLICATION OUTCOME", welcome, attachmentBeans, null);
	}
	
	public void sendAcknoledgementEmail(Company company, Qualification qualification, OfoCodes ofoCodes, Sites site, Users u) throws Exception {	
		String sitename = "";
		//RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		//Region r = regionService.findByKey(rt.getRegion().getId());		
		if(site == null){
			sitename= company.getCompanyName();
		}
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String qualifications = "";
		if(qualification !=null){
			qualifications = convertSmeQualificationToHTML(qualification);
		}else if(ofoCodes !=null){
			qualifications = convertOfoCodeToHTML(ofoCodes);
		}
				
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" 
						+ "<p>WORKPLACE APPROVAL APPLICATION FOR #COMPANYNAME# (#ENTITYID#): #SITENAME#.</p>" 
						+ "<p>The merSETA acknowledges receipt of the application for Workplace Approval for the following: </p>"
						+ qualifications
						+ "<p>Kindly be advised that it may take up to 30 working days to process the application. </p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>Manager: Quality Assurance</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", company.getCompanyName());	
		welcome = welcome.replaceAll("#ENTITYID#", company.getLevyNumber());
		//welcome = welcome.replaceAll("#Region#", r.getDescription());
		welcome = welcome.replaceAll("#SITENAME#", sitename);
		//welcome = welcome.replaceAll("#SubmissionDate#",GenericUtility.sdf7.format(coursewaredistibution.getCreateDate()));				
		GenericUtility.sendMail(u.getEmail(), "ACKNOWLEDGEMENT OF WORKPLACE APPROVAL APPLICATION", welcome, null);
	}
	
	public void sendwithdrawalAcknoledgementEmail(WorkPlaceApproval registration,Users u) throws Exception {	
		String sitename = "";
		//RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		//Region r = regionService.findByKey(rt.getRegion().getId());		
		if(registration.getSites() == null){
			sitename= registration.getCompany().getCompanyName();
		}
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String qualifications = "";
		if(registration.getQualification() !=null){
			qualifications = convertSmeQualificationToHTML(registration.getQualification());
		}
				
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" 
						+ "<p>ACKNOWLEDGEMENT OF REQUEST TO WITHDRAW WORKPLACE APPROVAL APPLICATION FOR #COMPANYNAME# (#ENTITYID#): #SITENAME#.</p>" 
						+ "<p>The merSETA acknowledges receipt of the request to withdraw a workplace approval application against the following qualification(s):</p>"
						+ qualifications
						+ "<p>Kindly be advised that it may take up to five (5) working days to process the application. </p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>merSETA Client Services</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", registration.getCompany().getCompanyName());	
		welcome = welcome.replaceAll("#ENTITYID#", registration.getCompany().getLevyNumber());
		//welcome = welcome.replaceAll("#Region#", r.getDescription());
		welcome = welcome.replaceAll("#SITENAME#", sitename);
		//welcome = welcome.replaceAll("#SubmissionDate#",GenericUtility.sdf7.format(coursewaredistibution.getCreateDate()));				
		GenericUtility.sendMail(u.getEmail(), "ACKNOWLEDGEMENT OF REQUEST TO WITHDRAW WORKPLACE APPROVAL APPLICATION", welcome, null);
	}
	
	public void sendWithdrawalApprovalEmail(WorkPlaceApproval registration,Users user) throws Exception {	
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		String sitename = "";
		RegionTown rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());		
		if(registration.getSites() == null){
			sitename= registration.getCompany().getCompanyName();
		}
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String qualifications = "";
		if(registration.getQualification() !=null){
			qualifications = convertSmeQualificationToHTML(registration.getQualification());
		}
				
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" 
						+ "<p>APPROVAL OF REQUEST TO WITHDRAW A WORKPLACE APPROVAL APPLICATION FOR  #COMPANYNAME# (#ENTITYID#): #SITENAME#.</p>" 
						+ "<p>Please be advised that the merSETA has approved the request to withdraw a workplace approval for the following qualification:</p>"
						+ qualifications
						+ "<p>Please do not hesitate to contact the merSETA #Region# Office for further assistance</p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>merSETA Client Services</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", registration.getCompany().getCompanyName());	
		welcome = welcome.replaceAll("#ENTITYID#", registration.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#Region#", r.getDescription());
		welcome = welcome.replaceAll("#SITENAME#", sitename);
		//welcome = welcome.replaceAll("#SubmissionDate#",GenericUtility.sdf7.format(coursewaredistibution.getCreateDate()));				
		GenericUtility.sendMail(u.getEmail(), "APPROVAL OF REQUEST TO WITHDRAW A WORKPLACE APPROVAL APPLICATION", welcome, null);
	}
	
	public void sendWithdrawalRejectEmail(WorkPlaceApproval registration,Users user, List<RejectReasons> rejectReasonsList) throws Exception {	
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		
		String sitename = "";
		RegionTown rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());		
		if(registration.getSites() == null){
			sitename= registration.getCompany().getCompanyName();
		}
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		String qualifications = "";
		if(registration.getQualification() !=null){
			qualifications = convertSmeQualificationToHTML(registration.getQualification());
		}
		String rejectReason = convertToHTML(rejectReasonsList);		
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" 
						+ "<p>NON-APPROVAL OF REQUEST TO WITHDRAW A WORKPLACE APPROVAL APPLICATION  FOR  #COMPANYNAME# (#ENTITYID#): #SITENAME#.</p>" 
						+ "<p>Please be advised that the merSETA has not approved the request to withdraw a workplace approval for the following qualification </p>"
						+ qualifications
						+ "<p>for the following reason:</p>"
						+ rejectReason
						+ "<p>Please do not hesitate to contact the merSETA #Region# Office for further assistance</p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>merSETA Client Services</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", registration.getCompany().getCompanyName());	
		welcome = welcome.replaceAll("#ENTITYID#", registration.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#Region#", r.getDescription());
		welcome = welcome.replaceAll("#SITENAME#", sitename);
		//welcome = welcome.replaceAll("#SubmissionDate#",GenericUtility.sdf7.format(coursewaredistibution.getCreateDate()));				
		GenericUtility.sendMail(u.getEmail(), "NON-APPROVAL OF REQUEST TO WITHDRAW A WORKPLACE APPROVAL APPLICATION ", welcome, null);
	}
	
	public void sendApprovalEmail(WorkPlaceApproval registration, Tasks tasks) throws Exception {	
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		
		String idorpassport = "";
		String title ="";
		if(u.getTitle()!=null) {title = u.getTitle().getDescription();}
		
		String qualification = workplaceQualification(registration);
		
		if(u.getPassportNumber() != null || !u.getPassportNumber().matches("") ){
			idorpassport = u.getPassportNumber();
		}else{
			idorpassport = u.getRsaIDNumber();
		}
		
		Boolean showList= false;
		List<WorkPlaceApprovalToolList>workPlaceApprovalToolList = new ArrayList<>();;
		if(registration.getQualification()!=null && registration.getQualification().getQualificationtypeid() !=null && registration.getQualification().getQualificationtypeid().equalsIgnoreCase(HAJConstants.TRADE_QUALIFICATION_CODE)) {
			workPlaceApprovalToolList = workPlaceApprovalToolListService.findByworkplaceapproval(registration);
			if(workPlaceApprovalToolList!=null && workPlaceApprovalToolList.size()>0) {
				showList = true;
			}
		}
		
		
		String sitename = "";
		Sites site = registration.getSites();
		if(site !=null){
			sitename = site.getCompanyName();
		}else{
			sitename = registration.getCompany().getCompanyName();
		}
		
		RegionTown rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		Address address = registration.getCompany().getResidentialAddress();
		if(address !=null && address.getId()!=null) {
			address = AddressService.instance().findByKey(registration.getCompany().getPostalAddress().getId());
		}else {
			address = new Address();
		}
		
		if(registration.getCompany() != null && registration.getCompany().getPostalAddress()!=null) {
			registration.getCompany().setPostalAddress(AddressService.instance().findByKey(registration.getCompany().getPostalAddress().getId()));
		}else {
			registration.getCompany().setPostalAddress(AddressService.instance().findByKey(registration.getCompany().getResidentialAddress().getId()));
		}
		
		String qualOrOfocodeTitle = "";
		String qualOrOfocodeCode = "";
		String qualOrOfocodeNqfLevel= "";
		if(registration.getQualification()!=null) {
			qualOrOfocodeTitle = registration.getQualification().getDescription();
			qualOrOfocodeCode = String.valueOf(registration.getQualification().getCode());
			qualOrOfocodeNqfLevel = registration.getQualification().getNqflevelg2description();
		}else if(registration.getOfoCodes()!=null){
			qualOrOfocodeTitle = registration.getOfoCodes().getDescription();
			qualOrOfocodeCode = registration.getOfoCodes().getOfoCodeParent();
			qualOrOfocodeNqfLevel = registration.getOfoCodes().getOfoCode();
		}
		
		List<SitesSme> smeList = dao.findSiteSmeByApproval(registration);
		//List<SitesSme> smeList = SitesSmeService.instance().allSitesSmeByCompanyAndStatus(registration.getCompany(), ApprovalEnum.Approved, true);
		for(SitesSme sitesSme: smeList) {
			SitesSmeService.instance().populateAdditionalInformation1(sitesSme);
		}
		for(SitesSme sitesSme: smeList) {
			if(sitesSme!=null && sitesSme.getId()!=null) {
				sitesSme.setReason(buildString(sitesSme.getSmeQualificationsList()));
			}
		}
		
		List<Signoff> signoffs = signoffService.findByTargetKeyAndClass(registration.getId(), WorkPlaceApproval.class.getName());
		for(Signoff s: signoffs) {
			HostingCompanyEmployees  hostingCompanyEmployees = hostingCompanyEmployeesService.findByUserReturnHostCompany(s.getUser().getId());
			if(hostingCompanyEmployees != null && hostingCompanyEmployees.getId() != null) {
				s.setDisignation(hostingCompanyEmployees.getJobTitle().getDescription());
			}else {
				SDFCompany sdfCompany = sdfCompanyService.findByCompanyAndUser(registration.getCompany(), s.getUser());
				if(sdfCompany !=null && sdfCompany.getId() !=null) {
					s.setDisignation(sdfCompany.getSdfType().getDescription());
				}				
			}
			if(s.getAccept()) {
				s.setStatus("Approved");
			}else if(s.getDispute()) {
				s.setStatus("Not Approved");
			}else {s.setStatus("N/A");}
		}	
		Signoff signoff1 = new Signoff();
		Signoff signoff2 = new Signoff();
		if(signoffs.size()>0) {
			signoff1 = signoffs.get(0);
			if(signoffs.size()>=1) {
				signoff2 = signoffs.get(1);
			}
		}
		 
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		
		String companyRepresentative = u.getFirstName() + " "+ u.getLastName(); 
		String merSETARepresentative = tasks.getActionUser().getFirstName() + " " + tasks.getActionUser().getLastName();
		Date date = registration.getApprovalDate();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		String approvalDate = "";
		if(date !=null) {
			approvalDate = dateFormat.format(date);
		}else {
			approvalDate = dateFormat.format(new Date()); 
		}
		
		params.put("signoff1", signoff1);
		params.put("signoff2", signoff2);		
		params.put("companyActivities", "N/A");
		params.put("merSETARepresentative", merSETARepresentative);
		params.put("companyRepresentative", companyRepresentative);
		params.put("approvalDate", approvalDate);
		params.put("user", u);
		params.put("contact", u);
		params.put("sitename", sitename);
		params.put("company", registration.getCompany());		
		params.put("sitesme", registration);
		params.put("workplaceapproval", registration);
		params.put("qualOrOfocodeTitle", qualOrOfocodeTitle);
		params.put("qualOrOfocodeCode", qualOrOfocodeCode);
		params.put("qualOrOfocodeNqfLevel", qualOrOfocodeNqfLevel);
		//params.put("qualifications", qualifications);
		params.put("address", address);
		params.put("physicaladdress", registration.getCompany().getResidentialAddress());
		params.put("regional_office", r.getDescription());
		//params.put("QualificationList", new JRBeanCollectionDataSource(registration.get));
		params.put("sitesmedatasource", new JRBeanCollectionDataSource(smeList));
		params.put("signoffdatasourse", new JRBeanCollectionDataSource(signoffs));
		params.put("showToolList", showList);
		params.put("toolListDatasource", new JRBeanCollectionDataSource(workPlaceApprovalToolList));
		
		
		AttachmentBean beanAttachment=new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		
		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-003-WorkplaceApproval.jasper", params);
		
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("Workplace_Approval.pdf");
		attachmentBeans.add(beanAttachment);

		byte[] bites2 = JasperService.instance().convertJasperReportToByte("ETQ-TP-054-WorkplaceApproval.jasper", params);
		beanAttachment=new AttachmentBean();
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites2);
		beanAttachment.setFilename("WorkplaceApprovalDetails.pdf");
		attachmentBeans.add(beanAttachment);
		
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" 
						+ "<p>WORKPLACE APPROVAL APPLICATION FOR #COMPANYNAME# (#ENTITYID#): #SITENAME#</p>" 
						+ "<p>The merSETA has pleasure in informing you that your workplace has been granted approval to train in the following: </p>"
						+ qualification
						+ "<p>Should you require any assistance or further information, kindly contact the Client Liaison Officer at the #RegionalOfficeName# Office.</p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>Manager: Quality Assurance</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", registration.getCompany().getCompanyName());	
		welcome = welcome.replaceAll("#ENTITYID#", registration.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);		
		welcome = welcome.replaceAll("#MentorName#", u.getLastName());
		welcome = welcome.replaceAll("#MentorSurname#", u.getLastName());
		welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);
		welcome = welcome.replaceAll("#RegionalOfficeName#", r.getDescription());
		JasperService js=new JasperService();
		
		//js.createReportFromDBtoServletOutputStream("ETQ-TP-003-WorkplaceApproval.jasper", params,"document.pdf");
		//js.createReportFromDBtoServletOutputStream("ETQ-TP-054-WorkplaceApproval.jasper", params,"document1.pdf");
		
		GenericUtility.sendMailWithAttachementTempWithLog(u.getEmail(), "WORKPLACE APPROVAL APPLICATION ", welcome, attachmentBeans, null);
		//GenericUtility.sendMail(u.getEmail(), "Workplace Approval Application", welcome, null);	
	}

	private void sendReviewDateEmail(WorkPlaceApproval registration, Users sessionUser) throws Exception {
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		
		String idorpassport = "";
		String title ="";
		if(u.getTitle()!=null) {title = u.getTitle().getDescription();}
		
		String qualifications = "";
		if(registration.getQualification() !=null){
			qualifications = convertSmeQualificationToHTML(registration.getQualification());
		}else if(registration.getOfoCodes() !=null){
			qualifications = convertOfoCodeToHTML(registration.getOfoCodes());
		}
		
		RegionTown rt = new  RegionTown();
		if(registration.getCompany().getResidentialAddress()!=null && registration.getCompany().getResidentialAddress().getTown()!=null) {
			rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		}
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = new Region();
		if(rt.getRegion()!=null){
			r=regionService.findByKey(rt.getRegion().getId());
		}
		
		Users clo = getCLO(registration.getCompany());
		
		String sitename = "";
		Sites site = registration.getSites();
		if(site !=null){
			sitename = site.getCompanyName();
		}else{
			sitename = registration.getCompany().getCompanyName();
		}
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" 
				+ "<p>The merSETA hereby advises that a site visit has been scheduled to take place at the following location: "
				+ "#SITENAME# on #date# as part of the Workplace Approval application against the following qualification: #qualifications#</p>" 				
				+ "<p>Should there be a change in the scheduled date, please contact the Client Liaison Officer before the visit to schedule a new date.</p>"				
				+ "<p>Please do not hesitate to contact the Regional office for further assistance.</p>"
				+ "<p>Yours sincerely,</p>" 
				+ "<p>#ClientLiaisonOfficerNameAndSurname#: CLO, #RegionName#</p>" 
				+ "<br/>";
				welcome = welcome.replaceAll("#date#", GenericUtility.sdf7.format(registration.getReviewDate()));
				welcome = welcome.replaceAll("#title#", title);
				welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
				welcome = welcome.replaceAll("#Surname#", u.getLastName());
				welcome = welcome.replaceAll("#COMPANYNAME#", registration.getCompany().getCompanyName());	
				welcome = welcome.replaceAll("#ENTITYID#", registration.getCompany().getLevyNumber());
				welcome = welcome.replaceAll("#SITENAME#", sitename);		
				welcome = welcome.replaceAll("#MentorName#", u.getLastName());
				welcome = welcome.replaceAll("#MentorSurname#", u.getLastName());
				welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);
				welcome = welcome.replaceAll("#qualifications#", qualifications);
				welcome = welcome.replaceAll("#ClientLiaisonOfficerNameAndSurname#", clo.getFirstName() + " " + clo.getLastName());
				welcome = welcome.replaceAll("#RegionName#", r.getDescription());
				
				GenericUtility.sendMail(u.getEmail(), "WORKPLACE APPROVAL SITE VISIT ", welcome, null);		
	}
	
	private void sendUpdateReviewDateEmail(WorkPlaceApproval registration, Users sessionUser) throws Exception {
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		
		String idorpassport = "";
		String title ="";
		if(u.getTitle()!=null) {title = u.getTitle().getDescription();}
		
		String qualifications = "";
		if(registration.getQualification() !=null){
			qualifications = convertSmeQualificationToHTML(registration.getQualification());
		}else if(registration.getOfoCodes() !=null){
			qualifications = convertOfoCodeToHTML(registration.getOfoCodes());
		}
		
		RegionTown rt = new  RegionTown();
		if(registration.getCompany().getResidentialAddress()!=null && registration.getCompany().getResidentialAddress().getTown()!=null) {
			rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		}
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = new Region();
		if(rt.getRegion()!=null){
			r=regionService.findByKey(rt.getRegion().getId());
		}
		
		Users clo = getCLO(registration.getCompany());
		
		String sitename = "";
		Sites site = registration.getSites();
		if(site !=null){
			sitename = site.getCompanyName();
		}else{
			sitename = registration.getCompany().getCompanyName();
		}
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" 
				+ "<p>The merSETA hereby advises that a site visit has been Re-scheduled to take place at the following location: "
				+ "#SITENAME# on #date# as part of the Workplace Approval application against the following qualification: #qualifications#</p>" 				
				+ "<p>Should there be a change in the scheduled date, please contact the Client Liaison Officer before the visit to schedule a new date.</p>"				
				+ "<p>Please do not hesitate to contact the Regional office for further assistance.</p>"
				+ "<p>Yours sincerely,</p>" 
				+ "<p>#ClientLiaisonOfficerNameAndSurname#: CLO, #RegionName#</p>" 
				+ "<br/>";
				welcome = welcome.replaceAll("#date#", GenericUtility.sdf7.format(registration.getReviewDate()));
				welcome = welcome.replaceAll("#title#", title);
				welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
				welcome = welcome.replaceAll("#Surname#", u.getLastName());
				welcome = welcome.replaceAll("#COMPANYNAME#", registration.getCompany().getCompanyName());	
				welcome = welcome.replaceAll("#ENTITYID#", registration.getCompany().getLevyNumber());
				welcome = welcome.replaceAll("#SITENAME#", sitename);		
				welcome = welcome.replaceAll("#MentorName#", u.getLastName());
				welcome = welcome.replaceAll("#MentorSurname#", u.getLastName());
				welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);
				welcome = welcome.replaceAll("#qualifications#", qualifications);
				welcome = welcome.replaceAll("#ClientLiaisonOfficerNameAndSurname#", clo.getFirstName() + " " + clo.getLastName());
				welcome = welcome.replaceAll("#RegionName#", r.getDescription());
				
				GenericUtility.sendMail(u.getEmail(), "WORKPLACE APPROVAL SITE VISIT ", welcome, null);		
	}
	
	private String workplaceQualification(WorkPlaceApproval registration) {
		String qualValue = "";
		if(registration.getOfoCodes()!=null){
			qualValue = convertOfoCodeToHTML(registration.getOfoCodes());
		}else if(registration.getQualification()!=null){
			qualValue = convertSmeQualificationToHTML(registration.getQualification());
		}
		return qualValue;
	}

	private String convertSmeQualificationToHTML(Qualification qualification) {
		String ofocode = "";
		if(qualification!=null && qualification.getCode() != null) {
			ofocode = qualification.getCode().toString();
		}
		String sb ="<ul>"+"<li>(" +ofocode+")  "+qualification.getDescription() +"</li>"+"</ul>"; 
		return sb;
	}
	
	private String convertOfoCodeToHTML(OfoCodes qualification) {
		String ofocode = "";
		if(qualification!=null && qualification.getOfoCode() != null) {
			ofocode = qualification.getOfoCode();
		}
		String sb ="<ul>"+"<li>(" +ofocode+")  "+qualification.getDescription() +"</li>"+"</ul>"; 
		return sb;
	}

	public String convertToHTML(List<RejectReasons> rejectReasons){		
		String sb ="<ul>"; 
		for (RejectReasons r: rejectReasons){
			sb +="<li>"+r.getDescription() +"</li>";
		}
		sb +="</ul>"; 
		return sb;
	}
	
	public String convertSmeQualificationsToHTML(List<SmeQualifications> smeQualificationsList){		
		String sb ="<ul>"; 
		for (SmeQualifications smeq: smeQualificationsList){
			sb +="<li>"+smeq.getQualification().getCode() + "  "+smeq.getQualification().getDescription() +"</li>";
		}
		sb +="</ul>"; 
		return sb;
	}
	
	public String buildString(List<SmeQualifications> smeQualificationsList) {
		String reason = ""; 
		if(smeQualificationsList.size() > 0) {		  
		  int size = 0; 
		  for(SmeQualifications child:smeQualificationsList) { 
			  size+=1;
			  if(child.getQualification().getCode() != null) {
				  reason+="("+child.getQualification().getCode()+")";
			  }
			  reason+=child.getQualification().getDescription(); 
			  if(size ==smeQualificationsList.size()) { 
				  reason += "."; 
			  } else { 
				  reason += ", "; 
			  } 
		  } 
		}
		return reason;
	}
	
	public Users getCLO(Company company) throws Exception {
		Users cloUser = new Users();
		if(company.getResidentialAddress() != null && company.getResidentialAddress().getTown()!=null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			cloUser = rt.getClo().getUsers();
		}
		return cloUser;
	}
	
	public Users getCRM(Company company) throws Exception {
		Users crmUser = new Users();
		if(company.getResidentialAddress() != null && company.getResidentialAddress().getTown()!=null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			crmUser = rt.getCrm().getUsers();
		}
		return crmUser;
	}

	public List<Users> getQualityAssures(Company company) throws Exception {
		List<Users> usersList = null;
		if(company.getResidentialAddress() != null && company.getResidentialAddress().getTown()!=null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			usersList = dao.findUserByRegionAndRole(HAJConstants.QUALITY_ASSUROR_ROLE_ID, rt.getRegion().getId());
		}
		return usersList;
	}
	
	public String generateCompanyWorkPlaceApprovalNumber(Date date, WorkPlaceApproval registration) throws Exception{
		//17/WPA/LevyNumber/19 
		DateFormat dateFormat = new SimpleDateFormat("yy");  
		String yearFormat = dateFormat.format(date);		
		String desc = "WPA/";
		String wpaNumber = "";
		String mersetaCode = "17/"; // merseta code
		String year = "/"+yearFormat;  // year [build in year identifier for future proof]
		wpaNumber = mersetaCode+ desc + registration.getCompany().getLevyNumber() + year;
		return wpaNumber;
	}
	
	
	public String countAllWpaWhereWpaNumberAplliedForCompany(Date date) throws Exception{
		CompanyService companyService = new CompanyService();
		//17/WPA/0000001/19 
		DateFormat dateFormat = new SimpleDateFormat("yy");  
		String yearFormat = dateFormat.format(date);		
		String desc = "WPA/";
		String wpaNumber = "";
		String mersetaCode = "17/"; // merseta code
		int number = 1000000 + (dao.countAllCompanyWpaWhereWpaNumberApllied() + 1); // number for company learner
		String year = "/"+yearFormat;  // year [build in year identifier for future proof]
		wpaNumber = mersetaCode+ desc + number + year;
		return wpaNumber;
	}
	
	public List<WorkPlaceApproval> findByStartAndEndDate(Date start) {
		return dao.findByStartAndEndDate(start);
	}

	public List<WorkPlaceApproval> findByStartAndEndDate(Date start, Users activeUser) {
		return dao.findByStartAndEndDate(start, activeUser.getId());
	}
	
	public void sendApprovalEmailTest(WorkPlaceApproval registration, Tasks tasks) throws Exception {	
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		
		String idorpassport = "";
		String title ="";
		if(u.getTitle()!=null) {title = u.getTitle().getDescription();}
		
		String qualification = workplaceQualification(registration);
		
		if(u.getPassportNumber() != null || !u.getPassportNumber().matches("") ){
			idorpassport = u.getPassportNumber();
		}else{
			idorpassport = u.getRsaIDNumber();
		}
		
		String sitename = "";
		Sites site = registration.getSites();
		if(site !=null){
			sitename = site.getCompanyName();
		}else{
			sitename = registration.getCompany().getCompanyName();
		}
		
		RegionTown rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		Address address = u.getPostalAddress();
		if(address !=null && address.getId()!=null) {
			address = u.getPostalAddress();
		}else{
			address = u.getResidentialAddress();
		}
		
		if(registration.getCompany() != null && registration.getCompany().getPostalAddress()!=null) {
			registration.getCompany().setPostalAddress(AddressService.instance().findByKey(registration.getCompany().getPostalAddress().getId()));
		}else {
			registration.getCompany().setPostalAddress(AddressService.instance().findByKey(registration.getCompany().getResidentialAddress().getId()));
		}
		
		String qualOrOfocodeTitle = "";
		String qualOrOfocodeCode = "";
		String qualOrOfocodeNqfLevel= "";
		if(registration.getQualification()!=null) {
			qualOrOfocodeTitle = registration.getQualification().getDescription();
			qualOrOfocodeCode = String.valueOf(registration.getQualification().getCode());
			qualOrOfocodeNqfLevel = registration.getQualification().getNqflevelg2description();
		}else if(registration.getOfoCodes()!=null){
			qualOrOfocodeTitle = registration.getOfoCodes().getDescription();
			qualOrOfocodeCode = registration.getOfoCodes().getOfoCodeParent();
			qualOrOfocodeNqfLevel = registration.getOfoCodes().getOfoCode();
		}
		
		List<SitesSme> smeList = dao.findSiteSmeByApproval(registration);
		//List<SitesSme> smeList = SitesSmeService.instance().allSitesSmeByCompanyAndStatus(registration.getCompany(), ApprovalEnum.Approved, true);
		for(SitesSme sitesSme: smeList) {
			SitesSmeService.instance().populateAdditionalInformation1(sitesSme);
		}
		for(SitesSme sitesSme: smeList) {
			if(sitesSme!=null && sitesSme.getId()!=null) {
				sitesSme.setReason(buildString(sitesSme.getSmeQualificationsList()));
			}
		}
		
		List<Signoff> signoffs = signoffService.findByTargetKeyAndClass(registration.getId(), WorkPlaceApproval.class.getName());
		for(Signoff s: signoffs) {
			HostingCompanyEmployees  hostingCompanyEmployees = hostingCompanyEmployeesService.findByUserReturnHostCompany(s.getUser().getId());
			if(hostingCompanyEmployees != null && hostingCompanyEmployees.getId() != null) {
				s.setDisignation(hostingCompanyEmployees.getJobTitle().getDescription());
			}else {
				SDFCompany sdfCompany = sdfCompanyService.findByCompanyAndUser(registration.getCompany(), s.getUser());
				if(sdfCompany !=null && sdfCompany.getId() !=null) {
					s.setDisignation(sdfCompany.getSdfType().getDescription());
				}				
			}
			if(s.getAccept()) {
				s.setStatus("Approved");
			}else if(s.getDispute()) {
				s.setStatus("Not Approved");
			}else {s.setStatus("N/A");}
		}	
		Signoff signoff1 = new Signoff();
		Signoff signoff2 = new Signoff();
		if(signoffs.size()>0) {
			signoff1 = signoffs.get(0);
			if(signoffs.size()>=1) {
				signoff2 = signoffs.get(1);
			}
		}
		 
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		
		String companyRepresentative = u.getFirstName() + " "+ u.getLastName(); 
		String merSETARepresentative = tasks.getActionUser().getFirstName() + " " + tasks.getActionUser().getLastName();
		Date date = registration.getApprovalDate();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		String approvalDate = "";
		if(date !=null) {
			approvalDate = dateFormat.format(date);
		}else {
			approvalDate = dateFormat.format(new Date()); 
		}
		
		params.put("signoff1", signoff1);
		params.put("signoff2", signoff2);
		
		params.put("companyActivities", "N/A");
		params.put("merSETARepresentative", merSETARepresentative);
		params.put("companyRepresentative", companyRepresentative);
		params.put("approvalDate", approvalDate);
		params.put("user", u);
		params.put("contact", u);
		params.put("sitename", sitename);
		params.put("company", registration.getCompany());		
		params.put("sitesme", registration);
		params.put("workplaceapproval", registration);
		params.put("qualOrOfocodeTitle", qualOrOfocodeTitle);
		params.put("qualOrOfocodeCode", qualOrOfocodeCode);
		params.put("qualOrOfocodeNqfLevel", qualOrOfocodeNqfLevel);
		//params.put("qualifications", qualifications);
		params.put("address", address);
		params.put("physicaladdress", registration.getCompany().getResidentialAddress());
		params.put("regional_office", r.getDescription());
		//params.put("QualificationList", new JRBeanCollectionDataSource(registration.get));
		params.put("sitesmedatasource", new JRBeanCollectionDataSource(smeList));
		params.put("signoffdatasourse", new JRBeanCollectionDataSource(signoffs));
		
		AttachmentBean beanAttachment=new AttachmentBean();
		AttachmentBean beanAttachment2=new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
		
		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-003-WorkplaceApproval.jasper", params);
		
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("Workplace_Approval.pdf");
		attachmentBeans.add(beanAttachment);

		byte[] bites2 = JasperService.instance().convertJasperReportToByte("ETQ-TP-054-WorkplaceApproval.jasper", params);

		beanAttachment2.setExt("pdf");
		beanAttachment2.setFile(bites2);
		beanAttachment2.setFilename("WorkplaceApprovalDetails.pdf");
		attachmentBeans.add(beanAttachment2);
		
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" 
						+ "<p>WORKPLACE APPROVAL APPLICATION FOR #COMPANYNAME# (#ENTITYID#): #SITENAME#</p>" 
						+ "<p>The merSETA has pleasure in informing you that your workplace has been granted approval to train in the following: </p>"
						+ qualification
						+ "<p>Should you require any assistance or further information, kindly contact the Client Liaison Officer at the #RegionalOfficeName# Office.</p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>Manager: Quality Assurance</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", registration.getCompany().getCompanyName());	
		welcome = welcome.replaceAll("#ENTITYID#", registration.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);		
		welcome = welcome.replaceAll("#MentorName#", u.getLastName());
		welcome = welcome.replaceAll("#MentorSurname#", u.getLastName());
		welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);
		welcome = welcome.replaceAll("#RegionalOfficeName#", r.getDescription());
		JasperService js=new JasperService();
		
		//js.createReportFromDBtoServletOutputStream("ETQ-TP-003-WorkplaceApproval.jasper", params,"document.pdf");
		//js.createReportFromDBtoServletOutputStream("ETQ-TP-054-WorkplaceApproval.jasper", params,"document1.pdf");
		
		GenericUtility.sendMailWithAttachementTempWithLog(u.getEmail(), "WORKPLACE APPROVAL APPLICATION ", welcome, attachmentBeans, null);
		//GenericUtility.sendMail(u.getEmail(), "Workplace Approval Application", welcome, null);	
	}
	
	public void downloadWorkPlaceApprovalLetter(WorkPlaceApproval registration) throws Exception {
		JasperService js=new JasperService();
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		
		String idorpassport = "";
		String title ="";
		if(u.getTitle()!=null) {title = u.getTitle().getDescription();}
		
		String qualification = workplaceQualification(registration);
		
		if(u.getPassportNumber() != null || !u.getPassportNumber().matches("") ){
			idorpassport = u.getPassportNumber();
		}else{
			idorpassport = u.getRsaIDNumber();
		}
		
		String sitename = "";
		Sites site = registration.getSites();
		if(site !=null){
			sitename = site.getCompanyName();
		}else{
			sitename = registration.getCompany().getCompanyName();
		}
		Boolean showList= false;
		List<WorkPlaceApprovalToolList>workPlaceApprovalToolList = new ArrayList<>();;
		if(registration.getQualification()!=null && registration.getQualification().getQualificationtypeid() !=null && registration.getQualification().getQualificationtypeid().equalsIgnoreCase(HAJConstants.TRADE_QUALIFICATION_CODE)) {
			workPlaceApprovalToolList = workPlaceApprovalToolListService.findByworkplaceapproval(registration);
			if(workPlaceApprovalToolList!=null && workPlaceApprovalToolList.size()>0) {
				showList = true;
			}
		}
		
		RegionTown rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		Address address = u.getPostalAddress();
		if(address !=null && address.getId()!=null) {
			address = u.getPostalAddress();
		}else{
			address = u.getResidentialAddress();
		}
		
		if(registration.getCompany() != null && registration.getCompany().getPostalAddress()!=null) {
			registration.getCompany().setPostalAddress(AddressService.instance().findByKey(registration.getCompany().getPostalAddress().getId()));
		}else {
			registration.getCompany().setPostalAddress(AddressService.instance().findByKey(registration.getCompany().getResidentialAddress().getId()));
		}
		
		String qualOrOfocodeTitle = "";
		String qualOrOfocodeCode = "";
		String qualOrOfocodeNqfLevel= "";
		if(registration.getQualification()!=null) {
			qualOrOfocodeTitle = registration.getQualification().getDescription();
			qualOrOfocodeCode = String.valueOf(registration.getQualification().getCode());
			qualOrOfocodeNqfLevel = registration.getQualification().getNqflevelg2description();
		}else if(registration.getOfoCodes()!=null){
			qualOrOfocodeTitle = registration.getOfoCodes().getDescription();
			qualOrOfocodeCode = registration.getOfoCodes().getOfoCodeParent();
			qualOrOfocodeNqfLevel = registration.getOfoCodes().getOfoCode();
		}
		
		List<SitesSme> smeList = dao.findSiteSmeByApproval(registration);
		//List<SitesSme> smeList = SitesSmeService.instance().allSitesSmeByCompanyAndStatus(registration.getCompany(), ApprovalEnum.Approved, true);
		for(SitesSme sitesSme: smeList) {
			SitesSmeService.instance().populateAdditionalInformation1(sitesSme);
		}
		for(SitesSme sitesSme: smeList) {
			if(sitesSme!=null && sitesSme.getId()!=null) {
				sitesSme.setReason(buildString(sitesSme.getSmeQualificationsList()));
			}
		}
		
		List<Signoff> signoffs = signoffService.findByTargetKeyAndClass(registration.getId(), WorkPlaceApproval.class.getName());
		for(Signoff s: signoffs) {
			HostingCompanyEmployees  hostingCompanyEmployees = hostingCompanyEmployeesService.findByUserReturnHostCompany(s.getUser().getId());
			if(hostingCompanyEmployees != null && hostingCompanyEmployees.getId() != null) {
				s.setDisignation(hostingCompanyEmployees.getJobTitle().getDescription());
			}else {
				SDFCompany sdfCompany = sdfCompanyService.findByCompanyAndUser(registration.getCompany(), s.getUser());
				if(sdfCompany !=null && sdfCompany.getId() !=null) {
					s.setDisignation(sdfCompany.getSdfType().getDescription());
				}				
			}
			if(s.getAccept()) {
				s.setStatus("Approved");
			}else if(s.getDispute()) {
				s.setStatus("Not Approved");
			}else {s.setStatus("N/A");}
		}	
		Signoff signoff1 = new Signoff();
		Signoff signoff2 = new Signoff();
		if(signoffs.size()>0) {
			signoff1 = signoffs.get(0);
			if(signoffs.size()>=1) {
				signoff2 = signoffs.get(1);
			}
		}
		 
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		
		String companyRepresentative = u.getFirstName() + " "+ u.getLastName(); 
		String merSETARepresentative = registration.getReviewUser().getFirstName() + " " + registration.getReviewUser().getLastName();
		Date date = registration.getApprovalDate();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		String approvalDate = "";
		if(date !=null) {
			approvalDate = dateFormat.format(date);
		}else {
			approvalDate = dateFormat.format(new Date()); 
		}
		
		params.put("signoff1", signoff1);
		params.put("signoff2", signoff2);
		
		params.put("companyActivities", "N/A");
		params.put("merSETARepresentative", merSETARepresentative);
		params.put("companyRepresentative", companyRepresentative);
		params.put("approvalDate", approvalDate);
		params.put("user", u);
		params.put("contact", u);
		params.put("sitename", sitename);
		params.put("company", registration.getCompany());		
		params.put("sitesme", registration);
		params.put("workplaceapproval", registration);
		params.put("qualOrOfocodeTitle", qualOrOfocodeTitle);
		params.put("qualOrOfocodeCode", qualOrOfocodeCode);
		params.put("qualOrOfocodeNqfLevel", qualOrOfocodeNqfLevel);
		//params.put("qualifications", qualifications);
		params.put("address", address);
		params.put("physicaladdress", registration.getCompany().getResidentialAddress());
		params.put("regional_office", r.getDescription());
		//params.put("QualificationList", new JRBeanCollectionDataSource(registration.get));
		params.put("sitesmedatasource", new JRBeanCollectionDataSource(smeList));
		params.put("signoffdatasourse", new JRBeanCollectionDataSource(signoffs));
		params.put("showToolList", showList);
		params.put("toolListDatasource", new JRBeanCollectionDataSource(workPlaceApprovalToolList));

//		byte[] bite = JasperService.instance().convertJasperReportToByte("ETQ-TP-054-WorkplaceApproval.jasper", params);
		
		js.createReportFromDBtoServletOutputStream("ETQ-TP-003-WorkplaceApproval.jasper", params,"ETQ-TP-003-WorkplaceApproval.pdf");
	}

	public void downloadVTwo(WorkPlaceApproval registration) throws Exception {
		JasperService js=new JasperService();
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		
		String idorpassport = "";
		String title ="";
		if(u.getTitle()!=null) {title = u.getTitle().getDescription();}
		
		String qualification = workplaceQualification(registration);
		
		if(u.getPassportNumber() != null || !u.getPassportNumber().matches("") ){
			idorpassport = u.getPassportNumber();
		}else{
			idorpassport = u.getRsaIDNumber();
		}
		
		String sitename = "";
		Sites site = registration.getSites();
		if(site !=null){
			sitename = site.getCompanyName();
		}else{
			sitename = registration.getCompany().getCompanyName();
		}
		Boolean showList= false;
		List<WorkPlaceApprovalToolList>workPlaceApprovalToolList = new ArrayList<>();;
		if(registration.getQualification()!=null && registration.getQualification().getQualificationtypeid() !=null && registration.getQualification().getQualificationtypeid().equalsIgnoreCase(HAJConstants.TRADE_QUALIFICATION_CODE)) {
			workPlaceApprovalToolList = workPlaceApprovalToolListService.findByworkplaceapproval(registration);
			if(workPlaceApprovalToolList!=null && workPlaceApprovalToolList.size()>0) {
				showList = true;
			}
		}
		
		RegionTown rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		Address address = u.getPostalAddress();
		if(address !=null && address.getId()!=null) {
			address = u.getPostalAddress();
		}else{
			address = u.getResidentialAddress();
		}
		
		if(registration.getCompany() != null && registration.getCompany().getPostalAddress()!=null) {
			registration.getCompany().setPostalAddress(AddressService.instance().findByKey(registration.getCompany().getPostalAddress().getId()));
		}else {
			registration.getCompany().setPostalAddress(AddressService.instance().findByKey(registration.getCompany().getResidentialAddress().getId()));
		}
		
		String qualOrOfocodeTitle = "";
		String qualOrOfocodeCode = "";
		String qualOrOfocodeNqfLevel= "";
		if(registration.getQualification()!=null) {
			qualOrOfocodeTitle = registration.getQualification().getDescription();
			qualOrOfocodeCode = String.valueOf(registration.getQualification().getCode());
			qualOrOfocodeNqfLevel = registration.getQualification().getNqflevelg2description();
		}else if(registration.getOfoCodes()!=null){
			qualOrOfocodeTitle = registration.getOfoCodes().getDescription();
			qualOrOfocodeCode = registration.getOfoCodes().getOfoCodeParent();
			qualOrOfocodeNqfLevel = registration.getOfoCodes().getOfoCode();
		}
		
		List<SitesSme> smeList = dao.findSiteSmeByApproval(registration);
		//List<SitesSme> smeList = SitesSmeService.instance().allSitesSmeByCompanyAndStatus(registration.getCompany(), ApprovalEnum.Approved, true);
		for(SitesSme sitesSme: smeList) {
			SitesSmeService.instance().populateAdditionalInformation1(sitesSme);
		}
		for(SitesSme sitesSme: smeList) {
			if(sitesSme!=null && sitesSme.getId()!=null) {
				sitesSme.setReason(buildString(sitesSme.getSmeQualificationsList()));
			}
		}
		
		List<Signoff> signoffs = signoffService.findByTargetKeyAndClass(registration.getId(), WorkPlaceApproval.class.getName());
		for(Signoff s: signoffs) {
			HostingCompanyEmployees  hostingCompanyEmployees = hostingCompanyEmployeesService.findByUserReturnHostCompany(s.getUser().getId());
			if(hostingCompanyEmployees != null && hostingCompanyEmployees.getId() != null) {
				s.setDisignation(hostingCompanyEmployees.getJobTitle().getDescription());
			}else {
				SDFCompany sdfCompany = sdfCompanyService.findByCompanyAndUser(registration.getCompany(), s.getUser());
				if(sdfCompany !=null && sdfCompany.getId() !=null) {
					s.setDisignation(sdfCompany.getSdfType().getDescription());
				}				
			}
			if(s.getAccept()) {
				s.setStatus("Approved");
			}else if(s.getDispute()) {
				s.setStatus("Not Approved");
			}else {s.setStatus("N/A");}
		}	
		Signoff signoff1 = new Signoff();
		Signoff signoff2 = new Signoff();
		if(signoffs.size()>0) {
			signoff1 = signoffs.get(0);
			if(signoffs.size()>=1) {
				signoff2 = signoffs.get(1);
			}
		}
		 
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		
		String companyRepresentative = u.getFirstName() + " "+ u.getLastName(); 
		String merSETARepresentative = registration.getReviewUser().getFirstName() + " " + registration.getReviewUser().getLastName();
		Date date = registration.getApprovalDate();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		String approvalDate = "";
		if(date !=null) {
			approvalDate = dateFormat.format(date);
		}else {
			approvalDate = dateFormat.format(new Date()); 
		}
		
		params.put("signoff1", signoff1);
		params.put("signoff2", signoff2);
		
		params.put("companyActivities", "N/A");
		params.put("merSETARepresentative", merSETARepresentative);
		params.put("companyRepresentative", companyRepresentative);
		params.put("approvalDate", approvalDate);
		params.put("user", u);
		params.put("contact", u);
		params.put("sitename", sitename);
		params.put("company", registration.getCompany());		
		params.put("sitesme", registration);
		params.put("workplaceapproval", registration);
		params.put("qualOrOfocodeTitle", qualOrOfocodeTitle);
		params.put("qualOrOfocodeCode", qualOrOfocodeCode);
		params.put("qualOrOfocodeNqfLevel", qualOrOfocodeNqfLevel);
		//params.put("qualifications", qualifications);
		params.put("address", address);
		params.put("physicaladdress", registration.getCompany().getResidentialAddress());
		params.put("regional_office", r.getDescription());
		//params.put("QualificationList", new JRBeanCollectionDataSource(registration.get));
		params.put("sitesmedatasource", new JRBeanCollectionDataSource(smeList));
		params.put("signoffdatasourse", new JRBeanCollectionDataSource(signoffs));
		params.put("showToolList", showList);
		params.put("toolListDatasource", new JRBeanCollectionDataSource(workPlaceApprovalToolList));

//		byte[] bite = JasperService.instance().convertJasperReportToByte("ETQ-TP-054-WorkplaceApproval.jasper", params);
		
		js.createReportFromDBtoServletOutputStream("ETQ-TP-054-WorkplaceApproval.jasper", params,"WorkplaceApproval.pdf");
	}
	
	
	public void downloadWorkPlaceApproval(WorkPlaceApproval registration) throws Exception {
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		
		String idorpassport = "";
		String title ="";
		if(u.getTitle()!=null) {title = u.getTitle().getDescription();}
		
		String qualification = workplaceQualification(registration);
		
		if(u.getPassportNumber() != null || !u.getPassportNumber().matches("") ){
			idorpassport = u.getPassportNumber();
		}else{
			idorpassport = u.getRsaIDNumber();
		}
		
		String sitename = "";
		Sites site = registration.getSites();
		if(site !=null){
			sitename = site.getCompanyName();
		}else{
			sitename = registration.getCompany().getCompanyName();
		}
		
		RegionTown rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		Address address = u.getPostalAddress();
		if(address !=null && address.getId()!=null) {
			address = u.getPostalAddress();
		}else{
			address = u.getResidentialAddress();
		}
		
		if(registration.getCompany() != null && registration.getCompany().getPostalAddress()!=null) {
			registration.getCompany().setPostalAddress(AddressService.instance().findByKey(registration.getCompany().getPostalAddress().getId()));
		}else {
			registration.getCompany().setPostalAddress(AddressService.instance().findByKey(registration.getCompany().getResidentialAddress().getId()));
		}
		
		String qualOrOfocodeTitle = "";
		String qualOrOfocodeCode = "";
		String qualOrOfocodeNqfLevel= "";
		if(registration.getQualification()!=null) {
			qualOrOfocodeTitle = registration.getQualification().getDescription();
			qualOrOfocodeCode = String.valueOf(registration.getQualification().getCode());
			qualOrOfocodeNqfLevel = registration.getQualification().getNqflevelg2description();
		}else if(registration.getOfoCodes()!=null){
			qualOrOfocodeTitle = registration.getOfoCodes().getDescription();
			qualOrOfocodeCode = registration.getOfoCodes().getOfoCodeParent();
			qualOrOfocodeNqfLevel = registration.getOfoCodes().getOfoCode();
		}
		
		
		List<SitesSme> smeList = dao.findSiteSmeByApproval(registration);
		//List<SitesSme> smeList = SitesSmeService.instance().allSitesSmeByCompanyAndStatus(registration.getCompany(), ApprovalEnum.Approved, true);
		for(SitesSme sitesSme: smeList) {
			SitesSmeService.instance().populateAdditionalInformation1(sitesSme);
		}
		for(SitesSme sitesSme: smeList) {
			if(sitesSme!=null && sitesSme.getId()!=null) {
				sitesSme.setReason(buildString(sitesSme.getSmeQualificationsList()));
			}
		}
			
		List<Signoff> signoffs = signoffService.findByTargetKeyAndClass(registration.getId(), WorkPlaceApproval.class.getName());
		for(Signoff s: signoffs) {
			HostingCompanyEmployees  hostingCompanyEmployees = hostingCompanyEmployeesService.findByUserReturnHostCompany(s.getUser().getId());
			if(hostingCompanyEmployees != null && hostingCompanyEmployees.getId() != null) {
				s.setDisignation(hostingCompanyEmployees.getJobTitle().getDescription());
			}else {
				SDFCompany sdfCompany = sdfCompanyService.findByCompanyAndUser(registration.getCompany(), s.getUser());
				if(sdfCompany !=null && sdfCompany.getId() !=null) {
					s.setDisignation(sdfCompany.getSdfType().getDescription());
				}				
			}
			if(s.getAccept()) {
				s.setStatus("Approved");
			}else if(s.getDispute()) {
				s.setStatus("Not Approved");
			}else {s.setStatus("N/A");}
		}	
		Signoff signoff1 = new Signoff();
		Signoff signoff2 = new Signoff();
		if(signoffs.size()>0) {
			signoff1 = signoffs.get(0);
			if(signoffs.size()>=1) {
				signoff2 = signoffs.get(1);
			}
		}
		 
		
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		
		String companyRepresentative = u.getFirstName() + " "+ u.getLastName(); 
		//String merSETARepresentative = tasks.getActionUser().getFirstName() + " " + tasks.getActionUser().getLastName();
		String merSETARepresentative = registration.getReviewUser().getFirstName() + " " + registration.getReviewUser().getLastName();
		Date date = registration.getApprovalDate();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
		String approvalDate = "";
		if(date !=null) {
			approvalDate = dateFormat.format(date);
		}else {
			approvalDate = dateFormat.format(new Date()); 
		}
		
		params.put("signoff1", signoff1);
		params.put("signoff2", signoff2);
		
		params.put("companyActivities", "N/A");
		params.put("merSETARepresentative", merSETARepresentative);
		params.put("companyRepresentative", companyRepresentative);
		params.put("approvalDate", approvalDate);
		params.put("user", u);
		params.put("contact", u);
		params.put("sitename", sitename);
		params.put("company", registration.getCompany());		
		params.put("sitesme", registration);
		params.put("workplaceapproval", registration);
		params.put("qualOrOfocodeTitle", qualOrOfocodeTitle);
		params.put("qualOrOfocodeCode", qualOrOfocodeCode);
		params.put("qualOrOfocodeNqfLevel", qualOrOfocodeNqfLevel);
		//params.put("qualifications", qualifications);
		params.put("address", address);
		params.put("physicaladdress", registration.getCompany().getResidentialAddress());
		params.put("regional_office", r.getDescription());
		//params.put("QualificationList", new JRBeanCollectionDataSource(registration.get));
		params.put("sitesmedatasource", new JRBeanCollectionDataSource(smeList));
		params.put("signoffdatasourse", new JRBeanCollectionDataSource(signoffs));
		
		AttachmentBean beanAttachment=new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
	
		byte[] bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-054-WorkplaceApproval.jasper", params);
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("WorkplaceApprovalDetails.pdf");
		attachmentBeans.add(beanAttachment);
		
		JasperService js=new JasperService();
		js.createReportFromDBtoServletOutputStream("ETQ-TP-054-WorkplaceApproval.jasper", params,"WorkplaceApproval.pdf");		
	}

	public List<WorkPlaceApprovalSites> findByMentor(SitesSme sitesSme) {
		return dao.findByMentor(sitesSme);
	}
	
	public void sendLegacyWorkPlaceApprovalApprovalEmail(WorkPlaceApproval registration, Tasks tasks) throws Exception {	
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		
		String idorpassport = "";
		String title ="";
		if(u.getTitle()!=null) {title = u.getTitle().getDescription();}
		
		String qualification = workplaceQualification(registration);
		
		if(u.getPassportNumber() != null || !u.getPassportNumber().matches("") ){
			idorpassport = u.getPassportNumber();
		}else{
			idorpassport = u.getRsaIDNumber();
		}
		
		String sitename = "";
		Sites site = registration.getSites();
		if(site !=null){
			sitename = site.getCompanyName();
		}else{
			sitename = registration.getCompany().getCompanyName();
		}
		
		RegionTown rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		
		String welcome = "<p>Dear #title# #FirstName# #Surname#,</p>" 
						+ "<p>WORKPLACE APPROVAL APPLICATION FOR #COMPANYNAME# (#ENTITYID#): #SITENAME#</p>" 
						+ "<p>The merSETA has pleasure in informing you that your workplace has been granted approval to train in the following: </p>"
						+ qualification
						+ "<p>Should you require any assistance or further information, kindly contact the Client Liaison Officer at the #RegionalOfficeName# Office.</p>"
						+ "<p>Yours sincerely,</p>" 
						+ "<p>Manager: Quality Assurance</p>" 
						+ "<br/>";
		welcome = welcome.replaceAll("#title#", title);
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", registration.getCompany().getCompanyName());	
		welcome = welcome.replaceAll("#ENTITYID#", registration.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);		
		welcome = welcome.replaceAll("#MentorName#", u.getLastName());
		welcome = welcome.replaceAll("#MentorSurname#", u.getLastName());
		welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);
		welcome = welcome.replaceAll("#RegionalOfficeName#", r.getDescription());
		
		GenericUtility.sendMail(u.getEmail(), "Workplace Approval Application", welcome, null);	
	}
	
	public void sendLegacyWorkPlaceApprovalRejectEmail(WorkPlaceApproval registration, Users user, Tasks tasks, List<RejectReasons> rejectReasonsList) throws Exception
	{
		SDFCompany sdfCom = SDFCompanyService.instance().findPrimarySDF(registration.getCompany());		
		Users u = sdfCom.getSdf();
		String title ="";
		if(u.getTitle()!=null) {
			title = u.getTitle().getDescription();
		}
		RegionTown rt = RegionTownService.instance().findByTown(registration.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		String sitename = "";
		String rejectReason = convertToHTML(rejectReasonsList);
		Address address = registration.getCompany().getResidentialAddress();
		if(address !=null && address.getId()!=null) {
			address = AddressService.instance().findByKey(registration.getCompany().getPostalAddress().getId());
		}else {
			address = new Address();
		}
		
		
		String welcome = "<p>Dear #FirstName# #Surname#,</p>" 
				+ "<p>WORKPLACE APPROVAL APPLICATION FOR #COMPANYNAME# (#ENTITYID#): #SITENAME# </p>" 
				+ "<p>The merSETA regrets to inform you that your workplace approval application has not been granted due to the following: </p>"
				+ "<p>"+rejectReason+"</p>"
				+ "<p>Please do not hesitate to contact the merSETA #RegionalOfficeName# Office for further assistance</p>"
				+ "<p>Yours sincerely,</p>" 
				+ "<p>Manager: Quality Assurance</p>" 
				+ "<br/>";
				welcome = welcome.replaceAll("#title#", title);
				welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
				welcome = welcome.replaceAll("#Surname#", u.getLastName());
				welcome = welcome.replaceAll("#COMPANYNAME#", registration.getCompany().getCompanyName());	
				welcome = welcome.replaceAll("#ENTITYID#", registration.getCompany().getLevyNumber());
				welcome = welcome.replaceAll("#SITENAME#", sitename);
				welcome = welcome.replaceAll("#RegionalOfficeName#", r.getDescription());
				//GenericUtility.sendMail(u.getEmail(), "WORKPLACE APPROVAL APPLICATION ", welcome, null);
		GenericUtility.sendMail(u.getEmail(), "WORKPLACE APPROVAL APPLICATION ", welcome, null);
				
	}
	
	public WorkPlaceApproval findWorkplaceApproval(CompanyLearners companylearners) throws Exception {	
		Qualification qualification = CompanyLearnersService.instance().getCompanyLearnerQualification(companylearners);
		WorkPlaceApproval workPlaceApproval = null;
		if(companylearners.getSite() != null) {
			workPlaceApproval = dao.findApprovedWorkPlaceApprovalByCompanyAndQualificationAndSites(companylearners.getEmployer(), qualification, companylearners.getSite().getId());
		}else {
			workPlaceApproval = dao.findApprovedWorkPlaceApprovalByCompanyAndQualification(companylearners.getEmployer(), qualification);
		}
		return workPlaceApproval;
	}
	

	@SuppressWarnings("unchecked")
	public List<WorkPlaceApproval> cloCrmWorkPlaceApprovalRegionReport(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long userID) throws Exception {
		String hql = "select o from WorkPlaceApproval o where o.company.nonSetaCompany = false and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		filters.put("userID", userID);
		return resolveRegionReportInformation((List<WorkPlaceApproval>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countCloCrmWorkPlaceApprovalRegionReport(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkPlaceApproval o where o.company.nonSetaCompany = false and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		return dao.countWhere(filters, hql);
	}
	
	private List<WorkPlaceApproval> resolveRegionReportInformation(List<WorkPlaceApproval> workPlaceApprovalList) throws Exception {
		for (WorkPlaceApproval workPlaceApproval : workPlaceApprovalList) {
			resolveRegionReportInformation(workPlaceApproval);
		}
		return workPlaceApprovalList;
	}
	
	private WorkPlaceApproval resolveRegionReportInformation(WorkPlaceApproval workPlaceApproval) throws Exception {
		// resolve company address information
		if (companyServiceWPA == null) {
			companyServiceWPA = new CompanyService();
		}
		if (workPlaceApproval.getCompany() != null && workPlaceApproval.getCompany().getId() != null) {
			companyServiceWPA.resolveCompanyAddresses(workPlaceApproval.getCompany());
		}
		
		// resolve site address information
		if (sitesServiceWPA == null) {
			sitesServiceWPA = new SitesService();
		}
		if (workPlaceApproval.getSites() != null && workPlaceApproval.getSites().getId() != null) {
			sitesServiceWPA.resolveSiteAddresses(workPlaceApproval.getSites());
		}
		return workPlaceApproval;
	}
	
	public int countWorkPlaceApprovalByCloCrmAssigned(Long userID) throws Exception {
		return dao.countWorkPlaceApprovalByCloCrmAssigned(userID);
	}

	public List<WorkplaceApprovalBean> populateWorkplaceApprovalBean() throws Exception {
		return dao.populateWorkplaceApprovalBean();
	}
	
	public void downloadWorkplaceApprovalReport() throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "Company Name", "Trading Name", "Entity ID", "Company WPA Number", "Site Name", "Address", "Qualification", "Trade", "Status", "Approval Date", "WPA Number"});
		counter++;
		// data population
		populateDataForWorkplaceApprovalReport(data, counter);
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
			String fileName = "WPA Report.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private void populateDataForWorkplaceApprovalReport(Map<String, Object[]> data, Integer counter) throws Exception{
		int counterForPopulation = counter;
		List<WorkplaceApprovalBean> resultsList = populateWorkplaceApprovalBean();
		// populate data found into report
		for (WorkplaceApprovalBean entry : resultsList) {
			populateDataWorkplaceApprovalReport(data, entry, counterForPopulation);
			counterForPopulation++;
		}
		resultsList.clear();
		counter = counterForPopulation;
	}

	private void populateDataWorkplaceApprovalReport(Map<String, Object[]> data, WorkplaceApprovalBean entry, Integer counter) throws Exception {
		String approvalDate = "";
		if (entry.getApprovalDate() != null) {
			approvalDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getApprovalDate());
		}
		String qualTitle = "N/A";
		if (entry.getQualificationTitle() != null && entry.getCodeString() != null) {
			qualTitle = "(" + entry.getCodeString() + ") - " + entry.getQualificationTitle();
		}
		// new Object[] { "Company Name", "Trading Name", "Entity ID", "Company WPA Number", "Site Name", "Address", "Qualification", "Trade", "Status", "Approval Date", "WPA Number"}
		data.put(counter.toString(), new Object[] {entry.getCompanyName(), entry.getTradingName(), entry.getLevyNumber(), entry.getSiteName(), entry.getResidentialAddress(), qualTitle, entry.getStatus(), approvalDate, entry.getWpaApprovalNumber() } );
	}
	
}