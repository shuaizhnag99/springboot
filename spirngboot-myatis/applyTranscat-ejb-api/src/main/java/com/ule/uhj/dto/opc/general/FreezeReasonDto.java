package com.ule.uhj.dto.opc.general;

import com.ule.uhj.Annotation.ParameterDescripor;
import com.ule.uhj.Annotation.Transfer;
import com.ule.uhj.dto.zgd.CheckedTransModel;

@Transfer(name = "账户冻结模型")
public class FreezeReasonDto extends CheckedTransModel{
    public static final String OPERATE_TYPE_FREEZE = "冻结";
    public static final String OPERATE_TYPE_UNFREEZE = "解冻";
    private static final long serialVersionUID = 8304559427452661243L;
    @ParameterDescripor(Index = "freeze_reason", Descript = "冻结原因")
    private String reason;

    @ParameterDescripor(Index = "operate_type", Descript = "操作类型")
    private String operate_type;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOperate_type() {
        return operate_type;
    }

    public void setOperate_type(String operate_type) {
        this.operate_type = operate_type;
    }
}
