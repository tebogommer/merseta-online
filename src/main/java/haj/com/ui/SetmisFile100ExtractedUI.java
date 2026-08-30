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
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SetmisFile100ExtractedService;

@ManagedBean(name = "setmisfile100extractedUI")
@ViewScoped
public class SetmisFile100ExtractedUI extends AbstractUI {

	private SetmisFile100ExtractedService service = new SetmisFile100ExtractedService();
	private List<SetmisFile100Extracted> setmisfile100extractedList = null;
	private List<SetmisFile100Extracted> setmisfile100extractedfilteredList = null;
	private SetmisFile100Extracted setmisfile100extracted = null;
	private LazyDataModel<SetmisFile100Extracted> dataModel;

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
	 * Initialize method to get all SetmisFile100Extracted and prepare a for a
	 * create of a new SetmisFile100Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile100Extracted
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		setmisfile100extractedInfo();
	}

	/**
	 * Get all SetmisFile100Extracted for data table
	 * 
	 * @author TechFinium
	 * @see SetmisFile100Extracted
	 */
	public void setmisfile100extractedInfo() {
		// dataModel = new SetmisFile100ExtractedDatamodel();
		dataModelInfo();
	}

	public void dataModelInfo() {
		dataModel = new LazyDataModel<SetmisFile100Extracted>() {
			private static final long serialVersionUID = 1L;
			private List<SetmisFile100Extracted> retorno = new ArrayList<SetmisFile100Extracted>();

			@Override
			public List<SetmisFile100Extracted> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSetmisFile100Extracted(SetmisFile100Extracted.class, first, pageSize, sortField, sortOrder,filters);
					dataModel.setRowCount(service.count(SetmisFile100Extracted.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(SetmisFile100Extracted obj) {
				return obj.getId();
			}
			@Override
			public SetmisFile100Extracted getRowData(String rowKey) {
				for (SetmisFile100Extracted obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SetmisFile100Extracted into database
	 * 
	 * @author TechFinium
	 * @see SetmisFile100Extracted
	 */
	public void setmisfile100extractedInsert() {
		try {
			service.create(this.setmisfile100extracted);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile100extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SetmisFile100Extracted in database
	 * 
	 * @author TechFinium
	 * @see SetmisFile100Extracted
	 */
	public void setmisfile100extractedUpdate() {
		try {
			service.update(this.setmisfile100extracted);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile100extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SetmisFile100Extracted from database
	 * 
	 * @author TechFinium
	 * @see SetmisFile100Extracted
	 */
	public void setmisfile100extractedDelete() {
		try {
			service.delete(this.setmisfile100extracted);
			prepareNew();
			setmisfile100extractedInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SetmisFile100Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile100Extracted
	 */
	public void prepareNew() {
		setmisfile100extracted = new SetmisFile100Extracted();
	}

	/*
	 * public List<SelectItem> getSetmisFile100ExtractedDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * setmisfile100extractedInfo(); for (SetmisFile100Extracted ug :
	 * setmisfile100extractedList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SetmisFile100Extracted> complete(String desc) {
		List<SetmisFile100Extracted> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SetmisFile100Extracted> getSetmisFile100ExtractedList() {
		return setmisfile100extractedList;
	}

	public void setSetmisFile100ExtractedList(List<SetmisFile100Extracted> setmisfile100extractedList) {
		this.setmisfile100extractedList = setmisfile100extractedList;
	}

	public SetmisFile100Extracted getSetmisfile100extracted() {
		return setmisfile100extracted;
	}

	public void setSetmisfile100extracted(SetmisFile100Extracted setmisfile100extracted) {
		this.setmisfile100extracted = setmisfile100extracted;
	}

	public List<SetmisFile100Extracted> getSetmisFile100ExtractedfilteredList() {
		return setmisfile100extractedfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param setmisfile100extractedfilteredList
	 *            the new setmisfile100extractedfilteredList list
	 * @see SetmisFile100Extracted
	 */
	public void setSetmisFile100ExtractedfilteredList(List<SetmisFile100Extracted> setmisfile100extractedfilteredList) {
		this.setmisfile100extractedfilteredList = setmisfile100extractedfilteredList;
	}

	public LazyDataModel<SetmisFile100Extracted> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SetmisFile100Extracted> dataModel) {
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
