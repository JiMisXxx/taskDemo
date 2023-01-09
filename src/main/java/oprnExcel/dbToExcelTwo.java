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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class dbToExcelTwo implements Runnable {
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
    static int threadcnt = 9; //�����߳���
    static AtomicInteger threadnow = new AtomicInteger(0);
    static int maxRow = 1000000;//����Excel�ļ�����д����������
    //    List<String> myList = Arrays.asList("0","1","2","3","4","5");//ÿ��Excel��Ҫд�����ݵĲ�ѯ����
    static String str = "select * from abc1 where ���ֱ��� = ?;";

    public static void main(String[] args) {
        running();
    }

    /**
     * ÿ���̴߳���һ����ѯ����������д�뵽Excel
     */
    @Override
    public void run() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("M01103", "���к�ƶѪ��������ƶѪ���鵰�������ϰ���ƶѪ��");
        map1.put("M01102", "�����ϰ���ƶѪ");
        map1.put("M01200", "Ѫ�Ѳ�");
        map1.put("M00300", "���̲�");
        map1.put("M00105", "��Էν��");
        map1.put("M02101", "�������֢");
        map1.put("M02104", "����������ϰ�");
        map1.put("M02103", "�־õ��������ϰ���ƫִ�Ծ��񲡣�");
        map1.put("M02102", "˫�ࣨ��У��ϰ�");
        map1.put("M02105", "������¾����ϰ�");
        map1.put("M02106", " ���������Ͱ鷢�����ϰ�");
        map1.put("M07803", " ���������ܲ�ȫ��Ѫ͸���ƣ�");
        map1.put("M07804", " ���������ܲ�ȫ����͸���ƣ�");
        map1.put("M00503", " ���������š�����������������");
        map1.put("M08300", " ������ֲ����������");
        map1.put("M01001", " ­����������������ҩ");
        map1.put("M03900", " ��Ѫѹ��");
        map1.put("M01600", " ����");
        map1.put("M05300", "���������Էμ���");
        map1.put("M04600", "���Ĳ�");
        map1.put("M04803", "��Ѫ�ܼ�������֢");
        map1.put("M06900", "���ʪ�ؽ���");
        map1.put("M05400", "֧��������");
        map1.put("M00201", "�������͸���");
        map1.put("M02300", "����ɭ��");
        map1.put("M02500", "���");
        map1.put("M07200", "ǿֱ�Լ�����");
        map1.put("M06000", "���޶���");
        map1.put("M06501", "�����Խ᳦��");
        map1.put("M06700", "��м��");
        map1.put("M04301", "�����Ĺ��ܲ�ȫ");
        map1.put("M07101", "ϵͳ�Ժ���Ǵ�");
        map1.put("M06201", " ��Ӳ����ʧ�����ڣ�");
        map1.put("M07800", " ���������ܲ�ȫ����͸�����ƣ�");
        map1.put("M03704", " ����Ĥ�����������»ư�ˮ��");
        map1.put("M12100", " �¹ڷ��׳�Ժ�������￵������");
        map1.put("M00504", "�����������ǷŻ��ƣ�");
        map1.put("M03701", "ʪ����������Իư߱���");
        map1.put("M01609", "���򲡻ư�ˮ��");
        map1.put("M03703", "����Ĥ����Ѫ��");
        map1.put("M00202", "���͸��ף�HCV RNA���ԣ�");
        map1.put("M01908", "֫�˷ʴ�֢");
        map1.put("M02900", "�෢��Ӳ��");
        map1.put("M04000", "�ζ�����ѹ");
        map1.put("M01903", "C������ƥ�˲�");
        for (Map.Entry<String, String> a : map1.entrySet()) {
            Map<String, String> map2 = new HashMap<>();
            map2.put(a.getKey(), a.getValue());
            list.add(map2);
        }
        int threadnowint = threadnow.getAndIncrement();
        for (int j = threadnowint; j < list.size(); j = j + threadcnt) {
            try {
                StringBuilder sb = new StringBuilder(list.get(j).keySet().toString());
                sb.replace(0, 1, "");
                sb.replace(6, 7, "");
                writeinto(list.get(j).values().toString(), sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ���̵߳���
     */
    public static void running() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();;
        for (int i = 0; i < threadcnt; i++) {
            cachedThreadPool.execute(new dbToExcelTwo());
        }
        cachedThreadPool.shutdown();
    }

    /**
     * POI SXSSFWorkbookд��Excel�ļ�
     *
     * @param conditions
     */
    public void writeinto(String conditions, String tioajian) throws Exception {
        System.out.println("�ļ�[" + conditions + ".xlsx]���ڻ�ȡ���ݣ�ʱ�䣺" + LocalDateTime.now().format(df));
        Connection con = gettingConnection.gettingCon("w_aaa");
        PreparedStatement ps = con.prepareStatement(str, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, tioajian);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData data = rs.getMetaData();
        System.out.println("�ļ�[" + conditions + ".xlsx]��ȡ������ɣ���ʼд�����ݣ�ʱ�䣺" + LocalDateTime.now().format(df));
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
            sb.append(conditions);
            //����fnindexֵ�ж��Ƿ�Ҫд���������Excel�ļ�
            if (fnindex != 1) {
                sb.append("-").append(fnindex);
            }
            //����һ���ļ�
            File wfile = new File("C:/Users/admin/Desktop/�����/" + sb.toString() + "���������ϸ��Ϣ.xlsx");
            FileOutputStream op = new FileOutputStream(wfile);
            workbook.write(op);
            op.flush();
            op.close();
            System.out.println("�ļ�[" + sb.toString() + ".xlsx]д��ɹ������ʱ�䣺" + LocalDateTime.now().format(df));
            fnindex++;
        }
    }
}
