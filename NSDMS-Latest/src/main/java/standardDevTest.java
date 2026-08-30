import java.util.ArrayList;
import java.util.List;

public class standardDevTest {

	public static void main(String[] args) {
		
//		String s1="javatpoint :TD1 website";  
//		 s1=s1.replace(":TD1", "123123");//replaces all occurrences of 'a' to 'e'  
//		System.out.println(s1);
		// standardDeviationCheckTest();

		/*
		 * Test Two (Passing data to method) 12 Entries entries: 9,004.66
		 * 7,744.91 5,380.92 9,059.96 8,148.36 8,379.10 15,499.99 6,007.05
		 * 8,880.78 11,436.73 9,294.33 9,024.59 Grand Total: 107,861.38 -
		 * correct Average: 8,988.00 - correct STD: 2,577.00 - correct STD %:29%
		 * - correct
		 */
		// List<Double> listOfEntriesTestTwo = new ArrayList<>();
		// listOfEntriesTestTwo.add(9004.66); // 1
		// listOfEntriesTestTwo.add(7744.91); // 2
		// listOfEntriesTestTwo.add(5380.92); // 3
		// listOfEntriesTestTwo.add(9059.96); // 4
		// listOfEntriesTestTwo.add(8148.36); // 5
		// listOfEntriesTestTwo.add(8379.10); // 6
		// listOfEntriesTestTwo.add(15499.99); // 7
		// listOfEntriesTestTwo.add(6007.05); // 8
		// listOfEntriesTestTwo.add(8880.78); // 9
		// listOfEntriesTestTwo.add(11436.73); // 10
		// listOfEntriesTestTwo.add(9294.33); // 11
		// listOfEntriesTestTwo.add(9024.59); // 12
		
		/*
		 * Grant Total: 1,403.01 
		 * Average: 1,403.01 
		 * STDDEV
		 * %
		 */
		 List<Double> listOfEntriesTestOneEntry = new ArrayList<>();
		 listOfEntriesTestOneEntry.add(1403.01 ); // 1

		/*
		 * Test Three (less than 12 entries) 6 Entries entries: 21,159.51
		 * 5,354.25 2,153.08 18,970.05 413.05 5,066.37 Grand Total: 53,116.31 -
		 * correct Average: 8,853.00 - correct STD: 8,905.00 - correct STD %:
		 * 101% - correct
		 */
		// List<Double> listOfEntriesTestThree = new ArrayList<>();
		// listOfEntriesTestThree.add(21159.51); // 1
		// listOfEntriesTestThree.add(5354.25); // 2
		// listOfEntriesTestThree.add(2153.08); // 3
		// listOfEntriesTestThree.add(18970.05); // 4
		// listOfEntriesTestThree.add(413.05); // 5
		// listOfEntriesTestThree.add(5066.37); // 6

		/*
		 * Test Four (1 Result Expected to fail on calculation) 1 Entry entry:
		 * 8,979.40 Grand Total: 8,979.40 - correct Average: 8,979.40 - correct
		 * STD: ERROR - did not error but returned 0 STD %: ERROR - did not
		 * error but returned 0
		 */
		// List<Double> listOfEntriesTestFour = new ArrayList<>();
		// listOfEntriesTestFour.add(8979.40 ); // 1

		/*
		 * Test Five (Testing Negative Entries) 12 Entries entries: 40,437.93
		 * 37,023.08 -1,324,446.58 72,090.05 73,500.87 68,059.01 28,895.03
		 * 49,203.18 44,154.88 43,718.60 45,479.94 44,242.79 Grand Total:
		 * -777,641.22 - correct Average: -64,803.00 - correct STD: 396,937.00 -
		 * correct STD %: -613% - correct
		 */
		// List<Double> listOfEntriesTestFive = new ArrayList<>();
		// listOfEntriesTestFive.add(40437.93); // 1
		// listOfEntriesTestFive.add(37023.08); // 2
		// listOfEntriesTestFive.add(-1324446.58); // 3
		// listOfEntriesTestFive.add(72090.05); // 4
		// listOfEntriesTestFive.add(73500.87); // 5
		// listOfEntriesTestFive.add(68059.01); // 6
		// listOfEntriesTestFive.add(28895.03); // 7
		// listOfEntriesTestFive.add(49203.18 ); // 8
		// listOfEntriesTestFive.add(44154.88); // 9
		// listOfEntriesTestFive.add(43718.60); // 10
		// listOfEntriesTestFive.add(45479.94); // 11
		// listOfEntriesTestFive.add(44242.79); // 12

		/*
		 * Test Six (Empty Array list passed) 0 entries: Grand Total: 0 -
		 * correct Average: 0 - result NaN STD: 0 - correct STD %: 0 - correct
		 */
		// List<Double> listOfEntriesTestSix = new ArrayList<>();

		/*
		 * Test Seven (Null Array list passed) [Testing fail, fail to return 0]
		 * 0 entries: Grand Total: 0 - correct Average: NaN - correct STD: 0 -
		 * correct STD %: 0 - correct
		 */
		// List<Double> listOfEntriesTestSeven = null;

		try {
			standardDeviationCheckgenericTest(listOfEntriesTestOneEntry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Generic method attempt
	 */
	public static void standardDeviationCheckgenericTest(List<Double> listOfEntries) throws Exception {
		double sd = 0.0;
		double grandTotal = 0.0;
		double average = 0.0;
		double standardDeviation = 0.0;
		double standardDeviationPercentage = 0.0;
		int numberOfEntries = 0;
		if (listOfEntries != null) {
			numberOfEntries = listOfEntries.size();
			for (Double entry : listOfEntries) {
				grandTotal += entry;
				average += entry;
			}
			average = average / listOfEntries.size();
		} else {
			average = average / 0;
		}

		System.out.println("Grand Total: " + grandTotal);
		System.out.println("Average: " + average);
		for (int i = 0; i < numberOfEntries; i++) {
			sd += ((listOfEntries.get(i) - average) * (listOfEntries.get(i) - average)) / (listOfEntries.size() - 1);
		}
		standardDeviation = Math.round(Math.sqrt(sd));
		System.out.println("StandardDeviation: " + standardDeviation);
		standardDeviationPercentage = standardDeviation / average;
		System.out.println("Percentage: " + Math.round(standardDeviationPercentage * 100) + "%");
	}

	/*
	 * Test One (Creating once off method to test amounts) grand total:
	 * 106716.04 - correct average: 8,893.00 - correct STD DEV: 2,507.00 -
	 * correct STD DEV: 28 % - correct Status: Normal
	 */
	public static void standardDeviationCheckTestOne() {
		try {
			double sd = 0.0;
			double grandTotal = 0.0;
			double average = 0.0;
			/*
			 	 	 	 	
			 */
			// add entries to list
			List<Double> listOfEntries = new ArrayList<>();
			listOfEntries.add(10523.69); // 1
			listOfEntries.add(10326.64); // 2
			listOfEntries.add(10561.07); // 3
			listOfEntries.add(8558.23); // 4
			listOfEntries.add(8472.51); // 5
			listOfEntries.add(2920.38); // 6
			listOfEntries.add(7485.48); // 7
			listOfEntries.add(10260.55); // 8
			listOfEntries.add(7458.73); // 9
			listOfEntries.add(9566.19); // 10
			listOfEntries.add(13067.24); // 11
			listOfEntries.add(7515.33); // 12

			for (Double entry : listOfEntries) {
				grandTotal += entry;
				average += entry;
			}

			average = average / listOfEntries.size();

			for (int i = 0; i < listOfEntries.size(); i++) {
				sd += ((listOfEntries.get(i) - average) * (listOfEntries.get(i) - average))
						/ (listOfEntries.size() - 1);
			}
			double standardDeviation = Math.round(Math.sqrt(sd));
			System.out.println(standardDeviation);
			double standardDeviationPercentage = standardDeviation / average;
			System.out.println("Percentage: " + Math.round(standardDeviationPercentage * 100) + "%");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void calculateStandardDeviation(List<Double> listOfEntries) throws Exception {
		double sd = 0.0;
		double average = 0.0;
		double standardDeviation = 0.0;
		double standardDeviationPercentage = 0.0;
		int numberOfEntries = 0;
		if (listOfEntries != null) {
			numberOfEntries = listOfEntries.size();
			for (Double entry : listOfEntries) {
				average += entry;
			}
			average = average / listOfEntries.size();
		} else {
			average = average / 0;
		}
		for (int i = 0; i < numberOfEntries; i++) {
			sd += ((listOfEntries.get(i) - average) * (listOfEntries.get(i) - average)) / (listOfEntries.size() - 1);
		}
		standardDeviation = Math.round(Math.sqrt(sd));
		// standardDeviationPercentage = standardDeviation / average;
	}

	public static void calculateStandardDeviationPercentage(List<Double> listOfEntries) throws Exception {
		double sd = 0.0;
		double average = 0.0;
		double standardDeviation = 0.0;
		double standardDeviationPercentage = 0.0;
		int numberOfEntries = 0;
		if (listOfEntries != null) {
			numberOfEntries = listOfEntries.size();
			for (Double entry : listOfEntries) {
				average += entry;
			}
			average = average / listOfEntries.size();
		} else {
			average = average / 0;
		}
		for (int i = 0; i < numberOfEntries; i++) {
			sd += ((listOfEntries.get(i) - average) * (listOfEntries.get(i) - average)) / (listOfEntries.size() - 1);
		}
		standardDeviation = Math.round(Math.sqrt(sd));
		standardDeviationPercentage = standardDeviation / average;
	}

	/*
	 * Alternative formula, needs to be tested
	 */
	public static double calculateSD(double numArray[], int numberOfEntriesInArray) {
		double sum = 0.0, standardDeviation = 0.0;
		for (double num : numArray) {
			sum += num;
		}
		double mean = sum / numberOfEntriesInArray;
		for (double num : numArray) {
			standardDeviation += Math.pow(num - mean, 2);
		}
		return Math.sqrt(standardDeviation / numberOfEntriesInArray);
	}

}
