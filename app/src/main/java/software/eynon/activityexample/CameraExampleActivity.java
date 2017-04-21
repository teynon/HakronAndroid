package software.eynon.activityexample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class CameraExampleActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PERMISSION_REQUEST_STORAGE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_example);
    }

    protected void setPicture(View view) {
        trySetPicture();
    }

    private void trySetPicture() {
        // Check for permissions first.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Should we show a dialog first?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setCancelable(true);
                alert.setTitle("Permission necessary");
                alert.setMessage("External storage permission is needed to open pictures.");
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        trySetPicture();
                    }
                });
                AlertDialog a = alert.create();
                a.show();
            }
            else {
                // Request permissions.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
            }
        }
        else { // Looks ok.
            // Launch the intent for grabbing the picture.
            Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (picture.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(picture, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Is the result the image request?
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Uri selected = data.getData();
            String[] file = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selected, file, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(file[0]);
            String path = c.getString(columnIndex);
            c.close();

            ImageView imgView = (ImageView)findViewById(R.id.picture);
            imgView.setImageBitmap(BitmapFactory.decodeFile(path));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    trySetPicture();
                }
                else {
                    // End of the road.
                }
                break;
        }
    }

}
