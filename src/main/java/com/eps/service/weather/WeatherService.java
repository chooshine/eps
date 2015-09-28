package com.eps.service.weather;

import java.rmi.RemoteException;

import org.springframework.stereotype.Service;

import com.eps.service.weather.client.WeatherWebServiceSoap;
import com.eps.service.weather.client.WeatherWebServiceSoapProxy;
import com.eps.utils.LStrMap;
import com.eps.utils.StringHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class WeatherService {
	
	private WeatherWebServiceSoap service;// = new WeatherWebServiceSoapProxy();
	
	private void init(){
		synchronized (WeatherWebServiceSoap.class) {
			if(service==null)service = new WeatherWebServiceSoapProxy();
		}
	}
	public String[] getSupportCity(String provinceName){
		init();
		try {
			return service.getSupportCity(provinceName);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String[] getSupportProvince(){
		init();
		try {
			return service.getSupportProvince();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public LStrMap<String> getWeatherByCityName(String cityName){
		init();
		String[] result;
		try {
			result = service.getWeatherbyCityName(cityName);
			if(result !=null && result.length == 23){
				LStrMap<String> weather = LStrMap.newInstance();
				String s = result[10];
				weather.put("province", result[0]);
				weather.put("city", result[1]);
				weather.put("time", result[4]);
				weather.put("minandmax", result[5]);
				weather.put("todayweather", result[6].split(" ")[1]);
				weather.put("windpower", result[7]);
				weather.put("icon", result[8]);
				weather.put("icon2",result[9]);
				weather.put("currenWeather", s.substring(s.indexOf("：")+1, s.indexOf("；")));
				return weather;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public static void main(String[] args) {
		String string = StringHelper.replaceBlank("乌鲁木齐 (51463)");
		String s1 = string.substring(0, (string.length()-1)).replaceAll("\\(", "\\#");
		System.out.println(s1);
	}
}
