package haj.com.ui;

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

import haj.com.entity.Doc;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SiteVisit;
import haj.com.entity.SiteVisitReport;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.SiteVisitService;
import haj.com.service.TasksService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "sitevisitUI")
@ViewScoped
public class SiteVisitUI extends AbstractUI {

	private SiteVisitService service = new SiteVisitService();
	private List<SiteVisit> sitevisitList = null;
	private List<SiteVisit> sitevisitfilteredList = null;
	private SiteVisit sitevisit = null;
	private LazyDataModel<SiteVisit> dataModel;
	private Doc doc;
	private RejectReasons rejectReason;
	private List<RejectReasons> selectedRejectReason;
	private List<RejectReasonsChild> rejectReasonsChild;
	private RejectReasonsChildService rejectReasonsService = new RejectReasonsChildService();
	private RegionTown rt;
	private Date maxDate = new Date();

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
	 * Initialize method to get all SiteVisit and prepare a for a create of a new
	 * SiteVisit
	 * 
	 * @author TechFinium
	 * @see SiteVisit
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.SITE_VISIT) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			sitevisit = service.findByKey(getSessionUI().getTask().getTargetKey());
			rejectReasonsChild = rejectReasonsService.findByTargetClassAndKey(SiteVisit.class.getName(), sitevisit.getId());
			rt = RegionTownService.instance().findByTown(sitevisit.getCompany().getResidentialAddress().getTown());
		} else {
			prepareNew();
			sitevisitInfo();
		}
		populateMaxDateSelection();
	}

	private void populateMaxDateSelection() {
//		maxDate = GenericUtility.addDaysToDate(maxDate, 1);
		maxDate = new Date();
	}

	/**
	 * Get all SiteVisit for data table
	 * 
	 * @author TechFinium
	 * @see SiteVisit
	 */
	public void sitevisitInfo() {

		dataModel = new LazyDataModel<SiteVisit>() {

			private static final long serialVersionUID = 1L;
			private List<SiteVisit> retorno = new ArrayList<SiteVisit>();

			@Override
			public List<SiteVisit> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allSiteVisit(SiteVisit.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SiteVisit.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SiteVisit obj) {
				return obj.getId();
			}

			@Override
			public SiteVisit getRowData(String rowKey) {
				for (SiteVisit obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SiteVisit into database
	 * 
	 * @author TechFinium
	 * @see SiteVisit
	 */
	public void sitevisitInsert() {
		try {
			service.create(sitevisit, getSessionUI().getActiveUser(), true);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sitevisitInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Insert SiteVisit into database once it was rejected prior
	 * 
	 * @author TechFinium
	 * @see SiteVisit
	 */
	public void sitevisitInsertOnceRejected() {
		try {
			service.createOnceRejected(sitevisit, getSessionUI().getActiveUser(), true, getSessionUI().getTask());
			addInfoMessage(super.getEntryLanguage("update.successful"));
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeNewFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SiteVisit in database
	 * 
	 * @author TechFinium
	 * @see SiteVisit
	 */
	public void sitevisitUpdate() {
		try {
			service.update(this.sitevisit);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			sitevisitInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SiteVisit from database
	 * 
	 * @author TechFinium
	 * @see SiteVisit
	 */
	public void sitevisitDelete() {
		try {
			service.delete(this.sitevisit);
			prepareNew();
			sitevisitInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeTask() {
		try {
			if (getSessionUI().getTask().getProcessRole() == null) {
				service.completeToFirst(sitevisit, getSessionUI().getActiveUser(), getSessionUI().getTask());
			} else {
				service.completeTask(sitevisit, getSessionUI().getActiveUser(), getSessionUI().getTask());
			}
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void approveTask() {
		try {
			service.approveTask(sitevisit, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rejectTask() {
		try {
			service.rejectTask(sitevisit, getSessionUI().getActiveUser(), getSessionUI().getTask());
			rejectReasonsService.createReject(selectedRejectReason, SiteVisit.class.getName(), sitevisit.getId(), getSessionUI().getTask(), "");
			getSessionUI().setTask(null);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create new instance of SiteVisit
	 * 
	 * @author TechFinium
	 * @see SiteVisit
	 */
	public void prepareNew() {
		sitevisit = new SiteVisit();
		// sitevisit.setDocs(docs);
		try {
			service.prepareNewDocs(sitevisit);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.SITE_VISIT);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/*
	 * public List<SelectItem> getSiteVisitDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * sitevisitInfo(); for (SiteVisit ug : sitevisitList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SiteVisit> complete(String desc) {
		List<SiteVisit> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SiteVisit> getSiteVisitList() {
		return sitevisitList;
	}

	public void setSiteVisitList(List<SiteVisit> sitevisitList) {
		this.sitevisitList = sitevisitList;
	}

	public SiteVisit getSitevisit() {
		return sitevisit;
	}

	public void setSitevisit(SiteVisit sitevisit) {
		this.sitevisit = sitevisit;
	}

	public List<SiteVisit> getSiteVisitfilteredList() {
		return sitevisitfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param sitevisitfilteredList
	 *            the new sitevisitfilteredList list
	 * @see SiteVisit
	 */
	public void setSiteVisitfilteredList(List<SiteVisit> sitevisitfilteredList) {
		this.sitevisitfilteredList = sitevisitfilteredList;
	}

	public LazyDataModel<SiteVisit> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SiteVisit> dataModel) {
		this.dataModel = dataModel;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public RejectReasons getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(RejectReasons rejectReason) {
		this.rejectReason = rejectReason;
	}

	public List<RejectReasonsChild> getRejectReasonsChild() {
		return rejectReasonsChild;
	}

	public void setRejectReasonsChild(List<RejectReasonsChild> rejectReasonsChild) {
		this.rejectReasonsChild = rejectReasonsChild;
	}

	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(List<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public RegionTown getRt() {
		return rt;
	}

	public void setRt(RegionTown rt) {
		this.rt = rt;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

}
