package bot;


import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class TimeKeyboard {
    public static ReplyKeyboardMarkup createTimeKeyboard() {

        List<KeyboardRow> timeKeyboard = new ArrayList<>();

        // First option
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("in 1 hour");

        // Second option
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("in 2 hours");

        // Third option
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add("in 5 hours");

        // Forth option
        KeyboardRow keyboardForthRow = new KeyboardRow();
        keyboardForthRow.add("cancel");


        // Adding options to the keyboard
        timeKeyboard.add(keyboardFirstRow);
        timeKeyboard.add(keyboardSecondRow);
        timeKeyboard.add(keyboardThirdRow);
        timeKeyboard.add(keyboardForthRow);


        // Adding our keyboard to the chat
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(timeKeyboard);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        return replyKeyboardMarkup;
    }
}
