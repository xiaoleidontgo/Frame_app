package lyh.library.cm.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Fragment工具类封装
 *
 * @author lyh
 */
public class FragmentUtils {

    private FragmentUtils() {
    }

    /**
     * 切换碎片 没有传递数据
     *
     * @param manager        事物管理
     * @param container      替换的容器
     * @param newFragment    新Fragment
     * @param addToBackStack 是否加入回退栈
     * @return
     */
    public static Fragment replaceFragment(FragmentManager manager, int container, Fragment newFragment,
                                           boolean addToBackStack) {
        FragmentTransaction transaction = manager.beginTransaction();// 开启事物
        String tag = newFragment.getClass().getSimpleName();

        // 切换碎片
        if (newFragment != null) {
            transaction.replace(container, newFragment, tag);
        }
        // 是否将当前碎片加入到回退栈
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
        return newFragment;
    }

    /**
     * 将新Fragment的类名传递进来 直接替换 传递数据
     *
     * @param manager
     * @param container
     * @param newFragment
     * @param args           新Fragment携带的数据
     * @param addToBackStack
     * @return
     */
    public static Fragment replaceFragment(FragmentManager manager, int container,
                                           Class<? extends Fragment> newFragment, Bundle args, boolean addToBackStack) {
        Fragment fragment = null;
        try {
            fragment = newFragment.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (fragment != null) {

            if (args != null && !args.isEmpty()) {
                Bundle bundle = fragment.getArguments();
                if (bundle != null) {
                    bundle.putAll(args);
                } else {
                    fragment.setArguments(args);
                }
            }
            return replaceFragment(manager, container, fragment, addToBackStack);
        }
        return null;

    }

    /**
     * 切换碎片 没有传递数据 不加入回退栈
     *
     * @param manager
     * @param container   容器
     * @param newFragment
     * @return
     */
    public static Fragment replaceFragment(FragmentManager manager, int container, Fragment newFragment) {
        return replaceFragment(manager, container, newFragment, false);
    }

    /**
     * 通过类名 传递数据 不加入回退栈
     *
     * @param manager
     * @param container
     * @param newFragment
     * @param args        新fragment携带的数据
     * @return
     */
    public static Fragment replaceFragment(FragmentManager manager, int container,
                                           Class<? extends Fragment> newFragment, Bundle args) {
        return replaceFragment(manager, container, newFragment, args, false);

    }

    /**
     * 改变Fragment的显示隐藏 切换碎片
     *
     * @return
     */
    public static Fragment switchFragment(FragmentManager manager, int container, Fragment curFragment,
                                          Class<? extends Fragment> newFragment, Bundle args, boolean addToBackStack) {
        FragmentTransaction transaction = manager.beginTransaction();
        String tag = newFragment.getSimpleName();
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment != null) {
            if (fragment != curFragment) {
                if (curFragment != null) {
                    transaction.hide(curFragment);// 隐藏当前的碎片
                }
                transaction.show(fragment);// 显示新的碎片
                if (addToBackStack) {
                    transaction.addToBackStack(null);
                }
                transaction.commitAllowingStateLoss();
            } else {
                if (args != null) {
                    fragment.getArguments().putAll(args);
                }
            }
            return fragment;
        } else {
            try {
                fragment = newFragment.newInstance();// 如果这个碎片以前不存在 实例化出来并显示
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (fragment != null) {
            if (args != null && !args.isEmpty()) {
                Bundle bundle = fragment.getArguments();
                if (bundle != null) {
                    bundle.putAll(args);
                } else {
                    fragment.setArguments(args);
                }
            }
        }
        if (curFragment != null) {
            transaction.hide(curFragment);
        }
        transaction.add(container, fragment, tag);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
        return fragment;
    }

    public static Fragment switchFragment(FragmentManager manager, int container, Fragment curFragment,
                                          Class<? extends Fragment> newFragment, Bundle args) {
        return switchFragment(manager, container, curFragment, newFragment, args, false);
    }
}
