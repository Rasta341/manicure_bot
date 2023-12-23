package manicure.bot.service;

import lombok.SneakyThrows;
import manicure.bot.database.ClientRepository;
import manicure.bot.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class BotService extends TelegramLongPollingBot {
    String clientDate;
    String clientTime;
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
            private String botToken;

    @Autowired
    private ClientRepository clientRepository;



    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        User client = update.getMessage().getFrom();
        Long userId = update.getMessage().getFrom().getId();

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            if ( messageText.contains("/signup")
                    || "Вернуться".equals(messageText)
                    || messageText.contains("/change")) {
                sendDateButtons(userId, "Свободные даты: ");
            }
            else if (messageText.matches("\\d{2}\\.\\d{2}")) {
                clientDate = String.valueOf(update.getMessage().getText());
                sendTimeButtons(userId, "Свободное время: ");
            }
            else if(messageText.matches("\\d{1,2}:\\d{2}")){
                clientTime = String.valueOf(update.getMessage().getText());
                sendConfirmButtons(userId, "Записаться на " + clientDate +  ", на " + clientTime + "?");
            }
            else if (messageText.equalsIgnoreCase("Записаться")) {

                if(){
                    clientRepository.save(new Client(client.getUserName(), clientDate, clientTime));
                    sendMessageToUser(5349370758L, client.getFirstName() + " записалась на: " + clientDate + ", на " + clientTime);
                    sendMessageToUser(userId, "Вы," + client.getFirstName() + " записаны на: " + clientDate + ", на " + clientTime);
                }
            }

        }
    }
    public void sendMessageToUser(Long chatId, List<KeyboardRow> rowList, String text) {

            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            keyboardMarkup.setKeyboard(rowList);
            keyboardMarkup.setOneTimeKeyboard(true);
            keyboardMarkup.setResizeKeyboard(true);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(text);
            sendMessage.setReplyMarkup(keyboardMarkup);

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        public void sendMessageToUser(Long chatId, String text) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(text);

            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
        public void sendDateButtons(Long chatId, String text) {
            Calendar calendar = Calendar.getInstance();
            List<KeyboardRow> keyboard = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                calendar.add(Calendar.DAY_OF_MONTH, 1 );
                DateFormat df = new SimpleDateFormat("dd.MM");
                KeyboardRow row = new KeyboardRow();
                row.add(df.format(calendar.getTime()));
                keyboard.add(row);
            }
            sendMessageToUser(chatId, keyboard, text);
        }

        public void sendTimeButtons(Long chatId, String text) {
            List<KeyboardRow> keyboard = new ArrayList<>();
            for (int i = 8; i <= 18; i += 2) {
                KeyboardRow row = new KeyboardRow();
                row.add(i + ":00");
                keyboard.add(row);
            }
            sendMessageToUser(chatId, keyboard, text);
        }
        public void sendConfirmButtons(Long chatId, String text) {
            List<KeyboardRow> keyboard = new ArrayList<>();
            for (int i = 0; i < 1; i++) {
                KeyboardRow row = new KeyboardRow();
                row.add("Записаться");
                row.add("Вернуться");
                keyboard.add(row);
            }
            sendMessageToUser(chatId, keyboard, text);
        }





    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}