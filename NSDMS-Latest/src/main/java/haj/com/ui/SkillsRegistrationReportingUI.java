package haj.com.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.SkillsRegistration;
import haj.com.entity.SkillsRegistrationQualificationUnitStandards;
import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SkillsRegistrationQualificationUnitStandardsService;
import haj.com.service.SkillsRegistrationService;

@ManagedBean(name = "skillsRegistrationReportingUI")
@ViewScoped
public class SkillsRegistrationReportingUI extends AbstractUI {

	private SkillsRegistrationService service = new SkillsRegistrationService();
	private LazyDataModel<SkillsRegistration> dataModel;
	private LazyDataModel<SkillsRegistration> dataModelMonthlyYearlyReport;
	
	private Date searchMonth;
	private Integer searchYear;
	private boolean searching=false;
	
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
	 * Initialize method to get all SkillsRegistration and prepare a for a create of
	 * a new SkillsRegistration
	 * 
	 * @author TechFinium
	 * @see SkillsRegistration
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		
		try {
			allSkillsInfo();
		
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		
	}
	
	public void searchSkillsByYearOrMon()
	{
		try {
			
			if(searchMonth==null && searchYear==null)
			{
				throw new ValidationException("Please specify the month or yaer. Both search criteria can be use in combination");
			}
			skillsInfoByMonthOrYear();
			searching=true;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void clearYearAndMon()
	{
		searchMonth=null;
		searchYear=null;
		dataModelMonthlyYearlyReport=null;
		searching=false;
	}
	
	public String skillsRegHTML(List<SkillsRegistrationQualificationUnitStandards> list)
	{
		String qual;
		qual=service.convertQualificationsToHTML(list, null);
	    return qual;
	}
	
	public String  getQualHTML(Qualification qual)
	{
		return "<ul> <li> "+qual.getDescription()+" </li> </ul>";
	}
	
	private void skillsInfoByMonthOrYear() throws Exception{
		dataModelMonthlyYearlyReport = new LazyDataModel<SkillsRegistration>() {
			private static final long serialVersionUID = 1L;
			private List<SkillsRegistration> retorno = new ArrayList<SkillsRegistration>();
			@Override
			public List<SkillsRegistration> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					
					String searchType="";
					if(searchMonth !=null)
					{
						Calendar cal = Calendar.getInstance();
						cal.setTime(searchMonth);
						int month = cal.get(Calendar.MONTH)+1;
						filters.put("month", month);
						searchType="MON";
					}
					if(searchYear !=null){filters.put("year", searchYear);searchType="YR";}
					if(searchMonth !=null && searchYear !=null){searchType="MON_YR";}
					retorno = service.skillsByYearAndMonth(first, pageSize, sortField, sortOrder, filters,searchType);
					for(SkillsRegistration skillsRegistration:retorno )
					{
						service.populateInformation(skillsRegistration);
					}
					dataModelMonthlyYearlyReport.setRowCount(service.countSkillsByYearAndMonth(filters,searchType));
					
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(SkillsRegistration obj) {
				return obj.getId();
			}
			@Override
			public SkillsRegistration getRowData(String rowKey) {
				for (SkillsRegistration obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}
	
	public void allSkillsInfo() {
		
		 dataModel = new LazyDataModel<SkillsRegistration>() { 
		 
		   private static final long serialVersionUID = 1L; 
		   private List<SkillsRegistration> retorno = new  ArrayList<SkillsRegistration>();
		   
		   @Override 
		   public List<SkillsRegistration> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
		   
			try {
				retorno = service.allSkillsRegistration(SkillsRegistration.class, first,  pageSize,  sortField,  sortOrder,  filters);
				for(SkillsRegistration skillsRegistration:retorno )
				{
					service.populateInformation(skillsRegistration);
				}
				dataModel.setRowCount(service.count(SkillsRegistration.class,filters));
			} catch (Exception e) {
				logger.fatal(e);
				e.printStackTrace();
			} 
		    return retorno; 
		   }
		   
		    @Override
		    public Object getRowKey(SkillsRegistration obj) {
		        return obj.getId();
		    }
		    
		    @Override
		    public SkillsRegistration getRowData(String rowKey) {
		        for(SkillsRegistration obj : retorno) {
		            if(obj.getId().equals(Long.valueOf(rowKey)))
		                return obj;
		        }
		        return null;
		    }			    
		    
		  }; 
		}

	public LazyDataModel<SkillsRegistration> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SkillsRegistration> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<SkillsRegistration> getDataModelMonthlyYearlyReport() {
		return dataModelMonthlyYearlyReport;
	}

	public void setDataModelMonthlyYearlyReport(LazyDataModel<SkillsRegistration> dataModelMonthlyYearlyReport) {
		this.dataModelMonthlyYearlyReport = dataModelMonthlyYearlyReport;
	}

	public Date getSearchMonth() {
		return searchMonth;
	}

	public void setSearchMonth(Date searchMonth) {
		this.searchMonth = searchMonth;
	}

	public Integer getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(Integer searchYear) {
		this.searchYear = searchYear;
	}

	public boolean isSearching() {
		return searching;
	}

	public void setSearching(boolean searching) {
		this.searching = searching;
	}


}
