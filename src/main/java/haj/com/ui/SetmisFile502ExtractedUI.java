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
import haj.com.entity.SetmisFile502Extracted;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SetmisFile502ExtractedService;

@ManagedBean(name = "setmisfile502extractedUI")
@ViewScoped
public class SetmisFile502ExtractedUI extends AbstractUI {

	private SetmisFile502ExtractedService service = new SetmisFile502ExtractedService();
	private List<SetmisFile502Extracted> setmisfile502extractedList = null;
	private List<SetmisFile502Extracted> setmisfile502extractedfilteredList = null;
	private SetmisFile502Extracted setmisfile502extracted = null;
	private LazyDataModel<SetmisFile502Extracted> dataModel;

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
	 * Initialize method to get all SetmisFile502Extracted and prepare a for a
	 * create of a new SetmisFile502Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile502Extracted
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		setmisfile502extractedInfo();
	}

	/**
	 * Get all SetmisFile502Extracted for data table
	 * 
	 * @author TechFinium
	 * @see SetmisFile502Extracted
	 */
	public void setmisfile502extractedInfo() {
		// dataModel = new SetmisFile502ExtractedDatamodel();
		dataModelInfo();
	}

	public void dataModelInfo() {
		dataModel = new LazyDataModel<SetmisFile502Extracted>() {
			private static final long serialVersionUID = 1L;
			private List<SetmisFile502Extracted> retorno = new ArrayList<SetmisFile502Extracted>();

			@Override
			public List<SetmisFile502Extracted> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSetmisFile502Extracted(SetmisFile502Extracted.class, first, pageSize,
							sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(SetmisFile502Extracted.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SetmisFile502Extracted obj) {
				return obj.getId();
			}

			@Override
			public SetmisFile502Extracted getRowData(String rowKey) {
				for (SetmisFile502Extracted obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SetmisFile502Extracted into database
	 * 
	 * @author TechFinium
	 * @see SetmisFile502Extracted
	 */
	public void setmisfile502extractedInsert() {
		try {
			service.create(this.setmisfile502extracted);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile502extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update SetmisFile502Extracted in database
	 * 
	 * @author TechFinium
	 * @see SetmisFile502Extracted
	 */
	public void setmisfile502extractedUpdate() {
		try {
			service.update(this.setmisfile502extracted);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			setmisfile502extractedInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete SetmisFile502Extracted from database
	 * 
	 * @author TechFinium
	 * @see SetmisFile502Extracted
	 */
	public void setmisfile502extractedDelete() {
		try {
			service.delete(this.setmisfile502extracted);
			prepareNew();
			setmisfile502extractedInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SetmisFile502Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile502Extracted
	 */
	public void prepareNew() {
		setmisfile502extracted = new SetmisFile502Extracted();
	}

	/*
	 * public List<SelectItem> getSetmisFile502ExtractedDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * setmisfile502extractedInfo(); for (SetmisFile502Extracted ug :
	 * setmisfile502extractedList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<SetmisFile502Extracted> complete(String desc) {
		List<SetmisFile502Extracted> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<SetmisFile502Extracted> getSetmisFile502ExtractedList() {
		return setmisfile502extractedList;
	}

	public void setSetmisFile502ExtractedList(List<SetmisFile502Extracted> setmisfile502extractedList) {
		this.setmisfile502extractedList = setmisfile502extractedList;
	}

	public SetmisFile502Extracted getSetmisfile502extracted() {
		return setmisfile502extracted;
	}

	public void setSetmisfile502extracted(SetmisFile502Extracted setmisfile502extracted) {
		this.setmisfile502extracted = setmisfile502extracted;
	}

	public List<SetmisFile502Extracted> getSetmisFile502ExtractedfilteredList() {
		return setmisfile502extractedfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param setmisfile502extractedfilteredList
	 *            the new setmisfile502extractedfilteredList list
	 * @see SetmisFile502Extracted
	 */
	public void setSetmisFile502ExtractedfilteredList(List<SetmisFile502Extracted> setmisfile502extractedfilteredList) {
		this.setmisfile502extractedfilteredList = setmisfile502extractedfilteredList;
	}

	public LazyDataModel<SetmisFile502Extracted> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SetmisFile502Extracted> dataModel) {
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
