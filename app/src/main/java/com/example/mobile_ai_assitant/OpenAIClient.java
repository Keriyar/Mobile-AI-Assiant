package com.example.mobile_ai_assitant;

import android.annotation.SuppressLint;

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

public class OpenAIClient extends AppCompatActivity {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    private final String message;       // 用户输入的消息
    private final String model;         // 模型名称（如 gpt-4）
    private final String apiKey;        // OpenAI API 密钥
    private final Boolean isStream;     // 是否启用流式响应
    private final Integer temperature;  // 控制生成随机性的温度值

    // 构造函数
    public OpenAIClient(String apiKey, String model, String message, Boolean isStream, Integer temperature) {
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
                  "temperature": %d,
                  "stream": %s
                }
                """.formatted(model, message, temperature, isStream);
    }

    // 发送请求
    public void sendRequest() {
        OkHttpClient client = new OkHttpClient();

        String requestBody = getRequestBody(); // 调用 getRequestBody 方法生成请求体

        // 构造 HTTP 请求
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(requestBody, MediaType.get("application/json; charset=utf-8")))
                .build();

        // 执行异步请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 请求失败的回调
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 请求成功的回调
                if (!response.isSuccessful()) {
                    System.err.println("Request failed: " + response);
                    return;
                }

                // 处理流式响应
                try (ResponseBody responseBody = response.body()) {
                    if (responseBody != null) {
                        BufferedSource source = responseBody.source();
                        while (!source.exhausted()) {
                            String line = source.readUtf8Line();
                            if (line != null && !line.isEmpty()) {
                                System.out.println(line); // 打印逐行响应
                            }
                        }
                    }
                }
            }
        });
    }

    // Main 方法用于示例调用
    public static void main(String[] args) {
        // 实例化 OpenAIClient
        String apiKey = "your-api-key-here";
        String model = "gpt-4";
        String message = "Tell me a joke.";
        Boolean isStream = true;
        Integer temperature = 1;

        // 创建客户端并发送请求
        OpenAIClient client = new OpenAIClient(apiKey, model, message, isStream, temperature);
        client.sendRequest();
    }
}
