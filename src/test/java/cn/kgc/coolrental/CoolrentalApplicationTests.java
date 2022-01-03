//package cn.kgc.coolrental;
//
//import cn.kgc.coolrental.dto.ElementUiTree;
//import cn.kgc.coolrental.entity.Post;
//import cn.kgc.coolrental.entity.Role;
//import cn.kgc.coolrental.entity.User;
//import cn.kgc.coolrental.mapper.UserMapper;
//import cn.kgc.coolrental.service.PermService;
//import cn.kgc.coolrental.service.PostService;
//import cn.kgc.coolrental.service.UserService;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.sql.DataSource;
//import java.io.InputStream;
//import java.net.URL;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@SpringBootTest
//class CoolrentalApplicationTests {
//
//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PostService postService;
//
//    @Autowired
//    private PermService permService;
//
//    @Test
//    void contextLoads() {
//        Page<User> userPage = new Page<User>();
//
//        Page<User> page = userService.queryWord(1, "黄诗扶", userPage);
////        print(dataSource.getClass());
////        User user = new User();
////        user.setNick("admin");
////        user.setPassword("123");
////        print(userMapper.login(user));
//
//    }
//
//    @Test
//    void test01(){
////        Set<Role> roles = userMapper.queryUserRoles(15002);
////        System.out.println(roles);
//        Set<Integer> ids = new HashSet<>();
//        ids.add(1);
//        ids.add(2);
//        if(userMapper.insertUserRoles(14003,ids)){
//            System.out.println("插入成功");
//        }else{
//            System.out.println("插入失败");
//
//        }
//    }
//
//    @Test
//    void test02(){
//        System.out.println(userService.hashName("英国大理石"));
//    }
//
//
//    private void print(Object obj){
//        System.err.println("====================================\n");
//        System.out.println(obj);
//        System.err.println("\n\n====================================");
//    }
//
//    @Test
//    void test03(){
//        Page<Post> page = new Page<>();
//        Page<Post> postPage = postService.queryAllPage(page);
//        System.out.println(postPage.getRecords());
//    }
//
//
//    @Test
//    void test04(){
//        Post post = new Post();
//        post.setTypeName("没你");
//        Set<String> strings = new HashSet<>();
//        strings.add("车床");
//        strings.add("数控");
//
//        post.setPostNames(strings);
//        post.setStatus(1);
//        print(postService.add(post)?"成功":"失败");
//    }
//    @Test
//    void test05(){
//        URL resource = this.getClass().getClassLoader().getResource("");
//        System.out.println(resource);
//    }
//
//    @Test
//    void test06(){
//        List<ElementUiTree> elementUiTrees = permService.queryAllTree();
//        System.out.println(elementUiTrees);
//    }
//}
