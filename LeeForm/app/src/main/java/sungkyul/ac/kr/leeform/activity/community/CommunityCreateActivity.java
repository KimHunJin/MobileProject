<<<<<<< HEAD:LeeForm/app/src/main/java/sungkyul/ac/kr/leeform/activity/community/CommunityCreateActivity.java
package sungkyul.ac.kr.leeform.activity.community;
=======
package sungkyul.ac.kr.leeform.community;
import android.content.Intent;
import android.media.Image;
>>>>>>> 6d7fd513157f8ca15e7e86a7d659af9f775490d0:LeeForm/app/src/main/java/sungkyul/ac/kr/leeform/community/CommunityCreateActivity.java
import android.os.Bundle;
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

            }
        });

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
