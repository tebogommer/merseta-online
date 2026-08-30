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
import haj.com.entity.SetmisFile506Extracted;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SetmisFile506ExtractedService;

@ManagedBean(name = "setmisfile506extractedUI")
@ViewScoped
public class SetmisFile506ExtractedUI extends AbstractUI {

	private SetmisFile506ExtractedService service = new SetmisFile506ExtractedService();
	private List<SetmisFile506Extracted> setmisfile506extractedList = null;
	private List<SetmisFile506Extracted> setmisfile506extractedfilteredList = null;
	private SetmisFile506Extracted setmisfile506extracted = null;
	private LazyDataModel<SetmisFile506Extracted> dataModel;

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
	 * Initialize method to get all SetmisFile506Extracted and prepare a for a
	 * create of a new SetmisFile506Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile506Extracted
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		setmisfile506extractedInfo();
	}

	/**
	 * Get all SetmisFile506Extracted for data table
	 * 
	 * @author TechFinium
	 * @see SetmisFile506Extracted
	 */
	public void setmisfile506extractedInfo() {
		// dataModel = new SetmisFile506ExtractedDatamodel();
		dataModelInfo();
	}

	public void dataModelInfo() {
		dataModel = new LazyDataModel<SetmisFile506Extracted>() {
			private static final long serialVersionUID = 1L;
			private List<SetmisFile506Extracted> retorno = new ArrayList<SetmisFile506Extracted>();

			@Override
			public List<SetmisFile506Extracted> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSetmisFile506Extracted(SetmisFile506Extracted.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SetmisFile506Extracted.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SetmisFile506Extracted obj) {
				return obj.getId();
			}

			@Override
			public SetmisFile506Extracted getRowData(String rowKey) {
				for (SetmisFile506Extracted obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SetmisFile506Extracted into database
	 * 
	 * @author TechFinium
	 * @see SetmisFile506Extracted
	 */
	public void setmisfile506extractedInsert() {
		try {
			service.create(this.setmisfile506extracted);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile506extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SetmisFile506Extracted in database
	 * 
	 * @author TechFinium
	 * @see SetmisFile506Extracted
	 */
	public void setmisfile506extractedUpdate() {
		try {
			service.update(this.setmisfile506extracted);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile506extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SetmisFile506Extracted from database
	 * 
	 * @author TechFinium
	 * @see SetmisFile506Extracted
	 */
	public void setmisfile506extractedDelete() {
		try {
			service.delete(this.setmisfile506extracted);
			prepareNew();
			setmisfile506extractedInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SetmisFile506Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile506Extracted
	 */
	public void prepareNew() {
		setmisfile506extracted = new SetmisFile506Extracted();
	}

	/*
	 * public List<SelectItem> getSetmisFile506ExtractedDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * setmisfile506extractedInfo(); for (SetmisFile506Extracted ug :
	 * setmisfile506extractedList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SetmisFile506Extracted> complete(String desc) {
		List<SetmisFile506Extracted> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SetmisFile506Extracted> getSetmisFile506ExtractedList() {
		return setmisfile506extractedList;
	}

	public void setSetmisFile506ExtractedList(List<SetmisFile506Extracted> setmisfile506extractedList) {
		this.setmisfile506extractedList = setmisfile506extractedList;
	}

	public SetmisFile506Extracted getSetmisfile506extracted() {
		return setmisfile506extracted;
	}

	public void setSetmisfile506extracted(SetmisFile506Extracted setmisfile506extracted) {
		this.setmisfile506extracted = setmisfile506extracted;
	}

	public List<SetmisFile506Extracted> getSetmisFile506ExtractedfilteredList() {
		return setmisfile506extractedfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param setmisfile506extractedfilteredList
	 *            the new setmisfile506extractedfilteredList list
	 * @see SetmisFile506Extracted
	 */
	public void setSetmisFile506ExtractedfilteredList(List<SetmisFile506Extracted> setmisfile506extractedfilteredList) {
		this.setmisfile506extractedfilteredList = setmisfile506extractedfilteredList;
	}

	public LazyDataModel<SetmisFile506Extracted> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SetmisFile506Extracted> dataModel) {
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
