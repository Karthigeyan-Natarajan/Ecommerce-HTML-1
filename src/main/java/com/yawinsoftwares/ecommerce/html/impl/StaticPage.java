package com.yawinsoftwares.ecommerce.html.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.yawinsoftwares.ecommerce.html.util.FileUtil;

@Component
@Scope("prototype")
public class StaticPage extends DefaultSubPage {

	public StaticPage() {
	}
	
	@Autowired
	public StaticPage(String pageId) {
		this.pageId=pageId;
	}

	@Override
	public StringBuffer getContent() {
		StringBuffer sb = new StringBuffer();
		sb.append(findAndReplace(FileUtil.read(projectProperties.getProperty("project.html.dir")+"/"+pageId+".html")));
		return sb;
	}

}
