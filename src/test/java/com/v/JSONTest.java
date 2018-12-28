package com.v;/**
 * Created by VLoye on 2018/12/20.
 */

import com.alibaba.fastjson.JSON;

import java.util.HashMap;

/**
 * @author V
 * @Classname JSONTest
 * @Description
 **/
public class JSONTest {
    public static void main(String[] args) {
        HashMap<String,String> hashMap = new HashMap<String,String>();
        for(int i=0;i<1;i++)
            hashMap.put("key"+i,"value"+i);
        byte[] bytes = JSON.toJSONBytes(hashMap);
        System.out.println(new String(bytes));
        System.out.println(bytes.length);

    }
}
