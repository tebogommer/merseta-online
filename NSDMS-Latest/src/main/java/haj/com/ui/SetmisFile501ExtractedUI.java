package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.SetmisFile100Extracted;
import haj.com.entity.SetmisFile501Extracted;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SetmisFile501ExtractedService;

@ManagedBean(name = "setmisfile501extractedUI")
@ViewScoped
public class SetmisFile501ExtractedUI extends AbstractUI {

	private SetmisFile501ExtractedService service = new SetmisFile501ExtractedService();
	private List<SetmisFile501Extracted> setmisfile501extractedList = null;
	private List<SetmisFile501Extracted> setmisfile501extractedfilteredList = null;
	private SetmisFile501Extracted setmisfile501extracted = null;
	private LazyDataModel<SetmisFile501Extracted> dataModel; 

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
	 * Initialize method to get all SetmisFile501Extracted and prepare a for a create of a new SetmisFile501Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile501Extracted
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		setmisfile501extractedInfo();
	}

	/**
	 * Get all SetmisFile501Extracted for data table
 	 * @author TechFinium 
 	 * @see    SetmisFile501Extracted
 	 */
	public void setmisfile501extractedInfo() {
//			dataModel = new SetmisFile501ExtractedDatamodel();
		dataModelInfo();
	}

	public void dataModelInfo() {
		dataModel = new LazyDataModel<SetmisFile501Extracted>() {
			private static final long serialVersionUID = 1L;
			private List<SetmisFile501Extracted> retorno = new ArrayList<SetmisFile501Extracted>();

			@Override
			public List<SetmisFile501Extracted> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSetmisFile501Extracted(SetmisFile501Extracted.class, first, pageSize, sortField, sortOrder,filters);
					dataModel.setRowCount(service.count(SetmisFile501Extracted.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(SetmisFile501Extracted obj) {
				return obj.getId();
			}
			@Override
			public SetmisFile501Extracted getRowData(String rowKey) {
				for (SetmisFile501Extracted obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SetmisFile501Extracted into database
 	 * @author TechFinium 
 	 * @see    SetmisFile501Extracted
 	 */
	public void setmisfile501extractedInsert() {
		try {
				 service.create(this.setmisfile501extracted);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 setmisfile501extractedInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SetmisFile501Extracted in database
 	 * @author TechFinium 
 	 * @see    SetmisFile501Extracted
 	 */
    public void setmisfile501extractedUpdate() {
		try {
				 service.update(this.setmisfile501extracted);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 setmisfile501extractedInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SetmisFile501Extracted from database
 	 * @author TechFinium 
 	 * @see    SetmisFile501Extracted
 	 */
	public void setmisfile501extractedDelete() {
		try {
			 service.delete(this.setmisfile501extracted);
			  prepareNew();
			 setmisfile501extractedInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SetmisFile501Extracted 
 	 * @author TechFinium 
 	 * @see    SetmisFile501Extracted
 	 */
	public void prepareNew() {
		setmisfile501extracted = new SetmisFile501Extracted();
	}

/*
    public List<SelectItem> getSetmisFile501ExtractedDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	setmisfile501extractedInfo();
    	for (SetmisFile501Extracted ug : setmisfile501extractedList) {
    		// l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc()));
		}
    	return l;
    }
  */
  
    /**
     * Complete.
     *
     * @param desc the desc
     * @return the list
     */  
    public List<SetmisFile501Extracted> complete(String desc) {
		List<SetmisFile501Extracted> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    public List<SetmisFile501Extracted> getSetmisFile501ExtractedList() {
		return setmisfile501extractedList;
	}
	public void setSetmisFile501ExtractedList(List<SetmisFile501Extracted> setmisfile501extractedList) {
		this.setmisfile501extractedList = setmisfile501extractedList;
	}
	public SetmisFile501Extracted getSetmisfile501extracted() {
		return setmisfile501extracted;
	}
	public void setSetmisfile501extracted(SetmisFile501Extracted setmisfile501extracted) {
		this.setmisfile501extracted = setmisfile501extracted;
	}

    public List<SetmisFile501Extracted> getSetmisFile501ExtractedfilteredList() {
		return setmisfile501extractedfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param setmisfile501extractedfilteredList the new setmisfile501extractedfilteredList list
 	 * @see    SetmisFile501Extracted
 	 */	
	public void setSetmisFile501ExtractedfilteredList(List<SetmisFile501Extracted> setmisfile501extractedfilteredList) {
		this.setmisfile501extractedfilteredList = setmisfile501extractedfilteredList;
	}

	
	public LazyDataModel<SetmisFile501Extracted> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SetmisFile501Extracted> dataModel) {
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
