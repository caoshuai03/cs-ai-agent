package com.cs.csaiagent.agent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;


/**
 * @author caoshuai
 * @version 1.1
 * @date 2025/6/27
 */

/**
 * ReAct模式代理(思考-行动循环)
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public abstract class ReActAgent extends BaseAgent {

    /**
     * 思考决策阶段
     * @return 是否需要执行行动
     */
    public abstract boolean think();

    /**
     * 执行行动阶段
     * @return 行动执行结果
     */
    public abstract String act();

    /**
     * 单步执行思考和行动
     * 模版模式
     */
    @Override
    public String step() {
        try {
            // 先思考后行动
            boolean shouldAct = think();
            if (!shouldAct) {
                return "思考完成 - 无需行动";
            }
            return act();
        } catch (Exception e) {
            log.error("步骤执行失败", e);
            return "步骤执行失败: " + e.getMessage();
        }
    }
}

