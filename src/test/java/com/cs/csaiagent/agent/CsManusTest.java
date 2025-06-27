package com.cs.csaiagent.agent;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/27
 */
@SpringBootTest
public class CsManusTest {
    @Resource
    private CsManus csManus;

    @Test
    public void run() {
        String userPrompt = """
                我的女朋友居住在四川成都，请帮我找到 5 公里内合适的约会地点，
                并搜索合结合一些网络图片，制定一份详细的约会计划，
                并以 pdf 格式输出
                """;
        String answer = csManus.run(userPrompt);
        System.out.println(answer);
        Assertions.assertNotNull(answer);
    }
}
