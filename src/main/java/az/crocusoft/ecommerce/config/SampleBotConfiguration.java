//package az.crocusoft.ecommerce.config;
//
//import az.crocusoft.ecommerce.bot.SampleBot;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.telegram.telegrambots.meta.TelegramBotsApi;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
//
//@Configuration
//public class SampleBotConfiguration {
//
//    @Bean
//    public TelegramBotsApi telegramBotsApi(SampleBot sampleBot) throws TelegramApiException {
//        var botsApi = new TelegramBotsApi(DefaultBotSession.class);
//        botsApi.registerBot(sampleBot);
//        return botsApi;
//    }
//}
