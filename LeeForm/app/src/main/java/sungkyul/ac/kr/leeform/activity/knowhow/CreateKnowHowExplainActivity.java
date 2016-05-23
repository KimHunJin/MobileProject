package sungkyul.ac.kr.leeform.activity.knowhow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import sungkyul.ac.kr.leeform.R;

public class CreateKnowHowExplainActivity extends AppCompatActivity {

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM= 1;
    private static final int CROP_FROM_IMAGE = 2;


    private Uri mImageCpatureUri;
    private String strAbsolutePath;
    private Button btnOk, btnCancel;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_know_how_explain);

        btnOk = (Button)findViewById(R.id.btnExplainOk);
        btnCancel = (Button)findViewById(R.id.btnExplainCancel);

        img = (ImageView)findViewById(R.id.imgCreateExplain);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                        startActivityForResult(i, PICK_FROM_ALBUM);
                    }
                };
                Dialog.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                };
                Dialog.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
    }

    // 카메라 촬영 후 이미지 가져오기
    public void doTakePhotoAction() {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCpatureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),url));

        it.putExtra(MediaStore.EXTRA_OUTPUT,mImageCpatureUri);
        startActivityForResult(it,PICK_FROM_CAMERA);
    }

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

        switch (requestCode) {
            case PICK_FROM_CAMERA : {
                break;
            }
            case PICK_FROM_ALBUM : {
                Uri selectedImg = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImg, filePathColumn,null,null,null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                break;
            }
        }

    }
}
