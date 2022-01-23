package top.kaluna.wiki.mapper;

import top.kaluna.wiki.resp.StatisticResp;

import java.util.List;

/**
 * @author Yuery
 * @date 2022/1/22/0022 - 23:21
 */
public interface EbookSnapshotMapperCust {
    void genSnapshot();

    List<StatisticResp> getStatistic();

    List<StatisticResp> get30Statistic();
}
