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
    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//设置日期格式
    static int threadcnt = 9; //启用线程数
    static AtomicInteger threadnow = new AtomicInteger(0);
    static int maxRow = 1000000;//单个Excel文件允许写入的最大行数
    //    List<String> myList = Arrays.asList("0","1","2","3","4","5");//每个Excel需要写入数据的查询条件
    static String str = "select * from abc1 where 病种编码 = ?;";

    public static void main(String[] args) {
        running();
    }

    /**
     * 每个线程处理一个查询条件的数据写入到Excel
     */
    @Override
    public void run() {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map1 = new HashMap<>();
        map1.put("M01103", "地中海贫血（海洋性贫血或珠蛋白生成障碍性贫血）");
        map1.put("M01102", "再生障碍性贫血");
        map1.put("M01200", "血友病");
        map1.put("M00300", "艾滋病");
        map1.put("M00105", "活动性肺结核");
        map1.put("M02101", "精神分裂症");
        map1.put("M02104", "分裂情感性障碍");
        map1.put("M02103", "持久的妄想性障碍（偏执性精神病）");
        map1.put("M02102", "双相（情感）障碍");
        map1.put("M02105", "癫痫所致精神障碍");
        map1.put("M02106", " 精神发育迟滞伴发精神障碍");
        map1.put("M07803", " 慢性肾功能不全（血透治疗）");
        map1.put("M07804", " 慢性肾功能不全（腹透治疗）");
        map1.put("M00503", " 恶性肿瘤放、化、介入或核素治疗");
        map1.put("M08300", " 器官移植抗排异治疗");
        map1.put("M01001", " 颅内良性肿瘤辅助用药");
        map1.put("M03900", " 高血压病");
        map1.put("M01600", " 糖尿病");
        map1.put("M05300", "慢性阻塞性肺疾病");
        map1.put("M04600", "冠心病");
        map1.put("M04803", "脑血管疾病后遗症");
        map1.put("M06900", "类风湿关节炎");
        map1.put("M05400", "支气管哮喘");
        map1.put("M00201", "慢性乙型肝炎");
        map1.put("M02300", "帕金森病");
        map1.put("M02500", "癫痫");
        map1.put("M07200", "强直性脊柱炎");
        map1.put("M06000", "克罗恩病");
        map1.put("M06501", "溃疡性结肠炎");
        map1.put("M06700", "银屑病");
        map1.put("M04301", "慢性心功能不全");
        map1.put("M07101", "系统性红斑狼疮");
        map1.put("M06201", " 肝硬化（失代偿期）");
        map1.put("M07800", " 慢性肾功能不全（非透析治疗）");
        map1.put("M03704", " 视网膜静脉阻塞所致黄斑水肿");
        map1.put("M12100", " 新冠肺炎出院患者门诊康复治疗");
        map1.put("M00504", "恶性肿瘤（非放化疗）");
        map1.put("M03701", "湿性年龄相关性黄斑变性");
        map1.put("M01609", "糖尿病黄斑水肿");
        map1.put("M03703", "脉络膜新生血管");
        map1.put("M00202", "丙型肝炎（HCV RNA阳性）");
        map1.put("M01908", "肢端肥大症");
        map1.put("M02900", "多发性硬化");
        map1.put("M04000", "肺动脉高压");
        map1.put("M01903", "C型尼曼匹克病");
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
     * 多线程调用
     */
    public static void running() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();;
        for (int i = 0; i < threadcnt; i++) {
            cachedThreadPool.execute(new dbToExcelTwo());
        }
        cachedThreadPool.shutdown();
    }

    /**
     * POI SXSSFWorkbook写入Excel文件
     *
     * @param conditions
     */
    public void writeinto(String conditions, String tioajian) throws Exception {
        System.out.println("文件[" + conditions + ".xlsx]正在获取数据，时间：" + LocalDateTime.now().format(df));
        Connection con = gettingConnection.gettingCon("w_aaa");
        PreparedStatement ps = con.prepareStatement(str, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, tioajian);
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
            File wfile = new File("C:/Users/admin/Desktop/结果集/" + sb.toString() + "门诊费用明细信息.xlsx");
            FileOutputStream op = new FileOutputStream(wfile);
            workbook.write(op);
            op.flush();
            op.close();
            System.out.println("文件[" + sb.toString() + ".xlsx]写入成功！完成时间：" + LocalDateTime.now().format(df));
            fnindex++;
        }
    }
}
