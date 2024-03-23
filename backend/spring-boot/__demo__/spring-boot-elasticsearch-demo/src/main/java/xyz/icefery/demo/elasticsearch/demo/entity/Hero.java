package xyz.icefery.demo.elasticsearch.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "hero")
public class Hero {
    @Id
    @Field(type = FieldType.Integer)
    private Integer id;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String name;

    @Field(type = FieldType.Keyword)
    private String alias;

    @Field(type = FieldType.Text, analyzer = "ik_smart")
    private String title;

    @Field(value = "short_bio", type = FieldType.Text, analyzer = "ik_max_word")
    private String shortBio;

    @Field(value = "avatar_src", type = FieldType.Keyword)
    private String avatarSrc;
}
