package cn.kgc.coolrental.service.impl;

import cn.kgc.coolrental.dto.ElementUiTree;
import cn.kgc.coolrental.entity.Perm;
import cn.kgc.coolrental.mapper.PermMapper;
import cn.kgc.coolrental.service.PermService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermServiceImpl implements PermService {
    @Autowired
    private PermMapper permMapper;

    @Override
    public List<Perm> queryWord(String word) {

        return permMapper.selectList(new QueryWrapper<Perm>()
                .like("name", word)
                .or()
                .like("perm", word)
                .or()
                .like("url", word));
    }

    @Override
    public List<Perm> queryAll() {
        return permMapper.selectList(new QueryWrapper<Perm>());
    }

    @Override
    public List<ElementUiTree> queryAllTree() {
        List<Perm> parent = permMapper.selectList(new QueryWrapper<Perm>().isNull("p_id"));
        List<ElementUiTree> rootList = new ArrayList<>();
        ElementUiTree elementUiTree = null;
        for (Perm perm : parent) {
            elementUiTree = new ElementUiTree();
            elementUiTree.setId(perm.getId());
            elementUiTree.setLabel(perm.getName());
            elementUiTree.setChildren(queryChildByPid(perm.getId()));
            rootList.add(elementUiTree);
        }
        return rootList;
    }

    @Override
    public List<ElementUiTree> queryChildByPid(Integer pid) {
        List<Perm> childList = permMapper.selectList(new QueryWrapper<Perm>().eq("p_id", pid));
        List<ElementUiTree> childs = new ArrayList<>();
        ElementUiTree elementUiTree = null;
        if(childList!=null && childList.size()>0){
            for (Perm perm : childList) {
                elementUiTree = new ElementUiTree();
                elementUiTree.setId(perm.getId());
                elementUiTree.setLabel(perm.getName());
                elementUiTree.setChildren(queryChildByPid(perm.getId()));
                childs.add(elementUiTree);
            }
        }
        return childs;
    }

    @Override
    public Perm queryById(Integer id) {
        return permMapper.selectById(id);
    }

    @Override
    public boolean add(Perm perm) {
        System.out.println(perm);
        if(perm.getPId() != null && perm.getPId() == -99){
            perm.setPId(null);
        }
        return permMapper.insert(perm) > 0;
    }

    @Override
    public boolean remove(Integer id) {
        return permMapper.deleteById(id) > 0;
    }

    @Override
    public boolean hashChild(Integer pid) {
        return permMapper.selectCount(new QueryWrapper<Perm>().eq("p_id",pid)) > 0;
    }

    @Override
    public boolean modify(Perm perm) {
        System.out.println(perm);
        return permMapper.updateById(perm) > 0;
    }
}
