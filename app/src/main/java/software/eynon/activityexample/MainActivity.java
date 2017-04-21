package software.eynon.activityexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Use "Alt + Enter" to import
    protected void openLinearExample(View view) {
        Intent intent = new Intent(this, LinearExampleActivity.class);
        startActivity(intent);
    }

    protected void openRelativeExample(View view) {
        Intent intent = new Intent(this, RelativeExampleActivity.class);
        startActivity(intent);
    }

    protected void openCameraExample(View view) {
        Intent intent = new Intent(this, CameraExampleActivity.class);
        startActivity(intent);
    }
}
