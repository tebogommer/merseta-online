package haj.com.wsp.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Wsp;
import haj.com.entity.WspSkillsRequirements;
import haj.com.entity.enums.PastFutureEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspSkillsRequirementsService;
import haj.com.service.YesNoLookupService;

// TODO: Auto-generated Javadoc
/**
 * The Class WspSkillsRequirementsUI.
 */
@ManagedBean(name = "wspSkillsRequirementsUI")
@ViewScoped
public class WspSkillsRequirementsUI extends AbstractUI {

	/** The wsp skills requirements service. */
	private WspSkillsRequirementsService wspSkillsRequirementsService = new WspSkillsRequirementsService();

	/** The wsp skills requirements. */
	private WspSkillsRequirements wspSkillsRequirements= new WspSkillsRequirements();
	private WspSkillsRequirements wspSkillsRequirementsFuture;

	/** The wsp skills requirements list. */
	private List<WspSkillsRequirements> wspSkillsRequirementsList;
	
	/**WspSkillsRequirements lazy data model*/
	private LazyDataModel<WspSkillsRequirements> dataModel;

	/** The wsp. */
	private Wsp wsp;
	private boolean disableYesNo;
	private boolean disableForm;

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
	 * Run init.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		wspSkillsRequirementsInfo();
		prepareWspSkillsRequirements();
	}

	/**
	 * Prepare wsp skills requirements.
	 */
	public void prepareWspSkillsRequirements() {
		try {
			prepNewSkillsRequirementPast();
			prepNewSkillsRequirementFuture();
			disableForm = false;
			this.wspSkillsRequirementsList = new ArrayList<>();
			if (getSessionUI().getWspSession() != null) {
				this.wspSkillsRequirementsList = wspSkillsRequirementsService.findByWsp(getSessionUI().getWspSession());
				this.wsp = new Wsp();
				this.wsp = getSessionUI().getWspSession();
				this.wspSkillsRequirements.setWsp(this.wsp);
				this.wspSkillsRequirementsFuture.setWsp(this.wsp);
				List<WspSkillsRequirements> wspSkillsRequirements = wspSkillsRequirementsService.findByWsp(wsp);

				if (wspSkillsRequirements.size() > 0) {
					for (WspSkillsRequirements wspSkillsRequirements2 : wspSkillsRequirements) {
						if (wspSkillsRequirements2.getYesNo().getId() == HAJConstants.NO_ID) {
							wspSkillsRequirementsFuture.setYesNo(YesNoLookupService.instance().findByKey(HAJConstants.NO_ID));
							this.wspSkillsRequirements.setYesNo(YesNoLookupService.instance().findByKey(HAJConstants.NO_ID));
							disableForm = true;
						}
					}
					if (!disableForm) {
						wspSkillsRequirementsFuture.setYesNo(YesNoLookupService.instance().findByKey(HAJConstants.YES_ID));
						this.wspSkillsRequirements.setYesNo(YesNoLookupService.instance().findByKey(HAJConstants.YES_ID));
						disableYesNo = true;
					}
				} else {
					this.wspSkillsRequirements.setYesNo(null);
				}

			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void wspSkillsRequirementsInfo()
	{
		
		dataModel=new LazyDataModel<WspSkillsRequirements>() 
		{
			private static final long serialVersionUID = 1L;
			private List<WspSkillsRequirements> list=new ArrayList<>();
			
			@Override
			public List<WspSkillsRequirements> load(int first, int pageSize, String sortField, SortOrder sortOrder,Map<String, Object> filters) {
				
				try
				{
				
					list=wspSkillsRequirementsService.findByWsp(WspSkillsRequirements.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getWspSession().getId());
					dataModel.setRowCount((int) wspSkillsRequirementsService.allWspSkillsRequirementsCount( filters,getSessionUI().getWspSession()));
				}
				catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(WspSkillsRequirements object) {
				// TODO Auto-generated method stub
				return object.getId();
			}
			@Override
			public WspSkillsRequirements getRowData(String rowKey) {
				for (WspSkillsRequirements obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
					{
						return obj;
					}
				}
				return null;
			}
		};
	}

	private void prepNewSkillsRequirementFuture() throws Exception {
		wspSkillsRequirementsFuture = new WspSkillsRequirements();
		wspSkillsRequirementsFuture.setPastFutureEnum(PastFutureEnum.Future);
		disableForm = false;
		disableYesNo = false;
		if (wsp != null) {
			wspSkillsRequirementsFuture.setWsp(wsp);
			List<WspSkillsRequirements> wspSkillsRequirements = wspSkillsRequirementsService.findByWsp(wsp);
			if (wspSkillsRequirements.size() > 0) {
				for (WspSkillsRequirements wspSkillsRequirements2 : wspSkillsRequirements) {
					if (wspSkillsRequirements2.getYesNo().getId() == HAJConstants.NO_ID) {
						wspSkillsRequirementsFuture.setYesNo(YesNoLookupService.instance().findByKey(HAJConstants.NO_ID));
					}
				}
				if (!disableForm) {
					this.wspSkillsRequirements.setYesNo(YesNoLookupService.instance().findByKey(HAJConstants.YES_ID));
				}
				disableYesNo = true;
			} else {
				this.wspSkillsRequirements.setYesNo(null);
			}
		}
	}

	private void prepNewSkillsRequirementPast() throws Exception {
		wspSkillsRequirements = new WspSkillsRequirements();
		wspSkillsRequirements.setPastFutureEnum(PastFutureEnum.Past);
		disableForm = false;
		disableYesNo = false;
		if (wsp != null) {
			wspSkillsRequirements.setWsp(wsp);
			List<WspSkillsRequirements> wspSkillsRequirements = wspSkillsRequirementsService.findByWsp(wsp);
			if (wspSkillsRequirements.size() > 0) {
				for (WspSkillsRequirements wspSkillsRequirements2 : wspSkillsRequirements) {
					if (wspSkillsRequirements2.getYesNo().getId() == HAJConstants.NO_ID) {
						this.wspSkillsRequirements.setYesNo(YesNoLookupService.instance().findByKey(HAJConstants.NO_ID));
						disableForm = true;
					}
				}
				if (!disableForm) {
					this.wspSkillsRequirements.setYesNo(YesNoLookupService.instance().findByKey(HAJConstants.YES_ID));
				}
				disableYesNo = true;
			} else {
				this.wspSkillsRequirements.setYesNo(null);
			}
		}
	}

	/**
	 * Prepare new skills requirements.
	 */
	public void prepareNewSkillsRequirements() {
		this.wspSkillsRequirements = new WspSkillsRequirements();
		if (getSessionUI().getWspSession() != null) {
			this.wsp = new Wsp();
			this.wsp = getSessionUI().getWspSession();
			this.wspSkillsRequirements.setWsp(this.wsp);
		}
	}

	/**
	 * Creates the and append to list.
	 */
	public void createAndAppendToListPast() {
		try {
			if (wspSkillsRequirements.getTotalVacanciesFilled() != null && wspSkillsRequirements.getTotalVacancies() != null) {
				if (wspSkillsRequirements.getTotalVacanciesFilled() > wspSkillsRequirements.getTotalVacancies()) {
					throw new Exception("Total vacancies filled can not be greater than total number of vacancies for the position.");
				}
			}
			wspSkillsRequirementsService.create(clearValues(this.wspSkillsRequirements));
			this.wspSkillsRequirementsList = new ArrayList<>();
			this.wspSkillsRequirementsList = wspSkillsRequirementsService.findByWsp(getSessionUI().getWspSession());
			addInfoMessage("Update Success");
			prepNewSkillsRequirementPast();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Creates the and append to list.
	 */
	public void createAndAppendToListFuture() {
		try {
			wspSkillsRequirementsService.create(clearValues(this.wspSkillsRequirementsFuture));
			this.wspSkillsRequirementsList = new ArrayList<>();
			this.wspSkillsRequirementsList = wspSkillsRequirementsService.findByWsp(getSessionUI().getWspSession());
			addInfoMessage("Update Success");
			prepNewSkillsRequirementFuture();

			// disableForm

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private WspSkillsRequirements clearValues(WspSkillsRequirements wspSkillsRequirements) throws Exception {
		if (wspSkillsRequirements.getYesNo().getYesNoName().toLowerCase().equals("yes")) {
			if (wspSkillsRequirements.getNoHardToFillVacancies() != null) {
				wspSkillsRequirements.setNoHardToFillVacancies(null);
			}
		} else {
			if (wspSkillsRequirements.getOfoCodes() != null) {
				wspSkillsRequirements.setOfoCodes(null);
			}
			if (wspSkillsRequirements.getOccupationCategory() != null) {
				wspSkillsRequirements.setOccupationCategory(null);
			}
			if (wspSkillsRequirements.getJobTitle() != null) {
				wspSkillsRequirements.setJobTitle(null);
			}
			if (wspSkillsRequirements.getQualification() != null) {
				wspSkillsRequirements.setQualification(null);
			}
			if (wspSkillsRequirements.getTotalVacancies() != null) {
				wspSkillsRequirements.setTotalVacancies(null);
			}
			if (wspSkillsRequirements.getTotalVacanciesFilled() != null) {
				wspSkillsRequirements.setTotalVacanciesFilled(null);
			}
			if (wspSkillsRequirements.getVacancyReasons() != null) {
				wspSkillsRequirements.setVacancyReasons(null);
			}
		}
		return wspSkillsRequirements;
	}

	public void clearPastEntry() {
		try {
			prepNewSkillsRequirementPast();
			prepNewSkillsRequirementFuture();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void clearFutureEntry() {
		try {
			prepNewSkillsRequirementFuture();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void prepUpdateSkillsRequirement() {
		try {
			if (wspSkillsRequirements.getPastFutureEnum() == PastFutureEnum.Future) {
				wspSkillsRequirementsFuture = wspSkillsRequirementsService.findByKey(wspSkillsRequirements.getId());
				prepNewSkillsRequirementPast();
			} else {
				prepNewSkillsRequirementFuture();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete wsp skills requirements.
	 */
	public void deleteWspSkillsRequirements() {
		try {
			wspSkillsRequirementsService.delete(this.wspSkillsRequirements);
			addInfoMessage("Delete Success");
			this.wspSkillsRequirementsList = new ArrayList<>();
			this.wspSkillsRequirementsList = wspSkillsRequirementsService.findByWsp(getSessionUI().getWspSession());
			prepNewSkillsRequirementPast();
			prepNewSkillsRequirementFuture();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the wsp skills requirements.
	 *
	 * @return the wsp skills requirements
	 */
	public WspSkillsRequirements getWspSkillsRequirements() {
		return wspSkillsRequirements;
	}

	/**
	 * Sets the wsp skills requirements.
	 *
	 * @param wspSkillsRequirements
	 *            the new wsp skills requirements
	 */
	public void setWspSkillsRequirements(WspSkillsRequirements wspSkillsRequirements) {
		this.wspSkillsRequirements = wspSkillsRequirements;
	}

	/**
	 * Gets the wsp skills requirements list.
	 *
	 * @return the wsp skills requirements list
	 */
	public List<WspSkillsRequirements> getWspSkillsRequirementsList() {
		return wspSkillsRequirementsList;
	}

	/**
	 * Gets the wsp.
	 *
	 * @return the wsp
	 */
	public Wsp getWsp() {
		return wsp;
	}

	/**
	 * Sets the wsp.
	 *
	 * @param wsp
	 *            the new wsp
	 */
	public void setWsp(Wsp wsp) {
		this.wsp = wsp;
	}

	public WspSkillsRequirements getWspSkillsRequirementsFuture() {
		return wspSkillsRequirementsFuture;
	}

	public void setWspSkillsRequirementsFuture(WspSkillsRequirements wspSkillsRequirementsFuture) {
		this.wspSkillsRequirementsFuture = wspSkillsRequirementsFuture;
	}

	public boolean isDisableYesNo() {
		return disableYesNo;
	}

	public void setDisableYesNo(boolean disableYesNo) {
		this.disableYesNo = disableYesNo;
	}

	public boolean isDisableForm() {
		return disableForm;
	}

	public void setDisableForm(boolean disableForm) {
		this.disableForm = disableForm;
	}

	public LazyDataModel<WspSkillsRequirements> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<WspSkillsRequirements> dataModel) {
		this.dataModel = dataModel;
	}

}
