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
import haj.com.entity.SetmisFile503Extracted;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SetmisFile503ExtractedService;

@ManagedBean(name = "setmisfile503extractedUI")
@ViewScoped
public class SetmisFile503ExtractedUI extends AbstractUI {

	private SetmisFile503ExtractedService service = new SetmisFile503ExtractedService();
	private List<SetmisFile503Extracted> setmisfile503extractedList = null;
	private List<SetmisFile503Extracted> setmisfile503extractedfilteredList = null;
	private SetmisFile503Extracted setmisfile503extracted = null;
	private LazyDataModel<SetmisFile503Extracted> dataModel; 

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
	 * Initialize method to get all SetmisFile503Extracted and prepare a for a create of a new SetmisFile503Extracted
 	 * @author TechFinium 
 	 * @see    SetmisFile503Extracted
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		setmisfile503extractedInfo();
	}

	/**
	 * Get all SetmisFile503Extracted for data table
 	 * @author TechFinium 
 	 * @see    SetmisFile503Extracted
 	 */
	public void setmisfile503extractedInfo() {
//			dataModel = new SetmisFile503ExtractedDatamodel();
		dataModelInfo();
	}

	public void dataModelInfo() {
		dataModel = new LazyDataModel<SetmisFile503Extracted>() {
			private static final long serialVersionUID = 1L;
			private List<SetmisFile503Extracted> retorno = new ArrayList<SetmisFile503Extracted>();

			@Override
			public List<SetmisFile503Extracted> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allSetmisFile503Extracted(SetmisFile503Extracted.class, first, pageSize, sortField, sortOrder,filters);
					dataModel.setRowCount(service.count(SetmisFile503Extracted.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(SetmisFile503Extracted obj) {
				return obj.getId();
			}
			@Override
			public SetmisFile503Extracted getRowData(String rowKey) {
				for (SetmisFile503Extracted obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/**
	 * Insert SetmisFile503Extracted into database
 	 * @author TechFinium 
 	 * @see    SetmisFile503Extracted
 	 */
	public void setmisfile503extractedInsert() {
		try {
				 service.create(this.setmisfile503extracted);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 setmisfile503extractedInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SetmisFile503Extracted in database
 	 * @author TechFinium 
 	 * @see    SetmisFile503Extracted
 	 */
    public void setmisfile503extractedUpdate() {
		try {
				 service.update(this.setmisfile503extracted);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 setmisfile503extractedInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SetmisFile503Extracted from database
 	 * @author TechFinium 
 	 * @see    SetmisFile503Extracted
 	 */
	public void setmisfile503extractedDelete() {
		try {
			 service.delete(this.setmisfile503extracted);
			  prepareNew();
			 setmisfile503extractedInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SetmisFile503Extracted 
 	 * @author TechFinium 
 	 * @see    SetmisFile503Extracted
 	 */
	public void prepareNew() {
		setmisfile503extracted = new SetmisFile503Extracted();
	}

/*
    public List<SelectItem> getSetmisFile503ExtractedDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	setmisfile503extractedInfo();
    	for (SetmisFile503Extracted ug : setmisfile503extractedList) {
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
    public List<SetmisFile503Extracted> complete(String desc) {
		List<SetmisFile503Extracted> l = null;
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
    
    public List<SetmisFile503Extracted> getSetmisFile503ExtractedList() {
		return setmisfile503extractedList;
	}
	public void setSetmisFile503ExtractedList(List<SetmisFile503Extracted> setmisfile503extractedList) {
		this.setmisfile503extractedList = setmisfile503extractedList;
	}
	public SetmisFile503Extracted getSetmisfile503extracted() {
		return setmisfile503extracted;
	}
	public void setSetmisfile503extracted(SetmisFile503Extracted setmisfile503extracted) {
		this.setmisfile503extracted = setmisfile503extracted;
	}

    public List<SetmisFile503Extracted> getSetmisFile503ExtractedfilteredList() {
		return setmisfile503extractedfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param setmisfile503extractedfilteredList the new setmisfile503extractedfilteredList list
 	 * @see    SetmisFile503Extracted
 	 */	
	public void setSetmisFile503ExtractedfilteredList(List<SetmisFile503Extracted> setmisfile503extractedfilteredList) {
		this.setmisfile503extractedfilteredList = setmisfile503extractedfilteredList;
	}

	
	public LazyDataModel<SetmisFile503Extracted> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SetmisFile503Extracted> dataModel) {
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
