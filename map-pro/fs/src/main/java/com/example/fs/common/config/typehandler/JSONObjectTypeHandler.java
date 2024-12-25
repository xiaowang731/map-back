package com.example.fs.common.config.typehandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

@Slf4j
@MappedTypes({JSONObject.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
@Component
public class JSONObjectTypeHandler extends AbstractJsonTypeHandler<JSONObject> {
    public JSONObjectTypeHandler() {
        super(JSONObject.class);
    }

    @Override
    public JSONObject parse(String json) {
        return JSON.parseObject(json, JSONObject.class, Feature.OrderedField);
    }

    @Override
    public String toJson(JSONObject obj) {
        return JSON.toJSONString(obj,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty
        );
    }
}
