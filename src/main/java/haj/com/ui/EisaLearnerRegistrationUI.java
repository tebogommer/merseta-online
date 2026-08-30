package haj.com.ui;

import javax.annotation.PostConstruct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.service.EisaLearnerRegistrationService;
import haj.com.service.JasperService;

import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;

import haj.com.entity.Company;
import haj.com.entity.EisaLearnerRegistration;
import haj.com.entity.Users;
import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "eisaLearnerRegistrationUI")
@ViewScoped
public class EisaLearnerRegistrationUI extends AbstractUI {

	private EisaLearnerRegistrationService service = new EisaLearnerRegistrationService();
	private List<EisaLearnerRegistration> eisaLearnerRegistrationList = null;
	private List<EisaLearnerRegistration> eisaLearnerRegistrationfilteredList = null;
	private EisaLearnerRegistration eisaLearnerRegistration = null;
	private LazyDataModel<EisaLearnerRegistration> dataModel;
	private Users learner =new Users();

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
	 * Initialize method to get all EisaLearnerRegistration and prepare a for a create of a new EisaLearnerRegistration
 	 * @author TechFinium 
 	 * @see    EisaLearnerRegistration
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		prepareNew();
		eisaLearnerRegistrationInfo();
		learner=getSessionUI().getActiveUser();
	}

	/**
	 * Get all EisaLearnerRegistration for data table
 	 * @author TechFinium 
 	 * @see    EisaLearnerRegistration
 	 */
	public void eisaLearnerRegistrationInfo() {
	 
			
			 dataModel = new LazyDataModel<EisaLearnerRegistration>() { 
			 
			   private static final long serialVersionUID = 1L; 
			   private List<EisaLearnerRegistration> retorno = new  ArrayList<EisaLearnerRegistration>();
			   
			   @Override 
			   public List<EisaLearnerRegistration> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
			   
				try {
					retorno = service.allEisaLearnerRegistration(EisaLearnerRegistration.class,first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(service.count(EisaLearnerRegistration.class,filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
			   }
			   
			    @Override
			    public Object getRowKey(EisaLearnerRegistration obj) {
			        return obj.getId();
			    }
			    
			    @Override
			    public EisaLearnerRegistration getRowData(String rowKey) {
			        for(EisaLearnerRegistration obj : retorno) {
			            if(obj.getId().equals(Long.valueOf(rowKey)))
			                return obj;
			        }
			        return null;
			    }			    
			    
			  }; 
			
			
	
	}

	/**
	 * Insert EisaLearnerRegistration into database
 	 * @author TechFinium 
 	 * @see    EisaLearnerRegistration
 	 */
	public void eisaLearnerRegistrationInsert() {
		try {
				 service.create(this.eisaLearnerRegistration);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 eisaLearnerRegistrationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Insert EisaLearnerRegistration into database
 	 * @author TechFinium 
 	 * @see    EisaLearnerRegistration
 	 */
	public void saveRegistration() {
		try {
			     eisaLearnerRegistration.setLearner(learner);
				 service.saveRegistration(this.eisaLearnerRegistration);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 eisaLearnerRegistrationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	public void downloadLearnerRegistrationForm() {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			JasperService.addLogo(params);
			JasperService.addImage(params, "checkbox-marked.png", "checked_image");
			JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
			String fileName = learner.getFirstName()+"_"+learner.getLastName()+"_Registration_Form_For_EISA.pdf";
			// INSERT JASPER HERE
			// MM-282
			SimpleDateFormat sdfDate=new SimpleDateFormat("dd MMM yyyy");
			SimpleDateFormat sdfTime=new SimpleDateFormat("HH:mm");
			Integer learnerAge=24;//To be fixed
			boolean statementOfResultsAttached=true;//To be fixed
			
			params.put("learner", learner);
			params.put("learnerAge", learnerAge);
			params.put("specialAssessmentNeeds", eisaLearnerRegistration.getSpecialAssesmentNeeds());
			params.put("summativeAssessment", eisaLearnerRegistration.getCompany());
			params.put("sdp", eisaLearnerRegistration.getSdp());
			params.put("statementOfResultsAttached", statementOfResultsAttached);
			params.put("qualification", eisaLearnerRegistration.getQualification());
			params.put("assessmentCentre", eisaLearnerRegistration.getAssessmentCentre());
			params.put("dateEISA", sdfDate.format(eisaLearnerRegistration.getEisaDate()));
			params.put("timeEISA", sdfTime.format(eisaLearnerRegistration.getEisaTime()));
			params.put("completence", eisaLearnerRegistration.getCompletence().getFriendlyName());
			
			JasperService.instance().createReportFromDBtoServletOutputStream("LearnerRegistrationFormForEISA.jasper", params, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update EisaLearnerRegistration in database
 	 * @author TechFinium 
 	 * @see    EisaLearnerRegistration
 	 */
    public void eisaLearnerRegistrationUpdate() {
		try {
				 service.update(this.eisaLearnerRegistration);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 eisaLearnerRegistrationInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
    
   
	/**
	 * Delete EisaLearnerRegistration from database
 	 * @author TechFinium 
 	 * @see    EisaLearnerRegistration
 	 */
	public void eisaLearnerRegistrationDelete() {
		try {
			 service.delete(this.eisaLearnerRegistration);
			  prepareNew();
			 eisaLearnerRegistrationInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of EisaLearnerRegistration 
 	 * @author TechFinium 
 	 * @see    EisaLearnerRegistration
 	 */
	public void prepareNew() {
		eisaLearnerRegistration = new EisaLearnerRegistration();
	}

/*
    public List<SelectItem> getEisaLearnerRegistrationDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	eisaLearnerRegistrationInfo();
    	for (EisaLearnerRegistration ug : eisaLearnerRegistrationList) {
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
    public List<EisaLearnerRegistration> complete(String desc) {
		List<EisaLearnerRegistration> l = null;
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
    
    public List<EisaLearnerRegistration> getEisaLearnerRegistrationList() {
		return eisaLearnerRegistrationList;
	}
	public void setEisaLearnerRegistrationList(List<EisaLearnerRegistration> eisaLearnerRegistrationList) {
		this.eisaLearnerRegistrationList = eisaLearnerRegistrationList;
	}
	public EisaLearnerRegistration getEisaLearnerRegistration() {
		return eisaLearnerRegistration;
	}
	public void setEisaLearnerRegistration(EisaLearnerRegistration eisaLearnerRegistration) {
		this.eisaLearnerRegistration = eisaLearnerRegistration;
	}

    public List<EisaLearnerRegistration> getEisaLearnerRegistrationfilteredList() {
		return eisaLearnerRegistrationfilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param eisaLearnerRegistrationfilteredList the new eisaLearnerRegistrationfilteredList list
 	 * @see    EisaLearnerRegistration
 	 */	
	public void setEisaLearnerRegistrationfilteredList(List<EisaLearnerRegistration> eisaLearnerRegistrationfilteredList) {
		this.eisaLearnerRegistrationfilteredList = eisaLearnerRegistrationfilteredList;
	}

	
	public LazyDataModel<EisaLearnerRegistration> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<EisaLearnerRegistration> dataModel) {
		this.dataModel = dataModel;
	}

	public Users getLearner() {
		return learner;
	}

	public void setLearner(Users learner) {
		this.learner = learner;
	}
	
}