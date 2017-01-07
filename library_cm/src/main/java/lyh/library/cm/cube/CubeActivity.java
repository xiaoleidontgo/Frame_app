package lyh.library.cm.cube;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * 内部管理添加进来的Fragment
 */
public abstract class CubeActivity extends AppCompatActivity {

    private final static String LOG_TAG = "cube-fragment";

    public static boolean DEBUG = true;
    protected CubeFragment mCurrentFragment;
    private boolean mCloseWarned;

    /**
     * return the string id of close warning
     * <p>
     * return value which lower than 1 will exit instantly when press back key
     *
     * @return
     */
    protected abstract String getCloseWarning();

    protected abstract int getFragmentContainerId();

    public void pushFragmentToBackStack(Class<?> cls, Object data) {
        FragmentParam param = new FragmentParam();
        param.cls = cls;
        param.data = data;
        goToThisFragment(param);
    }

    protected String getFragmentTag(FragmentParam param) {
        StringBuilder sb = new StringBuilder(param.cls.toString());
        return sb.toString();
    }

    private void goToThisFragment(FragmentParam param) {
        int containerId = getFragmentContainerId();
        Class<?> cls = param.cls;
        if (cls == null) {
            return;
        }
        try {
            String fragmentTag = getFragmentTag(param);
            FragmentManager fm = getSupportFragmentManager();
            if (DEBUG) {
                Log.d(LOG_TAG, "before operate, stack entry count: %s" + fm.getBackStackEntryCount());
            }
            CubeFragment fragment = (CubeFragment) fm.findFragmentByTag(fragmentTag);
            if (fragment == null) {
                fragment = (CubeFragment) cls.newInstance();
            }
            if (mCurrentFragment != null && mCurrentFragment != fragment) {
                mCurrentFragment.onLeave();
            }
            fragment.onEnter(param.data);

            FragmentTransaction ft = fm.beginTransaction();
            if (fragment.isAdded()) {
                if (DEBUG) {
                    Log.d(LOG_TAG, "%s has been added, will be shown again." + fragmentTag);
                }
                ft.show(fragment);
            } else {
                if (DEBUG) {
                    Log.d(LOG_TAG, "%s is added." + fragmentTag);
                }
                ft.add(containerId, fragment, fragmentTag);
            }
            mCurrentFragment = fragment;

            ft.addToBackStack(fragmentTag);
            ft.commitAllowingStateLoss();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mCloseWarned = false;
    }

    public void goToFragment(Class<?> cls, Object data) {
        if (cls == null) {
            return;
        }
        CubeFragment fragment = (CubeFragment) getSupportFragmentManager().findFragmentByTag(cls.toString());
        if (fragment != null) {
            mCurrentFragment = fragment;
            fragment.onBackWithData(data);
        }
        getSupportFragmentManager().popBackStackImmediate(cls.toString(), 0);
    }

    public void popTopFragment(Object data) {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate();
        if (tryToUpdateCurrentAfterPop() && mCurrentFragment != null) {
            mCurrentFragment.onBackWithData(data);
        }
    }

    public void popToRoot(Object data) {
        FragmentManager fm = getSupportFragmentManager();
        while (fm.getBackStackEntryCount() > 1) {
            fm.popBackStackImmediate();
        }
        popTopFragment(data);
    }

    /**
     * process the return back logic
     * return true if back pressed event has been processed and should stay in current view
     *
     * @return
     */
    protected boolean processBackPressed() {
        return false;
    }

    /**
     * process back pressed
     */
    @Override
    public void onBackPressed() {

        // process back for fragment
        if (processBackPressed()) {
            return;
        }

        // process back for fragment
        boolean enableBackPressed = true;
        if (mCurrentFragment != null) {
            enableBackPressed = !mCurrentFragment.processBackPressed();
        }

        if (enableBackPressed) {
            int cnt = getSupportFragmentManager().getBackStackEntryCount();
            if (cnt <= 1 && isTaskRoot()) {
                String closeWarningHint = getCloseWarning();
                if (!mCloseWarned && !TextUtils.isEmpty(closeWarningHint)) {
                    Toast toast = Toast.makeText(this, closeWarningHint, Toast.LENGTH_SHORT);
                    toast.show();
                    mCloseWarned = true;
                } else {
                    doReturnBack();
                }
            } else {
                mCloseWarned = false;
                doReturnBack();
            }
        }
    }

    private boolean tryToUpdateCurrentAfterPop() {
        FragmentManager fm = getSupportFragmentManager();
        int cnt = fm.getBackStackEntryCount();
        if (cnt > 0) {
            String name = fm.getBackStackEntryAt(cnt - 1).getName();
            Fragment fragment = fm.findFragmentByTag(name);
            if (fragment != null && fragment instanceof CubeFragment) {
                mCurrentFragment = (CubeFragment) fragment;
            }
            return true;
        }
        return false;
    }

    protected void doReturnBack() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count <= 1) {
            finish();
        } else {
            getSupportFragmentManager().popBackStackImmediate();
            if (tryToUpdateCurrentAfterPop() && mCurrentFragment != null) {
                mCurrentFragment.onBack();
            }
        }
    }


}
