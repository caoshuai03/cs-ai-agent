package com.cs.csaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/25
 */
@SpringBootTest
class WebSearchToolTest {

    @Value("${search-api.api-key}")
    private String searchApiKey;

    @Test
    void searchWeb() {
        WebSearchTool webSearchTool = new WebSearchTool(searchApiKey);
        String query = "介绍一下特朗普";
        String result = webSearchTool.searchWeb(query);
        Assertions.assertNotNull(result);
    }
}
