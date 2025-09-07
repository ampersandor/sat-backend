package com.ampersandor.sat_backend.domain;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Tool {
    MAFFT("mafft"),
    UCLUST("uclust"),
    VSEARCH("vsearch");

    private String value;
    private Tool (String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }
}
