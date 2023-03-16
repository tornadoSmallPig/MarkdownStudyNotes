import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AI_first {
    public static void main(String[] args) {
        // 定义AI的问题（prompt）和API密钥
        String prompt = "Hello, how are you?";
        String apiKey = "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";
        // 定义OpenAI API的Endpoint
        String apiEndPoint = "https://api.openai.com/v1/engines/davinci-codex/completions";
        try {
            // 创建URL对象
            URL url = new URL(apiEndPoint);
            // 创建HTTP连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置请求方法为POST
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            // 设置请求头，包括Content-Type和Authorization
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);

            // 构造JSON格式的数据
            String data = "{\"prompt\":\"" + prompt + "\",\"max_tokens\":10,\"n\":1,\"stop\":\"\\n\"}";
            // 将数据写入HTTP请求体中
            conn.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));

            // 如果HTTP响应码不是200，则抛出异常
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            // 读取HTTP响应内容
            Scanner scanner = new Scanner(conn.getInputStream());
            String response = scanner.useDelimiter("\\A").next();
            // 打印AI的回答
            System.out.println("Response : " + response);

            scanner.close();
            conn.disconnect();
        } catch (Exception e) {
            //捕获异常并打印
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
    }
}