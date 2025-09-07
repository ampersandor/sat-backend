package com.ampersandor.sat_backend.dto;

import com.ampersandor.sat_backend.domain.Tool;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AlignRequest(
    @JsonProperty("align_tool") Tool tool,
    @JsonProperty("options") String options,
    @JsonProperty("input_path") String inputPath
){}
