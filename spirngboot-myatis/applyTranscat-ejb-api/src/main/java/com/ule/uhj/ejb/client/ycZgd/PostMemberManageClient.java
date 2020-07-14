package com.ule.uhj.ejb.client.ycZgd;

import com.ule.uhj.dto.zgd.PostMemberManageDto;
import com.ule.wildfly.annotation.BeanName;

import java.util.List;

import javax.ejb.Remote;

/**
 * Created by zhengxin on 2018/9/18.
 */
@Remote
@BeanName("PostMemberManageBean")
public interface PostMemberManageClient {

    /***
     * 查询指定用户是否与某地推存在绑定关系
     * 或查询某用户是否存在某种绑定关系
     * @param dto
     * @return
     */
    public Boolean CheckUserRel(PostMemberManageDto dto);

    /***
     * 绑定地推关系
     * @param dto
     */
    public PostMemberManageDto BindPostMember(PostMemberManageDto dto);

    /***
     * 查询指定地推列表
     * @param dto
     */
    public List  QueryPostMember(PostMemberManageDto dto);

    /***
     * 查询地推名下掌柜
     * @param dto
     * @return
     */
    public List QueryRelUser(PostMemberManageDto dto);

    /***
     * 通过vps更新地推信息
     * @param dto
     * @return
     */
    public boolean UpdatePostmemberInfoByVPS(PostMemberManageDto dto);

    /***
     * 初始化老表数据
     */
    public void init();
}
