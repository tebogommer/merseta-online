package haj.com.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.bean.CompanyLearnersBean;
import haj.com.bean.InterventionBean;
import haj.com.bean.LPMFM035ModularTrainingBean;
import haj.com.bean.LearnersMentorBean;
import haj.com.bean.LostTimeReasonBean;
import haj.com.bean.SkillsProgrammeSkillsSetBean;
import haj.com.bean.UnitStandardBean;
import haj.com.bean.WorkplaceBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.CompanyLearnersDAO;
import haj.com.entity.Address;
import haj.com.entity.Appointment;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersChange;
import haj.com.entity.CompanyLearnersLostTime;
import haj.com.entity.CompanyLearnersProgress;
import haj.com.entity.CompanyLearnersTermination;
import haj.com.entity.CompanyLearnersTradeTest;
import haj.com.entity.CompanyLearnersTransfer;
import haj.com.entity.CompletionLetter;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DateChangeMultipleSelection;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.OfoCodes;
import haj.com.entity.ProcessRoles;
import haj.com.entity.ProjectImplementationPlanLearners;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.entity.ReviewCommitteeMeetingUsers;
import haj.com.entity.SDFCompany;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.ScheduledEventUsers;
import haj.com.entity.Signoff;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.WorkPlaceApprovalSites;
import haj.com.entity.datamodel.CompanyLearnersLearnershipDataModel;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyLearnersTransferRecommendation;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.CreatedByEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.LearnerChangeTypeEnum;
import haj.com.entity.enums.LearnerDocRequirements;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.LearnerTransferApproval;
import haj.com.entity.enums.LearnerTransferTypeEnum;
import haj.com.entity.enums.LostTimeReason;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.ProgressType;
import haj.com.entity.enums.SignoffByEnum;
import haj.com.entity.enums.SubmissionEnum;
import haj.com.entity.enums.TerminationTypeEnum;
import haj.com.entity.enums.TradeTestProgressEnum;
import haj.com.entity.enums.TradeTestTypeEnum;
import haj.com.entity.enums.TradeTypeEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.enums.TransferRequestTypeEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.DesignatedTrade;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.Designation;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.DesignatedTradeLevelService;
import haj.com.service.lookup.DesignatedTradeService;
import haj.com.service.lookup.LearnershipService;
import haj.com.service.lookup.NonCreditBearingIntervationTitleService;
import haj.com.service.lookup.QualificationService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RolesService;
import haj.com.service.lookup.SkillsProgramService;
import haj.com.service.lookup.SkillsSetService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

public class CompanyLearnersService extends AbstractService {

	/** The dao. */
	private CompanyLearnersDAO dao = new CompanyLearnersDAO();
	//private EtqaService etqaService = new EtqaService();
	private UsersService usersService = new UsersService();
	private RegisterService registerService = new RegisterService();
	private ConfigDocService configDocService = new ConfigDocService();
	private CompanyService companyService = new CompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private AppointmentService appointmentService = new AppointmentService();
	private ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaSevice = new ReviewCommitteeMeetingAgendaService();
	private ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService = new ReviewCommitteeMeetingUsersService();
	//private ReviewCommitteeMeetingService reviewCommitteeMeetingService = new ReviewCommitteeMeetingService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private ActiveContractDetailService activeContractDetailService = new ActiveContractDetailService();
	private SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
	private UnitStandardsService unitStandardsService = new UnitStandardsService();
	private RegionService regionService;
	/** Instance of service level */
	private static CompanyLearnersService companyLearnersService;
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private RolesService rolesService = new RolesService();
	private WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
	private DesignatedTradeLevelService designatedTradeLevelService;
	private DesignatedTradeService designatedTradeService;

	public static synchronized CompanyLearnersService instance() {
		if (companyLearnersService == null) {
			companyLearnersService = new CompanyLearnersService();
		}
		return companyLearnersService;
	}

	/**
	 * Get all CompanyLearners
	 * 
	 * @author TechFinium
	 * @see CompanyLearners
	 * @return a list of {@link haj.com.entity.CompanyLearners}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearners> allCompanyLearners() throws Exception {
		return dao.allCompanyLearners();
	}

	/**
	 * Create or update CompanyLearners.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearners
	 */
	public void create(CompanyLearners entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update CompanyLearners.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearners
	 */
	public void update(CompanyLearners entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyLearners.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearners
	 */
	public void delete(CompanyLearners entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyLearners}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearners
	 */
	public CompanyLearners findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	public CompanyLearners findByKey(long id, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return resolveEverything(dao.findByKey(id), configDocProcessEnum);
	}

	public CompanyLearnersTransfer findTransferByKey(long id, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		CompanyLearnersTransfer entity = dao.findTransferByKey(id);
		if (configDocProcessEnum == ConfigDocProcessEnum.LearnerTransferSiteVisit) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.LearnerTransfer));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.LearnerTransferSiteVisit, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
		/*
		 * else { entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.
		 * getClass().getName(), entity.getId(), configDocProcessEnum)); List<Doc>
		 * siteVisitDocs=DocService.instance().searchByTargetKeyAndClass(entity.getClass
		 * ().getName(), entity.getId(), ConfigDocProcessEnum.LearnerTransferSiteVisit);
		 * if(siteVisitDocs !=null && siteVisitDocs.size()>0) { List<Doc>
		 * allDocs=entity.getDocs(); allDocs.addAll(siteVisitDocs);
		 * entity.setDocs(allDocs); } List<ConfigDoc> l =
		 * configDocService.findByProcessNotUploaded(entity.getClass().getName(),
		 * entity.getId(), configDocProcessEnum, CompanyUserTypeEnum.User); if (l !=
		 * null && l.size() > 0) { l.forEach(a -> { entity.getDocs().add(new Doc(a));
		 * }); } }
		 */

		return entity;
	}

	/**
	 * Find CompanyLearners by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyLearners}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearners
	 */
	public List<CompanyLearners> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	public CompanyLearners resolveEverything(CompanyLearners entity, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		// entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(),
		// entity.getId(), configDocProcessEnum));
		// prepareNewRegistration(configDocProcessEnum, entity);
		if (entity.getReviewCommitteeMeeting() != null) {
			entity.getReviewCommitteeMeeting().setReviewCommitteeMeetingUsersList((ArrayList<ReviewCommitteeMeetingUsers>) reviewCommitteeMeetingUsersService.findByReviewCommitteeMeeting(entity.getReviewCommitteeMeeting().getId()));
			entity.getReviewCommitteeMeeting().setReviewCommitteeMeetingAgendaList((ArrayList<ReviewCommitteeMeetingAgenda>) reviewCommitteeMeetingAgendaSevice.findByReviewCommitteeMeeting(entity.getReviewCommitteeMeeting().getId()));

		}
		return entity;
	}

	public void updateSchedule(CompanyLearners entity) {

	}

	public CompanyLearnersTransfer findTransferByKeyWithoutMissingDocs(long id, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		CompanyLearnersTransfer entity = dao.findTransferByKey(id);
		entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcessEnum));
		return entity;
	}
	
	public void prepareNewRegistrationDocs(ConfigDocProcessEnum configDocProcess, CompanyLearners entity, Tasks tasks) throws Exception {
		List<ConfigDoc> l;
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));				
			if(entity != null && entity.getInterventionType() != null && entity.getInterventionType().getBusary() != null && entity.getInterventionType().getBusary()) {
				l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), tasks.getProcessRole());
			}else {
				l = ConfigDocService.instance().findByProcessRolesNotUploadedNotBusary(entity.getClass().getName(), entity.getId(), tasks.getProcessRole());
			}			
			if (l != null && l.size() > 0) {				
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			l = configDocService.findByProcess(configDocProcess, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, CompanyLearners entity, Tasks tasks) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
			//entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			//List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcess, CompanyUserTypeEnum.User);
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), tasks.getProcessRole());
			if (l != null && l.size() > 0) {
				
				l.forEach(a -> {
					if(entity != null && entity.getInterventionType() != null && entity.getInterventionType().getBusary() != null && entity.getInterventionType().getBusary()) {
						entity.getDocs().add(new Doc(a));
					}else if(a.getLearnerDocRequirements() != null &&a.getLearnerDocRequirements() == LearnerDocRequirements.Bursary) { 
						//do nothing
					}else {
						entity.getDocs().add(new Doc(a));
					}
					
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(configDocProcess, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
		checkDocRequired(entity);
		/*if ((configDocProcess == ConfigDocProcessEnum.Learner || configDocProcess == ConfigDocProcessEnum.LearnerRegistration)&& entity != null && entity.getInterventionType() != null) {
			if (!entity.getInterventionType().getBusary()) {
				if (entity.getDocs() != null && entity.getDocs().size() > 3) {
					entity.getDocs().remove(entity.getDocs().size() - 3);
					entity.getDocs().remove(entity.getDocs().size() - 2);
					if(tasks != null && tasks.getProcessRole() != null && tasks.getProcessRole().getRolePermission() != null && tasks.getProcessRole().getRolePermission() != UserPermissionEnum.FinalUpload) {
						entity.getDocs().remove(entity.getDocs().size() - 1);
					}
				}
			}
		}*/
	}
	
	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, CompanyLearners entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcess, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(configDocProcess, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
		//checkDocRequired(entity);
		/*if ((configDocProcess == ConfigDocProcessEnum.Learner || configDocProcess == ConfigDocProcessEnum.LearnerRegistration)&& entity != null && entity.getInterventionType() != null) {
			if (!entity.getInterventionType().getBusary()) {
				// System.out.println(entity.getInterventionType().getDescription());
				if (entity.getDocs() != null && entity.getDocs().size() > 3) {
					entity.getDocs().remove(entity.getDocs().size() - 3);
					entity.getDocs().remove(entity.getDocs().size() - 2);
				}
			}
		}*/
	}
	
	public void prepareNewRegistration(CompanyLearners entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		} 		
	}

	public void prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum configDocProcess, CompanyLearners entityDoc, CompanyLearners entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {

			if (processRoles.getCompanyUserType() == null) entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}

			/*
			 * l = ConfigDocService.instance().findByProcessA(configDocProcess); if (l !=
			 * null && l.size() > 0) { l.forEach(a -> { entityDoc.getDocs().add(new Doc(a));
			 * }); }
			 */

		} else {
			entityDoc.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}
		}

		if ((configDocProcess == ConfigDocProcessEnum.Learner || configDocProcess == ConfigDocProcessEnum.LearnerRegistration) && entity != null && entity.getInterventionType() != null) {
			if (!entity.getInterventionType().getBusary()) {
				// System.out.println(entity.getInterventionType().getDescription());
				if (entity.getDocs() != null && entity.getDocs().size() > 3) {
					entity.getDocs().remove(4);
					entity.getDocs().remove(entity.getDocs().size() - 1);
				}
			}
		}

	}
	
	public void prepareLearnerRegistrationDocs(ConfigDocProcessEnum configDocProcess, CompanyLearners entityDoc, CompanyLearners entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			if (processRoles.getCompanyUserType() == null) entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}
		} else {
			entityDoc.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public CompanyLearners checkDocRequired(CompanyLearners entity)throws Exception {		
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if(entity != null && entity.getInterventionType() != null && entity.getInterventionType().getBusary() != null && entity.getInterventionType().getBusary()) {
					doc.setRequired(true);
				}else {
					if(doc.getConfigDoc().getLearnerDocRequirements() == LearnerDocRequirements.Bursary) {
						doc.setRequired(false);
						entity.getDocs().remove(doc);						
					}else {
						doc.setRequired(true);
					}
				}				
			}
		}	
		return entity;
	}

	public void locateDocsByTargetClassAndKey(CompanyLearners entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		}
	}

	public void prepareNewRegistration(ConfigDocProcessEnum configDocProcess, CompanyLearners entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {

			if (entity.getStatus() != null && entity.getStatus() == ApprovalEnum.RejectedByLearnerReview) {
				entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.Learner));
			} else {
				if (processRoles != null && processRoles.getCompanyUserType() == null) {
					entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
				} else {
					entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));
				}

				List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

				/*
				 * if (l != null && l.size() > 0) { l.forEach(a -> { entity.getDocs().add(new
				 * Doc(a)); }); }
				 */

				if (l != null && l.size() > 0) {
					l.forEach(a -> {
						if (entity.getInterventionType().getBusary()) {
							// entity.getDocs().add(new Doc(a));
						} else {
							if (a.getName().contains("Duly signed bursary agreement between the learner and the SETA or its contracted agent")) {
								// entity.getDocs().add(new Doc(a));
							} else if (a.getName().contains("Proof of registration/admission from the Institution")) {
								// entity.getDocs().add(new Doc(a));
							} else {
								entity.getDocs().add(new Doc(a));
							}
						}
					});
				}
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	/**
	 * Lazy load CompanyLearners
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyLearners}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearners> allCompanyLearners(int first, int pageSize) throws Exception {
		return dao.allCompanyLearners(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of CompanyLearners for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the CompanyLearners
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyLearners.class);
	}

	/**
	 * Lazy load CompanyLearners with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            CompanyLearners class
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
	 * @return a list of {@link haj.com.entity.CompanyLearners} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> allCompanyLearners(Class<CompanyLearners> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CompanyLearners>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of CompanyLearners for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            CompanyLearners class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyLearners entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyLearners> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void applySaqaData(CompanyLearners companyLearners) throws Exception {
		if (companyLearners.getQualification() != null && companyLearners.getQualification().getNqflevel() != null) {
			companyLearners.setNqfLevels(companyLearners.getQualification().getNqflevel());
			companyLearners.setInterventionLevel(companyLearners.getNqfLevels().getInterventionLevel());
		}
	}

	public void applyNQFData(MandatoryGrantDetail mandatoryGrantDetail) throws Exception {
		mandatoryGrantDetail.setInterventionLevel(mandatoryGrantDetail.getNqfLevels().getInterventionLevel());
	}

	public void applySkillsSet(CompanyLearners companyLearners) throws Exception {
		if (companyLearners.getSkillsSet() != null) {
			if (companyLearners.getSkillsSet().getQualification() != null) {
				companyLearners.setNqfLevels(companyLearners.getSkillsSet().getQualification().getNqflevel());
				companyLearners.setInterventionLevel(companyLearners.getNqfLevels().getInterventionLevel());
			}
		}
	}

	public void applySkillsProgram(CompanyLearners companyLearners) throws Exception {
		if (companyLearners.getSkillsProgram() != null) {
			if (companyLearners.getSkillsProgram().getQualification() != null) {
				companyLearners.setNqfLevels(companyLearners.getSkillsProgram().getQualification().getNqflevel());
				companyLearners.setInterventionLevel(companyLearners.getNqfLevels().getInterventionLevel());

			}
		}
	}

	public void applyInterventionData(CompanyLearners companyLearners) throws Exception {
		if (companyLearners.getInterventionType() == null) {
			companyLearners.setPivotNonPivot(null);
		} else {
			companyLearners.setPivotNonPivot(companyLearners.getInterventionType().getPivotNonPivot());
			if (companyLearners.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				companyLearners.setNqfAligned(YesNoLookupService.instance().findByKey(YesNoEnum.Yes.getYesNoLookupId()));
			} else {
				companyLearners.setNqfAligned(YesNoLookupService.instance().findByKey(HAJConstants.NO_ID));
			}
		}
	}

	/**
	 * Create or update Users.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Users
	 */
	public void createLearner(Users createUser, Users entity, Company company, Company trainingProvider, CompanyLearners cl, boolean submitWorkflow, boolean requireGaurdian, List<UsersLanguage> usersLanguages, List<UsersDisability> usersDisabilityList) throws Exception {
		
		if (company == null) {
			throw new Exception("Employer error on registration");
		}
		if (company != null && company.getId() == null) {
			throw new Exception("Employer error on registration");
		}

		if (trainingProvider == null) {
			throw new Exception("Provider error on registration");
		}
		if (trainingProvider != null && trainingProvider.getId() == null) {
			throw new Exception("Provider error on registration");
		}
		
		if(cl.getSourceFunding()== null) {
			throw new Exception("Provide Source Funding");
		}
		
		if(cl.getMersetaFunded() == YesNoEnum.Yes) {
			if(cl.getProjectImplementationPlan() != null) {			
				int count = countLearnersAssignedToPip(cl);
				if(count == -1) {
					throw new Exception("Project implementation error on registration when counting learners");
				}
				if(count >= (cl.getProjectImplementationPlan().getCoFundingLearnersAwarded() + cl.getProjectImplementationPlan().getFullyFundedLearnerAwarded())){
					throw new Exception("You have exceeded the number of learners allocated for this intervention as per the project implementation plan");
				}
			}else {
				throw new Exception("Project inplemantation error on registration");
			}
		}
		if(company.getOrganisationType() == null) {
			throw new Exception("The company does not have organasation type. Please contact your administrator");
		}
		cl.setEmployer(company);
		
		if(company.getOrganisationType().getWorkplaceApprovalRequired() && cl.getInterventionType().getWorkplaceApprovalRequired() != null &&  cl.getInterventionType().getWorkplaceApprovalRequired()) {
			if(getCompanyLearnerQualification(cl) != null && getCompanyLearnerQualification(cl).getId() != null) {
				WorkPlaceApproval workPlaceApproval = null;
				
				if(cl.getSite() != null) {
					workPlaceApproval = workPlaceApprovalService.findApprovedWorkPlaceApprovalByCompanyAndQualificationAndSites(company, getCompanyLearnerQualification(cl), ApprovalEnum.Approved, cl.getSite().getId());
				}else {
					workPlaceApproval = workPlaceApprovalService.findApprovedWorkPlaceApprovalByCompanyAndQualification(company, getCompanyLearnerQualification(cl), ApprovalEnum.Approved);
				}
				
				if(workPlaceApproval == null) {
					throw new Exception("The company is not workplace approved for this qualification " + getCompanyLearnerQualification(cl).getDescription());
				}
				if(cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship || cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
					checkSmeQualificationMentor(cl, getCompanyLearnerQualification(cl), workPlaceApproval);
				}
				
				/*if(cl.getSite() == null) {
					throw new Exception("No available mentor for this learner");
				}*/
			}
		}
		
		
		Users gaurdian = null;
		if (requireGaurdian) {
			gaurdian = entity.getLegalGaurdian();
		} else {
			gaurdian = null;
			entity.setLegalGaurdian(gaurdian);
		}

		List<IDataEntity> createBatch = new ArrayList<>();
		List<IDataEntity> updateBatch = new ArrayList<>();
		String error = "";
		String pwdLearner = "";
		cl.setCreateUser(createUser);
		cl.setMersetaCompany(true);
		if (entity.getId() == null) {
			usersService.checkEmailUsedEmailOnly(entity.getEmail());
		}
		if (entity.getLegalGaurdian() != null && entity.getLegalGaurdian().getId() == null) {
			usersService.checkEmailUsedEmailOnly(entity.getEmail());
		}

		List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
		if (cl.getDocs() != null) {
			for (Doc doc : cl.getDocs()) {
				doc.setCompany(null);
				doc.setVersionNo(1);
				// doc.setForUsers(formUser);
				doc.setUsers(createUser);
				doc.setTargetKey(cl.getId());
				doc.setTargetClass(CompanyLearners.class.getName());
				docsDataEntities.add(doc);
				if (doc.getData() != null) {
					docsDataEntities.add(new DocByte(doc.getData(), doc));
					docsDataEntities.add(new DocumentTracker(doc, createUser, new java.util.Date(), DocumentTrackerEnum.Upload));
				} else {
					throw new Exception("Please upload required document ");
				}
			}
		}

		if (docsDataEntities.size() > 0) {
			dao.createBatch(docsDataEntities);
		}

		/*
		 * if (cl.getDocs() != null) { for (Doc doc : cl.getDocs()) { if (doc.getId() !=
		 * null) { DocByte docByte =
		 * DocService.instance().findDocByteByKey(doc.getId()); if (docByte != null)
		 * doc.setData(docByte.getData()); } if (doc.getConfigDoc().getReqImmediate()) {
		 * if ((doc.getData() == null || doc.getData().length == 0) &&
		 * doc.getConfigDoc().getRequiredDocument() != null &&
		 * doc.getConfigDoc().getRequiredDocument()) { error += "Upload " +
		 * doc.getConfigDoc().getName() + " for " + cl.getUser().getFirstName() + " " +
		 * cl.getUser().getLastName(); } } } }
		 */

		if (error.length() > 0) throw new ValidationException(error);

		if (gaurdian != null) {
			if (gaurdian.getId() == null) {
				AddressService.instance().saveAddresses(entity.getResidentialAddress(), gaurdian.getPostalAddress());
				String pwd = GenericUtility.genPassord();
				gaurdian.setPassword(LogonService.encryptPassword(pwd));
				gaurdian.setStatus(UsersStatusEnum.EmailNotConfrimed);
				gaurdian.setEmailGuid(UUID.randomUUID().toString());
				gaurdian.setRegistrationDate(new java.util.Date());
				gaurdian.setAdmin(false);
				gaurdian.setAcceptPopi(false);
				createBatch.add(gaurdian);
			} else {
				updateBatch.add(gaurdian);
			}

			entity.setLegalGaurdian(gaurdian);
		}

		if (entity.getId() == null) {
			pwdLearner = GenericUtility.genPassord();
			new LogonService();
			entity.setPassword(LogonService.encryptPassword(pwdLearner));
			entity.setStatus(UsersStatusEnum.EmailNotConfrimed);
			entity.setEmailGuid(UUID.randomUUID().toString());
			entity.setRegistrationDate(new java.util.Date());
			entity.setAdmin(false);
			entity.setAcceptPopi(false);
			createBatch.add(entity);
		} else {
			updateBatch.add(entity);
		}

		if (usersLanguages != null && usersLanguages.size() > 0) {
			if (entity != null && entity.getId() != null) {
				UsersLanguageService uls = new UsersLanguageService();
				List<UsersLanguage> list = (ArrayList<UsersLanguage>) uls.findByUser(entity);
				if (list != null && list.size() > 0) {
					List<IDataEntity> usersLanguagesList = new ArrayList<>();
					usersLanguagesList.addAll(list);
					dao.deleteBatch(usersLanguagesList);
				}
			}

			for (UsersLanguage ul : usersLanguages) {
				ul.setUser(entity);
				createBatch.add(ul);
			}
		}

		if (usersDisabilityList != null && usersDisabilityList.size() > 0) {
			if (entity != null && entity.getId() != null) {
				UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
				List<UsersDisability> usersDisabilitylistRemove = (ArrayList<UsersDisability>) usersDisabilityService.findByKeyUser(entity);
				if (usersDisabilitylistRemove != null && usersDisabilitylistRemove.size() > 0) {
					List<IDataEntity> usersDisabilitylistIDataEntity = new ArrayList<>();
					usersDisabilitylistIDataEntity.addAll(usersDisabilitylistRemove);
					dao.deleteBatch(usersDisabilitylistIDataEntity);
				}
			}

			for (UsersDisability ud : usersDisabilityList) {
				ud.setUser(entity);
				createBatch.add(ud);
			}
		}

		if (company.getId() == null) createBatch.add(company);
		else updateBatch.add(company);

		cl.setUser(entity);
		cl.setCompany(trainingProvider);

		if (submitWorkflow) cl.setStatus(ApprovalEnum.PendingApproval);
		if (cl.getId() == null) createBatch.add(cl);
		else if (cl.getId() != null) updateBatch.add(cl);

		AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
		//AddressService.instance().create(entity.getBirthAddress());
		AddressService.instance().saveAddresses(company.getResidentialAddress(), company.getPostalAddress());
		if (gaurdian != null && gaurdian.getId() == null) {
			// AddressService.instance().saveAddresses(entity.getResidentialAddress(),
			// gaurdian.getPostalAddress());
			AddressService.instance().saveAddresses(gaurdian.getResidentialAddress(), gaurdian.getPostalAddress());
		}
		// if (createBatch.size() > 0) this.dao.createBatch(createBatch);
		// if (updateBatch.size() > 0) this.dao.updateBatch(updateBatch);
		this.dao.createAndUpdateBatch(createBatch, updateBatch);

		for (Doc doc : cl.getDocs()) {
			if (doc.getConfigDoc().getReqImmediate()) {
				doc.setTargetKey(cl.getId());
				doc.setTargetClass(CompanyLearners.class.getName());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), createUser);
			}
		}
		if (pwdLearner.length() > 0) registerService.registerLearner(entity, pwdLearner);
		if (submitWorkflow) TasksService.instance().findFirstInProcessAndCreateTask(createUser, cl.getId(), CompanyLearners.class.getName(), ConfigDocProcessEnum.Learner, false);

		if (cl.getInterventionType().getForm().matches("002")) {
			// Sending LPMFM002 learner Registration Form
			// sendLPMFM002Email(createUser, entity, company, trainingProvider,
			// requireGaurdian, cl);
			sendWorkBasedLearningProgrammeAgreement(createUser, entity, company, trainingProvider, requireGaurdian, cl, usersDisabilityList);
		} else if (cl.getInterventionType().getForm().matches("015")) {
			// Sending LPMFM015 learner Registration Form
			sendLPMFM015Email(createUser, entity, company, trainingProvider, requireGaurdian, cl, usersLanguages, usersDisabilityList);
		} else if (cl.getInterventionType().getForm().matches("009")) {
			// Sending LPMFM015 learner Registration Form
			sendLPMFM015Email(createUser, entity, company, trainingProvider, requireGaurdian, cl, usersLanguages, usersDisabilityList);
			// sendLPMFM007Email(createUser, entity, company, trainingProvider,
			// requireGaurdian, cl, usersLanguages);
		}

		/*
		 * if (cl.getInterventionType().getInterventionTypeEnum() ==
		 * InterventionTypeEnum.Learnership ||
		 * cl.getInterventionType().getInterventionTypeEnum() ==
		 * InterventionTypeEnum.Apprenticeship) { // Sending LPMFM002 learner
		 * Registration Form // sendLPMFM002Email(createUser, entity, company,
		 * trainingProvider, // requireGaurdian, cl);
		 * sendWorkBasedLearningProgrammeAgreement(createUser, entity, company,
		 * trainingProvider, requireGaurdian, cl); } else { // Sending LPMFM015 learner
		 * Registration Form sendLPMFM015Email(createUser, entity, company,
		 * trainingProvider, requireGaurdian, cl); }
		 */

	}
	
		public void createNewLearner(Users createUser, Users entity, CompanyLearners cl, boolean submitWorkflow, boolean requireGaurdian, List<UsersLanguage> usersLanguages, List<UsersDisability> usersDisabilityList) throws Exception {
			if(cl.getEmployer() == null) {
				throw new Exception("Employer error, Please contact you administrator");
			}
			if(cl.getCompany() == null) {
				throw new Exception("Employer error, Please contact you administrator");
			}
			if(cl.getEmployer().getOrganisationType() == null) {
				throw new Exception("Organisation type error for the employer");
			}
			
			if(cl.getMersetaFunded() == YesNoEnum.Yes) {
				if(cl.getProjectImplementationPlan() != null) {			
					int count = countLearnersAssignedToPip(cl);
					if(count == -1) {
						throw new Exception("Project implementation error on registration when counting learners");
					}
					if(count >= (cl.getProjectImplementationPlan().getCoFundingLearnersAwarded() + cl.getProjectImplementationPlan().getFullyFundedLearnerAwarded())){
						throw new Exception("You have exceeded the number of learners allocated for this intervention as per the project implementation plan");
					}
				}else {
					throw new Exception("Project inplemantation error on registration");
				}
			}

						
			if(cl.getEmployer().getOrganisationType().getWorkplaceApprovalRequired() && cl.getInterventionType().getWorkplaceApprovalRequired() != null &&  cl.getInterventionType().getWorkplaceApprovalRequired()) {
				if(getCompanyLearnerQualification(cl) != null && getCompanyLearnerQualification(cl).getId() != null) {
					WorkPlaceApproval workPlaceApproval = null;
					
					if(cl.getSite() != null) {
						workPlaceApproval = workPlaceApprovalService.findApprovedWorkPlaceApprovalByCompanyAndQualificationAndSites(cl.getEmployer(), getCompanyLearnerQualification(cl), ApprovalEnum.Approved, cl.getSite().getId());
					}else {
						workPlaceApproval = workPlaceApprovalService.findApprovedWorkPlaceApprovalByCompanyAndQualification(cl.getEmployer(), getCompanyLearnerQualification(cl), ApprovalEnum.Approved);
					}
					
					if(workPlaceApproval == null) {
						throw new Exception("The company is not workplace approved for this qualification " + getCompanyLearnerQualification(cl).getDescription());
					}
					if(cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship || cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
						checkSmeQualificationMentor(cl, getCompanyLearnerQualification(cl), workPlaceApproval);
					}
				}
			}
						
			Users gaurdian = null;
			if (requireGaurdian) {
				gaurdian = entity.getLegalGaurdian();
			} else {
				gaurdian = null;
				entity.setLegalGaurdian(gaurdian);
			}

			List<IDataEntity> createBatch = new ArrayList<>();
			List<IDataEntity> updateBatch = new ArrayList<>();
			String error = "";
			String pwdLearner = "";
			cl.setCreateUser(createUser);
			//cl.setMersetaCompany(true);
			if (entity.getId() == null && entity.getEmail() != null && !entity.getEmail().isEmpty()) {
				usersService.checkEmailUsedEmailOnly(entity.getEmail());								
			}else {
				if(entity.getId() == null) {
					entity.setEmail(usersService.generateUuidEmailAddress());
				}
			}
			
			if (entity.getLegalGaurdian() != null && entity.getLegalGaurdian().getId() == null) {
				usersService.checkEmailUsedEmailOnly(entity.getEmail());
			}

			List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
			if (cl.getDocs() != null) {
				for (Doc doc : cl.getDocs()) {
					if(doc.isRequired()) {
						doc.setCompany(null);
						doc.setVersionNo(1);
						doc.setUsers(createUser);
						doc.setTargetKey(cl.getId());
						doc.setTargetClass(CompanyLearners.class.getName());
						docsDataEntities.add(doc);
						if (doc.getData() != null) {
							docsDataEntities.add(new DocByte(doc.getData(), doc));
							docsDataEntities.add(new DocumentTracker(doc, createUser, new java.util.Date(), DocumentTrackerEnum.Upload));
						} else {
							throw new Exception("Please upload required document ");
						}
					}
				}
			}

			if (docsDataEntities.size() > 0) {
				dao.createBatch(docsDataEntities);
			}

			if (error.length() > 0) throw new ValidationException(error);

			if (gaurdian != null) {
				if (gaurdian.getId() == null) {
					AddressService.instance().saveAddresses(gaurdian.getResidentialAddress(), gaurdian.getPostalAddress());
					String pwd = GenericUtility.genPassord();
					gaurdian.setPassword(LogonService.encryptPassword(pwd));
					gaurdian.setStatus(UsersStatusEnum.EmailNotConfrimed);
					gaurdian.setEmailGuid(UUID.randomUUID().toString());
					gaurdian.setRegistrationDate(new java.util.Date());
					gaurdian.setAdmin(false);
					gaurdian.setAcceptPopi(false);
					createBatch.add(gaurdian);
				} else {
					if(gaurdian.getId()!=null) {
						AddressService.instance().saveAddresses(gaurdian.getResidentialAddress(), gaurdian.getPostalAddress());
						usersService.update(gaurdian);
					}
				}
				entity.setLegalGaurdian(gaurdian);
			}

			AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
			
			if (entity.getId() == null) {
				pwdLearner = GenericUtility.genPassord();
				new LogonService();
				entity.setPassword(LogonService.encryptPassword(pwdLearner));
				entity.setStatus(UsersStatusEnum.EmailNotConfrimed);
				entity.setEmailGuid(UUID.randomUUID().toString());
				entity.setRegistrationDate(new java.util.Date());
				entity.setAdmin(false);
				entity.setAcceptPopi(false);
				createBatch.add(entity);
			} else {
				updateBatch.add(entity);
			}
			cl.setUser(entity);
			if (usersLanguages != null && usersLanguages.size() > 0) {
				if (entity != null && entity.getId() != null) {
					UsersLanguageService uls = new UsersLanguageService();
					List<UsersLanguage> list = (ArrayList<UsersLanguage>) uls.findByUser(entity);
					if (list != null && list.size() > 0) {
						List<IDataEntity> usersLanguagesList = new ArrayList<>();
						usersLanguagesList.addAll(list);
						dao.deleteBatch(usersLanguagesList);
					}
				}

				for (UsersLanguage ul : usersLanguages) {
					ul.setUser(entity);
					createBatch.add(ul);
				}
			}

			if (usersDisabilityList != null && usersDisabilityList.size() > 0) {
				if (entity != null && entity.getId() != null) {
					UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
					List<UsersDisability> usersDisabilitylistRemove = (ArrayList<UsersDisability>) usersDisabilityService.findByKeyUser(entity);
					if (usersDisabilitylistRemove != null && usersDisabilitylistRemove.size() > 0) {
						List<IDataEntity> usersDisabilitylistIDataEntity = new ArrayList<>();
						usersDisabilitylistIDataEntity.addAll(usersDisabilitylistRemove);
						dao.deleteBatch(usersDisabilitylistIDataEntity);
					}
				}

				for (UsersDisability ud : usersDisabilityList) {
					ud.setUser(entity);
					createBatch.add(ud);
				}
			}

			if (submitWorkflow) cl.setStatus(ApprovalEnum.PendingApproval);
			if (cl.getId() == null) createBatch.add(cl);
			else if (cl.getId() != null) updateBatch.add(cl);

			
			
			this.dao.createAndUpdateBatch(createBatch, updateBatch);

			for (Doc doc : cl.getDocs()) {
				if (doc.getConfigDoc().getReqImmediate()) {
					doc.setTargetKey(cl.getId());
					doc.setTargetClass(CompanyLearners.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), createUser);
				}
			}
			if (pwdLearner.length() > 0) registerService.registerLearner(entity, pwdLearner);
			if (submitWorkflow) {
				if(cl.getInterventionType().getId() == HAJConstants.INTERVENTION_BURSARIES_UNEMPLOYED_ID) {
					TasksService.instance().findFirstInProcessAndCreateTask(createUser, cl.getId(), CompanyLearners.class.getName(), ConfigDocProcessEnum.UnemployedBursaryLearnerRegistration, false);
				}else {
					TasksService.instance().findFirstInProcessAndCreateTask(createUser, cl.getId(), CompanyLearners.class.getName(), ConfigDocProcessEnum.LearnerRegistration, false);
				}
			}

			if (cl.getInterventionType().getForm().matches("002")) {
				sendWorkBasedLearningProgrammeAgreement(createUser, entity, cl.getEmployer(), cl.getCompany(), requireGaurdian, cl, usersDisabilityList);
			} else if (cl.getInterventionType().getForm().matches("015")) {
				sendLPMFM015Email(createUser, entity,cl.getEmployer(), cl.getCompany(), requireGaurdian, cl, usersLanguages, usersDisabilityList);
			} else if (cl.getInterventionType().getForm().matches("009")) {
				sendLPMFM015Email(createUser, entity, cl.getEmployer(), cl.getCompany(), requireGaurdian, cl, usersLanguages, usersDisabilityList);
			}
	}
	
	private int countLearnersAssignedToPip(CompanyLearners cl) throws Exception {
		int count = -1;
		if(SKILLS_PROGRAM_LIST.contains(cl.getInterventionType().getId())) {
			count = dao.countLearnersAssignedToPipSkillsProgramme(cl.getProjectImplementationPlan().getId(), cl.getSkillsProgram().getId());
		}else if(SKILLS_SET_LIST.contains(cl.getInterventionType().getId())) {
			count = dao.countLearnersAssignedToPipSkillsSet(cl.getProjectImplementationPlan().getId(), cl.getSkillsSet().getId());
		}else if(cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
			count = dao.countLearnersAssignedToPipQualification(cl.getProjectImplementationPlan().getId(), cl.getQualification().getId());
		}else if(cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			count = dao.countLearnersAssignedToPipQualification(cl.getProjectImplementationPlan().getId(), cl.getLearnership().getQualification().getId());
		}else if(cl.getInterventionType().getId() == HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
			
		}else if(cl.getUnitStandard() != null) {
			count = dao.countLearnersAssignedToPipUnitStandard(cl.getProjectImplementationPlan().getId(), cl.getUnitStandard().getId());
		}else if(cl.getQualification() !=null) {
			count = dao.countLearnersAssignedToPipQualification(cl.getProjectImplementationPlan().getId(), cl.getQualification().getId());
		}
		return count;
	}

	public void checkSmeQualificationMentor(CompanyLearners cl, Qualification qualification, WorkPlaceApproval workplaceapproval)throws Exception {
		List<WorkPlaceApprovalSites> workPlaceApprovalSites = workPlaceApprovalService.findSitesByApproval(workplaceapproval, ApprovalEnum.Approved);
		System.out.println("workPlaceApprovalSites.size():"+workPlaceApprovalSites.size());
		if(workPlaceApprovalSites.size() > 0) {
			boolean valid = false;
			for(WorkPlaceApprovalSites wpas :workPlaceApprovalSites) {
				System.out.println("wpas.getSitesSme():"+wpas.getSitesSme().getFirstName());
				int count = countCompanyLearnersBySme(wpas.getSitesSme());
				System.out.println("count:"+count);
				if(qualification.getLearnerMentorRatio().getNoOfLearners() == null) {
					throw new Exception("No learner mentor ratio assigned to a qualification, please contact support");
				}
				
				System.out.println("count:"+count);
				System.out.println("qualification.getLearnerMentorRatio().getNoOfLearners():"+qualification.getLearnerMentorRatio().getNoOfLearners());
				if(count < qualification.getLearnerMentorRatio().getNoOfLearners()) {
					valid = true;
					cl.setSitesSme(wpas.getSitesSme());
					break;
				}
			}	
			
			if(!valid) {
				throw new Exception("Number of learners have exceeded mentor ratio");
			}
		}else {
			throw new Exception("No available mentor(s) for this workplace approval");
		}
	}
	
	/*public boolean checkSmeQualificationMentor(Qualification qualification, Company company)throws Exception {
		boolean valid = false;
		List<SmeQualifications> list = SmeQualificationsService.instance().findByQualificationAndCompany(qualification, company, ApprovalEnum.Approved);
		if(list != null && list.size() > 0) {
			for(SmeQualifications smeQualifications:list) {
				if(checkIfCanAssignMentor(smeQualifications, company)) {
					valid = true;
					break;
				}
			}			
		}
		
		return valid;
	}*/
	
	/*public boolean checkSmeQualificationMentor(Company company)throws Exception {
		boolean valid = false;
		List<SmeQualifications> list = SmeQualificationsService.instance().findByCompany(company, ApprovalEnum.Approved);
		
		if(list != null && list.size() > 0) {
			for(SmeQualifications smeQualifications:list) {
				if(checkIfCanAssignMentor(smeQualifications, company)) {
					valid = true;
					break;
				}
			}			
		}
		
		return valid;
	}*/
	
	/*private boolean checkIfCanAssignMentor(SmeQualifications smeQualifications, Company company) throws Exception {
		boolean assign = false;
		int countCompanyLearnerSme = dao.countCompanyLearnersBySme(company.getId(), smeQualifications.getId());
		if(smeQualifications.getQualification().getLearnerMentorRatio() != null) {
			LearnerMentorRatio learnerMentorRatio = smeQualifications.getQualification().getLearnerMentorRatio();
			if(learnerMentorRatio.getNoOfLearners() > countCompanyLearnerSme) {
				assign = true;
			}
		}else{
			throw new Exception("No Learner Ratio Available");
		}
		return assign;
	}*/
	
	public boolean checkSmeQualificationMentor(Qualification qualification, WorkPlaceApproval workplaceapproval)throws Exception {
		boolean valid = false;
		if(workplaceapproval != null) {
			List<WorkPlaceApprovalSites> workPlaceApprovalSites = workPlaceApprovalService.findSitesByApproval(workplaceapproval);	
			if(workPlaceApprovalSites.size() > 0) {
				valid = true;			
			}
		}		
		return valid;
	}
	
	public int countCompanyLearnersBySme(Long companyId, Long smeQualificationID) throws Exception {
		return dao.countCompanyLearnersBySme(companyId, smeQualificationID);
	}
	
	public int countCompanyLearnersBySme(SitesSme sitessme) throws Exception {
		return dao.countCompanyLearnersBySme(sitessme.getId());
	}

	public boolean checkMentorsRatio(Qualification qualification, Company company) throws Exception {
		WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();

		boolean mentorLearnerRatio = true;
		int learnerRation = 0;
		int numberOfCompanyMentors = 0;

		int countLearners = dao.countLearnersAssignedToCompanyWithQualificationId(company.getId(), qualification.getId());

		if (qualification.getWorkplaceApprovalRequired() && qualification.getLearnerMentorRatio() != null) {
			learnerRation = qualification.getLearnerMentorRatio().getNoOfLearners();
			
		}

		WorkPlaceApproval workPlaceApproval = workPlaceApprovalService.findWorkPlaceApprovalByCompanyandQualification(company, qualification);

		if (workPlaceApproval != null) {
			List<WorkPlaceApprovalSites> wpas = workPlaceApprovalService.findSitesByApproval(workPlaceApproval, null, null);
			numberOfCompanyMentors = wpas.size();
		}
		if (learnerRation != 0 && numberOfCompanyMentors != 0) if (countLearners > learnerRation * numberOfCompanyMentors) {
			mentorLearnerRatio = false;
		}

		return mentorLearnerRatio;
	}

	public boolean checkMentors(Qualification qualification, Company company) throws Exception {
		boolean mentorLearnerRatio = true;
		int count = 0;
		int learners = 0;

		WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
		SitesService sitesService = new SitesService();
		List<Sites> sites = sitesService.findByCompany(company);
		if (sites != null && sites.size() > 0) {
			for (Sites site : sites) {
				count = workPlaceApprovalService.checkForExistingRecords(company, qualification, null, site);
				if (count >= 1) {
					break;
				}
			}

		} else {
			count = workPlaceApprovalService.checkForExistingRecords(company, qualification, null, null);
			WorkPlaceApproval workPlaceApproval = new WorkPlaceApproval();
			if (count == 1) {
				workPlaceApproval = workPlaceApprovalService.findWorkPlaceApprovalByCompanyandQualification(company, qualification);
			} else if (count > 1) {
				List<WorkPlaceApproval> workPlaceApprovals = workPlaceApprovalService.findByCompanyandQualification(company, qualification);
				workPlaceApproval = workPlaceApprovals.get(0);
			}

			List<WorkPlaceApprovalSites> wpas = workPlaceApprovalService.findSitesByApproval(workPlaceApproval, null, null);

			if (wpas != null && wpas.size() > 0) {
				for (WorkPlaceApprovalSites w : wpas) {
					w.getWorkPlaceApproval().getQualification().getLearnerMentorRatio().getNoOfMentors();
					learners += w.getWorkPlaceApproval().getQualification().getLearnerMentorRatio().getNoOfLearners();
				}
			}

			int assignedLearners = dao.countLearnersAssignedToCompanyWithQualificationIdLearnerStatusEnum(company.getId(), qualification.getId());
			if (assignedLearners >= learners) {
				mentorLearnerRatio = false;
			}
		}
		return mentorLearnerRatio;
	}

	public boolean checkIfCompanyQualificationWorkplaceApproved(Company company, Qualification qualification) throws Exception {
		boolean isworkplaceapproved = false;
		WorkPlaceApprovalService wpas = new WorkPlaceApprovalService();
		// isworkplaceapproved =
		// wpas.workplaceApprovedCheck(companyService.findByKey(company.getId()),
		// qualification, null, null, null);
		isworkplaceapproved = wpas.checkIfWorkplaceApproved(companyService.findByKey(company.getId()), qualification, null, null, null);
		/**
		 * SitesService sitesService = new SitesService(); List<Sites> sites =
		 * sitesService.findByCompany(company); if(sites != null && sites.size() >0) {
		 * for(Sites site : sites) { isworkplaceapproved =
		 * wpas.workplaceApprovedCheck(companyService.findByKey(company.getId()),
		 * qualification, null, site, null); if(isworkplaceapproved) { break; } } }else
		 * { isworkplaceapproved =
		 * wpas.workplaceApprovedCheck(companyService.findByKey(company.getId()),
		 * qualification, null, null, null); }
		 */
		return isworkplaceapproved;
	}

	public void createWorkplaceApproval(Company company, Qualification qualification, Users createUser) throws Exception {
		WorkPlaceApprovalService wpas = new WorkPlaceApprovalService();
		SDFCompany primarySDF = companyService.findPrimarySDF(companyService.findByKey(company.getId()));
		if (primarySDF == null) {
			throw new Exception("Unable to locate Primary SDF for: " + company.getCompanyName());
		} else {
			boolean byQualification = true;
			if (qualification.getQualificationtypeid() != null && qualification.getQualificationtypeid().matches(HAJConstants.TRADE_QUALIFICATION_CODE)) {
				byQualification = false;
			}
			wpas.createWorkplaceApproval(company, qualification, null, null, createUser, byQualification);
		}
	}

	public boolean checkIfWorkplaceApproved(CompanyLearners cl, Users u) throws Exception {
		String error = "";
		if (companyService == null) {
			companyService = new CompanyService();
		}

		WorkPlaceApprovalService wpas = new WorkPlaceApprovalService();
		boolean isworkplaceapproved = true;
		if (cl != null && cl.getEmployer() != null && cl.getEmployer().getId() != null && cl.getQualification() != null && cl.getQualification().getId() != null) {
			if (cl.getSite() == null) {
				isworkplaceapproved = wpas.workplaceApprovedCheck(companyService.findByKey(cl.getEmployer().getId()), cl.getQualification(), null, null, u);
			} else {
				isworkplaceapproved = wpas.workplaceApprovedCheck(companyService.findByKey(cl.getEmployer().getId()), cl.getQualification(), null, SitesService.instance().findByKey(cl.getSite().getId()), u);
			}
		}

		if (cl != null && cl.getEmployer() != null && cl.getEmployer().getId() != null && cl.getLearnership() != null && cl.getLearnership().getQualification() != null && cl.getLearnership().getQualification().getId() != null) {
			if (cl.getSite() == null) {
				isworkplaceapproved = wpas.workplaceApprovedCheck(companyService.findByKey(cl.getEmployer().getId()), cl.getLearnership().getQualification(), null, null, u);
			} else {
				isworkplaceapproved = wpas.workplaceApprovedCheck(companyService.findByKey(cl.getEmployer().getId()), cl.getLearnership().getQualification(), null, SitesService.instance().findByKey(cl.getSite().getId()), u);
			}
		}

		if (!isworkplaceapproved) {
			error = "The provider is not workplace approved for this qualification. Workplace approval task has been sent to skill development provider for approval.";
			SDFCompany primarySDF = companyService.findPrimarySDF(companyService.findByKey(cl.getEmployer().getId()));
			if (primarySDF == null && cl.getSite() == null) {
				throw new Exception("Unable to locate Primary SDF for: " + companyService.findByKey(cl.getEmployer().getId()).getLevyNumber());
			} else {
				boolean byQualification = true;
				if (cl.getQualification().getQualificationtypeid().matches(HAJConstants.TRADE_QUALIFICATION_CODE)) {
					byQualification = false;
				}
				wpas.createWorkplaceApproval(cl.getEmployer(), cl.getQualification(), null, null, u, byQualification);
			}
		}

		if (error != "") {
			// throw new Exception(error);
		}

		return isworkplaceapproved;
	}

	public void completeCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks, Appointment appointment) throws Exception {
		System.out.println("completeCompanyLearners");
		if (appointment.getUser() == null) {
			appointment.setUser(user);
		}
		appointmentService.create(appointment);
		companyLearners.setAppointment(appointment);
		dao.update(companyLearners);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void withdrawCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Withdrawn);
		companyLearners.setApprovalDate(getSynchronizedDate());
		companyLearners.setLearnerStatus(LearnerStatusEnum.Withdrawn);
		dao.update(companyLearners);
		TasksService.instance().completeTask(tasks);
	}
	
	public void completeCompanyLearnersNew(CompanyLearners companyLearners, Users user, Tasks tasks, Appointment appointment) throws Exception {
		companyLearners.setReviewStatus(ApprovalEnum.PendingCommitteeApproval);
		companyLearners.setStatus(ApprovalEnum.PendingCommitteeApproval);

		uploadDocuments(companyLearners, tasks, user);

		dao.update(companyLearners);
		TasksService.instance().completeTask(tasks);
	}

	/**
	 * Uploads docs uploaded against LearnershipDevelopmentRegistration throws
	 * Exception if doc data is empty for permissions: Upload or EditUpload Only
	 * time documents required if permissions is: FinalUploadApproval or FinalUpload
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void uploadDocuments(CompanyLearners entity, Tasks task, Users sessionUser) throws Exception {
		// check if all docs provided for correct permissions
		if (task != null && task.getProcessRole() != null && (task.getProcessRole().getRolePermission() == UserPermissionEnum.Upload || task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUploadApproval || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.UploadApprove)) {
			// prepareNewRegistration(task.getWorkflowProcess(), entity,
			// task.getProcessRole());
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
				}
			}

			// check if data not empty and creates document
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() == null && doc.getData() != null) {
					doc.setTargetKey(entity.getId());
					doc.setTargetClass(CompanyLearners.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
				} else {
					// if(doc.getConfigDoc().getConfigDocProcess().getConfigDocProcessEnumByValue(1).compareTo(doc.getConfigDoc().getProcessRoles().getRoleOrder())
					// )
					if(doc.isRequired()) {
						if (doc.getData() != null) {
							if (doc.getId() == null) {
								doc.setTargetKey(entity.getId());
								doc.setTargetClass(CompanyLearners.class.getName());
								DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
							}
						} else {
							throw new Exception("Please ensure all documents are uploaded");
						}
					}
				}
			}
		}
	}
	
	public void uploadDocuments(CompanyLearnersTransfer entity, Tasks task, Users sessionUser) throws Exception {
		// check if all docs provided for correct permissions
		if (task != null && task.getProcessRole() != null && (task.getProcessRole().getRolePermission() == UserPermissionEnum.Upload || task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUploadApproval || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.UploadApprove)) {
			// prepareNewRegistration(task.getWorkflowProcess(), entity,
			// task.getProcessRole());
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) {
						doc.setData(docByte.getData());
					}
				}
			}

			// check if data not empty and creates document
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() == null && doc.getData() != null) {
					doc.setTargetKey(entity.getId());
					doc.setTargetClass(CompanyLearnersTransfer.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
				} else {
					// if(doc.getConfigDoc().getConfigDocProcess().getConfigDocProcessEnumByValue(1).compareTo(doc.getConfigDoc().getProcessRoles().getRoleOrder())
					// )
					if(doc.isRequired()) {
						if (doc.getData() != null) {
							if (doc.getId() == null) {
								doc.setTargetKey(entity.getId());
								doc.setTargetClass(CompanyLearnersTransfer.class.getName());
								DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
							}
						} else {
							throw new Exception("Please ensure all documents are uploaded");
						}
					}
				}
			}
		}
	}

	public void rejectlearnerTransfer(CompanyLearnersTransfer companyLearners, Users user, Tasks tasks) throws Exception {
		if(tasks.getProcessRole().getRoleOrder() == 4) {
			List<Users> userList = new ArrayList<>();
			userList.add(companyLearners.getCreateUser());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, userList);
		}else {
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
		}
	}
	
	public void rejectlearnerTransferFinal(CompanyLearnersTransfer companyLearners, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);		
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(companyLearners.getCompanyLearners().getId());
		cl.setLearnerStatus(LearnerStatusEnum.Registered);
		cl.setStatus(ApprovalEnum.Approved);
		CompanyLearnersService.instance().update(cl);
		setRejectedReasons(companyLearners.getClass().getName(), companyLearners.getId(), user, rejectReasons, tasks.getWorkflowProcess());
		TasksService.instance().completeTask(tasks);	
		sendFinalRejectEmail(companyLearners, companyLearners.getCreateUser(), rejectReasons);
	}
	
	public void setRejectedReasons(String className, Long targetkey, Users users, List<RejectReasons> rejectReasonsSelected, ConfigDocProcessEnum configProcess) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		if (rejectReasonsSelected != null && rejectReasonsSelected.size() != 0) {
			entityList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(targetkey, className, rejectReasonsSelected, users, configProcess));
		} else {
			RejectReasonMultipleSelectionService.instance().clearEntries(targetkey, className, configProcess);
		}
		if (entityList.size() != 0) {
			dao.createBatch(entityList);
		}
	}

	public void approveCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks, Appointment appointment) throws Exception {
		if (appointment.getAppointmentWithuser() == null) {
			appointment.setStatus(ApprovalEnum.Approved);
			appointment.setAppointmentWithuser(user);
			/*
			 * The system should send a notification of the confirmed appointment to the
			 * workplace/provider
			 */
			sendAppointmentNotification(companyLearners.getCreateUser());
		}
		appointmentService.create(appointment);
		companyLearners.setStatus(ApprovalEnum.Approved);
		companyLearners.setLearnerStatus(LearnerStatusEnum.Registered);
		if (companyLearners.getLegacyId() == null) {
			if (companyLearners.getDateLearnerRegistered() == null) {
				companyLearners.setDateLearnerRegistered(getSynchronizedDate());
			}
		}
		dao.update(companyLearners);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
//		WorkplaceMonitoring workplaceMonitoring = new WorkplaceMonitoring();
//		workplaceMonitoring.setCompany(companyLearners.getCompany());
//		workplaceMonitoring.setUsers(companyLearners.getUser());
//		WorkplaceMonitoringService workplaceMonitoringService = new WorkplaceMonitoringService();
//		workplaceMonitoringService.requesteWorkflow(workplaceMonitoring, companyLearners.getCreateUser());
		if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
			activeContractDetailService.addTranchePaymentDetail(companyLearners, user, 0.25, TrancheEnum.TRANCHE_TWO, true);
		}
	}

	public void completeNewCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		uploadDocuments(companyLearners, tasks, user);

		List<Users> userList = findRegionClinetServiceCoodinator(companyLearners.getCompany());
		if (userList.size() == 0) {
			throw new Exception("No Client Service Coodinator assigned to region");
		}

		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		// TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null,
		// false);
	}

	public List<CompanyLearners> findCompanyLearnersByStatus(Long companyId, ApprovalEnum status) throws Exception {
		return dao.findCompanyLearnersByStatus(companyId, status);
	}
	
	public List<CompanyLearners> findCompanyLearnersByStatus(Long companyId, ApprovalEnum status, SubmissionEnum submissionEnum, Long companyLearnerParentID) throws Exception {
		return dao.findCompanyLearnersByStatus(companyId, status, submissionEnum, companyLearnerParentID);
	}

	public List<CompanyLearners> findCompanyLearnersByParent(Long companyLearnerParentID) throws Exception {
		List<CompanyLearners> list = dao.findCompanyLearnersByParent(companyLearnerParentID);
		CompanyService companyService = new CompanyService();
		for (CompanyLearners companyLearners : list) {
			companyLearners.setEmployer(companyService.findByKey(companyLearners.getEmployer().getId()));
		}
		return list;
	}

	public void completeCompanyLearnersToRegion(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {	
		System.out.println("inside completeCompanyLearnersToRegion");
		companyLearners.setSubmissionEnum(SubmissionEnum.Application);
		if(tasks.getWorkflowProcess() == ConfigDocProcessEnum.UnemployedBursaryLearnerRegistration) {
			System.out.println("inside unemployed if");
			uploadDocuments(companyLearners, tasks, user);
			companyLearners.setSubmissionEnum(SubmissionEnum.ForApproval);
			create(companyLearners);
	
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}else {
			System.out.println("inside unemployed else");
			List<Users> userList = findRegionClinetServiceAdministrator(companyLearners.getCompany());
			if (userList.size() == 0) {
				throw new Exception("No Client Service Administrator assigned to region");
			}
			uploadDocuments(companyLearners, tasks, user);
			companyLearners.setSubmissionEnum(SubmissionEnum.ForApproval);
			create(companyLearners);
	
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}
	}
	
	public void completeCompanyLearnersUnderReviewToRegion(CompanyLearners cl, Users user) throws Exception {
		CompanyLearners companyLearners = findByKey(cl.getId());
		List<Users> userList = findRegionClinetServiceAdministrator(companyLearners.getCompany());
		if (userList.size() == 0) {
			throw new Exception("No Client Service Administrator assigned to region");
		}	
		companyLearners.setSubmissionEnum(SubmissionEnum.ForFinalApproval);
		companyLearners.setStatus(ApprovalEnum.PendingFinalApproval);
		update(companyLearners);
		String description = "Please review the proposed learner at the Regional Office for " + companyLearners.getCompany().getCompanyName() + " (" + companyLearners.getCompany().getLevyNumber() + ").";
		TasksService.instance().findFirstInProcessAndCreateTask(description, user, companyLearners.getId(), CompanyLearners.class.getName(), true, ConfigDocProcessEnum.CompanyLearner, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.CompanyLearner.getTaskDescription()), userList);
	}
	
	public void completeCompanyLearnersToFinalApproval(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		if(tasks.getWorkflowProcess() == ConfigDocProcessEnum.UnemployedBursaryLearnerRegistration) {
			companyLearners.setStatus(ApprovalEnum.PendingFinalApproval);
			create(companyLearners);
	
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}else {
			List<Users> userList = findRegionClinetServiceAdministrator(companyLearners.getCompany());
			if (userList.size() == 0) {
				throw new Exception("No Client Service Administrator assigned to region");
			}
		
			companyLearners.setStatus(ApprovalEnum.PendingFinalApproval);
			create(companyLearners);
	
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}
	}

	public void completeCompanyLearnersForReview(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setStatus(ApprovalEnum.PreApproved);
		companyLearners.setSubmissionEnum(SubmissionEnum.ForReview);
		create(companyLearners);
		// int count =
		// dao.countCompanyLearnersByStatus(companyLearners.getCompany().getId(),
		// ApprovalEnum.PendingApproval);
		int count = dao.countCompanyLearnersByLearnerStatus(companyLearners.getCompany().getId(), ApprovalEnum.PendingApproval, LearnerStatusEnum.Application);
		// int count =
		// dao.countCompanyLearnersByLearnerStatus(companyLearners.getCompany().getId(),
		// ApprovalEnum.PendingApproval, SubmissionEnum.ForReview);
		if (count > 0) {
			TasksService.instance().completeTask(tasks);
		} else if (count == 0) {
			List<Users> users = new ArrayList<>();
			users.add(companyLearners.getCreateUser());
			CompanyLearners cl = new CompanyLearners();
			cl.setCompany(companyLearners.getCompany());
			cl.setCreateUser(companyLearners.getCreateUser());
			create(cl);
			// List<CompanyLearners> clList =
			// dao.findCompanyLearnersByStatus(companyLearners.getCompany().getId(),
			// ApprovalEnum.PreApproved);
			List<CompanyLearners> clList = dao.findCompanyLearnersByStatus(companyLearners.getCompany().getId(), ApprovalEnum.PreApproved, LearnerStatusEnum.Application);
			// List<CompanyLearners> clList =
			// dao.findCompanyLearnersByStatusParentId(companyLearners.getCompany().getId(),
			// ApprovalEnum.PreApproved, SubmissionEnum.ForReview);
			for (CompanyLearners comlearner : clList) {
				comlearner.setCompanyLearnersParent(cl);
				update(comlearner);
			}

			String description = "Please review the proposed date to submit learner documents at the Regional Office for " + companyLearners.getCompany().getCompanyName() + " (" + companyLearners.getCompany().getLevyNumber() + ").";
			// TasksService.instance().findFirstInProcessAndCreateTask(description, user,
			// companyLearners.getCompany().getId(), Company.class.getName(), true,
			// ConfigDocProcessEnum.LearnerReview, MailDef.instance(MailEnum.Task,
			// MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName,
			// user.getLastName(), MailTagsEnum.Task,
			// ConfigDocProcessEnum.LearnerReview.getTaskDescription()), users);
			TasksService.instance().findFirstInProcessAndCreateTask(description, user, cl.getId(), CompanyLearners.class.getName(), true, ConfigDocProcessEnum.LearnerReview, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.LearnerReview.getTaskDescription()), users);

			TasksService.instance().completeTask(tasks);
		} else {
			throw new Exception("Error when approving");
		}
	}

	public void approveCompanyLearner(CompanyLearners entity, Users user, Tasks tasks) throws Exception {

		RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
		List<Users> users = hostingCompanyEmployeesService.findUserByRegionAndRole(rt.getRegion(), tasks.getProcessRole().getNextTaskRole());
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, users, false);

	}

	public void finalApproveWorkflow(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {

		companyLearners.setStatus(ApprovalEnum.Approved);
		companyLearners.setRegistrationNumber(generateCompanyLearnerRegNumber(companyLearners));
		companyLearners.setLearnerStatus(LearnerStatusEnum.Registered);
		companyLearners.setSubmissionEnum(SubmissionEnum.Completed);
		companyLearners.setApprovalDate(getSynchronizedDate());
		companyLearners.setDateLearnerRegistered(getSynchronizedDate());
		if (companyLearners.getLegacyId() == null) {
			if (companyLearners.getDateLearnerRegistered() == null) {
				companyLearners.setDateLearnerRegistered(getSynchronizedDate());
			}
		}
		if(companyLearners.getInterventionType().getBusary() != null && companyLearners.getInterventionType().getBusary()) {
			//CompanyLearners cl = findCompanyLearnersByUserAndQualification(companyLearners.getUser(), companyLearners.getQualification(), companyLearners.getInterventionType(), LearnerStatusEnum.Completed);
			CompanyLearners cl = findCompanyLearnersByUserAndQualification(companyLearners.getUser(), companyLearners.getQualification(),  LearnerStatusEnum.Completed);
			if(cl != null && cl.getId()!=null) {
				cl.setCompany_learners_bursary_link(companyLearners);
				//companyLearners.setCompany_learners_bursary_link(cl);
				companyLearners.setNewBursary(false);
				update(cl);
			}else {
				companyLearners.setNewBursary(true);
			}
		}
		dao.update(companyLearners);
		if(companyLearners.getInterventionType().getForm().matches("015")) {
			TasksService.instance().completeTask(tasks);
			sendApprovalNotification(companyLearners, companyLearners.getCreateUser());
		}else {
			if(tasks.getWorkflowProcess() == ConfigDocProcessEnum.UnemployedBursaryLearnerRegistration) {
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
			}else {
				List<Users> userList = new ArrayList<>();
				userList.add(user);
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
			}
			sendForOfficeUseOnlyForm(companyLearners, user);
		}
		
		// updates for tranch payment and learner link
		if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
			if(companyLearners.getProjectImplementationPlan() != null) {
				ProjectImplementationPlanLearnersService.instance().linkCompanyLearnerToOpenPipLink(companyLearners, companyLearners.getProjectImplementationPlan());
				ProjectImplementationPlanLearners link = ProjectImplementationPlanLearnersService.instance().findByCompanyLearnerAndPip(companyLearners.getId(), companyLearners.getProjectImplementationPlan().getId());
				if (link == null || link.getId() == null) {
					// fail safe
					activeContractDetailService.addTranchePaymentDetail(companyLearners, user, 0.25, TrancheEnum.TRANCHE_TWO, true);
					// send notification maybe
				}else{
					activeContractDetailService.addTranchePaymentDetail(link,companyLearners, user, 0.25, TrancheEnum.TRANCHE_TWO, true);
				}
			}
		}
		
	}
	
	public void finalApproveDocuments(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setDocumentChangeStatus(ApprovalEnum.Approved);
		dao.update(companyLearners);
		TasksService.instance().completeTask(tasks);		
	}
	
	public void uploadSignedDocument(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {			
		uploadDocuments(companyLearners, tasks, user);
		TasksService.instance().completeTask(tasks);
	}
	
	public void uploadChangeDocuments(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		//List<Users> userList = findRegionClinetServiceAdministrator(companyLearners.getCompany());
		List<Users> userList = findRegionClinetServiceCoodinator(companyLearners.getCompany());
		if (userList.size() == 0) {
			throw new Exception("No Client Service Administrator assigned to region");
		}
		uploadDocuments(companyLearners, tasks, user);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
	}
	
	public void uploadSignedDocuments(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {			
		uploadDocuments(companyLearners, tasks, user);
		TasksService.instance().completeTask(tasks);
		List<Users> list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());
		list.add(companyLearners.getUser());
		super.removeDuplicatesFromList(list);
		for (Users u : list) {
			sendApprovalEmail(companyLearners, u);
		}
		createLearnerFileManagementWorkflow(companyLearners, user, null);
	}

	public void createLearnerFileManagementWorkflow(CompanyLearners entity, Users user, Tasks tasks) throws Exception {
		String desc = "Please review the learner file management ";
		entity.setFileApprovalEnum(ApprovalEnum.PendingApproval);
		dao.update(entity);
		TasksService.instance().findFirstInProcessAndCreateTask(desc, user, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.LearnerFileManagement, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);

		if (tasks != null) {
			TasksService.instance().completeTask(tasks);
		}
	}

	public void finalApprovalFileManagementWorkflow(CompanyLearners entity, Users user, Tasks tasks) throws Exception {
		entity.setFileApprovalEnum(ApprovalEnum.Approved);
		entity.setFileApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().completeTask(tasks);

		sendApprovalFileManagementEmail(entity, user, tasks);
	}

	public void finalRejectFileManagementWorkflow(CompanyLearners entity, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		List<Users> users = findRegionClinetServiceAdministrator(entity.getCompany());

		if (users == null || users.size() == 0) {
			throw new Exception("No Region Client Service Administrator for the region");
		}

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), CompanyLearners.class.getName(), rejectReasons, user, ConfigDocProcessEnum.LearnerFileManagement);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		entity.setFileApprovalEnum(ApprovalEnum.Rejected);
		entity.setFileApprovalDate(getSynchronizedDate());
		dao.update(entity);
		String emailMessage = "";
		String subject = "";
		String description = "";

		// TasksService.instance().findFirstInProcessAndCreateRejectTask("", user,
		// entity.getId(), CompanyLearners.class.getName(), true,
		// ConfigDocProcessEnum.LearnerFileManagement, null, users);
		TasksService.instance().createTask(CompanyLearners.class.getName(), null, emailMessage, subject, description, user, entity.getId(), true, true, tasks, users, ConfigDocProcessEnum.LearnerFileManagement, null);
		// TasksService.instance().completeTask(tasks);
		// TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks,
		// users);

		sendRejectFileManagementEmail(entity, user, tasks, rejectReasons);
	}

	public void rejectCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks, List<RejectReasons> rejectReasons, Appointment appointment) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearners.class.getName(), rejectReasons, user, ConfigDocProcessEnum.Learner);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		/*
		 * if (appointment.getStatus() == null || appointment.getStatus() !=
		 * ApprovalEnum.Approved) { appointment.setStatus(ApprovalEnum.Rejected); }
		 */
		List<Users> list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, list);
		list.add(companyLearners.getUser());
		super.removeDuplicatesFromList(list);
		for (Users u : list) {
			sendRejectEmail(companyLearners, rejectReasons, u);
		}
		sendRejectEmailToTheLearner(companyLearners, rejectReasons);
	}

	public void rejectCompanyLearner(CompanyLearners companyLearners, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearners.class.getName(), rejectReasons, user, ConfigDocProcessEnum.CompanyLearner);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		///List<Users> mersetaUsers = findRegionClinetServiceAdministrator(companyLearners.getCompany());
		TasksService.instance().completeTask(tasks);
		TasksService.instance().findFirstInProcessAndCreateTask(user, companyLearners.getId(), CompanyLearners.class.getName(), ConfigDocProcessEnum.LearnerRegistration, false);
		//TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, mersetaUsers);

		List<Users> list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());
		list.add(companyLearners.getUser());

		super.removeDuplicatesFromList(list);
		for (Users u : list) {
			sendRejectEmail(companyLearners, rejectReasons, u);
		}
		// sendRejectEmailToTheLearner(companyLearners, rejectReasons);
	}
	
	public void rejectCompanyLearnerDocuments(CompanyLearners companyLearners, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setDocumentChangeStatus(ApprovalEnum.Rejected);
		dao.update(companyLearners);

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearners.class.getName(), rejectReasons, user, ConfigDocProcessEnum.LearnerRegistrationDocuments);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		
		List<Users> list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());	
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, list);
	}
	
	public void reviewRejectCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks, ArrayList<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setStatus(ApprovalEnum.RejectedByLearnerReview);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);
		List<Users> users = new ArrayList<>();
		users.add(companyLearners.getCreateUser());
		new MailDef();
		TasksService.instance().findFirstInProcessAndCreateTask(user, companyLearners.getId(), CompanyLearners.class.getName(), ConfigDocProcessEnum.Learner, false);
		// TasksService.instance().createTask(CompanyLearners.class.getName(),
		// companyLearners.getCompany(), emailMessage, subject, description, user,
		// companyLearners.getId(), sendMail, createTask, tasks, users,
		// ConfigDocProcessEnum.Learner, mailDef);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearners.class.getName(), rejectReasons, user, ConfigDocProcessEnum.Learner);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}

		List<Users> list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());
		list.add(companyLearners.getUser());

		super.removeDuplicatesFromList(list);
		for (Users u : list) {
			sendRejectEmail(companyLearners, rejectReasons, u);
		}
		sendRejectEmailToTheLearner(companyLearners, rejectReasons);
	}

	public void finalApproveCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		// learnerStatus.
		companyLearners.setLearnerStatus(LearnerStatusEnum.Registered);
		companyLearners.setRegisteredDate(getSynchronizedDate());
		if (companyLearners.getLegacyId() == null) {
			if (companyLearners.getDateLearnerRegistered() == null) {
				companyLearners.setDateLearnerRegistered(getSynchronizedDate());
			}
		}
		companyLearners.setStatus(ApprovalEnum.PendingFinalApproval);
		companyLearners.setApprovalDate(getSynchronizedDate());
		companyLearners.setRegistrationNumber(generateCompanyLearnerRegNumber(companyLearners));
		dao.update(companyLearners);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		// TasksService.instance().completeTask(tasks);
		// sendConfirmEmailToProvider
	}

	public void confirmReviewDate(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setReviewStatus(ApprovalEnum.Approved);
		dao.update(companyLearners);
		// sendConfirmEmailToProvider
		// companyLearners.getReviewCommitteeMeeting().setStatus(ApprovalEnum.Approved);
		// reviewCommitteeMeetingService.update(companyLearners.getReviewCommitteeMeeting());
	}

	public void reviewApproveCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setStatus(ApprovalEnum.PendingFinalApproval);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);
		TasksService.instance().findFirstInProcessAndCreateTask(user, companyLearners.getId(), companyLearners.getClass().getName(), ConfigDocProcessEnum.LearnerReview, false);
	}

	public void finalRejectCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setLearnerStatus(LearnerStatusEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);
		// TasksService.instance().completeTask(tasks);
		List<Users> users = new ArrayList<>();
		users.add(tasks.getCreateUser());
		String emailMessage = "";
		String subject = "";
		String description = "";

		boolean createTask = true;
		boolean sendMail = true;
		MailDef mailDef = new MailDef();
		TasksService.instance().completeTask(tasks);
		// TasksService.instance().createTask(CompanyLearners.class.getName(),
		// companyLearners.getCompany(), emailMessage, subject, description, user,
		// companyLearners.getId(), sendMail, createTask, tasks, users,
		// tasks.getWorkflowProcess(), mailDef);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearners.class.getName(), rejectReasons, user, tasks.getWorkflowProcess());
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}

		List<Users> list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());
		list.add(companyLearners.getUser());

		super.removeDuplicatesFromList(list);
		for (Users u : list) {
			sendRejectEmail(companyLearners, rejectReasons, u);
		}
		sendRejectEmailToTheLearner(companyLearners, rejectReasons);
	}

	public void submitLearnerTransfer(CompanyLearnersTransfer companyLearners, Users user, Tasks tasks) throws Exception {
		String error = "";
		if (companyLearners.getDocs() != null) {
			for (Doc doc : companyLearners.getDocs()) {
				if (!tasks.getFirstInProcess()) {
					if (doc.getId() != null) {
						DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
						doc.setData(docByte.getData());
					}
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName() + " for " + companyLearners.getCompanyLearners().getUser().getFirstName() + " " + companyLearners.getCompanyLearners().getUser().getLastName() + "\n\n";
				}
			}
		}
		if (error.length() > 0) throw new ValidationException(error);
		for (Doc doc : companyLearners.getDocs()) {
			if (doc.getId() == null && doc.getData() != null) {
				doc.setTargetKey(companyLearners.getId());
				doc.setTargetClass(CompanyLearnersTransfer.class.getName());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
			}
		}
		if (tasks.getProcessRole().getRoleOrder() == 6) {
			List<Users> userList = new ArrayList<>();
			// Sending email
			if (companyLearners.getLearnerTransferType() == LearnerTransferTypeEnum.ProviderTransfer) {
				userList.add(SDFCompanyService.instance().findPrimarySDF(companyLearners.getTransferToCompany()).getSdf());
				if (userList == null || userList.size() <= 0) {
					throw new Exception("No primary SDF found  for " + companyLearners.getTransferToCompany().getCompanyName() + ".");
				}
				Users u = userList.get(0);
				sendLPMAD008Email(companyLearners, u);
			} else if (companyLearners.getLearnerTransferType() == LearnerTransferTypeEnum.WorkplaceTransfer) {
				CompanyUsersService companyUsersService = new CompanyUsersService();
				userList.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompanyLearners().getCompany().getId(), ConfigDocProcessEnum.TP));
				if (userList == null || userList.size() <= 0) {
					throw new Exception("No primary Company Contact found  for " + companyLearners.getTransferToCompany().getCompanyName() + ".");
				}
				// Sending to 1 user
				Users tempUser = userList.get(0);
				userList.clear();
				userList.add(tempUser);
				sendLPMAD002Email(companyLearners, tempUser);
			}
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		} else {
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}
	}
	
	public void uploadLearnerTransferDocuments(CompanyLearnersTransfer companyLearnersTransfer, Users user, Tasks tasks) throws Exception {
		uploadDocuments(companyLearnersTransfer, tasks, user);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}
	
	public void withdrawTransfer(CompanyLearnersTransfer companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Withdrawn);
		dao.update(companyLearners);
		
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(companyLearners.getCompanyLearners().getId());
		cl.setLearnerStatus(LearnerStatusEnum.Registered);
		cl.setStatus(ApprovalEnum.Approved);
		CompanyLearnersService.instance().update(cl);
		
		TasksService.instance().completeTask(tasks);
	}
	
	public void eLearnerTransferCompleteTask(CompanyLearnersTransfer companyLearnersTransfer, Users user, Tasks tasks) throws Exception {
		if(tasks.getFirstInProcess()) {
			List<Users> userList = new ArrayList<>();
			CompanyLearners cl = CompanyLearnersService.instance().findByKey(companyLearnersTransfer.getCompanyLearners().getId());
			userList.add(cl.getUser());
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}else if(tasks.getProcessRole().getRoleOrder() == 2) {
			List<Users> userList = new ArrayList<>();
			userList.add(companyLearnersTransfer.getCreateUser());
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}else {
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}
	}

	public void completeLearnerTransferSiteVisit(CompanyLearnersTransfer companyLearners, Users user, Tasks tasks) throws Exception {
		String error = "";
		if (companyLearners.getDocs() != null) {
			for (Doc doc : companyLearners.getDocs()) {
				if (!tasks.getFirstInProcess()) {
					if (doc.getId() != null) {
						DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
						doc.setData(docByte.getData());
					}
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName() + " for " + companyLearners.getCompanyLearners().getUser().getFirstName() + " " + companyLearners.getCompanyLearners().getUser().getLastName() + "\n\n";
				}
			}
		}
		if (error.length() > 0) throw new ValidationException(error);
		for (Doc doc : companyLearners.getDocs()) {
			if (doc.getId() == null && doc.getData() != null) {
				doc.setTargetKey(companyLearners.getId());
				doc.setTargetClass(CompanyLearnersTransfer.class.getName());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
			}
		}

		CompanyLearnersTransferService.instance().update(companyLearners);
		if (tasks.getProcessRole().getRoleOrder() == 1) {
			List<Users> list = new ArrayList<>();
			list.add(user);
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, list, false);
		} else {

			if (companyLearners.getRecommendation() == CompanyLearnersTransferRecommendation.Transfer_Continues) {

				learnerApproval(companyLearners, user, TasksService.instance().findByKey(companyLearners.getTasks().getId()));
				TasksService.instance().completeTask(tasks);
				sendTransferCLOApprovalEmail(companyLearners,user);
			} else {
				List<IDataEntity> updateList = new ArrayList<>();
				companyLearners.setStatus(ApprovalEnum.Rejected);
				companyLearners.setApprovalDateLearner(getSynchronizedDate());
				updateList.add(companyLearners);

				CompanyLearners cl = companyLearners.getCompanyLearners();
				cl.setStatus(companyLearners.getCompanyLearnerPreviousStatus());
				cl.setLearnerStatus(companyLearners.getLearnerPreviousStatus());
				updateList.add(cl);
				dao.updateBatch(updateList);
				TasksService.instance().completeTask(tasks);
				sendTransferCLORectionEmail(companyLearners);
			}

		}
	}

	public void sendLPMAD002() {
		// logo
		// back
	}

	public void finalApproveCompanyLearnersTransfer(CompanyLearnersTransfer companyLearnersTransfer, Users user, Tasks tasks) throws Exception {
		
		CompanyLearners cl = findByKey(companyLearnersTransfer.getCompanyLearners().getId());
		
		if(companyLearnersTransfer.getLearnerTransferType() == LearnerTransferTypeEnum.ProviderTransfer) {
			cl.setCompany(companyLearnersTransfer.getTransferToCompany());
			cl.setTrainingProviderApplication(companyLearnersTransfer.getTransferTrainingProviderApplication());
			if (companyLearnersTransfer.getTransferTrainingProviderApplication() != null && companyLearnersTransfer.getTransferTrainingProviderApplication().getTrainingSite() != null &&companyLearnersTransfer.getTransferTrainingProviderApplication().getTrainingSite().getId() != null ) {
				cl.setTrainingSite(companyLearnersTransfer.getTransferTrainingProviderApplication().getTrainingSite());
			}
			cl.setStatus(companyLearnersTransfer.getCompanyLearnerPreviousStatus());
			cl.setLearnerStatus(companyLearnersTransfer.getLearnerPreviousStatus());
			//cl.setLearnerStatus(LearnerStatusEnum.Registered);
			//cl.setStatus(ApprovalEnum.Approved);11
			dao.update(cl);
			
			companyLearnersTransfer.setStatus(ApprovalEnum.Approved);
			companyLearnersTransfer.setApprovalDate(getSynchronizedDate());
			dao.update(companyLearnersTransfer);		
			TasksService.instance().completeTask(tasks);
			sendApprovalEmail(companyLearnersTransfer, companyLearnersTransfer.getCreateUser());
		}else if(companyLearnersTransfer.getLearnerTransferType() == LearnerTransferTypeEnum.WorkplaceTransfer) {
			cl.setEmployer(companyLearnersTransfer.getTransferToCompany());
			if(cl.getTrainingProviderApplication() == null) {
				cl.setCompany(companyLearnersTransfer.getTransferToCompany());
			}
			cl.setStatus(companyLearnersTransfer.getCompanyLearnerPreviousStatus());
			cl.setLearnerStatus(companyLearnersTransfer.getLearnerPreviousStatus());
			//cl.setLearnerStatus(LearnerStatusEnum.Registered);
			//cl.setStatus(ApprovalEnum.Approved);
			dao.update(cl);
			
			companyLearnersTransfer.setStatus(ApprovalEnum.Approved);
			companyLearnersTransfer.setApprovalDate(getSynchronizedDate());
			dao.update(companyLearnersTransfer);		
			TasksService.instance().completeTask(tasks);
			sendApprovalEmail(companyLearnersTransfer, companyLearnersTransfer.getCreateUser());
		}
	}
	
	public void finalApproveCompanyLearnersTransferOld(CompanyLearnersTransfer companyLearners, Users user, Tasks tasks) throws Exception {
		switch (companyLearners.getLearnerTransferType()) {
			case ProviderTransfer:
				companyLearners.getCompanyLearners().setCompany(companyLearners.getTransferToCompany());
				break;
			case WorkplaceTransfer:
				companyLearners.getCompanyLearners().setEmployer(companyLearners.getTransferToCompany());
				break;
			default:
				break;
		}
		companyLearners.getCompanyLearners().setLearnerStatus(LearnerStatusEnum.Registered);
		companyLearners.getCompanyLearners().setStatus(ApprovalEnum.Approved);
		companyLearners.getCompanyLearners().setApprovalDate(getSynchronizedDate());

		companyLearners.setStatus(ApprovalEnum.Approved);
		companyLearners.setApprovalDate(getSynchronizedDate());

		dao.update(companyLearners.getCompanyLearners());
		dao.update(companyLearners);
		TasksService.instance().completeTask(tasks);
	}
	
	public void checkRequiredFieldsForTransfer(CompanyLearnersTransfer companyLearnersTransfer) throws Exception {
		if(companyLearnersTransfer.getLearnerTransferType() ==null) {
			throw new Exception("Please Specify which entity is requesting this transfer");
		} else if(companyLearnersTransfer.getTransferRequestType() ==null) {
			throw new Exception("Please select Transfer Request Type");
		} else if(companyLearnersTransfer.getTransferToCompany() ==null) {
			throw new Exception("Please select company");
		} else if(companyLearnersTransfer.getTransferReason() ==null || companyLearnersTransfer.getTransferReason().trim().isEmpty()) {
			throw new Exception("Please provide transfer reason");
		}
	}

	public void requestTransfer(CompanyLearnersTransfer companyLearnersTransfer, Users u) throws Exception {
		CompanyLearners companyLearners = companyLearnersTransfer.getCompanyLearners();		
		List<Users> userList = new ArrayList<>();
		if (companyLearnersTransfer.getTransferRequestType() == TransferRequestTypeEnum.Provider) {
			userList.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompany().getId(), ConfigDocProcessEnum.TP));
			if (userList.size() == 0) {
				throw new Exception("Unable to locate request users, contact support!");
			}else if(companyLearners.getElearner() != null && companyLearners.getElearner()) {
				
			}
		}else if (companyLearnersTransfer.getTransferRequestType() == TransferRequestTypeEnum.Learner) {
			if(companyLearnersTransfer.getCompanyLearners().getInterventionType().getForSdpAccreditaion()!=null && companyLearnersTransfer.getCompanyLearners().getInterventionType().getForSdpAccreditaion()) {
				userList.addAll(companyUsersService.findUsersByCompanyType(companyLearners.getCompany().getId(), ConfigDocProcessEnum.TP));
			}else {
				userList.add(SDFCompanyService.instance().findPrimarySDF(companyLearnersTransfer.getTransferToCompany()).getSdf());
			}
			if (userList.size() == 0) {
				throw new Exception("Unable to locate request users, contact support!");
			}
		}else if (companyLearnersTransfer.getTransferRequestType() == TransferRequestTypeEnum.Workplace) {
			userList.add(SDFCompanyService.instance().findPrimarySDF(companyLearnersTransfer.getTransferToCompany()).getSdf());
			if (userList.size() == 0) {
				throw new Exception("Unable to locate request users, contact support!");
			}
		}
		
		companyLearnersTransfer.setCompanyLearnerPreviousStatus(companyLearners.getStatus());
		companyLearnersTransfer.setLearnerPreviousStatus(companyLearners.getLearnerStatus());
		companyLearnersTransfer.setStatus(ApprovalEnum.PendingApproval);
		companyLearnersTransfer.setLearnerTransferApproval(LearnerTransferApproval.WithCompany);
		companyLearnersTransfer.setCreateUser(u);
		companyLearners.setStatus(ApprovalEnum.PendingApproval);
		companyLearners.setLearnerStatus(LearnerStatusEnum.TransferPendingApproval);

		companyLearnersTransfer.setStatus(ApprovalEnum.PendingApproval);

		dao.update(companyLearners);
		dao.create(companyLearnersTransfer);
		TasksService.instance().findFirstInProcessAndCreateTask(u, companyLearnersTransfer.getId(), companyLearnersTransfer.getClass().getName(), ConfigDocProcessEnum.LearnerTransfer, false);
		/*if(companyLearners.getElearner() != null && companyLearners.getElearner()) {
			List<IDataEntity> dataEntities = new ArrayList<>();	
			
			Signoff signoff = new Signoff();
			signoff.setAccept(false);
			signoff.setUser(userList.get(0));
			signoff.setTargetClass(CompanyLearnersTransfer.class.getName());
			signoff.setTargetKey(companyLearnersTransfer.getId());
			dataEntities.add(signoff);
			
			Signoff signoffLearner = new Signoff();
			signoffLearner.setAccept(false);
			signoffLearner.setUser(companyLearners.getUser());
			signoffLearner.setTargetClass(CompanyLearnersTransfer.class.getName());
			signoffLearner.setTargetKey(companyLearnersTransfer.getId());
			signoffLearner.setSignoffByEnum(SignoffByEnum.Learner);
			dataEntities.add(signoffLearner);
			
			Signoff signoffCreateUser = new Signoff();
			signoffCreateUser.setAccept(false);
			signoffCreateUser.setUser(u);
			signoffCreateUser.setTargetClass(CompanyLearnersTransfer.class.getName());
			signoffCreateUser.setTargetKey(companyLearnersTransfer.getId());
			dataEntities.add(signoffCreateUser);
			
			this.dao.createBatch(dataEntities);
			TasksService.instance().findFirstInProcessAndCreateTask(u, companyLearnersTransfer.getId(), companyLearnersTransfer.getClass().getName(), ConfigDocProcessEnum.ELearnerTransfer, false);
		}else {
			TasksService.instance().findFirstInProcessAndCreateTask(u, companyLearnersTransfer.getId(), companyLearnersTransfer.getClass().getName(), ConfigDocProcessEnum.LearnerTransfer, false);
		}*/
	}

	public void companyRejection(CompanyLearnersTransfer companyLearnersTransfer, Users u, Tasks task) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		companyLearnersTransfer.setStatus(ApprovalEnum.Rejected);
		companyLearnersTransfer.setApprovalDateCompanySelected(getSynchronizedDate());
		companyLearnersTransfer.setCompanySelectedApprovalUser(u);
		updateList.add(companyLearnersTransfer);

		CompanyLearners companyLearners = companyLearnersTransfer.getCompanyLearners();
		companyLearners.setStatus(companyLearnersTransfer.getCompanyLearnerPreviousStatus());
		companyLearners.setLearnerStatus(companyLearnersTransfer.getLearnerPreviousStatus());
		updateList.add(companyLearners);

		if (updateList.size() != 0) {
			dao.updateBatch(updateList);
		}
		TasksService.instance().completeTask(task);

		String subject = "LEARNER TRANSFER APPLICATION NON-APPROVAL".toUpperCase();
		String msg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + companyLearnersTransfer.getCreateUser().getFirstName() + " " + companyLearnersTransfer.getCreateUser().getLastName() + " </p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please note the request to transfer learner "+companyLearners.getUser().getFirstName() + " "+companyLearners.getUser().getLastName()+" ("+companyLearners.getRegistrationNumber()+") has been rejected for the following reason(s):" + "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + companyLearnersTransfer.getRejectionNote() + "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";
		GenericUtility.sendMail(companyLearnersTransfer.getCreateUser(), subject, msg, null);
	}

	public void companyApproval(CompanyLearnersTransfer companyLearnersTransfer, Users users, Tasks task) throws Exception {
		if (companyLearnersTransfer.getCompanyLearners().getUser() == null) {
			throw new Exception("Unable to locate learner assigned for next task, contact support!");
		}
		companyLearnersTransfer.setCompanySelectedApprovalUser(users);
		companyLearnersTransfer.setApprovalDateCompanySelected(getSynchronizedDate());
		companyLearnersTransfer.setLearnerTransferApproval(LearnerTransferApproval.WithLearner);
		dao.update(companyLearnersTransfer);
		List<Users> userList = new ArrayList<>();
		userList.add(companyLearnersTransfer.getCompanyLearners().getUser());
		TasksService.instance().findNextInProcessAndCreateTask(users, task, userList, false);
		// TasksService.instance().findNextInProcessAndCreateTask(users, task, null,
		// false);
	}

	public void learnerRejection(CompanyLearnersTransfer companyLearnersTransfer, Users u, Tasks task) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		Users clo = getCLO(companyLearnersTransfer.getCompanyLearners().getEmployer());
		List<Users> userList = new ArrayList<>();
		if (clo != null && clo.getId() != null) {
			userList.add(clo);
		} else {
			throw new Exception("An attempt to create Site Visit task faild, because we are unable to locate CLO");
		}

		companyLearnersTransfer.setStatus(ApprovalEnum.Rejected);
		companyLearnersTransfer.setApprovalDateLearner(getSynchronizedDate());
		companyLearnersTransfer.setLearnerApproved(u);
		companyLearnersTransfer.setTasks(task);
		updateList.add(companyLearnersTransfer);

		CompanyLearners companyLearners = companyLearnersTransfer.getCompanyLearners();
		companyLearners.setStatus(companyLearnersTransfer.getCompanyLearnerPreviousStatus());
		companyLearners.setLearnerStatus(companyLearnersTransfer.getLearnerPreviousStatus());
		updateList.add(companyLearners);

		if (updateList.size() != 0) {
			dao.updateBatch(updateList);
		}
		TasksService.instance().completeTask(task);

		String desc = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.LearnerTransferSiteVisit.getTaskDescription());
		TasksService.instance().findFirstInProcessAndCreateTask(desc, u, companyLearnersTransfer.getId(), companyLearnersTransfer.getClass().getName(), true, ConfigDocProcessEnum.LearnerTransferSiteVisit, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, u.getFirstName(), MailTagsEnum.LastName, u.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.LearnerTransferSiteVisit.getTaskDescription()), userList);
		sendLearnerRejectionEmail(companyLearnersTransfer);
	}

	public void learnerApproval(CompanyLearnersTransfer companyLearnersTransfer, Users users, Tasks task) throws Exception {
		List<Users> userList = new ArrayList<>();
		/*if (companyLearnersTransfer.getTransferRequestType() == TransferRequestTypeEnum.Learner) {
			userList.add(companyLearnersTransfer.getCompanyLearners().getUser());
		} else if (companyLearnersTransfer.getTransferRequestType() == TransferRequestTypeEnum.Provider) {
			userList.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTransfer.getCompanyLearners().getCompany().getId(), ConfigDocProcessEnum.TP));
		} else if (companyLearnersTransfer.getTransferRequestType() == TransferRequestTypeEnum.Workplace) {
			userList.add(SDFCompanyService.instance().findPrimarySDF(companyLearnersTransfer.getTransferToCompany()).getSdf());			
		}*/
		userList.add(companyLearnersTransfer.getCreateUser());
		if (userList.size() == 0) {
			throw new Exception("Unable to locate users for next task, contact support!");
		}
		// Creating Task to Single user and Send Application Form to Single user
		//Users singleUser = userList.get(0);
		//userList.clear();
		//userList.add(singleUser);

		companyLearnersTransfer.setLearnerApproved(users);
		companyLearnersTransfer.setApprovalDateLearner(getSynchronizedDate());
		companyLearnersTransfer.setLearnerTransferApproval(LearnerTransferApproval.NormalWorkflow);
		dao.update(companyLearnersTransfer);

		TasksService.instance().findNextInProcessAndCreateTask(users, task, userList, false);

		// Sending email
		if (companyLearnersTransfer.getTransferRequestType() == TransferRequestTypeEnum.Learner) {
			//sendLPMFM005Email(companyLearnersTransfer);
			sendLPMFM005AddendumEmail(companyLearnersTransfer, userList.get(0));			
		} else if (companyLearnersTransfer.getTransferRequestType() == TransferRequestTypeEnum.Provider) {
			//sendLPMFM018BEmail(companyLearnersTransfer, userList.get(0));
			sendLPMFM018BAddendumEmail(companyLearnersTransfer, userList.get(0));			
		} else if (companyLearnersTransfer.getTransferRequestType() == TransferRequestTypeEnum.Workplace) {
			//sendLPMFM018BEmail(companyLearnersTransfer, userList.get(0));
			sendLPMFM018BAddendumEmail(companyLearnersTransfer, userList.get(0));
		}
		
		//List<Users> userList = new ArrayList<>();
		// Sending email
		/*if (companyLearnersTransfer.getLearnerTransferType() == LearnerTransferTypeEnum.ProviderTransfer) {
			userList.add(SDFCompanyService.instance().findPrimarySDF(companyLearnersTransfer.getTransferToCompany()).getSdf());
			if (userList == null || userList.size() <= 0) {
				throw new Exception("No primary SDF found  for " + companyLearnersTransfer.getTransferToCompany().getCompanyName() + ".");
			}
			Users u = userList.get(0);
			sendLPMAD008Email(companyLearnersTransfer, u);
		} else if (companyLearnersTransfer.getLearnerTransferType() == LearnerTransferTypeEnum.WorkplaceTransfer) {
			CompanyUsersService companyUsersService = new CompanyUsersService();
			userList.addAll(companyUsersService.findUsersByCompanyType(companyLearnersTransfer.getCompanyLearners().getCompany().getId(), ConfigDocProcessEnum.TP));
			if (userList == null || userList.size() <= 0) {
				throw new Exception("No primary Company Contact found  for " + companyLearnersTransfer.getTransferToCompany().getCompanyName() + ".");
			}
			// Sending to 1 user
			Users tempUser = userList.get(0);
			userList.clear();
			userList.add(tempUser);
			sendLPMAD002Email(companyLearnersTransfer, tempUser);
		}*/

	}

	public void requestLostTime(CompanyLearnersLostTime companyLearnersLostTime, Users u) throws Exception {
		companyLearnersLostTime.setStatus(ApprovalEnum.PendingApproval);
		companyLearnersLostTime.setCreateUser(u);
		calculateNewTerminationDate(companyLearnersLostTime);
		if(companyLearnersLostTime.getCreatedByEnum() == CreatedByEnum.merSETA) {
			companyLearnersLostTime.setSignoffByEnum(SignoffByEnum.merSETA);
		}else if(companyLearnersLostTime.getCreatedByEnum() == CreatedByEnum.sdp) {
			companyLearnersLostTime.setSignoffByEnum(SignoffByEnum.sdp);
		}else if(companyLearnersLostTime.getCreatedByEnum() == CreatedByEnum.sdf) {
			companyLearnersLostTime.setSignoffByEnum(SignoffByEnum.sdf);
		}
		dao.create(companyLearnersLostTime);
		
		CompanyLearners cl = findByKey(companyLearnersLostTime.getCompanyLearners().getId());
		cl.setLearnerStatus(LearnerStatusEnum.PendingLostTime);
		update(cl);
		
		List<Users> usersList = new ArrayList<>();
		usersList.add(u);
		
		if(cl.getElearner() != null && cl.getElearner()){
			List<IDataEntity> dataEntities = new ArrayList<>();	
			
			Signoff signoff = new Signoff();
			signoff.setAccept(false);
			signoff.setUser(u);
			signoff.setTargetClass(CompanyLearnersLostTime.class.getName());
			signoff.setTargetKey(companyLearnersLostTime.getId());
			//signoff.setSignoffByEnum(SignoffByEnum.User);
			if(companyLearnersLostTime.getCreatedByEnum() == CreatedByEnum.merSETA) {
				signoff.setSignoffByEnum(SignoffByEnum.merSETA);
			}else if(companyLearnersLostTime.getCreatedByEnum() == CreatedByEnum.sdp) {
				signoff.setSignoffByEnum(SignoffByEnum.sdp);
			}else if(companyLearnersLostTime.getCreatedByEnum() == CreatedByEnum.sdf) {
				signoff.setSignoffByEnum(SignoffByEnum.sdf);
			}
			dataEntities.add(signoff);
			
			Signoff signoffLearner = new Signoff();
			signoffLearner.setAccept(false);
			signoffLearner.setUser(cl.getUser());
			signoffLearner.setTargetClass(CompanyLearnersLostTime.class.getName());
			signoffLearner.setTargetKey(companyLearnersLostTime.getId());
			signoffLearner.setSignoffByEnum(SignoffByEnum.Learner);
			dataEntities.add(signoffLearner);
			dao.createBatch(dataEntities);
			
			String description = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.ELearnerLostTime.getTaskDescription());
			TasksService.instance().findFirstInProcessAndCreateTask(description, u, companyLearnersLostTime.getId(), companyLearnersLostTime.getClass().getName(), true, ConfigDocProcessEnum.ELearnerLostTime, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, u.getFirstName(), MailTagsEnum.LastName, u.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.ELearnerLostTime.getTaskDescription()), usersList);
		}else {
			// SEND JASPER HERE
			sendLPMFM011Email(companyLearnersLostTime, u);
			String description = AbstractUI.getEntryLanguage(ConfigDocProcessEnum.LearnerLostTime.getTaskDescription());
			TasksService.instance().findFirstInProcessAndCreateTask(description, u, companyLearnersLostTime.getId(), companyLearnersLostTime.getClass().getName(), true, ConfigDocProcessEnum.LearnerLostTime, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, u.getFirstName(), MailTagsEnum.LastName, u.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.LearnerLostTime.getTaskDescription()), usersList);
		}
		// sendLPMAD001Email(companyLearnersLostTime, u);
		// sendLPMTP013Email(companyLearnersLostTime, u);
		// TasksService.instance().findFirstInProcessAndCreateTask(u,
		// companyLearnersLostTime.getId(),
		// companyLearnersLostTime.getClass().getName(),
		// ConfigDocProcessEnum.LearnerLostTime, false);

	}

	/**
	 * Calculate Lost time new termination date
	 **/
	public void calculateNewTerminationDate(CompanyLearnersLostTime companyLearnersLostTime) throws Exception {

		Date originalTerminationdate = companyLearnersLostTime.getCompanyLearners().getCompletionDate();
		Date newDate = originalTerminationdate;
		//int lostDays = GenericUtility.getDaysBetweenDates(companyLearnersLostTime.getLostTimeStartDate(), companyLearnersLostTime.getLostTimeEndDate());
		int lostDays = GenericUtility.getDaysBetweenDatesExcludeWeekends(companyLearnersLostTime.getLostTimeStartDate(), companyLearnersLostTime.getLostTimeEndDate());
		
		if (lostDays < 1) {
			throw new Exception("Lost day(s) cannot be less than 1");
		}

		int daysExtended = 0;
		if (companyLearnersLostTime.getLostTimeReason() == LostTimeReason.AbsentWithoutPermission) {
			/* Absent without permission (AWOL) - Extension on a day for a day basis */
			//newDate = GenericUtility.addDaysToDate(originalTerminationdate, lostDays);
			newDate = GenericUtility.addDaysToDateExcludeWeekends(originalTerminationdate, lostDays);
			daysExtended = lostDays;
		} else if (companyLearnersLostTime.getLostTimeReason() == LostTimeReason.Suspension5) {
			/* Suspension (maximum 5 days) */
			if (lostDays > 5) {
				throw new Exception("Maximum days cannot be more than 5");
			} else {
				//newDate = GenericUtility.addDaysToDate(originalTerminationdate, lostDays);
				newDate = GenericUtility.addDaysToDateExcludeWeekends(originalTerminationdate, lostDays);
				daysExtended = lostDays;
			}

		} else if (companyLearnersLostTime.getLostTimeReason() == LostTimeReason.Suspension60) {
			/* Suspension (6 - 30 days) */
			if (lostDays > 30) {
				throw new Exception("Maximum days cannot be more than 30");
			}
			if (lostDays < 6) {
				throw new Exception("Maximum days cannot be less than 6");
			}
			//newDate = GenericUtility.addDaysToDate(originalTerminationdate, lostDays);
			newDate = GenericUtility.addDaysToDateExcludeWeekends(originalTerminationdate, lostDays);
			daysExtended = lostDays;
		} else if (companyLearnersLostTime.getLostTimeReason() == LostTimeReason.Exceeding30Days) {
			/*
			 * Exceeding 30 day sick period in the respective apprenticeship year: Where a
			 * learner is absent for more than 30 days in a learning year, the termination
			 * date is extend on a day for a day basis for each day of absence for everyday
			 * after the 30 days period.
			 * 
			 * So if a learner is absent for 33 days, extension would be 33 days
			 */
			if (lostDays < 31) {
				throw new Exception("This option is available if a learner is absent for more than 30 days in a learning year");
			} else {
				//newDate = GenericUtility.addDaysToDate(originalTerminationdate, lostDays);
				lostDays = lostDays -30;
				//newDate = GenericUtility.addDaysToDateExcludeWeekends(originalTerminationdate, lostDays);
				newDate = GenericUtility.addDaysToDate(originalTerminationdate, lostDays);
				daysExtended = lostDays;
			}

		} else if (companyLearnersLostTime.getLostTimeReason() == LostTimeReason.MaternityLeave) {
			/* Maternity/Paternity leave = day for a day extension */
			//newDate = GenericUtility.addDaysToDate(originalTerminationdate, lostDays);
			newDate = GenericUtility.addDaysToDateExcludeWeekends(originalTerminationdate, lostDays);
			daysExtended = lostDays;
		} else if (companyLearnersLostTime.getLostTimeReason() == LostTimeReason.SpecialLeave) {
			/* Extend on a day for a day basis */
			//newDate = GenericUtility.addDaysToDate(originalTerminationdate, lostDays);
			newDate = GenericUtility.addDaysToDateExcludeWeekends(originalTerminationdate, lostDays);
			daysExtended = lostDays;
		} else if (companyLearnersLostTime.getLostTimeReason() == LostTimeReason.ExtensionOfAgreement) {
			if (companyLearnersLostTime.getDesignatedTradeExtension() != null && companyLearnersLostTime.getDesignatedTradeExtension()) {
				if (designatedTradeLevelService == null) {
					designatedTradeLevelService = new DesignatedTradeLevelService();
				}
				if (designatedTradeService == null) {
					designatedTradeService = new DesignatedTradeService();
				}

				boolean noOrder = designatedTradeLevelService.determainQualificationDesignatedTradeAndNoCompletionOrder(companyLearnersLostTime.getCompanyLearners().getQualification(), 0l);
				if (noOrder) {
					List<DesignatedTradeLevel> designatedTradeLevelList = designatedTradeLevelService.findByQualificationIdOrderByLevel(companyLearnersLostTime.getCompanyLearners().getQualification());
					if (!designatedTradeLevelList.isEmpty()) {
						DesignatedTrade dt = designatedTradeService.findByKey(designatedTradeLevelList.get(0).getDesignatedTrade().getId());
						companyLearnersLostTime.setDesignatedTrade(dt);
						companyLearnersLostTime.setWeeksAssigned(dt.getExtention().intValue());
						newDate = GenericUtility.addWeeksToDate(originalTerminationdate,dt.getExtention().intValue());
						daysExtended = GenericUtility.getDaysBetweenDatesExcludeWeekends(originalTerminationdate, newDate);
						companyLearnersLostTime.setCalculatedEndDate(newDate);
					} else {
						throw new Exception("Unable to locate levels assigned to designated trade. Contact support!");
					}
				} else {
					DesignatedTradeLevel dtl = locateCorrectDesignatedTradeLevelReturnTradeLevel(companyLearnersLostTime.getCompanyLearners());
					if (dtl != null) {
						DesignatedTrade dt = designatedTradeService.findByKey(dtl.getDesignatedTrade().getId());
						companyLearnersLostTime.setDesignatedTradeLevel(dtl);
						companyLearnersLostTime.setDesignatedTrade(dt);
						companyLearnersLostTime.setWeeksAssigned(dt.getExtention().intValue());
						newDate = GenericUtility.addWeeksToDate(originalTerminationdate,dt.getExtention().intValue());
						daysExtended = GenericUtility.getDaysBetweenDatesExcludeWeekends(originalTerminationdate, newDate);
						companyLearnersLostTime.setCalculatedEndDate(newDate);
					}else {
						throw new Exception("Unable to locate current level assigned to designated trade. Contact support!");
					}
				}
			} else {
				/*
				 * Version Two
				 * extend by 52 weeks (1 year for extension of agreement) for trades that are not desiganted
				 */
				companyLearnersLostTime.setWeeksAssigned(52);
				newDate = GenericUtility.addWeeksToDate(originalTerminationdate, lostDays);
				daysExtended = GenericUtility.getDaysBetweenDatesExcludeWeekends(originalTerminationdate, newDate);
				/*
				 * Extension of Agreement - allow for an extension to be applied for. The
				 * general rule is 6 months. Motor time-based is a 1-year automatic extension
				 * 
				 * Version One
				 */
//				newDate = GenericUtility.addWeeksToDate(originalTerminationdate, 24);
//				daysExtended = 168;
//				if (companyLearnersLostTime.getCompanyLearners().getQualification() != null) {
//					if (companyLearnersLostTime.getCompanyLearners().getQualification().getDesignatedTrade() != null) {
//						if (companyLearnersLostTime.getCompanyLearners().getQualification().getDesignatedTrade().getId() == 2L)// MOTOR TIME BASED
//						{
//							newDate = GenericUtility.addMonthsToDate(originalTerminationdate, 12);
//							daysExtended = 52;
//						} else if (companyLearnersLostTime.getCompanyLearners().getQualification().getDesignatedTrade().getId() == 3L)// MOTOR CBMT
//						{
//							if (companyLearnersLostTime.getCompanyLearners().getDesignatedTradeLevel() != null) {
//								/*
//								 * For CBMT, if two levels from completion, request would be submitted to
//								 * generate a new agreement after contract ends and this request would have to
//								 * be submitted prior to contract/agreement terminating
//								 */
//								if (companyLearnersLostTime.getCompanyLearners().getDesignatedTradeLevel().getDescription() != "Level 1" || companyLearnersLostTime.getCompanyLearners().getDesignatedTradeLevel().getDescription() != "Level 2") {
//									throw new Exception("Extension cannot be granted for learner who completed Level 1 or Level 2");
//								}
//							}
//						}
//					}
//				}
			}
			

		}

		companyLearnersLostTime.setNewCompletionDate(newDate);
		companyLearnersLostTime.setDaysExtended(daysExtended);
	}

	public void requestTermination(CompanyLearnersTermination companyLearnersTermination, Users u) throws Exception {

		CompanyLearners companyLearners = companyLearnersTermination.getCompanyLearners();
		companyLearners.setStatus(ApprovalEnum.PendingApproval);
		companyLearners.setLearnerStatus(LearnerStatusEnum.PendingTermination);
		companyLearnersTermination.setStatus(ApprovalEnum.PendingApproval);
		if(companyLearnersTermination.getCreatedByEnum() == CreatedByEnum.merSETA) {
			companyLearnersTermination.setSignoffByEnum(SignoffByEnum.merSETA);
		}else if(companyLearnersTermination.getCreatedByEnum() == CreatedByEnum.sdp) {
			companyLearnersTermination.setSignoffByEnum(SignoffByEnum.sdp);
		}else if(companyLearnersTermination.getCreatedByEnum() == CreatedByEnum.sdf) {
			companyLearnersTermination.setSignoffByEnum(SignoffByEnum.sdf);
		}
		dao.update(companyLearners);
		dao.create(companyLearnersTermination);
		
		if(companyLearners.getElearner() != null && companyLearners.getElearner()) {
			List<IDataEntity> dataEntities = new ArrayList<>();	
			
			Signoff signoff = new Signoff();
			signoff.setAccept(false);
			signoff.setUser(u);
			signoff.setTargetClass(CompanyLearnersTermination.class.getName());
			signoff.setTargetKey(companyLearnersTermination.getId());
			//signoff.setSignoffByEnum(SignoffByEnum.User);
			if(companyLearnersTermination.getCreatedByEnum() == CreatedByEnum.merSETA) {
				signoff.setSignoffByEnum(SignoffByEnum.merSETA);
			}else if(companyLearnersTermination.getCreatedByEnum() == CreatedByEnum.sdp) {
				signoff.setSignoffByEnum(SignoffByEnum.sdp);
			}else if(companyLearnersTermination.getCreatedByEnum() == CreatedByEnum.sdf) {
				signoff.setSignoffByEnum(SignoffByEnum.sdf);
			}
			dataEntities.add(signoff);
			
			ConfigDocProcessEnum configDocProcessEnum = ConfigDocProcessEnum.ELearnerOneSidedTermination;
			if (companyLearnersTermination.getTerminationTypeEnum() == TerminationTypeEnum.MutualSidedTermination) {
				configDocProcessEnum = ConfigDocProcessEnum.ELearnerMutualTermination;
				Signoff signoffLearner = new Signoff();
				signoffLearner.setAccept(false);
				signoffLearner.setUser(companyLearners.getUser());
				signoffLearner.setTargetClass(CompanyLearnersTermination.class.getName());
				signoffLearner.setTargetKey(companyLearnersTermination.getId());
				signoffLearner.setSignoffByEnum(SignoffByEnum.Learner);
				dataEntities.add(signoffLearner);
			}
			dao.createBatch(dataEntities);		
			List<Users> usersList = new ArrayList<>();
			usersList.add(u);
			String description = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
			TasksService.instance().findFirstInProcessAndCreateTask(description, u, companyLearnersTermination.getId(), companyLearnersTermination.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, u.getFirstName(), MailTagsEnum.LastName, u.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), usersList);
		}else {
			// SEND JASPER HERE
			// sendLPMTP003Email(companyLearnersTermination);
			if (companyLearnersTermination.getTerminationTypeEnum() == TerminationTypeEnum.MutualSidedTermination) {
				sendLPMFM016Email(companyLearnersTermination, u);
			} else if (companyLearnersTermination.getTerminationTypeEnum() == TerminationTypeEnum.OneSidedTermination ||
					companyLearnersTermination.getTerminationTypeEnum() == TerminationTypeEnum.DeceasedLearnerTermination) {
				sendLPMFM017Email(companyLearnersTermination, u);
			}
			ConfigDocProcessEnum configDocProcessEnum = ConfigDocProcessEnum.OneLearnerTermination;
			if (companyLearnersTermination.getTerminationTypeEnum() == TerminationTypeEnum.MutualSidedTermination) {
				configDocProcessEnum = ConfigDocProcessEnum.MutualLearnerTermination;
			}
			List<Users> usersList = new ArrayList<>();
			usersList.add(u);
			String description = AbstractUI.getEntryLanguage(configDocProcessEnum.getTaskDescription());
			TasksService.instance().findFirstInProcessAndCreateTask(description, u, companyLearnersTermination.getId(), companyLearnersTermination.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, u.getFirstName(), MailTagsEnum.LastName, u.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), usersList);
		}

	}

	public void initiateWorkplaceApproval(CompanyLearnersChange companyLearnersChange, Users u) throws Exception {
		WorkPlaceApprovalService wpas = new WorkPlaceApprovalService();
		SDFCompany primarySDF = companyService.findPrimarySDF(companyService.findByKey(companyLearnersChange.getCompanyLearners().getCompany().getId()));
		if (primarySDF == null) {
			throw new Exception("Unable to locate Primary SDF for: " + companyService.findByKey(companyLearnersChange.getCompanyLearners().getCompany().getId()).getLevyNumber());
		} else {
			boolean byQualification = true;
			if (companyLearnersChange.getQualification().getQualificationtypeid().matches(HAJConstants.TRADE_QUALIFICATION_CODE)) {
				byQualification = false;
			}
			wpas.createWorkplaceApproval(companyLearnersChange.getCompanyLearners().getCompany(), companyLearnersChange.getQualification(), null, null, u, byQualification);
		}
	}
	
	public boolean workplaceApprovalAccreditationChecks(CompanyLearnersChange companyLearnersChange, Users u) throws Exception {
		boolean continueAplication = true;		
		CompanyLearners cl =companyLearnersChange.getCompanyLearners();
		Company company = cl.getEmployer();			
		if(company.getOrganisationType().getWorkplaceApprovalRequired() && cl.getInterventionType().getWorkplaceApprovalRequired() != null &&  cl.getInterventionType().getWorkplaceApprovalRequired()) {
			if(getCompanyLearnerQualification(cl) != null && getCompanyLearnerQualification(cl).getId() != null) {
				WorkPlaceApproval workPlaceApproval = null;
				
				if(cl.getSite() != null) {
					workPlaceApproval = workPlaceApprovalService.findApprovedWorkPlaceApprovalByCompanyAndQualificationAndSites(company, getCompanyLearnerQualification(companyLearnersChange), ApprovalEnum.Approved, cl.getSite().getId());
				}else {
					workPlaceApproval = workPlaceApprovalService.findApprovedWorkPlaceApprovalByCompanyAndQualification(company, getCompanyLearnerQualification(companyLearnersChange), ApprovalEnum.Approved);
				}
				
				if(workPlaceApproval == null) {
					continueAplication= false;
				}			
			}
		}
		return continueAplication;
	}

	public boolean workplaceApprovalAccreditationChecksOld(CompanyLearnersChange companyLearnersChange, Users u) throws Exception {
		boolean continueAplication = true;
		if(companyLearnersChange.getLearnerChangeTypeEnum() !=LearnerChangeTypeEnum.ChangeCommencementDate && companyLearnersChange.getLearnerChangeTypeEnum() !=LearnerChangeTypeEnum.ChangeNonCreditBearingTitle)
		{
			companyLearnersChange.getCompanyLearners().setCompany(companyService.findByKey(companyLearnersChange.getCompanyLearners().getCompany().getId()));
			Qualification qualification = null;
			if (companyLearnersChange.getQualification() != null) {
				qualification = companyLearnersChange.getQualification();
			} else if (companyLearnersChange.getSkillsProgram() != null && companyLearnersChange.getSkillsProgram().getQualification() != null) {
				qualification = companyLearnersChange.getSkillsProgram().getQualification();
			} else if (companyLearnersChange.getSkillsSet() != null && companyLearnersChange.getSkillsSet().getQualification() != null) {
				qualification = companyLearnersChange.getSkillsSet().getQualification();
			}
			if (qualification != null && companyLearnersChange.getCompanyLearners().getInterventionType().getWorkplaceApprovalRequired() != companyLearnersChange.getCompanyLearners().getInterventionType().getWorkplaceApprovalRequired() && companyLearnersChange.getCompanyLearners().getInterventionType().getWorkplaceApprovalRequired()) {
				if (companyService == null) {
					companyService = new CompanyService();
				}
				WorkPlaceApprovalService wpas = new WorkPlaceApprovalService();
				boolean isworkplaceapproved = false;
				if (companyLearnersChange.getCompanyLearners().getSite() == null) {
					//pat to resolve
					isworkplaceapproved = wpas.workplaceApprovedCheck(companyService.findByKey(companyLearnersChange.getCompanyLearners().getCompany().getId()), qualification, null, null, u);
				} else {
					isworkplaceapproved = wpas.workplaceApprovedCheck(companyService.findByKey(companyLearnersChange.getCompanyLearners().getCompany().getId()), qualification, null, SitesService.instance().findByKey(companyLearnersChange.getCompanyLearners().getSite().getId()), u);
				}
	
				if (!isworkplaceapproved) {
					continueAplication = false;
				}
				wpas = null;
			} else {
				throw new Exception("Unable to locate qualification assigned, contact support");
			}
		}

		return continueAplication;

	}
	
	public void validateLearnerChange(CompanyLearnersChange companyLearnersChange) throws Exception {	
		if(companyLearnersChange.getCompanyLearners().getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
			if (companyLearnersChange.getQualification() == null ) {						
				throw new Exception("Select SAQA qualification");
			}
			if (companyLearnersChange.getCompanyLearners().getQualification().equals(companyLearnersChange.getQualification())) {
				throw new Exception("Learner already registered for the requested qualification");
			}
		}else if(companyLearnersChange.getCompanyLearners().getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			if (companyLearnersChange.getLearnership() == null) {				
				throw new Exception("Select learnership");
			}
		}else {
			if(SKILLS_PROGRAM_LIST.contains(companyLearnersChange.getCompanyLearners().getInterventionType().getId())) {
				if (companyLearnersChange.getCompanyLearners().getSkillsProgram()== null) {
					throw new Exception("Select Skills Programme");
				}
				if (companyLearnersChange.getCompanyLearners().getSkillsProgram().equals(companyLearnersChange.getSkillsProgram())) {
					throw new Exception("Learner already registered for the requested Skills Programme");
				}
			}else if(SKILLS_SET_LIST.contains(companyLearnersChange.getCompanyLearners().getInterventionType().getId())) {
				if (companyLearnersChange.getCompanyLearners().getSkillsSet()== null) {
					throw new Exception("Select Skills Programme");
				}
				if (companyLearnersChange.getCompanyLearners().getSkillsSet().equals(companyLearnersChange.getSkillsSet())) {
					throw new Exception("Learner already accredited for the requested unit standard Skills Set");
				}
			}else if(companyLearnersChange.getCompanyLearners().getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
				if (companyLearnersChange.getUnitStandard() == null) {
					throw new Exception("Select SAQA UnitStandard");
				}
				if (companyLearnersChange.getQualification() == null) {
					throw new Exception("Select SAQA Qualification");
				}
				if (companyLearnersChange.getCompanyLearners().getUnitStandard().equals(companyLearnersChange.getUnitStandard())) {
					throw new Exception("Learner already accredited for the requested unit standard ");
				}
			}else if(companyLearnersChange.getCompanyLearners().getInterventionType().getId() == HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
				if(companyLearnersChange.getNonCredidBearingDescription() == null || companyLearnersChange.getNonCredidBearingDescription().matches("") || companyLearnersChange.getNonCredidBearingDescription().isEmpty()) {
					throw new Exception("Please provide non credid bearing description");
				}
			}else{				
				if (companyLearnersChange.getQualification() == null ) {						
					throw new Exception("Select SAQA qualification");
				}
				if (companyLearnersChange.getCompanyLearners().getQualification().equals(companyLearnersChange.getQualification())) {
					throw new Exception("Learner already registered for the requested qualification");
				}
			}
		}
	}

	public void validateLearnerChangeOld(CompanyLearnersChange companyLearnersChange) throws Exception {

		if (companyLearnersChange.getLearnerChangeTypeEnum() == null) {
			throw new Exception("Please specify what you want to change");
		}

		if (companyLearnersChange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeOfProgramQalification || companyLearnersChange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeOfProgramTrade) {
			
			if(companyLearnersChange.getCompanyLearners().getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
				if (companyLearnersChange.getQualification() == null ) {						
					throw new Exception("Select SAQA qualification");
				}
				if (companyLearnersChange.getCompanyLearners().getQualification().equals(companyLearnersChange.getQualification())) {
					throw new Exception("Learner already registered for the requested qualification");
				}
			}else if(companyLearnersChange.getCompanyLearners().getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
				if (companyLearnersChange.getLearnership() == null) {				
					throw new Exception("Select learnership");
				}
			}else {
				if(SKILLS_PROGRAM_LIST.contains(companyLearnersChange.getCompanyLearners().getInterventionType().getId())) {
					if (companyLearnersChange.getCompanyLearners().getSkillsProgram()== null) {
						throw new Exception("Select Skills Programme");
					}
					if (companyLearnersChange.getCompanyLearners().getSkillsProgram().equals(companyLearnersChange.getSkillsProgram())) {
						throw new Exception("Learner already registered for the requested Skills Programme");
					}
				}else if(SKILLS_SET_LIST.contains(companyLearnersChange.getCompanyLearners().getInterventionType().getId())) {
					if (companyLearnersChange.getCompanyLearners().getSkillsSet()== null) {
						throw new Exception("Select Skills Programme");
					}
					if (companyLearnersChange.getCompanyLearners().getSkillsSet().equals(companyLearnersChange.getSkillsSet())) {
						throw new Exception("Learner already accredited for the requested unit standard Skills Set");
					}
				}else if(companyLearnersChange.getCompanyLearners().getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
					if (companyLearnersChange.getUnitStandard() == null) {
						throw new Exception("Select SAQA UnitStandard");
					}
					if (companyLearnersChange.getQualification() == null) {
						throw new Exception("Select SAQA Qualification");
					}
					if (companyLearnersChange.getCompanyLearners().getUnitStandard().equals(companyLearnersChange.getUnitStandard())) {
						throw new Exception("Learner already accredited for the requested unit standard ");
					}
				}else {
					if (companyLearnersChange.getQualification() == null ) {						
						throw new Exception("Select SAQA qualification");
					}
					if (companyLearnersChange.getCompanyLearners().getQualification().equals(companyLearnersChange.getQualification())) {
						throw new Exception("Learner already registered for the requested qualification");
					}
				}
			}
		} else if (companyLearnersChange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeCommencementDate) {
			/*
			 * if(companyLearnersChange.getCompanyLearners().getInterventionType() !=null &&
			 * companyLearnersChange.getCompanyLearners().getInterventionType().getForm()
			 * !=null &&
			 * companyLearnersChange.getCompanyLearners().getInterventionType().getForm().
			 * equals("002")) { throw new
			 * Exception("Learner agreement commencement date may not be amended as per the Workplace Based Learning Programme Agreement Regulations, 2018"
			 * ); }
			 */
			if (companyLearnersChange.getCommencmentDate() == null) {
				throw new Exception("Select commencment date");
			}
			if (companyLearnersChange.getCompletionDate() == null) {
				throw new Exception("Select completion date");
			}
			if (companyLearnersChange.getCompanyLearners().getCommencementDate() != null) {
				if (companyLearnersChange.getCompanyLearners().getCommencementDate().equals(companyLearnersChange.getCompletionDate())) {
					throw new Exception("Your new commencement date cannot be the same as your current commencement date");
				}
			}
		} else if (companyLearnersChange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeNonCreditBearingTitle) {
			if (companyLearnersChange.getNonCredidBearingDescription().length() > 50) {
				throw new Exception("Your Non Credit Bearing Title is too long");
			}
		}
	}

	public String tPAccreditedChecks(CompanyLearnersChange companyLearnersChange) throws Exception {
		String error = "";
		CompanyLearners cl = companyLearnersChange.getCompanyLearners();
		if(cl == null) {
			error = "Learner error!!!. Please contact your administrator";
		}else if(cl.getInterventionType().getForSdpAccreditaion() != null && cl.getInterventionType().getForSdpAccreditaion()) {
			System.out.print("Training Provider Application");
			TrainingProviderApplication trainingProviderApplication = TrainingProviderApplicationService.instance().findByKey(cl.getTrainingProviderApplication().getId());
			if(trainingProviderApplication == null) {
				 error = "Training Provider Application Issue!!!. Please contact your administrator";				
			}
		
			if(cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
				if(trainingProviderApplicationService.countTrainingProviderApplicationQualifications(trainingProviderApplication.getCompany().getId(), cl.getQualification().getId(), true, ApprovalEnum.Approved) == 0) {
					error = "Please note that you are not accredited for this qualification. You may apply for accreditation or transfer the learner to an accredited provider.";
				}
			}else if(cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
				System.out.print("Learnership");
				if(trainingProviderApplicationService.countTrainingProviderApplicationQualifications(trainingProviderApplication.getCompany().getId(), cl.getLearnership().getQualification().getId(), true, ApprovalEnum.Approved) == 0) {
					error = "Please note that you are not accredited for this qualification. You may apply for accreditation or transfer the learner to an accredited provider.";
				}
			}else {
				if(SKILLS_PROGRAM_LIST.contains(cl.getInterventionType().getId())) {
					System.out.print("SKILLS_PROGRAM");
					TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService = new TrainingProviderSkillsProgrammeService();
					if(trainingProviderSkillsProgrammeService.countByTrainingProviderApplication(trainingProviderApplication.getId(), cl.getSkillsProgram().getId(), true, ApprovalEnum.Approved) == 0) {
						System.out.print("Please note that you are not accredited for this qualification");
						error = "Please note that you are not accredited for this qualification. You may apply for accreditation or transfer the learner to an accredited provider.";
					}
				}else if(SKILLS_SET_LIST.contains(cl.getInterventionType().getId())) {
					TrainingProviderSkillsSetService trainingProviderSkillsSetService = new TrainingProviderSkillsSetService();
					if(trainingProviderSkillsSetService.countByTrainingProviderApplication(trainingProviderApplication.getId(), cl.getSkillsSet().getId(), true, ApprovalEnum.Approved) == 0) {
						error = "Please note that you are not accredited for this qualification. You may apply for accreditation or transfer the learner to an accredited provider.";
					}
				}else if(cl.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
					if(trainingProviderApplicationService.countTrainingProviderApplicationUnitStandard(trainingProviderApplication.getCompany().getId(), cl.getUnitStandard().getId(), true, ApprovalEnum.Approved) == 0) {
						error = "Please note that you are not accredited for this qualification. You may apply for accreditation or transfer the learner to an accredited provider.";
					}
				}else {
					if(cl.getQualification() != null) {
						if(trainingProviderApplicationService.countTrainingProviderApplicationQualifications(trainingProviderApplication.getCompany().getId(),cl.getQualification().getId(), true, ApprovalEnum.Approved) == 0) {
							error = "Please note that you are not accredited for this qualification. You may apply for accreditation or transfer the learner to an accredited provider.";
						}
					}else {
						error = "Error checking accreditation!!!. Please contact you administrator";
					}
				}
			}							
		}else {
			error = "Error checking accreditation!!!. Please contact you administrator";
		}
		return error;
	}
	
	public boolean tpAccreditationChecks(CompanyLearnersChange companyLearnersChange) throws Exception {
		boolean accredited = true;
		CompanyLearners cl = companyLearnersChange.getCompanyLearners();
		if(cl.getInterventionType().getForSdpAccreditaion() != null && cl.getInterventionType().getForSdpAccreditaion()) {
			TrainingProviderApplication trainingProviderApplication = TrainingProviderApplicationService.instance().findByKey(cl.getTrainingProviderApplication().getId());
			if(cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
				if(trainingProviderApplicationService.countTrainingProviderApplicationQualifications(trainingProviderApplication.getCompany().getId(), companyLearnersChange.getQualification().getId(), true, ApprovalEnum.Approved) > 0) {
					accredited = true;
				}else {
					accredited = false;
				}
			}else if(cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
				if(trainingProviderApplicationService.countTrainingProviderApplicationQualifications(trainingProviderApplication.getCompany().getId(), companyLearnersChange.getLearnership().getQualification().getId(), true, ApprovalEnum.Approved) > 0) {
					accredited = true;
				}else {
					accredited = false;
				}
			}else {
				if(SKILLS_PROGRAM_LIST.contains(cl.getInterventionType().getId())) {
					TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService = new TrainingProviderSkillsProgrammeService();
					if(trainingProviderSkillsProgrammeService.countByTrainingProviderApplication(trainingProviderApplication.getId(), companyLearnersChange.getSkillsProgram().getId(), true, ApprovalEnum.Approved) > 0) {
						accredited = true;
					}else {
						accredited = false;
					}
				}else if(SKILLS_SET_LIST.contains(cl.getInterventionType().getId())) {
					TrainingProviderSkillsSetService trainingProviderSkillsSetService = new TrainingProviderSkillsSetService();
					if(trainingProviderSkillsSetService.countByTrainingProviderApplication(trainingProviderApplication.getId(), companyLearnersChange.getSkillsSet().getId(), true, ApprovalEnum.Approved) > 0) {
						accredited = true;
					}else {
						accredited = false;
					}
				}else if(cl.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
					if(trainingProviderApplicationService.countTrainingProviderApplicationUnitStandard(trainingProviderApplication.getCompany().getId(), companyLearnersChange.getUnitStandard().getId(), true, ApprovalEnum.Approved) > 0) {
						accredited = true;
					}else {
						accredited = false;
					}
				}else {
					if(cl.getQualification() != null) {
						if(trainingProviderApplicationService.countTrainingProviderApplicationQualifications(trainingProviderApplication.getCompany().getId(),companyLearnersChange.getQualification().getId(), true, ApprovalEnum.Approved) > 0) {
							accredited = true;
						}
					}else {
						accredited = false;
					}
				}
			}							
		}
		return accredited;
	}

	public void requestChange(CompanyLearnersChange companyLearnersChange, Users u) throws Exception {		
		CompanyLearners companyLearners = companyLearnersChange.getCompanyLearners();
		companyLearnersChange.setLearnerStatus(companyLearners.getLearnerStatus());
		companyLearners.setStatus(ApprovalEnum.PendingApproval);
		companyLearners.setLearnerStatus(LearnerStatusEnum.PendingChangeApproval);
		companyLearnersChange.setStatus(ApprovalEnum.PendingApproval);
		companyLearnersChange.setCreateUser(u);
		if(companyLearnersChange.getCreatedByEnum() == CreatedByEnum.merSETA) {
			companyLearnersChange.setSignoffByEnum(SignoffByEnum.merSETA);
		}else if(companyLearnersChange.getCreatedByEnum() == CreatedByEnum.sdp) {
			companyLearnersChange.setSignoffByEnum(SignoffByEnum.sdp);
		}else if(companyLearnersChange.getCreatedByEnum() == CreatedByEnum.sdf) {
			companyLearnersChange.setSignoffByEnum(SignoffByEnum.sdf);
		}
		
		dao.update(companyLearners);
		dao.create(companyLearnersChange);
		if(companyLearners.getElearner() != null && companyLearners.getElearner()) {
			List<IDataEntity> dataEntities = new ArrayList<>();	
			
			Signoff signoff = new Signoff();
			signoff.setAccept(false);
			signoff.setUser(u);
			signoff.setTargetClass(CompanyLearnersChange.class.getName());
			signoff.setTargetKey(companyLearnersChange.getId());
			//signoff.setSignoffByEnum(SignoffByEnum.User);
			if(companyLearnersChange.getCreatedByEnum() == CreatedByEnum.merSETA) {
				signoff.setSignoffByEnum(SignoffByEnum.merSETA);
			}else if(companyLearnersChange.getCreatedByEnum() == CreatedByEnum.sdp) {
				signoff.setSignoffByEnum(SignoffByEnum.sdp);
			}else if(companyLearnersChange.getCreatedByEnum() == CreatedByEnum.sdf) {
				signoff.setSignoffByEnum(SignoffByEnum.sdf);
			}
			dataEntities.add(signoff);
			
			Signoff signoffLearner = new Signoff();
			signoffLearner.setAccept(false);
			signoffLearner.setUser(companyLearners.getUser());
			signoffLearner.setTargetClass(CompanyLearnersChange.class.getName());
			signoffLearner.setTargetKey(companyLearnersChange.getId());
			signoffLearner.setSignoffByEnum(SignoffByEnum.Learner);
			dataEntities.add(signoffLearner);
			
			dao.createBatch(dataEntities);
			
			TasksService.instance().findFirstInProcessAndCreateTask(u, companyLearnersChange.getId(), companyLearnersChange.getClass().getName(), ConfigDocProcessEnum.ELearnerChange, false);
		}else {
			TasksService.instance().findFirstInProcessAndCreateTask(u, companyLearnersChange.getId(), companyLearnersChange.getClass().getName(), ConfigDocProcessEnum.LearnerChange, false);
			if (companyLearnersChange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeOfProgramQalification) {
				sendLPMAD005Email(companyLearnersChange, u);
			} else if (companyLearnersChange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeOfProgramTrade) {
				sendLPMAD010Email(companyLearnersChange, u);
			} else if (companyLearnersChange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeCommencementDate) {
				sendLPMAD001Email(companyLearnersChange, u);
			} else if (companyLearnersChange.getLearnerChangeTypeEnum() == LearnerChangeTypeEnum.ChangeNonCreditBearingTitle) {
				sendLPMAD005NonCreditBearingEmail(companyLearnersChange, u);
			}
		}
	}

	public List<SkillsProgram> addSKillsProgrammes(Qualification qual) throws Exception {
		SkillsProgramService unitStandardsService = new SkillsProgramService();
		List<Qualification> qualificationList = new ArrayList<>();
		qualificationList.add(qual);
		List<SkillsProgram> l = unitStandardsService.findByQualificationList(qualificationList);
		return l;
	}

	public void requestVerification(TrainingProviderVerfication trainingProviderVerfication, Users u) throws Exception {
		CompanyLearners companyLearners = trainingProviderVerfication.getCompanyLearners();
		companyLearners.setLearnerStatus(LearnerStatusEnum.PendingVerificationOfAssesmentApproval);
		update(companyLearners);

		trainingProviderVerfication.setStatus(ApprovalEnum.NA);
		trainingProviderVerfication.setCreateUser(u);
		this.dao.create(trainingProviderVerfication);
		
		SummativeAssessmentReport summativeAssessmentReport = new SummativeAssessmentReport();
		summativeAssessmentReport.setTrainingProviderVerfication(trainingProviderVerfication);
		summativeAssessmentReport.setCompanyLearners(trainingProviderVerfication.getCompanyLearners());
		summativeAssessmentReport.setInterventionType(trainingProviderVerfication.getCompanyLearners().getInterventionType());
		summativeAssessmentReport.setSkillsProgram(trainingProviderVerfication.getCompanyLearners().getSkillsProgram());
		summativeAssessmentReport.setSkillsSet(trainingProviderVerfication.getCompanyLearners().getSkillsSet());
		summativeAssessmentReport.setUnitStandard(trainingProviderVerfication.getCompanyLearners().getUnitStandard());
		summativeAssessmentReport.setQualification(trainingProviderVerfication.getCompanyLearners().getQualification());
		List<UnitStandards> unitStandards = unitStandardsService.resolveCompanyLearners(trainingProviderVerfication.getCompanyLearners());
		if(unitStandards != null && unitStandards.size() > 0) {
			summativeAssessmentReportService.applyInterventionData(summativeAssessmentReport);
			summativeAssessmentReportService.create(summativeAssessmentReport, unitStandards);
		}
		
		//TasksService.instance().findFirstInProcessAndCreateTask(u, trainingProviderVerfication.getId(), trainingProviderVerfication.getClass().getName(), ConfigDocProcessEnum.ProviderVerification, false);
	}

	public void requestVerificationLearner(TrainingProviderVerfication trainingProviderVerfication, Users u) throws Exception {
	
		CompanyLearners companyLearners = trainingProviderVerfication.getCompanyLearners();
		if(companyLearners == null){
			throw new Exception("There is an error with the learner application!!! Please contact your administrator");
		}
		List<SummativeAssessmentReportUnitStandards> summativeAssessmentReportUnitStandards = summativeAssessmentReportService.findByCompanylearners(companyLearners);
		if(summativeAssessmentReportUnitStandards == null) {
			throw new Exception("The unit standarts are not linked to the qualification!!! Please contact your administrator");
		}
		if(summativeAssessmentReportUnitStandards.size() == 0) {
			throw new Exception("The unit standarts are not linked to the qualification!!! Please contact your administrator");
		}
		companyLearners.setLearnerStatus(LearnerStatusEnum.PendingVerificationOfAssesmentApproval);
		trainingProviderVerfication.setStatus(ApprovalEnum.NA);
		trainingProviderVerfication.setCreateUser(u);

		this.dao.update(companyLearners);
		this.dao.create(trainingProviderVerfication);
		
		SummativeAssessmentReport summativeAssessmentReport = new SummativeAssessmentReport();
		summativeAssessmentReport.setTrainingProviderVerfication(trainingProviderVerfication);
		summativeAssessmentReport.setCompanyLearners(trainingProviderVerfication.getCompanyLearners());
		summativeAssessmentReport.setInterventionType(trainingProviderVerfication.getCompanyLearners().getInterventionType());
		summativeAssessmentReport.setSkillsProgram(trainingProviderVerfication.getCompanyLearners().getSkillsProgram());
		summativeAssessmentReport.setSkillsSet(trainingProviderVerfication.getCompanyLearners().getSkillsSet());
		summativeAssessmentReport.setUnitStandard(trainingProviderVerfication.getCompanyLearners().getUnitStandard());
		summativeAssessmentReport.setQualification(trainingProviderVerfication.getCompanyLearners().getQualification());
		
		
		//List<UnitStandards> unitStandards = unitStandardsService.resolveCompanyLearners(trainingProviderVerfication.getCompanyLearners());
		
		/*if(unitStandards != null && unitStandards.size() > 0) {
			summativeAssessmentReportService.applyInterventionData(summativeAssessmentReport);
			dao.create(summativeAssessmentReport);
			summativeAssessmentReportService.create(summativeAssessmentReport, unitStandards);
		}/*else {
			throw new Exception("No UnitsStandard for qualification");
		}*/
		
		if(summativeAssessmentReportUnitStandards != null && summativeAssessmentReportUnitStandards.size() > 0) {
			summativeAssessmentReportService.applyInterventionData(summativeAssessmentReport);
			dao.create(summativeAssessmentReport);
		
			summativeAssessmentReportService.createSummativeAssessmentReportUnitStandards(summativeAssessmentReport, summativeAssessmentReportUnitStandards);
		}
		
		//TasksService.instance().findFirstInProcessAndCreateTask(u, trainingProviderVerfication.getId(), trainingProviderVerfication.getClass().getName(), ConfigDocProcessEnum.ProviderVerification, false);
	}
	
	public void requestDocumentsChange(CompanyLearners companyLearners, Users u,List<DateChangeReasons> changeReasonsList, ConfigDocProcessEnum config) throws Exception {		
		ArrayList<Users> users = new ArrayList<>();
		if(companyLearners.getCreateUser() != null && companyLearners.getCreateUser().getActive()) {
			users.add(companyLearners.getCreateUser());
		}else {
			throw new Exception("");
		}
		companyLearners.setDocumentChangeStatus(ApprovalEnum.PendingApproval);
		update(companyLearners);
		
		if (changeReasonsList != null && changeReasonsList.size() != 0 && config != null) {
			for (DateChangeReasons reason : changeReasonsList) {
				DateChangeMultipleSelection dateChangeMultipleSelection = new DateChangeMultipleSelection(companyLearners.getId(), companyLearners.getClass().getName(), reason, u, config);
				dao.create(dateChangeMultipleSelection);
			}
		}
		
		String description = "";		
		TasksService.instance().findFirstInProcessAndCreateTask(description, u, companyLearners.getId(), companyLearners.getClass().getName(), true, ConfigDocProcessEnum.LearnerRegistrationDocuments, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, u.getFirstName()), users);
	}
	
	public void validiationOnTradeTestRequest(CompanyLearnersTradeTest companyLearnersTradeTest) throws Exception{
		// if designated trade level
		if (Boolean.TRUE.equals(companyLearnersTradeTest.getCbmtQualification())) {
			if (companyLearnersTradeTest.getDesignatedTradeLevel() == null) {
				throw new Exception("Select Level Before Proceeding");
			}
		}
		// check if learner readiness date before today or equals today
		if (companyLearnersTradeTest.getLearnerReadinessDate() == null) {
			throw new Exception("Unable to locate learner's readiness date, contact support!");
		}else {
			if (companyLearnersTradeTest.getLearnerReadinessDate().after(GenericUtility.getStartOfDay(getSynchronizedDate()))) {
				throw new Exception("Unable to proceed, please wait till date learner is ready to attempt test.");
			}
		}
		
	}

	public void requestTradeTestApplication(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		CompanyLearners companyLearners = companyLearnersTradeTest.getCompanyLearners();
		companyLearners.setStatus(ApprovalEnum.PendingApproval);
		companyLearners.setLearnerStatus(LearnerStatusEnum.PendingTradeTest);
		companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
		companyLearnersTradeTest.setTradeTestProgress(TradeTestProgressEnum.WithInitiator);

		// add logic for 
		if (companyLearnersTradeTest.getAttemptNumber() == null || companyLearnersTradeTest.getAttemptNumber().equals(0)) {
			companyLearnersTradeTest.setAttemptNumber(1);
		}
		this.dao.update(companyLearners);
		this.dao.create(companyLearnersTradeTest);
		
		// create task
		String description = "";
		ArrayList<Users> users = new ArrayList<>();
		users.add(u);
		TasksService.instance().findFirstInProcessAndCreateTask(description, u, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), true, ConfigDocProcessEnum.TradeTestApplication, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, u.getFirstName()), users);
		
		// send notifications
		sendTradeTestAppForms(companyLearners, companyLearnersTradeTest, u);
	}

	private void sendTradeTestAppForms(CompanyLearners companyLearners, CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		Date commencementDate = GenericUtility.getStartOfDay(companyLearners.getCommencementDate());
		Date weeksFromCommencementDate = GenericUtility.addWeeksToDate(commencementDate, 80);
		// business rule: send 007A if only past 80 weeks
		if (weeksFromCommencementDate.after(GenericUtility.getStartOfDay(getSynchronizedDate()))) {
			/** ATRAMI Trade Test Application Form */
			// sendLPMFM007A(companyLearnersTradeTest, u);
		} else {
			/** Trade Test Application for Contractual Learner under SDA */
			// sendLPMFM007(companyLearnersTradeTest, u);
		}
		sendLPMFM007(companyLearnersTradeTest, u);

	}

	public void requestARPLTradeTestApplication(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		CompanyLearners companyLearners = companyLearnersTradeTest.getCompanyLearners();
		companyLearners.setStatus(ApprovalEnum.PendingApproval);
		companyLearners.setLearnerStatus(LearnerStatusEnum.PendingTradeTest);
		companyLearnersTradeTest.setStatus(ApprovalEnum.PendingApproval);
		if (companyLearnersTradeTest.getAttemptNumber() != null) {
			Integer num = companyLearnersTradeTest.getAttemptNumber();
			if (num >= 3) {
				throw new Exception("You have reached maximum number of attemts");
			}
			companyLearnersTradeTest.setAttemptNumber(num + 1);
		} else {
			companyLearnersTradeTest.setAttemptNumber(1);
		}
		this.dao.update(companyLearners);
		this.dao.create(companyLearnersTradeTest);
		/** Trade Test Application Forms */
		sendLPMFM008AndLPMFM009(companyLearnersTradeTest, companyLearnersTradeTest.getCompanyLearners().getCreateUser());

		TasksService.instance().findFirstInProcessAndCreateTask(u, companyLearnersTradeTest.getId(), companyLearnersTradeTest.getClass().getName(), ConfigDocProcessEnum.ARPLTradeTestApplication, false);

	}

	public void sendLPMFM018BEmail(CompanyLearnersTransfer companyLearnersTransfer, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		Users learner = usersService.findByKey(companyLearnersTransfer.getCompanyLearners().getUser().getId());
		Company newEmployer = companyService.findByKey(companyLearnersTransfer.getTransferToCompany().getId());
		Users employerContactPerson = companyUsersService.findCompanyContactPerson(newEmployer.getId());
		String applicationReason = companyLearnersTransfer.getTransferReason();
		String learnershipNum = "N/A";
		String leanership = "N/A";
		if (companyLearnersTransfer.getCompanyLearners() != null && companyLearnersTransfer.getCompanyLearners().getInterventionType() != null) {
			if (companyLearnersTransfer.getCompanyLearners().getLearnership() != null) {
				leanership = companyLearnersTransfer.getCompanyLearners().getLearnership().getDescription();
				learnershipNum = companyLearnersTransfer.getCompanyLearners().getLearnership().getCode();
			}
		}
		String skillsDevProviderAccreditationNum = "";
		if (companyLearnersTransfer.getCompanyLearners().getCompany() != null) {
			if (companyLearnersTransfer.getCompanyLearners().getCompany().getAccreditationNumber() != null) {
				skillsDevProviderAccreditationNum = companyLearnersTransfer.getCompanyLearners().getCompany().getAccreditationNumber();
			}
		}
		boolean isMinor = checkRequireGaurdian(learner);
		params.put("learner", learner);
		params.put("new_employer", newEmployer);// New training provider
		params.put("employerContactPerson", employerContactPerson);
		params.put("applicationReason", applicationReason);
		params.put("learnershipNum", learnershipNum);
		params.put("leanership", leanership);
		params.put("skillsDevProviderAccreditationNum", skillsDevProviderAccreditationNum);
		params.put("isMinor", isMinor);

		String toEmail = user.getEmail();
		JasperService.addLogo(params);

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-018-B-Application-to-Transfer-Learner-Between-Training-Providers.jasper", params);

		String subject = "Learner Transfer Application".toUpperCase();

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges Learner Transfer Application for the following learner: " + learner.getFirstName() + " " + learner.getLastName() + " (" + anIDNumber(learner) + ")." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly complete the enclosed form and submit on the NSDMS." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours in Skills Development," + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA Team " + "</p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Transfer_Application_Form.pdf";
		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	public void sendAppointmentNotification(Users user) throws Exception {

		String subject = "Learner registration Appointment Approval".toUpperCase();

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"

				+ "Your learner registration appointment has been approved " + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}

	public void sendLPMFM005Email(CompanyLearnersTransfer companyLearnersTransfer) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
		OfoCodesService ofoCodesService = new OfoCodesService();
		OfoCodes ofoCodes = new OfoCodes();
		if (companyLearnersTransfer.getCompanyLearners().getOfoCodes() != null) {
			ofoCodes = ofoCodesService.findByKey(companyLearnersTransfer.getCompanyLearners().getOfoCodes().getId());
		}

		Users learner = usersService.findByKey(companyLearnersTransfer.getCompanyLearners().getUser().getId());
		Company newEmployer = companyService.findByKey(companyLearnersTransfer.getTransferToCompany().getId());
		Users employerContactPerson = companyUsersService.findCompanyContactPerson(newEmployer.getId());
		Company skillsDevProvider = companyService.findByKey(companyLearnersTransfer.getCompanyLearners().getCompany().getId());
		Users skillsDevProviderContactPerson = companyUsersService.findCompanyContactPerson(skillsDevProvider.getId());
		String applicationReason = companyLearnersTransfer.getTransferReason();
		String contractAgreementNum = "N/A";
		if (companyLearnersTransfer.getCompanyLearners().getRegistrationNumber() != null) {
			contractAgreementNum = companyLearnersTransfer.getCompanyLearners().getRegistrationNumber();
		}
		String skillsDevProviderAccreditationNum = trainingProviderApplicationService.findCompanyAccreditation(skillsDevProvider);
		boolean isMinor = checkRequireGaurdian(learner);
		// Setting N/As if Contact Person is not available
		if (employerContactPerson == null) {
			employerContactPerson = setNotApplicable();
		}
		if (skillsDevProviderContactPerson == null) {
			skillsDevProviderContactPerson = setNotApplicable();
		}

		params.put("learner", learner);
		params.put("new_employer", newEmployer);
		params.put("employerContactPerson", employerContactPerson);
		params.put("skillsDevProvider", skillsDevProvider);
		params.put("skillsDevProviderContactPerson", skillsDevProviderContactPerson);
		params.put("applicationReason", applicationReason);
		params.put("contractAgreementNum", contractAgreementNum);
		params.put("ofo_codes", ofoCodes);
		params.put("skillsDevProviderAccreditationNum", skillsDevProviderAccreditationNum);
		params.put("isMinor", isMinor);

		String toEmail = learner.getEmail();

		JasperService.addLogo(params);

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-005-ApplicationToTransferALearner.jasper", params);

		String subject = "Learner Transfer Application".toUpperCase();

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear  " + companyLearnersTransfer.getCreateUser().getFirstName() + " " + companyLearnersTransfer.getCreateUser().getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the request for the transfer of learner "+  learner.getFirstName() + " " + learner.getLastName() +"("+companyLearnersTransfer.getCompanyLearners().getRegistrationNumber()+ "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly complete the enclosed form and addendum to upload on the NSDMS" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours in Skills Development," + "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Administration" + "</p>";
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Transfer_Application_Form.pdf";
		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	public void sendLPMFM011Email(CompanyLearnersLostTime companyLearnersLostTime, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		Users learner = usersService.findByKey(companyLearnersLostTime.getCompanyLearners().getUser().getId());
		Company employer = companyService.findByKey(companyLearnersLostTime.getCompanyLearners().getEmployer().getId());
		Company skillsDevProvider = companyService.findByKey(companyLearnersLostTime.getCompanyLearners().getCompany().getId());
		String registrationNumber = "";
		if(companyLearnersLostTime.getCompanyLearners().getRegistrationNumber() != null) {
			registrationNumber=companyLearnersLostTime.getCompanyLearners().getRegistrationNumber();
		}
		params.put("registration_number", registrationNumber);
		params.put("learner", learner);
		params.put("employer", employer);
		params.put("skillsDevProvider", skillsDevProvider);
		params.put("originalTerminationdate", HAJConstants.sdfDDMMYYYY2.format(companyLearnersLostTime.getCompanyLearners().getCompletionDate()));
		params.put("requestedTerminationDate", HAJConstants.sdfDDMMYYYY2.format(companyLearnersLostTime.getNewCompletionDate()));
		params.put("agreementDate", new Date());// To be confirmed

		String toEmail = user.getEmail();
		JasperService.addLogo(params);

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-011-Request-for-Extension-of-Termination-Date-of-Learnership.jasper", params);

		String subject = "ACKNOWLEDGEMENT OF REQUEST TO EXTEND LEARNER AGREEMENT DURATION";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the request for extension for the following learner: " + learner.getFirstName() + " " + learner.getLastName() + " (" + anIDNumber(learner) + ") for the following reason:</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> <ul><li> " + companyLearnersLostTime.getLostTimeReason().getFriendlyName() + "</li></ul></p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly complete the enclosed form and submit on the NSDMS. " + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> For any assistance, please contact your Regional Office or merSETA Head Office. </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Lost_Time_Application_Form.pdf";
		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	public void sendLPMAD001Email(CompanyLearnersLostTime companyLearnersLostTime, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		Users learner = usersService.findByKey(companyLearnersLostTime.getCompanyLearners().getUser().getId());
		Company skillsDevProvider = companyService.findByKey(companyLearnersLostTime.getCompanyLearners().getCompany().getId());

		Date commencementDate = companyLearnersLostTime.getLostTimeStartDate();
		Date endDate = companyLearnersLostTime.getNewCompletionDate();
		
		if(companyLearnersLostTime.getCompanyLearners().getInterventionType().getInterventionTypeEnum() != null && 
				(companyLearnersLostTime.getCompanyLearners().getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership || 
				companyLearnersLostTime.getCompanyLearners().getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship)) {
			
			Date date1=  HAJConstants.sdfDDMMYYYY2.parse("01/03/2019");
			
			if (companyLearnersLostTime.getCompanyLearners().getRegisteredDate() != null) {
				if(companyLearnersLostTime.getCompanyLearners().getRegisteredDate().before(date1)) {
					commencementDate = companyLearnersLostTime.getCompanyLearners().getRegisteredDate();
				}else {
					commencementDate = companyLearnersLostTime.getCompanyLearners().getCommencementDate();
				}
			}
		}else if(SKILLS_PROGRAM_LIST.contains(companyLearnersLostTime.getCompanyLearners().getInterventionType().getId())
				|| SKILLS_SET_LIST.contains(companyLearnersLostTime.getCompanyLearners().getInterventionType().getId())
				|| companyLearnersLostTime.getCompanyLearners().getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE){
			if (companyLearnersLostTime.getCompanyLearners().getRegisteredDate() != null) {
				commencementDate = companyLearnersLostTime.getCompanyLearners().getCommencementDate();
			}
		}

		params.put("commencement_date", HAJConstants.sdfDDMMYYYY2.format(commencementDate));
		params.put("end_date", HAJConstants.sdfDDMMYYYY2.format(endDate));
		params.put("learner_id", learner.getId());
		params.put("company_id", skillsDevProvider.getId());

		String toEmail = user.getEmail();
		JasperService.addLogo(params);

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-AD-001-Addendum-LearnershipAgreement.jasper", params);
		String subject = "Lost Time Application (Addendum LPM-AD-001)".toUpperCase();
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA has approved the request for extension for the following learner: " + learner.getFirstName() + " " + learner.getLastName() + " (" + anIDNumber(learner) + ") for the following reason:</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<ul><li>" + companyLearnersLostTime.getLostTimeReason().getFriendlyName() + "</li></ul></p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly complete the enclosed Addendum that must be signed by the relevant parties and submitted on the NSDMS." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Lost_Time_Application_Form.pdf";
		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	public void sendLPMTP013Email(CompanyLearnersLostTime companyLearnersLostTime, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		Users learner = usersService.findByKey(companyLearnersLostTime.getCompanyLearners().getUser().getId());
		String contractNum = "N/A";
		if (companyLearnersLostTime.getCompanyLearners().getRegistrationNumber() != null) {
			contractNum = companyLearnersLostTime.getCompanyLearners().getRegistrationNumber();
		}
		int daysExtended = companyLearnersLostTime.getDaysExtended();
		String newTerminationDate = HAJConstants.sdfDDMMYYYY2.format(companyLearnersLostTime.getNewCompletionDate());
		String expiryDateForLeve = HAJConstants.sdfDDMMYYYY2.format(companyLearnersLostTime.getNewCompletionDate());

		params.put("company_id", companyLearnersLostTime.getCompanyLearners().getCompany().getId());
		params.put("user_id", companyLearnersLostTime.getCompanyLearners().getUser().getId());
		params.put("extension_reason", companyLearnersLostTime.getLostTimeReason().getFriendlyName());
		params.put("contract_num", contractNum);
		params.put("daysExtended", daysExtended);
		params.put("newTerminationDate", newTerminationDate);
		params.put("expiryDateForLeve", expiryDateForLeve);
		boolean extensionOfAgreement = false;
		if (companyLearnersLostTime.getLostTimeReason() == LostTimeReason.ExtensionOfAgreement) {
			extensionOfAgreement = true;
		}
		ArrayList<LostTimeReasonBean> reasonList = new ArrayList<>();
		for (LostTimeReason val : LostTimeReason.values()) {
			boolean selected = false;
			if (companyLearnersLostTime.getLostTimeReason().getFriendlyName().equalsIgnoreCase(val.getFriendlyName())) {
				selected = true;
			}
			LostTimeReasonBean reason = new LostTimeReasonBean(val.getFriendlyName(), selected);
			reasonList.add(reason);
		}

		params.put("extensionOfAgreement", extensionOfAgreement);

		params.put("lostTimeReasonBeanDataSource", new JRBeanCollectionDataSource(reasonList));
		String toEmail = user.getEmail();

		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-TP-013-Extension-of-Contract-Template.jasper", params);
		String subject = "Lost Time Application".toUpperCase();
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"

				+ "Kindly find the approved extension request notification for " + learner.getFirstName() + " " + learner.getLastName() + " (" + anIDNumber(learner) + ") for your records.</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Administration" + "</p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Lost_Time_Application_Form.pdf";
		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	public String anIDNumber(Users user) {
		if (user.getRsaIDNumber() != null && user.getRsaIDNumber() != "" && !user.getRsaIDNumber().isEmpty()) {
			return user.getRsaIDNumber();
		} else if (user.getPassportNumber() != null && user.getPassportNumber() != "" && !user.getPassportNumber().isEmpty()) {
			return user.getPassportNumber();
		} else {
			return "N/A";
		}
	}

	/**
	 * Mutual-sided termination Send termination report
	 * 
	 * @throws Exception
	 */

	public void sendLPMFM016Email(CompanyLearnersTermination companyLearnersTermination, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		String idNum = "";
		Users learner = usersService.findByKey(companyLearnersTermination.getCompanyLearners().getUser().getId());
		if (learner.getRsaIDNumber() != null && learner.getRsaIDNumber().length() > 0) {
			idNum = learner.getRsaIDNumber();
		} else {
			idNum = learner.getPassportNumber();
		}
		String agreementNumber = "N/A";
		if (companyLearnersTermination.getCompanyLearners() != null && companyLearnersTermination.getCompanyLearners().getRegistrationNumber() != null) {
			agreementNumber = companyLearnersTermination.getCompanyLearners().getRegistrationNumber();
		}
		Company provider = companyService.findByKey(companyLearnersTermination.getCompanyLearners().getCompany().getId());
		Company employer = companyService.findByKey(companyLearnersTermination.getCompanyLearners().getEmployer().getId());

		Users employerContactPerson = companyUsersService.findCompanyContactPerson(employer.getId());
		Users providerContactPerson = companyUsersService.findCompanyContactPerson(provider.getId());

		params.put("employer", employer);
		params.put("employerContactPerson", employerContactPerson);
		params.put("learner", learner);
		params.put("provider", provider);
		params.put("providerContactPerson", providerContactPerson);
		params.put("companyLearnersTermination", companyLearnersTermination);
		params.put("agreement_number", agreementNumber);

		String toEmail = u.getEmail();

		JasperService.addLogo(params);

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-016-Termination-of-Learnership-Agreement-Mutual.jasper", params);

		String subject = "PROVIDER MUTUAL AGREEMENT TERMINATION APPLICATION";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the request for termination for " + "the following learner: " + learner.getFirstName() + " " + learner.getLastName() + "" + " " + "(" + idNum + ")." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly complete the enclosed form and submit " + "together with the required supporting documentation on the NSDMS." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Your sincerely," + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Administration" + "</p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Termination_Letter.pdf";
		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	/**
	 * One-sided termination Send termination report
	 * 
	 * @throws Exception
	 */
	public void sendLPMFM017Email(CompanyLearnersTermination companyLearnersTermination, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		String idNum = "";
		Users learner = usersService.findByKey(companyLearnersTermination.getCompanyLearners().getUser().getId());
		if (learner.getRsaIDNumber() != null && learner.getRsaIDNumber().length() > 0) {
			idNum = learner.getRsaIDNumber();
		} else {
			idNum = learner.getPassportNumber();
		}
		;
		Company provider = companyService.findByKey(companyLearnersTermination.getCompanyLearners().getCompany().getId());
		Company employer = companyService.findByKey(companyLearnersTermination.getCompanyLearners().getEmployer().getId());

		Users employerContactPerson = companyUsersService.findCompanyContactPerson(employer.getId());
		Users providerContactPerson = companyUsersService.findCompanyContactPerson(provider.getId());
		String agreementNumber = "N/A";
		if (companyLearnersTermination.getCompanyLearners() != null && companyLearnersTermination.getCompanyLearners().getRegistrationNumber() != null) {
			agreementNumber = companyLearnersTermination.getCompanyLearners().getRegistrationNumber();
		}

		params.put("employer", employer);
		params.put("employerContactPerson", employerContactPerson);
		params.put("learner", learner);
		params.put("provider", provider);
		params.put("providerContactPerson", providerContactPerson);
		params.put("companyLearnersTermination", companyLearnersTermination);
		params.put("agreement_number", agreementNumber);
		params.put("applicant_full_name", u.getFirstName()+" "+u.getLastName());

		String toEmail = u.getEmail();

		JasperService.addLogo(params);

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-017-Termination-of-Learnership-Agreement-One-Sided.jasper", params);

		String subject = "ACKNOWLEDGEMENT OF ONE-SIDED AGREEMENT TERMINATION APPLICATION";
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the request for termination for " + "the following learner: " + learner.getFirstName() + " " + learner.getLastName() + "" + "" + " (" + idNum + ")." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly complete the enclosed form and submit " + "together with the required supporting documentation on the NSDMS." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Your sincerely," + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Client Services" + "</p>";
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Termination_Form.pdf";
		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);
	}

	/**
	 * Send termination report(final approval)
	 * 
	 * @throws Exception
	 */
	public void sendLPMTP003Email(CompanyLearnersTermination companyLearnersTermination) throws Exception {
		List<Users> userList = new ArrayList<>();
		if (companyLearnersTermination.getTerminationTypeEnum() == TerminationTypeEnum.OneSidedTermination) {
			RegionTown rt = RegionTownService.instance().findByTown(companyLearnersTermination.getCompanyLearners().getCompany().getResidentialAddress().getTown());
			if (rt != null && rt.getClo() != null) {
				userList.add(rt.getClo().getUsers());
				userList.add(rt.getCrm().getUsers());
			}
		}
		Users learner = usersService.findByKey(companyLearnersTermination.getCompanyLearners().getUser().getId());
		Users sdp = companyLearnersTermination.getCreateUser();
		userList.add(learner);
		userList.add(sdp);
		//Employer
		if(companyLearnersTermination !=null && companyLearnersTermination.getCompanyLearners() !=null && companyLearnersTermination.getCompanyLearners().getEmployer() !=null)
		{
			CompanyService companyService=new CompanyService();
			Company employer=companyService.findByKey(companyLearnersTermination.getCompanyLearners().getEmployer().getId());
			SDFCompany sdf=SDFCompanyService.instance().findPrimarySDF(employer);
			if(sdf !=null){
				Users empUser=sdf.getSdf();
				userList.add(empUser);
			}
		}
		

		Map<String, Object> params = new HashMap<String, Object>();
		String idNum = "";
		if (learner.getRsaIDNumber() != null && learner.getRsaIDNumber().length() > 0) {
			idNum = learner.getRsaIDNumber();
		} else {
			idNum = learner.getPassportNumber();
		}
		String learnerNumber = "N/A";
		if (companyLearnersTermination.getCompanyLearners().getRegistrationNumber() != null) {
			learnerNumber = companyLearnersTermination.getCompanyLearners().getRegistrationNumber();
		}
		String decisionNumber = "N/A";
		if (companyLearnersTermination.getCompanyLearners().getRegistrationNumber() != null) {
			learnerNumber = companyLearnersTermination.getCompanyLearners().getRegistrationNumber();
		}
		params.put("company_id", companyLearnersTermination.getCompanyLearners().getEmployer().getId());
		params.put("user_id", companyLearnersTermination.getCompanyLearners().getUser().getId());
		params.put("decision_number", decisionNumber);
		params.put("learner_agreement_no", learnerNumber);
		params.put("review_date", HAJConstants.sdf.format(new Date()));
		JasperService.addLogo(params);
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-TP-003-Learning-Programme-Termination-Letter.jasper", params);
		super.removeDuplicatesFromList(userList);
		for (Users user : userList) {
			String toEmail = user.getEmail();
			String subject = "" + companyLearnersTermination.getTerminationTypeEnum().getFriendlyName().toUpperCase() + " Application outcome".toUpperCase();
			String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + " </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the merSETA has approved the request for learner agreement termination (" + learnerNumber + ") " + "for the following learner: " + learner.getFirstName() + " " + learner.getLastName() + " " + "(" + idNum + ")." + "</p>"
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Your sincerely," + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Manager: Quality Assurance" + "</p>";
			String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Termination_Letter.pdf";
			// GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites,
			// fileName, "pdf", null);
			GenericUtility.sendMail(toEmail, subject, mssg, null);
		}

	}

	/**
	 * System termination tasks Expired Learning Programme Agreement
	 * 
	 * @throws Exception
	 */
	public void sendLPMTP029AEmail(CompanyLearnersTermination companyLearnersTermination, String reason, String learnerNumber, String cloEmail, String providerWorkplaceEmail) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		Users learner = usersService.findByKey(companyLearnersTermination.getCompanyLearners().getUser().getId());

		params.put("company_id", companyLearnersTermination.getCompanyLearners().getEmployer().getId());
		params.put("user_id", companyLearnersTermination.getCompanyLearners().getUser().getId());
		params.put("learner_number", learnerNumber);
		params.put("reason", reason);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addLogo(params);

		String toEmail = learner.getEmail();
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-TP-029(A)-ExpiredLearningProgrammeAgreement.jasper", params);

		String subject = "EXPIRED LEARNERSHIP AGREEMENT";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + learner.getFirstName() + " " + learner.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Attached, please find the termination latter" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_expiration_Letter.pdf";

		String[] bcc = new String[] { cloEmail, providerWorkplaceEmail };

		GenericUtility.sendMailWithAttachementBcc(toEmail, subject, mssg, bites, fileName, "pdf", null, bcc);

	}

	/**
	 * System termination tasks Expired Skills Programme
	 * 
	 * @throws Exception
	 */
	public void sendLPMTP029BEmail(CompanyLearnersTermination companyLearnersTermination, String reason, String learnerNumber, String cloEmail, String providerWorkplaceEmail) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		Users learner = usersService.findByKey(companyLearnersTermination.getCompanyLearners().getUser().getId());

		params.put("company_id", companyLearnersTermination.getCompanyLearners().getEmployer().getId());
		params.put("user_id", companyLearnersTermination.getCompanyLearners().getUser().getId());
		params.put("learner_number", learnerNumber);
		params.put("reason", reason);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addLogo(params);

		String toEmail = learner.getEmail();
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-TP-029(B)-ExpiredSkillsProgramme.jasper", params);

		String subject = "EXPIRED SKILLS PROGRAMME";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + learner.getFirstName() + " " + learner.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Attached, please find the termination latter" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_expiration_Letter.pdf";

		String[] bcc = new String[] { cloEmail, providerWorkplaceEmail };

		GenericUtility.sendMailWithAttachementBcc(toEmail, subject, mssg, bites, fileName, "pdf", null, bcc);

	}

	public void sendLPMAD005NonCreditBearingEmail(CompanyLearnersChange companyLearnersChange, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String qualificationChange = "";
		if (companyLearnersChange.getNonCredidBearingDescription() != null) {
			qualificationChange = "<b>Current:</b> " + companyLearnersChange.getCompanyLearners().getNonCredidBearingDescription() + " <br/>" + "<b>Amend To: </b> " + companyLearnersChange.getNonCredidBearingDescription() + " </b>";
		}
		Users learner = usersService.findByKey(companyLearnersChange.getCompanyLearners().getUser().getId());
		String learnerRegNum = "";
		if (companyLearnersChange.getCompanyLearners().getRegistrationNumber() != null) {
			learnerRegNum = companyLearnersChange.getCompanyLearners().getRegistrationNumber();
		}

		if (companyLearnersChange.getCompanyLearners().getRegistrationNumber() != null) {
			learnerRegNum = companyLearnersChange.getCompanyLearners().getRegistrationNumber();
		}

		params.put("company_id", companyLearnersChange.getCompanyLearners().getEmployer().getId());
		params.put("learner_id", companyLearnersChange.getCompanyLearners().getUser().getId());
		params.put("NonCreditBearingTitle", companyLearnersChange.getNonCredidBearingDescription());
		params.put("learner_reg_num", learnerRegNum);
		JasperService.addLogo(params);

		String toEmail = u.getEmail();
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-AD-005-AddendumToAmendNonCreditBearingTitle.jasper", params);

		String subject = "ACKNOWLEDGEMENT OF NON-CREDIT BEARING PROGRAMME AMENDMENT APPLICATION";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the request for change in " + "non-credit bearing programme for the following learner: " + learner.getFirstName() + " " + learner.getLastName() + " (" + anIDNumber(learner) + ")." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + qualificationChange + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly complete the enclosed form and submit on the NSDMS." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration" + "</p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Change_of_Non_Credit_Bearing_Title _Letter.pdf";

		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	/**
	 * Change of Programme Addendum to Amend Qualification
	 * 
	 * @throws Exception
	 */
	public void sendLPMAD005Email(CompanyLearnersChange companyLearnersChange, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String qualificationChange = "";
		if (companyLearnersChange.getCompanyLearners().getQualification() != null && companyLearnersChange.getQualification() != null) {
			qualificationChange = "<b>Current:</b> (" + companyLearnersChange.getCompanyLearners().getQualification().getCode() + ") " + companyLearnersChange.getCompanyLearners().getQualification().getDescription() + " <br/>" + "<b>Amend To: </b> (" + companyLearnersChange.getQualification().getCode() + ") " + companyLearnersChange.getQualification().getDescription();
		}
		Users learner = usersService.findByKey(companyLearnersChange.getCompanyLearners().getUser().getId());
		String learnerRegNum = "";
		if (companyLearnersChange.getCompanyLearners().getRegistrationNumber() != null) {
			learnerRegNum = companyLearnersChange.getCompanyLearners().getRegistrationNumber();
		}

		if (companyLearnersChange.getCompanyLearners().getRegistrationNumber() != null) {
			learnerRegNum = companyLearnersChange.getCompanyLearners().getRegistrationNumber();
		}

		params.put("company_id", companyLearnersChange.getCompanyLearners().getEmployer().getId());
		params.put("learner_id", companyLearnersChange.getCompanyLearners().getUser().getId());
		
		if(companyLearnersChange.getQualification() != null) {
			params.put("qual_id", companyLearnersChange.getQualification().getId());
		}else if(companyLearnersChange.getLearnership() != null) {
			params.put("qual_id", companyLearnersChange.getLearnership().getQualification().getId());
		}

		params.put("learner_reg_num", learnerRegNum);
		JasperService.addLogo(params);

		String toEmail = u.getEmail();
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-AD-005-AddendumToAmendQualification.jasper", params);

		String subject = "ACKNOWLEDGEMENT OF QUALIFICATION AMENDMENT APPLICATION";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the request for change in qualification for the following learner: " + learner.getFirstName() + " " + learner.getLastName() + " (" + anIDNumber(learner) + ")." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + qualificationChange + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly complete the enclosed form and submit together with required supporting documentation on the NSDMS." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration" + "</p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Change_of_Programme _Letter.pdf";

		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	/**
	 * Change of Programme Addendum to Amend a Trade
	 * 
	 * @throws Exception
	 */
	public void sendLPMAD010Email(CompanyLearnersChange companyLearnersChange, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		Users learner = usersService.findByKey(companyLearnersChange.getCompanyLearners().getUser().getId());

		String learnerRegNum = "";
		String trade = "";
		if (companyLearnersChange != null && companyLearnersChange.getQualification() != null && companyLearnersChange.getQualification().getDescription() != null) {
			trade = companyLearnersChange.getQualification().getDescription();
		}

		if (companyLearnersChange.getCompanyLearners().getRegistrationNumber() != null) {
			learnerRegNum = companyLearnersChange.getCompanyLearners().getRegistrationNumber();
		}
		params.put("company_id", companyLearnersChange.getCompanyLearners().getEmployer().getId());
		params.put("learner_id", companyLearnersChange.getCompanyLearners().getUser().getId());
		params.put("registration_number", learnerRegNum);
		params.put("trade", trade);
		JasperService.addLogo(params);

		String toEmail = u.getEmail();
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-AD-010-AddendumToAmendATrade.jasper", params);

		String subject = "ACKNOWLEDGEMENT OF TRADE REGISTRATION AMENDMENT APPLICATION";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the request for change in trade for the following learner: " + learner.getFirstName() + " " + learner.getLastName() + " (" + anIDNumber(learner) + ").</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly complete the enclosed form and submit together with required supporting documentation on the NSDMS." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Your sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Change_of_Programme_Letter.pdf";

		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	/**
	 * Change Commencement Date Addendum-Learnership Agreement Commence Date
	 * 
	 * @throws Exception
	 */
	public void sendLPMAD001Email(CompanyLearnersChange companyLearnersChange, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		Users learner = usersService.findByKey(companyLearnersChange.getCompanyLearners().getUser().getId());
		String commencementDate = HAJConstants.sdf.format(companyLearnersChange.getCommencmentDate());
		String endDate = HAJConstants.sdf.format(companyLearnersChange.getCompletionDate());// To be Confirmed

		if (companyLearnersChange.getCompanyLearners().getRegistrationNumber() != null) {
			companyLearnersChange.getCompanyLearners().getRegistrationNumber();
		}
		
		params.put("registration_number", companyLearnersChange.getCompanyLearners().getRegistrationNumber());
		params.put("company_id", companyLearnersChange.getCompanyLearners().getCompany().getId());
		params.put("learner_id", companyLearnersChange.getCompanyLearners().getUser().getId());
		params.put("commencement_date", commencementDate);
		params.put("end_date", endDate);
		params.put("company_name", companyLearnersChange.getCompanyLearners().getCompany().getCompanyName());
		JasperService.addLogo(params);

		String toEmail = u.getEmail();
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-AD-001-Addendum-LearnershipAgreement.jasper", params);

		String subject = "ACKNOWLEDGEMENT OF AGREEMENT COMMENCEMENT DATE AMENDMENT APPLICATION";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the request for change in commencement " + "date for the following learner: " + learner.getFirstName() + " " + learner.getLastName() + " (" + anIDNumber(learner) + ")." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly complete the enclosed form and submit together with required supporting documentation on the NSDMS." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Your sincerely," + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Administration" + "</p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Change_of_Programme _Letter.pdf";

		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	/**
	 * Trade Test Application for Contractual Learner under SDA
	 * 
	 * @throws Exception
	 */
	public void sendLPMFM007(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		String trade = "";
		if (companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getQualification() != null && companyLearnersTradeTest.getCompanyLearners().getQualification().getDesignatedTrade() != null) {
			trade = companyLearnersTradeTest.getCompanyLearners().getQualification().getDesignatedTrade().getDescription();
		}
		String currentOccupation = "N/A";
		String experience = "";
		String etqa = "MERSETA - Manufacturing, Engineering and Related Services Education and Training Authority";
		if (companyLearnersTradeTest.getCompanyLearners().getEmployer().getEtqa() != null) {
			etqa = companyLearnersTradeTest.getCompanyLearners().getEmployer().getEtqa().getDescription();
		}
		companyLearnersTradeTest.getCompanyLearners().getRegistrationNumber();

		if (companyLearnersTradeTest.getCompanyLearners().getUser().getEmploymentStatus() != null) {
			currentOccupation = companyLearnersTradeTest.getCompanyLearners().getUser().getEmploymentStatus().getFriendlyName();
		}

		List<WorkplaceBean> workplaces = new ArrayList<>();
		if (companyLearnersTradeTest != null && companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getCompany() != null) {
			Company company = companyService.findByKey(companyLearnersTradeTest.getCompanyLearners().getCompany().getId());
			WorkplaceBean workplaceBean = new WorkplaceBean();
			workplaceBean.setName(company.getCompanyName());
			workplaceBean.setFrom(company.getCompanyName());
			workplaceBean.setTo(company.getCompanyName());
			workplaceBean.setDetailes(company.getCompanyName());

			workplaces.add(workplaceBean);
		}

		List<WorkplaceBean> providers = new ArrayList<>();
		if (companyLearnersTradeTest != null && companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getEmployer() != null) {
			Company company1 = companyService.findByKey(companyLearnersTradeTest.getCompanyLearners().getEmployer().getId());
			WorkplaceBean workplaceBean1 = new WorkplaceBean();
			workplaceBean1.setName(company1.getCompanyName());
			workplaceBean1.setFrom(company1.getCompanyName());
			workplaceBean1.setTo(company1.getCompanyName());
			workplaceBean1.setDetailes(company1.getCompanyName());

			providers.add(workplaceBean1);
		}

		OfoCodesService ofoCodesService = new OfoCodesService();
		OfoCodes ofoCodes = new OfoCodes();
		if (companyLearnersTradeTest.getCompanyLearners().getOfoCodes() != null) {
			ofoCodes = ofoCodesService.findByKey(companyLearnersTradeTest.getCompanyLearners().getOfoCodes().getId());
		}
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "education_logo");

		Users learner = usersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getUser().getId());
		Company employer = companyService.findByKey(companyLearnersTradeTest.getCompanyLearners().getEmployer().getId());
		Users employerContactPerson = companyUsersService.findCompanyContactPerson(employer.getId());
		if (employerContactPerson == null || employerContactPerson.getId() == null) {
			/* The inclusion of a contact person detail and email address is compulsory **/
			throw new Exception("No employer contact person found.");
		}

		boolean attemptedTradeTest = false;
		if (companyLearnersTradeTest.getAttemptedTradeTest().getFriendlyName().equalsIgnoreCase("Yes")) {
			attemptedTradeTest = true;
		}

		boolean flc = false;
		if (companyLearnersTradeTest.getFlc() != null && companyLearnersTradeTest.getFlc().getFriendlyName().equalsIgnoreCase("Yes")) {
			flc = true;
		}

		learner.getResidentialAddress().getMunicipality().getProvince().getProvinceDesc();

		params.put("user", u);
		params.put("learner", learner);
		params.put("employer", employer);
		params.put("employer_contact_person", employerContactPerson);
		params.put("company_learners_trade_test", companyLearnersTradeTest);
		params.put("attempted_trade_test", attemptedTradeTest);
		params.put("flc", flc);
		params.put("ofo_codes", ofoCodes);
		params.put("currentOccupation", currentOccupation);
		params.put("experience", experience);
		params.put("workplacesReasonDataSource", new JRBeanCollectionDataSource(workplaces));
		params.put("providersDataSource", new JRBeanCollectionDataSource(providers));
		params.put("etqa", etqa);
		params.put("trade", trade);

		String toEmail = u.getEmail();

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-007-TradeTest ApplicationForContractualLearnerUnderSDA.jasper", params);
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		AttachmentBean beanAttachment = new AttachmentBean();
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Trade_Test_Application_Form.pdf";
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename(fileName);
		attachmentBeans.add(beanAttachment);

		/*
		 * beanAttachment = new AttachmentBean(); fileName = learner.getFirstName() +
		 * "_" + learner.getLastName() + "_Declaration_Form.pdf";
		 * beanAttachment.setExt("pdf");
		 * beanAttachment.setFile(CompanyLearnersTradeTestService.instance().
		 * getDeclarationFormBytes(companyLearnersTradeTest));
		 * beanAttachment.setFilename(fileName); attachmentBeans.add(beanAttachment);
		 */

		String subject = "TRADE TEST APPLICATION FOR CONTRACTUAL LEARNER UNDER SDA";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Attached, please find Declaration Form and  Trade Test Application for Contractual Learner under SDA form" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		// GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites,
		// fileName, "pdf", null);
		GenericUtility.sendMailWithAttachementTempWithLog(toEmail, subject, mssg, attachmentBeans, null);

	}

	/**
	 * ATRAMI Trade Test Application Form
	 * 
	 * @throws Exception
	 */
	public void sendLPMFM007A(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String trade = "";
		if (companyLearnersTradeTest.getCompanyLearners() != null && companyLearnersTradeTest.getCompanyLearners().getQualification() != null && companyLearnersTradeTest.getCompanyLearners().getQualification().getDesignatedTrade() != null) {
			trade = companyLearnersTradeTest.getCompanyLearners().getQualification().getDesignatedTrade().getDescription();
		}
		OfoCodesService ofoCodesService = new OfoCodesService();
		OfoCodes ofoCodes = new OfoCodes();
		if (companyLearnersTradeTest.getCompanyLearners().getOfoCodes() != null) {
			ofoCodes = ofoCodesService.findByKey(companyLearnersTradeTest.getCompanyLearners().getOfoCodes().getId());
		}

		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");

		Users learner = usersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getUser().getId());
		Company employer = companyService.findByKey(companyLearnersTradeTest.getCompanyLearners().getEmployer().getId());
		Users employerContactPerson = companyUsersService.findCompanyContactPerson(employer.getId());
		if (employerContactPerson == null || employerContactPerson.getId() == null) {
			/* The inclusion of a contact person detail and email address is compulsory **/
			throw new Exception("No employer contact person found.");
		}

		learner.getResidentialAddress().getMunicipality().getProvince().getProvinceDesc();
		params.put("learner", learner);
		params.put("employer", employer);
		params.put("employer_contact_person", employerContactPerson);
		params.put("ofo_codes", ofoCodes);
		params.put("companyLearnerTradeTest", companyLearnersTradeTest);
		params.put("trade", trade);
		String toEmail = u.getEmail();

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-007(A)-ATRAMITradeTestApplicationForm.jasper", params);
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		AttachmentBean beanAttachment = new AttachmentBean();
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_ATRAMI_Form.pdf";
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename(fileName);
		attachmentBeans.add(beanAttachment);

		/*
		 * beanAttachment = new AttachmentBean(); fileName = learner.getFirstName() +
		 * "_" + learner.getLastName() + "_Declaration_Form.pdf";
		 * beanAttachment.setExt("pdf");
		 * beanAttachment.setFile(CompanyLearnersTradeTestService.instance().
		 * getDeclarationFormBytes(companyLearnersTradeTest));
		 * beanAttachment.setFilename(fileName); attachmentBeans.add(beanAttachment);
		 */

		String subject = "ATRAMI TRADE TEST APPLICATION ";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Attached, please find ATRAMI Trade Test Application Forms " + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		// GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites,
		// fileName, "pdf", null);

		GenericUtility.sendMailWithAttachementTempWithLog(toEmail, subject, mssg, attachmentBeans, null);
		;

	}

	/**
	 * Trade/ Level Test application: for Artisan Recognition of Prior Learning
	 * 
	 * @throws Exception
	 */
	public void sendLPMFM008AndLPMFM009(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		OfoCodesService ofoCodesService = new OfoCodesService();
		OfoCodes ofoCodes = new OfoCodes();
		if (companyLearnersTradeTest.getCompanyLearners().getOfoCodes() != null) {
			ofoCodes = ofoCodesService.findByKey(companyLearnersTradeTest.getCompanyLearners().getOfoCodes().getId());
		}
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "education_logo");
		Users learner = usersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getUser().getId());
		Company employer = companyService.findByKey(companyLearnersTradeTest.getCompanyLearners().getEmployer().getId());
		Users employerContactPerson = companyUsersService.findCompanyContactPerson(employer.getId());
		boolean attemptedTradeTest = false;
		if (companyLearnersTradeTest.getAttemptedTradeTest().getFriendlyName().equalsIgnoreCase("Yes")) {
			attemptedTradeTest = true;
		}
		boolean flc = false;
		if (companyLearnersTradeTest.getFlc().getFriendlyName().equalsIgnoreCase("Yes")) {
			flc = true;
		}
		/* Trade/ Level Test application: for Artisan Recognition of Prior Learning */
		learner.getResidentialAddress().getMunicipality().getProvince().getProvinceDesc();
		params.put("learner", learner);
		params.put("employer", employer);
		params.put("employer_contact_person", employerContactPerson);
		params.put("company_learners_trade_test", companyLearnersTradeTest);
		params.put("attempted_trade_test", attemptedTradeTest);
		params.put("flc", flc);
		params.put("ofo_codes", ofoCodes);
		u.getEmail();
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-009-TradeTestApplicationForm.jasper", params);
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Trade_Test_Application_Form.pdf";
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename(fileName);
		attachmentBeans.add(beanAttachment);
		/* AMIC LEARNER/CANDIDATE APPLICATION FORM FOR IMMMEDIATE RECOGNITION TEST */
		params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		beanAttachment = new AttachmentBean();
		params.put("employer_contact_person", employerContactPerson);
		params.put("learner", learner);
		params.put("employer", employer);
		bites = JasperService.instance().convertJasperReportToByte("LPM-FM-008-AMICApplicationForImmediateRecognitionTest.jasper", params);
		fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Immediate_Recognition_Test_Form.pdf";
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename(fileName);
		attachmentBeans.add(beanAttachment);

		String subject = "TRADE TEST APPLICATION FORMS";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Attached, please find Trade Test Application forms" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		GenericUtility.sendMailWithAttachementTempWithLog(u.getEmail(), subject, mssg, attachmentBeans, null);

	}

	/**
	 * Trade/ Level Test application: for Artisan Recognition of Prior Learning
	 * 
	 * @throws Exception
	 */
	public void sendLPMFM009(CompanyLearnersTradeTest companyLearnersTradeTest, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		OfoCodesService ofoCodesService = new OfoCodesService();
		OfoCodes ofoCodes = new OfoCodes();
		if (companyLearnersTradeTest.getCompanyLearners().getOfoCodes() != null) {
			ofoCodes = ofoCodesService.findByKey(companyLearnersTradeTest.getCompanyLearners().getOfoCodes().getId());
		}

		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "education_logo");

		Users learner = usersService.findByKey(companyLearnersTradeTest.getCompanyLearners().getUser().getId());
		Company employer = companyService.findByKey(companyLearnersTradeTest.getCompanyLearners().getEmployer().getId());
		Users employerContactPerson = companyUsersService.findCompanyContactPerson(employer.getId());

		boolean attemptedTradeTest = false;
		if (companyLearnersTradeTest.getAttemptedTradeTest().getFriendlyName().equalsIgnoreCase("Yes")) {
			attemptedTradeTest = true;
		}

		boolean flc = false;
		if (companyLearnersTradeTest.getFlc().getFriendlyName().equalsIgnoreCase("Yes")) {
			flc = true;
		}

		learner.getResidentialAddress().getMunicipality().getProvince().getProvinceDesc();
		params.put("learner", learner);
		params.put("employer", employer);
		params.put("employer_contact_person", employerContactPerson);
		params.put("company_learners_trade_test", companyLearnersTradeTest);
		params.put("attempted_trade_test", attemptedTradeTest);
		params.put("flc", flc);
		params.put("ofo_codes", ofoCodes);
		String toEmail = u.getEmail();

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-009-TradeTestApplicationForm.jasper", params);

		String subject = "TRADE TEST APPLICATION FOR CONTRACTUAL LEARNER UNDER SDA";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Attached, please find Trade Test Application for Contractual Learner under SDA form" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Trade_Test_Application_Form.pdf";

		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	/**
	 * CBMT-Declaration-Form-Spray-Painter-Level-3
	 * 
	 * @throws Exception
	 */
	public void sendLPMFM035(CompanyLearners companyLearners, Users u) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		OfoCodesService ofoCodesService = new OfoCodesService();
		OfoCodes ofoCodes = new OfoCodes();
		JasperService.addLogo(params);
		if (companyLearners.getOfoCodes() != null) {
			ofoCodes = ofoCodesService.findByKey(companyLearners.getOfoCodes().getId());
		}
		Users learner = usersService.findByKey(companyLearners.getUser().getId());
		Company employer = companyService.findByKey(companyLearners.getEmployer().getId());
		ArrayList<LPMFM035ModularTrainingBean> moduleBeanlist = new ArrayList<>();

		// **************************To be Selected From
		// DB**************************************
		LPMFM035ModularTrainingBean module = new LPMFM035ModularTrainingBean("PERFORM MIXING, THINNING & APPLICATION OF FINISHING COATS", "SP-01", "Patrick Mthombeni", learner.getFirstName() + " " + learner.getFirstName(), "SP-02", learner.getFirstName() + " " + learner.getFirstName());
		moduleBeanlist.add(module);

		module = new LPMFM035ModularTrainingBean("PERFORM FINAL CLEANING & POLISHING", "SP-02", "Vusi Mthombeni", learner.getFirstName() + " " + learner.getFirstName(), "SP-03", learner.getFirstName() + " " + learner.getFirstName());
		moduleBeanlist.add(module);

		module = new LPMFM035ModularTrainingBean("PERFORM AN ASSESSMENT OF PAINT DEFECTS", "SP-03", "Vusi Mthombeni", learner.getFirstName() + " " + learner.getFirstName(), "SP-04", learner.getFirstName() + " " + learner.getFirstName());
		moduleBeanlist.add(module);
		// **************************************************************************************

		learner.getResidentialAddress().getMunicipality().getProvince().getProvinceDesc();
		params.put("learner", learner);
		params.put("company", employer);
		params.put("contruct_num", "");// To Be Fixed
		params.put("min_time", "22 WEEKS");// To Be fixed
		params.put("max_time", "47 WEEKS");// To Be fixed
		params.put("ModularTrainingBeanDataSource", new JRBeanCollectionDataSource(moduleBeanlist));
		params.put("ofo_codes", ofoCodes);
		String toEmail = u.getEmail();

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-035-CBMT-Declaration-Form-Spray-Painter-Level-3.jasper", params);

		String subject = "CBMT Declaration".toUpperCase();

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + u.getFirstName() + " " + u.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Attached, please find CBMT-Declaration Form " + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_ATRAMI_Form.pdf";

		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	/**
	 * Learner Transferred to a new provider
	 * 
	 * @throws Exception
	 */
	public void sendLPMAD008Email(CompanyLearnersTransfer companyLearnersTransfer, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		Users learner = usersService.findByKey(companyLearnersTransfer.getCompanyLearners().getUser().getId());

		params.put("company_id", companyLearnersTransfer.getTransferToCompany().getId());
		params.put("learners_id", learner.getId());

		String toEmail = user.getEmail();
		JasperService.addLogo(params);

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-AD-008-AddendumToAmendLinkBetweenProviderAndLearner.jasper", params);
		String subject = "learner Transfer to new Provider".toUpperCase();
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Attached, please find addendum to amend link between provider and learner form" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Learner_Transfere_Addendum_Form.pdf";
		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);

	}

	/**
	 * Learner Transferred to a new workplace
	 * 
	 * @throws Exception
	 */
	public void sendLPMAD002Email(CompanyLearnersTransfer companyLearnersTransfer, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		Users learner = usersService.findByKey(companyLearnersTransfer.getCompanyLearners().getUser().getId());

		params.put("company_id", companyLearnersTransfer.getTransferToCompany().getId());
		params.put("learners_id", learner.getId());

		String toEmail = user.getEmail();
		JasperService.addLogo(params);

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-AD-002-Addendum-ToAmendLinkBetweenEmployerAndLearner.jasper", params);
		String subject = "learner Transfer to new workplace".toUpperCase();
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Attached, please find addendum to amend link between employer and learner form" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Learner_Transfere_Addendum_Form.pdf";
		GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);
	}

	public boolean checkRequireGaurdian(Users user) {
		boolean requireGuardian = true;

		if (user.getDateOfBirth() != null && GenericUtility.getAge(user.getDateOfBirth()) > 18) {
			requireGuardian = false;

		}
		return requireGuardian;

	}

	public List<CompanyLearners> sortAndFilter(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company employer, InterventionTypeEnum designatedTradeID) {
		return dao.sortAndFilter(startingAt, pageSize, sortField, sortOrder, filters, employer, designatedTradeID);
	}

	public int count(Map<String, Object> filters, Company employer, InterventionTypeEnum designatedTradeID) {
		return dao.count(filters, employer, designatedTradeID);
	}

	public List<CompanyLearners> sortAndFilter(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company employer, Long designatedTradeID) throws Exception {
		return checkProgress(dao.sortAndFilter(startingAt, pageSize, sortField, sortOrder, filters, employer, designatedTradeID));
	}

	public List<CompanyLearners> sortAndFilterNotDesignatedOrLearnership(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company employer, InterventionTypeEnum ite) throws Exception {
		return checkProgress(dao.sortAndFilterNotDesignatedOrLearnership(startingAt, pageSize, sortField, sortOrder, filters, employer, ite));
	}

	public int countNotDesignatedTradeAndLearnership(Map<String, Object> filters, Company employer, InterventionTypeEnum ite) {
		return dao.countNotDesignatedTradeAndLearnership(filters, employer, ite);
	}

	public List<CompanyLearners> findByDesignatedTrade(Company employer, Long designatedTradeID) throws Exception {
		return checkProgress(dao.findByDesignatedTrade(employer, designatedTradeID));
	}

	private List<CompanyLearners> checkProgress(List<CompanyLearners> cl) throws Exception {
		for (CompanyLearners companyLearners : cl) {
			List<CompanyLearnersProgress> clp = findCompanyLearnersProgress(companyLearners.getId());
			List<ProgressType> progressTypes = clp.stream().map(m -> m.getProgressType()).collect(Collectors.toList());
			if (companyLearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
				List<ProgressType> prog = Arrays.asList(ProgressType.values()).stream().filter(type -> type.getInterventionType() == TradeTypeEnum.LEARNERSHIP).collect(Collectors.toList());
				for (ProgressType pt : prog)
					if (!progressTypes.contains(pt)) clp.add(new CompanyLearnersProgress(companyLearners, pt));
			} else if (companyLearners.getQualification() != null && companyLearners.getQualification().getDesignatedTrade() != null) {
				List<DesignatedTradeLevel> dtl = DesignatedTradeLevelService.instance().findBydesignatedTradeIdNotRecordedSingle(companyLearners.getQualification().getDesignatedTrade(), companyLearners);
				int i = clp.size() + 1;
				for (DesignatedTradeLevel pt : dtl) {
					switch (i) {
						case 1:
							clp.add(new CompanyLearnersProgress(companyLearners, pt, companyLearners.getQualification().getLevelOne()));
							break;
						case 2:
							clp.add(new CompanyLearnersProgress(companyLearners, pt, companyLearners.getQualification().getLevelTwo()));
							break;
						case 3:
							clp.add(new CompanyLearnersProgress(companyLearners, pt, companyLearners.getQualification().getLevelThree()));
							break;
						case 4:
							clp.add(new CompanyLearnersProgress(companyLearners, pt, companyLearners.getQualification().getLevelFour()));
							break;
						default:
							clp.add(new CompanyLearnersProgress(companyLearners, pt));
							break;
					}
				}
			}
			companyLearners.setCompanyLearnersProgresses(clp);
		}

		return cl;
	}

	public List<CompanyLearners> sortAndFilter(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company employer, TradeTypeEnum ite) throws Exception {
		return checkProgress(dao.sortAndFilter(startingAt, pageSize, sortField, sortOrder, filters, employer, ite));
	}

	public List<CompanyLearnersProgress> findCompanyLearnersProgress(Long companyLearnersID) throws Exception {
		return dao.findCompanyLearnersProgress(companyLearnersID);
	}

	public int count(Map<String, Object> filters, Company employer, TradeTypeEnum ite) {
		return dao.count(filters, employer, ite);
	}

	public int count(Map<String, Object> filters, Company employer, Long designatedTradeID) {
		return dao.count(filters, employer, designatedTradeID);
	}

	public void resolveAllData(List<CompanyLearners> cl) throws Exception {
		for (CompanyLearners companyLearners : cl) {
			resolveAllData(companyLearners);
		}
	}

	@SuppressWarnings("unchecked")
	public void resolveAllData(CompanyLearners cl) throws Exception {
		cl.setCompanyLearnersTransfer((List<CompanyLearnersTransfer>) (List<?>) dao.findByCompanyLearner(cl.getId()));
		cl.setCompanyLearnersLostTime((List<CompanyLearnersLostTime>) (List<?>) dao.findByCompanyLearner("CompanyLearnersLostTime", cl.getId()));
		cl.setCompanyLearnersTermination((List<CompanyLearnersTermination>) (List<?>) dao.findByCompanyLearner("CompanyLearnersTermination", cl.getId()));
		cl.setCompanyLearnersChange((List<CompanyLearnersChange>) (List<?>) dao.findByCompanyLearner("CompanyLearnersChange", cl.getId()));
		cl.setCompanyLearnersTradeTest((List<CompanyLearnersTradeTest>) (List<?>) dao.findByCompanyLearner("CompanyLearnersTradeTest", cl.getId()));
		cl.setCompletionLetters((List<CompletionLetter>) (List<?>) dao.findByCompanyLearner("CompletionLetter", cl.getId()));
		additionalData(cl);
		populateDocs(cl);
	}

	private void populateDocs(CompanyLearners cl) throws Exception {
		List<CompanyLearnersTransfer> companyLearnersTransferList = cl.getCompanyLearnersTransfer();
		if (companyLearnersTransferList != null && companyLearnersTransferList.size() > 0) {
			for (CompanyLearnersTransfer companyLearnersTransfer : companyLearnersTransferList) {
				companyLearnersTransfer.setDocs(DocService.instance().searchByTargetKeyAndClass(CompanyLearnersTransfer.class.getName(), companyLearnersTransfer.getId()));
			}
		}

		List<CompanyLearnersLostTime> companyLearnersLostTimeList = cl.getCompanyLearnersLostTime();
		if (companyLearnersLostTimeList != null && companyLearnersLostTimeList.size() > 0) {
			for (CompanyLearnersLostTime companyLearnersLostTime : companyLearnersLostTimeList) {
				companyLearnersLostTime.setDocs(DocService.instance().searchByTargetKeyAndClass(CompanyLearnersLostTime.class.getName(), companyLearnersLostTime.getId()));
			}
		}

		List<CompanyLearnersTermination> companyLearnersTerminationList = cl.getCompanyLearnersTermination();
		if (companyLearnersTerminationList != null && companyLearnersTerminationList.size() > 0) {
			for (CompanyLearnersTermination companyLearnersTermination : companyLearnersTerminationList) {
				companyLearnersTermination.setDocs(DocService.instance().searchByTargetKeyAndClass(CompanyLearnersTermination.class.getName(), companyLearnersTermination.getId()));
			}
		}

		List<CompanyLearnersChange> companyLearnersChangeList = cl.getCompanyLearnersChange();
		if (companyLearnersChangeList != null && companyLearnersChangeList.size() > 0) {
			for (CompanyLearnersChange companyLearnersChange : companyLearnersChangeList) {
				companyLearnersChange.setDocs(DocService.instance().searchByTargetKeyAndClass(CompanyLearnersChange.class.getName(), companyLearnersChange.getId()));
			}
		}

		List<CompanyLearnersTradeTest> companyLearnersTradeTestList = cl.getCompanyLearnersTradeTest();
		if (companyLearnersTradeTestList != null && companyLearnersTradeTestList.size() > 0) {
			for (CompanyLearnersTradeTest companyLearnersTradeTest : companyLearnersTradeTestList) {
				companyLearnersTradeTest.setDocs(DocService.instance().searchByTargetKeyAndClass(CompanyLearnersTradeTest.class.getName(), companyLearnersTradeTest.getId()));
			}
		}

		List<CompletionLetter> completionLetterList = cl.getCompletionLetters();
		if (completionLetterList != null && completionLetterList.size() > 0) {
			for (CompletionLetter completionLetter : completionLetterList) {
				completionLetter.setDocs(DocService.instance().searchByTargetKeyAndClass(CompletionLetter.class.getName(), completionLetter.getId()));
			}
		}
	}

	public void additionalData(CompanyLearners cl) throws Exception {
		if (cl != null && cl.getEmployer() != null && cl.getEmployer().getId() != null) {
			cl.setEmployer(companyService.findByKey(cl.getEmployer().getId()));

			if (cl.getEmployer().getPostalAddress() != null) {
				cl.getEmployer().setPostalAddress(AddressService.instance().findByKey(cl.getEmployer().getPostalAddress().getId()));
				if(cl.getEmployer().getPostalAddress().getSameAddress() == null) {
					cl.getEmployer().getPostalAddress().setSameAddress(false);
				}
			}
			
			List<Users> list = companyUsersService.findCompanyContactPersonList(cl.getEmployer().getId());
			if (list != null && list.size() > 0) {
				cl.getEmployer().setContactPerson(list.get(0));
			}

			if (trainingProviderApplicationService.findByCompany(cl.getEmployer()) != null && trainingProviderApplicationService.findByCompany(cl.getEmployer()).size() > 0) {
				cl.getEmployer().setTrainingProviderApplication(trainingProviderApplicationService.findByCompany(cl.getEmployer()).get(0));
			}
		}
		if (cl != null && cl.getCompany() != null && cl.getCompany().getId() != null) {
			cl.setCompany(companyService.findByKey(cl.getCompany().getId()));

			if (cl.getCompany().getPostalAddress() != null) {
				cl.getCompany().setPostalAddress(AddressService.instance().findByKey(cl.getCompany().getPostalAddress().getId()));
				if(cl.getCompany().getPostalAddress().getSameAddress() == null) {
					cl.getCompany().getPostalAddress().setSameAddress(false);
				}
			}

			List<Users> list = companyUsersService.findCompanyContactPersonList(cl.getCompany().getId());
			if (list != null && list.size() > 0) {
				cl.getCompany().setContactPerson(list.get(0));
			}

			if (trainingProviderApplicationService.findByCompany(cl.getCompany()) != null && trainingProviderApplicationService.findByCompany(cl.getCompany()).size() > 0) {
				cl.getCompany().setTrainingProviderApplication(trainingProviderApplicationService.findByCompany(cl.getCompany()).get(0));
			}
		}
		if (cl.getUser() != null && cl.getUser().getPostalAddress() != null) {
			cl.getUser().setPostalAddress(AddressService.instance().findByKey(cl.getUser().getPostalAddress().getId()));
			if(cl.getUser().getPostalAddress().getSameAddress() == null) {
				cl.getUser().getPostalAddress().setSameAddress(false);
			}
		}
		if (cl.getUser() != null && cl.getUser().getBirthAddress() != null) {
			cl.getUser().setBirthAddress(AddressService.instance().findByKey(cl.getUser().getBirthAddress().getId()));
		}
	}

	public List<Users> findRsaIdOrName(String description) throws Exception {
		return dao.findRsaIdOrName(description);
	}

	public List<CompanyLearners> allLearnersByUser(Users user) throws Exception {
		return dao.findByUser(user.getId());

	}

	public void sendLPMFM015Email(Users createUser, Users learner, Company company, Company trainingProvider, boolean requireGaurdian, CompanyLearners companylearners, List<UsersLanguage> usersLanguages, List<UsersDisability> usersDisabilityList) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		populateParametersOther(companylearners, params);
		
		String toEmail = createUser.getEmail();
		String rsaIdPassport = anIDNumber(companylearners.getUser());
		String qualificationTitle = getCompanyLearnerStringQualification(companylearners);
		
		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-015-SkillsProgrammeLearnerRegistrationForm.jasper", params);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("LearnerRegistrationForm.pdf");
		attachmentBeans.add(beanAttachment);

		byte[] bites2 = JasperService.instance().convertJasperReportToByte("LPM-FM-015-FOR_OFFICE_USE_ONLY.jasper", params);
		beanAttachment = new AttachmentBean();
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites2);
		beanAttachment.setFilename("LearnerRegistrationFormForOfficeUseOnly.pdf");
		attachmentBeans.add(beanAttachment);


		String subject = "LEARNER REGISTRATION APPLICATION";

		String mssg = "<br/>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Dear #FirstName# #Surname#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the learner registration for #learnerFirstName# #learnerSurname# (#rsaIdPassport#) for the qualification/programme: #QualificationTitle#.</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Attached is the agreement that must be signed by the relevant parties and submitted on the NSDMS for review by the merSETA.</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Should you require any assistance or further information, kindly contact the Client Liaison Officer at " + getRegionString(trainingProvider) + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Client Services</p>" + "<br/>";

		mssg = mssg.replaceAll("#FirstName#", createUser.getFirstName());
		mssg = mssg.replaceAll("#Surname#", createUser.getLastName());
		mssg = mssg.replaceAll("#learnerFirstName#", companylearners.getUser().getFirstName());
		mssg = mssg.replaceAll("#learnerSurname#", companylearners.getUser().getLastName());
		mssg = mssg.replaceAll("#rsaIdPassport#", rsaIdPassport);
		mssg = mssg.replaceAll("#QualificationTitle#", qualificationTitle);
		// String fileName = learner.getFirstName() + "_" + learner.getLastName() +
		// "_Reg_Form.pdf";

		// GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites,
		// fileName, "pdf", null);

		GenericUtility.sendMailWithAttachementTempWithLog(toEmail, subject, mssg, attachmentBeans, null);

	}

	public List<CompanyLearners> findUserAndQualification(Users u, Qualification qualification) throws Exception {
		return dao.findUserAndQualification(u, qualification);
	}

	/**
	 * 
	 * @param company
	 * @param qualification
	 * @return int
	 * @throws Exception
	 */
	public int countLearnersAssignedToCompanyWithQualificationId(Company company, Qualification qualification) throws Exception {
		return dao.countLearnersAssignedToCompanyWithQualificationId(company.getId(), qualification.getId());
	}

	/**
	 * 
	 * @param company
	 * @param ofoCode
	 * @return int
	 * @throws Exception
	 */
	public int countLearnersAssignedToCompanyWithOfoCodeId(Company company, OfoCodes ofoCode) throws Exception {
		return dao.countLearnersAssignedToCompanyWithOfoCodeId(company.getId(), ofoCode.getId());
	}

	public void sendLPMFM002Email(Users createUser, Users learner, Company company, Company trainingProvider, boolean requireGaurdian, CompanyLearners companylearners) throws Exception {

		MunicipalityService municipalityService = new MunicipalityService();
		// Setting Residential Address District
		Address residentialAddress = learner.getResidentialAddress();
		if (residentialAddress.getMunicipality().getDistrict() != null) {
			residentialAddress.getMunicipality().setDistrict(municipalityService.findByKey(residentialAddress.getMunicipality().getDistrict().getId()));
		} else {
			residentialAddress.getMunicipality().setDistrict(null);
		}
		learner.setResidentialAddress(residentialAddress);
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");

		Users leadCompanyContactPerson = companyUsersService.findCompanyContactPerson(company.getId());

		Users leadCompanyContactSupervisor = companyUsersService.findCompanyContactPerson(company.getId());// To be fixed

		Company hostCompany = companyService.findByKey(companylearners.getCompany().getId());

		Users hostCompanyContactPerson = companyUsersService.findCompanyContactPerson(hostCompany.getId());

		Users skillDevelopmentProviderContactPerson = companyUsersService.findCompanyContactPerson(trainingProvider.getId());

		// Setting Residential Address District
		if (trainingProvider.getResidentialAddress() != null) {
			trainingProvider.setResidentialAddress(AddressService.instance().findByKeyJoinDistric(trainingProvider.getResidentialAddress().getId()));
		} else {
			trainingProvider.setRegisteredAddress(new Address());
		}
		// Checking workplace approval for lead company
		boolean isLeadComWorkplaceApproved = false;
		boolean isPublic = false;

		if (trainingProvider != null && trainingProvider.getOrganisationType() != null && trainingProvider.getOrganisationType().getDescription().matches("Public Entity")) {
			isPublic = true;
		}

		WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
		if (workPlaceApprovalService.findByCompanyCount(company) > 0) {
			isLeadComWorkplaceApproved = true;
		}
		// Checking workplace approval for lead company
		boolean isHostCompWorkplaceApproved = false;
		if (workPlaceApprovalService.findByCompanyCount(hostCompany) > 0) {
			isHostCompWorkplaceApproved = true;
		}
		String leadCompanySETA = "merSETA";
		if (company.getCompanyStatus() == CompanyStatusEnum.NonMersetaCompany) {
			// Set ETQA des as leadCompanySETA
			if (company.getEtqa() != null) {
				leadCompanySETA = company.getEtqa().getDescription();
			} else {
				leadCompanySETA = "N/A";
			}
		}else {
			leadCompanySETA = "MERSETA - Manufacturing, Engineering and Related Services Education and Training Authority";
		}
		String training_intervention = companylearners.getInterventionType().getDescription();
		boolean funded_by_merseta = companylearners.getMersetaFunded().getYesNoBoolean();
		boolean isHavingHostEmployer = true; // To be fixed
		boolean liveInUrban = false;
		if (learner.getUrbanRuralEnum().getFriendlyName().equalsIgnoreCase("Urban")) {
			liveInUrban = true;
		}

		params.put("isPublic", isPublic);
		params.put("companylearners", companylearners);
		params.put("training_intervention", training_intervention);
		params.put("funded_by_merseta", funded_by_merseta);
		params.put("isHavingHostEmployer", isHavingHostEmployer);
		params.put("isHavingHostEmployer", isHavingHostEmployer);
		params.put("live_in_urban", liveInUrban);
		params.put("learner", learner);
		params.put("employer", company);
		params.put("leadCompanySETA", leadCompanySETA);
		params.put("isLeadComWorkplaceApproved", isLeadComWorkplaceApproved);
		params.put("leadCompanyContactPerson", leadCompanyContactPerson);
		params.put("leadCompanyContactSupervisor", leadCompanyContactSupervisor);
		params.put("hostCompany", hostCompany);
		params.put("isHostCompWorkplaceApproved", isHostCompWorkplaceApproved);
		params.put("hostCompanyContactPerson", hostCompanyContactPerson);
		params.put("skill_dev_provider", trainingProvider);
		params.put("skill_dev_provider_contact_person", skillDevelopmentProviderContactPerson);
		params.put("isMinor", requireGaurdian);

		if (requireGaurdian) {
			Users legalGaurdian = learner.getLegalGaurdian();
			params.put("parentGuadian", legalGaurdian);
		}

		String toEmail = createUser.getEmail();

		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-002-LearnershipAgreement(ExcludingSkillsProgrammes).jasper", params);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("LearnershipAgreement.pdf");
		attachmentBeans.add(beanAttachment);

		byte[] bites2 = JasperService.instance().convertJasperReportToByte("LPM-FM-002-LearnershipAgreementForOfficeUseOnly.jasper", params);
		beanAttachment = new AttachmentBean();
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites2);
		beanAttachment.setFilename("LearnershipAgreementForOfficeUseOnly.pdf");
		attachmentBeans.add(beanAttachment);

		String subject = "LEARNER REGISTRATION APPLICATION";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + createUser.getFirstName() + " " + createUser.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Attached, please find Skills Programme Learner Registration Form" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		// String fileName = learner.getFirstName() + "_" + learner.getLastName() +
		// "_Reg_Form.pdf";

		// GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites,
		// fileName, "pdf", null);
		GenericUtility.sendMailWithAttachementTempWithLog(toEmail, subject, mssg, attachmentBeans, null);

	}

	public String convertToHTML(List<RejectReasons> rejectReasons) {

		String sb = "<ul style=\"font-size:11.0pt;\";font-family:\"Arial\">";
		for (RejectReasons r : rejectReasons) {
			sb += "<li>" + r.getDescription() + "</li>";
		}
		sb += "</ul>";
		return sb;
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilterWhereLearnerInfomation(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o where o.id is not null and o.status = :status";
		filters.put("status", ApprovalEnum.PendingCommitteeApproval);
		return (List<CompanyLearners>) dao.sortAndFilterWhereLearnerInfo(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWhereLearnerInfomation(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.id is not null and o.status = :status";
		filters.put("status", ApprovalEnum.PendingCommitteeApproval);
		return dao.countWhereLearnerInfo(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilterWhereLearnerInfo(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o where o.id is not null and o.interventionType is not null ";
		return (List<CompanyLearners>) dao.sortAndFilterWhereLearnerInfo(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWhereLearnerInfo(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.id is not null and o.interventionType is not null ";
		return dao.countWhereLearnerInfo(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilterWhereLearnerUnderReview(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		//String hql = "select o from CompanyLearners o where o.interventionType is not null and o.status =:status and o.learnerStatus =:learnerStatus and o.companyLearnersParent.id is null";
		String hql = "select o from CompanyLearners o where o.interventionType is not null and o.status =:status and o.learnerStatus =:learnerStatus ";
		return (List<CompanyLearners>) dao.sortAndFilterWhereLearnerInfo(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWhereLearnerUnderReview(Map<String, Object> filters) throws Exception {
		//String hql = "select count(o) from CompanyLearners o where o.interventionType is not null and o.status =:status and o.learnerStatus =:learnerStatus and o.companyLearnersParent.id is null";
		String hql = "select count(o) from CompanyLearners o where o.interventionType is not null and o.status =:status and o.learnerStatus =:learnerStatus ";
		return dao.countWhereLearnerInfo(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilterWhereLearnerMentorRatio(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o where o.interventionType is not null and (o.sitesSme.id is not null or o.smeQualification.id is not null) and (o.company.id = :companyID or  o.employer.id = :companyID)";
		return (List<CompanyLearners>) dao.sortAndFilterWhereLearnerInfo(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWhereLearnerMentorRatio(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.interventionType is not null and (o.sitesSme.id is not null or o.smeQualification.id is not null) and (o.company.id = :companyID or  o.employer.id = :companyID)";
		return dao.countWhereLearnerInfo(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findByUser(Users u) throws Exception {
		return dao.findByUser(u);
	}
	
	@SuppressWarnings("unchecked")
	public CompanyLearners findClByUser(Users u) throws Exception {
		List<CompanyLearners>list =  dao.findByUser(u);
		if(list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}		
	}
	
	public CompanyLearners findCompanyLearnersByUserAndQualification(Users u, Qualification q, InterventionType i, LearnerStatusEnum learnerStatusEnum) throws Exception {
		List<CompanyLearners>list =  dao.findCompanyLearnersByUserAndQualification(u,q,i, learnerStatusEnum);
		if(list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}		
	}
	
	public CompanyLearners findCompanyLearnersByUserAndQualification(Users u, Qualification q,LearnerStatusEnum learnerStatusEnum) throws Exception {
		List<CompanyLearners>list =  dao.findCompanyLearnersByUserAndQualification(u,q,learnerStatusEnum);
		if(list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}		
	}
	
	public CompanyLearners findCompanyLearnersByUserAndQualification(Users u, Qualification q, InterventionType i) throws Exception {
		List<CompanyLearners>list =  dao.findCompanyLearnersByUserAndQualification(u,q,i);
		if(list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}		
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilterSearch(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status and o.company.id = :companyID";
		return (List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countSearch(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.status = :status and o.company.id = :companyID";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilterSearchStatus(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status and o.company.id = :companyID and o.submissionEnum = :submissionEnum and o.companyLearnersParent.id = :companyLearnerParentID ";
		return (List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countSearchStatus(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.status = :status and o.company.id = :companyID and o.submissionEnum = :submissionEnum and o.companyLearnersParent.id = :companyLearnerParentID ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilterHoldingRoomF(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status  and o.interventionType is null";
		return (List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countHoldingRoomF(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.status = :status  and o.interventionType is null ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilterHoldingRoom(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status  and o.interventionType is null and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		return (List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countHoldingRoom(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.status = :status  and o.interventionType is null and o.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilterReport(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o where o.status = :status  and o.interventionType is null";
		return (List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countReport(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.status = :status  and o.interventionType is null";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearners> sortAndFilterSearchParent(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o where o.companyLearnersParent.id = :companyLearnerParentID and o.interventionType is not null";
		return (List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countSearchParent(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.companyLearnersParent.id = :companyLearnerParentID and o.interventionType is not null";
		return dao.countWhere(filters, hql);
	}

	public Users setNotApplicable() {
		Users user = new Users();
		user.setFirstName("N/A");
		user.setFirstName("N/A");
		Designation designation = new Designation();
		designation.setDescription("N/A");
		user.setDesignation(designation);
		user.setTelNumber("N/A");
		user.setCellNumber("N/A");
		user.setFaxNumber("N/A");
		return user;
	}

	public String generateCompanyLearnerRegNumber(CompanyLearners cl) throws Exception {
		String regNumber = "";
		boolean valueExist = false;
		int clId = cl.getId().intValue();
		int coutApprovedLearners = dao.countAllCompanyLearnersWhereRegNumberApllied();
		
		if(clId <= coutApprovedLearners){
			valueExist = true;
		}
		
		while(valueExist){
			clId ++;
			coutApprovedLearners = dao.countAllCompanyLearnersWhereRegNumberApllied();

			if(clId <= coutApprovedLearners){
				valueExist = true;
			}else{
				valueExist = false;
			}
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yy"); 
		String yearFormat = dateFormat.format(getSynchronizedDate());
		
		String mersetaCode = "17/"; // merseta code
		int number = 1000000 + clId;
		//int number = 1000000 + (dao.countAllCompanyLearnersWhereRegNumberApllied() + 1); // number for company learner
		String year = "/"+yearFormat; // year [build in year identifier for future proof]
		regNumber = mersetaCode + number + year;
		return regNumber;
	}

	public void sendWorkBasedLearningProgrammeAgreement(Users createUser, Users learner, Company company, Company trainingProvider, boolean requireGaurdian, CompanyLearners companylearners, List<UsersDisability> usersDisabilityList) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		populateParametersWBA(companylearners, params);
		byte[] bites = JasperService.instance().convertJasperReportToByte("WORK_BASED_LEARNING_PROGRAMME_AGREEMENT.jasper", params);

		String rsaIdPassport = anIDNumber(companylearners.getUser());
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("LearnerAgreement.pdf");
		attachmentBeans.add(beanAttachment);

		String subject = "LEARNER REGISTRATION APPLICATION";

		String welcome = "<br/>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Dear #FirstName# #Surname#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the learner registration for #learnerFirstName# #learnerSurname# (#rsaIdPassport#) for the qualification/programme: (#QualificationCode#)#QualificationTitle#.</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Attached is the agreement that must be signed by the relevant parties and submitted on the NSDMS for review by the merSETA.</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Should you require any assistance or further information, kindly contact the Client Liaison Officer at " + getRegionString(trainingProvider) + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Client Services</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", createUser.getFirstName());
		welcome = welcome.replaceAll("#Surname#", createUser.getLastName());
		welcome = welcome.replaceAll("#learnerFirstName#", companylearners.getUser().getFirstName());
		welcome = welcome.replaceAll("#learnerSurname#", companylearners.getUser().getLastName());
		welcome = welcome.replaceAll("#rsaIdPassport#", rsaIdPassport);
		if (companylearners.getQualification() != null && companylearners.getQualification().getId() != null) {
			welcome = welcome.replaceAll("#QualificationCode#", companylearners.getQualification().getCodeString());
			welcome = welcome.replaceAll("#QualificationTitle#", companylearners.getQualification().getDescription());
		} else if (companylearners.getLearnership() != null && companylearners.getLearnership().getQualification() != null && companylearners.getLearnership().getId() != null) {
			welcome = welcome.replaceAll("#QualificationCode#", companylearners.getLearnership().getQualification().getCodeString());
			welcome = welcome.replaceAll("#QualificationTitle#", companylearners.getLearnership().getQualification().getDescription());
		}

		createUser.getFirstName();
		createUser.getLastName();

		GenericUtility.sendMailWithAttachementTempWithLog(createUser.getEmail(), subject, welcome, attachmentBeans, null);
	}
	
	public void downloadWorkBasedLearningProgrammeAgreement(CompanyLearners companylearners) throws Exception {		
		if(companylearners.getInterventionType().getForm().matches("002")) {
			Map<String, Object> params = new HashMap<String, Object>();
			populateParametersWBA(companylearners, params);
			JasperService js = new JasperService();
			js.createReportFromDBtoServletOutputStream("WORK_BASED_LEARNING_PROGRAMME_AGREEMENT.jasper", params, companylearners.getUser().getFirstName()+"_"+companylearners.getUser().getLastName()+"_"+"AggrementForm.pdf");
		}else {
			Map<String, Object> params = new HashMap<String, Object>();
			populateParametersOther(companylearners, params);
			JasperService js = new JasperService();
			js.createReportFromDBtoServletOutputStream("LPM-FM-015-SkillsProgrammeLearnerRegistrationForm.jasper", params,companylearners.getUser().getFirstName()+"_"+companylearners.getUser().getLastName()+"_"+"AggrementForm.pdf");
		}
	}

	private void populateParametersWBA(CompanyLearners companylearners, Map<String, Object> params) throws Exception {		
		companylearners.setCompany(companyService.findByKey(companylearners.getCompany().getId()));
		companylearners.setEmployer(companyService.findByKey(companylearners.getEmployer().getId()));
		companylearners.getUser().setPostalAddress(AddressService.instance().findByKey(companylearners.getUser().getPostalAddress().getId()));
		companylearners.getUser().setResidentialAddress(AddressService.instance().findByKey(companylearners.getUser().getResidentialAddress().getId()));
		companylearners.getCompany().setPostalAddress(AddressService.instance().findByKey(companylearners.getCompany().getPostalAddress().getId()));
		companylearners.getCompany().setResidentialAddress(AddressService.instance().findByKey(companylearners.getCompany().getResidentialAddress().getId()));
		companylearners.getEmployer().setPostalAddress(AddressService.instance().findByKey(companylearners.getEmployer().getPostalAddress().getId()));
		companylearners.getEmployer().setResidentialAddress(AddressService.instance().findByKey(companylearners.getEmployer().getResidentialAddress().getId()));
		
		String userDisability = "N/A";
		String leadCompanySETA = "";
		String approvingSETA = "";
		String leadProviderSETA = "";
		String employerApprovedDate = "";
		String employerReviewDate = "";
		
		UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
		List<InterventionBean> list = populateList(companylearners.getInterventionType());
		List<UsersDisability> usersDisabilityList = usersDisabilityService.findByKeyUser(companylearners.getUser());
		if (usersDisabilityList != null && usersDisabilityList.size() > 0) {
			userDisability = pupulateStringUserDisability(usersDisabilityList);
		}
		
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "higher_education_and_training_logo");

		Users leadCompanyContactPerson = new Users();
		List<Users> trainingProviderlist = companyUsersService.findCompanyContactPersonList(companylearners.getCompany().getId());
		if (trainingProviderlist != null && trainingProviderlist.size() > 0) {
			leadCompanyContactPerson = trainingProviderlist.get(0);
		}
		Users employerContactPerson = new Users();
		SDFCompany primarySDF = companyService.findPrimarySDF(companyService.findByKey(companylearners.getEmployer().getId()));
		if(primarySDF != null && primarySDF.getSdf() != null) {
			employerContactPerson = primarySDF.getSdf();
		}else {
			List<Users> companylist = companyUsersService.findCompanyContactPersonList(companylearners.getEmployer().getId());
			if (companylist != null && companylist.size() > 0) {
				employerContactPerson = companylist.get(0);
			}
		}
		//Please check 
		boolean isEmployerLiableForSDL = true;
		boolean isCompanyLiableForSDL = true;
		if(companylearners.getHostCompany() != null) {
			isCompanyLiableForSDL = true;
		}		
		boolean isCompanyActingAsLeadCompany = true;
		if (companylearners.getHostCompanyyesNoEnum() != null && companylearners.getHostCompanyyesNoEnum() == YesNoEnum.Yes) {
			isCompanyActingAsLeadCompany = false;
		}
		
		WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
		WorkPlaceApproval wpa = workPlaceApprovalService.findApprovedFirstByCompany(companylearners.getEmployer());

		if (companylearners.getEmployer().getCompanyStatus() == CompanyStatusEnum.NonMersetaCompany) {
			if (companylearners.getEmployer().getEtqa() != null) {
				leadCompanySETA = companylearners.getEmployer().getEtqa().getDescription();
			} else {
				leadCompanySETA = "N/A";
			}
		}else {
			leadCompanySETA = "MERSETA - Manufacturing, Engineering and Related Services Education and Training Authority";
		}

		if (wpa != null) {
			approvingSETA = "MERSETA - Manufacturing, Engineering and Related Services Education and Training Authority";
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			if (wpa.getApprovalDate() != null) {
				employerApprovedDate = dateFormat.format(wpa.getApprovalDate());
			}
			if (wpa.getApprovalReviewDate() != null) {
				employerReviewDate = dateFormat.format(wpa.getApprovalReviewDate());
			}
		}

		/*if (companylearners.getEmployer().getPostalAddress() != null && companylearners.getEmployer().getPostalAddress().getId() != null) {
			companylearners.getEmployer().setPostalAddress(AddressService.instance().findByKey(companylearners.getEmployer().getPostalAddress().getId()));
		}

		if (companylearners.getCompany().getPostalAddress() != null && companylearners.getCompany().getPostalAddress().getId() != null) {
			companylearners.getCompany().setPostalAddress(AddressService.instance().findByKey(companylearners.getCompany().getPostalAddress().getId()));
		}*/
		
		if (companylearners.getHostCompanyyesNoEnum() != null && companylearners.getHostCompanyyesNoEnum() == YesNoEnum.Yes) {
			isCompanyActingAsLeadCompany = false;
		}
		
		boolean isEmployerActingAsLeadEmployer = true;
		if (companylearners.getHostCompanyyesNoEnum() != null && companylearners.getHostCompanyyesNoEnum() == YesNoEnum.Yes) {
			isEmployerActingAsLeadEmployer = false;
		}
		
		Users companyContactPerson = new Users();
		if(companylearners.getQualification() == null) {
			Qualification qualification = getCompanyLearnerQualification(companylearners);
			companylearners.setQualification(qualification);
		}		
	
		TrainingProviderApplication trainingProviderApplication = new  TrainingProviderApplication();
		Users trainingProviderContactPerson = new Users();
		if(companylearners.getTrainingProviderApplication() != null){
			trainingProviderApplication = TrainingProviderApplicationService.instance().findByKey(companylearners.getTrainingProviderApplication().getId());
			List<Users> providerlist = companyUsersService.findCompanyContactPersonList(trainingProviderApplication.getCompany().getId());
			if (providerlist != null && providerlist.size() > 0) {
				trainingProviderContactPerson = providerlist.get(0);
			}else {
				trainingProviderContactPerson.setFirstName("");
				trainingProviderContactPerson.setLastName("");
			}
			if(trainingProviderApplication != null && trainingProviderApplication.getEtqa() != null && trainingProviderApplication.getEtqa().getDescription() !=null) {
				leadProviderSETA = trainingProviderApplication.getEtqa().getDescription();
			}
			companylearners.getCompany().setTrainingProviderApplication(trainingProviderApplication);
		}else {
			trainingProviderContactPerson = employerContactPerson;
		}
		
		/*List<TrainingProviderApplication> lists = TrainingProviderApplicationService.instance().findByCompanyAndStatus(companylearners.getCompany(), ApprovalEnum.Approved);
		if (lists != null && lists.size() > 0) {
			trainingProviderApplication = lists.get(0);
			if (trainingProviderApplication != null && trainingProviderApplication.getId() != null && trainingProviderApplication.getEtqa() != null && trainingProviderApplication.getEtqa().getDescription() != null) {
				leadProviderSETA = trainingProviderApplication.getEtqa().getDescription();
				companylearners.getCompany().setTrainingProviderApplication(trainingProviderApplication);
			}
		}*/
		
		String partOfQualification = "";
		if(companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership && companylearners.getLearnership() !=null) {
			partOfQualification = companylearners.getLearnership().getCode();
		}else {
			if(companylearners.getQualification() != null) {
				partOfQualification = companylearners.getQualification().getCodeString();
			}
		}
		
		boolean training_provider_required = false;
		if(companylearners.getInterventionType().getForSdpAccreditaion() != null && companylearners.getInterventionType().getForSdpAccreditaion()) {
			training_provider_required = true;
		}
		params.put("training_provider_required", training_provider_required);
		params.put("list", new JRBeanCollectionDataSource(list));		
		params.put("isEmployerLiableForSDL", isEmployerLiableForSDL);
		params.put("isCompanyLiableForSDL", isCompanyLiableForSDL);
		params.put("isCompanyActingAsLeadCompany", isCompanyActingAsLeadCompany);
		params.put("isEmployerActingAsLeadEmployer", isEmployerActingAsLeadEmployer);
		params.put("learner", companylearners.getUser());
		params.put("trainingProviderApplication", trainingProviderApplication);
		params.put("trainingProviderContactPerson", trainingProviderContactPerson);
		params.put("employerContactPerson", employerContactPerson);
		params.put("leadCompanyContactPerson", leadCompanyContactPerson);
		params.put("employer", companylearners.getEmployer());
		params.put("company", companylearners.getCompany());
		params.put("companylearners", companylearners);
		params.put("userDisability", userDisability);
		params.put("leadCompanySETA", leadCompanySETA);
		params.put("approvingSETA", approvingSETA);
		params.put("employerApprovedDate", employerApprovedDate);
		params.put("employerReviewDate", employerReviewDate);
		params.put("leadProviderSETA", leadProviderSETA);
		params.put("leadProviderSETA", leadProviderSETA);
		params.put("partOfQualification", partOfQualification);

		if (companylearners.getUser().getLegalGaurdian() != null) {
			Users legalGaurdian = companylearners.getUser().getLegalGaurdian();
			params.put("parentGuadian", legalGaurdian);
		}
	}
	
	private void populateParametersOther(CompanyLearners companylearners, Map<String, Object> params) throws Exception {		
		companylearners.setCompany(companyService.findByKey(companylearners.getCompany().getId()));
		companylearners.setEmployer(companyService.findByKey(companylearners.getEmployer().getId()));
		companylearners.getUser().setPostalAddress(AddressService.instance().findByKey(companylearners.getUser().getPostalAddress().getId()));
		companylearners.getUser().setResidentialAddress(AddressService.instance().findByKey(companylearners.getUser().getResidentialAddress().getId()));
		companylearners.getCompany().setPostalAddress(AddressService.instance().findByKey(companylearners.getCompany().getPostalAddress().getId()));
		companylearners.getCompany().setResidentialAddress(AddressService.instance().findByKey(companylearners.getCompany().getResidentialAddress().getId()));
		companylearners.getEmployer().setPostalAddress(AddressService.instance().findByKey(companylearners.getEmployer().getPostalAddress().getId()));
		companylearners.getEmployer().setResidentialAddress(AddressService.instance().findByKey(companylearners.getEmployer().getResidentialAddress().getId()));
		String userDisability = "N/A";
		UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
		List<UsersDisability> usersDisabilityList = usersDisabilityService.findByKeyUser(companylearners.getUser());
		if (usersDisabilityList != null && usersDisabilityList.size() > 0) {
			userDisability = pupulateStringUserDisability(usersDisabilityList);
		}
		UsersLanguageService usersLanguageService = new UsersLanguageService();
		List<UsersLanguage>usersLanguages = usersLanguageService.findByUser(companylearners.getUser());
		String leadCompanySETA = "";
		TrainingProviderApplication trainingProviderApplication = new TrainingProviderApplication();
		if (companylearners.getCompany().getAccreditationNumber() != null && !companylearners.getCompany().getAccreditationNumber().equals("")) {
			List<TrainingProviderApplication> lists = TrainingProviderApplicationService.instance().findByCertificateNumberOrAccreditationNumberList(companylearners.getCompany().getAccreditationNumber(), ApprovalEnum.Approved);
			// trainingProviderApplication =
			// TrainingProviderApplicationService.instance().findByCertificateNumberOrAccreditationNumber(trainingProvider.getAccreditationNumber(),
			// ApprovalEnum.Approved);
			if (lists != null && lists.size() > 0) {
				trainingProviderApplication = lists.get(0);
			}
		}
		companylearners.getCompany().setTrainingProviderApplication(trainingProviderApplication);

		String site = "";
		if (companylearners.getSite() != null) {
			site = companylearners.getSite().getCompanyName();
		} else {
			site = companylearners.getCompany().getCompanyName();
		}

		if (trainingProviderApplication != null && trainingProviderApplication.getEtqa() != null) {
			leadCompanySETA = trainingProviderApplication.getEtqa().getDescription();
		}

		MunicipalityService municipalityService = new MunicipalityService();
		// Setting Residential Address District
		Address residentialAddress = companylearners.getUser().getResidentialAddress();
		if (residentialAddress.getMunicipality().getDistrict() != null) {
			residentialAddress.getMunicipality().setDistrict(municipalityService.findByKey(residentialAddress.getMunicipality().getDistrict().getId()));
		} else {
			residentialAddress.getMunicipality().setDistrict(null);
		}
		companylearners.getUser().setResidentialAddress(residentialAddress);
	
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		CompanyService companyService = new CompanyService();

		Users leadCompanyContactPerson = new Users();
		SDFCompany sDFCompany = SDFCompanyService.instance().findPrimarySDF(companylearners.getEmployer());
		if (sDFCompany != null) {
			leadCompanyContactPerson = sDFCompany.getSdf();
		}

		Users leadCompanyContactSupervisor = companyUsersService.findCompanyContactPerson(companylearners.getEmployer().getId());// To be fixed

		Company hostEmployer = new Company();
		if (companylearners.getHostCompany() != null) {
			hostEmployer = companyService.findByKey(companylearners.getHostCompany().getId());
		}

		String region = getRegionString(companylearners.getCompany());

		Users hostCompanyContactPerson = new Users();
		if(hostEmployer !=null && hostEmployer.getId() !=null) {
			List<Users> hostCompanyContactPersonlist = companyUsersService.findCompanyContactPersonList(hostEmployer.getId());
			if (hostCompanyContactPersonlist != null && hostCompanyContactPersonlist.size() > 0) {
				hostCompanyContactPerson = hostCompanyContactPersonlist.get(0);
			}
		}
		
		List<Users> leadCompanyContactPersonlist = companyUsersService.findCompanyContactPersonList(companylearners.getEmployer().getId());
		if (leadCompanyContactPersonlist != null && leadCompanyContactPersonlist.size() > 0) {
			//companylearners.getHostCompany().setContactPerson(hostCompanyContactPersonlist.get(0)); 
			companylearners.getEmployer().setContactPerson(leadCompanyContactPersonlist.get(0));
		}
		
		hostEmployer.setSetaRegisteredAt(leadCompanySETA);
		companylearners.getEmployer().setSetaRegisteredAt(leadCompanySETA);
		companylearners.getCompany().setSetaRegisteredAt(leadCompanySETA);

		Users skillDevelopmentProviderContactPerson = companyUsersService.findCompanyContactPerson(companylearners.getCompany().getId());

		sDFCompany = SDFCompanyService.instance().findPrimarySDF(companylearners.getCompany());
		if (sDFCompany != null) {
			skillDevelopmentProviderContactPerson = sDFCompany.getSdf();
		}

		// Setting Residential Address District
		if (companylearners.getCompany().getResidentialAddress() != null) {
			companylearners.getCompany().setResidentialAddress(AddressService.instance().findByKeyJoinDistric(companylearners.getCompany().getResidentialAddress().getId()));
		} else {
			companylearners.getCompany().setRegisteredAddress(new Address());
		}
		if (companylearners.getCompany().getPostalAddress() != null) {
			companylearners.getCompany().setPostalAddress(AddressService.instance().findByKeyJoinDistric(companylearners.getCompany().getPostalAddress().getId()));
		} else {
			companylearners.getCompany().setPostalAddress(new Address());
		}
		// Checking workplace approval for lead company
		boolean isLeadComWorkplaceApproved = false;
		WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
		if (workPlaceApprovalService.findByCompanyCount(companylearners.getEmployer()) > 0) {
			isLeadComWorkplaceApproved = true;
		}
		// Checking workplace approval for lead company
		boolean isHostCompWorkplaceApproved = false;
		if (hostEmployer != null && hostEmployer.getId() != null) {
			if (workPlaceApprovalService.findByCompanyCount(hostEmployer) > 0) {
				isHostCompWorkplaceApproved = true;
			}
		}

		if (companylearners.getEmployer().getCompanyStatus() == CompanyStatusEnum.NonMersetaCompany) {
			if (companylearners.getEmployer().getEtqa() != null) {
				leadCompanySETA = companylearners.getEmployer().getEtqa().getDescription();
			} else {
				leadCompanySETA = "N/A";
			}
		}else {
			leadCompanySETA = "MERSETA - Manufacturing, Engineering and Related Services Education and Training Authority";
		}
		
		String training_intervention = companylearners.getInterventionType().getDescription();
		boolean funded_by_merseta = false;
		if(companylearners.getMersetaFunded()!= null && companylearners.getMersetaFunded().getYesNoBoolean()) {
			funded_by_merseta = true;
		}
		boolean isHavingHostEmployer = true; // To be fixed
		boolean liveInUrban = false;
		if (companylearners.getUser().getUrbanRuralEnum() != null && companylearners.getUser().getUrbanRuralEnum().getFriendlyName().equalsIgnoreCase("Urban")) {
			liveInUrban = true;
		}
		boolean tvet_fet_learner = false;
		boolean non_credit_bearing = false;
		if(companylearners.getInterventionType().getId() == HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
			non_credit_bearing =true;
		}
		List<CompanyLearners> companylearnersList = new ArrayList<>();
		if(companylearners.getInterventionType().getId() != HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
			companylearners.setNonCredidBearingDescription(getCompanyLearnerStringQualification(companylearners));			
		}else if(companylearners.getInterventionType().getId() != HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			if(companylearners.getTvetFetQualification() !=null && companylearners.getTvetFetQualification().getTvetFet() != null && companylearners.getTvetFetQualification().getTvetFet()) {
				tvet_fet_learner = true;
			}
		}
		companylearnersList.add(companylearners);

		boolean physical_addresses = true;
		boolean commencement_completion_date = true;
		boolean sa_citizen = true;
		boolean skills_type = true;
		boolean flc = true;
		boolean union_member= false;
		
		if (companylearners.getUnionMembership()!= null && companylearners.getUnionMembership().getId() == 1) {
			union_member= true;
		}

		String howLongEmployed = "";
		if (companylearners.getEmploymentStatus() == EmploymentStatusEnum.UnEmployed) {
			howLongEmployed = companylearners.getHowLong() + " Months(s)";
		}
		
		if(companylearners.getQualification() == null) {
			Qualification qualification = getCompanyLearnerQualification(companylearners);
			companylearners.setQualification(qualification);
		}
		
		List<Users> trainingProviderlist = companyUsersService.findCompanyContactPersonList(companylearners.getCompany().getId());
		if (trainingProviderlist != null && trainingProviderlist.size() > 0) {
			companylearners.getCompany().setContactPerson(trainingProviderlist.get(0)); 
		}
		boolean requireGaurdian = false;
		if (companylearners.getUser().getLegalGaurdian() != null) {
			requireGaurdian = true;
			Users legalGaurdian = companylearners.getUser().getLegalGaurdian();
			params.put("parentGuadian", legalGaurdian);
		}
		boolean training_provider_required = false;
		if(companylearners.getInterventionType().getForSdpAccreditaion() != null && companylearners.getInterventionType().getForSdpAccreditaion()) {
			training_provider_required = true;
		}
		
		params.put("non_credit_bearing", non_credit_bearing);
		params.put("training_provider_required", training_provider_required);
		params.put("union_member", union_member);
		params.put("userDisability", userDisability);
		params.put("learner", companylearners.getUser());
		params.put("employer", companylearners.getEmployer());
		params.put("employer_contact_person", leadCompanyContactPerson);
		params.put("leadCompanyContactPerson", leadCompanyContactPerson);
		params.put("company_learners_trade_test", new CompanyLearnersTradeTest());
		params.put("attempted_trade_test", true);
		params.put("flc", flc);
		params.put("trade", "Patrick To resolve");
		params.put("ofo_codes", new OfoCodes());
		params.put("currentOccupation", "Resolve currentOccupation");
		params.put("companylearners", companylearners);
		params.put("experience", "Resolve experience");
		params.put("user", companylearners.getUser());
		params.put("etqa", " ");
		params.put("tvet_fet_learner", tvet_fet_learner);		
		params.put("skills_type", skills_type);
		params.put("sa_citizen", sa_citizen);
		params.put("commencement_completion_date", commencement_completion_date);
		params.put("physical_addresses", physical_addresses);
		params.put("training_intervention", training_intervention);
		params.put("funded_by_merseta", funded_by_merseta);
		params.put("isHavingHostEmployer", isHavingHostEmployer);
		params.put("isHavingHostEmployer", isHavingHostEmployer);
		params.put("live_in_urban", liveInUrban);

		params.put("leadCompany", companylearners.getEmployer());
		params.put("leadCompanySETA", leadCompanySETA);
		params.put("isLeadComWorkplaceApproved", isLeadComWorkplaceApproved);

		params.put("leadCompanyContactSupervisor", leadCompanyContactSupervisor);
		params.put("hostEmployer", hostEmployer);
		params.put("isHostCompWorkplaceApproved", isHostCompWorkplaceApproved);
		params.put("hostCompanyContactPerson", hostCompanyContactPerson);
		params.put("skillDevelopmentProvider", companylearners.getCompany());
		params.put("skillDevelopmentProviderContactPerson", skillDevelopmentProviderContactPerson);
		params.put("isMinor", requireGaurdian);
		params.put("how_long", howLongEmployed);
		params.put("region", region);
		params.put("site", site);

		
		ArrayList<SkillsProgrammeSkillsSetBean> skillsProgrammeSkillsSetBeanList = new ArrayList<>();
		ArrayList<UnitStandardBean> unitStandardBeanList = new ArrayList<>();

		// *******************To be selected from DB************************
		if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
			SkillsProgrammeSkillsSetBean sks = new SkillsProgrammeSkillsSetBean(companylearners.getSkillsSet().getDescription(), companylearners.getSkillsSet().getProgrammeID(), companylearners.getCommencementDate(), companylearners.getCompletionDate());
			skillsProgrammeSkillsSetBeanList.add(sks);
		}
		if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
			SkillsProgrammeSkillsSetBean sks = new SkillsProgrammeSkillsSetBean(companylearners.getSkillsProgram().getDescription(), companylearners.getSkillsProgram().getProgrammeID(), companylearners.getCommencementDate(), companylearners.getCompletionDate());
			skillsProgrammeSkillsSetBeanList.add(sks);
		}

		if (companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			UnitStandardBean usb = new UnitStandardBean(companylearners.getUnitStandard().getTitle(), companylearners.getUnitStandard().getCode() + "", companylearners.getCommencementDate(), companylearners.getCompletionDate());
			unitStandardBeanList.add(usb);
		}

		// *******************END To be selected from DB********************

		params.put("usersLanguages", new JRBeanCollectionDataSource(usersLanguages));
		params.put("companylearnersList", new JRBeanCollectionDataSource(companylearnersList));
		params.put("SkillsProgrammeSkillsSetBeanDataSource", new JRBeanCollectionDataSource(skillsProgrammeSkillsSetBeanList));
		params.put("UnitStandardBeanDataSource", new JRBeanCollectionDataSource(unitStandardBeanList));
		
	}
	
	public void downloadForOfficeUseOnlyForm(CompanyLearners companyLearners, Users user) throws Exception {

		String adminName = user.getFirstName() + " " + user.getLastName();
		String rsaIdPassport = anIDNumber(companyLearners.getUser());
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		HostingCompanyEmployees hostingCompanyEmployees = hostingCompanyEmployeesService.findByUserReturnHostCompany(user.getId());
		
		String designation = "N/A";
		if(hostingCompanyEmployees != null) {
			designation = hostingCompanyEmployees.getJobTitle().getDescription();
		}
		
		String conditionalPlacementDate = "";
		String registrationDateOfaggrement = "";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		if(companyLearners.getConditionalPlacementDate() != null) {
			conditionalPlacementDate = dateFormat.format(companyLearners.getConditionalPlacementDate());
		}
		
		if(companyLearners.getApprovalDate() != null) {
			registrationDateOfaggrement = dateFormat.format(companyLearners.getApprovalDate());
		}
	
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "higher_education_and_training_logo");
		params.put("user", user);
		params.put("adminName", adminName);
		params.put("designation", designation);
		params.put("aggrementNumber", companyLearners.getRegistrationNumber());
		params.put("conditionalPlacementDate", conditionalPlacementDate);
		params.put("registrationDateofaggrement", registrationDateOfaggrement);
		
		
		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("WORK_BASED_LEARNING_PROGRAMME_AGREEMENT_OFFICE_USE.jasper", params, companyLearners.getUser().getFirstName()+"_"+companyLearners.getUser().getLastName()+"_"+"ForOfficeUseOnly.pdf");
	}

	public void sendLearnerTerminationUnsuccessful(Users user, ArrayList<RejectReasons> selectedRejectReason) throws Exception {

		String subject = "LEARNER TERMINATION APPLICATION OUTCOME";
		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + title + " " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the merSETA ETQA Review Committee has considered the Learner Termination application."

				+ "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We regret to advise that the outcome for the request " + "is <b>unsuccessful</b>. " + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<b>Rejection Reason(s):</b><br/>" + "" + convertToHTML(selectedRejectReason) + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely," + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<b>merSETA Administration</b>" + "</p>";

		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}

	public void sendLearnerTerminationRejection(Users user, ArrayList<RejectReasons> selectedRejectReason, CompanyLearnersTermination companyLearnersTermination) throws Exception {
		String appType = companyLearnersTermination.getTerminationTypeEnum().getFriendlyName();
		String subject = "" + appType.toUpperCase() + " APPLICATION OUTCOME";
		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		String agreementNum = "N/A";
		if (companyLearnersTermination.getCompanyLearners() != null && companyLearnersTermination.getCompanyLearners().getRegistrationNumber() != null) {
			agreementNum = companyLearnersTermination.getCompanyLearners().getRegistrationNumber();
		}
		Users learner = usersService.findByKey(companyLearnersTermination.getCompanyLearners().getUser().getId());
		String idNum = "";
		if (learner.getRsaIDNumber() != null && learner.getRsaIDNumber().length() > 0) {
			idNum = learner.getRsaIDNumber();
		} else {
			idNum = learner.getPassportNumber();
		}

		String learnerFullName = learner.getFirstName() + " " + learner.getLastName();
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + title + " " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the merSETA has reviewed the " + "request for a " + appType + " (Agreement No: " + agreementNum + ") " + "for the following learner: " + learnerFullName + " (" + idNum + ") " + "and has not approved the application for the following reason(s):" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + convertToHTML(selectedRejectReason) + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely," + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<b>Manager: Quality Assurance</b>" + "</p>";

		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}

	public void sendLearnerTerminationInvestigateEmail(Users user, String inverstigationDate, String time, Users learner, Users clo) throws Exception {
		String subject = "LEARNER AGREEMENT TERMINATION APPLICATION INVESTIGATION VISIT";
		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		String idNum = "";
		if (learner.getRsaIDNumber() != null && learner.getRsaIDNumber().length() > 0) {
			idNum = learner.getRsaIDNumber();
		} else {
			idNum = learner.getPassportNumber();
		}
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + title + " " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the merSETA has considered the learner agreement termination application for " + "" + learner.getFirstName() + " " + learner.getLastName() + "" + " " + "(" + idNum + ")." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "An investigation regarding the application will be conducted on: " + inverstigationDate + " at " + time + " by " + clo.getFirstName() + " " + clo.getLastName() + "." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely," + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<b>Client Liaison Officer: " + clo.getFirstName() + " " + clo.getLastName() + "</b>" + "</p>";

		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}

	public void sendUpdateReviewDateEmail(CompanyLearners companyLearnerParent, Company taskCompany, Users activeUser, Date reviewDate) throws Exception {
		List<Users> users = new ArrayList<>();
		// users.add(taskCompany.getContactPerson());
		List<CompanyLearners> list = findCompanyLearnersByParent(companyLearnerParent.getId());
		for (CompanyLearners companyLearner : list) {
			users.add(companyLearner.getUser());
			users.add(companyLearner.getCreateUser());
		}
		super.removeDuplicatesFromList(list);
		for (Users user : users) {
			sendReviewDateEmail(taskCompany, user, reviewDate);
		}
	}

	public void sendReviewDateEmail(Company taskCompany, Users user, Date reviewDate) throws Exception {
		RegionTown rt = new RegionTown();
		if (taskCompany.getResidentialAddress() != null && taskCompany.getResidentialAddress().getTown() != null) {
			rt = RegionTownService.instance().findByTown(taskCompany.getResidentialAddress().getTown());
		}
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = new Region();
		String regionDsec = "";
		if (rt.getRegion() != null) {
			r = regionService.findByKey(rt.getRegion().getId());
			regionDsec = r.getDescription();
		}

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String date = dateFormat.format(reviewDate);
		String time = timeFormat.format(reviewDate);

		Users clo = getCLO(taskCompany);
		String welcome = "<p>Dear #FirstName# #Surname#,</p>" + "<p>Please be advised that an appointment has been scheduled at: #RegionName# on  #date# at #time# as part of the learner registration process.  " + "You will be required to bring the original documents for each learner where a learner registration application has been submitted.</p>" + "<p>Should there be a change in the scheduled date, please contact the Client Liaison Officer before the visit to schedule a new date.</p>"
				+ "<p>Yours sincerely,</p>" + "<p>merSETA Client Services</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", taskCompany.getContactPerson().getFirstName());
		welcome = welcome.replaceAll("#Surname#", taskCompany.getContactPerson().getLastName());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerNameAndSurname#", clo.getFirstName() + " " + clo.getLastName());
		welcome = welcome.replaceAll("#RegionName#", regionDsec);
		welcome = welcome.replaceAll("#date#", date);
		welcome = welcome.replaceAll("#time#", time);

		GenericUtility.sendMail(user.getEmail(), "LEARNER REGISTRATION REVIEW DATE", welcome, null);
	}

	public void sendLearnerRejectionEmail(CompanyLearnersTransfer companyLearnersTransfer) throws Exception {
		CompanyLearners companyLearners = findByKey(companyLearnersTransfer.getCompanyLearners().getId());
		String subject = "LEARNER TRANSFER APPLICATION NON-APPROVAL".toUpperCase();
		String msg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + companyLearnersTransfer.getCreateUser().getFirstName() + " " + companyLearnersTransfer.getCreateUser().getLastName() + " </p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please note the request to transfer learner "+companyLearners.getUser().getFirstName() + " "+companyLearners.getUser().getLastName()+" ("+companyLearners.getRegistrationNumber()+") has been rejected for the following reason(s):" + "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + companyLearnersTransfer.getRejectionNote() + "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "A notification has been sent to the CLO to conduct a " + "Site Visit and complete an investigation report. " 
				+ "The CLO will either recommend that transfer continues, " + "or transfer does not continue" + "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours in Skills Development,</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Administration" + "</p>";
		GenericUtility.sendMail(companyLearnersTransfer.getCreateUser(), subject, msg, null);

	}

	public void sendTransferCLORectionEmail(CompanyLearnersTransfer companyLearnersTransfer) {

		List<Users> toList = new ArrayList<>();
		toList.add(companyLearnersTransfer.getCreateUser());
		toList.add(companyLearnersTransfer.getCompanyLearners().getUser());
		for (Users user : toList) {
			String subject = "Learner Transfer Application outcome".toUpperCase();
			String msg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + " </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be inform that the CLO recommend that the learner transfer application does not continue " + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA Team " + "</p>";
			GenericUtility.sendMail(user, subject, msg, null);
		}

	}

	public void sendTransferCLOApprovalEmail(CompanyLearnersTransfer companyLearnersTransfer, Users u) {
		List<Users> toList = new ArrayList<>();
		toList.add(companyLearnersTransfer.getCreateUser());
		toList.add(companyLearnersTransfer.getCompanyLearners().getUser());
		for (Users user : toList) {
			String subject = "Learner Transfer Application outcome".toUpperCase();
			String msg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">  Dear " + user.getFirstName() + " " + user.getLastName() + " </p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the CLO - "+u.getFirstName()+ " "+u.getLastName()+" has recommended that the learner transfer application for "+companyLearnersTransfer.getCompanyLearners().getUser().getFirstName()+ " "+companyLearnersTransfer.getCompanyLearners().getUser().getLastName()+ "("+companyLearnersTransfer.getCompanyLearners().getRegistrationNumber()+") may continue." + "</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For further support/clarity, please contact the Regional Office or merSETA Head Office</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours in Skills Development,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Administratoion" + "</p>";
			GenericUtility.sendMail(user, subject, msg, null);
		}

	}

	public void setFirstVisitDate(Company taskCompany, Users activeUser, Date reviewDate) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		ScheduledEvent scheduledEvent = new ScheduledEvent();
		scheduledEvent.setFromDateTime(reviewDate);
		scheduledEvent.setTargetKey(taskCompany.getId());
		scheduledEvent.setTargetClass(Company.class.getName());
		scheduledEvent.setUser(activeUser);
		dataEntities.add(scheduledEvent);

		dataEntities.add(new ScheduledEventUsers(scheduledEvent, activeUser));

		dao.createBatch(dataEntities);
		// sendReviewDateEmail(taskCompany, activeUser, reviewDate);
	}

	public void setFirstVisitDate(Company taskCompany, Users activeUser, ScheduledEvent scheduledEvent, CompanyLearners companyLearnerParent) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		scheduledEvent.setTargetKey(companyLearnerParent.getId());
		scheduledEvent.setTargetClass(Company.class.getName());
		scheduledEvent.setUser(activeUser);
		dataEntities.add(scheduledEvent);
		if (scheduledEvent.getId() == null) {
			dataEntities.add(new ScheduledEventUsers(scheduledEvent, activeUser));
			dao.createBatch(dataEntities);
		} else {
			dao.update(scheduledEvent);
		}
		taskCompany.setContactPerson(companyLearnerParent.getCreateUser());
		// sendReviewDateEmail(taskCompany, activeUser,
		// scheduledEvent.getFromDateTime());
	}

	public Users getCLO(Company company) throws Exception {
		Users cloUser = new Users();
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			cloUser = rt.getClo().getUsers();
		}
		return cloUser;
	}

	public Users getCRM(Company company) throws Exception {
		Users crmUser = new Users();
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			crmUser = rt.getCrm().getUsers();
		}
		return crmUser;
	}

	public Company findByKeyFoReview(long id) throws Exception {
		return null;// resolveContactPerson(dao.findByKey(id));
	}

	private Company resolveContactPerson(Company company) throws Exception {
		CompanyUsersService cus = new CompanyUsersService();
		company.setContactPerson(cus.findCompanyContactPerson(company.getId()));
		// resolveCompanyData(company);
		return company;
	}

	public List<Users> findRegionClinetServiceAdministrator(Company company) throws Exception {
		List<Users> list = new ArrayList<>();
		if(company != null && company.getResidentialAddress() != null){
			Roles roles = rolesService.findByKey(HAJConstants.CLIENT_SERVICE_ADMIN_ROLE_ID);
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		}
		return list;
	}
	
	public List<Users> findRegionClinetServiceAdministrator(Address address) throws Exception {
		List<Users> list = new ArrayList<>();
		if(address != null){
			Roles roles = rolesService.findByKey(HAJConstants.CLIENT_SERVICE_ADMIN_ROLE_ID);
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(address.getTown(), roles);
		}
		return list;
	}

	public List<Users> findRegionClinetServiceCoodinator(Company company) throws Exception {
		List<Users> list = new ArrayList<>();
		if(company != null && company.getResidentialAddress() != null){
			Roles roles = rolesService.findByKey(HAJConstants.CLIENT_SERVICE_COORDINATOR_ROLE_ID);
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		}
		return list;
	}

	public void prepareTransferDocumentByProcess(ConfigDocProcessEnum configDocProcess, CompanyLearnersTransfer entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {

			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}

			List<Doc> siteVisitDocs = DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.LearnerTransferSiteVisit);
			if (siteVisitDocs != null && siteVisitDocs.size() > 0) {
				List<Doc> allDocs = entity.getDocs();
				allDocs.addAll(siteVisitDocs);
				entity.setDocs(allDocs);
			}

		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	public Date calculateMiniumDateForTradeTestBasedOnLearnerNonCBMT(CompanyLearners companyLearner) throws Exception {
		Date miniumDate = new Date();
		// Not CBMT Qualification Trade - 12 months for common qualification
		GenericUtility.getStartOfDay(miniumDate = GenericUtility.addMonthsToDate(companyLearner.getCommencementDate(), 12));
		// CBMT Qualification - Weeks configured against qualification
		// GenericUtility.addWeeksToDate(companyLearner.getCommencementDate(), weeks)
		return miniumDate;
	}

	public Date calculateMinDateForCbmt(CompanyLearnersTradeTest companyLearnersTradeTest, CompanyLearners companyLearners) throws Exception {
		Date miniumDate = new Date();
//		if (companyLearners != null && companyLearners.getLastPassedTestDate() != null) {
//			miniumDate = GenericUtility.addWeeksToDate(companyLearners.getLastPassedTestDate(), companyLearnersTradeTest.getDesignatedTradeLevel().getMinweeks().intValue());
//		} else {
			miniumDate = GenericUtility.addWeeksToDate(companyLearners.getCommencementDate(), companyLearnersTradeTest.getDesignatedTradeLevel().getMinweeks().intValue());
//		} 
		return miniumDate;
	}

	public Date calculateMaxDateForCbmt(CompanyLearnersTradeTest companyLearnersTradeTest, CompanyLearners companyLearners) throws Exception {
		Date maxDate = new Date();
		
		// Version Two: check if passed a previous level of designated trade test
//		if (companyLearners != null && companyLearners.getLastPassedTestDate() != null) {
//			maxDate = GenericUtility.addWeeksToDate(companyLearners.getLastPassedTestDate(), companyLearnersTradeTest.getDesignatedTradeLevel().getMaxweeks().intValue());
//		} else {
			maxDate = GenericUtility.addWeeksToDate(companyLearners.getCommencementDate(), companyLearnersTradeTest.getDesignatedTradeLevel().getMaxweeks().intValue());
//		}
		
		// version one
//		maxDate = GenericUtility.addWeeksToDate(companyLearners.getCommencementDate(), companyLearnersTradeTest.getDesignatedTradeLevel().getMaxweeks().intValue());
		return maxDate;
	}

	public void locateCorrectDesignatedTradeLevel(CompanyLearnersTradeTest companyLearnersTradeTest, CompanyLearners companyLearners) throws Exception {
		CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
		List<CompanyLearnersTradeTest> locatePrevoulsyUndertakenTests = companyLearnersTradeTestService.findByTradeTypeCompanyLearnerQualificationAndStatus(TradeTestTypeEnum.TRADE_TEST, companyLearners, companyLearners.getQualification(), null);
		DesignatedTradeLevel tradeLevel = null;
		// locates DesignatedTradeLevel
		if (locatePrevoulsyUndertakenTests.size() == 0) {
			// locates first
			List<DesignatedTradeLevel> levelList = DesignatedTradeLevelService.instance().findByQualificationIdOrderByLevel(companyLearners.getQualification());
			if (levelList.size() > 0) {
				tradeLevel = levelList.get(0);

			}
		} else {
			if (companyLearners.getQualification() != null) {
				List<DesignatedTradeLevel> levelList = DesignatedTradeLevelService.instance().findByQualificationIdNextLevel(companyLearners.getQualification(), locatePrevoulsyUndertakenTests.get(0).getDesignatedTradeLevel().getLevel());
				if (levelList.size() > 0) {
					tradeLevel = levelList.get(0);

				}
			} else if (companyLearnersTradeTest.getQualification() != null) {
				List<DesignatedTradeLevel> levelList = DesignatedTradeLevelService.instance().findByQualificationIdNextLevel(companyLearnersTradeTest.getQualification(), locatePrevoulsyUndertakenTests.get(0).getDesignatedTradeLevel().getLevel());
				if (levelList.size() > 0) {
					tradeLevel = levelList.get(0);
				}
			}
		}
		if (tradeLevel != null) {
			List<DesignatedTradeLevel> checklist = DesignatedTradeLevelService.instance().findByQualificationIdNextLevel(tradeLevel.getQualification(), tradeLevel.getLevel());
			if (checklist.size() == 0) {
				companyLearnersTradeTest.setDesignatedTradeLevel(tradeLevel);
				companyLearnersTradeTest.setFinalCbmtQualification(true);
			} else {
				companyLearnersTradeTest.setDesignatedTradeLevel(tradeLevel);
				companyLearnersTradeTest.setFinalCbmtQualification(false);
			}
		} else if (locatePrevoulsyUndertakenTests.size() > 0) {
			CompanyLearnersTradeTest prevCompanyLearnersTradeTest = locatePrevoulsyUndertakenTests.get(0);
			companyLearnersTradeTest.setDesignatedTradeLevel(prevCompanyLearnersTradeTest.getDesignatedTradeLevel());
			companyLearnersTradeTest.setFinalCbmtQualification(true);
		}
	}
	
	public void validateIfLearnerCanApplyLostTime(CompanyLearners companyLearners) throws Exception {
		// should already have been checked if a designated trade qualification
		if (designatedTradeLevelService == null) {
			designatedTradeLevelService = new DesignatedTradeLevelService();
		}
		boolean noOrder = designatedTradeLevelService.determainQualificationDesignatedTradeAndNoCompletionOrder(companyLearners.getQualification(), 0l);
	    if (noOrder) {
	    	int totalConfigured = 0;
	    	int totalCompleted = 0;
	    	List<DesignatedTradeLevel> designatedTradeLevelList = designatedTradeLevelService.findByQualificationIdOrderByLevel(companyLearners.getQualification());
	    	totalConfigured = designatedTradeLevelList.size();
			for (DesignatedTradeLevel tradeLevel : designatedTradeLevelList) {
				int completedMoudle = CompanyLearnersTradeTestService.instance().countTradeTestByCompanyLearnerDesignatedTradeAndCompleted(TradeTestTypeEnum.TRADE_TEST, companyLearners, tradeLevel, CompetenceEnum.Competent, ApprovalEnum.Completed);
				if (completedMoudle > 0) {
					totalCompleted++;
				}
			}
			Double percentageCompleted = 0.0;
			if (totalConfigured != 0 && totalCompleted != 0) {
				percentageCompleted = (double) (totalCompleted / totalConfigured);
				percentageCompleted = percentageCompleted * 100;
			}
			
			if (percentageCompleted < 50.00) {
				throw new Exception("Unable to apply. User must complete a mininum of 50% of total desiganted trade levels. Contact support!");
			}
	    } else {
	    	DesignatedTradeLevel dtl = locateCorrectDesignatedTradeLevelReturnTradeLevel(companyLearners);
	    	if (dtl == null) {
				throw new Exception("Unable to locate current designated trade level assigned to learner. Contact Support!");
			} else {
				if (dtl.getExtension() == null || !dtl.getExtension()) {
					throw new Exception("Unable to create lost time request at current level of designated trade. Contact Support!");
				}
			}
		}
		
		
	}
	
	public DesignatedTradeLevel locateCorrectDesignatedTradeLevelReturnTradeLevel(CompanyLearners companyLearners) throws Exception {
		CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
		List<CompanyLearnersTradeTest> locatePrevoulsyUndertakenTests = companyLearnersTradeTestService.findByTradeTypeCompanyLearnerQualificationAndStatus(TradeTestTypeEnum.TRADE_TEST, companyLearners, companyLearners.getQualification(), null);
		DesignatedTradeLevel tradeLevel = null;
		// locates DesignatedTradeLevel
		if (locatePrevoulsyUndertakenTests.size() == 0) {
			// locates first
			List<DesignatedTradeLevel> levelList = DesignatedTradeLevelService.instance().findByQualificationIdOrderByLevel(companyLearners.getQualification());
			if (levelList.size() > 0) {
				tradeLevel = levelList.get(0);
			}
		} else {
			if (companyLearners.getQualification() != null) {
				List<DesignatedTradeLevel> levelList = DesignatedTradeLevelService.instance().findByQualificationIdNextLevel(companyLearners.getQualification(), locatePrevoulsyUndertakenTests.get(0).getDesignatedTradeLevel().getLevel());
				if (levelList.size() > 0) {
					tradeLevel = levelList.get(0);

				}
			} 
		}
		if (tradeLevel != null) {
			return tradeLevel;
		} else if (locatePrevoulsyUndertakenTests.size() > 0) {
			CompanyLearnersTradeTest prevCompanyLearnersTradeTest = locatePrevoulsyUndertakenTests.get(0);
			return prevCompanyLearnersTradeTest.getDesignatedTradeLevel();
		} else {
			return null;
		}
	}
	
	public void locateCorrectDesignatedTradeLevelNoOrder(CompanyLearnersTradeTest companyLearnersTradeTest, CompanyLearners companyLearners) throws Exception {
		CompanyLearnersTradeTestService companyLearnersTradeTestService = new CompanyLearnersTradeTestService();
		List<CompanyLearnersTradeTest> locatePrevoulsyUndertakenTests = companyLearnersTradeTestService.findByTradeTypeCompanyLearnerQualificationAndStatus(TradeTestTypeEnum.TRADE_TEST, companyLearners, companyLearners.getQualification(), null);
		DesignatedTradeLevel tradeLevel = null;
		if (locatePrevoulsyUndertakenTests.size() == 0) {
			// locates first
			List<DesignatedTradeLevel> levelList = DesignatedTradeLevelService.instance().findByQualificationIdOrderByLevel(companyLearners.getQualification());
			if (levelList.size() > 0) {
				tradeLevel = levelList.get(0);
			}
		}
		if (tradeLevel != null) {
			List<DesignatedTradeLevel> checklist = DesignatedTradeLevelService.instance().findByQualificationIdNextLevel(tradeLevel.getQualification(), tradeLevel.getLevel());
			if (checklist.size() == 0) {
				companyLearnersTradeTest.setDesignatedTradeLevel(tradeLevel);
				companyLearnersTradeTest.setFinalCbmtQualification(true);
			} else {
				companyLearnersTradeTest.setDesignatedTradeLevel(tradeLevel);
				companyLearnersTradeTest.setFinalCbmtQualification(false);
			}
		} else if (locatePrevoulsyUndertakenTests.size() > 0) {
			CompanyLearnersTradeTest prevCompanyLearnersTradeTest = locatePrevoulsyUndertakenTests.get(0);
			companyLearnersTradeTest.setDesignatedTradeLevel(prevCompanyLearnersTradeTest.getDesignatedTradeLevel());
			companyLearnersTradeTest.setFinalCbmtQualification(true);
		}
	}

	public List<CompanyLearners> findByEmployer(long companyID) throws Exception {
		return checkProgress(dao.findByEmployer(companyID));
	}

	private List<InterventionBean> populateList(InterventionType interventionType) {

		List<InterventionBean> list = new ArrayList<>();
		InterventionBean bean = new InterventionBean();
		bean.setName("Apprenticeship");
		bean.setSelected(false);
		list.add(bean);

		bean = new InterventionBean();
		bean.setName("Learnership");
		bean.setSelected(false);
		list.add(bean);

		bean = new InterventionBean();
		bean.setName("Internship for the \"N\" Diploma");
		bean.setSelected(false);
		list.add(bean);

		bean = new InterventionBean();
		bean.setName("Candidacy");
		bean.setSelected(false);
		list.add(bean);

		bean = new InterventionBean();
		bean.setName("Student internship: Category A");
		bean.setSelected(false);
		list.add(bean);

		bean = new InterventionBean();
		bean.setName("Student internship: Category B");
		bean.setSelected(false);
		list.add(bean);

		bean = new InterventionBean();
		bean.setName("Student internship: Category C");
		bean.setSelected(false);
		list.add(bean);

		bean = new InterventionBean();
		bean.setName("Student internship");
		bean.setSelected(false);
		list.add(bean);

		bean = new InterventionBean();
		bean.setName("Graduate internship");
		bean.setSelected(false);
		list.add(bean);

		for (InterventionBean interventionBean : list) {
			if (interventionType != null && interventionType.getGovDescription() != null) {
				if (interventionType.getGovDescription().matches(interventionBean.getName())) {
					interventionBean.setSelected(true);
				}
			}
		}

		return list;
	}

	/*
	 * Find all CompanyLearners that expires on the current date
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findTodayExpiredContracts() throws Exception {
		return dao.findTodayExpiredContracts();
	}

	/**
	 * System to provide a list of agreements/contracts that have expired System to
	 * send notifications to Learner, CLO and the Provider/ Workplace to notify on
	 * expiration/ termination status of the agreement
	 */
	public void checkExpiredContactsAndSendNotification() throws Exception {
		List<CompanyLearners> expiredList = findTodayExpiredContracts();
		if (expiredList == null || expiredList.size() == 0) {
			throw new Exception("No agreements/contracts that have expired found");
		}
		if (expiredList != null && expiredList.size() > 0) {
			for (CompanyLearners cl : expiredList) {
				try {
					cl.setLearnerStatus(LearnerStatusEnum.Terminated);
					cl.setSystemTermination(true);
					update(cl);
					List<Users> toUserList = getCompanyLeanderUsers(cl);
					for (Users user : toUserList) {
						sendExpiredContractsNotifications(cl, user);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

	public List<Users> getCompanyLeanderUsers(CompanyLearners cl) throws Exception {
		List<Users> toUserList = new ArrayList();
		// Learner
		toUserList.add(cl.getUser());

		// Provider
		if (cl.getCompany() != null) {
			List<Users> providerList = companyUsersService.findUsersByCompanyType(cl.getCompany().getId(), ConfigDocProcessEnum.TP);
			if (providerList != null && providerList.size() > 0) {
				toUserList.addAll(providerList);
			}
		}

		// Workplace
		if (cl.getEmployer() != null) {
			Users workplaceUser = SDFCompanyService.instance().findPrimarySDF(cl.getEmployer()).getSdf();
			if (workplaceUser != null && workplaceUser.getId() != null) {
				toUserList.add(workplaceUser);
			}
		}

		// Host Company
		if (cl.getHostCompany() != null) {
			Users hostCompUser = SDFCompanyService.instance().findPrimarySDF(cl.getHostCompany()).getSdf();
			if (hostCompUser != null && hostCompUser.getId() != null) {
				toUserList.add(hostCompUser);
			}
		}

		// CLO
		if (cl.getCompany() != null) {
			Users clo = getCLO(cl.getCompany());
			if (clo != null && clo.getId() != null) {
				toUserList.add(clo);
			}
		}

		super.removeDuplicatesFromList(toUserList);

		return toUserList;
	}

	public void sendExpiredContractsNotifications(CompanyLearners companyLearners, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();

		/*
		 * params.put("company_id",
		 * companyLearnersTransfer.getTransferToCompany().getId());
		 * params.put("learners_id", learner.getId());
		 */

		String toEmail = user.getEmail();
		JasperService.addLogo(params);

		// byte[] bites =
		// JasperService.instance().convertJasperReportToByte("LPM-AD-002-Addendum-ToAmendLinkBetweenEmployerAndLearner.jasper",
		// params);

		String subject = "Expired agreements/contracts ".toUpperCase();
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + " Be inform that the agreements/contracts for " + companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " " + " Has been terminated" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		GenericUtility.sendMail(toEmail, subject, mssg, null);

	}

	public void sendRejectEmail(CompanyLearners companyLearners, List<RejectReasons> rejectReasons, Users u) throws Exception {
		Company company = companyService.findByKey(companyLearners.getCompany().getId());
		String accreditationNumber = "N/A";
		if (company != null && company.getAccreditationNumber() != null) {
			accreditationNumber = company.getAccreditationNumber();
		}
		String rsaIdPassport = anIDNumber(companyLearners.getUser());
		String QualificationString = getCompanyLearnerStringQualification(companyLearners);
		//Qualification qualification = getCompanyLearnerQualification(companyLearners);
		//QualificationString = "(" + qualification.getCodeString() + ") " + qualification.getDescription();
		String reasons = convertToHTML(rejectReasons);

		String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FirstName# #Surname#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "#LearnerFirstName# #LearnerSurname# (#rsaIdPassport#) " + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA hereby confirms that the application documents for the above learner have been not been registered accordingly for the following reason(s):</p>" + reasons
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please attend to the issues and re-submit the application. Should you require any assistance or further information, kindly contact the Client Liaison Officer at the " + getRegionString(company) + " Office</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours faithfully,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Client Services Administrator</p>" + "<br/>";
		welcome = welcome.replaceAll("#FirstName#", companyLearners.getCreateUser().getFirstName());
		welcome = welcome.replaceAll("#Surname#", companyLearners.getCreateUser().getLastName());
		welcome = welcome.replaceAll("#LearnerFirstName#", companyLearners.getUser().getFirstName());
		welcome = welcome.replaceAll("#LearnerSurname#", companyLearners.getUser().getLastName());
		welcome = welcome.replaceAll("#rsaIdPassport#", rsaIdPassport);
		welcome = welcome.replaceAll("#QualificationString#", QualificationString);
		welcome = welcome.replaceAll("#CompanyName#", company.getCompanyName());
		welcome = welcome.replaceAll("#AccreditationNumber#", accreditationNumber);

		GenericUtility.sendMail(u.getEmail(), "merSETA LEARNER REGISTRATION APPLICATION", welcome, null);
	}

	public void sendRejectEmailToTheLearner(CompanyLearners companyLearners, List<RejectReasons> rejectReasons) throws Exception {
		Company company = companyService.findByKey(companyLearners.getCompany().getId());
		String accreditationNumber = "N/A";
		if (company != null && company.getAccreditationNumber() != null) {
			accreditationNumber = company.getAccreditationNumber();
		}
		String rsaIdPassport = anIDNumber(companyLearners.getUser());
		String QualificationString = getCompanyLearnerStringQualification(companyLearners);
		String reasons = convertToHTML(rejectReasons);
		String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #LearnerFirstName# #LearnerSurname#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly be advised that the learner registration application for #LearnerFirstName# #LearnerSurname# (#rsaIdPassport#) for #QualificationString#. was not been approved for the following reason(s):</p>" + reasons + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please attend to the issues and re-submit the application. Should you require any assistance or further information, kindly contact the Client Liaison Officer at the " + getRegionString(company) + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Client Services</p>" + "<br/>";
		welcome = welcome.replaceAll("#FirstName#", companyLearners.getCreateUser().getFirstName());
		welcome = welcome.replaceAll("#Surname#", companyLearners.getCreateUser().getLastName());
		welcome = welcome.replaceAll("#LearnerFirstName#", companyLearners.getUser().getFirstName());
		welcome = welcome.replaceAll("#LearnerSurname#", companyLearners.getUser().getLastName());
		welcome = welcome.replaceAll("#rsaIdPassport#", rsaIdPassport);
		welcome = welcome.replaceAll("#QualificationString#", QualificationString);
		welcome = welcome.replaceAll("#CompanyName#", company.getCompanyName());
		welcome = welcome.replaceAll("#AccreditationNumber#", accreditationNumber);

		GenericUtility.sendMail(companyLearners.getUser().getEmail(), "merSETA Client Services", welcome, null);
	}

	public void sendApprovalEmail(CompanyLearners companyLearners, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		// Qualification qualification =
		// getCompanyLearnerQualification(companyLearners);
		String toEmail = user.getEmail();
		JasperService.addLogo(params);

		// String regionDescription = getRegionString(companyLearners.getCompany());

		String rsaIdPassport = anIDNumber(companyLearners.getUser());

		String subject = "LEARNER REGISTRATION APPLICATION OUTCOME";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + companyLearners.getCreateUser().getFirstName() + " " + companyLearners.getCreateUser().getLastName() + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Learner:  " + companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " (" + rsaIdPassport + ")" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Registration Number: "
				+ companyLearners.getRegistrationNumber() + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA hereby confirms that the application documents for the above learner have been registered accordingly." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the learner registration documents are available under the learner's profile." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "You are requested to note that if the operations of the business changes, or if it is desired to transfer the agreement to another employer, the merSETA must be notified beforehand." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Client Services Administrator </p>";

		GenericUtility.sendMail(toEmail, subject, mssg, null);

	}

	public void sendApprovalEmailToTheLearner(CompanyLearners companyLearners) throws Exception {
		String regionDescription = getRegionString(companyLearners.getCompany());

		String aprovalDate = "";

		Map<String, Object> params = new HashMap<String, Object>();

		String toEmail = companyLearners.getCreateUser().getEmail();
		JasperService.addLogo(params);

		String subject = "LEARNER REGISTRATION APPLICATION OUTCOME";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + " Kindly be advised that your learner registration application (" + getCompanyLearnerStringQualification(companyLearners) + " " + " Has been Approved" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Registration Date: " + aprovalDate + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Learner/Agreement Number: " + companyLearners.getRegistrationNumber() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Should you require any assistance or further information, kindly contact the Client Liaison Officer at " + regionDescription + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Client Services </p>";

		GenericUtility.sendMail(toEmail, subject, mssg, null);
	}

	public void sendLPMFM007Email(Users createUser, Users learner, Company company, Company trainingProvider, boolean requireGaurdian, CompanyLearners companylearners, List<UsersLanguage> usersLanguages) throws Exception {
		String leadCompanySETA = "MERSETA - Manufacturing, Engineering and Related Services Education and Training Authority";
		MunicipalityService municipalityService = new MunicipalityService();
		// Setting Residential Address District
		Address residentialAddress = learner.getResidentialAddress();
		if (residentialAddress.getMunicipality().getDistrict() != null) {
			residentialAddress.getMunicipality().setDistrict(municipalityService.findByKey(residentialAddress.getMunicipality().getDistrict().getId()));
		} else {
			residentialAddress.getMunicipality().setDistrict(null);
		}
		learner.setResidentialAddress(residentialAddress);
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		CompanyService companyService = new CompanyService();

		Users leadCompanyContactPerson = new Users();
		SDFCompany sDFCompany = SDFCompanyService.instance().findPrimarySDF(company);
		if (sDFCompany != null) {
			leadCompanyContactPerson = sDFCompany.getSdf();
		} else {
			leadCompanyContactPerson = companyUsersService.findCompanyContactPerson(company.getId());
		}

		Users leadCompanyContactSupervisor = companyUsersService.findCompanyContactPerson(company.getId());// To be fixed

		Company hostEmployer = new Company();
		if (companylearners.getHostCompany() != null) {
			hostEmployer = companyService.findByKey(companylearners.getHostCompany().getId());
		}

		Users hostCompanyContactPerson = new Users();
		if (hostEmployer != null && hostEmployer.getId() != null) {
			hostCompanyContactPerson = companyUsersService.findCompanyContactPerson(hostEmployer.getId());
			hostEmployer.setSetaRegisteredAt(leadCompanySETA);
		}
		company.setSetaRegisteredAt(leadCompanySETA);
		trainingProvider.setSetaRegisteredAt(leadCompanySETA);

		Users skillDevelopmentProviderContactPerson = companyUsersService.findCompanyContactPerson(trainingProvider.getId());
		// Setting Residential Address District
		if (trainingProvider.getResidentialAddress() != null) {
			trainingProvider.setResidentialAddress(AddressService.instance().findByKeyJoinDistric(trainingProvider.getResidentialAddress().getId()));
		} else {
			trainingProvider.setRegisteredAddress(new Address());
		}
		if (trainingProvider.getPostalAddress() != null) {
			trainingProvider.setPostalAddress(AddressService.instance().findByKeyJoinDistric(trainingProvider.getPostalAddress().getId()));
		} else {
			trainingProvider.setPostalAddress(new Address());
		}
		// Checking workplace approval for lead company
		boolean isLeadComWorkplaceApproved = false;
		WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
		if (workPlaceApprovalService.findByCompanyCount(company) > 0) {
			isLeadComWorkplaceApproved = true;
		}
		// Checking workplace approval for lead company
		boolean isHostCompWorkplaceApproved = false;
		if (hostEmployer != null && hostEmployer.getId() != null) {
			if (workPlaceApprovalService.findByCompanyCount(hostEmployer) > 0) {
				isHostCompWorkplaceApproved = true;
			}
		}

		if (company.getCompanyStatus() == CompanyStatusEnum.NonMersetaCompany) {
			// Set ETQA des as leadCompanySETA
			if (company.getEtqa() != null) {
				leadCompanySETA = company.getEtqa().getDescription();
			} else {
				leadCompanySETA = "N/A";
			}
		}else {
			leadCompanySETA = "MERSETA - Manufacturing, Engineering and Related Services Education and Training Authority";
		}
		
		String training_intervention = companylearners.getInterventionType().getDescription();
		boolean funded_by_merseta = companylearners.getMersetaFunded().getYesNoBoolean();
		boolean isHavingHostEmployer = true; // To be fixed
		boolean liveInUrban = false;
		if (learner.getUrbanRuralEnum() != null && learner.getUrbanRuralEnum().getFriendlyName().equalsIgnoreCase("Urban")) {
			liveInUrban = true;
		}

		/// List<CompanyLearners>companylearnersList =
		/// findByUser(companylearners.getUser());
		// List<EmploymentBean> list = populateEmploymentBeanList(companylearnersList);
		List<CompanyLearners> companylearnersList = new ArrayList<>();
		companylearnersList.add(companylearners);

		boolean physical_addresses = true;
		boolean commencement_completion_date = true;
		boolean sa_citizen = true;
		boolean skills_type = true;
		boolean application_correctly_completed = true;

		String howLongEmployed = "";
		if (learner.getEmploymentStatus() == EmploymentStatusEnum.Employed) {
			int months = 0;
			if (companylearners.getEmploymentContractStartDate() != null && companylearners.getEmploymentContractEndDate() != null) {
				if (!companylearners.getEmploymentContractStartDate().after(companylearners.getEmploymentContractEndDate())) {
					months = GenericUtility.getMonthsBetweenDates(companylearners.getEmploymentContractStartDate(), companylearners.getEmploymentContractEndDate());
				}
			}
			howLongEmployed = months + " Months(s)";
		}
		String hostEmployerWorkplace = "N/A";
		String leadEmployerWorkplace = "N/A";

		params.put("hostEmployerWorkplace", hostEmployerWorkplace);
		params.put("leadEmployerWorkplace", leadEmployerWorkplace);
		params.put("companylearners", companylearners);
		params.put("application_correctly_completed", application_correctly_completed);
		params.put("skills_type", skills_type);
		params.put("sa_citizen", sa_citizen);
		params.put("commencement_completion_date", commencement_completion_date);
		params.put("physical_addresses", physical_addresses);
		params.put("training_intervention", training_intervention);
		params.put("funded_by_merseta", funded_by_merseta);
		params.put("isHavingHostEmployer", isHavingHostEmployer);
		params.put("isHavingHostEmployer", isHavingHostEmployer);
		params.put("live_in_urban", liveInUrban);
		params.put("learner", learner);
		params.put("leadCompany", company);
		params.put("leadCompanySETA", leadCompanySETA);
		params.put("isLeadComWorkplaceApproved", isLeadComWorkplaceApproved);
		params.put("leadCompanyContactPerson", leadCompanyContactPerson);
		params.put("leadCompanyContactSupervisor", leadCompanyContactSupervisor);
		params.put("hostEmployer", hostEmployer);
		params.put("isHostCompWorkplaceApproved", isHostCompWorkplaceApproved);
		params.put("hostCompanyContactPerson", hostCompanyContactPerson);
		params.put("skillDevelopmentProvider", trainingProvider);
		params.put("skillDevelopmentProviderContactPerson", skillDevelopmentProviderContactPerson);
		params.put("isMinor", requireGaurdian);
		params.put("how_long", howLongEmployed);
		// params.put("list", new JRBeanCollectionDataSource(list));

		if (requireGaurdian) {
			Users legalGaurdian = learner.getLegalGaurdian();
			params.put("parentGuadian", legalGaurdian);
		}
		ArrayList<SkillsProgrammeSkillsSetBean> skillsProgrammeSkillsSetBeanList = new ArrayList<>();
		ArrayList<UnitStandardBean> unitStandardBeanList = new ArrayList<>();

		// *******************To be selected from DB************************
		if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
			SkillsProgrammeSkillsSetBean sks = new SkillsProgrammeSkillsSetBean(companylearners.getSkillsSet().getDescription(), companylearners.getSkillsSet().getCode(), companylearners.getCommencementDate(), companylearners.getCompletionDate());
			skillsProgrammeSkillsSetBeanList.add(sks);
		}
		if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
			SkillsProgrammeSkillsSetBean sks = new SkillsProgrammeSkillsSetBean(companylearners.getSkillsProgram().getDescription(), companylearners.getSkillsProgram().getCode(), companylearners.getCommencementDate(), companylearners.getCompletionDate());
			skillsProgrammeSkillsSetBeanList.add(sks);
		}

		if (companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			UnitStandardBean usb = new UnitStandardBean(companylearners.getUnitStandard().getTitle(), companylearners.getUnitStandard().getCode() + "", companylearners.getCommencementDate(), companylearners.getCompletionDate());
			unitStandardBeanList.add(usb);
		}

		// *******************END To be selected from DB********************

		params.put("usersLanguages", new JRBeanCollectionDataSource(usersLanguages));
		params.put("companylearnersList", new JRBeanCollectionDataSource(companylearnersList));
		params.put("SkillsProgrammeSkillsSetBeanDataSource", new JRBeanCollectionDataSource(skillsProgrammeSkillsSetBeanList));
		params.put("UnitStandardBeanDataSource", new JRBeanCollectionDataSource(unitStandardBeanList));

		String toEmail = createUser.getEmail();

		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-007(A)-ATRAMITradeTestApplicationForm.jasper", params);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("ATRAMITradeTestApplicationForm.pdf");
		attachmentBeans.add(beanAttachment);

		String subject = "LEARNER REGISTRATION APPLICATION";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + createUser.getFirstName() + " " + createUser.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Attached, please find Skills Programme Learner Registration Form" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";

		// String fileName = learner.getFirstName() + "_" + learner.getLastName() +
		// "_Reg_Form.pdf";

		// GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites,
		// fileName, "pdf", null);

		GenericUtility.sendMailWithAttachementTempWithLog(toEmail, subject, mssg, attachmentBeans, null);

	}

	public Qualification getCompanyLearnerQualification(CompanyLearners companylearners) throws Exception {
		Qualification qualification = null;
		if (companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			qualification = companylearners.getQualification();
		} else if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
			qualification = companylearners.getSkillsSet().getQualification();
		} else if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
			qualification = companylearners.getSkillsProgram().getQualification();
		} else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			qualification = companylearners.getLearnership().getQualification();
		} else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
			qualification = companylearners.getQualification();
		}else if (companylearners.getQualification() != null && companylearners.getInterventionType().getId() != HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			qualification = companylearners.getQualification();
		} else if (companylearners.getQualification() != null && companylearners.getInterventionType().getId() != HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
			qualification = new Qualification();
			qualification.setCode(0000);
			qualification.setDescription(companylearners.getNonCredidBearingDescription());
		}
		return qualification;
	}
	
	public Qualification getCompanyLearnerQualification(CompanyLearnersChange companyLearnersChange) throws Exception {
		Qualification qualification = null;
		if (companyLearnersChange.getQualification() != null) {
			qualification = companyLearnersChange.getQualification();
		} else if (companyLearnersChange.getSkillsSet() != null) {
			qualification = companyLearnersChange.getSkillsSet().getQualification();
		} else if (companyLearnersChange.getSkillsProgram() != null) {
			qualification = companyLearnersChange.getSkillsProgram().getQualification();
		} else if (companyLearnersChange.getLearnership() != null) {
			qualification = companyLearnersChange.getLearnership().getQualification();
		}else if (companyLearnersChange.getUnitStandard() != null) {
			qualification = companyLearnersChange.getUnitStandard().getQualification();
		}
		return qualification;
	}
	
	public boolean checkIfRequiresWPA(CompanyLearners companylearners) throws Exception {
		boolean required = false;
		Qualification qualification = getCompanyLearnerQualification(companylearners);
		if(qualification!= null && qualification.getId() != null && qualification.getWorkplaceApprovalRequired() != null && qualification.getWorkplaceApprovalRequired()) {
			required = true;
		}
		return required;
	}
	
	private boolean checkIfShouldDisplayTP(InterventionType interventiontype) {
		boolean showTrainingProvider = false;
		if (SKILLS_PROGRAM_LIST.contains(interventiontype.getId()) || SKILLS_SET_LIST.contains(interventiontype.getId()) || interventiontype.getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE || interventiontype.getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			showTrainingProvider = true;
		}
		return showTrainingProvider;
	}

	public String getCompanyLearnerStringQualification(CompanyLearners companylearners) throws Exception {
		// CompanyQualificationsService companyQualificationsService = new
		// CompanyQualificationsService();
		String fullDescription = "";
		if (companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			if(companylearners.getUnitStandard() != null && companylearners.getUnitStandard().getQualification()  != null && companylearners.getUnitStandard().getQualification().getCode() != null) {
				fullDescription = "(" + companylearners.getUnitStandard().getCode() + ")" ;
				if(companylearners.getUnitStandard().getQualification() != null) {
					fullDescription = fullDescription + companylearners.getUnitStandard().getQualification().getDescription();
				}
			}
		} else if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
			fullDescription = "(" + companylearners.getSkillsSet().getProgrammeID() + ")" + companylearners.getSkillsSet().getDescription();
		} else if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
			fullDescription = "(" + companylearners.getSkillsProgram().getProgrammeID() + ")" + companylearners.getSkillsProgram().getDescription();
		} else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			fullDescription = "(" + companylearners.getLearnership().getQualification().getCode() + ")" + companylearners.getLearnership().getQualification().getDescription();
		} else if (companylearners.getInterventionType().getId() == HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
			fullDescription = companylearners.getNonCredidBearingDescription();
		} else if (companylearners.getQualification() != null) {
			fullDescription = "(" + companylearners.getQualification().getCodeString() + ")" + companylearners.getQualification().getDescription();
		}
		return fullDescription;
	}
	
	public String getCompanyLearnerStringQualification(CompanyLearnersChange companylearners) throws Exception {
		String fullDescription = "";
		if(companylearners.getUnitStandard() != null ) {
				fullDescription = "(" + companylearners.getUnitStandard().getCode() + ")" ;
				if(companylearners.getUnitStandard().getQualification() != null) {
					fullDescription = fullDescription + companylearners.getUnitStandard().getQualification().getDescription();
				}
		}else if (companylearners.getSkillsSet() != null) {
			fullDescription = "(" + companylearners.getSkillsSet().getProgrammeID() + ")" + companylearners.getSkillsSet().getDescription();
		}else if (companylearners.getSkillsProgram() != null) {
			fullDescription = "(" + companylearners.getSkillsProgram().getProgrammeID() + ")" + companylearners.getSkillsProgram().getDescription();
		}else if (companylearners.getLearnership() != null) {
			fullDescription = "(" + companylearners.getLearnership().getQualification().getCode() + ")" + companylearners.getLearnership().getQualification().getDescription();
		}else if (companylearners.getQualification() != null) {
			fullDescription = "(" + companylearners.getQualification().getCodeString() + ")" + companylearners.getQualification().getDescription();
		}
		return fullDescription;
	}
	
	public String findStringQualificationDescription(CompanyLearners companylearners) throws Exception {
		String fullDescription = "";
		if (companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			fullDescription = fullDescription + companylearners.getUnitStandard().getTitle();
		} else if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
			fullDescription = companylearners.getSkillsSet().getDescription();
		} else if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
			fullDescription = companylearners.getSkillsProgram().getDescription();
		} else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			fullDescription = companylearners.getLearnership().getQualification().getDescription();
		}else if (companylearners.getQualification() != null) {
			fullDescription = companylearners.getQualification().getDescription();
		}
		return fullDescription;
	}
	
	public String findStringQualificationCode(CompanyLearners companylearners) throws Exception {
		String fullDescription = "";
		if (companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			fullDescription = companylearners.getUnitStandard().getCode()+"" ;		
		} else if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
			fullDescription =  companylearners.getSkillsSet().getCode() + "";
		} else if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
			fullDescription = companylearners.getSkillsProgram().getCode() + "" ;
		} else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			fullDescription = companylearners.getLearnership().getQualification().getCode() + "";
		}else if (companylearners.getQualification() != null) {
			fullDescription = companylearners.getQualification().getCode() + "";
		}
		return fullDescription;
	}

	private String pupulateStringUserDisability(List<UsersDisability> usersDisabilityList) {
		String reason = "";
		if (usersDisabilityList.size() > 0) {
			int size = 0;
			for (UsersDisability child : usersDisabilityList) {
				size += 1;
				if (child.getDisabilityStatus() != null) {
					reason += child.getDisabilityStatus().getDescription();
				}

				if (size == usersDisabilityList.size()) {
					reason += ".";
				} else {
					reason += ", ";
				}
			}
		}
		return reason;
	}

	public String getRegionString(Company company) throws Exception {
		String regionDescription = "";
		RegionTown rt = new RegionTown();
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
		}
		if (regionService == null) {
			regionService = new RegionService();
		}

		Region r = new Region();
		if (rt.getRegion() != null) {
			r = regionService.findByKey(rt.getRegion().getId());
			if (r != null) {
				regionDescription = r.getDescription();
			}
		}
		return regionDescription;
	}

	public void sendLPMTP024Letter(Users user, Company company) throws Exception {
		// LPM-TP-024-ConfirmationOfLearnerRegistration
		JasperService js = new JasperService();
		String title = "";
		String clientServiceAdminName = user.getFirstName() + " " + user.getLastName();
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		List<CompanyLearners> list = findByEmployer(company.getId());

		params.put("company", company);
		params.put("address", company.getResidentialAddress());
		params.put("user", user);
		params.put("title", title);
		params.put("clientServiceAdminName", clientServiceAdminName);
		params.put("learners", new JRBeanCollectionDataSource(list));

		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();

		/*
		 * byte[] bites = JasperService.instance().convertJasperReportToByte(
		 * "LPM-TP-024-ConfirmationOfLearnerRegistration.jasper", params);
		 * 
		 * beanAttachment.setExt("pdf"); beanAttachment.setFile(bites);
		 * beanAttachment.setFilename("ConfirmationOfLearnerRegistration.pdf");
		 * attachmentBeans.add(beanAttachment);
		 */

		js.createReportFromDBtoServletOutputStream("LPM-TP-024-ConfirmationOfLearnerRegistration.jasper", params, "ConfirmationOfLearnerRegistration.pdf");
	}

	private void sendApprovalFileManagementEmail(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		Users clo = tasks.getCreateUser();
		String rsaIdPassport = anIDNumber(companyLearners.getUser());

		String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #CLOFirstName# #CLOSurname#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Learner: #LearnerFirstName# #LearnerSurname# (#rsaIdPassport#) </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Registration Number: #RegistrationNumber# </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Box Number: #BoxNumber# </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Central Administration hereby confirms that the application documents for the above learner have been received and are in order." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "#NameAndSurnameOfAdministrator#</p>" + "<br/>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Central Admin: Administrator</p>" + "<br/>";

		welcome = welcome.replaceAll("#CLOFirstName#", companyLearners.getCreateUser().getFirstName());
		welcome = welcome.replaceAll("#CLOSurname#", companyLearners.getCreateUser().getLastName());

		welcome = welcome.replaceAll("#FirstName#", companyLearners.getCreateUser().getFirstName());
		welcome = welcome.replaceAll("#Surname#", companyLearners.getCreateUser().getLastName());
		welcome = welcome.replaceAll("#LearnerFirstName#", companyLearners.getUser().getFirstName());
		welcome = welcome.replaceAll("#LearnerSurname#", companyLearners.getUser().getLastName());
		welcome = welcome.replaceAll("#rsaIdPassport#", rsaIdPassport);
		welcome = welcome.replaceAll("#CompanyName#", companyLearners.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#RegistrationNumber#", companyLearners.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#BoxNumber#", companyLearners.getDocumentBoxID());
		welcome = welcome.replaceAll("#NameAndSurnameOfAdministrator#", user.getFirstName() + " " + user.getLastName());

		GenericUtility.sendMail(clo.getEmail(), "Learner File: Compliant", welcome, null);
	}

	private void sendRejectFileManagementEmail(CompanyLearners companyLearners, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		String rsaIdPassport = anIDNumber(companyLearners.getUser());
		String reasons = convertToHTML(rejectReasons);
		Users clo = tasks.getCreateUser();

		String welcome = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #CLOFirstName# #CLOSurname#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Learner: #LearnerFirstName# #LearnerSurname# (#rsaIdPassport#) </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Registration Number: #RegistrationNumber# </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Box Number: #BoxNumber# </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Central Administration hereby confirms that the application documents for the above learner have been received and are not compliant for the following reason(s)::</p>" + reasons + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly address the issue as a matter of priority.</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "#NameAndSurnameOfAdministrator#</p>" + "<br/>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Central Admin: Administrator</p>" + "<br/>";

		welcome = welcome.replaceAll("#CLOFirstName#", companyLearners.getCreateUser().getFirstName());
		welcome = welcome.replaceAll("#CLOSurname#", companyLearners.getCreateUser().getLastName());

		welcome = welcome.replaceAll("#FirstName#", companyLearners.getCreateUser().getFirstName());
		welcome = welcome.replaceAll("#Surname#", companyLearners.getCreateUser().getLastName());
		welcome = welcome.replaceAll("#LearnerFirstName#", companyLearners.getUser().getFirstName());
		welcome = welcome.replaceAll("#LearnerSurname#", companyLearners.getUser().getLastName());
		welcome = welcome.replaceAll("#rsaIdPassport#", rsaIdPassport);
		welcome = welcome.replaceAll("#CompanyName#", companyLearners.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#RegistrationNumber#", companyLearners.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#BoxNumber#", companyLearners.getDocumentBoxID());
		welcome = welcome.replaceAll("#NameAndSurnameOfAdministrator#", user.getFirstName() + " " + user.getLastName());

		GenericUtility.sendMail(clo.getEmail(), "Learner File: Compliant", welcome, null);
	}

	public void sendNotAccreditedEmail(Users user, CompanyLearners companyLearners) throws Exception {
		String regionDescription = getRegionString(companyLearners.getCompany());
		String idNum = "";
		if (companyLearners.getUser().getRsaIDNumber() != null && !companyLearners.getUser().getRsaIDNumber().isEmpty() && !companyLearners.getUser().getRsaIDNumber().equals("")) {
			idNum = companyLearners.getUser().getRsaIDNumber();
		} else {
			idNum = companyLearners.getUser().getPassportNumber();
		}
		Map<String, Object> params = new HashMap<String, Object>();

		String toEmail = user.getEmail();
		JasperService.addLogo(params);

		String subject = "QUALIFICATION AMENDMENT NOTIFICATION";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + " Kindly be advised that your request to amend the qualification for " + companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " (" + idNum + ")" + " could not be processed as you are currently not accredited for the requested qualification." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Should you wish to continue with the amendment kindly apply for accreditation for this qualification or apply to transfer the learner." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance or further information, kindly contact the Client Liaison Officer at " + regionDescription + "." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely," + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Client Services " + "</p>";

		GenericUtility.sendMail(toEmail, subject, mssg, null);
	}
	
	public void saveForOfficeUseOnlyDocument(CompanyLearners companyLearners, Users user) throws Exception {

		String adminName = user.getFirstName() + " " + user.getLastName();
		String rsaIdPassport = anIDNumber(companyLearners.getUser());
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		HostingCompanyEmployees hostingCompanyEmployees = hostingCompanyEmployeesService.findByUserReturnHostCompany(user.getId());
		
		String designation = "N/A";
		if(hostingCompanyEmployees != null) {
			designation = hostingCompanyEmployees.getJobTitle().getDescription();
		}
		
		String conditionalPlacementDate = "";
		String registrationDateOfaggrement = "";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		if(companyLearners.getConditionalPlacementDate() != null) {
			conditionalPlacementDate = dateFormat.format(companyLearners.getConditionalPlacementDate());
		}
		
		if(companyLearners.getApprovalDate() != null) {
			registrationDateOfaggrement = dateFormat.format(companyLearners.getApprovalDate());
		}
		
	
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "higher_education_and_training_logo");
		params.put("user", user);
		params.put("adminName", adminName);
		params.put("designation", designation);
		params.put("aggrementNumber", companyLearners.getRegistrationNumber());
		params.put("conditionalPlacementDate", conditionalPlacementDate);
		params.put("registrationDateofaggrement", registrationDateOfaggrement);

		JasperService js=new JasperService();
		Doc doc = js.createReportFromDBtoServletOutputStream("WORK_BASED_LEARNING_PROGRAMME_AGREEMENT_OFFICE_USE.jasper", params,"ForOfficeUseOnly"+".pdf");
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		doc.setUsers(user);
		doc.setTargetKey(companyLearners.getId());
		doc.setTargetClass(CompanyLearners.class.getName());
		DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
	}
	
	public void sendForOfficeUseOnlyForm(CompanyLearners companyLearners, Users user) throws Exception {

		String adminName = user.getFirstName() + " " + user.getLastName();
		String rsaIdPassport = anIDNumber(companyLearners.getUser());
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		HostingCompanyEmployees hostingCompanyEmployees = hostingCompanyEmployeesService.findByUserReturnHostCompany(user.getId());
		
		String designation = "N/A";
		if(hostingCompanyEmployees != null) {
			designation = hostingCompanyEmployees.getJobTitle().getDescription();
		}
		
		String conditionalPlacementDate = "";
		String registrationDateOfaggrement = "";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		if(companyLearners.getConditionalPlacementDate() != null) {
			conditionalPlacementDate = dateFormat.format(companyLearners.getConditionalPlacementDate());
		}
		
		if(companyLearners.getApprovalDate() != null) {
			registrationDateOfaggrement = dateFormat.format(companyLearners.getApprovalDate());
		}
		
		String subject = "LEARNER REGISTRATION APPLICATION OUTCOME";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Learner:  " + companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " (" + rsaIdPassport + ")" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Registration Number: " + companyLearners.getRegistrationNumber() 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA hereby confirms that the application documents for the above learner have been registered accordingly." + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the learner registration documents are available under the learner's profile." + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "You are requested to note that if the operations of the business changes, or if it is desired to transfer the agreement to another employer, the merSETA must be notified beforehand." + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Client Services Administrator </p>";
	
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "higher_education_and_training_logo");
		params.put("user", user);
		params.put("adminName", adminName);
		params.put("designation", designation);
		params.put("aggrementNumber", companyLearners.getRegistrationNumber());
		params.put("conditionalPlacementDate", conditionalPlacementDate);
		params.put("registrationDateofaggrement", registrationDateOfaggrement);
		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();

		byte[] bites = JasperService.instance().convertJasperReportToByte("WORK_BASED_LEARNING_PROGRAMME_AGREEMENT_OFFICE_USE.jasper", params);

		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("ForOfficeUseOnly.pdf");
		attachmentBeans.add(beanAttachment);

		GenericUtility.sendMailWithAttachementTempWithLog(user.getEmail(), subject, mssg, attachmentBeans, null);		
	}
	
	public void sendApprovalNotification(CompanyLearners companyLearners, Users user) throws Exception {

		String adminName = user.getFirstName() + " " + user.getLastName();
		String rsaIdPassport = anIDNumber(companyLearners.getUser());
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		HostingCompanyEmployees hostingCompanyEmployees = hostingCompanyEmployeesService.findByUserReturnHostCompany(user.getId());
		
		String designation = "N/A";
		if(hostingCompanyEmployees != null) {
			designation = hostingCompanyEmployees.getJobTitle().getDescription();
		}
		
		String conditionalPlacementDate = "";
		String registrationDateOfaggrement = "";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		if(companyLearners.getConditionalPlacementDate() != null) {
			conditionalPlacementDate = dateFormat.format(companyLearners.getConditionalPlacementDate());
		}
		
		if(companyLearners.getApprovalDate() != null) {
			registrationDateOfaggrement = dateFormat.format(companyLearners.getApprovalDate());
		}
		
		String subject = "LEARNER REGISTRATION APPLICATION OUTCOME";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Learner:  " + companyLearners.getUser().getFirstName() + " " + companyLearners.getUser().getLastName() + " (" + rsaIdPassport + ")" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Registration Number: " + companyLearners.getRegistrationNumber() 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA hereby confirms that the application documents for the above learner have been registered accordingly." + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the learner registration documents are available under the learner's profile." + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "You are requested to note that if the operations of the business changes, or if it is desired to transfer the agreement to another employer, the merSETA must be notified beforehand." + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Client Services Administrator </p>";
	
		

		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);		
	}
	
	private List<Users> getClientServiceAdmin(CompanyLearners companyLearners) throws Exception {
		List<Users> users = new ArrayList<>();
		List<Tasks>tasks = TasksService.instance().findTasksByTypeAndKey(ConfigDocProcessEnum.Learner, CompanyLearners.class.getName(), companyLearners.getId());
		users.add(tasks.get(0).getActionUser());
		return users;
	}
	
	public int countCompanyLearnersByLearnerIdQualificationAndNotLearnerStatus(Long learnerId, Long qualificationId, LearnerStatusEnum learnerStatusEnum) throws Exception {
		return dao.countCompanyLearnersByLearnerIdQualificationAndNotLearnerStatus(learnerId, qualificationId, learnerStatusEnum);
	}

	public void checkIfRegisteredForQualification(Users user, CompanyLearners companylearners)throws Exception {
		Qualification qual = getCompanyLearnerQualification(companylearners);
		if(qual != null && qual.getId() != null) {
			List<CompanyLearners> list = dao.findByUserAndQualification(user.getId(), qual.getId());
			if(list.size() >= 1) {
				throw new Exception("Learner is already registered for this qualification");
			}
		}		
	}
	
	public List<CompanyLearners> findLearnerRegisteredForQualification(Users user, CompanyLearners companylearners)throws Exception {
		List<CompanyLearners> list = new ArrayList<>();
		if(companylearners != null && companylearners.getId() != null){
			Qualification qual = getCompanyLearnerQualification(companylearners);
			if(qual != null && qual.getId() != null) {
				list = dao.findByUserAndQualification(user.getId(), qual.getId());			
			}
		}
		return list;
	}
	
	public List<CompanyLearners> findLearnersByStatus(ApprovalEnum status, LearnerStatusEnum learnerStatus) throws Exception {
		return dao.findLearnersByStatus(status, learnerStatus);
	}

	public void sendLearnerTermination(CompanyLearners cl) throws Exception {
		cl.setLearnerStatus(LearnerStatusEnum.Terminated);
		dao.update(cl);
	}
	
	public List<CompanyLearners> allCompanyLearnersWhereproviderAssignedButNoApplication() throws Exception {
		return dao.allCompanyLearnersWhereProviderAssignedButNoApplication();
	}
	
	public Integer countCompanyLearnersByEmployerId(Long companyId) throws Exception {
		return dao.countCompanyLearnersByEmployerId(companyId);
	}
	
	public List<CompanyLearners> findLearnersByStatusNonLegacyAndDateNotPopulated(ApprovalEnum status, LearnerStatusEnum learnerStatus) throws Exception {
		return dao.findLearnersByStatusNonLegacyAndDateNotPopulated(status, learnerStatus);
	}
	
	public List<CompanyLearners> findLearnersByStatusLegacyAndDateNotPopulated(ApprovalEnum status, LearnerStatusEnum learnerStatus) throws Exception {
		return dao.findLearnersByStatusLegacyAndDateNotPopulated(status, learnerStatus);
	}
	
	public List<CompanyLearners> findLearnersByStatusAndPipAssignedImport(List<LearnerStatusEnum> learnerStatus) throws Exception {
		return dao.findLearnersByStatusAndPipAssignedImport(learnerStatus);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Long id) throws Exception {
		return dao.generateCompanyLearnerReport(id);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport(InterventionType intevention) throws Exception {
		return dao.generateCompanyLearnerReport(intevention);
	}
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Region region, InterventionType intevention, LearnerStatusEnum learnerStatusEnum) throws Exception {
		return dao.generateCompanyLearnerReport(region,intevention, learnerStatusEnum);
	}
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Region region, InterventionType intevention) throws Exception {
		return dao.generateCompanyLearnerReport(region,intevention);
	}
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Region region, LearnerStatusEnum learnerStatusEnum) throws Exception {
		return dao.generateCompanyLearnerReport(region,learnerStatusEnum);
	}
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Chamber chamber, InterventionType intevention, LearnerStatusEnum learnerStatusEnum) throws Exception {
		return dao.generateCompanyLearnerReport(chamber,intevention, learnerStatusEnum);
	}
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Chamber chamber, InterventionType intevention) throws Exception {
		return dao.generateCompanyLearnerReport(chamber,intevention);
	}
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Chamber chamber, LearnerStatusEnum learnerStatusEnum) throws Exception {
		return dao.generateCompanyLearnerReport(chamber,learnerStatusEnum);
	}
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Chamber chamber) throws Exception {
		return dao.generateCompanyLearnerReport(chamber);
	}
	public List<CompanyLearnersBean> generateCompanyLearnerReport(Region region) throws Exception {
		return dao.generateCompanyLearnerReport(region);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerReport() throws Exception {
		return dao.generateCompanyLearnerReport();
	}
	public List<LearnersMentorBean> generateLearnersMentorBean() throws Exception {
		return dao.generateLearnersMentorBean();
	}
	public List<LearnersMentorBean> generateLearnersMentorBean(Long companyId) throws Exception {
		return dao.generateLearnersMentorBean(companyId);
	}
	public List<CompanyLearnersBean> generateCompanyLearnerReport(LearnerStatusEnum learnerStatusEnum) throws Exception {
		return dao.generateCompanyLearnerReport(learnerStatusEnum);
	}
	
	public List<CompanyLearnersBean> generateCompanyLearnerByCompanyReport(Company c) throws Exception {
		return dao.generateCompanyLearnerByCompanyReport(c);
	}
	
	public List<CompanyLearners> resolveQualificationData(List<CompanyLearners> entityList) throws Exception{
		for (CompanyLearners entity : entityList) {
			resolveQualificationData(entity);
		}
		return entityList;
	}
	
	public CompanyLearners resolveQualificationData(CompanyLearners entity) throws Exception{
		if (entity.getId() != null) {
			// resolve qualification
			if (entity.getQualification() != null && entity.getQualification().getId() != null) {
				entity.setQualification(QualificationService.instance().findByKey(entity.getQualification().getId()));
			}
			// resolve learning program info
			if (entity.getLeaningProgramme() != null && entity.getLeaningProgramme().getId() != null) {
				entity.setLeaningProgramme(QualificationService.instance().findByKey(entity.getLeaningProgramme().getId()));
			}
			// resolve learner ship
			if (entity.getLearnership() != null && entity.getLearnership().getId() != null) {
				entity.setLearnership(LearnershipService.instance().findByKey(entity.getLearnership().getId()));
			}
			// resolve unit standards
			if (entity.getUnitStandard() != null && entity.getUnitStandard().getId() != null) {
				entity.setUnitStandard(UnitStandardsService.instance().findByKey(entity.getUnitStandard().getId()));
			}
			// resolve skills set
			if (entity.getSkillsSet() != null && entity.getSkillsSet().getId() != null) {
				entity.setSkillsSet(SkillsSetService.instance().findByKey(entity.getSkillsSet().getId()));
			}
			// resolve skills program information
			if (entity.getSkillsProgram() != null && entity.getSkillsProgram().getId() != null) {
				entity.setSkillsProgram(SkillsProgramService.instance().findByKey(entity.getSkillsProgram().getId()));
			}
			// resolve non-credit bearing information
			if (entity.getNonCreditBearingIntervationTitle() != null && entity.getNonCreditBearingIntervationTitle().getId() != null) {
				entity.setNonCreditBearingIntervationTitle(NonCreditBearingIntervationTitleService.instance().findByKey(entity.getNonCreditBearingIntervationTitle().getId()));
			}
		}
		return entity;
	}
	
	public void sendLPMFM005AddendumEmail(CompanyLearnersTransfer companyLearnersTransfer, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
		OfoCodesService ofoCodesService = new OfoCodesService();
		OfoCodes ofoCodes = new OfoCodes();
		if (companyLearnersTransfer.getCompanyLearners().getOfoCodes() != null) {
			ofoCodes = ofoCodesService.findByKey(companyLearnersTransfer.getCompanyLearners().getOfoCodes().getId());
		}

		Users learner = usersService.findByKey(companyLearnersTransfer.getCompanyLearners().getUser().getId());
		Company newEmployer = companyService.findByKey(companyLearnersTransfer.getTransferToCompany().getId());
		Users employerContactPerson = companyUsersService.findCompanyContactPerson(newEmployer.getId());
		Company skillsDevProvider = companyService.findByKey(companyLearnersTransfer.getCompanyLearners().getCompany().getId());
		Users skillsDevProviderContactPerson = companyUsersService.findCompanyContactPerson(skillsDevProvider.getId());
		String applicationReason = companyLearnersTransfer.getTransferReason();
		String contractAgreementNum = "N/A";
		if (companyLearnersTransfer.getCompanyLearners().getRegistrationNumber() != null) {
			contractAgreementNum = companyLearnersTransfer.getCompanyLearners().getRegistrationNumber();
		}
		String skillsDevProviderAccreditationNum = trainingProviderApplicationService.findCompanyAccreditation(skillsDevProvider);
		boolean isMinor = checkRequireGaurdian(learner);
		// Setting N/As if Contact Person is not available
		if (employerContactPerson == null) {
			employerContactPerson = setNotApplicable();
		}
		if (skillsDevProviderContactPerson == null) {
			skillsDevProviderContactPerson = setNotApplicable();
		}

		params.put("learner", learner);
		params.put("new_employer", newEmployer);
		params.put("employerContactPerson", employerContactPerson);
		params.put("skillsDevProvider", skillsDevProvider);
		params.put("skillsDevProviderContactPerson", skillsDevProviderContactPerson);
		params.put("applicationReason", applicationReason);
		params.put("contractAgreementNum", contractAgreementNum);
		params.put("ofo_codes", ofoCodes);
		params.put("skillsDevProviderAccreditationNum", skillsDevProviderAccreditationNum);
		params.put("isMinor", isMinor);

		//String toEmail = learner.getEmail();

		JasperService.addLogo(params);
		
		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-005-ApplicationToTransferALearner.jasper", params);
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Transfer_Application_Form.pdf";
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename(fileName);
		attachmentBeans.add(beanAttachment);

		AttachmentBean beanAttachmentAddendum = new AttachmentBean();
		if (companyLearnersTransfer.getLearnerTransferType() == LearnerTransferTypeEnum.ProviderTransfer) {
			params.put("company_id", companyLearnersTransfer.getTransferToCompany().getId());
			params.put("learners_id", learner.getId());
			byte[] bitesAddendum = JasperService.instance().convertJasperReportToByte("LPM-AD-008-AddendumToAmendLinkBetweenProviderAndLearner.jasper", params);
			String fileNameAddendum = learner.getFirstName() + "_" + learner.getLastName() + "_Learner_Transfere_Addendum_Form.pdf";
			beanAttachmentAddendum.setExt("pdf");
			beanAttachmentAddendum.setFile(bitesAddendum);
			beanAttachmentAddendum.setFilename(fileNameAddendum);
			attachmentBeans.add(beanAttachmentAddendum);
			//sendLPMAD008Email(companyLearnersTransfer, u);
		} else if (companyLearnersTransfer.getLearnerTransferType() == LearnerTransferTypeEnum.WorkplaceTransfer) {
			params.put("company_id", companyLearnersTransfer.getTransferToCompany().getId());
			params.put("learners_id", learner.getId());
			byte[] bitesAddendum = JasperService.instance().convertJasperReportToByte("LPM-AD-002-Addendum-ToAmendLinkBetweenEmployerAndLearner.jasper", params);
			String fileNameAddendum = learner.getFirstName() + "_" + learner.getLastName() + "_Learner_Transfere_Addendum_Form.pdf";
			beanAttachmentAddendum.setExt("pdf");
			beanAttachmentAddendum.setFile(bitesAddendum);
			beanAttachmentAddendum.setFilename(fileNameAddendum);
			attachmentBeans.add(beanAttachmentAddendum);			
			//sendLPMAD002Email(companyLearnersTransfer, tempUser);
		}
		
		String subject = "Learner Transfer Application".toUpperCase();

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear  " + user.getFirstName() + " " + user.getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the request for the transfer of learner "+  learner.getFirstName() + " " + learner.getLastName() +"("+companyLearnersTransfer.getCompanyLearners().getRegistrationNumber()+ "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly complete the enclosed form and addendum to upload on the NSDMS" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours in Skills Development," + "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Administration" + "</p>";
		
		//GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);
		GenericUtility.sendMailWithAttachementTempWithLog(user.getEmail(), subject, mssg, attachmentBeans, null);
	}
	
	public void sendLPMFM018BAddendumEmail(CompanyLearnersTransfer companyLearnersTransfer, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		CompanyLearners companyLearners = findByKey(companyLearnersTransfer.getCompanyLearners().getId());
		Users learner = usersService.findByKey(companyLearnersTransfer.getCompanyLearners().getUser().getId());
		Company newEmployer = companyService.findByKey(companyLearnersTransfer.getTransferToCompany().getId());
		Users employerContactPerson = companyUsersService.findCompanyContactPerson(newEmployer.getId());
		String applicationReason = companyLearnersTransfer.getTransferReason();
		String learnershipNum = "N/A";
		String leanership = "N/A";
		if (companyLearnersTransfer.getCompanyLearners() != null && companyLearnersTransfer.getCompanyLearners().getInterventionType() != null) {
			if (companyLearnersTransfer.getCompanyLearners().getLearnership() != null) {
				leanership = companyLearnersTransfer.getCompanyLearners().getLearnership().getDescription();
				learnershipNum = companyLearnersTransfer.getCompanyLearners().getLearnership().getCode();
			}
		}
		String skillsDevProviderAccreditationNum = "";
		if (companyLearnersTransfer.getCompanyLearners().getCompany() != null) {
			if (companyLearnersTransfer.getCompanyLearners().getCompany().getAccreditationNumber() != null) {
				skillsDevProviderAccreditationNum = companyLearnersTransfer.getCompanyLearners().getCompany().getAccreditationNumber();
			}
		}
		boolean isMinor = checkRequireGaurdian(learner);
		params.put("learner", learner);
		params.put("new_employer", newEmployer);// New training provider
		params.put("employerContactPerson", employerContactPerson);
		params.put("applicationReason", applicationReason);
		params.put("learnershipNum", learnershipNum);
		params.put("leanership", leanership);
		params.put("skillsDevProviderAccreditationNum", skillsDevProviderAccreditationNum);
		params.put("isMinor", isMinor);
		params.put("agreement_number", companyLearners.getRegistrationNumber());

		String toEmail = user.getEmail();
		JasperService.addLogo(params);
		
		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-018-B-Application-to-Transfer-Learner-Between-Training-Providers.jasper", params);
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Transfer_Application_Form.pdf";
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename(fileName);
		attachmentBeans.add(beanAttachment);

		AttachmentBean beanAttachmentAddendum = new AttachmentBean();
		if (companyLearnersTransfer.getLearnerTransferType() == LearnerTransferTypeEnum.ProviderTransfer) {
			params.put("company_id", companyLearnersTransfer.getTransferToCompany().getId());
			params.put("learners_id", learner.getId());
			byte[] bitesAddendum = JasperService.instance().convertJasperReportToByte("LPM-AD-008-AddendumToAmendLinkBetweenProviderAndLearner.jasper", params);
			String fileNameAddendum = learner.getFirstName() + "_" + learner.getLastName() + "_Learner_Transfere_Addendum_Form.pdf";
			beanAttachmentAddendum.setExt("pdf");
			beanAttachmentAddendum.setFile(bitesAddendum);
			beanAttachmentAddendum.setFilename(fileNameAddendum);
			attachmentBeans.add(beanAttachmentAddendum);
			//sendLPMAD008Email(companyLearnersTransfer, u);
		} else if (companyLearnersTransfer.getLearnerTransferType() == LearnerTransferTypeEnum.WorkplaceTransfer) {
			params.put("company_id", companyLearnersTransfer.getTransferToCompany().getId());
			params.put("learners_id", learner.getId());
			byte[] bitesAddendum = JasperService.instance().convertJasperReportToByte("LPM-AD-002-Addendum-ToAmendLinkBetweenEmployerAndLearner.jasper", params);
			String fileNameAddendum = learner.getFirstName() + "_" + learner.getLastName() + "_Learner_Transfere_Addendum_Form.pdf";
			beanAttachmentAddendum.setExt("pdf");
			beanAttachmentAddendum.setFile(bitesAddendum);
			beanAttachmentAddendum.setFilename(fileNameAddendum);
			attachmentBeans.add(beanAttachmentAddendum);			
			//sendLPMAD002Email(companyLearnersTransfer, tempUser);
		}

		String subject = "Learner Transfer Application".toUpperCase();

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + " </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges Learner Transfer Application for the following learner: " + learner.getFirstName() + " " + learner.getLastName() + " (" + anIDNumber(learner) + ")." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly complete the enclosed form and submit on the NSDMS." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours in Skills Development," + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA Team " + "</p>";

		
		//GenericUtility.sendMailWithAttachement(toEmail, subject, mssg, bites, fileName, "pdf", null);
		GenericUtility.sendMailWithAttachementTempWithLog(toEmail, subject, mssg, attachmentBeans, null);
	}
	
	public void sendApprovalEmail(CompanyLearnersTransfer companyLearners, Users user) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String toEmail = user.getEmail();
		JasperService.addLogo(params);
		CompanyLearners cl = findByKey(companyLearners.getCompanyLearners().getId());
		String rsaIdPassport = anIDNumber(cl.getUser());

		String subject = "LEARNER TRANSFER OUTCOME";

		String mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear " + companyLearners.getCreateUser().getFirstName() + " " + companyLearners.getCreateUser().getLastName() + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please be advised that the merSETA has approved the learner transfer request for the following learner:  " + cl.getUser().getFirstName() + " " + cl.getUser().getLastName() + " (" + rsaIdPassport + ")"+ "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>" 					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Administration</p>";

		GenericUtility.sendMail(toEmail, subject, mssg, null);
	}
	
	private void sendFinalRejectEmail(CompanyLearnersTransfer companyLearners, Users user, List<RejectReasons> rejectReasons) throws Exception {
		CompanyLearners cl = findByKey(companyLearners.getCompanyLearners().getId());
		String rsaIdPassport = anIDNumber(cl.getUser());
		String reasons = convertToHTML(rejectReasons);

		String subject = "LEARNER TRANSFER OUTCOME";
		String toEmail = user.getEmail();

		String mssg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear " + companyLearners.getCreateUser().getFirstName() + " " + companyLearners.getCreateUser().getLastName() + "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please be advised that the merSETA has not approved the learner transfer request for the following learner: " + cl.getUser().getFirstName() + " " + cl.getUser().getLastName() + " (" + rsaIdPassport + ")"+ "</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>" 					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely,</p>" 
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> merSETA Administration</p>";

		GenericUtility.sendMail(toEmail, subject, mssg, null);
	}
}
