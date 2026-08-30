package haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Doc;
import haj.com.service.ActiveContractDetailService;
import haj.com.service.DocService;
import haj.com.service.TasksService;
import haj.com.entity.datamodel.ActiveContractDetailDatamodel;
import haj.com.entity.enums.ConfigDocProcessEnum;

import javax.faces.model.SelectItem;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "activecontractdetailapprovalUI")
@ViewScoped
public class ActiveContractDetailApprovalUI extends AbstractUI {

	private ActiveContractDetailService service = new ActiveContractDetailService();
	private List<ActiveContractDetail> activecontractdetailList = null;
	private List<ActiveContractDetail> activecontractdetailfilteredList = null;
	private ActiveContractDetail activecontractdetail = null;
	private LazyDataModel<ActiveContractDetail> dataModel;
	private ActiveContracts activecontracts = null;
	private Doc doc;

	/**
	 * @return the activecontracts
	 */
	public ActiveContracts getActivecontracts() {
		return activecontracts;
	}

	/**
	 * @param activecontracts
	 *            the activecontracts to set
	 */
	public void setActivecontracts(ActiveContracts activecontracts) {
		this.activecontracts = activecontracts;
	}

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
	 * Initialize method to get all ActiveContractDetail and prepare a for a create
	 * of a new ActiveContractDetail
	 * 
	 * @author TechFinium
	 * @see ActiveContractDetail
	 * @throws Exception
	 *             the exception
	 */
	
	
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.CONTRACT_DETAIL) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			this.activecontractdetail = service.findByKey(getSessionUI().getTask().getTargetKey());
			service.prepareNewRegistration(activecontractdetail);
		} else {
			prepareNew();
			activecontractdetailInfo();
		}
	}

	/**
	 * Get all ActiveContractDetail for data table
	 * 
	 * @author TechFinium
	 * @see ActiveContractDetail
	 */
	public void activecontractdetailInfo() {
		dataModel = new ActiveContractDetailDatamodel();
	}

	/**
	 * Insert ActiveContractDetail into database
	 * 
	 * @author TechFinium
	 * @see ActiveContractDetail
	 */
	public void activecontractdetailInsert() {
		try {
			service.calculateTotals(activecontractdetail);
			service.create(this.activecontractdetail);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			activecontractdetailInfo();
			super.runClientSideExecute("PF('newDetail').hide()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update ActiveContractDetail in database
	 * 
	 * @author TechFinium
	 * @see ActiveContractDetail
	 */
	public void activecontractdetailUpdate() {
		try {
			service.update(this.activecontractdetail);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			activecontractdetailInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete ActiveContractDetail from database
	 * 
	 * @author TechFinium
	 * @see ActiveContractDetail
	 */
	public void activecontractdetailDelete() {
		try {
			service.delete(this.activecontractdetail);
			prepareNew();
			activecontractdetailInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ActiveContractDetail
	 * 
	 * @author TechFinium
	 * @see ActiveContractDetail
	 */
	public void prepareNew() {
		activecontractdetail = new ActiveContractDetail();
		activecontractdetail.setActiveContracts(activecontracts);
	}

	/*
	 * public List<SelectItem> getActiveContractDetailDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * activecontractdetailInfo(); for (ActiveContractDetail ug :
	 * activecontractdetailList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	public void completeWorkflow() {
		try {
			service.completeWorkflow(activecontractdetail, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveWorkflow() {
		try {
			service.approveWorkflow(activecontractdetail, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectWorkflow() {
		try {
			service.rejectWorkflow(activecontractdetail, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveWorkflow() {
		try {
			service.finalApproveWorkflow(activecontractdetail, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectWorkflow() {
		try {
			service.finalRejectWorkflow(activecontractdetail, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				doc.setTargetKey(activecontractdetail.getId());
				doc.setTargetClass(ActiveContractDetail.class.getName());
				if (doc.getId() == null) DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<ActiveContractDetail> complete(String desc) {
		List<ActiveContractDetail> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ActiveContractDetail> getActiveContractDetailList() {
		return activecontractdetailList;
	}

	public void setActiveContractDetailList(List<ActiveContractDetail> activecontractdetailList) {
		this.activecontractdetailList = activecontractdetailList;
	}

	public ActiveContractDetail getActivecontractdetail() {
		return activecontractdetail;
	}

	public void setActivecontractdetail(ActiveContractDetail activecontractdetail) {
		this.activecontractdetail = activecontractdetail;
	}

	public List<ActiveContractDetail> getActiveContractDetailfilteredList() {
		return activecontractdetailfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param activecontractdetailfilteredList
	 *            the new activecontractdetailfilteredList list
	 * @see ActiveContractDetail
	 */
	public void setActiveContractDetailfilteredList(List<ActiveContractDetail> activecontractdetailfilteredList) {
		this.activecontractdetailfilteredList = activecontractdetailfilteredList;
	}

	public LazyDataModel<ActiveContractDetail> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ActiveContractDetail> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * @param doc the doc to set
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

}
