package Model.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientGet {
    private HttpClient http_client;
    private HttpGet get_request;
    private HttpResponse response;

    private String get_projects_addr = "http://142.93.134.194:8000/joboonja/project";
    private String get_skills_addr = "http://142.93.134.194:8000/joboonja/skill";

    public HttpClientGet() {
        http_client = HttpClientBuilder.create().build();
    }

    public String HttpGetRequest(RequestType req_type) throws IOException{
        String ip_addr = mapRequestTypeToAddress(req_type);
        get_request = new HttpGet(ip_addr);
        get_request.addHeader("accept", "application/json");

        HttpResponse response = http_client.execute(get_request);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }

        BufferedReader br = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));

        String line_data, output = "";
        while ((line_data = br.readLine()) != null) {
            output += line_data;
        }
        return output;
    }

    public String mapRequestTypeToAddress(RequestType req_type) {
        switch (req_type) {
            case PROJECT:
                return get_projects_addr;
            case SKILL:
                return get_skills_addr;
            default:
                return "";
        }
    }
}
