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

    @Override
    @Transactional
    public void newTask(NewTaskParams newTaskParams) {
        TaskPO taskPO = new TaskPO();
        taskPO.setClassroomId(newTaskParams.getClassroomId());
        taskPO.setTaskTitle(newTaskParams.getTaskTitle());
        taskPO.setTaskDesc(newTaskParams.getTaskDesc());
        taskPO.setDateStart(newTaskParams.getDateStart());
        taskPO.setDateEnd(newTaskParams.getDateEnd());
        taskMapper.insert(taskPO);

        // 添加选择题
        ChoiceQuestionPO choiceQuestionPO = new ChoiceQuestionPO();
        for (ChoiceQuestion choiceQuestion: newTaskParams.getChoiceQuestions()) {
            choiceQuestionPO.setTaskId(taskPO.getTaskId());
            choiceQuestionPO.setSequenceNumber(choiceQuestion.getSequenceNumber());
            choiceQuestionPO.setType(choiceQuestion.getType());
            choiceQuestionPO.setScore(choiceQuestion.getScore());
            choiceQuestionMapper.insert(choiceQuestionPO);

            QuestionOptionPO questionOptionPO = new QuestionOptionPO();
            for (ChoiceOption choiceOption: choiceQuestion.getOptions()) {
                questionOptionPO.setChoiceQuestionId(choiceQuestionPO.getChoiceQuestionId());
                questionOptionPO.setSequenceNumber(choiceOption.getSequenceNumber());
                questionOptionPO.setContent(choiceOption.getContent());
                questionOptionMapper.insert(questionOptionPO);

                ChoiceQuestionAnswerPO choiceQuestionAnswerPO = new ChoiceQuestionAnswerPO();
                if (choiceOption.isAnswer()) {
                    choiceQuestionAnswerPO.setChoiceQuestionId(choiceQuestionPO.getChoiceQuestionId());
                    choiceQuestionAnswerPO.setQuestionOptionId(questionOptionPO.getQuestionOptionId());
                    choiceQuestionAnswerMapper.insert(choiceQuestionAnswerPO);
                }
            }
        }

        // 添加简答题
        for (ShortQuestion shortQuestion: newTaskParams.getShortQuestions()) {
            ShortAnswerQuestionPO shortAnswerQuestionPO = new ShortAnswerQuestionPO();
            shortAnswerQuestionPO.setTaskId(taskPO.getTaskId());
            shortAnswerQuestionPO.setSequenceNumber(shortQuestion.getNumber());
            shortAnswerQuestionPO.setTitle(shortQuestion.getTitle());
            shortAnswerQuestionPO.setScore(shortQuestion.getScore());
            shortAnswerQuestionMapper.insert(shortAnswerQuestionPO);
        }
    }

    @Override
    @Transactional
    public void submitStudentTask(StudentAnswerParams studentAnswerParams) {
        StudentChoicePO studentChoicePO = new StudentChoicePO();
        for(Long questionOptionId: studentAnswerParams.getQuestionOptionIds()) {
            studentChoicePO.setStudentId(studentAnswerParams.getStudentId());
            studentChoicePO.setQuestionOptionId(questionOptionId);
            studentChoiceMapper.insert(studentChoicePO);
        }

        StudentShortAnswerPO studentShortAnswerPO = new StudentShortAnswerPO();
        for (ShortQuestionAnswer shortAnswer: studentAnswerParams.getShortAnswers()) {
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

    @Override
    @Transactional
    public void remarkStudentTask(RemarkParams remarkParams) {
        StudentTaskPO studentTaskPO = new StudentTaskPO();
        studentTaskPO.setTaskId(remarkParams.getTaskId());
        studentTaskPO.setStudentId(remarkParams.getStudentId());
        studentTaskPO.setRemark(remarkParams.getRemark());
        studentTaskPO.setChecked(true);
        studentTaskMapper.insert(studentTaskPO);

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

    @Override
    @Transactional
    public ClassroomTaskInfo getTasksOfClassroom(Long classId) {
        QueryWrapper<TaskPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("classroom_id", classId);
        List<TaskPO> taskPOS = taskMapper.selectList(queryWrapper);

        List<TaskInfo> taskInfos = new ArrayList<>();
        for (TaskPO taskPO: taskPOS) {
            taskInfos.add(new TaskInfo(
                    taskPO.getTaskId(),
                    taskPO.getClassroomId(),
                    taskPO.getTaskTitle(),
                    taskPO.getTaskDesc(),
                    taskPO.getDateStart(),
                    taskPO.getDateEnd()
            ));
        }
        ClassroomTaskInfo classroomTaskInfo = new ClassroomTaskInfo();
        classroomTaskInfo.setTasks(taskInfos);
        return classroomTaskInfo;
    }

    @Override
    @Transactional
    public StudentTaskProgressInfo getAllStudentProgressOfTask(Long taskId) {
        TaskPO taskPO = taskMapper.selectById(taskId);

        QueryWrapper<ClassroomStudentPO> classroomStudentPOQueryWrapper = new QueryWrapper<>();
        classroomStudentPOQueryWrapper.eq("classroom_id", taskPO.getClassroomId()).eq("joined", true);
        List<ClassroomStudentPO> classroomStudentPOS = classroomStudentMapper.selectList(classroomStudentPOQueryWrapper);

        List<Long> studenIdsAll = new ArrayList<>();
        for (ClassroomStudentPO classroomStudentPO: classroomStudentPOS) {
            studenIdsAll.add(classroomStudentPO.getStudentId());
        }

        QueryWrapper<StudentTaskPO> studentTaskPOQueryWrapper = new QueryWrapper<>();
        studentTaskPOQueryWrapper.eq("task_id", taskId);
        List<StudentTaskPO> studentTaskPOS = studentTaskMapper.selectList(studentTaskPOQueryWrapper);

        List<Long> studentIdsIsChecked = new ArrayList<>();
        List<Long> studentIdsIsNotChecked = new ArrayList<>();
        for (StudentTaskPO studentTaskPO: studentTaskPOS) {
            if (studentTaskPO.isChecked())
                studentIdsIsChecked.add(studentTaskPO.getStudentId());
            else
                studentIdsIsNotChecked.add(studentTaskPO.getStudentId());
        }

        studenIdsAll.removeAll(studentIdsIsChecked);
        studenIdsAll.removeAll(studentIdsIsNotChecked);
        List<Long> studentIdsIsNotFinished = studenIdsAll;

        List<StudentInfoPO> studentInfoPOSIsNotFinished = studentInfoMapper.selectBatchIds(studentIdsIsNotFinished);
        List<StudentInfoPO> studentInfoPOSIsNotChecked = studentInfoMapper.selectBatchIds(studentIdsIsNotChecked);
        List<StudentInfoPO> studentTaskPOSIsChecked = studentInfoMapper.selectBatchIds(studentIdsIsChecked);

        List<StudentInfo> studentTaskIsChecked = new ArrayList<>();
        for(StudentInfoPO studentInfoPO: studentTaskPOSIsChecked) {
            studentTaskIsChecked.add(new StudentInfo(
                    studentInfoPO.getStudentId(),
                    studentInfoPO.getName(),
                    studentInfoPO.getAvatar(),
                    true
            ));
        }

        List<StudentInfo> studentTaskIsNotChecked = new ArrayList<>();
        for(StudentInfoPO studentInfoPO: studentInfoPOSIsNotChecked) {
            studentTaskIsNotChecked.add(new StudentInfo(
                    studentInfoPO.getStudentId(),
                    studentInfoPO.getName(),
                    studentInfoPO.getAvatar(),
                    true
            ));
        }

        List<StudentInfo> studentTaskIsNotFinished = new ArrayList<>();
        for(StudentInfoPO studentInfoPO: studentInfoPOSIsNotFinished) {
            studentTaskIsNotFinished.add(new StudentInfo(
                    studentInfoPO.getStudentId(),
                    studentInfoPO.getName(),
                    studentInfoPO.getAvatar(),
                    true
            ));
        }

        StudentTaskProgressInfo studentTaskProgressInfo = new StudentTaskProgressInfo();
        studentTaskProgressInfo.setTaskId(taskId);
        studentTaskProgressInfo.setStudentTaskIsChecked(studentTaskIsChecked);
        studentTaskProgressInfo.setStudentTaskIsNotChecked(studentTaskIsNotChecked);
        studentTaskProgressInfo.setStudentTaskIsNotFinished(studentTaskIsNotFinished);

        return studentTaskProgressInfo;
    }

    @Override
    public StudentTaskInfo getStudentTask(Long taskId, Long studentId) {
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
