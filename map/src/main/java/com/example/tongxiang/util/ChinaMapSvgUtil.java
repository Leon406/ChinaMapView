package com.example.tongxiang.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;

import com.example.tongxiang.model.ChinaMapModel;
import com.example.tongxiang.model.ProvinceModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Vmmet on 2016/7/28.
 */
public class ChinaMapSvgUtil {
    private String path;
    private Context context;
    private float maxX, minX, maxY, minY;
    public ChinaMapSvgUtil(String path, Context context){
        this.context=context;
        this.path=path;
    }
    public ChinaMapSvgUtil(Context context){
        this.context=context;
    }
    public ChinaMapModel getProvinces(){
        ChinaMapModel map=new ChinaMapModel();
        try {
            InputStream inputStream= context.getResources().getAssets().open("tongxiang.svg");
            DocumentBuilder mybuilder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document myDoc=mybuilder.parse(inputStream);
            //找到根Element
            Element root=myDoc.getDocumentElement();
            NodeList items1 = root.getElementsByTagName("defs");
            Element groot=(Element)items1.item(0);
            NodeList items2 = groot.getElementsByTagName("path");
            //遍历每一个省份
            if (items2.getLength()>0){
            List<ProvinceModel> list=new ArrayList<>();
            SvgPathParserUtil svg=new SvgPathParserUtil();
            for (int i=0;i<items2.getLength();i++){
                ProvinceModel provinceModel =new ProvinceModel();
                Element ele_Province=(Element)items2.item(i);
                String PathPoints=ele_Province.getAttribute("d");
                String name=ele_Province.getAttribute("id");
                List<Path> listpath=new ArrayList<>();
                //拿到每个省的path集合
                String s[]=PathPoints.split("z");
                for(String ss:s){
                    ss+="z";
                    listpath.add(svg.parsePath(ss));
                }
                //拿到name和path
                provinceModel.setName(name);
                provinceModel.setListpath(listpath);
                provinceModel.setColor(Color.rgb(228,223,255));
                provinceModel.setrightColor(Color.rgb(115,98,254));
                provinceModel.setSelectNameColor(Color.WHITE);
                provinceModel.setNameColor(Color.BLACK);
                provinceModel.setNormalBordercolor(Color.WHITE);
                provinceModel.setSelectBordercolor(Color.WHITE);

                if (svg.getMax_X()>= maxX){
                    maxX =svg.getMax_X();
                }
                if (svg.getMax_Y()>= maxY){
                    maxY =svg.getMax_Y();
                }
                if (svg.getMin_X()<= minX){
                    minX =svg.getMin_X();
                }
                if (svg.getMin_Y()<= minY){
                    minY =svg.getMin_Y();
                }
                list.add(provinceModel);
            }
                map.setProvinceslist(list);
                map.setMaxX(maxX);
                map.setMaxY(maxY);
                map.setMinX(minX);
                map.setMinY(minY);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
