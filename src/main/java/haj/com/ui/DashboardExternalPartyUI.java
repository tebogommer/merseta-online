package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CoursewareDistibution;
import haj.com.entity.ExtensionRequest;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyUsersService;
import haj.com.service.CoursewareDistibutionService;
import haj.com.service.ExtensionRequestService;
import haj.com.service.SDFCompanyService;
import haj.com.service.lookup.ModulesService;

// TODO: Auto-generated Javadoc
/**
 * The Class DashboardExternalPartyUI.
 */
@ManagedBean(name = "dashboardExternalPartyUI")
@ViewScoped
public class DashboardExternalPartyUI extends AbstractUI {

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();

	/** The Extension Request Service */
	private ExtensionRequestService extensionRequestService = new ExtensionRequestService();

	/** The companies. */
	private List<Company> companies;
	private List<CompanyLearners> companyLearners;
	private Long companyLearnersCount;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	/** ThecompanyExtensionRequest */
	private List<ExtensionRequest> companyExtensionRequests = new ArrayList<>();

	/** The Selected Company */
	private Company selectedCompay = new Company();
	private CompanyLearners selectedCompayLearner;

	private LazyDataModel<CoursewareDistibution> coursewareDistibutionDataModel;
	private CoursewareDistibutionService coursewareDistibutionService = new CoursewareDistibutionService();
	private ModulesService modulesService = new ModulesService();

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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getActiveUser() != null) {
			if (!getSessionUI().isLearner()) {
				this.companies = companyUsersService.findByUser(getSessionUI().getActiveUser());
				this.companies = sdfCompanyService.findByUser(getSessionUI().getActiveUser(), companies);
				this.companyLearners = companyLearnersService.allLearnersByUser(getSessionUI().getActiveUser());
			} else {
				this.companyLearners = companyLearnersService.allLearnersByUser(getSessionUI().getActiveUser());
			}
			// if (getSessionUI().isTrainingProvider()) courseWareInfo();
		}
	}

	public void courseWareInfo() {
		coursewareDistibutionDataModel = new LazyDataModel<CoursewareDistibution>() {

			private static final long serialVersionUID = 1L;
			private List<CoursewareDistibution> retorno = new ArrayList<CoursewareDistibution>();

			@Override
			public List<CoursewareDistibution> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = coursewareDistibutionService.allCompanyDashboard(CoursewareDistibution.class, first, pageSize, sortField, sortOrder, filters, selectedCompayLearner.getCompany());
					modulesService.resolveEverythingForCourseware(retorno);
					coursewareDistibutionDataModel.setRowCount(coursewareDistibutionService.countCompanyDashboard(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CoursewareDistibution obj) {
				return obj.getId();
			}

			@Override
			public CoursewareDistibution getRowData(String rowKey) {
				for (CoursewareDistibution obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}

	public void companyExtensionRequests() {
		try {
			companyExtensionRequests = extensionRequestService.findByCompany(selectedCompay);

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<ExtensionRequest> checkCompanyExtensionRequests(Company comp) {
		List<ExtensionRequest> list = new ArrayList<>();
		try {
			list = extensionRequestService.findByCompany(comp);

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return list;
	}

	/**
	 * Gets the companies.
	 *
	 * @return the companies
	 */
	public List<Company> getCompanies() {
		return companies;
	}

	/**
	 * Sets the companies.
	 *
	 * @param companies
	 *            the new companies
	 */
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public Company getSelectedCompay() {
		return selectedCompay;
	}

	public void setSelectedCompay(Company selectedCompay) {
		this.selectedCompay = selectedCompay;
	}

	public List<ExtensionRequest> getCompanyExtensionRequest() {
		return companyExtensionRequests;
	}

	public void setCompanyExtensionRequest(List<ExtensionRequest> companyExtensionRequest) {
		this.companyExtensionRequests = companyExtensionRequest;
	}

	public LazyDataModel<CoursewareDistibution> getCoursewareDistibutionDataModel() {
		return coursewareDistibutionDataModel;
	}

	public void setCoursewareDistibutionDataModel(LazyDataModel<CoursewareDistibution> coursewareDistibutionDataModel) {
		this.coursewareDistibutionDataModel = coursewareDistibutionDataModel;
	}

	public List<CompanyLearners> getCompanyLearners() {
		return companyLearners;
	}

	public void setCompanyLearners(List<CompanyLearners> companyLearners) {
		this.companyLearners = companyLearners;
	}

	public Long getCompanyLearnersCount() {
		return companyLearnersCount;
	}

	public void setCompanyLearnersCount(Long companyLearnersCount) {
		this.companyLearnersCount = companyLearnersCount;
	}

	public CompanyLearners getSelectedCompayLearner() {
		return selectedCompayLearner;
	}

	public void setSelectedCompayLearner(CompanyLearners selectedCompayLearner) {
		this.selectedCompayLearner = selectedCompayLearner;
	}

}
