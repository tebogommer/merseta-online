package haj.com.wsp.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.constants.HAJConstants;
import haj.com.entity.Wsp;
import haj.com.entity.WspSkillsGap;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.WspService;
import haj.com.service.WspSkillsGapService;

// TODO: Auto-generated Javadoc
/**
 * The Class WspSkillsGapUI.
 */
@ManagedBean(name = "wspSkillsGapUI")
@ViewScoped
public class WspSkillsGapUI extends AbstractUI {

	/** The wsp. */
	private Wsp wsp;

	/** The wsp skills gap */
	private WspSkillsGap wspSkillsGap;

	/** The wsp skills gap service. */
	private WspSkillsGapService wspSkillsGapService = new WspSkillsGapService();

	/** The wsp service */
	private WspService wspService = new WspService();

	/** The wsp skills gap section 3 list. */
	private List<WspSkillsGap> wspSkillsGapSectionThreeList;

	/** The wsp skills gap section 4 list. */
	private List<WspSkillsGap> wspSkillsGapSectionFourList;

	/** the disable indicators */

	/**
	 * If user allowed to edit or just view
	 */
	private Boolean viewOnly;

	/**
	 * Section 3
	 */
	private Boolean disableManagersSectionThree;
	private Boolean disableProfessionalsSectionThree;
	private Boolean disableTechniciansAssociateProfessionalsSectionThree;
	private Boolean disableClericalSupportWorkersSectionThree;
	private Boolean disableServiceSalesWorkersSectionThree;
	private Boolean disableSkilledTradesWorkersSectionThree;
	private Boolean disablePlantMachineOperatorsAssemblersSectionThree;
	private Boolean disableElementaryWorkersSectionThree;

	/**
	 * Section 4
	 */
	private Boolean disableManagersSectionFour;
	private Boolean disableProfessionalsSectionFour;
	private Boolean disableTechniciansAssociateProfessionalsSectionFour;
	private Boolean disableClericalSupportWorkersSectionFour;
	private Boolean disableServiceSalesWorkersSectionFour;
	private Boolean disableSkilledTradesWorkersSectionFour;
	private Boolean disablePlantMachineOperatorsAssemblersSectionFour;
	private Boolean disableElementaryWorkersSectionFour;

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
		prepareWsp();
		defaultDisableValuesSectionThree();
		defaultDisableValuesSectionFour();
		populateWspSkillsGapSectionThreeList();
		populateWspSkillsGapSectionFourList();
	}

	/**
	 * Prepare wsp skills gap.
	 */
	public void prepareWsp() throws Exception {
		if (getSessionUI().getWspSession() != null) {
			this.wsp = new Wsp();
			this.wsp = getSessionUI().getWspSession();
			viewOnly = wsp.getWspStatusEnum() != WspStatusEnum.Draft && !getSessionUI().isExternalParty();

		}
	}

	/**
	 * Defaults all the disable values for section three list
	 * 
	 * @throws Exception
	 */
	private void defaultDisableValuesSectionThree() throws Exception {
		disableManagersSectionThree = false;
		disableProfessionalsSectionThree = false;
		disableTechniciansAssociateProfessionalsSectionThree = false;
		disableClericalSupportWorkersSectionThree = false;
		disableServiceSalesWorkersSectionThree = false;
		disableSkilledTradesWorkersSectionThree = false;
		disablePlantMachineOperatorsAssemblersSectionThree = false;
		disableElementaryWorkersSectionThree = false;
	}

	/**
	 * Defaults all the disable values for section four list
	 * 
	 * @throws Exception
	 */
	private void defaultDisableValuesSectionFour() throws Exception {
		disableManagersSectionFour = false;
		disableProfessionalsSectionFour = false;
		disableTechniciansAssociateProfessionalsSectionFour = false;
		disableClericalSupportWorkersSectionFour = false;
		disableServiceSalesWorkersSectionFour = false;
		disableSkilledTradesWorkersSectionFour = false;
		disablePlantMachineOperatorsAssemblersSectionFour = false;
		disableElementaryWorkersSectionFour = false;
	}

	/**
	 * Populate WSP Skills Gap Section Three List
	 * 
	 * @throws Exception
	 */
	private void populateWspSkillsGapSectionThreeList() throws Exception {
		this.wspSkillsGapSectionThreeList = wspSkillsGapService.findByWspAndSection(getSessionUI().getWspSession(), HAJConstants.WSP_SKILLS_GAP_SECTION_3);

	}

	/**
	 * Populate WSP Skills Gap Section Four List
	 * 
	 * @throws Exception
	 */
	private void populateWspSkillsGapSectionFourList() throws Exception {
		this.wspSkillsGapSectionFourList = wspSkillsGapService.findByWspAndSection(getSessionUI().getWspSession(), HAJConstants.WSP_SKILLS_GAP_SECTION_4);

	}

	/**
	 * Check to disable if used maximum amount of selections for Section Three
	 */
	public void checkForMaxiumSelectionSectionThree() {
		try {
			defaultDisableValuesSectionThree();

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Check to disable if used maximum amount of selections for Section Four
	 */
	public void checkForMaxiumSelectionSectionFour() {
		try {
			defaultDisableValuesSectionFour();

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Does the check if a user can edit
	 */
	private int calulateCanEdit(List<WspSkillsGap> list) throws Exception {
		int count = 0;
		int validCount = 0;
		int selectedCount = 0;

		int counterManager = 0;
		int counterProfessionals = 0;
		int counterTechniciansAssociateProfessionals = 0;
		int counterClericalSupportWorkers = 0;
		int counterServiceSalesWorkers = 0;
		int counterSkilledTradesWorkers = 0;
		int counterPlantMachineOperatorsAssemblers = 0;
		int counterElementaryWorkers = 0;

		for (WspSkillsGap wspSkillsGap : list) {
			if (wspSkillsGap.getManagerSelection()) {
				counterManager++;
				selectedCount++;
				if (counterManager == 6) {
					count += 1;
					validCount++;
				}
			}
			if (wspSkillsGap.getProfessionalsSelection()) {
				counterProfessionals++;
				selectedCount++;
				if (counterProfessionals == 6) {
					count += 1;
					validCount++;
				}
			}
			if (wspSkillsGap.getTechniciansAssociateProfessionalsSelection()) {
				counterTechniciansAssociateProfessionals++;
				selectedCount++;
				if (counterTechniciansAssociateProfessionals == 6) {
					count += 1;
					validCount++;
				}
			}
			if (wspSkillsGap.getClericalSupportWorkersSelection()) {
				counterClericalSupportWorkers++;
				selectedCount++;
				if (counterClericalSupportWorkers == 6) {
					count += 1;
					validCount++;
				}
			}
			if (wspSkillsGap.getServiceSalesWorkersSelection()) {
				counterServiceSalesWorkers++;
				selectedCount++;
				if (counterServiceSalesWorkers == 6) {
					count += 1;
					validCount++;
				}
			}
			if (wspSkillsGap.getSkilledTradesWorkersSelection()) {
				counterSkilledTradesWorkers++;
				selectedCount++;
				if (counterSkilledTradesWorkers == 6) {
					count += 1;
					validCount++;
				}
			}
			if (wspSkillsGap.getPlantMachineOperatorsAssemblersSelection()) {
				counterPlantMachineOperatorsAssemblers++;
				selectedCount++;
				if (counterPlantMachineOperatorsAssemblers == 6) {
					count += 1;
					validCount++;
				}
			}
			if (wspSkillsGap.getElementaryWorkersSelection()) {
				counterElementaryWorkers++;
				selectedCount++;
				if (counterElementaryWorkers == 6) {
					count += 1;
					validCount++;
				}
			}
		}
		if (validCount == 0 || selectedCount == 0) return 0;
		return (selectedCount / validCount == 6) ? count : 0;
	}

	/**
	 * save sections results
	 */
	public void saveSectionsResults() {
		try {
			wspService.update(wsp);

			if (wsp.getTrackSkillsGap().getYesNoName().toLowerCase().equals("yes")) {
				List<WspSkillsGap> a = new ArrayList<>();
				a.addAll(wspSkillsGapSectionFourList);
				a.addAll(wspSkillsGapSectionThreeList);
				if (calulateCanEdit(a) > 0) {
					wspSkillsGapService.updateList(wspSkillsGapSectionThreeList);
					wspSkillsGapService.updateList(wspSkillsGapSectionFourList);
				} else {
					throw new Exception("Please ensure you select 3 options for each occupational category in your organisation for each section");
				}
			}
			addInfoMessage("Submit Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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

	public WspSkillsGap getWspSkillsGap() {
		return wspSkillsGap;
	}

	public void setWspSkillsGap(WspSkillsGap wspSkillsGap) {
		this.wspSkillsGap = wspSkillsGap;
	}

	public List<WspSkillsGap> getWspSkillsGapSectionThreeList() {
		return wspSkillsGapSectionThreeList;
	}

	public void setWspSkillsGapSectionThreeList(List<WspSkillsGap> wspSkillsGapSectionThreeList) {
		this.wspSkillsGapSectionThreeList = wspSkillsGapSectionThreeList;
	}

	public List<WspSkillsGap> getWspSkillsGapSectionFourList() {
		return wspSkillsGapSectionFourList;
	}

	public void setWspSkillsGapSectionFourList(List<WspSkillsGap> wspSkillsGapSectionFourList) {
		this.wspSkillsGapSectionFourList = wspSkillsGapSectionFourList;
	}

	public Boolean getDisableManagersSectionThree() {
		return disableManagersSectionThree;
	}

	public void setDisableManagersSectionThree(Boolean disableManagersSectionThree) {
		this.disableManagersSectionThree = disableManagersSectionThree;
	}

	public Boolean getDisableProfessionalsSectionThree() {
		return disableProfessionalsSectionThree;
	}

	public void setDisableProfessionalsSectionThree(Boolean disableProfessionalsSectionThree) {
		this.disableProfessionalsSectionThree = disableProfessionalsSectionThree;
	}

	public Boolean getDisableTechniciansAssociateProfessionalsSectionThree() {
		return disableTechniciansAssociateProfessionalsSectionThree;
	}

	public void setDisableTechniciansAssociateProfessionalsSectionThree(Boolean disableTechniciansAssociateProfessionalsSectionThree) {
		this.disableTechniciansAssociateProfessionalsSectionThree = disableTechniciansAssociateProfessionalsSectionThree;
	}

	public Boolean getDisableClericalSupportWorkersSectionThree() {
		return disableClericalSupportWorkersSectionThree;
	}

	public void setDisableClericalSupportWorkersSectionThree(Boolean disableClericalSupportWorkersSectionThree) {
		this.disableClericalSupportWorkersSectionThree = disableClericalSupportWorkersSectionThree;
	}

	public Boolean getDisableServiceSalesWorkersSectionThree() {
		return disableServiceSalesWorkersSectionThree;
	}

	public void setDisableServiceSalesWorkersSectionThree(Boolean disableServiceSalesWorkersSectionThree) {
		this.disableServiceSalesWorkersSectionThree = disableServiceSalesWorkersSectionThree;
	}

	public Boolean getDisableSkilledTradesWorkersSectionThree() {
		return disableSkilledTradesWorkersSectionThree;
	}

	public void setDisableSkilledTradesWorkersSectionThree(Boolean disableSkilledTradesWorkersSectionThree) {
		this.disableSkilledTradesWorkersSectionThree = disableSkilledTradesWorkersSectionThree;
	}

	public Boolean getDisablePlantMachineOperatorsAssemblersSectionThree() {
		return disablePlantMachineOperatorsAssemblersSectionThree;
	}

	public void setDisablePlantMachineOperatorsAssemblersSectionThree(Boolean disablePlantMachineOperatorsAssemblersSectionThree) {
		this.disablePlantMachineOperatorsAssemblersSectionThree = disablePlantMachineOperatorsAssemblersSectionThree;
	}

	public Boolean getDisableElementaryWorkersSectionThree() {
		return disableElementaryWorkersSectionThree;
	}

	public void setDisableElementaryWorkersSectionThree(Boolean disableElementaryWorkersSectionThree) {
		this.disableElementaryWorkersSectionThree = disableElementaryWorkersSectionThree;
	}

	public Boolean getDisableManagersSectionFour() {
		return disableManagersSectionFour;
	}

	public void setDisableManagersSectionFour(Boolean disableManagersSectionFour) {
		this.disableManagersSectionFour = disableManagersSectionFour;
	}

	public Boolean getDisableProfessionalsSectionFour() {
		return disableProfessionalsSectionFour;
	}

	public void setDisableProfessionalsSectionFour(Boolean disableProfessionalsSectionFour) {
		this.disableProfessionalsSectionFour = disableProfessionalsSectionFour;
	}

	public Boolean getDisableTechniciansAssociateProfessionalsSectionFour() {
		return disableTechniciansAssociateProfessionalsSectionFour;
	}

	public void setDisableTechniciansAssociateProfessionalsSectionFour(Boolean disableTechniciansAssociateProfessionalsSectionFour) {
		this.disableTechniciansAssociateProfessionalsSectionFour = disableTechniciansAssociateProfessionalsSectionFour;
	}

	public Boolean getDisableClericalSupportWorkersSectionFour() {
		return disableClericalSupportWorkersSectionFour;
	}

	public void setDisableClericalSupportWorkersSectionFour(Boolean disableClericalSupportWorkersSectionFour) {
		this.disableClericalSupportWorkersSectionFour = disableClericalSupportWorkersSectionFour;
	}

	public Boolean getDisableServiceSalesWorkersSectionFour() {
		return disableServiceSalesWorkersSectionFour;
	}

	public void setDisableServiceSalesWorkersSectionFour(Boolean disableServiceSalesWorkersSectionFour) {
		this.disableServiceSalesWorkersSectionFour = disableServiceSalesWorkersSectionFour;
	}

	public Boolean getDisableSkilledTradesWorkersSectionFour() {
		return disableSkilledTradesWorkersSectionFour;
	}

	public void setDisableSkilledTradesWorkersSectionFour(Boolean disableSkilledTradesWorkersSectionFour) {
		this.disableSkilledTradesWorkersSectionFour = disableSkilledTradesWorkersSectionFour;
	}

	public Boolean getDisablePlantMachineOperatorsAssemblersSectionFour() {
		return disablePlantMachineOperatorsAssemblersSectionFour;
	}

	public void setDisablePlantMachineOperatorsAssemblersSectionFour(Boolean disablePlantMachineOperatorsAssemblersSectionFour) {
		this.disablePlantMachineOperatorsAssemblersSectionFour = disablePlantMachineOperatorsAssemblersSectionFour;
	}

	public Boolean getDisableElementaryWorkersSectionFour() {
		return disableElementaryWorkersSectionFour;
	}

	public void setDisableElementaryWorkersSectionFour(Boolean disableElementaryWorkersSectionFour) {
		this.disableElementaryWorkersSectionFour = disableElementaryWorkersSectionFour;
	}

	public Boolean getViewOnly() {
		return viewOnly;
	}

	public void setViewOnly(Boolean viewOnly) {
		this.viewOnly = viewOnly;
	}

}
