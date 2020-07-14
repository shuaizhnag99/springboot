package com.ule.uhj.util;

import java.io.Serializable;

import com.ule.uhj.dto.zgd.ApplyDetailDto;

public class ApplyDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 167190721113565507L;
	ApplyDetailDto applyDetailDto;

	public ApplyDetailDto getApplyDetailDto() {
		return applyDetailDto;
	}

	public void setApplyDetailDto(ApplyDetailDto applyDetailDto) {
		this.applyDetailDto = applyDetailDto;
	}

}
