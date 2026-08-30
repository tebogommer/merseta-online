package haj.com.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import haj.com.bean.LearnersMentorBean;
import haj.com.bean.SkillsProgrammeSkillsSetBean;
import haj.com.bean.UnitStandardBean;
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
import haj.com.entity.SDFCompany;
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
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.CreatedByEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.LearnerChangeTypeEnum;
import haj.com.entity.enums.LearnerDocRequirements;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.ProgressType;
import haj.com.entity.enums.SignoffByEnum;
import haj.com.entity.enums.SubmissionEnum;
import haj.com.entity.enums.TradeTypeEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.DateChangeReasons;
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
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.DesignatedTradeLevelService;
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

public class CompanyLearnersOtpSignoffService extends AbstractService {

	/** The dao. */
	private CompanyLearnersDAO dao = new CompanyLearnersDAO();
	//private EtqaService etqaService = new EtqaService();
	private UsersService usersService = new UsersService();
	private RegisterService registerService = new RegisterService();
	private ConfigDocService configDocService = new ConfigDocService();
	private CompanyService companyService = new CompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private AppointmentService appointmentService = new AppointmentService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private ActiveContractDetailService activeContractDetailService = new ActiveContractDetailService();
	private SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
	private UnitStandardsService unitStandardsService = new UnitStandardsService();
	private RegionService regionService;
	/** Instance of service level */
	private static CompanyLearnersOtpSignoffService companyLearnersService;
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private RolesService rolesService = new RolesService();
	private WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();

	public static synchronized CompanyLearnersOtpSignoffService instance() {
		if (companyLearnersService == null) {
			companyLearnersService = new CompanyLearnersOtpSignoffService();
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
		return dao.findByKey(id);
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

	public void updateSchedule(CompanyLearners entity) {

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
	
	public void prepareELearnerBursaryLearnerRegistrationDocs(ConfigDocProcessEnum configDocProcess, CompanyLearners entityDoc, CompanyLearners entity, ProcessRoles processRoles) throws Exception {
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
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessELearnerBursary(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void prepareELearnerBursaryDisabledLearnerRegistrationDocs(ConfigDocProcessEnum configDocProcess, CompanyLearners entityDoc, CompanyLearners entity, ProcessRoles processRoles) throws Exception {
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
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessADisabledBursary(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void prepareELearnerLearnerRegistrationDocs(ConfigDocProcessEnum configDocProcess, CompanyLearners entityDoc, CompanyLearners entity, ProcessRoles processRoles) throws Exception {
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
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessAELearner(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void prepareELearnerDisabledRegistrationDocs(ConfigDocProcessEnum configDocProcess, CompanyLearners entityDoc, CompanyLearners entity, ProcessRoles processRoles) throws Exception {
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
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessAELearnerDisabled(configDocProcess);
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
	}
	
		public void createNewLearner(Users createUser, Users entity, CompanyLearners cl, boolean submitWorkflow, boolean requireGaurdian) throws Exception {
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
						
			//Users gaurdian = null;
			if (requireGaurdian) {
				//gaurdian = entity.getLegalGaurdian();
			} else {
				//gaurdian = null;
				//entity.setLegalGaurdian(gaurdian);
			}

			List<IDataEntity> createBatch = new ArrayList<>();
			List<IDataEntity> updateBatch = new ArrayList<>();
			String error = "";
			String pwdLearner = "";
			cl.setCreateUser(createUser);

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

			updateBatch.add(entity);
			cl.setUser(entity);
			
			if(cl.getCreatedByEnum() == CreatedByEnum.sdf) {
				cl.setSignoffByEnum(SignoffByEnum.sdf);
			}else if(cl.getCreatedByEnum() == CreatedByEnum.sdp) {
				cl.setSignoffByEnum(SignoffByEnum.sdp);
			}else if(cl.getCreatedByEnum() == CreatedByEnum.merSETA) {
				cl.setSignoffByEnum(SignoffByEnum.merSETA);
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
					TasksService.instance().findFirstInProcessAndCreateTask(createUser, cl.getId(), CompanyLearners.class.getName(), ConfigDocProcessEnum.ELearner, false);					
				}
			}
			
			List<IDataEntity> dataEntities = new ArrayList<>();			
			Signoff signoff = new Signoff();
			signoff.setAccept(false);
			signoff.setUser(createUser);
			signoff.setTargetClass(CompanyLearners.class.getName());
			signoff.setTargetKey(cl.getId());
			signoff.setSignoffByEnum(SignoffByEnum.sdf);
			//SignoffService.instance().create(signoff);
			dataEntities.add(signoff);
			
			
			if(cl.getInterventionType().getForSdpAccreditaion() != null && cl.getInterventionType().getForSdpAccreditaion()) {
				Users u = companyUsersService.findSdpUsersByCompanyIdReturnFirstUser(cl.getCompany().getId(), true);
				Signoff signoffSDP = new Signoff();
				signoffSDP.setAccept(false);
				signoffSDP.setUser(u);
				signoffSDP.setTargetClass(CompanyLearners.class.getName());
				signoffSDP.setTargetKey(cl.getId());
				signoffSDP.setSignoffByEnum(SignoffByEnum.sdp);
				//SignoffService.instance().create(signoffSDP);
				dataEntities.add(signoffSDP);				
			}
			
			if(entity.getDateOfBirth() != null && GenericUtility.getAge(entity.getDateOfBirth()) < 18 && entity.getMaried() == YesNoEnum.No) {
				Signoff signoffGaurdian = new Signoff();
				signoffGaurdian.setAccept(false);
				signoffGaurdian.setUser(entity.getLegalGaurdian());
				signoffGaurdian.setTargetClass(CompanyLearners.class.getName());
				signoffGaurdian.setTargetKey(cl.getId());
				signoffGaurdian.setSignoffByEnum(SignoffByEnum.gaurdian);
				SignoffService.instance().create(signoffGaurdian);
				//dataEntities.add(signoffGaurdian);
			}
			
			Signoff signoffLearner = new Signoff();
			signoffLearner.setAccept(false);
			signoffLearner.setUser(cl.getUser());
			signoffLearner.setTargetClass(CompanyLearners.class.getName());
			signoffLearner.setTargetKey(cl.getId());
			signoffLearner.setSignoffByEnum(SignoffByEnum.Learner);
			//SignoffService.instance().create(signoffLearner);
			dataEntities.add(signoffLearner);
			
			this.dao.createBatch(dataEntities);
	}
		
		public void createNewLearnerBySDP(Users createUser, Users entity, CompanyLearners cl, boolean submitWorkflow, boolean requireGaurdian) throws Exception {
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
						
			//Users gaurdian = null;
			if (requireGaurdian) {
				//gaurdian = entity.getLegalGaurdian();
			} else {
				//gaurdian = null;
				//entity.setLegalGaurdian(gaurdian);
			}

			List<IDataEntity> createBatch = new ArrayList<>();
			List<IDataEntity> updateBatch = new ArrayList<>();
			String error = "";
			String pwdLearner = "";
			cl.setCreateUser(createUser);

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

			updateBatch.add(entity);
			cl.setUser(entity);
			
			if(cl.getCreatedByEnum() == CreatedByEnum.sdf) {
				cl.setSignoffByEnum(SignoffByEnum.sdf);
			}else if(cl.getCreatedByEnum() == CreatedByEnum.sdp) {
				cl.setSignoffByEnum(SignoffByEnum.sdp);
			}else if(cl.getCreatedByEnum() == CreatedByEnum.merSETA) {
				cl.setSignoffByEnum(SignoffByEnum.merSETA);
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
					TasksService.instance().findFirstInProcessAndCreateTask(createUser, cl.getId(), CompanyLearners.class.getName(), ConfigDocProcessEnum.ELearner, false);
				}
			}
						
			List<IDataEntity> dataEntities = new ArrayList<>();	
			
			Users u = companyUsersService.findSdpUsersByCompanyIdReturnFirstUser(cl.getCompany().getId(), true);
			Signoff signoffSDP = new Signoff();
			signoffSDP.setAccept(false);
			signoffSDP.setUser(u);
			signoffSDP.setTargetClass(CompanyLearners.class.getName());
			signoffSDP.setTargetKey(cl.getId());
			signoffSDP.setSignoffByEnum(SignoffByEnum.sdp);
			dataEntities.add(signoffSDP);
			
			SDFCompany sdfCompany = SDFCompanyService.instance().findPrimarySDF(cl.getEmployer());
			if(sdfCompany != null) {
				Signoff signoff = new Signoff();
				signoff.setAccept(false);
				signoff.setUser(sdfCompany.getSdf());
				signoff.setTargetClass(CompanyLearners.class.getName());
				signoff.setTargetKey(cl.getId());
				signoff.setSignoffByEnum(SignoffByEnum.sdf);
				dataEntities.add(signoff);
			}
			
			if(cl.getInterventionType().getForSdpAccreditaion() != null && cl.getInterventionType().getForSdpAccreditaion()) {
							
			}
			
			if(entity.getDateOfBirth() != null && GenericUtility.getAge(entity.getDateOfBirth()) < 18 && entity.getMaried() == YesNoEnum.No) {
				Signoff signoffGaurdian = new Signoff();
				signoffGaurdian.setAccept(false);
				signoffGaurdian.setUser(entity.getLegalGaurdian());
				signoffGaurdian.setTargetClass(CompanyLearners.class.getName());
				signoffGaurdian.setTargetKey(cl.getId());
				signoffGaurdian.setSignoffByEnum(SignoffByEnum.gaurdian);
				SignoffService.instance().create(signoffGaurdian);
				//dataEntities.add(signoffGaurdian);
			}
			
			Signoff signoffLearner = new Signoff();
			signoffLearner.setAccept(false);
			signoffLearner.setUser(cl.getUser());
			signoffLearner.setTargetClass(CompanyLearners.class.getName());
			signoffLearner.setTargetKey(cl.getId());
			signoffLearner.setSignoffByEnum(SignoffByEnum.Learner);
			//SignoffService.instance().create(signoffLearner);
			dataEntities.add(signoffLearner);
			
			this.dao.createBatch(dataEntities);
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
		
		if(workPlaceApprovalSites.size() > 0) {
			boolean valid = false;
			for(WorkPlaceApprovalSites wpas :workPlaceApprovalSites) {
				int count = countCompanyLearnersBySme(wpas.getSitesSme());
				if(qualification.getLearnerMentorRatio().getNoOfLearners() == null) {
					throw new Exception("No learner mentor ratio assigned to a qualification, please contact support");
				}
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
		isworkplaceapproved = wpas.checkIfWorkplaceApproved(companyService.findByKey(company.getId()), qualification, null, null, null);
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

	public void completeNewCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		uploadDocuments(companyLearners, tasks, user);
		List<Users> userList = new ArrayList<>();
		TrainingProviderApplication tpa = null;
		if (companyLearners.getTrainingProviderApplication() != null && companyLearners.getTrainingProviderApplication().getId() != null) {
			tpa = trainingProviderApplicationService.findByKey(companyLearners.getTrainingProviderApplication().getId());
		}
		if (tpa != null && tpa.getId() != null) {
			userList = findRegionClinetServiceCoodinator(tpa);
		} else {
			userList = findRegionClinetServiceCoodinator(companyLearners.getCompany());
		}
		if (tpa != null && userList.isEmpty()) {
			userList = findRegionClinetServiceCoodinator(companyLearners.getCompany());
		}
		if (userList.size() == 0) {
			throw new Exception("No Client Service Coodinator assigned to region");
		}
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
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
	
	public void completeCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		if(companyLearners.getCreatedByEnum() == CreatedByEnum.sdf) {
			if(tasks.getFirstInProcess()) {
				if(companyLearners.getInterventionType().getForSdpAccreditaion() != null && companyLearners.getInterventionType().getForSdpAccreditaion()) {
					Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.sdp);
					if(s == null) {
						throw new Exception("No training provider assigned to this application");
					}
					List<Users> userList = new ArrayList<>();			
					userList.add(s.getUser());
					companyLearners.setStatus(ApprovalEnum.PendingSignOff);
					companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
					companyLearners.setSignoffByEnum(SignoffByEnum.sdp);
					create(companyLearners);
					TasksService.instance().completeTask(tasks);
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
				}else if(companyLearners.getUser().getDateOfBirth() != null && GenericUtility.getAge(companyLearners.getUser().getDateOfBirth()) < 18 && companyLearners.getUser().getMaried() == YesNoEnum.No) {
					Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.gaurdian);
					if(s == null) {
						throw new Exception("No gaurdian assigned to this application");
					}
					List<Users> userList = new ArrayList<>();			
					userList.add(s.getUser());
					companyLearners.setStatus(ApprovalEnum.PendingSignOff);
					companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
					companyLearners.setSignoffByEnum(SignoffByEnum.gaurdian);
					create(companyLearners);
					TasksService.instance().completeTask(tasks);
					TasksService.instance().findByPositionAndCreateTaskWithUsers(2, "", user, companyLearners.getId(), companyLearners.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), tasks.getWorkflowProcess(), null, "", true, userList);
				}else {
					Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.Learner);
					if(s == null) {
						throw new Exception("No learner assigned to this application");
					}					
					List<Users> userList = new ArrayList<>();
					userList.add(s.getUser());				
					//uploadDocuments(companyLearners, tasks, user);
					companyLearners.setStatus(ApprovalEnum.PendingSignOff);
					companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
					companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
					create(companyLearners);
					TasksService.instance().completeTask(tasks);
					TasksService.instance().findByPositionAndCreateTaskWithUsers(3, "", user, companyLearners.getId(), companyLearners.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), tasks.getWorkflowProcess(), null, "", true, userList);
				}
			}else if(companyLearners.getSignoffByEnum() == SignoffByEnum.sdp) {
				if(companyLearners.getUser().getDateOfBirth() != null && GenericUtility.getAge(companyLearners.getUser().getDateOfBirth()) < 18 && companyLearners.getUser().getMaried() == YesNoEnum.No) {
					Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.gaurdian);
					if(s == null) {
						throw new Exception("No gaurdian assigned to this application");
					}
					List<Users> userList = new ArrayList<>();			
					userList.add(s.getUser());		
					companyLearners.setStatus(ApprovalEnum.PendingSignOff);
					companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
					companyLearners.setSignoffByEnum(SignoffByEnum.gaurdian);
					create(companyLearners);
					TasksService.instance().completeTask(tasks);
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
				}else {
					Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.Learner);
					if(s == null) {
						throw new Exception("No learner assigned to this application");
					}					
					List<Users> userList = new ArrayList<>();
					userList.add(s.getUser());	
					companyLearners.setStatus(ApprovalEnum.PendingSignOff);
					companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
					companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
					create(companyLearners);
					TasksService.instance().completeTask(tasks);
					TasksService.instance().findByPositionAndCreateTaskWithUsers(3, "", user, companyLearners.getId(), companyLearners.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), tasks.getWorkflowProcess(), null, "", true, userList);
				}
			}else if(companyLearners.getSignoffByEnum() == SignoffByEnum.gaurdian) {
				Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.Learner);
				if(s == null) {
					throw new Exception("No learner assigned to this application");
				}					
				List<Users> userList = new ArrayList<>();
				userList.add(s.getUser());		
				companyLearners.setStatus(ApprovalEnum.PendingSignOff);
				companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
				companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
				create(companyLearners);
				TasksService.instance().completeTask(tasks);
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
			}else if(companyLearners.getSignoffByEnum() == SignoffByEnum.Learner) {
				List<Users> userList = findRegionClinetServiceAdministrator(companyLearners.getCompany());
				if (userList.size() == 0) {
					throw new Exception("No Client Service Administrator assigned to region");
				}
				//uploadAgrementForm(companyLearners, user);
				companyLearners.setStatus(ApprovalEnum.PendingApproval);
				companyLearners.setSubmissionEnum(SubmissionEnum.Submitted);
				companyLearners.setSignoffByEnum(SignoffByEnum.Completed);
				create(companyLearners);			
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
			}else {
				if(companyLearners.getStatus() == ApprovalEnum.Rejected) {
					List<Users> userList = findRegionClinetServiceAdministrator(companyLearners.getCompany());
					if (userList.size() == 0) {
						throw new Exception("No Client Service Administrator assigned to region");
					}
					//uploadAgrementForm(companyLearners, user);
					companyLearners.setStatus(ApprovalEnum.PendingApproval);
					companyLearners.setSubmissionEnum(SubmissionEnum.Submitted);
					companyLearners.setSignoffByEnum(SignoffByEnum.Completed);
					create(companyLearners);			
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
				}else {
					throw new Exception("Signoff error");
				}
			}
			
			//End of Learners registration signoff created by SDF
		}else if(companyLearners.getCreatedByEnum() == CreatedByEnum.sdp) {
			if(tasks.getFirstInProcess()) {
				Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.sdf);
				List<Users> userList = new ArrayList<>();
				userList.add(s.getUser());				
				companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
				companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
				companyLearners.setStatus(ApprovalEnum.PendingSignOff);
				companyLearners.setSubmissionEnum(SubmissionEnum.Submitted);
				companyLearners.setSignoffByEnum(SignoffByEnum.sdf);
				create(companyLearners);			
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
			}else if(companyLearners.getSignoffByEnum() == SignoffByEnum.sdf) {
				if(companyLearners.getUser().getDateOfBirth() != null && GenericUtility.getAge(companyLearners.getUser().getDateOfBirth()) < 18 && companyLearners.getUser().getMaried() == YesNoEnum.No) {
					Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.gaurdian);
					if(s == null) {
						throw new Exception("No gaurdian assigned to this application");
					}
					List<Users> userList = new ArrayList<>();			
					userList.add(s.getUser());		
					companyLearners.setStatus(ApprovalEnum.PendingSignOff);
					companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
					companyLearners.setSignoffByEnum(SignoffByEnum.gaurdian);
					create(companyLearners);
					TasksService.instance().completeTask(tasks);
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
				}else {
					Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.Learner);
					if(s == null) {
						throw new Exception("No learner assigned to this application");
					}
					List<Users> userList = new ArrayList<>();
					userList.add(s.getUser());				
					
					companyLearners.setSubmissionEnum(SubmissionEnum.Submitted);
					companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
					create(companyLearners);			
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
				}
			}else if(companyLearners.getSignoffByEnum() == SignoffByEnum.gaurdian) {
				Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.Learner);
				if(s == null) {
					throw new Exception("No learner assigned to this application");
				}					
				List<Users> userList = new ArrayList<>();
				userList.add(s.getUser());	
				companyLearners.setStatus(ApprovalEnum.PendingSignOff);
				companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
				companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
				create(companyLearners);
				TasksService.instance().completeTask(tasks);
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
			}else if(companyLearners.getSignoffByEnum() == SignoffByEnum.Learner) {
				List<Users> userList = findRegionClinetServiceAdministrator(companyLearners.getCompany());
				if (userList.size() == 0) {
					throw new Exception("No Client Service Administrator assigned to region");
				}
				//uploadAgrementForm(companyLearners, user);
				companyLearners.setStatus(ApprovalEnum.PendingApproval);
				companyLearners.setSubmissionEnum(SubmissionEnum.Submitted);
				companyLearners.setSignoffByEnum(SignoffByEnum.Completed);
				update(companyLearners);
				
				/*if(tasks.getProcessRole().getRoleOrder() == 3) {
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
				}else {
					TasksService.instance().completeTask(tasks);
					TasksService.instance().findByPositionAndCreateTaskWithUsersIgnoringCompanyUserTypeCheck(4, "", user, companyLearners.getId(), companyLearners.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), tasks.getWorkflowProcess(), null, "", true, userList);
				}*/
				
				TasksService.instance().completeTask(tasks);
				TasksService.instance().findByPositionAndCreateTaskWithUsersIgnoringCompanyUserTypeCheck(4, "", user, companyLearners.getId(), companyLearners.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), tasks.getWorkflowProcess(), null, "", true, userList);
			}
		}
	}

	public void completeCompanyLearnersOld(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {		
		if(tasks.getProcessRole().getRoleOrder() == 1) {
			if(companyLearners.getInterventionType().getForSdpAccreditaion() != null && companyLearners.getInterventionType().getForSdpAccreditaion()) {
				Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.sdp);
				if(s == null) {
					throw new Exception("No training provider assigned to this application");
				}
				List<Users> userList = new ArrayList<>();			
				userList.add(s.getUser());
				
				companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
				companyLearners.setSignoffByEnum(SignoffByEnum.sdp);
				create(companyLearners);
				TasksService.instance().completeTask(tasks);
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
			}else {
				if(companyLearners.getUser().getDateOfBirth() != null && GenericUtility.getAge(companyLearners.getUser().getDateOfBirth()) < 18 && companyLearners.getUser().getMaried() == YesNoEnum.No) {
					Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.gaurdian);
					if(s == null) {
						throw new Exception("No gaurdian assigned to this application");
					}
					List<Users> userList = new ArrayList<>();			
					userList.add(s.getUser());
					
					companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
					companyLearners.setSignoffByEnum(SignoffByEnum.gaurdian);
					create(companyLearners);
					TasksService.instance().completeTask(tasks);
					TasksService.instance().findByPositionAndCreateTaskWithUsers(3, "", user, companyLearners.getId(), companyLearners.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), tasks.getWorkflowProcess(), null, "", true, userList);
					//TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
				}else {
					Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.Learner);
					if(s == null) {
						throw new Exception("No learner assigned to this application");
					}					
					List<Users> userList = new ArrayList<>();
					userList.add(s.getUser());
					
					//uploadDocuments(companyLearners, tasks, user);
					companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
					companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
					create(companyLearners);
					TasksService.instance().completeTask(tasks);
					TasksService.instance().findByPositionAndCreateTaskWithUsers(4, "", user, companyLearners.getId(), companyLearners.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), tasks.getWorkflowProcess(), null, "", true, userList);
					//TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
				}
			}
		}else if(tasks.getProcessRole().getRoleOrder() == 2){
			if(companyLearners.getSignoffByEnum() == SignoffByEnum.gaurdian) {
				Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.Learner);
				if(s == null) {
					throw new Exception("No learner assigned to this application");
				}					
				List<Users> userList = new ArrayList<>();
				userList.add(s.getUser());
				
				//uploadDocuments(companyLearners, tasks, user);
				companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
				companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
				create(companyLearners);
				TasksService.instance().completeTask(tasks);
				TasksService.instance().findByPositionAndCreateTaskWithUsers(4, "", user, companyLearners.getId(), companyLearners.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), tasks.getWorkflowProcess(), null, "", true, userList);
				//TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
			}else {
				if(companyLearners.getUser().getDateOfBirth() != null && GenericUtility.getAge(companyLearners.getUser().getDateOfBirth()) < 18 && companyLearners.getUser().getMaried() == YesNoEnum.No) {
					Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.gaurdian);
					if(s == null) {
						throw new Exception("No gaurdian assigned to this application");
					}
					List<Users> userList = new ArrayList<>();			
					userList.add(s.getUser());
					
					companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
					companyLearners.setSignoffByEnum(SignoffByEnum.gaurdian);
					create(companyLearners);
					TasksService.instance().completeTask(tasks);
					TasksService.instance().findByPositionAndCreateTaskWithUsers(3, "", user, companyLearners.getId(), companyLearners.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), tasks.getWorkflowProcess(), null, "", true, userList);
					//TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
				}else {
					Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.Learner);
					if(s == null) {
						throw new Exception("No learner assigned to this application");
					}					
					List<Users> userList = new ArrayList<>();
					userList.add(s.getUser());
					
					//uploadDocuments(companyLearners, tasks, user);
					companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
					companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
					create(companyLearners);
					TasksService.instance().completeTask(tasks);
					TasksService.instance().findByPositionAndCreateTaskWithUsers(4, "", user, companyLearners.getId(), companyLearners.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), tasks.getWorkflowProcess(), null, "", true, userList);
					//TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
				}
			}
		}else if(tasks.getProcessRole().getRoleOrder() == 3){
			Signoff s = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companyLearners.getId(), companyLearners.getClass().getName(), SignoffByEnum.Learner);
			if(s == null) {
				throw new Exception("No learner assigned to this application");
			}					
			List<Users> userList = new ArrayList<>();
			userList.add(s.getUser());
			
			//uploadDocuments(companyLearners, tasks, user);
			companyLearners.setSubmissionEnum(SubmissionEnum.NotSubmitted);
			companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
			create(companyLearners);
			TasksService.instance().completeTask(tasks);
			TasksService.instance().findByPositionAndCreateTaskWithUsers(4, "", user, companyLearners.getId(), companyLearners.getClass().getName(), true, hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA), tasks.getWorkflowProcess(), null, "", true, userList);
			//TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}else if(tasks.getProcessRole().getRoleOrder() == 4){
			List<Users> userList = findRegionClinetServiceAdministrator(companyLearners.getCompany());
			if (userList.size() == 0) {
				throw new Exception("No Client Service Administrator assigned to region");
			}
			//uploadAgrementForm(companyLearners, user);
			//uploadDocuments(companyLearners, tasks, user);
			companyLearners.setSubmissionEnum(SubmissionEnum.Submitted);
			companyLearners.setSignoffByEnum(SignoffByEnum.merSETA);
			create(companyLearners);
			
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}
	}
	
	private void uploadAgrementForm(CompanyLearners companyLearners, Users user) throws Exception {
		List<Doc> docs = DocService.instance().findDocByClassKeyLearnerDocRequirements(ConfigDocProcessEnum.ELearner, LearnerDocRequirements.Agreement, companyLearners.getClass().getName(), companyLearners.getId());
		if(docs.size()>0) {
			Doc document = docs.get(0);
			Doc doc = generateAgreementForm(companyLearners);
			document.setData(doc.getData());
			document.setUsers(user);
			DocService.instance().uploadNewVersion(document, user);
		}else {
			ConfigDoc configDoc = configDocService.findByProcessNotUploadedLearnerDocRequirements(ConfigDocProcessEnum.ELearner, LearnerDocRequirements.Agreement);
			if(configDoc !=null) {
				Doc doc = generateAgreementForm(companyLearners);
				doc.setConfigDoc(configDoc);
				doc.setUsers(user);
				doc.setVersionNo(1);
				doc.setTargetClass(companyLearners.getClass().getName());
				doc.setTargetKey(companyLearners.getId());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
				//DocService.instance().create(doc);
			}else {
				Doc doc = generateAgreementForm(companyLearners);
				doc.setConfigDoc(null);
				doc.setUsers(user);
				doc.setVersionNo(1);
				doc.setTargetClass(companyLearners.getClass().getName());
				doc.setTargetKey(companyLearners.getId());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
			}
		}
	}

	public void completeCompanyLearnersToRegion(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {		
		if(tasks.getWorkflowProcess() == ConfigDocProcessEnum.UnemployedBursaryLearnerRegistration) {
			uploadDocuments(companyLearners, tasks, user);
			companyLearners.setSubmissionEnum(SubmissionEnum.ForApproval);
			create(companyLearners);
	
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}else {
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

		int count = dao.countCompanyLearnersByLearnerStatus(companyLearners.getCompany().getId(), ApprovalEnum.PendingApproval, LearnerStatusEnum.Application);

		if (count > 0) {
			TasksService.instance().completeTask(tasks);
		} else if (count == 0) {
			List<Users> users = new ArrayList<>();
			users.add(companyLearners.getCreateUser());
			CompanyLearners cl = new CompanyLearners();
			cl.setCompany(companyLearners.getCompany());
			cl.setCreateUser(companyLearners.getCreateUser());
			create(cl);

			List<CompanyLearners> clList = dao.findCompanyLearnersByStatus(companyLearners.getCompany().getId(), ApprovalEnum.PreApproved, LearnerStatusEnum.Application);

			for (CompanyLearners comlearner : clList) {
				comlearner.setCompanyLearnersParent(cl);
				update(comlearner);
			}

			String description = "Please review the proposed date to submit learner documents at the Regional Office for " + companyLearners.getCompany().getCompanyName() + " (" + companyLearners.getCompany().getLevyNumber() + ").";

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
		
		if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
			if(companyLearners.getProjectImplementationPlan() != null) {
				ProjectImplementationPlanLearnersService.instance().linkCompanyLearnerToOpenPipLink(companyLearners, companyLearners.getProjectImplementationPlan());
				ProjectImplementationPlanLearners link = ProjectImplementationPlanLearnersService.instance().findByCompanyLearnerAndPip(companyLearners.getId(), companyLearners.getProjectImplementationPlan().getId());
				if (link == null || link.getId() == null) {
					// fail safe
					activeContractDetailService.addTranchePaymentDetail(companyLearners, user, 0.25, TrancheEnum.TRANCHE_TWO, true);
				} else {
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
		
		List<Users> userList = new ArrayList<>();
		TrainingProviderApplication tpa = null;
		if (companyLearners.getTrainingProviderApplication() != null && companyLearners.getTrainingProviderApplication().getId() != null) {
			tpa = trainingProviderApplicationService.findByKey(companyLearners.getTrainingProviderApplication().getId());
		}
		if (tpa != null && tpa.getId() != null) {
			userList = findRegionClinetServiceCoodinator(tpa);
		} else {
			userList = findRegionClinetServiceCoodinator(companyLearners.getCompany());
		}
		if (tpa != null && userList.isEmpty()) {
			userList = findRegionClinetServiceCoodinator(companyLearners.getCompany());
		}
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

		TasksService.instance().createTask(CompanyLearners.class.getName(), null, emailMessage, subject, description, user, entity.getId(), true, true, tasks, users, ConfigDocProcessEnum.LearnerFileManagement, null);
		sendRejectFileManagementEmail(entity, user, tasks, rejectReasons);
	}

	public void rejectCompanyLearners(CompanyLearners companyLearners, Users user, Tasks tasks, List<RejectReasons> rejectReasons, Appointment appointment) throws Exception {
		
		if(companyLearners.getCreatedByEnum() == CreatedByEnum.sdf) {
			companyLearners.setSignoffByEnum(SignoffByEnum.sdf);
		}else if(companyLearners.getCreatedByEnum() == CreatedByEnum.sdp){
			companyLearners.setSignoffByEnum(SignoffByEnum.sdp);
		}else if(companyLearners.getCreatedByEnum() == CreatedByEnum.merSETA){
			companyLearners.setSignoffByEnum(SignoffByEnum.merSETA);
		}		
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearners.class.getName(), rejectReasons, user, ConfigDocProcessEnum.Learner);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}

		List<Users> list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());
		TasksService.instance().completeTask(tasks);
		String description = "The learner registration application for: "+companyLearners.getUser().getFirstName()+" "+companyLearners.getUser().getLastName()+" ("+anIDNumber(companyLearners.getUser()) +","+ companyLearners.getUser().getEmail()+") for #COMPANY_NAME# (#ENTITY_ID#) has not been approved. Please review the application.";
		TasksService.instance().findFirstInProcessAndCreateTask(description, user, companyLearners.getId(), companyLearners.getClass().getName(), true, tasks.getWorkflowProcess(), MailDef.instance(MailEnum.Task, MailTagsEnum.Task, description), list);
		list.add(companyLearners.getUser());
		super.removeDuplicatesFromList(list);
		for (Users u : list) {
			sendRejectEmail(companyLearners, rejectReasons, u);
		}
		sendRejectEmailToTheLearner(companyLearners, rejectReasons);
		updateSignoffs(companyLearners);
	}

	public void updateSignoffs(CompanyLearners companyLearners) throws Exception {
		List<Signoff>signOffs = SignoffService.instance().findByTargetKeyAndClass(companyLearners.getId(), companyLearners.getClass().getName());
		for(Signoff s:signOffs) {
			s.setAccept(false);
			SignoffService.instance().update(s);
		}
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
		List<Users> mersetaUsers = findRegionClinetServiceAdministrator(companyLearners.getCompany());
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, mersetaUsers);

		List<Users> list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());
		list.add(companyLearners.getUser());

		super.removeDuplicatesFromList(list);
		for (Users u : list) {
			sendRejectEmail(companyLearners, rejectReasons, u);
		}
	}
	
	public void finalRejectCompanyLearner(CompanyLearners companyLearners, Users user, Tasks tasks, List<RejectReasons> rejectReasons, Appointment appointment) throws Exception {
		companyLearners.setLearnerStatus(LearnerStatusEnum.Rejected);	
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearners.class.getName(), rejectReasons, user, ConfigDocProcessEnum.Learner);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}

		List<Users> list = new ArrayList<>();
		list.add(companyLearners.getCreateUser());
		TasksService.instance().completeTask(tasks);
		list.add(companyLearners.getUser());
		super.removeDuplicatesFromList(list);
		for (Users u : list) {
			sendRejectEmail(companyLearners, rejectReasons, u);
		}
		sendRejectEmailToTheLearner(companyLearners, rejectReasons);
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
		companyLearners.setStatus(ApprovalEnum.Approved);
		companyLearners.setApprovalDate(getSynchronizedDate());
		companyLearners.setRegistrationNumber(generateCompanyLearnerRegNumber(companyLearners));
		dao.update(companyLearners);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		sendApprovalEmail(companyLearners, companyLearners.getCreateUser());
		uploadAgrementForm(companyLearners, user);
	}

	public void confirmReviewDate(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setReviewStatus(ApprovalEnum.Approved);
		dao.update(companyLearners);
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
					workPlaceApproval = workPlaceApprovalService.findApprovedWorkPlaceApprovalByCompanyAndQualificationAndSites(company, getCompanyLearnerQualification(cl), ApprovalEnum.Approved, cl.getSite().getId());
				}else {
					workPlaceApproval = workPlaceApprovalService.findApprovedWorkPlaceApprovalByCompanyAndQualification(company, getCompanyLearnerQualification(cl), ApprovalEnum.Approved);
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
			}else {
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

		if(summativeAssessmentReportUnitStandards != null && summativeAssessmentReportUnitStandards.size() > 0) {
			summativeAssessmentReportService.applyInterventionData(summativeAssessmentReport);
			dao.create(summativeAssessmentReport);
		
			summativeAssessmentReportService.createSummativeAssessmentReportUnitStandards(summativeAssessmentReport, summativeAssessmentReportUnitStandards);
		}
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
		cl.setCompanyLearnersTransfer((List<CompanyLearnersTransfer>) (List<?>) dao.findByCompanyLearner("CompanyLearnersTransfer", cl.getId()));
		cl.setCompanyLearnersLostTime((List<CompanyLearnersLostTime>) (List<?>) dao.findByCompanyLearner("CompanyLearnersLostTime", cl.getId()));
		cl.setCompanyLearnersTermination((List<CompanyLearnersTermination>) (List<?>) dao.findByCompanyLearner("CompanyLearnersTermination", cl.getId()));
		cl.setCompanyLearnersChange((List<CompanyLearnersChange>) (List<?>) dao.findByCompanyLearner("CompanyLearnersChange", cl.getId()));
		cl.setCompanyLearnersTradeTest((List<CompanyLearnersTradeTest>) (List<?>) dao.findByCompanyLearner("CompanyLearnersTradeTest", cl.getId()));
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

	}

	public void additionalData(CompanyLearners cl) throws Exception {
		if (cl != null && cl.getEmployer() != null && cl.getEmployer().getId() != null) {
			cl.setEmployer(companyService.findByKey(cl.getEmployer().getId()));

			if (cl.getEmployer().getPostalAddress() != null) {
				cl.getEmployer().setPostalAddress(AddressService.instance().findByKey(cl.getEmployer().getPostalAddress().getId()));
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

		byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-015-SkillsProgrammeLearnerRegistrationFormNew.jasper", params);

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

	public List<CompanyLearners> findByUser(Users u) throws Exception {
		return dao.findByUser(u);
	}
	
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

	

	public Users getCLO(Company company) throws Exception {
		Users cloUser = new Users();
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			cloUser = rt.getClo().getUsers();
		}
		return cloUser;
	}
	
	public Users getCLO(TrainingProviderApplication tpa) throws Exception {
		Users cloUser = new Users();
		Address address = null;
		RegionTown rt = null;
		if (tpa.getTrainingSite() != null && tpa.getTrainingSite().getId() != null) {
			if (tpa.getTrainingSite().getResidentialAddress() != null && tpa.getTrainingSite().getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(tpa.getTrainingSite().getResidentialAddress().getId());
			}
		} else if (tpa.getCompany() != null && tpa.getCompany().getId() != null) {
			if (tpa.getCompany().getResidentialAddress() != null && tpa.getCompany().getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(tpa.getCompany().getResidentialAddress().getId());
			}
		}
		if (address != null && address.getId() != null && address.getTown() != null) {
			rt = RegionTownService.instance().findByTown(address.getTown());
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
	
	public Users getCRM(TrainingProviderApplication tpa) throws Exception {
		Users crmUser = new Users();
		Address address = null;
		RegionTown rt = null;
		if (tpa.getTrainingSite() != null && tpa.getTrainingSite().getId() != null) {
			if (tpa.getTrainingSite().getResidentialAddress() != null && tpa.getTrainingSite().getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(tpa.getTrainingSite().getResidentialAddress().getId());
			}
		} else if (tpa.getCompany() != null && tpa.getCompany().getId() != null) {
			if (tpa.getCompany().getResidentialAddress() != null && tpa.getCompany().getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(tpa.getCompany().getResidentialAddress().getId());
			}
		}
		if (address != null && address.getId() != null && address.getTown() != null) {
			rt = RegionTownService.instance().findByTown(address.getTown());
			crmUser = rt.getCrm().getUsers();
		}
		return crmUser;
	}

	public Company findByKeyFoReview(long id) throws Exception {
		return null;// resolveContactPerson(dao.findByKey(id));
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
	
	public List<Users> findRegionClinetServiceCoodinator(TrainingProviderApplication tpa) throws Exception {
		List<Users> list = new ArrayList<>();
		Address address = null;
		if (tpa.getTrainingSite() != null && tpa.getTrainingSite().getId() != null) {
			if (tpa.getTrainingSite().getResidentialAddress() != null && tpa.getTrainingSite().getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(tpa.getTrainingSite().getResidentialAddress().getId());
			}
		} else if (tpa.getCompany() != null && tpa.getCompany().getId() != null) {
			if (tpa.getCompany().getResidentialAddress() != null && tpa.getCompany().getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(tpa.getCompany().getResidentialAddress().getId());
			}
		}
		if (address != null && address.getId() != null && address.getTown() != null) {
			Roles roles = rolesService.findByKey(HAJConstants.CLIENT_SERVICE_COORDINATOR_ROLE_ID);
			list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(address.getTown(), roles);
		}
		return list;
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
		
		TrainingProviderApplication tpa = null;
		if (cl.getTrainingProviderApplication() != null && cl.getTrainingProviderApplication().getId() != null) {
			tpa = trainingProviderApplicationService.findByKey(cl.getTrainingProviderApplication().getId());
		}

		// Provider
		if (cl.getCompany() != null) {
			List<Users> providerList = new ArrayList<>();
			try {
				if (tpa != null) {
					if (tpa.getTrainingSite() != null && tpa.getTrainingSite().getId() != null) {
						providerList = sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteIdReturnUsers(tpa.getCompany().getId(), tpa.getTrainingSite().getId());
					} else {
						providerList = sdpCompanyService.findAllSdpByCompanyIdReturnUsers(tpa.getCompany().getId());
					}
				}
				tpa = null;
			} catch (Exception e) {
				providerList = companyUsersService.findUsersByCompanyType(cl.getCompany().getId(), ConfigDocProcessEnum.TP);
			}
			if (tpa == null && providerList.isEmpty()) {
				providerList = companyUsersService.findUsersByCompanyType(cl.getCompany().getId(), ConfigDocProcessEnum.TP);
			}
			if (!providerList.isEmpty()) {
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
			Users clo = null;
			if (tpa != null && tpa.getId() != null) {
				clo = getCLO(tpa);
			}else {
				clo = getCLO(cl.getCompany());
			}
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
		} else if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())){
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

public void sendWorkBasedLearningProgrammeAgreement(Users createUser, Users learner, Company company, Company trainingProvider, boolean requireGaurdian, CompanyLearners companylearners, List<UsersDisability> usersDisabilityList) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		AttachmentBean beanAttachment = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		populateParametersWBA(companylearners, params);
		byte[] bites = JasperService.instance().convertJasperReportToByte("WORK_BASED_LEARNING_PROGRAMME_AGREEMENT_NEW.jasper", params);

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
			js.createReportFromDBtoServletOutputStream("WORK_BASED_LEARNING_PROGRAMME_AGREEMENT_SIGNOFF.jasper", params, companylearners.getUser().getFirstName()+"_"+companylearners.getUser().getLastName()+"_"+"AggrementForm.pdf");
		}else {
			Map<String, Object> params = new HashMap<String, Object>();
			populateParametersOther(companylearners, params);
			JasperService js = new JasperService();
			js.createReportFromDBtoServletOutputStream("LPM-FM-015-SkillsProgrammeLearnerRegistrationFormSignoff.jasper", params,companylearners.getUser().getFirstName()+"_"+companylearners.getUser().getLastName()+"_"+"AggrementForm.pdf");
		}
	}
	
	public void downloadOTPAgreementForm(CompanyLearners companylearners) throws Exception {		
		if(companylearners.getInterventionType().getForm().matches("002")) {
			Map<String, Object> params = new HashMap<String, Object>();
			populateParametersWBA(companylearners, params);
			JasperService js = new JasperService();
			js.createReportFromDBtoServletOutputStream("WORK_BASED_LEARNING_PROGRAMME_AGREEMENT_SIGNOFF.jasper", params, companylearners.getUser().getFirstName()+"_"+companylearners.getUser().getLastName()+"_"+"AggrementForm.pdf");
		}else {
			Map<String, Object> params = new HashMap<String, Object>();
			populateParametersOther(companylearners, params);			
			JasperService js = new JasperService();
			js.createReportFromDBtoServletOutputStream("LPM-FM-015-SkillsProgrammeLearnerRegistrationFormSignoff.jasper", params,companylearners.getUser().getFirstName()+"_"+companylearners.getUser().getLastName()+"_"+"AggrementForm.pdf");
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
		
		Signoff sdf_signoff = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companylearners.getId(), companylearners.getClass().getName(), SignoffByEnum.sdf);
		Signoff sdp_signoff = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companylearners.getId(), companylearners.getClass().getName(), SignoffByEnum.sdp);
		Signoff gurdian_signoff = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companylearners.getId(), companylearners.getClass().getName(), SignoffByEnum.gaurdian);
		Signoff learner_signoff = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companylearners.getId(), companylearners.getClass().getName(), SignoffByEnum.Learner);
		
		if(sdf_signoff != null && sdf_signoff.getAccept()) {
			params.put("sdf_signoff_initials", populateInitial(sdf_signoff.getUser()));
			params.put("sdf_signoff", sdf_signoff);
		}
		if(sdp_signoff !=null && sdp_signoff.getAccept()) {
			params.put("sdp_signoff_initials", populateInitial(sdp_signoff.getUser()));
			params.put("sdp_signoff", sdp_signoff);
		}
		if(gurdian_signoff != null && gurdian_signoff.getAccept()) {
			params.put("gurdian_signoff_initials", populateInitial(gurdian_signoff.getUser()));
			params.put("gurdian_signoff", gurdian_signoff);
		}
		if(learner_signoff != null && learner_signoff.getAccept()) {
			params.put("learner_signoff_initials", populateInitial(learner_signoff.getUser()));
			params.put("learner_signoff",learner_signoff);
		}
		
		if (companylearners.getUser().getLegalGaurdian() != null) {
			Users legalGaurdian = companylearners.getUser().getLegalGaurdian();
			params.put("parentGuadian", legalGaurdian);
		}
	}
	
	private String populateInitial(Users user) {
		String inStringForm = "";
		if(user != null) {
			inStringForm = inStringForm + user.getFirstName().trim().charAt(0) + user.getLastName().trim().charAt(0);
		}
		return inStringForm;
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

		Signoff sdf_signoff = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companylearners.getId(), companylearners.getClass().getName(), SignoffByEnum.sdf);
		Signoff sdp_signoff = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companylearners.getId(), companylearners.getClass().getName(), SignoffByEnum.sdp);
		Signoff gurdian_signoff = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companylearners.getId(), companylearners.getClass().getName(), SignoffByEnum.gaurdian);
		Signoff learner_signoff = SignoffService.instance().findByTargetKeyAndClassAndSignoffByEnum(companylearners.getId(), companylearners.getClass().getName(), SignoffByEnum.Learner);
		
		if(sdf_signoff != null && sdf_signoff.getAccept()) {
			params.put("sdf_signoff_initials", populateInitial(sdf_signoff.getUser()));
			params.put("sdf_signoff", sdf_signoff);
		}
		if(sdp_signoff !=null && sdp_signoff.getAccept()) {
			params.put("sdp_signoff_initials", populateInitial(sdp_signoff.getUser()));
			params.put("sdp_signoff", sdp_signoff);
		}
		if(gurdian_signoff != null && gurdian_signoff.getAccept()) {
			params.put("gurdian_signoff_initials", populateInitial(gurdian_signoff.getUser()));
			params.put("gurdian_signoff", gurdian_signoff);
		}
		if(learner_signoff != null && learner_signoff.getAccept()) {
			params.put("learner_signoff_initials", populateInitial(learner_signoff.getUser()));
			params.put("learner_signoff",learner_signoff);
		}
		
		if (companylearners.getUser().getLegalGaurdian() != null) {
			Users legalGaurdian = companylearners.getUser().getLegalGaurdian();
			params.put("parentGuadian", legalGaurdian);
		}
				
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
	
	public Doc generateAgreementForm(CompanyLearners companyLearners) throws Exception {
		Doc doc = new Doc();
 		Map<String, Object> params = new HashMap<String, Object>();	
 		//JasperService js=new JasperService();
 		if (companyLearners.getInterventionType().getForm().matches("002")) {
 			populateParametersWBA(companyLearners, params);
 			byte[] bites = JasperService.instance().convertJasperReportToByte("WORK_BASED_LEARNING_PROGRAMME_AGREEMENT_SIGNOFF.jasper", params);
 			doc.setData(bites);
 			doc.setOriginalFname("Agreement_"+anIDNumber(companyLearners.getUser())+".pdf");
 			//doc = js.createReportFromDBtoServletOutputStream("WORK_BASED_LEARNING_PROGRAMME_AGREEMENT_NEW.jasper", params,"Agreement_"+anIDNumber(companyLearners.getUser())+".pdf");
		} else if (companyLearners.getInterventionType().getForm().matches("015")) {
			populateParametersOther(companyLearners, params);
			byte[] bites = JasperService.instance().convertJasperReportToByte("LPM-FM-015-SkillsProgrammeLearnerRegistrationFormSignoff.jasper", params);
			doc.setData(bites);
			doc.setOriginalFname("Agreement_"+anIDNumber(companyLearners.getUser())+".pdf");
			//doc = js.createReportFromDBtoServletOutputStream("LPM-FM-015-SkillsProgrammeLearnerRegistrationFormNew.jasper", params,"Agreement_"+anIDNumber(companyLearners.getUser())+".pdf");
		} 		
 		return doc;
	}
}
