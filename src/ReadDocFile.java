import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

class TestStep {
	String StepType;
	String StepText;
	ArrayList<String> Data;
}

class TestFeaturenfo {
	String TestCaseName;
	String TestCaseFeature;
	ArrayList<TestStep> TestCaseSteps;
}

public class ReadDocFile {
	public static ArrayList<TestFeaturenfo> ReadTheDocument() {
		try {
			File file = new File("arikovani - basic use case.docx");
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			XWPFDocument document = new XWPFDocument(fis);

			// get Tables / Rows / Cells
			ArrayList<TestFeaturenfo> cucumberTestCase = new ArrayList<>();
			Iterator<IBodyElement> bodyElementIterator = document.getBodyElementsIterator();
			IBodyElement element = bodyElementIterator.next();

			List<XWPFTable> tableList = element.getBody().getTables();

			for (int i = 0; i < tableList.size(); i = i + 2) {
				XWPFTable topicTable = tableList.get(i);
				XWPFTable stepTable = tableList.get(i + 1);

				// Table 1
				TestFeaturenfo testInfo = new TestFeaturenfo();
				List<XWPFTableRow> tablerows = topicTable.getRows();
				for (XWPFTableRow row : tablerows) {

					XWPFTableCell cell1 = row.getTableCells().get(0);
					XWPFTableCell cell2 = row.getTableCells().get(1);

					if (cell1.getText().toLowerCase().equals("use case tanımı")) {
						testInfo.TestCaseFeature = cell2.getText();
					} else if (cell1.getText().toLowerCase().equals("test case name")) {
						testInfo.TestCaseName = cell2.getText();
					}
				}

				// Table 2
				ArrayList<TestStep> testSteps = new ArrayList<>();
				List<XWPFTableRow> tablerows2 = stepTable.getRows();
				for (XWPFTableRow row : tablerows2) {

					XWPFTableCell typeCell = row.getTableCells().get(2);
					XWPFTableCell stepTextCell = row.getTableCells().get(1);
					XWPFTableCell dataCell = row.getTableCells().get(5);
					TestStep testStep = new TestStep();
					boolean isTestCase = false;
					if (typeCell.getText().toLowerCase().equals("enter")) {
						testStep.StepType = "GIVEN";
						isTestCase = true;
					} else if (typeCell.getText().toLowerCase().equals("write")) {
						testStep.StepType = "WHEN";
						isTestCase = true;
					} else if (typeCell.getText().toLowerCase().equals("read")) {
						testStep.StepType = "THEN";
						isTestCase = true;
					}

					if (isTestCase) {
						testStep.StepText = stepTextCell.getText();
						if (!dataCell.getText().isEmpty()) {
							ArrayList<String> data = new ArrayList<>();
							if (dataCell.getText().contains(",")) {
								for (String item : dataCell.getText().split(",")) {
									data.add(item);
								}
							} else {
								data.add(dataCell.getText());
							}
							testStep.Data = data;
						}
						testSteps.add(testStep);

					}

				}
				testInfo.TestCaseSteps = testSteps;
				cucumberTestCase.add(testInfo);
			}
			document.close();
			fis.close();
			return cucumberTestCase;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		ArrayList<TestFeaturenfo> cucumberTestCases = ReadTheDocument();

		WriteFeatureTestCases(cucumberTestCases);
	}

	public static void WriteFeatureTestCases(ArrayList<TestFeaturenfo> testCases) {
		PrintWriter writer;
		try {
			Map<Object, List<TestFeaturenfo>> featureListGrouped = testCases.stream()
					.collect(Collectors.groupingBy(w -> w.TestCaseFeature));

			for (List<TestFeaturenfo> testCasess : featureListGrouped.values()) {

				writer = new PrintWriter(testCasess.get(0).TestCaseFeature.replaceAll("\\s+", "") + ".feature", "UTF-8");
				writer.println("Feature: " + testCasess.get(0).TestCaseFeature + "\n");
				
				for (TestFeaturenfo testCase : testCasess) {

					writer.println("\tScenario: " + testCase.TestCaseName + "\n");
					for (int i = 0; i < testCase.TestCaseSteps.size(); i++) {
						// for(TestStep step : testCase.TestCaseSteps){
						if (i != 0 && testCase.TestCaseSteps.get(i - 1).StepType == testCase.TestCaseSteps
								.get(i).StepType) {
							writer.print("\t\tAND");
						} else {
							writer.print("\t\t" + testCase.TestCaseSteps.get(i).StepType);
						}
						writer.print(" " + testCase.TestCaseSteps.get(i).StepText);
						if (testCase.TestCaseSteps.get(i).Data != null) {
							writer.print(" " + testCase.TestCaseSteps.get(i).Data);
						}
						writer.println("");
					}
					writer.println("");
				}

				writer.close();

			}
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}