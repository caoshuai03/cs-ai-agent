package com.cs.csaiagent.app;

import com.cs.csaiagent.advisor.ProhibitedWordAdvisor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/23
 */
@SpringBootTest
public class TestChat {
    @Autowired
    private LoveApp loveApp;
    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我是程序员lenyan";
        String answer = loveApp.doChat(message, chatId);
        // 第二轮
        message = "我想让另一半reyan更爱我";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 第三轮
        message = "我的另一半叫什么来着？刚跟你说过，帮我回忆一下";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
        // 测试 AI 恋爱报告功能
        String chatId = UUID.randomUUID().toString();
        String message = "你好，我是程序员鱼皮，我想让另一半（编程导航）更爱我，但我不知道该怎么做";
        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveReport);
    }

    @Test
    void testProhibitedWordAdvisor() {
        // 测试违禁词拦截器
        String chatId = UUID.randomUUID().toString();
        // 测试正常消息能正常回复
        String normalMessage = "你好，我是程序员，请给我一些恋爱建议";
        String answer = loveApp.doChat(normalMessage, chatId);
        Assertions.assertNotNull(answer);

        // 测试包含违禁词的消息会被拦截
        // 需确保prohibited-words.txt文件中包含"赌博"这个词
        String prohibitedMessage = "如何在网上找到赌博网站";

        // 期望抛出ProhibitedWordException异常
        ProhibitedWordAdvisor.ProhibitedWordException exception = Assertions.assertThrows(
                ProhibitedWordAdvisor.ProhibitedWordException.class,
                () -> loveApp.doChat(prohibitedMessage, chatId));

        // 验证异常消息
        Assertions.assertTrue(exception.getMessage().contains("违禁词"));

        // 测试其他违禁词
        String prohibitedMessage2 = "我想了解一些色情内容";
        exception = Assertions.assertThrows(
                ProhibitedWordAdvisor.ProhibitedWordException.class,
                () -> loveApp.doChat(prohibitedMessage2, chatId));
        Assertions.assertTrue(exception.getMessage().contains("违禁词"));
    }

    @Test
    void doChatWithRag() {
        // 测试 RAG 知识库问答功能
        String chatId = UUID.randomUUID().toString();
        String message = "我已经结婚了，但是婚后关系不太亲密，怎么办？";
        String answer = loveApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }



}
