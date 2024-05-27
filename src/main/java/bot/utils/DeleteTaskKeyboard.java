package bot.utils;

import bot.AllTasks;
import bot.model.Task;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class DeleteTaskKeyboard {
        public static ReplyKeyboardMarkup createDeleteTaskKeyBoard(AllTasks tasks) {

            List<KeyboardRow> optionToDeleteKeyboard = new ArrayList<>();


            for (Task task : tasks.tasks) {
                KeyboardRow row = new KeyboardRow();
                KeyboardButton button = new KeyboardButton(task.getTask());
                row.add(button);
                optionToDeleteKeyboard.add(row);
            }

            System.out.println("Creating now");

            // Adding our keyboard to the chat
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setKeyboard(optionToDeleteKeyboard);
            replyKeyboardMarkup.setOneTimeKeyboard(true);

            return replyKeyboardMarkup;
        }
    }


