package xyz.xgh.questionnaire.questionnaire.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xgh.questionnaire.questionnaire.entity.QuestionnaireItem;
import xyz.xgh.questionnaire.questionnaire.mapper.QuestionnaireItemMapper;

@Service
public class QuestionnaireItemService {

    @Autowired
    private QuestionnaireItemMapper questionnaireItemMapper;

    @Transactional
    public String createQuestionnaireItem(QuestionnaireItem questionnaireItem) {
        questionnaireItemMapper.insert(questionnaireItem);
        return questionnaireItem.getId();
    }

    public List<QuestionnaireItem> listQuestionnaireItem(String questionnaireId) {
        LambdaQueryWrapper<QuestionnaireItem> wrapper = new LambdaQueryWrapper<QuestionnaireItem>().eq(QuestionnaireItem::getQuestionnaireId, questionnaireId);
        return questionnaireItemMapper.selectList(wrapper);
    }
}
