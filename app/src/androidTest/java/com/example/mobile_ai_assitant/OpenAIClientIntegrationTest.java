package com.example.mobile_ai_assitant;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import static org.junit.Assert.*;

public class OpenAIClientIntegrationTest {

    private static final String API_KEY = "sk-DwGTSy9YdgwOnyckUkD7QQQcWlGRz87adkWLDUiZiWRGiQVf  "; // 替换为你的实际 API 密钥

    @Test
    public void testActualNetworkRequestWithContentPrinting() {
        CountDownLatch latch = new CountDownLatch(1);

        OpenAIClient client = new OpenAIClient(
                API_KEY,
                "gpt-4",
                "介绍一下OpenAI",
                false,
                0.7
        );

        client.sendRequest(new OpenAIClient.ResponseCallback() {
            @Override
            public void onSuccess(String content) {
                System.out.println("Actual Content from API: " + content);
                assertNotNull("Response content should not be null", content);
                assertFalse("Response content should not be empty", content.isEmpty());
                latch.countDown();
            }

            @Override
            public void onError(String error) {
                fail("Request failed with error: " + error);
                latch.countDown();
            }
        });

        try {
            if (!latch.await(10, TimeUnit.SECONDS)) {
                fail("Test timed out.");
            }
        } catch (InterruptedException e) {
            fail("Test interrupted: " + e.getMessage());
        }
    }
}
