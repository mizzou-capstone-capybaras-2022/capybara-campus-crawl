package com.capybara.CapybaraCampusCrawl.databaseseeder.CapybaraDatabaseSeeder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReaderHeaderAware;

public class DatabaseSeederHelper {
	public static List<Map<String, String>> readFileHashMap(CSVReaderHeaderAware reader){
		
		List<Map<String, String>> hashMapList = new ArrayList<Map<String, String>>();
		
		Map<String, String> currentHashMap = null;
		
		try {
			currentHashMap = reader.readMap();
		
			while (currentHashMap != null)
			{
				hashMapList.add(currentHashMap);
				currentHashMap = reader.readMap();
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hashMapList;
	}
}
