import taskRun.Tasktest;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import static taskRun.creatBat.executeBat;

public class test1 {
    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//设置日期格式
    static File directory = new File("");// 参数为空
    static String courseFile;

    static {
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static final Timer timer = new Timer();
    static String file = courseFile+"/"; //要执行文件的路径
    static int times = 0;
    static long runSeconds = 2;//设置任务多少秒后启动
    static long runbeteen = 5;//设置启动下个任务的间隔时间，单位秒
    static String runfileNameMul = "";//要执行的内容

    public static void main(String[] args) {
        runTaskDemo();
    }

    public static class Task extends TimerTask {
        public void run() {
            try {
                String[] runfileName = runfileNameMul.split("\\|");
                System.out.println("定时任务第" + (times+1) + "次启动,启动时间:" + LocalDateTime.now().format(df));
                executeBat(file, runfileName[times]);
                System.out.println("当前任务启动成功!");
                String endTime = LocalDateTime.now().plusSeconds(runbeteen).format(df);
                System.out.println("下次任务执行时间为" + runbeteen + "秒之后,预计启动时间:"+endTime);
                System.out.println("---------------------------------------------------------");
                times++;
                if(times == runfileName.length) {
                    timer.cancel();
                    System.out.println("定时任务已经全部执行完成......");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void runTaskDemo(){
        System.out.println("当前文件路径:"+courseFile);
        Scanner sc = new Scanner(System.in);
        //"请输入要执行的文件名，多个文件名用'|'隔开,注意文件名不能带有空格等特殊字符:"
        System.out.println("请输入要执行的文件名或命令，多个'|'隔开,注意文件名不能带有中文和空格等特殊字符:");
//        sc.useDelimiter("\n");
        runfileNameMul = sc.nextLine();
        //"请设置文件启动时间，n秒后启动，单位（秒）:"
        System.out.println("请设置文件启动时间,n秒后启动,单位(秒):");
        runSeconds = sc.nextLong();
        //"请设置启动多个文件之间的间隔时间，n秒间隔，单位（秒）:"
        System.out.println("请设置启动多个文件之间的间隔时间,n秒间隔,单位(秒):");
        runbeteen = sc.nextLong();
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusSeconds(runSeconds);
        long between = Duration.between(startTime, endTime).getSeconds();
        System.out.println("---------------------------------------------------------");
        System.out.println("任务开始启动...");
        System.out.println("开始时间:" + startTime.format(df));
        System.out.println(between + "秒后启动任务,预计启动时间:"+endTime.format(df));
        System.out.println("---------------------------------------------------------");
        timer.schedule(new Task(), between * 1000, runbeteen* 1000);

    }

}
