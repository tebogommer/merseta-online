package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.StakeholderRelations;
import haj.com.entity.lookup.StakeholderRelationsSurvey;
import haj.com.service.lookup.StakeholderRelationsSurveyService;
import haj.com.entity.datamodel.lookup.StakeholderRelationsSurveyDatamodel;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "stakeholderrelationssurveyUI")
@ViewScoped
public class StakeholderRelationsSurveyUI extends AbstractUI {

	private StakeholderRelationsSurveyService service = new StakeholderRelationsSurveyService();
	private List<StakeholderRelationsSurvey> stakeholderrelationssurveyList = null;
	private List<StakeholderRelationsSurvey> stakeholderrelationssurveyfilteredList = null;
	private StakeholderRelationsSurvey stakeholderrelationssurvey = null;
	private StakeholderRelations stakeholderrelations;
	private LazyDataModel<StakeholderRelationsSurvey> dataModel;

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
	 * Initialize method to get all StakeholderRelationsSurvey and prepare a for a
	 * create of a new StakeholderRelationsSurvey
	 * 
	 * @author TechFinium
	 * @see StakeholderRelationsSurvey
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		stakeholderrelationssurveyInfo();
	}

	/**
	 * Get all StakeholderRelationsSurvey for data table
	 * 
	 * @author TechFinium
	 * @see StakeholderRelationsSurvey
	 */
	public void stakeholderrelationssurveyInfo() {
		dataModel = new StakeholderRelationsSurveyDatamodel();
	}
	
	/**
	 * Preps new instance of stakeholderrelationssurvey
	 */
	public void prepNewStakeholderRelationsSurvey(){
		try {
			stakeholderrelationssurvey = new StakeholderRelationsSurvey();
			if (stakeholderrelations == null || stakeholderrelations.getId() == null) {
				throw new Exception("Unable To Assign Stakeholder Relations, contact support!");
			}
			stakeholderrelationssurvey.setStakeholderRelations(stakeholderrelations);
			runClientSideExecute("PF('dlgQuestion').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}

	/**
	 * Insert StakeholderRelationsSurvey into database
	 * 
	 * @author TechFinium
	 * @see StakeholderRelationsSurvey
	 */
	public void stakeholderrelationssurveyInsert() {
		try {
			if (stakeholderrelations == null || stakeholderrelations.getId() == null) {
				throw new Exception("Unable To Assign Stakeholder Relations, contact support!");
			}
			service.create(this.stakeholderrelationssurvey);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			runClientSideExecute("PF('dlgQuestion').hide()");
			stakeholderrelationssurveyInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update StakeholderRelationsSurvey in database
	 * 
	 * @author TechFinium
	 * @see StakeholderRelationsSurvey
	 */
	public void stakeholderrelationssurveyUpdate() {
		try {
			service.update(this.stakeholderrelationssurvey);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			stakeholderrelationssurveyInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete StakeholderRelationsSurvey from database
	 * 
	 * @author TechFinium
	 * @see StakeholderRelationsSurvey
	 */
	public void stakeholderrelationssurveyDelete() {
		try {
			service.delete(this.stakeholderrelationssurvey);
			prepareNew();
			stakeholderrelationssurveyInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of StakeholderRelationsSurvey
	 * 
	 * @author TechFinium
	 * @see StakeholderRelationsSurvey
	 */
	public void prepareNew() {
		stakeholderrelationssurvey = new StakeholderRelationsSurvey();
	}

	/*
	 * public List<SelectItem> getStakeholderRelationsSurveyDD() { List<SelectItem>
	 * l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * stakeholderrelationssurveyInfo(); for (StakeholderRelationsSurvey ug :
	 * stakeholderrelationssurveyList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<StakeholderRelationsSurvey> complete(String desc) {
		List<StakeholderRelationsSurvey> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<StakeholderRelationsSurvey> getStakeholderRelationsSurveyList() {
		return stakeholderrelationssurveyList;
	}

	public void setStakeholderRelationsSurveyList(List<StakeholderRelationsSurvey> stakeholderrelationssurveyList) {
		this.stakeholderrelationssurveyList = stakeholderrelationssurveyList;
	}

	public StakeholderRelationsSurvey getStakeholderrelationssurvey() {
		return stakeholderrelationssurvey;
	}

	public void setStakeholderrelationssurvey(StakeholderRelationsSurvey stakeholderrelationssurvey) {
		this.stakeholderrelationssurvey = stakeholderrelationssurvey;
	}

	public List<StakeholderRelationsSurvey> getStakeholderRelationsSurveyfilteredList() {
		return stakeholderrelationssurveyfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param stakeholderrelationssurveyfilteredList
	 *            the new stakeholderrelationssurveyfilteredList list
	 * @see StakeholderRelationsSurvey
	 */
	public void setStakeholderRelationsSurveyfilteredList(List<StakeholderRelationsSurvey> stakeholderrelationssurveyfilteredList) {
		this.stakeholderrelationssurveyfilteredList = stakeholderrelationssurveyfilteredList;
	}

	public LazyDataModel<StakeholderRelationsSurvey> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<StakeholderRelationsSurvey> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * @return the stakeholderrelations
	 */
	public StakeholderRelations getStakeholderrelations() {
		return stakeholderrelations;
	}

	/**
	 * @param stakeholderrelations
	 *            the stakeholderrelations to set
	 */
	public void setStakeholderrelations(StakeholderRelations stakeholderrelations) {
		this.stakeholderrelations = stakeholderrelations;
	}

}
