import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static MyLocal.taskRun.creatBat.executeBat;
public class test1 {
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
    static File directory = new File("");// ����Ϊ��
    static String courseFile;

    static {
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static final Timer timer = new Timer();
    static String file = courseFile+"/"; //Ҫִ���ļ���·��
    static int times = 0;
    static long runSeconds = 2;//������������������
    static long runbeteen = 5;//���������¸�����ļ��ʱ�䣬��λ��
    static String runfileNameMul = "";//Ҫִ�е�����

    public static void main(String[] args) {
        runTaskDemo();
    }

    public static class Task extends TimerTask {
        public void run() {
            try {
                String[] runfileName = runfileNameMul.split("\\|");
                System.out.println("��ʱ�����" + (times+1) + "������,����ʱ��:" + LocalDateTime.now().format(df));
                executeBat(file, runfileName[times]);
                System.out.println("��ǰ���������ɹ�!");
                String endTime = LocalDateTime.now().plusSeconds(runbeteen).format(df);
                System.out.println("�´�����ִ��ʱ��Ϊ" + runbeteen + "��֮��,Ԥ������ʱ��:"+endTime);
                System.out.println("---------------------------------------------------------");
                times++;
                if(times == runfileName.length) {
                    timer.cancel();
                    System.out.println("��ʱ�����Ѿ�ȫ��ִ�����......");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void runTaskDemo(){
        System.out.println("��ǰ�ļ�·��:"+courseFile);
        Scanner sc = new Scanner(System.in);
        //"������Ҫִ�е��ļ���������ļ�����'|'����,ע���ļ������ܴ��пո�������ַ�:"
        System.out.println("������Ҫִ�е��ļ�����������'|'����,ע���ļ������ܴ������ĺͿո�������ַ�:");
//        sc.useDelimiter("\n");
        runfileNameMul = sc.nextLine();
        //"�������ļ�����ʱ�䣬n�����������λ���룩:"
        System.out.println("�������ļ�����ʱ��,n�������,��λ(��):");
        runSeconds = sc.nextLong();
        //"��������������ļ�֮��ļ��ʱ�䣬n��������λ���룩:"
        System.out.println("��������������ļ�֮��ļ��ʱ��,n����,��λ(��):");
        runbeteen = sc.nextLong();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(runSeconds);
        long between = Duration.between(startTime, endTime).getSeconds();
        System.out.println("---------------------------------------------------------");
        System.out.println("����ʼ����...");
        System.out.println("��ʼʱ��:" + startTime.format(df));
        System.out.println(between + "�����������,Ԥ������ʱ��:"+endTime.format(df));
        System.out.println("---------------------------------------------------------");
        timer.schedule(new Task(), between * 1000, runbeteen* 1000);

    }

}
