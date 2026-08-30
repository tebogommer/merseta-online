package haj.com.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.MgVerification;
import haj.com.entity.MgVerificationDetails;
import haj.com.entity.Sites;
import haj.com.entity.datamodel.MGVerificationDataModel;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.AuditorMonitorReview;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.MandatoryGrantRecommendationService;
import haj.com.service.MandatoryGrantService;
import haj.com.service.MgVerificationDetailsService;
import haj.com.service.MgVerificationService;
import haj.com.service.SignoffService;
import haj.com.service.SitesService;
import haj.com.service.TasksService;
import haj.com.service.WspService;
import haj.com.service.YesNoLookupService;
import haj.com.service.lookup.EtqaService;
import haj.com.utils.GenericUtility;

//TODO: Auto-generated Javadoc
/**
 * The Class MandatoryGrantVerificationUI.
 */
@ManagedBean(name = "mandatoryGrantVerificationReportUI")
@ViewScoped
public class MandatoryGrantVerificationReportUI extends AbstractUI {

	/** Entity Layer */
	private MgVerification mgVerification;
	private Company company;
	


	/** Service Layer */
	private MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();
	private CompanyService companyService = new CompanyService();
	private MgVerificationService mgVerificationService = new MgVerificationService();
	private MgVerificationDetailsService mgVerificationDetailsService = new MgVerificationDetailsService();
	private SitesService sitesService = new SitesService();

	/** Lists */	
	private LazyDataModel<MgVerification> mgVerificationDataModel;
	private LazyDataModel<MgVerificationDetails> dataModel = null;
	private List<Sites>  sites = null;

	private boolean selected = false;

	
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

	private void runInit() throws Exception {
		populateMGVerificationDtata();
	}
	
	private void populateMGVerificationDtata() {
		mgVerificationDataModel = new MGVerificationDataModel();
	}

	private void pivitolInfoWsp() throws Exception {
		company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
		sites = sitesService.findByCompany(company);
		dataModel = new LazyDataModel<MgVerificationDetails>() {

			private static final long serialVersionUID = 1L;
			private List<MgVerificationDetails> retorno = new ArrayList<MgVerificationDetails>();

			@Override
			public List<MgVerificationDetails> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = mgVerificationDetailsService.allMgVerificationDetails(MgVerificationDetails.class, first, pageSize, sortField, sortOrder, filters, mgVerification.getWsp());
					dataModel.setRowCount(mgVerificationDetailsService.count(MgVerificationDetails.class, filters, mgVerification.getWsp()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(MgVerificationDetails obj) {
				return obj.getId();
			}

			@Override
			public MgVerificationDetails getRowData(String rowKey) {
				for (MgVerificationDetails obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}
	
	/**
	 * When a wsp is selected locates all mandatory grants by wsp company
	 */
	public void selctemgVerification() {
		try {
			mandatoryGrantDetailService.generateDetail(mgVerification);
			dataModel=null;
			this.company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
			selected = true;
			locateWspMgVerification();
			pivitolInfoWsp();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	/**
	 * locates MG verification by WSP
	 * 
	 * @throws Exception
	 */
	private void locateWspMgVerification() throws Exception {
		mgVerification = mgVerificationService.findByWspId(mgVerification.getWsp());
		
		if (mgVerification == null || mgVerification.getId() == null) {
			mgVerification = new MgVerification();
			mgVerification.setWsp(mgVerification.getWsp());
		}
	}

	public MgVerification getMgVerification() {
		return mgVerification;
	}

	public void setMgVerification(MgVerification mgVerification) {
		this.mgVerification = mgVerification;
	}


	public LazyDataModel<MgVerification> getMgVerificationDataModel() {
		return mgVerificationDataModel;
	}

	public void setMgVerificationDataModel(LazyDataModel<MgVerification> mgVerificationDataModel) {
		this.mgVerificationDataModel = mgVerificationDataModel;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public LazyDataModel<MgVerificationDetails> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MgVerificationDetails> dataModel) {
		this.dataModel = dataModel;
	}

}
