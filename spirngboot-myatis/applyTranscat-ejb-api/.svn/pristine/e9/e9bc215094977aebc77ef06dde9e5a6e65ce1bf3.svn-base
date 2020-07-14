package com.ule.uhj.dto.opc.general;

import com.ule.uhj.Annotation.ParameterDescripor;
import com.ule.uhj.Annotation.Transfer;
import com.ule.uhj.dto.zgd.CheckedTransModel;

@Transfer(name = "地推移交信息模型")
public class TransferPostmemberDto extends CheckedTransModel{

    private static final long serialVersionUID = -9123636787729498000L;
    @ParameterDescripor(Index = "transer", Descript = "移出人员")
    private PostmemberDto transer;
    @ParameterDescripor(Index = "reciver", Descript = "接收人员")
    private PostmemberDto reciver;

    public PostmemberDto getTranser() {
        return transer;
    }

    public void setTranser(PostmemberDto transer) {
        this.transer = transer;
    }

    public PostmemberDto getReciver() {
        return reciver;
    }

    public void setReciver(PostmemberDto reciver) {
        this.reciver = reciver;
    }
}
