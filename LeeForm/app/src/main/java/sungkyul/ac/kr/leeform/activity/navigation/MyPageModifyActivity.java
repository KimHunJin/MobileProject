package sungkyul.ac.kr.leeform.activity.navigation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.UserBean;
import sungkyul.ac.kr.leeform.dto.UserModifyBean;
import sungkyul.ac.kr.leeform.utils.DownloadImageTask;
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;

/**
 * Created by user on 2016-06-10.
 */
public class MyPageModifyActivity extends AppCompatActivity {
    String upLoadServerUri = StaticURL.IMAGE_UPLOAD_URL; // 파일 업로드를 위한 php url 이다.
    String imageStorageUrl = StaticURL.IMAGE_URL; // 이미지 저장 위치
    int serverResponseCode = 0;
    EditText edtUserName, edtAddress, edtBankName, edtBankNumber, edtPhoneNumber, edtAccountName;
    ImageView image, imgOk, imgBack;
    String URL = StaticURL.BASE_URL;
    private Toolbar toolbar;
    private TextView txtToolbarTitle;
    ;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;
    private Uri mImageCpatureUri;
    private String absoultePath;
    String uploadFilePath; // 파일 경로
    String uploadFileName; // 파일 이름
    private ArrayList<String> strUrl = new ArrayList<>(); //파일 경로리스트
    private ArrayList<String> strExplain = new ArrayList<>(); //파일 설명 리스트트

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypagemodify);

        layoutSetting();
        getUserDetails(); //값가져오기

        toolbar.setContentInsetsAbsolute(0, 0);
        txtToolbarTitle.setText("내 정보 수정");

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if (absoultePath == null) {
                        setUserDetails(uploadFileName);
                        finish();
                    } else {
                        File file = new File(absoultePath);  // 파일을 만듬
                        uploadFileName = file.getName(); // 파일의 이름 추출
                        uploadFilePath = file.getPath(); // 파일의 경로 추출

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                uploadFile(uploadFilePath); // 파일 업로드
                            }
                        }).start();

                        setUserDetails(uploadFileName);
                        finish();
                    }
                }
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };
                Dialog.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };
                Dialog.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                new AlertDialog.Builder(MyPageModifyActivity.this)
                        .setTitle("이미지 선택")
                        .setPositiveButton("취소", cancelListener)
                        .setNeutralButton("사진촬영", cameraListener)
                        .setNegativeButton("앨범선택", albumListener)
                        .show();
            }
        });
    }

    /**
     * 레이아웃 셋팅
     */
    private void layoutSetting() {
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtAccountName = (EditText) findViewById(R.id.edtAccountName);
        edtBankName = (EditText) findViewById(R.id.edtBankName);
        edtBankNumber = (EditText) findViewById(R.id.edtBankNumber);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        imgOk = (ImageView) findViewById(R.id.imgOk);
        image = (ImageView) findViewById(R.id.imgMypageUser);
        imgBack = (ImageView) findViewById(R.id.imgBackOk);
        toolbar = (Toolbar) findViewById(R.id.toolbarBack);
        txtToolbarTitle = (TextView) findViewById(R.id.txtToolBarTitle);
    }

    // 카메라 촬영 후 이미지 가져오기
    public void doTakePhotoAction() {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg"; // 임시 경로 생성
        mImageCpatureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url)); // 임시 파일 생성

        it.putExtra(MediaStore.EXTRA_OUTPUT, mImageCpatureUri);
        startActivityForResult(it, PICK_FROM_CAMERA);
    }

    // 앨범에서 이미지 가져오기
    public void doTakeAlbumAction() {
        Intent it = new Intent(Intent.ACTION_PICK);
        it.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(it, PICK_FROM_ALBUM);
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            strUrl = data.getStringArrayListExtra("image");
            strExplain = data.getStringArrayListExtra("explain");
            return;
        }
        // 받아온 값이 있으면
        switch (requestCode) {
            // 앨범에서 가져오는 경우 카메라에서 가져오는 거와 같은 기능을 하기에 break 없이 진행
            case PICK_FROM_ALBUM: {
                mImageCpatureUri = data.getData();
            }
            // 카메라에서 가져올 경우
            case PICK_FROM_CAMERA: {
                // 이미지 크랍
                absoultePath = getPath(mImageCpatureUri);
                Picasso.with(getApplicationContext()).load(mImageCpatureUri).resize(340, 260).centerCrop().into(image);
                Log.e("absolutePath", absoultePath);
                break;
            }
            case CROP_FROM_IMAGE: {
                if (resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                // 경로에 이미지 저장 (임시 파일)
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LeeForm/" + System.currentTimeMillis() + ".jpg";

                if (extras != null) {
                    Log.e("extras", extras + "");
                    Bitmap photo = extras.getParcelable("data"); // 크랍한 이미지를 가져옴
                    Log.e("photo", photo + "");
                    image.setImageBitmap(photo); // 이미지 로드

                    storeCropImage(photo, filePath); // 크랍한 이미지 저장, 안하게 되면 이미지를 가져오지 못함
                    absoultePath = filePath; // 경로 반환
                    break;
                }
                File f = new File(mImageCpatureUri.getPath()); // 임시 파일 삭제
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }

    // 크랍한 이미지 저장
    private void storeCropImage(Bitmap bitmap, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/LeeForm";
        File directory_LeeForm = new File(dirPath);

        if (!directory_LeeForm.exists()) {
            directory_LeeForm.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    /**
     * 사용자의 정보를 가져와서
     * ImageView, EditText에 뿌려주기
     */
    private void getUserDetails() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        String key = SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key");
        final Call<UserBean> call = connectService.getUserDetail(key);

        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                UserBean decodedResponse = response.body();
                String accountNumber = decodedResponse.getMyinfo_detail().get(0).getAccount_number();
                String address = decodedResponse.getMyinfo_detail().get(0).getAddress();
                String bankName = decodedResponse.getMyinfo_detail().get(0).getBank_name();
                String name = decodedResponse.getMyinfo_detail().get(0).getName();
                String phoneNumber = decodedResponse.getMyinfo_detail().get(0).getPhone_number();
                String accountName = decodedResponse.getMyinfo_detail().get(0).getAccount_name();

                edtUserName.setText(name);
                edtAddress.setText(address);
                edtBankName.setText(bankName);
                edtBankNumber.setText(accountNumber);
                edtPhoneNumber.setText(phoneNumber);
                edtAccountName.setText(accountName);
                new DownloadImageTask(image).execute(decodedResponse.getMyinfo_detail().get(0).getImg());
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
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

    /**
     * 사용자가 입력한 값으로 수정하기
     *
     * @param fileName
     */
    private void setUserDetails(String fileName) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        String userUniqueKey = SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key");
        Map<String, String> data = new HashMap<>();
        //이미지변경을 안했을 때
        if (fileName == null) {
            Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_SHORT).show();
            data.put("user_unique_key", userUniqueKey);
            data.put("name", edtUserName.getText().toString());
            data.put("address", edtAddress.getText().toString());
            data.put("bank_name", edtBankName.getText().toString());
            data.put("account_number", edtBankNumber.getText().toString());
            data.put("account_name", edtAccountName.getText().toString());
            data.put("phone_number", edtPhoneNumber.getText().toString());

            Intent intent = getIntent();
            //판매자 등록할 때 은행명, 계좌번호, 계좌명 RegistSellerActivity로 보내기
            intent.putExtra("bank_name", edtBankName.getText().toString());
            intent.putExtra("account_number", edtBankNumber.getText().toString());
            intent.putExtra("account_name", edtAccountName.getText().toString());
            setResult(RESULT_OK, intent);

            //이미지 변경을 했을 때
        } else {
            data.put("user_unique_key", userUniqueKey);
            data.put("name", edtUserName.getText().toString());
            data.put("address", edtAddress.getText().toString());
            data.put("bank_name", edtBankName.getText().toString());
            data.put("account_number", edtBankNumber.getText().toString());
            data.put("phone_number", edtPhoneNumber.getText().toString());
            data.put("img", imageStorageUrl + fileName);
            data.put("account_name", edtAccountName.getText().toString());

        }
        final Call<UserModifyBean> call = connectService.setUserDetail(data);

        call.enqueue(new Callback<UserModifyBean>() {
            @Override
            public void onResponse(Call<UserModifyBean> call, Response<UserModifyBean> response) {
                UserModifyBean decode = response.body();
                Log.e("ModifyErr", decode.getErr() + "");
                if (decode.getErr().equals("0")) {
                    Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserModifyBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }
}