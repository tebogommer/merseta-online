package haj.com.ui;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.TabChangeEvent;
import haj.com.bean.CompanyLearnersBean;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.InterventionType;
import haj.com.entity.lookup.Region;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;

@ManagedBean(name = "companyLearnersReportingPageUI")
@ViewScoped
public class CompanyLearnersReportingPageUI extends AbstractUI {

	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private List<CompanyLearnersBean> companyLearnersList;
	private List<CompanyLearnersBean> companyLearnersByInterventionList;
	private List<CompanyLearnersBean> companyLearnersByStatusList;
	private List<CompanyLearnersBean> companyLearnersByChamberList;
	private List<CompanyLearnersBean> companyLearnersByRegionList;
	
	private int index; // used for changing tabs
	
	private boolean filterByLearnerStatus = false;
	private boolean filterByChamber = false;
	private boolean filterByRegion = false;
	private boolean filterByIntervention = false;
	private boolean displayReport = false;
	
	private InterventionType intevention;
	private LearnerStatusEnum learnerStatusEnum;
	private Chamber chamber;
	private Region region;
	
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {
		
	}

	
	public void onTabChange(TabChangeEvent event) {
		try {
			// should be set to false when switching between tabs
			displayReport = false;
			clearEntries();
			clearArrayLists();
			// switch (index) {
			switch (event.getTab().getId()) {
			case "wspByFinYearTab":
				displayReport = false;
				clearEntries();
				clearArrayLists();
				// super.runClientSideUpdate(":mainForm:tabView:wspByFinYearTab");
				break;
			case "tasksByWsp":
				displayReport = false;
				clearEntries();
				clearArrayLists();
				//prepTasksReport();
				// super.runClientSideUpdate(":mainForm:tabView:tasksByWsp");
				break;
			case "statusCountWsp":
				displayReport = false;
				clearEntries();
				clearArrayLists();
				//prepStatusReport();
				// super.runClientSideUpdate(":mainForm:tabView:statusCountWsp");
				break;
			case "targetReportDG":
				displayReport = false;
				clearEntries();
				clearArrayLists();
				//prepGrantsReportByTypeStatusCompanySize();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void generateReportByStatus() {
		try {
			if(learnerStatusEnum == null) {
				displayReport=true;
				companyLearnersByStatusList=companyLearnersService.generateCompanyLearnerReport();
			}else {
				displayReport=true;
				companyLearnersByStatusList=companyLearnersService.generateCompanyLearnerReport(learnerStatusEnum);
			}
			if(companyLearnersByStatusList.size()==0) {
				addInfoMessage("No Learners Found");
			}
			clearEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void generateInterventionReport() {
		try {
			if(intevention == null) {
				displayReport=true;
				companyLearnersByInterventionList=companyLearnersService.generateCompanyLearnerReport();
			}else {
				displayReport=true;
				companyLearnersByInterventionList=companyLearnersService.generateCompanyLearnerReport(intevention);
			}
			if(companyLearnersByInterventionList.size()==0) {
				addInfoMessage("No Learners Found");
			}
			clearEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void generateChamberReport() {
		try {
			if(chamber == null && intevention == null && learnerStatusEnum == null) {
				displayReport=true;
				companyLearnersByChamberList=companyLearnersService.generateCompanyLearnerReport();
			}else {
				if(chamber != null && intevention != null && learnerStatusEnum!= null) {
					displayReport=true;
					companyLearnersByChamberList=companyLearnersService.generateCompanyLearnerReport(chamber, intevention, learnerStatusEnum);
				}else if(chamber != null && intevention != null) {
					displayReport=true;
					companyLearnersByChamberList=companyLearnersService.generateCompanyLearnerReport(chamber, intevention);
				}else if(chamber != null && learnerStatusEnum!= null) {
					displayReport=true;
					companyLearnersByChamberList=companyLearnersService.generateCompanyLearnerReport(chamber, learnerStatusEnum);
				}else if(chamber != null){
					displayReport=true;
					companyLearnersByChamberList=companyLearnersService.generateCompanyLearnerReport(chamber);
				}else {
					companyLearnersByChamberList=companyLearnersService.generateCompanyLearnerReport();
				}
			}
			if(companyLearnersByChamberList.size()==0) {
				addInfoMessage("No Learners Found");
			}
			clearEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void generateRegionReport() {
		try {
			if(region == null && intevention == null && learnerStatusEnum == null) {
				displayReport=true;
				companyLearnersByRegionList=companyLearnersService.generateCompanyLearnerReport();
			}else {
				if(region != null && intevention != null && learnerStatusEnum!= null) {
					displayReport=true;
					companyLearnersByRegionList=companyLearnersService.generateCompanyLearnerReport(region, intevention, learnerStatusEnum);
				}else if(region != null && intevention != null) {
					displayReport=true;
					companyLearnersByRegionList=companyLearnersService.generateCompanyLearnerReport(region, intevention);
				}else if(region != null && learnerStatusEnum!= null) {
					displayReport=true;
					companyLearnersByRegionList=companyLearnersService.generateCompanyLearnerReport(region, learnerStatusEnum);
				}else if(region != null){
					displayReport=true;
					companyLearnersByRegionList=companyLearnersService.generateCompanyLearnerReport(region);
				}else {
					companyLearnersByRegionList=companyLearnersService.generateCompanyLearnerReport();
				}
			}
			if(companyLearnersByRegionList.size()==0) {
				addInfoMessage("No Learners Found");
			}
			clearEntries();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	private void clearEntries() {
		learnerStatusEnum = null;
		intevention = null;
		region = null;
		chamber = null;
	}
	
	private void clearArrayLists() throws Exception {
		companyLearnersByInterventionList = null;
		companyLearnersByStatusList = null;
		companyLearnersByRegionList = null;
		companyLearnersByChamberList = null;
	}

	public List<CompanyLearnersBean> getCompanyLearnersList() {
		return companyLearnersList;
	}

	public void setCompanyLearnersList(List<CompanyLearnersBean> companyLearnersList) {
		this.companyLearnersList = companyLearnersList;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isFilterByChamber() {
		return filterByChamber;
	}

	public boolean isFilterByRegion() {
		return filterByRegion;
	}

	public boolean isFilterByLearnerStatus() {
		return filterByLearnerStatus;
	}

	public void setFilterByLearnerStatus(boolean filterByLearnerStatus) {
		this.filterByLearnerStatus = filterByLearnerStatus;
	}

	public boolean isFilterByIntervention() {
		return filterByIntervention;
	}

	public boolean isDisplayReport() {
		return displayReport;
	}

	public void setFilterByChamber(boolean filterByChamber) {
		this.filterByChamber = filterByChamber;
	}

	public void setFilterByRegion(boolean filterByRegion) {
		this.filterByRegion = filterByRegion;
	}

	public void setFilterByIntervention(boolean filterByIntervention) {
		this.filterByIntervention = filterByIntervention;
	}

	public void setDisplayReport(boolean displayReport) {
		this.displayReport = displayReport;
	}

	public List<CompanyLearnersBean> getCompanyLearnersByInterventionList() {
		return companyLearnersByInterventionList;
	}

	public List<CompanyLearnersBean> getCompanyLearnersByStatusList() {
		return companyLearnersByStatusList;
	}

	public List<CompanyLearnersBean> getCompanyLearnersByChamberList() {
		return companyLearnersByChamberList;
	}

	public List<CompanyLearnersBean> getCompanyLearnersByRegionList() {
		return companyLearnersByRegionList;
	}

	public void setCompanyLearnersByInterventionList(List<CompanyLearnersBean> companyLearnersByInterventionList) {
		this.companyLearnersByInterventionList = companyLearnersByInterventionList;
	}

	public void setCompanyLearnersByStatusList(List<CompanyLearnersBean> companyLearnersByStatusList) {
		this.companyLearnersByStatusList = companyLearnersByStatusList;
	}

	public void setCompanyLearnersByChamberList(List<CompanyLearnersBean> companyLearnersByChamberList) {
		this.companyLearnersByChamberList = companyLearnersByChamberList;
	}

	public void setCompanyLearnersByRegionList(List<CompanyLearnersBean> companyLearnersByRegionList) {
		this.companyLearnersByRegionList = companyLearnersByRegionList;
	}

	public InterventionType getIntevention() {
		return intevention;
	}

	public LearnerStatusEnum getLearnerStatusEnum() {
		return learnerStatusEnum;
	}

	public Chamber getChamber() {
		return chamber;
	}

	public Region getRegion() {
		return region;
	}

	public void setIntevention(InterventionType intevention) {
		this.intevention = intevention;
	}

	public void setLearnerStatusEnum(LearnerStatusEnum learnerStatusEnum) {
		this.learnerStatusEnum = learnerStatusEnum;
	}

	public void setChamber(Chamber chamber) {
		this.chamber = chamber;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
}
