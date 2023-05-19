package com.cc.ccso.openAPI;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import com.alibaba.fastjson.JSON;
import com.cc.ccso.exception.ChatException;
import com.cc.ccso.model.entity.Messages;
import com.cc.ccso.openAPI.Api;

import com.cc.ccso.openAPI.model.BaseResponse;
import com.cc.ccso.openAPI.model.ChatCompletionRequest;
import com.cc.ccso.openAPI.model.ChatCompletionResponse;
import io.reactivex.Single;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.net.Proxy;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ChatGPT {
    /**
     * keys
     */
    private String apiKey = "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";

    private List<String> apiKeyList;

    private static final String API_URL = "https://open.aiproxy.xyz/v1/chat/completions";
    /**
     * 自定义api host使用builder的方式构造client
     */
    @Builder.Default
    private String apiHost = Api.DEFAULT_API_HOST;
    private Api apiClient;
    private OkHttpClient okHttpClient;
    /**
     * 超时 默认300
     */
    @Builder.Default
    private long timeout = 3000;
    /**
     * okhttp 代理
     */
    @Builder.Default
    private Proxy proxy = Proxy.NO_PROXY;

    private String context = null;

    /**
     * 初始化
     */
    public ChatGPT init() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(chain -> {
            Request original = chain.request();
            String key = apiKey;
            if (apiKeyList != null && !apiKeyList.isEmpty()) {
                key = RandomUtil.randomEle(apiKeyList);
            }

            Request request = original.newBuilder()
                    .header(Header.AUTHORIZATION.getValue(), "Bearer " + key)
                    .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        }).addInterceptor(chain -> {
            Request original = chain.request();
            Response response = chain.proceed(original);
            if (!response.isSuccessful()) {
                String errorMsg = response.body().string();

                log.error("请求异常：{}", errorMsg);
                BaseResponse baseResponse = JSON.parseObject(errorMsg, BaseResponse.class);
                if (Objects.nonNull(baseResponse.getError())) {
                    log.error(baseResponse.getError().getMessage());
                    throw new ChatException(baseResponse.getError().getMessage());
                }
                throw new ChatException("error");
            }
            return response;
        });

        client.connectTimeout(timeout, TimeUnit.SECONDS);
        client.writeTimeout(timeout, TimeUnit.SECONDS);
        client.readTimeout(timeout, TimeUnit.SECONDS);
        if (Objects.nonNull(proxy)) {
            client.proxy(proxy);
        }
        OkHttpClient httpClient = client.build();
        this.okHttpClient = httpClient;


        this.apiClient = new Retrofit.Builder()
                .baseUrl(this.apiHost)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(Api.class);

        return this;
    }


    /**
     * 最新版的GPT-3.5 chat completion 更加贴近官方网站的问答模型
     *
     * @param chatCompletionRequest 问答参数
     * @return 答案
     */
    public ChatCompletionResponse chatCompletion(ChatCompletionRequest chatCompletionRequest) {
        Single<ChatCompletionResponse> chatCompletionResponse =
                this.apiClient.chatCompletion(chatCompletionRequest);
        return chatCompletionResponse.blockingGet();
    }


    /**
     * 简易版
     *
     * @param messages 问答参数
     */
    public ChatCompletionResponse chatCompletion(List<Messages> messages) {
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder().messages(messages).build();
        return this.chatCompletion(chatCompletionRequest);
    }

    /**
     * 直接问
     */
//    public String chat(String message) {
//        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
//                .messages(Arrays.asList(Chat.of(message)))
//                .build();
//        ChatCompletionResponse response = this.chatCompletion(chatCompletionRequest);
//        return response.getChoices().get(0).getChatMsg().getContent();
//    }

}
