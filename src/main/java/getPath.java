import java.io.File;
import java.io.IOException;
import java.net.URL;

public class getPath {
    public static void main(String[] args) throws IOException {
        System.out.println("文件路径:");
//        File f = new File(getPath.class.getResource("/").getPath());
//        System.out.println(f.toString());
//        File f2 = new File(getPath.class.getResource("").getPath());
//        System.out.println(f2);

        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        System.out.println(courseFile);

//        URL xmlpath = getPath.class.getClassLoader().getResource("");
//        System.out.println(xmlpath);
//
//
//        System.out.println(System.getProperty("java.class.path"));
    }
}
