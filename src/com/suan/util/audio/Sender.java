package com.suan.util.audio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Sender {

	public static void sendMMS(final AudioSendCallBack mmsBack) {

		final Handler audioSendHandler = new Handler() {

			// 子类必须重写此方法,接受数据
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub

				super.handleMessage(msg);
				// 此处可以更新UI
				String resulString = (String) msg.obj;
				mmsBack.onBack(resulString);

			}
		};

		new Thread() {
			public void run() {
				Log.e("load", "start post");
				String resultString = sendAudio();
				if (resultString != null) {

					Message message = new Message();
					message.obj = resultString;

					audioSendHandler.sendMessage(message);

				}

			}

			public String sendAudio() {

				/* 声明网址字符串 */
				String uriAPI = "http://192.168.1.165";
				/* 建立HTTP Post联机 */
				HttpPost httpRequest = new HttpPost(uriAPI);

				JSONObject jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < 50; i++) {
					String nowValue = RecordThread.record[i][0] + "|"
							+ RecordThread.record[i][1];
					jsonArray.put(nowValue);
				}
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				try {
					jsonObject.put("recordArray", jsonArray);

					params.add(new BasicNameValuePair("content", jsonObject
							.toString()));

				} catch (Exception exception) {
					Log.e("arrayError", exception+"");
				}
				try {

					/* 发出HTTP request */
					httpRequest.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));
					/* 取得HTTP response */
					HttpResponse httpResponse = new DefaultHttpClient()
							.execute(httpRequest);

					Log.e("sendaudio", httpResponse.getStatusLine() + "");

					/* 若状态码为200 ok */
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						/* 取出响应字符串 */
						String strResult = EntityUtils.toString(httpResponse
								.getEntity());
						Log.e("sendaudio ok", strResult);
						return strResult;
					} else {
						Log.e("errorcode", httpResponse.getStatusLine()
								.toString());
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
					Log.e("error1", e + "");
				} catch (IOException e) {
					e.printStackTrace();
					Log.e("error2", e + "");
				} catch (Exception e) {
					e.printStackTrace();
					Log.e("error3", e + "");
				}

				return null;

			}

		}.start();
	}

	public interface AudioSendCallBack {
		public void onBack(String resultString);
	}
}
