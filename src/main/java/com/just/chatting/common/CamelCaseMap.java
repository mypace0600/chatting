package com.just.chatting.common;

import org.apache.commons.collections4.map.ListOrderedMap;

public class CamelCaseMap extends ListOrderedMap {
    public Object put(Object key, Object value) {
        return super.put(CamelUtil.convert2CamelCase((String) key), value);
    }
}
