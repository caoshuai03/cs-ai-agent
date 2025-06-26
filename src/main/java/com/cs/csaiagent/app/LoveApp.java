package com.cs.csaiagent.app;

import com.cs.csaiagent.advisor.MyLoggerAdvisor;
import com.cs.csaiagent.advisor.ProhibitedWordAdvisor;
import com.cs.csaiagent.advisor.ReReadingAdvisor;
import com.cs.csaiagent.chatmemory.MybatisPlusChatMemory;
import com.cs.csaiagent.rag.QueryRewriter;
import com.cs.csaiagent.service.ChatMemoryService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;


/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/23
 */

@Component
@Slf4j
public class LoveApp {

    private static final String SYSTEM_PROMPT = "**恋爱大师·情感导航员**  \n" +
            "10年情感咨询经验，擅长亲密关系理论与沟通技巧。提供中立建议，保护隐私。" +
            "通过情绪确认、需求拆解（3-5维度）、心理学理论（如非暴力沟通）解析问题，给出2种实操策略（如\"我句式\"对话模拟），引导关系边界建立。" +
            "示例：\"遗忘纪念日可能涉及记忆模式/爱意表达方式差异，建议用'观察+感受'沟通\"。" +
            "不评判道德、不做医疗建议，严守伦理规范。您的专属情感顾问，随时为您解惑。";
    private final ChatClient chatClient;

    /**
     * 初始化 ChatClient
     *
     * @param dashscopeChatModel
     */
    public LoveApp(ChatModel dashscopeChatModel, MybatisPlusChatMemory mybatisPlusChatMemory) {
//        ChatMemory chatMemory = new InMemoryChatMemory();
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(new MessageChatMemoryAdvisor(mybatisPlusChatMemory))//实现了多轮对话记忆
                .build();
    }

    /**
     * AI 基础对话（支持多轮对话记忆）
     *
     * @param message
     * @param chatId
     * @return
     */
    public String doChat(String message, String chatId) {
        ChatResponse response = chatClient.prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))//设置会话id和历史会话数量
                .advisors(new MyLoggerAdvisor())//日志记录
                .advisors(new ProhibitedWordAdvisor())//违禁词检查
                .call().chatResponse();
        String content = response.getResult().getOutput().getText();
//        log.info("content: {}", content);
        return content;
    }

    /**
     * AI 基础对话（支持多轮对话记忆，SSE 流式传输）
     *
     * @param message
     * @param chatId
     * @return
     */
    public Flux<String> doChatByStream(String message, String chatId) {
        return chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .advisors(new MyLoggerAdvisor())
                .stream()
                .content();
    }


    record LoveReport(String title, List<String> suggestions) {

    }

    /**
     * AI 恋爱报告功能（实战结构化输出）
     *
     * @param message
     * @param chatId
     * @return
     */
    public LoveReport doChatWithReport(String message, String chatId) {
        LoveReport loveReport = chatClient
                .prompt()
                .system(SYSTEM_PROMPT + "每次对话后都要生成恋爱结果，标题为{用户名}的恋爱报告，内容为建议列表")//结构化指令
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                .call()
                .entity(LoveReport.class);//反序列化
        log.info("loveReport: {}", loveReport);
        return loveReport;
    }


    @Resource
    private VectorStore loveAppVectorStore;

    @Resource
    private Advisor loveAppRagCloudAdvisor;
    //
    @Resource
    private VectorStore pgVectorVectorStore;

    @Resource
    private QueryRewriter queryRewriter;

    /**
     * 和 RAG 知识库进行对话
     *
     * @param message
     * @param chatId
     * @return
     */
    public String doChatWithRag(String message, String chatId) {
        // 查询重写
        String rewrittenMessage = queryRewriter.doQueryRewrite(message);
        ChatResponse chatResponse = chatClient
                .prompt()
                // 使用改写后的查询
//                .user(rewrittenMessage)
                .user(rewrittenMessage)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                // 应用 RAG 知识库问答
//                .advisors(new QuestionAnswerAdvisor(loveAppVectorStore))
                // 应用 RAG （基于云知识库服务）
//                .advisors(loveAppRagCloudAdvisor)
                // 应用 RAG 检索增强服务（基于 PgVector 向量存储）
                .advisors(new QuestionAnswerAdvisor(pgVectorVectorStore))
                // 应用自定义的 RAG 检索增强服务（文档查询器 + 上下文增强器）
//                .advisors(
//                        LoveAppRagCustomAdvisorFactory.createLoveAppRagCustomAdvisor(
//                                loveAppVectorStore, "单身"
//                        )
//                )
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
//        log.info("content: {}", content);
        return content;
    }

    // AI 调用工具能力
    @Resource
    private ToolCallback[] allTools;

    /**
     * AI 恋爱报告功能（支持调用工具）
     *
     * @param message
     * @param chatId
     * @return
     */
    public String doChatWithTools(String message, String chatId) {
        ChatResponse chatResponse = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                .tools(allTools)
                .call()
                .chatResponse();
        String content = chatResponse.getResult().getOutput().getText();
//        log.info("content: {}", content);
        return content;
    }


    @Resource
    private ToolCallbackProvider toolCallbackProvider;

    public String doChatWithMcp(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10))
                // 开启日志，便于观察效果
                .advisors(new MyLoggerAdvisor())
                .tools(allTools)
                .tools(toolCallbackProvider)
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
//        log.info("content: {}", content);
        return content;
    }


}
