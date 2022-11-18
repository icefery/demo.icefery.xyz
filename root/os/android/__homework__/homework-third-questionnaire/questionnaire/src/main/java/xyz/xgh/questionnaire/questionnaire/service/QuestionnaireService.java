package xyz.xgh.questionnaire.questionnaire.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xgh.questionnaire.questionnaire.entity.Questionnaire;
import xyz.xgh.questionnaire.questionnaire.mapper.QuestionnaireMapper;
import java.util.List;

@Service
public class QuestionnaireService {
    @Autowired
    private QuestionnaireMapper questionnaireMapper;

    @Transactional
    public String createQuestionnaire(Questionnaire questionnaire) {
        questionnaireMapper.insert(questionnaire);
        return questionnaire.getId();
    }

    public List<Questionnaire> listQuestionnaire() {
        return questionnaireMapper.selectList(null);
    }

    public Questionnaire findQuestionnaire(String questionnaireId) {
        return questionnaireMapper.selectById(questionnaireId);
    }
}
