package  haj.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.MISReportBean;
import haj.com.entity.Blank;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DashBoardService;

// TODO: Auto-generated Javadoc
/**
 * The Class OperationalReportsUI.
 */
@ManagedBean(name = "operationalReportsUI")
@ViewScoped
public class OperationalReportsUI extends AbstractUI {

	private Long notStartedTask;
	private Long underwayTask;
	private Long completedTask;
	private Long empNotStartedTask;
	private Long empUnderwayTask;
	private Long empCompletedTask;
	private Long empAllTask;
	private Long extAllTask;
	private Long allTask;
	private Double avgDaysToCompleteEmployees;
	private Double avgDaysToActionEmployees;
	private Double avgDaysToCompleteExternal;
	private Double avgDaysToActionExternal;
	private List<MISReportBean> countTasksbyBP;

	private List<MISReportBean> findEmpPerProv;
	private List<MISReportBean> findEmpPerProvNonLevyPaying;
	private List<MISReportBean> findEmpPerCity;
	private List<MISReportBean> findSDFPerEmploy;
	private List<MISReportBean> findSDFPerArea;
	private List<MISReportBean> findSDFPerProvince;
	private List<MISReportBean> findSDFPerProfile;
	private List<MISReportBean> findSDFPerRelationship;
	private List<MISReportBean> sdfBiographical;
	private List<MISReportBean> countCompaniesPerProv;
	private  List<MISReportBean> countContactsByAvtiveInactive;

	private List<MISReportBean> countUsersByType;
	private String categories;
	private String series;
	private Long activeU;
	private Long inactiveU;
	private Long totalU;
	private String provData;
	private Long noContacts;
	private String provDataNonLevyPaying;
	private int colorBoxCount;
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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
		
		empNotStartedTask = DashBoardService.instance().countTasks(TaskStatusEnum.NotStarted, true);
		
		empUnderwayTask = DashBoardService.instance().countTasks(TaskStatusEnum.Underway, true);
		
		empCompletedTask = DashBoardService.instance().countTasks(TaskStatusEnum.Completed, true);
		
		notStartedTask = DashBoardService.instance().countTasks(TaskStatusEnum.NotStarted, false);
		
		underwayTask = DashBoardService.instance().countTasks(TaskStatusEnum.Underway, false);
		
		completedTask = DashBoardService.instance().countTasks(TaskStatusEnum.Completed, false);

		extAllTask = notStartedTask + underwayTask + completedTask;
		empAllTask = empNotStartedTask + empUnderwayTask + empCompletedTask;
		allTask = extAllTask + empAllTask;
		
		
		avgDaysToCompleteEmployees = DashBoardService.instance().avgTimeToCompleteTasksEmployees();
		
		avgDaysToActionEmployees = DashBoardService.instance().avgTimeToActionTasksEmployees();
		
		avgDaysToCompleteExternal = DashBoardService.instance().avgTimeToCompleteTasksExternal();
		
		avgDaysToActionExternal = DashBoardService.instance().avgTimeToActionTasksExternal();
		
		countTasksbyBP = DashBoardService.instance().countByBusinessProcess();
		noContacts  = DashBoardService.instance().countContacts();
		
		countContactsByAvtiveInactive = DashBoardService.instance().countContactsByAvtiveInactive();

		
		findEmpPerProv = DashBoardService.instance().findEmpPerProv();
		doTotal(findEmpPerProv);
		
		findEmpPerProvNonLevyPaying = DashBoardService.instance().findEmpPerProvNonLevyPaying();
		doTotal(findEmpPerProvNonLevyPaying);

		findEmpPerCity= DashBoardService.instance().findEmpPerCity();
		doTotal(findEmpPerCity);
		findSDFPerEmploy= DashBoardService.instance().findSDFPerEmploy();
		doTotal(findSDFPerEmploy);
		findSDFPerArea= DashBoardService.instance().findSDFPerArea();
		doTotal(findSDFPerArea);
		findSDFPerProvince= DashBoardService.instance().findSDFPerProvince();
		doTotal(findSDFPerProvince);
		findSDFPerProfile= DashBoardService.instance().findSDFPerProfile();
		doTotal(findSDFPerProfile);
		findSDFPerRelationship= DashBoardService.instance().findSDFPerRelationship();
		doTotal(findSDFPerRelationship);
		sdfBiographical=DashBoardService.instance().sdfBiographical();
		this.provData = DashBoardService.instance().copmanyPerProvData(true);
		this.provDataNonLevyPaying = DashBoardService.instance().copmanyPerProvData(false);
		countUsersByType = DashBoardService.instance().usersByType();

		countUsersByType.forEach(a-> {
			if (a.getActive()) activeU += a.getCount();
			else inactiveU += a.getCount();
			totalU+=a.getCount();
		});
		processGraphs();
	}

	private void doTotal(List<MISReportBean> list) {
		Long total = 0l;
		Long total2 = 0l;
		for (MISReportBean a : list) {
			total+=(a.getCount()==null?0l:a.getCount());
			total2+=(a.getCount2()==null?0l:a.getCount2());
		}
		list.add(new MISReportBean("Total", total,total2));

	}

	private void processGraphs() {
		Map<String,Map<Boolean,Long> > m = new HashMap<String,Map<Boolean,Long> >();

		for (MISReportBean misReportBean : countUsersByType) {
		     if (!m.containsKey(misReportBean.getDescription().trim())) {
		    	      m.put(misReportBean.getDescription().trim(), new HashMap<Boolean,Long>());
		    	      m.get(misReportBean.getDescription().trim()).put(Boolean.TRUE, 0l);
		    	      m.get(misReportBean.getDescription().trim()).put(Boolean.FALSE, 0l);
		     }
			     m.get(misReportBean.getDescription().trim()).put(misReportBean.getActive(), misReportBean.getCount());

		}

		/*

				['Bananas', 8],

		 */

		categories ="";
		series = "";

		for (Entry<String,Map<Boolean,Long>> mb : m.entrySet()) {
		/*	categories += "'"+mb.getKey()+"',";
			series += "{name: "+"'"+mb.getKey()+"',"+
			        "data: ["+mb.getValue().get(Boolean.TRUE) +","+mb.getValue().get(Boolean.FALSE) +"]},";
		*/
			if (mb.getValue().get(Boolean.TRUE) > 0l) {
			 series += "['"+mb.getKey()+"- Active',"+mb.getValue().get(Boolean.TRUE)+"],";
			}
			if (mb.getValue().get(Boolean.FALSE) > 0l) {
			  series += "['"+mb.getKey()+"- Inactive',"+mb.getValue().get(Boolean.FALSE)+"],";
			}
		}
/*		if (categories.lastIndexOf(',')>-1) {
			categories = categories.substring(0,categories.lastIndexOf(','));
		}
*/
		if (series.lastIndexOf(',')>-1) {
			series = series.substring(0,series.lastIndexOf(','));
		}
	}
	
	public int colorBoxValue(){
		colorBoxCount ++;
		if(colorBoxCount>7)
		{
			colorBoxCount=1;
		}
		return colorBoxCount;
	}

	public Long getNotStartedTask() {
		return notStartedTask;
	}

	public void setNotStartedTask(Long notStartedTask) {
		this.notStartedTask = notStartedTask;
	}

	public Long getUnderwayTask() {
		return underwayTask;
	}

	public void setUnderwayTask(Long underwayTask) {
		this.underwayTask = underwayTask;
	}

	public Long getCompletedTask() {
		return completedTask;
	}

	public void setCompletedTask(Long completedTask) {
		this.completedTask = completedTask;
	}
	
	public List<MISReportBean> getCountTasksbyBP() {
		return countTasksbyBP;
	}

	public void setCountTasksbyBP(List<MISReportBean> countTasksbyBP) {
		this.countTasksbyBP = countTasksbyBP;
	}

	public List<MISReportBean> getFindEmpPerProv() {
		return findEmpPerProv;
	}

	public void setFindEmpPerProv(List<MISReportBean> findEmpPerProv) {
		this.findEmpPerProv = findEmpPerProv;
	}

	public List<MISReportBean> getFindEmpPerCity() {
		return findEmpPerCity;
	}

	public void setFindEmpPerCity(List<MISReportBean> findEmpPerCity) {
		this.findEmpPerCity = findEmpPerCity;
	}

	public List<MISReportBean> getFindSDFPerEmploy() {
		return findSDFPerEmploy;
	}

	public void setFindSDFPerEmploy(List<MISReportBean> findSDFPerEmploy) {
		this.findSDFPerEmploy = findSDFPerEmploy;
	}

	public List<MISReportBean> getFindSDFPerArea() {
		return findSDFPerArea;
	}

	public void setFindSDFPerArea(List<MISReportBean> findSDFPerArea) {
		this.findSDFPerArea = findSDFPerArea;
	}

	public List<MISReportBean> getFindSDFPerProvince() {
		return findSDFPerProvince;
	}

	public void setFindSDFPerProvince(List<MISReportBean> findSDFPerProvince) {
		this.findSDFPerProvince = findSDFPerProvince;
	}

	public List<MISReportBean> getFindSDFPerProfile() {
		return findSDFPerProfile;
	}

	public void setFindSDFPerProfile(List<MISReportBean> findSDFPerProfile) {
		this.findSDFPerProfile = findSDFPerProfile;
	}

	public List<MISReportBean> getFindSDFPerRelationship() {
		return findSDFPerRelationship;
	}

	public void setFindSDFPerRelationship(List<MISReportBean> findSDFPerRelationship) {
		this.findSDFPerRelationship = findSDFPerRelationship;
	}

	public List<MISReportBean> getSdfBiographical() {
		return sdfBiographical;
	}

	public void setSdfBiographical(List<MISReportBean> sdfBiographical) {
		this.sdfBiographical = sdfBiographical;
	}
	public List<MISReportBean> getCountUsersByType() {
		return countUsersByType;
	}

	public void setCountUsersByType(List<MISReportBean> countUsersByType) {
		this.countUsersByType = countUsersByType;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public Long getActiveU() {
		return activeU;
	}

	public void setActiveU(Long activeU) {
		this.activeU = activeU;
	}

	public Long getInactiveU() {
		return inactiveU;
	}

	public void setInactiveU(Long inactiveU) {
		this.inactiveU = inactiveU;
	}

	public Long getTotalU() {
		return totalU;
	}

	public void setTotalU(Long totalU) {
		this.totalU = totalU;

	}

	public List<MISReportBean> getCountCompaniesPerProv() {
		return countCompaniesPerProv;
	}

	public void setCountCompaniesPerProv(List<MISReportBean> countCompaniesPerProv) {
		this.countCompaniesPerProv = countCompaniesPerProv;
	}

	public String getProvData() {
		return provData;
	}

	public void setProvData(String provData) {
		this.provData = provData;
	}

	public Long getNoContacts() {
		return noContacts;
	}

	public void setNoContacts(Long noContacts) {
		this.noContacts = noContacts;
	}

	public Long getEmpCompletedTask() {
		return empCompletedTask;
	}

	public void setEmpCompletedTask(Long empCompletedTask) {
		this.empCompletedTask = empCompletedTask;
	}

	public Long getEmpNotStartedTask() {
		return empNotStartedTask;
	}

	public void setEmpNotStartedTask(Long empNotStartedTask) {
		this.empNotStartedTask = empNotStartedTask;
	}

	public Long getEmpUnderwayTask() {
		return empUnderwayTask;
	}

	public void setEmpUnderwayTask(Long empUnderwayTask) {
		this.empUnderwayTask = empUnderwayTask;
	}

	public List<MISReportBean> getCountContactsByAvtiveInactive() {
		return countContactsByAvtiveInactive;
	}

	public void setCountContactsByAvtiveInactive(List<MISReportBean> countContactsByAvtiveInactive) {
		this.countContactsByAvtiveInactive = countContactsByAvtiveInactive;
	}

	public List<MISReportBean> getFindEmpPerProvNonLevyPaying() {
		return findEmpPerProvNonLevyPaying;
	}

	public void setFindEmpPerProvNonLevyPaying(List<MISReportBean> findEmpPerProvNonLevyPaying) {
		this.findEmpPerProvNonLevyPaying = findEmpPerProvNonLevyPaying;
	}

	public String getProvDataNonLevyPaying() {
		return provDataNonLevyPaying;
	}

	public void setProvDataNonLevyPaying(String provDataNonLevyPaying) {
		this.provDataNonLevyPaying = provDataNonLevyPaying;
	}

	public Double getAvgDaysToCompleteEmployees() {
		return avgDaysToCompleteEmployees;
	}

	public void setAvgDaysToCompleteEmployees(Double avgDaysToCompleteEmployees) {
		this.avgDaysToCompleteEmployees = avgDaysToCompleteEmployees;
	}

	public Double getAvgDaysToActionEmployees() {
		return avgDaysToActionEmployees;
	}

	public void setAvgDaysToActionEmployees(Double avgDaysToActionEmployees) {
		this.avgDaysToActionEmployees = avgDaysToActionEmployees;
	}

	public Double getAvgDaysToCompleteExternal() {
		return avgDaysToCompleteExternal;
	}

	public void setAvgDaysToCompleteExternal(Double avgDaysToCompleteExternal) {
		this.avgDaysToCompleteExternal = avgDaysToCompleteExternal;
	}

	public Double getAvgDaysToActionExternal() {
		return avgDaysToActionExternal;
	}

	public void setAvgDaysToActionExternal(Double avgDaysToActionExternal) {
		this.avgDaysToActionExternal = avgDaysToActionExternal;
	}

	public Long getEmpAllTask() {
		return empAllTask;
	}

	public void setEmpAllTask(Long empAllTask) {
		this.empAllTask = empAllTask;
	}

	public Long getExtAllTask() {
		return extAllTask;
	}

	public void setExtAllTask(Long extAllTask) {
		this.extAllTask = extAllTask;
	}

	public Long getAllTask() {
		return allTask;
	}

	public void setAllTask(Long allTask) {
		this.allTask = allTask;
	}

	public int getColorBoxCount() {
		return colorBoxCount;
	}

	public void setColorBoxCount(int colorBoxCount) {
		this.colorBoxCount = colorBoxCount;
	}




}
