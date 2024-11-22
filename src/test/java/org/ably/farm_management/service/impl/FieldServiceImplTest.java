package org.ably.farm_management.service.impl;

import org.ably.farm_management.domain.entity.Farm;
import org.ably.farm_management.domain.entity.Field;
import org.ably.farm_management.dto.FieldDTO;
import org.ably.farm_management.dto.TreeDTO;
import org.ably.farm_management.exception.BusinessException;
import org.ably.farm_management.mapper.FieldMapper;
import org.ably.farm_management.repository.FieldRepository;
import org.ably.farm_management.service.FarmService;
import org.ably.farm_management.vm.FieldVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FieldServiceImplTest {

    @Mock
    private FieldRepository fieldRepository;

    @Mock
    private FieldMapper fieldMapper;

    @Mock
    private FarmService farmService;

    @InjectMocks
    private FieldServiceImpl fieldService;

    private Field field;
    private FieldDTO fieldDTO;
    private FieldVM fieldVM;
    private List<TreeDTO> trees;

    @BeforeEach
    void setUp() {
        // Initialize test data
        field = new Field();
        field.setId(1L);
        field.setName("Test Field");
        field.setArea(2000.0);
        field.setFarm(new Farm());

        trees = new ArrayList<>();
        trees.add(TreeDTO.builder().id(1L).build());

        fieldDTO = FieldDTO.builder()
                .id(1L)
                .name("Test Field")
                .area(2000.0)
                .farmId(1L)
                .trees(trees)
                .build();

        fieldVM = FieldVM.builder()
                .name("Test Field")
                .area(2000.0)
                .farmId(1L)
                .build();
    }

    @Nested
    @DisplayName("Save Operations")
    class SaveOperations {
        @Test
        @DisplayName("Should successfully save a valid field")
        void save_ValidField_ReturnsSavedField() {
            when(fieldRepository.save(any(Field.class))).thenReturn(field);
            when(fieldMapper.entityToDTO(any(Field.class))).thenReturn(fieldDTO);

            FieldDTO result = fieldService.save(field);

            assertNotNull(result);
            assertEquals(fieldDTO.getId(), result.getId());
            assertEquals(fieldDTO.getName(), result.getName());
            verify(fieldRepository).save(field);
        }
    }

    @Nested
    @DisplayName("Create Operations")
    class CreateOperations {
        @Test
        @DisplayName("Should successfully create a field with valid data")
        void create_ValidField_ReturnsCreatedField() {
            // Arrange
            doNothing().when(farmService).existsById(anyLong());
            when(farmService.findAreaById(anyLong())).thenReturn(5000.0);
            when(fieldRepository.sumAreaByFarmId(anyLong())).thenReturn(1);
            when(fieldRepository.countByFarmId(anyLong())).thenReturn(1L);
            when(fieldMapper.vmToEntity(any(FieldVM.class))).thenReturn(field);
            when(fieldRepository.save(any(Field.class))).thenReturn(field);
            when(fieldMapper.entityToDTO(any(Field.class))).thenReturn(fieldDTO);

            // Act
            FieldDTO result = fieldService.create(fieldVM);

            // Assert
            assertNotNull(result);
            assertEquals(fieldDTO.getName(), result.getName());
            verify(fieldRepository).save(any(Field.class));
        }

        @Test
        @DisplayName("Should throw exception when farm has maximum fields")
        void create_MaxFieldsReached_ThrowsException() {
            // Arrange
            doNothing().when(farmService).existsById(anyLong());
            when(fieldRepository.countByFarmId(anyLong())).thenReturn(5L);

            // Act & Assert
            BusinessException exception = assertThrows(BusinessException.class,
                    () -> fieldService.create(fieldVM));
            assertTrue(exception.getMessage().contains("Farm can have maximum 5 fields"));
        }
    }

    @Nested
    @DisplayName("Update Operations")
    class UpdateOperations {
        @Test
        @DisplayName("Should successfully update existing field")
        void update_ExistingField_ReturnsUpdatedField() {
            // Arrange
            Long fieldId = 1L;
            doNothing().when(farmService).existsById(anyLong());
            when(fieldRepository.existsById(anyLong())).thenReturn(true);
            when(farmService.findAreaById(anyLong())).thenReturn(5000.0);
            when(fieldRepository.sumAreaByFarmId(anyLong())).thenReturn(1000);
            when(fieldMapper.vmToEntity(any(FieldVM.class))).thenReturn(field);
            when(fieldRepository.save(any(Field.class))).thenReturn(field);
            when(fieldMapper.entityToDTO(any(Field.class))).thenReturn(fieldDTO);

            // Act
            FieldDTO result = fieldService.update(fieldId, fieldVM);

            // Assert
            assertNotNull(result);
            assertEquals(fieldDTO.getName(), result.getName());
            verify(fieldRepository).save(any(Field.class));
        }
    }

    @Nested
    @DisplayName("Delete Operations")
    class DeleteOperations {
        @Test
        @DisplayName("Should successfully delete existing field")
        void delete_ExistingField_DeletesSuccessfully() {
            // Arrange
            Long fieldId = 1L;
            when(fieldRepository.existsById(fieldId)).thenReturn(true);
            doNothing().when(fieldRepository).deleteById(fieldId);

            // Act
            fieldService.delete(fieldId);

            // Assert
            verify(fieldRepository).deleteById(fieldId);
        }

        @Test
        @DisplayName("Should throw exception when deleting non-existing field")
        void delete_NonExistingField_ThrowsException() {
            // Arrange
            Long fieldId = 999L;
            when(fieldRepository.existsById(fieldId)).thenReturn(false);

            // Act & Assert
            assertThrows(BusinessException.class, () -> fieldService.delete(fieldId));
            verify(fieldRepository, never()).deleteById(any());
        }
    }

    @Nested
    @DisplayName("Find Operations")
    class FindOperations {
        @Test
        @DisplayName("Should return field when finding by valid ID")
        void findById_ExistingField_ReturnsField() {
            // Arrange
            Long fieldId = 1L;
            when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));
            when(fieldMapper.entityToDTO(field)).thenReturn(fieldDTO);

            // Act
            FieldDTO result = fieldService.findById(fieldId);

            // Assert
            assertNotNull(result);
            assertEquals(fieldDTO.getId(), result.getId());
        }

        @Test
        @DisplayName("Should return all fields")
        void findAll_ReturnsAllFields() {
            // Arrange
            List<Field> fields = Arrays.asList(field);
            List<FieldDTO> fieldDTOs = Arrays.asList(fieldDTO);
            when(fieldRepository.findAll()).thenReturn(fields);
            when(fieldMapper.toDTOList(fields)).thenReturn(fieldDTOs);

            // Act
            List<FieldDTO> results = fieldService.findAll();

            // Assert
            assertFalse(results.isEmpty());
            assertEquals(1, results.size());
        }

        @Test
        @DisplayName("Should return field area when finding by ID")
        void findAreaById_ExistingField_ReturnsArea() {
            // Arrange
            Long fieldId = 1L;
            Double expectedArea = 2000.0;
            when(fieldRepository.findAreaById(fieldId)).thenReturn(expectedArea);

            // Act
            Double result = fieldService.findAreaById(fieldId);

            // Assert
            assertEquals(expectedArea, result);
        }
    }

    @Nested
    @DisplayName("Validation Operations")
    class ValidationOperations {
        @Test
        @DisplayName("Should pass when field exists")
        void existsById_ExistingField_NoException() {
            // Arrange
            Long fieldId = 1L;
            when(fieldRepository.existsById(fieldId)).thenReturn(true);

            // Act & Assert
            assertDoesNotThrow(() -> fieldService.existsById(fieldId));
        }

        @Test
        @DisplayName("Should throw exception when field doesn't exist")
        void existsById_NonExistingField_ThrowsException() {
            // Arrange
            Long fieldId = 999L;
            when(fieldRepository.existsById(fieldId)).thenReturn(false);

            // Act & Assert
            assertThrows(BusinessException.class, () -> fieldService.existsById(fieldId));
        }
    }
}