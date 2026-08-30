package haj.com.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.ExtensionRequestService;
import haj.com.service.TasksService;
import haj.com.service.WspService;
import haj.com.utils.GenericUtility;

@ManagedBean(name = "extensionrequestUI")
@ViewScoped
public class ExtensionRequestUI extends AbstractUI {

	private ExtensionRequestService service = new ExtensionRequestService();
	private List<ExtensionRequest> extensionrequestList = null;
	private List<ExtensionRequest> extensionrequestfilteredList = null;
	private List<Wsp> wsps = null;
	private WspService wspService = new WspService();
	private ExtensionRequest extensionrequest = null;
	private Company company;
	private Wsp wsp;
	private LazyDataModel<ExtensionRequest> dataModel;
	private Doc doc;
	private Date endOfMay;
	private boolean createWithoutGrantSelection;
	private Integer filterOptions = 1;
	private List<Integer> financialYears;
	private Integer selectedYear;

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
	 * Initialize method to get all ExtensionRequest and prepare a for a create of a
	 * new ExtensionRequest
	 * 
	 * @author TechFinium
	 * @see ExtensionRequest
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		createWithoutGrantSelection = false;
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.EXTENSION_REQUEST) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			extensionrequest = service.findByKey(getSessionUI().getTask().getTargetKey());
		} else {
			prepareNew();
			extensionrequestInfo();
		}
		endOfMay = GenericUtility.getEndOfDay(GenericUtility.sdf6.parse("31-05-" + GenericUtility.sdfYYYY.format(getNow())));
		populatesDistinctFinancialYears();
	}
	
	public void populatesDistinctFinancialYears() throws Exception {
		financialYears = wspService.findDictinctFinYears();
		if (financialYears.size() != 0) {
			selectedYear = financialYears.get(0);
		}
	}

	public void applyFilter(){
		try {
			if (filterOptions == 2 && selectedYear == null) {
				throw new Exception("Select a grant year before proceeding");
			}
			extensionrequestInfo();
			addInfoMessage("Filter Applied");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Get all ExtensionRequest for data table
	 * 
	 * @author TechFinium
	 * @see ExtensionRequest
	 */
	public void extensionrequestInfo() {
		dataModel = new LazyDataModel<ExtensionRequest>() {
			private static final long serialVersionUID = 1L;
			private List<ExtensionRequest> retorno = new ArrayList<ExtensionRequest>();
			@Override
			public List<ExtensionRequest> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (getSessionUI().isAdmin()) {
						switch (filterOptions) {
						case 1:
							retorno = service.allExtensionRequest(first, pageSize, sortField, sortOrder, filters);
							dataModel.setRowCount(service.count().intValue());
							break;
						case 2:
							if (selectedYear != null) {
								retorno = service.allExtensionRequestByWspFinYear(first, pageSize, sortField, sortOrder, filters, selectedYear);
								dataModel.setRowCount(service.countAllExtensionRequestByWspFinYear(filters));
							} else {
								retorno = service.allExtensionRequest(first, pageSize, sortField, sortOrder, filters);
								dataModel.setRowCount(service.count().intValue());
							}
							break;
						case 3:
							retorno = service.allExtensionRequestAwaitingWspAssignment(first, pageSize, sortField, sortOrder, filters);
							dataModel.setRowCount(service.countAllExtensionRequestAwaitingWspAssignment(filters));
							break;
						default:
							retorno = service.allExtensionRequest(first, pageSize, sortField, sortOrder, filters);
							dataModel.setRowCount(service.count().intValue());
							break;
						}
					} else {
						if (!getSessionUI().isEmployee()) {
							retorno = service.allExtensionRequest(first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
							dataModel.setRowCount(service.count(filters, getSessionUI().getActiveUser()));
						} else {
							switch (filterOptions) {
							case 1:
								retorno = service.allExtensionRequest(first, pageSize, sortField, sortOrder, filters);
								dataModel.setRowCount(service.count().intValue());
								break;
							case 2:
								if (selectedYear != null) {
									retorno = service.allExtensionRequestByWspFinYear(first, pageSize, sortField, sortOrder, filters, selectedYear);
									dataModel.setRowCount(service.countAllExtensionRequestByWspFinYear(filters));
								} else {
									retorno = service.allExtensionRequest(first, pageSize, sortField, sortOrder, filters);
									dataModel.setRowCount(service.count().intValue());
								}
								break;
							case 3:
								retorno = service.allExtensionRequestAwaitingWspAssignment(first, pageSize, sortField, sortOrder, filters);
								dataModel.setRowCount(service.countAllExtensionRequestAwaitingWspAssignment(filters));
								break;
							default:
								retorno = service.allExtensionRequest(first, pageSize, sortField, sortOrder, filters);
								dataModel.setRowCount(service.count().intValue());
								break;
							}
						}
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(ExtensionRequest obj) {
				return obj.getId();
			}
			@Override
			public ExtensionRequest getRowData(String rowKey) {
				for (ExtensionRequest obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert ExtensionRequest into database
	 * 
	 * @author TechFinium
	 * @see ExtensionRequest
	 */
	public void extensionrequestInsert() {
		try {
			boolean createTask = true;
			if (getSessionUI().isEmployee()) {
				extensionrequest.setStatus(ApprovalEnum.Approved);
				extensionrequest.setApprovedUser(getSessionUI().getActiveUser());
				createTask = false;
			}
			service.create(extensionrequest, getSessionUI().getActiveUser(), createTask, getSessionUI().getActiveUser());
			this.wsp = null;
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			extensionrequestInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void extensionrequestInsertVersionTwo() {
		try {
			if (getSessionUI().isEmployee()) {
				extensionrequest.setStatus(ApprovalEnum.Approved);
				extensionrequest.setApprovedUser(getSessionUI().getActiveUser());
				service.create(extensionrequest, getSessionUI().getActiveUser(), false, getSessionUI().getActiveUser());
			} else {
				// can only proceed before 01 May #Year# 00:00:00
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
				if (!getSessionUI().isEmployee() || getSessionUI().isExternalParty()) {
					Date today = new Date();
//					if (wsp != null && wsp.getFinYear() != null) {
//						Integer wspFinYearStart = wsp.getFinYear() - 1;
//						if (wsp.getFinYear() == 2021) {
//							if (today.after(GenericUtility.getStartOfDay(dateFormat.parse("31 05 " + wspFinYearStart )))) {
//								throw new Exception("Date passed for extension request. Please contact support.");
//							}
//						} else {
//							if (today.after(GenericUtility.getStartOfDay(dateFormat.parse("01 05 " +  wspFinYearStart )))) {
//								throw new Exception("Date passed for extension request. Please contact support.");
//							}
//						}
//					}else {
//						if (today.after(GenericUtility.getStartOfDay(dateFormat.parse("01 05 " + Integer.parseInt(GenericUtility.sdfYear.format(new Date())))))) {
//							throw new Exception("Date passed for extension request. Please contact support.");
//						}
//					}
					
				}
				if (extensionrequest.getNewSubmissionDate() == null) {
					if (extensionrequest.getWsp() != null) {
						int year = extensionrequest.getWsp().getFinYear() - 1;
						if (extensionrequest.getWsp().getFinYear() == 2021) {
							extensionrequest.setNewSubmissionDate(dateFormat.parse("30 06 " + year));
						}else {
							extensionrequest.setNewSubmissionDate(dateFormat.parse("31 05 " + year));
						}
					} else {
						if (Integer.parseInt(GenericUtility.sdfYear.format(new Date())) == 2020) {
							extensionrequest.setNewSubmissionDate(dateFormat.parse("30 06 " + Integer.parseInt(GenericUtility.sdfYear.format(new Date()))));
						} else {
							extensionrequest.setNewSubmissionDate(dateFormat.parse("31 05 " + Integer.parseInt(GenericUtility.sdfYear.format(new Date()))));
						}
					}
				}
				service.create(extensionrequest, getSessionUI().getActiveUser(), true, getSessionUI().getActiveUser());
			}
			this.wsp = null;
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			extensionrequestInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void findWSPforCompany() {
		try {
			wsps = wspService.findByCompany(extensionrequest.getCompany());
			List<ExtensionRequest> ex = service.findByCompanyNoWSP(extensionrequest.getCompany());
			createWithoutGrantSelection = false;
//			if (ex.size() > 0) {
//				addWarningMessage("Company already has and extension request on the system.");
//				prepareNew();
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void prepforUpdate() {
		try {
			extensionrequest = service.prepareNewDocs(extensionrequest, ConfigDocProcessEnum.EXTENSION_REQUEST, CompanyUserTypeEnum.Company);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Update ExtensionRequest in database
	 * 
	 * @author TechFinium
	 * @see ExtensionRequest
	 */
	public void extensionrequestUpdate() {
		try {
			service.update(this.extensionrequest);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			extensionrequestInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete ExtensionRequest from database
	 * 
	 * @author TechFinium
	 * @see ExtensionRequest
	 */
	public void extensionrequestDelete() {
		try {
			service.delete(this.extensionrequest);
			prepareNew();
			extensionrequestInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ExtensionRequest
	 * 
	 * @author TechFinium
	 * @see ExtensionRequest
	 */
	public void prepareNew() {
		try {
			extensionrequest = new ExtensionRequest();
			extensionrequest.setUser(getSessionUI().getActiveUser());
			extensionrequest.setStatus(ApprovalEnum.PendingApproval);
			extensionrequest.setWsp(wsp);
			service.prepareNewDocs(extensionrequest);
			wsps = new ArrayList<>();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() == null) {
				doc.setExtensionRequest(extensionrequest);
			}else {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void completeTask() {
		try {
			service.completeTask(extensionrequest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveTask() {
		try {
			service.approveTask(extensionrequest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectTask() {
		try {
			service.rejectTask(extensionrequest, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	/*
	 * public List<SelectItem> getExtensionRequestDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * extensionrequestInfo(); for (ExtensionRequest ug : extensionrequestList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return
	 * l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<ExtensionRequest> complete(String desc) {
		List<ExtensionRequest> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void downloadExtensionRequestReport(){
		try {
			service.downloadExtensionRequestReport();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	

	public List<ExtensionRequest> getExtensionRequestList() {
		return extensionrequestList;
	}

	public void setExtensionRequestList(List<ExtensionRequest> extensionrequestList) {
		this.extensionrequestList = extensionrequestList;
	}

	public ExtensionRequest getExtensionrequest() {
		return extensionrequest;
	}

	public void setExtensionrequest(ExtensionRequest extensionrequest) {
		this.extensionrequest = extensionrequest;
	}

	public List<ExtensionRequest> getExtensionRequestfilteredList() {
		return extensionrequestfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param extensionrequestfilteredList
	 *            the new extensionrequestfilteredList list
	 * @see ExtensionRequest
	 */
	public void setExtensionRequestfilteredList(List<ExtensionRequest> extensionrequestfilteredList) {
		this.extensionrequestfilteredList = extensionrequestfilteredList;
	}

	public LazyDataModel<ExtensionRequest> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ExtensionRequest> dataModel) {
		this.dataModel = dataModel;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Wsp getWsp() {
		return wsp;
	}

	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public Date getEndOfMay() {
		return endOfMay;
	}

	public void setEndOfMay(Date endOfMay) {
		this.endOfMay = endOfMay;
	}

	public List<Wsp> getWsps() {
		return wsps;
	}

	public void setWsps(List<Wsp> wsps) {
		this.wsps = wsps;
	}

	public boolean isCreateWithoutGrantSelection() {
		return createWithoutGrantSelection;
	}

	public void setCreateWithoutGrantSelection(boolean createWithoutGrantSelection) {
		this.createWithoutGrantSelection = createWithoutGrantSelection;
	}

	public Integer getFilterOptions() {
		return filterOptions;
	}

	public void setFilterOptions(Integer filterOptions) {
		this.filterOptions = filterOptions;
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

}
