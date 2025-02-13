package com.javarush.telegram;

import com.javarush.telegram.ChatGPTService;
import com.javarush.telegram.DialogMode;
import com.javarush.telegram.MultiSessionTelegramBot;
import com.javarush.telegram.UserInfo;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;

public class TinderBoltApp extends MultiSessionTelegramBot {
    public static final String TELEGRAM_BOT_NAME = System.getenv("TELEGRAM_BOT_NAME"); 
    public static final String TELEGRAM_BOT_TOKEN = System.getenv("TELEGRAM_BOT_TOKEN");
    public static final String OPEN_AI_TOKEN = System.getenv("OPEN_AI_TOKEN"); 

    private ChatGPTService chatGPT = new ChatGPTService(OPEN_AI_TOKEN);
    private DialogMode currenMode = null;
    private  ArrayList<String>_list = new ArrayList<>();
    public TinderBoltApp() {
        super(TELEGRAM_BOT_NAME, TELEGRAM_BOT_TOKEN);
    }

    
    public void onUpdateEventReceived(Update update) {
        
        String nessage = getMessageText();
//команда start
        if (nessage.equals("/start")) {
            currenMode = DialogMode.MAIN;
            sendPhotoMessage("main");
            String text = loadMessage("main");
            sendTextMessage(text);

            showMainMenu("Начало всех начал", "/start", "Выберете соответсвие", "/matches", "Вопросы по теме история", "/question", "Напиши термин", "/terms", "Опиши события", "/understand", "Напиши интересующий тебе год", "/year");
            return;
        }

//команда year
        if (nessage.equals("/year")){
            currenMode = DialogMode.GPT;
            sendPhotoMessage("year");
            String text = loadMessage("gpt");
            sendTextMessage(text);
            return;
        }

        if (currenMode == DialogMode.GPT){
            String prompt = loadPrompt("year");
            Message nsg = sendTextMessage("/Подождите gpt набирает текст/");
            String answer = chatGPT.sendMessage(prompt, nessage);
            updateTextMessage(nsg, answer);
            return;
        }
//команда terms
        if (nessage.equals("/terms")){
            currenMode = DialogMode.TERMS;
            sendPhotoMessage("terms");
            String text = loadMessage("opener");
            sendTextMessage(text);
            return;
        }


        if (currenMode == DialogMode.TERMS){
            String prompt = loadPrompt("terms");
            Message nsg = sendTextMessage("/Подождите gpt набирает текст/");
            String answer = chatGPT.sendMessage(prompt, nessage);
            updateTextMessage(nsg, answer);
            return;
        }
//команда understand
        if (nessage.equals("/understand")){
            currenMode = DialogMode.UNDERSTAND;
            sendPhotoMessage("understand");
            String text = loadMessage("date");
            sendTextMessage(text);
            return;
        }


        if (currenMode == DialogMode.UNDERSTAND){
            String prompt = loadPrompt("understand");
            Message nsg = sendTextMessage("/Подождите gpt набирает текст/");
            String answer = chatGPT.sendMessage(prompt, nessage);
            updateTextMessage(nsg, answer);
            return;
        }
        //команда question
        if (nessage.equals("/question")){
            currenMode = DialogMode.QUESTION;
            sendPhotoMessage("profile");
            String text = loadMessage("profile");
            sendTextMessage(text);
            return;
        }


        if (currenMode == DialogMode.QUESTION){
            String prompt = loadPrompt("question");
            Message nsg = sendTextMessage("/Подождите gpt набирает текст/");
            String answer = chatGPT.sendMessage(prompt, nessage);
            updateTextMessage(nsg, answer);
            return;
        }
//команда matches
        if (nessage.equals("/matches")){
            currenMode = DialogMode.MATCHES;
            sendPhotoMessage("machs");
            String text = loadMessage("message");
            sendTextMessage(text);
            return;
        }


        if (currenMode == DialogMode.MATCHES){
            String prompt = loadPrompt("machs");
            Message nsg = sendTextMessage("/Подождите gpt набирает текст/");
            String answer = chatGPT.sendMessage(prompt, nessage);
            updateTextMessage(nsg, answer);
            return;
        }
        //команда gifts
        if (nessage.equals("/gifts")){
            currenMode = DialogMode.GIFTS;
            sendPhotoMessage("gifts");
            String text = loadMessage("gifts");
            sendTextMessage(text);
            return;
        }


        if (currenMode == DialogMode.GIFTS){
            String prompt = loadPrompt("gifts");
            Message nsg = sendTextMessage("/Подождите gpt набирает текст/");
            String answer = chatGPT.sendMessage(prompt, nessage);
            updateTextMessage(nsg, answer);
            return;
        }
        Message nsg = sendTextMessage("/Подождите GPT набирает текст/");
        String answer = chatGPT.addMessage(nessage);
        updateTextMessage(nsg, answer);
        return;





    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new TinderBoltApp());
    }
}
