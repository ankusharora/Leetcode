package Envoy;

import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class BasicWebScrapper {
    public static void main(String[] args) {
        String url = "http://example.com";  // Change this to the target website
        try {
            String htmlContent = fetchHTML(url);
            parseHTML(htmlContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Step 1: Fetch HTML from the given URL
    public static String fetchHTML(String urlString) throws IOException {
        StringBuilder content = new StringBuilder();
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    // Step 2: Parse the HTML content
    public static void parseHTML(String html) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Convert the HTML string to an InputStream and parse it
        InputSource is = new InputSource(new StringReader(html));
        Document doc = builder.parse(is);

        // Step 3: Extract the title (or any other tag you need)
        NodeList titleList = doc.getElementsByTagName("title");
        if (titleList.getLength() > 0) {
            System.out.println("Page Title: " + titleList.item(0).getTextContent());
        } else {
            System.out.println("No title found.");
        }
    }
}

