package com.example.mobile_ai_assitant;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NewMainActivity extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.title_text);


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建PopupMenu 创建弹出菜单
                PopupMenu popup = new PopupMenu(NewMainActivity.this, tv);
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
                        } else if (item.getItemId() == R.id.choice2) {
                            tv.setText(item.getTitle());
                            handleMenuItem2();
                        } else if (item.getItemId() == R.id.choice3) {
                            tv.setText(item.getTitle());
                            handleMenuItem3();
                        } else if (item.getItemId() == R.id.choice4) {
                            tv.setText(item.getTitle());
                            handleMenuItem4();
                        }
                        return true;
                    }
                });


                //发送按键
                Button btnLogin = findViewById(R.id.send_button);
                btnLogin.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionevent) {
                        Toast.makeText(NewMainActivity.this, "Message sent!", Toast.LENGTH_SHORT).show();

                        return false;
                    }
                });

                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText inputmessage = findViewById(R.id.message_input);
                        String message = inputmessage.getText().toString();
                        Log.i("TouchEvent", "Click:" + message);
                    }
                });

            }

            //切换item并给列表设置监听器

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
//        MenuInflater inflater = new MenuInflater(this);
//        inflater.inflate(R.menu.main, menu);
//        super.onCreateContextMenu(menu, v, menuInfo);
//        getMenuInflater().inflate(R.menu.main, menu);
//        menu.setHeaderTitle("模型");
//    }
//    @Override
//    public boolean onContextItemSelected(MenuItem item){
//        if (item.getItemId() == R.id.choice1){
//            tv.setText(item.getTitle()); // 设置文本视图的内容为选项的名称
//            handleMenuItem1();
//        } else if (item.getItemId() == R.id.choice2) {
//            tv.setText(item.getTitle());
//            handleMenuItem2();
//        } else if (item.getItemId() == R.id.choice3) {
//            tv.setText(item.getTitle());
//            handleMenuItem3();
//        } else if (item.getItemId() == R.id.choice4) {
//            tv.setText(item.getTitle());
//            handleMenuItem4();
//        }
//        return true;
//    }

            private void handleMenuItem1() {
            }

            private void handleMenuItem2() {

            }

            private void handleMenuItem3() {

            }

            private void handleMenuItem4() {

            }


        });

    }
}