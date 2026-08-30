package haj.com.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.bean.ResultsBean;
import haj.com.bean.SarsEmployerDetailBean;
import haj.com.bean.SarsProvinceCountBean;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.report.bean.ByChamberReportBean;
import haj.com.sars.SarsFiles;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankDAO.
 */
public class ReportDAO extends AbstractDAO {

	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> findSchemeYears() throws Exception {
		String hql ="select distinct(schemeYear) from SarsLevyDetails where schemeYear is not null "; 
		return (List<Integer>) super.getList(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> findLastestSchemeYear() throws Exception {
		String hql ="select distinct(schemeYear) from SarsLevyDetails where schemeYear is not null order by schemeYear desc "; 
		return (List<Integer>) super.getList(hql, 1);
	}
	
	public List<ResultsBean> belowThresholdReportBySchemeYear(Integer schemeYear, Integer latestSchemeYear) throws Exception {
		String sql = "select   " + 
				"	b.ref_no as refNo  " + 
				"	, '' as registeredNameOfEntity  " + 
				"	, b.scheme_year as schemeYear  " + 
				"	, b.total_Amount_M_D_A as totalMDA  " + 
				"	, b.total_Amount_P_I as totalPI  " + 
				"	, b.total_Amount as totalAmount  " + 
				"from (  " + 
				"	select   " + 
				"		ref_no  " + 
				"		, scheme_year  " + 
				"		, ROUND(SUM(IFNULL(a.mandatory_levy, 0) + IFNULL(a.discretionary_levy, 0) + IFNULL(a.admin_levy, 0)),2) as total_Amount_M_D_A  " + 
				"		, ROUND(SUM(IFNULL(a.penalty, 0) + IFNULL(a.interest, 0)),2) as total_Amount_P_I  " + 
				"		, ROUND(SUM(IFNULL(a.total, 0)), 2) as total_Amount  " + 
				"	from   " + 
				"		sars_levy_detail a  " + 
				"		inner join (		  " + 
				"			select distinct scheme_year as _year from sars_levy_detail where scheme_year is not null and scheme_year IN (:schemeYear)  " + 
				"		) f ON f._year = a.scheme_year  " + 
				"	where   " + 
				"		a.total > 0  " + 
				"		and a.sars_filel_id is not null  " + 
				"	group by   " + 
				"		a.ref_no  " + 
				"		, a.scheme_year  " + 
				") as b   " + 
				"where (scheme_year = :latestSchemeYear and total_Amount between 0.00 and 350) or (scheme_year <> :latestSchemeYear and total_Amount between 0.00 and 4000)  " + 
				"order by b.ref_no, registeredNameOfEntity, b.scheme_year";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("schemeYear", schemeYear);
		parameters.put("latestSchemeYear", latestSchemeYear);
		return (List<ResultsBean>) super.nativeSelectSqlList(sql, ResultsBean.class, parameters);
	}
	
	public List<ResultsBean> belowThresholdReportBySchemeYearWithAmounts(Integer schemeYear, Integer latestSchemeYear, Double minAmount, Double maxAmount, Double minAmountProRata, Double maxAmountProRata) throws Exception {
		String sql = "select   " + 
				"	b.ref_no as refNo  " + 
				"	, '' as registeredNameOfEntity  " + 
				"	, b.scheme_year as schemeYear  " + 
				"	, b.total_Amount_M_D_A as totalMDA  " + 
				"	, b.total_Amount_P_I as totalPI  " + 
				"	, b.total_Amount as totalAmount  " + 
				"from (  " + 
				"	select   " + 
				"		ref_no  " + 
				"		, scheme_year  " + 
				"		, ROUND(SUM(IFNULL(a.mandatory_levy, 0) + IFNULL(a.discretionary_levy, 0) + IFNULL(a.admin_levy, 0)),2) as total_Amount_M_D_A  " + 
				"		, ROUND(SUM(IFNULL(a.penalty, 0) + IFNULL(a.interest, 0)),2) as total_Amount_P_I  " + 
				"		, ROUND(SUM(IFNULL(a.total, 0)), 2) as total_Amount  " + 
				"	from   " + 
				"		sars_levy_detail a  " + 
				"		inner join (		  " + 
				"			select distinct scheme_year as _year from sars_levy_detail where scheme_year is not null and scheme_year IN (:schemeYear)  " + 
				"		) f ON f._year = a.scheme_year  " + 
				"	where   " + 
				"		a.sars_filel_id is not null  " + 
				"	group by   " + 
				"		a.ref_no  " + 
				"		, a.scheme_year  " + 
				") as b   " + 
				"where (scheme_year = :latestSchemeYear and total_Amount_M_D_A between :maxAmountProRata and :minAmountProRata) or (scheme_year <> :latestSchemeYear and total_Amount_M_D_A between :maxAmount and :minAmount)  " + 
				"order by b.ref_no, registeredNameOfEntity, b.scheme_year";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("schemeYear", schemeYear);
		parameters.put("latestSchemeYear", latestSchemeYear);
		parameters.put("minAmount", minAmount);
		parameters.put("maxAmount", maxAmount);
		parameters.put("minAmountProRata", minAmountProRata);
		parameters.put("maxAmountProRata", maxAmountProRata);
		return (List<ResultsBean>) super.nativeSelectSqlList(sql, ResultsBean.class, parameters);
	}
	
	public List<ResultsBean> belowThresholdReportBySchemeYearWithAmountsExcludingLastestSchemeYear(Integer schemeYear, Double minAmountProRata, Double maxAmountProRata) throws Exception {
		String sql = "select   " + 
				"	b.ref_no as refNo  " + 
				"	, '' as registeredNameOfEntity  " + 
				"	, b.scheme_year as schemeYear  " + 
				"	, b.total_Amount_M_D_A as totalMDA  " + 
				"	, b.total_Amount_P_I as totalPI  " + 
				"	, b.total_Amount as totalAmount  " + 
				"from (  " + 
				"	select   " + 
				"		ref_no  " + 
				"		, scheme_year  " + 
				"		, ROUND(SUM(IFNULL(a.mandatory_levy, 0) + IFNULL(a.discretionary_levy, 0) + IFNULL(a.admin_levy, 0)),2) as total_Amount_M_D_A  " + 
				"		, ROUND(SUM(IFNULL(a.penalty, 0) + IFNULL(a.interest, 0)),2) as total_Amount_P_I  " + 
				"		, ROUND(SUM(IFNULL(a.total, 0)), 2) as total_Amount  " + 
				"	from   " + 
				"		sars_levy_detail a  " + 
				"		inner join (		  " + 
				"			select distinct scheme_year as _year from sars_levy_detail where scheme_year is not null and scheme_year IN (:schemeYear)  " + 
				"		) f ON f._year = a.scheme_year  " + 
				"	where   " + 
				"		a.sars_filel_id is not null  " + 
				"	group by   " + 
				"		a.ref_no  " + 
				"		, a.scheme_year  " + 
				") as b   " + 
				"where total_Amount_M_D_A between :maxAmountProRata and :minAmountProRata " + 
				"order by b.ref_no, registeredNameOfEntity, b.scheme_year";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("schemeYear", schemeYear);
		parameters.put("minAmountProRata", minAmountProRata);
		parameters.put("maxAmountProRata", maxAmountProRata);
		return (List<ResultsBean>) super.nativeSelectSqlList(sql, ResultsBean.class, parameters);
	}
	
	//GET A LIST OF SARSFILES
	public BigInteger findPreSarsFile(Long monthId) throws Exception {
		String sql = "SELECT id "
				+ "FROM merseta.sars_files " + 
				"WHERE for_month = DATE_ADD((SELECT a.for_month FROM merseta.sars_files a where a.id = :monthId ), INTERVAL -1 YEAR) ";
		
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("monthId", monthId);
		return (BigInteger) super.nativeSelectSqlUniqueResult(sql, parameters);
	}
	
	//REPORT 1.2.1 -- REPORT 1.3.2
    public List<ByChamberReportBean> findContributingEmployersBetweenDates(SarsFiles monthId ) throws Exception {
    	String hql ="SELECT  " + 
    			"	description,   " + 
    			"    MAX(if(numEmp = 'auto', num, 0)) as auto,   " + 
    			"    MAX(if(numEmp = 'metal', num, 0)) as metal,   " + 
    			"    MAX(if(numEmp = 'motor', num, 0)) as motor,   " + 
    			"    MAX(if(numEmp = 'newTyre', num, 0)) as newTyre,   " + 
    			"    MAX(if(numEmp = 'plastic', num, 0)) as plastic,   " + 
    			"    MAX(if(numEmp = 'unknown', num, 0)) as unknown,   " + 
    			"    MAX(if(numEmp = 'NA', num, 0)) as NA,   " + 
    			"    MAX(if(numEmp = 'acm', num, 0)) as acm   " + 
    			"FROM ( 	 " + 
    			"	select  " + 
    			"        description,  " + 
    			"        count(ref_no) as num,  " + 
    			"        numEmp  " + 
    			"    from ( " + 
    			"        select  " + 
    			"            distinct " + 
    			"            sed.ref_no, " + 
    			"            case      " + 
    			"                when sc.chamber_id = 1 then 'auto'   " + 
    			"                when sc.chamber_id = 3 then 'metal'   " + 
    			"                when sc.chamber_id = 4 then 'motor'   " + 
    			"                when sc.chamber_id = 5 then 'newTyre'   " + 
    			"                when sc.chamber_id = 6 then 'plastic'   " + 
    			"                when sc.chamber_id = 7 then 'unknown'   " + 
    			"                when sc.chamber_id = 8 then 'NA'   " + 
    			"                when sc.chamber_id = 9 then 'acm'   " + 
    			"                else 'NA' end as numEmp, " + 
    			"            'Contributing Employers' as description " + 
    			"        from  " + 
    			"            sars_employer_detail sed 	 " + 
    			"        inner join  " + 
    			"            ( " + 
    			"                select " + 
    			"                    ref_no, " + 
    			"                    sars_filel_id " + 
    			"                from  " + 
    			"                    sars_levy_detail " + 
    			"                where " + 
    			"                    sars_filel_id = :monthId " + 
    			"            ) sld on sed.ref_no = sld.ref_no and sld.sars_filel_id = sed.sars_filel_id " + 
    			"        inner join  " + 
    			"            ( " + 
    			"                select  " + 
    			"                    code,  " + 
    			"                    chamber_id  " + 
    			"                from  " + 
    			"                    sic_code		 " + 
    			"            ) sc on sc.code = sed.sic_code_2  " + 
    			"        where  " + 
    			"            sed.sars_filel_id = :monthId " + 
    			"    ) nums  " + 
    			"    group by  " + 
    			"		description,  " + 
    			"		numEmp " + 
    			") as counts   " + 
    			"group by  " + 
    			"description";
    	 /* String hql ="SELECT description,  " + 
                "                        MAX(if(numEmp = 'auto', num, 0)) as auto,  " + 
                "                        MAX(if(numEmp = 'metal', num, 0)) as metal,  " + 
                "                        MAX(if(numEmp = 'motor', num, 0)) as motor,  " + 
                "                        MAX(if(numEmp = 'newTyre', num, 0)) as newTyre,  " + 
                "                        MAX(if(numEmp = 'plastic', num, 0)) as plastic,  " + 
                "                        MAX(if(numEmp = 'unknown', num, 0)) as unknown,  " + 
                "                        MAX(if(numEmp = 'NA', num, 0)) as NA  " + 
                "                    FROM ( " + 
                "                        select description, count(ref_no) as num, numEmp from (  " + 
                "                        select count(DISTINCT(sed.ref_no)) as ref_no " + 
                "                            , case     when sc.chamber_id = 1 then 'auto'  " + 
                "                            when sc.chamber_id = 3 then 'metal'  " + 
                "                            when sc.chamber_id = 4 then 'motor'  " + 
                "                            when sc.chamber_id = 5 then 'newTyre'  " + 
                "                            when sc.chamber_id = 6 then 'plastic'  " + 
                "                            when sc.chamber_id = 7 then 'unknown'  " + 
                "                            when sc.chamber_id = 8 then 'NA'  " + 
                "                            else 'NA' end as numEmp   " + 
                "                , 'Contributing Employers' as description   " + 
                "                             " + 
                "                from sars_employer_detail sed  " + 
                "                INNER join sars_levy_detail sld on sed.ref_no = sld.ref_no and sld.sars_filel_id = sed.sars_filel_id  " + 
                "                INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = sed.sic_code_2 " + 
                "                where sed.sars_filel_id = :monthId  " + 
                "                group by sed.ref_no          " + 
                "                ) nums group by description, numEmp  " + 
                "                ) counts  " + 
                "                group by description "; */
        /*String hql ="SELECT    " + 
        		"        			'Contributing Employers' AS description    " + 
        		"        			, SUM(CASE chamber WHEN 1 THEN 1 ELSE 0 END) as auto     " + 
        		"        			, SUM(CASE chamber WHEN 3 THEN 1 ELSE 0 END) as metal     " + 
        		"        			, SUM(CASE chamber WHEN 4 THEN 1 ELSE 0 END) as motor     " + 
        		"        			, SUM(CASE chamber WHEN 5 THEN 1 ELSE 0 END) as newTyre     " + 
        		"        			, SUM(CASE chamber WHEN 6 THEN 1 ELSE 0 END) as plastic     " + 
        		"        			, SUM(CASE chamber WHEN 7 THEN 1 ELSE 0 END) as unknown     " + 
        		"        			, SUM(CASE chamber WHEN 8 THEN 1 ELSE 0 END) as NA    " + 
        		"        		    " + 
        		"        		FROM (    " + 
        		"        			SELECT DISTINCT    " + 
        		"        				sed.ref_no    " + 
        		"        				, CASE COALESCE(sc.chamber_id, 8)     " + 
        		"        					WHEN 1 THEN 1       " + 
        		"        					WHEN 3 THEN 3       " + 
        		"        					WHEN 4 THEN 4      " + 
        		"        					WHEN 5 THEN 5       " + 
        		"        					WHEN 6 THEN 6       " + 
        		"        					WHEN 7 THEN 7       " + 
        		"        					WHEN 8 THEN 8       " + 
        		"        					ELSE 8 END AS chamber                      " + 
        		"        			FROM sars_employer_detail sed       " + 
        		"        			INNER JOIN sars_levy_detail sld ON sed.ref_no = sld.ref_no AND sld.sars_filel_id = sed.sars_filel_id       " + 
        		"        			INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc ON sc.code = sed.sic_code_2      " + 
        		"        			WHERE sed.sars_filel_id = :monthId     " + 
        		"        		) a ";*/
        Map<String, Object> parameters = new Hashtable<String, Object>();
        parameters.put("monthId", monthId.getId());
        return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class,parameters);
    }
	
	//REPORT 4 -- REPORT 1.3.2
	public ByChamberReportBean findLevyContributionsBetweenDates(Date fromDate, Date toDate ) throws Exception {
		List<ByChamberReportBean> l;
		
		String hql="SELECT " + 
				" 	'Levies received (80%)' as description"	+
				"   , (-1 * SUM(if(b.id = 1, a.total, 0)) ) as bdAuto " + 
				"    , (-1 * SUM(if(b.id = 3, a.total, 0)) )as bdMetal " + 
				"    , (-1 * SUM(if(b.id = 4, a.total, 0)) )as bdMotor " + 
				"    , (-1 * SUM(if(b.id = 5, a.total, 0)) )as bdNewTyre " + 
				"    , (-1 * SUM(if(b.id = 6, a.total, 0)) )as bdPlastic " + 
				"    , (-1 * SUM(if(b.id = 7, a.total, 0)) )as bdUnknown " + 
				"    , (-1 * SUM(if(b.id = 8, a.total, 0)) )as bdNA " + 
				"    , (-1 * SUM(if(b.id = 9, a.total, 0)) )as bdAcm " + 
				"    , (-1 * SUM(a.total) )as bdTotal " + 
				"FROM ( " + 
				"    SELECT " + 
				"        ref_no " + 
				"        , sars_filel_id " + 
				"        , SUM(total) as total " + 
				"    FROM ( " + 
				"        SELECT " + 
				"        ref_no " + 
				"        , sars_filel_id " + 
				"        , CAST(total AS DECIMAL(16,2)) as total " + 
				"        FROM sars_levy_detail sld " + 
				"        INNER JOIN ( " + 
				"			SELECT id FROM merseta.sars_files " + 
				"			WHERE for_month BETWEEN CAST('" + df.format(fromDate) + "' AS DATE)  AND CAST( '" + df.format(toDate) + "' AS DATE) " +
				"        ) sf on sf.id = sld.sars_filel_id " + 
				"    ) rawdata " + 
				"    GROUP BY ref_no, sars_filel_id " + 
				") a " + 
				"INNER JOIN ( " + 
				"    SELECT " + 
				"        ref_no " + 
				"        , sars_filel_id " + 
				"        , id " + 
				"    FROM " + 
				"    ( " + 
				"        SELECT " + 
				"            ser.ref_no " + 
				"            , ser.sars_filel_id " + 
				"            , CASE COALESCE(sc.chamber_id, 9) WHEN 1 THEN 1 " + 
				"                WHEN 3 THEN 3 " + 
				"                WHEN 4 THEN 4 " + 
				"                WHEN 5 THEN 5 " + 
				"                WHEN 6 THEN 6 " + 
				"                WHEN 7 THEN 7 " + 
				"                WHEN 8 THEN 8 " + 
				"                WHEN 9 THEN 9 " + 
				"                ELSE 9 END AS id " + 
				"        FROM sars_employer_detail ser " + 
				"		INNER JOIN ( " + 
				"			SELECT id FROM merseta.sars_files " + 
				"			WHERE for_month BETWEEN CAST('" + df.format(fromDate) + "' AS DATE)  AND CAST( '" + df.format(toDate) + "' AS DATE) " +
				"        ) sf on sf.id = ser.sars_filel_id " + 
				"        INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = ser.sic_code_2 " + 
				"    ) as xps " + 
				"    GROUP BY ref_no, sars_filel_id, id " + 
				") b on b.ref_no = a.ref_no AND b.sars_filel_id = a.sars_filel_id";
		l =  (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class);
		if (l != null && !l.isEmpty()) return l.get(0);
		else return null;
	}
	
	//REPORT 1.1
    public List<ByChamberReportBean> findleviesBetweenDates(Long monthId) throws Exception {
        String hql ="SELECT description,  " + 
                "                            MAX(if(numEmp = 'auto', num, 0)) as auto,  " + 
                "                            MAX(if(numEmp = 'metal', num, 0)) as metal,  " + 
                "                            MAX(if(numEmp = 'motor', num, 0)) as motor,  " + 
                "                            MAX(if(numEmp = 'newTyre', num, 0)) as newTyre,  " + 
                "                            MAX(if(numEmp = 'plastic', num, 0)) as plastic,  " + 
                "                            MAX(if(numEmp = 'unknown', num, 0)) as unknown,  " + 
                "                            MAX(if(numEmp = 'NA', num, 0)) as NA,  " + 
                "                            MAX(if(numEmp = 'acm', num, 0)) as acm  " + 
                "                        FROM ( " + 
                "                            select description, count(id) as num, numEmp from (  " + 
                "                            select sed.id as id " + 
                "                                , case     when sc.chamber_id = 1 then 'auto'  " + 
                "                                when sc.chamber_id = 3 then 'metal'  " + 
                "                                when sc.chamber_id = 4 then 'motor'  " + 
                "                                when sc.chamber_id = 5 then 'newTyre'  " + 
                "                                when sc.chamber_id = 6 then 'plastic'  " + 
                "                                when sc.chamber_id = 7 then 'unknown'  " + 
                "                                when sc.chamber_id = 8 then 'NA'  " + 
                "                                when sc.chamber_id = 9 then 'acm'  " + 
                "                                else 'NA' end as numEmp   " + 
                "                    , 'Unknown' as description  " + 
                "                    from sars_employer_detail sed  " + 
                "                    INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = sed.sic_code_2  " + 
                "                    WHERE sed.sars_filel_id = :monthId " + 
                "                    and (sed.trading_status = null or sed.trading_status = '0' or sed.trading_status = 'D')  " + 
                "                union  " + 
                "                    select sed.id as id  " + 
                "                                , case     when sc.chamber_id = 1 then 'auto'  " + 
                "                                when sc.chamber_id = 3 then 'metal'  " + 
                "                                when sc.chamber_id = 4 then 'motor'  " + 
                "                                when sc.chamber_id = 5 then 'newTyre'  " + 
                "                                when sc.chamber_id = 6 then 'plastic'  " + 
                "                                when sc.chamber_id = 7 then 'unknown'  " + 
                "                                when sc.chamber_id = 8 then 'NA'  " + 
                "                                when sc.chamber_id = 9 then 'acm'  " + 
                "                                else 'NA' end as numEmp   " + 
                "                    , 'Active' as description  " + 
                "                    from sars_employer_detail sed " + 
                "                     INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = sed.sic_code_2  " + 
                "                    WHERE sed.sars_filel_id = :monthId " + 
                "                    and (sed.trading_status = 'A')     " + 
                "                    union  " + 
                "                    select sed.id as id  " + 
                "                    , case     when sc.chamber_id = 1 then 'auto'  " + 
                "                    when sc.chamber_id = 3 then 'metal'  " + 
                "                    when sc.chamber_id = 4 then 'motor'  " + 
                "                    when sc.chamber_id = 5 then 'newTyre'  " + 
                "                    when sc.chamber_id = 6 then 'plastic'  " + 
                "                    when sc.chamber_id = 7 then 'unknown'  " + 
                "                    when sc.chamber_id = 8 then 'NA'  " + 
                "                    when sc.chamber_id = 9 then 'acm'  " + 
                "                    else 'NA' end as numEmp   " + 
                "                    , 'Stopped Trading' as description  " + 
                "                    from sars_employer_detail sed " + 
                "                     INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = sed.sic_code_2  " + 
                "                    WHERE sed.sars_filel_id = :monthId " + 
                "                    and (sed.trading_status = 'S')  " + 
                "                    union  " + 
                "                    select sed.id as id  " + 
                "                    , case     when sc.chamber_id = 1 then 'auto'  " + 
                "                    when sc.chamber_id = 3 then 'metal'  " + 
                "                    when sc.chamber_id = 4 then 'motor'  " + 
                "                    when sc.chamber_id = 5 then 'newTyre'  " + 
                "                    when sc.chamber_id = 6 then 'plastic'  " + 
                "                    when sc.chamber_id = 7 then 'unknown'  " + 
                "                    when sc.chamber_id = 8 then 'NA'  " + 
                "                    when sc.chamber_id = 9 then 'acm'  " + 
                "                    else 'NA' end as numEmp   " + 
                "                    , 'Deregistered' as description  " + 
                "                    from sars_employer_detail sed  " + 
                "                     INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = sed.sic_code_2  " + 
                "                    WHERE sed.sars_filel_id = :monthId " + 
                "                    and (sed.trading_status = 'Y')  " + 
                "                    union  " + 
                "                    select sed.id as id  " + 
                "                    , case     when sc.chamber_id = 1 then 'auto'  " + 
                "                    when sc.chamber_id = 3 then 'metal'  " + 
                "                    when sc.chamber_id = 4 then 'motor'  " + 
                "                    when sc.chamber_id = 5 then 'newTyre'  " + 
                "                    when sc.chamber_id = 6 then 'plastic'  " + 
                "                    when sc.chamber_id = 7 then 'unknown'  " + 
                "                    when sc.chamber_id = 8 then 'NA'  " + 
                "                    when sc.chamber_id = 9 then 'acm'  " + 
                "                    else 'NA' end as numEmp   " + 
                "                    , 'Estate' as description  " + 
                "                    from sars_employer_detail sed " + 
                "                     INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = sed.sic_code_2  " + 
                "                    WHERE sed.sars_filel_id = :monthId " + 
                "                    and (sed.trading_status = 'B')  " + 
                "                    union  " + 
                "                    select sed.id as id  " + 
                "                    , case     when sc.chamber_id = 1 then 'auto'  " + 
                "                    when sc.chamber_id = 3 then 'metal'  " + 
                "                    when sc.chamber_id = 4 then 'motor'  " + 
                "                    when sc.chamber_id = 5 then 'newTyre'  " + 
                "                    when sc.chamber_id = 6 then 'plastic'  " + 
                "                    when sc.chamber_id = 7 then 'unknown'  " + 
                "                    when sc.chamber_id = 8 then 'NA'  " + 
                "                    when sc.chamber_id = 9 then 'acm'  " + 
                "                    else 'NA' end as numEmp   " + 
                "                    , 'Cannot be traced' as description  " + 
                "                    from sars_employer_detail sed " + 
                "                     INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = sed.sic_code_2  " + 
                "                    WHERE sed.sars_filel_id = :monthId " + 
                "                    and (sed.trading_status = 'X') " + 
                "                    ) nums group by description, numEmp  " + 
                "                    ) counts  " + 
                "                    group by description";
        Map<String, Object> parameters = new Hashtable<String, Object>();
        parameters.put("monthId", monthId);
        return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class,parameters);
    }
	
	//REPORT 2 && REPORT 8.2
	public List<ByChamberReportBean> findLevyIncomeByYear() throws Exception {
		String hql = " SELECT "
		+ " 	concat('Y',a.theyear) as description "
		+ "     , a.theyear "
		+ "     , (-1 * SUM(if(b.id = 1, a.total, 0)) ) as bdAuto "
		+ "     , (-1 * SUM(if(b.id = 3, a.total, 0)) )as bdMetal "
		+ "     , (-1 * SUM(if(b.id = 4, a.total, 0)) )as bdMotor "
		+ "     , (-1 * SUM(if(b.id = 5, a.total, 0)) )as bdNewTyre "
		+ "     , (-1 * SUM(if(b.id = 6, a.total, 0)) )as bdPlastic "
		+ "     , (-1 * SUM(if(b.id = 7, a.total, 0)) )as bdUnknown "
		+ "     , (-1 * SUM(if(b.id = 8, a.total, 0)) )as bdNA "
		+ "     , (-1 * SUM(if(b.id = 9, a.total, 0)) )as bdAcm "
		+ " FROM ("
		+ " 	SELECT "
		+ " 		ref_no"
		+ " 		, sars_filel_id"
		+ " 		, theyear"
		+ " 		, SUM(total) as total"
		+ " 	FROM ("
		+ " 		SELECT "
		+ " 			ref_no"
		+ " 			, sars_filel_id"
		+ " 			, CAST(total AS DECIMAL(16,2)) as total "
		+ " 			, CASE WHEN MONTH(arrival_date_1) <= 2 THEN YEAR(arrival_date_1) ELSE YEAR(arrival_date_1) + 1 END as theyear"
		+ " 		FROM sars_levy_detail sld "
		+ " 	) rawdata "
		+ " 	GROUP BY ref_no, sars_filel_id, theyear"
		+ " ) a"
		+ " INNER JOIN ("
		+ " 	SELECT "
		+ " 		ref_no"
		+ " 		, sars_filel_id"
		+ " 		, id"
		+ " 	FROM ("
		+ " 		SELECT"
		+ " 			ser.ref_no"
		+ " 			, ser.sars_filel_id"
		+ " 			, CASE COALESCE(sc.chamber_id, 9) WHEN 1 THEN 1 "
		+ " 				WHEN 3 THEN 3 "
		+ " 				WHEN 4 THEN 4 "
		+ " 				WHEN 5 THEN 5 "
		+ " 				WHEN 6 THEN 6 "
		+ " 				WHEN 7 THEN 7 "
		+ " 				WHEN 8 THEN 8 "
		+ " 				WHEN 9 THEN 9 "
		+ " 				ELSE 9 END AS id "
		+ " 		FROM sars_employer_detail ser"
		+ " 		INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = ser.sic_code_2"
		+ " 	) as xps"
		+ " 	GROUP BY ref_no, sars_filel_id, id"
		+ " ) b on b.ref_no = a.ref_no AND b.sars_filel_id = a.sars_filel_id"
		+ " GROUP BY theyear;";

	return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class);
	}
	
	//REPORT 8.1
	public List<ByChamberReportBean>findGrantsProjectsByYear () throws Exception {
		String hql ="SELECT " + 
				"	concat('Y',theyear) as description " + 
				"    , man  " + 
				"    , dis " +  
				"FROM ( " + 
				"	SELECT " + 
				"		YEAR(arrival_date_1) as theyear " + 
				"		, (-1 * SUM(mandatory_levy)) as man " + 
				"		, (-1 * SUM(discretionary_levy)) as dis " + 
				"	FROM sars_levy_detail " + 
				" 	where arrival_date_1 is not null " +
				"	GROUP BY YEAR(arrival_date_1) " + 
				") x;";
		return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class);
	}
	
	//REPORT 6
	public ByChamberReportBean findSizeOfEmpoloyerContribution(Long monthId ) throws Exception {
		List<ByChamberReportBean> l = new ArrayList<>();
	String hql ="SELECT  " + 
			"	id   " + 
			"    , MAX(if(numEmp = 'small', cmp, 0)) as smallCount  " + 
			"	, MAX(if(numEmp = 'mid', cmp, 0)) as midCount  " + 
			"	, MAX(if(numEmp = 'large', cmp, 0)) as largeCount  " + 
			"FROM (  " + 
			"SELECT id, numEmp, count(ref_no) as cmp  " + 
			"FROM (  " + 
			"	SELECT id, ref_no, emp, case when emp <= 49 then 'small'   " + 
			"		when emp between 50 and 149 then 'mid'   " + 
			"		when emp >= 150 then 'large' end as numEmp  " + 
			"	FROM (  " + 
			"		SELECT 1 as id, sld.ref_no, MAX(no_employes_according_to_sars) as emp  " + 
			"		FROM sars_employer_detail sed  " + 
			"		/*INNER JOIN merseta.sars_files sf ON sf.id = sed.sars_filel_id  */ " + 
			"        inner join sars_levy_detail sld  on sld.ref_no = sed.ref_no and sld.sars_filel_id = sed.sars_filel_id " + 
			"        WHERE sld.sars_filel_id = :monthId " + 
			"		/*AND sf.for_month BETWEEN CAST(' + df.format(fromDate) + ' AS DATE)  AND CAST( ' + df.format(toDate) + ' AS DATE) */ " + 
			"		GROUP BY sld.ref_no  " + 
			"	) base ) t GROUP BY id, numEmp ) fin   " + 
			"GROUP BY id ";
	Map<String, Object> parameters = new Hashtable<String, Object>();
	parameters.put("monthId", monthId);
	l =  (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class,parameters);
	if (l != null && !l.isEmpty()) return l.get(0);
		else return null;
	}

	//REPORT 3 && REPORT 10
	public List<ByChamberReportBean> findChamberRevenueByMonth(Date fromDate, Date toDate) throws Exception {
		String hql = " SELECT " + 
				"    CONCAT(MONTHNAME(STR_TO_DATE(CONCAT(theyear,'-',theMonth,'-',01), '%Y-%m-%d')),' ',theyear) as description" + 
				"    , a.theyear " + 
				"    , a.theMonth " + 
				"    , (-1 * SUM(if(b.id = 1, a.total, 0)) ) as bdAuto " + 
				"    , (-1 * SUM(if(b.id = 3, a.total, 0)) ) as bdMetal " + 
				"    , (-1 * SUM(if(b.id = 4, a.total, 0)) ) as bdMotor " + 
				"    , (-1 * SUM(if(b.id = 5, a.total, 0)) ) as bdNewTyre " + 
				"    , (-1 * SUM(if(b.id = 6, a.total, 0)) ) as bdPlastic " + 
				"    , (-1 * SUM(if(b.id = 7, a.total, 0)) ) as bdUnknown " + 
				"    , (-1 * SUM(if(b.id = 8, a.total, 0)) ) as bdNA " + 
				"    , (-1 * SUM(if(b.id = 9, a.total, 0)) ) as bdAcm " + 
				"    , (-1 * SUM(a.total) )as bdTotal " + 
				"FROM ( " + 
				"    SELECT " + 
				"        ref_no " + 
				"        , sars_filel_id " + 
				"        , theyear " + 
				"        , theMonth " + 
				"        , SUM(total) as total " + 
				"    FROM ( " + 
				"        SELECT " + 
				"        ref_no " + 
				"        , sars_filel_id " + 
				"        , CAST(total AS DECIMAL(16,2)) as total " + 
				"        , YEAR(arrival_date_1) as theyear " + 
				"        , MONTH (sld.arrival_date_1) as theMonth  " + 
				"        FROM sars_levy_detail sld " + 
				"        INNER JOIN ( " + 
				"			SELECT id FROM merseta.sars_files " + 
				"			WHERE for_month BETWEEN CAST('" + df.format(fromDate) + "' AS DATE)  AND CAST( '" + df.format(toDate) + "' AS DATE) " +
				"        ) sf on sf.id = sld.sars_filel_id " + 
				"    ) rawdata " + 
				"    GROUP BY ref_no, sars_filel_id, theyear, theMonth " + 
				") a " + 
				"INNER JOIN ( " + 
				"    SELECT " + 
				"        ref_no " + 
				"        , sars_filel_id " + 
				"        , id " + 
				"    FROM " + 
				"    ( " + 
				"        SELECT " + 
				"            ser.ref_no " + 
				"            , ser.sars_filel_id " + 
				"            , CASE COALESCE(sc.chamber_id, 9) WHEN 1 THEN 1 " + 
				"                WHEN 3 THEN 3 " + 
				"                WHEN 4 THEN 4 " + 
				"                WHEN 5 THEN 5 " + 
				"                WHEN 6 THEN 6 " + 
				"                WHEN 7 THEN 7 " + 
				"                WHEN 8 THEN 8 " + 
				"                WHEN 9 THEN 9 " + 
				"                ELSE 9 END AS id " + 
				"        FROM sars_employer_detail ser " + 
				"		INNER JOIN ( " + 
				"			SELECT id FROM merseta.sars_files " + 
				"			WHERE for_month BETWEEN CAST('" + df.format(fromDate) + "' AS DATE)  AND CAST( '" + df.format(toDate) + "' AS DATE) " + 
				"        ) sf on sf.id = ser.sars_filel_id " + 
				"        INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = ser.sic_code_2 " + 
				"    ) as xps " + 
				"    GROUP BY ref_no, sars_filel_id, id " + 
				") b on b.ref_no = a.ref_no AND b.sars_filel_id = a.sars_filel_id " + 
				"GROUP BY theyear, theMonth";
	 	return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class);
	}
	
	//REPORT 3 && REPORT 10
	public List<ByChamberReportBean> findChamberRevenueByMonthAndSchemeYear(Date fromDate, Date toDate, Integer schemeYear) throws Exception {
		String hql = " SELECT " + 
				"    CONCAT(MONTHNAME(STR_TO_DATE(CONCAT(theyear,'-',theMonth,'-',01), '%Y-%m-%d')),' ',theyear) as description" + 
				"    , a.theyear " + 
				"    , a.theMonth " + 
				"    , (-1 * SUM(if(b.id = 1, a.total, 0)) ) as bdAuto " + 
				"    , (-1 * SUM(if(b.id = 3, a.total, 0)) ) as bdMetal " + 
				"    , (-1 * SUM(if(b.id = 4, a.total, 0)) ) as bdMotor " + 
				"    , (-1 * SUM(if(b.id = 5, a.total, 0)) ) as bdNewTyre " + 
				"    , (-1 * SUM(if(b.id = 6, a.total, 0)) ) as bdPlastic " + 
				"    , (-1 * SUM(if(b.id = 7, a.total, 0)) ) as bdUnknown " + 
				"    , (-1 * SUM(if(b.id = 8, a.total, 0)) ) as bdNA " + 
				"    , (-1 * SUM(if(b.id = 9, a.total, 0)) ) as bdAcm " + 
				"    , (-1 * SUM(a.total) )as bdTotal " + 
				"FROM ( " + 
				"    SELECT " + 
				"        ref_no " + 
				"        , sars_filel_id " + 
				"        , theyear " + 
				"        , theMonth " + 
				"        , SUM(total) as total " + 
				"    FROM ( " + 
				"        SELECT " + 
				"        ref_no " + 
				"        , sars_filel_id " + 
				"        , CAST(total AS DECIMAL(16,2)) as total " + 
				"        , YEAR(arrival_date_1) as theyear " + 
				"        , MONTH (sld.arrival_date_1) as theMonth  " + 
				"        FROM sars_levy_detail sld " + 
				"        INNER JOIN ( " + 
				"			SELECT id FROM merseta.sars_files " + 
				"			WHERE for_month BETWEEN CAST('" + df.format(fromDate) + "' AS DATE)  AND CAST( '" + df.format(toDate) + "' AS DATE) " +
				"        ) sf on sf.id = sld.sars_filel_id " + 
				"        where sld.scheme_year = '" + schemeYear + "'" + 
				"    ) rawdata " + 
				"    GROUP BY ref_no, sars_filel_id, theyear, theMonth " + 
				") a " + 
				"INNER JOIN ( " + 
				"    SELECT " + 
				"        ref_no " + 
				"        , sars_filel_id " + 
				"        , id " + 
				"    FROM " + 
				"    ( " + 
				"        SELECT " + 
				"            ser.ref_no " + 
				"            , ser.sars_filel_id " + 
				"            , CASE COALESCE(sc.chamber_id, 9) WHEN 1 THEN 1 " + 
				"                WHEN 3 THEN 3 " + 
				"                WHEN 4 THEN 4 " + 
				"                WHEN 5 THEN 5 " + 
				"                WHEN 6 THEN 6 " + 
				"                WHEN 7 THEN 7 " + 
				"                WHEN 8 THEN 8 " + 
				"                WHEN 9 THEN 9 " + 
				"                ELSE 9 END AS id " + 
				"        FROM sars_employer_detail ser " + 
				"		 INNER JOIN ( " + 
				"			SELECT id FROM merseta.sars_files " + 
				"			WHERE for_month BETWEEN CAST('" + df.format(fromDate) + "' AS DATE)  AND CAST( '" + df.format(toDate) + "' AS DATE) " + 
				"        ) sf on sf.id = ser.sars_filel_id " + 
				"        INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = ser.sic_code_2 " + 
				"    ) as xps " + 
				"    GROUP BY ref_no, sars_filel_id, id " + 
				") b on b.ref_no = a.ref_no AND b.sars_filel_id = a.sars_filel_id " + 
				"GROUP BY theyear, theMonth";
	 	return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class);
	}

	//REPORT 1.3.1
    public List<ByChamberReportBean> findActiveEmployersBetweenDate(SarsFiles monthId ) throws Exception {
       String hql =" SELECT description, " + 
               "                            MAX(if(numEmp = 'auto', num, 0)) as auto, " + 
               "                            MAX(if(numEmp = 'metal', num, 0)) as metal, " + 
               "                            MAX(if(numEmp = 'motor', num, 0)) as motor, " + 
               "                            MAX(if(numEmp = 'newTyre', num, 0)) as newTyre, " + 
               "                            MAX(if(numEmp = 'plastic', num, 0)) as plastic, " + 
               "                            MAX(if(numEmp = 'unknown', num, 0)) as unknown, " + 
               "                            MAX(if(numEmp = 'NA', num, 0)) as NA, " + 
               "                            MAX(if(numEmp = 'acm', num, 0)) as acm " + 
               "                        FROM (" + 
               "                            select description, count(id) as num, numEmp from ( " + 
               "                    select sed.id as id " + 
               "                                , case     when sc.chamber_id = 1 then 'auto' " + 
               "                                when sc.chamber_id = 3 then 'metal' " + 
               "                                when sc.chamber_id = 4 then 'motor' " + 
               "                                when sc.chamber_id = 5 then 'newTyre' " + 
               "                                when sc.chamber_id = 6 then 'plastic' " + 
               "                                when sc.chamber_id = 7 then 'unknown' " + 
               "                                when sc.chamber_id = 8 then 'NA' " + 
               "                                when sc.chamber_id = 9 then 'acm' " + 
               "                                else 'NA' end as numEmp  " + 
               "                    , 'Active' as description " + 
               "                    from sars_employer_detail sed" + 
               "                     INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = sed.sic_code_2 " + 
               "                    WHERE sed.sars_filel_id = :monthId" + 
               "                    and (sed.trading_status = 'A')    " + 
               "                    ) nums group by description, numEmp " + 
               "                    ) counts " + 
               "                    group by description";
       Map<String, Object> parameters = new Hashtable<String, Object>();
       parameters.put("monthId", monthId.getId());
        return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class,parameters);
    }
    
    //REPORT 1.3.3
    public ByChamberReportBean findLevyContributionsBySarsMonth(SarsFiles monthId ) throws Exception {
        List<ByChamberReportBean> l = new ArrayList<>();
        String hql="SELECT   " + 
                "                     'Levies received (80%)' as description " + 
                "                   , (-1 * SUM(if(b.id = 1, a.total, 0)) ) as bdAuto   " + 
                "                    , (-1 * SUM(if(b.id = 3, a.total, 0)) )as bdMetal   " + 
                "                    , (-1 * SUM(if(b.id = 4, a.total, 0)) )as bdMotor   " + 
                "                    , (-1 * SUM(if(b.id = 5, a.total, 0)) )as bdNewTyre   " + 
                "                    , (-1 * SUM(if(b.id = 6, a.total, 0)) )as bdPlastic   " + 
                "                    , (-1 * SUM(if(b.id = 7, a.total, 0)) )as bdUnknown   " + 
                "                    , (-1 * SUM(if(b.id = 8, a.total, 0)) )as bdNA   " + 
                "                    , (-1 * SUM(if(b.id = 9, a.total, 0)) )as bdAcm   " + 
                "                    , (-1 * SUM(a.total) )as bdTotal   " + 
                "                FROM (   " + 
                "                    SELECT   " + 
                "                        ref_no   " + 
                "                        , sars_filel_id   " + 
                "                        , SUM(total) as total   " + 
                "                    FROM (   " + 
                "                        SELECT   " + 
                "                        ref_no   " + 
                "                        , sars_filel_id   " + 
                "                        , CAST(total AS DECIMAL(16,2)) as total   " + 
                "                        FROM sars_levy_detail sld   " + 
                "                        INNER JOIN (SELECT id FROM merseta.sars_files where id = :monthId ) sf on sf.id = sld.sars_filel_id   " + 
                "                    ) rawdata   " + 
                "                    GROUP BY ref_no, sars_filel_id   " + 
                "                ) a   " + 
                "                INNER JOIN (   " + 
                "                    SELECT   " + 
                "                        ref_no   " + 
                "                        , sars_filel_id   " + 
                "                        , id   " + 
                "                    FROM   " + 
                "                    (   " + 
                "                        SELECT   " + 
                "                            ser.ref_no   " + 
                "                            , ser.sars_filel_id   " + 
                "                            , CASE COALESCE(sc.chamber_id, 9) WHEN 1 THEN 1   " + 
                "                                WHEN 3 THEN 3   " + 
                "                                WHEN 4 THEN 4   " + 
                "                                WHEN 5 THEN 5   " + 
                "                                WHEN 6 THEN 6   " + 
                "                                WHEN 7 THEN 7   " + 
                "                                WHEN 8 THEN 8   " + 
                "                                WHEN 9 THEN 9   " + 
                "                                ELSE 9 END AS id   " + 
                "                        FROM sars_employer_detail ser   " + 
                "                        INNER JOIN (   " + 
                "                            SELECT id FROM merseta.sars_files where id = :monthId  ) sf on sf.id = ser.sars_filel_id   " + 
                "                        INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = ser.sic_code_2   " + 
                "                    ) as xps   " + 
                "                    GROUP BY ref_no, sars_filel_id, id   " + 
                "                ) b on b.ref_no = a.ref_no AND b.sars_filel_id = a.sars_filel_id";
        Map<String, Object> parameters = new Hashtable<String, Object>();
        parameters.put("monthId", monthId.getId());
        l =  (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class,parameters);
        if (l != null && !l.isEmpty()) return l.get(0);
        else return null;
    }
	 
	 
	//REPORT 7
	public List<ByChamberReportBean> reportDataEmployerAnalysisBySizeAndSisCode(Long sarsFileId) throws Exception {
		String hql ="SELECT description, " + 
			"			MAX(if(numEmp = 'auto', num, 0)) as auto, " + 
			"			MAX(if(numEmp = 'metal', num, 0)) as metal, " + 
			"			MAX(if(numEmp = 'motor', num, 0)) as motor, " + 
			"			MAX(if(numEmp = 'newTyre', num, 0)) as newTyre, " + 
			"			MAX(if(numEmp = 'plastic', num, 0)) as plastic, " + 
			"			MAX(if(numEmp = 'unknown', num, 0)) as unknown, " + 
			"			MAX(if(numEmp = 'NA', num, 0)) as NA, " + 
			"			MAX(if(numEmp = 'acm', num, 0)) as acm " + 
			" FROM ( " + 
			" select no_employes_according_to_sars as description, count(id) as num, numEmp from (  " + 
			" select ser.id as id " + 
			"				, case 	when c.id = 1 then 'auto' " + 
			"				when c.id = 3 then 'metal' " + 
			"				when c.id = 4 then 'motor' " + 
			"				when c.id = 5 then 'newTyre' " + 
			"				when c.id = 6 then 'plastic' " + 
			"				when c.id = 7 then 'unknown' " + 
			"				when c.id = 8 then 'NA' " + 
			"				when c.id = 9 then 'acm' " + 
			"				else 'NA' end as numEmp  " + 
			" , 'Small' as no_employes_according_to_sars  " + 
			" from sars_employer_detail ser  " + 
			" left join sic_code sc on sc.code = ser.sic_code_2  " + 
			" left join chamber c on c.id = sc.chamber_id " + 
			" where ser.sars_filel_id = " + sarsFileId.intValue() + " " + 
			" and ser.no_employes_according_to_sars < 50 " + 
			" union " + 
			" select ser.id as id " + 
			"				, case 	when c.id = 1 then 'auto' " + 
			"				when c.id = 3 then 'metal' " + 
			"				when c.id = 4 then 'motor' " + 
			"				when c.id = 5 then 'newTyre' " + 
			"				when c.id = 6 then 'plastic' " + 
			"				when c.id = 7 then 'unknown' " + 
			"				when c.id = 8 then 'NA' " + 
			"				when c.id = 9 then 'acm' " + 
			"				else 'NA' end as numEmp    " + 
			" , 'Medium' as no_employes_according_to_sars  " + 
			" from sars_employer_detail ser  " + 
			" left join sic_code sc on sc.code = ser.sic_code_2  " + 
			" left join chamber c on c.id = sc.chamber_id  " + 
			" where ser.sars_filel_id = " + sarsFileId.intValue() + " " +  
			" and ser.no_employes_according_to_sars between 50 AND 150 	" + 
			" union " + 
			" select ser.id as id " + 
			"				, case 	when c.id = 1 then 'auto' " + 
			"				when c.id = 3 then 'metal' " + 
			"				when c.id = 4 then 'motor' " + 
			"				when c.id = 5 then 'newTyre' " + 
			"				when c.id = 6 then 'plastic' " + 
			"				when c.id = 7 then 'unknown' " + 
			"				when c.id = 8 then 'NA' " + 
			"				when c.id = 9 then 'acm' " + 
			"				else 'NA' end as numEmp  " + 
			", 'Large' as no_employes_according_to_sars  " + 
			"from sars_employer_detail ser  " + 
			"left join sic_code sc on sc.code = ser.sic_code_2  " + 
			"left join chamber c on c.id = sc.chamber_id  " + 
			"where ser.sars_filel_id = " + sarsFileId.intValue() + " " +  
			"and ser.no_employes_according_to_sars > 150 " + 
			") nums group by description, numEmp " + 
			") counts " + 
			"group by description";
		return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class);
	}
	
	//REPORT 7
	public List<ByChamberReportBean> reportDataContributingEmployerAnalysisBySizeAndSisCode(Long sarsFileId) throws Exception {
		String hql ="SELECT   " + 
				"	description,   " + 
				"	MAX(if(numEmp = 'auto', num, 0)) as auto,   " + 
				"	MAX(if(numEmp = 'metal', num, 0)) as metal,   " + 
				"	MAX(if(numEmp = 'motor', num, 0)) as motor,   " + 
				"	MAX(if(numEmp = 'newTyre', num, 0)) as newTyre,   " + 
				"	MAX(if(numEmp = 'plastic', num, 0)) as plastic,   " + 
				"	MAX(if(numEmp = 'unknown', num, 0)) as unknown,   " + 
				"	MAX(if(numEmp = 'NA', num, 0)) as NA,   " + 
				"	MAX(if(numEmp = 'acm', num, 0)) as acm   " + 
				"	  " + 
				"FROM (   " + 
				"	select   " + 
				"		no_employes_according_to_sars as description  " + 
				"		, count(id) as num  " + 
				"		, numEmp   " + 
				"	from (    " + 
				" 		select   " + 
				" 			ser.id as id   " + 
				"			, case 	  " + 
				"				when c.id = 1 then 'auto'   " + 
				"				when c.id = 3 then 'metal'   " + 
				"				when c.id = 4 then 'motor'   " + 
				"				when c.id = 5 then 'newTyre'   " + 
				"				when c.id = 6 then 'plastic'   " + 
				"				when c.id = 7 then 'unknown'   " + 
				"				when c.id = 8 then 'NA'   " + 
				"				when c.id = 9 then 'acm'   " + 
				"				else 'NA'   " + 
				"				end as numEmp    " + 
				"			, 'Small' as no_employes_according_to_sars    " + 
				"		from   " + 
				"			sars_employer_detail ser  " + 
				" 		left join   " + 
				" 			sic_code sc on sc.code = ser.sic_code_2    " + 
				" 		left join   " + 
				" 			chamber c on c.id = sc.chamber_id   " + 
				" 		inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    ref_no  " + 
				"                from   " + 
				"                    sars_levy_detail		  " + 
				"                where  " + 
				"                	sars_filel_id =  " + sarsFileId.intValue() + " " + 
				"            ) sld on sld.ref_no = ser.ref_no  " + 
				" 		where   " + 
				" 			ser.sars_filel_id = " + sarsFileId.intValue() + " " +  
				" 			and ser.no_employes_according_to_sars < 50  			  " + 
				" 			  " + 
				" 		union   " + 
				" 		  " + 
				" 		select   " + 
				" 			ser.id as id   " + 
				"			, case 	  " + 
				"				when c.id = 1 then 'auto'   " + 
				"				when c.id = 3 then 'metal'   " + 
				"				when c.id = 4 then 'motor'   " + 
				"				when c.id = 5 then 'newTyre'   " + 
				"				when c.id = 6 then 'plastic'   " + 
				"				when c.id = 7 then 'unknown'   " + 
				"				when c.id = 8 then 'NA'   " + 
				"				when c.id = 9 then 'acm'   " + 
				"				else 'NA'   " + 
				"				end as numEmp      " + 
				" 			, 'Medium' as no_employes_according_to_sars    " + 
				" 		from   " + 
				" 			sars_employer_detail ser    " + 
				"		left join   " + 
				"			sic_code sc on sc.code = ser.sic_code_2    " + 
				"		left join   " + 
				"			chamber c on c.id = sc.chamber_id    " + 
				"		inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    ref_no  " + 
				"                from   " + 
				"                    sars_levy_detail		  " + 
				"                where  " + 
				"                	sars_filel_id = " + sarsFileId.intValue() + " " +  
				"            ) sld on sld.ref_no = ser.ref_no  " + 
				"		where   " + 
				"			ser.sars_filel_id = "  + sarsFileId.intValue() + " " +  
				" 			and ser.no_employes_according_to_sars between 50 AND 150 	  " + 
				" 			  " + 
				" 		union   " + 
				" 		  " + 
				" 		select   " + 
				" 			ser.id as id   " + 
				"			, case 	  " + 
				"				when c.id = 1 then 'auto'   " + 
				"				when c.id = 3 then 'metal'   " + 
				"				when c.id = 4 then 'motor'   " + 
				"				when c.id = 5 then 'newTyre'   " + 
				"				when c.id = 6 then 'plastic'   " + 
				"				when c.id = 7 then 'unknown'   " + 
				"				when c.id = 8 then 'NA'   " + 
				"				when c.id = 9 then 'acm'   " + 
				"				else 'NA' end as numEmp    " + 
				"			, 'Large' as no_employes_according_to_sars    " + 
				"		from   " + 
				"			sars_employer_detail ser    " + 
				"		left join   " + 
				"			sic_code sc on sc.code = ser.sic_code_2    " + 
				"		left join   " + 
				"			chamber c on c.id = sc.chamber_id    " + 
				"		inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    ref_no  " + 
				"                from   " + 
				"                    sars_levy_detail		  " + 
				"                where  " + 
				"                	sars_filel_id =  " + sarsFileId.intValue() + " " +  
				"            ) sld on sld.ref_no = ser.ref_no  " + 
				"		where   " + 
				"			ser.sars_filel_id =  " + sarsFileId.intValue() + " " +  
				"			and ser.no_employes_according_to_sars > 150   " + "" + 
				"	) nums group by description, numEmp   " + 
				") counts   " + 
				"group by description";
		return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class);
	}
	
	public List<ByChamberReportBean> reportDataContributingEmployerAnalysisBySizeAndSisCodeWithProvinceAssigned(Long sarsFileId) throws Exception {
		String hql ="SELECT   " + 
				"	description,   " + 
				"	MAX(if(numEmp = 'auto', num, 0)) as auto,   " + 
				"	MAX(if(numEmp = 'metal', num, 0)) as metal,   " + 
				"	MAX(if(numEmp = 'motor', num, 0)) as motor,   " + 
				"	MAX(if(numEmp = 'newTyre', num, 0)) as newTyre,   " + 
				"	MAX(if(numEmp = 'plastic', num, 0)) as plastic,   " + 
				"	MAX(if(numEmp = 'unknown', num, 0)) as unknown,   " + 
				"	MAX(if(numEmp = 'NA', num, 0)) as NA,   " + 
				"	MAX(if(numEmp = 'acm', num, 0)) as acm   " + 
				"	  " + 
				"FROM (   " + 
				"	select   " + 
				"		no_employes_according_to_sars as description  " + 
				"		, count(id) as num  " + 
				"		, numEmp   " + 
				"	from (    " + 
				" 		select   " + 
				" 			ser.id as id   " + 
				"			, case 	  " + 
				"				when c.id = 1 then 'auto'   " + 
				"				when c.id = 3 then 'metal'   " + 
				"				when c.id = 4 then 'motor'   " + 
				"				when c.id = 5 then 'newTyre'   " + 
				"				when c.id = 6 then 'plastic'   " + 
				"				when c.id = 7 then 'unknown'   " + 
				"				when c.id = 8 then 'NA'   " + 
				"				when c.id = 9 then 'acm'   " + 
				"				else 'NA'   " + 
				"				end as numEmp    " + 
				"			, 'Small' as no_employes_according_to_sars    " + 
				"		from   " + 
				"			sars_employer_detail ser  " + 
				" 		left join   " + 
				" 			sic_code sc on sc.code = ser.sic_code_2    " + 
				" 		left join   " + 
				" 			chamber c on c.id = sc.chamber_id   " + 
				" 		inner join   " + 
				"            (  " + 
				"                select   " + 
				"	                 distinct "   + 
				"                    ref_no  " + 
				"                from   " + 
				"                    sars_levy_detail		  " + 
				"                where  " + 
				"                	sars_filel_id =  " + sarsFileId.intValue() + " " + 
				"            ) sld on sld.ref_no = ser.ref_no  " + 
				"		inner join " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " +
				" 		where   " + 
				" 			ser.sars_filel_id = " + sarsFileId.intValue() + " " +  
				" 			and ser.no_employes_according_to_sars < 50  			  " +  
				" 			  " + 
				" 		union   " + 
				" 		  " + 
				" 		select   " + 
				" 			ser.id as id   " + 
				"			, case 	  " + 
				"				when c.id = 1 then 'auto'   " + 
				"				when c.id = 3 then 'metal'   " + 
				"				when c.id = 4 then 'motor'   " + 
				"				when c.id = 5 then 'newTyre'   " + 
				"				when c.id = 6 then 'plastic'   " + 
				"				when c.id = 7 then 'unknown'   " + 
				"				when c.id = 8 then 'NA'   " + 
				"				when c.id = 9 then 'acm'   " + 
				"				else 'NA'   " + 
				"				end as numEmp      " + 
				" 			, 'Medium' as no_employes_according_to_sars    " + 
				" 		from   " + 
				" 			sars_employer_detail ser    " + 
				"		left join   " + 
				"			sic_code sc on sc.code = ser.sic_code_2    " + 
				"		left join   " + 
				"			chamber c on c.id = sc.chamber_id    " + 
				"		inner join   " + 
				"            (  " + 
				"                select   " + 
				"	                 distinct "   + 
				"                    ref_no  " + 
				"                from   " + 
				"                    sars_levy_detail		  " + 
				"                where  " + 
				"                	sars_filel_id = " + sarsFileId.intValue() + " " +  
				"            ) sld on sld.ref_no = ser.ref_no  " + 
				"		inner join " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " +
				"		where   " + 
				"			ser.sars_filel_id = "  + sarsFileId.intValue() + " " +  
				" 			and ser.no_employes_according_to_sars between 50 AND 150 	  " + 
				" 			  " + 
				" 		union   " + 
				" 		  " + 
				" 		select   " + 
				" 			ser.id as id   " + 
				"			, case 	  " + 
				"				when c.id = 1 then 'auto'   " + 
				"				when c.id = 3 then 'metal'   " + 
				"				when c.id = 4 then 'motor'   " + 
				"				when c.id = 5 then 'newTyre'   " + 
				"				when c.id = 6 then 'plastic'   " + 
				"				when c.id = 7 then 'unknown'   " + 
				"				when c.id = 8 then 'NA'   " + 
				"				when c.id = 9 then 'acm'   " + 
				"				else 'NA' end as numEmp    " + 
				"			, 'Large' as no_employes_according_to_sars    " + 
				"		from   " + 
				"			sars_employer_detail ser    " + 
				"		left join   " + 
				"			sic_code sc on sc.code = ser.sic_code_2    " + 
				"		left join   " + 
				"			chamber c on c.id = sc.chamber_id    " + 
				"		inner join   " + 
				"            (  " + 
				"                select   " + 
				"	                 distinct "   + 
				"                    ref_no  " + 
				"                from   " + 
				"                    sars_levy_detail		  " + 
				"                where  " + 
				"                	sars_filel_id =  " + sarsFileId.intValue() + " " +  
				"            ) sld on sld.ref_no = ser.ref_no  " + 
				"		inner join " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " +
				"		where   " + 
				"			ser.sars_filel_id =  " + sarsFileId.intValue() + " " +  
				"			and ser.no_employes_according_to_sars > 150   " + 
				"	) nums group by description, numEmp   " + 
				") counts   " + 
				"group by description";
		return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class);
	}
	
	
	//REPORT 9 - JOINERS
	public List<SarsEmployerDetailBean> findMersetaJoiners(Long sarsFileIdFrom,Long sarsFileIdTo) throws Exception {
		String hql =" SELECT sed.id " + 
				"    , sed.company_registration_number as companyRegistrationNumber " + 
				"    , sed.create_date as createDate " + 
				"    , sed.ref_no as refNo " + 
				"    , sed.registered_name_of_entity as registeredNameOfEntity " + 
				"    , sed.sic_code_2 as sicCode2 " + 
				"    , sed.trading_name as tradingName " + 
				"    , sed.trading_status as tradingStatus " + 
				"    , sed.sars_filel_id as sarsFiles " + 
				"    , sed.no_employes_according_to_sars as noEmployesAccordingToSARS FROM sars_levy_detail sld ,sars_employer_detail sed " + 
				"    WHERE sld.sars_filel_id = " + sarsFileIdTo + " and (sld.ref_no = sed.ref_no and sld.sars_filel_id = sed.sars_filel_id) " + 
				"    AND sld.ref_no not in (SELECT c.ref_no FROM sars_levy_detail c WHERE c.sars_filel_id = " + sarsFileIdFrom + ") ";
		return (List<SarsEmployerDetailBean>)super.nativeSelectSqlList(hql, SarsEmployerDetailBean.class);
	}
	
	//REPORT 9 - JOINERS V2
	public List<SarsEmployerDetailBean> findMersetaJoinersVersionTwo(Long sarsFileIdFrom,Long sarsFileIdTo) throws Exception {
		String hql =" SELECT sed.id " + 
				"    , sed.company_registration_number as companyRegistrationNumber " + 
				"    , sed.create_date as createDate " + 
				"    , sed.ref_no as refNo " + 
				"    , sed.registered_name_of_entity as registeredNameOfEntity " + 
				"    , sed.sic_code_2 as sicCode2 " + 
				"    , sed.trading_name as tradingName " + 
				"    , sed.trading_status as tradingStatus " + 
				"    , sed.sars_filel_id as sarsFiles " + 
				"    , sed.no_employes_according_to_sars as noEmployesAccordingToSARS " + 
				"    , c.code as chamberPassed  " + 
				"    FROM sars_levy_detail sld ,sars_employer_detail sed " +
				"    INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = sed.sic_code_2 " + 
				"    INNER JOIN chamber c on c.id = sc.chamber_id   " + 
				"    WHERE sld.sars_filel_id = " + sarsFileIdTo + " and (sld.ref_no = sed.ref_no and sld.sars_filel_id = sed.sars_filel_id) " + 
				"    AND sld.ref_no not in (SELECT c.ref_no FROM sars_levy_detail c WHERE c.sars_filel_id = " + sarsFileIdFrom + ") ";
		return (List<SarsEmployerDetailBean>)super.nativeSelectSqlList(hql, SarsEmployerDetailBean.class);
	}

	//REPORT 9 - LEAVERS
	public List<SarsEmployerDetailBean> findMersetaLeavers(Long sarsFileIdFrom,Long sarsFileIdTo) throws Exception {
		String hql =" SELECT sed.id " + 
				"    , sed.company_registration_number as companyRegistrationNumber " + 
				"    , sed.create_date as createDate " + 
				"    , sed.ref_no as refNo " + 
				"    , sed.registered_name_of_entity as registeredNameOfEntity " + 
				"    , sed.sic_code_2 as sicCode2 " + 
				"    , sed.trading_name as tradingName " + 
				"    , sed.trading_status as tradingStatus " + 
				"    , sed.sars_filel_id as sarsFiles " + 
				"    , sed.no_employes_according_to_sars as noEmployesAccordingToSARS FROM sars_levy_detail sld ,sars_employer_detail sed " + 
				"    WHERE sld.sars_filel_id = " + sarsFileIdFrom + " and (sld.ref_no = sed.ref_no and sld.sars_filel_id = sed.sars_filel_id) " + 
				"    AND sld.ref_no not in (SELECT c.ref_no FROM sars_levy_detail c WHERE c.sars_filel_id = " + sarsFileIdTo + ") ";
		return (List<SarsEmployerDetailBean>)super.nativeSelectSqlList(hql, SarsEmployerDetailBean.class);
	}
	
	//REPORT 9 - LEAVERS V2
	public List<SarsEmployerDetailBean> findMersetaLeaversVersionTwo(Long sarsFileIdFrom,Long sarsFileIdTo) throws Exception {
		String hql =" SELECT sed.id " + 
				"    , sed.company_registration_number as companyRegistrationNumber " + 
				"    , sed.create_date as createDate " + 
				"    , sed.ref_no as refNo " + 
				"    , sed.registered_name_of_entity as registeredNameOfEntity " + 
				"    , sed.sic_code_2 as sicCode2 " + 
				"    , sed.trading_name as tradingName " + 
				"    , sed.trading_status as tradingStatus " + 
				"    , sed.sars_filel_id as sarsFiles " + 
				"    , sed.no_employes_according_to_sars as noEmployesAccordingToSARS " + 
				"    , c.code as chamberPassed "+
				"    FROM sars_levy_detail sld ,sars_employer_detail sed " + 
				"    INNER JOIN (SELECT code, chamber_id FROM sic_code GROUP BY code, chamber_id) sc on sc.code = sed.sic_code_2   " + 
				"    INNER JOIN chamber c on c.id = sc.chamber_id  "+
				"    WHERE sld.sars_filel_id = " + sarsFileIdFrom + " and (sld.ref_no = sed.ref_no and sld.sars_filel_id = sed.sars_filel_id) " + 
				"    AND sld.ref_no not in (SELECT c.ref_no FROM sars_levy_detail c WHERE c.sars_filel_id = " + sarsFileIdTo + ") ";
		return (List<SarsEmployerDetailBean>)super.nativeSelectSqlList(hql, SarsEmployerDetailBean.class);
	}
	
	/*
	 * Report 2 - Levy Income by Year by Chamber
	 * JMB
	 */
	public List<ByChamberReportBean> findLevyIncomeBySchemeYears(List<Integer> schemeYearsAvalaible) throws Exception {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT description, " + 
				"	MIN(if(numEmp = 'auto', num, 0)) as bdAuto, " + 
				"	MIN(if(numEmp = 'metal', num, 0)) as bdMetal, " + 
				"	MIN(if(numEmp = 'motor', num, 0)) as bdMotor, " + 
				"	MIN(if(numEmp = 'newTyre', num, 0)) as bdNewTyre, " + 
				"	MIN(if(numEmp = 'plastic', num, 0)) as bdPlastic, " + 
				"	MIN(if(numEmp = 'unknown', num, 0)) as bdUnknown, " + 
				"	MIN(if(numEmp = 'NA', num, 0)) as bdNA, " + 
				"	MIN(if(numEmp = 'acm', num, 0)) as bdAcm " + 
				" FROM ( " + 
				"	select yearIndicator as description, round(sum(totalD),2) as num, numEmp from (  ");
		int count = 1;
		for (Integer year : schemeYearsAvalaible) {
			if (year != null) {
				hql.append("select " + 
						"			'Y"+year+"' as yearIndicator " + 
						"			,case when d.id = 1 then 'auto'  " + 
						"				when d.id = 3 then 'metal'  " + 
						"				when d.id = 4 then 'motor'  " + 
						"				when d.id = 5 then 'newTyre'  " + 
						"				when d.id = 6 then 'plastic'  " + 
						"				when d.id = 7 then 'unknown'  " + 
						"				when d.id = 8 then 'NA'  " + 
						"				when d.id = 9 then 'acm'  " + 
						"				else 'NA' end as numEmp " + 
						"			, CAST(sum(a.total) AS DECIMAL(16,2)) as totalD  " + 
						"		from sars_levy_detail a , sars_employer_detail b,  sic_code c, chamber d  " + 
						"		where a.sars_filel_id = b.sars_filel_id  " + 
						"			and a.ref_no = b.ref_no " + 
						"			and b.sic_code_2 = c.code " + 
						"			and c.chamber_id = d.id  " + 
						"			and a.scheme_year = '"+year+"' " + 
						"		group by d.id ");
				if (count != schemeYearsAvalaible.size()) {
					hql.append(" union ");
				} 
			}
			count++;
		}
		schemeYearsAvalaible = null;
		hql.append(" ) nums group by description, numEmp " + 
				" ) counts " + 
				" group by description ");
		return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql.toString(), ByChamberReportBean.class);
	}
	/*
	 * Report 8 - Historical Grants and Projects Analysis
	 * Mandatory Grants By Chamber
	 * JMB
	 */
	public List<ByChamberReportBean> findMandatoryGrantDataBySchemeYearsGroupByChamber(List<Integer> schemeYearsAvalaible) throws Exception {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT description, " + 
				"	MIN(if(numEmp = 'auto', num, 0)) as bdAuto, " + 
				"	MIN(if(numEmp = 'metal', num, 0)) as bdMetal, " + 
				"	MIN(if(numEmp = 'motor', num, 0)) as bdMotor, " + 
				"	MIN(if(numEmp = 'newTyre', num, 0)) as bdNewTyre, " + 
				"	MIN(if(numEmp = 'plastic', num, 0)) as bdPlastic, " + 
				"	MIN(if(numEmp = 'unknown', num, 0)) as bdUnknown, " + 
				"	MIN(if(numEmp = 'NA', num, 0)) as bdNA, " + 
				"	MIN(if(numEmp = 'acm', num, 0)) as bdAcm " + 
				" FROM ( " + 
				"	select yearIndicator as description, round(sum(totalD),2) as num, numEmp from (  ");
		int count = 1;
		for (Integer year : schemeYearsAvalaible) {
			if (year != null) {
				hql.append("select " + 
						"			'Y"+year+"' as yearIndicator " + 
						"			,case when d.id = 1 then 'auto'  " + 
						"				when d.id = 3 then 'metal'  " + 
						"				when d.id = 4 then 'motor'  " + 
						"				when d.id = 5 then 'newTyre'  " + 
						"				when d.id = 6 then 'plastic'  " + 
						"				when d.id = 7 then 'unknown'  " + 
						"				when d.id = 8 then 'NA'  " + 
						"				when d.id = 9 then 'acm'  " + 
						"				else 'NA' end as numEmp " + 
						"			, CAST(sum(a.mandatory_levy) AS DECIMAL(16,2)) as totalD  " + 
						"		from sars_levy_detail a , sars_employer_detail b,  sic_code c, chamber d  " + 
						"		where a.sars_filel_id = b.sars_filel_id  " + 
						"			and a.ref_no = b.ref_no " + 
						"			and b.sic_code_2 = c.code " + 
						"			and c.chamber_id = d.id  " + 
						"			and a.scheme_year = '"+year+"' " + 
						"		group by d.id ");
				if (count != schemeYearsAvalaible.size()) {
					hql.append(" union ");
				} 
			}
			count++;
		}
		schemeYearsAvalaible = null;
		hql.append(" ) nums group by description, numEmp " + 
				" ) counts " + 
				" group by description ");
		return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql.toString(), ByChamberReportBean.class);
	}
	
	
	public List<ByChamberReportBean> levyStatusOfEmployerByChamber() throws Exception{
		String sql = " SELECT tradingStatusType, " + 
				" MAX(if(numEmp = 'auto', num, 0)) as auto, " + 
				" MAX(if(numEmp = 'metal', num, 0)) as metal," + 
				" MAX(if(numEmp = 'motor', num, 0)) as motor," + 
				" MAX(if(numEmp = 'newTyre', num, 0)) as newTyre," + 
				" MAX(if(numEmp = 'plastic', num, 0)) as plastic," + 
				" MAX(if(numEmp = 'unknown', num, 0)) as unknown," + 
				" MAX(if(numEmp = 'NA', num, 0)) as NA," + 
				" MAX(if(numEmp = 'acm', num, 0)) as acm" + 
				" FROM ( " + 
				"	select tradingStatusType, count(id) as num, numEmp from ( " + 
				"		select ser.id as id" + 
				"			, case 	when c.id = 1 then 'auto' " + 
				"					when c.id = 3 then 'metal' " + 
				"					when c.id = 4 then 'motor' " + 
				"					when c.id = 5 then 'newTyre' " + 
				"					when c.id = 6 then 'plastic' " + 
				"					when c.id = 7 then 'unknown' " + 
				"					when c.id = 8 then 'NA' " + 
				"					when c.id = 9 then 'Acm' " + 
				"					else 'NA' end as numEmp  " + 
				"			, 'Unknown' as tradingStatusType " + 
				"		from sars_employer_detail ser" + 
				"		left join sic_code sc on sc.code = ser.sic_code_2 " + 
				"		left join chamber c on c.id = sc.chamber_id" + 
				"		where (ser.trading_status = null or ser.trading_status = '0' or ser.trading_status = 'D')" + 
				"        ) nums group by tradingStatusType, numEmp" + 
				" ) counts " + 
				" group by tradingStatusType";
		
		return (List<ByChamberReportBean>)super.nativeSelectSqlList(sql, ByChamberReportBean.class);
	}
	
	public List<ByChamberReportBean> findEmployersBySize(Date fromDate, Date toDate ) throws Exception {
	String hql ="SELECT description, " 
			+"MAX(if(numEmp = 'auto', num, 0)) as auto, "
			+"MAX(if(numEmp = 'metal', num, 0)) as metal, "
			+"MAX(if(numEmp = 'motor', num, 0)) as motor, "
			+"MAX(if(numEmp = 'newTyre', num, 0)) as newTyre, "
			+"MAX(if(numEmp = 'plastic', num, 0)) as plastic, "
			+"MAX(if(numEmp = 'unknown', num, 0)) as unknown, "
			+"MAX(if(numEmp = 'NA', num, 0)) as NA, "
			+"MAX(if(numEmp = 'acm', num, 0)) as acm "
				+"FROM ( "
				+"select no_employes_according_to_sars as description, count(id) as num, numEmp from ( " 
				+"select ser.id as id "
								+", case 	when c.id = 1 then 'auto' "
								+"when c.id = 3 then 'metal' "
								+"when c.id = 4 then 'motor' "
								+"when c.id = 5 then 'newTyre' "
								+"when c.id = 6 then 'plastic' "
								+"when c.id = 7 then 'unknown' "
								+"when c.id = 8 then 'NA' "
								+"when c.id = 9 then 'acm' "
								+"else 'NA' end as numEmp  "
				+",'Small' as no_employes_according_to_sars  "
				+"from sars_levy_detail sld " 
				+"left join sars_employer_detail ser on ser.ref_no = sld.ref_no "
				+"left join sic_code sc on sc.code = ser.sic_code_2  "
				+"left join chamber c on c.id = sc.chamber_id "
				+"where sld.arrival_date_1 between CAST('" + df.format(fromDate) + "' AS DATE)  AND CAST( '" + df.format(toDate) + "' AS DATE) "
				+"and ser.no_employes_according_to_sars < 50 "
				+"union "
				+"select ser.id as id "
								+", case 	when c.id = 1 then 'auto' "
								+"when c.id = 3 then 'metal' "
								+"when c.id = 4 then 'motor' "
								+"when c.id = 5 then 'newTyre' "
								+"when c.id = 6 then 'plastic' "
								+"when c.id = 7 then 'unknown' "
								+"when c.id = 8 then 'NA' "
								+"when c.id = 9 then 'acm' "
								+"else 'NA' end as numEmp  "  
				+", 'Medium' as no_employes_according_to_sars  "
				+"from sars_levy_detail sld " 
				+"left join sars_employer_detail ser on ser.ref_no = sld.ref_no "
				+"left join sic_code sc on sc.code = ser.sic_code_2 " 
				+"left join chamber c on c.id = sc.chamber_id "
				+"where sld.arrival_date_1 between CAST('" + df.format(fromDate) + "' AS DATE)  AND CAST( '" + df.format(toDate) + "' AS DATE) "
				+"and ser.no_employes_according_to_sars between 50 AND 150 "	
				+"union "
				+"select ser.id as id "
								+", case 	when c.id = 1 then 'auto' "
								+"when c.id = 3 then 'metal' "
								+"when c.id = 4 then 'motor' "
								+"when c.id = 5 then 'newTyre' "
								+"when c.id = 6 then 'plastic' "
								+"when c.id = 7 then 'unknown' "
								+"when c.id = 8 then 'NA' "
								+"when c.id = 9 then 'acm' "
								+"else 'NA' end as numEmp  "
				+", 'Large' as no_employes_according_to_sars " 
				+"from sars_levy_detail sld "
				+"left join sars_employer_detail ser on ser.ref_no = sld.ref_no "
				+"left join sic_code sc on sc.code = ser.sic_code_2 " 
				+"left join chamber c on c.id = sc.chamber_id "
				+"where sld.arrival_date_1 between CAST('" + df.format(fromDate) + "' AS DATE)  AND CAST( '" + df.format(toDate) + "' AS DATE) "
				+"and ser.no_employes_according_to_sars >150 "
				+") nums group by description, numEmp "
				+") counts " 
				+"group by description ";

	return (List<ByChamberReportBean>)super.nativeSelectSqlList(hql, ByChamberReportBean.class);
	}
	
	
	public List<SarsProvinceCountBean> employerCountBySarsFileAndChamberIdGroupedByProvince(Long sarsFileId, Long chamberId) throws Exception {
		String hql = "SELECT   " + 
				"	description,   " + 
				"	MAX(if(numEmp = 'GP', num, 0)) as gauteng,   " + 
				"	MAX(if(numEmp = 'EC', num, 0)) as easternCape,   " + 
				"	MAX(if(numEmp = 'FS', num, 0)) as freeState,   " + 
				"	MAX(if(numEmp = 'KZN', num, 0)) as kwazulunatal,   " + 
				"	MAX(if(numEmp = 'LP', num, 0)) as limpopo,   " + 
				"	MAX(if(numEmp = 'MP', num, 0)) as mpumalanga,   " + 
				"	MAX(if(numEmp = 'NC', num, 0)) as northernCape,   " + 
				"	MAX(if(numEmp = 'NW', num, 0)) as northWest,  " + 
				"	MAX(if(numEmp = 'WC', num, 0)) as westernCape,  " + 
				"	MAX(if(numEmp = 'N', num, 0)) as saNational,  " + 
				"	MAX(if(numEmp = 'X', num, 0)) as outsideSA   " + 
				"FROM (      " + 
				"    select   " + 
				"        no_employes_according_to_sars as description  " + 
				"        , count(id) as num  " + 
				"        , numEmp      " + 
				"    from (  " + 
				"        select     " + 
				"            ser.id as id     " + 
				"            , p.code as numEmp      " + 
				"            , 'Small' as no_employes_according_to_sars      " + 
				"        from     " + 
				"            sars_employer_detail ser   " + 
				"        inner join    " + 
				"            (  " + 
				"                select   " + 
				"                    code  " + 
				"                    , chamber_id   " + 
				"                from   " + 
				"                    sic_code   " + 
				"                group by   " + 
				"                    code  " + 
				"                    , chamber_id  " + 
				"            ) sc on sc.code = ser.sic_code_2 and sc.chamber_id = " + chamberId.intValue() + 
				"        inner join     " + 
				"            chamber c on c.id = sc.chamber_id  " + 
				"        inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " + 
				"        inner join     " + 
				"            (    " + 
				"                select     " + 
				"	                 distinct "   + 
				"                    ref_no    " + 
				"                from     " + 
				"                    sars_levy_detail		    " + 
				"                where    " + 
				"                    sars_filel_id = " + sarsFileId.intValue() +  
				"            ) sld on sld.ref_no = ser.ref_no    " + 
				"        where     " + 
				"            ser.sars_filel_id =  " + sarsFileId.intValue() + 
				"            and ser.no_employes_according_to_sars < '50'  " + 
				"          " + 
				"          " + 
				"        UNION   " + 
				"          " + 
				"        select     " + 
				"            ser.id as id     " + 
				"            , p.code as numEmp      " + 
				"            , 'Medium' as no_employes_according_to_sars      " + 
				"        from     " + 
				"            sars_employer_detail ser   " + 
				"        inner join    " + 
				"            (  " + 
				"                select   " + 
				"                    code  " + 
				"                    , chamber_id   " + 
				"                from   " + 
				"                    sic_code   " + 
				"                group by   " + 
				"                    code  " + 
				"                    , chamber_id  " + 
				"            ) sc on sc.code = ser.sic_code_2 and sc.chamber_id = " + chamberId.intValue() +  
				"        inner join     " + 
				"            chamber c on c.id = sc.chamber_id  " + 
				"        inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " + 
				"        inner join     " + 
				"            (    " + 
				"                select     " + 
				"	                 distinct "   + 
				"                    ref_no    " + 
				"                from     " + 
				"                    sars_levy_detail		    " + 
				"                where    " + 
				"                    sars_filel_id =  " + sarsFileId.intValue() +  
				"            ) sld on sld.ref_no = ser.ref_no    " + 
				"        where     " + 
				"            ser.sars_filel_id =  "  + sarsFileId.intValue() + 
				"            and ser.no_employes_according_to_sars between '50' AND '150'  " + 
				"          " + 
				"        UNION   " + 
				"          " + 
				"        select     " + 
				"            ser.id as id     " + 
				"            , p.code as numEmp      " + 
				"            , 'Large' as no_employes_according_to_sars      " + 
				"        from     " + 
				"            sars_employer_detail ser   " + 
				"        inner join    " + 
				"            (  " + 
				"                select   " + 
				"                    code  " + 
				"                    , chamber_id   " + 
				"                from   " + 
				"                    sic_code   " + 
				"                group by   " + 
				"                    code  " + 
				"                    , chamber_id  " + 
				"            ) sc on sc.code = ser.sic_code_2 and sc.chamber_id = " + chamberId.intValue() + 
				"        inner join     " + 
				"            chamber c on c.id = sc.chamber_id  " + 
				"        inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " + 
				"        inner join     " + 
				"            (    " + 
				"                select     " + 
				"	                 distinct "   + 
				"                    ref_no    " + 
				"                from     " + 
				"                    sars_levy_detail		    " + 
				"                where    " + 
				"                    sars_filel_id =  " + sarsFileId.intValue() + 
				"            ) sld on sld.ref_no = ser.ref_no    " + 
				"        where     " + 
				"            ser.sars_filel_id =  " + sarsFileId.intValue() + 
				"            and ser.no_employes_according_to_sars > '150'  " + 
				"    ) as base   " + 
				"    group by   " + 
				"        description  " + 
				"        , numEmp  " + 
				") counts  " + 
				"group by   " + 
				"	description";
		return (List<SarsProvinceCountBean>)super.nativeSelectSqlList(hql, SarsProvinceCountBean.class);
	}
	
	public List<SarsProvinceCountBean> contributingEmployerCountBySarsFileGroupedByProvince(Long sarsFileId) throws Exception {
		String hql = "SELECT   " + 
				"	description,   " + 
				"	MAX(if(numEmp = 'GP', num, 0)) as gauteng,   " + 
				"	MAX(if(numEmp = 'EC', num, 0)) as easternCape,   " + 
				"	MAX(if(numEmp = 'FS', num, 0)) as freeState,   " + 
				"	MAX(if(numEmp = 'KZN', num, 0)) as kwazulunatal,   " + 
				"	MAX(if(numEmp = 'LP', num, 0)) as limpopo,   " + 
				"	MAX(if(numEmp = 'MP', num, 0)) as mpumalanga,   " + 
				"	MAX(if(numEmp = 'NC', num, 0)) as northernCape,   " + 
				"	MAX(if(numEmp = 'NW', num, 0)) as northWest,  " + 
				"	MAX(if(numEmp = 'WC', num, 0)) as westernCape,  " + 
				"	MAX(if(numEmp = 'N', num, 0)) as saNational,  " + 
				"	MAX(if(numEmp = 'X', num, 0)) as outsideSA   " + 
				"FROM (      " + 
				"    select   " + 
				"        no_employes_according_to_sars as description  " + 
				"        , count(id) as num  " + 
				"        , numEmp      " + 
				"    from (  " + 
				"        select     " + 
				"            ser.id as id     " + 
				"            , p.code as numEmp      " + 
				"            , 'Small' as no_employes_according_to_sars      " + 
				"        from     " + 
				"            sars_employer_detail ser   " + 
				"        inner join    " + 
				"            (  " + 
				"                select   " + 
				"                    code  " + 
				"                    , chamber_id   " + 
				"                from   " + 
				"                    sic_code   " + 
				"                group by   " + 
				"                    code  " + 
				"                    , chamber_id  " + 
				"            ) sc on sc.code = ser.sic_code_2   " +
				"        inner join     " + 
				"            chamber c on c.id = sc.chamber_id  " + 
				"        inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " + 
				"        inner join     " + 
				"            (    " + 
				"                select " + 
				"	                 distinct "   + 
				"                    ref_no    " + 
				"                from     " + 
				"                    sars_levy_detail		    " + 
				"                where    " + 
				"                    sars_filel_id = " + sarsFileId.intValue() +  
				"            ) sld on sld.ref_no = ser.ref_no    " + 
				"        where     " + 
				"            ser.sars_filel_id =  " + sarsFileId.intValue() + 
				"            and ser.no_employes_according_to_sars < '50'  " + 
				"          " + 
				"          " + 
				"        UNION   " + 
				"          " + 
				"        select     " + 
				"            ser.id as id     " + 
				"            , p.code as numEmp      " + 
				"            , 'Medium' as no_employes_according_to_sars      " + 
				"        from     " + 
				"            sars_employer_detail ser   " + 
				"        inner join    " + 
				"            (  " + 
				"                select   " + 
				"                    code  " + 
				"                    , chamber_id   " + 
				"                from   " + 
				"                    sic_code   " + 
				"                group by   " + 
				"                    code  " + 
				"                    , chamber_id  " + 
				"            ) sc on sc.code = ser.sic_code_2   " + 
				"        inner join     " + 
				"            chamber c on c.id = sc.chamber_id  " + 
				"        inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " + 
				"        inner join     " + 
				"            (    " + 
				"                select     " + 
				"	                 distinct "   + 
				"                    ref_no    " + 
				"                from     " + 
				"                    sars_levy_detail		    " + 
				"                where    " + 
				"                    sars_filel_id =  " + sarsFileId.intValue() +  
				"            ) sld on sld.ref_no = ser.ref_no    " + 
				"        where     " + 
				"            ser.sars_filel_id =  "  + sarsFileId.intValue() + 
				"            and ser.no_employes_according_to_sars between '50' AND '150'  " + 
				"          " + 
				"        UNION   " + 
				"          " + 
				"        select     " + 
				"            ser.id as id     " + 
				"            , p.code as numEmp      " + 
				"            , 'Large' as no_employes_according_to_sars      " + 
				"        from     " + 
				"            sars_employer_detail ser   " + 
				"        inner join    " + 
				"            (  " + 
				"                select   " + 
				"                    code  " + 
				"                    , chamber_id   " + 
				"                from   " + 
				"                    sic_code   " + 
				"                group by   " + 
				"                    code  " + 
				"                    , chamber_id  " + 
				"            ) sc on sc.code = ser.sic_code_2   " +
				"        inner join     " + 
				"            chamber c on c.id = sc.chamber_id  " + 
				"        inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " + 
				"        inner join     " + 
				"            (    " + 
				"                select     " + 
				"	                 distinct "   + 
				"                    ref_no    " + 
				"                from     " + 
				"                    sars_levy_detail		    " + 
				"                where    " + 
				"                    sars_filel_id =  " + sarsFileId.intValue() + 
				"            ) sld on sld.ref_no = ser.ref_no    " + 
				"        where     " + 
				"            ser.sars_filel_id =  " + sarsFileId.intValue() + 
				"            and ser.no_employes_according_to_sars > '150'  " + 
				"    ) as base   " + 
				"    group by   " + 
				"        description  " + 
				"        , numEmp  " + 
				") counts  " + 
				"group by   " + 
				"	description";
		return (List<SarsProvinceCountBean>)super.nativeSelectSqlList(hql, SarsProvinceCountBean.class);
	}
	
	public List<SarsProvinceCountBean> allEmployersCountBySarsFileGroupedByProvince(Long sarsFileId) throws Exception {
		String hql = "SELECT   " + 
				"	description,   " + 
				"	MAX(if(numEmp = 'GP', num, 0)) as gauteng,   " + 
				"	MAX(if(numEmp = 'EC', num, 0)) as easternCape,   " + 
				"	MAX(if(numEmp = 'FS', num, 0)) as freeState,   " + 
				"	MAX(if(numEmp = 'KZN', num, 0)) as kwazulunatal,   " + 
				"	MAX(if(numEmp = 'LP', num, 0)) as limpopo,   " + 
				"	MAX(if(numEmp = 'MP', num, 0)) as mpumalanga,   " + 
				"	MAX(if(numEmp = 'NC', num, 0)) as northernCape,   " + 
				"	MAX(if(numEmp = 'NW', num, 0)) as northWest,  " + 
				"	MAX(if(numEmp = 'WC', num, 0)) as westernCape,  " + 
				"	MAX(if(numEmp = 'N', num, 0)) as saNational,  " + 
				"	MAX(if(numEmp = 'X', num, 0)) as outsideSA   " + 
				"FROM (      " + 
				"    select   " + 
				"        no_employes_according_to_sars as description  " + 
				"        , count(id) as num  " + 
				"        , numEmp      " + 
				"    from (  " + 
				"        select     " + 
				"            ser.id as id     " + 
				"            , p.code as numEmp      " + 
				"            , 'Small' as no_employes_according_to_sars      " + 
				"        from     " + 
				"            sars_employer_detail ser   " + 
				"        inner join    " + 
				"            (  " + 
				"                select   " + 
				"                    code  " + 
				"                    , chamber_id   " + 
				"                from   " + 
				"                    sic_code   " + 
				"                group by   " + 
				"                    code  " + 
				"                    , chamber_id  " + 
				"            ) sc on sc.code = ser.sic_code_2   " +
				"        inner join     " + 
				"            chamber c on c.id = sc.chamber_id  " + 
				"        inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " + 
				"        inner join     " + 
				"            (    " + 
				"                select     " + 
				"                    ref_no    " + 
				"                from     " + 
				"                    sars_levy_detail		    " + 
				"                where    " + 
				"                    sars_filel_id = " + sarsFileId.intValue() +  
				"            ) sld on sld.ref_no = ser.ref_no    " + 
				"        where     " + 
				"            ser.sars_filel_id =  " + sarsFileId.intValue() + 
				"            and ser.no_employes_according_to_sars < '50'  " + 
				"          " + 
				"          " + 
				"        UNION   " + 
				"          " + 
				"        select     " + 
				"            ser.id as id     " + 
				"            , p.code as numEmp      " + 
				"            , 'Medium' as no_employes_according_to_sars      " + 
				"        from     " + 
				"            sars_employer_detail ser   " + 
				"        inner join    " + 
				"            (  " + 
				"                select   " + 
				"                    code  " + 
				"                    , chamber_id   " + 
				"                from   " + 
				"                    sic_code   " + 
				"                group by   " + 
				"                    code  " + 
				"                    , chamber_id  " + 
				"            ) sc on sc.code = ser.sic_code_2   " + 
				"        inner join     " + 
				"            chamber c on c.id = sc.chamber_id  " + 
				"        inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " + 
				"        inner join     " + 
				"            (    " + 
				"                select     " + 
				"                    ref_no    " + 
				"                from     " + 
				"                    sars_levy_detail		    " + 
				"                where    " + 
				"                    sars_filel_id =  " + sarsFileId.intValue() +  
				"            ) sld on sld.ref_no = ser.ref_no    " + 
				"        where     " + 
				"            ser.sars_filel_id =  "  + sarsFileId.intValue() + 
				"            and ser.no_employes_according_to_sars between '50' AND '150'  " + 
				"          " + 
				"        UNION   " + 
				"          " + 
				"        select     " + 
				"            ser.id as id     " + 
				"            , p.code as numEmp      " + 
				"            , 'Large' as no_employes_according_to_sars      " + 
				"        from     " + 
				"            sars_employer_detail ser   " + 
				"        inner join    " + 
				"            (  " + 
				"                select   " + 
				"                    code  " + 
				"                    , chamber_id   " + 
				"                from   " + 
				"                    sic_code   " + 
				"                group by   " + 
				"                    code  " + 
				"                    , chamber_id  " + 
				"            ) sc on sc.code = ser.sic_code_2   " +
				"        inner join     " + 
				"            chamber c on c.id = sc.chamber_id  " + 
				"        inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " + 
				"        inner join     " + 
				"            (    " + 
				"                select     " + 
				"                    ref_no    " + 
				"                from     " + 
				"                    sars_levy_detail		    " + 
				"                where    " + 
				"                    sars_filel_id =  " + sarsFileId.intValue() + 
				"            ) sld on sld.ref_no = ser.ref_no    " + 
				"        where     " + 
				"            ser.sars_filel_id =  " + sarsFileId.intValue() + 
				"            and ser.no_employes_according_to_sars > '150'  " + 
				"    ) as base   " + 
				"    group by   " + 
				"        description  " + 
				"        , numEmp  " + 
				") counts  " + 
				"group by   " + 
				"	description";
		return (List<SarsProvinceCountBean>)super.nativeSelectSqlList(hql, SarsProvinceCountBean.class);
	}
	
	public List<SarsProvinceCountBean> activeContributingEmployersCountBySarsFileGroupedByProvince(Long sarsFileId) throws Exception {
		String hql = "SELECT   " + 
				"	description,   " + 
				"	MAX(if(numEmp = 'GP', num, 0)) as gauteng,   " + 
				"	MAX(if(numEmp = 'EC', num, 0)) as easternCape,   " + 
				"	MAX(if(numEmp = 'FS', num, 0)) as freeState,   " + 
				"	MAX(if(numEmp = 'KZN', num, 0)) as kwazulunatal,   " + 
				"	MAX(if(numEmp = 'LP', num, 0)) as limpopo,   " + 
				"	MAX(if(numEmp = 'MP', num, 0)) as mpumalanga,   " + 
				"	MAX(if(numEmp = 'NC', num, 0)) as northernCape,   " + 
				"	MAX(if(numEmp = 'NW', num, 0)) as northWest,  " + 
				"	MAX(if(numEmp = 'WC', num, 0)) as westernCape,  " + 
				"	MAX(if(numEmp = 'N', num, 0)) as saNational,  " + 
				"	MAX(if(numEmp = 'X', num, 0)) as outsideSA   " + 
				"FROM (      " + 
				"    select   " + 
				"        no_employes_according_to_sars as description  " + 
				"        , count(id) as num  " + 
				"        , numEmp      " + 
				"    from (  " + 
				"        select     " + 
				"            ser.id as id     " + 
				"            , p.code as numEmp      " + 
				"            , 'Contributing' as no_employes_according_to_sars      " + 
				"        from     " + 
				"            sars_employer_detail ser   " + 
				"        inner join    " + 
				"            (  " + 
				"                select   " + 
				"                    code  " + 
				"                    , chamber_id   " + 
				"                from   " + 
				"                    sic_code   " + 
				"                group by   " + 
				"                    code  " + 
				"                    , chamber_id  " + 
				"            ) sc on sc.code = ser.sic_code_2  " + 
				"        inner join     " + 
				"            chamber c on c.id = sc.chamber_id  " + 
				"        inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " + 
				"        inner join     " + 
				"            (    " + 
				"                select     " + 
				"	                 distinct "   + 
				"                    ref_no    " + 
				"                from     " + 
				"                    sars_levy_detail		    " + 
				"                where    " + 
				"                    sars_filel_id = " + sarsFileId.intValue() + 
				"            ) sld on sld.ref_no = ser.ref_no    " + 
				"        where     " + 
				"            ser.sars_filel_id =  " + sarsFileId.intValue() +  
				"          " + 
				"        UNION ALL   " + 
				"          " + 
				"        select     " + 
				"            ser.id as id     " + 
				"            , p.code as numEmp      " + 
				"            , 'Active' as no_employes_according_to_sars      " + 
				"        from     " + 
				"            sars_employer_detail ser   " + 
				"        inner join    " + 
				"            (  " + 
				"                select   " + 
				"                    code  " + 
				"                    , chamber_id   " + 
				"                from   " + 
				"                    sic_code   " + 
				"                group by   " + 
				"                    code  " + 
				"                    , chamber_id  " + 
				"            ) sc on sc.code = ser.sic_code_2  " + 
				"        inner join     " + 
				"            chamber c on c.id = sc.chamber_id  " + 
				"        inner join   " + 
				"            (  " + 
				"                select   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id   " + 
				"                from   " + 
				"                    post_code_link   " + 
				"                group by   " + 
				"                    post_code_used_for_sars  " + 
				"                    , province_id  " + 
				"            ) pcl on pcl.post_code_used_for_sars = ser.employer_post_code  " + 
				"        inner join   " + 
				"            province p on p.id = pcl.province_id  " + 
				"        where     " + 
				"            ser.sars_filel_id = "  + sarsFileId.intValue() + 
				"            and (ser.trading_status = 'A')   " + 
				"	) as base   " + 
				"    group by   " + 
				"        description  " + 
				"        , numEmp  " + 
				") counts  " + 
				"group by   " + 
				"	description";
		return (List<SarsProvinceCountBean>)super.nativeSelectSqlList(hql, SarsProvinceCountBean.class);
	}
	
	/*
	 * QUICK FIX
	 */
	public List<ByChamberReportBean> getWspIdsFromNativeSQL() throws Exception {
		String sql ="select w.id as wspID from dg_verification dgv " + 
				"left join wsp w on w.id = dgv.wsp_id " + 
				"left join dg_allocation_parent dga on dga.wsp_id = w.id " + 
				"left join company c on c.id = w.company_id " + 
				"where w.theyear = 2020 and dgv.status = 0 " + 
				"and dga.id is null ";
		return (List<ByChamberReportBean>)super.nativeSelectSqlList(sql, ByChamberReportBean.class);
	}
}