package bot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardMarkup2 {
    public static InlineKeyboardMarkup getInlineKeyboardMarkup() {
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
        option2.setCallbackData("/sayBye");

        // Third option
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
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);
        return inlineKeyboardMarkup;
    }
}

