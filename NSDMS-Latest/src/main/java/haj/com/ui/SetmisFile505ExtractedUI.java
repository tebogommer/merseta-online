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
import haj.com.entity.SetmisFile505Extracted;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SetmisFile505ExtractedService;

@ManagedBean(name = "setmisfile505extractedUI")
@ViewScoped
public class SetmisFile505ExtractedUI extends AbstractUI {

	private SetmisFile505ExtractedService service = new SetmisFile505ExtractedService();
	private List<SetmisFile505Extracted> setmisfile505extractedList = null;
	private List<SetmisFile505Extracted> setmisfile505extractedfilteredList = null;
	private SetmisFile505Extracted setmisfile505extracted = null;
	private LazyDataModel<SetmisFile505Extracted> dataModel;

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
	 * Initialize method to get all SetmisFile505Extracted and prepare a for a
	 * create of a new SetmisFile505Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile505Extracted
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		setmisfile505extractedInfo();
	}

	/**
	 * Get all SetmisFile505Extracted for data table
	 * 
	 * @author TechFinium
	 * @see SetmisFile505Extracted
	 */
	public void setmisfile505extractedInfo() {
		// dataModel = new SetmisFile505ExtractedDatamodel();
		dataModelInfo();
	}

	public void dataModelInfo() {
		dataModel = new LazyDataModel<SetmisFile505Extracted>() {
			private static final long serialVersionUID = 1L;
			private List<SetmisFile505Extracted> retorno = new ArrayList<SetmisFile505Extracted>();

			@Override
			public List<SetmisFile505Extracted> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSetmisFile505Extracted(SetmisFile505Extracted.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SetmisFile505Extracted.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SetmisFile505Extracted obj) {
				return obj.getId();
			}

			@Override
			public SetmisFile505Extracted getRowData(String rowKey) {
				for (SetmisFile505Extracted obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SetmisFile505Extracted into database
	 * 
	 * @author TechFinium
	 * @see SetmisFile505Extracted
	 */
	public void setmisfile505extractedInsert() {
		try {
			service.create(this.setmisfile505extracted);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile505extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SetmisFile505Extracted in database
	 * 
	 * @author TechFinium
	 * @see SetmisFile505Extracted
	 */
	public void setmisfile505extractedUpdate() {
		try {
			service.update(this.setmisfile505extracted);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile505extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SetmisFile505Extracted from database
	 * 
	 * @author TechFinium
	 * @see SetmisFile505Extracted
	 */
	public void setmisfile505extractedDelete() {
		try {
			service.delete(this.setmisfile505extracted);
			prepareNew();
			setmisfile505extractedInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SetmisFile505Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile505Extracted
	 */
	public void prepareNew() {
		setmisfile505extracted = new SetmisFile505Extracted();
	}

	/*
	 * public List<SelectItem> getSetmisFile505ExtractedDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * setmisfile505extractedInfo(); for (SetmisFile505Extracted ug :
	 * setmisfile505extractedList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SetmisFile505Extracted> complete(String desc) {
		List<SetmisFile505Extracted> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SetmisFile505Extracted> getSetmisFile505ExtractedList() {
		return setmisfile505extractedList;
	}

	public void setSetmisFile505ExtractedList(List<SetmisFile505Extracted> setmisfile505extractedList) {
		this.setmisfile505extractedList = setmisfile505extractedList;
	}

	public SetmisFile505Extracted getSetmisfile505extracted() {
		return setmisfile505extracted;
	}

	public void setSetmisfile505extracted(SetmisFile505Extracted setmisfile505extracted) {
		this.setmisfile505extracted = setmisfile505extracted;
	}

	public List<SetmisFile505Extracted> getSetmisFile505ExtractedfilteredList() {
		return setmisfile505extractedfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param setmisfile505extractedfilteredList
	 *            the new setmisfile505extractedfilteredList list
	 * @see SetmisFile505Extracted
	 */
	public void setSetmisFile505ExtractedfilteredList(List<SetmisFile505Extracted> setmisfile505extractedfilteredList) {
		this.setmisfile505extractedfilteredList = setmisfile505extractedfilteredList;
	}

	public LazyDataModel<SetmisFile505Extracted> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SetmisFile505Extracted> dataModel) {
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
