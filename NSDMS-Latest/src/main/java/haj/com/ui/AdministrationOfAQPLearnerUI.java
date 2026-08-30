package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;

import haj.com.entity.AdministrationOfAQP;
import haj.com.entity.AdministrationOfAQPLearners;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Doc;
import haj.com.entity.datamodel.AdministrationOfAQPModel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AdministrationOfAQPService;
import haj.com.service.CompanyLearnersService;
import haj.com.service.DocService;

@ManagedBean(name = "administrationofaqplearnerUI")
@ViewScoped
public class AdministrationOfAQPLearnerUI extends AbstractUI {

	private AdministrationOfAQPService service = new AdministrationOfAQPService();
	private List<AdministrationOfAQP> administrationofaqpfilteredList = null;
	private AdministrationOfAQP administrationofaqp = null;
	private AdministrationOfAQPLearners administrationOfAQPLearners = null;
	private Doc doc;
	private LazyDataModel<AdministrationOfAQP> dataModel;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();

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
	 * Initialize method to get all AdministrationOfAQP and prepare a for a create
	 * of a new AdministrationOfAQP
	 * 
	 * @author TechFinium
	 * @see AdministrationOfAQP
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		administrationofaqpInfo();
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
				// doc.setTargetKey(administrationofaqp.getId());
				doc.setTargetClass(AdministrationOfAQPLearners.class.getName());
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepnewLearner() {
		try {
			List<CompanyLearners> companyLearners = companyLearnersService.findUserAndQualification(getSessionUI().getActiveUser(), administrationofaqp.getQualification());
			if (companyLearners.size() > 1) {
				this.administrationOfAQPLearners = new AdministrationOfAQPLearners();
				administrationOfAQPLearners.setCompanyLearners(companyLearners.get(0));
				administrationOfAQPLearners.setAdministrationOfAQP(administrationofaqp);
				service.prepareNewRegistration(administrationOfAQPLearners);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void saveRegistration() {
		try {
			service.saveNewRegistration(administrationOfAQPLearners, getSessionUI().getActiveUser());
			this.administrationOfAQPLearners = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		
	}

	/**
	 * Get all AdministrationOfAQP for data table
	 * 
	 * @author TechFinium
	 * @see AdministrationOfAQP
	 */
	public void administrationofaqpInfo() {
		dataModel = new AdministrationOfAQPModel(getSessionUI().getActiveUser());
	}

	public void prepareNew() {
		administrationofaqp = new AdministrationOfAQP();
	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<AdministrationOfAQP> complete(String desc) {
		List<AdministrationOfAQP> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public AdministrationOfAQP getAdministrationofaqp() {
		return administrationofaqp;
	}

	public void setAdministrationofaqp(AdministrationOfAQP administrationofaqp) {
		this.administrationofaqp = administrationofaqp;
	}

	public List<AdministrationOfAQP> getAdministrationOfAQPfilteredList() {
		return administrationofaqpfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param administrationofaqpfilteredList
	 *            the new administrationofaqpfilteredList list
	 * @see AdministrationOfAQP
	 */
	public void setAdministrationOfAQPfilteredList(List<AdministrationOfAQP> administrationofaqpfilteredList) {
		this.administrationofaqpfilteredList = administrationofaqpfilteredList;
	}

	public LazyDataModel<AdministrationOfAQP> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AdministrationOfAQP> dataModel) {
		this.dataModel = dataModel;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public AdministrationOfAQPLearners getAdministrationOfAQPLearners() {
		return administrationOfAQPLearners;
	}

	public void setAdministrationOfAQPLearners(AdministrationOfAQPLearners administrationOfAQPLearners) {
		this.administrationOfAQPLearners = administrationOfAQPLearners;
	}

}
