package com.portal.consumer.infrastructure.rabbit;

import com.google.gson.Gson;
import com.portal.consumer.util.WriterFileCSV;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitConsumer {

    @RabbitListener(queues = {"${rabbit.name.queue}",
            "${rabbit.name.queue.article}",
            "${rabbit.name.queue.honey}",
            "${rabbit.name.queue.lab.report.app}",
            "${rabbit.name.queue.event}"})
    public void processLogMessage(String message) {
        try {
            System.out.println(message);
            Gson gson = new Gson();
            Map<String, String> data = gson.fromJson(message, Map.class);

            System.out.println("Connect !!!");
            WriterFileCSV writerFileCSV = new WriterFileCSV();
            writerFileCSV.writeFileLog(data);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
