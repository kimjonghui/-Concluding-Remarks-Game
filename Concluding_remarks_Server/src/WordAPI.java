import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;

public class WordAPI {
	public int getTotalValue(String word) {
		try {
			String key = "4F568F0B880583B7E82510AAFA533017";
			URL url = new URL("https://stdict.korean.go.kr/api/search.do?key=" + key + "&type_search=search&q=" + word
					+ "&req_type=json");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// System.out.println(connection.getResponseCode()); // http에서 상태코드를 확인.
			// 해당 웹에서는 유효하지 않은 단어도 200의 값을 반환 해주어서 사용이 불가능함.
			System.out.println("-1일 경우 유효한단어, 0일 경우 유효하지 않은 단어 : " + connection.getContentLength()); // 유효한 단어면 -1 반환,
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
			br.close();
			if (connection.getContentLength() == -1) { // 유효한 단어일시 JSON 파싱
				// JSON 문자열을 JSONObject로 파싱
				JSONObject jsonObject = new JSONObject(response.toString());
				System.out.println(jsonObject);
				// "channel" 객체에서 "total" 값을 추출
				int total = jsonObject.getJSONObject("channel").getInt("total");
				if (total > 0) {
					return -1; // 유효한 단어일 경우 -1을 반환
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0; // 유효하지 않는 단어일시 0을 반환
	}

}