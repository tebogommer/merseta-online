package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.DetailsOfTrainingArpl;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DetailsOfTrainingArplService;

@ManagedBean(name = "detailsoftrainingarplUI")
@ViewScoped
public class DetailsOfTrainingArplUI extends AbstractUI {

	private DetailsOfTrainingArplService service = new DetailsOfTrainingArplService();
	private List<DetailsOfTrainingArpl> detailsoftrainingarplList = null;
	private List<DetailsOfTrainingArpl> detailsoftrainingarplfilteredList = null;
	private DetailsOfTrainingArpl detailsoftrainingarpl = null;
	private LazyDataModel<DetailsOfTrainingArpl> dataModel;

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
	 * Initialize method to get all DetailsOfTrainingArpl and prepare a for a
	 * create of a new DetailsOfTrainingArpl
	 * 
	 * @author TechFinium
	 * @see DetailsOfTrainingArpl
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		detailsoftrainingarplInfo();
	}

	/**
	 * Get all DetailsOfTrainingArpl for data table
	 * 
	 * @author TechFinium
	 * @see DetailsOfTrainingArpl
	 */
	public void detailsoftrainingarplInfo() {
		// dataModel = new DetailsOfTrainingArplDatamodel();
	}

	/**
	 * Insert DetailsOfTrainingArpl into database
	 * 
	 * @author TechFinium
	 * @see DetailsOfTrainingArpl
	 */
	public void detailsoftrainingarplInsert() {
		try {
			service.create(this.detailsoftrainingarpl);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			detailsoftrainingarplInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DetailsOfTrainingArpl in database
	 * 
	 * @author TechFinium
	 * @see DetailsOfTrainingArpl
	 */
	public void detailsoftrainingarplUpdate() {
		try {
			service.update(this.detailsoftrainingarpl);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			detailsoftrainingarplInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DetailsOfTrainingArpl from database
	 * 
	 * @author TechFinium
	 * @see DetailsOfTrainingArpl
	 */
	public void detailsoftrainingarplDelete() {
		try {
			service.delete(this.detailsoftrainingarpl);
			prepareNew();
			detailsoftrainingarplInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DetailsOfTrainingArpl
	 * 
	 * @author TechFinium
	 * @see DetailsOfTrainingArpl
	 */
	public void prepareNew() {
		detailsoftrainingarpl = new DetailsOfTrainingArpl();
	}

	/*
	 * public List<SelectItem> getDetailsOfTrainingArplDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * detailsoftrainingarplInfo(); for (DetailsOfTrainingArpl ug :
	 * detailsoftrainingarplList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DetailsOfTrainingArpl> complete(String desc) {
		List<DetailsOfTrainingArpl> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DetailsOfTrainingArpl> getDetailsOfTrainingArplList() {
		return detailsoftrainingarplList;
	}

	public void setDetailsOfTrainingArplList(List<DetailsOfTrainingArpl> detailsoftrainingarplList) {
		this.detailsoftrainingarplList = detailsoftrainingarplList;
	}

	public DetailsOfTrainingArpl getDetailsoftrainingarpl() {
		return detailsoftrainingarpl;
	}

	public void setDetailsoftrainingarpl(DetailsOfTrainingArpl detailsoftrainingarpl) {
		this.detailsoftrainingarpl = detailsoftrainingarpl;
	}

	public List<DetailsOfTrainingArpl> getDetailsOfTrainingArplfilteredList() {
		return detailsoftrainingarplfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param detailsoftrainingarplfilteredList
	 *            the new detailsoftrainingarplfilteredList list
	 * @see DetailsOfTrainingArpl
	 */
	public void setDetailsOfTrainingArplfilteredList(List<DetailsOfTrainingArpl> detailsoftrainingarplfilteredList) {
		this.detailsoftrainingarplfilteredList = detailsoftrainingarplfilteredList;
	}

	public LazyDataModel<DetailsOfTrainingArpl> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DetailsOfTrainingArpl> dataModel) {
		this.dataModel = dataModel;
	}

}
