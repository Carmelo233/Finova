package com.finova.finovabackendmodel.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzeChart {
    private Integer aid;
    private String analyzeName;
    private Integer state;
    private String pathPrefix;
    private Integer algNum;
    private Integer stockNum;

    public AnalyzeChart(String analyzeName, Integer state, String pathPrefix, Integer algNum, Integer stockNum) {
        this.analyzeName = analyzeName;
        this.state = state;
        this.pathPrefix = pathPrefix;
        this.algNum = algNum;
        this.stockNum = stockNum;
    }
}
