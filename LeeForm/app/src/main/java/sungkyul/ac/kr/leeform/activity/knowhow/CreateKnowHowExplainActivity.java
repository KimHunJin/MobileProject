package sungkyul.ac.kr.leeform.activity.knowhow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import sungkyul.ac.kr.leeform.R;
import sungkyul.ac.kr.leeform.items.CreateKnowHowItem;
import sungkyul.ac.kr.leeform.items.MaterialGridItem;

public class CreateKnowHowExplainActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM= 1;
    private static final int CROP_FROM_IMAGE = 2;

    private String absoultePath;

    private Uri mImageCpatureUri;
    private String strContents;
    private Button btnOk, btnCancel;
    private EditText edtContents;
    ImageView img;

    ArrayList<String> strUrl;
    ArrayList<String> strExplain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_know_how_explain);

        Intent it = getIntent();
        strUrl = it.getStringArrayListExtra("image"); // 이미지 배열 저장을 위한 리스트
        strExplain = it.getStringArrayListExtra("explain"); // 설명 저장을 위한 리스트

        btnOk = (Button)findViewById(R.id.btnExplainOk);
        btnCancel = (Button)findViewById(R.id.btnExplainCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                    추가작업
                    취소 했을 시 저장이 된 파일이 있다면 삭제
                 */
                finish();
            }
        });

        edtContents = (EditText)findViewById(R.id.edtCreateExplain);

        // 이미지 클릭 했을 때
        img = (ImageView)findViewById(R.id.imgCreateExplain);
        img.setOnClickListener(new View.OnClickListener() {
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
                new AlertDialog.Builder(CreateKnowHowExplainActivity.this)
                        .setTitle("이미지 선택")
                        .setPositiveButton("취소",cancelListener)
                        .setNeutralButton("사진촬영",cameraListener)
                        .setNegativeButton("앨범선택",albumListener)
                        .show();
            }
        });

        // 버튼 클릭 했을 떄
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strContents = edtContents.getText().toString();
                Log.e("picturePath",absoultePath + " ");
                Log.e("contents",strContents+ " ");
                if(!absoultePath.equals("")&&!strContents.equals("")) {
                    strUrl.add(absoultePath);
                    strExplain.add(strContents);
                    Intent i = new Intent();
                    i.putExtra("image",strUrl);
                    i.putExtra("explain",strExplain);
                    setResult(RESULT_OK,i);
                    finish();
                }
            }
        });
    }

    // 카메라 촬영 후 이미지 가져오기
    public void doTakePhotoAction() {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg"; // 임시 경로 생성
        mImageCpatureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),url)); // 임시 파일 생성

        it.putExtra(MediaStore.EXTRA_OUTPUT,mImageCpatureUri);
        startActivityForResult(it,PICK_FROM_CAMERA);
    }

    // 앨범에서 이미지 가져오기
    public void doTakeAlbumAction() {
        Intent it = new Intent(Intent.ACTION_PICK);
        it.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(it,PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) {
            return;
        }

        // 받아온 값이 있으면
        switch (requestCode) {
            // 앨범에서 가져오는 경우 카메라에서 가져오는 거와 같은 기능을 하기에 break 없이 진행
            case PICK_FROM_ALBUM : {
                mImageCpatureUri = data.getData();
            }
            // 카메라에서 가져올 경우
            case PICK_FROM_CAMERA : {
                // 이미지 크랍
                Intent it = new Intent("com.android.camera.action.CROP");
                it.setDataAndType(mImageCpatureUri, "image/*");

                // 외부에 보여줄 크기를 200으로 설정
                it.putExtra("outputX",200);
                it.putExtra("outputY",200);

                // 크랍할 비율을 1 : 1로 설정 (가로 : 세로)
                it.putExtra("aspectX",1);
                it.putExtra("aspectY",1);

                it.putExtra("scale",true);
                it.putExtra("return-data",true);
                startActivityForResult(it,CROP_FROM_IMAGE);
                break;
            }
            case CROP_FROM_IMAGE : {
                if(resultCode != RESULT_OK) {
                    return;
                }

                final Bundle extras = data.getExtras();

                // 경로에 이미지 저장 (임시 파일)
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/LeeForm/"+ System.currentTimeMillis()+".jpg";

                if(extras!=null) {
                    Log.e("extras",extras+"");
                    Bitmap photo = extras.getParcelable("data"); // 크랍한 이미지를 가져옴
                    Log.e("photo",photo+"");
                    img.setImageBitmap(photo); // 이미지 로드

                    storeCropImage(photo, filePath); // 크랍한 이미지 저장, 안하게 되면 이미지를 가져오지 못함
                    absoultePath = filePath; // 경로 반환
                    break;
                }

                File f = new File(mImageCpatureUri.getPath()); // 임시 파일 삭제
                if(f.exists()) {
                    f.delete();
                }
            }

        }

    }

    // 크랍한 이미지 저장
    private void storeCropImage(Bitmap bitmap, String filePath) {
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/LeeForm";
        File directory_LeeForm = new File(dirPath);

        if(!directory_LeeForm.exists()) {
            directory_LeeForm.mkdir();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);

            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
