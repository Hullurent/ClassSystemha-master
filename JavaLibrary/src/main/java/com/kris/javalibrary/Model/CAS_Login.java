

/***
 *                    _ooOoo_
 *                   o8888888o
 *                   88" . "88
 *                   (| -_- |)
 *                    O\ = /O
 *                ____/`---'\____
 *              .   ' \\| |// `.
 *               / \\||| : |||// \
 *             / _||||| -:- |||||- \
 *               | | \\\ - /// | |
 *             | \_| ''\---/'' | |
 *              \ .-\__ `-` ___/-. /
 *           ___`. .' /--.--\ `. . __
 *        ."" '< `.___\_<|>_/___.' >'"".
 *       | | : `- \`.;`\ _ /`;.`/ - ` : | |
 *         \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 *                    `=---='
 *
 * .............................................
 *          佛祖保佑             永无BUG
 */

package com.kris.javalibrary.Model;


import com.kris.javalibrary.Bean.MyCookies;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CAS_Login {

    private String username = "201614454101";
    private String password = "1d13c5b7fe30634b5a96ba9724d0aa2b";
    private String Sel_XZBJ = "2016080701";
    private String Sel_XNXQ = "20190";

    private String JSESSIONID;
    private String jwxkxt;
    private String ASP_sessionid = null;
    private String ticket;

    public MyCookies getMyCookies() {
        this.getCookies();
        return new MyCookies(JSESSIONID, jwxkxt, ASP_sessionid, ticket);
    }


    private String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
    }

    private void getCookies() {

        try {
            // 创建模拟客户端
            OkHttpClient client = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        private ConcurrentMap<String, List<Cookie>> storage = new ConcurrentHashMap<>();

                        @SuppressWarnings("NullableProblems")
                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            String host = url.host();
                            if (!cookies.isEmpty()) {
                                storage.put(host, cookies);
                            }
                        }

                        @SuppressWarnings("NullableProblems")
                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            String host = url.host();
                            List<Cookie> list = storage.get(host);
                            return list == null ? new ArrayList<>() : list;
                        }
                    }).build();

            final String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36";

            //第一次Get请求, 获取JSESSIONID cookie.
            Request request = new Request.Builder()
                    .url("http://202.203.158.158/sso//login?service=http://202.203.158.106/jwweb/cas_ynmz.aspx")
                    .addHeader("User-Agent", userAgent)
                    .build();

            Response response1 = client.newCall(request).execute();

            final Headers headers1 = response1.headers();
            List<String> coo = headers1.values("Set-Cookie");
            // System.out.println(coo);
            final String sessionid = coo.get(0); // Todo 需要处理
            JSESSIONID = sessionid.substring(11, sessionid.indexOf(";"));
            // System.out.println(JSESSIONID);


//        if(!response.isSuccessful()){
//            Log.d("LoginActivity", "");
//        }
            //第二次POST请求, 发送表单验证信息 建立session
            request = new Request.Builder()
                    .url("http://202.203.158.158/sso/ssoLogin")
                    .post(new FormBody.Builder()
                            .add("username", "201614454101")
                            .add("password", "1d13c5b7fe30634b5a96ba9724d0aa2b")
                            .build())
                    .addHeader("User-Agent", userAgent)
                    .build();

            Response response2 = client.newCall(request).execute(); // 无需传值到response
            //System.out.println(response2);


            request = new Request.Builder()
                    .url("http://202.203.158.158/sso/checkLoginFirst")
                    .addHeader("User-Agent", userAgent)
                    .build();
            response2 = client.newCall(request).execute();
            // System.out.println(response2);


            //第四次POST请求, 前往CAS客户端进行验证获取会话
            request = new Request.Builder()
                    .url("http://202.203.158.158/sso//login;jsessionid=" + JSESSIONID + "?service=http://202.203.158.106/jwweb/cas_ynmz.aspx")
                    .post(new FormBody.Builder()
                            .add("username", username)
                            .add("password", password)
                            .add("lt", "e1s1")
                            .add("_eventId", "submit")
                            .build())
                    .addHeader("User-Agent", userAgent)
                    .build();

            Response response3 = client.newCall(request).execute();
            // System.out.println(response3); // 得到ticket_url
            final Headers headers3 = response3.headers();
            // System.out.println(headers3);

            List<String> cookies = headers3.values("Set-Cookie");
            String cookies_s = listToString(cookies, ',');
            String Jwxkxt;
            //String jwxkxt;
            String ASP_sessionID;
            //String ASP_sessionid =null;
            // Todo cookies 值抓取不稳定,尝试从cookies中筛选出所需的cookie值；
            if (cookies_s.contains("HttpOnly")) {
                Jwxkxt = cookies.get(2);
                jwxkxt = Jwxkxt.substring(7, Jwxkxt.indexOf(";"));
                ASP_sessionID = cookies.get(1);
                ASP_sessionid = ASP_sessionID.substring(18, ASP_sessionID.indexOf(";"));
            } else {
                Jwxkxt = cookies.get(1);
                jwxkxt = Jwxkxt.substring(7, Jwxkxt.indexOf(";"));
                ASP_sessionID = cookies.get(0);
                ASP_sessionid = ASP_sessionID.substring(18, ASP_sessionID.indexOf(";"));
            }


            //  System.out.println(jwxkxt);
            // System.out.println(ASP_sessionid);

            String request_cookie = "name=value; jwxkxt=" + jwxkxt + "; ASP.NET_SessionId=" + ASP_sessionid;
            // System.out.println(request_cookie);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
