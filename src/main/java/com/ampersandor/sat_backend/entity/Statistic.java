package com.ampersandor.sat_backend.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
    @JsonProperty("total_seq") private int totalSeq;
    @JsonProperty("gap_seq_count") private int gapSeqCount;
    @JsonProperty("gap_count") private int gapCount;
    @JsonProperty("gap_frequency") private int gapFrequency;
    @JsonProperty("sum_of_gap_length") private int sumOfGapLength;
    @JsonProperty("gap_length") private int gapLength;
    @JsonProperty("sum_of_blue_bases") private int sumOfBlueBases;
    @JsonProperty("no_blue_bases") private int noBlueBases;
    @JsonProperty("no_miss_bases") private int noMissBases;
    @JsonProperty("blue_base_ratio") private float blueBaseRatio;
    @JsonProperty("blue_base_count") private String blueBaseCount;
}