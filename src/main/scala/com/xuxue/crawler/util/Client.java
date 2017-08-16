package com.xuxue.crawler.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author xujq
 * @time 2017-7-16
 */
public class Client {


    public static String crawler(String userName,String password)throws Exception{
        String urlPath = "http://localhost:8887/api/crawler/crawler";
        String param="userName="+ URLEncoder.encode(userName,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8");
        //建立连接
        URL url=new URL(urlPath);
        HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
        //设置参数
        httpConn.setDoOutput(true);   //需要输出
        httpConn.setDoInput(true);   //需要输入
        httpConn.setUseCaches(false);  //不允许缓存
        httpConn.setRequestMethod("POST");   //设置POST方式连接
        httpConn.setReadTimeout(100000);
        httpConn.setConnectTimeout(100000);
        //设置请求属性
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        httpConn.setRequestProperty("Charset", "UTF-8");
        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
        httpConn.connect();
        //建立输入流，向指向的URL传入参数
        DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
        dos.writeBytes(param);
        dos.flush();
        dos.close();
        //获得响应状态
        int resultCode=httpConn.getResponseCode();
        StringBuffer sb=new StringBuffer();
        if(HttpURLConnection.HTTP_OK==resultCode){
            String readLine;
            BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
            while((readLine=responseReader.readLine())!=null){
                sb.append(readLine).append("\n");
            }
            responseReader.close();
        }
        return sb.toString();
    }

    public static void main(String[] args)throws Exception{
        System.out.println(crawler("mazhiping13","ma123"));
    }

}
