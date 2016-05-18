package sungkyul.ac.kr.leeform.activity.community;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import sungkyul.ac.kr.leeform.R;

/**
 * Created by user on 2016-05-16.
 */

public class CommunityCreateActivity extends AppCompatActivity {
    ImageView camera,album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_create);

        camera=(ImageView)findViewById(R.id.imgCamera);
        album=(ImageView) findViewById(R.id.imgAlbum);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,1);
            }
        });

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent albumIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
                startActivity(albumIntent);
            }
        });

    }
}
