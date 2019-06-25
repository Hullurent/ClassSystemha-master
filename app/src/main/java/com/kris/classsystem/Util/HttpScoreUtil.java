package com.kris.classsystem.Util;

import android.util.Log;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HttpScoreUtil {
    private String cookie = null;
    private String sel_xn = null;
    private String sel_xq = null;

    public HttpScoreUtil(String xn, String xq) {
        sel_xn = xn;
        sel_xq = xq;
    }


    public List<MyScore> getQuery() {
        List<MyScore> scores = null;
        //Log.d(TAG, "getQuery: ");
        System.out.println(cookie + "---" + sel_xn + "---" + sel_xq);
        try {
            URL url = new URL(
                    "http://202.203.158.106/jwweb/xscj/Stu_MyScore_rpt.aspx"
            );
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Cookie", cookie);

            OutputStream os = conn.getOutputStream();
            String param = new String();
            param = "sel_xn=" + sel_xn + "&sel_xq=" + sel_xq + "&SJ=" + "1"
                    + "&SelXNXQ=" + "2" + "&zfx_flag=" + "0" + "&zfx=" + "0";
            os.write(param.getBytes());

            int code = conn.getResponseCode();
            if (code == 200) {
                String result;
                InputStream in = conn.getInputStream();
                result = streamTools(in);
                scores = filterHtml(result);
                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scores;
    }

    //过滤解析获取的HTML代码, 提取成绩, 课表等信息
    private List<MyScore> filterHtml(String source) {
        List<MyScore> scores = new ArrayList<MyScore>();
        StringBuffer sff = new StringBuffer();
        String score[];
        int i = 0, j = 0;
        String html = source;
        org.jsoup.nodes.Document doc = Jsoup.parse(html);
        org.jsoup.select.Elements links_class = ((org.jsoup.nodes.Element) doc)
                .select("td[width=23%][align=left]");
        org.jsoup.select.Elements links_grade = ((org.jsoup.nodes.Element) doc)
                .select("td[width=5%][align=right]");

        score = new String[links_grade.size()];
        for (org.jsoup.nodes.Element link_grade : links_grade) {
            score[i++] = link_grade.text();
        }
        for (org.jsoup.nodes.Element link : links_class) {
            MyScore my_Score = new MyScore();
            my_Score.setMy_class(((org.jsoup.nodes.Element) link).text());//TODO Is this right?
            my_Score.setMy_score(score[j]);
            scores.add(my_Score);
            j = j + 2;
        }
        return scores;
    }

    // 将一个inputStream 对象 转换为 String 对象
    private String streamTools(InputStream in) throws IOException {

        BufferedReader bufr = new BufferedReader(new InputStreamReader(in, "GBK"));


        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = bufr.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }


    public void getCookies(String username, String password) {
        try {
            URL url = new URL(
                    "http://202.203.158.106/jwweb/_data/index_LOGIN.aspx"); //教务系统的登录url地址;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            OutputStream os = conn.getOutputStream();
            String param;
            param = "Sel_Type=" + "STU" + "&UserID=" + username + "&PassWord=" + password;
            os.write(param.getBytes());
            conn.connect();
            int code = conn.getResponseCode();
            if (code == 200) {
                String responseCookie = conn.getHeaderField("Set-Cookie");
                cookie = responseCookie.substring(0,
                        responseCookie.indexOf(";"));
                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
