package core.framework.dev.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import core.framework.dev.R;

/**
 * 注册
 */
public class RegisterActivity extends AppCompatActivity {

    public static void launch(Activity from) {
        from.startActivity(new Intent(from, RegisterActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
