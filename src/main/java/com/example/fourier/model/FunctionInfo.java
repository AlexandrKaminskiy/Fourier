package com.example.fourier.model;


import com.example.fourier.processing.CurveTypeProcessing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FunctionInfo {
    private String name;
    private CurveTypeProcessing processing;
    private double a;
    private double f;
    private double fi;
}
