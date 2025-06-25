package com.cs.csaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/25
 */
class WebScrapingToolTest {

    @Test
    void scrapeWebPage() {
        WebScrapingTool webScrapingTool = new WebScrapingTool();
        String url = "https://blog.csdn.net/m0_69334152?type=blog";
        String result = webScrapingTool.scrapeWebPage(url);
        System.out.println(result);
        Assertions.assertNotNull(result);
    }
}
