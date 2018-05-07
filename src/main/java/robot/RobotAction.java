package robot;

import userInteractions.PreparedMessage;

@FunctionalInterface
public interface RobotAction {
    void apply(PreparedMessage message);
}
