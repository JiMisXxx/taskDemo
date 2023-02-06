package MyLocal.oprnExcel;

import MyLocal.dbConnection.GettingConnection;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class DbToExcel implements Runnable {

    public static final String CLASS_ALIAS = "DbToExcel";
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//设置日期格式
    static int threadcnt = 1; //启用线程数
    static AtomicInteger threadnow = new AtomicInteger(0);
    static int maxRow = 1000;//单个Excel文件允许写入的最大行数
    List<String> myList = Arrays.asList("44000000000001821594");//每个Excel需要写入数据的查询条件
    static String str = "select * from  setl_d where emp_no = ?;";

    public static void main(String[] args) {
        running();
    }

    /**
     * 每个线程处理一个查询条件的数据写入到Excel
     */
    @Override
    public void run() {
        int threadnowint = threadnow.getAndIncrement();
        for (int j = threadnowint; j < myList.size(); j = j + threadcnt) {
            try {
                writeinto(myList.get(j));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 多线程调用
     */
    public static void running() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadcnt);
        for (int i = 0; i < threadcnt; i++) {            fixedThreadPool.execute(new DbToExcel());
        }
        fixedThreadPool.shutdown();
    }

    /**
     * POI SXSSFWorkbook写入Excel文件
     */
    public void writeinto(String conditions) throws Exception {
        System.out.println("文件[" + conditions + ".xlsx]正在获取数据，时间：" + LocalDateTime.now().format(df));
        Connection con = GettingConnection.gettingCon("mylocal");
        PreparedStatement ps = con.prepareStatement(str, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, conditions);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData data = rs.getMetaData();
        System.out.println("文件[" + conditions + ".xlsx]获取数据完成，开始写入数据，时间：" + LocalDateTime.now().format(df));
        ArrayList<String> celltitle = new ArrayList<>();
        //获取表头字段
        for (int i = 0; i < data.getColumnCount(); i++) {
            celltitle.add(data.getColumnName(i + 1));
        }
        int fnindex = 1;//用来记录需要创建的文件数，
        while (true) {
            //创建Excel文件薄
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            //创建工作表sheeet
            SXSSFSheet sheet = workbook.createSheet();
            //创建第一行,写入表头
            SXSSFRow row = sheet.createRow(0);
            for (int i = 0; i < celltitle.size(); i++) {
                SXSSFCell cell = row.createCell(i);
                cell.setCellValue(celltitle.get(i));
            }
            //写入数据
            int rows = 1, limit = 0;
            while (rs.next()) {
                SXSSFRow nextrow = sheet.createRow(rows++);
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    SXSSFCell cell2 = nextrow.createCell(i - 1);
                    cell2.setCellValue(rs.getString(i));
                }
                //判断本次数据量是否达到写入Excel的最大行数
                if (++limit == maxRow) {
                    break;
                }
            }
            //limit等于0表示所有数据写入完毕，结束循环
            if (limit == 0) {
                break;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(conditions);
            //根据fnindex值判断是否要写入二个以上Excel文件
            if (fnindex != 1) {
                sb.append("-").append(fnindex);
            }
            //创建一个文件
            File wfile = new File("D:\\Users\\Admin\\Desktop\\结果集\\" + sb + ".xlsx");
            FileOutputStream op = new FileOutputStream(wfile);
            workbook.write(op);
            op.flush();
            op.close();
            System.out.println("文件[" + sb + ".xlsx]写入成功！完成时间：" + LocalDateTime.now().format(df));
            fnindex++;
        }
    }
}
