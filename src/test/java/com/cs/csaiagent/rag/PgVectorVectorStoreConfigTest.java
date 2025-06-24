package com.cs.csaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

//@SpringBootTest
//class PgVectorVectorStoreConfigTest {
//
//    @Resource
//    private VectorStore pgVectorVectorStore;
//
//    @Test
//    void pgVectorVectorStore() {
//        List<Document> documents = List.of(
//                new Document("鱼皮的编程导航有什么用？学编程啊，做项目啊", Map.of("meta1", "meta1")),
//                new Document("程序员鱼皮的原创项目教程 codefather.cn"),
//                new Document("鱼皮这小伙子比较帅气", Map.of("meta2", "meta2")));
//        // 添加文档
//        pgVectorVectorStore.add(documents);
//        // 相似度查询
//        List<Document> results = pgVectorVectorStore.similaritySearch(SearchRequest.builder().query("怎么学编程啊").topK(3).build());
//        Assertions.assertNotNull(results);
//    }
//}

@SpringBootTest
public class PgVectorVectorStoreConfigTest {

    @Resource
    VectorStore pgVectorVectorStore;

    @Test
    void test() {
        List<Document> documents = List.of(
                new Document("LenYan 是一位热爱技术的开发者，专注于人工智能、Java 后端开发和系统架构设计。他的博客涵盖了 Spring Boot、AI 应用开发、数据库优化以及 DevOps 等热门主题。", Map.of("author", "lenyan")),

                new Document("LenYanJGK 是 Len Yan 维护的一个技术分享平台，内容涵盖 Java、Spring AI、PostgreSQL、向量数据库应用等前沿技术，适合对智能系统感兴趣的开发者学习参考。", Map.of("source", "lenyanjgk.github.io")),

                new Document("在 LenYanJGK 博客中，你可以找到关于如何使用 Spring AI 构建智能代理（Agent）、集成向量数据库如 pgVector，并实现本地化的 RAG 检索增强生成系统等内容。", Map.of("topic", "RAG", "tags", "Spring AI, PostgreSQL")));

        pgVectorVectorStore.add(documents);

        List<Document> results = pgVectorVectorStore.similaritySearch(SearchRequest.builder().query("LenYan是做什么的").topK(2).build());
        System.out.println(results);
        Assertions.assertNotNull(results);
    }

}