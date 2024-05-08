package com.iaco.screensound.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class GetInfoChatGPT {

    public static String getInfo(String artista) {
        OpenAiService service = new OpenAiService(System.getenv("OPENAI_APIKEY"));

        CompletionRequest requisicao = CompletionRequest.builder()
            .model("gpt-3.5-turbo-instruct")
            .prompt("Escreva uma pequena frase sobre o artista " + artista)
            .maxTokens(100)
            .temperature(0.7)
            .build();

        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText().trim();
        }
    }

