package lyh.library.cm.cube.lifecycle;

/**
 * Fragment lifeCycle Component
 * Fragment框架_生命周期方法
 */
public interface LifeCycleComponent {

    /**
     * The UI becomes partially invisible.
     * like {@link android.app.Activity#onPause}
     */
    void onBecomesPartiallyInvisible();

    /**
     * The UI becomes visible from partially or totally invisible.
     * like {@link android.app.Activity#onResume}
     */
    void onBecomesVisible();

    /**
     * The UI becomes totally invisible.
     * like {@link android.app.Activity#onStop}
     */
    void onBecomesTotallyInvisible();

    /**
     * The UI becomes visible from totally invisible.
     * like {@link android.app.Activity#onRestart}
     */
    void onBecomesVisibleFromTotallyInvisible();

    /**
     * like {@link android.app.Activity#onDestroy}
     */
    void onDestroy();
}
