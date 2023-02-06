import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;



public class test3 {
    public static void main(String[] args) throws IOException {
        //创建Excel文件薄
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        //创建工作表sheet
        SXSSFSheet sheet = workbook.createSheet();
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0,0,0,9);
        if (cellRangeAddress.getNumberOfCells() > 1) {
            sheet.addMergedRegionUnsafe(cellRangeAddress);
        }
        SXSSFRow row = sheet.createRow(0);
        SXSSFCell cell = row.createCell(0);
        cell.setCellValue("第一行");
        cell = sheet.createRow(1).createCell(0);
        cell.setCellValue("第二行");
        CellStyle cs = workbook.createCellStyle();
        cs.setAlignment(HorizontalAlignment.CENTER);
        sheet.getRow(0).getCell(0).setCellStyle(cs);
        File wFile = new File("D:/Users/Admin/Desktop/结果集/门诊费用明细信息.xlsx");
        FileOutputStream op = new FileOutputStream(wFile);
        workbook.write(op);
        op.flush();
        op.close();
    }
}

