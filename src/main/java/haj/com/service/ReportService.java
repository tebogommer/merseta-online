package haj.com.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import haj.com.annotations.ResolveData;
import haj.com.bean.BelowThresReportBean;
import haj.com.bean.ResultsBean;
import haj.com.bean.SarsChamberProvinceReportBean;
import haj.com.bean.SarsEmployerDetailBean;
import haj.com.bean.SarsProvinceCountBean;
import haj.com.bean.SchemeYearIdentifierBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.ReportDAO;
import haj.com.entity.LevyIncomeSummary;
import haj.com.entity.Users;
import haj.com.entity.enums.ReportPropertiesEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.entity.lookup.ReportGenerationProperties;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.sars.SarsFiles;
import haj.com.service.lookup.ReportGenerationPropertiesService;
import haj.com.utils.GenericUtility;
import haj.com.utils.ReflectionUtils;

public class ReportService extends AbstractService {

	/** The dao. */
	private ReportDAO dao = new ReportDAO();
	
	/** Service levels  */
	private SarsFilesService sarsFilesService = null;
	
	private List<String> alreadyVisited = new ArrayList<>();
	private String hql = "";
	private String hqlInner = "x";

	private void getWhereClause(List<String> targetClasses) throws ClassNotFoundException, Exception {
		for (String targetClass : targetClasses) {
			alreadyVisited = new ArrayList<>();
			if (targetClass != null) {
				Class<?> obj = Class.forName(targetClass);
				if (obj.isAnnotationPresent(ResolveData.class)) {

					if (hql.contains("(o.targetClass =")) {
						hql += " or";
					}
					hql += " (o.targetClass = '" + targetClass + "'";

					ResolveData annotation = (ResolveData) ReflectionUtils.getAnnotationOnType(ResolveData.class, obj);
					int count = 0;
					boolean addOr = annotation.paths().length > 1;
					for (String string : annotation.paths()) {
						if (count == 0) {
							if (addOr)
								hql += " and (o.targetKey in (";
							else
								hql += " and o.targetKey in (";
							hql += addStatement(targetClass, string, count);
						}
						else {
							hql += " or o.targetKey in (";
							hql += addStatement(targetClass, string, count);
						}
						if (addOr) {
							hql += ")";
						}
						count++;
					}
					hql += "))";

				}
			}
		}
	}

	private String addStatement(String targetClass, String string, int count) {
		String prefix = hqlInner + count;
		return "select " + prefix + ".id from " + targetClass + " " + prefix + " where " + prefix + "." + string + " = :queryParam";
	}

	private List<Field> recursiveCompanyCheck(List<Field> f, Class<?> c, String path) {
		List<Field> toReturn = new ArrayList<>();

		for (Field field : f) {
			if (path.equals("")) {
				path = "o";
			}
			try {
				if (!alreadyVisited.contains(field.getDeclaringClass().getName() + field.getName())) {
					alreadyVisited.add(field.getDeclaringClass().getName() + field.getName());
					if (field.getType().equals(c)) {
						path += "." + field.getName();
						toReturn.add(field);
						if (!path.equals("o."))
							System.out.println(path);
					}
					else if (field.getType().getPackage().equals(c.getPackage())) {
						path += "." + field.getName();
						toReturn.addAll(recursiveCompanyCheck(Arrays.asList(field.getType().getDeclaredFields()), c, path));
					}
				}
			} catch (Exception e) {
				path = "o";
				// System.out.println("FIELD NOT A CLASS");
			}
		}
		return toReturn;
	}

	public List<ByChamberReportBean> findleviesBetweenDates(Long monthId) throws Exception {
		return dao.findleviesBetweenDates(monthId);
	}

	public List<Integer> findSchemeYears() throws Exception {
		return dao.findSchemeYears();
	}

	public List<Integer> findLastestSchemeYear() throws Exception {
		return dao.findLastestSchemeYear();
	}

	public List<ByChamberReportBean> findContributingEmployersBetweenDates(SarsFiles monthId) throws Exception {
		return dao.findContributingEmployersBetweenDates(monthId);
	}

	public ByChamberReportBean findLevyContributionsBetweenDates(Date fromDate, Date toDate) throws Exception {
		return dao.findLevyContributionsBetweenDates(fromDate, toDate);
	}

	public List<ByChamberReportBean> findEmployersBySize(Date fromDate, Date toDate) throws Exception {
		return dao.findEmployersBySize(fromDate, toDate);
	}
	
	public ByChamberReportBean findLevyContributionsBySarsMonth(SarsFiles monthId ) throws Exception {
        return dao.findLevyContributionsBySarsMonth(monthId);
    }

	public List<ByChamberReportBean> findLevyIncomeByYear() throws Exception {
		return dao.findLevyIncomeByYear();
	}

	public List<ByChamberReportBean> findGrantsProjectsByYear() throws Exception {
		return dao.findGrantsProjectsByYear();
	}

	public ByChamberReportBean findSizeOfEmpoloyerContribution(Long monthId) throws Exception {
		return dao.findSizeOfEmpoloyerContribution(monthId);
	}

	public List<ByChamberReportBean> findChamberRevenueByMonth(Date fromDate, Date toDate) throws Exception {
		return dao.findChamberRevenueByMonth(fromDate, toDate);
	}
	
	public List<ByChamberReportBean> findChamberRevenueByMonthAndSchemeYear(Date fromDate, Date toDate, Integer schemeYear) throws Exception {
		return dao.findChamberRevenueByMonthAndSchemeYear(fromDate, toDate, schemeYear);
	}

    public List<ByChamberReportBean> findActiveEmployersBetweenDate(SarsFiles monthId) throws Exception {
        return dao.findActiveEmployersBetweenDate(monthId);
    }

	public List<ByChamberReportBean> reportDataEmployerAnalysisBySizeAndSisCode(Long sarsFileId) throws Exception {
		return dao.reportDataEmployerAnalysisBySizeAndSisCode(sarsFileId);
	}
	
	public List<ByChamberReportBean> reportDataContributingEmployerAnalysisBySizeAndSisCode(Long sarsFileId) throws Exception {
		return dao.reportDataContributingEmployerAnalysisBySizeAndSisCode(sarsFileId);
	}
	
	public List<ByChamberReportBean> reportDataContributingEmployerAnalysisBySizeAndSisCodeWithProvinceAssigned(Long sarsFileId) throws Exception {
		return dao.reportDataContributingEmployerAnalysisBySizeAndSisCodeWithProvinceAssigned(sarsFileId);
	}

	public List<SarsEmployerDetailBean> findMersetaJoiners(Long sarsFileIdFrom, Long sarsFileIdTo) throws Exception {
		return dao.findMersetaJoiners(sarsFileIdFrom, sarsFileIdTo);
	}
	
	public List<SarsEmployerDetailBean> findMersetaJoinersVersionTwo(Long sarsFileIdFrom,Long sarsFileIdTo) throws Exception {
		return dao.findMersetaJoinersVersionTwo(sarsFileIdFrom, sarsFileIdTo);
	}

	public List<SarsEmployerDetailBean> findMersetaLeavers(Long sarsFileIdFrom, Long sarsFileIdTo) throws Exception {
		return dao.findMersetaLeavers(sarsFileIdFrom, sarsFileIdTo);
	}
	
	public List<SarsEmployerDetailBean> findMersetaLeaversVersionTwo(Long sarsFileIdFrom,Long sarsFileIdTo) throws Exception {
		return dao.findMersetaLeaversVersionTwo(sarsFileIdFrom, sarsFileIdTo);
	}

	public List<ByChamberReportBean> findLevyIncomeBySchemeYears(List<Integer> schemeYearsAvalaible) throws Exception {
		return dao.findLevyIncomeBySchemeYears(schemeYearsAvalaible);
	}

	public List<ByChamberReportBean> findMandatoryGrantDataBySchemeYearsGroupByChamber(List<Integer> schemeYearsAvalaible) throws Exception {
		return dao.findMandatoryGrantDataBySchemeYearsGroupByChamber(schemeYearsAvalaible);
	}

	public List<ByChamberReportBean> levyStatusOfEmployerByChamber() throws Exception {
		return dao.levyStatusOfEmployerByChamber();
	}

	public List<ByChamberReportBean> getWspIdsFromNativeSQL() throws Exception {
		return dao.getWspIdsFromNativeSQL();
	}

	public BigInteger findPreSarsFile(Long monthId) throws Exception {
		return dao.findPreSarsFile(monthId);
	}

	public List<BelowThresReportBean> generateBelowThresholdReport(List<Integer> schemeYearList, Integer lastestSchemeYear, Double minAmount, Double maxAmount, Double minAmountProRata, Double maxAmountProRata) throws Exception {
		List<BelowThresReportBean> results = new ArrayList<>();
		for (Integer schemeYear : schemeYearList) {
			BelowThresReportBean newEntry = new BelowThresReportBean();
			newEntry.setSchemeYear(schemeYear);
			newEntry.setResultsForSchemeYear(belowThresholdReportBySchemeYearWithAmounts(schemeYear, lastestSchemeYear, minAmount, maxAmount, minAmountProRata, maxAmountProRata));
			results.add(newEntry);
			newEntry = null;
		}
		return results;
	}

	public List<ResultsBean> belowThresholdReportBySchemeYear(Integer schemeYear, Integer latestSchemeYear) throws Exception {
		return dao.belowThresholdReportBySchemeYear(schemeYear, latestSchemeYear);
	}
	
	public List<ResultsBean> belowThresholdReportBySchemeYearWithAmounts(Integer schemeYear, Integer latestSchemeYear, Double minAmount, Double maxAmount, Double minAmountProRata, Double maxAmountProRata) throws Exception {
		return dao.belowThresholdReportBySchemeYearWithAmounts(schemeYear, latestSchemeYear, minAmount, maxAmount, minAmountProRata, maxAmountProRata);
	}
	
	public List<BelowThresReportBean> generateBelowThresholdReportVersionTwo(List<Integer> schemeYearList, Double minAmountDefualt, Double maxAmountDefualt) throws Exception {
		List<BelowThresReportBean> results = new ArrayList<>();
		for (Integer schemeYear : schemeYearList) {
			BelowThresReportBean newEntry = new BelowThresReportBean();
			newEntry.setSchemeYear(schemeYear);
			
			// set information for prorata calc
			
			
			Double minAmount = minAmountDefualt;
			Double maxAmount = 0.0;
			
			
			// calculate financial year for scheme year 
			SchemeYearIdentifierBean finYearMerseta = findSchemeYearInfo(schemeYear);
			// count SARS files uploaded in scheme year
			if (sarsFilesService == null) {
				sarsFilesService = new SarsFilesService();
			}
			// calculate pro rata amount 
			if (finYearMerseta != null) {
				
				int countFilesUploaded = 12;
				// version 1
//				Date fromDate = GenericUtility.getStartOfDay(finYearMerseta.getStartOfYear());
//				Date toDate = GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth(finYearMerseta.getEndOfYear()));
				// version 2
				Date fromDate = GenericUtility.getStartOfDay(GenericUtility.addMonthsToDate(finYearMerseta.getStartOfYear(), 1));
				Date toDate = GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth((GenericUtility.addMonthsToDate(finYearMerseta.getEndOfYear(), 1))));
				countFilesUploaded = sarsFilesService.countSarsFilesWhereForMonthBetweenDates(fromDate, toDate);
				
				if (countFilesUploaded == 12) {
					maxAmount = maxAmountDefualt;
				} else if (countFilesUploaded > 0) {
					/*
					 * Formula
					 * 4000x10/12
					 * 4000 - MAx threshold amount (thresholdMaxAmount)
					 * 10 - Levies uploaded for the latest scheme year (countFilesUploadedForLastestSchemeYear)
					 * 12 - months in the year
					 */
					maxAmount = ( (maxAmountDefualt * countFilesUploaded) / 12 );
				}
			}
			
			if (maxAmount != 0.00) {
				maxAmount = maxAmount * -1;
			}
			if (minAmount != 0.00) {
				minAmount = minAmount * -1;
			}
			newEntry.setResultsForSchemeYear(belowThresholdReportBySchemeYearWithAmountsExcludingLastestSchemeYear(schemeYear, minAmount, maxAmount));
			results.add(newEntry);
			newEntry = null;
		}
		return results;
	}
	
	public List<ResultsBean> belowThresholdReportBySchemeYearWithAmountsExcludingLastestSchemeYear(Integer schemeYear, Double minAmountProRata, Double maxAmountProRata) throws Exception {
		return dao.belowThresholdReportBySchemeYearWithAmountsExcludingLastestSchemeYear(schemeYear, minAmountProRata, maxAmountProRata);
	}
	
	public List<BelowThresReportBean> generateAllSchemeYearsView(List<Integer> allSchemeYears,  Double minAmountDefualt, Double maxAmountDefualt) throws Exception{
		List<BelowThresReportBean> resultsList = new ArrayList<>();
		for (Integer schemeYear : allSchemeYears) {
			
			// calculate financial year for scheme year 
			SchemeYearIdentifierBean finYearMerseta = findSchemeYearInfo(schemeYear);
			
			Date startFinDate = null;
			Date endFinDate = null;
			Date startFinDateSearch = null;
			Date endFinDateSearch = null;
			Integer filesFound = 0;
			Double minAmount = minAmountDefualt;
			Double maxAmount = 0.00;
			
			
			if (finYearMerseta != null) {
				
				
				filesFound = 12;
				
				// version 1
				startFinDate = GenericUtility.getStartOfDay(finYearMerseta.getStartOfYear());
				endFinDate = GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth(finYearMerseta.getEndOfYear()));
				
				// version twp
				startFinDateSearch = GenericUtility.getStartOfDay(GenericUtility.addMonthsToDate(finYearMerseta.getStartOfYear(), 1));
				endFinDateSearch = GenericUtility.getEndOfDay(GenericUtility.getLasttDayOfMonth((GenericUtility.addMonthsToDate(finYearMerseta.getEndOfYear(), 1))));
				
				if (sarsFilesService == null) {
					sarsFilesService = new SarsFilesService();
				}
				
				filesFound = sarsFilesService.countSarsFilesWhereForMonthBetweenDates(startFinDateSearch, endFinDateSearch);
				
				if (filesFound == 12) {
					maxAmount = maxAmountDefualt;
				} else if (filesFound > 0) {
					/*
					 * Formula
					 * 4000x10/12
					 * 4000 - MAx threshold amount (thresholdMaxAmount)
					 * 10 - Levies uploaded for the latest scheme year (countFilesUploadedForLastestSchemeYear)
					 * 12 - months in the year
					 */
					maxAmount = ( (maxAmountDefualt * filesFound) / 12 );
				}
			}
			
			resultsList.add(new BelowThresReportBean(schemeYear, startFinDate, endFinDate, filesFound, minAmount, maxAmount));
		}
		
		return resultsList;
	}
	
	public SchemeYearIdentifierBean findSchemeYearInfo(Date myDate) {
		SchemeYearIdentifierBean thisSchemeYearThingy = new SchemeYearIdentifierBean();

		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(myDate.getTime());

		if (c.get(Calendar.MONTH) < 3) {
			c.set(c.get(Calendar.YEAR) - 1, 03, 01);
		}
		else {
			c.set(c.get(Calendar.YEAR), 03, 01);
		}

		thisSchemeYearThingy.setStartOfYear(c.getTime());
		thisSchemeYearThingy.setSchemeYear(c.get(Calendar.YEAR));

		c.set(c.get(Calendar.YEAR) + 1, 02, 31);
		thisSchemeYearThingy.setEndOfYear(c.getTime());

		thisSchemeYearThingy.setMonthsToDate(GenericUtility.getMonthsDates(thisSchemeYearThingy.getStartOfYear(), myDate));

		return thisSchemeYearThingy;
	}
	
	public SchemeYearIdentifierBean findSchemeYearInfo(Integer myYear) {
		SchemeYearIdentifierBean thisSchemeYearThingy = new SchemeYearIdentifierBean();

		Calendar c = Calendar.getInstance();
		
		c.set(myYear, 03, 01);
		thisSchemeYearThingy.setStartOfYear(c.getTime());
		thisSchemeYearThingy.setSchemeYear(myYear);
		
		c.set(c.get(Calendar.YEAR) + 1, 02, 31);
		thisSchemeYearThingy.setEndOfYear(c.getTime());

		thisSchemeYearThingy.setMonthsToDate(GenericUtility.getMonthsDates(thisSchemeYearThingy.getStartOfYear(), thisSchemeYearThingy.getEndOfYear()));

		return thisSchemeYearThingy;
	}
	
	public void generateLevyIncomeByYearChamber(){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				// set info for generation
				ReportGenerationPropertiesService rgpService = new ReportGenerationPropertiesService();
				UsersService us = new UsersService();
				LevyIncomeSummaryService lissService = new LevyIncomeSummaryService();
				
				// entity
				ReportGenerationProperties rgp = null;
				Users generationUser = null;
				
				List<LevyIncomeSummary> displayLevyList = new ArrayList<>();
				List<ByChamberReportBean> levyincomebyyearbychamber = new ArrayList<>();
				
				try {
					
					rgp = rgpService.findByReportProperty(ReportPropertiesEnum.Levy_Analysis_Scheme_Year_Chamber);
					generationUser = us.findByKey(rgp.getUserStartedGeneration().getId());
					
					notifyOnGeneration("NSDMS Notification: Report Generation Started", "Report generation for: Levy Analysis by Scheme Year and Chamber has started on: " + HAJConstants.PL_LINK, generationUser);
					
					// delete existing entries
					displayLevyList = lissService.allLevyIncomeSummary();
					for (LevyIncomeSummary levyIncomeSummary : displayLevyList) {
						lissService.delete(levyIncomeSummary);
					}
					
					levyincomebyyearbychamber = findLevyIncomeByYear();
					
					Double totalAuto = 0.0;
					Double totalMetal = 0.0;
					Double totalMotor = 0.0;
					Double totalNewTyre = 0.0;
					Double totalPlastic = 0.0;
					Double totalUnknown = 0.0;
					Double totalAcm = 0.0;
					Double finalTotal = 0.0;
					
					List<IDataEntity> createList = new ArrayList<>();
					
					for (ByChamberReportBean byChamberReportBean : levyincomebyyearbychamber) {
//						LevyIncomeSummary levyIncome = new LevyIncomeSummary(byChamberReportBean.getDescription(), byChamberReportBean.getBdAuto(), byChamberReportBean.getBdMetal(), byChamberReportBean.getBdMotor(), byChamberReportBean.getBdNewTyre(), byChamberReportBean.getBdPlastic(), byChamberReportBean.getBdUnknown(), null);
						LevyIncomeSummary levyIncome = new LevyIncomeSummary(byChamberReportBean.getDescription(), byChamberReportBean.getBdAuto(), byChamberReportBean.getBdMetal(), byChamberReportBean.getBdMotor(), byChamberReportBean.getBdNewTyre(), byChamberReportBean.getBdPlastic(), byChamberReportBean.getBdUnknown(), byChamberReportBean.getBdAcm(), null);
						levyIncome.setReportProperty(ReportPropertiesEnum.Levy_Analysis_Scheme_Year_Chamber);
						Double totalAmount = 0.0;
						if (byChamberReportBean.getBdAuto() != null && !byChamberReportBean.getBdAuto().equals(BigDecimal.valueOf(0.0))) {
							totalAmount = totalAmount + byChamberReportBean.getBdAuto().doubleValue();
							totalAuto = totalAmount + byChamberReportBean.getBdAuto().doubleValue();
//							totalAmount.add(byChamberReportBean.getBdAuto());
//							totalAuto.add(byChamberReportBean.getBdAuto());
						}							
						if (byChamberReportBean.getBdMetal() != null && !byChamberReportBean.getBdMetal().equals(BigDecimal.valueOf(0.0))) {
							totalAmount = totalAmount + byChamberReportBean.getBdMetal().doubleValue();
							totalMetal = totalMetal + byChamberReportBean.getBdMetal().doubleValue();
//							totalAmount.add(byChamberReportBean.getBdMetal());
//							totalMetal.add(byChamberReportBean.getBdMetal());
						}
						if (byChamberReportBean.getBdMotor() != null && !byChamberReportBean.getBdMotor().equals(BigDecimal.valueOf(0.0))) {
							totalAmount = totalAmount + byChamberReportBean.getBdMotor().doubleValue();
							totalMotor = totalMotor + byChamberReportBean.getBdMotor().doubleValue();
//							totalAmount.add(byChamberReportBean.getBdMotor());
//							totalMotor.add(byChamberReportBean.getBdMotor());
						}
						if (byChamberReportBean.getBdNewTyre() != null && !byChamberReportBean.getBdNewTyre().equals(BigDecimal.valueOf(0.0))) {
							totalAmount = totalAmount + byChamberReportBean.getBdNewTyre().doubleValue();
							totalNewTyre = totalNewTyre + byChamberReportBean.getBdNewTyre().doubleValue();
//							totalAmount.add(byChamberReportBean.getBdNewTyre());
//							totalNewTyre.add(byChamberReportBean.getBdNewTyre());
						}
						if (byChamberReportBean.getBdPlastic() != null && !byChamberReportBean.getBdPlastic().equals(BigDecimal.valueOf(0.0))) {
							totalAmount = totalAmount + byChamberReportBean.getBdPlastic().doubleValue();
							totalPlastic = totalPlastic + byChamberReportBean.getBdPlastic().doubleValue();
							
//							totalAmount.add(byChamberReportBean.getBdPlastic());
//							totalPlastic.add(byChamberReportBean.getBdPlastic());
						}
						if (byChamberReportBean.getBdUnknown() != null && !byChamberReportBean.getBdUnknown().equals(BigDecimal.valueOf(0.0))) {
							totalAmount = totalAmount + byChamberReportBean.getBdUnknown().doubleValue();
							totalUnknown = totalUnknown + byChamberReportBean.getBdUnknown().doubleValue();
//							totalAmount.add(byChamberReportBean.getBdUnknown());
//							totalUnknown.add(byChamberReportBean.getBdUnknown());
						}
						if (byChamberReportBean.getBdAcm() != null && !byChamberReportBean.getBdAcm().equals(BigDecimal.valueOf(0.0))) {
							totalAmount = totalAmount + byChamberReportBean.getBdAcm().doubleValue();
							totalAcm = totalAcm + byChamberReportBean.getBdAcm().doubleValue();
//							totalAmount.add(byChamberReportBean.getBdAcm());
//							totalAcm.add(byChamberReportBean.getBdAcm());
						}
						if (totalAmount != null) {	
							levyIncome.setTotal(BigDecimal.valueOf(totalAmount));
							finalTotal = finalTotal + totalAmount;
						}
						createList.add(levyIncome);
					}
					
//					createList.add(new LevyIncomeSummary("Total", BigDecimal.valueOf(totalAuto), BigDecimal.valueOf(totalMetal), BigDecimal.valueOf(totalMotor), 
//							BigDecimal.valueOf(totalNewTyre), BigDecimal.valueOf(totalPlastic), BigDecimal.valueOf(totalUnknown), BigDecimal.valueOf(finalTotal)));
					createList.add(new LevyIncomeSummary("Total", BigDecimal.valueOf(totalAuto), BigDecimal.valueOf(totalMetal), BigDecimal.valueOf(totalMotor), 
							BigDecimal.valueOf(totalNewTyre), BigDecimal.valueOf(totalPlastic), BigDecimal.valueOf(totalUnknown), BigDecimal.valueOf(totalAcm), BigDecimal.valueOf(finalTotal)));
					
					if (!createList.isEmpty()) {
						rgpService.createBatch(createList);
					}
					
					/* Re-open Reporting block */
					rgp.setGenerationUnderway(false);
					rgp.setDateLastCompleted(getSynchronizedDate());
					rgpService.update(rgp);
					
					notifyOnGeneration("NSDMS Notification: Report Generation Completed", "Report generation for: Levy Analysis by Scheme Year and Chamber has completed on: " + HAJConstants.PL_LINK , generationUser);
				} catch (Exception e) {
					logger.error(e.getMessage());
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					String exceptionAsString = sw.toString();
					GenericUtility.mailError("Error on Report Generation for: Levy Analysis by Scheme Year and Chamber","<p>Error On Report generation for: Levy Analysis by Scheme Year and Chamber on site: "+HAJConstants.PL_LINK+".</p><p>Stack Trace:<p/><p> " + exceptionAsString + "</p>");
				}
				// null objects
				rgpService = null;
				us = null;
				rgp = null;
				generationUser = null;
			}
		}).start();
	}
	
	public List<SarsProvinceCountBean> employerCountBySarsFileAndChamberIdGroupedByProvince(Long sarsFileId, Long chamberId) throws Exception {
		return dao.employerCountBySarsFileAndChamberIdGroupedByProvince(sarsFileId, chamberId);
	}
	
	public List<SarsProvinceCountBean> contributingEmployerCountBySarsFileGroupedByProvince(Long sarsFileId) throws Exception {
		return dao.contributingEmployerCountBySarsFileGroupedByProvince(sarsFileId);
	}
	
	public List<SarsProvinceCountBean> activeContributingEmployersCountBySarsFileGroupedByProvince(Long sarsFileId) throws Exception {
		return dao.activeContributingEmployersCountBySarsFileGroupedByProvince(sarsFileId);
	}
	
	public List<SarsProvinceCountBean> generateActiveContributingEmployersCountBySarsFileGroupedByProvince(Long sarsFileId) throws Exception{
		List<SarsProvinceCountBean> returnList = new ArrayList<>();
		List<SarsProvinceCountBean> resultsList = activeContributingEmployersCountBySarsFileGroupedByProvince(sarsFileId);
		for (SarsProvinceCountBean result : resultsList) {
			BigInteger total = BigInteger.valueOf(0l);
			if (result.getGauteng() != null) {
				total = total.add(result.getGauteng());
			}
			if (result.getEasternCape() != null) {
				total = total.add(result.getEasternCape());
			}
			if (result.getFreeState() != null) {
				total = total.add(result.getFreeState());
			}
			if (result.getKwazulunatal() != null) {
				total = total.add(result.getKwazulunatal());				
			}
			if (result.getLimpopo() != null) {
				total = total.add(result.getLimpopo());
			}
			if (result.getMpumalanga() != null) {
				total = total.add(result.getMpumalanga());
			}
			if (result.getNorthernCape() != null) {
				total = total.add(result.getNorthernCape());
			}
			if (result.getNorthWest() != null) {
				total = total.add(result.getNorthWest());
			}
			if (result.getWesternCape() != null) {
				total = total.add(result.getWesternCape());
			}
			if (result.getSaNational() != null) {
				total = total.add(result.getSaNational());
			}
			if (result.getOutsideSA() != null) {
				total = total.add(result.getOutsideSA());
			}
			result.setTotalEntry(false);
			result.setTotal(total);
		}
		if (!resultsList.isEmpty()) {
			returnList.addAll(resultsList);
		}
		resultsList = null;
		return returnList;
	}
	
	public List<SarsProvinceCountBean> generateEmployerAnayalisisByProvinceAndSarsFileId(Long sarsFileId) throws Exception{
		List<SarsProvinceCountBean> returnList = new ArrayList<>();
		List<SarsProvinceCountBean> resultsList = contributingEmployerCountBySarsFileGroupedByProvince(sarsFileId);
		SarsProvinceCountBean totalForSearch = new SarsProvinceCountBean("Total");
		for (SarsProvinceCountBean result : resultsList) {
			BigInteger total = BigInteger.valueOf(0l);
			if (result.getGauteng() != null) {
				totalForSearch.setGauteng(totalForSearch.getGauteng().add(result.getGauteng()));
				total = total.add(result.getGauteng());
			}
			if (result.getEasternCape() != null) {
				totalForSearch.setEasternCape(totalForSearch.getEasternCape().add(result.getEasternCape()));
				total = total.add(result.getEasternCape());
			}
			if (result.getFreeState() != null) {
				totalForSearch.setFreeState(totalForSearch.getFreeState().add(result.getFreeState()));
				total = total.add(result.getFreeState());
			}
			if (result.getKwazulunatal() != null) {
				totalForSearch.setKwazulunatal(totalForSearch.getKwazulunatal().add(result.getKwazulunatal()));
				total = total.add(result.getKwazulunatal());				
			}
			if (result.getLimpopo() != null) {
				totalForSearch.setLimpopo(totalForSearch.getLimpopo().add(result.getLimpopo()));
				total = total.add(result.getLimpopo());
			}
			if (result.getMpumalanga() != null) {
				totalForSearch.setMpumalanga(totalForSearch.getMpumalanga().add(result.getMpumalanga()));
				total = total.add(result.getMpumalanga());
			}
			if (result.getNorthernCape() != null) {
				totalForSearch.setNorthernCape(totalForSearch.getNorthernCape().add(result.getNorthernCape()));
				total = total.add(result.getNorthernCape());
			}
			if (result.getNorthWest() != null) {
				totalForSearch.setNorthWest(totalForSearch.getNorthWest().add(result.getNorthWest()));
				total = total.add(result.getNorthWest());
			}
			if (result.getWesternCape() != null) {
				totalForSearch.setWesternCape(totalForSearch.getWesternCape().add(result.getWesternCape()));
				total = total.add(result.getWesternCape());
			}
			if (result.getSaNational() != null) {
				totalForSearch.setSaNational(totalForSearch.getSaNational().add(result.getSaNational()));
				total = total.add(result.getSaNational());
			}
			if (result.getOutsideSA() != null) {
				totalForSearch.setOutsideSA(totalForSearch.getOutsideSA().add(result.getOutsideSA()));
				total = total.add(result.getOutsideSA());
			}
			result.setTotalEntry(false);
			result.setTotal(total);
			totalForSearch.setTotal(totalForSearch.getTotal().add(total));
		}
		// link results to list
		if (!resultsList.isEmpty()) {
			returnList.addAll(resultsList);
			// add to list
			returnList.add(totalForSearch);
		}
		resultsList = null;
		totalForSearch = null;
		return returnList;
	}
	
	public List<SarsChamberProvinceReportBean> generateEmployerAnayalisisByChamberProvinceAndSarsFileId(List<Chamber> chamberList, Long sarsFileId) throws Exception{
		List<SarsChamberProvinceReportBean> returnList = new ArrayList<>();
		
		// set up grant total for report
		SarsChamberProvinceReportBean grantTotal = new SarsChamberProvinceReportBean("Grand Total");
		grantTotal.setTotalEntry(true);
		SarsProvinceCountBean grantTotalProvince = new SarsProvinceCountBean("Total For All Data");
		
		
		// loop through all chambers
		for (Chamber chamber : chamberList) {
			
			SarsChamberProvinceReportBean entry = new SarsChamberProvinceReportBean("Chamber: "+chamber.getCode() + " Results");
			grantTotal.setTotalEntry(true);
			
			// locate results
			List<SarsProvinceCountBean> resultsList = employerCountBySarsFileAndChamberIdGroupedByProvince(sarsFileId, chamber.getId());
			SarsProvinceCountBean totalForSearch = new SarsProvinceCountBean("Total");
			for (SarsProvinceCountBean result : resultsList) {
				BigInteger total = BigInteger.valueOf(0l);
				if (result.getGauteng() != null) {
					totalForSearch.setGauteng(totalForSearch.getGauteng().add(result.getGauteng()));
					grantTotalProvince.setGauteng(grantTotalProvince.getGauteng().add(result.getGauteng()));
					total = total.add(result.getGauteng());
				}
				if (result.getEasternCape() != null) {
					totalForSearch.setEasternCape(totalForSearch.getEasternCape().add(result.getEasternCape()));
					grantTotalProvince.setEasternCape(grantTotalProvince.getEasternCape().add(result.getEasternCape()));
					total = total.add(result.getEasternCape());
				}
				if (result.getFreeState() != null) {
					totalForSearch.setFreeState(totalForSearch.getFreeState().add(result.getFreeState()));
					grantTotalProvince.setFreeState(grantTotalProvince.getFreeState().add(result.getFreeState()));
					total = total.add(result.getFreeState());
				}
				if (result.getKwazulunatal() != null) {
					totalForSearch.setKwazulunatal(totalForSearch.getKwazulunatal().add(result.getKwazulunatal()));
					grantTotalProvince.setKwazulunatal(grantTotalProvince.getKwazulunatal().add(result.getKwazulunatal()));
					total = total.add(result.getKwazulunatal());				
				}
				if (result.getLimpopo() != null) {
					totalForSearch.setLimpopo(totalForSearch.getLimpopo().add(result.getLimpopo()));
					grantTotalProvince.setLimpopo(grantTotalProvince.getLimpopo().add(result.getLimpopo()));
					total = total.add(result.getLimpopo());
				}
				if (result.getMpumalanga() != null) {
					totalForSearch.setMpumalanga(totalForSearch.getMpumalanga().add(result.getMpumalanga()));
					grantTotalProvince.setMpumalanga(grantTotalProvince.getMpumalanga().add(result.getMpumalanga()));
					total = total.add(result.getMpumalanga());
				}
				if (result.getNorthernCape() != null) {
					totalForSearch.setNorthernCape(totalForSearch.getNorthernCape().add(result.getNorthernCape()));
					grantTotalProvince.setNorthernCape(grantTotalProvince.getNorthernCape().add(result.getNorthernCape()));
					total = total.add(result.getNorthernCape());
				}
				if (result.getNorthWest() != null) {
					totalForSearch.setNorthWest(totalForSearch.getNorthWest().add(result.getNorthWest()));
					grantTotalProvince.setNorthWest(grantTotalProvince.getNorthWest().add(result.getNorthWest()));
					total = total.add(result.getNorthWest());
				}
				if (result.getWesternCape() != null) {
					totalForSearch.setWesternCape(totalForSearch.getWesternCape().add(result.getWesternCape()));
					grantTotalProvince.setWesternCape(grantTotalProvince.getWesternCape().add(result.getWesternCape()));
					total = total.add(result.getWesternCape());
				}
				if (result.getSaNational() != null) {
					totalForSearch.setSaNational(totalForSearch.getSaNational().add(result.getSaNational()));
					grantTotalProvince.setSaNational(grantTotalProvince.getSaNational().add(result.getSaNational()));
					total = total.add(result.getSaNational());
				}
				if (result.getOutsideSA() != null) {
					totalForSearch.setOutsideSA(totalForSearch.getOutsideSA().add(result.getOutsideSA()));
					grantTotalProvince.setOutsideSA(grantTotalProvince.getOutsideSA().add(result.getOutsideSA()));
					total = total.add(result.getOutsideSA());
				}
				result.setTotalEntry(false);
				result.setTotal(total);
				totalForSearch.setTotal(totalForSearch.getTotal().add(total));
				grantTotalProvince.setTotal(grantTotalProvince.getTotal().add(total));
			}
			// link results to list
			if (!resultsList.isEmpty()) {
				entry.getResultsList().addAll(resultsList);
				// add to list
				entry.getResultsList().add(totalForSearch);
			}
			// add to main return list
			returnList.add(entry);
		}
		// add totals
		grantTotal.getResultsList().add(grantTotalProvince);
		returnList.add(grantTotal);
		return returnList;
	}
	
	public void notifyOnGeneration(String subject, String message, Users generationUser) {
		List<String> emailAddressList = new ArrayList<>();
		if (HAJConstants.DEV_TEST_PROD.equals("P")) {
			// prod only sends to session user
			emailAddressList.add(generationUser.getEmail());
		} else {
			emailAddressList.add(generationUser.getEmail());
			emailAddressList.addAll(GenericUtility.testEmails());
		}
		for (String email : emailAddressList) {
			GenericUtility.sendMail(email, subject, message, null);
		}
	}

}