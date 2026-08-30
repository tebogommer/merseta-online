package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.WorkPlaceApproval;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.lookup.RegionTownService;

@ManagedBean(name = "workplaceApprovalRegionReportUI")
@ViewScoped
public class WorkplaceApprovalRegionReportUI extends AbstractUI {
	
	/* Service Levels */
	private HostingCompanyEmployeesService hceService = new HostingCompanyEmployeesService();
	private WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
	
	/* Lazy Data Models */
	private LazyDataModel<WorkPlaceApproval> workPlaceApprovalDataModel;
	
	/* Vars */
	private boolean cloCrmUser = false;
	private boolean displayNormalDownload = false;

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
		populateInformation();
		companyDataModelInfo();
	}
	
	private void populateInformation() throws Exception {
		if (getSessionUI().getRole() == null || getSessionUI().getRole().getCloCrmReporting() == null || !getSessionUI().getRole().getCloCrmReporting()) {
			super.redirectToDashboard();
		} else {
			HostingCompanyEmployees hce = hceService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
			if (hce == null) {
				super.redirectToDashboard();
			} else {
				cloCrmUser = RegionTownService.instance().checkIfCrmCloUser(hce);
				if (cloCrmUser) {
					// count for download
					if (workPlaceApprovalService.countWorkPlaceApprovalByCloCrmAssigned(getSessionUI().getActiveUser().getId()) > 65000) {
						displayNormalDownload = false;
					} else {
						displayNormalDownload = true;
					}
				}
			}
		}
	}

	private void companyDataModelInfo() {
		workPlaceApprovalDataModel = new LazyDataModel<WorkPlaceApproval>() {
			private static final long serialVersionUID = 1L;
			private List<WorkPlaceApproval> retorno = new ArrayList<>();
			@Override
			public List<WorkPlaceApproval> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (cloCrmUser) {
						retorno = workPlaceApprovalService.cloCrmWorkPlaceApprovalRegionReport(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser().getId());
						workPlaceApprovalDataModel.setRowCount(workPlaceApprovalService.countCloCrmWorkPlaceApprovalRegionReport( filters));
					}
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
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/* getters and Seters */
	public boolean isDisplayNormalDownload() {
		return displayNormalDownload;
	}

	public void setDisplayNormalDownload(boolean displayNormalDownload) {
		this.displayNormalDownload = displayNormalDownload;
	}

	public boolean isCloCrmUser() {
		return cloCrmUser;
	}

	public void setCloCrmUser(boolean cloCrmUser) {
		this.cloCrmUser = cloCrmUser;
	}

	public LazyDataModel<WorkPlaceApproval> getWorkPlaceApprovalDataModel() {
		return workPlaceApprovalDataModel;
	}

	public void setWorkPlaceApprovalDataModel(LazyDataModel<WorkPlaceApproval> workPlaceApprovalDataModel) {
		this.workPlaceApprovalDataModel = workPlaceApprovalDataModel;
	}

}