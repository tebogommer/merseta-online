package haj.com.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.TrainingProviderVerficationDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.entity.OfoCodes;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SDFCompany;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.ScheduledEventUsers;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CeritificateCollectionEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.CompetenceEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.GenerateAddEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.PercentageEnum;
import haj.com.entity.enums.QualificationTypeSelectionEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

public class TrainingProviderVerficationService extends AbstractService {
	/** The dao. */
	private TrainingProviderVerficationDAO   dao                              = new TrainingProviderVerficationDAO();
	private ConfigDocService                 configDocService                 = new ConfigDocService();
	private CompanyService                   companyService                   = new CompanyService();
	private AuditorMonitorReviewService      auditorMonitorReviewService      = new AuditorMonitorReviewService();
	private CompanyUsersService              companyUsersService              = new CompanyUsersService();
	private ScheduledEventService            scheduledEventService            = new ScheduledEventService();
	private SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
	private UnitStandardsService             unitStandardsService             = new UnitStandardsService();

	/**
	 * Get all TrainingProviderVerfication
	 * @author TechFinium
	 * @see TrainingProviderVerfication
	 * @return a list of {@link haj.com.entity.TrainingProviderVerfication}
	 * @throws Exception
	 * the exception
	 */
	public List<TrainingProviderVerfication> allTrainingProviderVerfication() throws Exception {
		return dao.allTrainingProviderVerfication();
	}

	/**
	 * Create or update TrainingProviderVerfication.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see TrainingProviderVerfication
	 */
	public void create(TrainingProviderVerfication entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update TrainingProviderVerfication.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see TrainingProviderVerfication
	 */
	public void update(TrainingProviderVerfication entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete TrainingProviderVerfication.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see TrainingProviderVerfication
	 */
	public void delete(TrainingProviderVerfication entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.TrainingProviderVerfication}
	 * @throws Exception
	 * the exception
	 * @see TrainingProviderVerfication
	 */
	public TrainingProviderVerfication findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	public void prepareNewRegistration(TrainingProviderVerfication entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.ProviderVerification));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.ProviderVerification, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.ProviderVerification, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	public void prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum configDocProcess, TrainingProviderVerfication entityDoc, TrainingProviderVerfication entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			if (processRoles != null && processRoles.getCompanyUserType() == null) {
				List<Doc> docs = DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess);
				for (Doc doc : docs) {
					doc.setEnableReupload(true);
				}
				entityDoc.setDocs(docs);
			} else {
				List<Doc> docs = DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles);
				for (Doc doc : docs) {
					doc.setEnableReupload(true);
				}
				entityDoc.setDocs(docs);
			}

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					Doc doc = new Doc(a);
					doc.setEnableReupload(true);
					entityDoc.getDocs().add(doc);
				});
			}
		} else {
			entityDoc.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					Doc doc = new Doc(a);
					doc.setEnableReupload(true);
					entityDoc.getDocs().add(doc);
				});
			}
		}
	}

	public void prepareNewLearnershipDevelopmentRegistrationDocuments(ConfigDocProcessEnum configDocProcess, TrainingProviderVerfication entityDoc, TrainingProviderVerfication entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			List<Doc> docs = DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId());
			for (Doc doc : docs) {
				doc.setEnableReupload(false);
			}
			entityDoc.setDocs(docs);

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					Doc doc = new Doc(a);
					doc.setEnableReupload(true);
					entityDoc.getDocs().add(doc);
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

	public void prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum configDocProcess, TrainingProviderVerfication entityDoc, TrainingProviderVerfication entity) throws Exception {

		List<Doc> ld = DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess);

		if (ld != null && ld.size() > 0) {
			entityDoc.setDocs(new ArrayList<>());
			ld.forEach(a -> {
				entityDoc.getDocs().add(a);
			});
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

	public void prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum configDocProcess, ScheduledEvent entity) throws Exception {
		entity.setDocs(new ArrayList<>());
		List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				entity.getDocs().add(new Doc(a));
			});
		}
	}

	public void prepareNewLearnershipDevelopmentRegistrationDocs(ConfigDocProcessEnum configDocProcess, ScheduledEvent entityDoc, ScheduledEvent entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			List<Doc> docs = DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId());
			for (Doc doc : docs) {
				doc.setEnableReupload(false);
			}
			entityDoc.setDocs(docs);

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					Doc doc = new Doc(a);
					doc.setEnableReupload(true);
					entityDoc.getDocs().add(doc);
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

	public Users resolveContactPerson(Company company) throws Exception {
		CompanyUsersService cus = new CompanyUsersService();
		Users               u   = cus.findCompanyContactPerson(company.getId());
		return u;
	}

	/**
	 * Find TrainingProviderVerfication by description.
	 * @author TechFinium
	 * @param desc
	 * the desc
	 * @return a list of {@link haj.com.entity.TrainingProviderVerfication}
	 * @throws Exception
	 * the exception
	 * @see TrainingProviderVerfication
	 */
	public List<TrainingProviderVerfication> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load TrainingProviderVerfication
	 * @param first
	 * from key
	 * @param pageSize
	 * no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingProviderVerfication}
	 * @throws Exception
	 * the exception
	 */
	public List<TrainingProviderVerfication> allTrainingProviderVerfication(int first, int pageSize) throws Exception {
		return dao.allTrainingProviderVerfication(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of TrainingProviderVerfication for lazy load
	 * @author TechFinium
	 * @return Number of rows in the TrainingProviderVerfication
	 * @throws Exception
	 * the exception
	 */
	public Long count() throws Exception {
		return dao.count(TrainingProviderVerfication.class);
	}

	/**
	 * Lazy load TrainingProviderVerfication with pagination, filter, sorting
	 * @author TechFinium
	 * @param class1
	 * TrainingProviderVerfication class
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
	 * @return a list of {@link haj.com.entity.TrainingProviderVerfication} containing data
	 * @throws Exception
	 * the exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> allTrainingProviderVerfication(Class<TrainingProviderVerfication> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolvelistCompanyLearners((List<TrainingProviderVerfication>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}

	private List<TrainingProviderVerfication> resolvelistCompanyLearners(List<TrainingProviderVerfication> list) throws Exception {
		for (TrainingProviderVerfication tpv : list) {
			tpv.setCompanyLearners(CompanyLearnersService.instance().findByKey(tpv.getCompanyLearners().getId()));
			if (tpv.getTrainingProvider() != null && tpv.getTrainingProvider().getAccreditationNumber() != null) {
				List<TrainingProviderApplication> lists = TrainingProviderApplicationService.instance().findByCompanyAndStatus(tpv.getTrainingProvider(), ApprovalEnum.Approved);
				if (lists != null && lists.size() > 0) {
					TrainingProviderApplication trainingProviderApplication = lists.get(0);
					if (trainingProviderApplication != null && trainingProviderApplication.getId() != null && trainingProviderApplication.getEtqa() != null && trainingProviderApplication.getEtqa().getDescription() != null) {
						tpv.getTrainingProvider().setTrainingProviderApplication(trainingProviderApplication);
					}
				}
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> allTrainingProviderVerfication(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderVerfication o where o.status = :status and o.createUser.id = :createUserID and o.companyLearners <> null ";
		return resolvelistCompanyLearners((List<TrainingProviderVerfication>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countTrainingProviderVerfication(Class<TrainingProviderVerfication> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderVerfication o where o.status = :status and o.createUser.id = :createUserID and o.companyLearners <> null ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> allTrainingProviderVerfication1(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderVerfication o where o.companyLearners <> null ";
		return resolvelistCompanyLearners((List<TrainingProviderVerfication>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countTrainingProviderVerfication1(Class<TrainingProviderVerfication> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderVerfication o where o.companyLearners <> null ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> allApprovedTrainingProviderVerfication(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderVerfication o where o.status = :status and o.companyLearners <> null ";
		return resolvelistCompanyLearners((List<TrainingProviderVerfication>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countApprovedTrainingProviderVerfication(Class<TrainingProviderVerfication> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderVerfication o where o.status = :status and o.companyLearners <> null ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> allApprovedTrainingProviderVerficationGenerated(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderVerfication o where o.status = :status and o.cetificateGenerated = :cetificateGenerated and o.companyLearners <> null ";
		return resolvelistCompanyLearners((List<TrainingProviderVerfication>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countApprovedTrainingProviderVerficationGenerated(Class<TrainingProviderVerfication> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderVerfication o where o.status = :status and o.cetificateGenerated = :cetificateGenerated and o.companyLearners <> null ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> allApprovedTrainingProviderVerficationGeneratedLearnership(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderVerfication o where o.status = :status and o.cetificateGenerated = :cetificateGenerated and o.companyLearners <> null and o.companyLearners.interventionType.interventionTypeEnum = :interventionTypeEnum ";
		return resolvelistCompanyLearners((List<TrainingProviderVerfication>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countApprovedTrainingProviderVerficationGeneratedLearnership(Class<TrainingProviderVerfication> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderVerfication o where o.status = :status and o.cetificateGenerated = :cetificateGenerated and o.companyLearners <> null and o.companyLearners.interventionType.interventionTypeEnum = :interventionTypeEnum ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> allTrainingProviderVerficationStatus(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderVerfication o where o.status = :status and o.verificationStatus = :verificationStatus and o.createUser.id = :createUserID and o.companyLearners <> null ";
		return resolvelistCompanyLearners((List<TrainingProviderVerfication>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countTrainingProviderVerficationStatus(Class<TrainingProviderVerfication> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderVerfication o where o.status = :status and o.verificationStatus = :verificationStatus and o.createUser.id = :createUserID and o.companyLearners <> null ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> allProviderVerfication(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :trainingProviderID and o.companyLearners <> null ";
		return resolvelistCompanyLearners((List<TrainingProviderVerfication>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countProviderVerfication(Class<TrainingProviderVerfication> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :trainingProviderID and o.companyLearners <> null ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Company> allCompany(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select distinct o.trainingProvider from TrainingProviderVerfication o where o.status = :status and o.createUser.id = :createUserID and o.companyLearners <> null ";
		return (List<Company>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countCompany(Class<Company> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(distinct o.trainingProvider) from TrainingProviderVerfication o where o.status = :status and o.createUser.id = :createUserID and o.companyLearners <> null ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<ScheduledEvent> allScheduledEvent(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select distinct o.scheduledEvent from TrainingProviderVerfication o where o.scheduledEvent.status = :status and o.trainingProvider.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		return (List<ScheduledEvent>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countScheduledEvent(Class<ScheduledEvent> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(distinct o.scheduledEvent) from TrainingProviderVerfication o where o.scheduledEvent.status = :status and o.trainingProvider.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Company> allCompanyVerification(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select distinct o.trainingProvider from TrainingProviderVerfication o where o.status = :status and o.companyLearners <> null and o.trainingProvider.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		return (List<Company>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countCompanyVerification(Class<Company> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(distinct o.trainingProvider) from TrainingProviderVerfication o where o.status = :status and o.companyLearners <> null and o.trainingProvider.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> allProviderVerficationScheduledEvent(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderVerfication o where o.scheduledEvent.id = :scheduledEventID and o.companyLearners <> null ";
		return resolvelistCompanyLearners((List<TrainingProviderVerfication>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countProviderVerficationScheduledEvent(Class<TrainingProviderVerfication> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderVerfication o where o.scheduledEvent.id = :scheduledEventID and o.companyLearners <> null ";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Get count of TrainingProviderVerfication for lazy load with filters
	 * @author TechFinium
	 * @param entity
	 * TrainingProviderVerfication class
	 * @param filters
	 * the filters
	 * @return Number of rows in the TrainingProviderVerfication entity
	 * @throws Exception
	 * the exception
	 */
	public int count(Class<TrainingProviderVerfication> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void completeWorkflowDataModel(ScheduledEvent scheduledEvent, Users user, Tasks tasks) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		// List<TrainingProviderVerfication>list = dao.findByTrainingProviderVerficationParentID(companyLearners.getTrainingProvider().getId());

		uploadDocuments(scheduledEvent, tasks, user);

		List<TrainingProviderVerfication> list = dao.findByScheduledEvent(scheduledEvent.getId());
		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			trainingProviderVerfication.setStatus(ApprovalEnum.Recommended);
			dataEntities.add(trainingProviderVerfication);
		}
		dao.updateBatch(dataEntities);
		scheduledEvent.setStatus(ApprovalEnum.Recommended);
		this.dao.update(scheduledEvent);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void createQualityAssurerTask(ScheduledEvent scheduledEvent, Users user, Tasks tasks) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		// List<TrainingProviderVerfication>list = dao.findByTrainingProviderVerficationParentID(companyLearners.getTrainingProvider().getId());
		Company     company  = companyService.findByKey(scheduledEvent.getCompany().getId());
		List<Users> userList = findRegionQualityAssuror(company);
		// List<TrainingProviderVerfication>list = findByProviderAnStatus(company.getId(), ApprovalEnum.PreApproved);
		if (userList.size() == 0) {
			throw new Exception("No Quality Assuror assigned to region");
		}

		uploadDocuments(scheduledEvent, tasks, user);

		List<TrainingProviderVerfication> list = dao.findByScheduledEvent(scheduledEvent.getId());
		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			trainingProviderVerfication.setStatus(ApprovalEnum.PendingApproval);
			dataEntities.add(trainingProviderVerfication);
		}
		dao.updateBatch(dataEntities);
		scheduledEvent.setStatus(ApprovalEnum.PendingApproval);
		this.dao.update(scheduledEvent);
		TasksService.instance().completeTask(tasks);
		TasksService.instance().findFirstInProcessAndCreateTask("", user, scheduledEvent.getId(), ScheduledEvent.class.getName(), true, ConfigDocProcessEnum.ProviderVerificationModeration, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.ProviderVerificationModeration), userList);
	}

	public void createAdministratorTask(ScheduledEvent scheduledEvent, Users user) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();

		List<TrainingProviderVerfication> list = dao.findByScheduledEvent(scheduledEvent.getId());
		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			trainingProviderVerfication.setStatus(ApprovalEnum.PendingFinalApproval);
			dataEntities.add(trainingProviderVerfication);
		}
		dao.updateBatch(dataEntities);
		scheduledEvent.setSignOffDate(getSynchronizedDate());
		scheduledEvent.setStatus(ApprovalEnum.PendingFinalApproval);
		this.dao.update(scheduledEvent);
		TasksService.instance().findFirstInProcessAndCreateTask("", user, scheduledEvent.getId(), ScheduledEvent.class.getName(), true, ConfigDocProcessEnum.ProviderVerificationModerationApproval, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.ProviderVerificationModerationApproval), null);
	}

	public void createTrainingProviderVerficationTask(TrainingProviderVerfication trainingProviderVerfication, Users user) throws Exception {
		List<Users> list = new ArrayList<>();
		list.add(trainingProviderVerfication.getCreateUser());
		trainingProviderVerfication.setStatus(ApprovalEnum.PendingFinalApproval);
		trainingProviderVerfication.setCreatedFinalApproval(true);
		trainingProviderVerfication.setVerificationStatus(ApprovalEnum.PendingApproval);
		trainingProviderVerfication.setCeritificateCollectionEnum(CeritificateCollectionEnum.PendingCollection);
		update(trainingProviderVerfication);
		TasksService.instance().findFirstInProcessAndCreateTask("", user, trainingProviderVerfication.getId(), TrainingProviderVerfication.class.getName(), true, ConfigDocProcessEnum.LEARNER_ASSESSMENT_VERIFICATION_FINAL_APPROVAL, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.LEARNER_ASSESSMENT_VERIFICATION_FINAL_APPROVAL), list);
	}

	public void createRejectApplication(ScheduledEvent scheduledEvent, Users user) throws Exception {
		List<IDataEntity>                 dataEntities = new ArrayList<>();
		List<TrainingProviderVerfication> list         = dao.findByScheduledEvent(scheduledEvent.getId());
		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			trainingProviderVerfication.setStatus(ApprovalEnum.NA);
			dataEntities.add(trainingProviderVerfication);
		}
		dao.updateBatch(dataEntities);
		scheduledEvent.setStatus(ApprovalEnum.Rejected);
		this.dao.update(scheduledEvent);
	}

	public void completeWorkflow(TrainingProviderVerfication companyLearners, Users user, Tasks tasks) throws Exception {
		String error = "";
		if (companyLearners.getDocs() != null) {
			for (Doc doc : companyLearners.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument() && doc.getConfigDoc().getReqImmediate() == tasks.getFirstInProcess()) {
					error += "Please ensure all documents are uplaoded";
					break;
				}
			}
		}
		if (error.length() > 0) throw new ValidationException(error);
		companyLearners.setVerificationStatus(ApprovalEnum.PendingFinalApproval);
		this.dao.update(companyLearners);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void approveModeration(TrainingProviderVerfication entity, Users user, Tasks tasks) throws Exception {
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(entity.getCompanyLearners().getId());
		if (SKILLS_PROGRAM_LIST.contains(cl.getInterventionType().getId()) || SKILLS_SET_LIST.contains(cl.getInterventionType().getId()) || cl.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			cl.setLearnerStatus(LearnerStatusEnum.Completed);
			cl.setStatus(ApprovalEnum.Completed);
			cl.setCertificateDate(getSynchronizedDate());
			cl.setDateLearnerCompleted(getSynchronizedDate());
			cl.setDateQualificationObtained(getSynchronizedDate());
		} else {
			cl.setLearnerStatus(LearnerStatusEnum.QualificationObtained);
			cl.setStatus(ApprovalEnum.Completed);
			cl.setCertificateDate(getSynchronizedDate());
			cl.setDateLearnerCompleted(getSynchronizedDate());
			cl.setDateQualificationObtained(getSynchronizedDate());
		}
		if (entity.getModerationDate() != null) {
			cl.setCompletionDate(entity.getModerationDate());
		}
		cl.setCertificateDate(entity.getApprovalDate());
		cl.setDateQualificationObtained(entity.getApprovalDate());
		// cl.setCertificateNumber(entity.getCertificateNumber());
		CompanyLearnersService.instance().update(cl);

		entity.setStatus(ApprovalEnum.Approved);
		entity.setVerificationStatus(ApprovalEnum.Approved);
		// entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
		List<Users> list       = new ArrayList<>();
		SDFCompany  sDFCompany = SDFCompanyService.instance().locateFirstPrimarySDF(cl.getEmployer());
		if (sDFCompany != null && sDFCompany.getSdf() != null) {
			list.add(sDFCompany.getSdf());
		}
		if (cl.getTrainingProviderApplication() != null) {
			TrainingProviderApplication tp = TrainingProviderApplicationService.instance().findByKey(cl.getTrainingProviderApplication().getId());
			list.add(tp.getUsers());
		}
		for (Users u : list) {
			sendApprovalEmalAndLetters(entity, u, user);
		}
	}

	public void completeCollectionWorkflow(ScheduledEvent scheduledEvent, Users user, Tasks tasks) throws Exception {
		this.dao.update(scheduledEvent);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void completeCollectionWorkflow(TrainingProviderVerfication entity, Users user, Tasks tasks) throws Exception {
		this.dao.update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void completeWorkflowForModeration(TrainingProviderVerfication entity, Users user, Tasks tasks) throws Exception {

		entity.setStatus(ApprovalEnum.PreApproved);
		create(entity);
		int count = dao.countTrainingProviderVerficationByStatus(entity.getTrainingProvider().getId(), ApprovalEnum.PendingApproval);

		if (count > 0) {
			TasksService.instance().completeTask(tasks);
		} else if (count == 0) {

			Company                           company  = companyService.findByKey(entity.getTrainingProvider().getId());
			List<Users>                       userList = findRegionQualityAssuror(company);
			List<TrainingProviderVerfication> list     = findByProviderAnStatus(company.getId(), ApprovalEnum.PreApproved);
			if (userList.size() == 0) {
				throw new Exception("No Quality Assuror assigned to region");
			}
			TrainingProviderVerfication trainingProviderVerficationParent = new TrainingProviderVerfication();
			trainingProviderVerficationParent.setCreateUser(user);
			trainingProviderVerficationParent.setTrainingProvider(company);
			dao.create(trainingProviderVerficationParent);
			for (TrainingProviderVerfication tpv : list) {
				tpv.setTrainingProviderVerficationParent(trainingProviderVerficationParent);
				dao.update(tpv);
			}
			// Patrick
			setLearnersToModerate(companyService.findByKey(trainingProviderVerficationParent.getTrainingProvider().getId()));
			String description = "";
			Long   id          = trainingProviderVerficationParent.getId();

			String               className            = TrainingProviderVerfication.class.getName();
			ConfigDocProcessEnum configDocProcessEnum = ConfigDocProcessEnum.ProviderVerificationModeration;
			auditorMonitorReviewService.prepNewReview(className, id, ConfigDocProcessEnum.ProviderVerificationModeration);
			TasksService.instance().findFirstInProcessAndCreateTask(description, user, id, className, true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, configDocProcessEnum.getTaskDescription()), userList);
			TasksService.instance().completeTask(tasks);
		} else {
			throw new Exception("Error when approving");
		}
	}

	public void completeWorkflowToQualityAssuror(TrainingProviderVerfication entity, Users user, Tasks tasks) throws Exception {
		uploadDocuments(entity, tasks, user);

		Company company = companyService.findByKey(entity.getTrainingProvider().getId());
		if (company == null || company.getId() == null) {
			throw new Exception("No Training provider");
		} else {
			List<Users> userList = findRegionQualityAssuror(company);
			if (userList.size() == 0) {
				throw new Exception("No Quality Assuror assigned to region");
			}
			entity.setStatus(ApprovalEnum.PendingApproval);
			dao.update(entity);
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}
		// TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void createWorkflowToQualityAssuror(Company company, Users user, Tasks task, List<TrainingProviderVerfication> list, boolean createParent, ScheduledEvent scheduledEvent) throws Exception {

		// Company company = companyService.findByKey(entity.getId());
		if (company == null || company.getId() == null) {
			throw new Exception("No Training provider");
		} else {
			List<Users> userList = findRegionQualityAssuror(company);
			if (userList.size() == 0) {
				throw new Exception("No Quality Assuror assigned to region");
			}
			if (scheduledEvent.getDocs().size() == 0) {
				throw new Exception("Please upload the required documents");
			}

			ScheduledEventService scheduledEventService = new ScheduledEventService();

			scheduledEvent.setCompany(company);
			scheduledEvent.setUser(user);
			scheduledEvent.setStatus(ApprovalEnum.PendingApproval);
			scheduledEventService.create(scheduledEvent);

			List<IDataEntity> docsDataEntities = new ArrayList<IDataEntity>();
			if (scheduledEvent.getDocs() != null) {
				for (Doc doc : scheduledEvent.getDocs()) {
					if (doc.isRequired()) {
						doc.setCompany(null);
						doc.setVersionNo(1);
						doc.setUsers(user);
						doc.setTargetKey(scheduledEvent.getId());
						doc.setTargetClass(ScheduledEvent.class.getName());
						docsDataEntities.add(doc);
						if (doc.getData() != null) {
							docsDataEntities.add(new DocByte(doc.getData(), doc));
							docsDataEntities.add(new DocumentTracker(doc, user, new java.util.Date(), DocumentTrackerEnum.Upload));
						} else {
							throw new Exception("Please upload required document ");
						}
					}
				}
			}

			if (docsDataEntities.size() > 0) {
				dao.createBatch(docsDataEntities);
			}
			// uploadDocuments(scheduledEvent, task, user);
			/*
			 * int answer = (int) Math.round(list.size()*(0.1)); if(answer <= 0) { answer =1; }
			 * 
			 * Random randomGenerator = new Random(); for(int x=0;x<answer;x++) { int index = randomGenerator.nextInt(answer); TrainingProviderVerfication trainingProviderVerfication = list.get(index); if(trainingProviderVerfication.getForModeration()!=null && trainingProviderVerfication.getForModeration()) { x = x-1; }else { trainingProviderVerfication.setForModeration(true); } }
			 */

			for (TrainingProviderVerfication tpv : list) {
				tpv.setCetificateGenerated(false);
				tpv.setStatus(ApprovalEnum.PendingApproval);
				tpv.setScheduledEvent(scheduledEvent);
				dao.update(tpv);
			}
			// auditorMonitorReviewService.prepNewReview(scheduledEvent.getClass().getName(), scheduledEvent.getId(), ConfigDocProcessEnum.ProviderVerificationModeration);
			TasksService.instance().findFirstInProcessAndCreateTask("", user, scheduledEvent.getId(), ScheduledEvent.class.getName(), true, ConfigDocProcessEnum.ProviderVerificationModeration, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.ProviderVerificationModeration), userList);
		}
	}

	public void createWorkflowToQualityAssuror(TrainingProviderVerfication entity, Users user) throws Exception {
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getData() != null) {

				} else {
					throw new Exception("Please upload required document ");
				}
			}
		}
		CompanyLearners cl      = CompanyLearnersService.instance().findByKey(entity.getCompanyLearners().getId());
		Company         company = cl.getCompany();
		if (company == null || company.getId() == null) {
			throw new Exception("No Training provider");
		} else {
			List<Users> userList = findRegionQualityAssuror(company);
			if (userList.size() == 0) {
				throw new Exception("No Quality Assuror assigned to region");
			}
			entity.setStatus(ApprovalEnum.PendingApproval);
			dao.update(entity);
			TasksService.instance().findFirstInProcessAndCreateTask("", user, entity.getId(), TrainingProviderVerfication.class.getName(), true, ConfigDocProcessEnum.LEARNER_ASSESSMENT_VERIFICATION, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.LEARNER_ASSESSMENT_VERIFICATION), userList);
		}
	}

	public void createWorkflow(TrainingProviderVerfication entity, Users user) throws Exception {
		List<Users> userList = new ArrayList<>();
		userList.add(entity.getCreateUser());
		entity.setVerificationStatus(ApprovalEnum.PendingApproval);
		entity.setCeritificateCollectionEnum(CeritificateCollectionEnum.PendingCollection);
		entity.setCetificateGenerated(true);
		dao.update(entity);
		TasksService.instance().findFirstInProcessAndCreateTask("", user, entity.getId(), TrainingProviderVerfication.class.getName(), true, ConfigDocProcessEnum.ASSESSMENT_VERIFICATION, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.ASSESSMENT_VERIFICATION), userList);
	}

	public void completeWorkflowToQualityAssuror(Company entity, Users user, Tasks task, List<TrainingProviderVerfication> list, PercentageEnum percentageEnum) throws Exception {

		Company company = companyService.findByKey(entity.getId());
		if (company == null || company.getId() == null) {
			throw new Exception("No Training provider");
		} else {
			List<Users> userList = findRegionQualityAssuror(company);
			if (userList.size() == 0) {
				throw new Exception("No Quality Assuror assigned to region");
			}
			for (TrainingProviderVerfication tpv : list) {
				tpv.setStatus(ApprovalEnum.PendingFinalApproval);
				dao.update(tpv);
			}
			if (task != null) {
				TasksService.instance().completeTask(task);
			}
			// setLearnersForModerate(entity, percentageEnum, list);
			TasksService.instance().findFirstInProcessAndCreateTask("", user, entity.getId(), Company.class.getName(), true, ConfigDocProcessEnum.ProviderVerificationModeration, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.ProviderVerificationModeration), userList);
		}
	}

	public void completeWorkflowFinal(TrainingProviderVerfication entity, Users user, Tasks tasks) throws Exception {
		this.dao.update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void approveWorkflow(Company company, Users user, Tasks tasks, ScheduledEvent scheduledEvent) throws Exception {
		Users                             u            = tasks.getCreateUser();
		List<IDataEntity>                 dataEntities = new ArrayList<>();
		List<TrainingProviderVerfication> list         = findByProviderAnStatus(company.getId(), ApprovalEnum.PendingApproval);
		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			trainingProviderVerfication.setStatus(ApprovalEnum.PreApproved);
			dataEntities.add(trainingProviderVerfication);
		}
		dao.updateBatch(dataEntities);
		TasksService.instance().completeTask(tasks);
		sendReviewDateEmail(company, user, scheduledEvent.getFromDateTime(), u);

	}

	public void approveModerationWorkflow(ScheduledEvent entity, Users activeUser, Tasks tasks) throws Exception {
		List<IDataEntity>                 dataEntities = new ArrayList<>();
		List<TrainingProviderVerfication> list         = dao.findByScheduledEvent(entity.getId());
		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			trainingProviderVerfication.setStatus(ApprovalEnum.PendingInverstigation);
			dataEntities.add(trainingProviderVerfication);
		}
		dao.updateBatch(dataEntities);
		entity.setStatus(ApprovalEnum.PendingInverstigation);
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
		/*
		 * patrick setLearnersForModerate(entity.getPercentageEnum(), list);
		 */

		auditorMonitorReviewService.prepNewReview(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.ProviderVerificationModeration);
		sendReviewDateEmail(entity.getCompany(), activeUser, entity.getFromDateTime(), entity.getUser());
	}

	public void finalApproveModeration(ScheduledEvent entity, Users user, Tasks tasks) throws Exception {
		boolean                           createtask   = false;
		List<IDataEntity>                 dataEntities = new ArrayList<>();
		List<TrainingProviderVerfication> list         = dao.findByScheduledEvent(entity.getId());
		List<Users>                       userList     = new ArrayList<>();
		userList.add(entity.getUser());
		// userList.addAll(companyUsersService.findUsersByCompanyType(entity.getCompany().getId(), ConfigDocProcessEnum.TP));
		if (userList.size() == 0) {
			throw new Exception("No Training Provider assigned for this application ");
		}

		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			trainingProviderVerfication.setStatus(ApprovalEnum.Approved);
			trainingProviderVerfication.setApprovalDate(getSynchronizedDate());
			trainingProviderVerfication.setCetificateGenerated(false);

			CompanyLearners cl = CompanyLearnersService.instance().findByKey(trainingProviderVerfication.getCompanyLearners().getId());

			if (cl.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
				trainingProviderVerfication.setGenerateAddEnum(GenerateAddEnum.GenerateCertificate);
				generateCertificateNumber(trainingProviderVerfication);
				dao.update(trainingProviderVerfication);
				createtask = true;
				cl.setLearnerStatus(LearnerStatusEnum.QualificationObtained);
				cl.setStatus(ApprovalEnum.Completed);
				cl.setCertificateDate(getSynchronizedDate());
				cl.setDateLearnerCompleted(getSynchronizedDate());
				cl.setDateQualificationObtained(getSynchronizedDate());
				cl.setCertificateNumber(trainingProviderVerfication.getCertificateNumber());
				CompanyLearnersService.instance().update(cl);
				TasksService.instance().findFirstInProcessAndCreateTask("", user, trainingProviderVerfication.getId(), TrainingProviderVerfication.class.getName(), true, ConfigDocProcessEnum.LEARNER_ASSESSMENT_CERTIFICATE_UPLOAD, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.LEARNER_ASSESSMENT_CERTIFICATE_UPLOAD), null);
			} else {
				trainingProviderVerfication.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails);
				// generateCertificateNumber(trainingProviderVerfication);
				dao.update(trainingProviderVerfication);

				cl.setLearnerStatus(LearnerStatusEnum.Completed);
				cl.setStatus(ApprovalEnum.Completed);
				cl.setCertificateDate(getSynchronizedDate());
				cl.setDateQualificationObtained(getSynchronizedDate());
				cl.setDateLearnerCompleted(getSynchronizedDate());
				CompanyLearnersService.instance().update(cl);
			}
			/*
			 * if(cl.getInterventionType().getId() == HAJConstants.SKILLS_PROGRAM || cl.getInterventionType().getId() == HAJConstants.SKILLS_SET || cl.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) { trainingProviderVerfication.setGenerateAddEnum(GenerateAddEnum.AddCertificateDetails); //generateCertificateNumber(trainingProviderVerfication); dao.update(trainingProviderVerfication);
			 * 
			 * cl.setLearnerStatus(LearnerStatusEnum.Completed); cl.setCertificateDate(getSynchronizedDate()); cl.setDateQualificationObtained(getSynchronizedDate()); cl.setDateLearnerCompleted(getSynchronizedDate()); CompanyLearnersService.instance().update(cl); }else {
			 * 
			 * trainingProviderVerfication.setGenerateAddEnum(GenerateAddEnum.GenerateCertificate); generateCertificateNumber(trainingProviderVerfication); dao.update(trainingProviderVerfication);
			 * 
			 * cl.setLearnerStatus(LearnerStatusEnum.QualificationObtained); cl.setCertificateDate(getSynchronizedDate()); cl.setDateLearnerCompleted(getSynchronizedDate()); cl.setDateQualificationObtained(getSynchronizedDate()); cl.setCertificateNumber(trainingProviderVerfication.getCertificateNumber()); CompanyLearnersService.instance().update(cl); TasksService.instance().findFirstInProcessAndCreateTask("", user, trainingProviderVerfication.getId(), TrainingProviderVerfication.class.getName(),
			 * true, ConfigDocProcessEnum.LEARNER_ASSESSMENT_CERTIFICATE_UPLOAD, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.LEARNER_ASSESSMENT_CERTIFICATE_UPLOAD), null); }
			 */

			// dataEntities.add(cl);
			// dataEntities.add(trainingProviderVerfication);
			sendApprovalEmalAndLetters(trainingProviderVerfication, entity.getUser(), user);
		}
		dao.updateBatch(dataEntities);
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);

		if (createtask) {
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		} else {
			TasksService.instance().completeTask(tasks);
		}
	}

	public void rejectWorkflow(TrainingProviderVerfication entity, Users user, Tasks tasks, ArrayList<RejectReasons> rejectReasons) throws Exception {
		entity.setStatus(ApprovalEnum.Rejected);
		dao.update(entity);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), TrainingProviderVerfication.class.getName(), rejectReasons, user, ConfigDocProcessEnum.ProviderVerification);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}

		List<Users> users = new ArrayList<>();
		users.add(entity.getCreateUser());
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, users);
		// TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
	}

	public void rejectTrainingProviderverfication(TrainingProviderVerfication entity, Users user, Tasks tasks, ArrayList<RejectReasons> rejectReasons) throws Exception {
		entity.setVerificationStatus(ApprovalEnum.Rejected);
		dao.update(entity);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), TrainingProviderVerfication.class.getName(), rejectReasons, user, ConfigDocProcessEnum.ProviderVerification);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		List<Users> users = new ArrayList<>();
		users.add(entity.getCreateUser());
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, users);
	}

	public void rejectWorkfloToHoldingRoom(TrainingProviderVerfication entity, Users user, Tasks tasks, ArrayList<RejectReasons> rejectReasons) throws Exception {
		entity.setStatus(ApprovalEnum.NA);
		dao.update(entity);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), TrainingProviderVerfication.class.getName(), rejectReasons, user, ConfigDocProcessEnum.ProviderVerification);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}

		List<Users> users = new ArrayList<>();
		users.add(entity.getCreateUser());
		TasksService.instance().completeTask(tasks);
	}

	public void rejectModetationWorkflow(Company entity, Users user, Tasks tasks, ArrayList<RejectReasons> rejectReasons) throws Exception {
		List<Users> users = new ArrayList<>();
		users.add(tasks.getCreateUser());
		List<TrainingProviderVerfication> list = findByProviderAnStatus(entity.getId(), ApprovalEnum.PendingApproval);

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), TrainingProviderVerfication.class.getName(), rejectReasons, user, ConfigDocProcessEnum.ProviderVerificationModeration);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		TasksService.instance().completeTask(tasks);

		String emailMessage = "";
		String subject      = "";
		String description  = "";
		TasksService.instance().createTask(Company.class.getName(), null, emailMessage, subject, description, user, entity.getId(), true, true, tasks, users, ConfigDocProcessEnum.ProviderVerification, null);
	}

	public void upholdWorkflow(TrainingProviderVerfication entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Uphold);
		dao.update(entity);
		// TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void finalApproveWorkflow(TrainingProviderVerfication entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void finalApproveTrainingProviderverfication(TrainingProviderVerfication entity, Users user, Tasks tasks) throws Exception {
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(entity.getCompanyLearners().getId());
		if (SKILLS_PROGRAM_LIST.contains(cl.getInterventionType().getId()) || SKILLS_SET_LIST.contains(cl.getInterventionType().getId()) || cl.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			cl.setLearnerStatus(LearnerStatusEnum.Completed);
			cl.setCertificateDate(getSynchronizedDate());
			cl.setDateQualificationObtained(getSynchronizedDate());
		} else {
			cl.setLearnerStatus(LearnerStatusEnum.QualificationObtained);
			cl.setCertificateDate(entity.getCetificateGeneratedDate());
			cl.setDateQualificationObtained(entity.getCetificateGeneratedDate());
			cl.setCertificateNumber(entity.getCertificateNumber());
		}

		CompanyLearnersService.instance().update(cl);

		entity.setStatus(ApprovalEnum.Approved);
		entity.setVerificationStatus(ApprovalEnum.Approved);
		// entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
	}

	public void approveWorkflowByQA(TrainingProviderVerfication entity, Users user, Tasks tasks) throws Exception {
		if (entity.getGenerateAddEnum() == GenerateAddEnum.AddCertificateDetails) {
			entity.setCetificateGenerated(false);
			entity.setVerificationStatus(ApprovalEnum.NA);
			entity.setStatus(ApprovalEnum.Approved);
			entity.setApprovalDate(getSynchronizedDate());
			// generateCertificateNumber(entity);
			dao.update(entity);
			TasksService.instance().completeTask(tasks);
			// createWorkflow(entity, user);
			approveModeration(entity, user, tasks);
		} else {
			entity.setCetificateGenerated(false);
			entity.setVerificationStatus(ApprovalEnum.NA);
			entity.setStatus(ApprovalEnum.Approved);
			entity.setApprovalDate(getSynchronizedDate());
			generateCertificateNumber(entity);
			dao.update(entity);
			TasksService.instance().completeTask(tasks);
		}

		/*
		 * else { ScheduledEventService scheduledEventService = new ScheduledEventService(); ScheduledEvent scheduledEvent = new ScheduledEvent(); scheduledEvent.setCompany(entity.getTrainingProvider()); scheduledEvent.setUser(user); scheduledEvent.setStatus(ApprovalEnum.Approved); scheduledEventService.create(scheduledEvent); entity.setScheduledEvent(scheduledEvent); dao.update(entity); }
		 */
	}

	public void uploadCertificateCopyDocuments(TrainingProviderVerfication entity, Users user, Tasks tasks) throws Exception {
		uploadDocuments(entity, tasks, user);
		entity.setVerificationStatus(ApprovalEnum.PendingFinalApproval);
		update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void uploadCertificateCopyDocument(TrainingProviderVerfication entity, Users user, Tasks tasks) throws Exception {
		uploadDocuments(entity, tasks, user);
		update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void finalApprove(TrainingProviderVerfication entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Completed);
		dao.update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void finalRejectWorkflow(TrainingProviderVerfication companyLearners, Users user, Tasks tasks, ArrayList<RejectReasons> rejectReasons) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), TrainingProviderVerfication.class.getName(), rejectReasons, user, ConfigDocProcessEnum.ProviderVerification);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}

		TasksService.instance().completeTask(tasks);
	}

	public void finalRejectWorkflowMerseta(TrainingProviderVerfication trainingProviderVerfication, Users user, Tasks tasks, ArrayList<RejectReasons> rejectReasons) throws Exception {
		CompanyLearners companyLearners = CompanyLearnersService.instance().findByKey(trainingProviderVerfication.getCompanyLearners().getId());
		companyLearners.setLearnerStatus(LearnerStatusEnum.Registered);
		CompanyLearnersService.instance().update(companyLearners);

		trainingProviderVerfication.setStatus(ApprovalEnum.Rejected);
		trainingProviderVerfication.setApprovalDate(getSynchronizedDate());
		dao.update(trainingProviderVerfication);

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), TrainingProviderVerfication.class.getName(), rejectReasons, user, ConfigDocProcessEnum.ProviderVerification);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		TasksService.instance().completeTask(tasks);
	}

	public void setFirstVisitDate(TrainingProviderVerfication workplaceapproval, Users sessionUser) throws Exception {
		workplaceapproval.setDateForVisitProvided(true);
		workplaceapproval.setReviewUser(sessionUser);
		update(workplaceapproval);
		// addNewWorkPlaceApprovalVisitDateLog(workplaceapproval, sessionUser, "System Generated: First Entry", true, null, workplaceapproval.getReviewDate(), null);
		// emails to be added
		// close task for the session user and create a new one with the due date as the new date
		// sendReviewDateEmail(workplaceapproval, sessionUser);
	}

	public void setFirstVisitDate(TrainingProviderVerfication trainingProviderVerficationParent, Users activeUser, ScheduledEvent scheduledEvent, Company taskCompany) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		scheduledEvent.setTargetKey(taskCompany.getId());
		scheduledEvent.setTargetClass(TrainingProviderVerfication.class.getName());
		scheduledEvent.setUser(activeUser);
		dataEntities.add(scheduledEvent);
		if (scheduledEvent.getId() == null) {
			dataEntities.add(new ScheduledEventUsers(scheduledEvent, activeUser));
			dao.createBatch(dataEntities);
		} else {
			dao.update(scheduledEvent);
		}

		List<TrainingProviderVerfication> list = findByProviderAnStatus(taskCompany.getId(), ApprovalEnum.PendingApproval);

		// sendReviewDateEmail(taskCompany, activeUser, scheduledEvent.getFromDateTime(), taskCompany.getContactPerson());
		// Mthombeni
		// setLearnersToModerate(trainingProviderVerficationParent.getTrainingProvider());
	}

	private List<TrainingProviderVerfication> findByProviderAnStatus(Long id, ApprovalEnum approvalEnum) throws Exception {
		List<TrainingProviderVerfication> list = dao.findByProviderAnStatus(id, approvalEnum);
		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			resolveTrainingProviderVerfication(trainingProviderVerfication);
		}
		return list;
	}

	private void resolveTrainingProviderVerfication(TrainingProviderVerfication trainingProviderVerfication) throws Exception {
		trainingProviderVerfication.setCompanyLearners(CompanyLearnersService.instance().findByKey(trainingProviderVerfication.getCompanyLearners().getId()));
	}

	public void updateReviewDateForApproval(TrainingProviderVerfication trainingProviderVerficationParent, Users activeUser, ScheduledEvent scheduledEvent, Company taskCompany) throws Exception {
		scheduledEvent.setUser(activeUser);
		dao.update(scheduledEvent);
		List<TrainingProviderVerfication> list = findByProviderAnStatus(taskCompany.getId(), ApprovalEnum.PendingApproval);

		// sendReviewDateEmail(taskCompany, activeUser, scheduledEvent.getFromDateTime(), taskCompany.getContactPerson());
	}

	private void setLearnersToModerate(Company taskCompany) throws Exception {

		int count = dao.countByStatusAndProvider(taskCompany.getId(), ApprovalEnum.PreApproved);

		int answer = (int) Math.round(count * 0.1);
		if (answer == 0) {
			answer = 1;
		}

		List<IDataEntity>                 dataEntities = new ArrayList<>();
		List<TrainingProviderVerfication> list         = findByProviderAnStatusTenPercent(taskCompany.getId(), ApprovalEnum.PreApproved, answer);
		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			trainingProviderVerfication.setForModeration(true);
			dataEntities.add(trainingProviderVerfication);
			auditorMonitorReviewService.prepNewReview(trainingProviderVerfication.getClass().getName(), trainingProviderVerfication.getId(), ConfigDocProcessEnum.ProviderVerification);
		}
		dao.updateBatch(dataEntities);
	}

	private void setLearnersForModerate(PercentageEnum percentageEnum, List<TrainingProviderVerfication> list) throws Exception {

		int answer = (int) Math.round(list.size() * (percentageEnum.getPercentage() / 100));
		if (answer <= 0) {
			answer = 1;
		}

		List<IDataEntity> dataEntities    = new ArrayList<>();
		Random            randomGenerator = new Random();
		for (int x = 0; x < answer; x++) {
			int                         index                       = randomGenerator.nextInt(answer);
			TrainingProviderVerfication trainingProviderVerfication = list.get(index);
			if (trainingProviderVerfication.getForModeration() != null && trainingProviderVerfication.getForModeration()) {
				x = x - 1;
			} else {
				trainingProviderVerfication.setForModeration(true);
				dataEntities.add(trainingProviderVerfication);
				auditorMonitorReviewService.prepNewReview(trainingProviderVerfication.getClass().getName(), trainingProviderVerfication.getId(), ConfigDocProcessEnum.ProviderVerification);
			}
		}

		dao.updateBatch(dataEntities);
	}

	public void createLearnersForModerate(ScheduledEvent scheduledEvent) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();

		List<TrainingProviderVerfication> list   = dao.findByScheduledEvent(scheduledEvent.getId());
		int                               answer = (int) Math.round(list.size() * (scheduledEvent.getPercentageEnum().getPercentage() / 100));
		if (answer <= 0) {
			answer = 1;
		}

		Random randomGenerator = new Random();
		for (int x = 0; x < answer; x++) {
			int                         index                       = randomGenerator.nextInt(answer) + 1;
			TrainingProviderVerfication trainingProviderVerfication = list.get(index);
			if (trainingProviderVerfication.getForModeration() != null && trainingProviderVerfication.getForModeration()) {
				x = x - 1;
			} else {
				trainingProviderVerfication.setForModeration(true);
				dataEntities.add(trainingProviderVerfication);
				// auditorMonitorReviewService.prepNewReview(trainingProviderVerfication.getClass().getName(), trainingProviderVerfication.getId(), ConfigDocProcessEnum.ProviderVerification);
			}
		}
		scheduledEvent.setLearnerSetForModeration(true);
		dataEntities.add(scheduledEvent);
		dao.updateBatch(dataEntities);
	}

	public void createLearnersForModerateNew(ScheduledEvent scheduledEvent) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		Long              tpvCount     = dao.findByScheduledEventCount(scheduledEvent.getId());
		int               answer       = (int) Math.round(tpvCount * (scheduledEvent.getPercentageEnum().getPercentage() / 100));
		if (answer <= 0) {
			answer = 1;
		}
		List<TrainingProviderVerfication> list         = dao.findByScheduledEventRandom(scheduledEvent.getId(), answer);
		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			trainingProviderVerfication.setForModeration(true);
			dataEntities.add(trainingProviderVerfication);			
		}
		scheduledEvent.setLearnerSetForModeration(true);
		dataEntities.add(scheduledEvent);
		dao.updateBatch(dataEntities);
	}

	// generate random centroid Ids -> these Ids can be used to retrieve data from the data Points generated
	// simply we are picking up random items from the data points
	// in this case all the random numbers are unique
	// it provides the necessary number of unique and random centroids
	private static ArrayList<Integer> randomCentroids(int min, int max, int numCentroids) throws Exception {
		Random             random = new Random();
		ArrayList<Integer> values = new ArrayList<Integer>();
		int                num    = -1;
		int                count  = 0;
		do {
			num = random.nextInt(max - min + 1) + min;
			values.add(num);
			int index = values.size() - 1;
			if (unique(values, num, numCentroids)) {
				count++;
			} else {
				values.remove(index);
			}
		} while (!(count == numCentroids));
		return values;
	}

	private static boolean unique(ArrayList<Integer> arr, int num, int numCentroids) throws Exception {
		boolean status = true;
		int     count  = 0;
		for (Integer i : arr) {
			if (i == num) {
				count++;
			}
		}
		if (count == 1) {
			status = true;
		} else if (count > 1) {
			status = false;
		}
		return status;
	}

	private List<TrainingProviderVerfication> findByProviderAnStatusTenPercent(Long id, ApprovalEnum preapproved, int answer) throws Exception {
		List<TrainingProviderVerfication> list = dao.findByProviderAnStatusTenPercent(id, ApprovalEnum.PreApproved, answer);
		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			resolveTrainingProviderVerfication(trainingProviderVerfication);
		}
		return list;
	}

	public List<Users> findRegionQualityAssuror(Company company) throws Exception {
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		RolesService                   rolesService                   = new RolesService();
		List<Users>                    list                           = new ArrayList<>();
		Roles                          roles                          = rolesService.findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID);
		list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> sortAndFilterSearch(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :trainingProviderID and o.companyLearners <> null";
		return populate((List<TrainingProviderVerfication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countSearch(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :trainingProviderID  and o.companyLearners <> null";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> sortAndFilterSearchScheduledEvent(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :trainingProviderID and o.scheduledEvent.id = :scheduledEventID and o.companyLearners <> null";
		return populate((List<TrainingProviderVerfication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countSearchScheduledEvent(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderVerfication o where o.status = :status and o.trainingProvider.id = :trainingProviderID and o.scheduledEvent.id = :scheduledEventID and o.companyLearners <> null";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<TrainingProviderVerfication> sortAndFilterSearchParent(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingProviderVerfication o where o.trainingProviderVerficationParent.id = :trainingProviderVerficationParentID and o.companyLearners <> null";
		return populate((List<TrainingProviderVerfication>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countSearchParent(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingProviderVerfication o where o.trainingProviderVerficationParent.id = :trainingProviderVerficationParentID and o.companyLearners <> null";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<ScheduledEvent> sortAndFilterScheduledEvent(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select DISTINCT(o.scheduledEvent) from TrainingProviderVerfication o where o.cetificateGenerated = :cetificateGenerated ";
		return (List<ScheduledEvent>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countScheduledEvent(Map<String, Object> filters) throws Exception {
		String hql = "select count(o.scheduledEvent) from TrainingProviderVerfication o where o.cetificateGenerated = :cetificateGenerated ";
		return dao.countWhere(filters, hql);
	}

	public List<TrainingProviderVerfication> findByScheduledEvent(ScheduledEvent scheduledEvent) throws Exception {
		return populateUnitsStandarts(dao.findByScheduledEvent(scheduledEvent.getId()));
	}

	private List<TrainingProviderVerfication> populateUnitsStandarts(List<TrainingProviderVerfication> list) throws Exception {
		SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
		for (TrainingProviderVerfication tpv : list) {
			tpv.setCompanyLearners(CompanyLearnersService.instance().findByKey(tpv.getCompanyLearners().getId()));
			// tpv.setTrainingProviderApplication(TrainingProviderApplicationService.instance().findByKey(tpv.getTrainingProviderApplication().getId()));
			// tpv.setSummativeAssessmentReportUnitStandards(summativeAssessmentReportService.findByTrainingProviderVerfication(tpv));
		}
		return list;
	}

	private List<TrainingProviderVerfication> populate(List<TrainingProviderVerfication> list) throws Exception {
		for (TrainingProviderVerfication tp : list) {
			populateDoc(tp);
			populateDocuments(tp);
		}
		return list;
	}

	private void populateDoc(TrainingProviderVerfication tp) throws Exception {
		List<Doc> docs = DocService.instance().searchByTargetKeyAndClass(TrainingProviderVerfication.class.getName(), tp.getId());
		if (docs.size() > 0) {
			tp.setDoc(docs.get(docs.size() - 1));
		} else {
			tp.setDoc(new Doc());
		}
	}

	public void populateDocuments(TrainingProviderVerfication tp) throws Exception {
		List<Doc> docs = DocService.instance().searchByTargetKeyAndClass(TrainingProviderVerfication.class.getName(), tp.getCompanyLearners().getId());
		if (docs.size() > 0) {
			tp.setDocuments(docs.get(docs.size() - 1));
		} else {
			tp.setDocuments(new Doc());
		}
	}

	private void sendReviewDateEmail(Company taskCompany, Users activeUser, Date fromDateTime, Users user) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();

		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		JasperService.addLogo(params);
		HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
		List<Users>                    userList   = hceService.findUserByJobTitle(HAJConstants.Manager_Quality_Assurance_ID);
		Users                          qa         = null;
		if (userList != null && userList.size() > 0) {
			qa = userList.get(0);
		} else {
			qa = activeUser;
		}
		String     datepattern = "MM/dd/yyyy";
		String     timepattern = "HH:mm:ss";
		DateFormat df          = new SimpleDateFormat(datepattern);
		DateFormat tf          = new SimpleDateFormat(timepattern);

		AttachmentBean form = new AttachmentBean();

		params.put("contact", user);
		params.put("company", taskCompany);
		params.put("address", taskCompany.getResidentialAddress());
		params.put("quality_assuror", qa);
		params.put("visit_date", df.format(fromDateTime));
		params.put("visit_time", tf.format(fromDateTime));

		byte[] first_bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-044-NotificationOfModeration.jasper", params);
		String fileName    = "Moderation_Notification.pdf";
		form.setExt("pdf");
		form.setFile(first_bites);
		form.setFilename(fileName);
		attachmentBeans.add(form);

		String subject = "NOTIFICATION OF MODERATION OF ASSESSMENT";

		String welcome = "<p>Dear #FirstName# #Surname#,</p>" + "<p>Kindly be advised of the external moderation visit that is scheduled on #date# </p>" + "<p>The moderation will be conducted on the following aspects:</p>"

				+ "<p>Portfolio of evidence of the assessments of candidates; </p>" + "<p>Validity, accuracy, credibility and sufficiency of assessments as per merSETA assessment and moderation guideline and criteria;</p>" + "<p>Assessment guides and instruments; and</p>" + "<p>Evidence on internal moderation process.</p>"

				+ "<p>For further information or assistance, kindly contact the merSETA Regional Office.</p>" + "<p>Yours sincerely,</p>" + "<p>#QaName# #QaSurname#,</p>" + "<p>Manager: Quality Assurance</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
		welcome = welcome.replaceAll("#Surname#", user.getLastName());
		welcome = welcome.replaceAll("#date#", df.format(fromDateTime));
		welcome = welcome.replaceAll("#time#", tf.format(fromDateTime));
		welcome = welcome.replaceAll("#QaName#", qa.getFirstName());
		welcome = welcome.replaceAll("#QaSurname#", qa.getLastName());

		GenericUtility.sendMailWithAttachementTempWithLog(user.getEmail(), subject, welcome, attachmentBeans, null);
	}

	public void sendAprovalMail(TrainingProviderVerfication entity, Users activeUser) throws Exception {
		AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();
		Map<String, Object>         params                      = new HashMap<String, Object>();
		AttachmentBean              firstForm                   = new AttachmentBean();
		ArrayList<AttachmentBean>   attachmentBeans             = new ArrayList<>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "education_logo");

		Users         learner       = entity.getCompanyLearners().getUser();
		Users         contact       = entity.getCompanyLearners().getCreateUser();
		Company       provider      = entity.getCompanyLearners().getCompany();
		Company       employer      = entity.getCompanyLearners().getEmployer();
		Qualification qualification = new Qualification();

		if (entity.getCompanyLearners().getQualification() != null && entity.getCompanyLearners().getQualification().getId() != null) {
			qualification = entity.getCompanyLearners().getQualification();
		} else if (entity.getCompanyLearners().getSkillsSet() != null && entity.getCompanyLearners().getSkillsSet().getId() != null) {
			qualification = entity.getCompanyLearners().getSkillsSet().getQualification();
		} else if (entity.getCompanyLearners().getSkillsProgram() != null && entity.getCompanyLearners().getSkillsProgram().getId() != null) {
			qualification = entity.getCompanyLearners().getSkillsProgram().getQualification();
		}

		int            learner_age    = calCAge(learner.getDateOfBirth().getYear(), learner.getDateOfBirth().getMonth(), learner.getDateOfBirth().getDay());
		ScheduledEvent scheduledEvent = scheduledEventService.findByTargetKeyAndTargetClass(entity.getId(), TrainingProviderVerfication.class.getName());

		Integer          number_of_leaners    = 0;
		Integer          number_of_assesors   = 0;
		String           validation_batch_num = "validation_batch_num";
		String           date_of_assessment   = "2019-04-04";
		String           moderation_date      = "";
		SimpleDateFormat format               = new SimpleDateFormat("yyyy-MM-dd");
		if (scheduledEvent != null && scheduledEvent.getId() != null) {
			moderation_date = format.format(scheduledEvent.getFromDateTime());
		}

		List<CompanyLearners>             companyLearners = new ArrayList<>();
		List<TrainingProviderVerfication> list            = dao.findByTrainingProviderVerficationParentID(entity.getTrainingProviderVerficationParent().getId());

		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			companyLearners.add(trainingProviderVerfication.getCompanyLearners());
			number_of_leaners++;
		}
		List<AuditorMonitorReview> mainList = auditorMonitorReviewService.findByTargetKeyAndClass(TrainingProviderVerfication.class.getName(), entity.getTrainingProviderVerficationParent().getId());

		List<AuditorMonitorReview> auditorMonitorReviewList       = new ArrayList<>();
		List<AuditorMonitorReview> auditorMonitorReviewSecondList = new ArrayList<>();

		for (AuditorMonitorReview auditorMonitorReview : mainList) {
			if (auditorMonitorReview.getAccreditationCriteria().equals("1 EXTERNAL MODERATION")) {
				auditorMonitorReviewList.add(auditorMonitorReview);
			} else if (auditorMonitorReview.getAccreditationCriteria().equals("2 EXTERNAL MODERATION")) {
				auditorMonitorReviewSecondList.add(auditorMonitorReview);
			}
		}
		// List<AuditorMonitorReview>auditorMonitorReviewList = auditorMonitorReviewService.findByTargetKeyAndClass(TrainingProviderVerfication.class.getName(), entity.getId());

		// List<AuditorMonitorReview>auditorMonitorReviewSecondList = auditorMonitorReviewService.findByTargetKeyAndClass(TrainingProviderVerfication.class.getName(), entity.getTrainingProviderVerficationParent().getId());

		params.put("number_of_assesors", number_of_assesors);
		params.put("number_of_leaners", number_of_leaners);
		params.put("learner", learner);
		params.put("learner_age", String.valueOf(learner_age));

		params.put("contact", contact);
		params.put("employer", employer);
		params.put("provider", provider);
		params.put("moderator", activeUser);
		params.put("quality_assuror", activeUser);
		params.put("qualification", qualification);

		params.put("date_of_assessment", date_of_assessment);
		params.put("moderation_date", moderation_date);
		params.put("validation_batch_num", String.valueOf(validation_batch_num));
		params.put("number_of_assessors", String.valueOf(number_of_assesors));
		params.put("guid", provider.getCompanyGuid());
		params.put("company_learners", new JRBeanCollectionDataSource(companyLearners));
		params.put("section_one", new JRBeanCollectionDataSource(auditorMonitorReviewList));
		params.put("section_two", new JRBeanCollectionDataSource(auditorMonitorReviewSecondList));

		AttachmentBean secondForm = new AttachmentBean();

		byte[] second_bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-045-ModerationValidationReportPerIndividualLearner.jasper", params);
		String fileName1    = "Second_form.pdf";
		secondForm.setExt("pdf");
		secondForm.setFile(second_bites);
		secondForm.setFilename(fileName1);
		attachmentBeans.add(secondForm);

		byte[] first_bites = JasperService.instance().convertJasperReportToByte("ETQ-TP-043-ModerationOrValidationReportOfSummativeAssessments.jasper", params);
		String fileName    = "first_form.pdf";
		firstForm.setExt("pdf");
		firstForm.setFile(first_bites);
		firstForm.setFilename(fileName);
		attachmentBeans.add(firstForm);

		String subject = "Training Provider Verfication";

		String mssg = "Testing";

		GenericUtility.sendMailWithAttachementTempWithLog(entity.getCreateUser().getEmail(), subject, mssg, attachmentBeans, null);
	}

	public int calCAge(int year, int month, int days) {
		return LocalDate.now().minus(Period.of(year, month, days)).getYear();
	}

	public void createUpdateCompanyModerationReport(List<AuditorMonitorReview> auditorMonitorReviews, ScheduledEvent scheduledEvent, Users activeUser) throws Exception {

		if (auditorMonitorReviews != null) {
			this.dao.updateBatch((List<IDataEntity>) (List<?>) auditorMonitorReviews);
		}
		scheduledEvent.setCompanyModerationDone(true);
		dao.update(scheduledEvent);
	}

	public void createUpdateLearnerModerationReport(List<AuditorMonitorReview> auditorMonitorReviews, TrainingProviderVerfication entity, Users activeUser) throws Exception {
		if (auditorMonitorReviews != null) {
			this.dao.updateBatch((List<IDataEntity>) (List<?>) auditorMonitorReviews);
		}

		entity.setLearnerModerationDone(true);
		dao.update(entity);
	}

	public void downloadCompanyModerationReport(ScheduledEvent entity, Users activeUser) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService       js     = new JasperService();

		AuditorMonitorReviewService       auditorMonitorReviewService     = new AuditorMonitorReviewService();
		List<AuditorMonitorReview>        auditorMonitorReviews           = auditorMonitorReviewService.findByTargetKeyAndClassNoDoc(ScheduledEvent.class.getName(), entity.getId(), 1);
		List<AuditorMonitorReview>        auditorMonitorReviewsSectionTwo = auditorMonitorReviewService.findByTargetKeyAndClassNoDoc(ScheduledEvent.class.getName(), entity.getId(), 2);
		List<TrainingProviderVerfication> list                            = dao.findByScheduledEvent(entity.getId());
		Integer                           number_of_leaners               = list.size();
		Users                             contact                         = entity.getUser();
		Company                           employer                        = entity.getCompany();
		Company                           provider                        = entity.getCompany();
		String                            qualification_description       = "";
		String                            qualification_code              = "";
		List<CompanyLearners>             companyLearners                 = new ArrayList<>();
		CompanyLearners                   cl                              = null;
		for (TrainingProviderVerfication trainingProviderVerfication : list) {
			if (trainingProviderVerfication.getCompanyLearners() != null) {
				cl = CompanyLearnersService.instance().findByKey(trainingProviderVerfication.getCompanyLearners().getId());

				companyLearners.add(cl);
			}
		}

		JasperService.addFormTemplateParams(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "education_logo");
		if (companyLearners.size() > 0) {
			CompanyLearners companyLearner = companyLearners.get(0);
			qualification_description = CompanyLearnersService.instance().findStringQualificationDescription(companyLearner);
			qualification_code        = CompanyLearnersService.instance().findStringQualificationCode(companyLearner);
			params.put("qualification_description", qualification_description);
			params.put("qualification_code", qualification_code);
		}

		List<Users> userList = findRegionQualityAssuror(entity.getCompany());
		if (userList.size() > 0) {
			params.put("quality_assuror", userList.get(0));
		}
		params.put("validation_batch_num", entity.getId() + provider.getId() + provider.getLevyNumber());
		params.put("scheduledevent", entity);
		params.put("number_of_leaners", number_of_leaners);
		params.put("contact", contact);
		params.put("employer", employer);
		params.put("provider", provider);
		params.put("moderator", activeUser);
		params.put("company_learners", new JRBeanCollectionDataSource(companyLearners));
		params.put("section_one", new JRBeanCollectionDataSource(auditorMonitorReviews));
		params.put("section_two", new JRBeanCollectionDataSource(auditorMonitorReviewsSectionTwo));
		js.createReportFromDBtoServletOutputStream("ETQ-TP-043-ModerationOrValidationReportOfSummativeAssessments.jasper", params, "SummativeAssessmentReport.pdf");

	}

	public void downloadLearnerModerationReport(List<AuditorMonitorReview> auditorMonitorReviews, TrainingProviderVerfication entity, Users activeUser) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();

		Users         learner       = entity.getCompanyLearners().getUser();
		Users         contact       = entity.getCompanyLearners().getCreateUser();
		Company       provider      = entity.getCompanyLearners().getCompany();
		Company       employer      = entity.getCompanyLearners().getEmployer();
		Qualification qualification = new Qualification();

		if (entity.getCompanyLearners().getQualification() != null && entity.getCompanyLearners().getQualification().getId() != null) {
			qualification = entity.getCompanyLearners().getQualification();
		} else if (entity.getCompanyLearners().getSkillsSet() != null && entity.getCompanyLearners().getSkillsSet().getId() != null) {
			qualification = entity.getCompanyLearners().getSkillsSet().getQualification();
		} else if (entity.getCompanyLearners().getSkillsProgram() != null && entity.getCompanyLearners().getSkillsProgram().getId() != null) {
			qualification = entity.getCompanyLearners().getSkillsProgram().getQualification();
		}

		int    learner_age        = GenericUtility.getYearsBetweenDates(learner.getDateOfBirth(), getSynchronizedDate());// calCAge(learner.getDateOfBirth().getYear(), learner.getDateOfBirth().getMonth(), learner.getDateOfBirth().getDay());
		String date_of_assessment = "2019-04-04";

		List<AuditorMonitorReview> auditorMonitorReviewList       = new ArrayList<>();
		List<AuditorMonitorReview> auditorMonitorReviewSecondList = new ArrayList<>();

		for (AuditorMonitorReview auditorMonitorReview : auditorMonitorReviews) {
			if (auditorMonitorReview.getAccreditationCriteria().equals("1 EXTERNAL MODERATOR VERIFICATION OF ON SITE ASSESSMENT PROCESS")) {
				auditorMonitorReviewList.add(auditorMonitorReview);
			} else if (auditorMonitorReview.getAccreditationCriteria().equals("2 EXTERNAL MODERATOR VERIFICATION OF ON SITE ASSESSMENT PROCESS")) {
				auditorMonitorReviewSecondList.add(auditorMonitorReview);
			}
		}
		if (entity.getScheduledEvent() != null) {
			params.put("scheduledevent", scheduledEventService.findByKey(entity.getScheduledEvent().getId()));
		}
		params.put("learner", learner);
		params.put("learner_age", String.valueOf(learner_age));
		params.put("contact", contact);
		params.put("employer", employer);
		params.put("provider", provider);
		params.put("moderator", activeUser);
		params.put("quality_assuror", activeUser);
		params.put("qualification", qualification);
		params.put("date_of_assessment", date_of_assessment);
		params.put("guid", provider.getCompanyGuid());
		params.put("section_one", new JRBeanCollectionDataSource(auditorMonitorReviewList));
		params.put("section_two", new JRBeanCollectionDataSource(auditorMonitorReviewSecondList));

		JasperService js = new JasperService();
		JasperService.addFormTemplateParams(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		JasperService.addImage(params, "higher_education_and_training_logo.jpg", "education_logo");
		js.createReportFromDBtoServletOutputStream("ETQ-TP-045-ModerationValidationReportPerIndividualLearner.jasper", params, "LearnerSummativeAssessmentReport.pdf");
	}

	public List<TrainingProviderVerfication> findByCompanyLearner(CompanyLearners companyLearners) {
		return dao.findByCompanyLearner(companyLearners.getId());
	}

	public TrainingProviderVerfication findTrainingProviderVerficationByCompanyLearner(CompanyLearners companyLearners) throws Exception {
		return dao.findTrainingProviderVerficationByCompanyLearner(companyLearners.getId());
	}

	public List<TrainingProviderVerfication> findByUserAndStatus(Users users, ApprovalEnum approvalEnum) {
		return dao.findByUserAndStatus(users.getId(), approvalEnum);
	}

	public List<TrainingProviderVerfication> findByCompanyAndStatus(Company company, ApprovalEnum approvalEnum) {
		return dao.findByCompanyAndStatus(company.getId(), approvalEnum);
	}

	public List<TrainingProviderVerfication> findByCompanyAndUserAndStatus(Company company, Users users, ApprovalEnum approvalEnum) {
		return dao.findByCompanyAndUserAndStatus(company.getId(), users.getId(), approvalEnum);
	}

	public void uploadDocuments(ScheduledEvent entity, Tasks task, Users sessionUser) throws Exception {
		// check if all docs provided for correct permissions
		if (task != null && (task.getProcessRole().getRolePermission() == UserPermissionEnum.Upload || task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUploadApproval || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.UploadApprove)) {
			// prepareNewRegistration(task.getWorkflowProcess(), entity, task.getProcessRole());
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
					doc.setTargetClass(TrainingProviderVerfication.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
				} else {
					// if(doc.getConfigDoc().getConfigDocProcess().getConfigDocProcessEnumByValue(1).compareTo(doc.getConfigDoc().getProcessRoles().getRoleOrder()) )
					if (doc.getData() != null) {
						if (doc.getId() == null) {
							doc.setTargetKey(entity.getId());
							doc.setTargetClass(TrainingProviderVerfication.class.getName());
							DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
						}
					} else {
						throw new Exception("Please ensure all documents are uploaded");
					}
				}
			}
		}
	}

	public void uploadDocuments(TrainingProviderVerfication entity, Tasks task, Users sessionUser) throws Exception {
		// check if all docs provided for correct permissions
		if (task != null && (task.getProcessRole().getRolePermission() == UserPermissionEnum.Upload || task.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUploadApproval || task.getProcessRole().getRolePermission() == UserPermissionEnum.FinalUpload || task.getProcessRole().getRolePermission() == UserPermissionEnum.UploadApprove)) {
			// prepareNewRegistration(task.getWorkflowProcess(), entity, task.getProcessRole());
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
					doc.setTargetClass(TrainingProviderVerfication.class.getName());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
				} else {
					// if(doc.getConfigDoc().getConfigDocProcess().getConfigDocProcessEnumByValue(1).compareTo(doc.getConfigDoc().getProcessRoles().getRoleOrder()) )
					if (doc.getData() != null) {
						if (doc.getId() == null) {
							doc.setTargetKey(entity.getId());
							doc.setTargetClass(TrainingProviderVerfication.class.getName());
							DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
						}
					} else {
						throw new Exception("Please ensure all documents are uploaded");
					}
				}
			}
		}
	}

	/**/
	public void generateAllCertificate(List<TrainingProviderVerfication> trainingproviderverficationList, Users activeUser) throws Exception {
		// TrainingProviderApplication tpa = TrainingProviderApplicationService.instance().findByKey(trainingproviderverficationList.get(0).getTrainingProviderApplication().getId());
		Company comapny = trainingproviderverficationList.get(0).getTrainingProvider();

		ScheduledEventService scheduledEventService = new ScheduledEventService();
		ScheduledEvent        scheduledEvent        = new ScheduledEvent();
		scheduledEvent.setCompany(comapny);
		scheduledEvent.setUser(activeUser);
		scheduledEvent.setStatus(ApprovalEnum.PendingApproval);
		scheduledEventService.create(scheduledEvent);

		for (TrainingProviderVerfication trainingProviderVerfication : trainingproviderverficationList) {
			trainingProviderVerfication.setScheduledEvent(scheduledEvent);
			Doc doc = generateCertificate(trainingProviderVerfication, activeUser);
			// doc.setData(file.getContents());
			// doc.setOriginalFname(file.getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(activeUser);
			doc.setTargetKey(trainingProviderVerfication.getId());
			doc.setTargetClass(TrainingProviderVerfication.class.getName());
			DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), activeUser);
		}
	}

	public void generateAllCertificateNew(List<TrainingProviderVerfication> trainingproviderverficationList, Users activeUser) throws Exception {
		Company comapny = trainingproviderverficationList.get(0).getTrainingProvider();

		ScheduledEventService scheduledEventService = new ScheduledEventService();
		ScheduledEvent        scheduledEvent        = new ScheduledEvent();
		scheduledEvent.setCompany(comapny);
		scheduledEvent.setUser(activeUser);
		scheduledEvent.setStatus(ApprovalEnum.PendingApproval);
		scheduledEventService.create(scheduledEvent);
		for (TrainingProviderVerfication trainingproviderverfication : trainingproviderverficationList) {
			trainingproviderverfication.setCeritificateCollectionEnum(CeritificateCollectionEnum.PendingSubmission);
			trainingproviderverfication.setCetificateGenerated(true);
			trainingproviderverfication.setCetificateGeneratedDate(getSynchronizedDate());
			trainingproviderverfication.setDownloads(0);
			trainingproviderverfication.setScheduledEvent(scheduledEvent);
			update(trainingproviderverfication);
		}
	}

	public void generateNewCertificate(List<TrainingProviderVerfication> trainingproviderverficationList, Users activeUser) throws Exception {
		for (TrainingProviderVerfication trainingProviderVerfication : trainingproviderverficationList) {
			Doc doc = generateCertificate(trainingProviderVerfication, activeUser);
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(activeUser);
			doc.setTargetKey(trainingProviderVerfication.getId());
			doc.setTargetClass(TrainingProviderVerfication.class.getName());
			DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), activeUser);
		}
	}

	public Doc generateCertificate(TrainingProviderVerfication trainingproviderverfication, Users activeUser) throws Exception {
		Doc                 doc             = new Doc();
		Map<String, Object> params          = new HashMap<String, Object>();
		CompanyLearners     companylearners = CompanyLearnersService.instance().findByKey(trainingproviderverfication.getCompanyLearners().getId());

		List<UnitStandards>                           unitStandardList                              = new ArrayList<>();
		SummativeAssessmentReportService              summativeAssessmentReportService              = new SummativeAssessmentReportService();
		SummativeAssessmentReport                     summativeAssessmentReport                     = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
		SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();

		List<SummativeAssessmentReportUnitStandards> list = new ArrayList<>();
		if (summativeAssessmentReport != null) {
			list = summativeAssessmentReportUnitStandardsService.findBySummativeAssessmentReport(summativeAssessmentReport.getId());
		}

		for (SummativeAssessmentReportUnitStandards sr : list) {
			unitStandardList.add(sr.getUnitStandards());
		}

		if (companylearners.getQualification() == null) {
			companylearners.setQualification(CompanyLearnersService.instance().getCompanyLearnerQualification(companylearners));
		}

		params.put("companylearners", companylearners);
		params.put("qualification", companylearners.getQualification());
		params.put("learner", companylearners.getUser());
		if (trainingproviderverfication.getCertificateNumber() == null) {
			generateCertificateNumber(trainingproviderverfication);
		}
		params.put("trainingproviderverfication", trainingproviderverfication);
		params.put("UnitStandardList", new JRBeanCollectionDataSource(unitStandardList));

		JasperService js = new JasperService();
		JasperService.addLogo(params);
		JasperService.addImage(params, "top_bottom_boder.png", "top_bottom_border");
		JasperService.addImage(params, "left_right_boder.png", "left_right_border");
		JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
		JasperService.addImage(params, "corner_image.png", "corner_image");
		JasperService.addImage(params, "logo2.png", "logo");
		JasperService.addImage(params, "ceo.png", "ceo");
		JasperService.addImage(params, "manager.png", "manager");

		if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			if (companylearners.getQualification() == null) {
				companylearners.setQualification(companylearners.getLearnership().getQualification());
			}
			doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params, "Certificate.pdf");
		} else if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
			if (companylearners.getQualification() == null) {
				companylearners.setQualification(companylearners.getSkillsProgram().getQualification());
			}
			doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params, "Certificate.pdf");
		} else if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
			doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params, "Certificate.pdf");
		} else if (companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params, "Certificate.pdf");
		} else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
			doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params, "Certificate.pdf");
		} else {
			throw new Exception("Error On Generating Certificate, Please contact your administrator");
		}

		trainingproviderverfication.setCeritificateCollectionEnum(CeritificateCollectionEnum.PendingSubmission);
		trainingproviderverfication.setCetificateGenerated(true);
		trainingproviderverfication.setCetificateGeneratedDate(getSynchronizedDate());
		trainingproviderverfication.setDownloads(0);
		update(trainingproviderverfication);
		return doc;
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

	public boolean checkAssessments(TrainingProviderVerfication trainingproviderverfication) throws Exception {
		SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();
		boolean                                       passed                                        = true;
		SummativeAssessmentReport                     summativeAssessmentReport                     = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
		if (summativeAssessmentReport != null) {
			List<SummativeAssessmentReportUnitStandards> list = summativeAssessmentReportUnitStandardsService.findBySummativeAssessmentReport(summativeAssessmentReport.getId());
			for (SummativeAssessmentReportUnitStandards sarus : list) {
				if (sarus.getCompetenceEnum() != null && sarus.getCompetenceEnum() == CompetenceEnum.Competent) {
					if (sarus.getAssesmentDate() == null) {
						passed = false;
						break;
					}
					if (sarus.getModerationDate() == null) {
						passed = false;
						break;
					}
					if (sarus.getModeratorApplication() == null && sarus.getLegacyModeratorAccreditation() == null) {
						passed = false;
						break;
					}

					if (sarus.getAssessorApplication() == null && sarus.getLegacyAssessorAccreditation() == null) {
						passed = false;
						break;
					}
				}
			}
		}
		return passed;
	}

	public boolean checkCredits(TrainingProviderVerfication trainingproviderverfication) throws Exception {
		SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();
		boolean                                       passed                                        = false;
		CompanyLearners                               companylearners                               = CompanyLearnersService.instance().findByKey(trainingproviderverfication.getCompanyLearners().getId());
		SummativeAssessmentReport                     summativeAssessmentReport                     = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
		if (summativeAssessmentReport != null) {
			List<SummativeAssessmentReportUnitStandards> list = summativeAssessmentReportUnitStandardsService.findBySummativeAssessmentReport(summativeAssessmentReport.getId());
			// Qualification qualification = null;
			int minumum = 0;
			if (companylearners.getUnitStandard() != null) {
				if (companylearners.getUnitStandard().getUnitstdnumberofcredits() == null) {
					throw new Exception("Unit standard credits error!!! Please contact your administrator");
				}
				minumum = Integer.parseInt(companylearners.getUnitStandard().getUnitstdnumberofcredits());
				// qualification = companylearners.getUnitStandard().getQualification();
			} else if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
				if (companylearners.getSkillsProgram().getCredits() == null) {
					// throw new Exception("Unit standard credits error!!! Please contact your administrator");
				}
				// minumum = companylearners.getSkillsProgram().getCredits();
				// qualification = companylearners.getSkillsProgram().getQualification();
			} else if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
				if (companylearners.getSkillsSet().getCredits() == null) {
					throw new Exception("Unit standard credits error!!! Please contact your administrator");
				}
				minumum = companylearners.getSkillsSet().getCredits();
				// qualification = companylearners.getSkillsSet().getQualification();
			} else if (companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
				if (companylearners.getUnitStandard().getUnitstdnumberofcredits() == null) {
					throw new Exception("Unit standard credits error!!! Please contact your administrator");
				}
				if (companylearners.getUnitStandard().getUnitstdnumberofcredits() != null) {
					minumum = Integer.parseInt(companylearners.getUnitStandard().getUnitstdnumberofcredits());
				} else {
					minumum = companylearners.getUnitStandard().getQualification().getCredits();
				}
				// qualification = companylearners.getQualification();
			} else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
				if (companylearners.getLearnership().getQualification().getCredits() == null) {
					throw new Exception("Unit standard credits error!!! Please contact your administrator");
				}
				minumum = companylearners.getLearnership().getQualification().getCredits();
				// qualification = companylearners.getLearnership().getQualification();
			} else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
				if (companylearners.getLearnership().getQualification().getCredits() == null) {
					throw new Exception("Unit standard credits error!!! Please contact your administrator");
				}
				minumum = companylearners.getLearnership().getQualification().getCredits();
				// qualification = companylearners.getLearnership().getQualification();
			}

			if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
				for (SummativeAssessmentReportUnitStandards sarus : list) {
					if (sarus.getCompetenceEnum() == CompetenceEnum.Competent) {
						passed = true;
					} else {
						passed = false;
						break;
					}
				}
			} else {
				int totalCredits = 0;

				for (SummativeAssessmentReportUnitStandards sarus : list) {
					if (sarus.getCompetenceEnum() == CompetenceEnum.Competent) {
						totalCredits += Integer.parseInt(sarus.getUnitStandards().getUnitstdnumberofcredits());
					}
				}
				if (totalCredits >= minumum) {
					passed = true;
				}
			}

		}

		return passed;
	}

	public boolean checkFieldsprovided(TrainingProviderVerfication trainingproviderverfication) throws Exception {
		SummativeAssessmentReport                     summativeAssessmentReport                     = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
		SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();
		List<SummativeAssessmentReportUnitStandards>  list                                          = summativeAssessmentReportUnitStandardsService.findBySummativeAssessmentReport(summativeAssessmentReport.getId());
		boolean                                       provided                                      = true;
		for (SummativeAssessmentReportUnitStandards sr : list) {
			if (sr.getCompetenceEnum() != null && sr.getAssessorApplication() == null && sr.getLegacyAssessorAccreditation() == null) {
				provided = false;
				break;
			}

			if (sr.getCompetenceEnum() != null && sr.getAssesmentDate() == null) {
				provided = false;
				break;
			}

			if (sr.getCompetenceEnum() != null && sr.getModeratorApplication() == null && sr.getLegacyModeratorAccreditation() == null) {
				provided = false;
				break;
			}

			if (sr.getCompetenceEnum() != null && sr.getModerationDate() == null) {
				provided = false;
				break;
			}
		}
		return provided;
	}

	public void downloadCertificate(TrainingProviderVerfication trainingproviderverfication, Users activeUser) throws Exception {
		Map<String, Object> params          = new HashMap<String, Object>();
		CompanyLearners     companylearners = CompanyLearnersService.instance().findByKey(trainingproviderverfication.getCompanyLearners().getId());

		List<UnitStandards>                           unitStandardList                              = new ArrayList<>();
		SummativeAssessmentReportService              summativeAssessmentReportService              = new SummativeAssessmentReportService();
		SummativeAssessmentReport                     summativeAssessmentReport                     = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
		SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();
		List<SummativeAssessmentReportUnitStandards>  list                                          = summativeAssessmentReportUnitStandardsService.findBySummativeAssessmentReport(summativeAssessmentReport.getId());

		for (SummativeAssessmentReportUnitStandards sr : list) {
			unitStandardList.add(sr.getUnitStandards());
		}

		if (companylearners.getQualification() == null) {
			companylearners.setQualification(CompanyLearnersService.instance().getCompanyLearnerQualification(companylearners));
		}

		params.put("companylearners", companylearners);
		params.put("qualification", companylearners.getQualification());
		params.put("learner", companylearners.getUser());
		params.put("trainingproviderverfication", trainingproviderverfication);
		params.put("UnitStandardList", new JRBeanCollectionDataSource(unitStandardList));

		JasperService js = new JasperService();
		JasperService.addLogo(params);
		JasperService.addImage(params, "top_bottom_boder.png", "top_bottom_border");
		JasperService.addImage(params, "left_right_boder.png", "left_right_border");
		JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
		JasperService.addImage(params, "corner_image.png", "corner_image");
		JasperService.addImage(params, "logo2.png", "logo");
		JasperService.addImage(params, "ceo.png", "ceo");
		JasperService.addImage(params, "manager.png", "manager");

		if (companylearners.getInterventionType().getId() == HAJConstants.LEARNERSHIP_ID) {
			if (companylearners.getQualification() == null) {
				companylearners.setQualification(companylearners.getLearnership().getQualification());
			}
			// doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params,"Certificate.pdf");
			js.createReportFromDBtoServletOutputStream("certificate.jasper", params, companylearners.getUser().getFirstName() + "_" + companylearners.getUser().getLastName() + "_" + "certificate.pdf");

		} else if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
			if (companylearners.getQualification() == null) {
				companylearners.setQualification(companylearners.getSkillsProgram().getQualification());
			}
			// doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params,"Certificate.pdf");
			js.createReportFromDBtoServletOutputStream("certificate.jasper", params, companylearners.getUser().getFirstName() + "_" + companylearners.getUser().getLastName() + "_" + "certificate.pdf");

		} else if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
			// doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params,"Certificate.pdf");
			js.createReportFromDBtoServletOutputStream("certificate.jasper", params, companylearners.getUser().getFirstName() + "_" + companylearners.getUser().getLastName() + "_" + "certificate.pdf");

		} else if (companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
			// doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params,"Certificate.pdf");
			js.createReportFromDBtoServletOutputStream("certificate.jasper", params, companylearners.getUser().getFirstName() + "_" + companylearners.getUser().getLastName() + "_" + "certificate.pdf");

		} else {
			// js.createReportFromDBtoServletOutputStream("Certificate2.jasper", params,"Certificate.pdf");
			throw new Exception("Error On Generating Certificate, Please contact your administrator");
		}

	}

	private void generateCertificateNumber(TrainingProviderVerfication trainingproviderverfication) throws Exception {
		CompanyLearners companylearners = CompanyLearnersService.instance().findByKey(trainingproviderverfication.getCompanyLearners().getId());
		String          code            = "000";
		if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			// code = companylearners.getLearnership().getCode();
		} else if (companylearners.getQualification() != null) {
			// code = companylearners.getQualification().getCodeString();
		}
		String certificateNumber = "";

		int sequencenumber = 3200;
		int count          = dao.countTrainingProviderVerficationByStatus(ApprovalEnum.Approved);
		int number         = sequencenumber + count;
		// String str = String.valueOf(GenericUtility.getYear(getSynchronizedDate()));
		// certificateNumber = code+number+"/"+str;
		certificateNumber = code + number;
		trainingproviderverfication.setCertificateNumber(certificateNumber);
	}

	public void generateAndZipDocuments(List<TrainingProviderVerfication> trainingproviderverfications, Users activeUser) throws Exception {
		String               fileName        = activeUser.getFirstName() + "_" + activeUser.getLastName() + "_" + HAJConstants.sdf.format(getSynchronizedDate()) + "_Certficate.zip";
		List<AttachmentBean> attachmentBeans = new ArrayList<>();
		for (TrainingProviderVerfication trainingproviderverfication : trainingproviderverfications) {
			AttachmentBean      beanAttachment  = new AttachmentBean();
			Map<String, Object> params          = new HashMap<String, Object>();
			CompanyLearners     companylearners = CompanyLearnersService.instance().findByKey(trainingproviderverfication.getCompanyLearners().getId());

			List<UnitStandards>                           unitStandardList                              = new ArrayList<>();
			SummativeAssessmentReportService              summativeAssessmentReportService              = new SummativeAssessmentReportService();
			SummativeAssessmentReport                     summativeAssessmentReport                     = summativeAssessmentReportService.findByTrainingProviderVerficationKey(trainingproviderverfication.getId());
			SummativeAssessmentReportUnitStandardsService summativeAssessmentReportUnitStandardsService = new SummativeAssessmentReportUnitStandardsService();

			List<SummativeAssessmentReportUnitStandards> list = new ArrayList<>();
			if (summativeAssessmentReport != null) {
				list = summativeAssessmentReportUnitStandardsService.findBySummativeAssessmentReport(summativeAssessmentReport.getId());
			}

			for (SummativeAssessmentReportUnitStandards sr : list) {
				unitStandardList.add(sr.getUnitStandards());
			}

			if (companylearners.getQualification() == null) {
				companylearners.setQualification(CompanyLearnersService.instance().getCompanyLearnerQualification(companylearners));
			}
			String learners_names = getLearnerNames(companylearners.getUser());
			params.put("learners_names", learners_names);
			params.put("companylearners", companylearners);
			params.put("qualification", companylearners.getQualification());
			params.put("learner", companylearners.getUser());
			if (trainingproviderverfication.getCertificateNumber() == null) {
				generateCertificateNumber(trainingproviderverfication);
			}
			params.put("trainingproviderverfication", trainingproviderverfication);
			params.put("UnitStandardList", new JRBeanCollectionDataSource(unitStandardList));

			// JasperService js=new JasperService();
			JasperService.addLogo(params);
			JasperService.addImage(params, "top_bottom_boder.png", "top_bottom_border");
			JasperService.addImage(params, "left_right_boder.png", "left_right_border");
			JasperService.addImage(params, "certificate_signature.png", "certificate_signature");
			JasperService.addImage(params, "corner_image.png", "corner_image");
			JasperService.addImage(params, "logo2.png", "logo");
			JasperService.addImage(params, "ceo.png", "ceo");
			JasperService.addImage(params, "manager.png", "manager");

			if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
				if (companylearners.getQualification() == null) {
					companylearners.setQualification(companylearners.getLearnership().getQualification());
				}
				// doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params,"Certificate.pdf");
			} else if (SKILLS_PROGRAM_LIST.contains(companylearners.getInterventionType().getId())) {
				if (companylearners.getQualification() == null) {
					companylearners.setQualification(companylearners.getSkillsProgram().getQualification());
				}
				// doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params,"Certificate.pdf");
			} else if (SKILLS_SET_LIST.contains(companylearners.getInterventionType().getId())) {
				// doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params,"Certificate.pdf");
			} else if (companylearners.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
				// doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params,"Certificate.pdf");
			} else if (companylearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship) {
				// doc = js.createReportFromDBtoServletOutputStream("certificate.jasper", params,"Certificate.pdf");
			} else {
				throw new Exception("Error On Generating Certificate, Please contact your administrator");
			}

			byte[] bites     = JasperService.instance().convertJasperReportToByte("certificate.jasper", params);
			String filesName = "Cert_002_" + anIDNumber(companylearners.getUser()) + ".pdf";
			beanAttachment.setExt("pdf");
			beanAttachment.setFile(bites);
			beanAttachment.setFilename(filesName);

			attachmentBeans.add(beanAttachment);
			trainingproviderverfication.setDownloads(trainingproviderverfication.getDownloads() + 1);
			update(trainingproviderverfication);
		}
		JasperService.instance().multipleJasperDocumentsToZip(attachmentBeans, fileName);
	}

	private String getLearnerNames(Users user) {
		String names = user.getFirstName();
		if (user.getMiddleName() != null) {
			names += " " + user.getMiddleName();
		}
		names += " " + user.getLastName();
		return names.toUpperCase();
	}

	public void sendApprovalEmalAndLetters(TrainingProviderVerfication entity, Users user, Users sender) throws Exception {
		// ETQ-TP-032-StatementOfResults
		SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
		Map<String, Object>              params                           = new HashMap<String, Object>();
		ArrayList<AttachmentBean>        attachmentBeans                  = new ArrayList<>();

		CompanyLearners cl = CompanyLearnersService.instance().findByKey(entity.getCompanyLearners().getId());

		SummativeAssessmentReportUnitStandardsService sruts                     = new SummativeAssessmentReportUnitStandardsService();
		SummativeAssessmentReport                     summativeAssessmentReport = summativeAssessmentReportService.findByTrainingProviderVerficationKey(entity.getId());
		List<SummativeAssessmentReportUnitStandards>  list                      = sruts.findBySummativeAssessmentReport(summativeAssessmentReport.getId());

		Users            learner          = cl.getUser();
		Qualification    qualification    = new Qualification();
		Company          provider         = cl.getCompany();
		InterventionType interventionType = cl.getInterventionType();

		String accreditation_number = anIDNumber(learner);
		/*
		 * if(cl.getTrainingProviderApplication() != null) { TrainingProviderApplication tp = TrainingProviderApplicationService.instance().findByKey(cl.getTrainingProviderApplication().getId()); accreditation_number = tp.getAccreditationNumber(); }
		 */
		String description      = "";
		String qual_code        = "";
		String qual_description = "";
		if (cl.getSkillsProgram() != null && cl.getSkillsProgram().getId() != null) {
			qualification    = cl.getSkillsProgram().getQualification();
			qual_description = cl.getSkillsProgram().getDescription();
			description      = "unit standard-based";
			qual_code        = cl.getSkillsProgram().getProgrammeID();
		} else if (cl.getSkillsSet() != null && cl.getSkillsSet().getId() != null) {
			qualification    = cl.getSkillsSet().getQualification();
			qual_description = cl.getSkillsSet().getDescription();
			qual_code        = cl.getSkillsSet().getProgrammeID();
			description      = "unit standard-based";
		} else if (cl.getUnitStandard() != null && cl.getUnitStandard().getId() != null) {
			qualification    = cl.getUnitStandard().getQualification();
			qual_description = cl.getUnitStandard().getTitle();
			qual_code        = cl.getUnitStandard().getCode() + " ";
			description      = "unit standard-based";
		} else if (cl.getQualification() != null && cl.getQualification().getId() != null) {
			qualification    = cl.getQualification();
			qual_description = cl.getQualification().getDescription();
			qual_code        = cl.getQualification().getCodeString();
			description      = "unit standard-based";
		}
		Users                          senior_manager                 = null;
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		List<Users>                    ul                             = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Senior_Manager_Quality_Assurance_and_Partnerships_ID);
		if (ul != null && ul.size() > 0) {
			senior_manager = ul.get(0);
		}

		Address address = provider.getResidentialAddress();

		String toEmail = user.getEmail();

		JasperService.addLogo(params);
		JasperService.addFormTemplateParams(params);
		params.put("users", sender);
		params.put("learner", learner);
		params.put("qualification", qualification);
		params.put("trainingproviderverfication", entity);
		params.put("companylearner", cl);
		params.put("representative", user);
		params.put("senior_manager", senior_manager);
		params.put("provider", provider);
		params.put("company", provider);
		params.put("intervention", interventionType);
		params.put("description", description);
		params.put("qual_description", qual_description);
		params.put("qual_code", qual_code);
		params.put("accreditation_number", accreditation_number);
		params.put("address", address);

		params.put("QualificationUnitStandardBeanDataSource", new JRBeanCollectionDataSource(list));

		byte[]         bites          = JasperService.instance().convertJasperReportToByte("ETQ-TP-032-StatementOfResults.jasper", params);
		AttachmentBean beanAttachment = new AttachmentBean();
		String         fileName       = learner.getFirstName() + "_" + learner.getLastName() + "_StatementOfResultsLetter.pdf";
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename(fileName);
		attachmentBeans.add(beanAttachment);

		byte[]         bites1          = JasperService.instance().convertJasperReportToByte("ETQ-TP-011-StatementOfQualificationsandorUnitStandards.jasper", params);
		AttachmentBean beanAttachment1 = new AttachmentBean();
		String         fileName1       = learner.getFirstName() + "_" + learner.getLastName() + "_StatementOfResults.pdf";
		beanAttachment1.setExt("pdf");
		beanAttachment1.setFile(bites1);
		beanAttachment1.setFilename(fileName1);
		attachmentBeans.add(beanAttachment1);

		String subject = "Learner Statement of results".toUpperCase();

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear  " + user.getFirstName() + " " + user.getLastName() + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Kindly find enclosed the statement of results for " + learner.getFirstName() + " " + learner.getLastName() + " (" + anIDNumber(learner) + ") enrolled for (" + qual_code + ") - " + qual_description + "." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "For any assistance, please contact your Regional Office or merSETA Head Office." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours in Skills Development," + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA Team " + "</p>";

		GenericUtility.sendMailWithAttachementTempWithLog(toEmail, subject, mssg, attachmentBeans, null);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public List<TrainingProviderVerfication> findAllTrainingProviderVerficationWithErrors() throws Exception {
		return resolvelistCompanyLearners(dao.findAllTrainingProviderVerficationWithErrors());
	}

	public void update(List<IDataEntity> entity) throws Exception {
		if (entity != null && entity.size() > 0) {
			this.dao.updateBatch(entity);
		}
	}

	public void closeTrainingProviderVerficationTasks(TrainingProviderVerfication trainingproviderverfication) throws Exception {
		List<Tasks> list = TasksService.instance().findTasksByTypeAndKey(ConfigDocProcessEnum.LEARNER_ASSESSMENT_VERIFICATION, trainingproviderverfication.getClass().getName(), trainingproviderverfication.getId());
		for (Tasks task : list) {
			TasksService.instance().completeTask(task);
		}
	}

}
