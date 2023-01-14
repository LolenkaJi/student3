package telran.java45.student.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.java45.student.dao.StudentRepository;
import telran.java45.student.dto.ScoreDto;
import telran.java45.student.dto.StudentCreateDto;
import telran.java45.student.dto.StudentDto;
import telran.java45.student.dto.StudentUpdateDto;
import telran.java45.student.dto.exceptions.StudentNotFoundException;
import telran.java45.student.model.Student;

@Component
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
	
	final StudentRepository studentRepository;
	final ModelMapper modelMapper;

	@Override
	public Boolean addStudent(StudentCreateDto studentCreateDto) {
		if(studentRepository.findById(studentCreateDto.getId()).isPresent()) {
			return false;
		};					
		Student student = new Student(studentCreateDto.getId(), 
				studentCreateDto.getName(), studentCreateDto.getPassword());
		studentRepository.save(student);
		return true;
	}

	@Override
	public StudentDto findStudent(Integer id) {
		Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException());
			return modelMapper.map(student, StudentDto.class);					
	}
	@Override
	public StudentDto removeStudent(Integer id) {
		Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException());
		studentRepository.deleteById(id);
		return modelMapper.map(student, StudentDto.class);
	}

	@Override
	public StudentCreateDto updateStudent(Integer id, StudentUpdateDto studentUpdateDto) {
		Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException());
		if(studentUpdateDto.getName() != null) {
			student.setName(studentUpdateDto.getName());
		}
		if(studentUpdateDto.getPassword() != null) {
			student.setPassword(studentUpdateDto.getPassword());
		}
		studentRepository.save(student);
		return modelMapper.map(student, StudentCreateDto.class);	
	}

	@Override
	public Boolean addScore(Integer id, ScoreDto score) {
		Student student = studentRepository.findById(id).orElseThrow(()-> new StudentNotFoundException());
		boolean res = student.addScore(score.getExamName(), score.getScore());
		studentRepository.save(student);
		return res;
	}

	@Override
	public List<StudentDto> findStudentsByName(String name) {
		return studentRepository.findByNameIgnoreCase(name)
				.map(s -> modelMapper.map(s, StudentDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Long getStudentsNamesQuantity(List<String> names) {
		return studentRepository.countStudentsByNameInIgnoreCase(names);
//		return StreamSupport.stream(studentRepository.findAll().spliterator(), false)
//				.filter(s -> names.contains(s.getName()))
//				.count();
	}

	@Override
	public List<StudentDto> getStudentsByExamMinScore(String exam, Integer minScore) {	
		return studentRepository.findByExamAndScore(exam, minScore)
				.map(s -> modelMapper.map(s, StudentDto.class))
				.collect(Collectors.toList());
	}

}
