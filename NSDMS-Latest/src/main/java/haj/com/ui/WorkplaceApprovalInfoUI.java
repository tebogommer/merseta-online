package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;

import haj.com.constants.HAJConstants;
import haj.com.entity.BankingDetails;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Doc;
import haj.com.entity.Employees;
import haj.com.entity.MailLog;
import haj.com.entity.SDFCompany;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.TrainingComittee;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyRegOrSDLEnum;
import haj.com.entity.enums.ProviderNameRegisTrationNumberEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.framework.AbstractUIInterface;
import haj.com.service.BankingDetailsService;
import haj.com.service.ChangeReasonService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.EmployeesService;
import haj.com.service.MailLogService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SitesService;
import haj.com.service.SitesSmeService;
import haj.com.service.TrainingComitteeService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class WorkplaceApprovalInfoUI.
 */
@ManagedBean(name = "workplaceApprovalInfoUI")
@ViewScoped
public class WorkplaceApprovalInfoUI extends AbstractUI {

	/** The criteria. */
	private String criteria;

	/** The object. */
	private AbstractUIInterface object;

	/** The company service. */
	private CompanyService companyService = new CompanyService();

	/** The company reg or SDL enum. */
	private CompanyRegOrSDLEnum companyRegOrSDLEnum;
	private ProviderNameRegisTrationNumberEnum providerNameRegisTrationNumberEnum;

	/** The Constant company registration number format. */
	private String companyRegistrationNumberFormat = HAJConstants.companyRegistrationNumberFormat;
	
	/** The Constant company accreditation number format. */
	private String companyAccreditationNumberFormat = HAJConstants.companyAccreditationNumberFormat;

	/** The Constant company levy number format. */
	private String companyLevyNumberFormat = HAJConstants.companyLevyNumberFormat;

	/** The company */
	private Company company = new Company();

	/** The search NGO. */
	private boolean searchNGO;
	
	/** The search Accreditation Number. */
	private boolean searchAccNumber;
	
	/** The search Entity ID. */
	private boolean searchEntityId;

	private boolean searchProvider;

	private boolean searchLevyPaying;
	private boolean searchTrainingProvider;
	/** Company lazy data model */
	private LazyDataModel<Company> dataModel;

	/** The company list **/
	private List<Company> companyFilteredList = null;

	private MailLogService mailLogService = new MailLogService();

	private List<MailLog> mailLogs;

	/** The company users. */
	private List<CompanyUsers> companyUsers;
	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/** The employees data model. */
	private LazyDataModel<Employees> empDataModel;
	/** The employeesService */
	private EmployeesService employeesService = new EmployeesService();

	/** The SDF data model. */
	private LazyDataModel<SDFCompany> sdfDataModel;
	
	private SDFCompany selectedSdfCompany = null;
	private Users sdf = null;
	
	/** The SDFCompanyService */
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();

	/** The Banking Details */
	private List<BankingDetails> bankingDetailsList;
	/** The banking details service. */
	private BankingDetailsService bankingDetailsService = new BankingDetailsService();
	/** The banking details. */
	private BankingDetails bankingDetails;
	private Vendor vendor;

	/** The linked companies. */
	private List<Company> linkedCompanies;

	/** The training comittees. */
	private List<TrainingComittee> trainingComittees;
	/** The training comittee service. */
	private TrainingComitteeService trainingComitteeService = new TrainingComitteeService();

	/** The sites service. */
	private SitesService sitesService = new SitesService();
	/** The sites. */
	private List<Sites> sites;
	
	/** The Sites SME */
	private SitesSmeService sitesSmeService = null;
	private LazyDataModel<SitesSme> allSmeDataModel;
	
	/** The workplace approval data */
	private WorkPlaceApprovalService workPlaceApprovalService = null;
	private LazyDataModel<WorkPlaceApproval> allWorkPlaceApprovalDataModel;
	
	private UsersService usersService = new UsersService();
	private List<Doc> changeReasonDocs = null; 
	private boolean displaySdfRegDocs = false;
	private boolean displayChangeDocs = false;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Run init.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void runInit() throws Exception {
		clearCriteria();
		companyInfo();
	}

	public void companyInfo() {
		dataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}
					companyList = companyService.allCompanySetaCompanies(Company.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(companyService.countSetaCompanies(Company.class, filters));

				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}

			@Override
			public Object getRowKey(Company object) {
				// TODO Auto-generated method stub
				return object.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	public void prepareCompayInfo() {
		try {
			/*companyUsers = companyUsersService.findByCompanyNotSDF(company);
			employeesInfo();
			sdfInfo();
			bankingDetailsInfo();
			linkedCompanies = companyService.findLinkedCompany(company);
			trainingComittees = trainingComitteeService.findByCompany(company);
			sites = sitesService.findByCompany(company);
			siteSmeDataInfo();*/
			workplaceApprovalDataInfo();
			//companyService.resolveDocsForProcessAndUserType(company, ConfigDocProcessEnum.SDF, CompanyUserTypeEnum.Company);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Get all Employees for data table.
	 *
	 * @author TechFinium
	 * @see Employees
	 */
	public void employeesInfo() {

		empDataModel = new LazyDataModel<Employees>() {

			private static final long serialVersionUID = 1L;
			private List<Employees> retorno = new ArrayList<Employees>();

			@Override
			public List<Employees> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = employeesService.allEmployees(Employees.class, first, pageSize, sortField, sortOrder, filters, company);
					empDataModel.setRowCount(employeesService.count(Employees.class, filters, company));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Employees obj) {
				return obj.getId();
			}

			@Override
			public Employees getRowData(String rowKey) {
				for (Employees obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Get all SDFCompany for data table.
	 *
	 * @author TechFinium
	 * @see SDFCompany
	 */
	public void sdfInfo() {
		sdfDataModel = new LazyDataModel<SDFCompany>() {
			
			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();
			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = sdfCompanyService.allSDFForCompany(SDFCompany.class, first, pageSize, sortField, sortOrder, filters, company, getSessionUI().getActiveUser());
					sdfDataModel.setRowCount(sdfCompanyService.allSDFForCompanyCount(filters, company, getSessionUI().getActiveUser()).intValue());

				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDFCompany obj) {
				return obj.getId();
			}

			@Override
			public SDFCompany getRowData(String rowKey) {
				for (SDFCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	public void viewSdfDocumnets(){
		try {
			displaySdfRegDocs = false;
			displayChangeDocs = false;
			// locates sdf doc from registartion
			sdf = usersService.findByKey(selectedSdfCompany.getSdf().getId());
//			usersService.resolveDocs(sdf, ConfigDocProcessEnum.SDF, CompanyUserTypeEnum.User);
			usersService.locateUserDocs(sdf);
			if (sdf.getDocs() != null && sdf.getDocs().size() != 0) {
				displaySdfRegDocs = true;
			}
			changeReasonDocs = new ArrayList<>();
			// if documents assigned to any change reasons
			List<ChangeReason> changeReasons = ChangeReasonService.instance().findByTargetClassAndTargetKey(selectedSdfCompany.getClass().getName(), selectedSdfCompany.getId());
			if (changeReasons.size() != 0) {
				for (ChangeReason changeReason : changeReasons) {
					if (changeReason.getDocs() != null && changeReason.getDocs().size() != 0) {
						displayChangeDocs = true;
						changeReasonDocs.addAll(changeReason.getDocs());
					}
				}
			}
			// if no doucments will display message none found
			if ((sdf.getDocs() != null && sdf.getDocs().size() != 0) || (changeReasonDocs.size() != 0)) {
				// oncomplete="PF('mailLogDlg').show()"
				super.runClientSideExecute("PF('sdfdocsDlg').show()");
				super.runClientSideUpdate("sdfDocForm");
			} else {
				addErrorMessage("No Documents Found For User");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void bankingDetailsInfo() {
		try {
			bankingDetails = bankingDetailsService.findByCompanyLates(company);
			if (bankingDetails == null) {
				bankingDetails = new BankingDetails();
			}

			if (bankingDetailsService.findByCompanyCount(company) > 1) {
				bankingDetailsList = bankingDetailsService.findByCompany(company);
				bankingDetailsList.remove(bankingDetails);
			}
			
//			this.vendor = bankingDetailsService.getGPDetailsForLNumber(company.getLevyNumber(), bankingDetails.getBankAccNumber());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void siteSmeDataInfo() {
		if (sitesSmeService == null) {
			sitesSmeService = new SitesSmeService();
		}
		
		allSmeDataModel = new LazyDataModel<SitesSme>() {
			private static final long serialVersionUID = 1L;
			private List<SitesSme> retorno = new ArrayList<SitesSme>();
			@Override
			public List<SitesSme> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = sitesSmeService.allSitesSmeByCompany(first, pageSize, sortField, sortOrder, filters, company, null, null);
					allSmeDataModel.setRowCount(sitesSmeService.countAllSitesSmeByCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(SitesSme obj) {
				return obj.getId();
			}
			@Override
			public SitesSme getRowData(String rowKey) {
				for (SitesSme obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	private void workplaceApprovalDataInfo() {
		if (workPlaceApprovalService == null) {
			workPlaceApprovalService = new WorkPlaceApprovalService();
		}
		allWorkPlaceApprovalDataModel = new LazyDataModel<WorkPlaceApproval>() {
			private static final long serialVersionUID = 1L;
			private List<WorkPlaceApproval> retorno = new ArrayList<WorkPlaceApproval>();
			@Override
			public List<WorkPlaceApproval> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					//retorno = workPlaceApprovalService.allWorkPlaceApprovalByCompany(first, pageSize, sortField, sortOrder, filters, company);
					retorno = workPlaceApprovalService.sortAndFilterWhereWPAInfo(first, pageSize, sortField, sortOrder, filters, company);
					//allWorkPlaceApprovalDataModel.setRowCount(workPlaceApprovalService.countAllWorkPlaceApprovalByCompany(filters));
					allWorkPlaceApprovalDataModel.setRowCount(workPlaceApprovalService.countWhereWPAInfo(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(WorkPlaceApproval obj) {
				return obj.getId();
			}
			@Override
			public WorkPlaceApproval getRowData(String rowKey) {
				for (WorkPlaceApproval obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Clear criteria.
	 */
	public void clearCriteria() {
		this.criteria = "";
		this.companyRegOrSDLEnum = null;
		this.searchNGO = false;
		this.searchLevyPaying = false;
	}

	/**
	 * Find.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void find() throws Exception {
		Company company = companyService.searchByReg(criteria);
		if (company == null) {
			company = new Company();
			company.setExistingCompany(false);
			company.setCompanyRegistrationNumber(criteria);
		} else {
			company.setExistingCompany(true);
		}
		company.setNonLevyPaying(false);
		company.setDoneSearch(true);
		clearCriteria();
		object.callBackMethod(company);
	}

	/**
	 * Find by SDL.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void findBySDL() throws Exception {
		Company company = companyService.searchBySDL(criteria);
		if (company == null) {
			company = new Company();
			company.setExistingCompany(false);
			company.setLevyNumber(criteria);
		} else {
			company.setExistingCompany(true);

		}
		company.setNonLevyPaying(false);
		company.setDoneSearch(true);
		clearCriteria();
		object.callBackMethod(company);
	}
	
	/**
	 * Method to find by SDL number and display error if accured
	 */
	public void findBySDLTrainingProviderRegistartionMethod(){
		try {
			findBySDLTrainingProviderRegistartion();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	/**
	 * Find by SDL.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void findBySDLTrainingProviderRegistartion() throws Exception {
		String msg = criteria;
		Company company = companyService.searchBySDL(criteria);
		if (company == null) {
			throw new Exception(msg + " is not registered on the portal.");
		} else {
			company.setExistingCompany(true);
		}
		company.setNonLevyPaying(false);
		company.setDoneSearch(true);
		clearCriteria();
		object.callBackMethod(company);
	}
	
	/**
	 * Find by AccreditationNumber.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void findByAccreditationNumberTrainingProviderRegistartion() throws Exception {
		Company company = companyService.searchByAccreditationNumber(criteria);
		if (company == null) {
			company = new Company();
			company.setExistingCompany(false);
			company.setAccreditationNumber(criteria);
		} else {
			company.setExistingCompany(true);
		}
		company.setDoneSearch(true);
		clearCriteria();
		object.callBackMethod(company);
	}

	public void returnNonLevyPaying() {
		if (searchNGO) {
			Company company;
			company = new Company();
			company.setExistingCompany(false);
			company.setDoneSearch(true);
			company.setNonLevyPaying(true);
			clearCriteria();
			object.callBackMethod(company);
		}
	}
	

	public void clearCheckbox1()
	{
		searchLevyPaying=false;
	    searchTrainingProvider=false;
	}
	
	public void clearCheckbox2()
	{
		searchProvider=false;
		searchTrainingProvider=false;
	}
	
	public void clearCheckbox3()
	{
		searchProvider=false;
		searchLevyPaying=false;
	}
	
	public void searchByAccNumber(){
		try {
			if (searchAccNumber) {
				searchEntityId = false;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void searchByEntityId(){
		try {
			if (searchEntityId) {
				searchAccNumber = false;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Company> completeCompany(String desc) {
		List<Company> l = null;
		try {
			l = companyService.findByNameOrLevyNum(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the object.
	 *
	 * @return the object
	 */
	public AbstractUIInterface getObject() {
		return object;
	}

	/**
	 * Sets the object.
	 *
	 * @param object
	 *            the new object
	 */
	public void setObject(AbstractUIInterface object) {
		this.object = object;
		clearCriteria();
	}

	/**
	 * Gets the criteria.
	 *
	 * @return the criteria
	 */
	public String getCriteria() {
		return criteria;
	}

	/**
	 * Sets the criteria.
	 *
	 * @param criteria
	 *            the new criteria
	 */
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	/**
	 * Gets the company reg or SDL enum.
	 *
	 * @return the company reg or SDL enum
	 */
	public CompanyRegOrSDLEnum getCompanyRegOrSDLEnum() {
		return companyRegOrSDLEnum;
	}

	/**
	 * Sets the company reg or SDL enum.
	 *
	 * @param companyRegOrSDLEnum
	 *            the new company reg or SDL enum
	 */
	public void setCompanyRegOrSDLEnum(CompanyRegOrSDLEnum companyRegOrSDLEnum) {
		this.companyRegOrSDLEnum = companyRegOrSDLEnum;
	}

	/**
	 * Checks if is search NGO.
	 *
	 * @return true, if is search NGO
	 */
	public boolean isSearchNGO() {
		return searchNGO;
	}

	/**
	 * Sets the search NGO.
	 *
	 * @param searchNGO
	 *            the new search NGO
	 */
	public void setSearchNGO(boolean searchNGO) {
		this.searchNGO = searchNGO;
	}

	public String getCompanyRegistrationNumberFormat() {
		return companyRegistrationNumberFormat;
	}

	public void setCompanyRegistrationNumberFormat(String companyRegistrationNumberFormat) {
		this.companyRegistrationNumberFormat = companyRegistrationNumberFormat;
	}

	public String getCompanyLevyNumberFormat() {
		return companyLevyNumberFormat;
	}

	public void setCompanyLevyNumberFormat(String companyLevyNumberFormat) {
		this.companyLevyNumberFormat = companyLevyNumberFormat;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {

		this.company = company;
	}

	public boolean isSearchLevyPaying() {
		return searchLevyPaying;
	}

	public void setSearchLevyPaying(boolean searchLevyPaying) {
		this.searchLevyPaying = searchLevyPaying;
	}

	public LazyDataModel<Company> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Company> dataModel) {
		this.dataModel = dataModel;
	}

	public List<Company> getCompanyFilteredList() {
		return companyFilteredList;
	}

	public void setCompanyFilteredList(List<Company> companyFilteredList) {
		this.companyFilteredList = companyFilteredList;
	}

	public List<MailLog> getMailLogs() {
		return mailLogs;
	}

	public void setMailLogs(List<MailLog> mailLogs) {
		this.mailLogs = mailLogs;
	}

	public void findMailLog() {
		try {
			this.mailLogs = mailLogService.allMailForCompany(company);
			if (this.mailLogs.size() < 1) {
				addInfoMessage("No mails has been send");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<CompanyUsers> getCompanyUsers() {
		return companyUsers;
	}

	public void setCompanyUsers(List<CompanyUsers> companyUsers) {
		this.companyUsers = companyUsers;
	}

	public LazyDataModel<Employees> getEmpDataModel() {
		return empDataModel;
	}

	public void setEmpDataModel(LazyDataModel<Employees> empDataModel) {
		this.empDataModel = empDataModel;
	}

	public LazyDataModel<SDFCompany> getSdfDataModel() {
		return sdfDataModel;
	}

	public void setSdfDataModel(LazyDataModel<SDFCompany> sdfDataModel) {
		this.sdfDataModel = sdfDataModel;
	}

	public List<BankingDetails> getBankingDetailsList() {
		return bankingDetailsList;
	}

	public void setBankingDetailsList(List<BankingDetails> bankingDetailsList) {
		this.bankingDetailsList = bankingDetailsList;
	}

	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public List<Company> getLinkedCompanies() {
		return linkedCompanies;
	}

	public void setLinkedCompanies(List<Company> linkedCompanies) {
		this.linkedCompanies = linkedCompanies;
	}

	public List<TrainingComittee> getTrainingComittees() {
		return trainingComittees;
	}

	public void setTrainingComittees(List<TrainingComittee> trainingComittees) {
		this.trainingComittees = trainingComittees;
	}

	public List<Sites> getSites() {
		return sites;
	}

	public void setSites(List<Sites> sites) {
		this.sites = sites;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public boolean isSearchProvider() {
		return searchProvider;
	}

	public void setSearchProvider(boolean searchProvider) {
		this.searchProvider = searchProvider;
	}

	public ProviderNameRegisTrationNumberEnum getProviderNameRegisTrationNumberEnum() {
		return providerNameRegisTrationNumberEnum;
	}

	public void setProviderNameRegisTrationNumberEnum(ProviderNameRegisTrationNumberEnum providerNameRegisTrationNumberEnum) {
		this.providerNameRegisTrationNumberEnum = providerNameRegisTrationNumberEnum;
	}
	
	public String getCompanyAccreditationNumberFormat() {
		return companyAccreditationNumberFormat;
	}

	public void setCompanyAccreditationNumberFormat(String companyAccreditationNumberFormat) {
		this.companyAccreditationNumberFormat = companyAccreditationNumberFormat;
	}

	public boolean isSearchAccNumber() {
		return searchAccNumber;
	}

	public void setSearchAccNumber(boolean searchAccNumber) {
		this.searchAccNumber = searchAccNumber;
	}

	public boolean isSearchEntityId() {
		return searchEntityId;
	}

	public void setSearchEntityId(boolean searchEntityId) {
		this.searchEntityId = searchEntityId;
	}

	public boolean isSearchTrainingProvider() {
		return searchTrainingProvider;
	}

	public void setSearchTrainingProvider(boolean searchTrainingProvider) {
		this.searchTrainingProvider = searchTrainingProvider;
	}

	public LazyDataModel<SitesSme> getAllSmeDataModel() {
		return allSmeDataModel;
	}

	public void setAllSmeDataModel(LazyDataModel<SitesSme> allSmeDataModel) {
		this.allSmeDataModel = allSmeDataModel;
	}

	public LazyDataModel<WorkPlaceApproval> getAllWorkPlaceApprovalDataModel() {
		return allWorkPlaceApprovalDataModel;
	}

	public void setAllWorkPlaceApprovalDataModel(LazyDataModel<WorkPlaceApproval> allWorkPlaceApprovalDataModel) {
		this.allWorkPlaceApprovalDataModel = allWorkPlaceApprovalDataModel;
	}

	public SDFCompany getSelectedSdfCompany() {
		return selectedSdfCompany;
	}

	public void setSelectedSdfCompany(SDFCompany selectedSdfCompany) {
		this.selectedSdfCompany = selectedSdfCompany;
	}

	public Users getSdf() {
		return sdf;
	}

	public void setSdf(Users sdf) {
		this.sdf = sdf;
	}

	public List<Doc> getChangeReasonDocs() {
		return changeReasonDocs;
	}

	public void setChangeReasonDocs(List<Doc> changeReasonDocs) {
		this.changeReasonDocs = changeReasonDocs;
	}

	public boolean isDisplaySdfRegDocs() {
		return displaySdfRegDocs;
	}

	public void setDisplaySdfRegDocs(boolean displaySdfRegDocs) {
		this.displaySdfRegDocs = displaySdfRegDocs;
	}

	public boolean isDisplayChangeDocs() {
		return displayChangeDocs;
	}

	public void setDisplayChangeDocs(boolean displayChangeDocs) {
		this.displayChangeDocs = displayChangeDocs;
	}

}
