package com.ule.uhj.dto.opc.general;

import com.ule.uhj.Annotation.ParameterDescripor;
import com.ule.uhj.Annotation.Transfer;
import com.ule.uhj.dto.zgd.CheckedTransModel;

@Transfer(name = "地推授权信息模型")
public class AuthorizationDto extends CheckedTransModel {
    public static final String OPERATE_TYPE_AUTH = "授权";
    public static final String OPERATE_TYPE_UNAUTH = "取消授权";
    private static final long serialVersionUID = -4683402000249394356L;

    @ParameterDescripor(Index = "operate_type", Descript = "操作类型")
    private String operateType;

    @ParameterDescripor(Index = "postmeber", Descript = "目标人员")
    private PostmemberDto postmemberDto;

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public PostmemberDto getPostmemberDto() {
        return postmemberDto;
    }

    public void setPostmemberDto(PostmemberDto postmemberDto) {
        this.postmemberDto = postmemberDto;
    }
}
