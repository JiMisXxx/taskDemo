import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test3 {
    public static void main(String[] args) {
        List<Map<String,String>> list = new ArrayList<>();
        Map <String,String> map1 = new HashMap<>();
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
        for (Map.Entry<String, String> a :map1.entrySet()){
            Map <String,String> map2 = new HashMap<>();
            map2.put(a.getKey(), a.getValue());
            list.add(map2);
        }
        for(int i = 0;i<list.size();i++) {
            StringBuilder sb = new StringBuilder(list.get(i).keySet().toString());
            sb.replace(0, 1, "");
            sb.replace(6, 7, "");
            System.out.println(sb.toString());
            System.out.println("名称"+list.get(i).get(sb.toString()));
        }
    }
}
