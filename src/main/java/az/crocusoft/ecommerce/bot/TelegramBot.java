package az.crocusoft.ecommerce.bot;

import az.crocusoft.ecommerce.exception.InvalidLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${spring.bot.username}")
    private String botUsername;


    public TelegramBot(@Value("${spring.bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            SendMessage sendMessage = new SendMessage();
            Long chatId = update.getMessage().getChatId();
            sendMessage.setChatId(chatId);
            sendMessage.setText("Invalid login attempt. Your account has been temporarily locked.");
            execute(sendMessage);
        } catch (Exception e) {
            log.error(String.valueOf(e));
            throw new RuntimeException();
        }
    }

    public void sendMessage(Long chatId, String text) {
        String chatIdStr = String.valueOf(chatId);
        SendMessage message = new SendMessage(chatIdStr, text);
        try {
            execute(message);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}