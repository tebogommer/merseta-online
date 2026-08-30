package haj.com.wsp.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.WspImpactOfStaffTraining;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspImpactOfStaffTrainingService;

@ManagedBean(name = "wspimpactofstafftrainingUI")
@ViewScoped
public class WspImpactOfStaffTrainingUI extends AbstractUI {

	private WspImpactOfStaffTrainingService service = new WspImpactOfStaffTrainingService();
	private List<WspImpactOfStaffTraining> wspimpactofstafftrainingList = null;
	private List<WspImpactOfStaffTraining> wspimpactofstafftrainingfilteredList = null;
	private WspImpactOfStaffTraining wspimpactofstafftraining = null;
	private LazyDataModel<WspImpactOfStaffTraining> dataModel;

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
	 * Initialize method to get all WspImpactOfStaffTraining and prepare a for a
	 * create of a new WspImpactOfStaffTraining
	 * 
	 * @author TechFinium
	 * @see WspImpactOfStaffTraining
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		wspimpactofstafftrainingInfo();
	}

	/**
	 * Get all WspImpactOfStaffTraining for data table
	 * 
	 * @author TechFinium
	 * @see WspImpactOfStaffTraining
	 */
	public void wspimpactofstafftrainingInfo() {
		try {
			wspimpactofstafftrainingList = service.findByWsp(getSessionUI().getWspSession().getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert WspImpactOfStaffTraining into database
	 * 
	 * @author TechFinium
	 * @see WspImpactOfStaffTraining
	 */
	public void wspimpactofstafftrainingInsert() {
		try {
			service.create(this.wspimpactofstafftraining);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			wspimpactofstafftrainingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update WspImpactOfStaffTraining in database
	 * 
	 * @author TechFinium
	 * @see WspImpactOfStaffTraining
	 */
	public void wspimpactofstafftrainingUpdate() {
		try {
			service.updateBatch(wspimpactofstafftrainingList);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			wspimpactofstafftrainingInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete WspImpactOfStaffTraining from database
	 * 
	 * @author TechFinium
	 * @see WspImpactOfStaffTraining
	 */
	public void wspimpactofstafftrainingDelete() {
		try {
			service.delete(this.wspimpactofstafftraining);
			prepareNew();
			wspimpactofstafftrainingInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of WspImpactOfStaffTraining
	 * 
	 * @author TechFinium
	 * @see WspImpactOfStaffTraining
	 */
	public void prepareNew() {
		wspimpactofstafftraining = new WspImpactOfStaffTraining();
	}

	/*
	 * public List<SelectItem> getWspImpactOfStaffTrainingDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * wspimpactofstafftrainingInfo(); for (WspImpactOfStaffTraining ug :
	 * wspimpactofstafftrainingList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<WspImpactOfStaffTraining> complete(String desc) {
		List<WspImpactOfStaffTraining> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<WspImpactOfStaffTraining> getWspImpactOfStaffTrainingList() {
		return wspimpactofstafftrainingList;
	}

	public void setWspImpactOfStaffTrainingList(List<WspImpactOfStaffTraining> wspimpactofstafftrainingList) {
		this.wspimpactofstafftrainingList = wspimpactofstafftrainingList;
	}

	public WspImpactOfStaffTraining getWspimpactofstafftraining() {
		return wspimpactofstafftraining;
	}

	public void setWspimpactofstafftraining(WspImpactOfStaffTraining wspimpactofstafftraining) {
		this.wspimpactofstafftraining = wspimpactofstafftraining;
	}

	public List<WspImpactOfStaffTraining> getWspImpactOfStaffTrainingfilteredList() {
		return wspimpactofstafftrainingfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param wspimpactofstafftrainingfilteredList
	 *            the new wspimpactofstafftrainingfilteredList list
	 * @see WspImpactOfStaffTraining
	 */
	public void setWspImpactOfStaffTrainingfilteredList(
			List<WspImpactOfStaffTraining> wspimpactofstafftrainingfilteredList) {
		this.wspimpactofstafftrainingfilteredList = wspimpactofstafftrainingfilteredList;
	}

	public LazyDataModel<WspImpactOfStaffTraining> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspImpactOfStaffTraining> dataModel) {
		this.dataModel = dataModel;
	}

}
