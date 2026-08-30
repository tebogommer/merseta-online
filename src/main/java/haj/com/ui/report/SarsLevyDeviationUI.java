package  haj.com.ui.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.SarsLevyRecon;
import haj.com.constants.HAJConstants;
import haj.com.entity.Blank;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsEmployerDetail;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.JasperService;
import haj.com.service.SarsFilesService;
import haj.com.service.SarsLevyDetailsService;
import haj.com.service.reports.SarsLeveyReportsService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "sarsLevyDeviationUI")
@ViewScoped
public class SarsLevyDeviationUI extends AbstractUI {

	private SarsLeveyReportsService service = new SarsLeveyReportsService();
	private SarsLevyDetailsService sarsLevyDetailsService= new SarsLevyDetailsService();
	private 	 SarsFilesService sarsFilesService = new SarsFilesService();
	private LazyDataModel<SarsLevyRecon> dataModel;
	private Date baseDate;
	private SarsLevyRecon sarsLevyRecon;
	private List<SarsLevyDetails> sarsLevyDetails;
	private SarsEmployerDetail sarsEmployerDetail;
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
		  baseDate = service.levyDeviationBaseDate();
	      report();
	}

	
	public void report() {

		dataModel = new LazyDataModel<SarsLevyRecon>() {

			private static final long serialVersionUID = 1L;
			private List<SarsLevyRecon> retorno = new ArrayList<SarsLevyRecon>();

			@Override
			public List<SarsLevyRecon> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.levyDeviation( first, pageSize,filters);
					dataModel.setRowCount(service.levyDeviationCount( filters).intValue());
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
				for (SarsLevyRecon obj : retorno) {
					if (obj.getSdlnumber().equals(rowKey))
						return obj;
				}
				return null;
			}

		};

	}

	public LazyDataModel<SarsLevyRecon> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<SarsLevyRecon> dataModel) {
		this.dataModel = dataModel;
	}

	public Date getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;
	}

	public SarsLevyRecon getSarsLevyRecon() {
		return sarsLevyRecon;
	}

	public void setSarsLevyRecon(SarsLevyRecon sarsLevyRecon) {
		this.sarsLevyRecon = sarsLevyRecon;
	}
	
   
	public void findDetail() {
		try {
			this.sarsLevyDetails = sarsLevyDetailsService.latestLevy(this.sarsLevyRecon.getSdlnumber());
			 this.sarsEmployerDetail = sarsFilesService.findBySDL(this.sarsLevyRecon.getSdlnumber());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<SarsLevyDetails> getSarsLevyDetails() {
		return sarsLevyDetails;
	}

	public void setSarsLevyDetails(List<SarsLevyDetails> sarsLevyDetails) {
		this.sarsLevyDetails = sarsLevyDetails;
	}

	public SarsEmployerDetail getSarsEmployerDetail() {
		return sarsEmployerDetail;
	}

	public void setSarsEmployerDetail(SarsEmployerDetail sarsEmployerDetail) {
		this.sarsEmployerDetail = sarsEmployerDetail;
	}

	
	public void excel() {
		try {
		     byte[] b = service.levyDeviationExcel(this.baseDate);
		     JasperService.convertByteArrayToServletOutputStreamExcel(b, "Levy_deviation_report_for_"+HAJConstants.sdf.format(this.baseDate)+".xlsx");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	
}
