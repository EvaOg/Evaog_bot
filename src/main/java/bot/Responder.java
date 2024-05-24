package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



public class Responder extends TelegramLongPollingBot {

    AllTasks allTasks = new AllTasks();
    String inputTask = "";
    boolean greeting = true;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = String.valueOf(update.getMessage().getChatId());

            if (inputTask.equals("task")) {
                String newTask = update.getMessage().getText().trim();
                allTasks.createAllTasks(newTask);
                String str = allTasks.toString();
                sendResponse(chatId, str);
                inputTask = "";  // reset inputTask after handling
            }

            if (greeting) {
                sendResponse(chatId, "Moin!  I'm EvaBot \uD83D\uDE42");
                InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardMarkup.getInlineKeyboardMarkup();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                sendMessage.setText("Please choose an option:");
                try {
                    execute(sendMessage); // we tell telegram to send this message
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
                greeting = false;
            } else {
                InlineKeyboardMarkup inlineKeyboardMarkup2 = KeyboardMarkup2.getInlineKeyboardMarkup();
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setReplyMarkup(inlineKeyboardMarkup2);
                sendMessage.setText("Do you want to enter other tasks?");
                try {
                    execute(sendMessage); // we tell telegram to send this message
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }

            }

            if (chatId.isEmpty()) {
                throw new IllegalStateException("The chat id is not found");
            }
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData() != null && !update.getCallbackQuery().getData().isEmpty()) {
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            String callBackData = update.getCallbackQuery().getData();

            if (callBackData.equalsIgnoreCase("/enteringTask")) {
                sendResponse(chatId, "Enter task & choose the time");
                inputTask = "task";
            } else if (callBackData.equalsIgnoreCase("/allTasks")) {
                sendResponse(chatId, allTasks.toString());
            }
        }
    }

    private void sendResponse(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return Bot.USERNAME;
    }

    @Override
    public String getBotToken() {
        return Bot.BOT_TOKEN;
    }
}