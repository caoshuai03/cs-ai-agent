package com.cs.csaiagent.tools;

/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/26
 */

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 工具注册配置类
 */
@Configuration
public class ToolRegistration {

    @Value("${search-api.api-key}")
    private String searchApiKey;

    /**
     * 注册所有AI工具
     */
    @Bean
    public ToolCallback[] allTools() {
        // 实例化所有工具
        return ToolCallbacks.from(
                new FileOperationTool(),
                new WebSearchTool(searchApiKey),
                new WebScrapingTool(),
                new ResourceDownloadTool(),
                new TerminalOperationTool(),
                new PDFGenerationTool(),
//                new HtmlGenerationTool(),
//                new ImageSearchTool(),
//                new DateTimeTool(),
                new TerminateTool()
        );
    }
}

