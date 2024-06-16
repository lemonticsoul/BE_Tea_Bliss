package store.teabliss.tea.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.tea.dto.TeaDto;
import store.teabliss.tea.dto.TeaFinalDto;
import store.teabliss.tea.dto.TeaSearchDto;
import store.teabliss.tea.entity.TeaFlavor;
import store.teabliss.tea.entity.TeaIngredient;
import store.teabliss.tea.entity.Tea;
import store.teabliss.tea.mapper.TeaMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeaService {

    private final TeaMapper teaMapper;


    public boolean createtea(TeaDto teaDto) throws JsonProcessingException {
            Tea tea = new Tea();

            tea.setCategory(teaDto.getCategory());
            tea.setPrice(teaDto.getPrice());
            tea.setReview(teaDto.getReview());
            tea.setSale(teaDto.getSale());
            tea.setRating(teaDto.getRating());
            tea.setSeason(teaDto.getSeason());
            tea.setName(teaDto.getName());
            tea.setNameEng(tea.getNameEng());
            tea.setRate(teaDto.getRate());
            tea.setCaffeine(teaDto.isCaffeine());

            tea.setDescription(teaDto.getDescription());
            tea.setImg(teaDto.getImg());
            tea.setInventory(teaDto.getInventory());
            tea.setSaleStatus(teaDto.getSaleStatus());
            tea.setCreateat(new Timestamp(new Date().getTime()));
            tea.setIsLastPage(false);

            /// flavor와 재료
            teaMapper.save(tea);


            List<Long> temp1=teaDto.getIngredient();
            for (Long t1:temp1){
                TeaIngredient teaingredient =new TeaIngredient();
                teaingredient.setIngredientId(t1);
                teaingredient.setTeaId(tea.getId());
                teaMapper.saveIngredient(teaingredient);
            }

            List<Long> temp2=teaDto.getFlavor();

            for (Long t2:temp2){
                TeaFlavor teaflavor =new TeaFlavor();
                teaflavor.setFlavor(t2);
                teaflavor.setTeaId(tea.getId());
                teaMapper.saveFlavor(teaflavor);
            }





            return true;

    }

    private List<TeaFinalDto> changeDto(List<Tea> listtea){

        List<TeaFinalDto> saveTeaFinalDto = new ArrayList<>();

        for (Tea tea:listtea){
            TeaFinalDto saveDto=new TeaFinalDto();
            saveDto.setPrice(tea.getPrice());
            saveDto.setCategory(tea.getCategory());
            saveDto.setReview(tea.getReview());
            saveDto.setSale(tea.getSale());
            saveDto.setRating(tea.getRating());
            saveDto.setRate(tea.getRate());
            saveDto.setSeason(tea.getSeason());
            saveDto.setName(tea.getName());
            saveDto.setNameEng(tea.getNameEng());
            saveDto.setImg(tea.getImg());
            saveDto.setCreateat(tea.getCreateat());
            saveDto.setIsLastPage(tea.isIsLastPage());


            saveTeaFinalDto.add(saveDto);




        }

        return saveTeaFinalDto;
    }



    public List<TeaFinalDto> sort(int page,int limit){

        List<Tea> recommend=teaMapper.recommend();

        double all=recommend.size();
        int limitpage= (int) Math.ceil(all/limit);
        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                recommend.get(i).setIsLastPage(true);

                new_recommend.add(recommend.get(i));

            } else if (page !=limitpage) {
                recommend.get(i).setIsLastPage(false);

                new_recommend.add(recommend.get(i));

            }

        }

        List<TeaFinalDto> saveFinalDto=changeDto(new_recommend);



        return saveFinalDto;



    }

    public List<TeaFinalDto> salesort(int page,int limit){

        List<Tea> sale=teaMapper.sale();

        double all=sale.size();
        int limitpage= (int) Math.ceil(all/limit);
        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                sale.get(i).setIsLastPage(true);
                new_recommend.add(sale.get(i));

            } else if (page !=limitpage) {
                sale.get(i).setIsLastPage(false);
                new_recommend.add(sale.get(i));

            }

        }

        List<TeaFinalDto> saveFinalDto=changeDto(new_recommend);



        return saveFinalDto;


    }

    public List<TeaFinalDto> topcostsort(int page,int limit){

        List<Tea> topcost=teaMapper.topcost();
        double all=topcost.size();
        int limitpage= (int) Math.ceil(all/limit);
        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                topcost.get(i).setIsLastPage(true);
                new_recommend.add(topcost.get(i));

            } else if (page !=limitpage) {
                topcost.get(i).setIsLastPage(false);
                new_recommend.add(topcost.get(i));

            }

        }

        List<TeaFinalDto> saveFinalDto=changeDto(new_recommend);



        return saveFinalDto;


    }
    public List<TeaFinalDto> lowcostsort(int page,int limit){

        List<Tea> lowcost=teaMapper.lowcost();
        double all=lowcost.size();
        int limitpage= (int) Math.ceil(all/limit);
        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                lowcost.get(i).setIsLastPage(true);
                new_recommend.add(lowcost.get(i));

            } else if (page !=limitpage) {
                lowcost.get(i).setIsLastPage(false);
                new_recommend.add(lowcost.get(i));

            }

        }

        List<TeaFinalDto> saveFinalDto=changeDto(new_recommend);



        return saveFinalDto;

    }

    public List<TeaFinalDto> all(int page,int limit){

        List<Tea> alllist=teaMapper.all();
        double all=alllist.size();
        int limitpage= (int) Math.ceil(all/limit);
        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                alllist.get(i).setIsLastPage(true);
                new_recommend.add(alllist.get(i));

            } else if (page !=limitpage) {
                alllist.get(i).setIsLastPage(false);
                new_recommend.add(alllist.get(i));

            }

        }

        List<TeaFinalDto> saveFinalDto=changeDto(new_recommend);



        return saveFinalDto;

    }

    public Long count(){

        Long count=teaMapper.count();
        return count;
    }

    public TeaSearchDto find(int id){

        Tea tea=teaMapper.findbyid(id);
        ArrayList<Long> ingredient=teaMapper.findbyingredient(id);
        ArrayList<Long> flavor=teaMapper.findbyflavor(id);

        TeaSearchDto dto=new TeaSearchDto();

        dto.setId(tea.getId());
        dto.setPrice(tea.getPrice());
        dto.setCategory(tea.getCategory());
        dto.setReview(tea.getReview());
        dto.setSale(tea.getSale());
        dto.setRating(tea.getRating());
        dto.setRate(tea.getRate());
        dto.setSeason(tea.getSeason());
        dto.setName(tea.getName());
        dto.setNameEng(tea.getNameEng());
        dto.setCaffeine(tea.isCaffeine());
        dto.setDescription(tea.getDescription());
        dto.setImg(tea.getImg());
        dto.setInventory(tea.getInventory());
        dto.setSaleStatus(tea.getSaleStatus());
        dto.setFlavor(flavor);
        dto.setIngredient(ingredient);


        return dto;

    }

    public List<TeaFinalDto> categorysort(int page,int limit,String category){


        List<Tea> categorylist=teaMapper.category(category);
        double all=categorylist.size();

        int limitpage= (int) Math.ceil(all/limit);

        List<Tea> new_recommend=new ArrayList<>();

        for (int i =limit*(page-1);i<(page*limit);i++){
            if (i==all) {
                break;
            }
            if (page==limitpage){
                categorylist.get(i).setIsLastPage(true);
                new_recommend.add(categorylist.get(i));

            } else if (page !=limitpage) {
                categorylist.get(i).setIsLastPage(false);
                new_recommend.add(categorylist.get(i));

            }

        }

        List<TeaFinalDto> saveFinalDto=changeDto(new_recommend);



        return saveFinalDto;

    }

    public Long categorycount(String category){

        Long count=teaMapper.countByCategory(category);

        return count;
    }


}
