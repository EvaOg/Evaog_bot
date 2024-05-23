package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Responder extends TelegramLongPollingBot {

    /*
    1. The program make every sek a GET require to Telegram  using
        URL: https://api.telegram.org/BotToken/getMe
    2. Program receives messages from users as JSON file (that first
        is automatically edited by the library) and then come to the method onUpdateReceived method
    */

    @Override
    public void onUpdateReceived(Update update) {

        String response = "Sorry, I haven't understood your message. Please push any button from above";

        String ChatId = String.valueOf(update.getMessage().getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(ChatId); // without ChatId it will be an exception
        sendMessage.setText(response);

        if(update.hasCallbackQuery() && update.getCallbackQuery().getData() != null && !update.getCallbackQuery().getData().isEmpty()){
            String callBackData = update.getCallbackQuery().getData();
            if(callBackData.equals("Option 1")){
                sendMessage.setText("Option 1");
            }

        }

        if(update.hasMessage() && update.getMessage().hasText()) { //we check first of all that there is a message, not an empty str and it's a text

            String userMessage = update.getMessage().getText().trim(); //trim remove any empty characters at the beginning & at the end of the string

                sendMessage.setText("Moin I'm Evaog_bot =) Let's start! Please choose: ");

                //Creating a keyboard
                InlineKeyboardMarkup inlineKeyboardMarkup = getInlineKeyboardMarkup();

                sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        }


        try {
            sendApiMethod(sendMessage); // we tell telegram to send this message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private static InlineKeyboardMarkup getInlineKeyboardMarkup() {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        // Create a button's row
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

        // First option
        InlineKeyboardButton option1 = new InlineKeyboardButton();
        option1.setText("Option 1");
        option1.setCallbackData("Option 1");

        // Second option
        InlineKeyboardButton option2 = new InlineKeyboardButton();
        option2.setText("Option 2");
        option2.setCallbackData("Option 2");

        // Adding options to the keyboard
        buttonsRow.add(option1);
        buttonsRow.add(option2);

        // setting this list with options to the keyboard
        keyboard.add(buttonsRow);

        //adding our keyboard to the chat
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
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
