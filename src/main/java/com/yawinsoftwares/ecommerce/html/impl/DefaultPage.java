package com.yawinsoftwares.ecommerce.html.impl;

import java.io.StringWriter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.shared.utils.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.yawinsoftwares.ecommerce.html.api.Page;
import com.yawinsoftwares.ecommerce.html.util.FileUtil;
import com.yawinsoftwares.ecommerce.html.util.ProjectProperties;

@Component
public class DefaultPage implements Page{

	public final String DEFAULT_PAGEID="default";
	public String pageId="default";

	@Autowired
	Environment env;

	@Autowired
	ProjectProperties projectProperties;

	public DefaultPage() {
		this("default");
	}
	
	public DefaultPage(String pageId) {
		this.pageId=pageId;
	}
	
	@Override
	public StringBuffer getHTMLString() {
		StringBuffer sb = new StringBuffer();
		sb.append(startHTMLTag());
		sb.append(getHead());
		sb.append(getBody());
		sb.append(endHTMLTag());
		return sb;
	}
	
	private StringBuffer startHTMLTag() {
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html>\n");
		sb.append("<html lang=\"en\">\n");
		return sb;
	}

	@Override
	public StringBuffer getHead() {
		StringBuffer sb = new StringBuffer();
		sb.append("<head>\n");
		sb.append(getMeta());
		sb.append(getTitle());
		sb.append("</head>\n");
		return sb;
	}

	@Override
	public StringBuffer getBody() {
		StringBuffer sb = new StringBuffer();
		sb.append("<body></body>\n");
		return sb;
	}

	private StringBuffer endHTMLTag() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js\"></script>\n");
		sb.append("<script>window.jQuery || document.write('<script src=\"js/jquery-1.9.1.min.js\"><\\/script>')</script>\n");
		sb.append("<script src=\"js/bootstrap.min.js\"></script>\n");
		sb.append("</html>");
		return sb;
	}

	public StringBuffer getTitle() {
		String title = getPropertyValue(pageId + ".title", getPropertyValue("company.name") +" - "+StringUtils.capitalise(pageId));
		StringBuffer sb = new StringBuffer();
		if(title==null||title.trim().length()==0) return sb;
		sb.append("<title>");
		sb.append(title);
		sb.append("</title>\n");
		return sb;
	}

	public StringBuffer getKeywords() {
		StringBuffer sb = new StringBuffer();
		String keywords = getPropertyValue(pageId + ".keywords",  getPropertyValue("company.name")+","+StringUtils.capitalise(pageId));
		if(!isEmpty(keywords)) sb.append("<meta name=\"keywords\" content=\"").append(keywords).append("\" />\n");
		return sb;
	}

	public StringBuffer getDescription() {
		StringBuffer sb = new StringBuffer();
		String description = getPropertyValue(pageId + ".description",  getPropertyValue("company.name")+","+StringUtils.capitalise(pageId));
		if(!isEmpty(description)) sb.append("<meta name=\"description\" content=\"").append(description).append("\" />\n");
		return sb;
	}
	
	public StringBuffer getMeta() {
		StringBuffer sb = new StringBuffer();
		sb.append("<meta charset=\"UTF-8\">\n");
		sb.append(getKeywords());
		sb.append(getDescription());

		sb.append("<meta name=\"rights\" content=\"").append(getPropertyValue("site.name")).append("\" />\n");
		sb.append("<meta name=\"robots\" content=\"").append("index").append("\" />\n");
		sb.append("<meta name=\"author\" content=\"").append(AUTHOR_CAMEL).append("\" />\n");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n");
		sb.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n");
		sb.append("<meta http-equiv=\"cache-control\" content=\"max-age=0\">\n");
		sb.append("<meta http-equiv=\"cache-control\" content=\"no-cache\" />\n");
		sb.append("<meta http-equiv=\"expires\" content=\"0\" />\n");
		sb.append("<meta http-equiv=\"expires\" content=\"Tue, 01 Jan 1980 1:00:00 GMT\" />\n");
		sb.append("<meta http-equiv=\"pragma\" content=\"no-cache\" />\n");

		sb.append("<link rel=\"icon\" href=\"images/favicon.html\" type=\"image/x-icon\">\n");
		sb.append("<link rel=\"shortcut icon\" href=\"images/favicon.ico\" type=\"image/x-icon\" />\n");

		sb.append("<!--[if lt IE 9]>\n");
		sb.append("  <script src=\"https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js\"></script>\n");
		sb.append("  <script src=\"https://oss.maxcdn.com/respond/1.4.2/respond.min.js\"></script>\n");
		sb.append("<![endif]-->\n");

		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/bootstrap.min.css\">\n");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/bootstrap-theme.min.css\">\n");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/font-awesome.min.css\">\n");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">\n");

		sb.append("<script type=\"text/javascript\" src=\"js/bootstrap.min.js\"></script>\n");
		sb.append("<script type=\"text/javascript\" src=\"js/jquery.js\"></script>\n");
		sb.append("<script type=\"text/javascript\" src=\"js/npm.js\"></script>\n");
		sb.append("<script type=\"text/javascript\" src=\"js/script.js\"></script>\n");
		
		return sb;
	}

	
	public String getPropertyValue(String key) {
		String value=env.getProperty(key);
		if(value==null) value=projectProperties.getProperty(key);
		return value;
	}

	public String getPropertyValue(String key, String defaultValue) {
		String value=env.getProperty(key);
		if(value==null) value=projectProperties.getProperty(key);
		if(value==null) value=defaultValue;
		return value;
	}
	
	public boolean isEmpty(String str) {
		return str==null||str.trim().length()==0;
	}
	final String AUTHOR="Y"+"A"+"W"+"I"+"N "+"S"+"O"+"F"+"T"+"W"+"A"+"R"+"E"+"S";
	final String AUTHOR_URL="w"+"w"+"w."+AUTHOR.replaceAll(" ", "").toLowerCase()+".c"+"o"+"m";
	final String AUTHOR_CAMEL="Y"+"a"+"w"+"i"+"n "+"S"+"o"+"f"+"t"+"w"+"a"+"r"+"e"+"s";

	@Override
	public void save(String dirName) {
		String fileName = env.getProperty(pageId+".filename",pageId+".html");
		FileUtil.save(dirName+"/"+fileName, getHTMLString().toString());
	}
	
	public StringBuffer findAndReplace(String str) {
		Pattern p = Pattern.compile( "\\$\\{([^}]+)\\}");
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (m.find())
		{
			String key = m.group(1);
			String value=env.getProperty(key);
			if(value==null) value=projectProperties.getProperty(key);
			if(value!=null)m.appendReplacement(sb, value);
		}
		m.appendTail(sb);
		return sb;
	}
	
	public StringBuffer findAndReplace(String str, Map<String, String> map) {
		Pattern p = Pattern.compile( "\\$\\{([^}]+)\\}");
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (m.find())
		{
			String key = m.group(1);
			String value=map.get(key);
			if(value==null) value=env.getProperty(key);
			if(value==null) value=projectProperties.getProperty(key);
			if(value!=null)m.appendReplacement(sb, value);
		}
		m.appendTail(sb);
		return sb;
	}

	public StringBuffer velocity(StringBuffer sb, Map<String, Object> map) {
		return velocity(sb.toString(), map);
	}
	
	public StringBuffer velocity(String str, Map<String, Object> map) {
        VelocityContext context = new VelocityContext();
        map.forEach((k, v)->context.put(k, v));
        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "s", str);
        return writer.getBuffer();
	}

}
