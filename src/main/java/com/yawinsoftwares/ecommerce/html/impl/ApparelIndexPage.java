package com.yawinsoftwares.ecommerce.html.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yawinsoftwares.ecommerce.html.util.ExcelUtil;
import com.yawinsoftwares.ecommerce.html.util.FileUtil;

@Component
@Scope("prototype")
public class ApparelIndexPage extends DefaultSubPage {

	String folderName = null;

	public ApparelIndexPage() {
	}
	
	@Autowired
	public ApparelIndexPage(String pageId, String folderName) {
		this.pageId=pageId;
		this.folderName=folderName;
	}

	@Override
	public StringBuffer getContent() {
		StringBuffer sb = new StringBuffer();
		sb.append(getSlideShow());
		sb.append(getCategoryShow());
		sb.append(getProductsShow());
		return sb;
	}
	
	
	public StringBuffer getSlideShow() {
		return getSlideShow(projectProperties.getProperty("source.slideshow.dir","images/slideshow"));
	}
	
	public StringBuffer getSlideShow(String slideshowDir) {
		StringBuffer sb = new StringBuffer();
		try {
			FileUtil.copyDirectory(slideshowDir,folderName+"/images/slideshow");
			File[] fileList = FileUtil.listFiles(slideshowDir,"jpg,jpeg,gif");
			List<String> fileNameList = new ArrayList<String>();
			List<Integer> indexList = new ArrayList<Integer>();
			int count =0;
			for(File file:fileList) {
				fileNameList.add("images/slideshow/"+file.getName());
				indexList.add(count++);
			}
			HashMap<String, Object> map = new HashMap<String,Object>();
			map.put("list", fileNameList);
			map.put("indexList", indexList);
			sb.append(velocity(findAndReplace(FileUtil.read(projectProperties.getProperty("project.html.dir")+"/slideshow.html")),map));
		} catch(Exception e) {
			System.out.println("ERROR:"+slideshowDir+" not available");
		}
			
		return sb;
	}

	public StringBuffer getCategoryShow() {
		return getCategoryShow("category");
	}
	
	public StringBuffer getCategoryShow(String name) {
		StringBuffer sb = new StringBuffer();
		String excelFileName = projectProperties.getProperty("homepage.file.name","homepage.xlsx");
		Map<String, ArrayList<ArrayList<Object>>> sheetMap = ExcelUtil.read(excelFileName);
		ArrayList<ArrayList<Object>> sheet = sheetMap.get(name);
		List<Integer> indexList = new ArrayList<Integer>();
		for(int i=0; i<sheet.size();i++) {
			indexList.add(i);
		}
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("sheet", sheet);
		map.put("indexList", indexList);
		sb.append(velocity(findAndReplace(FileUtil.read(projectProperties.getProperty("project.html.dir")+"/categoryshow.html")),map));
		return sb;
	}

	public StringBuffer getProductsShow() {
		return getProductsShow("products");
	}
	
	public StringBuffer getProductsShow(String name) {
		StringBuffer sb = new StringBuffer();
		String excelFileName = projectProperties.getProperty("homepage.file.name","homepage.xlsx");
		Map<String, ArrayList<ArrayList<Object>>> sheetMap = ExcelUtil.read(excelFileName);
		ArrayList<ArrayList<Object>> sheet = sheetMap.get(name);
		List<Integer> indexList = new ArrayList<Integer>();
		for(int i=0; i<sheet.size();i++) {
			indexList.add(i);
		}
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		map.put("sheet", sheet);
		map.put("indexList", indexList);
		sb.append(velocity(findAndReplace(FileUtil.read(projectProperties.getProperty("project.html.dir")+"/productsshow.html")),map));
		return sb;
	}
	
}
