package com.kris.javalibrary.Model;


import com.kris.javalibrary.Bean.MyCookies;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetMyScore {

    MyCookies myCookies;

    public GetMyScore(MyCookies myCookies) {
        this.myCookies = myCookies;
    }


    public void go() {
        String xn = "2018";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";
        String cookies = "name=value; jwxkxt=" + myCookies.getJwxkxt() + "; ASP.NET_SessionId=" + myCookies.getASP_sessionid();

        Request request = new Request.Builder()
                .url("http://202.203.158.106/jwweb/xscj/Stu_MyScore_rpt.aspx")
                .post(new FormBody.Builder()
                        .add("sel_xn", xn)
                        .add("sel_xq", "0")
                        .add("SelXNXQ", "2")
                        .add("SJ", "1")
                        .add("zfx_flag", "0")
                        .add("zxf", "0")
                        .add("btn_search", "%BC%EC%CB%F7")
                        .build())
                .addHeader("User-Agent", userAgent)
                .addHeader("Referer", "http://202.203.158.106/jwweb/xscj/Stu_MyScore.aspx")
                .addHeader("Cookie", cookies)
                .build();

        OkHttpClient client = new OkHttpClient.Builder().build();

        try {
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            response.close();
            Document document = Jsoup.parse(responseData);
            //Elements tbody = document.select("#tableReportMain > tbody");
            getScores(document);
            //System.out.println(tbody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getScores(Document document) {
        String xq_gpa = document.select("#tableReportMain > tbody > tr:nth-child(6) > td:nth-child(7)").text();

        System.out.println("该学期GPA：" + xq_gpa);

    }
}




