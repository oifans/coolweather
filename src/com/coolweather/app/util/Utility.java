package com.coolweather.app.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.Country;
import com.coolweather.app.model.Province;

public class Utility {
	
	/**
	 * 将接口返回的Json省数据，处理并存入SQLite
	 * @param coolWeatherDB 
	 * @param response 接口返回的数据
	 * @return
	 */
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB ,String response){
		try {
			Log.d("MT", "handleProvincesResponse:"+ response );
			JSONObject jsonObject = new JSONObject(response);
			JSONObject list = jsonObject.getJSONObject("list");
			for(int i = 1;i <= list.length() ; i++){ 
				JSONObject jsonProvince = list.getJSONObject("wjr"+i);
				Province province = new Province();
				province.setProvinceCode(jsonProvince.getString("daima"));
				province.setProvinceName(jsonProvince.getString("diming"));
				coolWeatherDB.saveProvince(province);
			}
			return true;
		} catch (JSONException e) {
		Log.e("MT","Utility:Province JSON ERROE");
		e.printStackTrace();
		}
		return false;
	}
	
	public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB ,String response, String provinceCode){
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject list = jsonObject.getJSONObject("list");
			for(int i = 1;i <= list.length() ; i++){ 
				JSONObject jsonCity = list.getJSONObject("wjr"+i);
				City city = new City();
				city.setCityCode(jsonCity.getString("daima"));
				city.setCityName(jsonCity.getString("diming"));
				city.setProvinceCode(provinceCode);
				coolWeatherDB.saveCity(city);
			}
			return true;
		} catch (JSONException e) {
		Log.e("MT","Utility:City JSON ERROE");
		e.printStackTrace();
		}
		return false;
	}
	
	public synchronized static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB ,String response, String cityCode){
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONObject list = jsonObject.getJSONObject("list");
			for(int i = 1;i <= list.length() ; i++){ 
				JSONObject jsonCountry = list.getJSONObject("wjr"+i);
				Country country = new Country();
				country.setCountryCode(jsonCountry.getString("daima"));
				country.setCountryName(jsonCountry.getString("diming"));
				country.setCityCode(cityCode);
				coolWeatherDB.saveCountry(country);
			}
			return true;
		} catch (JSONException e) {
		Log.e("MT","Utility:Country JSON ERROE");
		e.printStackTrace();
		}
		return false;
	}
}
