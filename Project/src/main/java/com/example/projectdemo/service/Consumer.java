//package com.example.projectdemo.service;
//
//import com.example.projectdemo.mapper.SubjectMapper;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * @author Zero
// */
//@Service
//@RocketMQMessageListener(topic = "select-subject", consumerGroup = "my-consumer_select-subject")
//public class Consumer implements RocketMQListener<String> {
//    @Autowired
//    private SubjectMapper subjectMapper;
//
//    @Override
//    public void onMessage(String message) {
//        System.out.println("received message:" + message);
//        select(message);
//    }
//
//    public synchronized void select(String message) {
//        subjectMapper.selectSubject(Integer.parseInt(message));
//    }
//}
