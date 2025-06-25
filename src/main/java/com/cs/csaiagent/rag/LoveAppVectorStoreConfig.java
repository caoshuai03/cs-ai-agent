package com.cs.csaiagent.rag;


import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/24
 */

/**
 * 恋爱大师向量数据库配置（初始化基于本地内存的向量数据库 Bean）
 */
@Configuration
public class LoveAppVectorStoreConfig {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Resource
    private MyTokenTextSplitter myTokenTextSplitter;

    @Resource
    private MyKeywordEnricher myKeywordEnricher;

    @Bean
    VectorStore loveAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        // 初始化基于内存的向量数据库
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();

        // 每次启动都会加载，会影响性能，所以注释掉了
        /*// 加载文档
        List<Document> documentList = loveAppDocumentLoader.loadMarkdowns();
        // 自主切分文档
        List<Document> splitDocuments = myTokenTextSplitter.splitCustomized(documentList);
        // 通过大模型给文档生成关键词，自动补充关键词元信息
        List<Document> enrichedDocuments = myKeywordEnricher.enrichDocuments(documentList);
        // 将文档添加到本地内存
        simpleVectorStore.add(enrichedDocuments);*/
        return simpleVectorStore;
    }
}
