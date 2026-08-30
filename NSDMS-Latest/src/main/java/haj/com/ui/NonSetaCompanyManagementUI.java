package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.CompanyQualifications;
import haj.com.entity.CompanyUnitStandard;
import haj.com.entity.NonSetaCompany;
import haj.com.entity.NonSetaCompanyUsers;
import haj.com.entity.TrainingProviderApplication;
import haj.com.entity.TrainingProviderSkillsProgramme;
import haj.com.entity.TrainingProviderSkillsSet;
import haj.com.entity.enums.CompanyTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyQualificationsService;
import haj.com.service.CompanyUnitStandardService;
import haj.com.service.NonSetaCompanyService;
import haj.com.service.NonSetaCompanyUsersService;
import haj.com.service.TrainingProviderApplicationService;
import haj.com.service.TrainingProviderSkillsProgrammeService;
import haj.com.service.TrainingProviderSkillsSetService;
import haj.com.service.UnitStandardsService;
import haj.com.service.lookup.QualificationService;
import haj.com.service.lookup.SkillsProgramService;
import haj.com.service.lookup.SkillsSetService;

/**
 * The Class NonSetaCompanyManagementUI.
 */
@ManagedBean(name = "nonSetaCompanyManagementUI")
@ViewScoped
public class NonSetaCompanyManagementUI extends AbstractUI {

	/* Entity Level */
	private NonSetaCompany nonSetaCompany;
	private NonSetaCompanyUsers nonSetaCompanyUsers;
	private TrainingProviderApplication trainingProviderApplication;

	/* Service Levels */
	private NonSetaCompanyService nonSetaCompanyService;
	private NonSetaCompanyUsersService nonSetaCompanyUsersService;
	private TrainingProviderApplicationService trainingProviderApplicationService;
	private QualificationService qualificationService;
	private CompanyQualificationsService companyQualificationsService;
	private UnitStandardsService unitStandardsService;
	private CompanyUnitStandardService companyUnitStandardService;
	private SkillsProgramService skillsProgramService;
	private TrainingProviderSkillsProgrammeService trainingProviderSkillsProgrammeService;
	private SkillsSetService skillsSetService;
	private TrainingProviderSkillsSetService trainingProviderSkillsSetSevice;
	
	/* Array Lists  */
	private List<NonSetaCompanyUsers> nonSetaCompanyUsersContactPersons;
	private List<NonSetaCompanyUsers> nonSetaCompanyUsersAssesModerators;
	private List<CompanyQualifications> companyQualificationsList;
	private List<CompanyUnitStandard> companyUnitStandardList;
	private List<TrainingProviderSkillsProgramme> trainingProviderSkillsProgrammeList;
	private List<TrainingProviderSkillsSet>  trainingProviderSkillsSetList;
	
	/* Data Model Lists */
	private LazyDataModel<NonSetaCompany> dataModelNonSetaCompany;
	private LazyDataModel<NonSetaCompanyUsers> dataModelNonSetaCompanyUsers;
	private LazyDataModel<Qualification> dataModelNonSetaCompanyQualifications;
	private LazyDataModel<UnitStandards> dataModelNonSetaCompanyUnitStandards;
	private LazyDataModel<SkillsProgram> dataModelNonSetaCompanySkillsProgram;
	private LazyDataModel<SkillsSet> dataModelNonSetaCompanySkillsSet;
	
	/* Vars */
	private int index;
	private int contactPersonAssigned;
	private int assesModAssigned;
	
	/* linked company user */
	private boolean linkedUser = false;

	/**
	 * Inits the.
	 */
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

	/**
	 * Initialize method to get all NonSetaCompany by user assigned
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see NonSetaCompany
	 * @see NonSetaCompanyUsers
	 */
	private void runInit() throws Exception {
		populateServiceLevels();
//		userValidiationCheck();
		nonSetaCompanyInfo();
	}
	
	/**
	 * populates service levels required
	 * 
	 * @throws Exception
	 */
	private void populateServiceLevels() throws Exception {
		if (nonSetaCompanyService == null) {
			nonSetaCompanyService = new NonSetaCompanyService();
		}
		if (nonSetaCompanyUsersService == null) {
			nonSetaCompanyUsersService = new NonSetaCompanyUsersService();
		}
		if (trainingProviderApplicationService == null) {
			trainingProviderApplicationService = new TrainingProviderApplicationService();
		}
		if (qualificationService == null) {
			qualificationService = new QualificationService();
		}
		if (companyQualificationsService == null) {
			companyQualificationsService = new CompanyQualificationsService();
		}
		if (companyUnitStandardService == null) {
			companyUnitStandardService = new CompanyUnitStandardService();
		}
		if (trainingProviderSkillsProgrammeService == null) {
			trainingProviderSkillsProgrammeService = new TrainingProviderSkillsProgrammeService();
		}
		if (trainingProviderSkillsSetSevice == null) {
			trainingProviderSkillsSetSevice = new TrainingProviderSkillsSetService();
		}
		if (unitStandardsService == null) {
			unitStandardsService = new UnitStandardsService();
		}
		if (skillsProgramService == null) {
			skillsProgramService = new SkillsProgramService();
		}
		if (skillsSetService == null) {
			skillsSetService = new SkillsSetService();
		}
	}
	
	public void nonSetaCompanyInfo() {
		dataModelNonSetaCompany = new LazyDataModel<NonSetaCompany>() {
			private static final long serialVersionUID = 1L;
			private List<NonSetaCompany> companyList = new ArrayList<>();
			@Override
			public List<NonSetaCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					companyList = nonSetaCompanyService.allNonSetaCompanyByUserAssigned(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
					dataModelNonSetaCompany.setRowCount(nonSetaCompanyService.countAllNonSetaCompanyByUserAssigned(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}
			@Override
			public Object getRowKey(NonSetaCompany object) {
				return object.getId();
			}
			@Override
			public NonSetaCompany getRowData(String rowKey) {
				for (NonSetaCompany obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	/*
	 * validation check to ensure user who has landed on the page has companies assigned.
	 * If not redirected to appropriate dash board
	 * 
	 * Not Required due to all types of users accessing the page
	 * 
	 */
	private void userValidiationCheck() throws Exception{	
		if (nonSetaCompanyUsersService.findByUserTypeCount(getSessionUI().getActiveUser()) == 0) {
			super.redirectToDashboard();
		}
	}

	
	
	/**
	 * Selection Of NonSetaCompany and retrieves all information based on type of NonSetaCompany
	 */
	public void companySelection(){
		try {
			identifyLinkedUserByNonSetaCompany();
			locateUsersForNonSetaCompany();
			nonSetaCompanyUsersInfo();
			locateApplicationForNonMersetaCompany();
			if (trainingProviderApplication != null && trainingProviderApplication.getId() != null) {
				companyQualificationsList = companyQualificationsService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
				companyUnitStandardList = companyUnitStandardService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
				trainingProviderSkillsProgrammeList = trainingProviderSkillsProgrammeService.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
				trainingProviderSkillsSetList = trainingProviderSkillsSetSevice.findByTargetClassAndTargetKey(trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
//				dataModelNonSetaCompanyQualificationsInfo();
//				dataModelNonSetaCompanyUnitStandardsInfo();
//				dataModelNonSetaCompanySkillsProgramInfo();
//				dataModelNonSetaCompanySkillsSetInfo();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * @throws Exception
	 */
	public void identifyLinkedUserByNonSetaCompany() throws Exception {
		if (nonSetaCompanyUsersService.findByUserTypeAndNonSetaCompanyCount(getSessionUI().getActiveUser(), nonSetaCompany) == 0) {
			linkedUser = false;
		} else {
			linkedUser = true;
		}
	}
	
	private void locateUsersForNonSetaCompany() throws Exception{
		nonSetaCompanyUsersContactPersons = nonSetaCompanyUsersService.findTPContact(nonSetaCompany.getId(), ConfigDocProcessEnum.NON_SETA_PROVIDERS);
		nonSetaCompanyUsersAssesModerators = nonSetaCompanyUsersService.findTPAssessorMod(nonSetaCompany.getId(), ConfigDocProcessEnum.NON_SETA_PROVIDERS);
	}
	
	private void locateApplicationForNonMersetaCompany() throws Exception{
		if (nonSetaCompany.getCompanyType() == CompanyTypeEnum.TP) {
			trainingProviderApplication = trainingProviderApplicationService.findByNonMersetaCompany(nonSetaCompany);
		}
	}
	
	/**
	 * Populates assigned Non Seta companies assigned to the user
	 */
	public void nonSetaCompanyUsersInfo() {
		dataModelNonSetaCompanyUsers = new LazyDataModel<NonSetaCompanyUsers>() {
			private static final long serialVersionUID = 1L;
			private List<NonSetaCompanyUsers> list = new ArrayList<>();
			@Override
			public List<NonSetaCompanyUsers> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = nonSetaCompanyUsersService.allNonSetaCompanyUsersByCompany(first, pageSize, sortField, sortOrder, filters, nonSetaCompany);
					dataModelNonSetaCompanyUsers.setRowCount(nonSetaCompanyUsersService.countAllNonSetaCompanyUsersByCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(NonSetaCompanyUsers object) {
				return object.getId();
			}
			@Override
			public NonSetaCompanyUsers getRowData(String rowKey) {
				for (NonSetaCompanyUsers obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	/**
	 * Populates Qualifications Assigned To NonSetaCompany
	 */
	public void dataModelNonSetaCompanyQualificationsInfo() {
		dataModelNonSetaCompanyQualifications = new LazyDataModel<Qualification>() {
			private static final long serialVersionUID = 1L;
			private List<Qualification> list = new ArrayList<>();
			@Override
			public List<Qualification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = qualificationService.allQualificationByTrainingProviderLink(first, pageSize, sortField, sortOrder, filters, trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
					dataModelNonSetaCompanyQualifications.setRowCount(qualificationService.countAllQualificationByTrainingProviderLink(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(Qualification object) {
				return object.getId();
			}
			@Override
			public Qualification getRowData(String rowKey) {
				for (Qualification obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	/**
	 * Populates Unit Standards Assigned To NonSetaCompany
	 */
	public void dataModelNonSetaCompanyUnitStandardsInfo() {
		dataModelNonSetaCompanyUnitStandards = new LazyDataModel<UnitStandards>() {
			private static final long serialVersionUID = 1L;
			private List<UnitStandards> list = new ArrayList<>();
			@Override
			public List<UnitStandards> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = unitStandardsService.allUnitStandardsByTrainingProviderLink(first, pageSize, sortField, sortOrder, filters, trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
					dataModelNonSetaCompanyUnitStandards.setRowCount(unitStandardsService.countAllUnitStandardsByTrainingProviderLink(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(UnitStandards object) {
				return object.getId();
			}
			@Override
			public UnitStandards getRowData(String rowKey) {
				for (UnitStandards obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	/**
	 * Populates SkillsProgram Assigned to NonSetaCompany
	 */
	public void dataModelNonSetaCompanySkillsProgramInfo() {
		dataModelNonSetaCompanySkillsProgram = new LazyDataModel<SkillsProgram>() {
			private static final long serialVersionUID = 1L;
			private List<SkillsProgram> list = new ArrayList<>();
			@Override
			public List<SkillsProgram> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = skillsProgramService.allSkillsProgramByTrainingProviderLink(first, pageSize, sortField, sortOrder, filters, trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
					dataModelNonSetaCompanySkillsProgram.setRowCount(skillsProgramService.countAllSkillsProgramByTrainingProviderLink(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(SkillsProgram object) {
				return object.getId();
			}
			@Override
			public SkillsProgram getRowData(String rowKey) {
				for (SkillsProgram obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	/**
	 * Populates SkillsSet Assigned to NonSetaCompany
	 */
	public void dataModelNonSetaCompanySkillsSetInfo() {
		dataModelNonSetaCompanySkillsSet = new LazyDataModel<SkillsSet>() {
			private static final long serialVersionUID = 1L;
			private List<SkillsSet> list = new ArrayList<>();
			@Override
			public List<SkillsSet> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					list = skillsSetService.allSkillsSetsByTrainingProviderLink(first, pageSize, sortField, sortOrder, filters, trainingProviderApplication.getClass().getName(), trainingProviderApplication.getId());
					dataModelNonSetaCompanySkillsSet.setRowCount(skillsSetService.countAllSkillsSetsByTrainingProviderLink(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(SkillsSet object) {
				return object.getId();
			}
			@Override
			public SkillsSet getRowData(String rowKey) {
				for (SkillsSet obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	/* getters and setters */
	public NonSetaCompany getNonSetaCompany() {
		return nonSetaCompany;
	}

	public void setNonSetaCompany(NonSetaCompany nonSetaCompany) {
		this.nonSetaCompany = nonSetaCompany;
	}

	public NonSetaCompanyUsers getNonSetaCompanyUsers() {
		return nonSetaCompanyUsers;
	}

	public void setNonSetaCompanyUsers(NonSetaCompanyUsers nonSetaCompanyUsers) {
		this.nonSetaCompanyUsers = nonSetaCompanyUsers;
	}

	public LazyDataModel<NonSetaCompany> getDataModelNonSetaCompany() {
		return dataModelNonSetaCompany;
	}

	public void setDataModelNonSetaCompany(LazyDataModel<NonSetaCompany> dataModelNonSetaCompany) {
		this.dataModelNonSetaCompany = dataModelNonSetaCompany;
	}

	public boolean isLinkedUser() {
		return linkedUser;
	}

	public void setLinkedUser(boolean linkedUser) {
		this.linkedUser = linkedUser;
	}

	public LazyDataModel<NonSetaCompanyUsers> getDataModelNonSetaCompanyUsers() {
		return dataModelNonSetaCompanyUsers;
	}

	public void setDataModelNonSetaCompanyUsers(LazyDataModel<NonSetaCompanyUsers> dataModelNonSetaCompanyUsers) {
		this.dataModelNonSetaCompanyUsers = dataModelNonSetaCompanyUsers;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public LazyDataModel<Qualification> getDataModelNonSetaCompanyQualifications() {
		return dataModelNonSetaCompanyQualifications;
	}

	public void setDataModelNonSetaCompanyQualifications(
			LazyDataModel<Qualification> dataModelNonSetaCompanyQualifications) {
		this.dataModelNonSetaCompanyQualifications = dataModelNonSetaCompanyQualifications;
	}

	public LazyDataModel<UnitStandards> getDataModelNonSetaCompanyUnitStandards() {
		return dataModelNonSetaCompanyUnitStandards;
	}

	public void setDataModelNonSetaCompanyUnitStandards(LazyDataModel<UnitStandards> dataModelNonSetaCompanyUnitStandards) {
		this.dataModelNonSetaCompanyUnitStandards = dataModelNonSetaCompanyUnitStandards;
	}

	public LazyDataModel<SkillsProgram> getDataModelNonSetaCompanySkillsProgram() {
		return dataModelNonSetaCompanySkillsProgram;
	}

	public void setDataModelNonSetaCompanySkillsProgram(LazyDataModel<SkillsProgram> dataModelNonSetaCompanySkillsProgram) {
		this.dataModelNonSetaCompanySkillsProgram = dataModelNonSetaCompanySkillsProgram;
	}

	public LazyDataModel<SkillsSet> getDataModelNonSetaCompanySkillsSet() {
		return dataModelNonSetaCompanySkillsSet;
	}

	public void setDataModelNonSetaCompanySkillsSet(LazyDataModel<SkillsSet> dataModelNonSetaCompanySkillsSet) {
		this.dataModelNonSetaCompanySkillsSet = dataModelNonSetaCompanySkillsSet;
	}

	public int getContactPersonAssigned() {
		return contactPersonAssigned;
	}

	public void setContactPersonAssigned(int contactPersonAssigned) {
		this.contactPersonAssigned = contactPersonAssigned;
	}

	public int getAssesModAssigned() {
		return assesModAssigned;
	}

	public void setAssesModAssigned(int assesModAssigned) {
		this.assesModAssigned = assesModAssigned;
	}

	public List<NonSetaCompanyUsers> getNonSetaCompanyUsersAssesModerators() {
		return nonSetaCompanyUsersAssesModerators;
	}

	public void setNonSetaCompanyUsersAssesModerators(List<NonSetaCompanyUsers> nonSetaCompanyUsersAssesModerators) {
		this.nonSetaCompanyUsersAssesModerators = nonSetaCompanyUsersAssesModerators;
	}

	public List<NonSetaCompanyUsers> getNonSetaCompanyUsersContactPersons() {
		return nonSetaCompanyUsersContactPersons;
	}

	public void setNonSetaCompanyUsersContactPersons(List<NonSetaCompanyUsers> nonSetaCompanyUsersContactPersons) {
		this.nonSetaCompanyUsersContactPersons = nonSetaCompanyUsersContactPersons;
	}

	public List<CompanyQualifications> getCompanyQualificationsList() {
		return companyQualificationsList;
	}

	public void setCompanyQualificationsList(List<CompanyQualifications> companyQualificationsList) {
		this.companyQualificationsList = companyQualificationsList;
	}

	public List<CompanyUnitStandard> getCompanyUnitStandardList() {
		return companyUnitStandardList;
	}

	public void setCompanyUnitStandardList(List<CompanyUnitStandard> companyUnitStandardList) {
		this.companyUnitStandardList = companyUnitStandardList;
	}

	public List<TrainingProviderSkillsProgramme> getTrainingProviderSkillsProgrammeList() {
		return trainingProviderSkillsProgrammeList;
	}

	public void setTrainingProviderSkillsProgrammeList(
			List<TrainingProviderSkillsProgramme> trainingProviderSkillsProgrammeList) {
		this.trainingProviderSkillsProgrammeList = trainingProviderSkillsProgrammeList;
	}

	public List<TrainingProviderSkillsSet> getTrainingProviderSkillsSetList() {
		return trainingProviderSkillsSetList;
	}

	public void setTrainingProviderSkillsSetList(List<TrainingProviderSkillsSet> trainingProviderSkillsSetList) {
		this.trainingProviderSkillsSetList = trainingProviderSkillsSetList;
	}

}
