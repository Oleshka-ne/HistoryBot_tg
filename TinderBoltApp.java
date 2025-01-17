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
// TELEGRAM_BOT_TOKEN = System.getenv(" TELEGRAM_BOT_TOKEN")
public class TinderBoltApp extends MultiSessionTelegramBot {
    public static final String TELEGRAM_BOT_NAME = "The_helper_for_history_bot"; 
    public static final String TELEGRAM_BOT_TOKEN = "7276003389:AAEXwiEAjldMRWKjQ87AMYnapupUcCzHauo";
    public static final String OPEN_AI_TOKEN = "gpt:y8HQgXidYWEPQ52jBuwnJFkblB3T8AScAHOTiNP46pG97Qae"; 

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

            showMainMenu("Начало всех начал", "/start", "Выберете соответсви", "/matches", "Вопросы по теме история", "/question", "Напиши термин", "/terms", "Опиши события", "/understand", "Напиши интересующий тебе год", "/year");
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
            String prompt = loadPrompt("gpt");
            Message nsg = sendTextMessage("/Подождите gpt набирает текст/");
            String answer = chatGPT.sendMessage(prompt, nessage);
            updateTextMessage(nsg, answer);
            return;
        }
//команда terms
        if (nessage.equals("/terms")){
            currenMode = DialogMode.OPENER;
            sendPhotoMessage("terms");
            String text = loadMessage("opener");
            sendTextMessage(text);
            return;
        }


        if (currenMode == DialogMode.OPENER){
            String prompt = loadPrompt("opener");
            Message nsg = sendTextMessage("/Подождите gpt набирает текст/");
            String answer = chatGPT.sendMessage(prompt, nessage);
            updateTextMessage(nsg, answer);
            return;
        }
//команда understand
        if (nessage.equals("/understand")){
            currenMode = DialogMode.DATE;
            sendPhotoMessage("understand");
            String text = loadMessage("date");
            sendTextMessage(text);
            return;
        }


        if (currenMode == DialogMode.DATE){
            String prompt = loadPrompt("date");
            Message nsg = sendTextMessage("/Подождите gpt набирает текст/");
            String answer = chatGPT.sendMessage(prompt, nessage);
            updateTextMessage(nsg, answer);
            return;
        }
        if (nessage.equals("/question")){
            currenMode = DialogMode.PROFILE;
            sendPhotoMessage("profile");
            String text = loadMessage("profile");
            sendTextMessage(text);
            return;
        }


        if (currenMode == DialogMode.PROFILE){
            String prompt = loadPrompt("profile");
            Message nsg = sendTextMessage("/Подождите gpt набирает текст/");
            String answer = chatGPT.sendMessage(prompt, nessage);
            updateTextMessage(nsg, answer);
            return;
        }

        if (nessage.equals("/matches")){
            currenMode = DialogMode.MESSAGE;
            sendPhotoMessage("machs");
            String text = loadMessage("message");
            sendTextMessage(text);
            return;
        }


        if (currenMode == DialogMode.MESSAGE){
            String prompt = loadPrompt("machs");
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
