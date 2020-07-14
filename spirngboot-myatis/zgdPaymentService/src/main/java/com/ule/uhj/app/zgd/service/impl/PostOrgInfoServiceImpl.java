package com.ule.uhj.app.zgd.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ule.uhj.app.zgd.dao.CreditPostmemberMapper;
import com.ule.uhj.app.zgd.dao.PostOrgInfoMapper;
import com.ule.uhj.app.zgd.model.CreditPostmember;
import com.ule.uhj.app.zgd.model.CreditPostmemberExample;
import com.ule.uhj.app.zgd.model.PostOrgInfo;
import com.ule.uhj.app.zgd.service.PostOrgInfoService;
import com.ule.uhj.ejb.client.WildflyBeanFactory;
import com.ule.uhj.ejb.client.app.ZgdAppClient;

@Service
public class PostOrgInfoServiceImpl implements PostOrgInfoService {

	@Autowired
	private PostOrgInfoMapper postOrgInfoMapper;
	
	@Autowired
	private CreditPostmemberMapper creditPostmemberMapper;
	
	@Override
	public List<PostOrgInfo> buildOrgInfoTree() throws Exception {
		// 查询所有数据
		List<PostOrgInfo> orgList = postOrgInfoMapper.selectAll();
		List<PostOrgInfo> roots = getRoots(orgList);
		if(!CollectionUtils.isEmpty(roots)) {
			for(PostOrgInfo root : roots) {
				buildTree(root, orgList);
			}
		}
		return roots;
	}

	/**
	 * 递归组装树
	 * @param info
	 * @param orgInfoList
	 * @return
	 */
	private void buildTree(PostOrgInfo info, List<PostOrgInfo> orgInfoList) {
		
		List<PostOrgInfo> children = getChildren(info, orgInfoList);
		info.setChildren(children);
		if(!CollectionUtils.isEmpty(children)) {
			for(PostOrgInfo childInfo : children) {
				buildTree(childInfo,orgInfoList);
			}
		}
	}
	/**
	 * 获取子节点列表
	 * @param info
	 * @param orgInfoList 总机构数
	 */
	private List<PostOrgInfo> getChildren(PostOrgInfo info, List<PostOrgInfo> orgInfoList) {
		List<PostOrgInfo> list = new ArrayList<PostOrgInfo>();
		String orgCode = info.getCode();
		if(!CollectionUtils.isEmpty(orgInfoList)) {
			for(PostOrgInfo orgInfo : orgInfoList) {
				if(null != orgCode) {
					if(orgCode.equals(orgInfo.getParentCode())) {
						list.add(orgInfo);
					}
				}
			}
		}
		return list;
	}

	/**
	 * 获取所有根节点
	 * @param orgInfoList
	 * @return
	 */
	private List<PostOrgInfo> getRoots(List<PostOrgInfo> orgInfoList){
		List<PostOrgInfo> list = new ArrayList<PostOrgInfo>();
		if(!CollectionUtils.isEmpty(orgInfoList)) {
			for(PostOrgInfo orgInfo : orgInfoList) {
				if("0".equals(orgInfo.getParentCode())) {
					list.add(orgInfo);
				}
			}
		}
		return list;
	}

	@Override
	public void saveOrgInfo(CreditPostmember member, String orgCode) throws Exception {
		if(StringUtils.isEmpty(member.getUserOnlyId())) {
			throw new Exception("用户编号为空");
		}
		// 首先查询是否已经保存过该用户的数据，如果有则更新
		CreditPostmemberExample example = new CreditPostmemberExample();
		example.createCriteria().andUserOnlyIdEqualTo(member.getUserOnlyId()).andMemberTypeEqualTo("1");
		List<CreditPostmember> list = creditPostmemberMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)) {
			// 更新数据
			for(CreditPostmember creditPostmember : list){
				creditPostmember.setOrgProvince(member.getOrgProvince());
				creditPostmember.setOrgCity(member.getOrgCity());
				creditPostmember.setOrgArea(member.getOrgArea());
				creditPostmember.setOrgTown(member.getOrgTown());
				creditPostmemberMapper.updateByPrimaryKeySelective(creditPostmember);
			}
		}
		else {
			creditPostmemberMapper.insertSelective(member);
		}
		
		// 处理白名单信息
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("userOnlyId", member.getUserOnlyId());
		param.put("orgCode", orgCode);
		param.put("provinceName", member.getOrgProvince());
		param.put("cityName", member.getOrgCity());
		param.put("areaName", member.getOrgArea());
		param.put("townName", member.getOrgTown());
		
		WildflyBeanFactory.getZgdAppClient().saveZgdWhiteInfo(param);
	}
}
