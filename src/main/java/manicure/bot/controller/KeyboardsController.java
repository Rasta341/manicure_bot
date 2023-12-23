package manicure.bot.controller;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KeyboardsController {


    public static SendMessage sendKeyBoard(Long chatId, List<KeyboardRow> rowList, String text) {

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rowList);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(keyboardMarkup);

        return sendMessage;
    }

    private ReplyKeyboardMarkup createReplyKeyboardMarkup(List<KeyboardRow> rowList) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rowList);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);
        return keyboardMarkup;
    }

    public static List<KeyboardRow> createKeyboardWithDates() {
        Calendar calendar = Calendar.getInstance();
        List<KeyboardRow> keyboard = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1 );
            DateFormat df = new SimpleDateFormat("dd.MM");
            KeyboardRow row = new KeyboardRow();
            row.add(df.format(calendar.getTime()));
            keyboard.add(row);
        }
        return keyboard;
    }

    public static List<KeyboardRow> createKeyboardWithTimes() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        for (int i = 8; i <= 18; i++) {
            KeyboardRow row = new KeyboardRow();
            row.add(i + ":00");
            keyboard.add(row);
        }
        return keyboard;
    }

    private List<KeyboardRow> createKeyboardWithConfirmOptions() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            KeyboardRow row = new KeyboardRow();
            row.add("Записаться");
            row.add("Вернуться");
            keyboard.add(row);
        }
        return keyboard;
    }
}
