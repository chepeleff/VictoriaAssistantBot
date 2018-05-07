package userInteractions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public @Data class PreparedMessage {
    private RobotUser user;
    private CommandType commandType;
    private String body;
}
