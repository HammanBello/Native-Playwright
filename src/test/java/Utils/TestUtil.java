package Utils;


import constants.AppConstants;
import io.qameta.allure.internal.shadowed.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static base.BaseTest.dataSheet;

public class TestUtil {

    public static Workbook book;
    public static Sheet sheet;

    public static void shortWait(){
        try {
            Thread.sleep(AppConstants.SHORT_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void mediumWait(){
        try {
            Thread.sleep(AppConstants.MEDIUM_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void longWait(){
        try {
            Thread.sleep(AppConstants.LONG_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public static Object[][] getTestData(String sheetName){
        FileInputStream file = null;
        try {
            if(dataSheet!=null)
                file = new FileInputStream("src/test/java/testdata/"+dataSheet+".xlsx");
            else
                 file = new FileInputStream(AppConstants.TESTDATA_SHEET_PATH);

        } catch (FileNotFoundException e) {
            Assert.fail("Fichier introuvable");
            e.printStackTrace();
        }

        try {
            book = WorkbookFactory.create(file);
        } catch (InvalidFormatException e) {
            Assert.fail("Format Invalide");
            e.printStackTrace();
        } catch (IOException e) {
            Assert.fail("Format Invalide");
            e.printStackTrace();
        }

        sheet = book.getSheet(sheetName);

        Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

        for(int i=0; i<sheet.getLastRowNum(); i++){
            for(int k=0; k<sheet.getRow(0).getLastCellNum(); k++){
//                if (data[i][k].equals(1))
                data[i][k] = sheet.getRow(i+1).getCell(k).toString();
            }
        }

        return data;

    }

}