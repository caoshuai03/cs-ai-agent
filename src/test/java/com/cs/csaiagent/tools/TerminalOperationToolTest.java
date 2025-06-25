package com.cs.csaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/25
 */
class TerminalOperationToolTest {

    @Test
    void executeTerminalCommand() {
        TerminalOperationTool terminalOperationTool = new TerminalOperationTool();
        String command = "pwd";//打开计算器
        String result = terminalOperationTool.executeTerminalCommand(command);
        System.out.println("result = " + result);
        command = "calc";//打开计算器
        result = terminalOperationTool.executeTerminalCommand(command);
        Assertions.assertNotNull(result);
    }
}
