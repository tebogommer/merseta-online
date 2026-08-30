package haj.com.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
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

import haj.com.entity.Company;
import haj.com.entity.DgAllocation;
import haj.com.entity.DgAllocationParent;
import haj.com.entity.Doc;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DgAllocationParentService;
import haj.com.service.DgAllocationService;
import haj.com.service.DocService;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.SDFCompanyService;
import haj.com.service.TasksService;
import haj.com.service.WspService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "dgAllocationAppealUI")
@ViewScoped
public class DgAllocationAppealUI extends AbstractUI {
	
	/** Entity Levels */
	private Wsp selectedWsp = null;
	private Company company = null;
	private DgAllocationParent dgAllocationParent = null;

	/** Service levels */
	private CompanyService companyService = null;
	private WspService wspService = null;
	private DgAllocationService dgAllocationService = null;
	private DgAllocationParentService allocationParentService = null;

	/** Array Lists */
	private List<DgAllocation> wspDgAllocationsList;
	
	private List<RejectReasons> selectedRejectReason;

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
		populateServiceLevels();
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.DG_ALLOCATION_APPEAL && super.getParameter("id", false) != null) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			dgAllocationParent = allocationParentService.findByKey(getSessionUI().getTask().getTargetKey());
			wspDgAllocationsList = dgAllocationService.findByParent(dgAllocationParent);
			selectedWsp = wspService.findByKey(dgAllocationParent.getWsp().getId());
			company = companyService.findByKey(selectedWsp.getCompany().getId());
			findRejectReasons();
		} else {
			super.ajaxRedirectToDashboard();
		}
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

	private void populateServiceLevels() {

		if (wspService == null) {
			wspService = new WspService();
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

	public void onSummaryRow(Object filter) {
		Long id = (Long) filter;
		total = 0.0;
		totalAwardAmount = 0.0;
		totalAwardAmountCofund = 0.0;
		totalAwardAmountDisabled = 0.0;
		totalRecommendedAmount = 0.0;
		totalRequestedAmount = 0.0;

		this.wspDgAllocationsList.stream()
			.filter(a -> a.getId() != null && (a.getDgAllocationParent().getId() == id || a.getMandatoryGrant().getWsp().getId() == id))
			.forEach(a -> {
				total += a.getTotalAwardAmount().doubleValue();
				totalAwardAmount += a.getAwardAmount().doubleValue();
				totalAwardAmountCofund += a.getCoFundingAwardAmount().doubleValue();
				totalRequestedAmount += a.getRequestedAmount().doubleValue();
				totalRecommendedAmount += a.getRecommendedAmount().doubleValue();
				totalAwardAmountDisabled += a.getDisabledGrantAmount().doubleValue();
			});
	}
	
	public void finalApproveAppeal(){
		try {
			allocationParentService.finalApproveParent(dgAllocationParent, getSessionUI().getActiveUser(), getSessionUI().getTask());
			clearTaskRedirect();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalRejectAppeal(){
		try {
			if(selectedRejectReason.size()<=0) {
				throw new Exception("Please select reject reason(s)");
			}
			allocationParentService.finalRejectParent(dgAllocationParent, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			clearTaskRedirect();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
	}
	
	private void clearTaskRedirect() throws Exception{
		getSessionUI().setTask(null);
		super.ajaxRedirectToDashboard();
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.DG_ALLOCATION_APPEAL);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/** Getters and setters */
	public Wsp getSelectedWsp() {
		return selectedWsp;
	}

	public void setSelectedWsp(Wsp selectedWsp) {
		this.selectedWsp = selectedWsp;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(List<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public List<RejectReasonsChild> getRejectReasonsChild() {
		return rejectReasonsChild;
	}

	public void setRejectReasonsChild(List<RejectReasonsChild> rejectReasonsChild) {
		this.rejectReasonsChild = rejectReasonsChild;
	}
	
	
}
