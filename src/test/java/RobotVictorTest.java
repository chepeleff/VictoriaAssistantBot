import static org.junit.Assert.assertEquals;

import org.junit.Test;
import robot.RobotVictor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RobotVictorTest {
    @Test
    public void testExtractBody() {
        String testMessageReceived = "!! question I have a question for you";
        String expectedResult = "I have a question for you";

        try {
            RobotVictor robotVictor = new RobotVictor();
            Method method = robotVictor.getClass().getDeclaredMethod("extractBody", String.class);
            method.setAccessible(true);
            String actualResult = (String) method.invoke(robotVictor, testMessageReceived);
            assertEquals(actualResult, expectedResult);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
