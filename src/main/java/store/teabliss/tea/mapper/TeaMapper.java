package store.teabliss.tea.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.tea.entity.Tea;

import java.util.List;

@Mapper
public interface TeaMapper {


    void save(Tea tea);

    List<Tea> recommend();

    List<Tea> sale();

    List<Tea> topcost();

    List<Tea> lowcost();

    List<Tea> all();

    Long count();



//    List<Tea> category();


}
