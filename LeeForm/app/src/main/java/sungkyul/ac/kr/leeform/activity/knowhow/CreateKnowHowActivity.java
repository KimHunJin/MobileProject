package sungkyul.ac.kr.leeform.activity.knowhow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.adapter.CreateKnowHowGridAdapter;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.KnowHowWritingBean;
import sungkyul.ac.kr.leeform.items.CreateKnowHowItem;
import sungkyul.ac.kr.leeform.utils.Util;

public class CreateKnowHowActivity extends AppCompatActivity {

    String upLoadServerUri = "http://14.63.196.255/api/upload.php"; // 파일 업로드를 위한 php url 이다.
    String URL = "http://14.63.196.255/api/"; // api 기본 베이스 주소
    String imageStorageUrl = "http://14.63.196.255/image/"; // 이미지 저장 위치
    int serverResponseCode = 0;

    String uploadFilePath; // 파일 경로
    String uploadFileName; // 파일 이름
    String writingUniqueKey;

    EditText edtName, edtVideoUrl, edtMakingTime;

    TextView txtOk;
    LinearLayout lineCreateView;

    private GridView grvCreate;
    private CreateKnowHowGridAdapter cAdapter;

    private ArrayList<String> strUrl = new ArrayList<>(); // 파일 경로 리스트
    private ArrayList<String> strExplain = new ArrayList<>(); // 파일 설명 리스트

    ArrayList<CreateKnowHowItem> gridItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_know_how);

        //툴바 텍스트 변경
        TextView tv = (TextView) findViewById(R.id.txtToolBarTitle);
        tv.setText("노하우 작성");

        layoutSetting();

        txtOk = (TextView) findViewById(R.id.tvOk);
        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("strUrl", strUrl.size() + "");

                saveKnowGetKey();

                finish();
            }
        });

        //뒤로가기 버튼
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lineCreateView = (LinearLayout) findViewById(R.id.lineCreateView);

        grvCreate = (GridView) findViewById(R.id.grvCreateView);
        cAdapter = new CreateKnowHowGridAdapter(getApplicationContext(), R.layout.item_grid_create, gridItems);
        grvCreate.setAdapter(cAdapter);

        init();

        grvCreate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == gridItems.size() - 1) {
                    Intent it = new Intent(getApplicationContext(), CreateKnowHowExplainActivity.class);
                    it.putExtra("image", strUrl);
                    it.putExtra("explain", strExplain);
                    startActivityForResult(it, 1000);
                }
            }
        });
    }

    void layoutSetting() {
        edtName = (EditText) findViewById(R.id.edtKnowHowName);
        edtMakingTime = (EditText) findViewById(R.id.edtMakingTime);
        edtVideoUrl = (EditText) findViewById(R.id.edtVideoUrl);
    }

    void init() {
        gridItems.clear();
        for (int i = 0; i < strUrl.size(); i++) {
            gridItems.add(new CreateKnowHowItem(i, strUrl.get(i), strExplain.get(i)));
        }
        gridItems.add(new CreateKnowHowItem(gridItems.size(), R.drawable.ic_control_point_black_36dp, "클릭"));
        cAdapter.notifyDataSetChanged();
    }

    // 액티비티로 돌아왔을 때
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                strUrl = data.getStringArrayListExtra("image");
                strExplain = data.getStringArrayListExtra("explain");
                init();
            }
        }
    }

    // 이미지 올리는 메서드
    public int uploadFile(String sourceFileUri) {

        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            Log.e("uploadFile", "Source File not exist :" + sourceFileUri);

            return 0;

        } else {
            try {

                // URL 생성
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                // HTTP를 열고 URL 연결
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);

                // 버퍼 생성
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // 버퍼를 사용하여 파일을 읽음
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // multipart 방식으로 파일 전송
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // 서버로부터 받은 response 값
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();
                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {

                    runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(getApplicationContext(), "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "MalformedURLException",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {

                e.printStackTrace();

            }
            return serverResponseCode;

        } // End else block
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    // retrofit을 이용하여 디비 접속
    private void leeformParsing(String fileName, String contents, int priority, String writing_unique_key) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.e("imgUrl", imageStorageUrl + fileName);
        Log.e("contents", contents);
        Log.e("priority", priority + "");
        Log.e("writing_unique_key", writing_unique_key);

        // get 방식 파라미터 전송
        Map<String, String> data = new HashMap<>();
        data.put("imgUrl", imageStorageUrl + fileName);
        data.put("contents", contents);
        data.put("priority", priority + "");
        data.put("writing_unique_key", writing_unique_key);
        data.put("time", System.currentTimeMillis() + "");

        ConnectService connectService = retrofit.create(ConnectService.class);
        Call<KnowHowWritingBean> call = connectService.setKnowHow(data);
        call.enqueue(new Callback<KnowHowWritingBean>() {
            @Override
            public void onResponse(Call<KnowHowWritingBean> call, Response<KnowHowWritingBean> response) {
                KnowHowWritingBean des = response.body();
                Log.e("success", "success");
                Log.e("err", des.getErr());
            }

            @Override
            public void onFailure(Call<KnowHowWritingBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

    /**
     * parameter
     * user_unique_key
     * writing_category_key
     * writing_name
     * check_video
     * video_url
     * making_time
     * price
     * level
     * writing_explanation
     * amount
     */
    private void saveKnowGetKey() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConnectService connectService = retrofit.create(ConnectService.class);
        String userUniqueKey = Util.getAppPreferences(getApplicationContext(), "user_key");
        Log.e("userUniqueKey", userUniqueKey);
        Map<String, String> data = new HashMap<>();

        data.put("user_unique_key", userUniqueKey);
        data.put("writing_category_key", "1"); // 변경 필요
        data.put("writing_name", edtName.getText().toString());
        data.put("check_video", "1"); // 변경 필요
        data.put("video_url", edtVideoUrl.getText().toString());
        data.put("price", "10000"); // 변경 필요
        data.put("making_time", edtMakingTime.getText().toString());
        data.put("level", "상"); // 변경 필요
        data.put("writing_explanation", "수고가 많습니다."); // 변경 필요
        data.put("amount", "1"); // 변경 필요

        Call<KnowHowWritingBean> call = connectService.setKnowGetKey(data);
        call.enqueue(new Callback<KnowHowWritingBean>() {
            @Override
            public void onResponse(Call<KnowHowWritingBean> call, Response<KnowHowWritingBean> response) {
                KnowHowWritingBean decode = response.body();
                Log.e("err", decode.getErr());
                writingUniqueKey = decode.getWriting_unique_key().get(0).getWriting_unique_key();
                Log.e("writing_key", writingUniqueKey);

                for (int i = 0; i < strUrl.size(); i++) { // 생성한 노하우의 수만큼 반복
                    File file = new File(strUrl.get(i));  // 파일을 만듬
                    final String contents = strExplain.get(i); // 설명 추출
                    uploadFileName = file.getName(); // 파일의 이름 추출
                    uploadFilePath = file.getPath(); // 파일의 경로 추출
                    Log.e("file Name", file.getName());

                    // writing_unique_key를 가져오는 함수가 필요하다.

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            uploadFile(uploadFilePath); // 파일 업로드
                        }
                    }).start();
                    // 파일이미지, 설명, 우선순위, writing_unique_key
                    leeformParsing(uploadFileName, contents, (i + 1), writingUniqueKey); // 데이터베이스에 저장
                }
            }

            @Override
            public void onFailure(Call<KnowHowWritingBean> call, Throwable t) {
                Log.e("Failure Writing Knowhow", t.getMessage());
            }
        });
    }
}
