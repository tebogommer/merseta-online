package haj.com.wsp.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.YesNoLookupService;
import haj.com.service.lookup.EtqaService;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantUI.
 * @param <K>
 */
@ManagedBean(name = "discgrantUI")
@ViewScoped
public class DiscGrantUI<K> extends AbstractUI {

	private MandatoryGrantDetail mandatoryGrant;
	private List<MandatoryGrantDetail> mandatoryGrants;
	private MandatoryGrantDetailService mandatoryGrantService = new MandatoryGrantDetailService();
	private WspReportEnum wspReport;
	private EtqaService etqaService = new EtqaService();
	private YesNoLookupService yesNoService = new YesNoLookupService();
	
	private HashMap mapRecommendations = new HashMap<>();
	
	/**MandatoryGrantDetail dataModel*/
	private LazyDataModel<MandatoryGrantDetail> dataModel;

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
		wspReport = WspReportEnum.WSP;

		mandatoryGrant = new MandatoryGrantDetail(getSessionUI().getWspSession(), wspReport);
		//pivitolInfo();
		MandatoryGrantDetailInfo() ;
	}

	public void pivitolInfo() {
		try {
			mandatoryGrants = mandatoryGrantService.findByWSPHosting(getSessionUI().getWspSession(), wspReport, HAJConstants.DISC_FUNDING_ID);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void MandatoryGrantDetailInfo() {
		
		 dataModel = new LazyDataModel<MandatoryGrantDetail>() { 
		 
		   private static final long serialVersionUID = 1L; 
		   private List<MandatoryGrantDetail> MandatoryGrantDetailList = new  ArrayList<MandatoryGrantDetail>();
		   
		   @Override 
		   public List<MandatoryGrantDetail> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
		   
			try {
				MandatoryGrantDetailList = mandatoryGrantService.findByWSPHosting(MandatoryGrantDetail.class, first, pageSize, sortField, sortOrder, filters, getSessionUI().getWspSession(), wspReport,  HAJConstants.DISC_FUNDING_ID);
				dataModel.setRowCount((int) mandatoryGrantService.findByWSPHostingCount(getSessionUI().getWspSession(), wspReport,  HAJConstants.DISC_FUNDING_ID));
			} catch (Exception e) {
				logger.fatal(e);
			}
			
		    return MandatoryGrantDetailList; 
		   }
		   
		    @Override
		    public Object getRowKey(MandatoryGrantDetail obj) {
		        return obj.getId();
		    }
		    
		    @Override
		    public MandatoryGrantDetail getRowData(String rowKey) {
		        for(MandatoryGrantDetail obj : MandatoryGrantDetailList) {
		            if(obj.getId().equals(Long.valueOf(rowKey)))
		                return obj;
		        }
		        return null;
		    }			    
		    
		  }; 
	}
	
	/**
	 * getLastEntryOfMandatoryGrantDetailRowRecommendationQuantity
	 * 
	 * @param MandatoryGrantDetailId
	 * @return Integer
	 */
	public Integer getLastEntryOfMandatoryGrantDetailRowRecommendationQuantity(Long MandatoryGrantDetailId) {
		
		Integer returnedQuantity = new Integer(0);
		
		if(mapRecommendations != null) {
			
			returnedQuantity = ((Integer) mapRecommendations.get(MandatoryGrantDetailId));
		}
		
		return returnedQuantity;
	}

	public void generateEmployeesEmployed() {
		try {
			mandatoryGrantService.create(mandatoryGrant);
			mandatoryGrant = new MandatoryGrantDetail(getSessionUI().getWspSession(), wspReport);
			mandatoryGrants = mandatoryGrantService.findByWSPHosting(getSessionUI().getWspSession(), wspReport, HAJConstants.DISC_FUNDING_ID);
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteMandatory() {
		try {
			mandatoryGrantService.delete(mandatoryGrant);
			mandatoryGrant = new MandatoryGrantDetail(getSessionUI().getWspSession(), wspReport);
			mandatoryGrants = mandatoryGrantService.findByWSPHosting(getSessionUI().getWspSession(), wspReport, HAJConstants.DISC_FUNDING_ID);
			addInfoMessage(getEntryLanguage("row.deleted"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applySaqaData(SelectEvent event) {
		try {
			mandatoryGrant.setNqfLevels(mandatoryGrant.getQualification().getNqflevel());
			mandatoryGrant.setInterventionLevel(mandatoryGrant.getNqfLevels().getInterventionLevel());
			mandatoryGrant.setEtqa(etqaService.findByCode(mandatoryGrant.getQualification().getEtqaid()));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void applyInterventionData(SelectEvent event) {
		try {
			mandatoryGrant.setPivotNonPivot(mandatoryGrant.getInterventionType().getPivotNonPivot());
			if (mandatoryGrant.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				mandatoryGrant.setNqfAligned(yesNoService.findByKey(HAJConstants.YES_ID));
			} else {
				mandatoryGrant.setNqfAligned(yesNoService.findByKey(HAJConstants.NO_ID));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MandatoryGrantDetail getMandatoryGrant() {
		return mandatoryGrant;
	}

	public void setMandatoryGrant(MandatoryGrantDetail mandatoryGrant) {
		this.mandatoryGrant = mandatoryGrant;
	}

	public List<MandatoryGrantDetail> getMandatoryGrants() {
		return mandatoryGrants;
	}

	public void setMandatoryGrants(List<MandatoryGrantDetail> mandatoryGrants) {
		this.mandatoryGrants = mandatoryGrants;
	}

	public LazyDataModel<MandatoryGrantDetail> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MandatoryGrantDetail> dataModel) {
		this.dataModel = dataModel;
	}

}
