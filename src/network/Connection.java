package network;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class Connection {
	private HttpURLConnection httpURLConnection;

	public Connection(String httpURL) throws Exception {
		httpURLConnection = getConnection(httpURL);
	}

	private HttpURLConnection getConnection(String connectionURL) throws Exception {
		URL url = new URL(connectionURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		//conn.setRequestProperty("Accept-Encoding", "identity");
		conn.connect();
		return conn;
	}

	public String doPost(String postParameter) throws Exception {
		OutputStreamWriter osw = new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8");
		osw.write(postParameter);
		osw.flush();
		osw.close();
		InputStream inputStream = httpURLConnection.getInputStream();
		int len = httpURLConnection.getContentLength();
		if (len <= 0)
			len = inputStream.available();
		byte[] bytes = new byte[len];
		inputStream.read(bytes);
		return new String(bytes, "UTF-8");
	}

	public void close() {
		httpURLConnection.disconnect();
	}
}
