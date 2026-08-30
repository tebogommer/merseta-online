package haj.com.ui;

import java.util.ArrayList;
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

import haj.com.entity.ActiveContracts;
import haj.com.entity.Doc;
import haj.com.entity.ProjectImplementationPlan;
import haj.com.entity.SDFCompany;
import haj.com.entity.datamodel.ProjectImplementationPlanDatamodel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.SDFCompanyService;

@ManagedBean(name = "projectimplementationplanUI")
@ViewScoped
public class ProjectImplementationPlanUI extends AbstractUI {

	private ProjectImplementationPlanService service = new ProjectImplementationPlanService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private List<ProjectImplementationPlan> projectimplementationplanList = null;
	private List<ProjectImplementationPlan> projectimplementationplanfilteredList = null;
	private ProjectImplementationPlan projectimplementationplan = null;
	private LazyDataModel<ProjectImplementationPlan> dataModel;
	private LazyDataModel<SDFCompany> dataModelSDFCompany;
	private SDFCompany sdfCompany;
	private Doc doc;

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

	public void onRowSelect(SelectEvent event) {
		projectimplementationplanInfo();
	}

	/**
	 * Initialize method to get all ProjectImplementationPlan and prepare a for a
	 * create of a new ProjectImplementationPlan
	 * 
	 * @author TechFinium
	 * @see ProjectImplementationPlan
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		companysdfInfo();
		projectImplementationPlanDatamodelInfo();
	}

	
	/**
	 * Get all ActiveContracts for data table
	 * 
	 * @author TechFinium
	 * @see ActiveContracts
	 */
	public void projectImplementationPlanDatamodelInfo() {
		dataModel = new ProjectImplementationPlanDatamodel();
	}
	/**
	 * Get all ProjectImplementationPlan for data table
	 * 
	 * @author TechFinium
	 * @see ProjectImplementationPlan
	 */
	public void projectimplementationplanInfo() {
		try {
			projectimplementationplanList = service.findByCompany(sdfCompany.getCompany());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void companysdfInfo() {

		dataModelSDFCompany = new LazyDataModel<SDFCompany>() {
			private static final long serialVersionUID = 1L;
			private List<SDFCompany> retorno = new ArrayList<SDFCompany>();

			@Override
			public List<SDFCompany> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (getSessionUI().getActiveUser().getAdmin() == null || !getSessionUI().getActiveUser().getAdmin()) {
						retorno = sdfCompanyService.allCompanyFromSDFWherePrimary(SDFCompany.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getActiveUser());
						dataModelSDFCompany.setRowCount(sdfCompanyService.allCompanyFromSDFCountWherePrimary(filters, getSessionUI().getActiveUser()).intValue());
					} else {
						retorno = sdfCompanyService.allSDFCompany(SDFCompany.class, first, pageSize, sortField, sortOrder, filters);
						dataModelSDFCompany.setRowCount(sdfCompanyService.count(SDFCompany.class, filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SDFCompany obj) {
				return obj.getId();
			}

			@Override
			public SDFCompany getRowData(String rowKey) {
				for (SDFCompany obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	public void saveProjectImplementationPlan() {
		try {
			service.create(this.projectimplementationplanList);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			projectimplementationplanInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Insert ProjectImplementationPlan into database
	 * 
	 * @author TechFinium
	 * @see ProjectImplementationPlan
	 */
	public void projectimplementationplanInsert() {
		try {
			service.create(this.projectimplementationplan);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			projectimplementationplanInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update ProjectImplementationPlan in database
	 * 
	 * @author TechFinium
	 * @see ProjectImplementationPlan
	 */
	public void projectimplementationplanUpdate() {
		try {
			service.update(this.projectimplementationplan);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			projectimplementationplanInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete ProjectImplementationPlan from database
	 * 
	 * @author TechFinium
	 * @see ProjectImplementationPlan
	 */
	public void projectimplementationplanDelete() {
		try {
			service.delete(this.projectimplementationplan);
			prepareNew();
			projectimplementationplanInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ProjectImplementationPlan
	 * 
	 * @author TechFinium
	 * @see ProjectImplementationPlan
	 */
	public void prepareNew() {
		projectimplementationplan = new ProjectImplementationPlan();
		try {
			service.prepareNewRegistration(projectimplementationplan);
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
	}

	/*
	 * public List<SelectItem> getProjectImplementationPlanDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * projectimplementationplanInfo(); for (ProjectImplementationPlan ug :
	 * projectimplementationplanList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<ProjectImplementationPlan> complete(String desc) {
		List<ProjectImplementationPlan> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ProjectImplementationPlan> getProjectImplementationPlanList() {
		return projectimplementationplanList;
	}

	public void setProjectImplementationPlanList(List<ProjectImplementationPlan> projectimplementationplanList) {
		this.projectimplementationplanList = projectimplementationplanList;
	}

	public ProjectImplementationPlan getProjectimplementationplan() {
		return projectimplementationplan;
	}

	public void setProjectimplementationplan(ProjectImplementationPlan projectimplementationplan) {
		this.projectimplementationplan = projectimplementationplan;
	}

	public List<ProjectImplementationPlan> getProjectImplementationPlanfilteredList() {
		return projectimplementationplanfilteredList;
	}
	
	public void storeFilePIP(FileUploadEvent event) {
		try {
			doc = projectimplementationplan.getDocs().get(0);
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setTargetClass(projectimplementationplan.getClass().getName());
			doc.setTargetKey(projectimplementationplan.getId());
			if (doc.getId() == null) {
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), getSessionUI().getActiveUser());
			} else {
				DocService.instance().uploadNewVersion(doc, getSessionUI().getActiveUser());
			}
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param projectimplementationplanfilteredList
	 *            the new projectimplementationplanfilteredList list
	 * @see ProjectImplementationPlan
	 */
	public void setProjectImplementationPlanfilteredList(List<ProjectImplementationPlan> projectimplementationplanfilteredList) {
		this.projectimplementationplanfilteredList = projectimplementationplanfilteredList;
	}

	public LazyDataModel<ProjectImplementationPlan> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ProjectImplementationPlan> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<SDFCompany> getDataModelSDFCompany() {
		return dataModelSDFCompany;
	}

	public void setDataModelSDFCompany(LazyDataModel<SDFCompany> dataModelSDFCompany) {
		this.dataModelSDFCompany = dataModelSDFCompany;
	}

	public SDFCompany getSdfCompany() {
		return sdfCompany;
	}

	public void setSdfCompany(SDFCompany sdfCompany) {
		this.sdfCompany = sdfCompany;
	}

	public List<ProjectImplementationPlan> getProjectimplementationplanList() {
		return projectimplementationplanList;
	}

	public void setProjectimplementationplanList(List<ProjectImplementationPlan> projectimplementationplanList) {
		this.projectimplementationplanList = projectimplementationplanList;
	}

	public List<ProjectImplementationPlan> getProjectimplementationplanfilteredList() {
		return projectimplementationplanfilteredList;
	}

	public void setProjectimplementationplanfilteredList(
			List<ProjectImplementationPlan> projectimplementationplanfilteredList) {
		this.projectimplementationplanfilteredList = projectimplementationplanfilteredList;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

}
