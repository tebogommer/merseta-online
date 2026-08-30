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
import haj.com.entity.SetmisFile200Extracted;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SetmisFile200ExtractedService;

@ManagedBean(name = "setmisfile200extractedUI")
@ViewScoped
public class SetmisFile200ExtractedUI extends AbstractUI {

	private SetmisFile200ExtractedService service = new SetmisFile200ExtractedService();
	private List<SetmisFile200Extracted> setmisfile200extractedList = null;
	private List<SetmisFile200Extracted> setmisfile200extractedfilteredList = null;
	private SetmisFile200Extracted setmisfile200extracted = null;
	private LazyDataModel<SetmisFile200Extracted> dataModel;

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
	 * Initialize method to get all SetmisFile200Extracted and prepare a for a
	 * create of a new SetmisFile200Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile200Extracted
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		setmisfile200extractedInfo();
	}

	/**
	 * Get all SetmisFile200Extracted for data table
	 * 
	 * @author TechFinium
	 * @see SetmisFile200Extracted
	 */
	public void setmisfile200extractedInfo() {
		// dataModel = new SetmisFile200ExtractedDatamodel();
		dataModelInfo();
	}

	public void dataModelInfo() {
		dataModel = new LazyDataModel<SetmisFile200Extracted>() {
			private static final long serialVersionUID = 1L;
			private List<SetmisFile200Extracted> retorno = new ArrayList<SetmisFile200Extracted>();

			@Override
			public List<SetmisFile200Extracted> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSetmisFile200Extracted(SetmisFile200Extracted.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SetmisFile200Extracted.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SetmisFile200Extracted obj) {
				return obj.getId();
			}

			@Override
			public SetmisFile200Extracted getRowData(String rowKey) {
				for (SetmisFile200Extracted obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SetmisFile200Extracted into database
	 * 
	 * @author TechFinium
	 * @see SetmisFile200Extracted
	 */
	public void setmisfile200extractedInsert() {
		try {
			service.create(this.setmisfile200extracted);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile200extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SetmisFile200Extracted in database
	 * 
	 * @author TechFinium
	 * @see SetmisFile200Extracted
	 */
	public void setmisfile200extractedUpdate() {
		try {
			service.update(this.setmisfile200extracted);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile200extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SetmisFile200Extracted from database
	 * 
	 * @author TechFinium
	 * @see SetmisFile200Extracted
	 */
	public void setmisfile200extractedDelete() {
		try {
			service.delete(this.setmisfile200extracted);
			prepareNew();
			setmisfile200extractedInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SetmisFile200Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile200Extracted
	 */
	public void prepareNew() {
		setmisfile200extracted = new SetmisFile200Extracted();
	}

	/*
	 * public List<SelectItem> getSetmisFile200ExtractedDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * setmisfile200extractedInfo(); for (SetmisFile200Extracted ug :
	 * setmisfile200extractedList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SetmisFile200Extracted> complete(String desc) {
		List<SetmisFile200Extracted> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SetmisFile200Extracted> getSetmisFile200ExtractedList() {
		return setmisfile200extractedList;
	}

	public void setSetmisFile200ExtractedList(List<SetmisFile200Extracted> setmisfile200extractedList) {
		this.setmisfile200extractedList = setmisfile200extractedList;
	}

	public SetmisFile200Extracted getSetmisfile200extracted() {
		return setmisfile200extracted;
	}

	public void setSetmisfile200extracted(SetmisFile200Extracted setmisfile200extracted) {
		this.setmisfile200extracted = setmisfile200extracted;
	}

	public List<SetmisFile200Extracted> getSetmisFile200ExtractedfilteredList() {
		return setmisfile200extractedfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param setmisfile200extractedfilteredList
	 *            the new setmisfile200extractedfilteredList list
	 * @see SetmisFile200Extracted
	 */
	public void setSetmisFile200ExtractedfilteredList(List<SetmisFile200Extracted> setmisfile200extractedfilteredList) {
		this.setmisfile200extractedfilteredList = setmisfile200extractedfilteredList;
	}

	public LazyDataModel<SetmisFile200Extracted> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SetmisFile200Extracted> dataModel) {
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
