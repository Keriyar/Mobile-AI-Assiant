package com.example.mobile_ai_assitant;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;

import org.json.JSONArray;
import org.json.JSONObject;


public class OpenAIClient extends AppCompatActivity {

    private static final String API_URL = "https://api.chatanywhere.tech/v1/chat/completions";

    private final String message;       // 用户输入的消息
    private final String model;         // 模型名称（如 gpt-4）
    private final String apiKey;        // OpenAI API 密钥
    private final Boolean isStream;     // 是否启用流式响应
    private final Double temperature;  // 控制生成随机性的温度值

    // 构造函数
    public OpenAIClient(String apiKey, String model, String message, Boolean isStream, Double temperature) {
        this.message = message;
        this.model = model;
        this.apiKey = apiKey;
        this.isStream = isStream;
        this.temperature = temperature;
    }

    // 构造请求体
    @SuppressLint("NewApi") // 忽略版本兼容性问题
    public String getRequestBody() {
        return """
                {
                  "model": "%s",
                  "messages": [
                    {"role": "user", "content": "%s"}
                  ],
                  "temperature": %f,
                  "stream": %s
                }
                """.formatted(model, message, temperature, isStream);
    }
    //回调接口
    public interface ResponseCallback {
        void onSuccess(String content);
        void onError(String error);
    }
    //发送请求
    public void sendRequest(ResponseCallback callback) {
        OkHttpClient client = new OkHttpClient();

        String requestBody = getRequestBody();
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(requestBody, MediaType.get("application/json; charset=utf-8")))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (callback != null) {
                    callback.onError(e.getMessage());
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    if (callback != null) {
                        callback.onError("Request failed: " + response);
                    }
                    return;
                }

                try (ResponseBody responseBody = response.body()) {
                    if (responseBody != null) {
                        String responseString = responseBody.string();
                        JSONObject jsonResponse = new JSONObject(responseString);
                        JSONArray choices = jsonResponse.getJSONArray("choices");
                        String content = choices.getJSONObject(0).getJSONObject("message").getString("content");
                        if (callback != null) {
                            callback.onSuccess(content);
                        }
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onError("Error parsing response: " + e.getMessage());
                    }
                }
            }
        });
    }


}
