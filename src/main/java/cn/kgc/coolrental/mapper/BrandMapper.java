package cn.kgc.coolrental.mapper;

import cn.kgc.coolrental.entity.Brand;
import cn.kgc.coolrental.entity.BrandModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BrandMapper extends BaseMapper<Brand> {
    List<Brand> queryAllPage(@Param("index") Integer index,@Param("size") Integer size);

    List<Brand> queryAll();

    Brand queryById(Integer id);

    boolean addModelList(@Param("brandId") Integer brandId,@Param("list")List<BrandModel> brandModelList);

    @Delete("DELETE FROM t_brand_model_and WHERE brand_id=#{id}")
    boolean deleteModelsById(Integer id);

    List<Brand> queryWord(@Param("word")String word,@Param("index") Integer index,@Param("size") Integer size);

}
