package store.teabliss.ingredient.mapper;

import org.apache.ibatis.annotations.Mapper;
import store.teabliss.ingredient.entity.Flavor;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FlavorMapper {

    List<Flavor> findByFlavors(String[] ids);

    Optional<Flavor> findByFlavor(Long id);
}
