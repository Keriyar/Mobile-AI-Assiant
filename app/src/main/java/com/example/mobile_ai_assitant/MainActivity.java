package com.example.mobile_ai_assitant;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView responseTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // 绑定 TextView
        responseTextView = findViewById(R.id.responseTextView);

        // 测试 OpenAIClient
        testOpenAIClient();
    }
    private void testOpenAIClient() {
        // 初始化参数
        String apiKey = "sk-DwGTSy9YdgwOnyckUkD7QQQcWlGRz87adkWLDUiZiWRGiQVf "; // 替换为您的 OpenAI API 密钥
        String model = "gpt-3.5-turbo";
        String message = "介绍一下Postman";
        Boolean isStream = false;
        Double temperature = 0.8;

        // 创建 OpenAIClient 实例
        OpenAIClient client = new OpenAIClient(apiKey, model, message, isStream, temperature);

        // 调用 API 并处理回调
        client.sendRequest(new OpenAIClient.ResponseCallback() {
            @Override
            public void onSuccess(String content) {
                // 更新响应内容到 TextView
                runOnUiThread(() -> responseTextView.setText(content));
            }

            @Override
            public void onError(String error) {
                // 显示错误信息
                runOnUiThread(() -> responseTextView.setText("Error: " + error));
            }
        });
    }


}