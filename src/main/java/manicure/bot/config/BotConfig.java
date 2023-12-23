package manicure.bot.config;

import manicure.bot.service.BotService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@Configuration
public class BotConfig {

    private final BotService bot;

    public BotConfig(BotService bot) {
        this.bot = bot;
    }

    @Bean
    public BotSession startBot() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
             return telegramBotsApi.registerBot(bot);
        } catch (TelegramApiRequestException e) {
            throw  new RuntimeException(e);
        }
    }
}