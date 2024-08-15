package net.javaguides.ems.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.ems.dto.DepartmentDto;
import net.javaguides.ems.entity.Department;
import net.javaguides.ems.exception.ResourceNotFoundException;
import net.javaguides.ems.mapper.DepartmentMapper;
import net.javaguides.ems.repository.DepartmentRepository;
import net.javaguides.ems.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class DeploymentServiceImpl implements DepartmentService {
    private DepartmentRepository departmentRepository;
    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
       Department department = DepartmentMapper.mapToDepartment(departmentDto);
       Department savedDepartment = departmentRepository.save(department);
       return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new ResourceNotFoundException("Department with id " + departmentId + " not found")
        );
        return DepartmentMapper.mapToDepartmentDto(department);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(DepartmentMapper::mapToDepartmentDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto updateDepartment) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(
                () -> new ResourceNotFoundException("Department with id " + departmentId + " not found")
        );
        department.setDepartmentName(updateDepartment.getDepartmentName());
        department.setDepartmentDescription(updateDepartment.getDepartmentDescription());
        Department savedDepartment = departmentRepository.save(department);
        return DepartmentMapper.mapToDepartmentDto(savedDepartment);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        departmentRepository.findById(departmentId).orElseThrow(
                () -> new ResourceNotFoundException("Department with id " + departmentId + " not found")
        );
        departmentRepository.deleteById(departmentId);
    }
}
