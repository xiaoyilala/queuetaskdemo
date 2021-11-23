package com.ice.queuetaskdemo.config;

import android.os.Handler;

import java.util.concurrent.ExecutorService;

public class UploaderConfig {
    /*** 最大并行任务数量 */
    public final int mMaxUploadingCount;
    /*** 线程池 */
    public final ExecutorService mUploaderService;
    /*** 子线程 handler */
    public final Handler mLoadHandler;

    public UploaderConfig(Builder builder){
        mMaxUploadingCount = builder.mMaxUploadingCount;
        if(builder.mUploaderService == null){
            mUploaderService = Defaults.THREAD_POOL_UPLOAD;
        }else{
            mUploaderService = builder.mUploaderService;
        }

        if(builder.mLoadHandler == null){
            mLoadHandler = Defaults.getLoadingHandler();
        }else{
            mLoadHandler = builder.mLoadHandler;
        }

    }

    public static class Builder {
        public int mMaxUploadingCount = Defaults.MAX_UPLOAD_COUNT;
        public ExecutorService mUploaderService;
        public Handler mLoadHandler;

        public Builder setMaxUploadingCount(int maxUploadingCount) {
            mMaxUploadingCount = maxUploadingCount;
            return this;
        }

        public Builder setUploaderService(ExecutorService service) {
            mUploaderService = service;
            return this;
        }

        public Builder setLoadHandler(Handler handler) {
            mLoadHandler = handler;
            return this;
        }
    }
}
