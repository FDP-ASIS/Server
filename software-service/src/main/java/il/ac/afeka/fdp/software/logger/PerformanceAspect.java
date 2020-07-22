package il.ac.afeka.fdp.software.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceAspect {
    private Log log = LogFactory.getLog(PerformanceAspect.class);

    @Around("@annotation(il.ac.afeka.fdp.software.logger.SoftwarePerformance)")
    public Object measure (ProceedingJoinPoint jp) throws Throwable {
        long beginTime = System.currentTimeMillis();

        try {
            return jp.proceed();
        } catch (Throwable e) {
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long elapsed = endTime - beginTime;

            log.debug("*********** " + elapsed + "ms");
        }
    }
}