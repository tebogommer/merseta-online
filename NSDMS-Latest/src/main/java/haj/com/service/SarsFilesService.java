package haj.com.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.LeviesPaidBean;
import haj.com.bean.MgTransactionsBean;
import haj.com.bean.ReconSchemeYears;
import haj.com.bean.SchemeYearBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.SarsEmployerDetailDAO;
import haj.com.dao.SarsFilesDAO;
import haj.com.entity.Users;
import haj.com.entity.datatakeon.TS2;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.sars.SarsEmployerDetail;
import haj.com.sars.SarsFiles;
import haj.com.sars.SarsLevyDetails;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class SarsFilesService.
 */
public class SarsFilesService extends AbstractService {
	/** The dao. */
	private SarsFilesDAO dao = new SarsFilesDAO();
	private SarsEmployerDetailDAO sarsEmployerDetailDAO = new SarsEmployerDetailDAO();
	private SarsLevyDetailsService sarsLevyDetailsService =new SarsLevyDetailsService();
	/** The sars levies paid service. */
	private SarsLeviesPaidService sarsLeviesPaidService  = new SarsLeviesPaidService() ;
	private SchemeYearService schemeYearService = new SchemeYearService();
	private HistoricalIntersetaTransfersService historicalIntersetaTransfersService  = new HistoricalIntersetaTransfersService();

	/**
	 * Get all SarsFiles.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SarsFiles}
	 * @throws Exception the exception
	 * @see   SarsFiles
	 */
	public List<SarsFiles> allSarsFiles() throws Exception {
	  	return dao.allSarsFiles();
	}


	/**
	 * Create or update SarsFiles.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SarsFiles
	 */
	public void create(SarsFiles entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}

	/**
	 * Creates the.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public void create(IDataEntity entity) throws Exception  {
			 this.dao.create(entity);
		}

	/**
	 * Find by date.
	 *
	 * @param forMonth the for month
	 * @return the sars files
	 * @throws Exception the exception
	 */
	public SarsFiles findByDate(Date forMonth) throws Exception {
		return dao.findByDate(forMonth);
	}

	/**
	 * Update  SarsFiles.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsFiles
	 */
	public void update(IDataEntity entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SarsFiles.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SarsFiles
	 */
	public void delete(IDataEntity entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SarsFiles}
	 * @throws Exception the exception
	 * @see    SarsFiles
	 */
	public SarsFiles findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SarsFiles by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SarsFiles}
	 * @throws Exception the exception
	 * @see    SarsFiles
	 */
	public List<SarsFiles> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SarsFiles.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SarsFiles}
	 * @throws Exception the exception
	 */
	public List<SarsFiles> allSarsFiles(int first, int pageSize) throws Exception {
		return dao.allSarsFiles(Long.valueOf(first), pageSize);
	}


	/**
	 * Get count of SarsFiles for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SarsFiles
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SarsFiles.class);
	}

    /**
     * Lazy load SarsFiles with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 SarsFiles class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SarsFiles} containing data
     * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	public List<SarsFiles> allSarsFiles(Class<SarsFiles> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return doSummary((List<SarsFiles>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));
	}
	
	@SuppressWarnings("unchecked")
	public List<SarsFiles> allSarsFilesNoSummary(Class<SarsFiles> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SarsFiles>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
	public Integer countSarsFilesWhereForMonthBetweenDates(Date fromDate, Date toDate) throws Exception {
		return dao.countSarsFilesWhereForMonthBetweenDates(fromDate, toDate);
	}


	public LeviesPaidBean summaryBySchemeYear(Integer schemeYear) throws Exception  {
		LeviesPaidBean lb = new LeviesPaidBean();
		lb.setSchemeYear(schemeYear);
		SchemeYearBean syb = schemeYearService.setaSchemeYear(schemeYear);
		 List<SarsFiles> l = doSummary(dao.findBetweemDates(syb.getStartDate(), syb.getEndDate()));
		 lb.setDetail(l);
		 BigDecimal levy = BigDecimal.valueOf(0.0);
		 BigDecimal cb = BigDecimal.valueOf(0.0);
		 for (SarsFiles sf : l) {
			if (sf.getLevySummary()!=null && sf.getLevySummary().getTotal()!=null) {
				levy = BigDecimal.valueOf(levy.doubleValue() +
						( sf.getLevySummary().getTotal().doubleValue()<0?(sf.getLevySummary().getTotal().doubleValue() *-1.0):sf.getLevySummary().getTotal().doubleValue())
			           );
			}
			if (sf.getSarsLeviesPaid() !=null && sf.getSarsLeviesPaid().getCheckbookAmount()!=null) {
				cb =  BigDecimal.valueOf(cb.doubleValue() +
						sf.getSarsLeviesPaid().getCheckbookAmount().doubleValue()
						);
			}

		 }
		 lb.setTotLevy(levy);
		 lb.setTotCBAmount(cb);
		 if (cb.doubleValue()>= levy.doubleValue()) {
			 lb.setDiff(GenericUtility.roundToPrecision( BigDecimal.valueOf(
					 cb.doubleValue() - levy.doubleValue()
					 ),2));
		 }
		 else {
			 lb.setDiff(GenericUtility.roundToPrecision(BigDecimal.valueOf(
					  levy.doubleValue()-cb.doubleValue()
					 ),2));
		 }


		 return lb;
	}

    /**
     * Do summary.
     *
     * @param list the list
     * @return the list
     * @throws Exception the exception
     */
    private List<SarsFiles> doSummary(List<SarsFiles> list) throws Exception {
		for (SarsFiles s : list) {
			s.setLevySummary(sarsLevyDetailsService.fixSign(dao.monthySummary(s.getId())));
//			s.setLevySummary(dao.monthySummary(s.getId()));

			s.setSarsLeviesPaid(sarsLeviesPaidService.findBySarsLevyFile(s.getForMonth()));
			try {
				if (s.getSarsLeviesPaid()!=null && s.getSarsLeviesPaid().getCheckbookAmount()!=null && s.getLevySummary().getTotal()!=null) {
					double tot = s.getLevySummary().getTotal().doubleValue()<0?(s.getLevySummary().getTotal().doubleValue()*-1.0):s.getLevySummary().getTotal().doubleValue();
					s.setDiscreptancy(GenericUtility.roundToPrecision(java.math.BigDecimal.valueOf(s.getSarsLeviesPaid().getCheckbookAmount().doubleValue()-tot),2));
					if (s.getDiscreptancy().doubleValue() < 0.0) {
						double t = s.getDiscreptancy().doubleValue() * -1.0;
						if (t == 0.0) {
							s.setDiscreptancy(java.math.BigDecimal.valueOf(0.00));
						}
					}

				}
			} catch (Exception e) {
				logger.fatal(e);
			}
		}
		return list;
	}


	/**
	 * Get count of SarsFiles for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity SarsFiles class
	 * @param filters the filters
	 * @return Number of rows in the SarsFiles entity
	 * @throws Exception the exception
	 */
	public int count(Class<SarsFiles> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	/**
	 * Gets the dao.
	 *
	 * @return the dao
	 */
	public SarsFilesDAO getDao() {
		return dao;
	}

	/**
	 * Sdl summary.
	 *
	 * @param refNo the ref no
	 * @return the list
	 */
	public List<SarsLevyDetails> sdlSummary(String refNo) {
		return dao.sdlSummary(refNo);
	}

	/**
	 * Sdl summary by ref and date.
	 *
	 * @param refNo the ref no
	 * @param fromDate the from date
	 * @param toDate the to date
	 * @return the list
	 */
	public List<SarsLevyDetails> sdlSummaryByRefAndDate(String refNo, Date fromDate, Date toDate) {
		return sarsLevyDetailsService.fixSign(dao.sdlSummaryByRefAndDate(refNo, fromDate, toDate));
	}

	/**
	 * Recon levy summary.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<SarsLevyDetails> reconLevySummary() throws Exception {

		/*		 List<SarsLevyDetails> l = dao.reconLevySummary();
		 for (SarsLevyDetails sarsLevyDetails : l) {
		      sarsLevyDetails.setTs2(reconInvoiceSummary(sarsLevyDetails.getArrivalDate1()));

			  if (sarsLevyDetails.getTs2() !=null && sarsLevyDetails.getMandatoryLevy() !=null && sarsLevyDetails.getTs2().getDocumentAmountD()!=null) {
				  SarsLevyDetails x = (SarsLevyDetails)sarsLevyDetails.clone();
			      double tot =( x.getMandatoryLevy().doubleValue() *-1.0) - x.getTs2().getDocumentAmountD().doubleValue();
			      sarsLevyDetails.getTs2().setCurrentTrxAmountD(tot);
			  }
			  processEmployerTotalsAsync(sarsLevyDetails);
		}
		*/

			List<SarsLevyDetails> l =  new ArrayList<SarsLevyDetails>();
			List<ReconSchemeYears> sYears = schemeYears();
			for (ReconSchemeYears rYear : sYears) {
				SarsLevyDetails sarsLevyDetails = dao.monthSummary(rYear.getYear());
				sarsLevyDetails.setSchemeYear(rYear.getYear());
				sarsLevyDetails.setTs2(dao.gpMonthSummary(rYear.getYear()+1));
				  if (sarsLevyDetails.getTs2() !=null && sarsLevyDetails.getMandatoryLevy() !=null && sarsLevyDetails.getTs2().getDocumentAmountD()!=null) {
					  SarsLevyDetails x = (SarsLevyDetails)sarsLevyDetails.clone();
				      double tot =( x.getMandatoryLevy().doubleValue() *-1.0) - x.getTs2().getDocumentAmountD().doubleValue();
				      sarsLevyDetails.getTs2().setCurrentTrxAmountD(tot);
				  }


				l.add(sarsLevyDetails);
				// processEmployerTotalsAsync(sarsLevyDetails);
			}

		 return l;
	}

	private TS2 reconInvoiceSummary(Date arrivalDate1) throws Exception {
		if (arrivalDate1 != null) {
		 Date t = GenericUtility.addMonthsToDate(arrivalDate1, 3);
		 return dao.reconInvoiceSummary(GenericUtility.yearOfDate(t), GenericUtility.monthOfDate(t));
		}
		else return null;

	}


	public void processEmployerTotalsAsync(SarsLevyDetails sarsLevyDetails) {

	new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				if (sarsLevyDetails!=null && sarsLevyDetails.getSchemeYear() !=null) {

					sarsLevyDetails.setNumberEmployersInSARSLevyFiles(dao.numberEmployersInSARSLevyFiles(sarsLevyDetails.getSchemeYear()));
					sarsLevyDetails.setNumberEmployersInSETAInvoiceFile(dao.numberEmployersInSETAInvoiceFile(sarsLevyDetails.getSchemeYear()));


					List<SarsEmployerDetail> el = new ArrayList<SarsEmployerDetail>();
					String status = "A,B,S,X,Y,D,R";
					for (String s : status.split(",")) {
						SarsEmployerDetail t = dao.numberEmployersInSARSEmployerFilesByStatus(sarsLevyDetails.getSchemeYear(), s);
						if (t != null) {
							t.setTradingStatus(s);
							el.add(t);
						}
					}

					//sarsLevyDetails.setNumberEmployersInSARSEmployerFiles(dao.numberEmployersInSARSEmployerFiles(sarsLevyDetails.getSchemeYear()));
					sarsLevyDetails.setNumberEmployersInSARSEmployerFiles(0l);
					sarsLevyDetails.setEmployerSummary(el);
					for (SarsEmployerDetail se : sarsLevyDetails.getEmployerSummary()) {
						se.setId(1l);
						sarsLevyDetails.setNumberEmployersInSARSEmployerFiles(sarsLevyDetails.getNumberEmployersInSARSEmployerFiles() +  (se.getNoEmployes()==null?0l:se.getNoEmployes())    );
					}

				}
			} catch (Exception e) {
				logger.fatal(e);
			}
		}
	}).start();
}


	public void processEmployerTotals(SarsLevyDetails sarsLevyDetails) throws Exception {
		if (sarsLevyDetails!=null && sarsLevyDetails.getSchemeYear() !=null) {

			sarsLevyDetails.setNumberEmployersInSARSLevyFiles(dao.numberEmployersInSARSLevyFiles(sarsLevyDetails.getSchemeYear()));
			sarsLevyDetails.setNumberEmployersInSETAInvoiceFile(dao.numberEmployersInSETAInvoiceFile(sarsLevyDetails.getSchemeYear()));


			List<SarsEmployerDetail> el = new ArrayList<SarsEmployerDetail>();
			String status = "A,B,S,X,Y,D,R";
			for (String s : status.split(",")) {
				SarsEmployerDetail t = dao.numberEmployersInSARSEmployerFilesByStatus(sarsLevyDetails.getSchemeYear(), s);
				if (t != null) {
					t.setTradingStatus(s);
					el.add(t);
				}
			}

			//sarsLevyDetails.setNumberEmployersInSARSEmployerFiles(dao.numberEmployersInSARSEmployerFiles(sarsLevyDetails.getSchemeYear()));
			sarsLevyDetails.setNumberEmployersInSARSEmployerFiles(0l);
			sarsLevyDetails.setEmployerSummary(el);
			for (SarsEmployerDetail se : sarsLevyDetails.getEmployerSummary()) {
				se.setId(1l);
				sarsLevyDetails.setNumberEmployersInSARSEmployerFiles(sarsLevyDetails.getNumberEmployersInSARSEmployerFiles() +  (se.getNoEmployes()==null?0l:se.getNoEmployes())    );
			}

		}
	}




	/**
	 * Recon levy summary.
	 *
	 * @param refNo the ref no
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<SarsLevyDetails> reconLevySummary(String refNo) throws Exception {

		List<SarsLevyDetails> l =  new ArrayList<SarsLevyDetails>();
		List<ReconSchemeYears> sYears = schemeYears();
		for (ReconSchemeYears rYear : sYears) {
			SarsLevyDetails sarsLevyDetails = dao.monthSummary(rYear.getYear(), rYear.getBeforeDate(), refNo);

			sarsLevyDetails.setSchemeYear(rYear.getYear());
			sarsLevyDetails.setTs2(dao.gpMonthSummary(rYear.getYear()+1,  refNo));
			sarsLevyDetails.setInterSetaTransfer(historicalIntersetaTransfersService.totalMandatoryPerSchemeYearAndLNumber(rYear.getYear()+1, refNo));
			  if (sarsLevyDetails.getTs2() !=null && sarsLevyDetails.getMandatoryLevy() !=null && sarsLevyDetails.getTs2().getDocumentAmountD()!=null) {
				//	sarsLevyDetails.setMandatoryLevy(sarsLevyDetails.getMandatoryLevy().doubleValue() <0 ? BigDecimal.valueOf(sarsLevyDetails.getMandatoryLevy().doubleValue()*-1.0  ):sarsLevyDetails.getMandatoryLevy()  );
				  SarsLevyDetails x = (SarsLevyDetails)sarsLevyDetails.clone();
				  Double iseta = x.getInterSetaTransfer()==null?0.0 : x.getInterSetaTransfer()  ;
				  if (iseta>0) iseta = iseta.doubleValue() * -1.0;
			    //  System.out.println(x.getMandatoryLevy().doubleValue() + " \t" + (iseta*-1.0) + "\t" + (x.getMandatoryLevy().doubleValue() + (iseta*-1.0))  );
				  double tot =(( x.getMandatoryLevy().doubleValue() +  iseta.doubleValue()   ) *-1.0)   - x.getTs2().getDocumentAmountD().doubleValue();
			      sarsLevyDetails.getTs2().setCurrentTrxAmountD(tot);
			   }
			  else if (sarsLevyDetails.getTs2().getDocumentAmountD()!=null && sarsLevyDetails.getInterSetaTransfer() !=null) {
				  double tot =( sarsLevyDetails.getInterSetaTransfer().doubleValue()      - sarsLevyDetails.getTs2().getDocumentAmountD().doubleValue());
				  sarsLevyDetails.getTs2().setCurrentTrxAmountD(tot);
			  }

			l.add(sarsLevyDetails);
		}

	/*	List<SarsLevyDetails> l = dao.reconLevySummary(refNo);
		 for (SarsLevyDetails sarsLevyDetails : l) {

			  sarsLevyDetails.setTs2(reconInvoiceSummary(sarsLevyDetails.getArrivalDate1(),refNo));

			  if (sarsLevyDetails.getTs2() !=null && sarsLevyDetails.getMandatoryLevy() !=null && sarsLevyDetails.getTs2().getDocumentAmountD()!=null) {
				  SarsLevyDetails x = (SarsLevyDetails)sarsLevyDetails.clone();
			      double tot =( x.getMandatoryLevy().doubleValue() *-1.0) - x.getTs2().getDocumentAmountD().doubleValue();
			      sarsLevyDetails.getTs2().setCurrentTrxAmountD(tot);
			  }
		}
		*/
		 return l;

	}

	public List<ReconSchemeYears> schemeYears() throws Exception{
		List<ReconSchemeYears> sYears = new ArrayList<ReconSchemeYears>();
		List<Integer> years = dao.schemeYears();
		for (Integer year : years) {
			sYears.add(new ReconSchemeYears(year, HAJConstants.sdf.parse( (year+1)+"-02-01")));
		}
		return sYears;
	}
	
	public List<Integer> distinctSchemeYears() throws Exception{
		return dao.schemeYears();
	} 

	/**
	 * Recon invoice summary.
	 *
	 * @param schemeYear the scheme year
	 * @param vendorId the vendor id
	 * @return the ts2
	 * @throws Exception the exception
	 */
	public TS2 reconInvoiceSummary(Date arrivalDate1,String vendorId) throws Exception{
		if (arrivalDate1 ==null || vendorId==null) return null;
		Date t = GenericUtility.addMonthsToDate(arrivalDate1, 3);
	    return dao.reconInvoiceSummary(GenericUtility.yearOfDate(t), GenericUtility.monthOfDate(t),vendorId);


	}



	public List<SarsFiles> last12MonthsSarsFiles() throws Exception {
	 	return dao.last12MonthsSarsFiles();
	}

	public SarsEmployerDetail findBySDL(String refNo) throws Exception {
		return sarsEmployerDetailDAO.findSDL(refNo);
	}


	public void updateGPStatus(SarsFiles sarsfiles, Users user) throws Exception {
		sarsfiles.setLoadedToGP(Boolean.TRUE);
		sarsfiles.setLoadGPDate(new Date());
		sarsfiles.setLoadedToGpByUser(user);
		dao.update(sarsfiles);
	}

	public SarsFiles latestSarsLevyFile() throws Exception {
		return dao.latestSarsLevyFile();
	}

	public  List<SarsLevyDetails>  sdlSummaryByRefAndSchemeYearGroupByArrivalDate(String refNo, Integer schemeYear) {
		//return  sarsLevyDetailsService.fixSign( dao.sdlSummaryByRefAndSchemeYearGroupByArrivalDate(refNo, schemeYear));
		return  dao.sdlSummaryByRefAndSchemeYearGroupByArrivalDate(refNo, schemeYear);
	}

	public SarsLevyDetails sdlSummaryByRefAndSchemeYear(String refNo, Integer schemeYear) {
		return sarsLevyDetailsService.fixSign(dao.sdlSummaryByRefAndSchemeYear(refNo, schemeYear));
	}
	
	public BigDecimal returnDiscretionaryLevyByRefNoAndYear(String refNo, Integer schemeYear) throws Exception{
		SarsLevyDetails sarsLevyDetails = sdlSummaryByRefAndSchemeYear(refNo, schemeYear);
		return sarsLevyDetails.getDiscretionaryLevy();
	}

	public double getPercentageCalculatedForDeviationDoubleValue(String refNo, Integer schemeYear) {
		double percentageCalculatedForDeviationDoubleValue = 0.0;
		SarsLevyDetails sarsLevyDetails = sdlSummaryByRefAndSchemeYear(refNo, schemeYear);		
		if (sarsLevyDetails != null) {
			if (sarsLevyDetails.getDiscretionaryLevy() != null) {
				percentageCalculatedForDeviationDoubleValue = sarsLevyDetails.getDiscretionaryLevy().doubleValue();
			}
		}		
		return percentageCalculatedForDeviationDoubleValue;
	}
	
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedAndNotInBatchForMGTransfer(Date fromDate, Date toDate) {
		return dao.sarsLevyDetailBetweenDatesProvidedWspApprovedAndNotInBatchForMGTransfer(fromDate, toDate);
	}
	
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFile(Date fromDate, Date toDate)throws Exception {
		return dao.sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFile(fromDate, toDate);
	}
	
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFileWithMgCheck(Date fromDate, Date toDate) throws Exception{
		return dao.sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFileWithMgCheck(fromDate, toDate);
	}
	
	public List<SarsLevyDetails> sarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFileWithMgCheck() throws Exception{
		return dao.sarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFileWithMgCheck();
	}
	
	public Integer countSarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFileWithMgCheck() throws Exception {
		return dao.countSarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferAndCanProcessForSarsFileWithMgCheck();
	}
	
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFile(Date fromDate, Date toDate, Integer schemeYear) throws Exception{
		return dao.sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFile(fromDate, toDate, schemeYear);
	}
	
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFileWithCheck(Date fromDate, Date toDate, Integer schemeYear) throws Exception{
		return dao.sarsLevyDetailBetweenDatesProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFileWithCheck(fromDate, toDate, schemeYear);
	}
	
	public List<SarsLevyDetails> sarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFileWithCheck(Integer schemeYear) throws Exception{
		return dao.sarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFileWithCheck(schemeYear);
	}
	
	public Integer countSarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFileWithCheck(Integer schemeYear)  throws Exception{
		return dao.countSarsLevyDetailProvidedWspApprovedNotInBatchForMGTransferSchemeYearAndCanProcessForSarsFileWithCheck(schemeYear);
	}
	
	public List<SarsLevyDetails> sarsLevyDetailBetweenDatesProvidedWspApprovedAndNotInBatchForMGTransfer(Date fromDate, Date toDate, List<Integer> schemeYearList) {
		return dao.sarsLevyDetailBetweenDatesProvidedWspApprovedAndNotInBatchForMGTransfer(fromDate, toDate, schemeYearList);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<SarsFiles> sarsFilesReadyForMgTransferGroupedByRefSchemeYear(Class<SarsFiles> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Date fromDate, Date toDate, List<Integer> schemeYearList) throws Exception {
		String hql = "select new haj.com.sars.SarsLevyDetails(o.refNo, schemeYear, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.interest) , sum(o.penalty) , sum(o.total)) "
	 			+ " from SarsLevyDetails o "
	 			+ " where o.mandatoryLevy is not null "
	 			+ "	and date(o.arrivalDate1) between date(:fromDate) and date(:toDate) "
	 			+ " and EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum) ";
	    if (schemeYearList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (Integer year : schemeYearList) {
				if (count == schemeYearList.size()) {
					hql += " o.schemeYear = :schemeYear" + count;
				} else {
					hql += " o.schemeYear = :schemeYear" + count + " or ";
				}
				filters.put("schemeYear" + count, year);
				count++;
			}
			hql += " ) ";
		}
	    hql += " group by o.refNo , o.schemeYear ";
	    filters.put("fromDate", fromDate);
	    filters.put("toDate", toDate);
	    filters.put("approvalEnum", WspStatusEnum.Approved);
		return doSummary(( List<SarsFiles>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters,hql));
	}
	
	public int countSarsFilesReadyForMgTransferGroupedByRefSchemeYear(Class<SarsFiles> entity, Map<String, Object> filters, List<Integer> schemeYearList) throws Exception {
		String hql = "select count(new haj.com.sars.SarsLevyDetails(o.refNo, schemeYear, sum(o.mandatoryLevy) , sum(o.discretionaryLevy), sum(o.adminLevy), sum(o.interest) , sum(o.penalty) , sum(o.total))) "
	 			+ " from SarsLevyDetails o "
	 			+ " where o.mandatoryLevy is not null "
	 			+ "	and date(o.arrivalDate1) between date(:fromDate) and date(:toDate) "
	 			+ " and EXISTS(select x from WspApprovalView x where x.levyNumber = o.refNo and x.dhetFinYear = o.schemeYear and x.StatusEnum = :approvalEnum) ";
	    if (schemeYearList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (Integer year : schemeYearList) {
				if (count == schemeYearList.size()) {
					hql += " o.schemeYear = :schemeYear" + count;
				} else {
					hql += " o.schemeYear = :schemeYear" + count + " or ";
				}
				count++;
			}
			hql += " ) ";
		}
	    hql += " group by o.refNo , o.schemeYear ";
		return  dao.countWhere(filters, hql);
	}
	
	public List<MgTransactionsBean> countAvalaibleEntriesForProcessing() throws Exception {
		return dao.countAvalaibleEntriesForProcessing();
	}
	
	public List<MgTransactionsBean> countAvalaibleEntriesForProcessingBySchemeYear(Integer schemeYear) throws Exception {
		return dao.countAvalaibleEntriesForProcessingBySchemeYear(schemeYear);
	}
	
	public List<MgTransactionsBean> avalaibleEntriesForMgProcessing() throws Exception {
		return dao.avalaibleEntriesForMgProcessing();
	}
	
	public List<MgTransactionsBean> avalaibleEntriesForMgProcessingBySchemeYear(Integer schemeYear) throws Exception {
		return dao.avalaibleEntriesForMgProcessingBySchemeYear(schemeYear);
	}
	
}