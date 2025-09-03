package xyz.xgh.questionnaire.questionnaire.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.experimental.Accessors;
import xyz.xgh.questionnaire.questionnaire.util.QuestionsDeserializer;

@Data
@Accessors(chain = true)
@TableName("questionnaire_item")
public class QuestionnaireItem {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @TableField("tenant_id")
    private String tenantId;

    @TableField("questionnaire_id")
    private String questionnaireId;

    @JsonDeserialize(using = QuestionsDeserializer.class)
    @TableField("questions")
    private String questions;
}
