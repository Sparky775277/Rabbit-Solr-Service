package main.auditoriumupdateservice.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.auditoriumupdateservice.model.Auditorium;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;

@Service
public class RabbitReceiver {

    private final SolrService solrService;
    private final ValidationService validationService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitReceiver.class);

    public RabbitReceiver(SolrService solrService, ValidationService validationService) {
        this.solrService = solrService;
        this.validationService = validationService;
    }

    @RabbitListener(queues = "${response.queue.name}")
    public void receiveMessage(Message message) {
        MessageProperties messageProperties = message.getMessageProperties();
        String messageId = messageProperties.getMessageId();
        Instant timestamp = Instant.now();

        try {
            String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);

            List<Auditorium> auditoriums = objectMapper.readValue(messageBody, new TypeReference<List<Auditorium>>() {
            });
            for (Auditorium auditorium : auditoriums) {
                if (validationService.validateAuditoriumData(auditorium)) {
                    SolrInputDocument doc = mapToSolrDocument(auditorium);
                    solrService.addDocument(doc);
                    LOGGER.info("Id сообщения: {}, Время: {}, Статус: Успешно обработано", messageId, timestamp);
                } else {
                    LOGGER.warn("Id сообщения: {}, Время: {}, Статус: Ошибка ФЛК (Не проходят валидацию обязательные поля)", messageId, timestamp);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Id сообщения: {}, Время: {}, Статус: Ошибка, Ошибка: {}", messageId, timestamp, e.getMessage());
        }
    }

    private SolrInputDocument mapToSolrDocument(Auditorium auditorium) {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", "room_" + auditorium.getId());
        doc.addField("type", "Аудитория");
        doc.addField("name_cat", auditorium.getName());
        doc.addField("name", auditorium.getName());
        doc.addField("lecture_room_id_s", auditorium.getId());
        doc.addField("lecture_room_number_s", auditorium.getNumber());
        doc.addField("lecture_room_name_s", auditorium.getName());
        doc.addField("lecture_room_name_t", auditorium.getName());
        doc.addField("lecture_room_building_name_s", auditorium.getBuilding());
        doc.addField("lecture_room_building_name_t", auditorium.getBuilding());
        doc.addField("lecture_room_type_name_s", auditorium.getType());
        doc.addField("lecture_room_type_name_t", auditorium.getType());
        doc.addField("lecture_room_purpose_name_s", auditorium.getPurpose());
        doc.addField("lecture_room_purpose_name_t", auditorium.getPurpose());
        doc.addField("lecture_room_department_name_s", auditorium.getDepartment());
        doc.addField("lecture_room_department_name_t", auditorium.getDepartment());
        doc.addField("timestamp", Instant.now().toString());
        return doc;
    }
}