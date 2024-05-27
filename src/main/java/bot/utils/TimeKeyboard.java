package bot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class TimeKeyboard {
    public static ReplyKeyboardMarkup createTimeKeyboard() {
        // replyKeyboardMarkup.setResizeKeyboard(true);
        // Keep the keyboard visible
        List<KeyboardRow> timeKeyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add("in 1 hour");

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add("in 2 hours");

        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add("in 5 hours");

        KeyboardRow keyboardForthRow = new KeyboardRow();
        keyboardForthRow.add("cancel");

        timeKeyboard.add(keyboardFirstRow);
        timeKeyboard.add(keyboardSecondRow);
        timeKeyboard.add(keyboardThirdRow);
        timeKeyboard.add(keyboardForthRow);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(timeKeyboard);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        return replyKeyboardMarkup;
    }
}
