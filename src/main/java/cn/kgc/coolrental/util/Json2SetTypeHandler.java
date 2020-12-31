package cn.kgc.coolrental.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.type.*;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class Json2SetTypeHandler extends BaseTypeHandler<Set<String>> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Set<String> postNames, JdbcType jdbcType) throws SQLException {
        String jsonString = JSONArray.toJSONString(postNames);
        System.out.println("转换成功：" + jsonString);
        preparedStatement.setString(i, jsonString);
    }

    @Override
    public Set<String> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return new HashSet<>((List<String>) JSONArray.parse(resultSet.getString(s)));
    }

    @Override
    public Set<String> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return new HashSet<>((List<String>) JSONArray.parse(resultSet.getString(i)));
    }

    @Override
    public Set<String> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return new HashSet<>((List<String>) JSONArray.parse(callableStatement.getString(i)));
    }
}
