public class SpreadSheetTest {

	public static void main(String[] args) {
		try {
		/*	
			List<SarsLevyRecon> levyList = new ArrayList<SarsLevyRecon>();
			SarsLevyRecon a = new SarsLevyRecon();
			a.setSdlnumber("L1");
			a.setSarsAmount(10.0);
			a.setSetaAmount(1.0);
			a.setDiff(9.0);
			SarsLevyRecon b = new SarsLevyRecon();
			b.setSdlnumber("L2");
			b.setSarsAmount(20.0);
			b.setSetaAmount(10.0);
			b.setDiff(10.0);
			
			levyList.add(a);
			levyList.add(b);
			
			
  			List<ExcelSheetBean> excelSheets = new ArrayList<ExcelSheetBean>();
  			excelSheets.add(new ExcelSheetBean(levyList,"SDL Numbers", "Matching Levy numbers"));
  			excelSheets.add(new ExcelSheetBean(levyList,"SDL Numbers2", "Matching Levy numbers2"));
  			byte[] spreadsheet = CreateCsvExcel.createExcel(excelSheets);	
  			FileUtils.writeByteArrayToFile(new File("/Users/hendrik/Desktop/temp.xlsx"), spreadsheet);
			*/
			String url = "Content/Nov/DOWN/NOV 2017 17-sdl.zip";
			
			String x = url.substring(0,url.lastIndexOf('/')+1);
			System.out.println(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
