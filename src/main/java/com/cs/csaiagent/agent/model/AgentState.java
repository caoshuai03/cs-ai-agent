package com.cs.csaiagent.agent.model;

/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/27
 */

/**
 * agent代理执行状态枚举
 */
public enum AgentState {
    IDLE,      // 空闲状态
    RUNNING,   // 运行中状态
    FINISHED,  // 已完成状态
    ERROR      // 错误状态
}
