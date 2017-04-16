package rasterizer;

import rasterizer.brezenham.BrezenhamLineRasterizer;
import rasterizer.dda.DDALineRasterizer;
import rasterizer.step.StepByStepLineRasterizer;

public enum RasterizeMethod
{
    BrezenhamLineRasterizer(new BrezenhamLineRasterizer()),
    DDALineRasterizer(new DDALineRasterizer()),
    StepByStepLineRasterizer(new StepByStepLineRasterizer());

    LineRasterizer lineRasterizer;

    RasterizeMethod(LineRasterizer lineRasterizer) {
        this.lineRasterizer = lineRasterizer;
    }

    public LineRasterizer getLineRasterizer() {
        return lineRasterizer;
    }


    @Override
    public String toString() {
        return lineRasterizer.toString();
    }
}
