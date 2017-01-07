package lyh.library.cm.basic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import lyh.library.ultra.R;

public class FragmentContainerActivity extends ActivityBase {

    private static Class launchClassFragment;
    private static Object fragmentData;
    public static final int CODE_REQUEST = 1;

    public static void launch(Activity from, Class<? extends Fragment> launchFragment, Object data) {
        launchClassFragment = launchFragment;
        fragmentData = data;
        Intent intent = new Intent(from, FragmentContainerActivity.class);
        from.startActivity(intent);

    }

    public static void launch(Activity from, Class<? extends Fragment> launchFragment) {
        launchClassFragment = launchFragment;
        Intent intent = new Intent(from, FragmentContainerActivity.class);
        from.startActivity(intent);

    }

    public static void launchForResult(Activity from, Class<? extends Fragment> launchFragment, Object data) {
        launchClassFragment = launchFragment;
        fragmentData = data;
        Intent intent = new Intent(from, FragmentContainerActivity.class);
        from.startActivityForResult(intent, CODE_REQUEST);
    }

    public static void launchForResult(Activity from, Class<? extends Fragment> launchFragment) {
        launchClassFragment = launchFragment;
        Intent intent = new Intent(from, FragmentContainerActivity.class);
        from.startActivityForResult(intent, CODE_REQUEST);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_fragment_container);
            if (launchClassFragment != null)
                pushFragmentToBackStack(launchClassFragment, fragmentData);
        }
    }


}
