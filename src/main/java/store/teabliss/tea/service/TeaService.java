package store.teabliss.tea.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import store.teabliss.tea.dto.TeaDto;
import store.teabliss.tea.entity.Tea;
import store.teabliss.tea.mapper.TeaMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeaService {

    private final TeaMapper teaMapper;


    public boolean createtea(TeaDto teaDto){
            Tea tea = new Tea();

            tea.setTitle(teaDto.getTitle());
            tea.setCategory(teaDto.getCategory());
            tea.setCost(teaDto.getCost());
            tea.setReview(teaDto.getReview());
            tea.setSale(teaDto.getSale());
            tea.setRating(teaDto.getRating());
            tea.setSeason(teaDto.getSeason());
            tea.setRate(teaDto.getRate());
            tea.setCreateat(new Timestamp(new Date().getTime()));
            tea.setIsLastPage(false);
            teaMapper.save(tea);
            return true;

    }

    public List<Tea> sort(int page,int limit){

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

        return new_recommend;



    }

    public List<Tea> salesort(int page,int limit){

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

        return new_recommend;


    }

    public List<Tea> topcostsort(int page,int limit){

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

        return new_recommend;


    }
    public List<Tea> lowcostsort(int page,int limit){

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

        return new_recommend;

    }

    public List<Tea> all(){

        List<Tea> all=teaMapper.all();
        return all;
    }


}
