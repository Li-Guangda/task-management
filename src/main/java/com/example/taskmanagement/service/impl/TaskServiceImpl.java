package com.example.taskmanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.taskmanagement.dto.*;
import com.example.taskmanagement.dao.*;
import com.example.taskmanagement.po.*;
import com.example.taskmanagement.po.TaskPO;
import com.example.taskmanagement.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements ITaskService {

    private TaskMapper taskMapper;
    private ChoiceQuestionMapper choiceQuestionMapper;
    private QuestionOptionMapper questionOptionMapper;
    private ChoiceQuestionAnswerMapper choiceQuestionAnswerMapper;
    private StudentChoiceMapper studentChoiceMapper;
    private StudentShortAnswerMapper studentShortAnswerMapper;
    private StudentTaskMapper studentTaskMapper;
    private StudentInfoMapper studentInfoMapper;
    private ClassroomStudentMapper classroomStudentMapper;
    private ShortAnswerQuestionMapper shortAnswerQuestionMapper;

    /**
     * 发布作业
     * @param newTaskParams
     */
    @Override
    @Transactional
    public void newTask(NewTaskParams newTaskParams) {
        TaskPO taskPO = new TaskPO(
            null,
                newTaskParams.getClassroomId(),
                newTaskParams.getTaskTitle(),
                newTaskParams.getTaskDesc(),
                newTaskParams.getDateStart(),
                newTaskParams.getDateEnd()
        );
        taskMapper.insert(taskPO);

        // 添加选择题
        for (ChoiceQuestion choiceQuestion: newTaskParams.getChoiceQuestions()) {
            ChoiceQuestionPO choiceQuestionPO = new ChoiceQuestionPO(
                   null,
                    taskPO.getTaskId(),
                    choiceQuestion.getSequenceNumber(),
                    choiceQuestion.getTitle(),
                    choiceQuestion.getType(),
                    choiceQuestion.getScore()
            );
            choiceQuestionMapper.insert(choiceQuestionPO);

            for (ChoiceOption choiceOption: choiceQuestion.getOptions()) {
                QuestionOptionPO questionOptionPO = new QuestionOptionPO(
                    null,
                        choiceQuestionPO.getChoiceQuestionId(),
                        choiceOption.getSequenceNumber(),
                        choiceOption.getContent()
                );
                questionOptionMapper.insert(questionOptionPO);

                ChoiceQuestionAnswerPO choiceQuestionAnswerPO = new ChoiceQuestionAnswerPO();
                if (choiceOption.getIsAnswer()) {
                    choiceQuestionAnswerPO.setChoiceQuestionId(choiceQuestionPO.getChoiceQuestionId());
                    choiceQuestionAnswerPO.setQuestionOptionId(questionOptionPO.getQuestionOptionId());
                    choiceQuestionAnswerMapper.insert(choiceQuestionAnswerPO);
                }
            }
        }

        // 添加简答题
        for (ShortQuestion shortQuestion: newTaskParams.getShortQuestions()) {
            ShortAnswerQuestionPO shortAnswerQuestionPO = new ShortAnswerQuestionPO(
                null,
                    taskPO.getTaskId(),
                    shortQuestion.getSequenceNumber(),
                    shortQuestion.getTitle(),
                    shortQuestion.getScore()
            );
            shortAnswerQuestionMapper.insert(shortAnswerQuestionPO);
        }
    }

    /**
     * 学生提交作答内容
     * @param studentAnswerParams
     */
    @Override
    @Transactional
    public void submitStudentAnswer(StudentAnswerParams studentAnswerParams) {
        for(Long questionOptionId: studentAnswerParams.getQuestionOptionIds()) {
            StudentChoicePO studentChoicePO = new StudentChoicePO();
            studentChoicePO.setStudentId(studentAnswerParams.getStudentId());
            studentChoicePO.setQuestionOptionId(questionOptionId);
            studentChoiceMapper.insert(studentChoicePO);
        }

        for (ShortQuestionAnswer shortAnswer: studentAnswerParams.getShortAnswers()) {
            StudentShortAnswerPO studentShortAnswerPO = new StudentShortAnswerPO();
            studentShortAnswerPO.setStudentId(studentAnswerParams.getStudentId());
            studentShortAnswerPO.setShortAnswerQuestionId(shortAnswer.getShortAnswerQuestionId());
            studentShortAnswerPO.setAnswer(shortAnswer.getAnswer());
            studentShortAnswerMapper.insert(studentShortAnswerPO);
        }

        StudentTaskPO studentTaskPO = new StudentTaskPO();
        studentTaskPO.setStudentId(studentAnswerParams.getStudentId());
        studentTaskPO.setTaskId(studentAnswerParams.getTaskId());
        studentTaskPO.setChecked(false);
        studentTaskMapper.insert(studentTaskPO);
    }

    /**
     * 教师批阅学生作业
     * @param remarkParams
     */
    @Override
    @Transactional
    public void remarkStudentTask(RemarkParams remarkParams) {
        StudentTaskPO studentTaskPO = new StudentTaskPO();
        studentTaskPO.setRemark(remarkParams.getRemark());
        studentTaskPO.setChecked(true);
        QueryWrapper<StudentTaskPO> studentTaskPOQueryWrapper = new QueryWrapper<>();
        studentTaskPOQueryWrapper.eq("task_id", remarkParams.getTaskId()).eq("student_id", remarkParams.getStudentId());
        studentTaskMapper.update(studentTaskPO, studentTaskPOQueryWrapper);

        QueryWrapper<StudentShortAnswerPO> studentShortAnswerPOQueryWrapper = new QueryWrapper<>();
        StudentShortAnswerPO studentShortAnswerPO = new StudentShortAnswerPO();
        for (ShortAnswerQuestionScore shortAnswerQuestionScore: remarkParams.getShortAnswerQuestionScores()) {
            studentShortAnswerPOQueryWrapper.clear();
            studentShortAnswerPOQueryWrapper.eq("short_answer_question_id", shortAnswerQuestionScore.getShortAnswerQuestionId())
                                            .eq("student_id", shortAnswerQuestionScore.getStudentId());
            studentShortAnswerPO.setScore(shortAnswerQuestionScore.getScore());
            studentShortAnswerMapper.update(studentShortAnswerPO, studentShortAnswerPOQueryWrapper);
        }
    }

    /**
     * 获取班级作业概要
     * @param classId
     * @return
     */
    @Override
    @Transactional
    public ClassroomTaskInfo getTasksOfClassroom(Long classId) {
        QueryWrapper<TaskPO> taskPOQueryWrapper = new QueryWrapper<>();
        taskPOQueryWrapper.eq("classroom_id", classId);
        List<TaskPO> taskPOS = taskMapper.selectList(taskPOQueryWrapper);

        QueryWrapper<StudentTaskPO> studentTaskPOQueryWrapper = new QueryWrapper<>();
        List<TaskInfo> taskInfos = new ArrayList<>();
        for (TaskPO taskPO: taskPOS) {
            studentTaskPOQueryWrapper.clear();
            studentTaskPOQueryWrapper.eq("task_id", taskPO.getTaskId());
            Integer studentSubmittedCount = studentTaskMapper.selectCount(studentTaskPOQueryWrapper).intValue();

            taskInfos.add(new TaskInfo(
                    taskPO.getTaskId(),
                    taskPO.getClassroomId(),
                    taskPO.getTaskTitle(),
                    taskPO.getTaskDesc(),
                    taskPO.getDateStart(),
                    taskPO.getDateEnd(),
                    studentSubmittedCount
            ));
        }
        ClassroomTaskInfo classroomTaskInfo = new ClassroomTaskInfo();
        classroomTaskInfo.setTaskInfos(taskInfos);
        return classroomTaskInfo;
    }

    @Override
    @Transactional
    public QuestionAnswer getTaskAnswers(Long taskId, Long studentId) {
        Integer totalScore = 0;
        QueryWrapper<ChoiceQuestionPO> choiceQuestionPOQueryWrapper = new QueryWrapper<>();
        choiceQuestionPOQueryWrapper.eq("task_id", taskId);
        List<ChoiceQuestionPO> choiceQuestionPOS = choiceQuestionMapper.selectList(choiceQuestionPOQueryWrapper);

        // 查询该作业的所有选择题
        List<ChoiceQuestionAnswer> choiceQuestionAnswers = new ArrayList<>();
        for (ChoiceQuestionPO choiceQuestionPO: choiceQuestionPOS) {
            // 查询该选择题的正确选项
            String rightAnswer = getRightAnswerOfChoiceQuestion(choiceQuestionPO.getChoiceQuestionId());

            // 查询学生的作答选项
            String studentAnswer = getStudentAnswerOfChoiceQuestion(
                choiceQuestionPO.getChoiceQuestionId(),
                studentId
            );

            Integer score = rightAnswer.equals(studentAnswer) ? choiceQuestionPO.getScore() : 0;
            totalScore += score;

            choiceQuestionAnswers.add(new ChoiceQuestionAnswer(
                    choiceQuestionPO.getChoiceQuestionId(),
                    choiceQuestionPO.getSequenceNumber(),
                    choiceQuestionPO.getType(),
                    rightAnswer,
                    studentAnswer,
                    score,
                    getChoiceQuestionAccuracyOfClassroom(choiceQuestionPO.getChoiceQuestionId())
            ));
        }

        // 查询该作业的所有简答题
        QueryWrapper<ShortAnswerQuestionPO> shortAnswerQuestionPOQueryWrapper = new QueryWrapper<>();
        shortAnswerQuestionPOQueryWrapper.eq("task_id", taskId);
        List<ShortAnswerQuestionPO> shortAnswerQuestionPOS = shortAnswerQuestionMapper.selectList(shortAnswerQuestionPOQueryWrapper);
        QueryWrapper<StudentShortAnswerPO> studentShortAnswerPOQueryWrapper = new QueryWrapper<>();
        List<ShortQuestionScore> shortQuestionScores = new ArrayList<>();
        for (ShortAnswerQuestionPO shortAnswerQuestionPO: shortAnswerQuestionPOS) {
            studentShortAnswerPOQueryWrapper.clear();
            studentShortAnswerPOQueryWrapper.eq("short_answer_question_id", shortAnswerQuestionPO.getShortAnswerQuestionId())
                    .eq("student_id", studentId);
            StudentShortAnswerPO studentShortAnswerPO = studentShortAnswerMapper.selectOne(studentShortAnswerPOQueryWrapper);
            shortQuestionScores.add(new ShortQuestionScore(
                    studentShortAnswerPO.getShortAnswerQuestionId(),
                    studentShortAnswerPO.getScore()
            ));
            totalScore += studentShortAnswerPO.getScore();
        }

        return new QuestionAnswer(
                taskId,
                studentId,
                totalScore,
                choiceQuestionAnswers,
                shortQuestionScores
        );
    }

    @Override
    public String getChoiceQuestionAccuracyOfClassroom(Long choiceQuestionId) {
        ChoiceQuestionPO choiceQuestionPO = choiceQuestionMapper.selectById(choiceQuestionId);

        QueryWrapper<StudentTaskPO> studentTaskPOQueryWrapper = new QueryWrapper<>();
        studentTaskPOQueryWrapper.eq("task_id", choiceQuestionPO.getTaskId());
        List<StudentTaskPO> studentTaskPOS = studentTaskMapper.selectList(studentTaskPOQueryWrapper);

        String rightAnswer = getRightAnswerOfChoiceQuestion(choiceQuestionId);
        Integer submitted = 0, right = 0;
        for (StudentTaskPO studentTaskPO: studentTaskPOS) {
            String studentAnswer = getStudentAnswerOfChoiceQuestion(choiceQuestionId, studentTaskPO.getStudentId());
            if (rightAnswer.equals(studentAnswer))
                right++;
            submitted++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        return decimalFormat.format(right.doubleValue() / submitted.doubleValue());
    }

    @Override
    public String getRightAnswerOfChoiceQuestion(Long choiceQuestionId) {
        QueryWrapper<ChoiceQuestionAnswerPO> choiceQuestionAnswerPOQueryWrapper = new QueryWrapper<>();
        choiceQuestionAnswerPOQueryWrapper.eq("choice_question_id", choiceQuestionId);
        List<ChoiceQuestionAnswerPO> choiceQuestionAnswerPOS = choiceQuestionAnswerMapper.selectList(choiceQuestionAnswerPOQueryWrapper);
        List<Long> choiceOptionIds = new ArrayList<>();
        for (ChoiceQuestionAnswerPO choiceQuestionAnswerPO: choiceQuestionAnswerPOS) {
            choiceOptionIds.add(choiceQuestionAnswerPO.getQuestionOptionId());
        }
        QueryWrapper<QuestionOptionPO> questionOptionPOQueryWrapper = new QueryWrapper<>();
        questionOptionPOQueryWrapper.orderByAsc("sequence_number");
        List<QuestionOptionPO> questionOptionPOS = choiceOptionIds == null ? null : questionOptionMapper.selectBatchIds(choiceOptionIds);
        String rightAnswer = "";
        for (QuestionOptionPO questionOptionPO: questionOptionPOS) {
            rightAnswer += (char) ('A' + questionOptionPO.getSequenceNumber() - 1);
        }
        return rightAnswer;
    }

    @Override
    public String getStudentAnswerOfChoiceQuestion(Long choiceQuestionId, Long studentId) {
        List<Integer> studentChoiceSequenceNumbers = studentChoiceMapper.selectStudentOptionSequenceNumbersByChoiceQuestionId(
                choiceQuestionId,
                studentId
        );
        String studentAnswer = "";
        for (Integer studentChoiceSequenceNumber: studentChoiceSequenceNumbers) {
            studentAnswer += (char) ('A' + studentChoiceSequenceNumber - 1);
        }
        return studentAnswer;
    }

    /**
     * 获取该作业下的学生情况概要
     * @param taskId
     * @return
     */
    @Override
    @Transactional
    public StudentTaskProgressInfos getStudentsOfTask(Long taskId) {
        TaskPO taskPO = taskMapper.selectById(taskId);

        QueryWrapper<ClassroomStudentPO> classroomStudentPOQueryWrapper = new QueryWrapper<>();
        classroomStudentPOQueryWrapper.eq("classroom_id", taskPO.getClassroomId()).eq("joined", true);
        List<ClassroomStudentPO> classroomStudentPOS = classroomStudentMapper.selectList(classroomStudentPOQueryWrapper);

        List<Long> studentIds = new ArrayList<>();
        for (ClassroomStudentPO classroomStudentPO: classroomStudentPOS) {
            studentIds.add(classroomStudentPO.getStudentId());
        }

        List<StudentInfoPO> studentInfoPOS = studentIds == null ? null :
                studentInfoMapper.selectBatchIds(studentIds);

        List<StudentTaskProgressInfo> studentTaskProgressInfos = new ArrayList<>();
        QueryWrapper<StudentTaskPO> studentTaskPOQueryWrapper = new QueryWrapper<>();
        for (StudentInfoPO studentInfoPO: studentInfoPOS) {
            StudentTaskProgressInfo studentTaskProgressInfo = new StudentTaskProgressInfo(
                    taskId,
                    studentInfoPO.getStudentId(),
                    studentInfoPO.getStudentNumber(),
                    studentInfoPO.getAvatar(),
                    null
            );

            studentTaskPOQueryWrapper.clear();
            studentTaskPOQueryWrapper.eq("task_id", taskId).eq("student_id", studentInfoPO.getStudentId());
            if (studentTaskMapper.exists(studentTaskPOQueryWrapper)) {
                StudentTaskPO studentTaskPO = studentTaskMapper.selectOne(studentTaskPOQueryWrapper);
                if (studentTaskPO.isChecked())
                    studentTaskProgressInfo.setTaskStatus("CHECKED");
                else
                    studentTaskProgressInfo.setTaskStatus("NOT_CHECKED");
            } else {
                studentTaskProgressInfo.setTaskStatus("NOT_SUBMITTED");
            }
            studentTaskProgressInfos.add(studentTaskProgressInfo);
        }

        return new StudentTaskProgressInfos(studentTaskProgressInfos);
    }

    /**
     * 获取学生作业作答内容
     * @param taskId
     * @param studentId
     * @return
     */
    @Override
    public StudentTaskInfo getStudentAnswer(Long taskId, Long studentId) {
        List<StudentChoicePO> studentChoicePOSOfTask = studentChoiceMapper.selectByTaskId(taskId);
        List<Long> questionOptionIds = new ArrayList<>();
        for (StudentChoicePO studentChoicePO: studentChoicePOSOfTask) {
            questionOptionIds.add(studentChoicePO.getQuestionOptionId());
        }

        List<StudentShortAnswerPO> studentShortAnswerPOSOfTask = studentShortAnswerMapper.selectByTaskId(taskId);
        List<ShortQuestionAnswer> shortQuestionAnswers = new ArrayList<>();
        for (StudentShortAnswerPO studentShortAnswerPO: studentShortAnswerPOSOfTask) {
            shortQuestionAnswers.add(new ShortQuestionAnswer(
                    studentShortAnswerPO.getShortAnswerQuestionId(),
                    studentShortAnswerPO.getAnswer(),
                    studentShortAnswerPO.getScore()
            ));
        }

        StudentTaskInfo studentTaskInfo = new StudentTaskInfo();
        studentTaskInfo.setChoiceQuestionOptionIds(questionOptionIds);
        studentTaskInfo.setShortQuestionAnswers(shortQuestionAnswers);
        return studentTaskInfo;
    }

    /**
     * 获取作业内容
     * @param taskId
     * @return
     */
    @Override
    public TaskContent getTaskContent(Long taskId) {

        QueryWrapper<ChoiceQuestionPO> choiceQuestionQueryWrapper = new QueryWrapper<>();
        choiceQuestionQueryWrapper.eq("task_id", taskId);
        List<ChoiceQuestionPO> choiceQuestionPOS = choiceQuestionMapper.selectList(choiceQuestionQueryWrapper);

        // 选择题
        List<ChoiceQuestion> choiceQuestions = new ArrayList<>();
        for (ChoiceQuestionPO choiceQuestionPO: choiceQuestionPOS) {
            ChoiceQuestion choiceQuestion = new ChoiceQuestion(
                    choiceQuestionPO.getChoiceQuestionId(),
                    choiceQuestionPO.getSequenceNumber(),
                    choiceQuestionPO.getTitle(),
                    choiceQuestionPO.getType(),
                    choiceQuestionPO.getScore(),
                    null
            );
            QueryWrapper<QuestionOptionPO> questionOptionPOQueryWrapper = new QueryWrapper<>();
            questionOptionPOQueryWrapper.eq("choice_question_id", choiceQuestionPO.getChoiceQuestionId());
            List<QuestionOptionPO> questionOptionPOS = questionOptionMapper.selectList(questionOptionPOQueryWrapper);

            List<ChoiceOption> choiceOptions = new ArrayList<>();
            for (QuestionOptionPO questionOptionPO: questionOptionPOS) {
                choiceOptions.add(new ChoiceOption(
                        questionOptionPO.getQuestionOptionId(),
                        questionOptionPO.getSequenceNumber(),
                        questionOptionPO.getContent(),
                        false
                ));
            }
            choiceQuestion.setOptions(choiceOptions);
            choiceQuestions.add(choiceQuestion);
        }

        // 简答题
        QueryWrapper<ShortAnswerQuestionPO> shortAnswerQuestionPOQueryWrapper = new QueryWrapper<>();
        shortAnswerQuestionPOQueryWrapper.eq("task_id", taskId);
        List<ShortAnswerQuestionPO> shortAnswerQuestionPOS = shortAnswerQuestionMapper.selectList(shortAnswerQuestionPOQueryWrapper);

        List<ShortQuestion> shortQuestions = new ArrayList<>();
        for (ShortAnswerQuestionPO shortAnswerQuestionPO: shortAnswerQuestionPOS) {
            shortQuestions.add(new ShortQuestion(
                    shortAnswerQuestionPO.getShortAnswerQuestionId(),
                    shortAnswerQuestionPO.getSequenceNumber(),
                    shortAnswerQuestionPO.getTitle(),
                    shortAnswerQuestionPO.getScore()
            ));
        }

        return new TaskContent(taskId, choiceQuestions, shortQuestions);
    }

    /**
     * 删除作业
     * @param taskId
     */
    @Override
    public void deleteTask(Long taskId) {
        taskMapper.deleteById(taskId);
    }

    @Autowired
    public void setTaskMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Autowired
    public void setChoiceQuestionMapper(ChoiceQuestionMapper choiceQuestionMapper) {
        this.choiceQuestionMapper = choiceQuestionMapper;
    }

    @Autowired
    public void setQuestionOptionMapper(QuestionOptionMapper questionOptionMapper) {
        this.questionOptionMapper = questionOptionMapper;
    }

    @Autowired
    public void setChoiceQuestionAnswerMapper(ChoiceQuestionAnswerMapper choiceQuestionAnswerMapper) {
        this.choiceQuestionAnswerMapper = choiceQuestionAnswerMapper;
    }

    @Autowired
    public void setStudentChoiceMapper(StudentChoiceMapper studentChoiceMapper) {
        this.studentChoiceMapper = studentChoiceMapper;
    }

    @Autowired
    public void setStudentShortAnswerMapper(StudentShortAnswerMapper studentShortAnswerMapper) {
        this.studentShortAnswerMapper = studentShortAnswerMapper;
    }

    @Autowired
    public void setStudentTaskMapper(StudentTaskMapper studentTaskMapper) {
        this.studentTaskMapper = studentTaskMapper;
    }

    @Autowired
    public void setStudentInfoMapper(StudentInfoMapper studentInfoMapper) {
        this.studentInfoMapper = studentInfoMapper;
    }

    @Autowired
    public void setClassroomStudentMapper(ClassroomStudentMapper classroomStudentMapper) {
        this.classroomStudentMapper = classroomStudentMapper;
    }

    @Autowired
    public void setShortAnswerQuestionMapper(ShortAnswerQuestionMapper shortAnswerQuestionMapper) {
        this.shortAnswerQuestionMapper = shortAnswerQuestionMapper;
    }
}
