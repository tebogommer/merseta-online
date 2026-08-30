package haj.com.report.bean;

import haj.com.framework.IDataEntity;

public class MainSkillsGapReport implements IDataEntity {

	private String rowDescription;

	private Long managerSelection;

	private Long professionalsSelection;

	private Long techniciansAssociateProfessionalsSelection;

	private Long clericalSupportWorkersSelection;

	private Long serviceSalesWorkersSelection;

	private Long skilledTradesWorkersSelection;

	private Long plantMachineOperatorsAssemblersSelection;

	private Long elementaryWorkersSelection;

	public MainSkillsGapReport(String rowDescription, Long managerSelection, Long professionalsSelection, Long techniciansAssociateProfessionalsSelection, Long clericalSupportWorkersSelection, Long serviceSalesWorkersSelection, Long skilledTradesWorkersSelection,
			Long plantMachineOperatorsAssemblersSelection, Long elementaryWorkersSelection) {
		super();
		this.rowDescription = rowDescription;
		this.managerSelection = managerSelection;
		this.professionalsSelection = professionalsSelection;
		this.techniciansAssociateProfessionalsSelection = techniciansAssociateProfessionalsSelection;
		this.clericalSupportWorkersSelection = clericalSupportWorkersSelection;
		this.serviceSalesWorkersSelection = serviceSalesWorkersSelection;
		this.skilledTradesWorkersSelection = skilledTradesWorkersSelection;
		this.plantMachineOperatorsAssemblersSelection = plantMachineOperatorsAssemblersSelection;
		this.elementaryWorkersSelection = elementaryWorkersSelection;
	}

	public String getRowDescription() {
		return rowDescription;
	}

	public void setRowDescription(String rowDescription) {
		this.rowDescription = rowDescription;
	}

	public Long getManagerSelection() {
		return managerSelection;
	}

	public void setManagerSelection(Long managerSelection) {
		this.managerSelection = managerSelection;
	}

	public Long getProfessionalsSelection() {
		return professionalsSelection;
	}

	public void setProfessionalsSelection(Long professionalsSelection) {
		this.professionalsSelection = professionalsSelection;
	}

	public Long getTechniciansAssociateProfessionalsSelection() {
		return techniciansAssociateProfessionalsSelection;
	}

	public void setTechniciansAssociateProfessionalsSelection(Long techniciansAssociateProfessionalsSelection) {
		this.techniciansAssociateProfessionalsSelection = techniciansAssociateProfessionalsSelection;
	}

	public Long getClericalSupportWorkersSelection() {
		return clericalSupportWorkersSelection;
	}

	public void setClericalSupportWorkersSelection(Long clericalSupportWorkersSelection) {
		this.clericalSupportWorkersSelection = clericalSupportWorkersSelection;
	}

	public Long getServiceSalesWorkersSelection() {
		return serviceSalesWorkersSelection;
	}

	public void setServiceSalesWorkersSelection(Long serviceSalesWorkersSelection) {
		this.serviceSalesWorkersSelection = serviceSalesWorkersSelection;
	}

	public Long getSkilledTradesWorkersSelection() {
		return skilledTradesWorkersSelection;
	}

	public void setSkilledTradesWorkersSelection(Long skilledTradesWorkersSelection) {
		this.skilledTradesWorkersSelection = skilledTradesWorkersSelection;
	}

	public Long getPlantMachineOperatorsAssemblersSelection() {
		return plantMachineOperatorsAssemblersSelection;
	}

	public void setPlantMachineOperatorsAssemblersSelection(Long plantMachineOperatorsAssemblersSelection) {
		this.plantMachineOperatorsAssemblersSelection = plantMachineOperatorsAssemblersSelection;
	}

	public Long getElementaryWorkersSelection() {
		return elementaryWorkersSelection;
	}

	public void setElementaryWorkersSelection(Long elementaryWorkersSelection) {
		this.elementaryWorkersSelection = elementaryWorkersSelection;
	}
}
