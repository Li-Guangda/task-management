package com.example.taskmanagement.controller;

import com.example.taskmanagement.controller.response.Result;
import com.example.taskmanagement.model.Classroom;
import com.example.taskmanagement.service.impl.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClassController {

    private ClassroomService classroomService;

    @PostMapping("/classroom")
    public ResponseEntity<?> createClassroom(@RequestBody @Validated Classroom classroom) {
        classroomService.createClassroom(classroom);
        return ResponseEntity.ok(Result.onlyMessage("Create classroom successfully"));
    }

    @PostMapping("/classroom/{classId}/student/{studentId}")
    public ResponseEntity<?> addStudentToClassroom(@PathVariable Long classId, @PathVariable Long studentId) {
        classroomService.addStudentToClassroom(classId, studentId);
        return ResponseEntity.ok(Result.onlyMessage("Add the student to the classroom successfully"));
    }

    @DeleteMapping("/classrooms")
    public ResponseEntity<?> deleteAllClassrooms() {
        classroomService.deleteAllClassrooms();
        return ResponseEntity.ok(Result.onlyMessage("Deleted all classrooms successfully"));
    }

    @DeleteMapping("/classroom/{classId}")
    public ResponseEntity<?> deleteClassroom(@PathVariable Long classId) {
        classroomService.deleteClassroom(classId);
        return ResponseEntity.ok(Result.onlyMessage("Deleted the classroom successfully"));
    }

    @PutMapping("/classroom/{classId}")
    public ResponseEntity<?> updateClassroom(@PathVariable Long classId, @RequestBody Classroom classroom) {
        classroom.setClassId(classId);
        classroomService.updateClassroom(classroom);
        return ResponseEntity.ok(Result.onlyMessage("Updated the classroom successfully"));
    }

    @GetMapping("/classrooms")
    public ResponseEntity<?> getAllClasserooms() {
        List<Classroom> classrooms = classroomService.getAllClassrooms();
        return ResponseEntity.ok(new Result<>("Got all classrooms successfully", classrooms));
    }

    @GetMapping("/lecturer/{lecturerId}/classrooms")
    public ResponseEntity<?> getAllClassroomsOfLecturer(@PathVariable Long lecturerId) {
        List<Classroom> classrooms = classroomService.getAllClassroomOfLecturer(lecturerId);
        return ResponseEntity.ok(new Result<>("Got all classrooms successfully", classrooms));
    }

    @GetMapping("/classroom/{classId}")
    public ResponseEntity<?> getClassroom(@PathVariable Long classId) {
        Classroom classroom = classroomService.getClassroom(classId);
        return ResponseEntity.ok(new Result<>("Got the classroom successfully", classroom));
    }

    @Autowired
    public void setClassroomService(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }
}