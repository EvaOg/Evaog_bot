
package bot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

    public class KeyboardMarkup {
        public static InlineKeyboardMarkup getInlineKeyboardMarkup() {
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
    }

