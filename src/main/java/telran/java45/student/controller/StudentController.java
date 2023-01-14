package telran.java45.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import telran.java45.student.dto.ScoreDto;
import telran.java45.student.dto.StudentCreateDto;
import telran.java45.student.dto.StudentDto;
import telran.java45.student.dto.StudentUpdateDto;
import telran.java45.student.service.StudentService;
import telran.java45.student.service.StudentServiceImpl;

@RestController
public class StudentController {
	@Autowired
	StudentService studentService;
	
	@PostMapping("/student")
	public boolean addStudent(@RequestBody StudentCreateDto studentCreateDto) {
		return studentService.addStudent(studentCreateDto);
	}	
	@GetMapping("/student/{id}")
	public StudentDto findStudent(@PathVariable Integer id) {
		return studentService.findStudent(id);
	}	
	@DeleteMapping("/student/{id}")
	public StudentDto deleteStudent(@PathVariable Integer id) {
		return studentService.removeStudent(id);
	}	
	@PutMapping("/student/{id}")
	public StudentCreateDto updateStudent(@PathVariable Integer id, @RequestBody StudentUpdateDto student) {
		return studentService.updateStudent(id, student);
	}
	@PutMapping("/score/student/{id}")
	public Boolean addScore(@PathVariable Integer id,@RequestBody ScoreDto score) {
		return studentService.addScore(id, score);
	}
	@GetMapping("/students/name/{name}")
	public List<StudentDto> findStudentsByName(@PathVariable String name){
		return studentService.findStudentsByName(name);
	}
	@PostMapping("/quantity/students")
	public Long quantity(@RequestBody List<String> names) {
		return studentService.getStudentsNamesQuantity(names);
	}
	@GetMapping("/students/exam/{exam}/minscore/{minScore}")
	public List<StudentDto> findStudentsByMinScore(@PathVariable String exam, @PathVariable Integer minScore){
		return studentService.getStudentsByExamMinScore(exam, minScore);
	}

}
