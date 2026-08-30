package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.BugReport;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.Images;
import haj.com.entity.datamodel.CompanyContactsDatamodel;
import haj.com.entity.datamodel.CompanyUsersDatamodel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.BugReportService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.ImagesService;

// TODO: Auto-generated Javadoc
/**
 * The Class BugReportUI.
 */
@ManagedBean(name = "bugReportExternalUI")
@ViewScoped
public class BugReportExternalUI extends AbstractUI {

	/** The service. */
	private BugReportService service = new BugReportService();

	/** The bugreport list. */
	private List<BugReport> bugreportList = null;

	/** The bugreportfiltered list. */
	private List<BugReport> bugreportfilteredList = null;

	/** The bugreport. */
	private BugReport bugreport = null;

	/** The data model. */
	private LazyDataModel<BugReport> dataModel;
	
	private LazyDataModel<CompanyUsers> dataModelCompanyUsers;

	/** The image. */
	// Image
	private Images image = null;

	/** The image string. */
	private String imageString;

	/** The tempimage string. */
	private String tempimageString;

	/** The image small string. */
	private String imageSmallString;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {
		prepareNew();		
		//populateInformation();
		bugreportInfo();
	}

	private void populateInformation() {
		//dataModelCompanyUsers = new CompanyUsersDatamodel(getSessionUI().getActiveUser());
	}
	public void bugreportInfo() {

		dataModel = new LazyDataModel<BugReport>() {

			private static final long serialVersionUID = 1L;
			private List<BugReport> retorno = new ArrayList<BugReport>();

			@Override
			public List<BugReport> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allBugReport(BugReport.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(BugReport.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(BugReport obj) {
				return obj.getId();
			}

			@Override
			public BugReport getRowData(String rowKey) {
				for (BugReport obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	public void prepareNew() {
		bugreport = new BugReport();
	}

	public void bugreportInsert() {
		try {
			service.create(this.bugreport);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			bugreportInfo();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void bugreportUpdate() {
		try {
			service.update(this.bugreport);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			bugreportInfo();
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void bugreportDelete() {
		try {
			service.delete(this.bugreport);
			prepareNew();
			bugreportInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void submitBug() {
		try {
			this.bugreport.setUser(getSessionUI().getUser());
			service.submitExternalBug(this.bugreport);
			prepareNew();
			addInfoMessage("Bug was submitted successfully");
			bugreportInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void submitSupport() {
		try {
			this.bugreport.setUser(getSessionUI().getUser());
			service.submitSupport(this.bugreport);
			prepareNew();
			addInfoMessage("Bug was submitted successfully");
			bugreportInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void saveImage(FileUploadEvent event) {
		try {
			ImagesService.saveBugReportPic(this.image, event.getFile(), bugreport, false);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the bug report list.
	 *
	 * @return the bug report list
	 */
	public List<BugReport> getBugReportList() {
		return bugreportList;
	}

	/**
	 * Sets the bug report list.
	 *
	 * @param bugreportList
	 *            the new bug report list
	 */
	public void setBugReportList(List<BugReport> bugreportList) {
		this.bugreportList = bugreportList;
	}

	/**
	 * Gets the bugreport.
	 *
	 * @return the bugreport
	 */
	public BugReport getBugreport() {
		return bugreport;
	}

	/**
	 * Sets the bugreport.
	 *
	 * @param bugreport
	 *            the new bugreport
	 */
	public void setBugreport(BugReport bugreport) {
		this.bugreport = bugreport;
	}

	public List<SelectItem> getBugReportDD() {
		List<SelectItem> l = new ArrayList<SelectItem>();
		l.add(new SelectItem(Long.valueOf(-1L), "-------------Select-------------"));
		bugreportInfo();
		for (BugReport ug : bugreportList) {
			// l.add(new SelectItem(ug.getUserGroupId(),
			// ug.getUserGroupDesc()));
		}
		return l;
	}
	
	public List<CompanyUsers> completeCompanyUsers(String desc) {
		CompanyUsersService companyUsersService = new CompanyUsersService();
		List<CompanyUsers> l = null;
		try {
			l = companyUsersService.findByUserAndName(desc, getSessionUI().getActiveUser());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<BugReport> getBugReportfilteredList() {
		return bugreportfilteredList;
	}

	public void setBugReportfilteredList(List<BugReport> bugreportfilteredList) {
		this.bugreportfilteredList = bugreportfilteredList;
	}

	public List<BugReport> complete(String desc) {
		List<BugReport> l = null;
		try {
			l = service.findByName(desc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public LazyDataModel<BugReport> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<BugReport> dataModel) {
		this.dataModel = dataModel;
	}

	public Images getImage() {
		return image;
	}

	public void setImage(Images image) {
		this.image = image;
	}

	public String getImageString() {
		if (this.bugreport != null && this.bugreport.getBugImage() != null) {
			this.imageString = HAJConstants.DOC_SERVER + this.bugreport.getBugImage().getNewFname();
		}
		return imageString;
	}

	public void setImageString(String imageString) {
		this.imageString = imageString;
	}

	public String getImageSmallString() {
		if (this.bugreport != null && this.bugreport.getBugImage() != null) {
			this.imageSmallString = HAJConstants.DOC_SERVER + this.bugreport.getBugImage().getSmallFileName();
		}
		return imageSmallString;
	}

	public void setImageSmallString(String imageSmallString) {
		this.imageSmallString = imageSmallString;
	}

	public LazyDataModel<CompanyUsers> getDataModelCompanyUsers() {
		return dataModelCompanyUsers;
	}

	public void setDataModelCompanyUsers(LazyDataModel<CompanyUsers> dataModelCompanyUsers) {
		this.dataModelCompanyUsers = dataModelCompanyUsers;
	}

}
