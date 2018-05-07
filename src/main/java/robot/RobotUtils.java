package robot;

import DAO.DAOManager;
import DAO.csvDatabaseManager;
import userInteractions.CommandType;
import userInteractions.PreparedMessage;
import userInteractions.RobotUser;
import userInteractions.UserRole;

import java.util.HashMap;
import java.util.Map;

public class RobotUtils {
    private Map<CommandType, RobotAction> messagesActionMap;
    private Map<UserRole, CommandType[]> rolesPermissionMap;
    private DAOManager dbManager;

    public RobotUtils() {
        initialize();
    }

    public void handleCommand(PreparedMessage preparedMessage) {
        CommandType command = preparedMessage.getCommandType();
        if (roleAllowsCommand(command, preparedMessage.getUser().getUserRole())) {
            messagesActionMap.get(command).apply(preparedMessage);
        }
    }

    public RobotUser createNewUser(int telegramId, String userName) {
        RobotUser newUser = new RobotUser();
        newUser.setTelegramUserId(telegramId);
        newUser.setUserName(userName);
        newUser.setUserRole(UserRole.USER);

        dbManager.saveUser(newUser);

        return newUser;
    }

    public RobotUser getUserById(int telegramId) {
        return dbManager.getUserById(telegramId);
    }

    public boolean userExists(int telegramId) {
        return dbManager.userExists(telegramId);
    }

    private void initialize() {
        messagesActionMap = getMessagesActionMap();
        rolesPermissionMap = getRolesPermissionMap();
        // TODO: inject with Spring
        dbManager = new csvDatabaseManager();
    }

    private Map<CommandType, RobotAction> getMessagesActionMap() {
        Map<CommandType, RobotAction> messagesMap = new HashMap<>();
        messagesMap.put(CommandType.ADD_QUESTION,       this::addQuestion);
        messagesMap.put(CommandType.SHOW_QUESTIONS,     this::showQuestions);
        messagesMap.put(CommandType.CLEAN_QUESTIONS,    this::cleanQuestions);
        messagesMap.put(CommandType.SET_ROLE,           this::setRole);

        return messagesMap;
    }

    private Map<UserRole, CommandType[]> getRolesPermissionMap() {
        Map<UserRole, CommandType[]> permissionMap = new HashMap<>();

        CommandType[] adminPermissions = {
                CommandType.ADD_QUESTION,
                CommandType.SHOW_QUESTIONS,
                CommandType.CLEAN_QUESTIONS,
                CommandType.SET_ROLE
        };
        CommandType[] userPermissions = {
                CommandType.ADD_QUESTION
        };

        permissionMap.put(UserRole.ADMIN, adminPermissions);
        permissionMap.put(UserRole.USER, userPermissions);

        return permissionMap;
    }

    private void showQuestions(PreparedMessage message) {
        // TODO:
    }

    private void cleanQuestions(PreparedMessage message) {
        // TODO:
    }

    private void addQuestion(PreparedMessage message) {
        // TODO:
    }

    private void setRole(PreparedMessage message) {
        // TODO:
    }

    private boolean roleAllowsCommand(CommandType commandType, UserRole userRole) {
        CommandType[] allowedCommands = rolesPermissionMap.get(userRole);
        for (CommandType allowedCommand : allowedCommands) {
            if (commandType == allowedCommand) {
                return true;
            }
        }

        return false;
    }
}
