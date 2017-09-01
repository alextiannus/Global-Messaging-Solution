package com.wormwood;

import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * User: tangbin
 * Date: 2017/9/1
 * T me: 9:51
 * Description: To change this template use File | Settings | File Templates.
 */
public class LockTest {


}

class LockSet<T extends Collections> {

    private static final String LOCK_COUNT = "lockCount";
    private static final String LOCK_THREAD = "lockThread";

    private Object lock = new Object();


    private Map<String, T> lockMap = Maps.newHashMap();

    private final Map<String, Map<String, Object>> lockCountMap = Maps.newHashMap();
    private final ThreadLocal<Map<String, Boolean>> locked = new ThreadLocal<Map<String, Boolean>>();

    T getData(String id) {
        return lockMap.get(id);
    }

    void lock(String id) throws Exception {
        if (locked.get() != null && locked.get().get(id)) {
            Integer lockCount = (Integer) lockCountMap.get(id).get(LOCK_COUNT);
            lockCount = lockCount == null ? 1 : lockCount + 1;
            lockCountMap.get(id).put(LOCK_COUNT, lockCount);
            System.out.println("lock successful.");
        } else {
            if (lockCountMap.get(id).get(LOCK_COUNT) == null || (Integer) lockCountMap.get(id).get(LOCK_COUNT) < 1) {
                synchronized (lock) {
                    if (lockCountMap.get(id).get(LOCK_COUNT) == null || (Integer) lockCountMap.get(id).get(LOCK_COUNT) < 1) {
                        if (locked.get() == null) {
                            locked.set(Maps.newHashMap());
                        }
                        Integer lockCount = (Integer) lockCountMap.get(id).get(LOCK_COUNT);
                        lockCount = lockCount == null ? 1 : lockCount + 1;
                        lockCountMap.get(id).put(LOCK_COUNT, lockCount);
                        lockCountMap.get(id).put(LOCK_THREAD, Thread.currentThread().getId() + "-" + Thread.currentThread().getName() + "-" + new Date());
                        System.out.println("lock successful.");
                    } else {
                        throw new Exception("Id=" + id + " locked by other thread!, Please try lock later!");
                    }
                }
            } else {
                throw new Exception("Id=" + id + " locked by other thread!, Please try lock later!");
            }
        }
    }

    public void unlock(String id) throws Exception {
        if (locked.get() != null && locked.get().get(id)) {
            synchronized (lock) {
                locked.get().put(id, false);
                lockCountMap.get(id).put(LOCK_COUNT, null);
                lockCountMap.get(id).put(LOCK_THREAD, null);
                System.out.println("unlock successful.");
            }
        } else {
            throw new Exception("Id=" + id + "not locked by current Thread");
        }
    }

    public void opreate(String id) throws Exception {
        lock(id);
        T obj = lockMap.get(id);
        System.out.println(obj);
        unlock(id);
    }

    public void flush(String id, T newObj) throws Exception {
        lock(id);
        lockMap.put(id, newObj);
        unlock(id);
    }

}
