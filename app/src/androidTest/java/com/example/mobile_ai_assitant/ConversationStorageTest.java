package com.example.mobile_ai_assitant;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.mobile_ai_assitant.ConversationStorage;
import com.example.mobile_ai_assitant.Message; // 替换为你的实际包名

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ConversationStorageTest {

    private Context context;
    private String testFileName = "test_conversation.json";
    private File testFile;

    @Before
    public void setUp() {
        // 获取测试环境的 Context
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // 定义测试文件路径
        testFile = new File(context.getFilesDir(), testFileName);
    }

    @After
    public void tearDown() {
        // 删除测试文件
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testSaveToJSON() {
        // 创建测试数据
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("Hello, AI!", Message.TYPE_SEND));
        messages.add(new Message("Hello, User!", Message.TYPE_RECEIVED));

        // 调用保存方法
        ConversationStorage.saveToJSON(context, messages, testFileName);

        // 验证文件是否创建成功
        assertTrue(testFile.exists());
    }

    @Test
    public void testLoadFromJSON() {
        // 创建并保存测试数据
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("How are you?", Message.TYPE_SEND));
        messages.add(new Message("I'm fine, thank you!", Message.TYPE_RECEIVED));
        ConversationStorage.saveToJSON(context, messages, testFileName);

        // 加载文件并验证内容
        List<Message> loadedMessages = ConversationStorage.loadFromJSON(context, testFileName);

        // 验证数据大小
        assertEquals(2, loadedMessages.size());

        // 验证每条消息的内容和类型
        assertEquals("How are you?", loadedMessages.get(0).getContent());
        assertEquals(Message.TYPE_SEND, loadedMessages.get(0).getType());

        assertEquals("I'm fine, thank you!", loadedMessages.get(1).getContent());
        assertEquals(Message.TYPE_RECEIVED, loadedMessages.get(1).getType());
    }
}
