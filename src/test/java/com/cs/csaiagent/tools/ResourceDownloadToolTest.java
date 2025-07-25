package com.cs.csaiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/25
 */
public class ResourceDownloadToolTest {

    @Test
    public void testDownloadResource() {
        ResourceDownloadTool tool = new ResourceDownloadTool();
        String url = "https://www.helloimg.com/i/2024/11/30/674acf2e00438.png";
        String fileName = "logo.png";
        String result = tool.downloadResource(url, fileName);
        assertNotNull(result);
    }
}