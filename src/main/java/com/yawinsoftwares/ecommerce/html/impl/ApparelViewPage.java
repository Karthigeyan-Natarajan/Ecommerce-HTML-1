package com.yawinsoftwares.ecommerce.html.impl;

import java.util.Map;

import org.apache.maven.shared.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yawinsoftwares.ecommerce.html.util.FileUtil;

@Component
@Scope("prototype")
public class ApparelViewPage extends DefaultSubPage {

	Map<String, String> map = null;
	
	public ApparelViewPage() {
	}
	
	@Autowired
	public ApparelViewPage(String pageId, Map<String, String> map) {
		this.pageId=pageId;
		this.map = map;
	}

	@Override
	public StringBuffer getKeywords() {
		StringBuffer sb = new StringBuffer();
		String keywords = getPropertyValue(pageId + ".keywords",  getPropertyValue("company.name")+","+StringUtils.capitalise(pageId)+","+map.get("name"));
		if(!isEmpty(keywords)) sb.append("<meta name=\"keywords\" content=\"").append(keywords).append("\" />\n");
		return sb;
	}

	@Override
	public StringBuffer getDescription() {
		StringBuffer sb = new StringBuffer();
		String description = getPropertyValue(pageId + ".description",  getPropertyValue("company.name")+","+StringUtils.capitalise(pageId)+","+map.get("name"));
		if(!isEmpty(description)) sb.append("<meta name=\"description\" content=\"").append(description).append("\" />\n");
		return sb;
	}
	
	@Override
	public StringBuffer getTitle() {
		String title = getPropertyValue(pageId + ".title", getPropertyValue("company.name") +" - "+StringUtils.capitalise(map.get("name")));
		StringBuffer sb = new StringBuffer();
		if(title==null||title.trim().length()==0) return sb;
		sb.append("<title>");
		sb.append(title);
		sb.append("</title>\n");
		return sb;
	}

	@Override
	public void save(String dirName) {
		String fileName = env.getProperty(pageId+".filename",map.get("file.url"));
		FileUtil.save(dirName+"/"+fileName, getHTMLString().toString());
	}
	
	public void save(String dirName, int index) {
		String fileName = env.getProperty(pageId+".filename",map.get("file.url"));
		fileName = fileName.replace(".html",index+".html");
		FileUtil.save(dirName+"/"+fileName, getHTMLString().toString());
	}
	
	@Override
	public StringBuffer getContent() {
		StringBuffer sb = new StringBuffer();
		sb.append(findAndReplace(FileUtil.read(projectProperties.getProperty("project.html.dir")+"/"+projectProperties.getProperty("project.apparelview.filename")), map));
		return sb;
	}
}
