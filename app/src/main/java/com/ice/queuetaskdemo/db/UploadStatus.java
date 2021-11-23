package com.ice.queuetaskdemo.db;

public enum UploadStatus {
    /*** 初始化、上送失败 */
    INIT(1),
    /*** 等待中 */
    WAITING(2),
    /*** 上送中 */
    UPLOADING(3);

    int status;

    UploadStatus(int i) {
        status = i;
    }

    public int getStatus() {
        return status;
    }
}
