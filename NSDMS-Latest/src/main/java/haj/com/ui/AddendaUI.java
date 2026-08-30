package haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Addenda;
import haj.com.entity.Doc;
import haj.com.service.ActiveContractsService;
import haj.com.service.AddendaService;
import haj.com.service.DocService;
import haj.com.entity.datamodel.AddendaDatamodel;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.shaded.commons.io.FilenameUtils;

import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "addendaUI")
@ViewScoped
public class AddendaUI extends AbstractUI {

	private AddendaService         service                = new AddendaService();
	private ActiveContractsService activeContractsService = new ActiveContractsService();
	private List<Addenda>          addendaList            = null;
	private List<Addenda>          addendafilteredList    = null;
	private Addenda                addenda                = null;
	private ActiveContracts        activeContracts        = null;
	private LazyDataModel<Addenda> dataModel;
	private Doc                    doc;

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
		activeContracts = activeContractsService.findByWSP(getSessionUI().getWspSession());
		prepareNew();
		addendaInfo();
	}

	public void addendaInfo() {
		dataModel = new AddendaDatamodel(activeContracts);
	}

	public void prepDoc() {
		doc = new Doc();
	}

	// store document
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			doc.setTargetClass(addenda.getClass().getName());
			doc.setTargetKey(addenda.getId());
			if (doc.getId() != null) {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			} else {
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			}
			super.runClientSideExecute("PF('addendadlgUpload').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addendaInsert() {
		try {
			service.create(this.addenda);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			addendaInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addendaUpdate() {
		try {
			service.update(this.addenda);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			addendaInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void addendaDelete() {
		try {
			service.delete(this.addenda);
			prepareNew();
			addendaInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepareNew() {
		addenda = new Addenda();
		addenda.setActiveContracts(activeContracts);
	}

	public List<Addenda> complete(String desc) {
		List<Addenda> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Addenda> getAddendaList() {
		return addendaList;
	}

	public void setAddendaList(List<Addenda> addendaList) {
		this.addendaList = addendaList;
	}

	public Addenda getAddenda() {
		return addenda;
	}

	public void setAddenda(Addenda addenda) {
		this.addenda = addenda;
	}

	public List<Addenda> getAddendafilteredList() {
		return addendafilteredList;
	}

	public void setAddendafilteredList(List<Addenda> addendafilteredList) {
		this.addendafilteredList = addendafilteredList;
	}

	public LazyDataModel<Addenda> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Addenda> dataModel) {
		this.dataModel = dataModel;
	}

	public ActiveContracts getActiveContracts() {
		return activeContracts;
	}

	public void setActiveContracts(ActiveContracts activeContracts) {
		this.activeContracts = activeContracts;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

}
