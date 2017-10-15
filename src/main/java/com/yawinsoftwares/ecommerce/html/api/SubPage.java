package com.yawinsoftwares.ecommerce.html.api;

public interface SubPage extends Page {
	public StringBuffer getHeader();
	public StringBuffer getFooter();
	public StringBuffer getMenu();
	public StringBuffer getContent();
}
