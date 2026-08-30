package haj.com.bean;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Transient;

import org.hibernate.annotations.Immutable;

import haj.com.annotations.TechFiniumColumn;

/*

select ref_no as sdlnumber, round(sum(mandatory_levy),2) as mandatoryLevyD  , round(tmp.documentAmountD,2) as posted,
case when     round(sum(mandatory_levy),2) +round(tmp.documentAmountD,2) < 0 then (round(sum(mandatory_levy),2) +round(tmp.documentAmountD,2)) *-1
     else round(sum(mandatory_levy),2) +round(tmp.documentAmountD,2)
     end as difference
from sars_levy_detail    ,
(
select b.vendor_id , sum(b.document_amount_d) as documentAmountD from t_s2 b
	where b.posted_date_d between  '2000-04-01' and '2017-03-31'
	 and scheme_year = 2017
	group by b.vendor_id
) as tmp
 where arrival_date_1 between '2000-04-01' and '2017-02-28'
 and ref_no in (select b.vendor_id from t_s2 b where b.posted_date_d between '2000-04-01' and '2017-03-31'  and b.scheme_year = 2017	 )
 and scheme_year = 2016
 and tmp.vendor_id =  ref_no
 group by 	ref_no
 order by case when     round(sum(mandatory_levy),2) +round(tmp.documentAmountD,2) < 0 then (round(sum(mandatory_levy),2) +round(tmp.documentAmountD,2)) *-1
     else round(sum(mandatory_levy),2) +round(tmp.documentAmountD,2)
     end desc


*/
@NamedNativeQueries({

	@NamedNativeQuery(
			name = "NQ_NATIVE_leviesPerSDLNrForSchemeYear",
			query = "select ref_no as sdlnumber ,sum(mandatory_levy) as tamount from sars_levy_detail " +
					"where ref_no in (select b.levy_number from t_s1 b " +
					"					where b.scheme_year  = :setaSchemeYear " +
					"					and b.status_description in ('Paid','Approved')) " +
					"and scheme_year = :sarsSchemeYear " +
					"group by ref_no order by ref_no",
			resultSetMapping	= "SDLNrForSchemeYearResult"
			)	,
	@NamedNativeQuery(
			name = "NQ_NATIVE_invoicesPerSDLNrForSchemeYear",
			query = "select a.vendor_id as sdlnumber , sum(a.document_amount_d) as tamount from t_s2 a " +
					"where a.scheme_year = :setaSchemeYear " +
					"and a.vendor_id in (select b.levy_number from t_s1 b " +
					"					where b.scheme_year  = :setaSchemeYear " +
					"					and b.status_description in ('Paid','Approved')) group by a.vendor_id order by a.vendor_id",
			resultSetMapping	= "SDLNrForSchemeYearResult"
			)	,
	@NamedNativeQuery(
			name = "NQ_NATIVE_payments_that_has_no_mandatory_levy",
			query = "select a.vendor_id as sdlnumber , sum(a.document_amount_d) as tamount from t_s2 a " +
					"where a.scheme_year = :setaSchemeYear " +
					"and a.vendor_id not in (select b.levy_number from t_s1 b " +
					"					where b.scheme_year  = :setaSchemeYear " +
					"				and b.status_description in ('Paid','Approved')) " +
					"group by a.vendor_id order by a.vendor_id",
			resultSetMapping	= "SDLNrForSchemeYearResult"
			)		,
	@NamedNativeQuery(
			name = "NQ_NATIVE_approved_payments_that_has_not_been_paid",
			query = "select b.levy_number as sdlnumber , b.status_description as status from t_s1 b " +
					"where b.scheme_year  = :setaSchemeYear " +
					"and b.status_description in ('Paid','Approved')	" +
					"and b.levy_number not in (select a.vendor_id a " +
					"							from t_s2 a " +
					"							where a.scheme_year = :setaSchemeYear) " +
					"order by b.status_description desc	",
			resultSetMapping	= "ExceptionSDLNrForSchemeYearResult"
			)		,
	@NamedNativeQuery(
			name = "NQ_NATIVE_levy_deviation_report",
			query = "select a.ref_no as sdlnumber,  " +
					"     ROUND(sum(a.total-(a.interest + a.penalty) ),2)  as latest_levy,  " +
					"     ROUND(tmp.lavg,2)  as average_levy ,  " +
					"case when (sum(a.total-(a.interest + a.penalty))*-1.0) > (tmp.lavg*-1.0)  " +
					"     then ROUND(  ((sum(a.total-(a.interest + a.penalty))*-1.0) -(tmp.lavg*-1.0) ),2) " +
					"     else ROUND( ((tmp.lavg*-1.0)-  (sum(a.total-(a.interest + a.penalty))*-1.0)  ) ,2) " +
					"     end as deviation_amount, " +
					" " +
					"case when (sum(a.total-(a.interest + a.penalty))*-1.0) > (tmp.lavg*-1.0)  " +
					"        then ROUND(  ((((sum(a.total-(a.interest + a.penalty))*-1.0) - (tmp.lavg*-1.0) ) / (tmp.lavg*-1.0)) * 100),2) " +
					"		else ROUND( ((((tmp.lavg*-1.0) - (sum(a.total-(a.interest + a.penalty))*-1.0)   ) /(sum(a.total-(a.interest + a.penalty))*-1.0) ) * 100) ,2) " +
					"		end as deviation_percentage , tmp.cnt as no_levies " +
					"from sars_levy_detail a , ( " +
					"    select b.ref_no, avg(b.total - (b.interest + b.penalty) ) as lavg ,  count(distinct arrival_date_1) as cnt " +
					"	from sars_levy_detail b " +
					"	where b.arrival_date_1 >= DATE_SUB( (select max(arrival_date_1) from sars_levy_detail),INTERVAL 1 YEAR)  and b.arrival_date_1 < (select max(arrival_date_1) from sars_levy_detail) " +
					"	group by b.ref_no	 " +
					") as tmp " +
					"where a.arrival_date_1 >= (select max(arrival_date_1) from sars_levy_detail) " +
					"and  a.ref_no = tmp.ref_no " +
					"and a.ref_no like :sdl " +
				//	"and tmp.cnt = 12 "+
 					"group by a.ref_no " +
					"order by latest_levy asc, deviation_percentage desc",
			resultSetMapping	= "LevyDeviationReportResult"
			)	,

	@NamedNativeQuery(
			name = "NQ_NATIVE_levy_deviation_report_count",
			query = "select count(distinct a.ref_no) " +
					"from sars_levy_detail a , (" +
					"    select b.ref_no, avg(b.total - (b.interest + b.penalty) ) as lavg ,  count(distinct arrival_date_1) as cnt " +
					"	from sars_levy_detail b " +
					"	where b.arrival_date_1 >= DATE_SUB( (select max(arrival_date_1) from sars_levy_detail),INTERVAL 1 YEAR)  and b.arrival_date_1 < (select max(arrival_date_1) from sars_levy_detail) " +
					"	group by b.ref_no " +
					") as tmp " +
					"where a.arrival_date_1 >= (select max(arrival_date_1) from sars_levy_detail)"+
					"and  a.ref_no = tmp.ref_no  "+
					"and a.ref_no like :sdl " +
					"and tmp.cnt = 12 "
			)	,
	@NamedNativeQuery(
			name = "NQ_NATIVE_levy_deviation_report_fordate",
			query = "select max(arrival_date_1) from sars_levy_detail"
			)	,
	@NamedNativeQuery(
			name = "NQ_NATIVE_levy_deviation_report_by_period",
			query = "select ref_no as sdlnumber, round(sum(mandatory_levy),2) as mandatoryLevyD  , round(tmp.documentAmountD,2) as posted,  " +
					"case when     round(sum(mandatory_levy),2) +round(tmp.documentAmountD,2) < 0 then (round(sum(mandatory_levy),2) +round(tmp.documentAmountD,2)) *-1 " +
					"     else round(sum(mandatory_levy),2) +round(tmp.documentAmountD,2) " +
					"     end as difference " +
					"from sars_levy_detail    , " +
					"( " +
					"select b.vendor_id , sum(b.document_amount_d) as documentAmountD from t_s2 b   " +
					"	where b.posting_date_d between  :fromDateInv and :toDateInv and b.scheme_year = :setaSchemeYear	 " +
					"	group by b.vendor_id  " +
					") as tmp " +
					" where arrival_date_1 between :fromDate and :toDate " +
					" and ref_no in (select b.vendor_id from t_s2 b where b.posting_date_d between :fromDateInv and :toDateInv and b.scheme_year = :setaSchemeYear		 ) " +
					" and scheme_year = :sarsSchemeYear		 " +
					" and tmp.vendor_id =  ref_no and ref_no like :sdlNumber " +
					" group by 	ref_no	 " +
					" order by case when     round(sum(mandatory_levy),2) +round(tmp.documentAmountD,2) < 0 then (round(sum(mandatory_levy),2) +round(tmp.documentAmountD,2)) *-1 " +
					"     else round(sum(mandatory_levy),2) +round(tmp.documentAmountD,2) " +
					"     end desc"	,
					resultSetMapping	= "LevyDeviationReportPerPeriodResult"

			)

})
/*
@SqlResultSetMapping(name="SDLNrForSchemeYearResult", classes = {
	    @ConstructorResult(targetClass = SarsLevyRecon.class,
	    columns = {@ColumnResult(name="sdlnumber"), @ColumnResult(name="tamount")})
	})
*/

@SqlResultSetMappings({
	@SqlResultSetMapping(name="SDLNrForSchemeYearResult", classes = {
		    @ConstructorResult(targetClass = SarsLevyRecon.class,
		    columns = {@ColumnResult(name="sdlnumber"), @ColumnResult(name="tamount")})
		}),
	@SqlResultSetMapping(name="ExceptionSDLNrForSchemeYearResult", classes = {
		    @ConstructorResult(targetClass = SarsLevyRecon.class,
		    columns = {@ColumnResult(name="sdlnumber"), @ColumnResult(name="status")})
		}),
	@SqlResultSetMapping(name="LevyDeviationReportResult", classes = {
		    @ConstructorResult(targetClass = SarsLevyRecon.class,
		    columns = {@ColumnResult(name="sdlnumber"), @ColumnResult(name="latest_levy"), @ColumnResult(name="average_levy"), @ColumnResult(name="deviation_amount"), @ColumnResult(name="deviation_percentage"), @ColumnResult(name="no_levies")})
		}),
	@SqlResultSetMapping(name="LevyDeviationReportPerPeriodResult", classes = {
		    @ConstructorResult(targetClass = SarsLevyRecon.class,
		    columns = {@ColumnResult(name="sdlnumber"), @ColumnResult(name="mandatoryLevyD"), @ColumnResult(name="posted"), @ColumnResult(name="difference")})
		})
    })


@Entity
@Immutable
public class SarsLevyRecon implements Serializable {
	@Id
	@TechFiniumColumn(heading="SDL Number")
	private String sdlnumber;
	@Transient
	@TechFiniumColumn(suppress=true)
	private Double tamount;
	@Transient
	@TechFiniumColumn(heading = "Mandatory Grant", formula = "sum")
	private Double sarsAmount;
	@Transient
	@TechFiniumColumn(heading = "Invoice Amount", formula = "sum")
	private Double setaAmount;
	@Transient
	@TechFiniumColumn(heading = "Difference", formula = "sum")
	private Double diff;

	@Transient
	@TechFiniumColumn(suppress=true)
	private List<SarsLevyRecon> levyList;
	@Transient
	@TechFiniumColumn(suppress=true)
	private List<SarsLevyRecon> invList;

	@Transient
	@TechFiniumColumn(suppress=true)
	private String status;

	@Transient
	@TechFiniumColumn(suppress=true)
	private Double latest_levy;
	@Transient
	@TechFiniumColumn(suppress=true)
	private Double average_levy;
	@Transient
	@TechFiniumColumn(suppress=true)
	private Double deviation_amount;
	@Transient
	@TechFiniumColumn(suppress=true)
	private Double deviation_percentage;
	@Transient
	@TechFiniumColumn(suppress=true)
	private BigInteger no_levies;

	@Transient
	@TechFiniumColumn(suppress=true)
	private String css;

	@Transient
	private int levyStatus;    // 1=normal     2=inconsistent    3=below threshold

	@Transient
	private int noLeviesSinceStart;

	public SarsLevyRecon() {
	}


	@Transient
	@TechFiniumColumn(suppress=true)
	private Double mandatoryLevyD;

	@Transient
	@TechFiniumColumn(suppress=true)
	private Double posted;

	@Transient
	@TechFiniumColumn(suppress=true)
	private Double difference;



	public SarsLevyRecon(String sdlnumber, Double mandatoryLevyD, Double posted, Double difference) {
		super();
		this.sdlnumber = sdlnumber;
		this.mandatoryLevyD = mandatoryLevyD;
		this.posted = posted;
		this.difference = difference;

		this.sarsAmount = mandatoryLevyD;
		this.setaAmount = posted;
		this.diff = difference;
	}



	public SarsLevyRecon(String sdlnumber, Double latest_levy, Double average_levy, Double deviation_amount,
			Double deviation_percentage, BigInteger no_levies) {
		super();
		this.sdlnumber = sdlnumber;
		this.latest_levy = latest_levy;
		this.average_levy = average_levy;
		this.deviation_amount = deviation_amount;
		this.deviation_percentage = deviation_percentage;
		this.no_levies = no_levies;
	}



	public SarsLevyRecon(String sdlnumber, Double tamount) {
		super();
		this.sdlnumber = sdlnumber;
		this.tamount = tamount;
	}



	public SarsLevyRecon(String sdlnumber, String status) {
		super();
		this.sdlnumber = sdlnumber;
		this.status = status;
	}



	public SarsLevyRecon(String sdlnumber, Double setaAmount, Integer tmp) {
		super();
		this.sdlnumber = sdlnumber;
		this.setaAmount = setaAmount;
	}



	public String getSdlnumber() {
		return sdlnumber;
	}

	public void setSdlnumber(String sdlnumber) {
		this.sdlnumber = sdlnumber;
	}

	public Double getSarsAmount() {
		return sarsAmount;
	}

	public void setSarsAmount(Double sarsAmount) {
		this.sarsAmount = sarsAmount;
	}

	public Double getSetaAmount() {
		return setaAmount;
	}

	public void setSetaAmount(Double setaAmount) {
		this.setaAmount = setaAmount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sdlnumber == null) ? 0 : sdlnumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SarsLevyRecon other = (SarsLevyRecon) obj;
		if (sdlnumber == null) {
			if (other.sdlnumber != null)
				return false;
		} else if (!sdlnumber.equals(other.sdlnumber))
			return false;
		return true;
	}



	public Double getTamount() {
		return tamount;
	}



	public void setTamount(Double tamount) {
		this.tamount = tamount;
	}







	public Double getDiff() {
		return diff;
	}



	public void setDiff(Double diff) {
		this.diff = diff;
	}



	@Override
	public String toString() {
		return "SarsLevyRecon [sdlnumber=" + sdlnumber + ", tamount=" + tamount + ", sarsAmount=" + sarsAmount
				+ ", setaAmount=" + setaAmount + ", diff=" + diff + "]";
	}



	public List<SarsLevyRecon> getLevyList() {
		return levyList;
	}



	public void setLevyList(List<SarsLevyRecon> levyList) {
		this.levyList = levyList;
	}



	public List<SarsLevyRecon> getInvList() {
		return invList;
	}



	public void setInvList(List<SarsLevyRecon> invList) {
		this.invList = invList;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Double getLatest_levy() {
		return latest_levy;
	}



	public void setLatest_levy(Double latest_levy) {
		this.latest_levy = latest_levy;
	}



	public Double getAverage_levy() {
		return average_levy;
	}



	public void setAverage_levy(Double average_levy) {
		this.average_levy = average_levy;
	}



	public Double getDeviation_amount() {
		return deviation_amount;
	}



	public void setDeviation_amount(Double deviation_amount) {
		this.deviation_amount = deviation_amount;
	}



	public Double getDeviation_percentage() {
		return deviation_percentage;
	}



	public void setDeviation_percentage(Double deviation_percentage) {
		this.deviation_percentage = deviation_percentage;
	}



	public BigInteger getNo_levies() {
		return no_levies;
	}



	public void setNo_levies(BigInteger no_levies) {
		this.no_levies = no_levies;
	}



	public String getCss() {
		return css;
	}



	public void setCss(String css) {
		this.css = css;
	}



	public Double getMandatoryLevyD() {
		return mandatoryLevyD;
	}



	public void setMandatoryLevyD(Double mandatoryLevyD) {
		this.mandatoryLevyD = mandatoryLevyD;
	}



	public Double getPosted() {
		return posted;
	}



	public void setPosted(Double posted) {
		this.posted = posted;
	}



	public Double getDifference() {
		return difference;
	}



	public void setDifference(Double difference) {
		this.difference = difference;
	}



	public int getLevyStatus() {
		return levyStatus;
	}



	public void setLevyStatus(int levyStatus) {
		this.levyStatus = levyStatus;
	}



	public int getNoLeviesSinceStart() {
		return noLeviesSinceStart;
	}



	public void setNoLeviesSinceStart(int noLeviesSinceStart) {
		this.noLeviesSinceStart = noLeviesSinceStart;
	}


}
