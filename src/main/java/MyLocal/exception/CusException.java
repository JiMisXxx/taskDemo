package MyLocal.exception;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.sql.SQLSyntaxErrorException;

@Aspect
@Component
public class CusException {

    public static final String CLASS_ALIAS = "CusException";

    @Pointcut("execution(* MyLocal.exception.CusException.*(..))")
    public void pointCut() {
        //切面
    }

    @AfterThrowing(throwing = "e", pointcut = "pointCut()")
    public static void CusThrowable(JoinPoint jp, Throwable e) throws Exception {
//     System.out.println("异常信息"+e);
        Class<?> cls = null;
        Object fieldValue = null;
        // 获取bo中的类别名，以便在提示信息中展示
        try {
            cls = jp.getTarget().getClass();
            fieldValue = cls.getField("CLASS_ALIAS").get(cls);
        } catch (Exception ex) {
            if (cls == null) {
                throw new Exception("无法获取抛出异常的信息");
            } else {
                fieldValue = cls.getName();
            }
        }
        if (e instanceof SQLSyntaxErrorException) {
            throw new Exception("不知道该数据库" + ThrowableUtil.getErrorNo((String) fieldValue) );
        }
    }

}