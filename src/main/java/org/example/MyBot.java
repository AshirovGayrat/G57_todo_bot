package org.example;

import org.example.entity.TodoEntity;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {


    private final Path path = Path.of( "folder/todo.txt" );


    @Override
    public void onUpdateReceived(Update update) {
        if ( update.hasMessage() ) {
            Message message = update.getMessage();

            if ( message.hasText() ) {
                messageHandler( message );
            }

        }
    }


    private void messageHandler(Message message) {
        String text = message.getText();
        Long id = message.getChatId();

        SendMessage sendMessage = new SendMessage( );
        sendMessage.setChatId( id );

        if ( text.equals("/start") ) {
            String menu = """
                    1. Todo List
                    2. Add Todo
                    3. Complete
                    """;
            sendMessage.setText( "Assalomu Alaykum " + message.getFrom().getFirstName() + "\n" + menu );
            print( sendMessage );
        }
        else if ( text.equals("1") ) {
            List<String> list = getTodoList( id );
            StringBuilder builder = new StringBuilder();
            for ( String s: list ) {
                String[] str = s.split("#");
                if ( str[0].equals( String.valueOf( id ) ) ) {
                    builder.append( str[1] ).append("\n");
                }
            }
            sendMessage.setText(builder.toString());
            print( sendMessage );
        }
        else if ( text.equals("2") ) {
            sendMessage.setText("Enter todo:");
            print( sendMessage );
        }
        else if ( text.equals("3") ) {

        }
        else {
            saveTodo( message, text );
        }

    }

    private List<String> getTodoList(Long id) {
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void saveTodo(Message message, String text) {
        try {
            Files.writeString( path,message.getChatId() + "#" + text + "\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void print( SendMessage sendMessage ) {
        try {
            execute( sendMessage );
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
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
