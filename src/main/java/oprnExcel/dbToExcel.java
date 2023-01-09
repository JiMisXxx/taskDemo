package oprnExcel;

import dbConnection.gettingConnection;
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

public class dbToExcel implements Runnable {
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
    static int threadcnt = 5; //�����߳���
    static AtomicInteger threadnow = new AtomicInteger(0);
    static int maxRow = 1000;//����Excel�ļ�����д����������
    List<String> myList = Arrays.asList("0","1","2","3","4","5");//ÿ��Excel��Ҫд�����ݵĲ�ѯ����
    String filename = null;//�ļ�������Ϊ����ʹ�ò�ѯ������Ϊ�ļ���
        static String str = "select * from menzhenRC221227 where `\uFEFF��������` = ?;";

    public static void main(String[] args) {
        running();
    }

    /**
     * ÿ���̴߳���һ����ѯ����������д�뵽Excel
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
     * ���̵߳���
     */
    public static void running() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadcnt);
        for (int i = 0; i < threadcnt; i++) {
            fixedThreadPool.execute(new dbToExcel());
        }
        fixedThreadPool.shutdown();
    }

    /**
     * POI SXSSFWorkbookд��Excel�ļ�
     */
    public void writeinto(String conditions) throws Exception {
        if (filename == null) {
            filename = conditions;
        }
        System.out.println("�ļ�[" + filename + ".xlsx]���ڻ�ȡ���ݣ�ʱ�䣺" + LocalDateTime.now().format(df));
        Connection con = gettingConnection.gettingCon("w_aaa");
        PreparedStatement ps = con.prepareStatement(str, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, conditions);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData data = rs.getMetaData();
        System.out.println("�ļ�[" + filename + ".xlsx]��ȡ������ɣ���ʼд�����ݣ�ʱ�䣺" + LocalDateTime.now().format(df));
        ArrayList<String> celltitle = new ArrayList<>();
        //��ȡ��ͷ�ֶ�
        for (int i = 0; i < data.getColumnCount(); i++) {
            celltitle.add(data.getColumnName(i + 1));
        }
        int fnindex = 1;//������¼��Ҫ�������ļ�����
        while (true) {
            //����Excel�ļ���
            SXSSFWorkbook workbook = new SXSSFWorkbook();
            //����������sheeet
            SXSSFSheet sheet = workbook.createSheet();
            //������һ��,д���ͷ
            SXSSFRow row = sheet.createRow(0);
            for (int i = 0; i < celltitle.size(); i++) {
                SXSSFCell cell = row.createCell(i);
                cell.setCellValue(celltitle.get(i));
            }
            //д������
            int rows = 1, limit = 0;
            while (rs.next()) {
                SXSSFRow nextrow = sheet.createRow(rows++);
                for (int i = 1; i <= data.getColumnCount(); i++) {
                    SXSSFCell cell2 = nextrow.createCell(i - 1);
                    cell2.setCellValue(rs.getString(i));
                }
                //�жϱ����������Ƿ�ﵽд��Excel���������
                if (++limit == maxRow) {
                    break;
                }
            }
            //limit����0��ʾ��������д����ϣ�����ѭ��
            if (limit == 0) {
                break;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(filename);
            //����fnindexֵ�ж��Ƿ�Ҫд���������Excel�ļ�
            if (fnindex != 1) {
                sb.append("-").append(fnindex);
            }
            //����һ���ļ�
            File wfile = new File("C:/Users/admin/Desktop/�����/" + sb.toString() + ".xlsx");
            FileOutputStream op = new FileOutputStream(wfile);
            workbook.write(op);
            op.flush();
            op.close();
            System.out.println("�ļ�[" + sb.toString() + ".xlsx]д��ɹ������ʱ�䣺" + LocalDateTime.now().format(df));
            fnindex++;
        }
    }
}
