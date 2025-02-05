package edu.store.kh.GeneralStore.externalAPI;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// 네이버 API 예제 - 회원프로필 조회
public class APIExamMemberProfile {

    /*
     외부 자바 코드를 가져올 때 주의해야할 점
     프로젝트에서 static void main(String[] args)는 프로젝트 시작과 같은 역할을 하기 때문에 1개 이상의 main 메소드가 존재하면 안된다.
        따라서, 외부 코드를 사용하기 위해서는 main 메소드를 주석처리하거나 삭제해야 한다.
     만약 메서드 명칭이 static void main(String[] args)인 메소드를 사용하고자 한다면 method1()와 같이 메소드 명칭을 변경해주어야 한다.
     작성한 method1()를 호출하는 방법은 다음과 같다.

        APIExamMemberProfile api = new APIExamMemberProfile();
        api.method1();

        method1이 반환해야할 값이 없다면 void를 사용하면 된다.
        만약 반환해야할 값이 있다면 반환해야할 값의 자료형을 적어주면 된다. (String, int, double, ...)

    */

    public static void main(String[] args) {
        String token = "YOUR_ACCESS_TOKEN";// 네아로 접근 토큰 값";
        String header = "Bearer " + token; // Bearer 다음에 공백 추가
        try {
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}