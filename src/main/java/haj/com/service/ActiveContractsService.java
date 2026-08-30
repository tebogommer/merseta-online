package haj.com.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jsoup.Jsoup;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;

import haj.com.bean.AttachmentBean;
import haj.com.bean.ExtensionRequestReportBean;
import haj.com.bean.MoaStatusReportBean;
import haj.com.bean.PipTaskTrackerReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.ActiveContractsDAO;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyUsers;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.ProcessRoles;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SDFCompany;
import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.ContractStatusEnum;
import haj.com.entity.enums.DiscretionalWithdrawalAppealEnum;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.utils.GenericUtility;

public class ActiveContractsService extends AbstractService {
	/** The dao. */
	private ActiveContractsDAO                      dao                                     = new ActiveContractsDAO();
	private ActiveContractDetailService             activeContractDetailService             = new ActiveContractDetailService();
	private DgAllocationService                     dgAllocationService                     = new DgAllocationService();
	private ProjectImplementationPlanService        implementationPlanService               = new ProjectImplementationPlanService();
	private DgAllocationParentService               dgAllocationParentService;
	private SignoffService                          signoffService                          = null;
	private UsersService                            usersService                            = null;
	private WspService                              wspService                              = null;
	private CompanyService                          companyService                          = null;
	private SDFCompanyService                       sdfCompanyService                       = null;
	private CompanyUsersService                     companyUsersService                     = null;
	private HostingCompanyEmployeesService          hostingCompanyEmployeesService          = null;
	private ActiveContractExtensionRequestService   activeContractExtensionRequestService   = null;
	private ActiveContractTerminationRequestService activeContractTerminationRequestService = null;
	private DgContractingBulkItemsService           dgContractingBulkItemsService           = null;

	/**
	 * Get all ActiveContracts
	 * @author TechFinium
	 * @see ActiveContracts
	 * @return a list of {@link haj.com.entity.ActiveContracts}
	 * @throws Exception
	 * the exception
	 */
	public List<ActiveContracts> allActiveContracts() throws Exception {
		return dao.allActiveContracts();
	}

	/**
	 * Create or update ActiveContracts.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see ActiveContracts
	 */
	public void create(ActiveContracts entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update ActiveContracts.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see ActiveContracts
	 */
	public void update(ActiveContracts entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete ActiveContracts.
	 * @author TechFinium
	 * @param entity
	 * the entity
	 * @throws Exception
	 * the exception
	 * @see ActiveContracts
	 */
	public void delete(ActiveContracts entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 * @author TechFinium
	 * @param id
	 * the id
	 * @return a {@link haj.com.entity.ActiveContracts}
	 * @throws Exception
	 * the exception
	 * @see ActiveContracts
	 */
	public ActiveContracts findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find ActiveContracts by description.
	 * @author TechFinium
	 * @param desc
	 * the desc
	 * @return a list of {@link haj.com.entity.ActiveContracts}
	 * @throws Exception
	 * the exception
	 * @see ActiveContracts
	 */
	public List<ActiveContracts> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load ActiveContracts
	 * @param first
	 * from key
	 * @param pageSize
	 * no of rows to fetch
	 * @return a list of {@link haj.com.entity.ActiveContracts}
	 * @throws Exception
	 * the exception
	 */
	public List<ActiveContracts> allActiveContracts(int first, int pageSize) throws Exception {
		return dao.allActiveContracts(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of ActiveContracts for lazy load
	 * @author TechFinium
	 * @return Number of rows in the ActiveContracts
	 * @throws Exception
	 * the exception
	 */
	public Long count() throws Exception {
		return dao.count(ActiveContracts.class);
	}

	/**
	 * Lazy load ActiveContracts with pagination, filter, sorting
	 * @author TechFinium
	 * @param class1
	 * ActiveContracts class
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
	 * @return a list of {@link haj.com.entity.ActiveContracts} containing data
	 * @throws Exception
	 * the exception
	 */
	@SuppressWarnings("unchecked")
	public List<ActiveContracts> allActiveContracts(Class<ActiveContracts> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveDetails((List<ActiveContracts>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}

	@SuppressWarnings("unchecked")
	public List<ActiveContracts> allActiveContractsSearch(Class<ActiveContracts> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from ActiveContracts o where o.id is not null ";
		return resolveDetails((List<ActiveContracts>) dao.sortAndFilterSearch(class1, first, pageSize, sortField, sortOrder, filters, hql));
	}

	@SuppressWarnings("unchecked")
	public List<ActiveContracts> allActiveContractsSearchByFinYear(Class<ActiveContracts> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear, MoaTypeEnum moaType) throws Exception {
		String hql = "select o from ActiveContracts o where o.id is not null and o.dgAllocationParent.wsp.finYear = :finYear and o.moaType = :moaType";
		filters.put("finYear", finYear);
		filters.put("moaType", moaType);
		return resolveDetails((List<ActiveContracts>) dao.sortAndFilterSearch(class1, first, pageSize, sortField, sortOrder, filters, hql));
	}

	@SuppressWarnings("unchecked")
	public List<ActiveContracts> allActiveContractsDGStatusSearch(Class<ActiveContracts> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from ActiveContracts o where o.id is not null ";
		return resolveDetails((List<ActiveContracts>) dao.sortAndFilterDGStatusSearch(class1, first, pageSize, sortField, sortOrder, filters, hql));
	}

	private List<ActiveContracts> resolveDetails(List<ActiveContracts> activeContracts) throws Exception {
		for (ActiveContracts activeContract : activeContracts) {
			List<ActiveContractDetail> details = findActiveContractDetail(activeContract);
			activeContract.setActiveContractDetails(details);
			if (!details.isEmpty() && activeContract.getDgAllocationParent() != null) {
				activeContract.setNooflearners(findTotalLearners(activeContract.getDgAllocationParent()).intValue());
			} else {
				if (activeContract.getAccrualraised() == null) activeContract.setAccrualraised(0.0);
				if (details.size() > 0 && details.get(details.size() - 1).getClosingbalance() != null && details.get(details.size() - 1).getClosingbalance().compareTo(BigDecimal.ZERO) == 1) {
					activeContract.getActiveContractDetails().add(new ActiveContractDetail(activeContract, details.get(details.size() - 1).getClosingbalance()));
				} else {
					BigDecimal openVal = BigDecimal.valueOf(activeContract.getContractvalue()).subtract(BigDecimal.valueOf(activeContract.getAccrualraised()));
					activeContract.getActiveContractDetails().add(new ActiveContractDetail(activeContract, openVal));
				}
			}
		}
		return activeContracts;
	}

	/**
	 * Get count of ActiveContracts for lazy load with filters
	 * @author TechFinium
	 * @param entity
	 * ActiveContracts class
	 * @param filters
	 * the filters
	 * @return Number of rows in the ActiveContracts entity
	 * @throws Exception
	 * the exception
	 */
	public int count(Class<ActiveContracts> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public int countSearch(Class<ActiveContracts> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContracts o where o.id is not null ";
		return dao.countSearch(filters, hql);
	}

	public int countSearchByFinYear(Class<ActiveContracts> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContracts o where o.id is not null and o.dgAllocationParent.wsp.finYear = :finYear and o.moaType = :moaType";
		return dao.countSearch(filters, hql);
	}

	public int countDGStatusSearch(Class<ActiveContracts> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContracts o where o.id is not null ";
		return dao.countDGStatusSearch(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<ActiveContracts> allActiveContractsLinkedToCompanyId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from ActiveContracts o where (o.dgAllocationParent.wsp.company.id = :companyId) ";
		filters.put("companyId", companyId);
		return (List<ActiveContracts>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllActiveContractsLinkedToCompanyId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContracts o where (o.dgAllocationParent.wsp.company.id = :companyId) ";
		return dao.countWhere(filters, hql);
	}

	public List<ActiveContractDetail> findActiveContractDetail(ActiveContracts activeContracts) throws Exception {
		return dao.findActiveContractDetail(activeContracts.getId());
	}

	public List<ActiveContracts> findAllEletronicSignoff(Boolean eletronicSignoff) throws Exception {
		return dao.findAllEletronicSignoff(eletronicSignoff);
	}

	public List<ActiveContracts> findByEletronicSignoffNotSubmittedAndNotOpen(Boolean eletronicSignOff, Boolean submitted) throws Exception {
		return dao.findByEletronicSignoffNotSubmittedAndNotOpen(eletronicSignOff, submitted);
	}

	public void prepareNewRegistration(Tasks tasks, ConfigDocProcessEnum configDocProcess, ActiveContracts entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {

			if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
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

	public void prepareNewRegistrationProjectTermination(Tasks tasks, ConfigDocProcessEnum configDocProcess, ActiveContracts entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			if (processRoles == null) {
				HostingCompanyProcessService hcps = new HostingCompanyProcessService();
				List<HostingCompanyProcess>  hcp  = hcps.allHostingCompanyProcess();
				for (HostingCompanyProcess temp : hcp) {
					if (ConfigDocProcessEnum.DG_PROJECT_TERMINATION == temp.getWorkflowProcess()) {
						List<ProcessRoles> pr = new ProcessRolesService().firstProcessRoles(temp);
						processRoles = pr.get(0);
						tasks.setProcessRole(processRoles);
						new TasksService().update(tasks);
						break;
					}
				}
			}

			if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
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

	public ActiveContracts requesteWorkflow(ActiveContracts entity, Users u) throws Exception {
		entity.setStatus(ApprovalEnum.PendingApproval);
		entity.setMoaType(MoaTypeEnum.SpecialProject);
		// entity.setTranchintervals(3);
		create(entity);
		TasksService.instance().findFirstInProcessAndCreateTask(u, entity.getId(), ActiveContracts.class.getName(), ConfigDocProcessEnum.SPECIAL_PROJECTS, false);
		return entity;
	}

	/*
	 * Version two now includes sign off functionlaity to be done on the application
	 * 
	 * @param dgAllocationParent
	 * 
	 * @param u
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public ActiveContracts requesteWorkflowVersionTwo(DgAllocationParent dgAllocationParent, Users u, Users userForSignoff) throws Exception {
		ActiveContracts entity = new ActiveContracts();
		entity.setStatus(ApprovalEnum.PendingApproval);
		entity.setContractvalue(findTotalContractValueReturnDoubleValue(dgAllocationParent));
		entity.setDgAllocationParent(dgAllocationParent);
		entity.setContractStatusEnum(ContractStatusEnum.InProgress);
		entity.setMoaType(MoaTypeEnum.DGMOA);
		entity.setNooflearners(findTotalLearnersIntValue(dgAllocationParent));
		entity.setEletronicSignoff(true);
		entity.setExtensionTerminationWorkflowActive(false);
		entity.setSubmitted(false);
		int grantYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
		if (dgAllocationParent != null && dgAllocationParent.getWsp() != null && dgAllocationParent.getWsp().getFinYearNonNull() != null) {
			grantYear = dgAllocationParent.getWsp().getFinYearNonNull();
		}
		int previousYear = grantYear - 1;
		entity.setProjectedRegistrationDateStart(HAJConstants.sdfDDMMYYYY2.parse("01/04/" + previousYear));
		entity.setProjectedRegistrationDateEnd(HAJConstants.sdfDDMMYYYY2.parse("31/03/" + grantYear));
		// 30 day business rule for expiry
		entity.setDeadlineDate(GenericUtility.getStartOfDay(GenericUtility.addDaysToDateExcludeWeekends(getSynchronizedDate(), 30)));
		dao.create(entity);
		TasksService.instance().findFirstInProcessAndCreateTask(u, entity.getId(), ActiveContracts.class.getName(), ConfigDocProcessEnum.DG_CONTRACT_EC, false);
		Region r      = dgAllocationService.getRegion(dgAllocationParent.getWsp());
		String region = "";
		if (r != null) {
			region = r.getDescription();
		}
		SignoffService signoffService = new SignoffService();
		Integer        otp            = GenericUtility.generateFourNumberPin();
		Signoff        signoff        = new Signoff(userForSignoff, entity.getId(), entity.getClass().getName(), "MOA Sign Off", SignoffService.encryptPassword(otp.toString()), GenericUtility.addDaysToDate(getSynchronizedDate(), 5));
		signoffService.create(signoff);
		return entity;
	}

	public ActiveContracts requesteWorkflow(DgAllocationParent dgAllocationParent, Users u) throws Exception {
		ActiveContracts entity = new ActiveContracts();
		entity.setStatus(ApprovalEnum.PendingApproval);
		entity.setContractvalue(findTotalContractValueReturnDoubleValue(dgAllocationParent));
		entity.setDgAllocationParent(dgAllocationParent);
		entity.setContractStatusEnum(ContractStatusEnum.InProgress);
		entity.setMoaType(MoaTypeEnum.DGMOA);
		entity.setNooflearners(findTotalLearnersIntValue(dgAllocationParent));
		if (HAJConstants.DEV_TEST_PROD.equals("P")) {
			entity.setEletronicSignoff(false);
		} else {
			entity.setEletronicSignoff(true);
			// confirm
			entity.setProjectedRegistrationDateStart(HAJConstants.sdfDDMMYYYY2.parse("01/04/2019"));
			entity.setProjectedRegistrationDateEnd(HAJConstants.sdfDDMMYYYY2.parse("31/03/2020"));
		}
		// entity.setTranchintervals(3);
		dao.create(entity);
		if (HAJConstants.DEV_TEST_PROD.equals("P")) {
			TasksService.instance().findFirstInProcessAndCreateTask(u, entity.getId(), ActiveContracts.class.getName(), ConfigDocProcessEnum.DG_CONTRACT, false);
		} else {
			TasksService.instance().findFirstInProcessAndCreateTask(u, entity.getId(), ActiveContracts.class.getName(), ConfigDocProcessEnum.DG_CONTRACT_EC, false);
		}
		Region r      = dgAllocationService.getRegion(dgAllocationParent.getWsp());
		String region = "";
		if (r != null) {
			region = r.getDescription();
		}

		return entity;
	}

	public void completeWorkflow(ActiveContracts entity, Users user, Tasks tasks, List<Signoff> signOffMoaList) throws Exception {
		String error = "";
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName();
				}
			}
		}
		if (signOffMoaList != null) {
			if (tasks.getFirstInProcess()) {
				for (Signoff signoff : signOffMoaList) {
					if (signoff.getUser() == null) {
						error += "Provide MOA Sign Off User";
					}
				}
			}
		}
		if (error.length() > 0) throw new ValidationException(error);
		checkDates(entity, user);
		update(entity);
		// role permission of 2nd last step for admin
		if (tasks.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload) {
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		} else {
			RegionTown rt = null;
			if (entity.getDgAllocationParent() != null) {
				rt = RegionTownService.instance().findByTown(entity.getDgAllocationParent().getWsp().getCompany().getResidentialAddress().getTown());
			} else {
				RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
			}

			if (tasks.getProcessRole().getCompanyUserType() != null) {
				List<Users> signoffs = new ArrayList<>();
				signoffs.add(rt.getClo().getUsers());
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, signoffs, false);
			} else if (rt != null && (tasks.getProcessRole().getRoles().getCode().equals("3") || !user.equals(rt.getCrm().getUsers()))) {
				List<Users> signoffs = new ArrayList<>();
				signoffs.add(rt.getCrm().getUsers());
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, signoffs, false);
			} else {
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
			}
		}
	}

	/*
	 * Workflow for the electronic sign off
	 */
	public void completeWorkflowES(ActiveContracts entity, Users user, Tasks tasks, List<Signoff> signOffMoaList) throws Exception {
		String error = "";
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName();
				}
			}
		}
		if (signOffMoaList != null) {
			if (tasks.getFirstInProcess()) {
				for (Signoff signoff : signOffMoaList) {
					if (signoff.getUser() == null) {
						error += "Provide MOA Sign Off User";
					}
				}
			}
		}
		if (error.length() > 0) throw new ValidationException(error);
		checkDatesVersionTwo(entity, user);
		update(entity);
		RejectReasonMultipleSelectionService.instance().clearEntriesNoProcess(entity.getId(), ActiveContracts.class.getName());
		if (tasks.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload) {
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		} else {
			RegionTown rt = null;
			if (entity.getDgAllocationParent() != null) {
				rt = RegionTownService.instance().findByTown(entity.getDgAllocationParent().getWsp().getCompany().getResidentialAddress().getTown());
			} else {
				RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
			}
			// sets for sign off
			if (tasks.getProcessRole() != null && tasks.getProcessRole().getRoles() != null && tasks.getProcessRole().getRoles().getCode().equals("4")) {
				List<Users> signoffs = new ArrayList<>();
				for (Signoff signoff : signOffMoaList) {
					if (signoff.getUser() != null) {
						signoffs.add(signoff.getUser());
						sendOneTimePinForSignOffEmailNotification(entity, signoff);
					}
				}
				if (signoffs.size() == 0) {
					throw new Exception("Unable to locate users for sign off of MOA, contact support!");
				} else {
					entity.setSignOffState(true);
					entity.setAwaitingBatchSignOff(false);
					update(entity);
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, signoffs, false);
				}
				// signoffs.add(rt.getCrm().getUsers());
			} else {
				if (tasks.getProcessRole().getCompanyUserType() != null) {
					List<Users> signoffs = new ArrayList<>();
					signoffs.add(rt.getClo().getUsers());
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, signoffs, false);
				} else if (rt != null && (tasks.getProcessRole().getRoles().getCode().equals("3") || !user.equals(rt.getCrm().getUsers()))) {
					List<Users> signoffs = new ArrayList<>();
					signoffs.add(rt.getCrm().getUsers());
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, signoffs, false);
				} else {
					TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
				}
			}
		}
	}

	public void completeSignOff(ActiveContracts entity, Users user, Tasks tasks, List<Signoff> signOffMoaList) throws Exception {
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		for (Signoff signoff : signOffMoaList) {
			signoffService.update(signoff);
		}
		// Key for lazy load
		entity.setSignOffState(true);
		entity.setAwaitingBatchSignOff(true);
		entity.setSigndate(getSynchronizedDate());
		entity.setSubmitted(true);
		// entity.setStartdate(getSynchronizedDate());
		update(entity);
		RejectReasonMultipleSelectionService.instance().clearEntriesNoProcess(entity.getId(), ActiveContracts.class.getName());
		// send email notification
		TasksService.instance().completeTaskByTargetKey(entity.getClass().getName(), entity.getId());
	}

	public void sendBackToSdf(ActiveContracts entity, Users user, Tasks tasks, List<Signoff> signOffMoaList) throws Exception {
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		// update dates and information
		entity.setSignOffState(false);
		entity.setAwaitingBatchSignOff(false);
		entity.setSubmitted(false);
		update(entity);
		// update sign off entries
		for (Signoff signoff : signOffMoaList) {
			signoff.setUser(null);
			signoff.setAccept(false);
			signoff.setSignOffDate(null);
			signoffService.update(signoff);
		}
		// close and send to first place in workflow
		TasksService.instance().completeTaskByTargetKey(entity.getClass().getName(), entity.getId());
		TasksService.instance().findFirstInProcessAndCreateTask(user, entity.getId(), ActiveContracts.class.getName(), ConfigDocProcessEnum.DG_CONTRACT_EC, false);
		// could send email notification
	}

	@SuppressWarnings("unchecked")
	public List<ActiveContracts> allActiveContractsAwaitingSignOff(Class<ActiveContracts> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from ActiveContracts o where o.signOffState = :signOffStateValue and o.awaitingBatchSignOff = :awaitingBatchSignOff and o.id not in (select distinct(x.activeContracts.id) from DgContractingBulkItems x where x.statusAssigned is null)";
		filters.put("signOffStateValue", true);
		filters.put("awaitingBatchSignOff", true);
		return resolveDetails((List<ActiveContracts>) dao.sortAndFilterSearchActiveContracts(class1, first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllActiveContractsAwaitingSignOff(Class<ActiveContracts> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContracts o where o.signOffState = :signOffStateValue and o.awaitingBatchSignOff = :awaitingBatchSignOff and o.id not in (select distinct(x.activeContracts.id) from DgContractingBulkItems x where x.statusAssigned is null)";
		return dao.countSearchActiveContracts(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<ActiveContracts> allActiveContractsByCompanyId(Class<ActiveContracts> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long companyId) throws Exception {
		String hql = "select o from ActiveContracts o where o.company.id = :companyId or o.dgAllocationParent.wsp.company.id = :companyId";
		filters.put("companyId", companyId);
		return (List<ActiveContracts>) dao.sortAndFilterSearchActiveContracts(class1, first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllActiveContractsByCompanyId(Class<ActiveContracts> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContracts o where o.company.id = :companyId or o.dgAllocationParent.wsp.company.id = :companyId";
		return dao.countSearchActiveContracts(filters, hql);
	}

	public void rejectBatchOfActiveContracts(List<ActiveContracts> activeContractsList, Users user, Tasks tasks) throws Exception {
		for (ActiveContracts activeContract : activeContractsList) {
			// send email notification?
			activeContract.setSignOffState(false);
			activeContract.setAwaitingBatchSignOff(false);
			checkDates(activeContract, user);
			dao.update(activeContract);
			TasksService.instance().findFirstInProcessAndCreateTask(user, activeContract.getId(), ActiveContracts.class.getName(), ConfigDocProcessEnum.DG_CONTRACT_EC, false);
		}
		TasksService.instance().completeTask(tasks);
	}

	public void rejectEntryOfActiveContracts(ActiveContracts activeContract, Users user, List<RejectReasons> rejectionReasonsList) throws Exception {
		// set info for being processed
		activeContract.setSignOffState(false);
		activeContract.setAwaitingBatchSignOff(false);
		activeContract.setStartdate(null);
		// resets the date
		activeContract.setDeadlineDate(GenericUtility.getStartOfDay(GenericUtility.addDaysToDateExcludeWeekends(getSynchronizedDate(), 30)));
		activeContract.setSubmitted(false);
		checkDates(activeContract, user);
		dao.update(activeContract);

		// update sign offs to not accepted
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		List<Signoff> signoffs = signoffService.findByTargetKeyAndClass(activeContract.getId(), ActiveContracts.class.getName());
		for (Signoff signoff : signoffs) {
			signoff.setAccept(false);
			signoff.setSignOffDate(null);
			signoffService.update(signoff);
		}
		// set rejection reasons
		if (rejectionReasonsList != null && rejectionReasonsList.size() != 0) {
			RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(activeContract.getId(), ActiveContracts.class.getName(), rejectionReasonsList, user, ConfigDocProcessEnum.DG_CONTRACT_EC);
		}
		// send task
		TasksService.instance().findFirstInProcessAndCreateTask(user, activeContract.getId(), ActiveContracts.class.getName(), ConfigDocProcessEnum.DG_CONTRACT_EC, false);
		rejectBackToSdf(activeContract, user, rejectionReasonsList);
	}

	public void approveBatchOfActiveContracts(List<ActiveContracts> activeContractsList, Users user, Tasks tasks) throws Exception {
		for (ActiveContracts activeContract : activeContractsList) {
			// creates sign off By merseta staff
			if (signoffService == null) {
				signoffService = new SignoffService();
			}
			Signoff signOff = new Signoff(user, activeContract.getId(), activeContract.getClass().getName(), "MOA MerSETA Sign Off", "", null);
			signOff.setAccept(true);
			signOff.setSignOffDate(getSynchronizedDate());
			signoffService.create(signOff);
			activeContract.setSignOffState(false);
			activeContract.setAwaitingBatchSignOff(false);
			activeContract.setStatus(ApprovalEnum.Approved);
			activeContract.setApprovalDate(getSynchronizedDate());
			checkDates(activeContract, user);
			payTrancheOne(activeContract, user);
			dao.update(activeContract);
		}
		TasksService.instance().completeTask(tasks);
	}

	public void approveOneBatchEntryOfActiveContracts(ActiveContracts activeContract, Users user) throws Exception {
		// creates sign off By merseta staff
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		Signoff signOff = new Signoff(user, activeContract.getId(), activeContract.getClass().getName(), "MOA MerSETA Manager Sign Off", "", null);
		signOff.setAccept(true);
		signOff.setSignOffDate(getSynchronizedDate());
		signoffService.create(signOff);
		activeContract.setSignOffState(false);
		activeContract.setAwaitingBatchSignOff(false);
		activeContract.setStatus(ApprovalEnum.Approved);
		activeContract.setApprovalDate(getSynchronizedDate());
		activeContract.setStartdate(getSynchronizedDate());
		// activeContract.setSigndate(getSynchronizedDate());
		checkDates(activeContract, user);
		dao.update(activeContract);
		payTrancheOne(activeContract, user);
		// send approval notifiaction
		sendApprovalNotification(activeContract, user);
	}

	public void sendApprovalNotification(ActiveContracts activeContract, Users sessionUser) throws Exception {
		if (wspService == null) {
			wspService = new WspService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		if (dgAllocationService == null) {
			dgAllocationService = new DgAllocationService();
		}

		Wsp     wsp     = wspService.findByKey(activeContract.getDgAllocationParent().getWsp().getId());
		Company company = companyService.findByKey(wsp.getCompany().getId());
		Users   cloUser = getCLO(wsp);

		Region r = new Region();
		if (wsp != null) {
			r = getRegion(wsp);
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}

		List<Users>   emailRecivers = locateClientUsersNotification(wsp);
		List<Signoff> signoffList   = signoffService.findByTargetKeyAndClass(activeContract.getId(), activeContract.getClass().getName());
		determainSignoffUsersInList(emailRecivers, signoffList);

		String subject = "NOTIFICATION OF FINAL SIGN OFF OF MOA FOR #COMPANY_NAME_UPPER# (#ENTITY_ID#)";
		String message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> NOTIFICATION OF FINAL SIGN OFF OF MOA FOR #COMPANY_NAME_UPPER# (#ENTITY_ID#) </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Thank you for submitting a discretionary grant application #FIN_YEAR#. </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the Memorandum of Agreement (MOA) with the following reference: #ACTIVE_CONTRACT_REF_NUMBER# has been signed by the merSETA. You may access the signed MOA on the NSDMS. Please be advised that the first tranche payment will be processed accordingly. </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> For further assistance/support, kindly contact the designated Client Liaison Officer: Client Liaison Officer #CLO_FIRST_NAME# #CLO_LAST_NAME# (Email: #CLO_FIRST_NAME# #CLO_EMAIL_ADDRESS#) or the #REGION# Office. </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Chief Executive Officer of the merSETA</p>" + "<br/>";
		subject = subject.replaceAll("#COMPANY_NAME_UPPER#", company.getCompanyName().toUpperCase().trim());
		subject = subject.replaceAll("#ENTITY_ID#", company.getLevyNumber().trim());
		message = message.replaceAll("#COMPANY_NAME_UPPER#", company.getCompanyName().toUpperCase().trim());
		message = message.replaceAll("#ENTITY_ID#", company.getLevyNumber().trim());
		message = message.replaceAll("#FIN_YEAR#", wsp.getFinYearNonNull().toString());
		message = message.replaceAll("#ACTIVE_CONTRACT_REF_NUMBER#", activeContract.getRefnoAuto());
		message = message.replaceAll("#CLO_FIRST_NAME#", cloUser.getFirstName().trim());
		message = message.replaceAll("#CLO_LAST_NAME#", cloUser.getLastName().trim());
		message = message.replaceAll("#CLO_EMAIL_ADDRESS#", cloUser.getEmail().trim());
		message = message.replaceAll("#REGION#", region);

		// jasper report
		AttachmentBean            beanAttachment  = new AttachmentBean();
		ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		byte[]                    bites           = dgAllocationService.moaVersionTwoReturnByte(activeContract, true);
		beanAttachment.setExt("pdf");
		beanAttachment.setFile(bites);
		beanAttachment.setFilename("MOA_Contract.pdf");
		attachmentBeans.add(beanAttachment);

		// send notification
		if (emailRecivers.size() != 0) {
			for (Users users : emailRecivers) {
				String fullName = "";
				if (users.getTitle() != null && users.getTitle().getId() != null) {
					fullName = users.getTitle().getDescription() + " ";
				}
				fullName += users.getFirstName() + " " + users.getLastName();
				// GenericUtility.sendMail(users.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), null);
				GenericUtility.sendMailWithAttachementTempWithLog(users.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), attachmentBeans, null);
				break;
			}
		} else {
			String fullName = "";
			if (sessionUser.getTitle() != null && sessionUser.getTitle().getId() != null) {
				fullName = sessionUser.getTitle().getDescription() + " ";
			}
			fullName += sessionUser.getFirstName() + " " + sessionUser.getLastName();
			// GenericUtility.sendMail(sessionUser.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), null);
			GenericUtility.sendMailWithAttachementTempWithLog(sessionUser.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), attachmentBeans, null);
		}

		// nullify objects
		wsp           = null;
		company       = null;
		cloUser       = null;
		cloUser       = null;
		emailRecivers = null;
		signoffList   = null;
		r             = null;
	}

	public void withdrawNotification(ActiveContracts activeContract, Users sessionUser) throws Exception {
		if (wspService == null) {
			wspService = new WspService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		if (dgAllocationService == null) {
			dgAllocationService = new DgAllocationService();
		}

		Wsp     wsp     = wspService.findByKey(activeContract.getDgAllocationParent().getWsp().getId());
		Company company = companyService.findByKey(wsp.getCompany().getId());
		Users   cloUser = getCLO(wsp);

		Region r = new Region();
		if (wsp != null) {
			r = getRegion(wsp);
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}

		List<Users>   emailRecivers = locateClientUsersNotification(wsp);
		List<Signoff> signoffList   = signoffService.findByTargetKeyAndClass(activeContract.getId(), activeContract.getClass().getName());
		determainSignoffUsersInList(emailRecivers, signoffList);

		String subject = "NOTIFICATION OF WITHDRAWAL OF MOA FOR DG YEAR #FIN_YEAR# #COMPANY_NAME_UPPER# #ENTITY_ID#";
		String message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> NOTIFICATION OF WITHDRAWAL OF MOA FOR DG YEAR #FIN_YEAR# #COMPANY_NAME_UPPER# #ENTITY_ID# </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Thank you for submitting a discretionary grant application #FIN_YEAR#. </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the Memorandum of Agreement (MOA) with the following reference: #ACTIVE_CONTRACT_REF_NUMBER# has been withdrawn by #WITHDRAW_USER_FIRST_NAME# #WITHDRAW_USER_LAST_NAME# for the following reason: </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> - #WITHDRAW_REASON# </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> For further assistance/support, kindly contact the designated Client Liaison Officer: Client Liaison Officer #CLO_FIRST_NAME# #CLO_LAST_NAME# (Email: #CLO_FIRST_NAME# #CLO_EMAIL_ADDRESS#) or the #REGION# Office. </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Chief Executive Officer of the merSETA</p>" + "<br/>";
		subject = subject.replaceAll("#COMPANY_NAME_UPPER#", company.getCompanyName().toUpperCase().trim());
		subject = subject.replaceAll("#ENTITY_ID#", company.getLevyNumber().trim());
		subject = subject.replaceAll("#FIN_YEAR#", wsp.getFinYearNonNull().toString());
		message = message.replaceAll("#COMPANY_NAME_UPPER#", company.getCompanyName().toUpperCase().trim());
		message = message.replaceAll("#ENTITY_ID#", company.getLevyNumber().trim());
		message = message.replaceAll("#FIN_YEAR#", wsp.getFinYearNonNull().toString());
		message = message.replaceAll("#ACTIVE_CONTRACT_REF_NUMBER#", activeContract.getRefnoAuto());
		message = message.replaceAll("#CLO_FIRST_NAME#", cloUser.getFirstName().trim());
		message = message.replaceAll("#CLO_LAST_NAME#", cloUser.getLastName().trim());
		message = message.replaceAll("#CLO_EMAIL_ADDRESS#", cloUser.getEmail().trim());
		message = message.replaceAll("#WITHDRAW_USER_FIRST_NAME#", activeContract.getWithdrawnUser().getFirstName().trim());
		message = message.replaceAll("#WITHDRAW_USER_LAST_NAME#", activeContract.getWithdrawnUser().getLastName().trim());
		message = message.replaceAll("#WITHDRAW_REASON#", activeContract.getWithdrawalAppealEnum().getFriendlyName());
		message = message.replaceAll("#REGION#", region);

		// jasper report
		// AttachmentBean beanAttachment = new AttachmentBean();
		// ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		// byte[] bites = dgAllocationService.moaVersionTwoReturnByte(activeContract, true);
		// beanAttachment.setExt("pdf");
		// beanAttachment.setFile(bites);
		// beanAttachment.setFilename("MOA_Contract.pdf");
		// attachmentBeans.add(beanAttachment);

		// send notification
		if (emailRecivers.size() != 0) {
			for (Users users : emailRecivers) {
				String fullName = "";
				if (users.getTitle() != null && users.getTitle().getId() != null) {
					fullName = users.getTitle().getDescription() + " ";
				}
				fullName += users.getFirstName() + " " + users.getLastName();
				GenericUtility.sendMail(users.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), null);
				break;
			}
		} else {
			String fullName = "";
			if (sessionUser.getTitle() != null && sessionUser.getTitle().getId() != null) {
				fullName = sessionUser.getTitle().getDescription() + " ";
			}
			fullName += sessionUser.getFirstName() + " " + sessionUser.getLastName();
			GenericUtility.sendMail(sessionUser.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), null);
		}

		// nullify objects
		wsp           = null;
		company       = null;
		cloUser       = null;
		cloUser       = null;
		emailRecivers = null;
		signoffList   = null;
		r             = null;
	}

	public void finalRejectNotification(ActiveContracts activeContract, Users sessionUser, List<RejectReasons> rejectReasons) throws Exception {
		if (wspService == null) {
			wspService = new WspService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		if (dgAllocationService == null) {
			dgAllocationService = new DgAllocationService();
		}

		Wsp     wsp     = wspService.findByKey(activeContract.getDgAllocationParent().getWsp().getId());
		Company company = companyService.findByKey(wsp.getCompany().getId());
		Users   cloUser = getCLO(wsp);

		Region r = new Region();
		if (wsp != null) {
			r = getRegion(wsp);
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}

		List<Users>   emailRecivers = locateClientUsersNotification(wsp);
		List<Signoff> signoffList   = signoffService.findByTargetKeyAndClass(activeContract.getId(), activeContract.getClass().getName());
		determainSignoffUsersInList(emailRecivers, signoffList);

		String subject = "NOTIFICATION OF WITHDRAWAL OF MOA FOR DG YEAR #FIN_YEAR# #COMPANY_NAME_UPPER# #ENTITY_ID#";
		String message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> NOTIFICATION OF WITHDRAWAL OF MOA FOR DG YEAR #FIN_YEAR# #COMPANY_NAME_UPPER# #ENTITY_ID# </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Thank you for submitting a discretionary grant application #FIN_YEAR#. </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> The merSETA has further reviewed the allocation that had been awarded to the entity and after further consideration, kindly be advised that the Memorandum of Agreement (MOA) with the following reference: #ACTIVE_CONTRACT_REF_NUMBER# has been withdrawn by #WITHDRAW_USER_FIRST_NAME# #WITHDRAW_USER_LAST_NAME# for the following reason: </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> #REJECT_REASONS# </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> For further assistance/support, kindly contact the designated Client Liaison Officer: Client Liaison Officer #CLO_FIRST_NAME# #CLO_LAST_NAME# (Email: #CLO_FIRST_NAME# #CLO_EMAIL_ADDRESS#) or the #REGION# Office. </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Chief Executive Officer of the merSETA</p>" + "<br/>";
		subject = subject.replaceAll("#COMPANY_NAME_UPPER#", company.getCompanyName().toUpperCase().trim());
		subject = subject.replaceAll("#ENTITY_ID#", company.getLevyNumber().trim());
		subject = subject.replaceAll("#FIN_YEAR#", wsp.getFinYearNonNull().toString());
		message = message.replaceAll("#COMPANY_NAME_UPPER#", company.getCompanyName().toUpperCase().trim());
		message = message.replaceAll("#ENTITY_ID#", company.getLevyNumber().trim());
		message = message.replaceAll("#FIN_YEAR#", wsp.getFinYearNonNull().toString());
		message = message.replaceAll("#ACTIVE_CONTRACT_REF_NUMBER#", activeContract.getRefnoAuto());
		message = message.replaceAll("#CLO_FIRST_NAME#", cloUser.getFirstName().trim());
		message = message.replaceAll("#CLO_LAST_NAME#", cloUser.getLastName().trim());
		message = message.replaceAll("#CLO_EMAIL_ADDRESS#", cloUser.getEmail().trim());
		message = message.replaceAll("#WITHDRAW_USER_FIRST_NAME#", activeContract.getWithdrawnUser().getFirstName().trim());
		message = message.replaceAll("#WITHDRAW_USER_LAST_NAME#", activeContract.getWithdrawnUser().getFirstName().trim());
		message = message.replaceAll("#REJECT_REASONS#", populateRejectionReasonsForMail(rejectReasons));
		message = message.replaceAll("#REGION#", region);

		// jasper report
		// AttachmentBean beanAttachment = new AttachmentBean();
		// ArrayList<AttachmentBean> attachmentBeans = new ArrayList<>();
		// byte[] bites = dgAllocationService.moaVersionTwoReturnByte(activeContract, true);
		// beanAttachment.setExt("pdf");
		// beanAttachment.setFile(bites);
		// beanAttachment.setFilename("MOA_Contract.pdf");
		// attachmentBeans.add(beanAttachment);

		// send notification
		if (emailRecivers.size() != 0) {
			for (Users users : emailRecivers) {
				String fullName = "";
				if (users.getTitle() != null && users.getTitle().getId() != null) {
					fullName = users.getTitle().getDescription() + " ";
				}
				fullName += users.getFirstName() + " " + users.getLastName();
				GenericUtility.sendMail(users.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), null);
				break;
			}
		} else {
			String fullName = "";
			if (sessionUser.getTitle() != null && sessionUser.getTitle().getId() != null) {
				fullName = sessionUser.getTitle().getDescription() + " ";
			}
			fullName += sessionUser.getFirstName() + " " + sessionUser.getLastName();
			GenericUtility.sendMail(sessionUser.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), null);
			// GenericUtility.sendMailWithAttachementTempWithLog(sessionUser.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), attachmentBeans, null);
		}

		// nullify objects
		wsp           = null;
		company       = null;
		cloUser       = null;
		cloUser       = null;
		emailRecivers = null;
		signoffList   = null;
		r             = null;
	}

	public void rejectBackToSdf(ActiveContracts activeContract, Users sessionUser, List<RejectReasons> rejectReasons) throws Exception {
		if (wspService == null) {
			wspService = new WspService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		if (dgAllocationService == null) {
			dgAllocationService = new DgAllocationService();
		}
		if (sdfCompanyService == null) {
			sdfCompanyService = new SDFCompanyService();
		}

		Wsp     wsp     = wspService.findByKey(activeContract.getDgAllocationParent().getWsp().getId());
		Company company = companyService.findByKey(wsp.getCompany().getId());
		Users   cloUser = getCLO(wsp);

		Region r = new Region();
		if (wsp != null) {
			r = getRegion(wsp);
		}
		String region = "";
		if (r != null && r.getId() != null) {
			region = r.getDescription();
		}

		List<Users>      emailRecivers = new ArrayList<>();
		List<SDFCompany> sdfList       = sdfCompanyService.findAllPrimarySDF(activeContract.getDgAllocationParent().getWsp().getCompany());
		for (SDFCompany sdfCompany : sdfList) {
			emailRecivers.add(sdfCompany.getSdf());
			break;
		}
		emailRecivers.add(sessionUser);
		String subject = "NOTIFICATION OF NON-APPROVAL OF MOA FOR DG YEAR #FIN_YEAR#: #COMPANY_NAME_UPPER# (#ENTITY_ID#)";
		String message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> NOTIFICATION OF NON-APPROVAL OF MOA FOR DG YEAR #FIN_YEAR#: #COMPANY_NAME_UPPER# (#ENTITY_ID#) </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Thank you for submitting a discretionary grant application #FIN_YEAR#. </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be advised that the Memorandum of Agreement (MOA) with the following reference: #ACTIVE_CONTRACT_REF_NUMBER# has not been approved by #WITHDRAW_USER_FIRST_NAME# #WITHDRAW_USER_LAST_NAME# for the following reason:: </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> #REJECT_REASONS# </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please address the issue(s) and complete the electronic sign off process again. For further assistance/support, kindly contact the designated Client Liaison Officer: Client Liaison Officer #CLO_FIRST_NAME# #CLO_LAST_NAME# (Email: #CLO_FIRST_NAME# #CLO_EMAIL_ADDRESS#) or the #REGION# Office. </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Chief Executive Officer of the merSETA</p>" + "<br/>";
		subject = subject.replaceAll("#COMPANY_NAME_UPPER#", company.getCompanyName().toUpperCase().trim());
		subject = subject.replaceAll("#ENTITY_ID#", company.getLevyNumber().trim());
		subject = subject.replaceAll("#FIN_YEAR#", wsp.getFinYearNonNull().toString());
		message = message.replaceAll("#COMPANY_NAME_UPPER#", company.getCompanyName().toUpperCase().trim());
		message = message.replaceAll("#ENTITY_ID#", company.getLevyNumber().trim());
		message = message.replaceAll("#FIN_YEAR#", wsp.getFinYearNonNull().toString());
		message = message.replaceAll("#ACTIVE_CONTRACT_REF_NUMBER#", activeContract.getRefnoAuto());
		message = message.replaceAll("#CLO_FIRST_NAME#", cloUser.getFirstName().trim());
		message = message.replaceAll("#CLO_LAST_NAME#", cloUser.getLastName().trim());
		message = message.replaceAll("#CLO_EMAIL_ADDRESS#", cloUser.getEmail().trim());
		message = message.replaceAll("#WITHDRAW_USER_FIRST_NAME#", sessionUser.getFirstName().trim());
		message = message.replaceAll("#WITHDRAW_USER_LAST_NAME#", sessionUser.getFirstName().trim());
		message = message.replaceAll("#REJECT_REASONS#", populateRejectionReasonsForMail(rejectReasons));
		message = message.replaceAll("#REGION#", region);

		// send notification
		if (emailRecivers.size() != 0) {
			for (Users users : emailRecivers) {
				String fullName = "";
				if (users.getTitle() != null && users.getTitle().getId() != null) {
					fullName = users.getTitle().getDescription() + " ";
				}
				fullName += users.getFirstName() + " " + users.getLastName();
				GenericUtility.sendMail(users.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), null);
				break;
			}
		} else {
			String fullName = "";
			if (sessionUser.getTitle() != null && sessionUser.getTitle().getId() != null) {
				fullName = sessionUser.getTitle().getDescription() + " ";
			}
			fullName += sessionUser.getFirstName() + " " + sessionUser.getLastName();
			GenericUtility.sendMail(sessionUser.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), null);
		}

		// nullify objects
		wsp           = null;
		company       = null;
		cloUser       = null;
		cloUser       = null;
		emailRecivers = null;
		r             = null;
	}

	private String populateRejectionReasonsForMail(List<RejectReasons> rejectReasons) throws Exception {
		String list = "<ul>";
		try {
			for (RejectReasons rejectReason : rejectReasons) {
				list += "<li> " + rejectReason.getDescription();
				list += "</li>";
			}
		} catch (Exception e) {
		}
		list += "</ul>";
		return list;
	}

	private List<Users> locateClientUsersNotification(Wsp entity) throws Exception {
		if (sdfCompanyService == null) {
			sdfCompanyService = new SDFCompanyService();
		}
		if (companyUsersService == null) {
			companyUsersService = new CompanyUsersService();
		}

		List<Users>      users   = new ArrayList<Users>();
		List<SDFCompany> sdfList = sdfCompanyService.findAllPrimarySDF(entity.getCompany());
		for (SDFCompany sdfCompany : sdfList) {
			users.add(sdfCompany.getSdf());
			break;
		}

		// locate Labour/Union SDF
		// List<SDFCompany> sdfList = sdfCompanyService.findByCompanyAndSdfType(entity.getCompany(), (long) 3);
		// for (SDFCompany sdfCompany : sdfList) {
		// users.add(sdfCompany.getSdf());
		// break;
		// }
		// locate Employee SDF
		// sdfList = sdfCompanyService.findByCompanyAndSdfType(entity.getCompany(), (long) 4);
		// for (SDFCompany sdfCompany : sdfList) {
		// users.add(sdfCompany.getSdf());
		// break;
		// }
		// sdfList = null;
		// HR manager
		List<CompanyUsers> cu = companyUsersService.findByCompanyAndUserPosition(entity.getCompany(), (long) 3);
		for (CompanyUsers companyUsers : cu) {
			users.add(companyUsers.getUser());
			break;
		}
		// Training Manager
		cu = companyUsersService.findByCompanyAndUserPosition(entity.getCompany(), (long) 4);
		for (CompanyUsers companyUsers : cu) {
			users.add(companyUsers.getUser());
			break;
		}
		cu = null;
		return users;
	}

	public Users getCLO(Wsp wsp) throws Exception {
		RegionTown rt      = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Users      cloUser = rt.getClo().getUsers();
		return cloUser;
	}

	public Region getRegion(Wsp wsp) throws Exception {
		RegionService regionService = new RegionService();
		RegionTown    rt            = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Region        r             = regionService.findByKey(rt.getRegion().getId());
		return r;
	}

	private void determainSignoffUsersInList(List<Users> emailRecivers, List<Signoff> signoffList) throws Exception {
		for (Signoff signoff : signoffList) {
			boolean signoffUserNotInList = true;
			if (signoff.getUser() != null && signoff.getUser().getId() != null) {
				for (Users user : emailRecivers) {
					if (user.getId().equals(signoff.getUser().getId())) {
						signoffUserNotInList = false;
						break;
					}
				}
			} else {
				signoffUserNotInList = false;
			}
			if (signoffUserNotInList) {
				emailRecivers.add(signoff.getUser());
			}
		}
	}

	public void completeWorkflowProjectTermination(ActiveContracts entity, Users user, Tasks tasks) throws Exception {
		/*
		 * String error = ""; if (entity.getDocs() != null) { for (Doc doc : entity.getDocs()) { if (doc.getId() != null) { DocByte docByte = DocService.instance().findDocByteByKey(doc.getId()); if (docByte != null) doc.setData(docByte.getData()); } if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) { error += "Upload " + doc.getConfigDoc().getName(); } } } if (error.length() > 0) throw new
		 * ValidationException(error);
		 */
		uploadDocuments(entity, tasks, user);
		checkDates(entity, user);
		update(entity);

		/*
		 * RegionTown rt = RegionTownService.instance().findByTown(entity.getDgAllocationParent().getWsp().getCompany().getResidentialAddress().getTown());
		 * 
		 * if (tasks.getProcessRole().getCompanyUserType() != null) { List<Users> signoffs = new ArrayList<>(); signoffs.add(rt.getClo().getUsers()); TasksService.instance().findNextInProcessAndCreateTask(user, tasks, signoffs, false); } else if (tasks.getProcessRole().getRoles().getCode().equals("3") || !user.equals(rt.getCrm().getUsers())) { List<Users> signoffs = new ArrayList<>(); signoffs.add(rt.getCrm().getUsers()); TasksService.instance().findNextInProcessAndCreateTask(user, tasks,
		 * signoffs, false); } else { TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false); }
		 */

		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		List<Users> usersList = getUsersListToSendNotification(entity, user);
		for (Users u : usersList) {
			sendNotification(entity, u);
		}
	}

	public void completeWorkflowProjectTerminationSDF(ActiveContracts entity, Users user, Tasks tasks) throws Exception {
		List<Users> users = new ArrayList<>();
		if (entity.getClo() != null) {
			users.add(entity.getClo());
		} else {
			throw new Exception("No Client Laison Office Assigned");
		}
		uploadDocuments(entity, tasks, user);
		checkDates(entity, user);
		update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, users, false);
		List<Users> usersList = getUsersListToSendNotification(entity, user);
		for (Users u : usersList) {
			sendNotification(entity, u);
		}
	}

	private void checkDates(ActiveContracts entity, Users user) {
		/**
		 * Checking if user has been set before As SDF,CLO or CRM
		 */
		boolean addUser = true;
		if (entity != null) {
			if ((entity.getSdf() != null && entity.getSdf().getId().equals(user.getId())) || (entity.getClo() != null && entity.getClo().getId().equals(user.getId())) || (entity.getCrm() != null && entity.getCrm().getId().equals(user.getId()))) {
				addUser = false;
			}
		}
		if (addUser) {
			if (entity.getSdf() == null) {
				entity.setSdf(user);
				entity.setSdfSignDate(getSynchronizedDate());
			} else if (entity.getClo() == null) {
				entity.setClo(user);
				entity.setCloSignDate(getSynchronizedDate());
			} else if (entity.getCrm() == null) {
				entity.setCrm(user);
				entity.setCrmSignDate(getSynchronizedDate());
			}
		}
	}

	private void checkDatesVersionTwo(ActiveContracts entity, Users user) {
		/**
		 * Checking if user has been set before As SDF,CLO or CRM
		 */
		if (entity.getSdf() == null || entity.getSdf().getId().equals(user.getId())) {
			entity.setSdf(user);
			entity.setSdfSignDate(getSynchronizedDate());
		} else if (entity.getClo() == null || entity.getClo().getId().equals(user.getId())) {
			entity.setClo(user);
			entity.setCloSignDate(getSynchronizedDate());
		} else if (entity.getCrm() == null || entity.getCrm().getId().equals(user.getId())) {
			entity.setCrm(user);
			entity.setCrmSignDate(getSynchronizedDate());
		}
	}

	public void approveWorkflow(ActiveContracts entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.WaitingForManager);
		checkDates(entity, user);
		dao.update(entity);
		RejectReasonMultipleSelectionService.instance().clearEntriesNoProcess(entity.getId(), ActiveContracts.class.getName());
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void approveWorkflowProjectTermination(ActiveContracts entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.TerminateProjectWaitingForManager);
		dao.update(entity);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		/*
		 * if(tasks.getProcessRole().getRoleOrder() == 3) { List<Users> usersList = getUsersListToSendNotification(entity, user); for(Users u:usersList) { sendApprovalEmail(entity, u); } }
		 */
	}

	public void rejectWorkflow(ActiveContracts entity, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(entity.getDgAllocationParent().getWsp().getCompany().getResidentialAddress().getTown());
		if (tasks.getProcessRole().getCompanyUserType() != null) {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(rt.getCrm().getUsers());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, signoffs);
		} else if (tasks.getProcessRole().getRoles().getCode().equals("4")) {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(rt.getClo().getUsers());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, signoffs);
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
		}
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), ActiveContracts.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.DG_CONTRACT);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
	}

	public void rejectWorkflowProjectTermination(ActiveContracts entity, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(entity.getDgAllocationParent().getWsp().getCompany().getResidentialAddress().getTown());
		if (tasks.getProcessRole().getCompanyUserType() != null) {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(rt.getCrm().getUsers());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, signoffs);
		} else if (tasks.getProcessRole().getRoles().getCode().equals("4")) {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(rt.getClo().getUsers());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, signoffs);
		} else {
			if (tasks.getProcessRole().getRoleOrder() == 2) {
				List<Users> users = new ArrayList<>();
				List<Tasks> t     = TasksService.instance().findTasksByTypeAndKey(tasks.getWorkflowProcess(), tasks.getTargetClass(), tasks.getTargetKey());
				users.add(t.get(t.size() - 1).getCreateUser());
				Company com         = entity.getDgAllocationParent().getWsp().getCompany();
				String  description = "There is a project termination request for " + com.getCompanyName() + " - " + com.getLevyNumber() + " has not been approved. Please review. ";
				TasksService.instance().findPreviousInProcessAndCreateTask(description, user, tasks.getTargetKey(), tasks.getTargetClass(), true, tasks, null, users);
				// TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, users);
			} else {
				TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
			}
		}
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), ActiveContracts.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.DG_CONTRACT);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
	}

	public void rejectWorkflowProjectTerminationSDF(ActiveContracts entity, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(entity.getDgAllocationParent().getWsp().getCompany().getResidentialAddress().getTown());
		if (tasks.getProcessRole().getCompanyUserType() != null) {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(rt.getCrm().getUsers());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, signoffs);
		} else if (tasks.getProcessRole().getRoles().getCode().equals("4")) {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(rt.getClo().getUsers());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, signoffs);
		} else {
			if (tasks.getProcessRole().getRoleOrder() == 2) {
				List<Users> users = new ArrayList<>();
				List<Tasks> t     = TasksService.instance().findTasksByTypeAndKey(tasks.getWorkflowProcess(), tasks.getTargetClass(), tasks.getTargetKey());
				users.add(t.get(t.size() - 1).getCreateUser());
				Company com         = entity.getDgAllocationParent().getWsp().getCompany();
				String  description = "There is a project termination request for " + com.getCompanyName() + " - " + com.getLevyNumber() + " has not been approved. Please review. ";
				TasksService.instance().findPreviousInProcessAndCreateTask(description, user, tasks.getTargetKey(), tasks.getTargetClass(), true, tasks, null, users);
				// TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, users);
			} else {
				TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
			}
		}
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), ActiveContracts.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.DG_CONTRACT);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
	}

	public void finalApproveWorkflow(ActiveContracts entity, Users user, Tasks tasks) throws Exception {
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovalDate(getSynchronizedDate());
		RejectReasonMultipleSelectionService.instance().clearEntriesNoProcess(entity.getId(), ActiveContracts.class.getName());
		checkDates(entity, user);
		payTrancheOne(entity, user);
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
	}

	public void finalApproveWorkflowProjectTermination(ActiveContracts entity, Users user, Tasks tasks) throws Exception {
		uploadDocuments(entity, tasks, user);
		entity.setStatus(ApprovalEnum.ProjectTerminated);
		entity.setAmendmentenddate(getSynchronizedDate());
		entity.setApprovalDate(getSynchronizedDate());
		entity.setContractStatusEnum(ContractStatusEnum.Terminated);
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
		addTranchePayment(entity, user);
		List<Users> usersList = getUsersListToSendNotification(entity, user);
		super.removeDuplicatesFromList(usersList);
		for (Users u : usersList) {
			sendProjectTerminationApprovalNotification(entity, u, tasks);
		}
	}

	public void dataFixPayTrancheOne(ActiveContracts entity, Users user) throws Exception {
		payTrancheOneOneTimeFix(entity, user);
	}

	// tranch one payments auto-approved
	private void payTrancheOne(ActiveContracts entity, Users user) throws Exception {
		List<ProjectImplementationPlan> projectImplementationPlans = implementationPlanService.findByActiveContract(entity);
		BigDecimal                      openVal                    = BigDecimal.ZERO;
		for (ProjectImplementationPlan projectImplementationPlan : projectImplementationPlans) {
			if (projectImplementationPlan.getTotalAwardAmount().compareTo(BigDecimal.ZERO) == 1) {
				ActiveContractDetail acd = activeContractDetailService.addTranchePaymentDetail(entity, user, 0.25, TrancheEnum.TRANCHE_ONE, openVal, projectImplementationPlan);
				openVal = acd.getClosingbalance();
				acd.setStatus(ApprovalEnum.Approved);
				acd.setApprovalDate(getSynchronizedDate());
				create(entity);
				activeContractDetailService.create(acd);
				// activeContractDetailService.requesteWorkflow(acd, user);
				// activeContractDetailService.requesteWorkflowTranchOne(acd, user);
			}
		}
	}

	// tranch one payments auto-approved
	private void payTrancheOneOneTimeFix(ActiveContracts entity, Users user) throws Exception {
		List<ProjectImplementationPlan> projectImplementationPlans = implementationPlanService.findByActiveContract(entity);
		BigDecimal                      openVal                    = BigDecimal.ZERO;
		for (ProjectImplementationPlan projectImplementationPlan : projectImplementationPlans) {
			if (activeContractDetailService.countActiveContractDetailByPip(projectImplementationPlan.getId()) == 0) {
				if (projectImplementationPlan.getTotalAwardAmount().compareTo(BigDecimal.ZERO) == 1) {
					ActiveContractDetail acd = activeContractDetailService.addTranchePaymentDetail(entity, user, 0.25, TrancheEnum.TRANCHE_ONE, openVal, projectImplementationPlan);
					openVal = acd.getClosingbalance();
					acd.setStatus(ApprovalEnum.Approved);
					acd.setApprovalDate(getSynchronizedDate());
					create(entity);
					activeContractDetailService.create(acd);
				}
			}
		}
	}

	public void addTranchePayment(ActiveContracts entity, Users user) throws Exception {
		if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
			if (entity.getRecoverableAmount() != null && entity.getRecoverableAmount() > 0) {
				ActiveContractDetail acd = activeContractDetailService.addTranchePayment(entity, user, entity.getRecoverableAmount() * -1, TrancheEnum.RECOVERABLE_AMOUNT, null);
				dao.create(acd);
			}
		}
	}

	public void finalRejectWorkflow(ActiveContracts entity, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), ActiveContracts.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.DG_CONTRACT);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
	}

	public void finalRejectWorkflowProjectTermination(ActiveContracts entity, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		entity.setContractStatusEnum(ContractStatusEnum.InProgress);
		dao.update(entity);
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);

		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), ActiveContracts.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.DG_CONTRACT);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
	}

	public void finalRejectWorkflowAndTask(ActiveContracts entity, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		create(entity);
		dao.update(entity);
		// TasksService.instance().completeTask(tasks);
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), ActiveContracts.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.DG_CONTRACT);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
	}

	public void finalRejectWorkflowAndTaskProjectTermination(ActiveContracts entity, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovalDate(getSynchronizedDate());
		dao.update(entity);
		// TasksService.instance().completeTask(tasks);
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(), ActiveContracts.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.DG_CONTRACT);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
	}

	public ActiveContracts findByDgAllocationParent(Long id) throws Exception {
		return dao.findByDgAllocationParent(id);
	}

	public ActiveContracts findByWSP(Long id) throws Exception {
		return dao.findByWSP(id);
	}
	
	public ActiveContracts findByWSP(Wsp wsp) throws Exception {
		return dao.findByWSP(wsp);
	}

	public List<ActiveContracts> findActiveContractsByDgAllocationParent(Long id) throws Exception {
		return dao.findActiveContractsByDgAllocationParent(id);
	}

	public BigDecimal findTotalContractValue(DgAllocationParent dgAllocationParent) throws Exception {
		return dao.findTotalContractValue(dgAllocationParent);
	}

	public Double findTotalContractValueReturnDoubleValue(DgAllocationParent dgAllocationParent) throws Exception {
		BigDecimal amount = dao.findTotalContractValue(dgAllocationParent);
		if (amount.equals(BigDecimal.valueOf(0.0))) {
			return 0.0;
		} else {
			return amount.doubleValue();
		}
	}

	public java.lang.Long findTotalLearners(DgAllocationParent dgAllocationParent) throws Exception {
		return dao.findTotalLearners(dgAllocationParent);
	}

	public int findTotalLearnersIntValue(DgAllocationParent dgAllocationParent) throws Exception {
		return dao.findTotalLearnersIntValue(dgAllocationParent);
	}

	public List<ActiveContracts> findByCompany(Company c) throws Exception {
		return resolveDetails(dao.findByCompany(c.getId()));
	}

	public List<ActiveContracts> findByCompanyNoResolveDetails(Company c) throws Exception {
		return dao.findByCompany(c.getId());
	}

	public void createNewDetailForProcessing(UploadedFile file, Users u, ActiveContractDetail activeContractDetail) throws Exception {
		dao.create(activeContractDetail);
		Doc doc = new Doc();
		doc.setData(file.getContents());
		doc.setOriginalFname(file.getFileName());
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		doc.setUsers(u);
		doc.setTargetKey(activeContractDetail.getId());
		doc.setTargetClass(ActiveContractDetail.class.getName());
		DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), u);
		TasksService.instance().findFirstInProcessAndCreateTask(u, activeContractDetail.getId(), ActiveContractDetail.class.getName(), ConfigDocProcessEnum.CONTRACT_DETAIL, false);
	}

	// *************************************************************************************************************
	// //

	public void submitProjectTermination(ActiveContracts entity, Users user) throws Exception {
		try {
			List<Users> users = new ArrayList<>();
			users.add(user);
			entity.setStatus(ApprovalEnum.PendingApproval);
			entity.setContractStatusEnum(ContractStatusEnum.SuspendProgress);
			create(entity);
			// TasksService.instance().findFirstInProcessAndCreateTask(user, entity.getId(), entity.getClass().getName(), ConfigDocProcessEnum.DG_PROJECT_TERMINATION, true);
			TasksService.instance().createTaskUser(users, entity.getClass().getName(), entity.getId(), "Project Termination initiated by " + user.getFirstName() + " " + user.getLastName(), user, true, true, null, ConfigDocProcessEnum.DG_PROJECT_TERMINATION, true);

		} catch (Exception e) {
			throw e;
		}
	}

	public void submitProjectTerminationVersionTwo(ActiveContracts entity, Users user) throws Exception {
		try {
			List<Users> users = new ArrayList<>();
			users.add(user);
			entity.setStatus(ApprovalEnum.PendingApproval);
			entity.setContractStatusEnum(ContractStatusEnum.SuspendProgress);
			create(entity);
			TasksService.instance().findFirstInProcessAndCreateTask(user, entity.getId(), entity.getClass().getName(), ConfigDocProcessEnum.DG_PROJECT_TERMINATION, true);
			List<Users> usersList = getUsersListToSendNotification(entity, user);
			for (Users u : usersList) {
				// sendNotification(entity, u);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void submitProjectTerminationBySdf(ActiveContracts entity, Users user) throws Exception {
		List<Users> users = new ArrayList<>();
		users.add(user);
		entity.setStatus(ApprovalEnum.PendingApproval);
		entity.setContractStatusEnum(ContractStatusEnum.SuspendProgress);
		create(entity);
		TasksService.instance().findFirstInProcessAndCreateTask(user, entity.getId(), entity.getClass().getName(), ConfigDocProcessEnum.SDF_DG_PROJECT_TERMINATION, true);
		List<Users> usersList = getUsersListToSendNotification(entity, user);
		for (Users u : usersList) {
			// sendNotification(entity, u);
		}
	}

	public void sendApprovalEmail(ActiveContracts entity, Users u) throws Exception {
		Company company   = entity.getDgAllocationParent().getWsp().getCompany();
		String  subject   = "Projects Implementation Termination";
		String  grantyear = "";
		String  refNo     = "";
		if (entity.getDgAllocationParent() != null && entity.getDgAllocationParent().getWsp() != null && entity.getDgAllocationParent().getWsp().getFinYearNonNull() != null) {
			grantyear = entity.getDgAllocationParent().getWsp().getFinYearNonNull() + "";
		}

		if (entity.getRefno() != null) {
			refNo = entity.getRefno();
		} else if (entity.getRefnoAuto() != null) {
			refNo = entity.getRefnoAuto();
		}

		String welcome = "<p>Dear #FirstName# #Surname#,</p>" + "<p>Please be advised that the following discretionary grant MOA: #MOAReferenceNumber# for #grantyear# has been terminated. You are advised to update the tracking document and commitment schedule.</p>" + "<p>Company Name: #CompanyName#</p>" + "<p>Entity ID: #EntityID#</p>" + "<p>Kind regards,</p>" + "<p>Projects Implementation</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#CompanyName#", company.getCompanyName());
		welcome = welcome.replaceAll("#EntityID#", company.getLevyNumber());
		welcome = welcome.replaceAll("#MOAReferenceNumber#", refNo);
		welcome = welcome.replaceAll("#grantyear#", grantyear);

		GenericUtility.sendMail(u.getEmail(), subject, welcome, null);

	}

	public void sendOneTimePinForSignOffEmailNotification(ActiveContracts activeContracts, Signoff signoff) throws Exception {
		// set simple date format
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		// set service levels required
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		if (usersService == null) {
			usersService = new UsersService();
		}
		if (wspService == null) {
			wspService = new WspService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}

		Wsp     wsp     = wspService.findByKey(activeContracts.getDgAllocationParent().getWsp().getId());
		Company comapny = companyService.findByKey(wsp.getCompany().getId());

		// Sets New Expiry Date
		signoff.setExpiryDate(GenericUtility.addDaysToDate(getSynchronizedDate(), 5));
		Integer otp = signoffService.generateAndReturnNewOneTimePin(signoff);
		if (signoff.getUser() != null && signoff.getUser().getId() != null) {
			Users signoffUser = usersService.findByKey(signoff.getUser().getId());

			// set subject
			String subject = "ONE-TIME PIN TO ELECTRONICALLY SIGN DG YEAR #YEAR# MOA FOR #COMPANY_NAME# (#ENTITY_ID#)";
			subject = subject.replace("#YEAR#", wsp.getFinYearNonNull().toString());
			subject = subject.replace("#COMPANY_NAME#", comapny.getCompanyName());
			subject = subject.replace("#ENTITY_ID#", comapny.getLevyNumber());

			// set body contents
			String body = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FIRST_NAME# #LAST_NAME#, </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "Please be advised that you are required to electronically sign the merSETA Memorandum of Agreement (MOA) for DG YEAR #YEAR# for the allocation that has been accepted by your entity as the authorised representative for the entity. You are advised that, if it is established that you are not duly authorised to sign the MOA, the said action may constitute fraud and be subject to the full penalty of the law." + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
					+ "To sign the MOA electronically, you are required to enter the one-time pin (OTP) provided below:" + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> " + "<b>" + "OTP: " + otp + "</b>" + "<br/> " + "<b>" + "Expiry Date: " + sdf.format(signoff.getExpiryDate()) + "</b>" + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> " + "<b>Procedure to electronically sign the MOA</b>" + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> "
					+ "STEP 1: <b>Login on NSDMS</b> using your login details.<br/>" + "STEP 2: Go to the <b>Dashboard</b> and go to <b>Outstanding Tasks</b> and look for the task<br/>" + "STEP 3: Click on the arrow under <b>View/Edit</b> to access the task<br/>" + "STEP 4: Go to <b>MOA Sign Off</b> table to download the MOA if you wish.<br/>"
					+ "STEP 5: Go to <b>MOA Sign Off</b> table and under <b>Actions</b>, click on the pen icon. Please note that if you have not received an OTP or it has expired, click the icon to Resend OTP.<br/>" + "STEP 6: <b>Enter the OTP</b> and click on <b>Validate OTP</b><br/>" + "STEP 7: <b>Agree</b> to the terms and conditions of the MOA by <b>clicking the checkbox</b>.<br/>" + "STEP 8: Click on the <b>Sign Off</b> button and confirm/stop your action when prompted.<br/>"
					+ "STEP 9: Click on <b>Complete MOA Sign Off</b>.<br/>" + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> " + "For queries/support, please contact your regional office or the merSETA Call Centre at #CALL_CENTRE_NUMBER#" + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> " + "Please note that if you are not duly authorised to sign the MOA, please follow the steps:" + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> "
					+ "STEP 1: <b>Login on NSDMS</b>  using your login details.<br/>" + "STEP 2: Go to the <b>Dashboard</b>  and go to <b>Outstanding Tasks</b>  and look for the task.<br/>" + "STEP 3: Click on the arrow under <b>View/Edit</b> to access the task.<br/>" + "STEP 4: Click on the button <b>'Not Duly Authorised For Sign Off'</b>.<br/>" + "STEP 5: Click <b>yes</b> to confirm/accept.<br/>" + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely, <br/>"
					+ "<b>merSETA Projects Implementation</b>" + "</p>";

			body = body.replace("#FIRST_NAME#", signoffUser.getFirstName().trim());
			body = body.replace("#LAST_NAME#", signoffUser.getLastName().trim());
			body = body.replace("#YEAR#", wsp.getFinYearNonNull().toString());
			body = body.replace("#CALL_CENTRE_NUMBER#", "+27 (0) 861 637 738");

			// String message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> OTP: " +otp+ " </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Expiry Date Of OTP: " +sdf.format(signoff.getExpiryDate())+ " </p>";
			GenericUtility.sendMail(signoffUser.getEmail(), subject, body, null);
		} else {
			throw new Exception("Unable to locate user for sign notification of one time pin! contact support!");
		}
	}

	public void sendOneTimePinForSignOffSms(ActiveContracts activeContracts, Signoff signoff) throws Exception {
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		if (usersService == null) {
			usersService = new UsersService();
		}
		if (signoff.getUser() != null && signoff.getUser().getId() != null) {
			Users user = usersService.findByKey(signoff.getUser().getId());
		} else {
			throw new Exception("Unable to locate user for sign notification of one time pin! contact support!");
		}
	}

	public Boolean validatePinExpiryDate(Signoff signoff) throws Exception {
		if (getSynchronizedDate().after(signoff.getExpiryDate())) {
			return false;
		} else {
			return true;
		}
	}

	public Boolean validatePin(Signoff signoff, Integer inputPassword) throws Exception {
		StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		if (!passwordEncryptor.checkPassword(inputPassword.toString().trim(), signoff.getOneTimePassword().trim())) {
			return false;
		} else {
			return true;
		}
	}

	public int countDgContractsAwaitingProcessing() throws Exception {
		return dao.countDgContractsAwaitingProcessing();
	}

	/*
	 * private ActiveContractExtensionRequestService activeContractExtensionRequestService = null; private ActiveContractTerminationRequestService activeContractTerminationRequestService = null;
	 */
	public void requestExtensionOfContract(ActiveContracts activeContracts, Users sessionUser, Doc doc) throws Exception {
		if (doc == null || doc.getData() == null) {
			throw new Exception("Upload Supporing Document Before Proceeding");
		}
		if (activeContractExtensionRequestService == null) {
			activeContractExtensionRequestService = new ActiveContractExtensionRequestService();
		}
		activeContractExtensionRequestService.requestExtensionRequestWorkflowByActiveContract(activeContracts, sessionUser, doc);
	}

	public void requestTerminationOfContract(ActiveContracts activeContracts, Users sessionUser, Doc doc) throws Exception {
		if (doc == null || doc.getData() == null) {
			throw new Exception("Upload Supporing Document Before Proceeding");
		}
		if (activeContractTerminationRequestService == null) {
			activeContractTerminationRequestService = new ActiveContractTerminationRequestService();
		}
		activeContractTerminationRequestService.requestTerminationRequestWorkflowByActiveContract(activeContracts, sessionUser, doc, false);
	}

	public void checkIfCanWithDrawApplication(ActiveContracts activeContracts) throws Exception {
		// only DG MOA Projects
		if (activeContracts.getMoaType() == null || activeContracts.getMoaType() != MoaTypeEnum.DGMOA) {
			throw new Exception("You can only withdraw at DG MOA from this section. Please select a DG MOA to use this functionlaity.");
		}
		// Tranche Payments check
		if (activeContractDetailService == null) {
			activeContractDetailService = new ActiveContractDetailService();
		}
		if (activeContractDetailService.countActiveContractDetailByActiveContract(activeContracts.getId()) != 0) {
			throw new Exception("Tranche payments linked to MOA. Please proceed to use project termination for this MOA.");
		}
		// check if in batch process
		if (dgContractingBulkItemsService == null) {
			dgContractingBulkItemsService = new DgContractingBulkItemsService();
		}
		if (dgContractingBulkItemsService.countByActiveContractId(activeContracts.getId()) != 0) {
			throw new Exception("MOA found in batch processing. Please withdraw MOA there.");
		}
	}

	public void withdrawActiveContracts(ActiveContracts activeContracts, Users sessionUser, DiscretionalWithdrawalAppealEnum withdrawalAppealEnum, Tasks task, Doc doc) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();

		if (doc == null || doc.getData() == null) {
			throw new Exception("Upload Supporing Document Before Proceeding");
		} else {
			DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
		}

		// sets active contract to withdrawn
		activeContracts.setStatus(ApprovalEnum.Withdrawn);
		activeContracts.setContractStatusEnum(ContractStatusEnum.Withdrawn);
		activeContracts.setWithdrawnUser(sessionUser);
		activeContracts.setWithdrawnDate(getSynchronizedDate());
		activeContracts.setWithdrawalAppealEnum(withdrawalAppealEnum);
		activeContracts.setSignOffState(false);
		activeContracts.setAwaitingBatchSignOff(false);
		updateList.add(activeContracts);

		// sets pip to withdrawn
		if (implementationPlanService == null) {
			implementationPlanService = new ProjectImplementationPlanService();
		}
		List<ProjectImplementationPlan> pipList = implementationPlanService.findByActiveContract(activeContracts);
		for (ProjectImplementationPlan projectImplementationPlan : pipList) {
			projectImplementationPlan.setStatus(ApprovalEnum.Withdrawn);
			updateList.add(projectImplementationPlan);
		}

		// set the DG allocation Parent to withdrawn
		DgAllocationParentService dgAllocationParentService = new DgAllocationParentService();
		List<DgAllocationParent>  dgParentList              = dgAllocationParentService.findByWSPReturnList(activeContracts.getDgAllocationParent().getWsp().getId());
		if (dgParentList.size() != 0) {
			DgAllocationParent dgParent = dgAllocationParentService.findByKey(dgParentList.get(0).getId());
			dgParent.setStatus(ApprovalEnum.Withdrawn);
			dgParent.setApprovalDate(getSynchronizedDate());
			updateList.add(dgParent);
			dgParent = null;
		}
		if (updateList.size() != 0) {
			dao.updateBatch(updateList, 100);
		}
		dgAllocationParentService = null;
		pipList                   = null;
		dgParentList              = null;

		if (task != null) {
			TasksService.instance().completeTask(task);
		} else {
			TasksService.instance().closeOpenTasksByTargetClassAndKey(activeContracts.getClass().getName(), activeContracts.getId());
		}

		withdrawNotification(activeContracts, sessionUser);
	}

	public void finalRejectActiveContract(ActiveContracts activeContracts, Users sessionUser, Tasks task, List<RejectReasons> rejectionReasonsList) throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();

		// sets active contract to withdrawn
		activeContracts.setStatus(ApprovalEnum.Rejected);
		activeContracts.setRejectionUser(sessionUser);
		activeContracts.setRejectionDate(getSynchronizedDate());
		activeContracts.setSignOffState(false);
		activeContracts.setAwaitingBatchSignOff(false);
		activeContracts.setContractStatusEnum(ContractStatusEnum.Withdrawn);
		activeContracts.setWithdrawnUser(sessionUser);
		activeContracts.setWithdrawnDate(getSynchronizedDate());
		updateList.add(activeContracts);

		// sets pip to withdrawn
		if (implementationPlanService == null) {
			implementationPlanService = new ProjectImplementationPlanService();
		}
		List<ProjectImplementationPlan> pipList = implementationPlanService.findByActiveContract(activeContracts);
		for (ProjectImplementationPlan projectImplementationPlan : pipList) {
			projectImplementationPlan.setStatus(ApprovalEnum.Rejected);
			updateList.add(projectImplementationPlan);
		}

		// set the DG allocation Parent to withdrawn
		DgAllocationParentService dgAllocationParentService = new DgAllocationParentService();
		List<DgAllocationParent>  dgParentList              = dgAllocationParentService.findByWSPReturnList(activeContracts.getDgAllocationParent().getWsp().getId());
		if (dgParentList.size() != 0) {
			DgAllocationParent dgParent = dgAllocationParentService.findByKey(dgParentList.get(0).getId());
			dgParent.setStatus(ApprovalEnum.Rejected);
			dgParent.setApprovalDate(getSynchronizedDate());
			updateList.add(dgParent);
			dgParent = null;
		}
		if (updateList.size() != 0) {
			dao.updateBatch(updateList, 100);
		}
		dgAllocationParentService = null;
		pipList                   = null;
		dgParentList              = null;

		if (task != null) {
			TasksService.instance().completeTask(task);
		}
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDbNoProcess(activeContracts.getId(), activeContracts.getClass().getName(), rejectionReasonsList, sessionUser);
		// send rejection notification
		finalRejectNotification(activeContracts, sessionUser, rejectionReasonsList);
	}

	public void downloadMoaVersionTwo(ActiveContracts activeContracts, boolean eletronicSignoff) throws Exception {
		dgAllocationService.downloadMoaVersionTwo(activeContracts, eletronicSignoff);
	}

	public void sendNotification(ActiveContracts entity, Users user) throws Exception {
		Company             company = entity.getDgAllocationParent().getWsp().getCompany();
		Map<String, Object> params  = new HashMap<String, Object>();
		JasperService.addLogo(params);
		String subject = "PROJECT TERMINATION";

		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised of the intention to terminate discretionary grant MOA for " + company.getCompanyName() + " - " + company.getLevyNumber() + " (Ref No: " + entity.getRefnoAuto() + "). Kindly note that the merSETA may request additional information." + "Kindly be advised that you will be notified of any refund that may be due to the merSETA in due course." + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For any assistance/clarity, please contact your Regional Office or merSETA Head Office.</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><B>Programmes Implementation Unit</B></p>";

		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);
	}

	private Users getCoo() {
		Users                          user                           = new Users();
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		List<Users>                    cooList                        = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Chief_Operations_Officer_ID);
		if (cooList != null && cooList.size() > 0) {
			user = cooList.get(0);
		}
		return user;
	}

	public void sendProjectTerminationApprovalNotification(ActiveContracts entity, Users user, Tasks tasks) throws Exception {
		Company             company = entity.getDgAllocationParent().getWsp().getCompany();
		Map<String, Object> params  = new HashMap<String, Object>();
		JasperService.addLogo(params);
		String terminationDate = HAJConstants.sdfDDMMMMYYYY.format(tasks.getActionDate());
		String subject         = "PROJECT TERMINATION";
		Users  coo             = getCoo();
		String mssg            = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "The merSETA advises you that the discretionary grant MOA for " + company.getCompanyName() + " - " + company.getLevyNumber() + " (Ref No: " + entity.getRefnoAuto() + ") has been terminated on " + terminationDate + ".</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For any assistance/clarity, please contact your Regional Office or the merSETA Head Office.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">We wish to assure you of our continued support to your entity in assisting in its future skills development initiatives.</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + coo.getFirstName() + " " + coo.getLastName() + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"><B>Chief Operations Officer</B></p>";

		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);
	}

	private List<Users> getUsersListToSendNotification(ActiveContracts entity, Users user) throws Exception {
		// Company,
		// CLO,
		// Manager: Levies and Grants,
		// Senior Manager: Programme Implementation & Legal Manager

		List<Users> users = new ArrayList<>();
		users.add(user);
		Company                        company                        = entity.getDgAllocationParent().getWsp().getCompany();
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();

		List<Users> seniorManagerLevyAndGrant = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Manager_Levy_And_Grant_ID);
		if (seniorManagerLevyAndGrant != null && seniorManagerLevyAndGrant.size() > 0) {
			users.addAll(seniorManagerLevyAndGrant);
		}
		List<Users> seniorManagerClientService = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Senior_Manager_Client_Service_ID);
		if (seniorManagerClientService != null && seniorManagerClientService.size() > 0) {
			users.addAll(seniorManagerClientService);
		}

		List<Users> managerLegalAndContractManagement = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.Manager_Legal_and_Contract_Management_ID);
		if (managerLegalAndContractManagement != null && managerLegalAndContractManagement.size() > 0) {
			users.addAll(managerLegalAndContractManagement);
		}

		if (entity.getSdf() != null) {
			users.add(entity.getSdf());
		} else {
			if (company != null && company.getId() != null) {
				SDFCompany primarySDF = companyService.findPrimarySDF(companyService.findByKey(company.getId()));
				if (primarySDF != null) {
					users.add(primarySDF.getSdf());
				}
			}
		}

		if (entity.getClo() != null) {
			users.add(entity.getClo());
		} else {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			if (rt != null && rt.getClo() != null) {
				users.add(rt.getClo().getUsers());
				// users.add(rt.getCrm().getUsers());
			}
		}

		if (entity.getCrm() != null) {
			users.add(entity.getCrm());
		} else {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			if (rt != null && rt.getClo() != null) {
				// users.add(rt.getClo().getUsers());
				users.add(rt.getCrm().getUsers());
			}
		}

		return users;
	}

	public void uploadDocuments(ActiveContracts entity, Tasks task, Users sessionUser) throws Exception {
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

	public void activeContractReminderSchedual(Date today) throws Exception {
		Date                  todayCopied         = GenericUtility.getStartOfDay(today);
		List<ActiveContracts> openActiveContracts = findByEletronicSignoffNotSubmittedAndNotOpen(true, false);
		List<IDataEntity>     updateList          = new ArrayList<>();
		// sets service required
		if (companyService == null) {
			companyService = new CompanyService();
		}

		// loops through each entry
		for (ActiveContracts activeContract : openActiveContracts) {

			boolean addToUpdate = false;

			// check if expire date provided
			if (activeContract.getDeadlineDate() == null) {
				// if not populate date
				activeContract.setDeadlineDate(GenericUtility.getStartOfDay(GenericUtility.addDaysToDateExcludeWeekends(activeContract.getCreateDate(), 30)));
				addToUpdate = true;
			}

			Date deadlineDate = GenericUtility.getStartOfDay(activeContract.getDeadlineDate());

			if (todayCopied.equals(deadlineDate) || todayCopied.after(deadlineDate)) {
				addToUpdate = true;

				/*
				 * Business rule if current date after reminder due date Kick off termination task
				 */
				String information = "";
				ScheduleChangesLogService.instance().addNewEntry(null, null, activeContract.getClass().getName(), activeContract.getId(), information, false);

				// generate termination request
				if (activeContractTerminationRequestService == null) {
					activeContractTerminationRequestService = new ActiveContractTerminationRequestService();
				}
				activeContractTerminationRequestService.systemGeneratedTerminationRequestWorkflowByActiveContract(activeContract, true);

				// update the active contract
				activeContract.setExtensionTerminationWorkflowActive(true);

			} else {
				Integer amount           = GenericUtility.getDaysBetweenDatesExcludeWeekends(todayCopied, deadlineDate);
				boolean sendNotification = false;
				String  subject          = "";
				String  message          = "";

				/*
				 * business rules: Reminder before due date 15 day reminder before due date 10 day reminder before due date 5 Day reminder before due date 3 day reminder before due date 1 day reminder before due date ------ Create Date: Tue Oct 01 00:00:00 SAST 2019 ------ due date: Tue Nov 12 00:00:00 SAST 2019 ------ fifteenDayReminder: Mon Oct 21 00:00:00 SAST 2019 ------ 15 ----- Days between fifteenDayReminder and due date: 17 ------ tenDayReminder: Mon Oct 28 00:00:00 SAST 2019 ------ 10
				 * ----- Days between tenDayReminder and due date: 12 ------ fiveDayReminder: Mon Nov 04 00:00:00 SAST 2019 ------ 5 ----- Days between fiveDayReminder and due date: 7 ------ threeReminder: Thu Nov 07 00:00:00 SAST 2019 ------ 3 ----- Days between threeReminder and due date: 4 ------ oneDayReminder: Thu Nov 07 00:00:00 SAST 2019 ------ 1 ----- Days between oneDayReminder and due date: 2
				 */
				switch (amount) {
					case 17:
						sendNotification = true;
						break;
					case 12:
						sendNotification = true;
						break;
					case 7:
						sendNotification = true;
						break;
					case 4:
						sendNotification = true;
						break;
					case 2:
						sendNotification = true;
						break;
					default:
						sendNotification = false;
						break;
				}

				if (sendNotification) {

					// locate objects required for actions
					if (companyService == null) {
						companyService = new CompanyService();
					}
					if (wspService == null) {
						wspService = new WspService();
					}
					if (dgAllocationParentService == null) {
						dgAllocationParentService = new DgAllocationParentService();
					}
					if (usersService == null) {
						usersService = new UsersService();
					}

					DgAllocationParent dgAllocationParent = null;
					Wsp                wsp                = null;
					Company            company            = null;
					RegionTown         rt                 = null;
					Users              cloUser            = null;

					// populate DgAllocationParent Object
					if (activeContract.getDgAllocationParent() != null && activeContract.getDgAllocationParent().getId() != null) {
						dgAllocationParent = dgAllocationParentService.findByKey(activeContract.getDgAllocationParent().getId());
					}

					// populate WSP Object
					if (dgAllocationParent != null && dgAllocationParent.getWsp() != null && dgAllocationParent.getWsp().getId() != null) {
						wsp = wspService.findByKey(dgAllocationParent.getWsp().getId());
					}

					// Populate Company Object
					if (wsp != null && wsp.getCompany() != null && wsp.getCompany().getId() != null) {
						company = companyService.findByKey(wsp.getCompany().getId());
						if (company != null && company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
							company.setResidentialAddress(AddressService.instance().findByKey(company.getResidentialAddress().getId()));
							if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
								rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
							}
						}
					}

					// locate CLO user
					if (rt != null && rt.getClo() != null && rt.getClo().getId() != null) {
						cloUser = usersService.findByKey(rt.getClo().getId());
					}

					// populate Subject for mail
					subject = populateSubjectMessage(subject, activeContract, amount, wsp);

					// populate Message for email
					message = populateMessage(message, activeContract, amount, wsp, company, cloUser, rt);

					// locates users who will recive the notification
					List<Users> recievers = populateEmailRecivers(activeContract);
					if (recievers.size() != 0) {
						for (Users user : recievers) {
							String fullName = "";
							if (user.getTitle() != null && user.getTitle().getId() != null) {
								fullName = user.getTitle().getDescription() + " ";
							}
							fullName += user.getFirstName() + " " + user.getLastName();
							GenericUtility.sendMail(user.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), null);
						}
					} else {
						// maybe add fall back?
					}

				}
			}

			// add to list to be updated
			if (addToUpdate) {
				updateList.add(activeContract);
			}
		}

		if (updateList.size() != 0) {
			dao.updateBatch(updateList, 500);
		}
	}

	public String populateSubjectMessage(String subject, ActiveContracts activeContract, Integer amount, Wsp wsp) throws Exception {
		subject = "REMINDER: #AMOUNT# DAYS LEFT TO ELECTRONICALLY SIGN DG YEAR #GRANT_YEAR# MOA";
		// subject replacements
		Integer dayReminder = 0;
		/*
		 * ------ Create Date: Tue Oct 01 00:00:00 SAST 2019 ------ due date: Tue Nov 12 00:00:00 SAST 2019 ------ fifteenDayReminder: Mon Oct 21 00:00:00 SAST 2019 ------ 15 ----- Days between fifteenDayReminder and due date: 17 ------ tenDayReminder: Mon Oct 28 00:00:00 SAST 2019 ------ 10 ----- Days between tenDayReminder and due date: 12 ------ fiveDayReminder: Mon Nov 04 00:00:00 SAST 2019 ------ 5 ----- Days between fiveDayReminder and due date: 7 ------ threeReminder: Thu Nov 07 00:00:00
		 * SAST 2019 ------ 3 ----- Days between threeReminder and due date: 4 ------ oneDayReminder: Thu Nov 07 00:00:00 SAST 2019 ------ 1 ----- Days between oneDayReminder and due date: 2
		 */
		switch (amount) {
			case 17:
				dayReminder = 15;
				break;
			case 12:
				dayReminder = 10;
				break;
			case 7:
				dayReminder = 5;
				break;
			case 4:
				dayReminder = 3;
				break;
			case 2:
				dayReminder = 1;
				break;
			default:
				dayReminder = 0;
				break;
		}
		subject = subject.replace("#AMOUNT#", dayReminder.toString());
		if (wsp != null && wsp.getFinYearNonNull() != null) {
			subject = subject.replace("#GRANT_YEAR#", wsp.getFinYearNonNull().toString().trim());
		}
		return subject;
	}

	public String populateMessage(String message, ActiveContracts activeContract, Integer amount, Wsp wsp, Company company, Users cloUser, RegionTown rt) throws Exception {
		message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#, </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Kindly be <b><u>reminded</u></b> that the Memorandum of Agreement (MOA) for #COMPANY_NAME# (#ENTITY_ID#) must be signed off electronically by the duly authorised signatory within 30 business days from the date of acceptance."
				+ " Please note that should the PIP and MOA not be signed electronically on the NSDMS within 30 business days from the date of acceptance, the merSETA may withdraw the MOA. </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> For further assistance/support, kindly contact the designated Client Liaison Officer: Client Liaison Officer #CLO_FIRST_NAME# #CLO_LAST_NAME# (Email: #CLO_FIRST_NAME# #CLO_EMAIL#) or the #REGION# Office.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> For ease of reference, we include the procedure below to be followed by the duly authorised signatory. </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> <b>Procedure to electronically sign the MOA</b> </p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "STEP 1: <b>Login on NSDMS</b> using your login details<br/>"
				+ "STEP 2: Go to the <b>Dashboard</b> and go to <b>Outstanding Tasks</b> and look for the task<br/>" + "STEP 3: Click on the arrow under <b>View/Edit</b> to access the task<br/>" + "STEP 4: Go to <b>MOA Sign Off</b> table to download the MOA if you wish.<br/>" + "STEP 5: Go to <b>MOA Sign Off</b> table and under <b>Actions</b>, click on the pen icon. Please note that if you have not received an OTP or it has expired, click the icon to Resend OTP.<br/>"
				+ "STEP 6: <b>Enter the OTP</b> and click on <b>Validate OTP</b><br/>" + "STEP 7: <b>Agree</b> to the terms and conditions of the MOA by <b>clicking the checkbox</b>.<br/>" + "STEP 8: Click on the <b>Sign Off</b> button and confirm/stop your action when prompted.<br/>" + "STEP 9: Click on <b>Complete MOA Sign Off</b>.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For queries/support, please contact your regional office or the merSETA Call Centre at #CALL_CENTRE_NUMBER#</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please note that if you are not duly authorised to sign the MOA, please follow the steps:</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "STEP 1: <b>Login on NSDMS</b> using your login details.<br/>"
				+ "STEP 2: Go to the <b>Dashboard</b> and go to <b>Outstanding Tasks</b> and look for the task.<br/>" + "STEP 3: Click on the arrow under <b>View/Edit</b> to access the task.<br/>" + "STEP 4: Click on the button <b>'Not Duly Authorised For Sign Off'</b>.<br/>" + "STEP 5: Click <b>yes</b> to confirm/accept.</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely, <br/><b>merSETA Projects Implementation</b></p>";

		// replace messages
		message = message.replace("#COMPANY_NAME#", (company == null) ? "" : company.getCompanyName());
		message = message.replace("#ENTITY_ID#", (company == null) ? "" : company.getLevyNumber());
		message = message.replace("#CLO_FIRST_NAME#", (cloUser == null) ? "" : cloUser.getFirstName().trim());
		message = message.replace("#CLO_LAST_NAME#", (cloUser == null) ? "" : cloUser.getLastName().trim());
		message = message.replace("#CLO_EMAIL#", (cloUser == null) ? "" : cloUser.getEmail().trim());
		message = message.replace("#REGION#", (rt != null && rt.getRegion() != null && rt.getRegion().getId() != null) ? rt.getRegion().getDescription().trim() : "");
		message = message.replace("#CALL_CENTRE_NUMBER#", "+27 (0) 861 637 738");
		return message;
	}

	/**
	 * Populates user who will receive the reminder notification
	 * @param activeContract
	 * @return List<Users> the users who will recive the notification
	 */
	public List<Users> populateEmailRecivers(ActiveContracts activeContract) {

		// sets services required
		if (companyUsersService == null) {
			companyUsersService = new CompanyUsersService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (wspService == null) {
			wspService = new WspService();
		}
		if (dgAllocationParentService == null) {
			dgAllocationParentService = new DgAllocationParentService();
		}
		if (sdfCompanyService == null) {
			sdfCompanyService = new SDFCompanyService();
		}
		if (signoffService == null) {
			signoffService = new SignoffService();
		}
		if (usersService == null) {
			usersService = new UsersService();
		}
		if (hostingCompanyEmployeesService == null) {
			hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		}

		List<Users> recivers = new ArrayList<>();
		try {
			DgAllocationParent dgAllocationParent = null;
			Wsp                wsp                = null;
			Company            company            = null;
			RegionTown         rt                 = null;

			// populate DgAllocationParent Object
			if (activeContract.getDgAllocationParent() != null && activeContract.getDgAllocationParent().getId() != null) {
				dgAllocationParent = dgAllocationParentService.findByKey(activeContract.getDgAllocationParent().getId());
			}

			// populate WSP Object
			if (dgAllocationParent != null && dgAllocationParent.getWsp() != null && dgAllocationParent.getWsp().getId() != null) {
				wsp = wspService.findByKey(dgAllocationParent.getWsp().getId());
			}

			// Populate Company Object
			if (wsp != null && wsp.getCompany() != null && wsp.getCompany().getId() != null) {
				company = companyService.findByKey(wsp.getCompany().getId());
				if (company != null && company.getResidentialAddress() != null && company.getResidentialAddress().getId() != null) {
					company.setResidentialAddress(AddressService.instance().findByKey(company.getResidentialAddress().getId()));
					if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
						rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
					}
				}
			}

			if (company != null && company.getId() != null) {
				// Find Primary SDF User
				List<SDFCompany> sdfList = sdfCompanyService.findByCompanyAndSdfType(company, HAJConstants.PRIMARY_ID);
				for (SDFCompany sdfCompany : sdfList) {
					if (determainIfUserCanBeAdded(recivers, sdfCompany.getSdf())) {
						recivers.add(sdfCompany.getSdf());
					}
					break;
				}
				sdfList = null;

				// Find Training Manager Linked to Company
				List<CompanyUsers> cu = companyUsersService.findByCompanyUserPosition(company.getId(), HAJConstants.COMPANY_USER_POSITION_TRAINING_MANAGER_ID);
				for (CompanyUsers companyUsers : cu) {
					if (determainIfUserCanBeAdded(recivers, companyUsers.getUser())) {
						recivers.add(companyUsers.getUser());
					}
				}
				cu = null;
			}

			// Find Sign Off Representative for active contract
			if (activeContract != null && activeContract.getId() != null) {
				List<Signoff> signoffList = signoffService.findByTargetKeyAndClass(activeContract.getId(), activeContract.getClass().getName());
				for (Signoff signoff : signoffList) {
					if (determainIfUserCanBeAdded(recivers, signoff.getUser())) {
						recivers.add(signoff.getUser());
					}
				}
				signoffList = null;
			}

			// Find CLO assigned to company by region town
			if (rt != null && rt.getId() != null && rt.getClo() != null && rt.getClo().getId() != null) {
				Users cloUser = usersService.findByKey(rt.getClo().getId());
				if (cloUser != null && cloUser.getId() != null && determainIfUserCanBeAdded(recivers, cloUser)) {
					recivers.add(cloUser);
				}
				cloUser = null;
			}

			// Find CRM assigned to company by region town
			if (rt != null && rt.getId() != null && rt.getCrm() != null && rt.getCrm().getId() != null) {
				Users crmUser = usersService.findByKey(rt.getCrm().getId());
				if (crmUser != null && crmUser.getId() != null && determainIfUserCanBeAdded(recivers, crmUser)) {
					recivers.add(crmUser);
				}
				crmUser = null;
			}

			// Find Manager: Programmes Implementation Employee by Job Title
			List<Users> employeeUsersManagerPI = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_MANAGER_PROGRAMMES_IMPLEMENTATION_ID);
			for (Users employee : employeeUsersManagerPI) {
				if (determainIfUserCanBeAdded(recivers, employee)) {
					recivers.add(employee);
				}
			}
			employeeUsersManagerPI = null;

			// Find Senior Manager: Client Services Employee by Job Title
			List<Users> employeeUsersSeniorManagerCS = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_CLIENT_SERVICE_ID);
			for (Users employee : employeeUsersSeniorManagerCS) {
				if (determainIfUserCanBeAdded(recivers, employee)) {
					recivers.add(employee);
				}
			}
			employeeUsersSeniorManagerCS = null;

			// Find Senior Manager: Programmes Implementation Employee by Job Title
			List<Users> employeeUsersSeniorManagerPI = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_PROGRAMMES_IMPLEMENTATION_ID);
			for (Users employee : employeeUsersSeniorManagerPI) {
				if (determainIfUserCanBeAdded(recivers, employee)) {
					recivers.add(employee);
				}
			}
			employeeUsersSeniorManagerPI = null;

			dgAllocationParent = null;
			wsp                = null;
			company            = null;
			rt                 = null;
		} catch (Exception e) {
			logger.fatal(e);
		}
		return recivers;
	}

	private boolean determainIfUserCanBeAdded(List<Users> usersList, Users newUser) {
		// prevents duplicate users being added to notification
		boolean addUser = true;
		for (Users users : usersList) {
			if (users.getId().equals(newUser.getId())) {
				addUser = false;
				break;
			}
		}
		return addUser;
	}

	public void testDateAmount() throws Exception {
		/*
		 * Logic: 1 - 1 October 2 - 2 October 3 - 3 October 4 - 4 October 5 - 7 October 6 - 8 October 7 - 9 October 8 - 10 October 9 - 11 October 10 - 14 October 11 - 15 October 12 - 16 October 13 - 17 October 14 - 18 October 15 - 21 October : Reminder 16 - 22 October 17 - 23 October 18 - 24 October 19 - 25 October 20 - 28 October : Reminder 21 - 29 October 22 - 30 October 23 - 31 October 24 - 1 November 25 - 4 November : Reminder 26 - 5 November 27 - 6 November 28 - 7 November : Reminder 29 - 8
		 * November 30 - 11 November : Reminder 31 - 12 November - Termination
		 */
		/*
		 * ------ Create Date: Tue Oct 01 00:00:00 SAST 2019 ------ due date: Tue Nov 12 00:00:00 SAST 2019 ------ fifteenDayReminder: Mon Oct 21 00:00:00 SAST 2019 ------ 15 ----- Days between fifteenDayReminder and due date: 17 ------ tenDayReminder: Mon Oct 28 00:00:00 SAST 2019 ------ 10 ----- Days between tenDayReminder and due date: 12 ------ fiveDayReminder: Mon Nov 04 00:00:00 SAST 2019 ------ 5 ----- Days between fiveDayReminder and due date: 7 ------ threeReminder: Thu Nov 07 00:00:00
		 * SAST 2019 ------ 3 ----- Days between threeReminder and due date: 4 ------ oneDayReminder: Thu Nov 07 00:00:00 SAST 2019 ------ 1 ----- Days between oneDayReminder and due date: 2
		 */
		Date createDate = HAJConstants.sdfDDMMMMYYYY.parse("01 October 2019");
		System.out.println("------ Create Date: " + createDate);
		Date dueDate = GenericUtility.addDaysToDateExcludeWeekends(HAJConstants.sdfDDMMMMYYYY.parse("01 October 2019"), 30);
		System.out.println("------ due date: " + dueDate);

		Date fifteenDayReminder = GenericUtility.getStartOfDay(HAJConstants.sdfDDMMMMYYYY.parse("21 October 2019"));
		System.out.println("------ fifteenDayReminder: " + fifteenDayReminder);
		System.out.println("------ 15 ----- Days between fifteenDayReminder and due date: " + GenericUtility.getDaysBetweenDatesExcludeWeekends(fifteenDayReminder, dueDate));

		Date tenDayReminder = GenericUtility.getStartOfDay(HAJConstants.sdfDDMMMMYYYY.parse("28 October 2019"));
		System.out.println("------ tenDayReminder: " + tenDayReminder);
		System.out.println("------ 10 ----- Days between tenDayReminder and due date: " + GenericUtility.getDaysBetweenDatesExcludeWeekends(tenDayReminder, dueDate));

		Date fiveDayReminder = GenericUtility.getStartOfDay(HAJConstants.sdfDDMMMMYYYY.parse("4 November 2019"));
		System.out.println("------ fiveDayReminder: " + fiveDayReminder);
		System.out.println("------ 5 ----- Days between fiveDayReminder and due date: " + GenericUtility.getDaysBetweenDatesExcludeWeekends(fiveDayReminder, dueDate));

		Date threeReminder = GenericUtility.getStartOfDay(HAJConstants.sdfDDMMMMYYYY.parse("7 November 2019"));
		System.out.println("------ threeReminder: " + threeReminder);
		System.out.println("------ 3 ----- Days between threeReminder and due date: " + GenericUtility.getDaysBetweenDatesExcludeWeekends(threeReminder, dueDate));

		Date oneDayReminder = GenericUtility.getStartOfDay(HAJConstants.sdfDDMMMMYYYY.parse("11 November 2019"));
		System.out.println("------ oneDayReminder: " + threeReminder);
		System.out.println("------ 1 ----- Days between oneDayReminder and due date: " + GenericUtility.getDaysBetweenDatesExcludeWeekends(oneDayReminder, dueDate));

	}

	@SuppressWarnings("unchecked")
	public List<ActiveContracts> cloCrmActiveContractsRegionReportByMoaTypeAndGrantYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long userID, MoaTypeEnum moaType, Integer finYear) throws Exception {
		String hql = "select o from ActiveContracts o where " + " o.moaType = :moaType and " + " o.dgAllocationParent.wsp.finYear = :finYear and " + " o.dgAllocationParent.wsp.company.nonSetaCompany = false and " + " o.dgAllocationParent.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		filters.put("userID", userID);
		filters.put("moaType", moaType);
		filters.put("finYear", finYear);
		return populateActiveContractsRegionReportInformation((List<ActiveContracts>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countCloCrmActiveContractsRegionReportByMoaTypeAndGrantYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContracts o where " + " o.moaType = :moaType and " + " o.dgAllocationParent.wsp.finYear = :finYear and " + " o.dgAllocationParent.wsp.company.nonSetaCompany = false and " + " o.dgAllocationParent.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.clo.users.id = :userID or x.crm.users.id = :userID)";
		return dao.countWhere(filters, hql);
	}

	public int countByCloCrmActiveContractsByMoaTypeAndGrantYear(Long userID, MoaTypeEnum moaType, Integer finYear) throws Exception {
		return dao.countByCloCrmActiveContractsByMoaTypeAndGrantYear(userID, moaType, finYear);
	}

	@SuppressWarnings("unchecked")
	public List<ActiveContracts> activeContractsReportByMoaTypeAndGrantYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, MoaTypeEnum moaType, Integer finYear) throws Exception {
		String hql = "select o from ActiveContracts o where " + " o.moaType = :moaType and " + " o.dgAllocationParent.wsp.finYear = :finYear and " + " o.dgAllocationParent.wsp.company.nonSetaCompany = false";
		filters.put("moaType", moaType);
		filters.put("finYear", finYear);
		return populateActiveContractsRegionReportInformation((List<ActiveContracts>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countActiveContractsReportByMoaTypeAndGrantYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from ActiveContracts o where " + " o.moaType = :moaType and " + " o.dgAllocationParent.wsp.finYear = :finYear and " + " o.dgAllocationParent.wsp.company.nonSetaCompany = false";
		return dao.countWhere(filters, hql);
	}

	public int countActiveContractsReportByMoaTypeAndGrantYear(MoaTypeEnum moaType, Integer finYear) throws Exception {
		return dao.countActiveContractsReportByMoaTypeAndGrantYear(moaType, finYear);
	}

	private List<ActiveContracts> populateActiveContractsRegionReportInformation(List<ActiveContracts> activeContractsList) throws Exception {
		for (ActiveContracts activeContract : activeContractsList) {
			populateActiveContractsRegionReportInformation(activeContract);
		}
		return activeContractsList;
	}

	private ActiveContracts populateActiveContractsRegionReportInformation(ActiveContracts activeContract) throws Exception {

		// populate current company categorization
		if (companyService == null) {
			companyService = new CompanyService();
		}

		if (activeContract != null && activeContract.getDgAllocationParent() != null) {
			if (activeContract.getDgAllocationParent().getWsp() != null && activeContract.getDgAllocationParent().getWsp().getCompany() != null) {
				if (activeContract.getDgAllocationParent().getWsp().getCompany().getId() != null) {
					companyService.resolveCompanyDataCloCrmRegionReport(activeContract.getDgAllocationParent().getWsp().getCompany());
				}
			}
		}

		return activeContract;
	}

	public List<MoaStatusReportBean> populateMoaStatusReportByGrantYear(Integer grantYear) throws Exception {
		return dao.populateMoaStatusReportByGrantYear(grantYear);
	}

	public List<PipTaskTrackerReportBean> populatePipTaskTrackerReportByGrantYear(Integer grantYear) throws Exception {
		return dao.populatePipTaskTrackerReportByGrantYear(grantYear);
	}

	public void downloadPipTaskTrackerReport(Integer grantYear) throws Exception {
		Workbook              wb      = new SXSSFWorkbook(100);
		Sheet                 sh      = wb.createSheet();
		Map<String, Object[]> data    = new TreeMap<String, Object[]>();
		Integer               counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "Entity ID", "Company Name", "Chamber", "Region", "Reference Number", "MOA Type", "MOA Value", "Approved Date", "MOA Acceptance Date", "SDF Name and Surname", "SDF Sign Date", "CLO Name and Surname", "Clo Sign Date", "CRM Name and Surname", "CRM Sign Date" });
		counter++;
		// data population
		populateDataForPipTaskTrackerReport(data, counter, grantYear);
		counter = null;
		Set<String> keyset = data.keySet();
		int         rownum = 0;
		for (String key : keyset) {
			Row      row     = sh.createRow(rownum++);
			Object[] objArr  = data.get(key);
			int      cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String) cell.setCellValue((String) obj);
				else if (obj instanceof Integer) cell.setCellValue((Integer) obj);
				else if (obj instanceof Double) cell.setCellValue((Double) obj);
				;
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes    = bos.toByteArray();
			String fileName = "PIP Task Tracker Report.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void populateDataForPipTaskTrackerReport(Map<String, Object[]> data, Integer counter, Integer grantYear) throws Exception {
		int                            counterForPopulation = counter;
		List<PipTaskTrackerReportBean> resultsList          = populatePipTaskTrackerReportByGrantYear(grantYear);
		// populate data found into report
		for (PipTaskTrackerReportBean entry : resultsList) {
			populateDataPipTaskTrackerReport(data, entry, counterForPopulation);
			counterForPopulation++;
		}
		resultsList.clear();
		counter = counterForPopulation;
	}

	private void populateDataPipTaskTrackerReport(Map<String, Object[]> data, PipTaskTrackerReportBean entry, Integer counter) throws Exception {

		String approvedDate = "N/A";
		if (entry.getApprovalDate() != null) {
			approvedDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getApprovalDate());
		}

		String acceptanceDate = "N/A";
		if (entry.getAcceptanceDate() != null) {
			acceptanceDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getAcceptanceDate());
		}

		String sdfSignDate = "N/A";
		if (entry.getSdfSignDate() != null) {
			sdfSignDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getSdfSignDate());
		}

		String cloSignDate = "N/A";
		if (entry.getCloSignDate() != null) {
			cloSignDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getCloSignDate());
		}

		String crmSignDate = "N/A";
		if (entry.getCrmSignDate() != null) {
			crmSignDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getCrmSignDate());
		}
		// new Object[] {"Entity ID", "Company Name", "Chamber", "Region", "Reference Number", "MOA Type", "MOA Value", "Approved Date", "MOA Acceptance Date", "SDF Name and Surname", "SDF Sign Date", "CLO Name and Surname", "Clo Sign Date", "CRM Name and Surname", "CRM Sign Date"}
		data.put(counter.toString(), new Object[] { entry.getEntityId(), entry.getCompanyName(), entry.getChamber(), entry.getRegion(), entry.getReferanceNumber(), entry.getMoaType(), entry.getContractValue(), approvedDate, acceptanceDate, entry.getSdfFullName(), sdfSignDate, entry.getCloFullName(), cloSignDate, entry.getCrmFullName(), crmSignDate });

	}

	public void generateContract(Wsp wsp) {
		try {
			ActiveContracts entity = new ActiveContracts();
			generateContract(wsp, entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generateContract(Wsp wsp, ActiveContracts entity) {
		try {
			entity.setStatus(ApprovalEnum.Approved);
			entity.setContractvalue(wsp.getApprovedAmount());
			entity.setWsp(wsp);
			entity.setContractStatusEnum(ContractStatusEnum.InProgress);
			entity.setMoaType(MoaTypeEnum.SpecialProject);
			entity.setEletronicSignoff(false);
			entity.setExtensionTerminationWorkflowActive(false);
			entity.setSubmitted(false);
			entity.setCompany(wsp.getCompany());
			// int grantYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
			// if (dgAllocationParent != null && dgAllocationParent.getWsp() != null && dgAllocationParent.getWsp().getFinYear() != null) {
			// grantYear = dgAllocationParent.getWsp().getFinYear();
			// }
			// int previousYear = grantYear - 1;
			// entity.setProjectedRegistrationDateStart(HAJConstants.sdfDDMMYYYY2.parse("01/04/" + previousYear));
			// entity.setProjectedRegistrationDateEnd(HAJConstants.sdfDDMMYYYY2.parse("31/03/" + grantYear));
			// 30 day business rule for expiry
			if (entity.getRefno() != null && entity.getRefno().trim().isEmpty()) {
				entity.setRefno(null);
			}
			entity.setDeadlineDate(GenericUtility.getStartOfDay(GenericUtility.addDaysToDateExcludeWeekends(getSynchronizedDate(), 30)));
			dao.create(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}