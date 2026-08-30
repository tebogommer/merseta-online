package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Doc;
import haj.com.entity.lookup.Modules;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.lookup.ModulesService;

@ManagedBean(name = "modulesUI")
@ViewScoped
public class ModulesUI extends AbstractUI {

	private ModulesService service = new ModulesService();
	private List<Modules> modulesList = null;
	private List<Modules> modulesfilteredList = null;
	private Modules modules = null;
	private LazyDataModel<Modules> dataModel;
	private List<UnitStandards> unitStandards = null;
	private UnitStandards unitStandard = null;
	private Doc doc;

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all Modules and prepare a for a create of a new
	 * Modules
	 * 
	 * @author TechFinium
	 * @see Modules
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		modulesInfo();
	}

	/**
	 * Get all Modules for data table
	 * 
	 * @author TechFinium
	 * @see Modules
	 */
	public void modulesInfo() {

		dataModel = new LazyDataModel<Modules>() {

			private static final long serialVersionUID = 1L;
			private List<Modules> retorno = new ArrayList<Modules>();

			@Override
			public List<Modules> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allModules(Modules.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(Modules.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Modules obj) {
				return obj.getId();
			}

			@Override
			public Modules getRowData(String rowKey) {
				for (Modules obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Modules into database
	 * 
	 * @author TechFinium
	 * @see Modules
	 */
	public void modulesInsert() {
		try {
			service.create(this.modules, unitStandards);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			modulesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Modules in database
	 * 
	 * @author TechFinium
	 * @see Modules
	 */
	public void modulesUpdate() {
		try {
			service.update(this.modules);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			modulesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Modules from database
	 * 
	 * @author TechFinium
	 * @see Modules
	 */
	public void modulesDelete() {
		try {
			service.delete(this.modules);
			prepareNew();
			modulesInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Modules
	 * 
	 * @author TechFinium
	 * @see Modules
	 */
	public void prepareNew() {
		modules = new Modules();
		unitStandards = new ArrayList<>();
	}

	/*
	 * public List<SelectItem> getModulesDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * modulesInfo(); for (Modules ug : modulesList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Modules> complete(String desc) {
		List<Modules> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void prepModules() {
		this.unitStandards = this.modules.getModulesUnitStandards().stream().map(x -> x.getUnitStandards()).collect(Collectors.toList());
	}

	public void addUnitStandardToList() {
		this.unitStandards.add(unitStandard);
		this.unitStandard = null;
	}

	public void removeUnitStandardFromList() {
		this.unitStandards.remove(unitStandard);
		this.unitStandard = null;
	}

	public void prepDoc() {

		doc = new Doc(modules);
		// doc.setModules();
	}

	/**
	 * Store file.
	 *
	 * @param event
	 *            the event
	 */
	public void storeFile(FileUploadEvent event) {
		try {
			if (doc.getId() == null) {
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
				modulesInfo();

			} else {
				doc.setData(event.getFile().getContents());
				doc.setOriginalFname(event.getFile().getFileName());
				doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
				modulesInfo();
			}
			super.runClientSideExecute("PF('dlgUpload').hide()");
			super.runClientSideUpdate("mainForm");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<Modules> getModulesList() {
		return modulesList;
	}

	public void setModulesList(List<Modules> modulesList) {
		this.modulesList = modulesList;
	}

	public Modules getModules() {
		return modules;
	}

	public void setModules(Modules modules) {
		this.modules = modules;
	}

	public List<Modules> getModulesfilteredList() {
		return modulesfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param modulesfilteredList
	 *            the new modulesfilteredList list
	 * @see Modules
	 */
	public void setModulesfilteredList(List<Modules> modulesfilteredList) {
		this.modulesfilteredList = modulesfilteredList;
	}

	public LazyDataModel<Modules> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Modules> dataModel) {
		this.dataModel = dataModel;
	}

	public List<UnitStandards> getUnitStandards() {
		return unitStandards;
	}

	public void setUnitStandards(List<UnitStandards> unitStandards) {
		this.unitStandards = unitStandards;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
	}

}
