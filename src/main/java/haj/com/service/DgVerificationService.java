package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import haj.com.entity.*;
import org.primefaces.model.SortOrder;

import haj.com.bean.DgVerificationReportBean;
import haj.com.bean.StatusReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.DgVerificationDAO;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CloRecommendationEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.QualificationService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DgVerificationService extends AbstractService {

	/** The dao. */
	private DgVerificationDAO                    dao                         = new DgVerificationDAO();
	private Long                                 finYear                     = Long.parseLong(GenericUtility.sdfYear.format(getSynchronizedDate()));
	private WspSignoffService                    wspSignoffService           = new WspSignoffService();
	private WspService                           wspService                  = new WspService();
	private ConfigDocService                     configDocService            = new ConfigDocService();
	private SiteVisitService                     siteVisitService            = new SiteVisitService();
	private MandatoryGrantDetailService          mandatoryGrantDetailService = new MandatoryGrantDetailService();
	private MandatoryGrantService                mandatoryGrantService       = new MandatoryGrantService();
	private DgAllocationService                  allocationService           = new DgAllocationService();
	private WorkPlaceApprovalService             workPlaceApprovalService    = new WorkPlaceApprovalService();
	private RejectReasonMultipleSelectionService reasonMultipleSelectionService;
	private SDFCompanyService                    sdfCompanyService;
	private CompanyUsersService                  companyUsersService;
	private RegionService                        regionService;
	private MandatoryGrantRecommendationService  mandatoryGrantRecommendationService;
	private UsersService                         usersService;

	private static DgVerificationService dgVerificationService;

	public static synchronized DgVerificationService instance() {
		if (dgVerificationService == null) {
			dgVerificationService = new DgVerificationService();
		}
		return dgVerificationService;
	}

	/**
	 * Get all DgVerification
	 * @author TechFinium
	 * @see DgVerification
	 * @return a list of {@link haj.com.entity.DgVerification}
	 * @throws Exception
	 * the exception
	 */
	public List<DgVerification> allDgVerification() throws Exception {
		return dao.allDgVerification();
	}

	/**
	 * Create or update DgVerification.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see DgVerification
	 */
	public void create(DgVerification entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void createWithSignOffSubmission(DgVerification dgVerification, Users user, Tasks task, Boolean useClo, List<RejectReasons> rejectReasonsList, Roles role) throws Exception {
		dgVerification.setStatus(ApprovalEnum.PendingApproval);
		dgVerification.setApplicationAppealed(false);
		dgVerification.setWithSdf(false);
		dgVerification.setReadyForAllocation(false);
		dgVerification.setCloUser(user);
		dgVerification.setCloAlteration(true);
		create(dgVerification);
		RegionTown  rt       = RegionTownService.instance().findByTown(dgVerification.getWsp().getCompany().getResidentialAddress().getTown());
		List<Users> signoffs = new ArrayList<>();
		if (useClo) signoffs.add(rt.getClo().getUsers());
		else signoffs.add(rt.getCrm().getUsers());
		TasksService.instance().findFirstInProcessAndCreateTask("", user, dgVerification.getId(), DgVerification.class.getName(), true, ConfigDocProcessEnum.DG_VERIFICATION, null, signoffs);

		if (reasonMultipleSelectionService == null) {
			reasonMultipleSelectionService = new RejectReasonMultipleSelectionService();
		}
		// check if rejectReasonsList is not null and has entries
		if (rejectReasonsList != null && rejectReasonsList.size() != 0) {
			List<IDataEntity> entityList = new ArrayList<IDataEntity>();

			entityList.addAll(reasonMultipleSelectionService.removeCreateLinks(dgVerification.getId(), DgVerification.class.getName(), rejectReasonsList, user, ConfigDocProcessEnum.DG_VERIFICATION));
			// check to ensure values added to entity list
			if (entityList.size() != 0) {
				dao.createBatch(entityList);
			}
			entityList = null;
			if (role != null && role.getId() != null) {
				DgVerificationRejectionInformationService.instance().updateCreateLastestEntries(dgVerification, rejectReasonsList, user, task, role);
			} else {
				if (useClo) {
					DgVerificationRejectionInformationService.instance().updateCreateLastestEntries(dgVerification, rejectReasonsList, user, task, RolesService.instance().findByKey(HAJConstants.ROLES_CLIENT_LIAISON_OFFICER_ID));
				} else {
					DgVerificationRejectionInformationService.instance().updateCreateLastestEntries(dgVerification, rejectReasonsList, user, task, RolesService.instance().findByKey(HAJConstants.ROLES_CLIENT_RELATIONS_MANAGER_ID));
				}
			}
		} else {
			reasonMultipleSelectionService.clearEntries(dgVerification.getId(), DgVerification.class.getName(), ConfigDocProcessEnum.DG_VERIFICATION);
		}
		reasonMultipleSelectionService = null;

	}

	/*
	 * RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress(). getTown()); List<Users> signoffs = new ArrayList<>(); if (useCLO) signoffs.add(rt.getClo().getUsers()); else signoffs.add(rt.getCrm().getUsers()); TasksService.instance().findNextInProcessAndCreateTask(user, tasks, signoffs);
	 */

	public void createWithSignOff(DgVerification dgVerification, Users user, Tasks task) throws Exception {
		dgVerification.setStatus(ApprovalEnum.PendingApproval);
		create(dgVerification);
		RegionTown rt = RegionTownService.instance().findByTown(dgVerification.getWsp().getCompany().getResidentialAddress().getTown());
		List<Users> cloUsers = new ArrayList();
		cloUsers.add(rt.getCrm().getUsers());
		TasksService.instance().findFirstInProcessAndCreateTask("", user, dgVerification.getId(), DgVerification.class.getName(), true, ConfigDocProcessEnum.DG_VERIFICATION, null, null);
	}

	public void saveSignOff(DgVerification dgVerification, Users user, Tasks task) throws Exception {
		boolean changeComplete = true;
		for (Signoff sign : dgVerification.getSignOffs()) {
			if (sign.getAccept() == null || !sign.getAccept()) {
				changeComplete = false;
			}
			if (sign.getUser().equals(user)) {
				sign.setCompleted(true);
			}
			dao.update(sign);
		}
		if (changeComplete) {
			dgVerification.setStatus(ApprovalEnum.Completed);
			// allocationService.doAllocation(dgVerification.getWsp());
			// mgVerification.getWsp()
		}
		create(dgVerification);

		TasksService.instance().completeTask(task);
	}

	/**
	 * Update DgVerification.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see DgVerification
	 */
	public void update(DgVerification entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete DgVerification.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see DgVerification
	 */
	public void delete(DgVerification entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.DgVerification}
	 * @throws Exception
	 * the exception
	 * @see DgVerification
	 */
	public DgVerification findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * @param wspId
	 * @return ApprovalEnum or null
	 * @throws Exception
	 */
	public ApprovalEnum findByWspAndReturnStatus(Long wspId) throws Exception {
		DgVerification dg = dao.findByWsp(wspId);
		if (dg == null) {
			return null;
		} else {
			if (dg.getStatus() == null) {
				return null;
			} else {
				return dg.getStatus();
			}
		}
	}

	/**
	 * Find object by Wsp Id.
	 * @author TechFinium
	 * @param Wsp
	 * the Wsp id
	 * @return a {@link haj.com.entity.DgVerification}
	 * @throws Exception
	 * the exception
	 * @see DgVerification
	 */
	public DgVerification findByWspId(Wsp wsp) throws Exception {
		if (wsp == null || wsp.getId() == null) {
			throw new Exception("Please select company");
		}
		return dao.findByWspId(wsp.getId());
	}

	/**
	 * Find DgVerification by description.
	 * @author TechFinium
	 * @param desc
	 * the desc
	 * @return a list of {@link haj.com.entity.DgVerification}
	 * @throws Exception
	 * the exception
	 * @see DgVerification
	 */
	public List<DgVerification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	public List<Wsp> findByWspByVerificationCompany(String description) throws Exception {
		return dao.findByWspByVerificationCompany(description);
	}

	public List<Wsp> findByWspByVerificationCompanyWspApproved(String description) throws Exception {
		return dao.findByWspByVerificationCompanyWspApproved(description);
	}

	public List<Wsp> findByWspByVerificationCompanyWspApprovedAndFinYear(String description, Integer finYear) throws Exception {
		return dao.findByWspByVerificationCompanyWspApprovedAndFinYear(description, finYear);
	}

	/**
	 * Lazy load DgVerification
	 * @param first
	 * from key
	 * @param pageSize
	 * no of rows to fetch
	 * @return a list of {@link haj.com.entity.DgVerification}
	 * @throws Exception
	 * the exception
	 */
	public List<DgVerification> allDgVerification(int first, int pageSize) throws Exception {
		return dao.allDgVerification(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of DgVerification for lazy load
	 * @author TechFinium
	 * @return Number of rows in the DgVerification
	 * @throws Exception
	 * the exception
	 */
	public Long count() throws Exception {
		return dao.count(DgVerification.class);
	}

	/**
	 * Lazy load DgVerification with pagination, filter, sorting
	 * @author TechFinium
	 * @param class1
	 * DgVerification class
	 * @param first
	 * the first
	 * @param pageSize
	 * the page size
	 * @param sortField
	 * the sort field
	 * @param sortOrder
	 * the sort order
	 * @param filters
	 * the filters
	 * @return a list of {@link haj.com.entity.DgVerification} containing data
	 * @throws Exception
	 * the exception
	 */
	@SuppressWarnings("unchecked")
	public List<DgVerification> allDgVerification(Class<DgVerification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<DgVerification>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of DgVerification for lazy load with filters
	 * @author TechFinium
	 * @param entity
	 * DgVerification class
	 * @param filters
	 * the filters
	 * @return Number of rows in the DgVerification entity
	 * @throws Exception
	 * the exception
	 */
	public int count(Class<DgVerification> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public List<DgVerification> allDgVerificationByFinYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear) throws Exception {
		String hql = "select o from DgVerification o where o.wsp.finYear = :finYear or o.wsp.dgYear.finYear = :finYear";
		filters.put("finYear", finYear);
		return populateInformation((List<DgVerification>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countallDgVerificationByFinYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DgVerification o where o.wsp.finYear = :finYear or o.wsp.dgYear.finYear = :finYear";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<DgVerification> allDgVerificationByCloCrm(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users u) throws Exception {
		String hql = "select o from DgVerification o left join fetch o.wsp where o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.crm.users.id = :userID or x.clo.users.id = :userID)" + " and (o.wsp.wspStatusEnum = :pending or o.wsp.wspStatusEnum = :approved or o.wsp.wspStatusEnum = :rejected)";
		filters.put("userID", u.getId());
		filters.put("pending", WspStatusEnum.Pending);
		filters.put("approved", WspStatusEnum.Approved);
		filters.put("rejected", WspStatusEnum.Rejected);
		return (List<DgVerification>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countRegion(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DgVerification o where o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.crm.users.id = :userID or x.clo.users.id = :userID) " + " and (o.wsp.wspStatusEnum = :pending or o.wsp.wspStatusEnum = :approved or o.wsp.wspStatusEnum = :rejected)";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<DgVerification> allDgVerificationByCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select o from DgVerification o left join fetch o.wsp where o.wsp.company.id = :companyId ";
		filters.put("companyId", company.getId());
		return (List<DgVerification>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllDgVerificationByCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DgVerification o where o.wsp.company.id = :companyId ";
		return dao.countWhere(filters, hql);
	}

	public void generateVerification() throws Exception {
		List<Wsp>         wsp          = findDiscWSP();
		List<IDataEntity> dataEntities = new ArrayList<>();
		for (Wsp w : wsp) {
			generate(dataEntities, w);
		}
		dao.createBatch(dataEntities);
	}

	public List<Wsp> findDiscWSP() throws Exception {
		if (getSynchronizedDate().before(GenericUtility.getEndOfApril(getSynchronizedDate()))) {
			Calendar now = Calendar.getInstance();
			this.finYear = Long.valueOf(now.get(Calendar.YEAR) + 1);
		}
		return wspService.allMandatoryGrantWsp(WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID, finYear.intValue());
	}

	public void generateForWSP(Wsp w) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		generate(dataEntities, w);
		if (dataEntities.size() != 0) {
			dao.createBatch(dataEntities);
		}
	}

	// 
	public void generateForWSP(Wsp wsp, Users user) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		generate(dataEntities, wsp);
		if (dataEntities.size() != 0) {
		  this.dao.createBatch(dataEntities);
		  RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		  List<Users> cloUsers = new ArrayList<>();
		  cloUsers.add(rt.getCrm().getUsers());
		  cloUsers.addAll(wsp.getWspSignoffs().stream().map(WspSignoff::getUser).collect(Collectors.toList()));
		  cloUsers.removeIf(usr -> usr.getId() == user.getId());
		  wsp.setDgVerification(new DgVerificationService().findByWspId(wsp));
		  TasksService.instance().findFirstInProcessAndCreateTask("", user, wsp.getDgVerification().getId(), DgVerification.class.getName(), true, ConfigDocProcessEnum.DG_VERIFICATION, null, cloUsers.stream().distinct().collect(Collectors.toList()));
		} 
	  }
	  	
	public void generateForRejectedWspSmallCompany(Wsp w) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		generateVerificationForRejectedSmallCompany(dataEntities, w);
		if (dataEntities.size() != 0) {
			dao.createBatch(dataEntities);
		}
	}

	public void generateForWspAdmin(Wsp w) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		generateAdmin(dataEntities, w);
		if (dataEntities.size() != 0) {
			dao.createBatch(dataEntities);
		}
	}

	public void generateForWSP() throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		List<Wsp>         wsps         = wspService.allWsp();
		for (Wsp w : wsps) {
			generate(dataEntities, w);
		}
		if (dataEntities.size() != 0) {
			dao.createBatch(dataEntities);
		}
	}

	private void generate(List<IDataEntity> dataEntities, Wsp w) {
		try {
			// version 1
			// List<MandatoryGrant> mg = mandatoryGrantDetailService.findSummarizedData(w,
			// WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID);
			// version 2
			// List<MandatoryGrant> mg =
			// mandatoryGrantDetailService.findSummarizedDataVersionTwo(w,WspReportEnum.WSP,
			// HAJConstants.DISC_FUNDING_ID);
			// version 3
			// List<MandatoryGrant> mg =
			// mandatoryGrantDetailService.findSummarizedDataVersionThree(w,
			// WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID);
			// version 4
			// List<MandatoryGrant> mg =
			// mandatoryGrantDetailService.findSummarizedDataVersionFour(w,
			// WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID);
			// version 5
			// List<MandatoryGrant> mg = mandatoryGrantDetailService.findSummarizedDataVersionFive(w, WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID, HAJConstants.WSP_ENROLEMNT_STATUS_WSP_NEW);
			// version 6
			List<MandatoryGrant> mg = new ArrayList<>();
			if (w.getFinYear() != null) {
				mg = mandatoryGrantDetailService.findSummarizedDataVersionSix(w, WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID, HAJConstants.WSP_ENROLEMNT_STATUS_WSP_NEW);
			} else {
				mg = mandatoryGrantService.findByWSP(w, WspReportEnum.EMPLOYMENTDATA);
			}
			long mgCreated = mandatoryGrantDetailService.findCountCreatedMandatoryGrant(w);
			w.setWspSignoffs(wspSignoffService.findByWsp(w));
			if (findByWspId(w) == null && mg.size() > 0 && (w.getWspStatusEnum() == WspStatusEnum.ProjectReview || w.getWspStatusEnum() == WspStatusEnum.Pending || w.getWspStatusEnum() == WspStatusEnum.Approved)) {
				DgVerification dgVerification = new DgVerification();
				dgVerification.setWsp(w);
				// Date d = (w.getCompany().getCategorization() == CategorizationEnum.Platinum)
				// ? GenericUtility.deductMonthsFromDate(getSynchronizedDate(), 6) :
				// GenericUtility.deductMonthsFromDate(getSynchronizedDate(), 3);
				Date d = GenericUtility.deductMonthsFromDate(getSynchronizedDate(), 6);
				dgVerification.setRequiresSiteVisit(siteVisitService.allSiteVisitCount(w.getCompany(), d) == 0);
				dataEntities.add(dgVerification);

				for (MandatoryGrant mandatoryGrant : mg) {

					// WPA Check
					if (mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired() != null && mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired()) {
						// Intervention Type WPA check
						workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
					} else if (mandatoryGrant.getQualification() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired()) {
						// qualification WPA check
						workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
					} else if (mandatoryGrant.getSkillsProgram() != null && mandatoryGrant.getSkillsProgram().getQualification() != null) {
						// skills program qualification WPA check
						Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsProgram().getQualification().getId());
						if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
							workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), qualification, null, null, null);
						}
					} else if (mandatoryGrant.getSkillsSet() != null && mandatoryGrant.getSkillsSet().getQualification() != null) {
						// skills set qualification WPA check
						Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsSet().getQualification().getId());
						if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
							workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), qualification, null, null, null);
						}
					}

					mandatoryGrantService.applyInterventionData(mandatoryGrant);
					mandatoryGrantService.populateAdditionalInformationForGeneration(mandatoryGrant);
					if (mandatoryGrant.getId() == null) {
						dataEntities.add(mandatoryGrant);
					}
				}
			} else if (findByWspId(w) != null && mg.size() > 0 && mgCreated == (long) 0) {
				// DgVerification dg = findByWspId(w);
				for (MandatoryGrant mandatoryGrant : mg) {

					// WPA Check
					if (mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired() != null && mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired()) {
						// Intervention Type WPA check
						workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
					} else if (mandatoryGrant.getQualification() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired()) {
						// qualification WPA check
						workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
					} else if (mandatoryGrant.getSkillsProgram() != null && mandatoryGrant.getSkillsProgram().getQualification() != null) {
						// skills program qualification WPA check
						Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsProgram().getQualification().getId());
						if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
							workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), qualification, null, null, null);
						}
					} else if (mandatoryGrant.getSkillsSet() != null && mandatoryGrant.getSkillsSet().getQualification() != null) {
						// skills set qualification WPA check
						Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsSet().getQualification().getId());
						if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
							workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), qualification, null, null, null);
						}
					}
					mandatoryGrantService.applyInterventionData(mandatoryGrant);
					mandatoryGrantService.populateAdditionalInformationForGeneration(mandatoryGrant);
				}
				dataEntities.addAll(mg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateVerificationForRejectedSmallCompany(List<IDataEntity> dataEntities, Wsp w) {
		try {
			// List<MandatoryGrant> mg = mandatoryGrantDetailService.findSummarizedDataVersionFive(w, WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID, HAJConstants.WSP_ENROLEMNT_STATUS_WSP_NEW);
			List<MandatoryGrant> mg        = mandatoryGrantDetailService.findSummarizedDataVersionSix(w, WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID, HAJConstants.WSP_ENROLEMNT_STATUS_WSP_NEW);
			long                 mgCreated = mandatoryGrantDetailService.findCountCreatedMandatoryGrant(w);
			w.setWspSignoffs(wspSignoffService.findByWsp(w));
			if (findByWspId(w) == null && mg.size() > 0 && (w.getWspStatusEnum() == WspStatusEnum.Rejected)) {
				DgVerification dgVerification = new DgVerification();
				dgVerification.setWsp(w);
				Date d = GenericUtility.deductMonthsFromDate(getSynchronizedDate(), 6);
				dgVerification.setRequiresSiteVisit(siteVisitService.allSiteVisitCount(w.getCompany(), d) == 0);
				dataEntities.add(dgVerification);
				for (MandatoryGrant mandatoryGrant : mg) {
					if (mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired() != null && mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired()) {
						workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
					} else if (mandatoryGrant.getQualification() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired()) {
						workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
					} else if (mandatoryGrant.getSkillsProgram() != null && mandatoryGrant.getSkillsProgram().getQualification() != null) {
						Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsProgram().getQualification().getId());
						if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
							workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), qualification, null, null, null);
						}
					} else if (mandatoryGrant.getSkillsSet() != null && mandatoryGrant.getSkillsSet().getQualification() != null) {
						Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsSet().getQualification().getId());
						if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
							workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), qualification, null, null, null);
						}
					}
					mandatoryGrantService.applyInterventionData(mandatoryGrant);
					mandatoryGrantService.populateAdditionalInformationForGeneration(mandatoryGrant);
				}
				dataEntities.addAll(mg);
			} else if (findByWspId(w) != null && mg.size() > 0 && mgCreated == (long) 0) {
				for (MandatoryGrant mandatoryGrant : mg) {
					if (mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired() != null && mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired()) {
						workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
					} else if (mandatoryGrant.getQualification() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired()) {
						workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
					} else if (mandatoryGrant.getSkillsProgram() != null && mandatoryGrant.getSkillsProgram().getQualification() != null) {
						Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsProgram().getQualification().getId());
						if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
							workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), qualification, null, null, null);
						}
					} else if (mandatoryGrant.getSkillsSet() != null && mandatoryGrant.getSkillsSet().getQualification() != null) {
						Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsSet().getQualification().getId());
						if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
							workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), qualification, null, null, null);
						}
					}
					mandatoryGrantService.applyInterventionData(mandatoryGrant);
					mandatoryGrantService.populateAdditionalInformationForGeneration(mandatoryGrant);
				}
				dataEntities.addAll(mg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateAdmin(List<IDataEntity> dataEntities, Wsp w) throws Exception {
		// version two
		// List<MandatoryGrant> mg =
		// mandatoryGrantDetailService.findSummarizedDataVersionTwo(w,
		// WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID);
		// version three
		// List<MandatoryGrant> mg =
		// mandatoryGrantDetailService.findSummarizedDataVersionThree(w,
		// WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID);
		// version four
		// List<MandatoryGrant> mg =
		// mandatoryGrantDetailService.findSummarizedDataVersionFour(w,
		// WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID);
		// version 5
		// List<MandatoryGrant> mg = mandatoryGrantDetailService.findSummarizedDataVersionFive(w, WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID, HAJConstants.WSP_ENROLEMNT_STATUS_WSP_NEW);
		// version 6
		List<MandatoryGrant> mg        = mandatoryGrantDetailService.findSummarizedDataVersionSix(w, WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID, HAJConstants.WSP_ENROLEMNT_STATUS_WSP_NEW);
		long                 mgCreated = mandatoryGrantDetailService.findCountCreatedMandatoryGrant(w);
		w.setWspSignoffs(wspSignoffService.findByWsp(w));
		if (findByWspId(w) == null && mg.size() > 0 && (w.getWspStatusEnum() == WspStatusEnum.Pending || w.getWspStatusEnum() == WspStatusEnum.Approved)) {
			DgVerification dgVerification = new DgVerification();
			dgVerification.setWsp(w);
			Date d = GenericUtility.deductMonthsFromDate(getSynchronizedDate(), 6);
			dgVerification.setRequiresSiteVisit(siteVisitService.allSiteVisitCount(w.getCompany(), d) == 0);
			dataEntities.add(dgVerification);

			for (MandatoryGrant mandatoryGrant : mg) {

				// WPA Check
				if (mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired() != null && mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired()) {
					// Intervention Type WPA check
					workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
				} else if (mandatoryGrant.getQualification() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired()) {
					// qualification WPA check
					workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
				} else if (mandatoryGrant.getSkillsProgram() != null && mandatoryGrant.getSkillsProgram().getQualification() != null) {
					// skills program qualification WPA check
					Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsProgram().getQualification().getId());
					if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
						workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), qualification, null, null, null);
					}
				} else if (mandatoryGrant.getSkillsSet() != null && mandatoryGrant.getSkillsSet().getQualification() != null) {
					// skills set qualification WPA check
					Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsSet().getQualification().getId());
					if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
						workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), qualification, null, null, null);
					}
				}
				mandatoryGrantService.applyInterventionData(mandatoryGrant);
				mandatoryGrantService.populateAdditionalInformationForGeneration(mandatoryGrant);
			}
			dataEntities.addAll(mg);
		} else if (findByWspId(w) != null && mg.size() > 0 && mgCreated == (long) 0) {
			// DgVerification dg = findByWspId(w);
			for (MandatoryGrant mandatoryGrant : mg) {
				// WPA Check
				if (mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired() != null && mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired()) {
					// Intervention Type WPA check
					workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
				} else if (mandatoryGrant.getQualification() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired()) {
					// qualification WPA check
					workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
				} else if (mandatoryGrant.getSkillsProgram() != null && mandatoryGrant.getSkillsProgram().getQualification() != null) {
					// skills program qualification WPA check
					Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsProgram().getQualification().getId());
					if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
						workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), qualification, null, null, null);
					}
				} else if (mandatoryGrant.getSkillsSet() != null && mandatoryGrant.getSkillsSet().getQualification() != null) {
					// skills set qualification WPA check
					Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsSet().getQualification().getId());
					if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
						workPlaceApprovalService.checkWorkplaceApproval(w.getCompany(), qualification, null, null, null);
					}
				}
				mandatoryGrantService.applyInterventionData(mandatoryGrant);
				mandatoryGrantService.populateAdditionalInformationForGeneration(mandatoryGrant);
			}
			dataEntities.addAll(mg);
		}
	}

	public void generateWorkplaceApproval() throws Exception {
		List<DgVerification>  dgList                = allDgVerification();
		WspService            wspService            = new WspService();
		MandatoryGrantService mandatoryGrantService = new MandatoryGrantService();
		for (DgVerification dgVerification : dgList) {
			Wsp                  wsp    = wspService.findByKey(dgVerification.getWsp().getId());
			List<MandatoryGrant> mgList = mandatoryGrantService.findByWSPDgVerification(wsp);
			for (MandatoryGrant mandatoryGrant : mgList) {
				if (mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired() != null && mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired()) {
					workPlaceApprovalService.checkWorkplaceApproval(wsp.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
				} else if (mandatoryGrant.getQualification() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired()) {
					workPlaceApprovalService.checkWorkplaceApproval(wsp.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
				}
			}
		}
	}

	public void completeTask(DgVerification bankingDetails, Users user, Tasks task) throws Exception {
		TasksService.instance().findNextInProcessAndCreateTask("", user, bankingDetails.getId(), bankingDetails.getClass().getName(), true, task, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	}

	public void acceptVerification(DgVerification dgVerification, Users user, Tasks task) throws Exception {
		dgVerification.setStatus(ApprovalEnum.Approved);
		dgVerification.setWithSdf(false);
		dgVerification.setApprovedDate(getSynchronizedDate());
		update(dgVerification);
		TasksService.instance().completeTaskByTargetKey(DgVerification.class.getName(), dgVerification.getId());
		/*
		 * Allocation check and generation
		 */
		if (dgVerification.getWsp() != null && dgVerification.getWsp().getFinYear() != null && dgVerification.getWsp().getFinYear() != 2019) {
			allocationService.checkForAllocation(dgVerification.getWsp());
		} else if (dgVerification.getWsp().getFinYear() == null) {
			System.out.println("DOING ALLOCATION!!!!!!!!!");
			allocationService.doAllocation(dgVerification.getWsp());
			System.out.println("DONE ALLOCATION!!!!!!!!!");
		}
	}

	public void acceptVerificationRejection(DgVerification dgVerification, Users user, Tasks task) throws Exception {
		dgVerification.setStatus(ApprovalEnum.Rejected);
		dgVerification.setWithSdf(false);
		dgVerification.setApprovedDate(getSynchronizedDate());
		update(dgVerification);
		TasksService.instance().completeTask(task);
	}

	public void declineVerification(DgVerification dgVerification, Users user, Tasks task) throws Exception {
		dgVerification.setStatus(ApprovalEnum.PendingFinalApproval);
		update(dgVerification);
		TasksService.instance().findNextInProcessAndCreateTask("", user, dgVerification.getId(), dgVerification.getClass().getName(), true, task, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	}

	/**
	 * Just duplicated incase the process is changed later on but the declineVerification could have been called instead...
	 * @param dgVerification
	 * @param user
	 * @param task
	 * @throws Exception
	 */
	public void declineAllVerification(DgVerification dgVerification, Users user, Tasks task) throws Exception {
		dgVerification.setStatus(ApprovalEnum.PendingFinalApproval);
		update(dgVerification);
		TasksService.instance().findNextInProcessAndCreateTask("", user, dgVerification.getId(), dgVerification.getClass().getName(), true, task, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	}

	public void withdrawnVerification(DgVerification dgVerification, Users user, Tasks task) throws Exception {
		dgVerification.setStatus(ApprovalEnum.Withdrawn);
		dgVerification.setWithSdf(false);
		dgVerification.setApprovedDate(getSynchronizedDate());
		// dgVerification.getWsp().setWspStatusEnum(WspStatusEnum.Withdrawn);
		// dao.update(dgVerification.getWsp());
		update(dgVerification);
		TasksService.instance().completeTask(task);
		notifyWithdrawCrmClo(dgVerification, user);
	}

	public void approveTask(DgVerification dgVerification, Users user, Tasks task) throws Exception {
		dgVerification.setStatus(ApprovalEnum.PendingFinalApproval);
		update(dgVerification);
		TasksService.instance().findNextInProcessAndCreateTask("", user, dgVerification.getId(), dgVerification.getClass().getName(), true, task, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	}

	public void crmApproveTask(DgVerification dgVerification, Users user, Tasks task) throws Exception {
		dgVerification.setStatus(ApprovalEnum.PendingFinalApproval);
		dgVerification.setCrmDecision(CloRecommendationEnum.Approval);
		dgVerification.setCrmApprovalRejectionDate(getSynchronizedDate());
		dgVerification.setWithSdf(true);
		update(dgVerification);
		TasksService.instance().findNextInProcessAndCreateTask("", user, dgVerification.getId(), dgVerification.getClass().getName(), true, task, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
		notifyMainSdfAndContact(true, dgVerification, null);
	}

	public void sdfAppealApplicationTaskNotification(DgVerification dgVerification, Users user, Tasks task, boolean useClo) throws Exception {
		for (Doc doc : dgVerification.getDocs()) {
			if (doc.getId() == null) {
				throw new Exception("Upload all documents before proceeding");
			}
		}
		dgVerification.setWithSdf(false);
		dgVerification.setApplicationAppealed(true);
		dgVerification.setDateAppealed(getSynchronizedDate());
		update(dgVerification);
		RegionTown  rt       = RegionTownService.instance().findByTown(dgVerification.getWsp().getCompany().getResidentialAddress().getTown());
		List<Users> signoffs = new ArrayList<>();
		if (useClo) signoffs.add(rt.getClo().getUsers());
		else signoffs.add(rt.getCrm().getUsers());
		TasksService.instance().findNextInProcessAndCreateTask("", user, dgVerification.getId(), dgVerification.getClass().getName(), true, task, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), signoffs);
	}

	private void notifyWithdrawCrmClo(DgVerification dgVerification, Users user) throws Exception {
		List<Users> notifyUsers = new ArrayList<>();
		RegionTown  rt          = RegionTownService.instance().findByTown(dgVerification.getWsp().getCompany().getResidentialAddress().getTown());
		if (sdfCompanyService == null) {
			sdfCompanyService = new SDFCompanyService();
		}
		notifyUsers.add(sdfCompanyService.findPrimarySDF(dgVerification.getWsp().getCompany()).getSdf());
		if (companyUsersService == null) {
			companyUsersService = new CompanyUsersService();
		}
		List<CompanyUsers> cu = companyUsersService.findByCompanyResponsibility(dgVerification.getWsp().getCompany(), ConfigDocProcessEnum.DG_VERIFICATION);
		if (cu.size() == 0) {
			cu = companyUsersService.findByCompanyNotSDF(dgVerification.getWsp().getCompany());
			for (CompanyUsers companyUsers : cu) {
				notifyUsers.add(companyUsers.getUser());
			}
		} else {
			for (CompanyUsers companyUsers : cu) {
				notifyUsers.add(companyUsers.getUser());
			}
		}
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		regionService       = null;
		cu                  = null;
		companyUsersService = null;
		Users cloUser = rt.getClo().getUsers();
		Users crmUser = rt.getCrm().getUsers();
		for (Users users : notifyUsers) {
			sendWithdrawNotification(users, dgVerification.getWsp().getCompany(), dgVerification.getWsp().getFinYear().toString(), dgVerification, cloUser.getFirstName() + " " + cloUser.getLastName(), crmUser, r.getDescription());
		}
	}

	public void finalApproveTask(DgVerification dgVerification, Users user, Tasks task) throws Exception {
		dgVerification.setStatus(ApprovalEnum.Approved);
		dgVerification.setApprovedDate(getSynchronizedDate());
		update(dgVerification);
		TasksService.instance().findNextInProcessAndCreateTask("", user, dgVerification.getId(), dgVerification.getClass().getName(), true, task, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	}

	public void rejectTask(DgVerification dgVerification, Users user, Tasks task) throws Exception {
		TasksService.instance().findPreviousInProcessAndCreateTask("", user, dgVerification.getId(), dgVerification.getClass().getName(), true, task, MailDef.instance(MailEnum.Task), null);
	}

	public void finalRejectTask(DgVerification dgVerification, Users user, Tasks task) throws Exception {
		dgVerification.setStatus(ApprovalEnum.Rejected);
		dgVerification.setApprovedDate(getSynchronizedDate());
		update(dgVerification);
		TasksService.instance().completeTask(task);
	}

	public void crmRejectApplicationNotifySDF(DgVerification dgVerification, Users user, Tasks task, List<RejectReasons> rejectReasons, Roles role) throws Exception {
		// dgVerification.setStatus(ApprovalEnum.Rejected);
		// dgVerification.setCrmApprovalRejectionDate(getSynchronizedDate());
		dgVerification.setStatus(ApprovalEnum.PendingFinalApproval);
		dgVerification.setCrmDecision(CloRecommendationEnum.Rejection);
		dgVerification.setCrmApprovalRejectionDate(getSynchronizedDate());
		dgVerification.setCrmUser(user);
		dgVerification.setWithSdf(true);
		update(dgVerification);

		if (role != null && role.getId() != null) {
			DgVerificationRejectionInformationService.instance().updateCreateLastestEntries(dgVerification, rejectReasons, user, task, role);
		} else {
			DgVerificationRejectionInformationService.instance().updateCreateLastestEntries(dgVerification, rejectReasons, user, task, RolesService.instance().findByKey(HAJConstants.ROLES_CLIENT_RELATIONS_MANAGER_ID));
		}

		TasksService.instance().findNextInProcessAndCreateTask("", user, dgVerification.getId(), dgVerification.getClass().getName(), true, task, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);

		// TasksService.instance().completeTask(task);
		notifyMainSdfAndContact(false, dgVerification, rejectReasons);
	}

	public void prepareNewDocs(DgVerification extensionRequest) throws Exception {
		extensionRequest.setDocs(DocService.instance().searchByDgVerification(extensionRequest));
		// List<ConfigDoc> l =
		// configDocService.findByProcess(ConfigDocProcessEnum.DG_VERIFICATION,
		// CompanyUserTypeEnum.Company);
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(extensionRequest, ConfigDocProcessEnum.DG_VERIFICATION, CompanyUserTypeEnum.Company);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				extensionRequest.getDocs().add(new Doc(a, extensionRequest));
			});
		}
	}

	public void populateCloCrmRejectionReasons(DgVerification dgVerification) throws Exception {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		dgVerification.setCloRejectionReasons(rejectReasonsService.locateReasonsForDgVerificationAndReturnInString(dgVerification.getId(), Boolean.TRUE, HAJConstants.ROLES_CLIENT_LIAISON_OFFICER_ID));
		dgVerification.setCrmRejectionReasons(rejectReasonsService.locateReasonsForDgVerificationAndReturnInString(dgVerification.getId(), Boolean.TRUE, HAJConstants.ROLES_CLIENT_RELATIONS_MANAGER_ID));
	}

	public void finalApproveApplication(DgVerification dgVerification, Users user, Tasks task, boolean approved) throws Exception {
		// dgVerification.setStatus(ApprovalEnum.Approved);
		// dgVerification.setApprovedDate(getSynchronizedDate());
		dgVerification.setCrmDecision(CloRecommendationEnum.Approval);
		dgVerification.setCrmAppealedDateApproved(getSynchronizedDate());
		dgVerification.setWithSdf(true);
		update(dgVerification);
		TasksService.instance().findPreviousInProcessAndCreateTask(user, task, null);
		// sends task back to SDF to
		// TasksService.instance().completeTask(task);
		notifyReleventUsersOnAction(dgVerification, approved);
	}

	public void finalRejectApplication(DgVerification dgVerification, Users user, Tasks task, boolean approved) throws Exception {
		dgVerification.setStatus(ApprovalEnum.Rejected);
		dgVerification.setWithSdf(false);
		dgVerification.setApprovedDate(getSynchronizedDate());
		update(dgVerification);
		TasksService.instance().completeTask(task);
		notifyReleventUsersOnAction(dgVerification, approved);
	}

	/**
	 * Clears the DG verification data of assigned by WSP. Note should only be used if WSP is sent back to the SDF for grant year 2019
	 * @see Wsp
	 * @see MandatoryGrant
	 * @param entity
	 * @throws Exception
	 */
	public void clearDgVerification(Wsp wsp) throws Exception {
		DgVerification dgVerification = findByWspId(wsp);
		if (dgVerification != null && dgVerification.getId() != null) {
			List<IDataEntity> updateList = new ArrayList<IDataEntity>();
			List<IDataEntity> deleteList = new ArrayList<IDataEntity>();

			// sets DG verification information to generation
			dgVerification = setVerificationToDeafult(dgVerification);
			deleteList.add(dgVerification);

			// closes all tasks assigned to DG verification
			TasksService.instance().completeTaskByTargetKey(DgVerification.class.getName(), dgVerification.getId());

			if (mandatoryGrantService == null) {
				mandatoryGrantService = new MandatoryGrantService();
			}

			// deletes all entries assigned to DG verification
			if (mandatoryGrantRecommendationService == null) {
				mandatoryGrantRecommendationService = new MandatoryGrantRecommendationService();
			}

			List<MandatoryGrant> mgForWsp = mandatoryGrantService.findByWSPForDgVerificationIgnoreImport(wsp);
			// loops through all recommendations and adds to delete list
			for (MandatoryGrant mandatoryGrantEntry : mgForWsp) {
				List<MandatoryGrantRecommendation> mgRecommendation = mandatoryGrantRecommendationService.findByMG(mandatoryGrantEntry);
				if (mgRecommendation.size() != 0) {
					deleteList.addAll(mgRecommendation);
				}
			}
			deleteList.addAll(mgForWsp);

			if (updateList.size() > 0) {
				dao.updateBatch(updateList);
			}
			if (deleteList.size() > 0) {
				dao.deleteBatch(deleteList);
			}
			updateList                          = null;
			deleteList                          = null;
			mandatoryGrantService               = null;
			mandatoryGrantRecommendationService = null;
		}
	}

	/**
	 * Sets the DG verification data back to default/creation
	 * @param dgVerification
	 * @return DgVerification
	 */
	private DgVerification setVerificationToDeafult(DgVerification dgVerification) {
		dgVerification.setStatus(null);
		dgVerification.setReadyForAllocation(false);
		dgVerification.setCrmAppealedDateApproved(null);
		dgVerification.setDateAppealed(null);
		dgVerification.setApplicationAppealed(null);
		dgVerification.setWithSdf(false);
		dgVerification.setCloAlteration(null);
		dgVerification.setCrmApprovalRejectionDate(null);
		dgVerification.setCrmDecision(null);
		dgVerification.setCloRecommendation(null);
		dgVerification.setApprovedDate(null);
		return dgVerification;
	}

	/**
	 * Locates the SDF and Main contact for notification
	 * @param approved
	 * @param dgVerification
	 * @throws Exception
	 */

	private void notifyMainSdfAndContact(boolean approved, DgVerification dgVerification, List<RejectReasons> rejectReasons) throws Exception {
		List<Users> users = locateClientUsersNotification(dgVerification);

		RegionTown rt = RegionTownService.instance().findByTown(dgVerification.getWsp().getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		regionService = null;
		// locates company's region CRM and CLO
		Users crmUser = rt.getCrm().getUsers();
		Users cloUser = rt.getClo().getUsers();
		for (Users notifyUsers : users) {
			if (approved) {
				sendSuccessfulDGVerificationOutcomeEmail(notifyUsers, dgVerification.getWsp().getCompany(), dgVerification.getWsp().getFinYearNonNull().toString(), cloUser.getFirstName() + " " + cloUser.getLastName(), crmUser, r.getDescription());
			} else {
				sendDiscritionaryGrandRejectionEmail(notifyUsers, dgVerification.getWsp().getCompany(), dgVerification.getWsp().getFinYearNonNull().toString(), cloUser.getFirstName() + " " + cloUser.getLastName(), rejectReasons, crmUser, r.getDescription());
			}
		}
		users             = null;
		crmUser           = null;
		cloUser           = null;
		sdfCompanyService = null;
		r                 = null;
	}

	private void notifyReleventUsersOnAction(DgVerification dgVerification, boolean approved) throws Exception {
		List<Users> users = locateClientUsersNotification(dgVerification);

		RegionTown rt = RegionTownService.instance().findByTown(dgVerification.getWsp().getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		regionService = null;
		// locates company's region CRM and CLO
		Users               crmUser             = rt.getCrm().getUsers();
		Users               cloUser             = rt.getClo().getUsers();
		List<RejectReasons> rejectReasons       = new ArrayList<>();
		String              clientLiaisonOffice = cloUser.getFirstName() + " " + cloUser.getLastName();
		SimpleDateFormat    sdf                 = new SimpleDateFormat("dd MMMM yyyy");
		for (Users user : users) {
			if (approved) {
				sendDGAppealSuccessfulEmail(user, dgVerification.getWsp().getCompany(), clientLiaisonOffice, crmUser, r.getDescription(), sdf.format(dgVerification.getDateAppealed()));
			} else {
				sendDGAppealUnsuccessfulEmail(user, dgVerification.getWsp().getCompany(), clientLiaisonOffice, rejectReasons, crmUser, r.getDescription(), sdf.format(dgVerification.getDateAppealed()));
			}
		}
	}

	/**
	 * Locates the additional users to notify for the SDF notification
	 * @param dgVerification
	 * @return List<Users>
	 * @throws Exception
	 */
	private List<Users> locateClientUsersNotification(DgVerification dgVerification) throws Exception {
		List<Users> users = new ArrayList<Users>();

		if (sdfCompanyService == null) {
			sdfCompanyService = new SDFCompanyService();
		}
		users.add(sdfCompanyService.findPrimarySDF(dgVerification.getWsp().getCompany()).getSdf());

		if (companyUsersService == null) {
			companyUsersService = new CompanyUsersService();
		}

		/*
		 * Send list HR Manager (if provided), Training Manager (if provided),
		 */
		// locate Labour/Union SDF
		List<SDFCompany> sdfList = sdfCompanyService.findByCompanyAndSdfType(dgVerification.getWsp().getCompany(), (long) 3);
		for (SDFCompany sdfCompany : sdfList) {
			users.add(sdfCompany.getSdf());
			break;
		}

		// locate Employee SDF
		sdfList = sdfCompanyService.findByCompanyAndSdfType(dgVerification.getWsp().getCompany(), (long) 4);
		for (SDFCompany sdfCompany : sdfList) {
			users.add(sdfCompany.getSdf());
			break;
		}
		sdfList = null;

		// HR manager
		List<CompanyUsers> cu = companyUsersService.findByCompanyResponsibilityAndUserPosition(dgVerification.getWsp().getCompany(), ConfigDocProcessEnum.DG_VERIFICATION, (long) 3);
		for (CompanyUsers companyUsers : cu) {
			users.add(companyUsers.getUser());
			break;
		}

		// Training Manager
		cu = companyUsersService.findByCompanyResponsibilityAndUserPosition(dgVerification.getWsp().getCompany(), ConfigDocProcessEnum.DG_VERIFICATION, (long) 4);
		for (CompanyUsers companyUsers : cu) {
			users.add(companyUsers.getUser());
			break;
		}
		return users;
	}

	public void sendSuccessfulDGVerificationOutcomeEmail(Users user, Company company, String grantYear, String clientLiaisonOffice, Users crmUser, String region) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("grantYear", grantYear);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("crmUser", crmUser);
		params.put("crm_region", region);
		params.put("clientLiaisonOffice", clientLiaisonOffice);
		byte[] bites   = JasperService.instance().convertJasperReportToByte("Notification-SuccessfulDGverificationOutcome.jasper", params);
		String subject = "OUTCOME OF DISCRETIONARY GRANT VERIFICATION FOR " + company.getCompanyName() + " - " + company.getLevyNumber() + " ";
		String mssg    = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Thank you for submitting a discretionary grant application for " + grantYear + "." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We wish to inform you that the merSETA has completed the " + "discretionary grant application verification. " + "You are required to review and sign off the outcome of " + "the verification." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that should the discretionary grant " + "application verification not be signed off " + "online on the NSDMS within five (5) working " + "days from the date of this notification, the " + "merSETA will automatically use the outcome to " + "progress to the next phase of the application process." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We wish to advise that while the discretionary grant verification " + "has been successfully completed, the application has not yet " + "been finalised and this notification does not constitute a " + "discretionary grant award. The final outcome of the " + "discretionary grant application will be " + "communicated at a later date.  " + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>The merSETA Client Services </b></p>";
		/*
		 * + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" +crmUser.getFirstName()+" "+crmUser.getLastName()+" </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">CLIENT RELATIONS MANAGER: " +region+"/p>";
		 */

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "Successful_DG_verification_Outcome.pdf", "pdf", null);

	}

	public void sendDiscritionaryGrandRejectionEmail(Users user, Company company, String grantYear, String clientLiaisonOffice, List<RejectReasons> rejectReasons, Users crmUser, String region) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("grantYear", grantYear);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("crmUser", crmUser);
		params.put("crm_region", region);
		params.put("clientLiaisonOffice", clientLiaisonOffice);
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasons));
		byte[] bites   = JasperService.instance().convertJasperReportToByte("DiscretionaryGrantVerificationOutcomeUnsuccessful.jasper", params);
		String subject = "DISCRETIONARY GRANT APPLICATION FOR: " + company.getCompanyName() + " - " + company.getLevyNumber() + " ";
		String mssg    = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Thank you for submitting a discretionary grant application for " + grantYear + "." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We regret to advise that the application has not been successful. " + "Should you wish to appeal the outcome, please submit an appeal " + "on the NSDMS within 14 days of receipt of this notification." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Should you have any queries please do not hesitate to contact your Client " + "Liaison Officer: " + clientLiaisonOffice + " for assistance. " + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>The merSETA Client Services </b></p>";

		/*
		 * + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" +clientLiaisonOffice+" </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Client Liaison Officer: " +region+"/p>";
		 */

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "Discretionary_Grant_Verification_Outcome.pdf", "pdf", null);
	}

	/**
	 * Sends notification of withdrawl
	 * @param user
	 * @param company
	 * @param grantYear
	 * @param dgVerification
	 * @throws Exception
	 */
	public void sendWithdrawNotification(Users user, Company company, String grantYear, DgVerification dgVerification, String clientLiaisonOffice, Users crmUser, String region) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("grantYear", grantYear);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("withdraw_reason", dgVerification.getDiscretionalWithdrawalAppealEnum().getFriendlyName());
		params.put("client_liaison_officer", clientLiaisonOffice);
		params.put("crmUser", crmUser);
		params.put("crm_region", region);
		byte[] bites   = JasperService.instance().convertJasperReportToByte("Notification-Withdrawal-Of-DG-Verification.jasper", params);
		String subject = "WITHDRAWAL OF DISCRETIONARY GRANT APPLICATION FOR: " + company.getCompanyName() + " - " + company.getLevyNumber() + " ";
		String mssg    = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Thank you for submitting a discretionary grant application for " + grantYear + "." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We acknowledge notification to withdraw your application for the following reason(s):" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<ul> <li>" + dgVerification.getDiscretionalWithdrawalAppealEnum().getFriendlyName() + "</li> </ul>" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Should you have any queries please do not hesitate to contact your Client Liaison Officer: " + clientLiaisonOffice + " for assistance." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>The merSETA Client Services </b></p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "Notification_Withdrawal_Of_DG_Verification.pdf", "pdf", null);
	}

	// appealSubmissionDate format : 04 July 2018
	public void sendDGAppealUnsuccessfulEmail(Users user, Company company, String clientLiaisonOffice, List<RejectReasons> rejectReasons, Users crmUser, String region, String appealSubmissionDate) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("appealSubmissionDate", appealSubmissionDate);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("crmUser", crmUser);
		params.put("crm_region", region);
		params.put("clientLiaisonOffice", clientLiaisonOffice);
		params.put("rejectReasonDataSource", new JRBeanCollectionDataSource(rejectReasons));
		byte[] bites   = JasperService.instance().convertJasperReportToByte("Notification-DGApplicationAppealUnsuccessful.jasper", params);
		String subject = "DISCRETIONARY GRANT APPLICATION APPEAL FOR: " + company.getCompanyName() + " (" + company.getLevyNumber() + ") ";
		String mssg    = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We regret to advise that the appeal for the discretionary grant application has not been successful." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Should you have any queries please do not hesitate to contact your Client " + "Liaison Officer: " + clientLiaisonOffice + " for assistance. " + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>The merSETA Client Services </b></p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "Notification_DG_Application_Appeal_Unsuccessful.pdf", "pdf", null);
	}

	// appealSubmissionDate format : 04 July 2018
	public void sendDGAppealSuccessfulEmail(Users user, Company company, String clientLiaisonOffice, Users crmUser, String region, String appealSubmissionDate) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		params.put("appealSubmissionDate", appealSubmissionDate);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		params.put("crmUser", crmUser);
		params.put("crm_region", region);
		params.put("clientLiaisonOffice", clientLiaisonOffice);
		byte[] bites   = JasperService.instance().convertJasperReportToByte("Notification-SuccessfulDGAppealOutcome.jasper", params);
		String subject = "DISCRETIONARY GRANT APPLICATION APPEAL FOR: " + company.getCompanyName() + " (" + company.getLevyNumber() + ") ";
		String mssg    = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We wish to inform you that the merSETA has completed the " + "review of discretionary grant application appeal for your " + "organisation. The outcome of your appeal is <b>successful</b>, " + "and you are required to review and sign off the discretionary " + "grant verification." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that should the discretionary grant " + "application verification not be signed off online on " + "the NSDMS within five (5) working days from the date " + "of this notification, the merSETA will automatically " + "use the outcome to progress to the next phase of " + "the application process." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We wish to advise that while the discretionary grant " + "verification has been successfully completed, the " + "application has not yet been finalised and this " + "notification does not constitute a discretionary " + "grant award. The final outcome of the discretionary " + "grant application will be communicated at a later date. " + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><b>The merSETA Client Services </b></p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "Notification_DG_Application_Appeal_Successful.pdf", "pdf", null);
	}

	/**
	 * Locates Dg verifications that are with the SDF and the recommendation is approval
	 * @return
	 * @throws Exception
	 */
	public List<DgVerification> findDgVerificationWithSdfAndCrmDecisiion(CloRecommendationEnum crmRecommendationEnum) throws Exception {
		return dao.findDgVerificationWithSdfAndCrmDecisiion(crmRecommendationEnum);
	}

	public String validiationOnMandatoryGrantSubmission(DgVerification dg) throws Exception {
		String               errors  = "";
		List<MandatoryGrant> entries = mandatoryGrantService.findByWSPForDgVerificationIgnoreImport(dg.getWsp());
		for (MandatoryGrant mandatoryGrant : entries) {
			// check to see if no data provided
			if (mandatoryGrant.getQualification() == null && mandatoryGrant.getSkillsProgram() == null && mandatoryGrant.getSkillsSet() == null && mandatoryGrant.getNonCreditBearingIntervationTitle() == null && mandatoryGrant.getUnitStandard() == null) {

				// check to ensure service level is not null
				if (mandatoryGrantRecommendationService == null) {
					mandatoryGrantRecommendationService = new MandatoryGrantRecommendationService();
				}

				List<MandatoryGrantRecommendation> mgrList = mandatoryGrantRecommendationService.findByMG(mandatoryGrant);
				if (mgrList.size() != 0) {
					for (MandatoryGrantRecommendation recommendation : mgrList) {
						if (recommendation.getQualification() == null && recommendation.getSkillsProgram() == null && recommendation.getSkillsSet() == null && recommendation.getNonCreditBearingIntervationTitle() == null && recommendation.getUnitStandards() == null) {
							errors += "Provide SAQA Qualification / SAQA Unit Standards / Skills Set / Skills Program / Non-Credit Bearing Intervation Title For Reference ID: " + mandatoryGrant.getId().intValue() + " On Lastest Recommendation <br/> ";
						}
						break;
					}
				} else {
					errors += "Provide Recommendation For Reference ID: " + mandatoryGrant.getId().intValue() + ". SAQA Qualification / SAQA Unit Standard / Skills Programme / Skills Set / Non-Credit Bearing Intervation Title Required. <br/> ";
				}
			}
		}
		return errors;
	}

	/**
	 * Schedules processes start
	 */
	public void dgVerificationScheduleService() throws Exception {
		Date             today = GenericUtility.getStartOfDay(new Date());
		SimpleDateFormat sdf   = new SimpleDateFormat("dd MMMM yyyy");
		/*
		 * Business rule: 5 days to complete from task being sent Example: - CRM approves/rejects on 1st of September 2018 - 2nd, 3rd, 4th, 5th, 6th of September 2018 they can action - System must auto approve/reject DG on the 7th of September 2018 (morning schedule configuration) Java daysToComplate: 6 Excluding weekends
		 */
		Integer              daysToComplate          = 6;
		List<DgVerification> withSdfAwaitingApproval = findDgVerificationWithSdfAndCrmDecisiion(CloRecommendationEnum.Approval);
		for (DgVerification dgVerificationApproved : withSdfAwaitingApproval) {
			/**
			 * @author jonathanbowett Method 1: Add Days To Approved/Rejected Date And Do Date Comparisons Currently Used
			 */
			String information               = "5 Day System Approval Business Rule For: Discretionary Grant Verification. Date SDF Received Verification: " + sdf.format(GenericUtility.getStartOfDay(dgVerificationApproved.getCrmApprovalRejectionDate())) + ".";
			Date   compltedByDateIncWeekends = GenericUtility.getStartOfDay(GenericUtility.addDaysToDateExcludeWeekends(GenericUtility.getStartOfDay(dgVerificationApproved.getCrmApprovalRejectionDate()), daysToComplate));
			if (today.equals(compltedByDateIncWeekends) || today.after(compltedByDateIncWeekends)) {
				// information on changes
				information += " Changes: status change from " + dgVerificationApproved.getStatus().getFriendlyName() + " to " + ApprovalEnum.Approved.getFriendlyName() + ",";
				information += " set approved date to: " + sdf.format(getSynchronizedDate()) + ".";

				// auto approved the application
				dgVerificationApproved.setSystemApprovalRejection(true);
				acceptVerification(dgVerificationApproved, null, null);

				// creates log
				if (dgVerificationApproved.getWsp() != null && dgVerificationApproved.getWsp().getCompany() != null) {
					ScheduleChangesLogService.instance().addNewEntry(dgVerificationApproved.getWsp().getCompany(), null, dgVerificationApproved.getClass().getName(), dgVerificationApproved.getId(), information, false);
				} else {
					ScheduleChangesLogService.instance().addNewEntry(null, null, dgVerificationApproved.getClass().getName(), dgVerificationApproved.getId(), information, false);
				}
			}

			/**
			 * @author jonathanbowett Method 2: Calculate days between dates and use integer comparisons Not Used
			 */
			// Integer daysBetweenDates =
			// GenericUtility.getDaysBetweenDates(GenericUtility.getStartOfDay(dgVerificationApproved.getCrmApprovalRejectionDate()),
			// today);
			// if (daysBetweenDates >= daysToComplate) {
			// dgVerificationApproved.setSystemApprovalRejection(true);
			// acceptVerification(dgVerificationApproved, null, null);
			// }
		}
		withSdfAwaitingApproval = null;

		/*
		 * locates the DG verifications that are currently with the SDF awaiting accepting rejection or appeal process Awaiting confirmation
		 * 
		 * 14 Days business rule: refer to example above
		 */
		List<DgVerification> withSdfAwaitingSdfRejectionOrAppeal = findDgVerificationWithSdfAndCrmDecisiion(CloRecommendationEnum.Rejection);
		daysToComplate = 15;
		for (DgVerification dgVerificationRejected : withSdfAwaitingSdfRejectionOrAppeal) {
			Date   compltedByDateIncWeekends = GenericUtility.getStartOfDay(GenericUtility.addDaysToDate(GenericUtility.getStartOfDay(dgVerificationRejected.getCrmApprovalRejectionDate()), daysToComplate));
			String information               = "14 Day System Rejection Business Rule For: Discretionary Grant Verification. Date SDF Received erification: " + sdf.format(GenericUtility.getStartOfDay(dgVerificationRejected.getCrmApprovalRejectionDate())) + ".";
			if (today.equals(compltedByDateIncWeekends) || today.after(compltedByDateIncWeekends)) {
				information += " Changes: status change from " + dgVerificationRejected.getStatus().getFriendlyName() + " to " + ApprovalEnum.Rejected.getFriendlyName() + ",";
				information += " set approved date to: " + sdf.format(getSynchronizedDate()) + ".";
				dgVerificationRejected.setSystemApprovalRejection(true);
				dgVerificationRejected.setStatus(ApprovalEnum.Rejected);
				dgVerificationRejected.setWithSdf(false);
				dgVerificationRejected.setApprovedDate(getSynchronizedDate());
				update(dgVerificationRejected);
				TasksService.instance().completeTaskByTargetKey(DgVerification.class.getName(), dgVerificationRejected.getId());
				// creates log
				if (dgVerificationRejected.getWsp() != null && dgVerificationRejected.getWsp().getCompany() != null) {
					ScheduleChangesLogService.instance().addNewEntry(dgVerificationRejected.getWsp().getCompany(), null, dgVerificationRejected.getClass().getName(), dgVerificationRejected.getId(), information, false);
				} else {
					ScheduleChangesLogService.instance().addNewEntry(null, null, dgVerificationRejected.getClass().getName(), dgVerificationRejected.getId(), information, false);
				}
			}
		}
		// clears out of memort
		withSdfAwaitingSdfRejectionOrAppeal = null;
	}

	/**
	 * Schedules processes end
	 */
	public List<MandatoryGrant> viewDataGeneration(Wsp wsp) throws Exception {
		// version 6
		return mandatoryGrantDetailService.findSummarizedDataVersionSix(wsp, WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID, HAJConstants.WSP_ENROLEMNT_STATUS_WSP_NEW);
	}

	/*
	 * Fix Data Start
	 */
	public void generateMissingDataVerficiation(Wsp wsp) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		// version 6
		List<MandatoryGrant> mg = mandatoryGrantDetailService.findSummarizedDataVersionSix(wsp, WspReportEnum.WSP, HAJConstants.DISC_FUNDING_ID, HAJConstants.WSP_ENROLEMNT_STATUS_WSP_NEW);
		for (MandatoryGrant mandatoryGrant : mg) {
			// WPA Check
			if (mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired() != null && mandatoryGrant.getInterventionType().getWorkplaceApprovalRequired()) {
				// Intervention Type WPA check
				workPlaceApprovalService.checkWorkplaceApproval(wsp.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
			} else if (mandatoryGrant.getQualification() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired() != null && mandatoryGrant.getQualification().getWorkplaceApprovalRequired()) {
				// qualification WPA check
				workPlaceApprovalService.checkWorkplaceApproval(wsp.getCompany(), mandatoryGrant.getQualification(), mandatoryGrant.getOfoCodes(), null, null);
			} else if (mandatoryGrant.getSkillsProgram() != null && mandatoryGrant.getSkillsProgram().getQualification() != null) {
				// skills program qualification WPA check
				Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsProgram().getQualification().getId());
				if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
					workPlaceApprovalService.checkWorkplaceApproval(wsp.getCompany(), qualification, null, null, null);
				}
			} else if (mandatoryGrant.getSkillsSet() != null && mandatoryGrant.getSkillsSet().getQualification() != null) {
				// skills set qualification WPA check
				Qualification qualification = QualificationService.instance().findByKey(mandatoryGrant.getSkillsSet().getQualification().getId());
				if (qualification != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
					workPlaceApprovalService.checkWorkplaceApproval(wsp.getCompany(), qualification, null, null, null);
				}
			}
			mandatoryGrantService.applyInterventionData(mandatoryGrant);
			mandatoryGrantService.populateAdditionalInformationForGeneration(mandatoryGrant);
		}
		if (!mg.isEmpty()) {
			createList.addAll(mg);
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
	}

	public void reopenDgVerification(DgVerification dgVerification, Users sessionUser) throws Exception {
		// fail safe
		DgAllocationParentService dgapService = new DgAllocationParentService();
		if (dgapService.countByWspId(dgVerification.getWsp().getId()) != 0) {
			throw new Exception("FAIL SAFE: Allocation Data Against Grant Application. Unable to proceed!");
		}

		// log actions
		StringBuilder information = new StringBuilder();
		information.append("DG Verification re-opened to region Clo to process on: " + HAJConstants.sdfYYYYMMDDHHMMSSSSS.format(getSynchronizedDate()) + ". ");

		// Verification Information
		information.append("Orginal Status: " + (dgVerification.getStatus() != null ? dgVerification.getStatus().getFriendlyName() : "Not Started") + " to Not Started. ");
		dgVerification.setStatus(null);

		dgVerification.setReadyForAllocation(false);
		dgVerification.setSystemApprovalRejection(false);
		information.append("Date SDF Appealed: " + (dgVerification.getDateAppealed() != null ? HAJConstants.sdfDDMMYYYYHHmm.format(dgVerification.getDateAppealed()) : "Did Not Appeal") + " Now Cleared. ");
		dgVerification.setDateAppealed(null);
		information.append("Indicator if appealed: " + (dgVerification.getApplicationAppealed() != null && dgVerification.getApplicationAppealed() ? "SDF Appealed" : "Did Not Appeal") + " Now Cleared. ");
		dgVerification.setApplicationAppealed(null);
		dgVerification.setWithSdf(false);
		information.append("Approval Date: " + (dgVerification.getApprovedDate() != null ? HAJConstants.sdfDDMMYYYYHHmm.format(dgVerification.getApprovedDate()) : "Not Approved") + " Now Cleared. ");
		dgVerification.setApprovedDate(null);
		information.append("Withdraw Reason: " + (dgVerification.getDiscretionalWithdrawalAppealEnum() != null ? dgVerification.getDiscretionalWithdrawalAppealEnum().getFriendlyName() : "Did Not Indicate") + " Now Cleared. ");
		dgVerification.setDiscretionalWithdrawalAppealEnum(null);

		// CLO Information
		information.append("Clo Alteration: " + (dgVerification.getCloAlteration() != null && dgVerification.getCloAlteration() ? "Clo Altered Information" : "Clo Non-Altered On Information") + " Now Cleared. ");
		dgVerification.setCloAlteration(null);
		information.append("Clo Recommendation: " + (dgVerification.getCloRecommendation() != null ? dgVerification.getCloRecommendation().getFriendlyName() : "Did Not Recommend") + " Now Cleared. ");
		dgVerification.setCloRecommendation(null);
		information.append("Clo User ID: " + (dgVerification.getCloUser() != null && dgVerification.getCloUser().getId() != null ? dgVerification.getCloUser().getId() : "No Clo Assigned") + " Now Cleared. ");
		dgVerification.setCloUser(null);

		// CRM information
		information.append("Crm Appealed Date Approved: " + (dgVerification.getCrmAppealedDateApproved() != null ? HAJConstants.sdfDDMMYYYYHHmm.format(dgVerification.getCrmAppealedDateApproved()) : "Not Populated") + " Now Cleared. ");
		dgVerification.setCrmAppealedDateApproved(null);
		information.append("Crm Appealed Date Rejection: " + (dgVerification.getCrmApprovalRejectionDate() != null ? HAJConstants.sdfDDMMYYYYHHmm.format(dgVerification.getCrmApprovalRejectionDate()) : "Not Populated") + " Now Cleared. ");
		dgVerification.setCrmApprovalRejectionDate(null);
		information.append("Crm Recommendation: " + (dgVerification.getCrmDecision() != null ? dgVerification.getCrmDecision().getFriendlyName() : "Did Not Recommend") + " Now Cleared. ");
		dgVerification.setCrmDecision(null);
		information.append("Crm User ID: " + (dgVerification.getCrmUser() != null && dgVerification.getCrmUser().getId() != null ? dgVerification.getCrmUser().getId() : "No Crm Assigned") + " Now Cleared. ");
		dgVerification.setCrmUser(null);

		information.append("Rejection Reasons if assigned will be set to soft deleted and all open tasks assigned have been closed.");

		// run update
		update(dgVerification);

		// add log of change
		ScheduleChangesLogService.instance().addNewEntry(dgVerification.getWsp().getCompany(), null, dgVerification.getClass().getName(), dgVerification.getId(), information.toString(), true);

		// update any rejection reasons found and set to not lastest entries
		DgVerificationRejectionInformationService.instance().updateOldEntries(dgVerification, sessionUser, null, RolesService.instance().findByKey(HAJConstants.ROLES_CLIENT_RELATIONS_MANAGER_ID));
		DgVerificationRejectionInformationService.instance().updateOldEntries(dgVerification, sessionUser, null, RolesService.instance().findByKey(HAJConstants.ROLES_CLIENT_LIAISON_OFFICER_ID));

		// close all open tasks as verification is initiated by region clo
		TasksService.instance().completeTaskByTargetKey(dgVerification.getClass().getName(), dgVerification.getId());
	}

	/*
	 * Fix Data End
	 */

	/**
	 * Reporting Start
	 */
	public Integer countDgByStatusAndFinYear(ApprovalEnum status, List<Integer> finYear) throws Exception {
		return dao.countDgByStatusAndFinYear(status, finYear);
	}

	public Integer countDgByStatusAndFinYear(ApprovalEnum status, Integer finYear) throws Exception {
		return dao.countDgByStatusAndFinYear(status, finYear);
	}

	public Integer countDgByStatusNullAndFinYear(List<Integer> finYear) throws Exception {
		return dao.countDgByStatusNullAndFinYear(finYear);
	}

	public Integer countDgByStatusNullAndFinYear(Integer finYear) throws Exception {
		return dao.countDgByStatusNullAndFinYear(finYear);
	}

	public Integer countAllDgFinYear(List<Integer> finYear) throws Exception {
		return dao.countAllDgFinYear(finYear);
	}

	public Integer countAllDgFinYear(Integer finYear) throws Exception {
		return dao.countAllDgFinYear(finYear);
	}

	/**
	 * Populates status count report for DG
	 * @param finYear
	 * @return List<StatusReportBean>
	 * @throws Exception
	 * Used Status though DG life Cycle ApprovalEnum.PendingApproval ApprovalEnum.Completed ApprovalEnum.Approved ApprovalEnum.Rejected ApprovalEnum.PendingFinalApproval ApprovalEnum.Withdrawn ApprovalEnum.Approved ApprovalEnum.Rejected
	 */
	public List<StatusReportBean> populateStatusReportDg(List<Integer> finYear) throws Exception {
		List<StatusReportBean> reportList = new ArrayList<>();
		reportList.add(new StatusReportBean("Total Discretionary Grant Verifications", countAllDgFinYear(finYear)));
		reportList.add(new StatusReportBean("Not Started / In Progress", countDgByStatusNullAndFinYear(finYear)));
		for (ApprovalEnum status : ApprovalEnum.values()) {
			if (status != ApprovalEnum.WaitingForManager) {
				if (status != ApprovalEnum.PendingSignOff) {
					if (status != ApprovalEnum.PendingAcceptingCodeOfConduct) {
						if (status != ApprovalEnum.NA) {
							if (status != ApprovalEnum.AwaitingDHET) {
								if (status != ApprovalEnum.Recommended) {
									if (status != ApprovalEnum.Appealed) {
										if (status != ApprovalEnum.Completed) {
											reportList.add(new StatusReportBean(status.getFriendlyName(), countDgByStatusAndFinYear(status, finYear)));
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return reportList;
	}

	public List<StatusReportBean> populateStatusReportDg(Integer finYear) throws Exception {
		List<StatusReportBean> reportList = new ArrayList<>();
		reportList.add(new StatusReportBean("Total Discretionary Grant Verifications", countAllDgFinYear(finYear)));
		reportList.add(new StatusReportBean("Not Started / In Progress", countDgByStatusNullAndFinYear(finYear)));
		for (ApprovalEnum status : ApprovalEnum.values()) {
			if (status != ApprovalEnum.WaitingForManager) {
				if (status != ApprovalEnum.PendingSignOff) {
					if (status != ApprovalEnum.PendingAcceptingCodeOfConduct) {
						if (status != ApprovalEnum.NA) {
							if (status != ApprovalEnum.AwaitingDHET) {
								if (status != ApprovalEnum.Recommended) {
									if (status != ApprovalEnum.Appealed) {
										if (status != ApprovalEnum.Completed) {
											reportList.add(new StatusReportBean(status.getFriendlyName(), countDgByStatusAndFinYear(status, finYear)));
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return reportList;
	}

	private List<DgVerification> populateInformation(List<DgVerification> list) throws Exception {
		for (DgVerification dg : list) {
			populateDGVerificationRejectReasons(dg);
			if (dg != null && dg.getWsp() != null) {
				populateWspRejectReasons(dg.getWsp());
			}
		}
		return list;
	}

	public void populateDGVerificationRejectReasons(DgVerification dg) throws Exception {
		if (dg.getStatus() == ApprovalEnum.Rejected) {
			// populate last employee task user
			RejectReasonsService rejectReasonsService = new RejectReasonsService();
			List<RejectReasons>  rrList               = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(dg.getId(), DgVerification.class.getName(), ConfigDocProcessEnum.DG_VERIFICATION);
			rejectReasonsService = null;
			String results = "";
			int    count   = 1;
			for (RejectReasons rejectReasons : rrList) {
				if (count == rrList.size()) {
					results += rejectReasons.getDescription();
				} else {
					results += rejectReasons.getDescription() + ", ";
				}
				count++;
			}
			if (results != "") {
				dg.setDgVerificationRejectionReasons(results);
			} else {
				dg.setDgVerificationRejectionReasons("None Found");
			}
		} else {
			dg.setDgVerificationRejectionReasons("N/A");
			dg.setDgVerificationRejectionReasons("N/A");
		}
	}

	public void populateWspRejectReasons(Wsp wsp) throws Exception {
		if (wsp.getWspStatusEnum() == WspStatusEnum.Rejected) {
			// populate last employee task user
			RejectReasonsService rejectReasonsService = new RejectReasonsService();
			List<RejectReasons>  rrList               = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(wsp.getId(), Wsp.class.getName(), ConfigDocProcessEnum.WSP);
			rejectReasonsService = null;
			String results = "";
			int    count   = 1;
			for (RejectReasons rejectReasons : rrList) {
				if (count == rrList.size()) {
					results += rejectReasons.getDescription();
				} else {
					results += rejectReasons.getDescription() + ", ";
				}
				count++;
			}
			if (results != "") {
				wsp.setRejectionReasons(results);
			} else {
				wsp.setRejectionReasons("None Found");
			}
		} else {
			wsp.setRejectionReasons("N/A");
			wsp.setSystemApprovalRejectionMessage("N/A");
		}
	}

	public List<DgVerificationReportBean> getAllVerificationsByFinYear(Integer finYear) throws Exception {
		return dao.getAllVerificationsByFinYear(finYear);
	}

	public List<DgVerificationReportBean> getAllVerificationsByFinYearWithCloCrmId(Integer finYear, Long userId) throws Exception {
		return dao.getAllVerificationsByFinYearWithCloCrmId(finYear, userId);
	}

	@SuppressWarnings("unchecked")
	public List<DgVerification> allDgVerificationReporting(Class<DgVerification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateVerificationReportInfo((List<DgVerification>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}

	public List<DgVerification> populateVerificationReportInfo(List<DgVerification> list) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		for (DgVerification dgVerification : list) {
			if (dgVerification.getCloUser() != null && dgVerification.getCloUser().getId() != null) {
				dgVerification.setCloUser(usersService.findByKey(dgVerification.getCloUser().getId()));
			}
			if (dgVerification.getCrmUser() != null && dgVerification.getCrmUser().getId() != null) {
				dgVerification.setCrmUser(usersService.findByKey(dgVerification.getCrmUser().getId()));
			}
		}
		return list;
	}

	/**
	 * Reporting End
	 */
}