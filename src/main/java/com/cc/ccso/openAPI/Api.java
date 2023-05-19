package com.cc.ccso.openAPI;


import com.cc.ccso.openAPI.model.ChatCompletionRequest;
import com.cc.ccso.openAPI.model.ChatCompletionResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 *
 */
public interface Api {

    String DEFAULT_API_HOST = "https://open.aiproxy.xyz";


    /**
     * chat
     */
    @POST("v1/chat/completions")
    Single<ChatCompletionResponse> chatCompletion(@Body ChatCompletionRequest chatCompletionRequest);

}
