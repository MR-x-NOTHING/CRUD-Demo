package com.example.CRUD_Demo.service;

import com.example.CRUD_Demo.DTO.CreateRequestDTO;
import com.example.CRUD_Demo.DTO.CreateResponseDTO;
import com.example.CRUD_Demo.DTO.UpdateRequestDTO;
import com.example.CRUD_Demo.DTO.UpdateResponseDTO;
import com.example.CRUD_Demo.entity.Student;
import com.example.CRUD_Demo.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

//    private final ResourceUrlProvider resourceUrlProvider;
    private StudentRepo studentRepo;
    @Autowired
    public StudentService(StudentRepo studentRepo/*,ResourceUrlProvider resourceUrlProvider*/){

        this.studentRepo = studentRepo;
//        this.resourceUrlProvider = resourceUrlProvider;
    }
    public CreateResponseDTO createStudent(CreateRequestDTO createRequestDTO){
        Student student = mapToEntity(createRequestDTO);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        Student response = studentRepo.save(student);
        return mapToDTO(response);
    }

    public CreateResponseDTO getStudent(Integer id){
        Optional<Student> studentResp = studentRepo.findByIdAndDeletedIsFalse((id));

        if(studentResp.isPresent()){
            return mapToDTO(studentResp.get());
        }
        return null;
    }

    public List<CreateResponseDTO> getAllStudent(){
        List<Student> studentList = studentRepo.findByDeletedIsFalse();

        return studentList.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public UpdateResponseDTO updateStudent(Integer id, UpdateRequestDTO studentReq){
        Optional<Student> existingStudent = studentRepo.findByIdAndDeletedIsFalse((id));
        if (existingStudent.isEmpty()) return null;

        Student studentToSave = existingStudent.get();
        studentToSave.setAge(studentReq.getAge());
        studentToSave.setMob(studentReq.getMob());
        studentToSave.setSub(studentReq.getSub());
        studentToSave.setUpdatedAt(LocalDateTime.now());
        studentToSave.setDeleted(false);

        Student savedStudent = studentRepo.save(studentToSave);
        return mapToUpdateDTO(savedStudent);
    }

    public Boolean deleteStudent(Long id){
        Boolean isStudent = studentRepo.existsById(Math.toIntExact(id));
        if (!isStudent) return false;

        studentRepo.deleteById(Math.toIntExact(id));

        return true;
    }

    public Boolean deleteStudentSoftly(Integer id){
        Optional<Student> existingStudent = studentRepo.findByIdAndDeletedIsFalse(id);
        if (existingStudent.isEmpty()) {
            return false;
        }
        Student studentToSave =  existingStudent.get();
        studentToSave.setDeleted(true);
        studentRepo.save(studentToSave);
        return true;
    }

    private Student mapToEntity (CreateRequestDTO createRequestDTO){
        Student student = new Student();
        student.setName(createRequestDTO.getName());
        student.setAge(createRequestDTO.getAge());
        student.setMob(createRequestDTO.getMob());
        student.setSub(createRequestDTO.getSub());

        student.setDeleted(false);
        return student;
    }

    private CreateResponseDTO mapToDTO(Student student){
        CreateResponseDTO createResponseDTO = new CreateResponseDTO();
        createResponseDTO.setName(student.getName());
        createResponseDTO.setAge(student.getAge());
        createResponseDTO.setMob(student.getMob());
        createResponseDTO.setSub(student.getSub());
        createResponseDTO.setId(student.getId());
        createResponseDTO.setCreatedAt(student.getCreatedAt());
        createResponseDTO.setUpdatedAt(student.getUpdatedAt());

        createResponseDTO.setMessage("student saved successfully ");
        return createResponseDTO;
    }

    private UpdateResponseDTO mapToUpdateDTO(Student student){
        UpdateResponseDTO responseDTO = new UpdateResponseDTO();

        responseDTO.setAge(student.getAge());
        responseDTO.setId(student.getId());
        responseDTO.setName(student.getName());
        responseDTO.setAge(student.getAge());
        responseDTO.setUpdatedAt(student.getUpdatedAt());
        responseDTO.setMob(student.getMob());
        responseDTO.setSub(student.getSub());

        return responseDTO;
    }
}
