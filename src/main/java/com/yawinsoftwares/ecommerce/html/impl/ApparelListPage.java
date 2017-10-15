package com.yawinsoftwares.ecommerce.html.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yawinsoftwares.ecommerce.html.api.Page;
import com.yawinsoftwares.ecommerce.html.util.FileUtil;

@Component
@Scope("prototype")
public class ApparelListPage extends DefaultSubPage{

	@Autowired
	private ApplicationContext context;

	String folderName = null;
	ArrayList<ArrayList<Object>> sheet = null;
	NumberFormat format = new DecimalFormat("#0.00"); 
	
	public ApparelListPage() {
	}
	
	@Autowired
	public ApparelListPage(String pageId, ArrayList<ArrayList<Object>> sheet, String folderName) {
		this.pageId=pageId;
		this.sheet = sheet;
		this.folderName=folderName;
	}
	
	@Override
	public StringBuffer getContent() {
		format = new DecimalFormat(env.getProperty("price.format")); 
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"row productrow\">\n");
		int count = 0;
		for(int i=0; i<sheet.size(); i++) {
			String imageName =sheet.get(i).get(0).toString().trim();
			try {
				FileUtil.copyFileToDirectory(projectProperties.getProperty("source.image.dir")+"/"+imageName,folderName+"/images/"+pageId);
				Map<String, String> map = getProductDetailsMap(sheet.get(i));
				sb.append(findAndReplace(FileUtil.read(projectProperties.getProperty("project.html.dir")+"/"+projectProperties.getProperty("project.apparellist.filename")), map));
	
				Page page = context.getBean(ApparelViewPage.class,imageName.substring(0,imageName.lastIndexOf(".")),map);
				page.save(folderName);
				count++;
			} catch(Exception e) {
				System.out.println("ERROR:"+pageId+":"+projectProperties.getProperty("source.image.dir")+"/"+imageName+" not available");
			}
		}
		if(count==0) {
			sb.append("<br><br><br><br><br><center><h2>Sorry, All items are sold out.<h2></center><br><br><br><br><br>");
		}
		
		sb.append("</div>\n"); 
		return sb;
	}
	
	public Map<String, String> getProductDetailsMap(ArrayList<Object> row){
		Map<String, String> map = new HashMap<String, String>();
		map.put("pageid", this.pageId);
		if(row.get(0)!=null) map.put("image.url", "images/"+pageId+"/"+row.get(0).toString().trim());
		if(row.get(1)!=null) map.put("name", row.get(1).toString().trim());
		if(row.get(2)!=null) map.put("actual.price", format.format(row.get(2)));
		if(row.get(3)!=null) map.put("selling.price", format.format(row.get(3)));

		String imageName =row.get(0).toString().trim();
		if(row.get(0)!=null) map.put("file.url", pageId+"_"+imageName.substring(0,imageName.lastIndexOf("."))+".html");

		return map;
	}
}
