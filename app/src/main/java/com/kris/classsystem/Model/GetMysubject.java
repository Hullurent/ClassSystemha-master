package com.kris.classsystem.Model;

import com.kris.classsystem.Bean.MyCookies;
import com.kris.classsystem.Bean.MySubject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GetMysubject {
    private MyCookies myCookies;
    private List<MySubject> mySubjects = new ArrayList<>();

    public GetMysubject(MyCookies myCookies) {
        this.myCookies = myCookies;
    }

    public void go() {

        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";
        String cookies = "name=value; jwxkxt=" + myCookies.getJwxkxt() + "; ASP.NET_SessionId=" + myCookies.getASP_sessionid();
        String Sel_XZBJ = "2016080701";
        String Sel_XNXQ = "20190";


        for (int i = 1; i <= 10; i++) {
            Request request1 = new Request.Builder()
                    .url("http://202.203.158.106/jwweb/ZNPK/KBFB_DayJCSel_rpt.aspx")
                    .post(new FormBody.Builder()
                            .add("xzbj2_input_bjmc", "")
                            .add("Sel_XZBJ", Sel_XZBJ)
                            .add("Sel_XNXQ", Sel_XNXQ)
                            .add("Sel_XQ", "")
                            .add("sel_yx", "Nothing")
                            .add("sel_bm", "Nothing")
                            .add("Sel_ZC", Integer.toString(i))
                            .add("rad", "1")
                            .add("txt_yzm", "")
                            .add("Sel_KC", "")
                            .add("sel_cddw", "")
                            .add("Sel_JS", "")
                            .build())
                    .addHeader("Referer", "http://202.203.158.106/jwweb/ZNPK/KBFB_DayJCSel.aspx")
                    .addHeader("User-Agent", userAgent)
                    .addHeader("Cookie", cookies)
                    .addHeader("Host", "202.203.158.106")
                    .build();

            OkHttpClient client = new OkHttpClient.Builder().build();
            Response response;

            try {
                response = client.newCall(request1).execute();
                //System.out.println(response);
                String responseData = response.body().string().replaceAll("云南民族大学周/日/节次课表", "");
                response.close();
                Document document = Jsoup.parse(responseData);

                filter(document);

                List<Integer> weekList = new ArrayList<>(Arrays.asList(i)); // Todo 去了解java核心collection.
                packer();
                System.out.println(weekList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void filter(Document document) {
        String className;
        String classRoom = null;

        Element table = document.getElementsByTag("table").last();
        Elements tbody = Jsoup.parse(table.toString().replaceAll("<td width=\"10%\" align=\"center\">", "")).getElementsByTag("tbody");

        for (Element element : tbody) {
            Elements tr = element.getElementsByTag("tr");
            for (Element element1 : tr) {

                if ("课程".equals(element1.getElementsByAttributeValue("width", "'22%'").text()))
                    continue; // Todo Why not break? continue means skip.
                else if ((element1.getElementsByAttributeValue("width", "'22%'").text()).contains("通"))
                    continue;
                else if ("".equals(element1.getElementsByAttributeValue("width", "'22%'").text()))
                    continue;
                else {
                    className = element1.getElementsByAttributeValue("width", "'22%'").text();
                    //className = className.substring(1, className.length());
                    System.out.println(className);
                }
                if (!"".equals(element1.getElementsByAttributeValue("width", "\'16%\'").text())) {
                    classRoom = element1.getElementsByAttributeValue("width", "\'16%\'").text();
                    System.out.println(classRoom);
                }
                String weeks = element1.getElementsByAttributeValue("width", "\'12%\'").text();
                System.out.println(weeks);

                String teacher = element1.getElementsByAttributeValue("width", "'10%'").text();
                System.out.println(teacher);
                int weekNum = transformWeek(weeks);
                System.out.println(weekNum);
                int start = Integer.parseInt(weeks.subSequence(2, 3).toString());
                System.out.println(start);
                int end = Integer.parseInt(weeks.substring(weeks.indexOf("-") + 1, weeks.indexOf("节")));
                System.out.println(end);


            }
        }
    }

    public void packer() {
        // mySubjects.add(new MySubject("2018-2019学年春", className, classRoom, teacher, weekList, start, end - start + 1, weekNum, 1, ""));
    }

    private static int transformWeek(String weeks) {
        int week = 1;
        switch (weeks.subSequence(0, 1).toString()) {
            case "一":
                week = 1;
                break;
            case "二":
                week = 2;
                break;
            case "三":
                week = 3;
                break;
            case "四":
                week = 4;
                break;
            case "五":
                week = 5;
                break;
            case "六":
                week = 6;
                break;
            case "日":
                week = 7;
                break;
            default:
                break;
        }
        return week;
    }


}