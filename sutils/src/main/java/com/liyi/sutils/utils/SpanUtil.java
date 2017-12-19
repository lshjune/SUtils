package com.liyi.sutils.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.TextView;

import com.liyi.sutils.utils.log.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * SpannableString工具类
 */
public class SpanUtil {
    private final String TAG = this.getClass().getSimpleName();
    /**
     * Spannable.SPAN_EXCLUSIVE_EXCLUSIVE :
     * 前后不包括在内，也就是说，在前面和后面插入的新字符不会应用新样式
     * <p>
     * Spannable.SPAN_EXCLUSIVE_INCLUSIVE :
     * 不包括前面，但是包括后面，在后面插入的新字符会应用新样式
     * <p>
     * Spannable.SPAN_INCLUSIVE_EXCLUSIVE :
     * 包括前面，不包括后面
     * <p>
     * Spannable.SPAN_INCLUSIVE_INCLUSIVE :
     * 同时包括前面和后面
     */
    private final int DEF_SPAN_FLAG = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
    private int mSpanFlag;
    private SpannableStringBuilder mBuilder;
    private Context mContext;
    private TextView textView;

    private SpanUtil(TextView textView) {
        super();
        init(textView);
    }

    private void init(TextView tv) {
        mSpanFlag = DEF_SPAN_FLAG;
        mBuilder = new SpannableStringBuilder();
        textView = tv;
        if (textView != null) {
            mContext = textView.getContext();
        }
    }

    /**
     * 绑定需要使用SpannableString效果的textview
     *
     * @param textView textview
     * @return SpanUtil 类
     */
    public static SpanUtil bind(TextView textView) {
        return new SpanUtil(textView);
    }

    /**
     * 设置SpannableString类型
     *
     * @param flag
     * @return
     */
    public SpanUtil setSpanFlag(int flag) {
        this.mSpanFlag = flag;
        return this;
    }

    /**
     * 添加SpannableString的内容
     *
     * @param text
     * @return
     */
    public SpanUtil addContent(CharSequence text) {
        mBuilder.append(text);
        return this;
    }

    /**
     * 设置文字颜色
     *
     * @param color 文字颜色
     * @param start 文字颜色的起始位置
     * @param end   文字颜色结束位置
     * @return
     */
    public SpanUtil addFontColor(@ColorInt int color, int start, int end) {
        mBuilder.setSpan(new ForegroundColorSpan(color), start, end, mSpanFlag);
        return this;
    }

    /**
     * 设置文字颜色
     *
     * @param color 文字颜色
     * @param key   被设置颜色的文字
     * @return
     */
    public SpanUtil addFontColorByKey(@ColorInt int color, String key) {
        List<int[]> list = searchAllIndex(key);
        for (int[] index : list) {
            mBuilder.setSpan(new ForegroundColorSpan(color), index[0], index[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 设置文字颜色
     *
     * @param color 文字颜色
     * @param key   被设置颜色的文字（即关键字）
     * @param index 如果关键字有多个，选择指定index下标的关键字（从0开始）
     * @return
     */
    public SpanUtil addFontColorByKey(@ColorInt int color, String key, int index) {
        List<int[]> list = searchAllIndex(key);
        if (index >= 0 && index < list.size()) {
            mBuilder.setSpan(new ForegroundColorSpan(color), list.get(index)[0], list.get(index)[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 设置背景色
     *
     * @param color 文字背景颜色
     * @param start 背景颜色的起始位置
     * @param end   背景颜色的结束位置
     * @return
     */
    public SpanUtil addBgColor(@ColorInt int color, int start, int end) {
        mBuilder.setSpan(new BackgroundColorSpan(color), start, end, mSpanFlag);
        return this;
    }

    /**
     * 设置背景色
     *
     * @param color 文字背景颜色
     * @param key   被设置背景颜色的文字
     * @return
     */
    public SpanUtil addBgColorByKey(@ColorInt int color, String key) {
        List<int[]> list = searchAllIndex(key);
        for (int[] index : list) {
            mBuilder.setSpan(new BackgroundColorSpan(color), index[0], index[1], mSpanFlag);
        }
        return this;
    }

    public SpanUtil addBgColorByKey(@ColorInt int color, String key, int index) {
        List<int[]> list = searchAllIndex(key);
        if (index >= 0 && index < list.size()) {
            mBuilder.setSpan(new BackgroundColorSpan(color), list.get(index)[0], list.get(index)[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 对文字添加url链接
     *
     * @param url   链接
     * @param start 被添加链接的文字的起始位置
     * @param end   被添加链接的文字的结束位置
     * @return
     */
    public SpanUtil addURL(String url, int start, int end) {
        mBuilder.setSpan(new URLSpan(url), start, end, mSpanFlag);
        return this;
    }

    /**
     * 对文字添加url链接
     *
     * @param url 链接
     * @param key 被添加链接的文字
     * @return
     */
    public SpanUtil addURLByKey(String url, String key) {
        List<int[]> list = searchAllIndex(key);
        for (int[] index : list) {
            mBuilder.setSpan(new URLSpan(url), index[0], index[1], mSpanFlag);
        }
        return this;
    }

    public SpanUtil addURLByKey(String url, String key, int index) {
        List<int[]> list = searchAllIndex(key);
        if (index >= 0 && index < list.size()) {
            mBuilder.setSpan(new URLSpan(url), list.get(index)[0], list.get(index)[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 设置文字字体样式，例如斜体
     *
     * @param style Typeface.NORMAL、 Typeface.BOLD、Typeface.ITALIC、Typeface.BOLD_ITALIC
     * @param start 被设置字体文字的起始位置
     * @param end   被设置字体文字的结束位置
     * @return
     */
    public SpanUtil addTypeface(int style, int start, int end) {
        mBuilder.setSpan(new StyleSpan(style), start, end, mSpanFlag);
        return this;
    }

    /**
     * 设置文字字体样式，例如斜体
     *
     * @param style 字体样式
     * @param key   文字
     * @return
     */
    public SpanUtil addTypefaceByKey(int style, String key) {
        List<int[]> list = searchAllIndex(key);
        for (int[] index : list) {
            mBuilder.setSpan(new StyleSpan(style), index[0], index[1], mSpanFlag);
        }
        return this;
    }

    public SpanUtil addTypefaceByKey(int style, String key, int index) {
        List<int[]> list = searchAllIndex(key);
        if (index >= 0 && index < list.size()) {
            mBuilder.setSpan(new StyleSpan(style), list.get(index)[0], list.get(index)[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 添加删除线
     *
     * @param start 被添加删除线的文字的起始位置
     * @param end   被添加删除线的文字的结束位置
     * @return
     */
    public SpanUtil addStrikethrough(int start, int end) {
        mBuilder.setSpan(new StrikethroughSpan(), start, end, mSpanFlag);
        return this;
    }

    /**
     * 添加删除线
     *
     * @param key 被添加删除线的文字的文字
     * @return
     */
    public SpanUtil addStrikethroughByKey(String key) {
        List<int[]> list = searchAllIndex(key);
        for (int[] index : list) {
            mBuilder.setSpan(new StrikethroughSpan(), index[0], index[1], mSpanFlag);
        }
        return this;
    }

    public SpanUtil addStrikethroughByKey(String key, int index) {
        List<int[]> list = searchAllIndex(key);
        if (index >= 0 && index < list.size()) {
            mBuilder.setSpan(new StrikethroughSpan(), list.get(index)[0], list.get(index)[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 用图片替代指定文字
     *
     * @param span  替代文字的图片样式
     * @param start 起始位置
     * @param end   结束位置
     * @return
     */
    public SpanUtil addImage(ImageSpan span, int start, int end) {
        mBuilder.setSpan(span, start, end, mSpanFlag);
        return this;
    }

    /**
     * 用图片替代指定文字
     *
     * @param span 起始位置
     * @param key  结束位置
     * @return
     */
    public SpanUtil addImageByKey(ImageSpan span, String key) {
        List<int[]> list = searchAllIndex(key);
        for (int[] index : list) {
            mBuilder.setSpan(span, index[0], index[1], mSpanFlag);
        }
        return this;
    }

    public SpanUtil addImageByKey(ImageSpan span, String key, int index) {
        List<int[]> list = searchAllIndex(key);
        if (index >= 0 && index < list.size()) {
            mBuilder.setSpan(span, list.get(index)[0], list.get(index)[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 用图片替代指定文字
     *
     * @param b                 替代文字的图片
     * @param verticalAlignment 图片在文字中的对齐方式
     * @param start             起始位置
     * @param end               结束位置
     * @return
     */
    public SpanUtil addImage(Bitmap b, int verticalAlignment, int start, int end) {
        ImageSpan span = new ImageSpan(mContext, b, verticalAlignment);
        mBuilder.setSpan(span, start, end, mSpanFlag);
        return this;
    }

    /**
     * 用图片替代指定文字
     *
     * @param b
     * @param verticalAlignment 图片在文字中的对齐方式
     * @param key               被替换的文字
     * @return
     */
    public SpanUtil addImageByKey(Bitmap b, int verticalAlignment, String key) {
        List<int[]> list = searchAllIndex(key);
        for (int[] index : list) {
            ImageSpan span = new ImageSpan(mContext, b, verticalAlignment);
            mBuilder.setSpan(span, index[0], index[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 用图片替代指定文字
     *
     * @param b
     * @param verticalAlignment 图片在文字中的对齐方式
     * @param key               被替换的文字
     * @param index             如果被替换的文字有相同的多个，通过index选择是当中第几个（从0开始）
     * @return
     */
    public SpanUtil addImageByKey(Bitmap b, int verticalAlignment, String key, int index) {
        List<int[]> list = searchAllIndex(key);
        if (index >= 0 && index < list.size()) {
            ImageSpan span = new ImageSpan(mContext, b, verticalAlignment);
            mBuilder.setSpan(span, list.get(index)[0], list.get(index)[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 用图片替代指定文字
     *
     * @param d
     * @param verticalAlignment 图片在文字中的对齐方式
     * @param start
     * @param end
     * @return
     */
    public SpanUtil addImage(Drawable d, int verticalAlignment, int start, int end) {
//        d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, verticalAlignment);
        mBuilder.setSpan(span, start, end, mSpanFlag);
        return this;
    }

    /**
     * 用图片替代指定文字
     *
     * @param d
     * @param verticalAlignment 图片在文字中的对齐方式
     * @param key
     * @return
     */
    public SpanUtil addImageByKey(Drawable d, int verticalAlignment, String key) {
        List<int[]> list = searchAllIndex(key);
        for (int[] index : list) {
            ImageSpan span = new ImageSpan(d, verticalAlignment);
            mBuilder.setSpan(span, index[0], index[1], mSpanFlag);
        }
        return this;
    }

    public SpanUtil addImageByKey(Drawable d, int verticalAlignment, String key, int index) {
        List<int[]> list = searchAllIndex(key);
        if (index >= 0 && index < list.size()) {
            ImageSpan span = new ImageSpan(d, verticalAlignment);
            mBuilder.setSpan(span, list.get(index)[0], list.get(index)[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 用图片替代指定文字
     *
     * @param resourceId
     * @param verticalAlignment 图片在文字中的对齐方式
     * @param start
     * @param end
     * @return
     */
    public SpanUtil addImage(@DrawableRes int resourceId, int verticalAlignment, int start, int end) {
        ImageSpan span = new ImageSpan(mContext, resourceId, verticalAlignment);
        mBuilder.setSpan(span, start, end, mSpanFlag);
        return this;
    }

    /**
     * 用图片替代指定文字
     *
     * @param resourceId
     * @param verticalAlignment 图片在文字中的对齐方式
     * @param key
     * @return
     */
    public SpanUtil addImageByKey(@DrawableRes int resourceId, int verticalAlignment, String key) {
        List<int[]> list = searchAllIndex(key);
        for (int[] index : list) {
            ImageSpan span = new ImageSpan(mContext, resourceId, verticalAlignment);
            mBuilder.setSpan(span, index[0], index[1], mSpanFlag);
        }
        return this;
    }

    public SpanUtil addImageByKey(@DrawableRes int resourceId, int verticalAlignment, String key, int index) {
        List<int[]> list = searchAllIndex(key);
        if (index >= 0 && index < list.size()) {
            ImageSpan span = new ImageSpan(mContext, resourceId, verticalAlignment);
            mBuilder.setSpan(span, list.get(index)[0], list.get(index)[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 用图片替代指定文字
     *
     * @param uri
     * @param verticalAlignment 图片在文字中的对齐方式
     * @param start
     * @param end
     * @return
     */
    public SpanUtil addImage(Uri uri, int verticalAlignment, int start, int end) {
        ImageSpan span = new ImageSpan(mContext, uri, verticalAlignment);
        mBuilder.setSpan(span, start, end, mSpanFlag);
        return this;
    }

    /**
     * 用图片替代指定文字
     *
     * @param uri
     * @param verticalAlignment 图片在文字中的对齐方式
     * @param key
     * @return
     */
    public SpanUtil addImageByKey(Uri uri, int verticalAlignment, String key) {
        List<int[]> list = searchAllIndex(key);
        for (int[] index : list) {
            ImageSpan span = new ImageSpan(mContext, uri, verticalAlignment);
            mBuilder.setSpan(span, index[0], index[1], mSpanFlag);
        }
        return this;
    }

    public SpanUtil addImageByKey(Uri uri, int verticalAlignment, String key, int index) {
        List<int[]> list = searchAllIndex(key);
        if (index >= 0 && index < list.size()) {
            ImageSpan span = new ImageSpan(mContext, uri, verticalAlignment);
            mBuilder.setSpan(span, list.get(index)[0], list.get(index)[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 为指定文字添加点击事件
     *
     * @param isNeedUnderLine 文字是否需要添加下划线
     * @param start
     * @param end
     * @return
     */
    public SpanUtil addClick(final OnTextClickListener listener, final boolean isNeedUnderLine, int start, int end) {
        mBuilder.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                if (!isNeedUnderLine) {
                    ds.setUnderlineText(false);
                }
            }

            @Override
            public void onClick(View widget) {
                if (listener != null) {
                    listener.onTextClick(-1, widget);
                }
            }
        }, start, end, mSpanFlag);
        return this;
    }

    /**
     * 为指定文字添加点击事件
     *
     * @param listener
     * @param isNeedUnderLine 文字是否需要添加下划线
     * @param key
     * @return
     */
    public SpanUtil addClickByKey(final OnTextClickListener listener, final boolean isNeedUnderLine, String key) {
        List<int[]> list = searchAllIndex(key);
        for (int i = 0; i < list.size(); i++) {
            final int finalI = i;
            mBuilder.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    if (!isNeedUnderLine) {
                        ds.setUnderlineText(false);
                    }
                }

                @Override
                public void onClick(View widget) {
                    if (listener != null) {
                        listener.onTextClick(finalI, widget);
                    }
                }
            }, list.get(finalI)[0], list.get(finalI)[1], mSpanFlag);
        }
        return this;
    }

    public SpanUtil addClickByKey(final OnTextClickListener listener, final boolean isNeedUnderLine, String key, final int index) {
        List<int[]> list = searchAllIndex(key);
        if (index >= 0 && index < list.size()) {
            mBuilder.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    if (!isNeedUnderLine) {
                        ds.setUnderlineText(false);
                    }
                }

                @Override
                public void onClick(View widget) {
                    if (listener != null) {
                        listener.onTextClick(index, widget);
                    }
                }
            }, list.get(index)[0], list.get(index)[1], mSpanFlag);
        }
        return this;
    }

    /**
     * 执行
     */
    public void build() {
        if (textView != null) {
            textView.setText(mBuilder);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public SpannableStringBuilder getSpanBuilder() {
        if (mBuilder == null) {
            LogUtil.e(TAG, "error ========> The SpannableStringBuilder in SpanUtil is empty");
            return null;
        }
        return mBuilder;
    }

    public TextView getTextView() {
        if (textView == null) {
            LogUtil.e(TAG, "error ========> The textview in SpanUtil is empty");
            return null;
        }
        return textView;
    }

    /**
     * 查询字符串中的所有关键字
     *
     * @param key
     * @return
     */
    public List<int[]> searchAllIndex(String key) {
        List<int[]> indexList = new ArrayList<int[]>();
        String text = mBuilder.toString();
        if (TextUtils.isEmpty(text)) {
            return indexList;
        }
        int a = text.indexOf(key);
        while (a != -1) {
            int[] index = new int[]{a, a + key.length()};
            indexList.add(index);
            a = text.indexOf(key, a + 1);
        }
        return indexList;
    }

    /**
     * 部分文字点击事件监听器
     */
    public interface OnTextClickListener {
        /**
         * @param position
         * @param v
         */
        void onTextClick(int position, View v);
    }
}
