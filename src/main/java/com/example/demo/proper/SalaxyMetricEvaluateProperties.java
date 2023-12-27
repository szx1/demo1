package com.example.demo.proper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zengxi.song
 * @date 2023/11/29
 */
@Setter
@Getter
@Component
@ConfigurationProperties("metric.evaluate")
public class SalaxyMetricEvaluateProperties {

    /**
     * 限制配置
     */
    private LimitPro limit;
    /**
     * 大数据相关表的配置
     */
    private TablePro table;

    @Setter
    @Getter
    public static class LimitPro {
        /**
         * 可选数据的最大时间范围  单位:天
         */
        private Integer dataMaxTimeRange = 180;
        /**
         * 每个任务最大可选指标数量
         */
        private Integer selectMetricNum = 1000;
        /**
         * 任务保存天数  单位:天
         */
        private Integer ttlDay = 180;
        /**
         * 最大任务个数
         */
        private Integer maxTaskNum = 300;
    }

    @Setter
    @Getter
    public static class TablePro {
        private String database;
    }

    /**
     * 返回dataMaxTimeRange的毫秒值
     *
     * @return
     */
    public Long getDataMaxTimeRangeMill() {
        return limit.getDataMaxTimeRange() * 24 * 60 * 6 * 1000L;
    }
}

