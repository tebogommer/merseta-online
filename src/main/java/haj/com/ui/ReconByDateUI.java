package  haj.com.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.Email;

import org.omnifaces.util.Faces;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.IngterSetaTransferBean;
import haj.com.bean.ReconSchemeYears;
import haj.com.bean.SarsLevyRecon;
import haj.com.constants.HAJConstants;
import haj.com.entity.Blank;
import haj.com.entity.datatakeon.TS2;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.HistoricalIntersetaTransfersService;
import haj.com.service.SarsFilesService;
import haj.com.service.SarsLevyReconService;
import haj.com.service.TS2Service;
import haj.com.service.reports.SarsLeveyReportsService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "reconByDateUI")
@ViewScoped
public class ReconByDateUI extends AbstractUI {


	private SarsLevyReconService sarsLevyReconService = new SarsLevyReconService();
    private SarsFilesService sarsFilesService = new SarsFilesService();
    private TS2Service ts2Service =  new TS2Service();
	private Date fromDate;
	private Date toDate;
	private Date maxLevyDate;
	private Date maxPostDate;
	private Date fromDateInv;
	private Date toDateInv;
	private Double difference;
	@Email(message="Invalid email address")
	private String email;

	private SarsLevyDetails sarsLevyDetails;
	private SarsLeveyReportsService sarsLeveyReportsService = new SarsLeveyReportsService();
	private TS2 ts2;

    private List<ReconSchemeYears> sarsYears;
    private Integer sarsReconSchemeYears;
    private List<ReconSchemeYears> setaYears;
    private Integer setaReconSchemeYears;

    private List<ReconSchemeYears> reconSchemeYears;

	private LazyDataModel<SarsLevyRecon> dataModel;

	private HistoricalIntersetaTransfersService historicalIntersetaTransfersService = new HistoricalIntersetaTransfersService();
	private  List<IngterSetaTransferBean> ingterSetaTransferBeanList;
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
		this.email = getSessionUI().getUser().getEmail();
		this.sarsYears = sarsFilesService.schemeYears();
		this.setaYears = ts2Service.listSchemeYears();
		this.sarsLevyDetails = null;
	    prepNew();
	}

	public void prepNew() throws Exception {
		try {
			 this.fromDate = null;this.toDate=null;this.fromDateInv=null;this.toDateInv=null;
			 // For development
			 this.fromDate = HAJConstants.sdf.parse("2000-04-01");
			 this.fromDateInv = this.fromDate;
			 this.toDate = HAJConstants.sdf.parse("2017-02-28");
			 this.toDateInv = HAJConstants.sdf.parse("2017-03-31");
			 sarsReconSchemeYears = 2016;
			 setaReconSchemeYears = 2017;


			 this.maxLevyDate = sarsLeveyReportsService.levyDeviationBaseDate();
			 this.maxPostDate = sarsLevyReconService.maxPostDate();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void initToDate() {
		try {
			if (this.fromDate!=null) {
				this.toDate = GenericUtility.getLasttDayOfMonth(this.fromDate);
			}
			else {
				this.toDate = null;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void initToDateInv() {
		try {
			if (this.fromDateInv!=null) {
				this.toDateInv = GenericUtility.getLasttDayOfMonth(this.fromDateInv);
			}
			else {
				this.toDateInv = null;
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Date getFromDateInv() {
		return fromDateInv;
	}

	public void setFromDateInv(Date fromDateInv) {
		this.fromDateInv = fromDateInv;
	}

	public Date getToDateInv() {
		return toDateInv;
	}

	public void setToDateInv(Date toDateInv) {
		this.toDateInv = toDateInv;
	}

	public void run() {
		try {
			validateDates();
			difference = 0.0;
			ReconSchemeYears reconSchemeYear = sarsLevyReconService.levyForPeriod(fromDate, toDate, fromDateInv, toDateInv,sarsReconSchemeYears, setaReconSchemeYears,true );
			this.sarsLevyDetails = reconSchemeYear.getSarsLevyDetails();
			this.ts2 = reconSchemeYear.getTs2();
			this.difference = reconSchemeYear.getDifference();
			reconSchemeYears = null;
			ingterSetaTransferBeanList = historicalIntersetaTransfersService.mandatoryBySchemeYear(setaReconSchemeYears);
			new Thread(new Runnable() {
				@Override
				public void run() {

				try {
					reconSchemeYears = sarsLevyReconService.levyOverPeriod(fromDate, toDate, fromDateInv, toDateInv);
				} catch (Exception e) {
					logger.fatal(e);
				}

				}	}).start();


/*			sarsLevyDetails = 	sarsLevyReconService.levyForPeriod(fromDate, toDate, fromDateInv, toDateInv,sarsReconSchemeYears, setaReconSchemeYears );
			ts2 = sarsLevyReconService.invoiceTotalForPeriod(fromDateInv, toDateInv,setaReconSchemeYears);
			if (sarsLevyDetails !=null && sarsLevyDetails.getMandatoryLevyD()!=null && ts2!=null && ts2.getDocumentAmountD()!=null) {
				reconInfo();
				if (sarsLevyDetails.getMandatoryLevyD().doubleValue() < 0)  sarsLevyDetails.setMandatoryLevyD(sarsLevyDetails.getMandatoryLevyD()*-1);
				if (ts2.getDocumentAmountD().doubleValue() < 0)  ts2.setDocumentAmountD(ts2.getDocumentAmountD()*-1);

				if (sarsLevyDetails.getMandatoryLevyD().doubleValue() > ts2.getDocumentAmountD().doubleValue()) {
					difference = sarsLevyDetails.getMandatoryLevyD().doubleValue() - ts2.getDocumentAmountD().doubleValue();
				}
				else {
					difference =  ts2.getDocumentAmountD().doubleValue() - sarsLevyDetails.getMandatoryLevyD().doubleValue();
				}

			}
*/

		//	if (ts2!=null || ts2.getDocumentAmountD()==null) ts2.setDocumentAmountD(0.0);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void mailDetail() {
		try {
		///	if (sarsLevyDetails !=null && sarsLevyDetails.getMandatoryLevyD()!=null && ts2!=null && ts2.getDocumentAmountD()!=null) {
				byte[] content = sarsLevyReconService.ts2ForPeriod(fromDate, toDate, fromDateInv, toDateInv, sarsReconSchemeYears, setaReconSchemeYears, email);
				Faces.sendFile(content, "ReconBySchemeYearPerLevyNumber.xlsx", true);

				//addInfoMessage("You will receive an email shortly with the report attached.");
		//	}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	public SarsLevyDetails getSarsLevyDetails() {
		return sarsLevyDetails;
	}



	public void setSarsLevyDetails(SarsLevyDetails sarsLevyDetails) {
		this.sarsLevyDetails = sarsLevyDetails;
	}



	public TS2 getTs2() {
		return ts2;
	}



	public void setTs2(TS2 ts2) {
		this.ts2 = ts2;
	}

	private void validateDates() throws Exception {
		if (fromDate==null || fromDateInv==null) throw new Exception("From dates need to be supplied");

		fromDate = GenericUtility.getStartOfDay(fromDate);
		if (toDate ==null) {
			toDate = GenericUtility.getLasttDayOfMonth(fromDate);
		}
		toDate = GenericUtility.getEndOfDay(toDate);

		fromDateInv = GenericUtility.getStartOfDay(fromDateInv);
		if (toDateInv ==null) {
			toDateInv = GenericUtility.getLasttDayOfMonth(fromDateInv);
		}
		toDateInv = GenericUtility.getEndOfDay(toDateInv);

		if (toDate.before(fromDate)) throw new Exception("SARS/DHET to date must be after SARS/DHET from date.");
		if (toDateInv.before(fromDateInv)) throw new Exception("Posting to date must be after Posting from date.");
	}



	public Date getMaxLevyDate() {
		return maxLevyDate;
	}



	public void setMaxLevyDate(Date maxLevyDate) {
		this.maxLevyDate = maxLevyDate;
	}



	public Date getMaxPostDate() {
		return maxPostDate;
	}



	public void setMaxPostDate(Date maxPostDate) {
		this.maxPostDate = maxPostDate;
	}



	public Double getDifference() {
		return difference;
	}



	public void setDifference(Double difference) {
		this.difference = difference;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public List<ReconSchemeYears> getSarsYears() {
		return sarsYears;
	}



	public void setSarsYears(List<ReconSchemeYears> sarsYears) {
		this.sarsYears = sarsYears;
	}



	public Integer getSarsReconSchemeYears() {
		return sarsReconSchemeYears;
	}



	public void setSarsReconSchemeYears(Integer sarsReconSchemeYears) {
		this.sarsReconSchemeYears = sarsReconSchemeYears;
	}



	public List<ReconSchemeYears> getSetaYears() {
		return setaYears;
	}



	public void setSetaYears(List<ReconSchemeYears> setaYears) {
		this.setaYears = setaYears;
	}



	public Integer getSetaReconSchemeYears() {
		return setaReconSchemeYears;
	}



	public void setSetaReconSchemeYears(Integer setaReconSchemeYears) {
		this.setaReconSchemeYears = setaReconSchemeYears;
	}



	public LazyDataModel<SarsLevyRecon> getDataModel() {
		return dataModel;
	}



	public void setDataModel(LazyDataModel<SarsLevyRecon> dataModel) {
		this.dataModel = dataModel;
	}


	public void reconInfo() {


		 dataModel = new LazyDataModel<SarsLevyRecon>() {

		   private static final long serialVersionUID = 1L;
		   private List<SarsLevyRecon> retorno = new  ArrayList<SarsLevyRecon>();

		   @Override
		   public List<SarsLevyRecon> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  {

			try {
				//retorno = sarsLevyReconService.levyDeviationForPeriod(CompanyHistory.class,first,pageSize,sortField,sortOrder,filters);
				String sdlNumber = "%";
				if (filters.containsKey("sdlnumber")) {
					sdlNumber = (String)filters.get("sdlnumber");
				}
				retorno = sarsLevyReconService.levyDeviationForPeriod(fromDate, toDate, fromDateInv, toDateInv, sarsReconSchemeYears, setaReconSchemeYears, first, pageSize,sdlNumber);
				dataModel.setRowCount(sarsLevyReconService.countLevyDeviationForPeriod(fromDate, toDate, fromDateInv, toDateInv, sarsReconSchemeYears, setaReconSchemeYears,sdlNumber));
			} catch (Exception e) {
				logger.fatal(e);
			}
		    return retorno;
		   }

		    @Override
		    public Object getRowKey(SarsLevyRecon obj) {
		        return obj.getSdlnumber();
		    }

		    @Override
		    public SarsLevyRecon getRowData(String rowKey) {
		        for(SarsLevyRecon obj : retorno) {
		            if(obj.getSdlnumber().equals(rowKey))
		                return obj;
		        }
		        return null;
		    }

		  };



}



	public List<ReconSchemeYears> getReconSchemeYears() {
		return reconSchemeYears;
	}



	public void setReconSchemeYears(List<ReconSchemeYears> reconSchemeYears) {
		this.reconSchemeYears = reconSchemeYears;
	}



	public List<IngterSetaTransferBean> getIngterSetaTransferBeanList() {
		return ingterSetaTransferBeanList;
	}



	public void setIngterSetaTransferBeanList(List<IngterSetaTransferBean> ingterSetaTransferBeanList) {
		this.ingterSetaTransferBeanList = ingterSetaTransferBeanList;
	}





}
