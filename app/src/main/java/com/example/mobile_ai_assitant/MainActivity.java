package com.example.mobile_ai_assitant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tv;


    private List<Message> messageList = new ArrayList<>();
    private RecyclerView msgRecyclerView;
    private EditText inputText;
    private LinearLayoutManager layoutManager;
    private MessageAdapter adapter;
    private Button btnLogin;
    private Button butNew;

    private String modelName;

    private OpenAIClient openAIClient; // 新增 OpenAIClient 实例



    @SuppressLint({"MissingInflatedId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.title_text);
        msgRecyclerView = findViewById(R.id.msg_recycler_view);
        inputText = findViewById(R.id.message_input);
        layoutManager = new LinearLayoutManager(this);
        adapter = new MessageAdapter(messageList = getData());
        btnLogin = findViewById(R.id.send_button);
        butNew = findViewById(R.id.more_options);


        //设置 RecyclerView
        msgRecyclerView.setLayoutManager(layoutManager);
        msgRecyclerView.setAdapter(adapter);




        //newmainActivity

        butNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewMainActivity.class);
                startActivity(intent);
                long currentTimeMillis = System.currentTimeMillis();//获取现在的时间（时间戳）作为对话标识
                String fileName = "conversation.json";
                ConversationStorage.saveToJSON(MainActivity.this,messageList,fileName);

            }
        });






        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建PopupMenu 创建弹出菜单
                PopupMenu popup = new PopupMenu(MainActivity.this, tv);
                // 从 XML 资源填充菜单
                popup.inflate(R.menu.main);
                // 显示弹出菜单
                popup.show();
                // 设置菜单项点击监听器
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // 处理菜单项点击事件
                        if (item.getItemId() == R.id.choice1) {
                            tv.setText(item.getTitle()); // 设置文本视图的内容为选项的名称
                            handleMenuItem1();
                            modelName= (String) item.getTitle();
                        } else if (item.getItemId() == R.id.choice2) {
                            tv.setText(item.getTitle());
                            handleMenuItem2();
                            modelName= (String) item.getTitle();
                        } else if (item.getItemId() == R.id.choice3) {
                            tv.setText(item.getTitle());
                            handleMenuItem3();
                            modelName= (String) item.getTitle();
                        } else if (item.getItemId() == R.id.choice4) {
                            tv.setText(item.getTitle());
                            handleMenuItem4();
                            modelName= (String) item.getTitle();
                        }

                        return true;
                    }
                });

            }



            private void handleMenuItem1() {
            }

            private void handleMenuItem2() {

            }

            private void handleMenuItem3() {

            }

            private void handleMenuItem4() {

            }


        });

        //发送按键

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = findViewById(R.id.message_input);
                String userMessage = inputText.getText().toString();

                if(!userMessage.isEmpty()){
                    messageList.add(new Message(userMessage, Message.TYPE_SEND));
                    adapter.notifyItemInserted(messageList.size()-1);
                    msgRecyclerView.scrollToPosition(messageList.size()-1);
                    inputText.setText("");

                    // 调用 OpenAIClient
                    callOpenAI(userMessage);

                    Log.d("MainActivity", "Message List:");
                    for (Message message : messageList) {
                        Log.d("MainActivity", message.getContent());
                        System.out.println("List"+message.getContent());
                    }
                }


            }
        });
    }

//    What is apple?
//    什么是苹果？

    private void callOpenAI(String message) {
        // 这里请替换为您的OpenAI API密钥和模型
        String apiKey = "sk-DwGTSy9YdgwOnyckUkD7QQQcWlGRz87adkWLDUiZiWRGiQVf";
        String model = modelName; // 使用的模型
        boolean isStream = false; // 可以根据需求设置
        double temperature = 0.7; // 温度设置，可根据需求调整

        openAIClient = new OpenAIClient(apiKey, model, message, isStream, temperature);
        openAIClient.sendRequest(new OpenAIClient.ResponseCallback() {
            @Override
            public void onSuccess(String content) {
                // 将AI的响应添加到消息列表并更新视图
                messageList.add(new Message(content, Message.TYPE_RECEIVED));
                runOnUiThread(() -> {
                    adapter.notifyItemInserted(messageList.size() - 1);
                    msgRecyclerView.scrollToPosition(messageList.size() - 1);
                });
            }

            @Override
            public void onError(String error) {
                // 可以在此处处理错误，例如显示错误消息
                runOnUiThread(() -> messageList.add(new Message("Error: " + error, Message.TYPE_RECEIVED)));
            }
        });
    }
    private List<Message> getData(){
        List<Message> list = new ArrayList<>();
        list.add(new Message("Hello", Message.TYPE_RECEIVED));
        return list;
    }






}