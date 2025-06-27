package com.cs.csaiagent.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/25
 */

/**
 * 网页抓取工具类
 */
public class WebScrapingTool {
    // 定义最大字符数
    private static final int MAX_CHARACTERS = 10000;

    /**
     * 抓取指定URL的网页内容
     */
    @Tool(description = "Scrape the content of a web page, limit the content to 10000 characters")
    public String scrapeWebPage(@ToolParam(description = "URL of the web page to scrape") String url) {
        try {
            // 使用Jsoup获取页面内容
            Document document = Jsoup.connect(url).get();
            String html = document.html();
            // 截取不超过最大字符数的内容
            return html.substring(0, Math.min(html.length(), MAX_CHARACTERS));
        } catch (Exception e) {
            return "Error scraping web page: " + e.getMessage();
        }
    }
}
