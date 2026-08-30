package haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.OfoCodes;
import haj.com.entity.Province;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ProvinceService;
import haj.com.service.WorkPlaceApprovalService;
import haj.com.service.lookup.QualificationService;
import haj.com.utils.GenericUtility;

/**
 * UI for the reporting section for the Workplace approval
 * 
 * @author jonathanbowett
 */
@ManagedBean(name = "workplaceApproavlReportingUI")
@ViewScoped
public class WorkplaceApproavlReportingUI extends AbstractUI {

	/** Entity Level */
	private Qualification selectedQualification = null;
	private OfoCodes selectedOfoCodes = null;
	private Province selectedProvince = null;
	
	/** Service Level */
	private WorkPlaceApprovalService workPlaceApprovalService = null;
	private ProvinceService provinceService = null;

	/** Data Model Lists */
	private LazyDataModel<WorkPlaceApproval> dataModelWorkPlaceApproval;

	/** Array List */
	private List<ApprovalEnum> statusForSelection = null;
	private List<ApprovalEnum> statusSelected = null;
	private List<Qualification> qualificationList = null;
	private List<OfoCodes> ofoCodesList = null;
	private List<Province> avalibleProvinceList = null;
	private List<Province> provinceList = null;

	/** Date Range Filters */
	private Date fromDate;
	private Date toDate;

	/** Boolean Displays */
	private boolean filterByDateRange = false;
	// true for create date, false for approved date
	private boolean createApproveDatefilter = false;
	private boolean filterByStatus = false;
	private boolean filterTradeQualification = false;
	private boolean filterByProvince = false;
	private boolean displayReport = false;
	
	/** Vars */
	private ApprovalEnum addRemoveStatus = null;

	/**
	 * Inits the runInit.
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
		permissionCheck();
		populateServiceLevel();
		populateDefaultInformation();
	}

	/**
	 * Check to ensure only users with correct permissions can access the page
	 */
	private void permissionCheck() {
		boolean hasAccess = true;
		// check if super admin
		if (!getSessionUI().getUser().getSuperAdmin()) {
			// check if user has access to page
			if (!getSessionUI().getUser().getTempReporting()) {
				// hasAccess = false;
			}
		}
		if (!hasAccess) {
			super.redirectToDashboard();
		}
	}

	private void populateServiceLevel() {
		if (workPlaceApprovalService == null) {
			workPlaceApprovalService = new WorkPlaceApprovalService();
		}
		if (provinceService == null) {
			provinceService = new ProvinceService();
		}
	}

	private void populateDefaultInformation() throws Exception{
		fromDate = GenericUtility.getFirstDayOfMonth(new Date());
		toDate = GenericUtility.getStartOfDay(new Date());
		populateStatusList();
		populateQualificationOfocodeList();
		populateProvinceList();
	}

	private void populateStatusList() throws Exception {
		hideReportResults();
		statusSelected = new ArrayList<ApprovalEnum>();
		statusForSelection = workPlaceApprovalService.getStatusUsed(statusSelected);	
	}
	
	private void populateQualificationOfocodeList() throws Exception{
		hideReportResults();
		qualificationList = new ArrayList<Qualification>();
		ofoCodesList = new ArrayList<OfoCodes>();
	}
	
	private void populateProvinceList() throws Exception{
		hideReportResults();
		avalibleProvinceList = provinceService.allProvince();
		provinceList = new ArrayList<Province>();
	}
	
	public void hideReportResults(){
		displayReport = false;
	}
	
	public void clear(){
		try {
			filterByDateRange = false;
			createApproveDatefilter = false;
			filterByStatus = false;
			filterTradeQualification = false;
			populateDefaultInformation();
			addInfoMessage("Clear Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addProvince(){
		try {
			hideReportResults();
			if (selectedProvince == null || selectedProvince.getId() == null) {
				addErrorMessage("Selecte A Province Before Procceding");
			} else {
				if (provinceList.contains(selectedProvince)) {
					addWarningMessage("Province Already Assigned, Select Different Province");
				} else {
					provinceList.add(selectedProvince);
					addInfoMessage("Province Assigned");
					selectedProvince = null;
				}
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void removeProvince(){
		try {
			hideReportResults();
			provinceList.remove(selectedProvince);
			selectedProvince = null;
			addInfoMessage("Province Removed From Filter");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addQualification(){
		try {
			hideReportResults();
			if (qualificationList.contains(selectedQualification)) {
				addWarningMessage("Qualification Already Assigned, Select Different Qualification");
			} else {
				qualificationList.add(selectedQualification);
				addInfoMessage("Qualification Assigned");
				selectedQualification = null;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void removeQualification(){
		try {
			hideReportResults();
			qualificationList.remove(selectedQualification);
			selectedQualification = null;
			addInfoMessage("Qualification Removed From Filter");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addOfoCode(){
		try {
			if (ofoCodesList.contains(selectedOfoCodes)) {
				addWarningMessage("Trade Already Assigned, Select Different Trade");
			} else {
				ofoCodesList.add(selectedOfoCodes);
				addInfoMessage("Trade Assigned");
				selectedOfoCodes = null;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void removeOfoCode(){
		try {
			hideReportResults();
			ofoCodesList.remove(selectedOfoCodes);
			selectedOfoCodes = null;
			addInfoMessage("Trade Removed From Filter");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addStatusForFilter() {
		try {
			hideReportResults();
			if (addRemoveStatus == null) {
				throw new Exception("Unable to assigned status, contact support!");
			} else {
				if (statusSelected.contains(addRemoveStatus)) {
					addWarningMessage("Status Has Already Been Selected");
				}else {
					statusSelected.add(addRemoveStatus);
					statusForSelection.remove(addRemoveStatus);
					addInfoMessage("Status Added");
				}
				addRemoveStatus = null;
			}			
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
	}
	
	public void removeStatusForFilter(){
		try {
			hideReportResults();
			statusSelected.remove(addRemoveStatus);
			statusForSelection = workPlaceApprovalService.getStatusUsed(statusSelected);
			addRemoveStatus = null;
			addInfoMessage("Status Removed From Filter");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void generateReport(){
		try {
			String errors = workPlaceApprovalService.validateParamaters(filterByDateRange, createApproveDatefilter, fromDate, toDate, filterByStatus, statusSelected, filterTradeQualification, qualificationList, ofoCodesList, filterByProvince, provinceList);
			if (errors == null || errors == "") {
				generateReportInfo();
				addInfoMessage("Report Generated");
			} else {
				hideReportResults();
				addErrorMessage("Validation Error Has Accured: " + errors);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			hideReportResults();
		}
	}
	
	private void generateReportInfo() throws Exception{
		displayReport = true;
		dataModelWorkPlaceApproval = new LazyDataModel<WorkPlaceApproval>() {
			private static final long serialVersionUID = 1L;
			private List<WorkPlaceApproval> retorno = new ArrayList<WorkPlaceApproval>();
			@Override
			public List<WorkPlaceApproval> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (filterByDateRange) {
						fromDate = GenericUtility.getStartOfDay(fromDate);
						toDate = GenericUtility.getStartOfDay(toDate);
					}
					retorno = workPlaceApprovalService.workplaceApprovalReportByFilters(first, pageSize, sortField, sortOrder, filters, filterByDateRange, createApproveDatefilter, fromDate, toDate, filterByStatus, statusSelected, filterTradeQualification, qualificationList, ofoCodesList, filterByProvince, provinceList);
					dataModelWorkPlaceApproval.setRowCount(workPlaceApprovalService.countWorkplaceApprovalReportByFilters(filters, filterByDateRange, createApproveDatefilter, fromDate, toDate, filterByStatus, statusSelected, filterTradeQualification, qualificationList, ofoCodesList, filterByProvince, provinceList));
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
	
	public List<Qualification> completeMersetaQualification(String desc) {
		List<Qualification> l = null;
		try {
			QualificationService qualificationService = new QualificationService();
			l = qualificationService.findMersetaQualificationAutocomplete(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/** Getters & Setters */
	public boolean isDisplayReport() {
		return displayReport;
	}

	public void setDisplayReport(boolean displayReport) {
		this.displayReport = displayReport;
	}

	public LazyDataModel<WorkPlaceApproval> getDataModelWorkPlaceApproval() {
		return dataModelWorkPlaceApproval;
	}

	public void setDataModelWorkPlaceApproval(LazyDataModel<WorkPlaceApproval> dataModelWorkPlaceApproval) {
		this.dataModelWorkPlaceApproval = dataModelWorkPlaceApproval;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public boolean isFilterByDateRange() {
		return filterByDateRange;
	}

	public void setFilterByDateRange(boolean filterByDateRange) {
		this.filterByDateRange = filterByDateRange;
	}

	public boolean isFilterByStatus() {
		return filterByStatus;
	}

	public void setFilterByStatus(boolean filterByStatus) {
		this.filterByStatus = filterByStatus;
	}

	public List<ApprovalEnum> getStatusForSelection() {
		return statusForSelection;
	}

	public void setStatusForSelection(List<ApprovalEnum> statusForSelection) {
		this.statusForSelection = statusForSelection;
	}

	public List<ApprovalEnum> getStatusSelected() {
		return statusSelected;
	}

	public void setStatusSelected(List<ApprovalEnum> statusSelected) {
		this.statusSelected = statusSelected;
	}

	public ApprovalEnum getAddRemoveStatus() {
		return addRemoveStatus;
	}

	public void setAddRemoveStatus(ApprovalEnum addRemoveStatus) {
		this.addRemoveStatus = addRemoveStatus;
	}

	public boolean isCreateApproveDatefilter() {
		return createApproveDatefilter;
	}

	public void setCreateApproveDatefilter(boolean createApproveDatefilter) {
		this.createApproveDatefilter = createApproveDatefilter;
	}

	public Qualification getSelectedQualification() {
		return selectedQualification;
	}

	public void setSelectedQualification(Qualification selectedQualification) {
		this.selectedQualification = selectedQualification;
	}

	public OfoCodes getSelectedOfoCodes() {
		return selectedOfoCodes;
	}

	public void setSelectedOfoCodes(OfoCodes selectedOfoCodes) {
		this.selectedOfoCodes = selectedOfoCodes;
	}

	public List<Qualification> getQualificationList() {
		return qualificationList;
	}

	public void setQualificationList(List<Qualification> qualificationList) {
		this.qualificationList = qualificationList;
	}

	public List<OfoCodes> getOfoCodesList() {
		return ofoCodesList;
	}

	public void setOfoCodesList(List<OfoCodes> ofoCodesList) {
		this.ofoCodesList = ofoCodesList;
	}

	public boolean isFilterTradeQualification() {
		return filterTradeQualification;
	}

	public void setFilterTradeQualification(boolean filterTradeQualification) {
		this.filterTradeQualification = filterTradeQualification;
	}

	public List<Province> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<Province> provinceList) {
		this.provinceList = provinceList;
	}

	public Province getSelectedProvince() {
		return selectedProvince;
	}

	public void setSelectedProvince(Province selectedProvince) {
		this.selectedProvince = selectedProvince;
	}

	public boolean isFilterByProvince() {
		return filterByProvince;
	}

	public void setFilterByProvince(boolean filterByProvince) {
		this.filterByProvince = filterByProvince;
	}

	public List<Province> getAvalibleProvinceList() {
		return avalibleProvinceList;
	}

	public void setAvalibleProvinceList(List<Province> avalibleProvinceList) {
		this.avalibleProvinceList = avalibleProvinceList;
	}

}
