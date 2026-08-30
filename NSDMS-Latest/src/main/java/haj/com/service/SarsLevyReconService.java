package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import haj.com.bean.ExcelSheetBean;
import haj.com.bean.IngterSetaTransferBean;
import haj.com.bean.ReconSchemeYears;
import haj.com.bean.SarsLevyRecon;
import haj.com.dao.SarsLevyDetailsDAO;
import haj.com.dao.TS2DAO;
import haj.com.entity.datatakeon.TS2;
import haj.com.framework.AbstractService;
import haj.com.sars.SARSConstants;
import haj.com.sars.SarsLevyDetails;
import haj.com.utils.GenericUtility;


public class SarsLevyReconService extends AbstractService {

      private SarsLevyDetailsDAO sarsLevyDetailsDAO = new SarsLevyDetailsDAO();
      private TS2DAO ts2DAO = new TS2DAO();
      private SarsFilesService sarsFilesService = new SarsFilesService();
      private SendMail sendMail = new SendMail();
      private HistoricalIntersetaTransfersService historicalIntersetaTransfersService = new HistoricalIntersetaTransfersService();

  	  public SarsLevyDetails levyForSchemeYear(Integer setaSchemeYear, Integer sarsSchemeYear) throws Exception {
  		  return sarsLevyDetailsDAO.levyForSchemeYear(setaSchemeYear, sarsSchemeYear);
  	  }

  	  public TS2 invoiceTotalForSchemeYear(Integer setaSchemeYear) throws Exception {
  		  return ts2DAO.invoiceTotalForSchemeYear(setaSchemeYear);
  	  }

  	  public List<SarsLevyDetails> levyReconPerSchemeYear() throws Exception  {
  		List<SarsLevyDetails> l = new ArrayList<SarsLevyDetails>();
		List<ReconSchemeYears> sYears = sarsFilesService.schemeYears();
		for (ReconSchemeYears rYear : sYears) {

			SarsLevyDetails sarsLevyDetails = levyForSchemeYear(rYear.getYear()+1,rYear.getYear());//dao.monthSummary(rYear.getYear(), rYear.getBeforeDate(), refNo);
			sarsLevyDetails.setSchemeYear(rYear.getYear());
			sarsLevyDetails.setTs2(invoiceTotalForSchemeYear(rYear.getYear()+1));
			  if (sarsLevyDetails.getTs2() !=null && sarsLevyDetails.getMandatoryLevy() !=null && sarsLevyDetails.getTs2().getDocumentAmountD()!=null) {
				  SarsLevyDetails x = (SarsLevyDetails)sarsLevyDetails.clone();
			      double tot =( x.getMandatoryLevy().doubleValue() *-1.0) - x.getTs2().getDocumentAmountD().doubleValue();
			      sarsLevyDetails.getTs2().setCurrentTrxAmountD(tot);
			  }

			l.add(sarsLevyDetails);

		}
  		return l;
  	  }



  	  public SarsLevyDetails levyReconPerSchemeYear(Integer setaSchemeYear, Integer sarsSchemeYear) throws Exception  {

			SarsLevyDetails sarsLevyDetails = levyForSchemeYear(setaSchemeYear, sarsSchemeYear);//dao.monthSummary(rYear.getYear(), rYear.getBeforeDate(), refNo);
			sarsLevyDetails.setSchemeYear(sarsSchemeYear);
			sarsLevyDetails.setTs2(invoiceTotalForSchemeYear(setaSchemeYear));
			  if (sarsLevyDetails.getTs2() !=null && sarsLevyDetails.getMandatoryLevy() !=null && sarsLevyDetails.getTs2().getDocumentAmountD()!=null) {
				  SarsLevyDetails x = (SarsLevyDetails)sarsLevyDetails.clone();
			      double tot =( x.getMandatoryLevy().doubleValue() *-1.0) - x.getTs2().getDocumentAmountD().doubleValue();
			      sarsLevyDetails.getTs2().setCurrentTrxAmountD(tot);
			  }

  		return sarsLevyDetails;
  	  }


  	public List<SarsLevyRecon> levyForSchemeYearPerSDL(Integer setaSchemeYear, Integer sarsSchemeYear)  throws Exception {
  		return sarsLevyDetailsDAO.levyForSchemeYearPerSDL(setaSchemeYear, sarsSchemeYear);
  	}

  	public List<SarsLevyRecon> invoiceTotalPerSdlForSchemeYear(Integer setaSchemeYear) throws Exception {
  		return ts2DAO.invoiceTotalPerSdlForSchemeYear(setaSchemeYear);
  	}


  	 public byte[] levyReconPerSchemeYearPerSDL(Integer sarsSchemeYear) throws Exception  {
  		SarsLevyRecon recon = new SarsLevyRecon();

  		recon.setLevyList(levyForSchemeYearPerSDL(sarsSchemeYear+1,sarsSchemeYear));
  		recon.setInvList(invoiceTotalPerSdlForSchemeYear(sarsSchemeYear+1));



  			for (SarsLevyRecon levies : recon.getLevyList()) {
				levies.setSarsAmount(GenericUtility.roundToPrecision(  levies.getTamount(),2));
				int inx =recon.getInvList().indexOf(levies);
				if (inx >-1) {
					levies.setSetaAmount(GenericUtility.roundToPrecision(  recon.getInvList().get(inx).getTamount(),2));
				}
				doDiffs(levies);


  		}


  			List<ExcelSheetBean> excelSheets = new ArrayList<ExcelSheetBean>();
  			excelSheets.add(new ExcelSheetBean(recon.getLevyList(),"Levy Recon", "Levy recon per SDL number for scheme year "+(sarsSchemeYear+1)));
  			byte[] spreadsheet = CreateCsvExcel.createExcel(excelSheets);
  			//FileUtils.writeByteArrayToFile(new File("/Users/hendrik/Desktop/temp.xlsx"), spreadsheet);
  		return spreadsheet;
  	 }

	private void doDiffs(SarsLevyRecon levies) {
		levies.setDiff(
				GenericUtility.roundToPrecision(
				   (levies.getSarsAmount()==null?0.0:levies.getSarsAmount()) + (levies.getSetaAmount()==null?0.0:levies.getSetaAmount())
				   ,2)
				);
	}


	public SarsLevyRecon exceptionReport(Integer sarsSchemeYear)  throws Exception {
		SarsLevyRecon er = new SarsLevyRecon();
		er.setLevyList(checkInvalidLevyNumbers(approvedPaymentsThatHasNotBeenPaid(sarsSchemeYear+1)));
		er.setInvList(paymentsThatHasNoMandatoryLevy(sarsSchemeYear+1));
		return er;
/*		List<ExcelSheetBean> excelSheets = new ArrayList<ExcelSheetBean>();
		excelSheets.add(new ExcelSheetBean(er.getLevyList(),"MG no Payment", "Approved MG that has not been paid for scheme year "+(sarsSchemeYear+1)));
		excelSheets.add(new ExcelSheetBean(er.getInvList(),"Payments no MG", "Payments that has no approved MG for scheme year "+(sarsSchemeYear+1)));
		byte[] spreadsheet = CreateCsvExcel.createExcel(excelSheets);
		return spreadsheet;
*/
	}

	private List<SarsLevyRecon> checkInvalidLevyNumbers(List<SarsLevyRecon> l) throws Exception {
		for (SarsLevyRecon sarsLevyRecon : l) {
			if (sarsLevyDetailsDAO.levyNumberExist(sarsLevyRecon.getSdlnumber())) {
				sarsLevyRecon.setCss(null);
			}
			else {
				sarsLevyRecon.setCss("redRow");
			}

		}
		return l;
	}

	public List<SarsLevyRecon> paymentsThatHasNoMandatoryLevy(Integer setaSchemeYear)  throws Exception {
		 return sarsLevyDetailsDAO.paymentsThatHasNoMandatoryLevy(setaSchemeYear);
	}

	public List<SarsLevyRecon> approvedPaymentsThatHasNotBeenPaid(Integer setaSchemeYear)  throws Exception {
		return sarsLevyDetailsDAO.approvedPaymentsThatHasNotBeenPaid(setaSchemeYear);
	}

	public SarsLevyDetails levyForPeriod(Date fromDate, Date toDate,Date fromDateInv, Date toDateInv,  Integer sarsSchemeYear, Integer setaReconSchemeYears)  throws Exception {
		return sarsLevyDetailsDAO.levyForPeriod(fromDate, toDate, fromDateInv, toDateInv,sarsSchemeYear,setaReconSchemeYears);
	}

	public ReconSchemeYears levyForPeriod(Date fromDate, Date toDate,Date fromDateInv, Date toDateInv,  Integer sarsSchemeYear, Integer setaReconSchemeYears, boolean populatebean)  throws Exception {
		ReconSchemeYears reconSchemeYear = new ReconSchemeYears();
		reconSchemeYear.setYear(setaReconSchemeYears);
		reconSchemeYear.setSarsLevyDetails(sarsLevyDetailsDAO.levyForPeriod(fromDate, toDate, fromDateInv, toDateInv,sarsSchemeYear,setaReconSchemeYears));
		reconSchemeYear.setTs2(invoiceTotalForPeriod(fromDateInv, toDateInv,setaReconSchemeYears));
		reconSchemeYear.setDifference(0.0);
		processIntersetaTransfers(reconSchemeYear,setaReconSchemeYears);
		if (reconSchemeYear.getSarsLevyDetails() !=null && reconSchemeYear.getSarsLevyDetails().getMandatoryLevyD()!=null && reconSchemeYear.getTs2()!=null && reconSchemeYear.getTs2().getDocumentAmountD()!=null) {
			if (reconSchemeYear.getSarsLevyDetails().getMandatoryLevyD().doubleValue() < 0)  reconSchemeYear.getSarsLevyDetails().setMandatoryLevyD(reconSchemeYear.getSarsLevyDetails().getMandatoryLevyD()*-1);
			if (reconSchemeYear.getTs2().getDocumentAmountD().doubleValue() < 0)  reconSchemeYear.getTs2().setDocumentAmountD(reconSchemeYear.getTs2().getDocumentAmountD()*-1);

			if (reconSchemeYear.getSarsLevyDetails().getMandatoryLevyD().doubleValue() > reconSchemeYear.getTs2().getDocumentAmountD().doubleValue()) {
				reconSchemeYear.setDifference( reconSchemeYear.getSarsLevyDetails().getMandatoryLevyD().doubleValue() - reconSchemeYear.getTs2().getDocumentAmountD().doubleValue());
			}
			else {
				reconSchemeYear.setDifference( reconSchemeYear.getTs2().getDocumentAmountD().doubleValue() - reconSchemeYear.getSarsLevyDetails().getMandatoryLevyD().doubleValue());
			}

		}

		return reconSchemeYear;
	}


	private void processIntersetaTransfers(ReconSchemeYears reconSchemeYear, Integer setaReconSchemeYears) {
		try {
			 List<IngterSetaTransferBean> l =	historicalIntersetaTransfersService.mandatoryBySchemeYear(setaReconSchemeYears);
			 for (IngterSetaTransferBean is : l) {
				if ("Transfer In".equalsIgnoreCase(is.getDescription().trim())) {
					reconSchemeYear.setTransferIn(is.getAmount());
				}
				else {
					reconSchemeYear.setTransferOut(is.getAmount());
				}
			}
		} catch (Exception e) {
			logger.fatal(e);
		}

	}

	public List<ReconSchemeYears>  levyOverPeriod(Date fromDate, Date toDate, Date fromDateInv, Date toDateInv) throws Exception {
		List<ReconSchemeYears> l = new ArrayList<ReconSchemeYears>();
		for (int i = 2000; i < 2017; i++) {
			l.add(levyForPeriod(fromDate, toDate, fromDateInv, toDateInv, i, (i+1), true));
 		}
		return l;
	}



	public TS2 invoiceTotalForPeriod(Date fromDateInv, Date toDateInv, Integer setaSchemeYear ) throws Exception {
		return ts2DAO.invoiceTotalForPeriod(fromDateInv, toDateInv,setaSchemeYear);
	}

	public Date maxPostDate() throws Exception {
		return ts2DAO.maxPostDate();
	}

	 public void ts2ForPeriod(Date fromDate, Date toDate, Date fromDateInv, Date toDateInv, String email,Integer sarsSchemeYear,Integer setaSchemeYear) throws Exception  {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					 logger.info("ts2ForPeriod START");
					 ts2ForPeriod(fromDate, toDate, fromDateInv, toDateInv, sarsSchemeYear, setaSchemeYear,email);
					 logger.info("ts2ForPeriod END");
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	 }

 	 public byte[] ts2ForPeriod(Date fromDate, Date toDate, Date fromDateInv, Date toDateInv,Integer sarsSchemeYear,Integer setaSchemeYear,String email) throws Exception  {
 //		    List<SarsMandatoryLevyDetails> l0 = sarsLevyDetailsDAO.findDetailByPeriod(fromDate, toDate, fromDateInv, toDateInv,sarsSchemeYear,setaSchemeYear);
 //		    List<TS2> l = ts2DAO.findByPeriod(fromDateInv, toDateInv,setaSchemeYear);

 		    List<ExcelSheetBean> excelSheets = new ArrayList<ExcelSheetBean>();
 	//	   List<SarsLevyRecon> l0 = processAndfilter(fromDate, toDate, fromDateInv, toDateInv, sarsSchemeYear, setaSchemeYear);

 			logger.info("Start creation of excelSheets");
 			for (int i = 2000; i < 2017; i++) {
 	 			excelSheets.add(new ExcelSheetBean(processAndfilter(fromDate, toDate, fromDateInv, toDateInv, i, (i+1)),"Recon "+(i+1), "SARS Levies from  "+SARSConstants.dd_MMMM_yyyy.format(fromDate) + " to " + SARSConstants.dd_MMMM_yyyy.format(toDate)+" Scheme year: "+(i+1)));
 			}
 			logger.info("Start creation of SarsMandatoryLevyDetails.xlsx");
 			byte[] spreadsheet = CreateCsvExcel.createExcel(excelSheets);
 			logger.info("Done creation of SarsMandatoryLevyDetails.xlsx");
 		//	FileUtils.writeByteArrayToFile(new File("/Users/hendrik/Desktop/SarsMandatoryLevyDetails.xlsx"), spreadsheet);

 /*

 			excelSheets.add(new ExcelSheetBean(l0,"SARS Levies", "SARS Levies from  "+SARSConstants.dd_MMMM_yyyy.format(fromDate) + " to " + SARSConstants.dd_MMMM_yyyy.format(toDate)+" SARS scheme year: "+sarsSchemeYear));
 			logger.info("Start creation of SarsMandatoryLevyDetails.xlsx");
 			byte[] spreadsheet = CreateCsvExcel.createExcel(excelSheets);
 			//FileUtils.writeByteArrayToFile(new File("/Users/hendrik/Desktop/SarsMandatoryLevyDetails.xlsx"), spreadsheet);
 			logger.info("Done creation of SarsMandatoryLevyDetails.xlsx");
 			logger.info("Start sendMail");
 			sendMail.sendMailCommonsWithAttachement(email, "SARS / GP details", "Please find attached the requested reports", spreadsheet, "LevyRecon.xlsx", "xlsx", null);
			logger.info("End sendMail");
 */

 /*          List<AttachmentBean> files =  new  ArrayList<AttachmentBean>();
            logger.info("Start creation of SarsMandatoryLevyDetails.csv");
            files.add(new AttachmentBean("SarsMandatoryLevyDetails.csv", CreateCsvExcel.writeCsv(l0),"csv"));
            logger.info("Start creation of PostingDetails.csv");
            files.add(new AttachmentBean("PostingDetails.csv", CreateCsvExcel.writeCsv(l),"csv"));
         	//FileUtils.writeByteArrayToFile(new File("/Users/hendrik/Desktop/SarsMandatoryLevyDetails.csv"), b);
            logger.info("Start sendMail");
         	sendMail.mailWithAttachement(email, "SARS / GP details", "Please find attached the requested reports", files, null);
         	logger.info("End sendMail");
*/
         	return spreadsheet;

 	 }


 	private List<SarsLevyRecon> processAndfilter(Date fromDate, Date toDate, Date fromDateInv, Date toDateInv, Integer sarsSchemeYear, Integer setaSchemeYear) throws Exception{
	    List<SarsLevyRecon> l0 = sarsLevyDetailsDAO.levyDeviationForPeriod(fromDate, toDate, fromDateInv, toDateInv, sarsSchemeYear, setaSchemeYear, "%");
		    l0.removeIf(a-> {
	 			if (a.getDifference().doubleValue() > -11.0 && a.getDifference().doubleValue() <= 11.0) return true;
	 			return false;
	 		});
		return l0;
	}

	public List<SarsLevyRecon> levyDeviationForPeriod(Date fromDate, Date toDate,Date fromDateInv, Date toDateInv, Integer sarsSchemeYear, Integer setaSchemeYear, int startingAt, int maxPerPage, String sdlNumber)  throws Exception {
 		return applyTollerance( sarsLevyDetailsDAO.levyDeviationForPeriod(fromDate, toDate, fromDateInv, toDateInv, sarsSchemeYear, setaSchemeYear, startingAt, maxPerPage,sdlNumber));
 	}

 	private List<SarsLevyRecon> applyTollerance(List<SarsLevyRecon> levyDeviationForPeriod) {
 		logger.info("Before: "+levyDeviationForPeriod.size());
 		levyDeviationForPeriod.removeIf(a-> {
 			if (a.getDifference().doubleValue() > -11.0 && a.getDifference().doubleValue() <= 11.0) return true;
 			return false;
 		});

 		logger.info("After: "+levyDeviationForPeriod.size());
		return levyDeviationForPeriod;
	}

	public Integer countLevyDeviationForPeriod(Date fromDate, Date toDate,Date fromDateInv, Date toDateInv, Integer sarsSchemeYear, Integer setaSchemeYear, String sdlNumber)  throws Exception {
 	  return sarsLevyDetailsDAO.countLevyDeviationForPeriod(fromDate, toDate, fromDateInv, toDateInv, sarsSchemeYear, setaSchemeYear,sdlNumber);
 	}


}
