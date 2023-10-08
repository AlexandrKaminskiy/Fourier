package com.example.fourier.model;

import com.example.fourier.processing.CurveTypeProcessing;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FunctionState {

    private String title;
    private CurveTypeProcessing processing;

    @Override
    public String toString() {
        return title;
    }
}
