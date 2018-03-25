package org.nus.trailblaze.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.github.barteksc.pdfviewer.PDFView;

import org.nus.trailblaze.R;
import org.nus.trailblaze.tasks.AsyncTaskLoadImage;
import org.nus.trailblaze.tasks.AsyncTaskReadTextFile;
import org.nus.trailblaze.models.ContributedItem;

import java.io.IOException;

public class TrailBlazeItemViewerActivity extends AppCompatActivity {

    private static ProgressDialog progressDialog;
    private TextView tvDescription;
    private TextView tv_text_content;
    private ImageView ivItemImage;
    private VideoView vvItemVideo;
    private WebView wvPdfViewer;
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_blaze_item_viewer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDescription = (TextView) findViewById(R.id.tv_item_description);
        tv_text_content = (TextView) findViewById(R.id.tv_text_content);
        ivItemImage = (ImageView) findViewById(R.id.iv_item_image);
        vvItemVideo = (VideoView)findViewById(R.id.vv_item_video);
        pdfView = (PDFView) findViewById(R.id.pdfView);

        // To retrieve object in second Activity
        ContributedItem item = (ContributedItem) getIntent().getParcelableExtra("Item");
        renderContent(item);

        tvDescription.setText(item.getDescription() + " "+ item.getFile().getMimeType());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(TrailBlazeItemViewerActivity.this, TrailBlazaFeedActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void renderContent(ContributedItem item)
    {
        String fileUrl = item.getFile().getUrl();
        switch (item.getFile().getMimeType())
        {
            case "image/png":
            case "image/jpg":
            case "image/jpeg":
            case "image/gif":
                renderWebImage(ivItemImage, fileUrl);
                break;
            case "video/mp4":
                renderVideo(vvItemVideo, fileUrl);
                break;
            case "application/pdf":
                displayPdf(wvPdfViewer, fileUrl);
                break;
            case "text/plain":
                displayTextFile(tv_text_content, fileUrl);
                break;
            case "audio/mpeg3":
                playAudio(fileUrl);
                break;
        }

        //

        //playAudio("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");

        //displayTextFile(tv_text_content, "http://txt2html.sourceforge.net/sample.txt");

       // displayPdf(wvPdfViewer, "");
    }

    private void renderWebImage(ImageView imageView, String imageUrl)
    {
        progressDialog = ProgressDialog.show(this, "",
                "Downloading image...", true);
        progressDialog.setCancelable(true);

        new AsyncTaskLoadImage(imageView, progressDialog).execute(imageUrl);
    }

    private void displayTextFile(TextView textView, String fileUrl)
    {
        progressDialog = ProgressDialog.show(this, "",
                "Downloading file...", true);
        progressDialog.setCancelable(true);

        textView.setVisibility(View.VISIBLE);
        new AsyncTaskReadTextFile(textView, progressDialog).execute(fileUrl);
    }

    private void displayPdf(WebView webView, String pdfUrl)
    {
       /* progressDialog = ProgressDialog.show(this, "",
                "Loading...", true);
        progressDialog.setCancelable(true);*/


        pdfView.fromAsset(pdfUrl).load();

        Log.d("PDF", pdfUrl);
    }

    private void renderVideo(VideoView videoView, String videoUrl)
    {
        progressDialog = ProgressDialog.show(this, "",
                "Loading...", true);
        progressDialog.setCancelable(true);

        Uri vidUri = Uri.parse(videoUrl);

        videoView.setVideoURI(vidUri);

        MediaController vidControl = new MediaController(this);
        vidControl.setAnchorView(videoView);
        videoView.setMediaController(vidControl);
        videoView.setVisibility(View.VISIBLE);

        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1,
                                                   int arg2) {
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    private void playAudio(String audioUrl) {

//create new mediaplayer
        MediaPlayer mediaPlayer = new MediaPlayer();

//set audio file path
        try {
            mediaPlayer.setDataSource(audioUrl);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//Prepare mediaplayer
        try {
            mediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//Start mediaplayer
        mediaPlayer.start();;
    }
}
