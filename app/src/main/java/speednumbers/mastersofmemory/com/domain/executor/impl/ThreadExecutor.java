package speednumbers.mastersofmemory.com.domain.executor.impl;

import speednumbers.mastersofmemory.com.domain.executor.Executor;
import speednumbers.mastersofmemory.com.domain.interactors.base.AbstractInteractor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

/**
 * This singleton class will make sure that each interactor operation gets a background thread.
 * <p/>
 */
public class ThreadExecutor implements Executor {
    private static final int                     CORE_POOL_SIZE  = 3;
    private static final int                     MAX_POOL_SIZE   = 5;
    private static final int                     KEEP_ALIVE_TIME = 120;
    private static final TimeUnit                TIME_UNIT       = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE      = new LinkedBlockingQueue<Runnable>();

    private ThreadPoolExecutor mThreadPoolExecutor;

    @Inject
    public ThreadExecutor(ThreadPoolExecutor mThreadPoolExecutor) {
        this.mThreadPoolExecutor = mThreadPoolExecutor;
    }

    @Override
    public void execute(final AbstractInteractor interactor) {
        mThreadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                // run the main logic
                interactor.run();

                // mark it as finished
                interactor.onFinished();
            }
        });
    }
}
