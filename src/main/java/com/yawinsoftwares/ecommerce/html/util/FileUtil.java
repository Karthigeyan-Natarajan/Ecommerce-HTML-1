package com.yawinsoftwares.ecommerce.html.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class FileUtil {
	public static File createDir(String dirName) {
		File file  = new File(dirName);
		return file.mkdirs()?file:null;
	}
	
	public static void copyFileToDirectory(String srcFile, String destDir) throws IOException {
		FileUtils.copyFileToDirectory(new File(srcFile), new File(destDir));
	}

	public static void copyDirectory(String srcDir, String destDir) throws IOException {
		FileUtils.copyDirectory(new File(srcDir), new File(destDir));
	}

	public static void removeDir(String dirName) {
		try {
			FileUtils.deleteDirectory(new File(dirName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void save(String fileName, String content) {
		try {
			FileUtils.writeStringToFile(new File(fileName),content,"UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String read(String fileName) {
		try {
			return FileUtils.readFileToString(new File(fileName),"UTF-8");
		} catch (IOException e) {
			return "";
		}
	}
	
	public static File[] listFiles(String directoryName, String filter) {
		return new File(directoryName).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return filter.contains(name.substring(name.lastIndexOf(".")+1));
			}
		});
	}
	
	public static void main(String[] args) {
		String str="sdf sdf sdf ${abc} sdfs sdf";
		Pattern p = Pattern.compile( "\\$\\{([^}]+)\\}");
		Matcher m = p.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (m.find())
		{
			String key = m.group(1);
			String value="value";
			System.out.println(key);
			if(value!=null)m.appendReplacement(sb, value);
		}
		m.appendTail(sb);
		System.out.println(sb.toString());
		System.out.println(m.find());
	}
}
