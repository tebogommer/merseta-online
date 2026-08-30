package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.SetmisFile401Extracted;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SetmisFile401ExtractedService;

@ManagedBean(name = "setmisfile401extractedUI")
@ViewScoped
public class SetmisFile401ExtractedUI extends AbstractUI {

	private SetmisFile401ExtractedService service = new SetmisFile401ExtractedService();
	private List<SetmisFile401Extracted> setmisfile401extractedList = null;
	private List<SetmisFile401Extracted> setmisfile401extractedfilteredList = null;
	private SetmisFile401Extracted setmisfile401extracted = null;
	private LazyDataModel<SetmisFile401Extracted> dataModel;

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
	 * Initialize method to get all SetmisFile401Extracted and prepare a for a
	 * create of a new SetmisFile401Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile401Extracted
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		setmisfile401extractedInfo();
	}

	/**
	 * Get all SetmisFile401Extracted for data table
	 * 
	 * @author TechFinium
	 * @see SetmisFile401Extracted
	 */
	public void setmisfile401extractedInfo() {
		// dataModel = new SetmisFile401ExtractedDatamodel();
		dataModelInfo();
	}

	public void dataModelInfo() {
		dataModel = new LazyDataModel<SetmisFile401Extracted>() {
			private static final long serialVersionUID = 1L;
			private List<SetmisFile401Extracted> retorno = new ArrayList<SetmisFile401Extracted>();

			@Override
			public List<SetmisFile401Extracted> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSetmisFile401Extracted(SetmisFile401Extracted.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SetmisFile401Extracted.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SetmisFile401Extracted obj) {
				return obj.getId();
			}

			@Override
			public SetmisFile401Extracted getRowData(String rowKey) {
				for (SetmisFile401Extracted obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SetmisFile401Extracted into database
	 * 
	 * @author TechFinium
	 * @see SetmisFile401Extracted
	 */
	public void setmisfile401extractedInsert() {
		try {
			service.create(this.setmisfile401extracted);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile401extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SetmisFile401Extracted in database
	 * 
	 * @author TechFinium
	 * @see SetmisFile401Extracted
	 */
	public void setmisfile401extractedUpdate() {
		try {
			service.update(this.setmisfile401extracted);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile401extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SetmisFile401Extracted from database
	 * 
	 * @author TechFinium
	 * @see SetmisFile401Extracted
	 */
	public void setmisfile401extractedDelete() {
		try {
			service.delete(this.setmisfile401extracted);
			prepareNew();
			setmisfile401extractedInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SetmisFile401Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile401Extracted
	 */
	public void prepareNew() {
		setmisfile401extracted = new SetmisFile401Extracted();
	}

	/*
	 * public List<SelectItem> getSetmisFile401ExtractedDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * setmisfile401extractedInfo(); for (SetmisFile401Extracted ug :
	 * setmisfile401extractedList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SetmisFile401Extracted> complete(String desc) {
		List<SetmisFile401Extracted> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SetmisFile401Extracted> getSetmisFile401ExtractedList() {
		return setmisfile401extractedList;
	}

	public void setSetmisFile401ExtractedList(List<SetmisFile401Extracted> setmisfile401extractedList) {
		this.setmisfile401extractedList = setmisfile401extractedList;
	}

	public SetmisFile401Extracted getSetmisfile401extracted() {
		return setmisfile401extracted;
	}

	public void setSetmisfile401extracted(SetmisFile401Extracted setmisfile401extracted) {
		this.setmisfile401extracted = setmisfile401extracted;
	}

	public List<SetmisFile401Extracted> getSetmisFile401ExtractedfilteredList() {
		return setmisfile401extractedfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param setmisfile401extractedfilteredList
	 *            the new setmisfile401extractedfilteredList list
	 * @see SetmisFile401Extracted
	 */
	public void setSetmisFile401ExtractedfilteredList(List<SetmisFile401Extracted> setmisfile401extractedfilteredList) {
		this.setmisfile401extractedfilteredList = setmisfile401extractedfilteredList;
	}

	public LazyDataModel<SetmisFile401Extracted> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SetmisFile401Extracted> dataModel) {
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
