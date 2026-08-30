package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsInterSetaTransferLevies;
import haj.com.service.SarsInterSetaTransferLeviesService;

@ManagedBean(name = "sarsintersetatransferleviesUI")
@ViewScoped
public class SarsInterSetaTransferLeviesUI extends AbstractUI {

	private SarsInterSetaTransferLeviesService service = new SarsInterSetaTransferLeviesService();
	private List<SarsInterSetaTransferLevies> sarsintersetatransferleviesList = null;
	private List<SarsInterSetaTransferLevies> sarsintersetatransferleviesfilteredList = null;
	private SarsInterSetaTransferLevies sarsintersetatransferlevies = null;
	private LazyDataModel<SarsInterSetaTransferLevies> dataModel; 

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
	 * Initialize method to get all SarsInterSetaTransferLevies and prepare a for a create of a new SarsInterSetaTransferLevies
 	 * @author TechFinium 
 	 * @see    SarsInterSetaTransferLevies
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		sarsintersetatransferleviesInfo();
	}

	/**
	 * Get all SarsInterSetaTransferLevies for data table
 	 * @author TechFinium 
 	 * @see    SarsInterSetaTransferLevies
 	 */
	public void sarsintersetatransferleviesInfo() {
	 
			
			 dataModel = new LazyDataModel<SarsInterSetaTransferLevies>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SarsInterSetaTransferLevies> retorno = new  ArrayList<SarsInterSetaTransferLevies>();
			   
			   @Override 
			   public List<SarsInterSetaTransferLevies> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					if (sortField ==null) {
							retorno = service.allSarsInterSetaTransferLevies(SarsInterSetaTransferLevies.class,first,pageSize,"schemeYear",SortOrder.ASCENDING,filters);
					}
					else {
						retorno = service.allSarsInterSetaTransferLevies(SarsInterSetaTransferLevies.class,first,pageSize,sortField,sortOrder,filters);
					}
					dataModel.setRowCount(service.count(SarsInterSetaTransferLevies.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SarsInterSetaTransferLevies obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SarsInterSetaTransferLevies getRowData(String rowKey) {
			        for(SarsInterSetaTransferLevies obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SarsInterSetaTransferLevies into database
 	 * @author TechFinium 
 	 * @see    SarsInterSetaTransferLevies
 	 */
	public void sarsintersetatransferleviesInsert() {
		try {
				 service.create(this.sarsintersetatransferlevies);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 sarsintersetatransferleviesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SarsInterSetaTransferLevies in database
 	 * @author TechFinium 
 	 * @see    SarsInterSetaTransferLevies
 	 */
    public void sarsintersetatransferleviesUpdate() {
		try {
				 service.update(this.sarsintersetatransferlevies);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 sarsintersetatransferleviesInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SarsInterSetaTransferLevies from database
 	 * @author TechFinium 
 	 * @see    SarsInterSetaTransferLevies
 	 */
	public void sarsintersetatransferleviesDelete() {
		try {
			 service.delete(this.sarsintersetatransferlevies);
			  prepareNew();
			 sarsintersetatransferleviesInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SarsInterSetaTransferLevies 
 	 * @author TechFinium 
 	 * @see    SarsInterSetaTransferLevies
 	 */
	public void prepareNew() {
		sarsintersetatransferlevies = new SarsInterSetaTransferLevies();
	}

/*
    public List<SelectItem> getSarsInterSetaTransferLeviesDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	sarsintersetatransferleviesInfo();
    	for (SarsInterSetaTransferLevies ug : sarsintersetatransferleviesList) {
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
    public List<SarsInterSetaTransferLevies> complete(String desc) {
		List<SarsInterSetaTransferLevies> l = null;
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
    
    public List<SarsInterSetaTransferLevies> getSarsInterSetaTransferLeviesList() {
		return sarsintersetatransferleviesList;
	}
	public void setSarsInterSetaTransferLeviesList(List<SarsInterSetaTransferLevies> sarsintersetatransferleviesList) {
		this.sarsintersetatransferleviesList = sarsintersetatransferleviesList;
	}
	public SarsInterSetaTransferLevies getSarsintersetatransferlevies() {
		return sarsintersetatransferlevies;
	}
	public void setSarsintersetatransferlevies(SarsInterSetaTransferLevies sarsintersetatransferlevies) {
		this.sarsintersetatransferlevies = sarsintersetatransferlevies;
	}

    public List<SarsInterSetaTransferLevies> getSarsInterSetaTransferLeviesfilteredList() {
		return sarsintersetatransferleviesfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param sarsintersetatransferleviesfilteredList the new sarsintersetatransferleviesfilteredList list
 	 * @see    SarsInterSetaTransferLevies
 	 */	
	public void setSarsInterSetaTransferLeviesfilteredList(List<SarsInterSetaTransferLevies> sarsintersetatransferleviesfilteredList) {
		this.sarsintersetatransferleviesfilteredList = sarsintersetatransferleviesfilteredList;
	}

	
	public LazyDataModel<SarsInterSetaTransferLevies> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SarsInterSetaTransferLevies> dataModel) {
		this.dataModel = dataModel;
	}
	
}
