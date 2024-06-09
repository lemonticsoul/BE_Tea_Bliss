package store.teabliss.ingredient.dto;

import lombok.Builder;
import lombok.Getter;
import store.teabliss.ingredient.entity.Flavor;

import java.util.List;

@Builder
@Getter
public class FlavorResponseDto {

    String name;

    String nameEng;

    public static FlavorResponseDto of(Flavor flavor) {
        return FlavorResponseDto.builder()
                .name(flavor.getName())
                .nameEng(flavor.getNameEng())
                .build();
    }

    public static List<FlavorResponseDto> ofs(List<Flavor> flavors) {
        return flavors.stream()
                .map(FlavorResponseDto::of)
                .toList();
    }

}
