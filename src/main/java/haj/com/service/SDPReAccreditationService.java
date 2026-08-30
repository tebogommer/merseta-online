package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.SDPReAccreditationDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SDPReAccreditation;
import haj.com.entity.Tasks;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderDocParent;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.AuditorMonitorReviewService;
import haj.com.service.lookup.ProviderStatusService;
import haj.com.service.lookup.RolesService;
import haj.com.service.lookup.TownService;
import haj.com.utils.GenericUtility;

public class SDPReAccreditationService extends AbstractService {
	/** The dao. */
	private SDPReAccreditationDAO dao = new SDPReAccreditationDAO();
	private AuditorMonitorReviewService auditorMonitorReviewService = new AuditorMonitorReviewService();

	/**
	 * Get all SDPReAccreditation
 	 * @author TechFinium 
 	 * @see   SDPReAccreditation
 	 * @return a list of {@link haj.com.entity.SDPReAccreditation}
	 * @throws Exception the exception
 	 */
	public List<SDPReAccreditation> allSDPReAccreditation() throws Exception {
	  	return dao.allSDPReAccreditation();
	}


	/**
	 * Create or update SDPReAccreditation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SDPReAccreditation
	 */
	public void create(SDPReAccreditation entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SDPReAccreditation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SDPReAccreditation
	 */
	public void update(SDPReAccreditation entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SDPReAccreditation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SDPReAccreditation
	 */
	public void delete(SDPReAccreditation entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SDPReAccreditation}
	 * @throws Exception the exception
	 * @see    SDPReAccreditation
	 */
	public SDPReAccreditation findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SDPReAccreditation by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SDPReAccreditation}
	 * @throws Exception the exception
	 * @see    SDPReAccreditation
	 */
	public List<SDPReAccreditation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<SDPReAccreditation> findByTrainingProviderApplication(Long id) throws Exception {
		return dao.findByTrainingProviderApplication(id);
	}
	
	/**
	 * Lazy load SDPReAccreditation
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SDPReAccreditation}
	 * @throws Exception the exception
	 */
	public List<SDPReAccreditation> allSDPReAccreditation(int first, int pageSize) throws Exception {
		return dao.allSDPReAccreditation(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SDPReAccreditation for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SDPReAccreditation
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SDPReAccreditation.class);
	}
	
    /**
     * Lazy load SDPReAccreditation with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SDPReAccreditation class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SDPReAccreditation} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SDPReAccreditation> allSDPReAccreditation(Class<SDPReAccreditation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SDPReAccreditation>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SDPReAccreditation for lazy load with filters
     * @author TechFinium 
     * @param entity SDPReAccreditation class
     * @param filters the filters
     * @return Number of rows in the SDPReAccreditation entity
     * @throws Exception the exception     
     */	
	public int count(Class<SDPReAccreditation> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public int countOpenSDPReAccreditationByProviderApplicationId(Long trainingProviderApplicationId, List<ApprovalEnum> openStatusList) throws Exception {
		return dao.countOpenSDPReAccreditationByProviderApplicationId(trainingProviderApplicationId, openStatusList);
	}
	
	public void requesteWorkflow(SDPReAccreditation entity, Users u) throws Exception {
			entity.setStatus(ApprovalEnum.PendingApproval);
			dao.create(entity);
			TasksService.instance().findFirstInProcessAndCreateTask(u, entity.getId(), SDPReAccreditation.class.getName(), ConfigDocProcessEnum.SDPReAccreditation, false);
		}	
		
		public void completeWorkflow(SDPReAccreditation entity, Users user, Tasks tasks) throws Exception {
			String error = "";
			if (entity.getDocs() != null) {
				for (Doc doc : entity.getDocs()) {
					if (doc.getId() != null) {
						DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
						if (docByte != null) doc.setData(docByte.getData());
					}
					if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument() != null) {
						error += "Upload " + doc.getConfigDoc().getName() + " for " ;
					}
				}
			}
			
			if (error.length() > 0) throw new ValidationException(error);
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}
		
		public void approveWorkflow(SDPReAccreditation entity, Users user, Tasks tasks) throws Exception {
			entity.setStatus(ApprovalEnum.WaitingForManager);
			dao.update(entity);
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}
		
		public void rejectWorkflow(SDPReAccreditation entity, Users user, Tasks tasks) throws Exception {
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
		}
		
		public void finalApproveWorkflow(SDPReAccreditation entity, Users user, Tasks tasks) throws Exception {
			entity.setStatus(ApprovalEnum.Approved);
			entity.setApprovalDate(getSynchronizedDate());
			dao.update(entity);
			TasksService.instance().completeTask(tasks);
		}
		
		public void finalRejectWorkflow(SDPReAccreditation entity, Users user, Tasks tasks) throws Exception {
			entity.setStatus(ApprovalEnum.Rejected);
			entity.setApprovalDate(getSynchronizedDate());
			dao.update(entity);
			TasksService.instance().completeTask(tasks);
		}


		public void requestReAccreditation( TrainingProviderApplication trainingProviderApplication, Users activeUser) throws Exception {
			List<Users> qualityAssurorUsers=getQualityAssurorUsers(trainingProviderApplication);
			SDPReAccreditation sdpReAccreditation=new SDPReAccreditation();
			ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.SDPReAccreditation;
			sdpReAccreditation.setStatus(ApprovalEnum.PendingApproval);
			trainingProviderApplication.setProviderStatus(trainingProviderApplication.getProviderStatus());
//			trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingChangeApproval);
			sdpReAccreditation.setUsers(activeUser);
			sdpReAccreditation.setPrevioursAccreditationApplicationType(trainingProviderApplication.getAccreditationApplicationTypeEnum());
//			trainingProviderApplication.setAccreditationApplicationTypeEnum(AccreditationApplicationTypeEnum.REACCREDITATIONREAPPROVAL);
			sdpReAccreditation.setPrevioursSiteVisitComment(trainingProviderApplication.getSiteVisitComment());
			sdpReAccreditation.setPrevioursSiteVisitDate(trainingProviderApplication.getSiteVisitDate());
			sdpReAccreditation.setPrevioursSiteVisitReportDate(trainingProviderApplication.getSiteVisitReportDate());
			sdpReAccreditation.setTrainingProviderApplication(trainingProviderApplication);
			
			trainingProviderApplication.setSiteVisitComment(null);
			trainingProviderApplication.setSiteVisitDate(null);
			trainingProviderApplication.setSiteVisitDone(false);
			trainingProviderApplication.setSiteVisitReportDate(null);
			
			create(sdpReAccreditation);
			TrainingProviderApplicationService.instance().update(trainingProviderApplication);
			// Creating Self Evaluation For merSETA company
			auditorMonitorReviewService.prepSelfEvaluation(SDPReAccreditation.class.getName(),sdpReAccreditation.getId(), configDocProcessEnum, trainingProviderApplication);
			String desc = "";
			TasksService.instance().findFirstInProcessAndCreateTask(desc, activeUser, sdpReAccreditation.getId(),
					sdpReAccreditation.getClass().getName(), true, configDocProcessEnum,
					MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), qualityAssurorUsers);
		}
		
		public void requestResubmission(TrainingProviderApplication trainingProviderApplication, Users activeUser,List<CompanyQualifications> companyQualifications, List<CompanyUnitStandard> companyUnitStandardsList, List<TrainingProviderSkillsProgramme> tpSkillsProgramme, List<TrainingProviderSkillsSet> tpSkillsSetList, List<TrainingProviderDocParent> docParentList) throws Exception {
			
			SDPReAccreditation sdpReAccreditation=new SDPReAccreditation();
			ConfigDocProcessEnum configDocProcessEnum=ConfigDocProcessEnum.SDP_RE_SUBMISSION;
			sdpReAccreditation.setStatus(ApprovalEnum.PendingApproval);
			trainingProviderApplication.setPreviousStatus(trainingProviderApplication.getApprovalStatus());
//			trainingProviderApplication.setApprovalStatus(ApprovalEnum.PendingChangeApproval);
			sdpReAccreditation.setUsers(activeUser);
			sdpReAccreditation.setPrevioursAccreditationApplicationType(trainingProviderApplication.getAccreditationApplicationTypeEnum());
			sdpReAccreditation.setTrainingProviderApplication(trainingProviderApplication);
			create(sdpReAccreditation);
			TrainingProviderApplicationService.instance().update(trainingProviderApplication);

			List<IDataEntity> dataEntitiesCreate = new ArrayList<IDataEntity>();
			List<IDataEntity> dataEntitiesUpdate = new ArrayList<IDataEntity>();
			for (TrainingProviderDocParent docParent : docParentList) {
				dataEntitiesCreate.add(docParent);
				Doc doc = docParent.getDoc();
				doc.setVersionNo(1);
				doc.setUsers(activeUser);
				doc.setTargetKey(docParent.getId());
				doc.setTargetClass(TrainingProviderDocParent.class.getName());
				dataEntitiesCreate.add(doc);
				dataEntitiesCreate.add(new DocByte(doc.getData(), doc));
				dataEntitiesCreate.add(new DocumentTracker(doc, activeUser, new java.util.Date(), DocumentTrackerEnum.Upload));
			}
			if (dataEntitiesCreate.size() > 0) {
				dao.createBatch(dataEntitiesCreate);
			}
			
			dataEntitiesCreate = new ArrayList<IDataEntity>();
			// Creating qualifications
			for (CompanyQualifications cp : companyQualifications) {
				cp.setTargetClass(TrainingProviderApplication.class.getName());
				cp.setTargetKey(trainingProviderApplication.getId());
				cp.setCompany(trainingProviderApplication.getCompany());
				if(cp !=null && cp.getId()==null){
					dataEntitiesCreate.add(cp);
				} else {
					dataEntitiesUpdate.add(cp);
				}
			}
			// Creating unit standards
			for (CompanyUnitStandard us : companyUnitStandardsList) {
				us.setTargetClass(TrainingProviderApplication.class.getName());
				us.setTargetKey(trainingProviderApplication.getId());
				us.setCompany(trainingProviderApplication.getCompany());
				if(us !=null && us.getId()==null){
					dataEntitiesCreate.add(us);
				}
				else{
					dataEntitiesUpdate.add(us);
				}
				
			}
			
			// Creating Provider Skills Programme
			for (TrainingProviderSkillsProgramme tpSkillsProg : tpSkillsProgramme) {
				tpSkillsProg.setTrainingProviderApplication(trainingProviderApplication);
				if(tpSkillsProg !=null && tpSkillsProg.getId()==null){
					dataEntitiesCreate.add(tpSkillsProg);
				}
				else{
					dataEntitiesUpdate.add(tpSkillsProg);
				}
				
			}
			
			// Creating Provider Skills Set
			for (TrainingProviderSkillsSet tpSkillsSet : tpSkillsSetList) {
				tpSkillsSet.setTrainingProviderApplication(trainingProviderApplication);
				if(tpSkillsSet !=null && tpSkillsSet.getId()==null){
					dataEntitiesCreate.add(tpSkillsSet);
				}
				else{
					dataEntitiesUpdate.add(tpSkillsSet);
				}
			}
			if (dataEntitiesCreate.size() > 0) {
				dao.createBatch(dataEntitiesCreate);
			}
			if (dataEntitiesUpdate.size() > 0) {
				dao.updateBatch(dataEntitiesUpdate);
			}
			String desc = "";
			TasksService.instance().findFirstInProcessAndCreateTask(desc, activeUser, sdpReAccreditation.getId(), sdpReAccreditation.getClass().getName(), true, configDocProcessEnum, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			//Sending Email 
			sendResubmissionAcknolowdgementEmail(activeUser, trainingProviderApplication);
		}
		
		public void approveResubmission(SDPReAccreditation sdpReAccreditation,TrainingProviderApplication tpApplication,List<CompanyQualifications> companyQualifications,List<CompanyUnitStandard> unitStandards,List<TrainingProviderSkillsProgramme> skillsProgramList, Tasks task, List<TrainingProviderSkillsSet> tpSkillsSetList) throws Exception {
			sdpReAccreditation.setStatus(ApprovalEnum.Approved);
			tpApplication.setApprovalStatus(ApprovalEnum.Approved);
			tpApplication.setProviderStatus(ProviderStatusService.instance().findByCode("511"));
			sdpReAccreditation.setApprovedDate(new Date());
			tpApplication.setApprovedDate(new Date());
			String certificateNum="";
			CompanyService companyService=new CompanyService();
			Company company=tpApplication.getCompany();
			company.setAccreditationNumber(tpApplication.getAccreditationNumber());
			company.setCompanyStatus(CompanyStatusEnum.Active);
			companyService.upadeCompanyInfo(company);
			update(sdpReAccreditation);
			updateReSubmissionDates(tpApplication);
			TrainingProviderApplicationService.instance().update(tpApplication);
			TasksService.instance().completeTask(task);
			if(tpApplication.getAccreditationApplicationTypeEnum() ==AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL || tpApplication.getAccreditationApplicationTypeEnum() ==AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL)
			{
				TrainingProviderApplicationService.instance().sendProFormaLetterRorFullAccreditationEmail(certificateNum, tpApplication, companyQualifications, unitStandards,skillsProgramList,tpSkillsSetList);
			}
			else
			{
				List<Qualification> qualList=new ArrayList<>();
				for(CompanyQualifications cq:companyQualifications){
					qualList.add(cq.getQualification());
				}
				List<UnitStandards> usList=new ArrayList<>();
				for(CompanyUnitStandard cu:unitStandards){
					usList.add(cu.getUnitStandard());
				}
				List<SkillsProgram> spList=new ArrayList<>();
			    for(TrainingProviderSkillsProgramme tpSs: skillsProgramList) {
			    	spList.add(tpSs.getSkillsProgram()) ;
				}
			    List<SkillsSet> ssList=new ArrayList<>();
			    for(TrainingProviderSkillsSet ss:tpSkillsSetList){
			    	ssList.add(ss.getSkillsSet());
			    }
				sendResubmissionApprovalEmail(usList, qualList, sdpReAccreditation.getUsers(), tpApplication, spList, ssList);
			}
		}
		
		private void updateReSubmissionDates(TrainingProviderApplication tpApplication) throws Exception{
			if (tpApplication != null && tpApplication.getAccreditationApplicationTypeEnum() != null)  {
				if (tpApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL || tpApplication.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL) {
					// Main Acc, Learneirng Programme Acc - ETQA Dec. Date + 5 years
					Date newStartDate = (tpApplication.getEtqaReviewCommitteeDate() != null ? GenericUtility.getStartOfDay(tpApplication.getEtqaReviewCommitteeDate()) : GenericUtility.getStartOfDay(getSynchronizedDate()));
					Date newExpiryDate = GenericUtility.getStartOfDay(GenericUtility.addYearsToDate(newStartDate, 5));
					tpApplication.setStartDate(newStartDate);
					tpApplication.setExpiriyDate(newExpiryDate);
				}
			}
		}


		public void rejectResubmission(SDPReAccreditation sdpReAccreditation,Tasks task, Users user,ArrayList<RejectReasons> selectedRejectReason, TrainingProviderApplication trainingProviderApplication) throws Exception {

			List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(sdpReAccreditation.getId(), sdpReAccreditation.getClass().getName(), selectedRejectReason, user, ConfigDocProcessEnum.SDP_RE_SUBMISSION);
			for (RejectReasonMultipleSelection rrm : rrmList) {
				if (rrm != null) {
					RejectReasonMultipleSelectionService.instance().create(rrm);
				}

			}
			sdpReAccreditation.setStatus(ApprovalEnum.Rejected);
			update(sdpReAccreditation);
			trainingProviderApplication.setFinalRejected(true);
			trainingProviderApplication.setFinalRejectDate(new Date());
			trainingProviderApplication.setApprovalStatus(ApprovalEnum.Rejected);
			trainingProviderApplication.setProviderStatus(ProviderStatusService.instance().findByCode("515"));
			TrainingProviderApplicationService.instance().update(trainingProviderApplication);
			//sendResubmissionFinalRejectEmail(sdpReAccreditation,trainingProviderApplication, selectedRejectReason);
			sendResubmissionFinalRejectEmail(sdpReAccreditation.getUsers(), selectedRejectReason, trainingProviderApplication);
			TasksService.instance().completeTask(task);

		}
		
		public List<Users> getQualityAssurorUsers(TrainingProviderApplication trainingProviderApplication) throws Exception
		{
			HostingCompanyEmployeesService hces = new HostingCompanyEmployeesService();
			TownService townService = new TownService();
			List<Users> qualityAssurorUsers = new ArrayList<>();
			Address address = null;
			if (trainingProviderApplication.getTrainingSite() != null && trainingProviderApplication.getTrainingSite().getId() != null && trainingProviderApplication.getTrainingSite().getResidentialAddress() != null && trainingProviderApplication.getTrainingSite().getResidentialAddress().getId() != null ) {
				address = AddressService.instance().findByKey(trainingProviderApplication.getTrainingSite().getResidentialAddress().getId());
			}else if (trainingProviderApplication.getCompany().getResidentialAddress().getId() != null) {
				address = AddressService.instance().findByKey(trainingProviderApplication.getCompany().getResidentialAddress().getId());
			}
			if (address != null && address.getTown() != null) {
				qualityAssurorUsers = hces.locateHostingCompanyEmployeesByRegionAndRole(townService.findByKey(address.getTown().getId()), RolesService.instance().findByKey(HAJConstants.QUALITY_ASSUROR_ROLE_ID));
			}
			
			if(qualityAssurorUsers==null || qualityAssurorUsers.size()<1)
			{
				throw new Exception("We are unable to locate CLO for this company ("+trainingProviderApplication.getCompany().getCompanyName()+"), Please contact support.");
			}
			
			return qualityAssurorUsers;
		}
		
		 public void sendResubmissionFinalRejectEmail_OLD(SDPReAccreditation sdpReAccreditation,TrainingProviderApplication trainingProviderApplication, ArrayList<RejectReasons> selectedRejectReason) {
			Users u=sdpReAccreditation.getUsers();
			 String appTitle=trainingProviderApplication.getAccreditationApplicationTypeEnum().getFriendlyName();
			 String title ="";
				String rejectReason = convertToHTML(selectedRejectReason);
				if(u.getTitle()!=null) {
					title = u.getTitle().getDescription();
				}
				String welcome = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
								+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
								+ "We regret to inform you that your "+appTitle+" resubmission Application has been rejected due to the following:"
								+ "</p>" 
								+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"+rejectReason+"</p>"
								+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
								+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>" 
								+ "<br/>";
				welcome = welcome.replaceAll("#Title#", title);
				welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
				welcome = welcome.replaceAll("#Surname#", u.getLastName());
				GenericUtility.sendMail(u.getEmail(), ""+appTitle.toUpperCase()+" RESUBMISSION APPLICATION OUTCOME", welcome, null);
			}
			public String convertToHTML(List<RejectReasons> rejectReasons){		
					String sb ="<ul  style=\"font-size:11.0pt;\";font-family:\"Arial\">"; 
					for (RejectReasons r: rejectReasons){
						sb +="<li>"+r.getDescription() +"</li>";
					}
					sb +="</ul>"; 
					return sb;
			}
			
			public void sendResubmissionAcknolowdgementEmail(Users formUser,TrainingProviderApplication tpApplication) throws Exception {
				
				String accreditationNumber="N/A";
				String sdpName="N/A";
				if(tpApplication.getCompany() !=null){
					sdpName=tpApplication.getCompany().getCompanyName();
				}
				if(tpApplication.getAccreditationNumber() !=null){
					accreditationNumber=tpApplication.getAccreditationNumber();
				}
				
				String subject = "ACKNOWLEDGEMENT OF APPLICATION TO RE-REGISTER AS A SKILLS DEVELOPMENT PROVIDER ON THE NSDMS";
				String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Your application dated "+HAJConstants.sdfDDMMYYYY2.format(new Date())+" to re-register "+sdpName+" ("+accreditationNumber+") "
						+ " as a "+getTitle(tpApplication.getAccreditationApplicationTypeEnum())+" ("+accreditationNumber+") "
						+ "on the merSETA NSDMS is hereby acknowledged."
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Your application will be evaluated by the merSETA and the "
						+ "process may take up to 7 working days. Should any additional "
						+ "information be required, this will be communicated to you."
						+ "</p>"

						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">ETQA  Administrator</p>";

				GenericUtility.sendMail(formUser.getEmail(), subject, mssg, null);

			}
			
			
			public void sendResubmissionFinalRejectEmail(Users u, List<RejectReasons> selectedRejectReason,TrainingProviderApplication trainingProviderApplication) {
				String title ="";
				String rejectReason = convertToHTML(selectedRejectReason);
				if(u.getTitle()!=null) {
					title = u.getTitle().getDescription();
				}
				String accreditationNumber="N/A";
				String sdpName="N/A";
				if(trainingProviderApplication.getCompany() !=null){
					sdpName=trainingProviderApplication.getCompany().getCompanyName();
				}
				if(trainingProviderApplication.getAccreditationNumber() !=null){
					accreditationNumber=trainingProviderApplication.getAccreditationNumber();
				}
				String welcome = "<br/>"
								+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #Title# #FirstName# #Surname#,</p>" 
								+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
								+ "We write to inform you that your application to "
								+ "re-register "+sdpName+" ("+accreditationNumber+") as a "+getTitle(trainingProviderApplication.getAccreditationApplicationTypeEnum())+" "
								+ "on the merSETA NSDMS has not been approved for the following reason(s):"
								+ "</p>" 
								+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"+rejectReason+"</p>"
								+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
								+ "For any assistance/clarity, please contact the Regional Office."
								+ "</p>" 
								+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
								+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
								+ "Senior Manager: Quality Assurance & Partnerships"
								+ "</p>" 
								+ "<br/>";
				welcome = welcome.replaceAll("#Title#", title);
				welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
				welcome = welcome.replaceAll("#Surname#", u.getLastName());
				GenericUtility.sendMail(u.getEmail(), "SKILLS DEVELOPMENT PROVIDER RE-REGISTRATION OUTCOME", welcome, null);
			}
			
			
			public void sendResubmissionApprovalEmail(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList,Users formUser,TrainingProviderApplication trainingProviderApplication, List<SkillsProgram> skillsProgramList,List<SkillsSet> skillsSetList) throws Exception {
				AttachmentBean beanAttachment=new AttachmentBean();
				ArrayList<AttachmentBean> attachmentBeans=new ArrayList<>();
				byte[] bites =TrainingProviderApplicationService.instance().createETQFM002AccreditationApprovalApplicationFormBytes(unitStandardsList, qualificationsList, trainingProviderApplication.getCompany(), trainingProviderApplication.getUsers(), trainingProviderApplication, skillsProgramList, skillsSetList,getTitle(trainingProviderApplication.getAccreditationApplicationTypeEnum()));
				beanAttachment=new AttachmentBean();
				beanAttachment.setExt("pdf");
				beanAttachment.setFile(bites);
				beanAttachment.setFilename("Accreditation_Approval_Application_Form.pdf");
				attachmentBeans.add(beanAttachment);
				String accrNumber="N/A";
				String sdpName="N/A";
				if(trainingProviderApplication.getCompany() !=null){
					sdpName=trainingProviderApplication.getCompany().getCompanyName();
				}
				if(trainingProviderApplication.getAccreditationNumber() !=null){
					accrNumber=trainingProviderApplication.getAccreditationNumber();
				}
				String subject = "SKILLS DEVELOPMENT PROVIDER RE-REGISTRATION OUTCOME";
				String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "We write to inform you that your application to re-register "
						+ ""+sdpName+" ("+accrNumber+") as a "+getTitle(trainingProviderApplication.getAccreditationApplicationTypeEnum())+" "
						+ "on the merSETA NSDMS has been approved for the following qualification(s):"
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ convertToHTML(unitStandardsList, qualificationsList, skillsProgramList, skillsSetList)
						+ "</p>"
						
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
						+ "Please note that if your accreditation was for an area of "
						+ "specialisation within a qualification, this accreditation is "
						+ "for that area of specialisation only. The provider is therefore "
						+ "required to deliver strictly according to the registered qualification."
						+ "</p>"

						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours sincerely,</p>" 
						+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Senior Manager: Quality Assurance & Partnerships</p>";
				GenericUtility.sendMailWithAttachementTempWithLog(trainingProviderApplication.getUsers().getEmail(), subject, mssg, attachmentBeans, null,trainingProviderApplication.getClass().getName(),trainingProviderApplication.getId());

			}
			
			public String convertToHTML(List<UnitStandards> unitStandardsList, List<Qualification> qualificationsList,List<SkillsProgram> skillsProgramList, List<SkillsSet> skillsSetList){		
				String sb ="<ul style=\"font-size:11.0pt;\";font-family:\"Arial\">"; 
				
				
				for(UnitStandards obj:unitStandardsList)
				{
					sb +="<li>"+"("+obj.getCode()+") "+obj.getTitle()+""+ "</li>";
				}
				
				for(Qualification obj:qualificationsList)
				{
					sb +="<li>"+"("+obj.getCode()+") "+obj.getDescription()+""+ "</li>";
				}
				
				for(SkillsProgram obj:skillsProgramList)
				{
					sb +="<li>"+"("+obj.getProgrammeID()+") "+obj.getDescription()+""+ "</li>";
				}
				
				for(SkillsSet obj:skillsSetList)
				{
					sb +="<li>"+"("+obj.getProgrammeID()+") "+obj.getDescription()+""+ "</li>";
				}
				sb +="</ul>"; 
				return sb;
			}
			
			
			
			public String getTitle(AccreditationApplicationTypeEnum appType)
			{
				String title="";
				
				if(appType==AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL)
				{
					title="Primary Accreditation";
				}
				else if(appType==AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL)
				{
					title="Learning Programme Approval";
				}
				else if(appType==AccreditationApplicationTypeEnum.QCTOSkillsDevelopmentProvider)
				{
					title="QCTO Skills Development Provider";
				}
				else if(appType==AccreditationApplicationTypeEnum.QCTOTradeTestCentre)
				{
					title="QCTO Trade Test Centre Skills Development Provider";
				}
				else if(appType==AccreditationApplicationTypeEnum.Non_MerSETA_Focused_Provider)
				{
					title="Non-merSETA Scope Skills Development Provider";
				}
				else 
				{
					title=appType.getFriendlyName();
				}
					
				
				return title;
			}
			
			
	
}
