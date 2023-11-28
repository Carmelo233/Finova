package com.finova.finovabackendmodel.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UploadFile {
    private Integer fid;
    private String fileName;
    private Integer type;
    private Integer fileNum;
    private String filePath;
    private Integer uid;

    public UploadFile(String fileName, Integer type, String filePath, Integer uid) {
        this.fileName = fileName;
        this.type = type;
        this.filePath = filePath;
        this.uid = uid;
    }

    public UploadFile(String fileName, Integer type, Integer fileNum, String filePath, Integer uid) {
        this.fileName = fileName;
        this.type = type;
        this.fileNum = fileNum;
        this.filePath = filePath;
        this.uid = uid;
    }
}
