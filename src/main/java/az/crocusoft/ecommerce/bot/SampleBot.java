//package az.crocusoft.ecommerce.bot;
//
//import az.crocusoft.ecommerce.exception.InvalidLoginException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//import java.util.concurrent.TimeUnit;
//
//@Component
//@Slf4j
//public class SampleBot extends TelegramLongPollingBot {
//
//
//    @Value("${spring.bot.username}")
//    private String botUsername;
//
//
//    public SampleBot(@Value("${spring.bot.token}") String botToken) {
//        super(botToken);
//    }
//
//    @Override
//    public String getBotUsername() {
//        return botUsername;
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        try {
//                SendMessage sendMessage = new SendMessage();
//                Long chatId = update.getMessage().getChatId();
//                sendMessage.setChatId(chatId);
//                sendMessage.setText("Invalid login attempt. Your account has been temporarily locked.");
//                execute(sendMessage);
//                TimeUnit.SECONDS.sleep(3);
//        } catch (Exception e) {
//            log.error(String.valueOf(e));
//            throw new InvalidLoginException("Invalid login attempt. Your account has been temporarily locked.");
//        }
//    }
//}