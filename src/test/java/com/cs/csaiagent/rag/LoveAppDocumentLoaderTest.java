package com.cs.csaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/24
 */


@SpringBootTest
class LoveAppDocumentLoaderTest {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Test
    void loadMarkdowns() {
        loveAppDocumentLoader.loadMarkdowns();
    }
}
