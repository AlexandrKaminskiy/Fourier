package com.example.fourier.model;

import com.example.fourier.processing.CurveTypeProcessing;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FunctionState {

    public String title;
    public CurveTypeProcessing processing;

    @Override
    public String toString() {
        return title;
    }
}
