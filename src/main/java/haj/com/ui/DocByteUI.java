package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.DocByte;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DocByteService;

@ManagedBean(name = "docbyteUI")
@ViewScoped
public class DocByteUI extends AbstractUI {

	private DocByteService service = new DocByteService();
	private List<DocByte> docbyteList = null;
	private List<DocByte> docbytefilteredList = null;
	private DocByte docbyte = null;
	private LazyDataModel<DocByte> dataModel; 

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
	 * Initialize method to get all DocByte and prepare a for a create of a new DocByte
 	 * @author TechFinium 
 	 * @see    DocByte
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		docbyteInfo();
	}

	/**
	 * Get all DocByte for data table
 	 * @author TechFinium 
 	 * @see    DocByte
 	 */
	public void docbyteInfo() {
	 
			
			 dataModel = new LazyDataModel<DocByte>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<DocByte> retorno = new  ArrayList<DocByte>();
			   
			   @Override 
			   public List<DocByte> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allDocByte(DocByte.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(DocByte.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(DocByte obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public DocByte getRowData(String rowKey) {
			        for(DocByte obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert DocByte into database
 	 * @author TechFinium 
 	 * @see    DocByte
 	 */
	public void docbyteInsert() {
		try {
				 service.create(this.docbyte);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 docbyteInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update DocByte in database
 	 * @author TechFinium 
 	 * @see    DocByte
 	 */
    public void docbyteUpdate() {
		try {
				 service.update(this.docbyte);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 docbyteInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete DocByte from database
 	 * @author TechFinium 
 	 * @see    DocByte
 	 */
	public void docbyteDelete() {
		try {
			 service.delete(this.docbyte);
			  prepareNew();
			 docbyteInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of DocByte 
 	 * @author TechFinium 
 	 * @see    DocByte
 	 */
	public void prepareNew() {
		docbyte = new DocByte();
	}

/*
    public List<SelectItem> getDocByteDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	docbyteInfo();
    	for (DocByte ug : docbyteList) {
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
    public List<DocByte> complete(String desc) {
		List<DocByte> l = null;
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
    
    public List<DocByte> getDocByteList() {
		return docbyteList;
	}
	public void setDocByteList(List<DocByte> docbyteList) {
		this.docbyteList = docbyteList;
	}
	public DocByte getDocbyte() {
		return docbyte;
	}
	public void setDocbyte(DocByte docbyte) {
		this.docbyte = docbyte;
	}

    public List<DocByte> getDocBytefilteredList() {
		return docbytefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param docbytefilteredList the new docbytefilteredList list
 	 * @see    DocByte
 	 */	
	public void setDocBytefilteredList(List<DocByte> docbytefilteredList) {
		this.docbytefilteredList = docbytefilteredList;
	}

	
	public LazyDataModel<DocByte> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DocByte> dataModel) {
		this.dataModel = dataModel;
	}
	
}
