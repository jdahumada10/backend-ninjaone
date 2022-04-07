package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.database.SampleRepository;
import com.ninjaone.backendinterviewproject.model.SampleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SampleServiceTest {
    public static final String ID = "12345";

    @Mock
    private SampleRepository sampleRepository;

    @InjectMocks
    private SampleService testObject;

    private SampleEntity sampleEntity;

    @BeforeEach
    void setup(){
        sampleEntity = SampleEntity.builder()
                .id(ID)
                .value("value")
                .build();
    }

    @Test
    void getSampleData() {
        when(sampleRepository.findById(ID)).thenReturn(Optional.of(sampleEntity));
        Optional<SampleEntity> sampleEntityOptional = testObject.getSampleEntity(ID);
        SampleEntity actualEntity = sampleEntityOptional.orElse(null);
        assert actualEntity != null;
        assertEquals(sampleEntity.getValue(), actualEntity.getValue());
    }

    @Test
    void saveSampleData() {
        when(sampleRepository.save(sampleEntity)).thenReturn(sampleEntity);
        assertEquals(sampleEntity, testObject.saveSampleEntity(sampleEntity));
    }

    @Test
    void deleteSampleData(){
        doNothing().when(sampleRepository).deleteById(ID);
        testObject.deleteSampleEntity(ID);
        Mockito.verify(sampleRepository, times(1)).deleteById(ID);
    }
}
