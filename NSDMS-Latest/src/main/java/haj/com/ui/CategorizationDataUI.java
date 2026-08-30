package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.CategorizationData;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CategorizationDataService;
import haj.com.utils.CSVUtil;

@ManagedBean(name = "categorizationdataUI")
@ViewScoped
public class CategorizationDataUI extends AbstractUI {

	private CategorizationDataService service = new CategorizationDataService();
	private List<CategorizationData> categorizationdataList = null;
	private List<CategorizationData> categorizationdatafilteredList = null;
	private CategorizationData categorizationdata = null;
	private LazyDataModel<CategorizationData> dataModel; 
	public CSVUtil csvUtil = new CSVUtil();


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
	 * Initialize method to get all CategorizationData and prepare a for a create of a new CategorizationData
 	 * @author TechFinium 
 	 * @see    CategorizationData
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		categorizationdataInfo();
	}

	/**
	 * Get all CategorizationData for data table
 	 * @author TechFinium 
 	 * @see    CategorizationData
 	 */
	public void categorizationdataInfo() {
	 
			
			 dataModel = new LazyDataModel<CategorizationData>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<CategorizationData> retorno = new  ArrayList<CategorizationData>();
			   
			   @Override 
			   public List<CategorizationData> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allCategorizationData(CategorizationData.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(CategorizationData.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(CategorizationData obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public CategorizationData getRowData(String rowKey) {
			        for(CategorizationData obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert CategorizationData into database
 	 * @author TechFinium 
 	 * @see    CategorizationData
 	 */
	public void categorizationdataInsert() {
		try {
				 service.create(this.categorizationdata);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 categorizationdataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update CategorizationData in database
 	 * @author TechFinium 
 	 * @see    CategorizationData
 	 */
    public void categorizationdataUpdate() {
		try {
				 service.update(this.categorizationdata);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 categorizationdataInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete CategorizationData from database
 	 * @author TechFinium 
 	 * @see    CategorizationData
 	 */
	public void categorizationdataDelete() {
		try {
			 service.delete(this.categorizationdata);
			  prepareNew();
			 categorizationdataInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of CategorizationData 
 	 * @author TechFinium 
 	 * @see    CategorizationData
 	 */
	public void prepareNew() {
		categorizationdata = new CategorizationData();
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			logger.info("Starting file upload");
			List<CategorizationData> mandatoryGrantCSVImports = csvUtil.getObjects(CategorizationData.class, event.getFile().getInputstream(), ";");
			logger.info("Finished parsing upload. number of entries: " + mandatoryGrantCSVImports.size());
			service.save(mandatoryGrantCSVImports);			
			logger.info("Finished persisting upload");
			categorizationdataInfo();
		} catch (ConstraintViolationException e) {
			addErrorMessage("ID number exists for the specified OFO and Intervention Type");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}finally {
			csvUtil = new CSVUtil();
		}

	}

/*
    public List<SelectItem> getCategorizationDataDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	categorizationdataInfo();
    	for (CategorizationData ug : categorizationdataList) {
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
    public List<CategorizationData> complete(String desc) {
		List<CategorizationData> l = null;
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
    
    public List<CategorizationData> getCategorizationDataList() {
		return categorizationdataList;
	}
	public void setCategorizationDataList(List<CategorizationData> categorizationdataList) {
		this.categorizationdataList = categorizationdataList;
	}
	public CategorizationData getCategorizationdata() {
		return categorizationdata;
	}
	public void setCategorizationdata(CategorizationData categorizationdata) {
		this.categorizationdata = categorizationdata;
	}

    public List<CategorizationData> getCategorizationDatafilteredList() {
		return categorizationdatafilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param categorizationdatafilteredList the new categorizationdatafilteredList list
 	 * @see    CategorizationData
 	 */	
	public void setCategorizationDatafilteredList(List<CategorizationData> categorizationdatafilteredList) {
		this.categorizationdatafilteredList = categorizationdatafilteredList;
	}

	
	public LazyDataModel<CategorizationData> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CategorizationData> dataModel) {
		this.dataModel = dataModel;
	}
	
}
