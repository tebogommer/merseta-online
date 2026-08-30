package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.NQFAlignment;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.NQFAlignmentService;

// TODO: Auto-generated Javadoc
/**
 * The Class NQFAlignmentUI.
 */
@ManagedBean(name = "nqfalignmentUI")
@ViewScoped
public class NQFAlignmentUI extends AbstractUI {

	/** The service. */
	private NQFAlignmentService service = new NQFAlignmentService();
	
	/** The nqfalignment list. */
	private List<NQFAlignment> nqfalignmentList = null;
	
	/** The nqfalignmentfiltered list. */
	private List<NQFAlignment> nqfalignmentfilteredList = null;
	
	/** The nqfalignment. */
	private NQFAlignment nqfalignment = null;
	
	/** The data model. */
	private LazyDataModel<NQFAlignment> dataModel; 

    /**
     * Inits the.
     */
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
	 * Initialize method to get all NQFAlignment and prepare a for a create of a new NQFAlignment.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    NQFAlignment
	 */
	private void runInit() throws Exception {
		prepareNew();
		nqfalignmentInfo();
	}

	/**
	 * Get all NQFAlignment for data table.
	 *
	 * @author TechFinium
	 * @see    NQFAlignment
	 */
	public void nqfalignmentInfo() {
	 
			
			 dataModel = new LazyDataModel<NQFAlignment>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<NQFAlignment> retorno = new  ArrayList<NQFAlignment>();
			   
			   @Override 
			   public List<NQFAlignment> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allNQFAlignment(NQFAlignment.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(NQFAlignment.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(NQFAlignment obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public NQFAlignment getRowData(String rowKey) {
			        for(NQFAlignment obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert NQFAlignment into database.
	 *
	 * @author TechFinium
	 * @see    NQFAlignment
	 */
	public void nqfalignmentInsert() {
		try {
				 service.create(this.nqfalignment);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 nqfalignmentInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update NQFAlignment in database.
	 *
	 * @author TechFinium
	 * @see    NQFAlignment
	 */
    public void nqfalignmentUpdate() {
		try {
				 service.update(this.nqfalignment);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 nqfalignmentInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete NQFAlignment from database.
	 *
	 * @author TechFinium
	 * @see    NQFAlignment
	 */
	public void nqfalignmentDelete() {
		try {
			 service.delete(this.nqfalignment);
			  prepareNew();
			 nqfalignmentInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of NQFAlignment .
	 *
	 * @author TechFinium
	 * @see    NQFAlignment
	 */
	public void prepareNew() {
		nqfalignment = new NQFAlignment();
	}

/*
    public List<SelectItem> getNQFAlignmentDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	nqfalignmentInfo();
    	for (NQFAlignment ug : nqfalignmentList) {
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
    public List<NQFAlignment> complete(String desc) {
		List<NQFAlignment> l = null;
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
    
    /**
     * Gets the NQF alignment list.
     *
     * @return the NQF alignment list
     */
    public List<NQFAlignment> getNQFAlignmentList() {
		return nqfalignmentList;
	}
	
	/**
	 * Sets the NQF alignment list.
	 *
	 * @param nqfalignmentList the new NQF alignment list
	 */
	public void setNQFAlignmentList(List<NQFAlignment> nqfalignmentList) {
		this.nqfalignmentList = nqfalignmentList;
	}
	
	/**
	 * Gets the nqfalignment.
	 *
	 * @return the nqfalignment
	 */
	public NQFAlignment getNqfalignment() {
		return nqfalignment;
	}
	
	/**
	 * Sets the nqfalignment.
	 *
	 * @param nqfalignment the new nqfalignment
	 */
	public void setNqfalignment(NQFAlignment nqfalignment) {
		this.nqfalignment = nqfalignment;
	}

    /**
     * Gets the NQF alignmentfiltered list.
     *
     * @return the NQF alignmentfiltered list
     */
    public List<NQFAlignment> getNQFAlignmentfilteredList() {
		return nqfalignmentfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param nqfalignmentfilteredList the new nqfalignmentfilteredList list
	 * @see    NQFAlignment
	 */	
	public void setNQFAlignmentfilteredList(List<NQFAlignment> nqfalignmentfilteredList) {
		this.nqfalignmentfilteredList = nqfalignmentfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<NQFAlignment> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<NQFAlignment> dataModel) {
		this.dataModel = dataModel;
	}
	
}
