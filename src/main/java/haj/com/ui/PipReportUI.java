package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.DgAllocationParent;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.SDFCompany;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.MoaTypeEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractsService;
import haj.com.service.DgAllocationParentService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.SDFCompanyService;
import haj.com.service.WspService;
import haj.com.service.lookup.RegionTownService;

/**
 * The Class PipReportUI.
 */
@ManagedBean(name = "pipReportUI")
@ViewScoped
public class PipReportUI extends AbstractUI {
	
	/* Service Levels */
	private WspService wspService = new WspService();
	private ProjectImplementationPlanService service = new ProjectImplementationPlanService();
	private DgAllocationParentService dgAllocationParentService = new DgAllocationParentService();
	private ActiveContractsService activeContractsService = new ActiveContractsService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private RegionTownService regionTownService = new RegionTownService();
	
	/* Lazy Data Models */
	private LazyDataModel<ProjectImplementationPlan> dataModel;
	
	/* Array Lists */
	private List<Integer> financialYears;
	
	/* Vars */
	private Integer selectedYear;
	private Integer yearForReport;
	private boolean displayReport;
	
	/* Enums */
	private MoaTypeEnum moaTypeSelection;

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
		displayReport = false;
		moaTypeSelection = MoaTypeEnum.DGMOA;
		populatesDistinctFinancialYears();
	}
	
	public void populatesDistinctFinancialYears() throws Exception {
		financialYears = wspService.findDictinctFinYears();
		if (!financialYears.isEmpty()) {
			selectedYear = financialYears.get(0);
		}
	}
	
	public void generateReport(){
		try {
			yearForReport = selectedYear;
			displayReport = true;
			dataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void dataModelInfo() {
//		dataModel = new ProjectImplementationPlanDatamodel();
		dataModel = new LazyDataModel<ProjectImplementationPlan>() {
			private static final long serialVersionUID = 1L;
			private List<ProjectImplementationPlan> retorno = new ArrayList<>();
			@Override
			public List<ProjectImplementationPlan> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
//					retorno = service.sortAndFilterSearch(ProjectImplementationPlan.class, first, pageSize, sortField, sortOrder, filters);
					retorno = service.sortAndFilterSearchByWspYear(first, pageSize, sortField, sortOrder, filters, yearForReport);
					for (ProjectImplementationPlan projectImplementationPlan : retorno) {
						DgAllocationParent dgAllocationParent = dgAllocationParentService.findByWSP(projectImplementationPlan.getWsp().getId());
						projectImplementationPlan.setDgAllocationParent(dgAllocationParent);
						projectImplementationPlan.setCloUser(getCLO(projectImplementationPlan.getWsp()));
						projectImplementationPlan.setSdfCompany(sdfCompanyService.locateFirstPrimarySDF(projectImplementationPlan.getWsp().getCompany()));
						List<SDFCompany>list = sdfCompanyService.findByCompanyAndSdfType(projectImplementationPlan.getWsp().getCompany(), (long) 5);
						if(list.size()>0) {
							projectImplementationPlan.setSecondarySdfCompany(list.get(0));
						}
						if(dgAllocationParent != null) {
							projectImplementationPlan.setActiveContracts(activeContractsService.findByDgAllocationParent(dgAllocationParent.getId()));
							RegionTown rt = regionTownService.findByTown(dgAllocationParent.getWsp().getCompany().getResidentialAddress().getTown());
							projectImplementationPlan.getDgAllocationParent().getWsp().getCompany().setRegionTown(rt);
						}
					}
//					setRowCount(service.countSearch(ProjectImplementationPlan.class, filters));
					setRowCount(service.countSearchByWspYear(filters));
				} catch (Exception e) {
					e.printStackTrace();
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(ProjectImplementationPlan obj) {
				return obj.getId();
			}
			@Override
			public ProjectImplementationPlan getRowData(String rowKey) {
				for (ProjectImplementationPlan obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public Users getCLO(Wsp wsp) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
		Users cloUser = rt.getClo().getUsers();
		return cloUser;
	}
	
	public void downloadPipReport(){
		try {
			service.downloadPipReport(yearForReport);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* getters and setters */
	public LazyDataModel<ProjectImplementationPlan> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ProjectImplementationPlan> dataModel) {
		this.dataModel = dataModel;
	}

	public List<Integer> getFinancialYears() {
		return financialYears;
	}

	public void setFinancialYears(List<Integer> financialYears) {
		this.financialYears = financialYears;
	}

	public Integer getSelectedYear() {
		return selectedYear;
	}

	public void setSelectedYear(Integer selectedYear) {
		this.selectedYear = selectedYear;
	}

	public boolean isDisplayReport() {
		return displayReport;
	}

	public void setDisplayReport(boolean displayReport) {
		this.displayReport = displayReport;
	}

	public MoaTypeEnum getMoaTypeSelection() {
		return moaTypeSelection;
	}

	public void setMoaTypeSelection(MoaTypeEnum moaTypeSelection) {
		this.moaTypeSelection = moaTypeSelection;
	}
	
}