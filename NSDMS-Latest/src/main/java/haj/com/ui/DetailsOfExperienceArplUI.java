package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.DetailsOfExperienceArpl;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DetailsOfExperienceArplService;

@ManagedBean(name = "detailsofexperiencearplUI")
@ViewScoped
public class DetailsOfExperienceArplUI extends AbstractUI {

	private DetailsOfExperienceArplService service = new DetailsOfExperienceArplService();
	private List<DetailsOfExperienceArpl> detailsofexperiencearplList = null;
	private List<DetailsOfExperienceArpl> detailsofexperiencearplfilteredList = null;
	private DetailsOfExperienceArpl detailsofexperiencearpl = null;
	private LazyDataModel<DetailsOfExperienceArpl> dataModel;

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
	 * Initialize method to get all DetailsOfExperienceArpl and prepare a for a
	 * create of a new DetailsOfExperienceArpl
	 * 
	 * @author TechFinium
	 * @see DetailsOfExperienceArpl
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		detailsofexperiencearplInfo();
	}

	/**
	 * Get all DetailsOfExperienceArpl for data table
	 * 
	 * @author TechFinium
	 * @see DetailsOfExperienceArpl
	 */
	public void detailsofexperiencearplInfo() {
		// dataModel = new DetailsOfExperienceArplDatamodel();
	}

	/**
	 * Insert DetailsOfExperienceArpl into database
	 * 
	 * @author TechFinium
	 * @see DetailsOfExperienceArpl
	 */
	public void detailsofexperiencearplInsert() {
		try {
			service.create(this.detailsofexperiencearpl);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			detailsofexperiencearplInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update DetailsOfExperienceArpl in database
	 * 
	 * @author TechFinium
	 * @see DetailsOfExperienceArpl
	 */
	public void detailsofexperiencearplUpdate() {
		try {
			service.update(this.detailsofexperiencearpl);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			detailsofexperiencearplInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete DetailsOfExperienceArpl from database
	 * 
	 * @author TechFinium
	 * @see DetailsOfExperienceArpl
	 */
	public void detailsofexperiencearplDelete() {
		try {
			service.delete(this.detailsofexperiencearpl);
			prepareNew();
			detailsofexperiencearplInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of DetailsOfExperienceArpl
	 * 
	 * @author TechFinium
	 * @see DetailsOfExperienceArpl
	 */
	public void prepareNew() {
		detailsofexperiencearpl = new DetailsOfExperienceArpl();
	}

	/*
	 * public List<SelectItem> getDetailsOfExperienceArplDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * detailsofexperiencearplInfo(); for (DetailsOfExperienceArpl ug :
	 * detailsofexperiencearplList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<DetailsOfExperienceArpl> complete(String desc) {
		List<DetailsOfExperienceArpl> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<DetailsOfExperienceArpl> getDetailsOfExperienceArplList() {
		return detailsofexperiencearplList;
	}

	public void setDetailsOfExperienceArplList(List<DetailsOfExperienceArpl> detailsofexperiencearplList) {
		this.detailsofexperiencearplList = detailsofexperiencearplList;
	}

	public DetailsOfExperienceArpl getDetailsofexperiencearpl() {
		return detailsofexperiencearpl;
	}

	public void setDetailsofexperiencearpl(DetailsOfExperienceArpl detailsofexperiencearpl) {
		this.detailsofexperiencearpl = detailsofexperiencearpl;
	}

	public List<DetailsOfExperienceArpl> getDetailsOfExperienceArplfilteredList() {
		return detailsofexperiencearplfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param detailsofexperiencearplfilteredList
	 *            the new detailsofexperiencearplfilteredList list
	 * @see DetailsOfExperienceArpl
	 */
	public void setDetailsOfExperienceArplfilteredList(
			List<DetailsOfExperienceArpl> detailsofexperiencearplfilteredList) {
		this.detailsofexperiencearplfilteredList = detailsofexperiencearplfilteredList;
	}

	public LazyDataModel<DetailsOfExperienceArpl> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DetailsOfExperienceArpl> dataModel) {
		this.dataModel = dataModel;
	}

}
