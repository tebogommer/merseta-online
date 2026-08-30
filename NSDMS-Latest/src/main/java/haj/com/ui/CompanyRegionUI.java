package haj.com.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;

import haj.com.constants.HAJConstants;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.DgVerification;
import haj.com.entity.DgVerificationSites;
import haj.com.entity.Doc;
import haj.com.entity.Employees;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.MailLog;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.MandatoryGrantRecommendation;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.TrainingComittee;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.Wsp;
import haj.com.entity.enums.CloRecommendationEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.BankingDetailsService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DgVerificationService;
import haj.com.service.DgVerificationSitesService;
import haj.com.service.DocService;
import haj.com.service.EmployeesService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.MandatoryGrantRecommendationService;
import haj.com.service.MandatoryGrantService;
import haj.com.service.SDFCompanyService;
import haj.com.service.SignoffService;
import haj.com.service.SitesService;
import haj.com.service.SitesSmeService;
import haj.com.service.TasksService;
import haj.com.service.TrainingComitteeService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.WorkplaceMonitoringService;
import haj.com.service.WspService;
import haj.com.service.YesNoLookupService;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.InterventionTypeService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

//TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantUI.
 */
@ManagedBean(name = "companyRegionUI")
@ViewScoped
public class CompanyRegionUI extends AbstractUI {

	private Company company;
	private BankingDetails bankingDetails;
	private Vendor vendor;

	private LazyDataModel<Company> companyDataModel;
	private LazyDataModel<Employees> empDataModel;
	private LazyDataModel<SitesSme> allSmeDataModel;
	private LazyDataModel<SDFCompany> sdfDataModel;
	private LazyDataModel<WorkPlaceApproval> allWorkPlaceApprovalDataModel;

	private List<CompanyUsers> companyUsers;
	private List<Company> linkedCompanies;
	private List<BankingDetails> bankingDetailsList;
	private List<TrainingComittee> trainingComittees;
	private List<Sites> sites;
	private List<MailLog> mailLogs;

	private WorkPlaceApprovalService workPlaceApprovalService = null;
	private SitesSmeService sitesSmeService = null;
	private SitesService sitesService = new SitesService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private EmployeesService employeesService = new EmployeesService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private CompanyService companyService = new CompanyService();
	private BankingDetailsService bankingDetailsService = new BankingDetailsService();
	private TrainingComitteeService trainingComitteeService = new TrainingComitteeService();
	private WorkplaceMonitoringService workplaceMonitoringService = new WorkplaceMonitoringService();

	private boolean selected = false;
	private List<Doc> changeReasonDocs = null;
	private boolean displaySdfRegDocs = false;
	private boolean displayChangeDocs = false;
	private boolean sdfHistory = false;

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

	private void runInit() throws Exception {

		HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
		HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
		// no client users should be able to view DG Verifications
		if (hce == null) {
			if (getSessionUI().getUser().getAdmin()) {
				locateCompanyInformation();
			} else {
				super.redirectToDashboard();
			}
		} else {
			if (RegionTownService.instance().checkIfCrmCloUser(hce)) {
				companyInfo();
			} else {
				super.redirectToDashboard();
			}
		}
	}

	/**
	 * Get all SiteVisitReport for data table
	 * 
	 * @author TechFinium
	 * @see SiteVisitReport
	 */
	public void companyInfo() {
		companyDataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}

					long count = RegionTownService.instance().findByCrmCloCount(getSessionUI().getUser().getId(), true);

					if (count > 0) {
						retorno = companyService.allCompanyRegion(Company.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						for (Company c : retorno) {
							RegionTown rt = RegionTownService.instance().findByTown(c.getResidentialAddress().getTown());
							c.setRegionTown(rt);
						}
						companyDataModel.setRowCount(companyService.countRegion(Company.class, filters));
					} else {
						retorno = companyService.allCompanyRegionForUser(Company.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						for (Company c : retorno) {
							RegionTown rt = RegionTownService.instance().findByTown(c.getResidentialAddress().getTown());
							c.setRegionTown(rt);
						}
						companyDataModel.setRowCount(companyService.countRegionForUser(Company.class, filters));
					}

				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Company obj) {
				return obj.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}

	public void locateCompanyInformation() {
		companyDataModel = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> retorno = new ArrayList<Company>();

			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}

					retorno = companyService.allCompanyRegions(Company.class, first, pageSize, sortField, sortOrder, filters);
					for (Company c : retorno) {
						RegionTown rt = RegionTownService.instance().findByTown(c.getResidentialAddress().getTown());
						c.setRegionTown(rt);
					}
					companyDataModel.setRowCount(companyService.countRegions(Company.class, filters));

				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Company obj) {
				return obj.getId();
			}

			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}

	public void requesteWorkflowWorkplacemonitoring() {
		try {
			workplaceMonitoringService.requestWorkplaceMonitoring(company, getSessionUI().getActiveUser());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void prepareCompayInfo() {
		try {
			companyUsers = companyUsersService.findByCompanyNotSDF(company);
			employeesInfo();
			sdfInfo();
			sdfHistory = false;
			bankingDetailsInfo();
			linkedCompanies = companyService.findLinkedCompany(company);
			trainingComittees = trainingComitteeService.findByCompany(company);
			sites = sitesService.findByCompany(company);
			siteSmeDataInfo();
			workplaceApprovalDataInfo();
			companyService.resolveDocsForProcessAndUserType(company, ConfigDocProcessEnum.SDF, CompanyUserTypeEnum.Company);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

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

	private void bankingDetailsInfo() {
		try {
			bankingDetails = bankingDetailsService.findByCompanyLates(company);
			if (bankingDetails == null) {
				bankingDetails = new BankingDetails();
			}

			bankingDetailsList = new ArrayList<>();
			if (bankingDetailsService.findByCompanyCount(company) > 1) {
				bankingDetailsList = bankingDetailsService.findByCompany(company);
				bankingDetailsList.remove(bankingDetails);
			} else if (bankingDetails.getId() == null && bankingDetailsService.findByCompanyCount(company) > 0) {
				bankingDetailsList = bankingDetailsService.findByCompany(company);
				// bankingDetailsList.remove(bankingDetails);
			}
			this.vendor = bankingDetailsService.getGPDetailsForLNumber(company.getLevyNumber(), bankingDetails.getBankAccNumber());
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
					retorno = workPlaceApprovalService.allWorkPlaceApprovalByCompany(first, pageSize, sortField, sortOrder, filters, company);
					allWorkPlaceApprovalDataModel.setRowCount(workPlaceApprovalService.countAllWorkPlaceApprovalByCompany(filters));
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LazyDataModel<Company> getCompanyDataModel() {
		return companyDataModel;
	}

	public void setCompanyDataModel(LazyDataModel<Company> companyDataModel) {
		this.companyDataModel = companyDataModel;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public LazyDataModel<Employees> getEmpDataModel() {
		return empDataModel;
	}

	public void setEmpDataModel(LazyDataModel<Employees> empDataModel) {
		this.empDataModel = empDataModel;
	}

	public LazyDataModel<SitesSme> getAllSmeDataModel() {
		return allSmeDataModel;
	}

	public void setAllSmeDataModel(LazyDataModel<SitesSme> allSmeDataModel) {
		this.allSmeDataModel = allSmeDataModel;
	}

	public LazyDataModel<SDFCompany> getSdfDataModel() {
		return sdfDataModel;
	}

	public void setSdfDataModel(LazyDataModel<SDFCompany> sdfDataModel) {
		this.sdfDataModel = sdfDataModel;
	}

	public LazyDataModel<WorkPlaceApproval> getAllWorkPlaceApprovalDataModel() {
		return allWorkPlaceApprovalDataModel;
	}

	public void setAllWorkPlaceApprovalDataModel(LazyDataModel<WorkPlaceApproval> allWorkPlaceApprovalDataModel) {
		this.allWorkPlaceApprovalDataModel = allWorkPlaceApprovalDataModel;
	}

	public List<CompanyUsers> getCompanyUsers() {
		return companyUsers;
	}

	public void setCompanyUsers(List<CompanyUsers> companyUsers) {
		this.companyUsers = companyUsers;
	}

	public List<Company> getLinkedCompanies() {
		return linkedCompanies;
	}

	public void setLinkedCompanies(List<Company> linkedCompanies) {
		this.linkedCompanies = linkedCompanies;
	}

	public List<BankingDetails> getBankingDetailsList() {
		return bankingDetailsList;
	}

	public void setBankingDetailsList(List<BankingDetails> bankingDetailsList) {
		this.bankingDetailsList = bankingDetailsList;
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

	public boolean isSdfHistory() {
		return sdfHistory;
	}

	public void setSdfHistory(boolean sdfHistory) {
		this.sdfHistory = sdfHistory;
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

	public List<MailLog> getMailLogs() {
		return mailLogs;
	}

	public void setMailLogs(List<MailLog> mailLogs) {
		this.mailLogs = mailLogs;
	}

}
