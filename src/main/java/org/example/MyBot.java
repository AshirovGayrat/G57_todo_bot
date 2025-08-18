package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MyBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "@g57_first_bot";
    }
    @Override
    public String getBotToken() {
        return "8056426580:AAGvIXMfYejtrI5_dIZcyztazaU7TVZm87Q";
    }








}
