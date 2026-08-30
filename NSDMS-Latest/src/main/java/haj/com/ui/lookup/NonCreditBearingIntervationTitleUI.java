package  haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.service.lookup.NonCreditBearingIntervationTitleService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "noncreditbearingintervationtitleUI")
@ViewScoped
public class NonCreditBearingIntervationTitleUI extends AbstractUI {

	private NonCreditBearingIntervationTitleService service = new NonCreditBearingIntervationTitleService();
	private List<NonCreditBearingIntervationTitle> noncreditbearingintervationtitleList = null;
	private List<NonCreditBearingIntervationTitle> noncreditbearingintervationtitlefilteredList = null;
	private NonCreditBearingIntervationTitle noncreditbearingintervationtitle = null;
	private LazyDataModel<NonCreditBearingIntervationTitle> dataModel; 

    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all NonCreditBearingIntervationTitle and prepare a for a create of a new NonCreditBearingIntervationTitle
 	 * @author TechFinium 
 	 * @see    NonCreditBearingIntervationTitle
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		noncreditbearingintervationtitleInfo();
	}

	/**
	 * Get all NonCreditBearingIntervationTitle for data table
 	 * @author TechFinium 
 	 * @see    NonCreditBearingIntervationTitle
 	 */
	public void noncreditbearingintervationtitleInfo() {
	 
			
			 dataModel = new LazyDataModel<NonCreditBearingIntervationTitle>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<NonCreditBearingIntervationTitle> retorno = new  ArrayList<NonCreditBearingIntervationTitle>();
			   
			   @Override 
			   public List<NonCreditBearingIntervationTitle> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allNonCreditBearingIntervationTitle(NonCreditBearingIntervationTitle.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(NonCreditBearingIntervationTitle.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(NonCreditBearingIntervationTitle obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public NonCreditBearingIntervationTitle getRowData(String rowKey) {
			        for(NonCreditBearingIntervationTitle obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert NonCreditBearingIntervationTitle into database
 	 * @author TechFinium 
 	 * @see    NonCreditBearingIntervationTitle
 	 */
	public void noncreditbearingintervationtitleInsert() {
		try {
				 service.create(this.noncreditbearingintervationtitle);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 noncreditbearingintervationtitleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update NonCreditBearingIntervationTitle in database
 	 * @author TechFinium 
 	 * @see    NonCreditBearingIntervationTitle
 	 */
    public void noncreditbearingintervationtitleUpdate() {
		try {
				 service.update(this.noncreditbearingintervationtitle);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 noncreditbearingintervationtitleInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete NonCreditBearingIntervationTitle from database
 	 * @author TechFinium 
 	 * @see    NonCreditBearingIntervationTitle
 	 */
	public void noncreditbearingintervationtitleDelete() {
		try {
			 service.delete(this.noncreditbearingintervationtitle);
			  prepareNew();
			 noncreditbearingintervationtitleInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of NonCreditBearingIntervationTitle 
 	 * @author TechFinium 
 	 * @see    NonCreditBearingIntervationTitle
 	 */
	public void prepareNew() {
		noncreditbearingintervationtitle = new NonCreditBearingIntervationTitle();
	}

/*
    public List<SelectItem> getNonCreditBearingIntervationTitleDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	noncreditbearingintervationtitleInfo();
    	for (NonCreditBearingIntervationTitle ug : noncreditbearingintervationtitleList) {
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
    public List<NonCreditBearingIntervationTitle> complete(String desc) {
		List<NonCreditBearingIntervationTitle> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
    
    public List<NonCreditBearingIntervationTitle> getNonCreditBearingIntervationTitleList() {
		return noncreditbearingintervationtitleList;
	}
	public void setNonCreditBearingIntervationTitleList(List<NonCreditBearingIntervationTitle> noncreditbearingintervationtitleList) {
		this.noncreditbearingintervationtitleList = noncreditbearingintervationtitleList;
	}
	public NonCreditBearingIntervationTitle getNoncreditbearingintervationtitle() {
		return noncreditbearingintervationtitle;
	}
	public void setNoncreditbearingintervationtitle(NonCreditBearingIntervationTitle noncreditbearingintervationtitle) {
		this.noncreditbearingintervationtitle = noncreditbearingintervationtitle;
	}

    public List<NonCreditBearingIntervationTitle> getNonCreditBearingIntervationTitlefilteredList() {
		return noncreditbearingintervationtitlefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param noncreditbearingintervationtitlefilteredList the new noncreditbearingintervationtitlefilteredList list
 	 * @see    NonCreditBearingIntervationTitle
 	 */	
	public void setNonCreditBearingIntervationTitlefilteredList(List<NonCreditBearingIntervationTitle> noncreditbearingintervationtitlefilteredList) {
		this.noncreditbearingintervationtitlefilteredList = noncreditbearingintervationtitlefilteredList;
	}

	
	public LazyDataModel<NonCreditBearingIntervationTitle> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<NonCreditBearingIntervationTitle> dataModel) {
		this.dataModel = dataModel;
	}
	
}
