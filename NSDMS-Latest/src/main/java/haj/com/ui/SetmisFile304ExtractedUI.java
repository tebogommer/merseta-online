package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.SetmisFile100Extracted;
import haj.com.entity.SetmisFile304Extracted;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SetmisFile304ExtractedService;

@ManagedBean(name = "setmisfile304extractedUI")
@ViewScoped
public class SetmisFile304ExtractedUI extends AbstractUI {

	private SetmisFile304ExtractedService service = new SetmisFile304ExtractedService();
	private List<SetmisFile304Extracted> setmisfile304extractedList = null;
	private List<SetmisFile304Extracted> setmisfile304extractedfilteredList = null;
	private SetmisFile304Extracted setmisfile304extracted = null;
	private LazyDataModel<SetmisFile304Extracted> dataModel;

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
	 * Initialize method to get all SetmisFile304Extracted and prepare a for a
	 * create of a new SetmisFile304Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile304Extracted
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		setmisfile304extractedInfo();
	}

	/**
	 * Get all SetmisFile304Extracted for data table
	 * 
	 * @author TechFinium
	 * @see SetmisFile304Extracted
	 */
	public void setmisfile304extractedInfo() {
		// dataModel = new SetmisFile304ExtractedDatamodel();
		dataModelInfo();
	}

	public void dataModelInfo() {
		dataModel = new LazyDataModel<SetmisFile304Extracted>() {
			private static final long serialVersionUID = 1L;
			private List<SetmisFile304Extracted> retorno = new ArrayList<SetmisFile304Extracted>();

			@Override
			public List<SetmisFile304Extracted> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSetmisFile304Extracted(SetmisFile304Extracted.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SetmisFile304Extracted.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SetmisFile304Extracted obj) {
				return obj.getId();
			}

			@Override
			public SetmisFile304Extracted getRowData(String rowKey) {
				for (SetmisFile304Extracted obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SetmisFile304Extracted into database
	 * 
	 * @author TechFinium
	 * @see SetmisFile304Extracted
	 */
	public void setmisfile304extractedInsert() {
		try {
			service.create(this.setmisfile304extracted);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile304extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SetmisFile304Extracted in database
	 * 
	 * @author TechFinium
	 * @see SetmisFile304Extracted
	 */
	public void setmisfile304extractedUpdate() {
		try {
			service.update(this.setmisfile304extracted);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile304extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SetmisFile304Extracted from database
	 * 
	 * @author TechFinium
	 * @see SetmisFile304Extracted
	 */
	public void setmisfile304extractedDelete() {
		try {
			service.delete(this.setmisfile304extracted);
			prepareNew();
			setmisfile304extractedInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SetmisFile304Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile304Extracted
	 */
	public void prepareNew() {
		setmisfile304extracted = new SetmisFile304Extracted();
	}

	/*
	 * public List<SelectItem> getSetmisFile304ExtractedDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * setmisfile304extractedInfo(); for (SetmisFile304Extracted ug :
	 * setmisfile304extractedList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SetmisFile304Extracted> complete(String desc) {
		List<SetmisFile304Extracted> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SetmisFile304Extracted> getSetmisFile304ExtractedList() {
		return setmisfile304extractedList;
	}

	public void setSetmisFile304ExtractedList(List<SetmisFile304Extracted> setmisfile304extractedList) {
		this.setmisfile304extractedList = setmisfile304extractedList;
	}

	public SetmisFile304Extracted getSetmisfile304extracted() {
		return setmisfile304extracted;
	}

	public void setSetmisfile304extracted(SetmisFile304Extracted setmisfile304extracted) {
		this.setmisfile304extracted = setmisfile304extracted;
	}

	public List<SetmisFile304Extracted> getSetmisFile304ExtractedfilteredList() {
		return setmisfile304extractedfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param setmisfile304extractedfilteredList
	 *            the new setmisfile304extractedfilteredList list
	 * @see SetmisFile304Extracted
	 */
	public void setSetmisFile304ExtractedfilteredList(List<SetmisFile304Extracted> setmisfile304extractedfilteredList) {
		this.setmisfile304extractedfilteredList = setmisfile304extractedfilteredList;
	}

	public LazyDataModel<SetmisFile304Extracted> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SetmisFile304Extracted> dataModel) {
		this.dataModel = dataModel;
	}

	public void populateEntity() {
		try {
			service.populateEntity();
			dataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void deleteAllEntries() {
		try {
			service.deleteAllEntries();
			dataModelInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void downloadData() {
		try {
			service.downloadData();
			addInfoMessage("Action complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

}
