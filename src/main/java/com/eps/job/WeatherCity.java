package com.eps.job;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.eps.service.weather.client.WeatherWebServiceSoap;
import com.eps.service.weather.client.WeatherWebServiceSoapProxy;
import com.eps.utils.UStrMap;

public class WeatherCity {
	private  static UStrMap<List<String>> citys = UStrMap.newInstance();
	public final static String[] EXCEPT = {"亚洲","欧洲","非洲","北美洲","南美洲","大洋洲"};
//	private WeatherWebServiceSoap service = new WeatherWebServiceSoapProxy();
	
//	static{
//		if(citys.isEmpty()){
//			WeatherCity wc = new WeatherCity();
//			wc.getWeatherCity();
//		}
//	}
	public static UStrMap<List<String>> getCitys(){
		synchronized (citys) {
			if(citys.isEmpty()){
				new WeatherCity().getWeatherCity();
			}
		}
		return citys;
	}
	public void getWeatherCity(){
		WeatherWebServiceSoap service = new WeatherWebServiceSoapProxy();
		try {
			String[] provinces = service.getSupportProvince();
			for (String province : provinces) {
				if(ArrayUtils.contains(EXCEPT, province))continue;
				String[] city = service.getSupportCity(province);
				List<String> list = new ArrayList<String>();
				for (String string : city) {
					string.substring(0, string.length()-1).replaceAll("\\(", "\\#");
					String[] strings = string.split("\\(");
					list.add(strings[0]);
				}
				citys.put(province, list);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
