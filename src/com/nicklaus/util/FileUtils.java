package com.nicklaus.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils<T> {

    /**
     * 将对象list写入json文件中
     * @param fileName
     * @param list
     * @return
     */
    public boolean writeJsonList(String fileName, List<T> list){

        File file = new File(fileName);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;

        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            ObjectMapper mapper = new ObjectMapper();
            String jsonList = mapper.writeValueAsString(list);
            bufferedWriter.write(jsonList);
            bufferedWriter.flush();

            //关闭资源
            bufferedWriter.close();
            fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 从json文件中读取对象list
     * @param fileName
     * @param type
     * @return
     */
    public List<T> readJsonFileAsList(String fileName, Class<T> type){
        File file = new File(fileName);

        List<T> objects =null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            TypeFactory typeFactory = mapper.getTypeFactory();
            List<T> objectList = mapper.readValue(file, typeFactory.constructType(List.class, type));
            objects = new ArrayList<T>();
            for (int i = 0; i < objectList.size(); i++) {
                T t = JSON.parseObject(JSON.toJSONString(objectList.get(i)),type);
                objects.add(t);
            }
        }catch (Exception e){
            return null;
        }

        return objects;
    }
}
