package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.TrainingProviderVerfication;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.SummativeAssessmentReportUnitStandards;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SummativeAssessmentReportService;
import haj.com.service.TrainingProviderVerficationService;

@ManagedBean(name = "generateCertificatesUI")
@ViewScoped
public class GenerateCertificatesUI extends AbstractUI {

	private TrainingProviderVerficationService service = new TrainingProviderVerficationService();
	private TrainingProviderVerfication trainingproviderverfication = null;
	private List<TrainingProviderVerfication> trainingproviderverficationList = new ArrayList<>();
	private LazyDataModel<TrainingProviderVerfication> dataModel;
	private LazyDataModel<TrainingProviderVerfication> dataModelGenerated;
	private SummativeAssessmentReportService summativeAssessmentReportService = new SummativeAssessmentReportService();
	private LazyDataModel<SummativeAssessmentReport> dataModelSummativeAssessmentReport;
	private SummativeAssessmentReport assessmentReport;
	private SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards;
	boolean show = false;
	boolean showList = false;
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			e.printStackTrace();
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void runInit() throws Exception {		
		verificationInfo();
		verificationInfoGenerated();
	}
	
	
	public void verificationInfo() {	 
		 dataModel = new LazyDataModel<TrainingProviderVerfication>() { 		 
		   private static final long serialVersionUID = 1L; 
		   private List<TrainingProviderVerfication> retorno = new  ArrayList<TrainingProviderVerfication>();		   
		   @Override 
		   public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
				try {
					
					filters.put("cetificateGenerated", false);
					filters.put("status", ApprovalEnum.Approved);
					filters.put("interventionTypeEnum", InterventionTypeEnum.Learnership);
					retorno = service.allApprovedTrainingProviderVerficationGeneratedLearnership(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countApprovedTrainingProviderVerficationGeneratedLearnership(TrainingProviderVerfication.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
		   }
		   
		    @Override
		    public Object getRowKey(TrainingProviderVerfication obj) {
		        return obj.getId();
		    }
		    
		    @Override
		    public TrainingProviderVerfication getRowData(String rowKey) {
		        for(TrainingProviderVerfication obj : retorno) {
		            if(obj.getId().equals(Long.valueOf(rowKey)))
		                return obj;
		        }
		        return null;
		    }			    
		    
		  }; 
	}

	public void verificationInfoGenerated() {	 
		dataModelGenerated = new LazyDataModel<TrainingProviderVerfication>() { 		 
		   private static final long serialVersionUID = 1L; 
		   private List<TrainingProviderVerfication> retorno = new  ArrayList<TrainingProviderVerfication>();		   
		   @Override 
		   public List<TrainingProviderVerfication> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters)  { 
				try {
					filters.put("cetificateGenerated", true);
					filters.put("status", ApprovalEnum.Approved);
					retorno = service.allApprovedTrainingProviderVerficationGenerated(first, pageSize, sortField, sortOrder, filters);
					dataModelGenerated.setRowCount(service.countApprovedTrainingProviderVerficationGenerated(TrainingProviderVerfication.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				} 
			    return retorno; 
		   }
		   
		    @Override
		    public Object getRowKey(TrainingProviderVerfication obj) {
		        return obj.getId();
		    }
		    
		    @Override
		    public TrainingProviderVerfication getRowData(String rowKey) {
		        for(TrainingProviderVerfication obj : retorno) {
		            if(obj.getId().equals(Long.valueOf(rowKey)))
		                return obj;
		        }
		        return null;
		    }			    
		    
		  }; 
	}
	
	public void summativeassessmentreportInfo() {

		dataModelSummativeAssessmentReport = new LazyDataModel<SummativeAssessmentReport>() {
			private static final long serialVersionUID = 1L;
			private List<SummativeAssessmentReport> retorno = new ArrayList<SummativeAssessmentReport>();

			@Override
			public List<SummativeAssessmentReport> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					filters.put("trainingProviderVerficationID", trainingproviderverfication.getId());
					retorno = summativeAssessmentReportService.allSummativeAssessmentReportForVerification(first, pageSize, sortField, sortOrder, filters);
					dataModelSummativeAssessmentReport.setRowCount(summativeAssessmentReportService.countForVerification(filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(SummativeAssessmentReport obj) {
				return obj.getId();
			}

			@Override
			public SummativeAssessmentReport getRowData(String rowKey) {
				for (SummativeAssessmentReport obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
		show=true;
	}
	
	public void downloadReport() {
		try {			
			summativeAssessmentReportService.downloadETQ_TP_011_Report(trainingproviderverfication, getSessionUI().getActiveUser());
			//summativeAssessmentReportService.downloadReport(trainingproviderverfication, getSessionUI().getActiveUser());
			show = false;
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void addToList() {
		try {
			precheckIfExists();
			trainingproviderverficationList.add(trainingproviderverfication);
			showList=true;
			addInfoMessage("Added to the list");
		} catch (Exception e) {
			logger.fatal(e);
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void generateAllCertificate() {
		try {
			if(trainingproviderverficationList.size()==0) {
				throw new Exception("Please add atleast on learner to generate certificate");
			}
			service.generateAllCertificateNew(trainingproviderverficationList, getSessionUI().getActiveUser());
			//service.generateAllCertificate(trainingproviderverficationList, getSessionUI().getActiveUser());
			trainingproviderverficationList = new ArrayList<>();
			showList = false;
			verificationInfo();
			verificationInfoGenerated();
		}catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void precheckIfExists() throws Exception {
		if(trainingproviderverficationList.size() > 0) {
			boolean exits = false;
			for(TrainingProviderVerfication trainingProviderVerfication:trainingproviderverficationList) {
				if(trainingProviderVerfication.getId() == trainingproviderverfication.getId() ) {
					exits = true;
					break;
				}
			}	
			if(exits) {
				throw new Exception("Application already added to the list");
			}
		}		
	}
	
	public void createWorkflow() {
		try {
			service.createWorkflow(trainingproviderverfication, getSessionUI().getActiveUser());
			showList = false;
			verificationInfo();
			verificationInfoGenerated();
		}catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public TrainingProviderVerfication getTrainingproviderverfication() {
		return trainingproviderverfication;
	}

	public void setTrainingproviderverfication(TrainingProviderVerfication trainingproviderverfication) {
		this.trainingproviderverfication = trainingproviderverfication;
	}

	public LazyDataModel<TrainingProviderVerfication> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<TrainingProviderVerfication> dataModel) {
		this.dataModel = dataModel;
	}

	public LazyDataModel<SummativeAssessmentReport> getDataModelSummativeAssessmentReport() {
		return dataModelSummativeAssessmentReport;
	}

	public void setDataModelSummativeAssessmentReport(LazyDataModel<SummativeAssessmentReport> dataModelSummativeAssessmentReport) {
		this.dataModelSummativeAssessmentReport = dataModelSummativeAssessmentReport;
	}

	public SummativeAssessmentReport getAssessmentReport() {
		return assessmentReport;
	}

	public void setAssessmentReport(SummativeAssessmentReport assessmentReport) {
		this.assessmentReport = assessmentReport;
	}

	public SummativeAssessmentReportUnitStandards getSummativeAssessmentReportUnitStandards() {
		return summativeAssessmentReportUnitStandards;
	}

	public void setSummativeAssessmentReportUnitStandards(SummativeAssessmentReportUnitStandards summativeAssessmentReportUnitStandards) {
		this.summativeAssessmentReportUnitStandards = summativeAssessmentReportUnitStandards;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isShowList() {
		return showList;
	}

	public void setShowList(boolean showList) {
		this.showList = showList;
	}

	public List<TrainingProviderVerfication> getTrainingproviderverficationList() {
		return trainingproviderverficationList;
	}

	public void setTrainingproviderverficationList(List<TrainingProviderVerfication> trainingproviderverficationList) {
		this.trainingproviderverficationList = trainingproviderverficationList;
	}

	public LazyDataModel<TrainingProviderVerfication> getDataModelGenerated() {
		return dataModelGenerated;
	}

	public void setDataModelGenerated(LazyDataModel<TrainingProviderVerfication> dataModelGenerated) {
		this.dataModelGenerated = dataModelGenerated;
	}
}
