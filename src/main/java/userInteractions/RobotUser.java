package userInteractions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of="telegramUserId")
public @Data class RobotUser {
    private int telegramUserId;
    private String userName;
    private UserRole userRole;
}
