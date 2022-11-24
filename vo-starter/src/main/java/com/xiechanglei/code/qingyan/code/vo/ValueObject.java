package com.xiechanglei.code.qingyan.code.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * 值对象，用于一些封装数据到对象的场景，比如返回json数据等，避免了使用class导致类的增加
 * - 提供了两个重载的build方法用于构建对象
 * - 提供了一个of方法用于增加数据或者删除数据
 */
public class ValueObject extends TreeMap<String, Object> {

    protected ValueObject() {
    }

    protected ValueObject(String key, Object value) {
        this.put(key, value);
    }

    /**
     * 为统一代码规范的需求，将构造方法私有化，提供两个静态方法用于构建对象
     * for example:
     * Object object = ValueObject.build("key", "value");
     * Object object = ValueObject.build("key", "value").of("key2", "value2");
     * Object object = ValueObject.build().of("key2", "value2");
     */
    public static ValueObject build() {
        return new ValueObject();
    }

    public static ValueObject build(String key, Object value) {
        return new ValueObject(key, value);
    }

    /**
     * 增加数据字段
     */
    public ValueObject of(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     * 用于key的字段的顺序输出key=value的形式的字符串
     */
    public String buildUrlString() {
        List<String> blocks = new ArrayList<>();
        for (String key : this.keySet()) {
            blocks.add(key + "=" + this.get(key));
        }
        return String.join("&", blocks);
    }

    public String buildUrlStringWithUnicode() throws UnsupportedEncodingException {
        return URLEncoder.encode(buildUrlString(),"UTF-8");
    }

}
