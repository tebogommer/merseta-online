package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.WspSkillsGap;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspSkillsGapService;

@ManagedBean(name = "wspskillsgapUI")
@ViewScoped
public class WspSkillsGapUI extends AbstractUI {

	private WspSkillsGapService service = new WspSkillsGapService();
	private List<WspSkillsGap> wspskillsgapList = null;
	private List<WspSkillsGap> wspskillsgapfilteredList = null;
	private WspSkillsGap wspskillsgap = null;
	private LazyDataModel<WspSkillsGap> dataModel; 

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
	 * Initialize method to get all WspSkillsGap and prepare a for a create of a new WspSkillsGap
 	 * @author TechFinium 
 	 * @see    WspSkillsGap
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		wspskillsgapInfo();
	}

	/**
	 * Get all WspSkillsGap for data table
 	 * @author TechFinium 
 	 * @see    WspSkillsGap
 	 */
	public void wspskillsgapInfo() {
	 
			
			 dataModel = new LazyDataModel<WspSkillsGap>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<WspSkillsGap> retorno = new  ArrayList<WspSkillsGap>();
			   
			   @Override 
			   public List<WspSkillsGap> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allWspSkillsGap(WspSkillsGap.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(WspSkillsGap.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(WspSkillsGap obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public WspSkillsGap getRowData(String rowKey) {
			        for(WspSkillsGap obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert WspSkillsGap into database
 	 * @author TechFinium 
 	 * @see    WspSkillsGap
 	 */
	public void wspskillsgapInsert() {
		try {
				 service.create(this.wspskillsgap);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspskillsgapInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update WspSkillsGap in database
 	 * @author TechFinium 
 	 * @see    WspSkillsGap
 	 */
    public void wspskillsgapUpdate() {
		try {
				 service.update(this.wspskillsgap);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 wspskillsgapInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete WspSkillsGap from database
 	 * @author TechFinium 
 	 * @see    WspSkillsGap
 	 */
	public void wspskillsgapDelete() {
		try {
			 service.delete(this.wspskillsgap);
			  prepareNew();
			 wspskillsgapInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of WspSkillsGap 
 	 * @author TechFinium 
 	 * @see    WspSkillsGap
 	 */
	public void prepareNew() {
		wspskillsgap = new WspSkillsGap();
	}

/*
    public List<SelectItem> getWspSkillsGapDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	wspskillsgapInfo();
    	for (WspSkillsGap ug : wspskillsgapList) {
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
    public List<WspSkillsGap> complete(String desc) {
		List<WspSkillsGap> l = null;
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
    
    public List<WspSkillsGap> getWspSkillsGapList() {
		return wspskillsgapList;
	}
	public void setWspSkillsGapList(List<WspSkillsGap> wspskillsgapList) {
		this.wspskillsgapList = wspskillsgapList;
	}
	public WspSkillsGap getWspskillsgap() {
		return wspskillsgap;
	}
	public void setWspskillsgap(WspSkillsGap wspskillsgap) {
		this.wspskillsgap = wspskillsgap;
	}

    public List<WspSkillsGap> getWspSkillsGapfilteredList() {
		return wspskillsgapfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param wspskillsgapfilteredList the new wspskillsgapfilteredList list
 	 * @see    WspSkillsGap
 	 */	
	public void setWspSkillsGapfilteredList(List<WspSkillsGap> wspskillsgapfilteredList) {
		this.wspskillsgapfilteredList = wspskillsgapfilteredList;
	}

	
	public LazyDataModel<WspSkillsGap> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspSkillsGap> dataModel) {
		this.dataModel = dataModel;
	}
	
}
