package com.ule.uhj.dto.zgd;

import com.ule.uhj.Annotation.ParameterDescripor;
import com.ule.uhj.Annotation.Transfer;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transfer(name = "前置服务请求模型")
public class PreServiceRequest extends CheckedTransModel implements Serializable{

    //调用来源-OPC
    public static final String SOURCE_OPC = "opc";

    //调用来源-WEB
    public static final String SOURCE_WEB = "web";

    //调用服务-根据机构号与权限等级查询地推账户
    public static final String SERVICE_QUERY_POSTMEMBER = "queryPostmember";

    //调用服务-修改地推账户信息
    public static final String SERVICE_UPDATE_POSTMEMBER = "updatePostmember";

    //调用服务-合影照片面部识别
    public static final String SERVICE_FACE_CHECK = "faceCheck";

    //调用成功标志位-成功
    public static final Long FLAG_SUCCESS = 1l;

    //调用成功标志位-失败
    public static final Long FLAG_FAILED = 0l;

    //调用成功标志位-异常
    public static final Long FLAG_ERROR = 9l;

    //调用成功标志位-处理中
    public static final Long FLAG_WORK = 2l;
    private static final long serialVersionUID = 2269824012984059272L;

    @ParameterDescripor(Index = "flow_no", Descript = "调用流水号")
    private Long flowNo;

    @ParameterDescripor(Index = "source", Descript = "调用来源")
    private String source;

    @ParameterDescripor(Index = "call_service", Descript = "调用服务")
    private String callService;

    @ParameterDescripor(Index = "bus_desc", Descript = "业务说明")
    private String bussinessDescriptor;

    @ParameterDescripor(Index = "operator_name", Descript = "操作员姓名")
    private String operatorName;

    @ParameterDescripor(Index = "operator_type", Descript = "操作员类别")
    private String operatorType;

    @ParameterDescripor(Index = "operator_id", Descript = "操作员ID")
    private String operatorID;

    @ParameterDescripor(Index = "call_time", Descript = "调用时间")
    private String callTime;

    @ParameterDescripor(Index = "parameter", Descript = "调用参数")
    private List<TransferModel> parameter;

    @ParameterDescripor(Index = "success", Descript = "调用成功标识位")
    private Long successFlag;

    @ParameterDescripor(Index = "response_desc", Descript = "响应结果描述")
    private String responseDesc;

    @ParameterDescripor(Index = "response", Descript = "响应结果")
    private Object response;

    @ParameterDescripor(Index = "response_time", Descript = "响应时间")
    private String responseTime;

    private Object bussiness;

    @Override
    public String toString() {
        return "前置服务请求：{" +
                "流水号：" + flowNo +
                ", 调用来源：'" + source + '\'' +
                ", 业务描述：'" + bussinessDescriptor + '\'' +
                ", 操作员：'" + operatorName + '\'' +
                ", 操作员类型：'" + operatorType + '\'' +
                ", 操作员ID：'" + operatorID + '\'' +
                ", 调用时间：'" + callTime + '\'' +
                '}';
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCallService() {
        return callService;
    }

    public void setCallService(String callService) {
        this.callService = callService;
    }

    public String getBussinessDescriptor() {
        return bussinessDescriptor;
    }

    public void setBussinessDescriptor(String bussinessDescriptor) {
        this.bussinessDescriptor = bussinessDescriptor;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public List<TransferModel> getParameter() {
        return parameter;
    }

    public void setParameter(List<TransferModel> parameter) {
        this.parameter = parameter;
    }

    public Long getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Long successFlag) {
        this.successFlag = successFlag;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getResponseDesc() {
        String prefix = this.flowNo != null && this.flowNo > 0 ? "调用流水号："+this.flowNo+"\n" : "";
        return prefix + responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public Long getFlowNo() {
        return flowNo;
    }

    public void setFlowNo(Long flowNo) {
        this.flowNo = flowNo;
    }

    public Object getBussiness() {
        return bussiness;
    }

    public void setBussiness(Object bussiness) {
        this.bussiness = bussiness;
    }

    /***
     * 获取校验结果文本
     * @return
     */
    public String getCheckDescriptString(){
        StringBuffer stringBuffer = new StringBuffer(256);
        if(this.parameter==null || this.parameter.size()<=0) {
            return "未传递任何模型！";
        }
        for(TransferModel model : this.parameter){
            boolean allPass = true;
            stringBuffer.append("\t"+model.getModelType()+"\t"+"模型校验结果：\n");
            Map<String, String> parameterDescriptorTable = model.getParameterDescriptorTable();
            for(String index : parameterDescriptorTable.keySet()){
                String checkResult = this.getModelcheckResult(index);
                if(checkResult != null && checkResult.trim().length() > 0){
                    stringBuffer.append("\t"+parameterDescriptorTable.get(index)+"："+ checkResult + "\n");
                    allPass = false;
                }
            }
            if(allPass){
                stringBuffer.append("\t"+"该模型所需的全部参数均校验通过。\n");
            }
        }
        return stringBuffer.toString();
    }
}
