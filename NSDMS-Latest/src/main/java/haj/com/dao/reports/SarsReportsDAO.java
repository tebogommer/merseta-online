package haj.com.dao.reports;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haj.com.bean.SarsLevyRecon;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankDAO.
 */
public class SarsReportsDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}


	@SuppressWarnings("unchecked")
	public List<SarsLevyRecon> levyDeviation(int startingAt, int maxPerPage,String sdl) throws Exception {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("sdl", sdl);
		return (List<SarsLevyRecon>)super.getNQList("NQ_NATIVE_levy_deviation_report", parms, startingAt, maxPerPage);
	}

	@SuppressWarnings("unchecked")
	public List<SarsLevyRecon> levyDeviation() throws Exception {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("sdl", "%");
		return (List<SarsLevyRecon>)super.getNQList("NQ_NATIVE_levy_deviation_report", parms);
	}

	public Long levyDeviationCount(String sdl) throws Exception {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("sdl", sdl);
		return ((java.math.BigInteger)super.getUniqueNQResult("NQ_NATIVE_levy_deviation_report_count", parms)).longValue();
	}

	public Date levyDeviationBaseDate() throws Exception {
		return (Date)super.getUniqueNQResult("NQ_NATIVE_levy_deviation_report_fordate", null);
	}


	public int noLeviesForLnumber(String refNo) throws Exception {
	  String sql = " select count(distinct arrival_date_1) as cnt    " +
			"	from sars_levy_detail b   " +
			"	where b.ref_no = :refNo ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("refNo", refNo);
		return( (java.math.BigInteger)super.nativeSelectSqlUniqueResult(sql, parameters)).intValue();

	}
}

