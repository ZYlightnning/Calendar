package ljd.calendar;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Unit test for simple App.
 */

public class AppTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */

    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp(){
        //计时器  单线程
        Timer timer=new Timer();
        //任务
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                System.out.println("123");
            }
        };
        /**
         timer.schedule(timerTask, 1000); // 1秒后执行一次
         timer.schedule(timerTask, 2000, 2000); // 两秒后每两秒执行一次
         timer.scheduleAtFixedRate(timerTask, 3000, 3000); // 3秒后每3秒执行一次
         timer.scheduleAtFixedRate(task, new Date(), 4000); // 每4秒执行一次
         */
        System.out.println(123);
        timer.schedule(timerTask,2000);
    }
}
