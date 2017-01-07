package core.framework.dev.data.http.rx;

import rx.Subscriber;

/**
 * Created by lyh on 2016/11/22.
 * don't need the onComplete();
 */
public abstract class XSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

}
