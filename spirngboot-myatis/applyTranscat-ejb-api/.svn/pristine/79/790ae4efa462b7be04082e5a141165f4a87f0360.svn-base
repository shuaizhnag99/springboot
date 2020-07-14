package com.ule.uhj.dto.opc.general;

import com.ule.uhj.Annotation.ParameterDescripor;
import com.ule.uhj.Annotation.Transfer;
import com.ule.uhj.dto.zgd.CheckedTransModel;

@Transfer(name = "合影照片模型")
public class PhotoDto extends CheckedTransModel{
    private static final long serialVersionUID = -2291036279220195283L;
    @ParameterDescripor(Index = "photo_name", Descript = "照片名称")
    private String name;

    @ParameterDescripor(Index = "photo_type", Descript = "照片类型")
    private String type;

    @ParameterDescripor(Index = "file_url", Descript = "文件地址")
    private String fileURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }
}
