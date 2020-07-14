package com.example.cn.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

 


public class Person {
	@NotBlank(message="姓名不能为空")
	private String name;
	
	@NotBlank(message="密码不能为空")
	@Length(min=6,max=10,message="密码长度6-10位")
	private String password;
	
	@NotBlank(message="性别不能为空")
	private String sex;
	@NotNull(message="年龄不能为空")
	@Min(value = 10, message = "年龄必须大于10")
	@Max(value = 150, message = "年龄必须小于150")
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
