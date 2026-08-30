package haj.com.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import haj.com.constants.HAJConstants;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Company;
import haj.com.entity.DgAllocation;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.Doc;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ContractStatusEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.Title;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ActiveContractsService;
import haj.com.service.CompanyService;
import haj.com.service.DgAllocationParentService;
import haj.com.service.DgAllocationService;
import haj.com.service.DocService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.JasperService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.SDFCompanyService;
import haj.com.service.WspService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.TitleService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean(name = "dgAllocationInformationUI")
@ViewScoped
public class DgAllocationInformationUI extends AbstractUI {

	/** Entity Levels */
	private Wsp selectedWsp = null;
	private SDFCompany sdfCompany = null;
	private Company company = null;
	private DgAllocationParent dgAllocationParent = null;
	private Doc doc;

	/** Service levels */
	private SDFCompanyService sdfCompanyService = null;
	private CompanyService companyService = null;
	private WspService wspService = null;
	private DgAllocationService dgAllocationService = null;
	private DgAllocationParentService allocationParentService = null;

	private ActiveContractsService activeContractsService = new ActiveContractsService();

	private ProjectImplementationPlanService projectImplementationPlanService = new ProjectImplementationPlanService();
	private List<ProjectImplementationPlan> projectImplementationPlans;

	/** Lazy Data Model Lists */
	private LazyDataModel<SDFCompany> dataModelSdfCompany;
	private LazyDataModel<Company> dataModelCompany;
	private LazyDataModel<Wsp> dataModelWspWithAllocation;

	private ProjectImplementationPlan selectedProjectImplementationPlan;
	private Doc docPIP;

	/** Array Lists */
	private List<DgAllocation> wspDgAllocationsList;

	MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();

	/** The wsp. */
	private Wsp wsp;

	/** booelans */
	private boolean canAppeal = false;

	private ActiveContracts activeContract;
	private ActiveContractsService activeContractService = new ActiveContractsService();

	/** The Region Service */
	private RegionService regionService = new RegionService();

	/** Calculation Vars */
	private Double total = 0.0;
	private Double totalAwardAmount = 0.0;
	private Double totalRecommendedAmount = 0.0;
	private Double totalAwardAmountCofund = 0.0;
	private Double totalAwardAmountDisabled = 0.0;
	private Double totalRequestedAmount = 0.0;
	private BigDecimal maxAlloccation = BigDecimal.ZERO;
	private BigDecimal totalAllocated = BigDecimal.ZERO;
	private BigDecimal totalRequested = BigDecimal.ZERO;

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
		wsp = new Wsp();
		populateServiceLevels();
		if (getSessionUI().isExternalParty()) {
			dataModelSdfCompanyInfo();
		} else {
			dataModelCompanyInfo();
		}
	}

	public void prepareNew() throws Exception {

		wsp = new Wsp();
		populateServiceLevels();
		if (getSessionUI().isExternalParty()) {
			dataModelSdfCompanyInfo();
		} else {
			dataModelCompanyInfo();
		}
	}

	private void pipInfo() {
		try {
			projectImplementationPlans = projectImplementationPlanService.findByWspWhereAwarded(selectedWsp.getId());
			// projectImplementationPlans =
			// projectImplementationPlanService.findByCompany(sdfCompany.getCompany());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void kicakOffProjectImplementationPlan() {

		// Kicks off project implementation plan update creating a project termination
		// update task...
		try {
			projectImplementationPlanService.requestWorkflow(selectedProjectImplementationPlan, getSessionUI().getActiveUser());
			addInfoMessage("Project Implementation Plan requested");
			prepareNew();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void populateServiceLevels() {

		if (wspService == null) {
			wspService = new WspService();
		}
		if (sdfCompanyService == null) {
			sdfCompanyService = new SDFCompanyService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (allocationParentService == null) {
			allocationParentService = new DgAllocationParentService();
		}
		if (dgAllocationService == null) {
			dgAllocationService = new DgAllocationService();
		}

	}

	public void dataModelSdfCompanyInfo() throws Exception {
		dataModelSdfCompany = new LazyDataModel<SDFCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (getSessionUI().getActiveUser().getAdmin() == null || !getSessionUI().getActiveUser().getAdmin()) {

						retorno = sdfCompanyService.allCompanyFromSDFWherePrimarySearch(SDFCompany.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						// retorno = sdfCompanyService.allCompanyFromSDFWherePrimary(SDFCompany.class,
						// first, pageSize, sortField, sortOrder, filters,
						// getSessionUI().getActiveUser());
						dataModelSdfCompany.setRowCount(sdfCompanyService.allCompanyFromSDFCountWherePrimarySearch(filters, getSessionUI().getActiveUser()).intValue());
						// dataModelSdfCompany.setRowCount(sdfCompanyService.allCompanyFromSDFCountWherePrimary(filters,
						// getSessionUI().getActiveUser()).intValue());

					} else {
						// retorno = sdfCompanyService.allSDFCompany(SDFCompany.class, first, pageSize,
						// sortField, sortOrder, filters);
						retorno = sdfCompanyService.allSDFCompanySearch(SDFCompany.class, first, pageSize, sortField, sortOrder, filters);
						// dataModelSdfCompany.setRowCount(sdfCompanyService.count(SDFCompany.class,
						// filters));
						dataModelSdfCompany.setRowCount(sdfCompanyService.countSearch(SDFCompany.class, filters));
					}
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

	public boolean checkDisplayButtons(Wsp wsp) {
		boolean display = false;
		try {
			List<DgAllocationParent> dgAllocationParentList = allocationParentService.findByWSPReturnList(wsp.getId());
			DgAllocationParent dgAllocationParent = null;
			if (dgAllocationParentList.size() != 0) {
				dgAllocationParent = allocationParentService.findByKey(dgAllocationParentList.get(0).getId());
			}
			if (dgAllocationParent != null) {
				if (dgAllocationParent.getAcceptanceDate() != null) {
					display = true;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return display;

	}

	public boolean checkDisplayContract(Wsp wsp) {
		boolean display = false;
		try {
			ActiveContracts activeContracts = activeContractsService.findByWSP(wsp.getId());
			return activeContracts != null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return display;

	}

	public void dataModelCompanyInfo() {
		dataModelCompany = new LazyDataModel<Company>() {
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
					dataModelCompany.setRowCount(companyService.countSetaCompanies(Company.class, filters));

				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}

			@Override
			public Object getRowKey(Company object) {
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

	public void onRowSelectExternalParty(SelectEvent event) {
		try {
			company = companyService.findByKeyNoResolveData(sdfCompany.getCompany().getId());
			dataModelWspWithAllocationInfo();
			clearAllocationByWsp();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void onRowSelectExternalParty() {
		try {
			company = companyService.findByKeyNoResolveData(sdfCompany.getCompany().getId());
			dataModelWspWithAllocationInfo();
			clearAllocationByWsp();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void onRowSelectInternalParty() {
		try {
			company = companyService.findByKeyNoResolveData(company.getId());
			dataModelWspWithAllocationInfo();
			clearAllocationByWsp();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void onRowSelect(SelectEvent event) {
		try {
			company = companyService.findByKeyNoResolveData(company.getId());
			dataModelWspWithAllocationInfo();
			clearAllocationByWsp();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void dataModelWspWithAllocationInfo() {
		dataModelWspWithAllocation = new LazyDataModel<Wsp>() {
			private static final long serialVersionUID = 1L;
			private List<Wsp> retorno = new ArrayList<Wsp>();

			@Override
			public List<Wsp> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = wspService.allWspByCompanyWithAllocation(first, pageSize, sortField, sortOrder, filters, company);
					dataModelWspWithAllocation.setRowCount(wspService.countAllWspByCompanyWithAllocation(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Wsp obj) {
				return obj.getId();
			}

			@Override
			public Wsp getRowData(String rowKey) {
				for (Wsp obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	public void clearCompanySelected() {
		try {
			company = null;
			wspDgAllocationsList = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void onRowSelectWsp(SelectEvent event) {
		findAllocationByWsp();
	}

	public void loadCompanyAllocation() {
		findAllocationByWsp();
	}
	
	public void terminateProject(ActiveContracts activecontracts) {
		// Kicks off project termination workflow by creating a project termination task...
		try {
			ActiveContractsService activeContractsService = new ActiveContractsService();
			activeContractsService.submitProjectTerminationBySdf(activecontracts, getSessionUI().getActiveUser());
			addInfoMessage("Project terminiation for " + activecontracts.getRefnoAuto() + " requested");
			prepareNew();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void loadActiveContract() {
		try {
			wspDgAllocationsList = null; 
			this.activeContract = dgAllocationService.findActiveContractsByWsp(selectedWsp);
			if(this.activeContract != null && this.activeContract.getId()!=null) {
				terminateProject(this.activeContract);
			}else {
				addErrorMessage("No active contract found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public boolean checkStatus(Wsp wsp) {
		boolean showTermination = false;
		try {		
			//rowVar.contractStatusEnum ne ContractStatusEnum.SuspendProgress and rowVar.contractStatusEnum ne ContractStatusEnum.Terminated
			this.activeContract = dgAllocationService.findActiveContractsByWsp(wsp);
			if(this.activeContract != null && this.activeContract.getId()!=null) {
				if(this.activeContract.getContractStatusEnum() != ContractStatusEnum.SuspendProgress && this.activeContract.getContractStatusEnum() != ContractStatusEnum.Terminated) {
					showTermination = true;
				}				
			}		
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
		return showTermination;
	}
	
	public ActiveContracts getActiveContract() {
		return activeContract;
	}

	public void setActiveContract(ActiveContracts activeContract) {
		this.activeContract = activeContract;
	}

	public void findAllocationByWsp() {
		try {
			if (dgAllocationService.countByParentWsp(selectedWsp) == 0) {
				addWarningMessage("No Allocation Data Found");
			} else {
				// if (dgAllocationService.countByParentWspWithStatus(selectedWsp) == 0) {
				// addWarningMessage("Allocation still being processed.");
				// } else {
				validiateStatusForView();
				populateAllocationResults();
				// }
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void validiateStatusForView() throws Exception {
		allocationParentService.validiateParentStatus(selectedWsp);
	}

	/**
	 * @throws Exception
	 */
	public void populateAllocationResults() throws Exception {

		wspDgAllocationsList = dgAllocationService.findByParentWsp(selectedWsp);

		dgAllocationParent = allocationParentService.findByKey(wspDgAllocationsList.get(0).getDgAllocationParent().getId());

		activeContract = activeContractService.findByDgAllocationParent(dgAllocationParent.getId());
		if (activeContract != null) {
			dgAllocationParent = allocationParentService.findByKeyAndPopulateDoc(dgAllocationParent, activeContract);
		}

		canAppeal = false;
		findRejectReasons();
		if (getSessionUI().isExternalParty() && dgAllocationParent.getStatus() == ApprovalEnum.Rejected && dgAllocationParent.getApprovalDate() != null && dgAllocationParent.getAppealStatus() == null) {
			calculateAppeal();
		}

		pipInfo();
	}

	private List<RejectReasonsChild> rejectReasonsChild;

	private RejectReasonsChildService rejectReasonsService = new RejectReasonsChildService();

	public void findRejectReasons() {
		try {
			this.rejectReasonsChild = rejectReasonsService.findByTargetClassAndKey(DgAllocationParent.class.getName(), dgAllocationParent.getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Download Project Implementation Plan.
	 */
	public void downloadPIP() {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService js = new JasperService();
		try {

			wsp = wspService.findByKey(wsp.getId());
			resolveUser(params, wsp);
			// ********************************************
			Company comp = companyService.findByKey(wsp.getCompany().getId());
			String guid = comp.getCompanyGuid();
			params.put("guid", guid);
			// ********************************************
			ProjectImplementationPlanService projectImplementationPlanService = new ProjectImplementationPlanService();
			// List<ProjectImplementationPlan>
			// list=mandatoryGrantDetailService.findPIMReportData(wsp);
			// List<ProjectImplementationPlan>
			// list=projectImplementationPlanService.findByWsp(wsp);
			List<ProjectImplementationPlan> list = projectImplementationPlanService.findByWspWhereTotalaountIsGreaterThanZero(wsp);
			if (list == null || list.size() <= 0) {
				list = new ArrayList<>();
			}
			params.put("PIMDataSource1", new JRBeanCollectionDataSource(list));

			List<ProjectImplementationPlan> dummyList = new ArrayList<>();
			for (int x = 0; x < 10; x++) {
				ProjectImplementationPlan imp = new ProjectImplementationPlan();
				dummyList.add(imp);
			}
			params.put("PIMDataSource2", new JRBeanCollectionDataSource(dummyList));
			JasperService.addLogo(params);
			js.createReportFromDBtoServletOutputStream("Project_Implementation_Plan.jasper", params, "Project_Implementation_Plan.pdf");
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void resolveUser(Map<String, Object> params, Wsp wsp) throws Exception {
		ActiveContractsService activeContractsService = new ActiveContractsService();
		ActiveContracts ac = activeContractsService.findByWSP(wsp.getId());

		String primary_sdf_fullname = "";
		String primary_sdf_subtion_date = "";

		String clo_fullname = "";
		String clo_approved_date = "";

		String crm_fullname = "";
		String crm_approved_date = "";

		if (ac != null) {
			if (ac.getClo() != null) {
				clo_fullname = ac.getClo().getFirstName() + " " + ac.getClo().getLastName();
				if (ac.getCloSignDate() != null) {
					clo_approved_date = HAJConstants.sdf3.format(ac.getCloSignDate());
				}

			}
			if (ac.getCrm() != null) {
				crm_fullname = ac.getCrm().getFirstName() + " " + ac.getCrm().getLastName();
				if (ac.getCrmSignDate() != null) {
					crm_approved_date = HAJConstants.sdf3.format(ac.getCrmSignDate());
				}
			}
			if (ac.getSdf() != null) {
				primary_sdf_fullname = ac.getSdf().getFirstName() + " " + ac.getSdf().getLastName();
				if (ac.getSdfSignDate() != null) {
					primary_sdf_subtion_date = HAJConstants.sdf3.format(ac.getSdfSignDate());
				}

			}
		}
		params.put("primary_sdf_fullname", primary_sdf_fullname);
		params.put("primary_sdf_subtion_date", primary_sdf_subtion_date);

		params.put("clo_fullname", clo_fullname);
		params.put("clo_approved_date", clo_approved_date);

		params.put("crm_fullname", crm_fullname);
		params.put("crm_approved_date", crm_approved_date);

	}

	/**
	 * Download application form.
	 * Old version should not be used
	 */
	public void downloadMOA() {
		try {

			wsp = wspService.findByKey(wsp.getId());

			Map<String, Object> params = new HashMap<String, Object>();
			String path = HAJConstants.APP_PATH;
			// Creating Discretionary grant year to be displayed on the cover page
			String lastTwoDigitsOfNextYear = String.valueOf(wsp.getFinYear()).substring(Math.max(String.valueOf(wsp.getFinYear()).length() - 2, 0));
			String year = wsp.getFinYear() - 1 + "/" + lastTwoDigitsOfNextYear + " (Yr" + lastTwoDigitsOfNextYear + ")";
			// Adding Discretionary_Grant_Agreeement_details params

			RegionTown rt = RegionTownService.instance().findByTown(wsp.getCompany().getResidentialAddress().getTown());
			if (regionService == null) {
				regionService = new RegionService();
			}
			Region r = regionService.findByKey(rt.getRegion().getId());
			// ***************************Dg Allocation Data*****************************
			ArrayList<DgAllocation> dgList = new ArrayList<>();
			DgAllocationService dgAllocationService = new DgAllocationService();
			DgAllocationParentService dgAllocationParentService = new DgAllocationParentService();
			DgAllocationParent dgAllocationParent = null;
			if (wsp.getId() != null) {
				dgAllocationParent = dgAllocationParentService.findByWSP(wsp.getId());
				if (dgAllocationParent != null) {
					dgList = (ArrayList<DgAllocation>) dgAllocationService.findByParentWhereAmountAwarded(dgAllocationParent);
				}

			}
			params.put("DgAllocationDataSource", new JRBeanCollectionDataSource(dgList));
			params.put("AnnexureDataSource", new JRBeanCollectionDataSource(dgList));
			/*
			 * double totalAwardedAmnt=0.00; for(DgAllocation dga:dgList) { try {
			 * if(dga.getTotalAwardAmount() !=null) { totalAwardedAmnt
			 * +=dga.getTotalAwardAmount().longValueExact(); } }
			 * params.put("total_awarded_amnt",totalAwardedAmnt);
			 */

			params.put("total_awarded_amnt", dgAllocationService.findTotalAllocatedWhereAmountAwarded(dgAllocationParent).doubleValue());

			HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
			List<Users> ceoList = hostingCompanyEmployeesService.findUserByJobTitle(HAJConstants.CEO_JOB_TITLE_ID);
			Users ceo = null;
			if (ceoList.size() > 0) {
				ceo = ceoList.get(0);
			}

			String merSETA_CEO = "";
			if (ceo != null) {
				if (ceo.getTitle() != null) {
					TitleService titleService = new TitleService();
					Title tile = titleService.findByKey(ceo.getTitle().getId());
					merSETA_CEO = tile.getDescription() + " " + ceo.getLastName() + " " + ceo.getFirstName();
				} else {
					merSETA_CEO = ceo.getLastName() + " " + ceo.getFirstName();
				}

			}
			// **************************************************************************

			params.put("wsp", wsp);
			params.put("company", wsp.getCompany());
			params.put("wsp_id", wsp.getId());
			params.put("wsp_report", WspReportEnum.WSP.ordinal());
			params.put("etqa_code", HAJConstants.HOSTING_MERSETA_ETQA);
			params.put("funding_id", HAJConstants.DISC_FUNDING_ID);
			params.put("region", r.getDescription());
			params.put("year", year);
			params.put("merSETA_CEO", merSETA_CEO);
			params.put("terminationDate", "30 September " + (wsp.getFinYear() + 4));
			params.put("projectedStartDate", "1 Jan " + wsp.getFinYear());
			params.put("projectedEndDate", "31 Mar " + wsp.getFinYear() + 1);
			JasperService.addLogo(params);
			JasperService.addDiscretionaryGrantSubReports(params);
			String fileName = wsp.getCompany().getLevyNumber() + "-(Yr" + lastTwoDigitsOfNextYear + ")-Discretionary_Grant_Agreement.pdf";
			// Adding books
			if (wsp.getFinYearNonNull() > 2021){
				params.put("cover_page", path + "reports/newmoaver12/DG_Agreement_Book_cover.jasper");
				params.put("table_of_content", path + "reports/newmoaver12/DG_Agreement_Book_toc.jasper");
			}
			else {
				params.put("cover_page", path + "reports/DG_Agreement_Book_cover.jasper");
				params.put("table_of_content", path + "reports/DG_Agreement_Book_toc.jasper");
			}
			params.put("DGA_details", path + "reports/Discretionary_Grant_Agreeement_details.jasper");
			JasperService.instance().createReportFromDBtoServletOutputStream("DG_Agreement_Book.jasper", params, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/*
	 * version Two of the download of the MOA
	 * Caters for version one & two of the MOA based on Active Contract linked to wsp
	 */
	public void downloadMOAVersionTwo() {
		try {
			if (dgAllocationService == null) {
				dgAllocationService = new DgAllocationService();
			}
			wsp = wspService.findByKey(wsp.getId());
			dgAllocationService.donloadMoaVerstionTwoByWsp(wsp);
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void onSummaryRow(Object filter) {
		Long id = (Long) filter;
		total = 0.0;
		totalAwardAmount = 0.0;
		totalAwardAmountCofund = 0.0;
		totalAwardAmountDisabled = 0.0;
		totalRecommendedAmount = 0.0;
		totalRequestedAmount = 0.0;

		this.wspDgAllocationsList.stream().filter(a -> a.getId() != null && (a.getDgAllocationParent().getId() == id || a.getMandatoryGrant().getWsp().getId() == id)).forEach(a -> {
			total += a.getTotalAwardAmount().doubleValue();
			totalAwardAmount += a.getAwardAmount().doubleValue();
			totalAwardAmountCofund += a.getCoFundingAwardAmount().doubleValue();
			totalRequestedAmount += a.getRequestedAmount().doubleValue();
			totalRecommendedAmount += a.getRecommendedAmount().doubleValue();
			totalAwardAmountDisabled += a.getDisabledGrantAmount().doubleValue();
		});
	}

	/**
	 * Business rule: 14 days to appeal from rejection date Example: - 2nd, 3rd,
	 * 4th, 5th, 6th, 7th, 8th, 9th, 10th, 11th, 12th, 13th, 14th, 15th of September
	 * 2018 they can appeal - Excludes weekend days Java daysToComplate: 15
	 */
	private void calculateAppeal() throws Exception {
		Integer daysToAction = 15;
		Date today = GenericUtility.getStartOfDay(new Date());
		Date compltedByDateIncWeekends = GenericUtility.addDaysToDateExcludeWeekends(GenericUtility.getStartOfDay(dgAllocationParent.getApprovalDate()), daysToAction);
		if (today.equals(compltedByDateIncWeekends) || today.before(compltedByDateIncWeekends)) {
			canAppeal = true;
		}
	}

	public void clearAllocationByWsp() {
		try {
			wspDgAllocationsList = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeNewFileAndStartAppealProcess(FileUploadEvent event) {
		try {
			allocationParentService.populateAppealDocUpload(dgAllocationParent);
			doc = dgAllocationParent.getDocs().get(0);
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setTargetClass(dgAllocationParent.getClass().getName());
			doc.setTargetKey(dgAllocationParent.getId());
			if (doc.getId() == null) {
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			} else {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			allocationParentService.appleadDgParent(dgAllocationParent, getSessionUI().getActiveUser());
			// super.runClientSideExecute("PF('dlgUpload').hide()");
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/** Getters and setters */
	public Wsp getSelectedWsp() {
		return selectedWsp;
	}

	public void setSelectedWsp(Wsp selectedWsp) {
		this.selectedWsp = selectedWsp;
	}

	public SDFCompany getSdfCompany() {
		return sdfCompany;
	}

	public void setSdfCompany(SDFCompany sdfCompany) {
		this.sdfCompany = sdfCompany;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LazyDataModel<SDFCompany> getDataModelSdfCompany() {
		return dataModelSdfCompany;
	}

	public void setDataModelSdfCompany(LazyDataModel<SDFCompany> dataModelSdfCompany) {
		this.dataModelSdfCompany = dataModelSdfCompany;
	}

	public LazyDataModel<Wsp> getDataModelWspWithAllocation() {
		return dataModelWspWithAllocation;
	}

	public void setDataModelWspWithAllocation(LazyDataModel<Wsp> dataModelWspWithAllocation) {
		this.dataModelWspWithAllocation = dataModelWspWithAllocation;
	}

	public List<DgAllocation> getWspDgAllocationsList() {
		return wspDgAllocationsList;
	}

	public void setWspDgAllocationsList(List<DgAllocation> wspDgAllocationsList) {
		this.wspDgAllocationsList = wspDgAllocationsList;
	}

	public DgAllocationParent getDgAllocationParent() {
		return dgAllocationParent;
	}

	public void setDgAllocationParent(DgAllocationParent dgAllocationParent) {
		this.dgAllocationParent = dgAllocationParent;
	}

	public boolean isCanAppeal() {
		return canAppeal;
	}

	public void setCanAppeal(boolean canAppeal) {
		this.canAppeal = canAppeal;
	}

	public LazyDataModel<Company> getDataModelCompany() {
		return dataModelCompany;
	}

	public void setDataModelCompany(LazyDataModel<Company> dataModelCompany) {
		this.dataModelCompany = dataModelCompany;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getTotalAwardAmount() {
		return totalAwardAmount;
	}

	public void setTotalAwardAmount(Double totalAwardAmount) {
		this.totalAwardAmount = totalAwardAmount;
	}

	public Double getTotalRecommendedAmount() {
		return totalRecommendedAmount;
	}

	public void setTotalRecommendedAmount(Double totalRecommendedAmount) {
		this.totalRecommendedAmount = totalRecommendedAmount;
	}

	public Double getTotalAwardAmountCofund() {
		return totalAwardAmountCofund;
	}

	public void setTotalAwardAmountCofund(Double totalAwardAmountCofund) {
		this.totalAwardAmountCofund = totalAwardAmountCofund;
	}

	public Double getTotalAwardAmountDisabled() {
		return totalAwardAmountDisabled;
	}

	public void setTotalAwardAmountDisabled(Double totalAwardAmountDisabled) {
		this.totalAwardAmountDisabled = totalAwardAmountDisabled;
	}

	public Double getTotalRequestedAmount() {
		return totalRequestedAmount;
	}

	public void setTotalRequestedAmount(Double totalRequestedAmount) {
		this.totalRequestedAmount = totalRequestedAmount;
	}

	public BigDecimal getMaxAlloccation() {
		return maxAlloccation;
	}

	public void setMaxAlloccation(BigDecimal maxAlloccation) {
		this.maxAlloccation = maxAlloccation;
	}

	public BigDecimal getTotalAllocated() {
		return totalAllocated;
	}

	public void setTotalAllocated(BigDecimal totalAllocated) {
		this.totalAllocated = totalAllocated;
	}

	public BigDecimal getTotalRequested() {
		return totalRequested;
	}

	public void setTotalRequested(BigDecimal totalRequested) {
		this.totalRequested = totalRequested;
	}

	/**
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * @param wsp
	 *            the wsp to set
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public List<RejectReasonsChild> getRejectReasonsChild() {
		return rejectReasonsChild;
	}

	public void setRejectReasonsChild(List<RejectReasonsChild> rejectReasonsChild) {
		this.rejectReasonsChild = rejectReasonsChild;
	}

	public List<ProjectImplementationPlan> getProjectImplementationPlans() {
		return projectImplementationPlans;
	}

	public void setProjectImplementationPlans(List<ProjectImplementationPlan> projectImplementationPlans) {
		this.projectImplementationPlans = projectImplementationPlans;
	}

	public ProjectImplementationPlan getSelectedProjectImplementationPlan() {
		return selectedProjectImplementationPlan;
	}

	public void setSelectedProjectImplementationPlan(ProjectImplementationPlan selectedProjectImplementationPlan) {
		this.selectedProjectImplementationPlan = selectedProjectImplementationPlan;
	}

	public Doc getDocPIP() {
		return docPIP;
	}

	public void setDocPIP(Doc docPIP) {
		this.docPIP = docPIP;
	}
}