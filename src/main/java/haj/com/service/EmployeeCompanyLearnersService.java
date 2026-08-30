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

import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
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
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.OfoCodes;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.entity.ReviewCommitteeMeetingUsers;
import haj.com.entity.SDFCompany;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.ScheduledEventUsers;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.SmeQualifications;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.WorkPlaceApprovalSites;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.ProgressType;
import haj.com.entity.enums.SubmissionEnum;
import haj.com.entity.enums.TradeTypeEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.entity.lookup.DesignatedTradeLevel;
import haj.com.entity.lookup.Designation;
import haj.com.entity.lookup.LearnerMentorRatio;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.DesignatedTradeLevelService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

public class EmployeeCompanyLearnersService extends AbstractService {

	/** The dao. */
	private CompanyLearnersDAO dao = new CompanyLearnersDAO();
	private UsersService usersService = new UsersService();
	private RegisterService registerService = new RegisterService();
	private ConfigDocService configDocService = new ConfigDocService();
	private CompanyService companyService = new CompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private ActiveContractDetailService activeContractDetailService = new ActiveContractDetailService();
	private RegionService regionService;
	/** Instance of service level */
	private static EmployeeCompanyLearnersService companyLearnersService;
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private RolesService rolesService = new RolesService();
	private WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
	
	public static synchronized EmployeeCompanyLearnersService instance() {
		if (companyLearnersService == null) {
			companyLearnersService = new EmployeeCompanyLearnersService();
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
		return CompanyLearnersService.instance().resolveEverything(dao.findByKey(id), configDocProcessEnum);
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

		if (configDocProcess == ConfigDocProcessEnum.LearnerRegistrationByMerseta && entity != null && entity.getInterventionType() != null) {
			if (!entity.getInterventionType().getBusary()) {
				// System.out.println(entity.getInterventionType().getDescription());
				if (entity.getDocs() != null && entity.getDocs().size() > 3) {
					entity.getDocs().remove(4);
					entity.getDocs().remove(entity.getDocs().size() - 1);

				}
			}
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

		if (configDocProcess == ConfigDocProcessEnum.LearnerRegistrationByMerseta && entity != null && entity.getInterventionType() != null) {
			if (!entity.getInterventionType().getBusary()) {
				// System.out.println(entity.getInterventionType().getDescription());
				if (entity.getDocs() != null && entity.getDocs().size() > 3) {
					entity.getDocs().remove(4);
					entity.getDocs().remove(entity.getDocs().size() - 1);
				}
			}
		}

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
		List<Users> users = findRegionClinetServiceAdministrator(trainingProvider);

		if (users == null || users.size() == 0) {
			throw new Exception("No Region Client Service Administrator for the region");
		}
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
			throw new Exception("Provider cerror on registration");
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
					throw new Exception("The company is not workplace approved for this qualification");
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
		cl.setMersetaRegistration(true);
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
		//if (submitWorkflow) TasksService.instance().findFirstInProcessAndCreateTask(createUser, cl.getId(), CompanyLearners.class.getName(), ConfigDocProcessEnum.LearnerRegistrationByMerseta, false);
		TasksService.instance().findFirstInProcessAndCreateTask("", createUser, cl.getId(), cl.getClass().getName(), true, ConfigDocProcessEnum.LearnerRegistrationByMerseta, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, ""), users);

		if (cl.getInterventionType().getForm().matches("002")) {
			sendWorkBasedLearningProgrammeAgreement(createUser, entity, company, trainingProvider, cl);
		} else if (cl.getInterventionType().getForm().matches("015")) {
			sendLPMFM015Email(createUser, entity, company, trainingProvider, cl);
		}
	}
	
	public void checkSmeQualificationMentor(CompanyLearners cl, Qualification qualification, WorkPlaceApproval workplaceapproval)throws Exception {
		List<WorkPlaceApprovalSites> workPlaceApprovalSites = workPlaceApprovalService.findSitesByApproval(workplaceapproval);
		
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
				throw new Exception("Number of learners have exceeded mentor ration");
			}
		}else {
			throw new Exception("No available mentor(s) for this workplace approval");
		}
	}
	
	public int countCompanyLearnersBySme(SitesSme sitessme) throws Exception {
		return dao.countCompanyLearnersBySme(sitessme.getId());
	}
	
	/*public boolean checkSmeQualificationMentor(Qualification qualification, WorkPlaceApproval workplaceapproval)throws Exception {
		boolean valid = false;
		if(workplaceapproval != null) {
			List<WorkPlaceApprovalSites> workPlaceApprovalSites = workPlaceApprovalService.findSitesByApproval(workplaceapproval);		
			if(workPlaceApprovalSites.size() > 0) {
				valid = true;			
			}
		}
		return valid;
	}*/

	public boolean checkIfCompanyQualificationWorkplaceApproved(Company company, Qualification qualification) throws Exception {
		boolean isworkplaceapproved = false;
		WorkPlaceApprovalService wpas = new WorkPlaceApprovalService();
		isworkplaceapproved = wpas.checkIfWorkplaceApproved(companyService.findByKey(company.getId()), qualification, null, null, null);
		return isworkplaceapproved;
	}

	public void completeCompanyLearners(CompanyLearners cl, Users user, Tasks tasks) throws Exception {
		List<Users> users = findRegionClinetServiceAdministrator(cl.getCompany());

		if (users == null || users.size() == 0) {
			throw new Exception("No Region Client Service Administrator for the region");
		}
		
		cl.setStatus(ApprovalEnum.PendingApproval);
		dao.update(cl);

		uploadDocuments(cl, tasks, user);
		TasksService.instance().completeTask(tasks);
		TasksService.instance().findFirstInProcessAndCreateTask("", user, cl.getId(), cl.getClass().getName(), true, ConfigDocProcessEnum.LearnerRegistrationByMerseta, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, ""), users);
		if (cl.getInterventionType().getForm().matches("002")) {
			sendWorkBasedLearningProgrammeAgreement(user, cl.getUser(), cl.getEmployer(), cl.getCompany(), cl);
		} else if (cl.getInterventionType().getForm().matches("015")) {
			sendLPMFM015Email(user, cl.getUser(), cl.getEmployer(), cl.getCompany(), cl);
		}	
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

	public List<CompanyLearners> findCompanyLearnersByStatus(Long companyId, ApprovalEnum status) throws Exception {
		return dao.findCompanyLearnersByStatus(companyId, status);
	}

	public List<CompanyLearners> findCompanyLearnersByStatus(Long companyId, ApprovalEnum status, SubmissionEnum submissionEnum, Long companyLearnerParentID) throws Exception {
		return dao.findCompanyLearnersByStatus(companyId, status, submissionEnum, companyLearnerParentID);
	}

	public void completeCompanyLearnersToRegion(CompanyLearners companyLearners, Users user, Tasks tasks) throws Exception {

		List<Users> userList = findRegionClinetServiceAdministrator(companyLearners.getCompany());
		if (userList.size() == 0) {
			throw new Exception("No Client Service Administrator assigned to region");
		}
		uploadDocuments(companyLearners, tasks, user);
		companyLearners.setSubmissionEnum(SubmissionEnum.ForApproval);
		create(companyLearners);

		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
	}

	public void approveCompanyLearner(CompanyLearners entity, Users user, Tasks tasks) throws Exception {

		RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
		List<Users> users = hostingCompanyEmployeesService.findUserByRegionAndRole(rt.getRegion(), tasks.getProcessRole().getNextTaskRole());
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, users, false);

	}

	public void finalApproveWorkflow(CompanyLearners companyLearners, Users user, Tasks tasks, String documentBoxID) throws Exception {

		companyLearners.setStatus(ApprovalEnum.Approved);
		companyLearners.setRegistrationNumber(CompanyLearnersService.instance().generateCompanyLearnerRegNumber(companyLearners));
		companyLearners.setLearnerStatus(LearnerStatusEnum.Registered);
		companyLearners.setSubmissionEnum(SubmissionEnum.Completed);
		companyLearners.setApprovalDate(getSynchronizedDate());
		companyLearners.setDocumentBoxID(documentBoxID);
		if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
			activeContractDetailService.addTranchePaymentDetail(companyLearners, user, 0.25, TrancheEnum.TRANCHE_TWO, true);
		}
		WorkplaceMonitoring workplaceMonitoring = new WorkplaceMonitoring();
		workplaceMonitoring.setCompany(companyLearners.getEmployer());
		workplaceMonitoring.setUsers(user);
		WorkplaceMonitoringService workplaceMonitoringService = new WorkplaceMonitoringService();
		workplaceMonitoringService.requesteWorkflow(workplaceMonitoring, user);
		dao.update(companyLearners);

		List<Users> list = new ArrayList<>();
		list.add(user);
		//list.add(companyLearners.getCreateUser());
		//list.add(companyLearners.getUser());

		sendForOfficeUseOnlyForm(companyLearners, user);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, list, false);
		/*if(companyLearners.getInterventionType().getForm().matches("002")) {
			List<Users> userList = new ArrayList<>();
			userList.add(user);
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
			sendForOfficeUseOnlyForm(companyLearners, user);
		}else {
			TasksService.instance().completeTask(tasks);

			super.removeDuplicatesFromList(list);
			for (Users u : list) {
				sendApprovalEmail(companyLearners, u);
			}			
			// sendApprovalEmailToTheLearner(companyLearners);
			CompanyLearnersService.instance().createLearnerFileManagementWorkflow(companyLearners, user, null);
		}*/
	}
	
	public void rejectCompanyLearners(CompanyLearners entity, Users user, Tasks tasks, List<RejectReasons> rejectReasons) throws Exception {
		List<Users> users = new ArrayList<>();
		users.add(entity.getCreateUser());

		if (users == null || users.size() == 0) {
			throw new Exception("No Region Client Service Administrator for the region");
		}

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), CompanyLearners.class.getName(), rejectReasons, user, ConfigDocProcessEnum.LearnerRegistrationByMerseta);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		String emailMessage = "";
		String subject = "";
		String welcome = "The learner registration application for: #LEARNER_FIRST_NAME# #LEARNER_LAST_NAME# (#LEARNER_IDENTITY_NUMBER#, #LEARNER_EMAIL#) for #COMPANY_NAME# (#ENTITY_ID#) has not been approved. Please review the application.";
		String rsaIdPassport = anIDNumber(entity.getUser());
		
		welcome = welcome.replaceAll("#LEARNER_FIRST_NAME#", entity.getUser().getFirstName());
		welcome = welcome.replaceAll("#LEARNER_LAST_NAME#", entity.getUser().getLastName());
		welcome = welcome.replaceAll("#LEARNER_IDENTITY_NUMBER#", rsaIdPassport);
		welcome = welcome.replaceAll("#LEARNER_EMAIL#", entity.getUser().getEmail());
		welcome = welcome.replaceAll("#COMPANY_NAME#", entity.getEmployer().getCompanyName());
		welcome = welcome.replaceAll("#ENTITY_ID#", entity.getEmployer().getLevyNumber());
		// TasksService.instance().findFirstInProcessAndCreateRejectTask("", user,
		// entity.getId(), CompanyLearners.class.getName(), true,
		// ConfigDocProcessEnum.LearnerFileManagement, null, users);
		TasksService.instance().createTask(CompanyLearners.class.getName(), null, emailMessage, subject, welcome, user, entity.getId(), true, true, tasks, users, ConfigDocProcessEnum.LearnerRegistrationByMerseta, null);
		// TasksService.instance().completeTask(tasks);
		// TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks,
		// users);

		sendRejectEmail(entity, rejectReasons, entity.getCreateUser());

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
		// sendRejectEmailToTheLearner(companyLearners, rejectReasons);
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
		companyLearners.setStatus(ApprovalEnum.PendingFinalApproval);
		companyLearners.setApprovalDate(getSynchronizedDate());
		companyLearners.setRegistrationNumber(CompanyLearnersService.instance().generateCompanyLearnerRegNumber(companyLearners));
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
		List<Users> users = new ArrayList<>();
		users.add(tasks.getCreateUser());
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

	private String anIDNumber(Users user) {
		if (user.getRsaIDNumber() != null && user.getRsaIDNumber() != "" && !user.getRsaIDNumber().isEmpty()) {
			return user.getRsaIDNumber();
		} else if (user.getPassportNumber() != null && user.getPassportNumber() != "" && !user.getPassportNumber().isEmpty()) {
			return user.getPassportNumber();
		} else {
			return "N/A";
		}
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

	public void sendLPMFM015Email(Users createUser, Users learner, Company company, Company trainingProvider, CompanyLearners companylearners) throws Exception {
		
		String qualificationTitle = getCompanyLearnerStringQualification(companylearners);	

		String rsaIdPassport = anIDNumber(companylearners.getUser());

		String subject = "LEARNER REGISTRATION APPLICATION";

		String mssg = "<br/>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Dear #FirstName# #Surname#,</p>" 
							  + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the learner registration for #learnerFirstName# #learnerSurname# (#rsaIdPassport#) for the qualification/programme: #QualificationTitle#.</p>" 
							  + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Should you require any assistance or further information, kindly contact the Client Liaison Officer at " + getRegionString(trainingProvider) + "</p>" 
							  + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Client Services</p>" + "<br/>";

		mssg = mssg.replaceAll("#FirstName#", createUser.getFirstName());
		mssg = mssg.replaceAll("#Surname#", createUser.getLastName());
		mssg = mssg.replaceAll("#learnerFirstName#", companylearners.getUser().getFirstName());
		mssg = mssg.replaceAll("#learnerSurname#", companylearners.getUser().getLastName());
		mssg = mssg.replaceAll("#rsaIdPassport#", rsaIdPassport);
		mssg = mssg.replaceAll("#QualificationTitle#", qualificationTitle);
	
		GenericUtility.sendMail(createUser.getEmail(), subject, mssg, null);
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
	public List<CompanyLearners> findByUser(Users u) throws Exception {
		return dao.findByUser(u);
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
	public List<CompanyLearners> sortAndFilterSearchParent(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearners o where o.companyLearnersParent.id = :companyLearnerParentID ";
		return (List<CompanyLearners>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countSearchParent(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearners o where o.companyLearnersParent.id = :companyLearnerParentID ";
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
	
	public void sendWorkBasedLearningProgrammeAgreement(Users createUser, Users learner, Company company, Company trainingProvider, CompanyLearners companylearners) throws Exception {
		// WORK_BASED_LEARNING_PROGRAMME_AGREEMENT		
		String rsaIdPassport = anIDNumber(companylearners.getUser());
		String subject = "LEARNER REGISTRATION APPLICATION";
		String welcome = "<br/>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Dear #FirstName# #Surname#,</p>" 
								 + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA acknowledges the learner registration for #learnerFirstName# #learnerSurname# (#rsaIdPassport#) for the qualification/programme: (#QualificationCode#)#QualificationTitle#.</p>" 
								 + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Should you require any assistance or further information, kindly contact the Client Liaison Officer at " + getRegionString(trainingProvider) + "</p>" 
								 + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely,</p>" 
								 + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "merSETA Client Services</p>" + "<br/>";

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
		GenericUtility.sendMail(createUser.getEmail(), subject, welcome, null);
	}


	public void sendLearnerRejectionEmail(CompanyLearnersTransfer companyLearnersTransfer) {
		String subject = "Learner Transfer Application Rejection".toUpperCase();
		String msg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + companyLearnersTransfer.getCreateUser().getFirstName() + " " + companyLearnersTransfer.getCreateUser().getLastName() + " </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please note your learner transfer appliation has been " + "rejected for the following reason:" + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + companyLearnersTransfer.getRejectionNote() + "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "A notification has been sent to the CLO to conduct a " + "Site Visit and complete an investigation report. " + "The CLO will either recommend that transfer continues, " + "or transfer does not continue" + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA Team " + "</p>";
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

	public void sendTransferCLOApprovalEmail(CompanyLearnersTransfer companyLearnersTransfer) {
		List<Users> toList = new ArrayList<>();
		toList.add(companyLearnersTransfer.getCreateUser());
		toList.add(companyLearnersTransfer.getCompanyLearners().getUser());
		for (Users user : toList) {
			String subject = "Learner Transfer Application outcome".toUpperCase();
			String msg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + " </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be inform that the CLO recommend that the learner transfer application continues " + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA Team " + "</p>";
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

	public List<Users> findRegionClinetServiceAdministrator(Company company) throws Exception {
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(HAJConstants.CLIENT_SERVICE_ADMIN_ROLE_ID);
		list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}

	public List<Users> findRegionClinetServiceCoodinator(Company company) throws Exception {
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(HAJConstants.CLIENT_SERVICE_COORDINATOR_ROLE_ID);
		list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}

	public List<CompanyLearners> findByEmployer(long companyID) throws Exception {
		return checkProgress(dao.findByEmployer(companyID));
	}

	/*
	 * Find all CompanyLearners that expires on the current date
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearners> findTodayExpiredContracts() throws Exception {
		return dao.findTodayExpiredContracts();
	}


	public void sendRejectEmail(CompanyLearners companyLearners, List<RejectReasons> rejectReasons, Users u) throws Exception {
		Company company = companyService.findByKey(companyLearners.getCompany().getId());
		String accreditationNumber = "N/A";
		if (company != null && company.getAccreditationNumber() != null) {
			accreditationNumber = company.getAccreditationNumber();
		}
		String rsaIdPassport = anIDNumber(companyLearners.getUser());
		String QualificationString = "";
		Qualification qualification = getCompanyLearnerQualification(companyLearners);
		QualificationString = "(" + qualification.getCodeString() + ") " + qualification.getDescription();
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
		} else if (companylearners.getQualification() != null && companylearners.getInterventionType().getId() != HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			qualification = companylearners.getQualification();
		} else if (companylearners.getQualification() != null && companylearners.getInterventionType().getId() != HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
			qualification = new Qualification();
			qualification.setCode(0000);
			qualification.setDescription(companylearners.getNonCredidBearingDescription());
		}
		return qualification;
	}

	public String getCompanyLearnerStringQualification(CompanyLearners companylearners) throws Exception {
		// CompanyQualificationsService companyQualificationsService = new
		// CompanyQualificationsService();
		String fullDescription = "";
		if (companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			fullDescription = "(" + companylearners.getQualification().getCode() + ")" + companylearners.getQualification().getDescription();
		} else if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
			fullDescription = "(" + companylearners.getSkillsSet().getQualification().getCode() + ")" + companylearners.getSkillsSet().getQualification().getDescription();
		} else if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
			fullDescription = "(" + companylearners.getSkillsProgram().getQualification().getCode() + ")" + companylearners.getSkillsProgram().getQualification().getDescription();
		} else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			fullDescription = "(" + companylearners.getLearnership().getQualification().getCode() + ")" + companylearners.getLearnership().getQualification().getDescription();
		} else if (companylearners.getInterventionType().getId() == HAJConstants.NON_CREDIT_BEARING_SHORT_COURSE) {
			fullDescription = companylearners.getNonCredidBearingDescription();
		} else if (companylearners.getQualification() != null) {
			fullDescription = "(" + companylearners.getQualification().getCode() + ")" + companylearners.getQualification().getDescription();
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
}
