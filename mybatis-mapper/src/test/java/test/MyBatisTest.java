package test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mybatis.MybatisApplication;
import com.mybatis.entity.Torder;
import com.mybatis.mapper.TorderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = MybatisApplication.class)
public class MyBatisTest {
    @Autowired
    private TorderMapper testXmlMapper;

    /**
     * 测试查询 - XML方式
     */
    @Test
    public void testGetXml() {
        List<Torder> torders = testXmlMapper.selectAll();
        for (Torder torder : torders) {
            System.err.println(torder);
        }
    }

    /**
     * 测试分页
     */
    @Test
    public void testListPage() {
        Page<Object> page = PageHelper.startPage(2, 2).setOrderBy("id desc");
        List<Torder> torders = testXmlMapper.select(new Torder());
        for (Torder torder : torders) {
            System.err.println(torder);
        }
        System.err.println(page.toString());
    }

}
