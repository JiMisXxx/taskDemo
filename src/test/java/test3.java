import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test3 {
    public static void main(String[] args) {
        List<Map<String,String>> list = new ArrayList<>();
        Map <String,String> map1 = new HashMap<>();
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
            System.out.println("����"+list.get(i).get(sb.toString()));
        }
    }
}
