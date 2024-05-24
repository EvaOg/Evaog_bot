package bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Responder extends TelegramLongPollingBot {

    AllTasks allTasks = new AllTasks();
    String nextCommand = "";
    boolean greeting = true;
    String task = "";
    int time = 0;
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1); //java utils, controls the time of exec of a task

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            String userMessage = update.getMessage().getText().trim();

            if (nextCommand.equals("task")) {
                task = userMessage;
                ReplyKeyboardMarkup replyKeyboardMarkup = TimeKeyboard.createTimeKeyboard();
                sendResponseWithKeyboard(chatId, "Please choose a time for the reminder:", replyKeyboardMarkup);
                nextCommand = "setTime";
            } else if (nextCommand.equals("setTime")) {
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
                        sendResponse(chatId, "This task is cancelled");
                        nextCommand = "otherTask";
                        return;
                    default:
                        sendResponse(chatId, "Invalid option. Try again.");
                        nextCommand = "otherTask";
                        return;
                }

                allTasks.createAllTasks(new Task(task, time));// creating object Task with 2 param & adding it to array
                reminder(task, chatId, time);

                sendResponse(chatId, "I'll remind you about the task: " + task + " " + userMessage);
                task = "";
                time = 0;
                nextCommand = "otherTask";
            }
            if (greeting) {
                sendResponse(chatId, "Moin!  I'm EvaBot \uD83D\uDE42");
                InlineKeyboardMarkup inlineKeyboardMarkup = KeyboardMarkup.getInlineKeyboardMarkup();
                sendResponseWithKeyboard(chatId, "Please choose an option:", inlineKeyboardMarkup);
                greeting = false;

            }
            if (nextCommand.equals("otherTask")) {
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
                nextCommand = "task";
            }
            if (callBackData.equalsIgnoreCase("/allTasks")) {
                if(!allTasks.tasks.isEmpty()) {
                    sendResponse(chatId, allTasks.toString());
                } else{
                    sendResponse(chatId, "There are no tasks");
                }
            }
            if (callBackData.equalsIgnoreCase("/sayBye")) {
                sendResponse(chatId, "Ok, see you later \uD83D\uDC4B");
                task = "";
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

    //create method for executor
    private void reminder(String textToRemind, String chatId, int time) {
        long delay = TimeUnit.SECONDS.toMillis(time);
String message = "‼\uFE0F Don't forget about your task: " + textToRemind;
        executor.schedule(() -> {
            sendResponse(chatId, message);
            System.out.println("reminded");
            for (int i = 0; i < allTasks.tasks.size(); i++) {
                if (allTasks.tasks.get(i).getTask().equals(textToRemind)) {
                    allTasks.removeTask(allTasks.tasks.get(i));
                }
            }
        }, delay, TimeUnit.MILLISECONDS);
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