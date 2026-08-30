package  haj.com.ui;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Doc;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SARSConstants;
import haj.com.sars.SarsLeviesPaid;
import haj.com.service.SarsLeviesPaidService;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsLeviesPaidUI.
 */
@ManagedBean(name = "sarsleviespaidUI")
@ViewScoped
public class SarsLeviesPaidUI extends AbstractUI {

	/** The service. */
	private SarsLeviesPaidService service = new SarsLeviesPaidService();
	
	/** The sarsleviespaid list. */
	private List<SarsLeviesPaid> sarsleviespaidList = null;
	
	/** The sarsleviespaidfiltered list. */
	private List<SarsLeviesPaid> sarsleviespaidfilteredList = null;
	
	/** The sarsleviespaid. */
	private SarsLeviesPaid sarsleviespaid = null;
	
	/** The data model. */
	private LazyDataModel<SarsLeviesPaid> dataModel; 

	/** The doc. */
	private Doc doc;
	
    /**
     * Inits the.
     */
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
	 * Initialize method to get all SarsLeviesPaid and prepare a for a create of a new SarsLeviesPaid.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    SarsLeviesPaid
	 */
	private void runInit() throws Exception {
		prepareNew();
		sarsleviespaidInfo();
		service.sarsLeviesPaidWithNoLevyFileLink();
	}

	/**
	 * Get all SarsLeviesPaid for data table.
	 *
	 * @author TechFinium
	 * @see    SarsLeviesPaid
	 */
	public void sarsleviespaidInfo() {
	 
			
			 dataModel = new LazyDataModel<SarsLeviesPaid>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<SarsLeviesPaid> retorno = new  ArrayList<SarsLeviesPaid>();
			   
			   @Override 
			   public List<SarsLeviesPaid> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					if (sortField==null) {
					   retorno = service.allSarsLeviesPaid(SarsLeviesPaid.class,first,pageSize,"glPostingDate",SortOrder.DESCENDING,filters);
					}
					else {
					  retorno = service.allSarsLeviesPaid(SarsLeviesPaid.class,first,pageSize,sortField,sortOrder,filters);
					}
					dataModel.setRowCount(service.count(SarsLeviesPaid.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(SarsLeviesPaid obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public SarsLeviesPaid getRowData(String rowKey) {
			        for(SarsLeviesPaid obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert SarsLeviesPaid into database.
	 *
	 * @author TechFinium
	 * @see    SarsLeviesPaid
	 */
	public void sarsleviespaidInsert() {
		try {
			
				 service.create(this.sarsleviespaid, getSessionUI().getActiveUser(),this.doc);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 sarsleviespaidInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update SarsLeviesPaid in database.
	 *
	 * @author TechFinium
	 * @see    SarsLeviesPaid
	 */
    public void sarsleviespaidUpdate() {
		try {
				 service.update(this.sarsleviespaid);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 sarsleviespaidInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SarsLeviesPaid from database.
	 *
	 * @author TechFinium
	 * @see    SarsLeviesPaid
	 */
	public void sarsleviespaidDelete() {
		try {
			 service.delete(this.sarsleviespaid);
			  prepareNew();
			 sarsleviespaidInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of SarsLeviesPaid .
	 *
	 * @author TechFinium
	 * @see    SarsLeviesPaid
	 */
	public void prepareNew() {
		sarsleviespaid = new SarsLeviesPaid();
		this.doc = new Doc();
		this.doc.setBankStatement(sarsleviespaid);
		this.sarsleviespaid.setDocs(new ArrayList<Doc>());
		this.sarsleviespaid.getDocs().add(doc);
		
	}
	
	
	public void prepareUpdate() {
		try {
			this.sarsleviespaid.setLevyFileD(SARSConstants.sdf4.parse(this.sarsleviespaid.getLevyFile()));
		} catch (ParseException e) {
			logger.fatal(e);
		}
        if (sarsleviespaid.getDocs()==null || sarsleviespaid.getDocs().size()==0) {
		  this.doc = new Doc();
		  this.doc.setBankStatement(sarsleviespaid);
		  this.sarsleviespaid.setDocs(new ArrayList<Doc>());
		  this.sarsleviespaid.getDocs().add(doc);
        }
        else {
        	   this.doc = sarsleviespaid.getDocs().get(0);
        	   this.doc.setData(null);
        }
	}

/*
    public List<SelectItem> getSarsLeviesPaidDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	sarsleviespaidInfo();
    	for (SarsLeviesPaid ug : sarsleviespaidList) {
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
    public List<SarsLeviesPaid> complete(String desc) {
		List<SarsLeviesPaid> l = null;
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
    
    /**
     * Gets the sars levies paid list.
     *
     * @return the sars levies paid list
     */
    public List<SarsLeviesPaid> getSarsLeviesPaidList() {
		return sarsleviespaidList;
	}
	
	/**
	 * Sets the sars levies paid list.
	 *
	 * @param sarsleviespaidList the new sars levies paid list
	 */
	public void setSarsLeviesPaidList(List<SarsLeviesPaid> sarsleviespaidList) {
		this.sarsleviespaidList = sarsleviespaidList;
	}
	
	/**
	 * Gets the sarsleviespaid.
	 *
	 * @return the sarsleviespaid
	 */
	public SarsLeviesPaid getSarsleviespaid() {
		return sarsleviespaid;
	}
	
	/**
	 * Sets the sarsleviespaid.
	 *
	 * @param sarsleviespaid the new sarsleviespaid
	 */
	public void setSarsleviespaid(SarsLeviesPaid sarsleviespaid) {
		this.sarsleviespaid = sarsleviespaid;
	}

    /**
     * Gets the sars levies paidfiltered list.
     *
     * @return the sars levies paidfiltered list
     */
    public List<SarsLeviesPaid> getSarsLeviesPaidfilteredList() {
		return sarsleviespaidfilteredList;
	}
	
	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param sarsleviespaidfilteredList the new sarsleviespaidfilteredList list
	 * @see    SarsLeviesPaid
	 */	
	public void setSarsLeviesPaidfilteredList(List<SarsLeviesPaid> sarsleviespaidfilteredList) {
		this.sarsleviespaidfilteredList = sarsleviespaidfilteredList;
	}

	
	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<SarsLeviesPaid> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<SarsLeviesPaid> dataModel) {
		this.dataModel = dataModel;
	}
	
	public void storeFile(FileUploadEvent event) {
		try {
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
		
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}
}
