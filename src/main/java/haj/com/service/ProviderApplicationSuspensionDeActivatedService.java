package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.ProviderApplicationSuspensionDeActivatedDAO;
import haj.com.entity.Address;
import haj.com.entity.Doc;
import haj.com.entity.ProviderApplicationSuspensionDeActivated;
import haj.com.entity.SDPCompany;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.Users;
import haj.com.entity.enums.ProviderSusActionsEnum;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;

public class ProviderApplicationSuspensionDeActivatedService extends AbstractService {
	/** The dao. */
	private ProviderApplicationSuspensionDeActivatedDAO dao = new ProviderApplicationSuspensionDeActivatedDAO();
	
	/* Service levels */
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private SDPCompanyService sdpCompanyService = new SDPCompanyService();

	/**
	 * Get all ProviderApplicationSuspensionDeActivated
 	 * @author TechFinium 
 	 * @see   ProviderApplicationSuspensionDeActivated
 	 * @return a list of {@link haj.com.entity.ProviderApplicationSuspensionDeActivated}
	 * @throws Exception the exception
 	 */
	public List<ProviderApplicationSuspensionDeActivated> allProviderApplicationSuspensionDeActivated() throws Exception {
	  	return dao.allProviderApplicationSuspensionDeActivated();
	}

	/**
	 * Create or update ProviderApplicationSuspensionDeActivated.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ProviderApplicationSuspensionDeActivated
	 */
	public void create(ProviderApplicationSuspensionDeActivated entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ProviderApplicationSuspensionDeActivated.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProviderApplicationSuspensionDeActivated
	 */
	public void update(ProviderApplicationSuspensionDeActivated entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ProviderApplicationSuspensionDeActivated.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ProviderApplicationSuspensionDeActivated
	 */
	public void delete(ProviderApplicationSuspensionDeActivated entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ProviderApplicationSuspensionDeActivated}
	 * @throws Exception the exception
	 * @see    ProviderApplicationSuspensionDeActivated
	 */
	public ProviderApplicationSuspensionDeActivated findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ProviderApplicationSuspensionDeActivated by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ProviderApplicationSuspensionDeActivated}
	 * @throws Exception the exception
	 * @see    ProviderApplicationSuspensionDeActivated
	 */
	public List<ProviderApplicationSuspensionDeActivated> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load ProviderApplicationSuspensionDeActivated
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ProviderApplicationSuspensionDeActivated}
	 * @throws Exception the exception
	 */
	public List<ProviderApplicationSuspensionDeActivated> allProviderApplicationSuspensionDeActivated(int first, int pageSize) throws Exception {
		return dao.allProviderApplicationSuspensionDeActivated(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of ProviderApplicationSuspensionDeActivated for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the ProviderApplicationSuspensionDeActivated
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ProviderApplicationSuspensionDeActivated.class);
	}
	
    /**
     * Lazy load ProviderApplicationSuspensionDeActivated with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 ProviderApplicationSuspensionDeActivated class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ProviderApplicationSuspensionDeActivated} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<ProviderApplicationSuspensionDeActivated> allProviderApplicationSuspensionDeActivated(Class<ProviderApplicationSuspensionDeActivated> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList((List<ProviderApplicationSuspensionDeActivated>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));
	}
	
	private List<ProviderApplicationSuspensionDeActivated> populateAdditionalInformationList(List<ProviderApplicationSuspensionDeActivated> list) throws Exception {
		for (ProviderApplicationSuspensionDeActivated entity : list) {
			populateAdditionalInformation(entity);
		}
		return list;
	}

	private ProviderApplicationSuspensionDeActivated populateAdditionalInformation(ProviderApplicationSuspensionDeActivated entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDoc(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		}
		return entity;
	}
	
    /**
     * Get count of ProviderApplicationSuspensionDeActivated for lazy load with filters
     * @author TechFinium 
     * @param entity ProviderApplicationSuspensionDeActivated class
     * @param filters the filters
     * @return Number of rows in the ProviderApplicationSuspensionDeActivated entity
     * @throws Exception the exception     
     */	
	public int count(Class<ProviderApplicationSuspensionDeActivated> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public ProviderApplicationSuspensionDeActivated prepNewEntryByProviderApplication(TrainingProviderApplication tpa, Users sessionUser, ProviderSusActionsEnum providerSusActions) throws Exception{
		ProviderApplicationSuspensionDeActivated newEntry = new ProviderApplicationSuspensionDeActivated();
		newEntry.setCreateUser(sessionUser);
		newEntry.setTrainingProviderApplication(tpa);
		if (tpa.getCompany() != null && tpa.getCompany().getId() != null) {
			newEntry.setCompany(tpa.getCompany());
		}
		if (tpa.getApprovalStatus() != null) {
			newEntry.setPreviousProviderStatus(tpa.getApprovalStatus());
		}
		if (providerSusActions != null) {
			newEntry.setProviderSusActions(providerSusActions);
		}
		if (tpa.getCertificateNumber() != null && !tpa.getCertificateNumber().trim().isEmpty()) {
			newEntry.setAccreditationNumberAtTime(tpa.getCertificateNumber().trim());
		} else if (tpa.getAccreditationNumber() != null && !tpa.getAccreditationNumber().trim().isEmpty()) {
			newEntry.setAccreditationNumberAtTime(tpa.getAccreditationNumber().trim());
		} else {
			newEntry.setAccreditationNumberAtTime("N/A");
		}
		return newEntry;
	}
	
	
	public void submitNewEntry(ProviderApplicationSuspensionDeActivated newEntry, List<Doc> supportingDocs, Users sessionUser, TrainingProviderApplication tpa) throws Exception{
		List<IDataEntity> updateList = new ArrayList<>();
		if (newEntry.getProviderSusActions() == ProviderSusActionsEnum.RemoveSuspension) {
			newEntry.setPreviousProviderStatus(tpa.getApprovalStatus());
			newEntry.setStatusAssigned(tpa.getStatusBeforeSuspension());
			newEntry.setRemovedSuspension(true);
			tpa.setApprovalStatus(tpa.getStatusBeforeSuspension());
		} else {
			newEntry.setPreviousProviderStatus(tpa.getApprovalStatus());
			newEntry.setRemovedSuspension(false);
			tpa.setStatusBeforeSuspension(tpa.getApprovalStatus());
			tpa.setApprovalStatus(newEntry.getStatusAssigned());
		}
		// create entry
		create(newEntry);
		// update provider application
		updateList.add(tpa);
		dao.updateBatch(updateList);
		
		// creates / add supporting documents 
		if (supportingDocs != null) {
			for (Doc doc : supportingDocs) {
				if (doc.getId() == null) {
					doc.setTargetClass(newEntry.getClass().getName());
					doc.setTargetKey(newEntry.getId());
					DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), sessionUser);
				}
			}
		}
		sendEmailNotificationNewEntry(newEntry);
	}

	private void sendEmailNotificationNewEntry(ProviderApplicationSuspensionDeActivated entity) throws Exception {
		String subject = "CHANGES TO PROVIDER APPLICATION";
		String message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #RECEIVER_FULL_NAME#,</p>" + 
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please be advised #ACTION_USER_FULL_NAME# (#ACTION_USER_EMAIL#) has implemented changes to the following provider on #ACTION_DATE#:</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + 
				"Provider name: #COMPANY_NAME#  <br/>" +
				"Accreditation number: #APP_ACCRED_NUMBER# <br/>" +
				"Initial Status: #STATUS_BEFORE_ACTION# <br/>" +
				"Current Status: #NEW_STATUS# <br/>" +
				"Reason: #REASON# <br/>" +
				"</p>" +
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">For further clarity, please contact #ACTION_USER_FULL_NAME#.</p>" + 
				"<p style=\"font-size:11.0pt;\";font-family:\"Arial\">System Generated Notification.</p>";
		List<Users> notificationRecivers = new ArrayList<>();
		notificationRecivers.addAll(locateUsersForNotification(entity));
		message = replaceStringsProviderApplicationSuspensionDeActivated(message, entity);
		notifyUsers(subject, message, notificationRecivers, false, null);
	}
	
	private List<Users> locateUsersForNotification(ProviderApplicationSuspensionDeActivated entity) throws Exception{
		List<Users> notificationRecivers = new ArrayList<>();
		List<Users> employeeList = new ArrayList<>();	
		if (entity.getCreateUser() != null && entity.getCreateUser().getId() != null) {
			notificationRecivers.add(entity.getCreateUser());
		}
		employeeList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_QUALITY_ASSURANCE_ID));
//		employeeList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_MANAGER_QUALITY_ASSURANCE_ID));
		employeeList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_CHIEF_OPERATIONS_OFFICER_ID));
		employeeList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_CLIENT_SERVICE_ID));
		employeeList.addAll(hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.JOB_TITLE_SENIOR_MANAGER_ADMINISTRATION_ID));
		
		// locate primary SDP
		List<SDPCompany> sdpList = new ArrayList<>();
		SDPCompany primarySDP = null;
		// locate region
		Address addressForRegion = null;
		if (entity != null && entity.getTrainingProviderApplication() != null) {
			if (entity.getTrainingProviderApplication().getTrainingSite() != null && entity.getTrainingProviderApplication().getTrainingSite().getId() != null) {
				primarySDP = sdpCompanyService.findBySdpTypeByTrainingSiteId(entity.getTrainingProviderApplication().getTrainingSite().getId(), HAJConstants.PRIMARY_SDP_ID);
				if (primarySDP == null) {
					List<SDPCompany> allOtherSdp  = sdpCompanyService.findAllSdpByCompanyIdAndTrainingSiteId(entity.getTrainingProviderApplication().getCompany().getId(), entity.getTrainingProviderApplication().getTrainingSite().getId());
					if (!allOtherSdp.isEmpty()) {
						sdpList.addAll(allOtherSdp);
					}
					allOtherSdp = null;
				} else {
					sdpList.add(primarySDP);
				}
				if (entity.getTrainingProviderApplication().getTrainingSite().getResidentialAddress() != null && entity.getTrainingProviderApplication().getTrainingSite().getResidentialAddress().getId() != null) {
					addressForRegion = AddressService.instance().findByKey(entity.getTrainingProviderApplication().getTrainingSite().getResidentialAddress().getId());
				}
			} else if (entity.getTrainingProviderApplication().getCompany() != null && entity.getTrainingProviderApplication().getCompany().getId() != null) {
				primarySDP = sdpCompanyService.findBySdpTypeByHoldingCompany(entity.getTrainingProviderApplication().getCompany().getId(), HAJConstants.PRIMARY_SDP_ID);
				if (primarySDP == null) {
					List<SDPCompany> allOtherSdp  = sdpCompanyService.findAllSdpByCompanyId(entity.getTrainingProviderApplication().getCompany().getId());
					if (!allOtherSdp.isEmpty()) {
						sdpList.addAll(allOtherSdp);
					}
					allOtherSdp = null;
				} else {
					sdpList.add(primarySDP);
				}
				if (entity.getTrainingProviderApplication().getCompany().getResidentialAddress() != null && entity.getTrainingProviderApplication().getCompany().getResidentialAddress().getId() != null) {
					addressForRegion = AddressService.instance().findByKey(entity.getTrainingProviderApplication().getCompany().getResidentialAddress().getId());
				}
			}
		}
		primarySDP = null;
		
		// add all SDPs assigned
		if (!sdpList.isEmpty()) {
			for (SDPCompany sdpCompany : sdpList) {
				if (sdpCompany.getSdp() != null) {
					employeeList.add(sdpCompany.getSdp() );
				}
			}
		}
		sdpList.clear();
		
		if (addressForRegion != null && addressForRegion.getTown() != null) {
			RegionTown regionTown = RegionTownService.instance().findByTown(addressForRegion.getTown());
			if (regionTown != null) {
				// add CLO user
				if (regionTown.getClo() != null && regionTown.getClo().getUsers() != null) {
					employeeList.add(regionTown.getClo().getUsers());
				}
				// add CRM user
				if (regionTown.getCrm() != null && regionTown.getCrm().getUsers() != null) {
					employeeList.add(regionTown.getCrm().getUsers());
				}
			}
			// region QAs
			List<Users> qaUsers = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(addressForRegion.getTown(), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
			if (!qaUsers.isEmpty()) {
				employeeList.addAll(qaUsers);
			}
			qaUsers.clear();
			regionTown = null;
		}
		primarySDP = null;
		for (Users employee : employeeList) {
			if (determainIfUserCanBeAdded(notificationRecivers, employee)) {
				notificationRecivers.add(employee);
			}
		}	
		return notificationRecivers;
	}


	private boolean determainIfUserCanBeAdded(List<Users> usersList, Users newUser){
		// prevents duplicate users being added to notification
		boolean addUser = true;
		for (Users users : usersList) {
			if (users.getId().equals(newUser.getId())){
				addUser = false;
				break;
			}
		}
		return addUser;
	}
	
	public String replaceStringsProviderApplicationSuspensionDeActivated(String msg, ProviderApplicationSuspensionDeActivated entity) throws Exception {
		/*
		 * Tags:
		 * #ACTION_USER_FULL_NAME#, #ACTION_USER_EMAIL#, #ACTION_DATE#, #COMPANY_NAME#, #APP_ACCRED_NUMBER#, #STATUS_BEFORE_ACTION#, #NEW_STATUS#, #REASON#
		 */
		String actionUserFullName = "";
		if (entity.getCreateUser() != null && entity.getCreateUser().getId() != null) {
			actionUserFullName = entity.getCreateUser().getFirstName().trim() + " " + entity.getCreateUser().getLastName().trim();
		}else {
			actionUserFullName = "#UNABLE TO LOCATE ACTION USER#";
		}
		msg = msg.replace("#ACTION_USER_FULL_NAME#", actionUserFullName);
		
		
		String actionUserEmail = "";
		if (entity.getCreateUser() != null && entity.getCreateUser().getId() != null) {
			actionUserEmail = entity.getCreateUser().getEmail();
		}else {
			actionUserEmail = "#UNABLE TO LOCATE ACTION USER EMAIL#";
		}
		msg = msg.replace("#ACTION_USER_EMAIL#", actionUserEmail);
		msg = msg.replace("#ACTION_DATE#", ( (entity != null && entity.getId() != null && entity.getCreateDate() != null) ?  HAJConstants.sdfDDMMYYYYHHmm.format(entity.getCreateDate()) : "#UNABLE TO LOCATE ACTION DATE#" ) );
		msg = msg.replace("#COMPANY_NAME#", ( (entity != null && entity.getId() != null && entity.getCompany() != null) ?  entity.getCompany().getCompanyName().trim() : "#UNABLE TO LOCATE COMPANY NAME#" ) );
		msg = msg.replace("#APP_ACCRED_NUMBER#", ( (entity != null && entity.getId() != null && entity.getAccreditationNumberAtTime() != null) ?  entity.getAccreditationNumberAtTime().trim() : "#UNABLE TO LOCATE APPLICATION ACC NUMBER#" ) );
		msg = msg.replace("#STATUS_BEFORE_ACTION#", ( (entity != null && entity.getId() != null && entity.getPreviousProviderStatus() != null) ?  entity.getPreviousProviderStatus().getFriendlyName() : "#UNABLE TO LOCATE PREVIOUS STATUS#" ) );
		msg = msg.replace("#NEW_STATUS#", ( (entity != null && entity.getId() != null && entity.getStatusAssigned() != null) ?  entity.getStatusAssigned().getFriendlyName() : "#UNABLE TO LOCATE STATUS ASSIGNED#" ) );
		msg = msg.replace("#REASON#", ( (entity != null && entity.getId() != null && entity.getReason() != null) ?  entity.getReason().trim() : "#UNABLE TO LOCATE REASON#" ) );
		return msg;
	}
	
	public void notifyUsers(String subject, String message, List<Users> notificationRecivers, boolean withAttachment, List<AttachmentBean> attachmentBeans) {
		for (Users users : notificationRecivers) {
			String fullName = "";
			if (users.getTitle() != null && users.getTitle().getDescription() != null) {
				fullName = users.getTitle().getDescription() + " ";
			}
			fullName += users.getFirstName().trim() + " " + users.getLastName().trim();
			if (withAttachment) {
				GenericUtility.sendMailWithAttachementTempWithLog(users.getEmail(), subject, message.replace("#RECEIVER_FULL_NAME#", fullName), attachmentBeans, null);
			}else {
				GenericUtility.sendMail(users.getEmail(), subject, message.replace("#RECEIVER_FULL_NAME#", fullName), null);
			}
		}
	}
	
}