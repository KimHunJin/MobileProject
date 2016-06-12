package sungkyul.ac.kr.leeform.activity.knowhow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by user on 2016-06-08.
 */
public class WriteKnowHowActivity extends AppCompatActivity {
    String upLoadServerUri = StaticURL.IMAGE_UPLOAD_URL; // 파일 업로드를 위한 php url 이다.
    String URL = StaticURL.BASE_URL; // api 기본 베이스 주소
    String imageStorageUrl = StaticURL.IMAGE_URL; // 이미지 저장 위치
    int serverResponseCode = 0;
    String  categoryposition;
    String uploadFilePath; // 파일 경로
    String uploadFileName; // 파일 이름
    String writingUniqueKey;
    LinearLayout linearSell;
    String level;
    String check_sale;
    String selectedCategory;
    String selectedMakingTime;
    Spinner spnCategory, spnMakingTime;
    EditText edtName, edtCost, edtSellAmount, edtSellPrice, edtYoutubuCode;
    LinearLayout lineCreateView;
    ImageView imgOk, imgBack;
    TextView tvTitle;
    Toolbar toolbar;

    Button btnSellYes, btnSellNo, btnLevelHigh, btnLevelMiddle, btnLevelLow;


    String[] category;
    String[] makingTime;

    private GridView grvCreate;
    private CreateKnowHowGridAdapter cAdapter;

    private ArrayList<String> strUrl = new ArrayList<>(); //파일 경로리스트
    private ArrayList<String> strExplain = new ArrayList<>(); //파일 설명 리스트트

    ArrayList<CreateKnowHowItem> gridItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konw_how_write);

        layoutSetting();

        toolbar = (Toolbar)findViewById(R.id.toolbarBack);
        toolbar.setContentInsetsAbsolute(0,0);

        tvTitle.setText("노하우 작성");

        //기본값
        btnSellNo.setSelected(true);
        btnLevelHigh.setSelected(true);


        ArrayAdapter<String> spinnerCategoryAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_spinner, category);
        ArrayAdapter<String> spinnerMakingTimeAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_spinner, makingTime);

        spinnerCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMakingTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnCategory.setAdapter(spinnerCategoryAdapter); //스피너에 adapter 설정
        spnMakingTime.setAdapter(spinnerMakingTimeAdapter);

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) spnCategory.getSelectedItem().toString();
                //categoryposition = (int) spnCategory.getSelectedItemPosition();
                //categoryposition="거울";
                selectedCategory = str;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spnMakingTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) spnMakingTime.getSelectedItem().toString();

                selectedMakingTime = str;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cAdapter = new CreateKnowHowGridAdapter(getApplicationContext(), R.layout.item_grid_create, gridItems);
        grvCreate.setAdapter(cAdapter);

        init();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("strUrl", strUrl.size() + "");

               check();

                if(check()==false){
                    setting();
                    saveKnowGetKey();
                    finish();
                }
            }
        });

        grvCreate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == gridItems.size() - 1) {
                    Intent intent = new Intent(getApplicationContext(), CreateKnowHowExplainActivity.class);
                    intent.putExtra("image", strUrl);
                    intent.putExtra("explain", strExplain);
                    startActivityForResult(intent, 1000);
                }
            }
        });

        btnSellYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSellYes.setSelected(true);
                btnSellNo.setSelected(false);
                linearSell.setVisibility(View.VISIBLE);
                check_sale = "1";
            }
        });
        btnSellNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSellYes.setSelected(false);
                btnSellNo.setSelected(true);
                linearSell.setVisibility(View.INVISIBLE);
                check_sale="0";

            }
        });
        btnLevelHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLevelHigh.setSelected(true);
                btnLevelMiddle.setSelected(false);
                btnLevelLow.setSelected(false);
                level = btnLevelHigh.getText().toString();
            }
        });
        btnLevelMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLevelHigh.setSelected(false);
                btnLevelMiddle.setSelected(true);
                btnLevelLow.setSelected(false);
                level = btnLevelMiddle.getText().toString();
            }
        });
        btnLevelLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLevelHigh.setSelected(false);
                btnLevelMiddle.setSelected(false);
                btnLevelLow.setSelected(true);
                level = btnLevelLow.getText().toString();
            }
        });

    }

    /**
     * 기본값 셋팅
     */
  private void setting() {

        check_sale = "0"; //판매NO
        level=btnLevelHigh.getText().toString(); //레벨 상
        edtSellAmount.setText("0"); //판매수량 0
        edtSellPrice.setText("0"); //판매가격0
        edtYoutubuCode.setText("0"); //youtubucode
        edtCost.setText("0");

    }

   private boolean check(){
        if(edtName.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"제목을 입력해주세요",Toast.LENGTH_SHORT).show();
            return  true;
        }
       /* if(edtYoutubuCode.getText().toString().equals("")){
           Toast.makeText(getApplicationContext(),"유튜브코드를 입력해주세요",Toast.LENGTH_SHORT).show();
           return true;
       }*/
       if(edtCost.getText().toString().equals("")){
           Toast.makeText(getApplicationContext(),"소모비용을 입력해주세요",Toast.LENGTH_SHORT).show();
           return true;
       }
       if(check_sale.equals("1")){
           if(edtSellAmount.getText().toString().equals("")){
               Toast.makeText(getApplicationContext(),"판매수량을 입력해주세요",Toast.LENGTH_SHORT).show();
               return true;
           }
           if(edtSellPrice.getText().toString().equals("")){
               Toast.makeText(getApplicationContext(),"판매 가격을 입력해주세요",Toast.LENGTH_SHORT).show();
               return  true;
           }

       }
       if(spnCategory.getSelectedItem().toString().equals("카테고리")){
           Toast.makeText(getApplicationContext(),"카테고리를 선택해주세요",Toast.LENGTH_SHORT).show();
           return true;
       }
       if(spnMakingTime.getSelectedItem().toString().equals("소요시간")){
           Toast.makeText(getApplicationContext(),"소요시간을 선택해주세요",Toast.LENGTH_SHORT).show();
           return true;
       }
        return false;
    }

    private void layoutSetting() {
        tvTitle = (TextView) findViewById(R.id.txtToolBarTitle);
        imgOk = (ImageView) findViewById(R.id.imgOk);
        imgBack = (ImageView) findViewById(R.id.imgBackOk);
        edtName = (EditText) findViewById(R.id.edtName);
        lineCreateView = (LinearLayout) findViewById(R.id.lineImgAdd);
        grvCreate = (GridView) findViewById(R.id.grdImgView);

        spnCategory = (Spinner) findViewById(R.id.spnKnowCategory);
        spnMakingTime = (Spinner) findViewById(R.id.spnMakingTime);

        category = getResources().getStringArray(R.array.category);
        makingTime = getResources().getStringArray(R.array.makingTime);

        linearSell = (LinearLayout) findViewById(R.id.linearSell);
        edtCost = (EditText) findViewById(R.id.edtCost);
        edtYoutubuCode = (EditText) findViewById(R.id.edtYoutubuCode);
        btnSellYes = (Button) findViewById(R.id.btnSellYes);
        btnSellNo = (Button) findViewById(R.id.btnSellNo);
        btnLevelHigh = (Button) findViewById(R.id.btnLevelHigh);
        btnLevelMiddle = (Button) findViewById(R.id.btnLevelMiddle);
        btnLevelLow = (Button) findViewById(R.id.btnLevelLow);

        edtSellAmount = (EditText) findViewById(R.id.edtSellAmount);
        edtSellPrice = (EditText) findViewById(R.id.edtSellPrice);

    }

    private void init() {
        gridItems.clear();
        for (int i = 0; i < strUrl.size(); i++) {
            gridItems.add(new CreateKnowHowItem(i, strUrl.get(i), strExplain.get(i)));
            Log.e("strUrl",strUrl.get(i));
        }
        //사진 추가한 후 +이미지가 뜨도록 +이미지를 GridView에 추가
        gridItems.add(new CreateKnowHowItem(gridItems.size(), R.drawable.plus, "클릭"));
        cAdapter.notifyDataSetChanged();
    }

    private void saveKnowGetKey() {
        if(edtName.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"제목입력",Toast.LENGTH_SHORT).show();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ConnectService connectService = retrofit.create(ConnectService.class);
        String userUniqueKey = SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key");
        Log.e("userUniqueKey", userUniqueKey);
        Map<String, String> data = new HashMap<>();

        data.put("user_unique_key", userUniqueKey);

        // data.put("writing_category_key", "1"); // 변경 필요
        data.put("category_name",selectedCategory);
        data.put("writing_name", edtName.getText().toString());
        data.put("check_video", "1"); // 변경 필요
        // 지워 data.put("video_url", edtVideoUrl.getText().toString());
        data.put("check_sales", check_sale); //check_sale
        data.put("video_url", edtYoutubuCode.getText().toString()); //유튜브코드
        data.put("cost", edtCost.getText().toString()); // 변경 필요
        data.put("making_time", selectedMakingTime);
        data.put("level", level); // 변경 필요
        data.put("writing_explanation", "수고가 많습니다."); // 변경 필요
        data.put("amount", edtSellAmount.getText().toString());
        data.put("price",edtSellPrice.getText().toString());
        //   data.put("sellprice",edtSellPrice.getText().toString());// 변경 필요
        Log.e("log",userUniqueKey+"");
        Log.e("log",selectedCategory);
        Log.e("log",edtName.getText().toString()+"");
        Log.e("log",check_sale+"");
        Log.e("log",edtYoutubuCode.getText().toString()+"");
        Log.e("log",edtCost.getText().toString()+"");
        Log.e("log",selectedMakingTime+"");
        Log.e("log",level+"");
        Log.e("log",edtSellAmount.getText().toString()+"");

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                //URL과 노하우 설명 가져오기
                Log.e("resultCode",resultCode+"");
                Log.e("requestCode",requestCode+"");
                strUrl = data.getStringArrayListExtra("image");
                strExplain = data.getStringArrayListExtra("explain");
                init();
            }
        }
    }

    public int uploadFile(String sourceFileUri) {
        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 10 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {

            Log.e("uploadFile", "Source File not exist :" + sourceFileUri);

            return 0;

        } else {
            try {

                // StaticURL 생성
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                java.net.URL url = new java.net.URL(upLoadServerUri);

                // HTTP를 열고 StaticURL 연결
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
}
