package com.yawinsoftwares.ecommerce.html.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.yawinsoftwares.ecommerce.html.api.Page;
import com.yawinsoftwares.ecommerce.html.api.Project;
import com.yawinsoftwares.ecommerce.html.util.ExcelUtil;
import com.yawinsoftwares.ecommerce.html.util.FileUtil;
import com.yawinsoftwares.ecommerce.html.util.ProjectProperties;
import com.yawinsoftwares.ecommerce.html.util.ZipUtil;

@Component
public class DefaultProject implements Project {

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	ProjectProperties projectProperties;

	@Autowired
	Environment env;
	
	@Override
	public void createProjectZip() {
		createProjectZip(projectProperties.getProperty("project.zip.filename"));
	}

	@Override
	public void createProjectZip(String zipName) {
		createProjectFolder();
		ZipUtil.zipDirectory(projectProperties.getProperty("project.zip.dir"), zipName);
		deleteProjectFolder();
	}

	@Override
	public void createProjectFolder() {
		createProjectFolder(projectProperties.getProperty("project.zip.dir"));
	}

	@Override
	public void createProjectFolder(String folderName) {
		try {
			FileUtil.createDir(folderName);
			FileUtil.copyDirectory(projectProperties.getProperty("project.css.dir"),folderName+"/css");
			FileUtil.copyDirectory(projectProperties.getProperty("project.fonts.dir"),folderName+"/fonts");
			FileUtil.copyDirectory(projectProperties.getProperty("project.js.dir"),folderName+"/js");
			FileUtil.copyDirectory(projectProperties.getProperty("project.images.dir"),folderName+"/images");
			//createStaticPages(folderName);
			//createApparelSubPages(folderName);
			createApparelIndexPages(folderName);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteProjectFolder() {
		deleteProjectFolder(projectProperties.getProperty("project.zip.dir"));
	}

	public void deleteProjectFolder(String folderName) {
		FileUtil.removeDir(folderName);
	}

	public void createStaticPages() {
		createStaticPages(projectProperties.getProperty("project.html.dir"));
	}
	
	public void createStaticPages(String folderName) {
		String[] staticPageList = env.getProperty("saticpage.list").split("\\s*,\\s*");
		for(int i=0; staticPageList!=null && i<staticPageList.length; i++) {
			System.out.println(staticPageList[i]);
			Page page = context.getBean(StaticPage.class,staticPageList[i]);
			page.save(folderName);
		}
	}

	public void createApparelSubPages(String folderName) {
		String excelFileName = projectProperties.getProperty("product.file.name","products.xlsx");
		Map<String, ArrayList<ArrayList<Object>>> sheetMap = ExcelUtil.read(excelFileName);
		Iterator<String> sheetIterator = sheetMap.keySet().iterator();
		while(sheetIterator.hasNext()) {
			String name = sheetIterator.next();
			System.out.println(name);
			Page page = context.getBean(ApparelListPage.class,name,sheetMap.get(name),folderName);
			page.save(folderName);
		}
	}
	
	public void createApparelIndexPages(String folderName) {
		String name="index";
		Page page = context.getBean(ApparelIndexPage.class,name);
		System.out.println(name);
		page.save(folderName);
	}
}
