package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.UserLearnership;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserSkillsProgramme;
import haj.com.entity.UserUnitStandard;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.framework.IDataEntity;
import haj.com.service.AssessorModeratorApplicationService;
import haj.com.service.UnitStandardsService;
import haj.com.service.UserLearnershipService;
import haj.com.service.UserQualificationsService;
import haj.com.service.UserSkillsProgrammeService;
import haj.com.service.UserUnitStandardService;
import haj.com.service.lookup.SkillsProgramService;

@ManagedBean(name = "monitorLegacyAssessorModUI")
@ViewScoped
public class MonitorLegacyAssessorModUI extends AbstractUI {

	/* Entity Level */
	private AssessorModeratorApplication assessorModeratorApplication = null;

	/* Service Level */
	private AssessorModeratorApplicationService service = new AssessorModeratorApplicationService();

	/* Lazy Data Models */
	private LazyDataModel<AssessorModeratorApplication> assessorApplicationsDataModel;
	private LazyDataModel<AssessorModeratorApplication> moderatorApplicationsDataModel;
	
	/* Booleans */
	private boolean filterByApprovedAssessorApplications = false;
	private boolean filterByApprovedModeratorApplications = false;
	
	/** The user qualifications. */
	private List<UserQualifications> userQualifications;
	
	/** The user qualifications service. */
	private UserQualificationsService userQualificationsService = new UserQualificationsService();

	/** The user unit standards. */
	private List<UserUnitStandard> userUnitStandards;
	private UnitStandardsService unitStandardsService = new UnitStandardsService();
	private UserSkillsProgrammeService userSkillsProgrammeService=new UserSkillsProgrammeService();
	private UserLearnershipService userLearnershipService=new UserLearnershipService();
	private List<UserSkillsProgramme> userSkillsProgrammeList;
	private List<UserLearnership> userLearnershipList;
	private SkillsProgramService skillsProgramService = new SkillsProgramService();

	/** The user unit standard service. */
	private UserUnitStandardService userUnitStandardService = new UserUnitStandardService();
	private UnitStandards unitStandards;
	private UserUnitStandard userUnitStandard;
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {
		allAssessorApplicationInfo();
		allModeratorApplicationInfo();
	}

	public void allAssessorApplicationInfo() {
		assessorApplicationsDataModel = new LazyDataModel<AssessorModeratorApplication>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<>();
			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (Boolean.TRUE.equals(filterByApprovedAssessorApplications)) {
						retorno = service.sortAndFilterWhereAMInfoApprovedAndByApplicationTypeLegacy(first, pageSize, sortField, sortOrder, filters, AssessorModeratorAppTypeEnum.getAssessorValues());
						assessorApplicationsDataModel.setRowCount(service.countWhereAMInfoApprovedAndByApplicationTypeLegacy(filters,AssessorModeratorAppTypeEnum.getAssessorValues()));
					} else {
						retorno = service.sortAndFilterWhereAMInfoApplicationTypeLegacy(first, pageSize, sortField, sortOrder, filters, AssessorModeratorAppTypeEnum.getAssessorValues());
						assessorApplicationsDataModel.setRowCount(service.countWhereAMInfoApplicationTypeLegacy(filters,AssessorModeratorAppTypeEnum.getAssessorValues()));
					}

				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(AssessorModeratorApplication obj) {
				return obj.getId();
			}
			@Override
			public AssessorModeratorApplication getRowData(String rowKey) {
				for (AssessorModeratorApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void allModeratorApplicationInfo() {
		moderatorApplicationsDataModel = new LazyDataModel<AssessorModeratorApplication>() {
			private static final long serialVersionUID = 1L;
			private List<AssessorModeratorApplication> retorno = new ArrayList<>();
			@Override
			public List<AssessorModeratorApplication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (Boolean.TRUE.equals(filterByApprovedModeratorApplications)) {
						retorno = service.sortAndFilterWhereAMInfoApprovedAndByApplicationTypeLegacy(first, pageSize, sortField, sortOrder, filters, AssessorModeratorAppTypeEnum.getModeratorValues() );
						moderatorApplicationsDataModel.setRowCount(service.countWhereAMInfoApprovedAndByApplicationTypeLegacy(filters, AssessorModeratorAppTypeEnum.getModeratorValues()));
					} else {
						retorno = service.sortAndFilterWhereAMInfoApplicationTypeLegacy(first, pageSize, sortField, sortOrder, filters, AssessorModeratorAppTypeEnum.getModeratorValues() );
						moderatorApplicationsDataModel.setRowCount(service.countWhereAMInfoApplicationTypeLegacy(filters,AssessorModeratorAppTypeEnum.getModeratorValues() ));
					}

				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(AssessorModeratorApplication obj) {
				return obj.getId();
			}
			@Override
			public AssessorModeratorApplication getRowData(String rowKey) {
				for (AssessorModeratorApplication obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void prepareQuallifications(){
		try {
			userQualifications = userQualificationsService.findByUserAMApplication(assessorModeratorApplication.getUser().getId(),assessorModeratorApplication.getId());
			userUnitStandards = userUnitStandardService.findByUserAndAPApplication(assessorModeratorApplication.getUser().getId(),assessorModeratorApplication.getId());
			userSkillsProgrammeList = userSkillsProgrammeService.findByUserAMApplication(assessorModeratorApplication.getUser().getId(),assessorModeratorApplication.getId());
			userLearnershipList = userLearnershipService.findByUserAMApplication(assessorModeratorApplication.getUser().getId(),assessorModeratorApplication.getId());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepareUsersUnitStandards(){
		try {
			userUnitStandards = userUnitStandardService.findByUserAndAPApplication(assessorModeratorApplication.getUser().getId(),assessorModeratorApplication.getId());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveQualificationSkillProg(UserQualifications uq)
	{
		try {
			if(userSkillsProgrammeList !=null && userSkillsProgrammeList.size()>0)
			{
				for(UserSkillsProgramme sp: userSkillsProgrammeList)
				{
					if(sp.getSkillsProgram() !=null && sp.getSkillsProgram().getQualification() !=null && uq.getQualification() !=null)
					{
						if(sp.getSkillsProgram().getQualification().equals(uq.getQualification()))
						{
							sp.setAccept(uq.getAccept());
						}
					}
				}
			}
			
			if(userUnitStandards !=null && userUnitStandards.size()>0)
			{
				for(UserUnitStandard us: userUnitStandards)
				{
					if(us.getForQualification()!=null)
					{
						if(us.getForQualification().equals(uq.getQualification()))
						{
							us.setAccept(uq.getAccept());
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void addUnitStandardsToList(UserQualifications userQualifications) throws Exception {
		try {
			if(userQualifications.getQualificationUpdated() != null && userQualifications.getQualificationUpdated()) {
				throw new Exception("Qualification already updated");
			}
			List<UserUnitStandard> list= new ArrayList<>();		
			List<UserSkillsProgramme>listUserSkillsProgramme = new ArrayList<>();	
			List<UnitStandards> usl  = unitStandardsService.findByQualification(userQualifications.getQualification());
			List<SkillsProgram> spl = skillsProgramService.findByQualification(userQualifications.getQualification());
			for (UnitStandards us : usl) {
				boolean exists = existInList(us, userUnitStandards);
				if(!exists) {
					UserUnitStandard userUnitStandard = new UserUnitStandard(); 
					userUnitStandard.setAccept(true);
					userUnitStandard.setUnitStandard(us);
					userUnitStandard.setForAssessorModeratorApplication(assessorModeratorApplication);
					userUnitStandard.setUser(assessorModeratorApplication.getUser());
					userUnitStandard.setTargetKey(assessorModeratorApplication.getId());
					userUnitStandard.setTargetClass(assessorModeratorApplication.getClass().getName());
					list.add(userUnitStandard);
				}
			}
			for(SkillsProgram sp:spl) {
				UserSkillsProgramme usp = new UserSkillsProgramme();
				usp.setAccept(true);
				usp.setSkillsProgram(sp);
				usp.setForAssessorModeratorApplication(assessorModeratorApplication);
				usp.setUser(assessorModeratorApplication.getUser());
				listUserSkillsProgramme.add(usp);
			}
			
			service.create((List<IDataEntity>) (List<?>) list);
			service.create((List<IDataEntity>) (List<?>) listUserSkillsProgramme);
			
			userQualifications.setQualificationUpdated(true);
			userQualifications.setAccept(true);
			userQualificationsService.update(userQualifications);
			
			prepareQuallifications();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addUnitStandardsToListOld(UserQualifications userQualifications) throws Exception {
		try {
			if(userQualifications.getAccept()) {
				if(userUnitStandards == null) {
					userUnitStandards= new ArrayList<>();
				}
				
				if (userQualifications.getQualification() != null && userQualifications.getQualification().getId() != null) {
					List<UnitStandards> usl = new ArrayList<>();
					usl = unitStandardsService.findByQualification(userQualifications.getQualification());
					for (UnitStandards us : usl) {
						boolean exists = existInList(us, userUnitStandards);
						if(!exists) {
							UserUnitStandard userUnitStandard = new UserUnitStandard(); 
							userUnitStandard.setAccept(true);
							userUnitStandard.setUnitStandard(us);
							userUnitStandard.setForAssessorModeratorApplication(assessorModeratorApplication);
							userUnitStandard.setUser(assessorModeratorApplication.getUser());
							userUnitStandards.add(userUnitStandard);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
		
	private boolean existInList(UnitStandards us, List<UserUnitStandard> userUnitStandards) {
		boolean exists = false;
		for(UserUnitStandard uu:userUnitStandards) {
			if(uu.getUnitStandard().getId() == us.getId()) {
				exists = true;
				break;
			}
		}
		return exists;
	}
	
	@SuppressWarnings("unchecked")
	public void updateQualifications(){
		try {
			service.update((List<IDataEntity>) (List<?>) userQualifications);
			service.update((List<IDataEntity>) (List<?>) userUnitStandards);
			service.update((List<IDataEntity>) (List<?>) userSkillsProgrammeList);
			service.update((List<IDataEntity>) (List<?>) userLearnershipList);
			prepareQuallifications();
			super.runClientSideExecute("uploadDone()");
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void updateUnitStandards(){
		try {
			if(assessorModeratorApplication == null) {
				throw new Exception("Assessor/Moderator Error");				
			}
			if(unitStandards == null) {
				throw new Exception("Unit Standard Error");
			}			
			userUnitStandardService.createAUserUnitStandard(assessorModeratorApplication, unitStandards);			
			super.runClientSideExecute("uploadDone()");
			addInfoMessage("Unit Standard Added");
		} catch (Exception e) {
			e.printStackTrace();
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeUnitStandards(){
		try {
			if(userUnitStandard==null) {
				throw new Exception("Unit Standard Error");
			}
			userUnitStandardService.delete(userUnitStandard);			
			super.runClientSideExecute("uploadDone()");
			addInfoMessage("Unit Standard deleted");
		} catch (Exception e) {
			e.printStackTrace();
			super.runClientSideExecute("uploadDone()");
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void reSendStatementOfQualToAllApprovedAssessorApplications(){
		try {
			service.resendStatmentOfQualificationToAllApprovedApplicationsByTypeList(AssessorModeratorAppTypeEnum.getAssessorValues());
			assessorModeratorApplication = null;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void reSendStatementOfQualToAllApprovedModeratorApplications(){
		try {
			service.resendStatmentOfQualificationToAllApprovedApplicationsByTypeList(AssessorModeratorAppTypeEnum.getModeratorValues());
			assessorModeratorApplication = null;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* getters and setters */
	public AssessorModeratorApplication getAssessorModeratorApplication() {
		return assessorModeratorApplication;
	}

	public void setAssessorModeratorApplication(AssessorModeratorApplication assessorModeratorApplication) {
		this.assessorModeratorApplication = assessorModeratorApplication;
	}
	
	public LazyDataModel<AssessorModeratorApplication> getModeratorApplicationsDataModel() {
		return moderatorApplicationsDataModel;
	}

	public void setModeratorApplicationsDataModel(
			LazyDataModel<AssessorModeratorApplication> moderatorApplicationsDataModel) {
		this.moderatorApplicationsDataModel = moderatorApplicationsDataModel;
	}

	public LazyDataModel<AssessorModeratorApplication> getAssessorApplicationsDataModel() {
		return assessorApplicationsDataModel;
	}

	public void setAssessorApplicationsDataModel(
			LazyDataModel<AssessorModeratorApplication> assessorApplicationsDataModel) {
		this.assessorApplicationsDataModel = assessorApplicationsDataModel;
	}

	public boolean isFilterByApprovedAssessorApplications() {
		return filterByApprovedAssessorApplications;
	}

	public void setFilterByApprovedAssessorApplications(boolean filterByApprovedAssessorApplications) {
		this.filterByApprovedAssessorApplications = filterByApprovedAssessorApplications;
	}

	public boolean isFilterByApprovedModeratorApplications() {
		return filterByApprovedModeratorApplications;
	}

	public void setFilterByApprovedModeratorApplications(boolean filterByApprovedModeratorApplications) {
		this.filterByApprovedModeratorApplications = filterByApprovedModeratorApplications;
	}

	public List<UserQualifications> getUserQualifications() {
		return userQualifications;
	}

	public List<UserUnitStandard> getUserUnitStandards() {
		return userUnitStandards;
	}

	public List<UserSkillsProgramme> getUserSkillsProgrammeList() {
		return userSkillsProgrammeList;
	}

	public List<UserLearnership> getUserLearnershipList() {
		return userLearnershipList;
	}

	public void setUserQualifications(List<UserQualifications> userQualifications) {
		this.userQualifications = userQualifications;
	}

	public void setUserUnitStandards(List<UserUnitStandard> userUnitStandards) {
		this.userUnitStandards = userUnitStandards;
	}

	public void setUserSkillsProgrammeList(List<UserSkillsProgramme> userSkillsProgrammeList) {
		this.userSkillsProgrammeList = userSkillsProgrammeList;
	}

	public void setUserLearnershipList(List<UserLearnership> userLearnershipList) {
		this.userLearnershipList = userLearnershipList;
	}

	public UnitStandards getUnitStandards() {
		return unitStandards;
	}

	public void setUnitStandards(UnitStandards unitStandards) {
		this.unitStandards = unitStandards;
	}

	public UserUnitStandard getUserUnitStandard() {
		return userUnitStandard;
	}

	public void setUserUnitStandard(UserUnitStandard userUnitStandard) {
		this.userUnitStandard = userUnitStandard;
	}
}