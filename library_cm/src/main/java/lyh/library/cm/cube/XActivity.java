package lyh.library.cm.cube;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import lyh.library.cm.cube.lifecycle.IComponentContainer;
import lyh.library.cm.cube.lifecycle.LifeCycleComponentManager;
import lyh.library.cm.cube.lifecycle.LifeCycleComponent;

/**
 * 1. manager the components when move from a lifetime to another
 *
 * @author http://www. .net
 */
public abstract class XActivity extends CubeActivity implements IComponentContainer {

    private LifeCycleComponentManager mComponentContainer = new LifeCycleComponentManager();

    private static final boolean DEBUG = true;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onRestart() {
        super.onStart();
        mComponentContainer.onBecomesVisibleFromTotallyInvisible();
        if (DEBUG) {
            showStatus("onRestart");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mComponentContainer.onBecomesPartiallyInvisible();
        if (DEBUG) {
            showStatus("onPause");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mComponentContainer.onBecomesVisibleFromPartiallyInvisible();
        if (DEBUG) {
            showStatus("onResume");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (DEBUG) {
            showStatus("onCreate");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mComponentContainer.onBecomesTotallyInvisible();
        if (DEBUG) {
            showStatus("onStop");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mComponentContainer.onDestroy();
        if (DEBUG) {
            showStatus("onDestroy");
        }
    }

    //当前Activity关闭时弹出的Toast提示
    @Override
    protected String getCloseWarning() {
        return "";
    }

    @Override
    public void addComponent(LifeCycleComponent component) {
        mComponentContainer.addComponent(component);
    }

    private void showStatus(String status) {
        final String[] className = ((Object) this).getClass().getName().split("\\.");
        Log.d("cube-lifecycle", String.format("%s %s", className[className.length - 1], status));
    }
}
