package com.example.CRUD_Demo.controller;

import com.example.CRUD_Demo.DTO.CreateRequestDTO;
import com.example.CRUD_Demo.DTO.CreateResponseDTO;
import com.example.CRUD_Demo.DTO.UpdateRequestDTO;
import com.example.CRUD_Demo.DTO.UpdateResponseDTO;
import com.example.CRUD_Demo.entity.Student;
import com.example.CRUD_Demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }


    @PostMapping("/create")
    public ResponseEntity<CreateResponseDTO> createStudent(@Valid  @RequestBody CreateRequestDTO createRequestDTO){
        CreateResponseDTO createStudent = studentService.createStudent(createRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createStudent);
    }

    @GetMapping("/get")
    public ResponseEntity<CreateResponseDTO> readStudent(@RequestParam Integer id){
        CreateResponseDTO studentResp = studentService.getStudent(id);

        if(studentResp == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(studentResp);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CreateResponseDTO>> readAllStudent(){
        List<CreateResponseDTO> studentList = studentService.getAllStudent();

        if(studentList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(studentList);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateResponseDTO> updateStudent(@RequestParam Integer id, @RequestBody UpdateRequestDTO studentReq){
        UpdateResponseDTO studentResp = studentService.updateStudent(id,studentReq);

        if (studentResp == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentResp);
    }

    @DeleteMapping("/delete/{id}")
    public Boolean deleteStudent(@PathVariable Long id){
        Boolean isDeleted = studentService.deleteStudent(id);

        if (!isDeleted){
            return ResponseEntity.notFound().build().hasBody();
        }
        return ResponseEntity.ok("Record deleted").hasBody();
    }

    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<String> deleteStudentSoftly(@PathVariable Integer id){
        Boolean isDeleted = studentService.deleteStudentSoftly(id);

        if (!isDeleted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Record deleted");
    }
}
