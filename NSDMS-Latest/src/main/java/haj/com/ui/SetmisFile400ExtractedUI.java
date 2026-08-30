package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.SetmisFile400Extracted;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SetmisFile400ExtractedService;

@ManagedBean(name = "setmisfile400extractedUI")
@ViewScoped
public class SetmisFile400ExtractedUI extends AbstractUI {

	private SetmisFile400ExtractedService service = new SetmisFile400ExtractedService();
	private List<SetmisFile400Extracted> setmisfile400extractedList = null;
	private List<SetmisFile400Extracted> setmisfile400extractedfilteredList = null;
	private SetmisFile400Extracted setmisfile400extracted = null;
	private LazyDataModel<SetmisFile400Extracted> dataModel;

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
	 * Initialize method to get all SetmisFile400Extracted and prepare a for a
	 * create of a new SetmisFile400Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile400Extracted
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		setmisfile400extractedInfo();
	}

	/**
	 * Get all SetmisFile400Extracted for data table
	 * 
	 * @author TechFinium
	 * @see SetmisFile400Extracted
	 */
	public void setmisfile400extractedInfo() {
		// dataModel = new SetmisFile400ExtractedDatamodel();
		dataModelInfo();
	}

	public void dataModelInfo() {
		dataModel = new LazyDataModel<SetmisFile400Extracted>() {
			private static final long serialVersionUID = 1L;
			private List<SetmisFile400Extracted> retorno = new ArrayList<SetmisFile400Extracted>();

			@Override
			public List<SetmisFile400Extracted> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSetmisFile400Extracted(SetmisFile400Extracted.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SetmisFile400Extracted.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SetmisFile400Extracted obj) {
				return obj.getId();
			}

			@Override
			public SetmisFile400Extracted getRowData(String rowKey) {
				for (SetmisFile400Extracted obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SetmisFile400Extracted into database
	 * 
	 * @author TechFinium
	 * @see SetmisFile400Extracted
	 */
	public void setmisfile400extractedInsert() {
		try {
			service.create(this.setmisfile400extracted);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile400extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SetmisFile400Extracted in database
	 * 
	 * @author TechFinium
	 * @see SetmisFile400Extracted
	 */
	public void setmisfile400extractedUpdate() {
		try {
			service.update(this.setmisfile400extracted);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile400extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SetmisFile400Extracted from database
	 * 
	 * @author TechFinium
	 * @see SetmisFile400Extracted
	 */
	public void setmisfile400extractedDelete() {
		try {
			service.delete(this.setmisfile400extracted);
			prepareNew();
			setmisfile400extractedInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SetmisFile400Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile400Extracted
	 */
	public void prepareNew() {
		setmisfile400extracted = new SetmisFile400Extracted();
	}

	/*
	 * public List<SelectItem> getSetmisFile400ExtractedDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * setmisfile400extractedInfo(); for (SetmisFile400Extracted ug :
	 * setmisfile400extractedList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SetmisFile400Extracted> complete(String desc) {
		List<SetmisFile400Extracted> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SetmisFile400Extracted> getSetmisFile400ExtractedList() {
		return setmisfile400extractedList;
	}

	public void setSetmisFile400ExtractedList(List<SetmisFile400Extracted> setmisfile400extractedList) {
		this.setmisfile400extractedList = setmisfile400extractedList;
	}

	public SetmisFile400Extracted getSetmisfile400extracted() {
		return setmisfile400extracted;
	}

	public void setSetmisfile400extracted(SetmisFile400Extracted setmisfile400extracted) {
		this.setmisfile400extracted = setmisfile400extracted;
	}

	public List<SetmisFile400Extracted> getSetmisFile400ExtractedfilteredList() {
		return setmisfile400extractedfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param setmisfile400extractedfilteredList
	 *            the new setmisfile400extractedfilteredList list
	 * @see SetmisFile400Extracted
	 */
	public void setSetmisFile400ExtractedfilteredList(List<SetmisFile400Extracted> setmisfile400extractedfilteredList) {
		this.setmisfile400extractedfilteredList = setmisfile400extractedfilteredList;
	}

	public LazyDataModel<SetmisFile400Extracted> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SetmisFile400Extracted> dataModel) {
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
