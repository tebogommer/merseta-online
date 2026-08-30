package haj.com.bean;
import java.io.Serializable;
import java.util.List;


public class ExcelSheetBean implements Serializable {
	
	private List<?> sheetList;
	private String sheetName;
	private String sheetHeaderName;
	
	
	public ExcelSheetBean(List<?> sheetList,String sheetName,String sheetHeaderName)
	{
		this.sheetList = sheetList;
		this.sheetName = sheetName;
		this.sheetHeaderName = sheetHeaderName;
	}
	
	public ExcelSheetBean(List<?> sheetList,String sheetName)
	{
		this.sheetList = sheetList;
		this.sheetName = sheetName;
	}
	
	public List<?> getSheetList() {
		return sheetList;
	}
	public void setSheetList(List<?> sheetList) {
		this.sheetList = sheetList;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getSheetHeaderName() {
		return sheetHeaderName;
	}
	public void setSheetHeaderName(String sheetHeaderName) {
		this.sheetHeaderName = sheetHeaderName;
	}
	
	}
