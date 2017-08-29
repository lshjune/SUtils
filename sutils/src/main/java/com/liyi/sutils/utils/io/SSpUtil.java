package com.liyi.sutils.utils.io;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SSpUtil {
    private static final String DEF_FILENAME = "SSpUtil";
    private static final int DEF_MODE = Context.MODE_PRIVATE;

    private static Map<String, SSpUtil> mInstanceMap;
    private static SharedPreferences mSp;
    private static SharedPreferences.Editor mEditor;
    private Context mContext;

    private SSpUtil(Context context, String fileName, int mode) {
        super();
        this.mContext = context.getApplicationContext();
        mSp = mContext.getSharedPreferences(fileName, mode);
        mEditor = mSp.edit();
    }

    public static SSpUtil get(@NonNull Context context) {
        return get(context, DEF_FILENAME, DEF_MODE);
    }

    public static SSpUtil get(@NonNull Context context, @NonNull String fileName, @NonNull int mode) {
        if (mInstanceMap == null) {
            mInstanceMap = new HashMap<String, SSpUtil>();
        }
        SSpUtil manager = mInstanceMap.get(fileName + "_" + mode);
        if (manager == null) {
            manager = new SSpUtil(context, fileName, mode);
            mInstanceMap.put(fileName + "_" + mode, manager);
        }
        return manager;
    }

    /**
     * 保存单个数据
     *
     * @param key
     * @param object
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            mEditor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            mEditor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            mEditor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            mEditor.putLong(key, (Long) object);
        } else {
            mEditor.putString(key, object.toString());
        }
        mEditor.commit();
    }

    /**
     * 同时保存多条数据
     *
     * @param map
     */
    public void add(Map<String, Object> map) {
        Set<String> set = map.keySet();
        for (String key : set) {
            Object object = map.get(key);
            if (object instanceof String) {
                mEditor.putString(key, (String) object);
            } else if (object instanceof Integer) {
                mEditor.putInt(key, (Integer) object);
            } else if (object instanceof Boolean) {
                mEditor.putBoolean(key, (Boolean) object);
            } else if (object instanceof Float) {
                mEditor.putFloat(key, (Float) object);
            } else if (object instanceof Long) {
                mEditor.putLong(key, (Long) object);
            } else {
                mEditor.putString(key, object.toString());
            }
        }
        mEditor.commit();
    }

    public Object get(String key, Object object) {
        if (object instanceof String) {
            return mSp.getString(key, (String) object);
        } else if (object instanceof Integer) {
            return mSp.getInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            return mSp.getBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            return mSp.getFloat(key, (Float) object);
        } else if (object instanceof Long) {
            return mSp.getLong(key, (Long) object);
        }
        return null;
    }

    public void remove(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }

    public SharedPreferences.Editor getEditor() {
        return mEditor;
    }
}
