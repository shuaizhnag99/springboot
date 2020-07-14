package com.ule.uhj.app.zgd.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ule.uhj.app.zgd.dao.CountryAddressMapper;
import com.ule.uhj.app.zgd.model.CountryAddress;
import com.ule.uhj.app.zgd.model.CountryAddressExample;
import com.ule.uhj.app.zgd.service.TestService;
import com.ule.uhj.util.Convert;
import com.ule.uhj.util.UhjWebJsonUtil;

@Controller
@RequestMapping("/testCon")
public class TestController {
	
	@Autowired
	private TestService testService;
	@Autowired
	private CountryAddressMapper countryAddressMapper;
	
	@RequestMapping("/testImg")
	public String testImg(HttpServletRequest request){
//		String rcImageBase64  = "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0a\nHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIy\nMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAaAEYDASIA\nAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQA\nAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3\nODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWm\np6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEA\nAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSEx\nBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElK\nU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3\nuLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD2u+1X\nTtM8v7ff2tp5mdn2iZY92MZxk84yPzp0dzHf2Bn027t5RIrCGdf3se7kZ+UjcARyAR0xkVxPj2S2\nh8VeFXu9Nl1KAfa91pFAJmk+RcYQ8HBwfbGaueDNLvrCXXb6PTP7Osr2RJbHTppsbCFOWYDIj3Er\nkAErjGMKM5c2tiebU6C2/tSzMs+ralpz2iRlmMVo0GzHO4s0rDAGew+vFWrLULLUoTNYXlvdRK2w\nvBKrqG4OMg9eR+dcz4+03UdV8LQCG0+0mG4iuLyxhnYfaI1BLxqwALc4I4B4yBnArlLF473xtoSx\neGbnw5FfQ3FvcqhNs04QeYAuzawAIQ7xgtkryF56IUlKN7lOV2ejpJpnhfRLK2ur+G1tLeNLaKW7\nmVN21cAEnALYXPHoahTxDY6hJHHompadqEyupmghuUdvKLBWYYbjbuDdDnG3qwI5vx7p91qdlpWm\nadpbahNp+oQXZtLmZQLm3RWDHfITuGWCtnLZIJGGBNnwle6SNbn0xvCkPh7Xo7bzZIo4I8SQlgMp\nKgwy5256c8c7TiGrq5Vrq52MsiwwvKwcqiliEQs2B6AZJPsOa5DR/HMOqSLFZ3Wl6o24qFtbg29x\nKQM/JbygcAdT5mMAnr8tL8R5pW0XTtLSR4odX1S30+4kjYq6xOSW2nsSFxyCMEjHNT3Wg+C9ATTB\nNpNhas13Bb2cqW2ZDODmMb1G7OV5LHnnJ55kg6qioY7WGG5nuI02yT7fNIJwxAwCR0zjAz1ICg8K\nMFAzHvPDkl34u03W/t7LFZLJi1KbgWdNhIbPyjAXgDHBPUk1cl1dreZ1udM1COFWKrMkQmDnthYi\nzgEc5KgeuDgVpUVNrbCt2MHXr15dNMFuNVt5Zo0mgubW1Z2Qhgx3LjPAALIwBcEqu5uKybLwtqkl\n1DrqeLTdai8Sxx3Qs4jDJbZ3hNinkFudwYHHAx1rtKKtSaVhnP32m+IdRsTANXi067gkWSC+s4yw\nl4YMskD5AXDcDe2SA3GMVV0Lwu+h6lfeINc1x9T1KSHymu5UEEcMC4YqEB2gZG4n+XzE9VRSu7WH\nzMw9X0XQvGmimG5WC8tpFYQ3MLKzRnOC0bjOCCPocYIIyKwIfAmuT3Wlf214wm1Ky025juo4DYpG\n7PGDsJkyWPXnOSee/I7D+y9P/tD+0PsFr9t/5+fJXzOm372M9OPpVugRRtpdSF/NBd20Jt/maG5h\nfqM8K6HlWwQAQWDbWJ2ZC0VeopAf/9k=";
//		request.setAttribute("rcImageBase64", rcImageBase64);
		return "yc/yc_test";
	}	
	
//	private static void getCode(String code){
//		if(code!=null&&!"".equals(code)){
//			System.out.println(Integer.parseInt(code));
//		}
//	}
	
	public static void main(String[] args) {
//		getCode("28");
	}
	
	public String save(HttpServletRequest request) throws IOException{
		//处理返回数据
		InputStream  io = request.getInputStream();
		StringBuilder sb = new StringBuilder();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(io, Charset.forName("UTF-8")));
		  int cp;
		  while ((cp = rd.read()) != -1) {
		     sb.append((char) cp);
		  }
	    } finally {
	      io.close();
	    }
	    String aa = URLDecoder.decode(sb.toString());
	    System.out.println(aa);
	    String[] bb = aa.split("&");
	    Map<String,String> param = new HashMap<String, String>();//参数
	    for(String temp:bb){
	    	System.out.println("temp:"+temp);
	    	param.put(temp.substring(0,temp.indexOf("=")),temp.substring(temp.indexOf("=")+1,temp.length()));
	    }
	    return param.toString();
	}
	
	private static String juxinliSupportOperator="联通,福建移动,浙江移动,吉林移动,上海移动,陕西电信,山西电信,江苏电信,河南电信,黑龙江电信,天津电信,北京电信,安徽电信,河北电信,内蒙古电信,贵州电信";

	
//	public static void main(String[] args) {
//		boolean support = false;
//		String operatorName = "浙江移动";
//		String webSite = "chinamobilezj";
//		if(!Check.isBlank(operatorName)){
//			if("chinaunicom".equals(webSite)){
//				support = true;
//			}else{
//				String[] names = juxinliSupportOperator.split(",");
//				for(int i = 0 ; i < names.length ; i ++){
//					if(!Check.isBlank(operatorName)&&operatorName.equals(names[i])){
//						support = true;
//					}
//				}
//			}
//		}
//		System.out.println(support);
//	}
	
	private String getCode(String code){
		if(code!=null&&!"".equals(code)){
			return Convert.toStr(Integer.parseInt(code));
		}
		return code;
	}
	
	/**
	 * 查询现金借款记录详情
	 * @return
	 */
	@RequestMapping("/queryReport")
	@ResponseBody
	public JSONPObject queryReport(HttpServletRequest request,@RequestParam String jsoncallback){
		String country = "宁夏固原市原州区街路巷名称待定金城阳光佳苑7号楼4单元401室M-27号";
		Map<String, Object> countryMap = new HashMap<String, Object>();
		String remainingString = country;
		String[] provinceCity = {"北京市","上海市","天津市","重庆市","香港市","澳门市"};
		String[] province = {"北京","上海","天津","重庆","香港","澳门"};
		//取出县的数据字典
		List<CountryAddress> countyAddress = countryAddressMapper.selectCountyName();
		String parentCode = null;
		if (null != country && !"".equals(country)) {
			//根据县的名称去匹配
			for(int i = 0 ; i < countyAddress.size() ; i++){
				int areaIndex = country.indexOf(countyAddress.get(i).getName());
				if (areaIndex !=-1) {
					countryMap.put("area", getCode(countyAddress.get(i).getCode())+"-"+countyAddress.get(i).getName());
					remainingString = country.substring(0,areaIndex);
					parentCode = countyAddress.get(i).getParentCode();
					//根据市的名称去匹配
					List<CountryAddress> address = new ArrayList<CountryAddress>();
					CountryAddressExample countryExample = new CountryAddressExample();
					countryExample.createCriteria().andCodeEqualTo(parentCode);
					address = countryAddressMapper.selectByExample(countryExample);
					if(address!=null&&address.size()>0){
						int cityIndex = remainingString.indexOf(address.get(0).getName());
						parentCode = address.get(0).getParentCode();
						if(cityIndex!=-1){
							countryMap.put("city", getCode(address.get(0).getCode())+"-"+address.get(0).getName());
							boolean isProvinceCity = false;
							for(int t = 0 ; t < provinceCity.length ; t ++){
								if(address.get(0).getName().equals(provinceCity[t])){
									isProvinceCity = true;
								}
							}
							if(!isProvinceCity){
								remainingString = remainingString.substring(0,cityIndex);
							}
							//根据省的名称去匹配
							List<CountryAddress> provinceAddress = new ArrayList<CountryAddress>();
							CountryAddressExample provinceExample = new CountryAddressExample();
							provinceExample.createCriteria().andCodeEqualTo(parentCode);
							provinceAddress = countryAddressMapper.selectByExample(provinceExample);
							if(provinceAddress!=null&&provinceAddress.size()>0){
								int provincesIndex = remainingString.indexOf(provinceAddress.get(0).getName());
								if (provincesIndex !=-1) {
									for(int t = 0 ; t < provinceCity.length ; t ++){
										if(provinceAddress.get(0).getName().equals(provinceCity[t])){
											countryMap.put("province", getCode(provinceAddress.get(0).getCode())+"-"+provinceAddress.get(0).getName());
											return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(countryMap));
										}
									}
									countryMap.put("province", getCode(provinceAddress.get(0).getCode())+"-"+provinceAddress.get(0).getName());
									return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(countryMap));
								}
							}
						}else{
							//根据省的名称去匹配
							List<CountryAddress> cityAddress = new ArrayList<CountryAddress>();
							CountryAddressExample cityExample = new CountryAddressExample();
							cityExample.createCriteria().andCodeEqualTo(parentCode);
							cityAddress = countryAddressMapper.selectByExample(cityExample);
							if(cityAddress!=null&&cityAddress.size()>0){
								List<CountryAddress> provinceAddress = new ArrayList<CountryAddress>();
								CountryAddressExample provinceExample = new CountryAddressExample();
								provinceExample.createCriteria().andCodeEqualTo(cityAddress.get(0).getCode());
								provinceAddress = countryAddressMapper.selectByExample(provinceExample);
								int provincesIndex = remainingString.indexOf(provinceAddress.get(0).getName());
								if (provincesIndex !=-1) {
									countryMap.put("city", getCode(address.get(0).getCode())+"-"+address.get(0).getName());
									for(int t = 0 ; t < provinceCity.length ; t ++){
										if(provinceAddress.get(0).getName().equals(province[t])){
											countryMap.put("province", getCode(provinceAddress.get(0).getCode())+"-"+provinceAddress.get(0).getName());
											return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(countryMap));
										}
									}
									countryMap.put("province", getCode(provinceAddress.get(0).getCode())+"-"+provinceAddress.get(0).getName());
									return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(countryMap));
								}
							}
						}
					}
				}
			}
		}
		return new JSONPObject(jsoncallback, UhjWebJsonUtil.parseObjToJson(countryMap));
	}
}
