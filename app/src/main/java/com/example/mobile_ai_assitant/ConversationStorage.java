package com.example.mobile_ai_assitant;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ConversationStorage {
    String fileName = "conversation.json";

    // 保存对话到 JSON 文件
    public static void saveToJSON(Context context, List<Message> messages, String fileName) {
        // 将 List<Message> 转换为 JSON 字符串
        Gson gson = new Gson();
        String json = gson.toJson(messages);

        // 将 JSON 写入文件
        File file = new File(context.getFilesDir(), fileName);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从 JSON 文件加载对话
    public static List<Message> loadFromJSON(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Message>>() {}.getType();
            String json = new String(java.nio.file.Files.readAllBytes(file.toPath()));
            return gson.fromJson(json, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
