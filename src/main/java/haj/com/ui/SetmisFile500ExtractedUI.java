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
import haj.com.entity.SetmisFile500Extracted;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SetmisFile500ExtractedService;

@ManagedBean(name = "setmisfile500extractedUI")
@ViewScoped
public class SetmisFile500ExtractedUI extends AbstractUI {

	private SetmisFile500ExtractedService service = new SetmisFile500ExtractedService();
	private List<SetmisFile500Extracted> setmisfile500extractedList = null;
	private List<SetmisFile500Extracted> setmisfile500extractedfilteredList = null;
	private SetmisFile500Extracted setmisfile500extracted = null;
	private LazyDataModel<SetmisFile500Extracted> dataModel;

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
	 * Initialize method to get all SetmisFile500Extracted and prepare a for a
	 * create of a new SetmisFile500Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile500Extracted
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		setmisfile500extractedInfo();
	}

	/**
	 * Get all SetmisFile500Extracted for data table
	 * 
	 * @author TechFinium
	 * @see SetmisFile500Extracted
	 */
	public void setmisfile500extractedInfo() {
		// dataModel = new SetmisFile500ExtractedDatamodel();
		dataModelInfo();
	}

	public void dataModelInfo() {
		dataModel = new LazyDataModel<SetmisFile500Extracted>() {
			private static final long serialVersionUID = 1L;
			private List<SetmisFile500Extracted> retorno = new ArrayList<SetmisFile500Extracted>();

			@Override
			public List<SetmisFile500Extracted> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSetmisFile500Extracted(SetmisFile500Extracted.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SetmisFile500Extracted.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SetmisFile500Extracted obj) {
				return obj.getId();
			}

			@Override
			public SetmisFile500Extracted getRowData(String rowKey) {
				for (SetmisFile500Extracted obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SetmisFile500Extracted into database
	 * 
	 * @author TechFinium
	 * @see SetmisFile500Extracted
	 */
	public void setmisfile500extractedInsert() {
		try {
			service.create(this.setmisfile500extracted);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile500extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SetmisFile500Extracted in database
	 * 
	 * @author TechFinium
	 * @see SetmisFile500Extracted
	 */
	public void setmisfile500extractedUpdate() {
		try {
			service.update(this.setmisfile500extracted);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile500extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SetmisFile500Extracted from database
	 * 
	 * @author TechFinium
	 * @see SetmisFile500Extracted
	 */
	public void setmisfile500extractedDelete() {
		try {
			service.delete(this.setmisfile500extracted);
			prepareNew();
			setmisfile500extractedInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SetmisFile500Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile500Extracted
	 */
	public void prepareNew() {
		setmisfile500extracted = new SetmisFile500Extracted();
	}

	/*
	 * public List<SelectItem> getSetmisFile500ExtractedDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * setmisfile500extractedInfo(); for (SetmisFile500Extracted ug :
	 * setmisfile500extractedList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SetmisFile500Extracted> complete(String desc) {
		List<SetmisFile500Extracted> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SetmisFile500Extracted> getSetmisFile500ExtractedList() {
		return setmisfile500extractedList;
	}

	public void setSetmisFile500ExtractedList(List<SetmisFile500Extracted> setmisfile500extractedList) {
		this.setmisfile500extractedList = setmisfile500extractedList;
	}

	public SetmisFile500Extracted getSetmisfile500extracted() {
		return setmisfile500extracted;
	}

	public void setSetmisfile500extracted(SetmisFile500Extracted setmisfile500extracted) {
		this.setmisfile500extracted = setmisfile500extracted;
	}

	public List<SetmisFile500Extracted> getSetmisFile500ExtractedfilteredList() {
		return setmisfile500extractedfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param setmisfile500extractedfilteredList
	 *            the new setmisfile500extractedfilteredList list
	 * @see SetmisFile500Extracted
	 */
	public void setSetmisFile500ExtractedfilteredList(List<SetmisFile500Extracted> setmisfile500extractedfilteredList) {
		this.setmisfile500extractedfilteredList = setmisfile500extractedfilteredList;
	}

	public LazyDataModel<SetmisFile500Extracted> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SetmisFile500Extracted> dataModel) {
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
