package com.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.coolweather.app.model.City;
import com.coolweather.app.model.Country;
import com.coolweather.app.model.Province;

public class CoolWeatherDB {
	/*
	 * ���ݿ���
	 */
	public static final String DB_NAME = "cool_weather";
	/*
	 * ���ݿ�汾
	 */
	public static final int VERSION = 1;
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	/**
	 * �����췽��˽�л�
	 * ����ģʽ����֤ȫ��ֻ��һ��DBʵ��
	 */
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context , DB_NAME ,  null ,VERSION);
		db = dbHelper.getReadableDatabase();
	}
	
	/**
	 * ��ȡCoolWeatherDB��ʵ��
	 */
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB == null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/**
	 * ��Provinceʵ���洢�����ݿ⡣
	 */
	public void saveProvince(Province province){
		if(province != null){
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("province", null , values);
			
		}
	}
	
	/**
	 * �����ݿ��ȡȫ�����е�ʡ����Ϣ��
	 */
	public List<Province> loadProvinces(){
		Log.d("MT", "CoolWeatherDB: loadProvinces");
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.rawQuery("select * from province",null);
		if(cursor.moveToNext()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		return list;
	}
	
	/**
	 * ��Cityʵ���洢�����ݿ⡣
	 */
	public void saveCity(City city){
		if(city != null){
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_code", city.getProvinceCode());
			db.insert("city", null, values);
		}
	}
	
	/**
	 * �����ݿ��ȡĳʡ�����еĳ�����Ϣ��
	 */
	public List<City> loadCities(String provinceCode){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.rawQuery("select * from city where province_code = ?",new String[]{provinceCode});
		if(cursor.moveToNext()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceCode(provinceCode);
				list.add(city);
			}while(cursor.moveToNext());
		}
		return list;
	}
	
	/**
	 *��Countryʵ���洢�����ݿ⡣
	 */
	public void saveCountry(Country country){
		if(country != null){
			ContentValues values = new ContentValues();
			values.put("country_name", country.getCountryName());
			values.put("country_code", country.getCountryCode());
			values.put("city_code", country.getCityCode());
			db.insert("country", null, values);
		}
	}
	/**
	 * �����ݿ��ȡĳ���������е�����Ϣ��
	 */
	public List<Country> loadCounties(String cityCode){
		List<Country> list = new ArrayList<Country>();
		Cursor cursor = db.rawQuery("select * from country where city_code = ?", new String[]{cityCode});
		if(cursor.moveToNext()){
			do{
				Country country = new Country();
				country.setId(cursor.getInt(cursor.getColumnIndex("id")));
				country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
				country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
				country.setCityCode(cityCode);
				list.add(country);
			}while(cursor.moveToNext()); 
		}
 		return list;
	}
}
