package utilities;

import lombok.Data;
import org.testng.annotations.DataProvider;

import java.io.IOException;

public class DataProviders {

    @DataProvider(name = "LoginData")
    public String [][] getData() throws IOException {
        String path = ".//testData/testDataSelenium.xlsx";

        ExcelUtility excelUtility = new ExcelUtility(path);

        int totalrows = excelUtility.getRowCount("Sheet1");
        int totalcols = excelUtility.getCellCount("Sheet1",1);

        String [][] logindata = new String[totalrows][totalcols];
        for (int i = 1; i <= totalrows; i++) {
            for (int j = 0; j < totalcols; j++) {
                logindata[i-1][j]= excelUtility.getCellData("Sheet1",i,j);

            }
        }
        return logindata;
    }
}
