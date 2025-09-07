package com.ampersandor.sat_backend.dto;

import com.ampersandor.sat_backend.domain.Tool;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnalyzeRequest {
    @JsonProperty("align_tool") Tool tool;
    @JsonProperty("options") String options;
}
