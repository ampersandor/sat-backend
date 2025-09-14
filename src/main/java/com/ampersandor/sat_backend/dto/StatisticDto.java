package com.ampersandor.sat_backend.dto;

public record StatisticDto (
        int totalSeq,
        int gapSeqCount,
        int gapCount,
        int gapFrequency,
        int sumOfGapLength,
        int gapLength,
        int sumOfBlueBases,
        int noBlueBases,
        int noMissBases,
        float blueBaseRatio,
        String blueBaseCount
) {
}
