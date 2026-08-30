package haj.com.ui;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.CompanyLearnersProgress;
import haj.com.service.CompanyLearnersProgressService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "companylearnersprogressUI")
@ViewScoped
public class CompanyLearnersProgressUI extends AbstractUI {

	private CompanyLearnersProgressService service = new CompanyLearnersProgressService();
	private List<CompanyLearnersProgress> companylearnersprogressList = null;
	private List<CompanyLearnersProgress> companylearnersprogressfilteredList = null;
	private CompanyLearnersProgress companylearnersprogress = null;
	private LazyDataModel<CompanyLearnersProgress> dataModel;

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
	 * Initialize method to get all CompanyLearnersProgress and prepare a for a
	 * create of a new CompanyLearnersProgress
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersProgress
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		companylearnersprogressInfo();
	}

	/**
	 * Get all CompanyLearnersProgress for data table
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersProgress
	 */
	public void companylearnersprogressInfo() {

		dataModel = new LazyDataModel<CompanyLearnersProgress>() {

			private static final long serialVersionUID = 1L;
			private List<CompanyLearnersProgress> retorno = new ArrayList<CompanyLearnersProgress>();

			@Override
			public List<CompanyLearnersProgress> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allCompanyLearnersProgress(CompanyLearnersProgress.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(CompanyLearnersProgress.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearnersProgress obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearnersProgress getRowData(String rowKey) {
				for (CompanyLearnersProgress obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert CompanyLearnersProgress into database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersProgress
	 */
	public void companylearnersprogressInsert() {
		try {
			service.create(this.companylearnersprogress);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearnersprogressInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update CompanyLearnersProgress in database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersProgress
	 */
	public void companylearnersprogressUpdate() {
		try {
			service.update(this.companylearnersprogress);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearnersprogressInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete CompanyLearnersProgress from database
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersProgress
	 */
	public void companylearnersprogressDelete() {
		try {
			service.delete(this.companylearnersprogress);
			prepareNew();
			companylearnersprogressInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of CompanyLearnersProgress
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersProgress
	 */
	public void prepareNew() {
		companylearnersprogress = new CompanyLearnersProgress();
	}

	/*
	 * public List<SelectItem> getCompanyLearnersProgressDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * companylearnersprogressInfo(); for (CompanyLearnersProgress ug :
	 * companylearnersprogressList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<CompanyLearnersProgress> complete(String desc) {
		List<CompanyLearnersProgress> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<CompanyLearnersProgress> getCompanyLearnersProgressList() {
		return companylearnersprogressList;
	}

	public void setCompanyLearnersProgressList(List<CompanyLearnersProgress> companylearnersprogressList) {
		this.companylearnersprogressList = companylearnersprogressList;
	}

	public CompanyLearnersProgress getCompanylearnersprogress() {
		return companylearnersprogress;
	}

	public void setCompanylearnersprogress(CompanyLearnersProgress companylearnersprogress) {
		this.companylearnersprogress = companylearnersprogress;
	}

	public List<CompanyLearnersProgress> getCompanyLearnersProgressfilteredList() {
		return companylearnersprogressfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param companylearnersprogressfilteredList
	 *            the new companylearnersprogressfilteredList list
	 * @see CompanyLearnersProgress
	 */
	public void setCompanyLearnersProgressfilteredList(List<CompanyLearnersProgress> companylearnersprogressfilteredList) {
		this.companylearnersprogressfilteredList = companylearnersprogressfilteredList;
	}

	public LazyDataModel<CompanyLearnersProgress> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearnersProgress> dataModel) {
		this.dataModel = dataModel;
	}

}
