package common.feign;

import common.questionAnswer.Answer;
import common.questionAnswer.Question;
import common.questionAnswer.Zan;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "question-answer-service", url = "http://127.0.0.1:8082")
public interface QuestionAnswerFeign {
    //根据问题id获取问题
    @GetMapping("/api/question/{id}")
    Question getQuestionById(int qid);

    //获取所有问题
    @GetMapping("/api/question/all")
    List<Question> getAllQuestion();

    //分页获取问题(分页数量10）
    @GetMapping("/api/question")
    List<Question> getQuestion(@RequestParam(value = "page") int page, @RequestParam(value = "count") int count);

    //根据实体Id分页获取回答(分页数量10)
    @GetMapping("/api/answer")
    public List<Answer> getAnswer(@RequestParam("entityType") int entityType, @RequestParam("entityId") int entityId,
                                  @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                  @RequestParam(value = "count", defaultValue = "0", required = false) int count);

    //查询点赞记录
    @GetMapping("/api/zan")
    public Zan getZanByAnswerIdAndUserId(@RequestParam("answerId") int answerId, @RequestParam("userId") int userId);

    //根据回答Id获取赞的数量
    @GetMapping("/api/zan/count")
    public int getZan(@RequestParam("answerId") int answerId);

    @PostMapping("/api/question/visit")
    public int addVisitTime(@RequestParam("qid") int qid);
}
