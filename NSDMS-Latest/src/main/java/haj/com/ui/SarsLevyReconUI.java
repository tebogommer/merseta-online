package  haj.com.ui;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.microsoft.schemas.dynamics.gp._2006._01.PayablesInvoiceSummary;
import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;

import haj.com.bean.ReconSchemeYears;
import haj.com.bean.SarsLevyRecon;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.JasperService;
import haj.com.service.SarsFilesService;
import haj.com.service.SarsLevyReconService;
import haj.com.service.TS2Service;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter.GetPayablesInvoiceListAdapter;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter.GetVendorByKeyAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "sarsLevyReconUI")
@ViewScoped
public class SarsLevyReconUI extends AbstractUI {

    private SarsLevyReconService sarsLevyReconService = new SarsLevyReconService();
    private SarsFilesService sarsFilesService = new SarsFilesService();
    private TS2Service ts2Service =  new TS2Service();
    private List<SarsLevyDetails> levies; 
    private SarsLevyDetails sarsLevyDetails;
    private SarsLevyRecon sarsLevyRecon;
    private List<PayablesInvoiceSummary> gpSummary;
    private SarsLevyRecon sarsLevyReconGP;
    private Vendor vendor;
    private BigDecimal total;
    private List<ReconSchemeYears> sarsYears;
    private Integer sarsReconSchemeYears;
    private List<ReconSchemeYears> setaYears;
    private Integer setaReconSchemeYears;

    
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
		//this.levies = sarsLevyReconService.levyReconPerSchemeYear();
		this.sarsYears = sarsFilesService.schemeYears();
		this.setaYears = ts2Service.listSchemeYears();
		this.sarsLevyDetails = null;
	}

	
	public void runForSchemeYears() {
		try {
			this.sarsLevyDetails = null;
			if (sarsReconSchemeYears==null || setaReconSchemeYears==null) throw new Exception("Select scheme years");

			this.sarsLevyDetails = sarsLevyReconService.levyReconPerSchemeYear(setaReconSchemeYears, sarsReconSchemeYears);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<SarsLevyDetails> getLevies() {
		return levies;
	}


	public void setLevies(List<SarsLevyDetails> levies) {
		this.levies = levies;
	}


	public SarsLevyDetails getSarsLevyDetails() {
		return sarsLevyDetails;
	}


	public void setSarsLevyDetails(SarsLevyDetails sarsLevyDetails) {
		this.sarsLevyDetails = sarsLevyDetails;
	}

	

	public void detail() {
		try {
		     byte[] b = sarsLevyReconService.levyReconPerSchemeYearPerSDL(sarsLevyDetails.getSchemeYear());
		     JasperService.convertByteArrayToServletOutputStreamExcel(b, "Levy_recon_per_sdl_for_scheme_year_"+(sarsLevyDetails.getSchemeYear()+1)+".xlsx");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	
	public void exceptionReport() {
		try {
			sarsLevyRecon = sarsLevyReconService.exceptionReport(sarsLevyDetails.getSchemeYear());
		    // JasperService.convertByteArrayToServletOutputStreamExcel(b, "Exception_report_per_sdl_for_scheme_year_"+(sarsLevyDetails.getSchemeYear()+1)+".xlsx");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	public SarsLevyRecon getSarsLevyRecon() {
		return sarsLevyRecon;
	}


	public void setSarsLevyRecon(SarsLevyRecon sarsLevyRecon) {
		this.sarsLevyRecon = sarsLevyRecon;
	}
	
	
	public void gpTrans() {
		try {
			vendor = new GetVendorByKeyAdapter().getVendorByKey(this.sarsLevyReconGP.getSdlnumber());
			
			gpSummary =  new GetPayablesInvoiceListAdapter().getPayablesInvoiceList(this.sarsLevyReconGP.getSdlnumber());
			
			double t = 0.0;
			for (PayablesInvoiceSummary ps : gpSummary) {
				t = t + ps.getDocumentAmount().getValue().doubleValue();
			}
			this.total = BigDecimal.valueOf(t);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	public List<PayablesInvoiceSummary> getGpSummary() {
		return gpSummary;
	}


	public void setGpSummary(List<PayablesInvoiceSummary> gpSummary) {
		this.gpSummary = gpSummary;
	}


	public SarsLevyRecon getSarsLevyReconGP() {
		return sarsLevyReconGP;
	}


	public void setSarsLevyReconGP(SarsLevyRecon sarsLevyReconGP) {
		this.sarsLevyReconGP = sarsLevyReconGP;
	}


	public Vendor getVendor() {
		return vendor;
	}


	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}


	public BigDecimal getTotal() {
		return total;
	}


	public void setTotal(BigDecimal total) {
		this.total = total;
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








}
