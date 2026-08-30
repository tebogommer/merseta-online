package  haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.lookup.SaqaQualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SaqaQualificationService;

// TODO: Auto-generated Javadoc
/**
 * The Class SaqaQualificationUI.
 */
@ManagedBean(name = "saqaqualificationUI")
@ViewScoped
public class SaqaQualificationUI extends AbstractUI {

	/** The service. */
	private SaqaQualificationService service = new SaqaQualificationService();

	/** The saqaqualification list. */
	private List<SaqaQualification> saqaqualificationList = null;

	/** The saqaqualificationfiltered list. */
	private List<SaqaQualification> saqaqualificationfilteredList = null;

	/** The saqaqualification. */
	private SaqaQualification saqaqualification = null;

	/** The data model. */
	private LazyDataModel<SaqaQualification> dataModel;

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
	 * Initialize method to get all SaqaQualification and prepare a for a create of a new SaqaQualification.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    SaqaQualification
	 */
	private void runInit() throws Exception {
		prepareNew();
		saqaqualificationInfo();
	}

	/**
	 * Get all SaqaQualification for data table.
	 *
	 * @author TechFinium
	 * @see    SaqaQualification
	 */
	public void saqaqualificationInfo() {


			 dataModel = new LazyDataModel<SaqaQualification>() {

			   private static final long serialVersionUID = 1L;
			   private List<SaqaQualification> retorno = new  ArrayList<SaqaQualification>();

			   @Override
			   public List<SaqaQualification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  {

				try {
					retorno = service.allSaqaQualification(SaqaQualification.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(SaqaQualification.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
			    return retorno;
			   }

			    @Override
			    public Object getRowKey(SaqaQualification obj) {
			        return obj.getId();
			    }

			    @Override
			    public SaqaQualification getRowData(String rowKey) {
			        for(SaqaQualification obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }

			  };



	}

	/**
	 * Insert SaqaQualification into database.
	 *
	 * @author TechFinium
	 * @see    SaqaQualification
	 */
	public void saqaqualificationInsert() {
		try {
				 service.create(this.saqaqualification);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 saqaqualificationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Update SaqaQualification in database.
	 *
	 * @author TechFinium
	 * @see    SaqaQualification
	 */
    public void saqaqualificationUpdate() {
		try {
				 service.update(this.saqaqualification);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 saqaqualificationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete SaqaQualification from database.
	 *
	 * @author TechFinium
	 * @see    SaqaQualification
	 */
	public void saqaqualificationDelete() {
		try {
			 service.delete(this.saqaqualification);
			  prepareNew();
			 saqaqualificationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted "));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of SaqaQualification .
	 *
	 * @author TechFinium
	 * @see    SaqaQualification
	 */
	public void prepareNew() {
		saqaqualification = new SaqaQualification();
	}

/*
    public List<SelectItem> getSaqaQualificationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	saqaqualificationInfo();
    	for (SaqaQualification ug : saqaqualificationList) {
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
    public List<SaqaQualification> complete(String desc) {
		List<SaqaQualification> l = null;
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
     * Gets the saqa qualification list.
     *
     * @return the saqa qualification list
     */
    public List<SaqaQualification> getSaqaQualificationList() {
		return saqaqualificationList;
	}

	/**
	 * Sets the saqa qualification list.
	 *
	 * @param saqaqualificationList the new saqa qualification list
	 */
	public void setSaqaQualificationList(List<SaqaQualification> saqaqualificationList) {
		this.saqaqualificationList = saqaqualificationList;
	}

	/**
	 * Gets the saqaqualification.
	 *
	 * @return the saqaqualification
	 */
	public SaqaQualification getSaqaqualification() {
		return saqaqualification;
	}

	/**
	 * Sets the saqaqualification.
	 *
	 * @param saqaqualification the new saqaqualification
	 */
	public void setSaqaqualification(SaqaQualification saqaqualification) {
		this.saqaqualification = saqaqualification;
	}

    /**
     * Gets the saqa qualificationfiltered list.
     *
     * @return the saqa qualificationfiltered list
     */
    public List<SaqaQualification> getSaqaQualificationfilteredList() {
		return saqaqualificationfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param saqaqualificationfilteredList the new saqaqualificationfilteredList list
	 * @see    SaqaQualification
	 */
	public void setSaqaQualificationfilteredList(List<SaqaQualification> saqaqualificationfilteredList) {
		this.saqaqualificationfilteredList = saqaqualificationfilteredList;
	}


	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<SaqaQualification> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<SaqaQualification> dataModel) {
		this.dataModel = dataModel;
	}

}
