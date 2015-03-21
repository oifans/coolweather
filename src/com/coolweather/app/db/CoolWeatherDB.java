package com.coolweather.app.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coolweather.app.model.City;
import com.coolweather.app.model.Country;
import com.coolweather.app.model.Province;

public class CoolWeatherDB {
	/*
	 * 数据库名
	 */
	public static final String DB_NAME = "cool_weather";
	/*
	 * 数据库版本
	 */
	public static final int VERSION = 1;
	private static CoolWeatherDB coolWeatherDB;
	
	private SQLiteDatabase db;
	
	/**
	 * 将构造方法私有化
	 * 单例模式，保证全局只有一个DB实例
	 */
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context , DB_NAME ,  null ,VERSION);
		db = dbHelper.getReadableDatabase();
	}
	
	/**
	 * 获取CoolWeatherDB的实例
	 */
	public synchronized static CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB != null){
			coolWeatherDB = new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	
	/**
	 * 将Province实例存储到数据库。
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
	 * 从数据库读取全国所有的省份信息。
	 */
	public List<Province> loadProvinces(){
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
	 * 将City实例存储到数据库。
	 */
	public void saveCity(City city){
		if(city != null){
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("city", null, values);
		}
	}
	
	/**
	 * 从数据库读取某省下所有的城市信息。
	 */
	public List<City> loadCities(int provinceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.rawQuery("select * from city where province_id = ?",new String[]{String.valueOf(provinceId)});
		if(cursor.moveToNext()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while(cursor.moveToNext());
		}
		return list;
	}
	
	/**
	 *将Country实例存储到数据库。
	 */
	public void saveCountry(Country country){
		if(country != null){
			ContentValues values = new ContentValues();
			values.put("country_name", country.getCountryName());
			values.put("country_code", country.getCountryCode());
			values.put("city_id", country.getCityId());
			db.insert("country", null, values);
		}
	}
	/**
	 * 将数据库读取某城市下所有的县信息。
	 */
	public List<Country> loadCounties(int cityId){
		List<Country> list = new ArrayList<Country>();
		Cursor cursor = db.rawQuery("select * from country where city_id = ?", new String[]{String.valueOf(cityId)});
		if(cursor.moveToNext()){
			do{
				Country country = new Country();
				country.setId(cursor.getInt(cursor.getColumnIndex("id")));
				country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
				country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
				country.setCityId(cityId);
				list.add(country);
			}while(cursor.moveToNext()); 
		}
 		return list;
	}
}
