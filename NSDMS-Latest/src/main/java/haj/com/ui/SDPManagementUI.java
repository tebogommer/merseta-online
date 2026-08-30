package  haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.AddressHistory;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.CompanyHistory;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.CompanyUsers;
import haj.com.entity.CompanyUsersHistory;
import haj.com.entity.Doc;
import haj.com.entity.SDPReAccreditation;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.UserChangeRequest;
import haj.com.entity.Users;
import haj.com.entity.WspSkillsGap;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressHistoryService;
import haj.com.service.ChangeReasonService;
import haj.com.service.CompanyHistoryService;
import haj.com.service.CompanyQualificationsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUnitStandardService;
import haj.com.service.CompanyUsersHistoryService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.SDPReAccreditationService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingProviderSkillsProgrammeService;
import haj.com.service.TrainingProviderSkillsSetService;
import haj.com.service.UserChangeRequestService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "sdpmanagementUI")
@ViewScoped
public class SDPManagementUI extends AbstractUI {

	//private List<Company> companies = null;
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	
	/** The company unit standard service. */
	private CompanyUnitStandardService companyUnitStandardService = new CompanyUnitStandardService();
	
	/** The company qualifications service. */
	private CompanyQualificationsService companyQualificationsService = new CompanyQualificationsService();
	private TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService=new TrainingProviderSkillsProgrammeService();
	private TrainingProviderSkillsSetService trainingProviderSkillsSetService=new TrainingProviderSkillsSetService();
	private LazyDataModel<TrainingProviderApplication> tpDataModel; 
	private TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
	private LazyDataModel<Doc> docHistDataModel; 
	private TrainingProviderApplication trainingProviderApplication;
	
	/**The Doc Seversce*/
	private DocService docService=new DocService();
	private ConfigDocProcessEnum workflowProcess;
	private Company company;
	private Company prevCompany=new Company();
	private CompanyHistory companyHistory=new CompanyHistory();
	private ChangeReason changeReason = new ChangeReason();
	private List<ChangeReason> changeReasonsList = new ArrayList<>();
	private CompanyService companyService=new CompanyService();
	private CompanyUsers companyUser;
	/** The Reject Reasons Child Service */
	private ArrayList<RejectReasons> selectedRejectReason;
	private UserChangeRequest userChangeRequest;
	private UserChangeRequestService userChangeRequestService=new UserChangeRequestService();
	private CompanyUsers companyUsersHistory=new CompanyUsers();
	private CompanyUsersHistoryService companyUsersHistoryService = new CompanyUsersHistoryService();
	private SDPReAccreditationService sdpReAccreditationService;
	
	private Doc doc;
	private List<Doc> previoursDoc = new ArrayList<>();
	private List<Doc> currentDoc = new ArrayList<>();
	private List<Doc> docVersions = new ArrayList<>();

	
    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all WspSkillsGap and prepare a for a create of a new WspSkillsGap
 	 * @author TechFinium 
 	 * @see    WspSkillsGap
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null) {
			workflowProcess = getSessionUI().getTask().getWorkflowProcess();
			switch (getSessionUI().getTask().getWorkflowProcess()) {
				case SDP_COMPANY_CHANGE_APPROVAL:
					trainingProviderApplication=trainingProviderApplicationService.findByKey(getSessionUI().getTask().getTargetKey());
					if(trainingProviderApplication !=null){
						CompanyService companyService=new CompanyService();
						trainingProviderApplication.setCompany(companyService.findByKey(trainingProviderApplication.getCompany().getId()));
					}
					
					this.company=trainingProviderApplication.getCompany();
					
					List<CompanyHistory> ch = CompanyHistoryService.instance().findByCompanyLatest(company);
					if (ch.size() > 0) {
						companyHistory = ch.get(0);
						Long tempCompID=company.getId();
						BeanUtils.copyProperties(prevCompany, companyHistory);
						prevCompany.setId(tempCompID);
						List<AddressHistory> resAddressHistoryList=AddressHistoryService.instance().findByForAddress(company.getResidentialAddress().getId());
						List<AddressHistory> postAddressHistoryList=AddressHistoryService.instance().findByForAddress(company.getPostalAddress().getId());
						if(resAddressHistoryList !=null && resAddressHistoryList.size()>0){
							Address res=new Address();
							Long resAdreessID=company.getResidentialAddress().getId();
							BeanUtils.copyProperties(res, resAddressHistoryList.get(0));
							res.setId(resAdreessID);
							prevCompany.setResidentialAddress(res);
						}
						if(postAddressHistoryList !=null && postAddressHistoryList.size()>0){
							Address post=new Address();
							Long postAdreessID=company.getPostalAddress().getId();
							BeanUtils.copyProperties(post, postAddressHistoryList.get(0));
							post.setId(postAdreessID);
							prevCompany.setPostalAddress(post);
						}
						
					}
					
				break;
			case NEW_SDP_CONTACT_PERSON:
				companyUser=companyUsersService.findByKey(getSessionUI().getTask().getTargetKey());
				trainingProviderApplication=trainingProviderApplicationService.findByKey(companyUser.getTargetKey());
				if(trainingProviderApplication !=null){
					CompanyService companyService=new CompanyService();
					trainingProviderApplication.setCompany(companyService.findByKey(trainingProviderApplication.getCompany().getId()));
				}
				break;
			case REMOVE_SDP_CONTACT_PERSON:
				companyUser=companyUsersService.findByKey(getSessionUI().getTask().getTargetKey());
				trainingProviderApplication=trainingProviderApplicationService.findByKey(companyUser.getTargetKey());
				if(trainingProviderApplication !=null){
					CompanyService companyService=new CompanyService();
					trainingProviderApplication.setCompany(companyService.findByKey(trainingProviderApplication.getCompany().getId()));
				}
				break;
			case UPDATE_SDP_CONTACT_PERSON:
				companyUser=companyUsersService.findByKey(getSessionUI().getTask().getTargetKey());
				trainingProviderApplication=trainingProviderApplicationService.findByKey(companyUser.getTargetKey());
			    userChangeRequest=userChangeRequestService.findLatestByTargetKeyAndTargetClass(companyUser.getClass().getName(), companyUser.getId());
			    CompanyUsersHistory cuHist = companyUsersHistoryService.findAllByForID(companyUser.getId()).get(0);
			    BeanUtils.copyProperties(companyUsersHistory, cuHist);
			    Users histUser=new Users();
			    BeanUtils.copyProperties(histUser, userChangeRequest);
			    histUser.setId(companyUser.getUser().getId());
			    companyUsersHistory.setUser(histUser);
				if(trainingProviderApplication !=null){
					CompanyService companyService=new CompanyService();
					trainingProviderApplication.setCompany(companyService.findByKey(trainingProviderApplication.getCompany().getId()));
				}
				break;
			case NEW_SDP_ASSESSOR_MODERATOR:
				companyUser=companyUsersService.findByKey(getSessionUI().getTask().getTargetKey());
				trainingProviderApplication=trainingProviderApplicationService.findByKey(companyUser.getTargetKey());
				if(trainingProviderApplication !=null){
					CompanyService companyService=new CompanyService();
					trainingProviderApplication.setCompany(companyService.findByKey(trainingProviderApplication.getCompany().getId()));
				}
				break;
			case REMOVE_SDP_ASSESSOR_MODERATOR:
				companyUser=companyUsersService.findByKey(getSessionUI().getTask().getTargetKey());
				trainingProviderApplication=trainingProviderApplicationService.findByKey(companyUser.getTargetKey());
				if(trainingProviderApplication !=null){
					CompanyService companyService=new CompanyService();
					trainingProviderApplication.setCompany(companyService.findByKey(trainingProviderApplication.getCompany().getId()));
				}
				break;
			case SDP_DOC_CHANGE:
				doc = DocService.instance().findByKeyWithJoins(getSessionUI().getTask().getTargetKey());
				docVersions = DocService.instance().findByVersionNo(doc);
				trainingProviderApplication=trainingProviderApplicationService.findByKey(doc.getTargetKey());
				if(trainingProviderApplication !=null){
					CompanyService companyService=new CompanyService();
					trainingProviderApplication.setCompany(companyService.findByKey(trainingProviderApplication.getCompany().getId()));
				}
				if (docVersions.size() > 0) {
					if (docVersions.get(0).getVersionNo() == 2) {
						// Finding the original doc
						previoursDoc.add(DocService.instance().findByKeyWithJoins(docVersions.get(0).getDoc().getId()));
					} else {
						// Adding only the latest version
						if (docVersions.size() > 1) {
							previoursDoc.add(docVersions.get(1));
						} else {
							previoursDoc.add(docVersions.get(0));
						}

					}

				}

				currentDoc.add(doc);

				break;
			default:
				break;
			}
			
			findChangeReason();
		}
		else
		{
			loadUserTPInfo();
		}
		
	}
	
	

	/*public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}*/
	
	public void approveCompanyChangeTask() {
		try {
			trainingProviderApplicationService.approveCompanyChangeTask(company, getSessionUI().getTask(), getSessionUI().getActiveUser(),trainingProviderApplication.getUsers(),trainingProviderApplication);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	
	public void approveNewContactPersonTask() {
		try {
			trainingProviderApplicationService.approveNewContactPersonTask(companyUser, getSessionUI().getTask(), getSessionUI().getActiveUser(),trainingProviderApplication);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveNewAssessoModTask() {
		try {
			trainingProviderApplicationService.approveNewAssessoModTask(companyUser, getSessionUI().getTask(), getSessionUI().getActiveUser(),trainingProviderApplication);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectNewContactPersonTask() {
		try {
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			trainingProviderApplicationService.rejectNewContactTask(companyUser, getSessionUI().getTask(), getSessionUI().getActiveUser(),trainingProviderApplication,selectedRejectReason);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('finalRejectReason').show()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectNewAssessorModTask() {
		try {
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			trainingProviderApplicationService.rejectNewAssessorModTask(companyUser, getSessionUI().getTask(), getSessionUI().getActiveUser(),trainingProviderApplication,selectedRejectReason);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('finalRejectReason').show()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveDeleteContactPersonTask() {
		try {
			trainingProviderApplicationService.approveDeleteContactPersonTask(companyUser, getSessionUI().getTask(), getSessionUI().getActiveUser(),trainingProviderApplication);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveDeleteAssessorModTask() {
		try {
			trainingProviderApplicationService.approveDeleteAssessorModTask(companyUser,  getSessionUI().getTask(), getSessionUI().getActiveUser(), trainingProviderApplication);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectDeleteContactPersonTask() {
		try {
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			trainingProviderApplicationService.rejectDeleteContactPersonTask(companyUser, getSessionUI().getTask(), getSessionUI().getActiveUser(),trainingProviderApplication,selectedRejectReason);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('finalRejectReason').show()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectDeleteAssessorModTask() {
		try {
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			trainingProviderApplicationService.rejectDeleteAssessorModTask(companyUser, getSessionUI().getTask(), getSessionUI().getActiveUser(),trainingProviderApplication,selectedRejectReason);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('finalRejectReason').show()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveUpdateContactPersonTask() {
		try {
			trainingProviderApplicationService.approveUpdateContactPersonTask(companyUser, getSessionUI().getTask(), getSessionUI().getActiveUser(),trainingProviderApplication,userChangeRequest);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectUpdateContactPersonTask() {
		try {
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			trainingProviderApplicationService.rejectUpdateContactPersonTask(companyUser, getSessionUI().getTask(), getSessionUI().getActiveUser(),trainingProviderApplication,selectedRejectReason,userChangeRequest,companyUsersHistory);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
			super.runClientSideExecute("PF('finalRejectReason').show()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	public void rejectCompanyChangeRequest() {
		try {
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			trainingProviderApplicationService.rejectCompanyChangeRequest(company, getSessionUI().getTask(), getSessionUI().getActiveUser(),trainingProviderApplication.getUsers(),selectedRejectReason,prevCompany,trainingProviderApplication);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveDocumentTask() {
		try {

			DocService.instance().approveSDPDocument(doc, getSessionUI().getTask(),trainingProviderApplication);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			 e.printStackTrace();
			 super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectDocumentTask() {
		try {
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			DocService.instance().rejectSDPDocumentChange(doc, getSessionUI().getTask(), selectedRejectReason, trainingProviderApplication, getSessionUI().getActiveUser());
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	public void findChangeReason() throws Exception {
		changeReasonsList = (List<ChangeReason>) ChangeReasonService.instance().findByTargetClassAndTargetKey(getSessionUI().getTask().getTargetClass(), getSessionUI().getTask().getTargetKey());
		if (changeReasonsList.size() > 0) {
			changeReason = changeReasonsList.get(0);
		}
	}
	
	public ArrayList<CompanyUnitStandard> companyUnitStandard(TrainingProviderApplication tpApp)
	{
		
		ArrayList<CompanyUnitStandard> l=new ArrayList<>();
		try {
			l= (ArrayList<CompanyUnitStandard>) companyUnitStandardService.findByTargetClassAndTargetKey(tpApp.getClass().getName(), tpApp.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public ArrayList<TrainingProviderSkillsProgramme> trainingProviderSkillsProgrammes(TrainingProviderApplication tpApp)
	{
		ArrayList<TrainingProviderSkillsProgramme> l=new ArrayList<>();
		try {
			l=(ArrayList<TrainingProviderSkillsProgramme>) trainingProviderSkillsProgrammeService.findByTrainingProvider(tpApp.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public ArrayList<TrainingProviderSkillsSet> trainingProviderSkillsSet(TrainingProviderApplication tpApp)
	{
		ArrayList<TrainingProviderSkillsSet> l=new ArrayList<>();
		try {
			
			l=(ArrayList<TrainingProviderSkillsSet>) trainingProviderSkillsSetService.findByTrainingProvider(tpApp.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	
	public  ArrayList<CompanyQualifications> companyQualifications(TrainingProviderApplication tpApp)
	{
		 ArrayList<CompanyQualifications> l=new ArrayList<>();
		try {
			 l= (ArrayList<CompanyQualifications>) companyQualificationsService.findByTargetClassAndTargetKey(tpApp.getClass().getName(), tpApp.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		
		return l;
	}
	
	public List<Doc> loadDocs(TrainingProviderApplication tpApp)
	{
		List<Doc>  list=null;
	    try {
			DocService docService = new DocService();
		    list=docService.searchByClassAndKeyConfigDocNotNull(tpApp.getId(),tpApp.getClass().getName());
		    return list;
		} catch (Exception e) {
		    list=new ArrayList<>();
			e.printStackTrace();
			return  list;
		}
	    
	  
	}
	
	
	/**
	 * Get all TrainingProviderApplication for data table
 	 * @author TechFinium 
 	 * @see    TrainingProviderApplication
 	 */
	public void loadUserTPInfo() {
		     tpDataModel = new LazyDataModel<TrainingProviderApplication>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<TrainingProviderApplication> retorno = new  ArrayList<TrainingProviderApplication>();
			   
			   @Override 
			   public List<TrainingProviderApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
//					filters.put("userID",getSessionUI().getActiveUser().getId());
//					retorno = trainingProviderApplicationService.findUserTP(first, pageSize, sortField, sortOrder, filters);
//					tpDataModel.setRowCount(trainingProviderApplicationService.countUuserTP(filters));
					// version two
//					retorno = trainingProviderApplicationService.allTrainingProviderApplicationByCompanyUser(first, pageSize, sortField, sortOrder, filters,getSessionUI().getActiveUser().getId());
//					tpDataModel.setRowCount(trainingProviderApplicationService.countAllTrainingProviderApplicationByCompanyUser(filters));
					// version three
//					retorno = trainingProviderApplicationService.allTrainingProviderApplicationByCompanyUserAndSdpDesignation(first, pageSize, sortField, sortOrder, filters,getSessionUI().getActiveUser().getId(), true);
//					tpDataModel.setRowCount(trainingProviderApplicationService.countAllTrainingProviderApplicationByCompanyUserAndSdpDesignation(filters));
					// version four
					retorno = trainingProviderApplicationService.allTrainingProviderApplicationByUserLinkedAsSdp(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser().getId());
					tpDataModel.setRowCount(trainingProviderApplicationService.countAllTrainingProviderApplicationByUserLinkedAsSdp(filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(TrainingProviderApplication obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public TrainingProviderApplication getRowData(String rowKey) {
			        for(TrainingProviderApplication obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}
	
	public boolean showReSumissionRequest(TrainingProviderApplication tp) {
		boolean show=false;
		if (tp != null && tp.getId() != null) {
			if(tp.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.Non_MerSETA_Focused_Provider ||  tp.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.QCTOSkillsDevelopmentProvider || tp.getAccreditationApplicationTypeEnum() == AccreditationApplicationTypeEnum.QCTOTradeTestCentre) {
				if(tp.getApprovalStatus() == ApprovalEnum.Approved || tp.getApprovalStatus() == ApprovalEnum.DeAccredited) {
					
					// for test sites
//					if (!HAJConstants.DEV_TEST_PROD.equals("P")) {
//						return true;
//					}
					
					if (sdpReAccreditationService == null) {
						sdpReAccreditationService = new SDPReAccreditationService();
					}
					
					// check if application linked to an open one
					try {
						int openResubmissions = sdpReAccreditationService.countOpenSDPReAccreditationByProviderApplicationId(tp.getId(), ApprovalEnum.getOpenStatusForSdpReAccrediciation());
						if (openResubmissions < 0) {
							return false;
						}
					} catch (Exception e) {
						logger.fatal(e);
						return false;
					}
					
					if(tp.getExpiriyDate() !=null) {
						if (new Date().after(tp.getExpiriyDate())) {
							return true;
						} else {
							int months = GenericUtility.getMonthsBetweenDates(new Date(), tp.getExpiriyDate());
							if (months <= 6 ) {
								return true;
							}	
						}
					}
				}
			}
		}
		
		return show;
	}
	
	public boolean showExtensionRequest(TrainingProviderApplication tp){
		boolean show=false;
		if(tp.getAccreditationApplicationTypeEnum() ==AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL || 
		tp.getAccreditationApplicationTypeEnum()==AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL){
			//show=true;
			//JUST FOR TESTING,TO BE REMOVED
			if(tp.getApprovalStatus() ==ApprovalEnum.Approved){
				if(tp.getExpiriyDate() !=null){
					int months=GenericUtility.getMonthsBetweenDates(new Date(), tp.getExpiriyDate());
					if(months<=6){
						show=true;
					}
				}
			}else if(tp.getApprovalStatus() ==ApprovalEnum.DeAccredited) {
				show=true;
			}
		}
		
		return show;
	}
	
	public boolean showExtensionOfScope(TrainingProviderApplication tp)
	{
		boolean show=true;
		if(tp.getAccreditationApplicationTypeEnum() !=AccreditationApplicationTypeEnum.ACCREDITATIONAPPROVAL && 
		tp.getAccreditationApplicationTypeEnum()!=AccreditationApplicationTypeEnum.NON_PRIMARY_FOCUS_ACCREDITATION_APPROVAL)
		{
			show=false;
		}
		else
		{
			
			if(tp.getExpiriyDate() !=null)
			{
				int months=GenericUtility.getMonthsBetweenDates(new Date(), tp.getExpiriyDate());
				if(months<1)
				{
					show=false;
				}
			}
		    if(tp.getApprovalStatus() !=ApprovalEnum.Approved || tp.getAccreditationApplicationTypeEnum()==null)
			{
				show=false;
			}
		    //show=true;//JUST FOR TESTING,TO BE REMOVED
		}
		return show;
	}
	
	public boolean showAddQualification(TrainingProviderApplication tp)
	{
		boolean show=false;
		if(tp.getAccreditationApplicationTypeEnum() ==AccreditationApplicationTypeEnum.QCTOSkillsDevelopmentProvider || 
		tp.getAccreditationApplicationTypeEnum() ==AccreditationApplicationTypeEnum.QCTOTradeTestCentre ||
		tp.getAccreditationApplicationTypeEnum() ==AccreditationApplicationTypeEnum.Non_MerSETA_Focused_Provider)
		{
			show=true;
			if(tp.getExpiriyDate() !=null)
			{
				int months=GenericUtility.getMonthsBetweenDates(new Date(), tp.getExpiriyDate());
				if(months<1)
				{
					show=false;
				}
			}
		    if(tp.getApprovalStatus() !=ApprovalEnum.Approved)
			{
				show=false;
			}
		}
		
		return show;
	}
	
	public void documentHistoryInfo() {
		
		docHistDataModel = new LazyDataModel<Doc>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<Doc> retorno = new  ArrayList<Doc>();
			   
			   @Override 
			   public List<Doc> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					filters.put("targetClass", trainingProviderApplication.getClass().getName());
					filters.put("targetKey", trainingProviderApplication.getId());
					retorno = docService.allDocAndResolve(first, pageSize, sortField, sortOrder, filters);
					docHistDataModel.setRowCount(docService.countWhere(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(Doc obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public Doc getRowData(String rowKey) {
			        for(Doc obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}
	
	public List<RejectReasons> getNewSdpContactRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.NEW_SDP_CONTACT_PERSON);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<RejectReasons> getCompanyChangesRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.SDP_COMPANY_CHANGE_APPROVAL);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<RejectReasons> getUpdateSdpContactRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.UPDATE_SDP_CONTACT_PERSON);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	
	public List<RejectReasons> getDeleteSdpContactRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.REMOVE_SDP_CONTACT_PERSON);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<RejectReasons> getDeleteSdpAssessorModRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.REMOVE_SDP_ASSESSOR_MODERATOR);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<RejectReasons> getDocumentChangeRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.SDP_DOC_CHANGE);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public List<RejectReasons> getNewAssessorModRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.NEW_SDP_ASSESSOR_MODERATOR);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public LazyDataModel<TrainingProviderApplication> getTpDataModel() {
		return tpDataModel;
	}

	public void setTpDataModel(LazyDataModel<TrainingProviderApplication> tpDataModel) {
		this.tpDataModel = tpDataModel;
	}

	public LazyDataModel<Doc> getDocHistDataModel() {
		return docHistDataModel;
	}

	public void setDocHistDataModel(LazyDataModel<Doc> docHistDataModel) {
		this.docHistDataModel = docHistDataModel;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public CompanyHistory getCompanyHistory() {
		return companyHistory;
	}

	public void setCompanyHistory(CompanyHistory companyHistory) {
		this.companyHistory = companyHistory;
	}

	public ConfigDocProcessEnum getWorkflowProcess() {
		return workflowProcess;
	}

	public void setWorkflowProcess(ConfigDocProcessEnum workflowProcess) {
		this.workflowProcess = workflowProcess;
	}

	public ChangeReason getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(ChangeReason changeReason) {
		this.changeReason = changeReason;
	}

	public List<ChangeReason> getChangeReasonsList() {
		return changeReasonsList;
	}

	public void setChangeReasonsList(List<ChangeReason> changeReasonsList) {
		this.changeReasonsList = changeReasonsList;
	}

	public Company getPrevCompany() {
		return prevCompany;
	}

	public void setPrevCompany(Company prevCompany) {
		this.prevCompany = prevCompany;
	}

	public CompanyUsers getCompanyUser() {
		return companyUser;
	}

	public void setCompanyUser(CompanyUsers companyUser) {
		this.companyUser = companyUser;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public UserChangeRequest getUserChangeRequest() {
		return userChangeRequest;
	}

	public void setUserChangeRequest(UserChangeRequest userChangeRequest) {
		this.userChangeRequest = userChangeRequest;
	}

	public CompanyUsers getCompanyUsersHistory() {
		return companyUsersHistory;
	}

	public void setCompanyUsersHistory(CompanyUsers companyUsersHistory) {
		this.companyUsersHistory = companyUsersHistory;
	}

	public DocService getDocService() {
		return docService;
	}

	public void setDocService(DocService docService) {
		this.docService = docService;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public List<Doc> getPrevioursDoc() {
		return previoursDoc;
	}

	public void setPrevioursDoc(List<Doc> previoursDoc) {
		this.previoursDoc = previoursDoc;
	}

	public List<Doc> getCurrentDoc() {
		return currentDoc;
	}

	public void setCurrentDoc(List<Doc> currentDoc) {
		this.currentDoc = currentDoc;
	}

	public List<Doc> getDocVersions() {
		return docVersions;
	}

	public void setDocVersions(List<Doc> docVersions) {
		this.docVersions = docVersions;
	}

	

	
	
}
