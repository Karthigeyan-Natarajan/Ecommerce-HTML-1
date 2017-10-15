package com.yawinsoftwares.ecommerce.html.api;

public interface Page {
	public StringBuffer getHead();
	public StringBuffer getBody();
	public StringBuffer getHTMLString();
	public void save(String dirName);
}
