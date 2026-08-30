package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Blank;
import haj.com.entity.lookup.SaqaQualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.SaqaQualificationService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "assignWorkplaceApprovalQualificationsUI")
@ViewScoped
public class AssignWorkplaceApprovalQualificationsUI extends AbstractUI {

	/** Entity Level */
	private SaqaQualification saqaQualification = null;

	/** Service Level */
	private SaqaQualificationService saqaQualificationService = new SaqaQualificationService();

	/** Data Model Lists */
	private LazyDataModel<SaqaQualification> saqaQualificatioDataModelList;

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
	 * Initialize method to get all Blank and prepare a for a create of a new
	 * Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		saqaQualificatioDataModelListInfo();
	}

	/**
	 * Sets if qualification requires workplace approval 
	 */
	public void setRequiredNotRequired() {
		try {
			if (saqaQualification.getWorkplaceApprovalRequired() == null || saqaQualification.getWorkplaceApprovalRequired()) {
				saqaQualification.setWorkplaceApprovalRequired(false);
			} else {
				saqaQualification.setWorkplaceApprovalRequired(true);
			}
			saqaQualificationService.create(saqaQualification);
			saqaQualificationService.createLogChangeToWorkplaceApproval(saqaQualification, getSessionUI().getActiveUser());
			if (saqaQualification.getWorkplaceApprovalRequired()) {
				addInfoMessage("Workplace Approval Required, Set For Qualification: "
						+ saqaQualification.getQualificationtitle());
			} else {
				addInfoMessage("Workplace Approval Not Required, Set For Qualification: "
						+ saqaQualification.getQualificationtitle());
			}
			saqaQualificatioDataModelListInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void setRequiredNotRequiredAndMentorRatio() {
		try {
			if (saqaQualification.getWorkplaceApprovalRequired() == null || saqaQualification.getWorkplaceApprovalRequired()) {				
					saqaQualification.setWorkplaceApprovalRequired(false);	
					saqaQualification.setLearnerMentorRatio(null);
			} else {
				if(saqaQualification.getLearnerMentorRatio() !=null) {
					saqaQualification.setWorkplaceApprovalRequired(true);					
				}else {
					throw new Exception("Select Mentor Learner Ration");
				}
			}
			saqaQualificationService.create(saqaQualification);
			saqaQualificationService.createLogChangeToWorkplaceApproval(saqaQualification, getSessionUI().getActiveUser());
			if (saqaQualification.getWorkplaceApprovalRequired()) {
				addInfoMessage("Workplace Approval Required, Set For Qualification: "
						+ saqaQualification.getQualificationtitle());
			} else {
				addInfoMessage("Workplace Approval Not Required, Set For Qualification: "
						+ saqaQualification.getQualificationtitle());
			}
			saqaQualificatioDataModelListInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void setRequiredNotRequiredAndMentorRatioUpdate() {
		try {
			if(saqaQualification.getWorkplaceApprovalRequired() && saqaQualification.getLearnerMentorRatio() !=null) {
				saqaQualificationService.create(saqaQualification);
				saqaQualificationService.createLogChangeToWorkplaceApproval(saqaQualification, getSessionUI().getActiveUser());
				
				addInfoMessage("Workplace Approval Required, Set For Qualification: "
							+ saqaQualification.getQualificationtitle());
				
			}else if(!saqaQualification.getWorkplaceApprovalRequired()){
				saqaQualification.setLearnerMentorRatio(null);
				saqaQualificationService.create(saqaQualification);
				addInfoMessage("Workplace Approval Not Required, Set For Qualification: "
						+ saqaQualification.getQualificationtitle());
			}else {
				addErrorMessage("Please select any change before proceeding");
			}
			saqaQualificatioDataModelListInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void clearRatio(SaqaQualification saqaQualification) {
		try {
			if(saqaQualification.getWorkplaceApprovalRequired() == null || !saqaQualification.getWorkplaceApprovalRequired()) {
				saqaQualification.setLearnerMentorRatio(null);
			}	
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void saqaQualificatioDataModelListInfo() {
		saqaQualificatioDataModelList = new LazyDataModel<SaqaQualification>() {
			private static final long serialVersionUID = 1L;
			private List<SaqaQualification> retorno = new ArrayList<SaqaQualification>();

			@Override
			public List<SaqaQualification> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = saqaQualificationService.allSaqaQualification(SaqaQualification.class, first, pageSize,
							sortField, sortOrder, filters);
					saqaQualificatioDataModelList.setRowCount(saqaQualificationService.count(SaqaQualification.class, filters));
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
				for (SaqaQualification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/** Getters and setters */
	public SaqaQualification getSaqaQualification() {
		return saqaQualification;
	}

	public void setSaqaQualification(SaqaQualification saqaQualification) {
		this.saqaQualification = saqaQualification;
	}

	public LazyDataModel<SaqaQualification> getSaqaQualificatioDataModelList() {
		return saqaQualificatioDataModelList;
	}

	public void setSaqaQualificatioDataModelList(LazyDataModel<SaqaQualification> saqaQualificatioDataModelList) {
		this.saqaQualificatioDataModelList = saqaQualificatioDataModelList;
	}


}
