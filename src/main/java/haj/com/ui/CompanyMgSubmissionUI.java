package haj.com.ui;

import java.util.ArrayList;
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
import haj.com.entity.Signoff;
import haj.com.entity.Sites;
import haj.com.entity.datamodel.MGVerificationDataModel;
import haj.com.entity.datamodel.MGVerificationRegionDataModel;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.MgVerificationDetailsService;
import haj.com.service.MgVerificationService;
import haj.com.service.SignoffService;
import haj.com.service.SitesService;

//TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantUI.
 */
@ManagedBean(name = "companyMgSubmissionUI")
@ViewScoped
public class CompanyMgSubmissionUI extends AbstractUI {

	/** Entity Layer */
	private MgVerification mgVerification;
	private Company company;

	/** Service Layer */
	private MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();
	private CompanyService companyService = new CompanyService();
	private MgVerificationService mgVerificationService = new MgVerificationService();
	private MgVerificationDetailsService mgVerificationDetailsService = new MgVerificationDetailsService();
	private SitesService sitesService = new SitesService();
	private SignoffService signoffService = new SignoffService();

	/** Lists */	
	private LazyDataModel<MgVerification> mgVerificationDataModel;
	private LazyDataModel<MgVerificationDetails> dataModel = null;
	private List<Sites>  sites = null;
	private List<Signoff> signOffLists = new ArrayList<>();

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
		mgVerificationDataModel = new MGVerificationRegionDataModel(getSessionUI().getActiveUser());
	}

	private void pivitolInfoWsp() throws Exception {
		company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
//		sites = sitesService.findByCompany(company);
		dataModel = new LazyDataModel<MgVerificationDetails>() {

			private static final long serialVersionUID = 1L;
			private List<MgVerificationDetails> retorno = new ArrayList<MgVerificationDetails>();

			@Override
			public List<MgVerificationDetails> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
//					retorno = mgVerificationDetailsService.allMgVerificationDetails(MgVerificationDetails.class, first, pageSize, sortField, sortOrder, filters, mgVerification.getWsp());
//					dataModel.setRowCount(mgVerificationDetailsService.count(MgVerificationDetails.class, filters, mgVerification.getWsp()));
					retorno = mgVerificationDetailsService.allMgVerificationDetailsByWspId(MgVerificationDetails.class, first, pageSize, sortField, sortOrder, filters, mgVerification.getWsp());
					dataModel.setRowCount(mgVerificationDetailsService.countByWspId(MgVerificationDetails.class, filters, mgVerification.getWsp()));
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
			populateSignOffs();
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
	
	private void populateSignOffs() throws Exception{
		if (mgVerification != null && mgVerification.getId() != null) {
			signOffLists = signoffService.findByVerifivcation(mgVerification);
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

	public List<Signoff> getSignOffLists() {
		return signOffLists;
	}

	public void setSignOffLists(List<Signoff> signOffLists) {
		this.signOffLists = signOffLists;
	}

}
