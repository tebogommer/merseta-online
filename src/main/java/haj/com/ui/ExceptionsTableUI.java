package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.ExceptionsTable;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ExceptionsTableService;
import haj.com.service.TestService;

@ManagedBean(name = "exceptionstableUI")
@ViewScoped
public class ExceptionsTableUI extends AbstractUI {

	private ExceptionsTableService service = new ExceptionsTableService();
	private TestService testService = new TestService();
	private List<ExceptionsTable> exceptionstableList = null;
	private List<ExceptionsTable> exceptionstablefilteredList = null;
	private ExceptionsTable exceptionstable = null;
	private LazyDataModel<ExceptionsTable> dataModel;

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
	 * Initialize method to get all ExceptionsTable and prepare a for a create
	 * of a new ExceptionsTable
	 * 
	 * @author TechFinium
	 * @see ExceptionsTable
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		exceptionstableInfo();
	}

	/**
	 * Get all ExceptionsTable for data table
	 * 
	 * @author TechFinium
	 * @see ExceptionsTable
	 */
	public void exceptionstableInfo() {
		dataModel = new LazyDataModel<ExceptionsTable>() {
			private static final long serialVersionUID = 1L;
			private List<ExceptionsTable> list = new ArrayList<>();

			@Override
			public List<ExceptionsTable> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					list = service.allExceptionsTable(ExceptionsTable.class, first, pageSize, sortField, sortOrder,
							filters);
					dataModel.setRowCount(service.count(ExceptionsTable.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}

			@Override
			public Object getRowKey(ExceptionsTable object) {
				return object.getId();
			}

			@Override
			public ExceptionsTable getRowData(String rowKey) {
				for (ExceptionsTable obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}

	/**
	 * Insert ExceptionsTable into database
	 * 
	 * @author TechFinium
	 * @see ExceptionsTable
	 */
	public void exceptionstableInsert() {
		try {
			service.create(this.exceptionstable);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			exceptionstableInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update ExceptionsTable in database
	 * 
	 * @author TechFinium
	 * @see ExceptionsTable
	 */
	public void exceptionstableUpdate() {
		try {
			service.update(this.exceptionstable);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			exceptionstableInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete ExceptionsTable from database
	 * 
	 * @author TechFinium
	 * @see ExceptionsTable
	 */
	public void exceptionstableDelete() {
		try {
			service.delete(this.exceptionstable);
			prepareNew();
			exceptionstableInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runGeneration(){
		try {
			service.runAllExecptions();
			addInfoMessage("Action Underway please wait for email notification for completion");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ExceptionsTable
	 * 
	 * @author TechFinium
	 * @see ExceptionsTable
	 */
	public void prepareNew() {
		exceptionstable = new ExceptionsTable();
	}

	/*
	 * public List<SelectItem> getExceptionsTableDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * exceptionstableInfo(); for (ExceptionsTable ug : exceptionstableList) {
	 * // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); }
	 * return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<ExceptionsTable> complete(String desc) {
		List<ExceptionsTable> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<ExceptionsTable> getExceptionsTableList() {
		return exceptionstableList;
	}

	public void setExceptionsTableList(List<ExceptionsTable> exceptionstableList) {
		this.exceptionstableList = exceptionstableList;
	}

	public ExceptionsTable getExceptionstable() {
		return exceptionstable;
	}

	public void setExceptionstable(ExceptionsTable exceptionstable) {
		this.exceptionstable = exceptionstable;
	}

	public List<ExceptionsTable> getExceptionsTablefilteredList() {
		return exceptionstablefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param exceptionstablefilteredList
	 *            the new exceptionstablefilteredList list
	 * @see ExceptionsTable
	 */
	public void setExceptionsTablefilteredList(List<ExceptionsTable> exceptionstablefilteredList) {
		this.exceptionstablefilteredList = exceptionstablefilteredList;
	}

	public LazyDataModel<ExceptionsTable> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<ExceptionsTable> dataModel) {
		this.dataModel = dataModel;
	}

}
