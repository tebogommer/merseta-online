package haj.com.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.TrainingComitteeDAO;
import haj.com.entity.Company;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingComittee;
import haj.com.entity.TrainingComitteeHistory;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.framework.AbstractService;
import haj.com.ui.CompanyInfoUI;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class TrainingComitteeService.
 */
public class TrainingComitteeService extends AbstractService {
	/** The dao. */
	private TrainingComitteeDAO dao = new TrainingComitteeDAO();

	 private static TrainingComitteeService trainingComitteeService = null;

	/**
	 * Instance.
	 *
	 * @return the SitesService
	 */
	public static synchronized TrainingComitteeService instance() {
		if (trainingComitteeService == null) {
			trainingComitteeService = new TrainingComitteeService();
		}
		return trainingComitteeService;
	} 
	
	
	/**
	 * Get all TrainingComittee.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TrainingComittee}
	 * @throws Exception
	 *             the exception
	 * @see TrainingComittee
	 */
	public List<TrainingComittee> allTrainingComittee() throws Exception {
		return dao.allTrainingComittee();
	}

	/**
	 * Create or update TrainingComittee.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingComittee
	 */
	public void create(TrainingComittee entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	public void copyUser(Users newSDF, Company company) throws Exception {
		TrainingComittee sdfTrainingComittee = new TrainingComittee();
		sdfTrainingComittee.setEmail(newSDF.getEmail());
		sdfTrainingComittee.setFirstName(newSDF.getFirstName());
		sdfTrainingComittee.setLastName(newSDF.getLastName());
		sdfTrainingComittee.setCompany(company);

		if (newSDF.getTelNumber() != null && !newSDF.getTelNumber().isEmpty()) {
			sdfTrainingComittee.setTelNumber(newSDF.getTelNumber());
		}

		if (newSDF.getCellNumber() != null && !newSDF.getCellNumber().isEmpty()) {
			sdfTrainingComittee.setCellNumber(newSDF.getCellNumber());
		}

		if (newSDF.getFaxNumber() != null && !newSDF.getFaxNumber().isEmpty()) {
			sdfTrainingComittee.setFaxNumber(newSDF.getFaxNumber());
		}

		// check if RSA ID is not null and not empty
		if (newSDF.getRsaIDNumber() != null && !newSDF.getRsaIDNumber().isEmpty()) {
			sdfTrainingComittee.setRsaIDNumber(newSDF.getRsaIDNumber());
			GenericUtility.calcIDData(sdfTrainingComittee);
		} else {
			sdfTrainingComittee.setPassportNumber(newSDF.getPassportNumber());
		}
		create(sdfTrainingComittee);

	}
	
	public void copyUserAndCreateTask(Users newSDF, Company company,Users users) throws Exception {
		TrainingComittee sdfTrainingComittee = new TrainingComittee();
		sdfTrainingComittee.setEmail(newSDF.getEmail());
		sdfTrainingComittee.setFirstName(newSDF.getFirstName());
		sdfTrainingComittee.setLastName(newSDF.getLastName());
		sdfTrainingComittee.setCompany(company);

		if (newSDF.getTelNumber() != null && !newSDF.getTelNumber().isEmpty()) {
			sdfTrainingComittee.setTelNumber(newSDF.getTelNumber());
		}

		if (newSDF.getCellNumber() != null && !newSDF.getCellNumber().isEmpty()) {
			sdfTrainingComittee.setCellNumber(newSDF.getCellNumber());
		}

		if (newSDF.getFaxNumber() != null && !newSDF.getFaxNumber().isEmpty()) {
			sdfTrainingComittee.setFaxNumber(newSDF.getFaxNumber());
		}

		// check if RSA ID is not null and not empty
		if (newSDF.getRsaIDNumber() != null && !newSDF.getRsaIDNumber().isEmpty()) {
			sdfTrainingComittee.setRsaIDNumber(newSDF.getRsaIDNumber());
			GenericUtility.calcIDData(sdfTrainingComittee);
		} else {
			sdfTrainingComittee.setPassportNumber(newSDF.getPassportNumber());
		}
		
				
		sdfTrainingComittee.setApprovalStatus(ApprovalEnum.PendingApproval);
		create(sdfTrainingComittee);
		
		//Creating change reason
		ChangeReasonService.instance().createChangeReason(sdfTrainingComittee.getId(), sdfTrainingComittee.getClass().getName(), CompanyInfoUI.changeReason);
		//Creating task
		String desc="New Training comittee has been added, please approve the information provided";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, users, sdfTrainingComittee.getId(), sdfTrainingComittee.getClass().getName(), true, ConfigDocProcessEnum.NEW_TRAINING_COMMITTEE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
				

	}

	/**
	 * Update TrainingComittee.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingComittee
	 */
	public void update(TrainingComittee entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete TrainingComittee.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see TrainingComittee
	 */
	public void delete(TrainingComittee entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.TrainingComittee}
	 * @throws Exception
	 *             the exception
	 * @see TrainingComittee
	 */
	public TrainingComittee findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find TrainingComittee by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.TrainingComittee}
	 * @throws Exception
	 *             the exception
	 * @see TrainingComittee
	 */
	public List<TrainingComittee> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load TrainingComittee.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.TrainingComittee}
	 * @throws Exception
	 *             the exception
	 */
	public List<TrainingComittee> allTrainingComittee(int first, int pageSize) throws Exception {
		return dao.allTrainingComittee(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of TrainingComittee for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the TrainingComittee
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(TrainingComittee.class);
	}

	/**
	 * Lazy load TrainingComittee with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param startingAt
	 *            the starting at
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.TrainingComittee} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainingComittee> allTrainingComittee(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from TrainingComittee o left join fetch o.company c left join fetch o.union u left join fetch o.title t left join fetch o.gender g left join fetch o.equity e where o.company.id = :companyID";
		return (List<TrainingComittee>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql);

	}

	/**
	 * Get count of TrainingComittee for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            TrainingComittee class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the TrainingComittee entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<TrainingComittee> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from TrainingComittee o where o.company.id = :companyID";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Find by company.
	 *
	 * @param company
	 *            the company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<TrainingComittee> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company);
	}

	/**
	 * Find by company count.
	 *
	 * @param company
	 *            the company
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long findByCompanyCount(Company company) throws Exception {
		return dao.findByCompanyCount(company);
	}
	
	/**
	 * Create or update TrainingComittee and Create task.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @param users
	 * 			  the Users
	 * @throws Exception
	 *             the exception
	 * @see TrainingComittee
	 */
	public void createTrainingComAndTask(TrainingComittee entity,Users users) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
		//Creating ChangeReason
		ChangeReasonService.instance().createChangeReason(entity.getId(), entity.getClass().getName(), CompanyInfoUI.changeReason);
		//Creating task
		String desc="New Training comittee has been added, please approve the information provided";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.NEW_TRAINING_COMMITTEE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		
	}
	
	/**
	 * Approve  TrainingComittee update
	 * @param trainingComittee
	 * 		the TrainingComittee
	 * @param task
	 * 		The Tasks
	 * @throws Exception 
	 */
	public void approveTrainingComUpdateTask(TrainingComittee trainingComittee,Tasks task) throws Exception
	{
		trainingComittee.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingComittee);
		TasksService.instance().completeTask(task);
		//Sending Email to task creator
		UsersService usersService=new UsersService();
		//Sending email to user who created added the training committee
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Training Committee Changes Approval", " Changes for Training Comittee (" + trainingComittee.getFirstName() +" "+trainingComittee.getLastName()+ ") has been approved on the merSETA NSDMS system.", null);
	    
		//Sending email to TrainingComittee
		GenericUtility.sendMail(trainingComittee.getEmail(), "Training Committee Changes Approval", "Your Training Committee changes have been approved ", null);
			
	
	}
	
	/**
	 * Reject Training Committee update
	 * @param trainingComittee
	 * 		the TrainingComittee
	 * @param task
	 * 		The Tasks
	 * @param trainingComitteeHistory
	 * 			the TrainingComitteeHistory
	 * @throws Exception 
	 */
	public void rejectTrainingComUpdateTask(TrainingComittee trainingComittee,Tasks task,TrainingComitteeHistory trainingComitteeHistory) throws Exception
	{
		//Reverting the changes
		Long tempID=trainingComittee.getId();
		
		BeanUtils.copyProperties(trainingComittee,trainingComitteeHistory);
		trainingComittee.setId(tempID);
		
		trainingComittee.setApprovalStatus(ApprovalEnum.Rejected);
		update(trainingComittee);
		TasksService.instance().completeTask(task);
		
		//Sending email to user who the task
		UsersService usersService=new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Training Committee Changes Rejection", " Changes for Training Comittee (" + trainingComittee.getFirstName() +" "+trainingComittee.getLastName()+ ") has been rejected on the merSETA NSDMS system.", null);
			    
		//Sending email to TrainingComittee
		GenericUtility.sendMail(trainingComittee.getEmail(), "Training Committee Changes Rejection", "Your Training Committee changes have been rejected ", null);
					
	}
	
	
	
	/**
	 * Reject Training Committee delete
	 * @param trainingComittee
	 * 		the TrainingComittee
	 * @param task
	 * 		The Tasks
	 * @param trainingComitteeHistory
	 * 			the TrainingComitteeHistory
	 * @throws Exception 
	 */
	public void rejectDeleteTCTask(TrainingComittee trainingComittee,Tasks task,TrainingComitteeHistory trainingComitteeHistory) throws Exception
	{
		//Reverting the changes
		Long tempID=trainingComittee.getId();
		
		BeanUtils.copyProperties(trainingComittee,trainingComitteeHistory);
		trainingComittee.setId(tempID);
		
		trainingComittee.setApprovalStatus(ApprovalEnum.Rejected);
		update(trainingComittee);
		TasksService.instance().completeTask(task);
		
		// Sending email to task creator
		UsersService usersService = new UsersService();
		String fullname=trainingComittee.getFirstName()+" "+trainingComittee.getLastName();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Delete Training Committe Rejection", "The attempt to delete training committee (" + fullname + "  ) has been rejected on the merSETA NSDMS system.", null);
	
					
	}
	
	
	/**
	 * Approve new TrainingComittee
	 * @param trainingComittee
	 * 		the TrainingComittee
	 * @param task
	 * 		The Tasks
	 * @throws Exception 
	 */
	public void approveNewTrainingTask(TrainingComittee trainingComittee,Tasks task) throws Exception
	{
		trainingComittee.setApprovalStatus(ApprovalEnum.Approved);
		update(trainingComittee);
		TasksService.instance().completeTask(task);
		//Sending Email to task creator
		UsersService usersService=new UsersService();
		//Sending email to user who created the training committee
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "New Training Committee Approval", " New Training Comittee (" + trainingComittee.getFirstName() +" "+trainingComittee.getLastName()+ ") has been approved on the merSETA NSDMS system.", null);
	    
		//Sending email to TrainingComittee
		GenericUtility.sendMail(trainingComittee.getEmail(), "Training Committee Approval", "You have been added as training committee for "+trainingComittee.getCompany().getCompanyName(), null);
			
	
	}
	
	/**
	 * Reject new TrainingComittee
	 * @param trainingComittee
	 * 		the TrainingComittee
	 * @param task
	 * 		The Tasks
	 * @throws Exception 
	 */
	public void rejectNewTrainingTask(TrainingComittee trainingComittee,Tasks task) throws Exception
	{
		trainingComittee.setApprovalStatus(ApprovalEnum.Rejected);
		update(trainingComittee);
		TasksService.instance().completeTask(task);
		//Sending Email to task creator
		UsersService usersService=new UsersService();
		//Sending email to user who created added the training committee
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "New Training Committee rejection", " New Training Comittee (" + trainingComittee.getFirstName() +" "+trainingComittee.getLastName()+ ") has been rejected on the merSETA NSDMS system.", null);
	    
		//Sending email to TrainingComittee
		GenericUtility.sendMail(trainingComittee.getEmail(), "Training Committee Rejection", "You have been rejected as training committee for "+trainingComittee.getCompany().getCompanyName(), null);
			
	
	}
	
	public void approveRemoveTCTask(TrainingComittee trainingComittee, Tasks task) throws Exception {
		// Delete Training Committee History
		TrainingComitteeHistoryService.instance().removeTrainingCommitteeHistory(trainingComittee);
		// Deleting Training Committee
		delete(trainingComittee);
		TasksService.instance().completeTask(task);
		//Sending email to task creator
		UsersService usersService = new UsersService();
		String fullname=trainingComittee.getFirstName()+" "+trainingComittee.getLastName();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Delete Training Committe Approval", "The attempt to delete training committee (" + fullname + "  ) has been approve on the merSETA NSDMS system.", null);
	}
	
	/**
	 * Create or update TrainingComittee and Create task.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @param users
	 * 			  the Users
	 * @throws Exception
	 *             the exception
	 * @see TrainingComittee
	 */
	public void updateTrainingComAndTask(TrainingComittee entity,Users users) throws Exception {
		
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
		//Creating ChangeReason
		ChangeReasonService.instance().createChangeReason(entity.getId(), entity.getClass().getName(), CompanyInfoUI.changeReason);
		//Creating task
		String desc="Training comittee has been updated, please approve the information provided";
		
		TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.TRAINING_COMMITTEE_CHANGE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		
	}
	
	/**
	 * Create or update TrainingComittee and Create task.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @param users
	 * 			  the Users
	 * @throws Exception
	 *             the exception
	 * @see TrainingComittee
	 */
	public void updateTrainingComAndCreateDelTask(TrainingComittee entity,Users users) throws Exception {
		
		if (entity.getId() == null) {
			this.dao.create(entity);
		} 
		else
		{
			//Creating Training Committee History
			TrainingComitteeHistoryService.instance().createHistory(entity.getId());
			//Updating
			this.dao.update(entity);
		}
		//Creating ChangeReason
		ChangeReasonService.instance().createChangeReason(entity.getId(), entity.getClass().getName(), CompanyInfoUI.changeReason);
		//Creating task
		String desc="An attempt to delete training committee has been made, please approve the information provided";
		
		TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.DELETE_TRAINING_COMMITTEE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		
	}
	
	public void checkByCriteria(TrainingComittee trainingCommitee, Company company) throws Exception {
		List<TrainingComittee> userErrorList = dao.findByComitteeAttributes(trainingCommitee,company);
		String errors = "";
		boolean errorHit = false;
		for (TrainingComittee user : userErrorList) {
			
//			System.out.println("User Email: " + user.getEmail().trim() + "  trainingCommitee email: " + trainingCommitee.getEmail());
			if (user.getEmail().trim().toLowerCase().equals(trainingCommitee.getEmail().trim().toLowerCase())) {
				errors +=" Email Address  ";
				errorHit = true;
			}
			
//			System.out.println("User RSA ID: " + user.getRsaIDNumber() + "  trainingCommitee RSA ID: " + trainingCommitee.getRsaIDNumber());
			if (user.getRsaIDNumber().trim().equals(trainingCommitee.getRsaIDNumber().trim())) {
				if (errorHit) {
					errors +=",";
				}
				errors += " RSA ID  ";
				errorHit = true;
			}
			
			if (user.getPassportNumber().length() != 0 && trainingCommitee.getPassportNumber().length() != 0) {
//				System.out.println("User Pass Port: " + user.getPassportNumber() + "  trainingCommitee Pass Port: " + trainingCommitee.getPassportNumber());
				if (user.getPassportNumber().equals(trainingCommitee.getPassportNumber())) {
					if (errorHit) {
						errors +=",";
					}
					errors += " Passport Number  ";
					errorHit = true;
				}
			}
			if (errorHit) {
//				System.out.println("BREAK");
				break;
			}
		}
		userErrorList = null;
		if (!errors.isEmpty() && errors.length() != 0) {
			throw new Exception("The following info is used already:" + errors);
		}
	}
	
	
}
