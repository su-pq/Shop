package com.study.shop.project3_test;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class ApiExplorer {
	public static void main(String[] args) throws IOException {
		StringBuilder urlBuilder = new StringBuilder("http://openapi.its.ulsan.kr/UlsanAPI/getGuganLevel4Info.xo"); /* URL */
		String serviceKey = "hTjqfwgyovO3myFgGZjp6XZ10gSfVYPBPHTkkzwKvJ87P%2BkxJ5bBlwXgUqeCrwCIIZOpiXn9Nd7fS2bpqkIG6g%3D%3D";
		
		urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + serviceKey); /* Service Key */
		urlBuilder.append("&" + URLEncoder.encode("roadnm", "UTF-8") + "=" + URLEncoder.encode("강남로", "UTF-8")); /* 가로명 */
		urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /* 기본1 */
		urlBuilder.append(
				"&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /* 기본10 */
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());
	}
}