package org.ably.farm_management.controller;

import jakarta.validation.ConstraintViolationException;
import org.ably.farm_management.dto.FieldDTO;
import org.ably.farm_management.service.FieldService;
import org.ably.farm_management.vm.FieldVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FieldControllerTest {

    @Mock
    private FieldService fieldService;

    @InjectMocks
    private FieldController fieldController;

    private FieldVM validFieldVM;
    private FieldDTO validFieldDTO;

    @BeforeEach
    void setUp() {
        validFieldVM = FieldVM.builder()
                .name("Test Field")
                .area(2000.0)
                .farmId(1L)
                .build();

        validFieldDTO = FieldDTO.builder()
                .id(1L)
                .name("Test Field")
                .area(2000.0)
                .farmId(1L)
                .build();
    }

    @Test
    void testCreate_ValidField_ReturnsCreatedField() {
        // Arrange
        when(fieldService.create(validFieldVM)).thenReturn(validFieldDTO);

        // Act
        FieldDTO createdField = fieldController.create(validFieldVM);

        // Assert
        assertNotNull(createdField);
        assertEquals(validFieldDTO.getId(), createdField.getId());
        verify(fieldService, times(1)).create(validFieldVM);
    }

    @Test
    void testFindAll_MultipleFields_ReturnsFieldList() {
        // Arrange
        List<FieldDTO> fieldList = Arrays.asList(
                validFieldDTO,
                FieldDTO.builder()
                        .id(2L)
                        .name("Another Field")
                        .area(3000.0)
                        .farmId(2L)
                        .build()
        );
        when(fieldService.findAll()).thenReturn(fieldList);

        // Act
        List<FieldDTO> returnedFields = fieldController.findAll();

        // Assert
        assertNotNull(returnedFields);
        assertEquals(2, returnedFields.size());
        verify(fieldService, times(1)).findAll();
    }

    @Test
    void testFindAll_NoFields_ReturnsEmptyList() {
        // Arrange
        when(fieldService.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<FieldDTO> returnedFields = fieldController.findAll();

        // Assert
        assertNotNull(returnedFields);
        assertTrue(returnedFields.isEmpty());
    }

    @Test
    void testDelete_ExistingField_DeletesSuccessfully() {
        // Arrange
        Long fieldId = 1L;
        doNothing().when(fieldService).delete(fieldId);

        // Act
        fieldController.delete(fieldId);

        // Assert
        verify(fieldService, times(1)).delete(fieldId);
    }

    @Test
    void testUpdate_ValidField_ReturnsUpdatedField() {
        // Arrange
        Long fieldId = 1L;
        FieldVM updateFieldVM = FieldVM.builder()
                .name("Updated Field")
                .area(2500.0)
                .farmId(1L)
                .build();

        FieldDTO updatedFieldDTO = FieldDTO.builder()
                .id(fieldId)
                .name("Updated Field")
                .area(2500.0)
                .farmId(1L)
                .build();

        when(fieldService.update(fieldId, updateFieldVM)).thenReturn(updatedFieldDTO);

        // Act
        FieldDTO updatedField = fieldController.update(fieldId, updateFieldVM);

        // Assert
        assertNotNull(updatedField);
        assertEquals("Updated Field", updatedField.getName());
        assertEquals(2500.0, updatedField.getArea());
        verify(fieldService, times(1)).update(fieldId, updateFieldVM);
    }

    @Test
    void testFindById_ExistingField_ReturnsField() {
        // Arrange
        Long fieldId = 1L;
        when(fieldService.findById(fieldId)).thenReturn(validFieldDTO);

        // Act
        FieldDTO foundField = fieldController.findById(fieldId);

        // Assert
        assertNotNull(foundField);
        assertEquals(validFieldDTO.getId(), foundField.getId());
        assertEquals(validFieldDTO.getName(), foundField.getName());
    }

    @Test
    void testFindById_NonExistingField_ThrowsNotFoundException() {
        // Arrange
        Long nonExistingFieldId = 999L;
        when(fieldService.findById(nonExistingFieldId))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Act & Assert
        assertThrows(ResponseStatusException.class,
                () -> fieldController.findById(nonExistingFieldId));
    }
}