package haj.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.microsoft.schemas.dynamics.gp._2006._01.ExceptionInformation;

import haj.com.constants.HAJConstants;
import haj.com.dao.CompanyLearnersTerminationDAO;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersLostTime;
import haj.com.entity.CompanyLearnersTermination;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.ReviewCommitteeMeetingAgenda;
import haj.com.entity.SDFCompany;
import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.CreatedByEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.SignoffByEnum;
import haj.com.entity.enums.TerminationTypeEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;

public class CompanyLearnersTerminationService extends AbstractService {
	/** The dao. */
	private CompanyLearnersTerminationDAO dao = new CompanyLearnersTerminationDAO();
	private ConfigDocService configDocService = new ConfigDocService();
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private HostingCompanyEmployeesService hostingCompanyEmployeesService=new HostingCompanyEmployeesService();
	private RolesService rolesService=new RolesService();
	private UsersService usersService = new UsersService();
	private CompanyService companyService = new CompanyService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/** Instance of service level */
	private static CompanyLearnersTerminationService companyLearnersTerminationService;
	public static synchronized CompanyLearnersTerminationService instance() {
		if (companyLearnersTerminationService == null) {
			companyLearnersTerminationService = new CompanyLearnersTerminationService();
		}
		return companyLearnersTerminationService;
	}
	
	/**
	 * Populates additional information against CompanyLearnersTermination
	 * refer to method: populateAdditionalInformation
	 * @param companyLearnersTerminationList
	 * @return companyLearnersTerminationList
	 * @throws Exception
	 */
	public List<CompanyLearnersTermination> populateAdditionalInformationList(List<CompanyLearnersTermination> companyLearnersTerminationList) throws Exception{
		for (CompanyLearnersTermination companyLearnersTermination : companyLearnersTerminationList) {
			populateAdditionalInformation(companyLearnersTermination);
		}
		return companyLearnersTerminationList;
	}
	
	/**
	 * Populates additional information against CompanyLearnersTermination
	 * @param companyLearnersTermination
	 * @return companyLearnersTermination
	 * @throws Exception
	 */
	private CompanyLearnersTermination populateAdditionalInformation(CompanyLearnersTermination companyLearnersTermination) throws Exception{
		if (companyLearnersTermination.getCompanyLearners() != null && companyLearnersTermination.getCompanyLearners().getId() != null) {
			companyLearnersTermination.setCompanyLearners(companyLearnersService.findByKey(companyLearnersTermination.getCompanyLearners().getId())) ;
		}
		return companyLearnersTermination;
	}

	/**
	 * Get all CompanyLearnersTermination
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersTermination
	 * @return a list of {@link haj.com.entity.CompanyLearnersTermination}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersTermination> allCompanyLearnersTermination() throws Exception {
		return populateAdditionalInformationList(dao.allCompanyLearnersTermination());
	}

	/**
	 * Create or update CompanyLearnersTermination.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTermination
	 */
	public void create(CompanyLearnersTermination entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update CompanyLearnersTermination.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTermination
	 */
	public void update(CompanyLearnersTermination entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyLearnersTermination.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTermination
	 */
	public void delete(CompanyLearnersTermination entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyLearnersTermination}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTermination
	 */
	public CompanyLearnersTermination findByKey(long id) throws Exception {
		return populateAdditionalInformation(dao.findByKey(id));
	}

	public void prepareNewRegistration(CompanyLearnersTermination entity, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcessEnum));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), configDocProcessEnum, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(configDocProcessEnum, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void prepareDocumentByProcess(ConfigDocProcessEnum configDocProcess, CompanyLearnersTermination entity, ProcessRoles processRoles) throws Exception {
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
	
	public void prepareNewRegistration(CompanyLearnersTermination entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		} 		
	}
	
	public void prepareDocumentByProcess(ConfigDocProcessEnum configDocProcess, CompanyLearnersTermination entity, ProcessRoles processRoles,Boolean removeAgreement) throws Exception {
		if (entity.getId() != null) {
			
			if (processRoles.getCompanyUserType() == null) entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));

			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);

			if (l != null && l.size() > 0) {
				
				if(removeAgreement)
				{
					ConfigDoc toRemove=null;
					for(ConfigDoc cfd:l)
					{
						if(cfd.getName().contains("Learner agreement"))
						{
							toRemove=cfd;
							break;
						}
					}
					if(toRemove !=null){
						l.remove(toRemove);
					}
				}
				
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				if(removeAgreement){
					ConfigDoc toRemove=null;
					for(ConfigDoc cfd:l){
						if(cfd.getName().contains("Learner agreement")){
							toRemove=cfd;
							break;
						}
					}
					if(toRemove !=null){
						l.remove(toRemove);
					}
				}
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	/**
	 * Find CompanyLearnersTermination by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyLearnersTermination}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersTermination
	 */
	public List<CompanyLearnersTermination> findByName(String desc) throws Exception {
		return populateAdditionalInformationList(dao.findByName(desc));
	}

	/**
	 * Lazy load CompanyLearnersTermination
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyLearnersTermination}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersTermination> allCompanyLearnersTermination(int first, int pageSize) throws Exception {
		return populateAdditionalInformationList(dao.allCompanyLearnersTermination(Long.valueOf(first), pageSize));
	}

	/**
	 * Get count of CompanyLearnersTermination for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the CompanyLearnersTermination
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyLearnersTermination.class);
	}

	/**
	 * Lazy load CompanyLearnersTermination with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            CompanyLearnersTermination class
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
	 * @return a list of {@link haj.com.entity.CompanyLearnersTermination}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTermination> allCompanyLearnersTermination(Class<CompanyLearnersTermination> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList((List<CompanyLearnersTermination>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));

	}
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersTermination> allPendingETQAApprovalCompanyLearnersTermination(Class<CompanyLearnersTermination> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveDocumets(populateAdditionalInformationList((List<CompanyLearnersTermination>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters)));

	}
	public List<CompanyLearnersTermination> resolveDocumets(List<CompanyLearnersTermination> list)
	{
		list.forEach(entity->{
			try {
				prepareWorkflowDocs(entity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		return list;
	}
	
	public void prepareWorkflowDocs(CompanyLearnersTermination companyLearnersTermination) throws Exception {
		companyLearnersTermination.setDocs(DocService.instance().searchByTargetKeyAndClass(companyLearnersTermination.getClass().getName(), companyLearnersTermination.getId(), ConfigDocProcessEnum.OneLearnerTermination));
		companyLearnersTermination.getCompanyLearners().setDocs(DocService.instance().searchByTargetKeyAndClass(companyLearnersTermination.getCompanyLearners().getClass().getName(), companyLearnersTermination.getCompanyLearners().getId(), ConfigDocProcessEnum.Learner));
	}

	/**
	 * Get count of CompanyLearnersTermination for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            CompanyLearnersTermination class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyLearnersTermination entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyLearnersTermination> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void completeCompanyLearnersInitial(CompanyLearnersTermination companyLearners, Users user, Tasks tasks, boolean sendToRegion) throws Exception {
		String error = "";
		if (companyLearners.getDocs() != null) {
			for (Doc doc : companyLearners.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName() + " for " + companyLearners.getCompanyLearners().getUser().getFirstName() + " " + companyLearners.getCompanyLearners().getUser().getLastName();
					break;
				}
			}
		}
		if (error.length() > 0) throw new ValidationException(error);
		for (Doc doc : companyLearners.getDocs()) {
			doc.setTargetKey(companyLearners.getId());
			doc.setTargetClass(companyLearners.getClass().getName());
			if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
		}
		
		if(tasks.getWorkflowProcess()==ConfigDocProcessEnum.OneLearnerTermination)
		{
			sendOnesidedTerminationTask(companyLearners, user, tasks);
		}
		else
		{
			 sendTaskToNext(companyLearners.getTerminationTypeEnum(), companyLearners.getCompanyLearners().getCompany(), user, tasks, true);
		}
		/*if (sendToRegion) sendTaskToNext(companyLearners.getTerminationTypeEnum(), companyLearners.getCompanyLearners().getCompany(), user, tasks, true);
		else TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);*/
		if(companyLearners.getStatus()==ApprovalEnum.Rejected)
		{
			companyLearners.setStatus(ApprovalEnum.WaitingForManager);
			update(companyLearners);
		}
	}
	
	public void completeCompanyELearnersInitial(CompanyLearnersTermination companyLearners, Users user, Tasks tasks, boolean sendToRegion) throws Exception {
		String error = "";
		if (companyLearners.getDocs() != null) {
			for (Doc doc : companyLearners.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName() + " for " + companyLearners.getCompanyLearners().getUser().getFirstName() + " " + companyLearners.getCompanyLearners().getUser().getLastName();
					break;
				}
			}
		}
		if (error.length() > 0) throw new ValidationException(error);
		for (Doc doc : companyLearners.getDocs()) {
			doc.setTargetKey(companyLearners.getId());
			doc.setTargetClass(companyLearners.getClass().getName());
			if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
		}
		if(tasks.getFirstInProcess()) {
			companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
			update(companyLearners);
		}else {
			companyLearners.setSignoffByEnum(SignoffByEnum.Completed);
			update(companyLearners);
		}
		if(tasks.getFirstInProcess() && companyLearners.getTerminationTypeEnum() != TerminationTypeEnum.OneSidedTermination) {
			List<Users> userList = new ArrayList<>();
			CompanyLearners cl = CompanyLearnersService.instance().findByKey(companyLearners.getCompanyLearners().getId());
			userList.add(cl.getUser());
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}else {
			if(tasks.getWorkflowProcess()==ConfigDocProcessEnum.OneLearnerTermination) {
				sendOnesidedTerminationTask(companyLearners, user, tasks);
			} else {
				 sendTaskToNext(companyLearners.getTerminationTypeEnum(), companyLearners.getCompanyLearners().getCompany(), user, tasks, true);
			}
			
			if(companyLearners.getStatus()==ApprovalEnum.Rejected) {
				companyLearners.setStatus(ApprovalEnum.WaitingForManager);
				update(companyLearners);
			}
		}
	}

	public void completeCompanyLearnersRegion(CompanyLearnersTermination companyLearners, Users user, Tasks tasks) throws Exception {
		if (tasks.getProcessRole().getRolePermission() == UserPermissionEnum.EditUpload) {
			update(companyLearners);
			String error = "";
			if (companyLearners.getDocs() != null) {
				for (Doc doc : companyLearners.getDocs()) {
					if (doc.getId() != null) {
						DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
						if (docByte != null) doc.setData(docByte.getData());
					}
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						error += "Upload " + doc.getConfigDoc().getName() + " for " + companyLearners.getCompanyLearners().getUser().getFirstName() + " " + companyLearners.getCompanyLearners().getUser().getLastName();
					}
				}
			}
			if (error.length() > 0) throw new ValidationException(error);
			if(companyLearners.getTerminationTypeEnum() ==TerminationTypeEnum.MutualSidedTermination)
			{
				sendTaskToNext(companyLearners.getTerminationTypeEnum(), companyLearners.getCompanyLearners().getCompany(), user, tasks, false);
			}
			else
			{
				sendOnesidedTerminationTask(companyLearners, user, tasks);
			}
			
		} else {
			if(companyLearners.getTerminationTypeEnum() ==TerminationTypeEnum.MutualSidedTermination)
			{
				sendTaskToNext(companyLearners.getTerminationTypeEnum(), companyLearners.getCompanyLearners().getCompany(), user, tasks, false);
			}
			else
			{
				sendOnesidedTerminationTask(companyLearners, user, tasks);
			}
			
		}
	}

	public void completeCompanyLearners(CompanyLearnersTermination companyLearners, Users user, Tasks tasks) throws Exception {
		completeCompanyLearnersRegion(companyLearners, user, tasks);
	}
	
	public void completeCompanyELearners(CompanyLearnersTermination companyLearners, Users user, Tasks tasks) throws Exception {
		if(tasks.getFirstInProcess() && companyLearners.getTerminationTypeEnum()  != TerminationTypeEnum.OneSidedTermination) {
			String error = "";
			if (companyLearners.getDocs() != null) {
				for (Doc doc : companyLearners.getDocs()) {
					if (doc.getId() != null) {
						DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
						if (docByte != null) doc.setData(docByte.getData());
					}
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						error += "Upload " + doc.getConfigDoc().getName() + " for " + companyLearners.getCompanyLearners().getUser().getFirstName() + " " + companyLearners.getCompanyLearners().getUser().getLastName();
						break;
					}
				}
			}
			if (error.length() > 0) throw new ValidationException(error);
			for (Doc doc : companyLearners.getDocs()) {
				doc.setTargetKey(companyLearners.getId());
				doc.setTargetClass(companyLearners.getClass().getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
			}
			if(tasks.getFirstInProcess()) {
				companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
				update(companyLearners);
			}else {
				companyLearners.setSignoffByEnum(SignoffByEnum.Completed);
				update(companyLearners);
			}
			List<Users> userList = new ArrayList<>();
			CompanyLearners cl = CompanyLearnersService.instance().findByKey(companyLearners.getCompanyLearners().getId());
			userList.add(cl.getUser());
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}else {
			if(tasks.getFirstInProcess()) {
				companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
				update(companyLearners);
			}else {
				companyLearners.setSignoffByEnum(SignoffByEnum.Completed);
				update(companyLearners);
			}
			completeCompanyLearnersRegion(companyLearners, user, tasks);
		}
	}
	
	public void completeOneSidedTerminationCompanyELearners(CompanyLearnersTermination companyLearners, Users user, Tasks tasks) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(companyLearners.getCompanyLearners().getCompany().getResidentialAddress().getTown());
		List<Users> userList = new ArrayList<>();
		if(tasks.getFirstInProcess() ) {	
			String error = "";
			if (companyLearners.getDocs() != null) {
				for (Doc doc : companyLearners.getDocs()) {
					if (doc.getId() != null) {
						DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
						if (docByte != null) doc.setData(docByte.getData());
					}
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
						error += "Upload " + doc.getConfigDoc().getName() + " for " + companyLearners.getCompanyLearners().getUser().getFirstName() + " " + companyLearners.getCompanyLearners().getUser().getLastName();
						break;
					}
				}
			}
			if (error.length() > 0) throw new ValidationException(error);
			for (Doc doc : companyLearners.getDocs()) {
				doc.setTargetKey(companyLearners.getId());
				doc.setTargetClass(companyLearners.getClass().getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
			}
		
			companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
			update(companyLearners);
			userList.add(rt.getClo().getUsers());
		
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}else {			
			companyLearners.setSignoffByEnum(SignoffByEnum.Completed);
			update(companyLearners);
			userList.add(rt.getClo().getUsers());			
			sendOnesidedTerminationTask(companyLearners, user, tasks);
		}		
	}
	
	public void completeCompanyLearnersNotREGION(CompanyLearnersTermination compnayLearnerTermination, Users user, Tasks tasks) throws Exception {
		if(compnayLearnerTermination.getStatus()==ApprovalEnum.Rejected)
		{
			compnayLearnerTermination.setStatus(ApprovalEnum.WaitingForManager);
			update(compnayLearnerTermination);
		}
		sendOnesidedTerminationTask(compnayLearnerTermination, user, tasks);
	}
	
	public void sendOnesidedTerminationTask(CompanyLearnersTermination compnayLearnerTermination, Users user, Tasks tasks) throws Exception {
		
		RegionTown rt = RegionTownService.instance().findByTown(compnayLearnerTermination.getCompanyLearners().getCompany().getResidentialAddress().getTown());
		List<Users> userList = new ArrayList<>();
		if(compnayLearnerTermination.getStatus() ==ApprovalEnum.PendingInverstigation){
			tasks=TasksService.instance().findByKey(tasks.getId());
		}
		if(tasks.getProcessRole().getRoleOrder()==1)//CLO
		{
			userList.add(rt.getClo().getUsers());
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}
		else if(tasks.getProcessRole().getRoleOrder()==2)//CLO
		{
			if(compnayLearnerTermination.getInvestigateDate()==null){
				throw new Exception("Please provide Date Of Investigation");
			}
			/*userList.add(rt.getClo().getUsers());
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);*/
			if(compnayLearnerTermination.getStatus() !=ApprovalEnum.PendingInverstigation){
				new CompanyLearnersService().sendLearnerTerminationInvestigateEmail(compnayLearnerTermination.getCreateUser(), HAJConstants.sdfDDMMYYYY2.format(compnayLearnerTermination.getInvestigateDate()),HAJConstants.sdf2.format(compnayLearnerTermination.getInvestigateDate()),compnayLearnerTermination.getCompanyLearners().getUser(),user );
				compnayLearnerTermination.setTask(tasks);
				compnayLearnerTermination.setInvestigatorUser(user);
				compnayLearnerTermination.setStatus(ApprovalEnum.PendingInverstigation);
				TasksService.instance().completeTask(tasks);
			}
			else{
				compnayLearnerTermination.setStatus(ApprovalEnum.PendingApproval);
				userList.add(rt.getClo().getUsers());
				TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
			}
		}
		else if(tasks.getProcessRole().getRoleOrder()==3)//CRM
		{
			userList.add(rt.getCrm().getUsers());
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, userList, false);
		}
		else//CLPU OR Manager: Quality Assurance
		{
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}
		update(compnayLearnerTermination);
		
	}

	private void sendTaskToNext(TerminationTypeEnum terminationTypeEnum, Company company, Users user, Tasks tasks,boolean sentToRegioinAdmin) throws Exception {
		
		List<Users> signoffs = new ArrayList<>();
		if(sentToRegioinAdmin) {
			if (tasks.getProcessRole() != null && tasks.getProcessRole().getNextTaskRole() != null) {
				signoffs = findRegionUserByRoleAndCompany(company, tasks.getProcessRole().getNextTaskRole());
			} else {
				signoffs = findRegionClinetServiceAdministrator(company);
			}
		} else {
			if (tasks.getProcessRole() != null && tasks.getProcessRole().getNextTaskRole() != null) {
				signoffs= findRegionUserByRoleAndCompany(company, tasks.getProcessRole().getNextTaskRole());
			} else {
				signoffs= findRegionClientServiceCoordinator(company); // client service admin
			}
			
		}
		if(sentToRegioinAdmin && (signoffs ==null || signoffs.size()<1))
		{
			throw new Exception("No Region Administrator Found. Assign Region Administrator to this town: "+company.getResidentialAddress().getTown().getDescription());
		}
		else if(signoffs ==null || signoffs.size()<1)
		{
			throw new Exception("No Region Coordinator Found.  Assign Region Coordinator  to this town: "+company.getResidentialAddress().getTown().getDescription());
		}		
		signoffs=super.removeDuplicatesFromList(signoffs);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, signoffs, false);
	}

	public void approveCompanyLearners(CompanyLearnersTermination compnayLearnerTermination, Users user, Tasks tasks) throws Exception {
		compnayLearnerTermination.setStatus(ApprovalEnum.WaitingForManager);
		dao.update(compnayLearnerTermination);
		if (compnayLearnerTermination.getTerminationTypeEnum() ==TerminationTypeEnum.MutualSidedTermination)
		{
			 sendTaskToNext(compnayLearnerTermination.getTerminationTypeEnum(),compnayLearnerTermination.getCompanyLearners().getCompany(), user, tasks, false); 
		}
		else if(compnayLearnerTermination.getTerminationTypeEnum() ==TerminationTypeEnum.OneSidedTermination ||
        compnayLearnerTermination.getTerminationTypeEnum() ==TerminationTypeEnum.DeceasedLearnerTermination)
		{
			 sendOnesidedTerminationTask(compnayLearnerTermination, user, tasks);
		}
	}
	
	public List<Users> findRegionUserByRoleAndCompany(Company company, Roles role) throws Exception {
		List<Users> list = new ArrayList<>();
		Roles roles=rolesService.findByKey(role.getId());
		list=hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}
	
	public List<Users> findRegionClinetServiceAdministrator(Company company) throws Exception {
		List<Users> list = new ArrayList<>();
		Roles roles=rolesService.findByKey(HAJConstants.CLIENT_SERVICE_ADMIN_ROLE_ID);
		list=hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}
	
	public List<Users> findRegionAdministrator(Company company) throws Exception {
		List<Users> list = new ArrayList<>();
		Roles roles=rolesService.findByKey(HAJConstants.ADMIN_ROLE_ID);
		list=hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}
	
	public List<Users> findRegionClientServiceCoordinator(Company company) throws Exception{
		List<Users> list = new ArrayList<>();
		Roles roles=rolesService.findByKey(HAJConstants.CLIENT_SERVICE_COORDINATOR_ROLE_ID);
		list=hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}
	
	public List<Users> findRegionCoordinator(Company company) throws Exception{
		List<Users> list = new ArrayList<>();
		Roles roles=rolesService.findByKey(HAJConstants.COORDINATOR_ROLE_ID);
		list=hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}

	public void rejectCompanyLearners(CompanyLearnersTermination companyLearners, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), companyLearners.getClass().getName(), selectedRejectReason, user,tasks.getWorkflowProcess());
		for(RejectReasonMultipleSelection rrm:rrmList) {
			if(rrm !=null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}			
		}
		
		if(companyLearners.getTerminationTypeEnum()==TerminationTypeEnum.MutualSidedTermination) {
			if (!tasks.getFirstInProcess() && !tasks.getProcessRole().getRoleOrder().equals(2)) {
				List<Users> toUserList;
				if (tasks.getProcessRole() != null && tasks.getProcessRole().getNextTaskRole() != null) {
					toUserList = findRegionUserByRoleAndCompany(companyLearners.getCompanyLearners().getCompany(), tasks.getProcessRole().getNextTaskRole());
				} else {
					toUserList = findRegionClinetServiceAdministrator(companyLearners.getCompanyLearners().getCompany());
				}
				if(toUserList==null || toUserList.size()<1)
				{
					throw new Exception("Unable to locate Client Services Administrator, please contact support");
				}
				TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, toUserList);
				TasksService.instance().completeTask(tasks);
			} else {
				List<Users> usersList=new ArrayList<>();
				usersList.add(companyLearners.getCreateUser());
				String desc="The learner learner termination application has not been approved. Please review.";
				TasksService.instance().findFirstInProcessAndCreateRejectTask(desc, user,companyLearners.getId(), companyLearners.getClass().getName(), true,  ConfigDocProcessEnum.MutualLearnerTermination,  MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), usersList);
				TasksService.instance().completeTask(tasks);
			}
			/*if(tasks.getProcessRole().getRolePermission()==UserPermissionEnum.FinalApproval){
				new CompanyLearnersService().sendLearnerTerminationRejection(companyLearners.getCreateUser(), selectedRejectReason, companyLearners);
			}*/
		}else{
			 rejectCompanyLearnersRegion(companyLearners, user,tasks);
		}
		
		companyLearners.setStatus(ApprovalEnum.Rejected);
		update(companyLearners);
	}
	
	public void rejectECompanyLearnersTermination(CompanyLearnersTermination companyLearners, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), companyLearners.getClass().getName(), selectedRejectReason, user,tasks.getWorkflowProcess());
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		if(companyLearners.getCreatedByEnum() == CreatedByEnum.merSETA) {
			companyLearners.setSignoffByEnum(SignoffByEnum.merSETA);
		}else if(companyLearners.getCreatedByEnum() == CreatedByEnum.sdp) {
			companyLearners.setSignoffByEnum(SignoffByEnum.sdp);
		}else if(companyLearners.getCreatedByEnum() == CreatedByEnum.sdf) {
			companyLearners.setSignoffByEnum(SignoffByEnum.sdf);
		}
		companyLearners.setStatus(ApprovalEnum.Rejected);
		update(companyLearners);
		
		List<Users> usersList=new ArrayList<>();
		usersList.add(companyLearners.getCreateUser());
		String desc="The E learner termination application has not been approved. Please review.";
		TasksService.instance().findFirstInProcessAndCreateRejectTask(desc, user,companyLearners.getId(), companyLearners.getClass().getName(), true,  tasks.getWorkflowProcess(),  MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), usersList);
		TasksService.instance().completeTask(tasks);
		if(tasks.getWorkflowProcess() == ConfigDocProcessEnum.ELearnerMutualTermination) {
			updateSignoffs(companyLearners);
		}
	}
	
	public void updateSignoffs(CompanyLearnersTermination companyLearners) throws Exception {
		List<Signoff>signOffs = SignoffService.instance().findByTargetKeyAndClass(companyLearners.getId(), companyLearners.getClass().getName());
		for(Signoff s:signOffs) {
			s.setAccept(false);
			SignoffService.instance().update(s);
		}
	}
	
	public void finalRejectCompanyLearners(CompanyLearnersTermination companyLearnerTermination, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearnerTermination.getId(), companyLearnerTermination.getClass().getName(), selectedRejectReason, user,tasks.getWorkflowProcess());
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null){
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		
		new CompanyLearnersService().sendLearnerTerminationRejection(companyLearnerTermination.getCreateUser(), selectedRejectReason, companyLearnerTermination);
		new CompanyLearnersService().sendLearnerTerminationRejection(companyLearnerTermination.getCompanyLearners().getUser(), selectedRejectReason, companyLearnerTermination);
		if(companyLearnerTermination !=null && companyLearnerTermination.getCompanyLearners() !=null && companyLearnerTermination.getCompanyLearners().getEmployer() !=null){
			CompanyService companyService=new CompanyService();
			Company employer=companyService.findByKey(companyLearnerTermination.getCompanyLearners().getEmployer().getId());
			SDFCompany sdf=SDFCompanyService.instance().findPrimarySDF(employer);
			if(sdf !=null){
				Users empUser=sdf.getSdf();
				new CompanyLearnersService().sendLearnerTerminationRejection(empUser, selectedRejectReason,companyLearnerTermination);
			}
		}
		
		
		companyLearnerTermination.setStatus(ApprovalEnum.Rejected);
		CompanyLearners cl=companyLearnerTermination.getCompanyLearners();
		cl.setLearnerStatus(LearnerStatusEnum.Registered);
		CompanyLearnersService.instance().update(cl);
		TasksService.instance().completeTask(tasks);
		update(companyLearnerTermination);
	}

	public void rejectCompanyLearnersRegion(CompanyLearnersTermination companyLearners, Users user, Tasks tasks) throws Exception {	
		List<Users> userList = new ArrayList<>();
		if(tasks.getProcessRole().getRoleOrder()==2) {
			userList.add(companyLearners.getCreateUser());
		} else {
			RegionTown rt = RegionTownService.instance().findByTown(companyLearners.getCompanyLearners().getCompany().getResidentialAddress().getTown());
			userList.add(rt.getClo().getUsers());
		}
		companyLearners.setSignoffByEnum(SignoffByEnum.Completed);
		update(companyLearners);
		TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, userList);
	}

	public void finalApproveCompanyLearners(CompanyLearnersTermination companyLearners, Users user, Tasks tasks) throws Exception {
		
		if(companyLearners.getTerminationTypeEnum()==TerminationTypeEnum.OneSidedTermination || 
		companyLearners.getTerminationTypeEnum()==TerminationTypeEnum.DeceasedLearnerTermination)
		{

			assignMeetingAgender(companyLearners,user);
			companyLearners.setStatus(ApprovalEnum.PendingCommitteeApproval);
			dao.update(companyLearners);
			TasksService.instance().completeTask(tasks);
		}
		else
		{
			CompanyLearners learners = companyLearners.getCompanyLearners();
			learners.setStatus(ApprovalEnum.Approved);
			learners.setLearnerStatus(LearnerStatusEnum.Terminated);
			learners.setDateLearnerTerminated(getSynchronizedDate());
			companyLearners.setStatus(ApprovalEnum.Approved);
			companyLearners.setApprovalDate(getSynchronizedDate());
			dao.update(companyLearners);
			dao.update(learners);
			TasksService.instance().completeTask(tasks);
			new CompanyLearnersService().sendLPMTP003Email(companyLearners);
		}
	}
	
	public void assignMeetingAgender(CompanyLearnersTermination companyLearnersTermination,Users user) throws Exception
	{
		ReviewCommitteeMeetingAgendaService reviewCommitteeMeetingAgendaSevice=new ReviewCommitteeMeetingAgendaService();
		ScheduledEventService scheduledEventService=new ScheduledEventService();
		ReviewCommitteeMeetingUsersService reviewCommitteeMeetingUsersService=new ReviewCommitteeMeetingUsersService();
		if(companyLearnersTermination.getReviewCommitteeMeeting() !=null)
		{
			Long meetingAgendaID=7L;//Learner Termination
			ReviewCommitteeMeetingAgenda meetingAgender=reviewCommitteeMeetingAgendaSevice.findByMeetingAgendaAndReviewCommitteeMeeting(meetingAgendaID, companyLearnersTermination.getReviewCommitteeMeeting().getId());
			if(meetingAgender !=null)
			{
				companyLearnersTermination.setReviewCommitteeMeetingAgenda(meetingAgender);
			}
			else
			{
				throw new Exception("Please add Learner Termination Application to the agenda of the selected Review Committee meeting");
			}
			//Adding meeting details to Events schedule
			List<Users> userList=reviewCommitteeMeetingUsersService.findUsersByReviewCommitteeMeeting(companyLearnersTermination.getReviewCommitteeMeeting().getId());
			scheduledEventService.addSheduleInfo(CompanyLearnersTermination.class.getName(), companyLearnersTermination.getId(), user, userList, null, null, meetingAgender.getMeetingAgenda().getDescription(), companyLearnersTermination.getReviewCommitteeMeeting());
		
		}
		
	}
	
	public void etqaCompanyLearnersFinalApprove(CompanyLearnersTermination companyLearners, Users user) throws Exception {
		CompanyLearners learners = companyLearners.getCompanyLearners();
		learners.setStatus(ApprovalEnum.Approved);
		learners.setLearnerStatus(LearnerStatusEnum.Terminated);
		companyLearners.setStatus(ApprovalEnum.Approved);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);
		dao.update(learners);
		new CompanyLearnersService().sendLPMTP003Email(companyLearners);
	}
	
	public void etqaCompanyLearnersFinalRejection(CompanyLearnersTermination companyLearners, Users user, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		CompanyLearners learners = companyLearners.getCompanyLearners();
		learners.setStatus(ApprovalEnum.Approved);
		learners.setLearnerStatus(LearnerStatusEnum.Registered);
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearners);
		dao.update(learners);
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), companyLearners.getClass().getName(), selectedRejectReason, user, ConfigDocProcessEnum.OneLearnerTermination);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
			
		}
		new CompanyLearnersService().sendLearnerTerminationUnsuccessful(learners.getUser(), selectedRejectReason);
		new CompanyLearnersService().sendLearnerTerminationUnsuccessful(companyLearners.getCreateUser(), selectedRejectReason);
		//Employer
		if(companyLearners !=null && companyLearners.getCompanyLearners() !=null && companyLearners.getCompanyLearners().getEmployer() !=null)
		{
			CompanyService companyService=new CompanyService();
			Company employer=companyService.findByKey(companyLearners.getCompanyLearners().getEmployer().getId());
			SDFCompany sdf=SDFCompanyService.instance().findPrimarySDF(employer);
			if(sdf !=null){
				Users empUser=sdf.getSdf();
				new CompanyLearnersService().sendLearnerTerminationUnsuccessful(empUser, selectedRejectReason);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public int countByReviewCommitteeMeetingAgenda(ReviewCommitteeMeetingAgenda reviewCommitteeMeetingAgenda) throws Exception {
		String hql = "select count(o) from CompanyLearnersTermination o where o.reviewCommitteeMeetingAgenda.id =:id";
		Map<String, Object> filters=new HashMap<>();
		filters.put("id", reviewCommitteeMeetingAgenda.getId());
		return dao.countWhere(filters, hql);

	}
	
	public void completeSystemTermination(CompanyLearnersTermination companyLearners, Users user, Tasks tasks) throws Exception {
        
        TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
    }
    
    public void approveSystemTermination(CompanyLearnersTermination companyLearners, Users user, Tasks tasks) throws Exception {
        
        TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
    }
    
    public void downloadLPMFM016(CompanyLearnersTermination companyLearnersTermination) throws Exception {
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
		SignoffService  signoffService = new SignoffService();
		List<Signoff>signOffs = signoffService.findByTargetKeyAndClass(companyLearnersTermination.getId(), companyLearnersTermination.getClass().getName());
		if(signOffs.size()>0) {
			Signoff signoff = signOffs.get(0);
			if(signoff != null && signoff.getAccept()) {
				params.put("signoff_initials", populateInitial(signoff.getUser()));
				params.put("signoff", signoff);
			}
			Signoff learner_signoff = signOffs.get(1);
			if(learner_signoff != null && learner_signoff.getAccept()) {
				params.put("learner_signoff_initials", populateInitial(learner_signoff.getUser()));
				params.put("learner_signoff",learner_signoff);
			}
		}
		
		JasperService.addLogo(params);

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Termination_Letter.pdf";
		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-FM-016-Termination-of-Learnership-Agreement-Mutual-Signoff.jasper", params, fileName);
	}

	/**
	 * One-sided termination Send termination report
	 * 
	 * @throws Exception
	 */
	public void downloadLPMFM017(CompanyLearnersTermination companyLearnersTermination) throws Exception {
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
		params.put("applicant_full_name", companyLearnersTermination.getCreateUser().getFirstName()+" "+companyLearnersTermination.getCreateUser().getLastName());
		SignoffService  signoffService = new SignoffService();
		List<Signoff>signOffs = signoffService.findByTargetKeyAndClass(companyLearnersTermination.getId(), companyLearnersTermination.getClass().getName());
		if(signOffs.size()>0) {
			Signoff signoff = signOffs.get(0);
			if(signoff != null && signoff.getAccept()) {
				params.put("signoff_initials", populateInitial(signoff.getUser()));
				params.put("signoff", signoff);
			}
			/*Signoff learner_signoff = signOffs.get(1);
			if(learner_signoff != null && learner_signoff.getAccept()) {
				params.put("learner_signoff_initials", populateInitial(learner_signoff.getUser()));
				params.put("learner_signoff",learner_signoff);
			}*/
		}
		
		JasperService.addLogo(params);
		
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Termination_Form.pdf";
		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-FM-017-Termination-of-Learnership-Agreement-One-Sided-Signoff.jasper", params, fileName);
	}

	private String populateInitial(Users user) {
		String inStringForm = "";
		if(user != null) {
			inStringForm = inStringForm + user.getFirstName().trim().charAt(0) + user.getLastName().trim().charAt(0);
		}
		return inStringForm;
	}
	
	public void uploadDocuments(CompanyLearnersTermination entity, Tasks task, Users sessionUser) throws Exception {
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
}
