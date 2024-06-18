package store.teabliss.common.dto;

import lombok.Getter;

@Getter
public class SearchDto {

    private int page;
    private int limit;
    private String searchKeyword;
    private String searchType;

}
