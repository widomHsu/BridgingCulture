//package edu.monash.bridgingculture.service.config;
//
//import org.springframework.ai.openai.OpenAiChatClient;
//import org.springframework.ai.openai.OpenAiChatOptions;
//import org.springframework.ai.openai.api.OpenAiApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//
//import jakarta.annotation.Resource;
//
//@Configuration
//public class OpenAiChatClientConfig {
//
//    @Resource
//    Environment env;
//
//    @Bean
//    public OpenAiChatClient openAiChatClient(){
//        OpenAiApi openAiApi = new OpenAiApi(env.getProperty("OPENAI_API_KEY"));
//
//        return new OpenAiChatClient(openAiApi, OpenAiChatOptions.builder()
//                .withModel(env.getProperty("OPENAI_model"))
//                .withMaxTokens(200)
//                .build());
//    }
//}
