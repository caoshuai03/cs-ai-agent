package com.cs.csaiagent.rag;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/24
 */

/**
 * 自定义基于 Token 的切词器
 */
@Component
class MyTokenTextSplitter {
    public List<Document> splitDocuments(List<Document> documents) {
        // 默认规则进行拆分，得到拆分片段列表
        TokenTextSplitter splitter = new TokenTextSplitter();
        return splitter.apply(documents);
    }

    public List<Document> splitCustomized(List<Document> documents) {
        // 自定义规则进行拆分，得到拆分片段列表
        // 规则：每个片段的最大 Token 数量为 200，最小Token 数量为 100， 重叠Token 数量为 10，处理时最大 Token 数量为 5000，是否使用字符
        TokenTextSplitter splitter =
                new TokenTextSplitter(200, 100, 10, 5000, true);
        return splitter.apply(documents);
    }
}