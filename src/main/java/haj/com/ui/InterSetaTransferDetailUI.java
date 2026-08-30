package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.InterSetaTransfer;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.HostingCompanyService;
import haj.com.service.InterSetaTransferService;
import haj.com.service.TasksService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "intersetatransferdetailUI")
@ViewScoped
public class InterSetaTransferDetailUI extends AbstractUI {

	private CompanyService companyService = new CompanyService(getResourceBundle());
	private InterSetaTransfer interSetaTransfer;
	private LazyDataModel<InterSetaTransfer> dataModel;
	private InterSetaTransferService service = new InterSetaTransferService();
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();
	private Users formUser;
	private Company company;

	/** The search company UI. */
	@ManagedProperty(value = "#{searchCompanyUI}")
	private SearchCompanyUI searchCompanyUI;
	@ManagedProperty(value = "#{searchUserPassportOrIdUI}")
	private SearchUserPassportOrIdUI searchUserPassportOrIdUI;
	/** The doc. */
	private Doc doc;
	private boolean displayButtons;

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
		getSearchUserPassportOrIdUI().setObject(this);
		getSearchCompanyUI().setObject(this);
		if (super.getParameter("id", false) != null && getSessionUI().getTask() != null
				&& getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.INTER_SETA_TRANSFER) {
			this.interSetaTransfer = service.findByKey(getSessionUI().getTask().getTargetKey());
		} else if (super.getParameter("id", false) != null) {
			this.interSetaTransfer = service.findByGuid(super.getParameter("id", false).toString());
		} else {
			transferInfo();
			prepareNew();
		}
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.INTER_SETA_TRANSFER) {
//			sessionUI.task.processRole.rolePermission eq UserPermissionEnum.Approve or sessionUI.task.processRole.rolePermission eq UserPermissionEnum.FinalApproval
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			displayButtons = getSessionUI().getTask().getProcessRole().getHostingCompanyProcess().getWorkflowProcess() == ConfigDocProcessEnum.INTER_SETA_TRANSFER && (getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.Approve || getSessionUI().getTask().getProcessRole().getRolePermission() == UserPermissionEnum.FinalApproval); 
		}else if (TasksService.instance().findUserCountForTask(ConfigDocProcessEnum.INTER_SETA_TRANSFER, getSessionUI().getActiveUser().getId()) > 0) {
			displayButtons = true;
		}
	}

	public void viewTransfer() {
		super.redirect("/pages/intersetatransfer/transferrequestsdetail.jsf?id=" + interSetaTransfer.getGuid());
	}

	/**
	 * Get all InterSetaTransfer for data table.
	 *
	 * @author TechFinium
	 * @see InterSetaTransfer
	 */
	public void transferInfo() {

		dataModel = new LazyDataModel<InterSetaTransfer>() {

			private static final long serialVersionUID = 1L;
			private List<InterSetaTransfer> retorno = new ArrayList<InterSetaTransfer>();

			@Override
			public List<InterSetaTransfer> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allInterSetaTransfer(InterSetaTransfer.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(InterSetaTransfer.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(InterSetaTransfer obj) {
				return obj.getId();
			}

			@Override
			public InterSetaTransfer getRowData(String rowKey) {
				for (InterSetaTransfer obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	private void prepareNew() {
		interSetaTransfer = new InterSetaTransfer();
		company = null;
		formUser = null;
	}

	public void saveTransfer() {
		try {
			service.approveTransfer(interSetaTransfer, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			super.runClientSideUpdate("intersetaForm");
			super.ajaxRedirect("/pages/intersetatransfer/transferrequests.jsf");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectTransfer() {
		try {
			service.rejectTransfer(interSetaTransfer, getSessionUI().getActiveUser(), getSessionUI().getTask());
			getSessionUI().setTask(null);
			super.runClientSideUpdate("intersetaForm");
			super.ajaxRedirect("/pages/intersetatransfer/transferrequests.jsf");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void doneUserBit() {
		this.formUser.setRegFieldsDone(true);
	}

	/**
	 * Store file user.
	 *
	 * @param event
	 *            the event
	 */
	public void storeFileUser(FileUploadEvent event) {
		doc.setData(event.getFile().getContents());
		doc.setOriginalFname(event.getFile().getFileName());
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		super.runClientSideExecute("PF('dlgUploadUser').hide()");
	}

	@Override
	public void callBackMethod(Object object) {
		try {
			if (object instanceof Users) {
				this.formUser = (Users) object;
				companyService.prepareNewRegistrationType(ConfigDocProcessEnum.INTER_SETA_TRANSFER, null, this.formUser,
						CompanyUserTypeEnum.User);
			} else if (object instanceof Company) {
				this.company = (Company) object;
				if (company.getId() != null) {
					addWarningMessage(getEntryLanguage("company.exists"));
					this.company = null;
				} else {
					companyService.prepareNewRegistrationType(ConfigDocProcessEnum.INTER_SETA_TRANSFER, this.company,
							null, CompanyUserTypeEnum.Company);
				}
			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void storeFile(FileUploadEvent event) {
		doc.setCompany(company);
		doc.setData(event.getFile().getContents());
		doc.setOriginalFname(event.getFile().getFileName());
		doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		super.runClientSideExecute("PF('dlgUpload').hide()");
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public SearchCompanyUI getSearchCompanyUI() {
		return searchCompanyUI;
	}

	public void setSearchCompanyUI(SearchCompanyUI searchCompanyUI) {
		this.searchCompanyUI = searchCompanyUI;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public InterSetaTransfer getInterSetaTransfer() {
		return interSetaTransfer;
	}

	public void setInterSetaTransfer(InterSetaTransfer interSetaTransfer) {
		this.interSetaTransfer = interSetaTransfer;
	}

	public Users getFormUser() {
		return formUser;
	}

	public void setFormUser(Users formUser) {
		this.formUser = formUser;
	}

	public SearchUserPassportOrIdUI getSearchUserPassportOrIdUI() {
		return searchUserPassportOrIdUI;
	}

	public void setSearchUserPassportOrIdUI(SearchUserPassportOrIdUI searchUserPassportOrIdUI) {
		this.searchUserPassportOrIdUI = searchUserPassportOrIdUI;
	}

	public LazyDataModel<InterSetaTransfer> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<InterSetaTransfer> dataModel) {
		this.dataModel = dataModel;
	}

	public boolean isDisplayButtons() {
		return displayButtons;
	}

	public void setDisplayButtons(boolean displayButtons) {
		this.displayButtons = displayButtons;
	}

}
