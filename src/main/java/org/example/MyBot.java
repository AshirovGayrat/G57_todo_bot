package org.example;

import org.example.entity.UserEntity;
import org.example.enums.UserStep;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBot extends TelegramLongPollingBot {


    private final Path todoPath = Path.of( "folder/todo.txt" );
    private final Path userPath = Path.of( "folder/user.txt" );

    private Map< Long, UserEntity > map = new HashMap<>();


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
        String text = message.getText(); /// "Ali"
        Long id = message.getChatId(); /// 1243

        SendMessage sendMessage = new SendMessage( );
        sendMessage.setChatId( id );

        UserEntity currentUser = map.get( id ); // User  -> NAME

        if ( text.equals("/start") ) {
            String menu = """
                    1. Todo List
                    2. Add Todo
                    3. Complete
                    4. Profile
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
        else if ( text.equals("4") ) {
            getUserInfo( id );
        } else if (text.equals("/reg")) {
            registration( message, currentUser );
        } else if (currentUser != null) {
            registration(message, currentUser);
        } else {
            saveTodo( message, text );
        }

    }

    private void getUserInfo(Long id) {
        List<String> list = null;
        try {
            list = Files.readAllLines(userPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        StringBuilder builder = new StringBuilder();
        for ( String s: list ) {
            if ( s.startsWith( String.valueOf( id ) ) ) {
                String[] userData = s.split("#");
                builder.append("Name: ").append( userData[1] ).append("\n");
                builder.append("Phone number: ").append(userData[2]).append("\n");
                break;
            }
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId( id );
        sendMessage.setText( builder.toString() );
        print( sendMessage );
    }

    private void registration(Message message, UserEntity currentUser) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId( message.getChatId() );
        if ( currentUser == null ) {
            sendMessage.setText("Ismingizni kiriting");
            UserEntity entity = new UserEntity();
            entity.setId( message.getChatId() );
            entity.setUserStep( UserStep.NAME );
            map.put( message.getChatId() , entity  );
        } else if ( currentUser.getUserStep().equals( UserStep.NAME ) ) {
            currentUser.setName( message.getText() );
            currentUser.setUserStep( UserStep.PHONE );
            map.put( message.getChatId() , currentUser );
            sendMessage.setText("Telefon raqam kiriting");
        } else if (currentUser.getUserStep().equals(UserStep.PHONE)) {
            currentUser.setPhone( message.getText() );
            currentUser.setUserStep( UserStep.PASSWORD );
            map.put( message.getChatId() , currentUser );
            sendMessage.setText( "Parol kiriting" );
        } else if (currentUser.getUserStep().equals(UserStep.PASSWORD)) {
            currentUser.setPassword( message.getText() );
            save( currentUser );

            map.remove( message.getChatId() );
            sendMessage.setText("Registratsiya jarayoni tugadi");
        }

        print(sendMessage);
    }


    private void save(UserEntity user) {

        try {

            if ( !Files.exists( userPath ) ) {
                Files.createFile(userPath);
            }
            Files.writeString( userPath, user.getId() + "#" + user.getName() + "#" + user.getPhone() + "#" + user.getPassword() + "\n" , StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private List<String> getTodoList(Long id) {
        try {
            return Files.readAllLines(todoPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void saveTodo(Message message, String text) {
        try {
            Files.writeString(todoPath,message.getChatId() + "#" + text + "\n", StandardOpenOption.APPEND);
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
