package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



public class Responder extends TelegramLongPollingBot {

    AllTasks allTasks = new AllTasks();
    String inputTask = "";
    boolean greeting = true;
    String task = "";
    int time = 0;
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            String userMessage = update.getMessage().getText().trim();

            if (inputTask.equals("task")) {
                task = userMessage;
                ReplyKeyboardMarkup replyKeyboardMarkup = TimeKeyboard.createTimeKeyboard();
                sendResponseWithKeyboard(chatId, "Please choose a time for the reminder:", replyKeyboardMarkup);
                inputTask = "setTime";
            } else if (inputTask.equals("setTime")) {
                switch (userMessage) {
                    case "in 1 hour":
                        time = 1;
                        break;
                    case "in 2 hours":
                        time = 2;
                        break;
                    case "in 5 hours":
                        time = 5;
                        break;
                    case "cancel":
                        inputTask = "otherTask";
                        break;
                    default:
                        sendResponse(chatId, "Invalid option. Please choose a valid time:");
                        inputTask = "otherTask";
                        break;
                }
                allTasks.createAllTasks(task + " - Reminder set for " + time + " hour(s)");
                sendResponse(chatId, "Task: " + task + " added with a reminder set for " + time + " hour(s).");
                inputTask = ""; // reset inputTask after handling
                task = "";
                inputTask = "otherTask";
            }
            if (greeting) {
                sendResponse(chatId, "Moin!  I'm EvaBot \uD83D\uDE42");
                InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardMarkup.getInlineKeyboardMarkup();
                sendResponseWithKeyboard(chatId, "Please choose an option:", inlineKeyboardMarkup);
                greeting = false;

            }
            if (inputTask.equals("otherTask")){
                InlineKeyboardMarkup inlineKeyboardMarkup2 = KeyboardMarkup2.getInlineKeyboardMarkup();
                sendResponseWithKeyboard(chatId, "Do you want to enter other tasks?", inlineKeyboardMarkup2);
            }

            if (chatId.isEmpty()) {
                throw new IllegalStateException("The chat id is not found");
            }
        }

        if (update.hasCallbackQuery() && update.getCallbackQuery().getData() != null && !update.getCallbackQuery().getData().isEmpty()) {
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            String callBackData = update.getCallbackQuery().getData();

            if (callBackData.equalsIgnoreCase("/enteringTask")) {
                sendResponse(chatId, "Enter a task");
                inputTask = "task";
            }
            if (callBackData.equalsIgnoreCase("/allTasks")) {
                sendResponse(chatId, allTasks.toString());
            }
            if (callBackData.equalsIgnoreCase("/sayBye")) {
                sendResponse(chatId, "Ok, see you later \uD83D\uDC4B");
                greeting = true;
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


    private void sendResponseWithKeyboard(String chatId, String text, InlineKeyboardMarkup keyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(keyboard);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendResponseWithKeyboard(String chatId, String text, ReplyKeyboardMarkup keyboard) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(keyboard);

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