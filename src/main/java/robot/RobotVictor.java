package robot;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import userInteractions.CommandType;
import userInteractions.PreparedMessage;
import userInteractions.RobotUser;

import java.util.HashMap;
import java.util.Map;

public class RobotVictor extends TelegramLongPollingBot {

    private RobotUtils robotUtils;
    private String commandPrefix = "!!";
    private Map<String, CommandType> stringCommandTypeMap;

    public RobotVictor() {
        super();
        robotUtils = new RobotUtils();
        stringCommandTypeMap = getCommandTypeMap();
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (hasCommand(update)) {
            handleCommand(update.getMessage());
        }

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText(String.valueOf(update.getMessage().getFrom().getId()));
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    private void handleCommand(Message message) {
        String textBody = message.getText();
        CommandType command = extractCommand(textBody); // "!! command body"
        String body = extractBody(textBody);
        RobotUser robotUser;
        int telegramId = message.getFrom().getId();
        String userName = message.getFrom().getUserName();

        if (robotUtils.userExists(telegramId)) {
            robotUser = robotUtils.getUserById(telegramId);
        } else {
            robotUser = robotUtils.createNewUser(telegramId, userName);
        }

        if (command != CommandType.EMPTY) {
            // TODO: prepare message
            PreparedMessage preparedMessage = new PreparedMessage();
            preparedMessage.setUser(robotUser);
            preparedMessage.setCommandType(command);
            preparedMessage.setBody(body);

            robotUtils.handleCommand(preparedMessage);
        }
        
    }


    private CommandType extractCommand(String textBody) {
        String[] splittedBody = textBody.split(" ");
        if (splittedBody.length == 1) {
            return CommandType.EMPTY;
        } else {
            String stringCommand = splittedBody[1];
            CommandType parsedCommand = stringCommandTypeMap.get(stringCommand);
            return parsedCommand == null ? CommandType.EMPTY : parsedCommand;
        }
    }
    
    private String extractBody(String textBody) {
        String result = "";
        String[] splittedBody = textBody.split(" ");

        if (splittedBody.length > 2) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 2; i < splittedBody.length; i++) {
                stringBuilder.append(splittedBody[i]);
                stringBuilder.append(" ");
            }
            result = stringBuilder.toString();
            result = result.trim();
        }

        return result;
    }

    private boolean hasCommand(Update update) {
        Boolean result = false;

        if (update.hasMessage() && update.getMessage().hasText()) {
            String receivedText = update.getMessage().getText();
            result = receivedText.startsWith(commandPrefix);
        }

        return result;
    }

    private Map<String, CommandType> getCommandTypeMap() {
        Map<String, CommandType> commandTypeMap = new HashMap<>();

        commandTypeMap.put("question",  CommandType.ADD_QUESTION);
        commandTypeMap.put("вопрос",    CommandType.ADD_QUESTION);

        commandTypeMap.put("show",      CommandType.SHOW_QUESTIONS);
        commandTypeMap.put("показать",  CommandType.SHOW_QUESTIONS);
        commandTypeMap.put("покажи",    CommandType.SHOW_QUESTIONS);

        commandTypeMap.put("setrole",   CommandType.SET_ROLE);
        commandTypeMap.put("роль",      CommandType.SET_ROLE);

        commandTypeMap.put("clean",     CommandType.CLEAN_QUESTIONS);
        commandTypeMap.put("очистить",  CommandType.CLEAN_QUESTIONS);

        return commandTypeMap;
    }

    @Override
    public String getBotUsername() {
        // TODO: replace to resources
        return "VictoriaAssistantBot";
    }

    @Override
    public String getBotToken() {
        // TODO: replace to resources
        return "582320976:AAHQDqqWljd8ICwao615ZNed7NftdrxsCbs";
    }
}
