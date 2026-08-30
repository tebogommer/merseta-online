package haj.com.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.io.FilenameUtils;
import org.omnifaces.util.Faces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.ReviewCommitteeMeeting;
import haj.com.entity.SkillsRegistration;
import haj.com.entity.SkillsRegistrationQualificationUnitStandards;
import haj.com.entity.SkillsRegistrationUnitStandards;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.SkillsTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.ReviewCommitteeMeetingService;
import haj.com.service.SkillsRegistrationQualificationUnitStandardsService;
import haj.com.service.SkillsRegistrationService;
import haj.com.service.SkillsRegistrationUnitStandardsService;
import haj.com.service.TasksService;
import haj.com.service.UnitStandardsService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.service.lookup.SaqaUsQualificationService;

@ManagedBean(name = "skillsregistrationUI")
@ViewScoped
public class SkillsRegistrationUI extends AbstractUI {

	private SkillsRegistrationService service = new SkillsRegistrationService();
	private List<SkillsRegistration> skillsregistrationList = null;
	private List<SkillsRegistration> skillsregistrationfilteredList = null;
	private SkillsRegistration skillsregistration = null;
	private SkillsRegistration selectedSkillsregistration = null;
	private LazyDataModel<SkillsRegistration> dataModel;
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private List<Company> companies = null;
	private Company selectedCompany;
	private List<UnitStandards> unitStandards = new ArrayList<>();
	private UnitStandards unitStandard = null;
	private SkillsRegistrationUnitStandardsService registrationUnitStandardsService = new SkillsRegistrationUnitStandardsService();
	private SaqaUsQualificationService qualificationService=new SaqaUsQualificationService();
	private List<SkillsRegistrationQualificationUnitStandards> skillsRegistrationQualificationUnitStandardsList = null;
	private SkillsRegistrationQualificationUnitStandards skillsRegistrationQualificationUnitStandard;
	private SkillsRegistrationQualificationUnitStandardsService skillsRegistrationQualificationUnitStandardsService = new SkillsRegistrationQualificationUnitStandardsService();
	
	private ArrayList<UnitStandards> unitstandardList=new ArrayList<>();
	
	private ArrayList<UnitStandards> selectUnitstandardList=new ArrayList<>();	
	
	private ArrayList<RejectReasons> selectedRejectReason=new ArrayList<>();
	
	private boolean disableQualification;
	private boolean selected = false;
	
	private Qualification qualification;
	
	private List<Doc> registrationDocs;
	
	private Boolean showSkillsRegForm;
	

	/** The user doc check. */
	private Boolean userDocCheck;
	
	/** The doc. */
	private Doc doc;
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Initialize method to get all SkillsRegistration and prepare a for a create of
	 * a new SkillsRegistration
	 * 
	 * @author TechFinium
	 * @see SkillsRegistration
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		skillsInfo();
		if (super.getParameter("id", false) != null) {
		if (getSessionUI().getTask() != null && (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.SKILLS_SET_DEVELOPMENT || getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.SP_DEVELOPMENT)) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			skillsregistration = service.findByKey(getSessionUI().getTask().getTargetKey());
			
			if(skillsregistration.getSkillsType() == SkillsTypeEnum.SkillsProgram)			{
				this.unitStandards = registrationUnitStandardsService.allUnitStandards(skillsregistration);
				this.selectUnitstandardList = (ArrayList<UnitStandards>) unitStandards;
				this.unitstandardList=(ArrayList<UnitStandards>) qualificationService.findByQualification(skillsregistration.getQualification().getId());
				this.skillsRegistrationQualificationUnitStandard = new SkillsRegistrationQualificationUnitStandards();
			}else if(skillsregistration.getSkillsType() == SkillsTypeEnum.SkillsSet){
				this.skillsRegistrationQualificationUnitStandardsList = skillsRegistrationQualificationUnitStandardsService.allSkillsRegistrationQualificationUnitStandards(skillsregistration);
			}
			registrationDocs=service.prepareDocuments(skillsregistration.getClass().getName(), skillsregistration.getId(), getSessionUI().getTask().getWorkflowProcess(), CompanyUserTypeEnum.User);
			
		} else {
			prepareNew();
			skillsregistrationInfo();
		}
		selectedSkillsregistration = new SkillsRegistration();
		disableQualification=false;
		}else {
			populateDefaultLists();
		}
	}
	
	public List<SkillsRegistration>  userSkillsReg()
	{
		List<SkillsRegistration> list=null;
		try {
			list = service.findByUser(getSessionUI().getActiveUser());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	/**
	 * Populates lists for runInit()
	 * 
	 * @throws Exception
	 */
	private void populateDefaultLists() throws Exception {
		populateAssignedCompaniesTP();
	}

	/**
	 * Populates a list of companies the TP is assigned to
	 * 
	 * @throws Exception
	 */
	private void populateAssignedCompaniesTP() throws Exception {
		// change to lazy load
		companies = companyUsersService.findByUserType(getSessionUI().getActiveUser(), ConfigDocProcessEnum.TP);
		for(Company company : companies)
		{
			company.setSkillsRegistrations(service.findByCompany(company));
		}
			
		prepareNew();
	}
	
	public void skillsInfo() {
		
		 dataModel = new LazyDataModel<SkillsRegistration>() { 
		 
		   private static final long serialVersionUID = 1L; 
		   private List<SkillsRegistration> retorno = new  ArrayList<SkillsRegistration>();
		   
		   @Override 
		   public List<SkillsRegistration> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
		   
			try {
				filters.put("approvalEnum", ApprovalEnum.PendingCommitteeApproval);
				
				retorno = service.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters);
				for(SkillsRegistration skillsRegistration:retorno )
				{
					service.populateInformation(skillsRegistration);
					getShowActionButton(skillsRegistration);
				}
				dataModel.setRowCount(service.count(SkillsRegistration.class,filters));
			} catch (Exception e) {
				logger.fatal(e);
				e.printStackTrace();
			} 
		    return retorno; 
		   }
		   
		    @Override
		    public Object getRowKey(SkillsRegistration obj) {
		        return obj.getId();
		    }
		    
		    @Override
		    public SkillsRegistration getRowData(String rowKey) {
		        for(SkillsRegistration obj : retorno) {
		            if(obj.getId().equals(Long.valueOf(rowKey)))
		                return obj;
		        }
		        return null;
		    }			    
		    
		  }; 
		}

	
	public void createApprovalForReviewCommite()
	{
		try
		{
			
				ArrayList<SkillsRegistrationUnitStandards> list=new ArrayList<>();
				this.unitStandards = registrationUnitStandardsService.allUnitStandards(skillsregistration);
				for (UnitStandards unitStandard : unitStandards){
					list.add(new SkillsRegistrationUnitStandards(skillsregistration, unitStandard));
				}
				skillsregistration.setSkillsRegistrationUnitStandards(list);
				
				service.finalApproveRegistrationByReviewCommitee(skillsregistration, getSessionUI().getActiveUser(), getSessionUI().getTask());
				selectedSkillsregistration = null;
				selected  = false;
				prepareNew();
				addInfoMessage(super.getEntryLanguage("update.successful"));
				skillsregistrationInfo();
				skillsInfo() ;
				selectedSkillsregistration = null;
				selected = false;
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareSkillsReg()
	{
		showSkillsRegForm=true;
	}
	
	public void createSeniorManagerRejectionTask()
	{
		try
		{
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			service.createRejectionForReviewCommitee(ConfigDocProcessEnum.SKILLS_SET_DEVELOPMENT, skillsregistration, getSessionUI().getActiveUser(),  selectedRejectReason);
			selectedSkillsregistration = null;
			skillsInfo() ;
			selected  = false;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.SKILLS_SET_DEVELOPMENT);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return l;
	}
	
	public void getRegistrationInfo()
	{
		this.unitStandards=new ArrayList<UnitStandards>();
		this.skillsRegistrationQualificationUnitStandardsList=new ArrayList<SkillsRegistrationQualificationUnitStandards>();
		try {			
			selected =true;			
			if(selectedSkillsregistration.getSkillsType() == SkillsTypeEnum.SkillsProgram)			{
				this.unitStandards = registrationUnitStandardsService.allUnitStandards(selectedSkillsregistration);
			}else if(selectedSkillsregistration.getSkillsType() == SkillsTypeEnum.SkillsSet){
				this.skillsRegistrationQualificationUnitStandardsList = skillsRegistrationQualificationUnitStandardsService.allSkillsRegistrationQualificationUnitStandards(selectedSkillsregistration);			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * Get all SkillsRegistration for data table
	 * 
	 * @author TechFinium
	 * @see SkillsRegistration
	 */
	public void skillsregistrationInfo() {
		try {
			companies = companyUsersService.findByUserType(getSessionUI().getActiveUser(), ConfigDocProcessEnum.TP);
			for (Company company : companies) {
				company.setSkillsRegistrations(service.findByCompany(company));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void prepareUnitStandards()
	{		
		if(skillsregistration.getSkillsType() ==SkillsTypeEnum.SkillsProgram){			
			try {
				unitstandardList=(ArrayList<UnitStandards>) qualificationService.findByQualification(skillsregistration.getQualification().getId());
			} catch (Exception e) {
				e.printStackTrace();
				addErrorMessage(e.getMessage(), e);
			}
		}
		
		if(skillsregistration.getSkillsType() ==SkillsTypeEnum.SkillsSet)		{
			try {
				unitstandardList=(ArrayList<UnitStandards>) qualificationService.findByQualification(this.qualification.getId());
			} catch (Exception e) {
				e.printStackTrace();
				addErrorMessage(e.getMessage(), e);
			}
		}
		
	}

	public void onRowToggle(ToggleEvent event) {

	}
	
	public void buttonActionProg(ActionEvent actionEvent)
	{
		super.ajaxRedirect("/pages/reference/skillsprogram.jsf");
	}
	
	public void buttonActionSet(ActionEvent actionEvent)
	{
		super.ajaxRedirect("/pages/reference/skillsset.jsf");
	}
	
	/**
	 * Store file.
	 *
	 * @param event
	 *            the event
	 */
	// TODO Doc upload
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	

	/**
	 * Download.
	 */
	public void download(Doc doc) {
		try {
			Faces.sendFile(doc.getData(), doc.getOriginalFname(), true);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	//Old
	/*public void addUnitStandardToList() {
		if (unitStandard != null && unitStandard.getId() != null) {
			if (!unitStandards.contains(unitStandard)) {
				this.unitStandards.add(unitStandard);
			} else {
				addWarningMessage(unitStandard.getTitle() + " has already been selected");
			}
		}
		this.unitStandard = null;
	}*/
	
	public void addStikkSetUnitStandardToList() {
		if (unitStandard != null && unitStandard.getId() != null) {
			if (!unitStandards.contains(unitStandard)) {
				this.unitStandards.add(unitStandard);
			} else {
				addWarningMessage(unitStandard.getTitle() + " has already been selected");
			}
		}
		this.unitStandard = null;
	}
	
	public void clearQualificationInfo()
	{
		unitStandards.clear();
		skillsregistration.setQualification(null);
		selectUnitstandardList.clear();
		disableQualification=false;
		unitstandardList.clear();
		skillsRegistrationQualificationUnitStandardsList.clear();
		unitStandard=null;
		qualification=null;
	}
	
	/**Business rules:
	 * When selecting Unit Standards, 
	 * cannot be more than 120, so if 
	 * reaches 120, system to advise 
	 * user to remove some to reach 
	 * 120 unit standards
	 * @throws Exception */
	public void chechCredits(List<UnitStandards> usList,List<SkillsRegistrationQualificationUnitStandards> rrQualificationUnitStandardsList) throws Exception
	{
		int credits=0;
		if(unitStandard !=null) {
			
			if(usList !=null)
			{
				for(UnitStandards us:usList)
				{
					credits +=Integer.parseInt(us.getUnitstdnumberofcredits());
					if(credits>120)
					{
						throw new Exception("The total number of Unit Standards connot be more that 120, please remove some of Unit Standards");
					}
				}
			}
			
			if(rrQualificationUnitStandardsList !=null)
			{
				for(SkillsRegistrationQualificationUnitStandards QualUs:rrQualificationUnitStandardsList)
				{
					UnitStandards us=QualUs.getUnitStandards();
					credits +=Integer.parseInt(us.getUnitstdnumberofcredits());
					if(credits>120)
					{
						throw new Exception("The total number of Unit Standards connot be more that 120, please remove some of Unit Standards");
					}
				}
			}
			
			
			credits +=Integer.parseInt(unitStandard.getUnitstdnumberofcredits());
			if(credits>120)
			{
				throw new Exception("The total number of Unit Standards connot be more that 120, please remove some of Unit Standards");
			}
		}
		else
		{
			if(usList !=null)
			{
				for(UnitStandards us:usList)
				{
					credits +=Integer.parseInt(us.getUnitstdnumberofcredits());
					if(credits>120)
					{
						throw new Exception("The total number of Unit Standards connot be more that 120, please remove some of Unit Standards");
					}
				}
			}
			
			if(rrQualificationUnitStandardsList !=null)
			{
				for(SkillsRegistrationQualificationUnitStandards QualUs:rrQualificationUnitStandardsList)
				{
					UnitStandards us=QualUs.getUnitStandards();
					credits +=Integer.parseInt(us.getUnitstdnumberofcredits());
					if(credits>120)
					{
						throw new Exception("The total number of Unit Standards connot be more that 120, please remove some of Unit Standards");
					}
				}
			}
		}
		
	}
	
	public void updateUnitStandardToList() {
		try {
			if(selectUnitstandardList.size() == 0) {
				selectUnitstandardList=(ArrayList<UnitStandards>) unitStandards;
			}
			
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void addSkillsProgrameUnitStandardToList() {
		try {
			if(unitStandard ==null || unitStandard.getId() ==null){
				addWarningMessage("Please select unit standard");
			}
			else
			{
				chechCredits(unitStandards,null);
				if (!unitStandards.contains(unitStandard)) {
					this.unitStandards.add(unitStandard);
				} else {
					addWarningMessage(unitStandard.getTitle() + " has already been selected");
				}
				if(disableQualification==false){
					if(skillsregistration.getSkillsType()==SkillsTypeEnum.SkillsProgram){
						disableQualification=true;
					}
				}
				
				if(skillsRegistrationQualificationUnitStandardsList!=null) {
					skillsRegistrationQualificationUnitStandardsList.clear();
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
			
	}
	
	public void addUnitStandardToList() {
		try {
			chechCredits(unitStandards,null);
			for(UnitStandards us:selectUnitstandardList){
				if (!unitStandards.contains(us)) {
					this.unitStandards.add(us);
				} else {
					addWarningMessage(us.getTitle() + " has already been selected");
				}
				if(disableQualification==false){
					if(skillsregistration.getSkillsType()==SkillsTypeEnum.SkillsProgram){
						disableQualification=true;
					}
				}
			}
			selectUnitstandardList.clear();
			if(skillsRegistrationQualificationUnitStandardsList!=null) {
				skillsRegistrationQualificationUnitStandardsList.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
			
	}
	
	public void addSkillsRegistrationQualificationUnitStandardsToList() {	
		try {
			chechCredits(null,skillsRegistrationQualificationUnitStandardsList);
			if(this.qualification != null) {			
				if(this.unitStandard != null){
					if(skillsRegistrationQualificationUnitStandardsList.size()>0){	
						boolean addToList=true;
						for(SkillsRegistrationQualificationUnitStandards squs: skillsRegistrationQualificationUnitStandardsList){
							if(unitStandard.getId().equals(squs.getUnitStandards().getId())   && qualification.getId().equals(squs.getQualification().getId())){
								addToList=false;
								break;
							}else if(squs.getUnitStandards().equals(unitStandard)) {
								addToList=false;
								break;
							}else {
								skillsRegistrationQualificationUnitStandard = new SkillsRegistrationQualificationUnitStandards();
							}
						}
						
						if(addToList){
							skillsRegistrationQualificationUnitStandard.setUnitStandards(this.unitStandard);
							skillsRegistrationQualificationUnitStandard.setQualification(this.qualification);
							skillsRegistrationQualificationUnitStandardsList.add(this.skillsRegistrationQualificationUnitStandard);	
						}else {
							addWarningMessage(unitStandard.getTitle() + " has already been selected");
						}
					}else{
						skillsRegistrationQualificationUnitStandard.setUnitStandards(this.unitStandard);
						skillsRegistrationQualificationUnitStandard.setQualification(this.qualification);
						skillsRegistrationQualificationUnitStandardsList.add(this.skillsRegistrationQualificationUnitStandard);
					}
					this.skillsRegistrationQualificationUnitStandard = new SkillsRegistrationQualificationUnitStandards();
					this.unitStandard = null;
					skillsregistration.setQualification(null);
				}else {
					addWarningMessage("Please select unitStandard");
				}
			}else {
				addWarningMessage("Please select qualification");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}		
	}


	public void removeUnitStandardFromList() {
		this.unitStandards.remove(unitStandard);
		this.unitStandard = null;
	}
	public void removeSkillsRegistrationQualificationUnitStandardsFromList() {
		this.skillsRegistrationQualificationUnitStandardsList.remove(skillsRegistrationQualificationUnitStandard);
		this.unitStandard= null;
		this.skillsRegistrationQualificationUnitStandard = new SkillsRegistrationQualificationUnitStandards();
	}

	/**
	 * Insert SkillsRegistration into database
	 * 
	 * @author TechFinium
	 * @see SkillsRegistration
	 */
	public void skillsregistrationInsert() {
		try {
			service.create(this.skillsregistration);
			prepareNew();
			addInfoMessage("Your application has been sent for review");
			skillsregistrationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Update SkillsRegistration in database
	 * 
	 * @author TechFinium
	 * @see SkillsRegistration
	 */
	public void skillsregistrationUpdate() {
		try {
			service.update(this.skillsregistration);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			skillsregistrationInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Delete SkillsRegistration from database
	 * 
	 * @author TechFinium
	 * @see SkillsRegistration
	 */
	public void skillsregistrationDelete() {
		try {
			service.delete(this.skillsregistration);
			prepareNew();
			skillsregistrationInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Create new instance of SkillsRegistration
	 * 
	 * @author TechFinium
	 * @see SkillsRegistration
	 */
	public void prepareNew() {
		skillsregistration = new SkillsRegistration();
		unitStandards = new ArrayList<>();
		skillsRegistrationQualificationUnitStandard = new SkillsRegistrationQualificationUnitStandards();
		skillsRegistrationQualificationUnitStandardsList = new ArrayList<>();
		showSkillsRegForm=false;
		//skillsInfo();
	}

	public void submitRegistration() {
		try {
			
			checkUserDocProvided();
			if(userDocCheck)
			{
				if(skillsregistration.getSkillsType() == SkillsTypeEnum.SkillsProgram &&  skillsregistration.getQualification() !=null)
					{
					unitStandard=null;
					chechCredits(unitStandards,null);
					if(skillsregistration.getQualification().getLastDateForEnrolment() !=null)
					{
						skillsregistration.setLastDateForEnrolment(skillsregistration.getQualification().getLastDateForEnrolment());
					}
					skillsregistration.setCompany(selectedCompany);
					service.submitRegistration(skillsregistration, getSessionUI().getActiveUser(), unitStandards);
					prepareNew();
					addInfoMessage("Your application has been sent for review");
				}
				else if(skillsregistration.getSkillsType() == SkillsTypeEnum.SkillsSet && skillsRegistrationQualificationUnitStandardsList.size() >0)
				{	
					unitStandard=null;
					chechCredits(null,skillsRegistrationQualificationUnitStandardsList);
					skillsregistration.setCompany(selectedCompany);
					service.submitRegistrationForSkillsRegistrationQualificationUnitStandardsList(skillsregistration, getSessionUI().getActiveUser(), skillsRegistrationQualificationUnitStandardsList);
					prepareNew();
					addInfoMessage("Your application has been sent for review");
				}
				
				skillsregistrationInfo();
				clearQualificationInfo();
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Check user doc provided.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void checkUserDocProvided() throws Exception {
		userDocCheck = true;
		if (skillsregistration.getDocs() != null) {
			this.skillsregistration.getDocs().forEach(doc -> {
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					addErrorMessage("Please upload " + doc.getConfigDoc().getName());
					userDocCheck = false;
				}
			});
		}

	}
	
	public void preparRegistrationData()
	{
		try {
		
			clearQualificationInfo();
			doc=new Doc();
			prepareDocuments();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	
	public void prepareDocuments() throws Exception
	{
		skillsregistration.setDocs(null);
		if(skillsregistration.getSkillsType() == SkillsTypeEnum.SkillsSet){
			service.prepareRegistrationDocument(ConfigDocProcessEnum.SKILLS_SET_DEVELOPMENT, skillsregistration, getSessionUI().getActiveUser());
		}
		else{
			service.prepareRegistrationDocument(ConfigDocProcessEnum.SP_DEVELOPMENT, skillsregistration, getSessionUI().getActiveUser());
		}
	}
	
	

	public void resubmitRegistration() {
		try {
			if (skillsregistration.getSkillsType() != null) {
				if (skillsregistration.getSkillsType() == SkillsTypeEnum.SkillsProgram) { 
					service.resubmitRegistrationSkillsProgram(skillsregistration, getSessionUI().getActiveUser(), getSessionUI().getTask(), unitStandards);
				}
				else if (skillsregistration.getSkillsType() == SkillsTypeEnum.SkillsSet) {
					service.resubmitRegistration(skillsregistration, getSessionUI().getActiveUser(), getSessionUI().getTask(), skillsRegistrationQualificationUnitStandardsList);
				}
			}	
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			skillsregistrationInfo();
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void completeRegistration() {
		try {
			service.completeRegistration(skillsregistration, getSessionUI().getActiveUser(), getSessionUI().getTask());
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			skillsregistrationInfo();
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void approveRegistration() {
		try {
			service.approveRegistration(skillsregistration, getSessionUI().getActiveUser(), getSessionUI().getTask());
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			skillsregistrationInfo();
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void finalApproveRegistration() {
		try {
			
				service.finalApproveRegistration(skillsregistration, getSessionUI().getActiveUser(), getSessionUI().getTask());
	
				prepareNew();
				addInfoMessage(super.getEntryLanguage("update.successful"));
				skillsregistrationInfo();
			
				super.ajaxRedirectToDashboard();
			
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void rejectRegistration() {
		try {
			if (selectedRejectReason.size() == 0){
				throw new Exception("Please select a reject reason");				
			}
			service.rejectRegistration(skillsregistration, getSessionUI().getActiveUser(), getSessionUI().getTask(),  selectedRejectReason);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			skillsregistrationInfo();
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void rejectRegistrationBackToApplicant() {
		try {
			if (selectedRejectReason.size() == 0){
				throw new Exception("Please select a reject reason");				
			}
			service.rejectRegistrationBackToApplicant(skillsregistration, getSessionUI().getActiveUser(), getSessionUI().getTask(),  selectedRejectReason);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			skillsregistrationInfo();
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void finalRejectRegistration() {
		try {
			if (selectedRejectReason.size() == 0){
				throw new Exception("Please select a reject reason");				
			}
			service.finalRejectRegistration(skillsregistration, getSessionUI().getActiveUser(), getSessionUI().getTask(),  selectedRejectReason);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			skillsregistrationInfo();
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void scheduleRegistration() {
		try {
			service.scheduleRegistration(skillsregistration, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SkillsRegistration> complete(String desc) {
		List<SkillsRegistration> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return l;
	}

	public List<UnitStandards> completeUnitStandards(String desc) {
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		List<UnitStandards> l = null;
		try {
			if (skillsregistration.getSkillsType() != null) {
				if (skillsregistration.getSkillsType() == SkillsTypeEnum.SkillsSet) { 
					//l = unitStandardsService.findByTitle(desc, this.qualification.getCode());
					if(this.qualification != null) {
						l=(ArrayList<UnitStandards>) qualificationService.findByQualification(qualification.getId());
					}
					else {
						addErrorMessage("Please Select Qualification");
					}
				}
				else {
					if(skillsregistration.getQualification()!=null) {
						l = unitStandardsService.findByTitle(desc, skillsregistration.getQualification().getCode());
					}else {
						addErrorMessage("Please Select Qualification");
					}
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		return l;
	}

	public void getShowActionButton(SkillsRegistration skillsregistration) throws Exception
	{
		try {
			ReviewCommitteeMeeting reviewCommitteeMeeting = new ReviewCommitteeMeeting();
			ReviewCommitteeMeetingService s = new ReviewCommitteeMeetingService();
			if(skillsregistration !=null && skillsregistration.getReviewCommitteeMeeting()!=null) {
				reviewCommitteeMeeting = s.findByKey(skillsregistration.getReviewCommitteeMeeting().getId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");			
				String strDate1=sdf.format(skillsregistration.getReviewCommitteeMeeting().getFromDateTime());
				String strDate2=sdf.format(getNow());			
				Date meetingDate=sdf.parse(strDate1);
				Date currentDate=sdf.parse(strDate2);
				if (meetingDate.after(currentDate)) {
					skillsregistration.setShow(false);
					skillsregistration.setMeetingMessage("The meeting for this application is due to be held on "+ new SimpleDateFormat("dd MMMM yyyy HH:mm").format(reviewCommitteeMeeting.getFromDateTime()));
				}
				if (meetingDate.before(currentDate)) {
					skillsregistration.setShow(false);
					skillsregistration.setMeetingMessage("The meeting for this application was due to be held on "+ new SimpleDateFormat("dd MMMM yyyy HH:mm").format(reviewCommitteeMeeting.getFromDateTime()));			    
				}
				if (meetingDate.equals(currentDate)) {
					skillsregistration.setShow(true);
					skillsregistration.setMeetingMessage("The meeting for this application is to be held on "+ new SimpleDateFormat("dd MMMM yyyy HH:mm").format(reviewCommitteeMeeting.getFromDateTime()));
				}
			}else {
				skillsregistration.setMeetingMessage("This application has no meeting");
			}
		} catch (ParseException e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Store file.
	 *
	 * @param event
	 *            the event
	 */
	public void storeFileOnMemory(FileUploadEvent event) {
		doc.setData(event.getFile().getContents());
		doc.setOriginalFname(event.getFile().getFileName());
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		super.runClientSideExecute("PF('skillsUpload').hide()");
	}
	public List<SkillsRegistration> getSkillsRegistrationList() {
		return skillsregistrationList;
	}

	public void setSkillsRegistrationList(List<SkillsRegistration> skillsregistrationList) {
		this.skillsregistrationList = skillsregistrationList;
	}

	public SkillsRegistration getSkillsregistration() {
		return skillsregistration;
	}

	public void setSkillsregistration(SkillsRegistration skillsregistration) {
		this.skillsregistration = skillsregistration;
	}

	public List<SkillsRegistration> getSkillsRegistrationfilteredList() {
		return skillsregistrationfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param skillsregistrationfilteredList
	 *            the new skillsregistrationfilteredList list
	 * @see SkillsRegistration
	 */
	public void setSkillsRegistrationfilteredList(List<SkillsRegistration> skillsregistrationfilteredList) {
		this.skillsregistrationfilteredList = skillsregistrationfilteredList;
	}

	public LazyDataModel<SkillsRegistration> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SkillsRegistration> dataModel) {
		this.dataModel = dataModel;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public List<UnitStandards> getUnitStandards() {
		return unitStandards;
	}

	public void setUnitStandards(List<UnitStandards> unitStandards) {
		this.unitStandards = unitStandards;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

	public ArrayList<UnitStandards> getUnitstandardList() {
		return unitstandardList;
	}

	public void setUnitstandardList(ArrayList<UnitStandards> unitstandardList) {
		this.unitstandardList = unitstandardList;
	}

	public ArrayList<UnitStandards> getSelectUnitstandardList() {
		return selectUnitstandardList;
	}

	public void setSelectUnitstandardList(ArrayList<UnitStandards> selectUnitstandardList) {
		this.selectUnitstandardList = selectUnitstandardList;
	}

	public boolean isDisableQualification() {
		return disableQualification;
	}

	public void setDisableQualification(boolean disableQualification) {
		this.disableQualification = disableQualification;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public List<SkillsRegistrationQualificationUnitStandards> getSkillsRegistrationQualificationUnitStandardsList() {
		return skillsRegistrationQualificationUnitStandardsList;
	}

	public void setSkillsRegistrationQualificationUnitStandardsList(
			List<SkillsRegistrationQualificationUnitStandards> skillsRegistrationQualificationUnitStandardsList) {
		this.skillsRegistrationQualificationUnitStandardsList = skillsRegistrationQualificationUnitStandardsList;
	}
 
	public SkillsRegistrationQualificationUnitStandards getSkillsRegistrationQualificationUnitStandard() {
		return skillsRegistrationQualificationUnitStandard;
	}

	public void setSkillsRegistrationQualificationUnitStandard(
			SkillsRegistrationQualificationUnitStandards skillsRegistrationQualificationUnitStandard) {
		this.skillsRegistrationQualificationUnitStandard = skillsRegistrationQualificationUnitStandard;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public SkillsRegistration getSelectedSkillsregistration() {
		return selectedSkillsregistration;
	}

	public void setSelectedSkillsregistration(SkillsRegistration selectedSkillsregistration) {
		this.selectedSkillsregistration = selectedSkillsregistration;
	}

	public List<Doc> getRegistrationDocs() {
		return registrationDocs;
	}

	public void setRegistrationDocs(List<Doc> registrationDocs) {
		this.registrationDocs = registrationDocs;
	}

	public Boolean getShowSkillsRegForm() {
		return showSkillsRegForm;
	}

	public void setShowSkillsRegForm(Boolean showSkillsRegForm) {
		this.showSkillsRegForm = showSkillsRegForm;
	}

}
