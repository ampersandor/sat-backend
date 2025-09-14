package com.ampersandor.sat_backend.mapper;

import com.ampersandor.sat_backend.dto.StatisticDto;
import com.ampersandor.sat_backend.entity.Statistic;

public class StatisticMapper {
    public static StatisticDto toDto(Statistic statistic) {
        return new StatisticDto(
                statistic.getTotalSeq(),
                statistic.getGapSeqCount(),
                statistic.getGapCount(),
                statistic.getGapFrequency(),
                statistic.getSumOfGapLength(),
                statistic.getGapLength(),
                statistic.getSumOfBlueBases(),
                statistic.getNoBlueBases(),
                statistic.getNoMissBases(),
                statistic.getBlueBaseRatio(),
                statistic.getBlueBaseCount()
        );
    }
}
