package MyLocal.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ThrowableUtil {
    /**
     * 根据异常信息获取异常堆栈中异常信息（所有异常信息）
     *
     * @return String 返回发异常的所有堆栈信息printStackTrace
     * @author dengxj
     */
    public static String stackTraceFullToString(Throwable t) {
        StringWriter sw = new StringWriter();

        try (PrintWriter pw = new PrintWriter(sw)) {
            //t.printStackTrace(pw);
            return sw.toString();
        }
    }


    /**
     * 20180726 增加 dengxj
     * 返回调用这个方法位置的 java源代码的行数
     *
     * @return
     */
    public static int getLine() {
        return new Throwable().getStackTrace()[1].getLineNumber();
    }

    /**
     * 适用于业务检查的错误信息所在的代码行做为错误编号
     *
     * @param errTitle 错误编号的前缀
     * @return 返回errTitle + 调用这个方法位置的 java源代码的行数,作为错误编号  比如 ry_12 ry是errTitle 代表入院 业务的第12行的业务错误信息
     */
    public static String getErrorNo(String errTitle) {
        return "[" + errTitle + "_" + new Throwable().getStackTrace()[1].getLineNumber() + "]";
    }

    /**
     * 获取调用这个方法位置的 java文件名和代码行数
     *
     * @return String 例如 ： ThrowableUtil第87行
     */
    public static String getJavaLineInfo() {
        StackTraceElement ste = new Throwable().getStackTrace()[1];

        assert ste.getFileName() != null;
        return ste.getFileName().replaceAll("\\.java", "第") + ste.getLineNumber() + "行";
    }

    /**
     * 适用于获取异常信息的第一个触发异常的代码行数作为错误编号
     *
     * @param className 调用的Class 例如TestStatus.class 或this.getClass()
     * @param errTitle  错误编号前缀
     * @param t         异常堆栈
     * @return String 返回 错误标题_异常首次发生代码行数  例如 ：[入院检查_36]
     */
    public static String getFirstStackTraceInfo(Class className, String errTitle, Throwable t) {
        StackTraceElement[] messages = t.getStackTrace();
        String errMsg = stackTraceFullToString(t);

        for (StackTraceElement message : messages) {
            if (message.getClassName().equalsIgnoreCase(className.getName())) {
                errMsg = t.toString() + "[" + errTitle + "_" + message.getLineNumber() + "]";
            }
        }
        return errMsg;
    }

    /**
     * 适用于获取异常信息的第一个触发异常的代码行数作为错误编号
     *
     * @param className 调用的Class 例如TestStatus.class 或this.getClass()
     * @param errTitle  错误编号前缀
     * @param t         异常堆栈
     * @return String 返回 错误标题_异常首次发生代码行数  例如 ：[入院检查_36]
     */
    public static String getFirstStackTraceInfo(Class className, String errTitle, Throwable t, String msg) {
        StackTraceElement[] messages = t.getStackTrace();
        String errMsg = stackTraceFullToString(t);

        for (StackTraceElement message : messages) {
            if (message.getClassName().equalsIgnoreCase(className.getName())) {
                errMsg = msg + "[" + errTitle + "_" + message.getLineNumber() + "]";
            }
        }
        return errMsg;
    }

}
