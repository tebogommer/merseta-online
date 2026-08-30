package haj.com.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.bean.SarsEmployerDetailBean;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsFiles;
import haj.com.service.ReportService;
import haj.com.service.SarsFilesService;

// TODO: Auto-generated Javadoc
/**
 * The Class AfterLevyImportReportJoiningAndLeavingCompaniesUI.
 */
@ManagedBean(name = "afterLevyImportReportJoiningAndLeavingCompaniesUI")
@ViewScoped
public class AfterLevyImportReportJoiningAndLeavingCompaniesUI extends AbstractUI {

	private ReportService reportService = new ReportService();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMMM dd");
	private List<SarsEmployerDetailBean> joiners;
	private List<SarsEmployerDetailBean> leavers;
	private SarsFilesService sarsFilesService = new SarsFilesService();
	private LazyDataModel<SarsFiles> sarsFilesDataModel;
	private SarsFiles fromSarsFiles = null;
	private SarsFiles toSarsFiles = null;
	

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
	 * Initialize method to get all chamberrevenuebymonth and prepare a for a create
	 * of a new chamberrevenuebymonth.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see chamberrevenuebymonth
	 */
	private void runInit() throws Exception {
		sarsfilesInfo();
	}
	
	public void populateBean(){
		
		try {
			// version one
//			joiners = reportService.findMersetaJoiners(fromSarsFiles.getId(), toSarsFiles.getId());
//			leavers = reportService.findMersetaLeavers(fromSarsFiles.getId(), toSarsFiles.getId());
			
			// version two 
			joiners = reportService.findMersetaJoinersVersionTwo(fromSarsFiles.getId(), toSarsFiles.getId());
			leavers = reportService.findMersetaLeaversVersionTwo(fromSarsFiles.getId(), toSarsFiles.getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
	}

	public void sarsfilesInfo() {
		sarsFilesDataModel = new LazyDataModel<SarsFiles>() {
			private static final long serialVersionUID = 1L;
			private List<SarsFiles> retorno = new ArrayList<>();

			@Override
			public List<SarsFiles> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "forMonth";
						sortOrder = sortOrder.DESCENDING;
					}
					retorno = sarsFilesService.allSarsFiles(SarsFiles.class, first, pageSize, sortField, sortOrder, filters);
					sarsFilesDataModel.setRowCount(sarsFilesService.count(SarsFiles.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SarsFiles obj) {
				return obj.getId();
			}

			@Override
			public SarsFiles getRowData(String rowKey) {
				for (SarsFiles obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	public SarsFilesService getSarsFilesService() {
		return sarsFilesService;
	}

	public void setSarsFilesService(SarsFilesService sarsFilesService) {
		this.sarsFilesService = sarsFilesService;
	}

	public SimpleDateFormat getSdf() {
		return sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public List<SarsEmployerDetailBean> getJoiners() {
		return joiners;
	}

	public void setJoiners(List<SarsEmployerDetailBean> joiners) {
		this.joiners = joiners;
	}

	public List<SarsEmployerDetailBean> getLeavers() {
		return leavers;
	}

	public void setLeavers(List<SarsEmployerDetailBean> leavers) {
		this.leavers = leavers;
	}

	public LazyDataModel<SarsFiles> getSarsFilesDataModel() {
		return sarsFilesDataModel;
	}

	public void setSarsFilesDataModel(LazyDataModel<SarsFiles> sarsFilesDataModel) {
		this.sarsFilesDataModel = sarsFilesDataModel;
	}

	public SarsFiles getFromSarsFiles() {
		return fromSarsFiles;
	}

	public void setFromSarsFiles(SarsFiles fromSarsFiles) {
		if(fromSarsFiles != null) {
			this.fromSarsFiles = fromSarsFiles;
		}
	}

	public SarsFiles getToSarsFiles() {
		return toSarsFiles;
	}

	public void setToSarsFiles(SarsFiles toSarsFiles) {
		if(toSarsFiles != null) {
			this.toSarsFiles = toSarsFiles;
		}
	}

}
