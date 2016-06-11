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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kakao.usermgmt.response.model.User;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.dao.ConnectService;
import sungkyul.ac.kr.leeform.dto.CommunityDetailBean;
import sungkyul.ac.kr.leeform.dto.KnowHowWritingBean;
import sungkyul.ac.kr.leeform.dto.UserBean;
import sungkyul.ac.kr.leeform.items.ReplyItem;
import sungkyul.ac.kr.leeform.utils.DownloadImageTask;
import sungkyul.ac.kr.leeform.utils.SaveDataMemberInfo;
import sungkyul.ac.kr.leeform.utils.StaticURL;
import sungkyul.ac.kr.leeform.utils.TimeTransForm;

/**
 * Created by user on 2016-06-10.
 */
public class MyPageModifyActivity extends AppCompatActivity{
    EditText edtUserName,edtAddress,edtBankName,edtBankNumber,edtPhoneNumber;
    ImageView image,imgOk;
    String URL = StaticURL.BASE_URL;
    private Uri mImageCpatureUri;
    private String absoultePath;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_IMAGE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypagemodify);


        layoutSetting();
        getUserDetails();
        imgOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserDetails();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
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

//                Picasso.with(getApplicationContext()).load(mImageCpatureUri).resize(340,260).centerCrop().into(img);
                absoultePath = getPath(mImageCpatureUri);
                Picasso.with(getApplicationContext()).load(mImageCpatureUri).resize(340,260).centerCrop().into(image);
                Log.e("absolutePath",absoultePath);

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
    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
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

    private void layoutSetting() {
        edtUserName=(EditText)findViewById(R.id.edtUserName);
        edtAddress=(EditText)findViewById(R.id.edtAddress);
        edtBankName=(EditText)findViewById(R.id.edtBankName);
        edtBankNumber=(EditText)findViewById(R.id.edtBankNumber);
        edtPhoneNumber=(EditText)findViewById(R.id.edtPhoneNumber);
        imgOk=(ImageView)findViewById(R.id.imgOk);
        image=(ImageView)findViewById(R.id.imgMypageUser);

        Intent intent=getIntent();
        new DownloadImageTask(image).execute(intent.getExtras().getString("imageurl"));

    }


   private void getUserDetails(){

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
                    String accountNumber= decodedResponse.getMyinfo_detail().get(0).getAccount_number();
                    String address=decodedResponse.getMyinfo_detail().get(0).getAddress();
                    String bankName=decodedResponse.getMyinfo_detail().get(0).getBank_name();
                    String name=decodedResponse.getMyinfo_detail().get(0).getName();
                    String phoneNumber= decodedResponse.getMyinfo_detail().get(0).getPhone_number();
                    edtUserName.setText(name);
                    edtAddress.setText(address);
                    edtBankName.setText(bankName);
                    edtBankNumber.setText(accountNumber);
                    edtPhoneNumber.setText(phoneNumber);

                                  }

                @Override
                public void onFailure(Call<UserBean> call, Throwable t) {
                    Log.e("failure", t.getMessage());
                }
            });
   }

    private void setUserDetails(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ConnectService connectService = retrofit.create(ConnectService.class);
        String userUniqueKey = SaveDataMemberInfo.getAppPreferences(getApplicationContext(), "user_key");
        Map<String, String> data = new HashMap<>();

        data.put("user_unique_key", userUniqueKey);
        data.put("name", edtUserName.getText().toString());
        data.put("address", edtAddress.getText().toString());
        data.put("bank_name", edtBankName.getText().toString());
        data.put("account_number", edtBankNumber.getText().toString());
        data.put("phone_number", edtPhoneNumber.getText().toString());
        data.put("",absoultePath);
        final Call<UserBean> call = connectService.setUserDetail(data);

        call.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                UserBean decode = response.body();
                Log.e("ModifyErr",decode.getErr()+"");

            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }


}
