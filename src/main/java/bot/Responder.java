package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

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
                InlineKeyboardMarkup inlineKeyboardMarkup = getInlineKeyboardMarkup();
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
                InlineKeyboardMarkup inlineKeyboardMarkup2 = getInlineKeyboardMarkup2();
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

    private static InlineKeyboardMarkup getInlineKeyboardMarkup() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        // Create a button's row
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

        // First option
        InlineKeyboardButton option1 = new InlineKeyboardButton();
        option1.setText("Enter a task");
        option1.setCallbackData("/enteringTask");

        // Second option
        InlineKeyboardButton option2 = new InlineKeyboardButton();
        option2.setText("Show all tasks");
        option2.setCallbackData("/allTasks");

        // Adding options to the keyboard
        buttonsRow.add(option1);
        buttonsRow.add(option2);

        // Setting this list with options to the keyboard
        keyboard.add(buttonsRow);

        // Adding our keyboard to the chat
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }

    private static InlineKeyboardMarkup getInlineKeyboardMarkup2() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        // Create a button's row
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

        // First option
        InlineKeyboardButton option1 = new InlineKeyboardButton();
        option1.setText("Yes");
        option1.setCallbackData("/enteringTask");

        // Second option
        InlineKeyboardButton option2 = new InlineKeyboardButton();
        option2.setText("No");
        option2.setCallbackData("/sayBuy");

        // Second option
        InlineKeyboardButton option3 = new InlineKeyboardButton();
        option3.setText("Show all tasks");
        option3.setCallbackData("/allTasks");

        // Adding options to the keyboard
        buttonsRow.add(option1);
        buttonsRow.add(option2);
        buttonsRow.add(option3);

        // Setting this list with options to the keyboard
        keyboard.add(buttonsRow);

        // Adding our keyboard to the chat
        InlineKeyboardMarkup inlineKeyboardMarkup2 = new InlineKeyboardMarkup();
        inlineKeyboardMarkup2.setKeyboard(keyboard);
        return inlineKeyboardMarkup2;
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