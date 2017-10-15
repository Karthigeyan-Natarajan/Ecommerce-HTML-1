package com.yawinsoftwares.ecommerce.html.impl;

import org.springframework.stereotype.Component;

import com.yawinsoftwares.ecommerce.html.api.SubPage;
import com.yawinsoftwares.ecommerce.html.util.FileUtil;

@Component
public class DefaultSubPage extends DefaultPage implements SubPage {

	public DefaultSubPage() {
	}

	public DefaultSubPage(String pageId) {
		this.pageId=pageId;
	}

	@Override
	public StringBuffer getBody() {
		StringBuffer sb = new StringBuffer();
		sb.append("<body>\n");
		sb.append("<div class=\"container-fluid\">\n");
		sb.append(" <div class=\"row\"></div>\n");
		sb.append(" <div class=\"row\">\n");
		sb.append("  <div class=\"col-md-1\"></div>\n");
		sb.append("  <div class=\"col-md-10 nopadding overviewcontainer\">\n");
		sb.append("   <div class=\"contaniner-fluid\">\n");
		sb.append(getHeader());
		sb.append(getMenu());
		sb.append(getContent());
		sb.append(getFooter());
		sb.append("   </div>\n");
		sb.append("  </div>\n");
		sb.append("  <div class=\"col-md-1\"></div>\n");
		sb.append(" </div>\n");
		sb.append(" <div class=\"row\"></div>\n");
		sb.append("</div>\n");
		sb.append("</body>\n");
		return sb;
	}

	@Override
	public StringBuffer getHeader() {
		StringBuffer sb = new StringBuffer();
		sb.append(findAndReplace(FileUtil.read(projectProperties.getProperty("project.html.dir")+"/header.html")));
		return sb;
	}

	@Override
	public StringBuffer getFooter() {
		StringBuffer sb = new StringBuffer();
		sb.append(findAndReplace(FileUtil.read(projectProperties.getProperty("project.html.dir")+"/footer.html")));
		return sb;
	}

	@Override
	public StringBuffer getMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append(findAndReplace(FileUtil.read(projectProperties.getProperty("project.html.dir")+"/menu.html")));
		return sb;
	}

	@Override
	public StringBuffer getContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("content\n");
		return sb;
	}

}
