package COMP3204;

import java.io.File;
import java.io.IOException;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.convolution.FConvolution;
import org.openimaj.image.processor.SinglebandImageProcessor;

public class MyConvolution implements SinglebandImageProcessor<Float, FImage> {

    public static void main(String[] args) throws IOException {
        FImage image = ImageUtilities.readF(new File("hybrid-images/data/cat.bmp"));
        FImage copy = ImageUtilities.readF(new File("hybrid-images/data/cat.bmp"));

        float[][] kernel = new float[][] {{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
        //float[][] kernel = new float[][] {{0.11f} , {0.22f}, {0.33f}};
        //float[][] kernel = new float[][] {{0.11f, 0.22f, 0.33f}};

        MyConvolution myConvolution = new MyConvolution(kernel);
        myConvolution.processImage(image);
        DisplayUtilities.display(image);

//        FConvolution conv = new FConvolution(kernel);
//        conv.processImage(copy);
//        DisplayUtilities.display(copy);
//
//        float difference = 0f;
//
//        for (int y=1; y<image.getHeight()-2; y++) {
//            for(int x=1; x<image.getWidth()-2; x++) {
//                difference += Math.abs(image.getPixel(x,y) - copy.getPixel(x,y));
//                System.out.println(image.getPixel(x,y) + " " + copy.getPixel(x,y));
//            }
//        }
//
//        System.out.println(difference);
    }


    private final float[][] kernel;

    public MyConvolution(float[][] kernel) {
        // note that like the image pixels kernel is indexed by [row][column]
        this.kernel = kernel;
    }

    @Override
    public void processImage(FImage image) {
        FImage paddedImage = padImage(image);
        System.out.println(paddedImage.width + " " + paddedImage.height);
        System.out.println(image.width + " " + image.height);

        for (int y=0; y<image.getHeight(); y++) {
            for(int x=0; x<image.getWidth(); x++) {
                image.pixels[y][x] = calculateConvolution(paddedImage, x ,y);
            }
        }
    }

    private float calculateConvolution(FImage paddedImage, int x, int y){
        int yOffset = Math.floorDiv(kernel.length, 2) - 1;
        int xOffset = Math.floorDiv(kernel[0].length, 2) - 1;

        float pixel = 0f;

        for(int i = 0; i < kernel.length; i++){
            for(int j = 0; j < kernel[i].length; j++){
                pixel += kernel[kernel.length-i-1][kernel[0].length-j-1] * paddedImage.getPixel(x+j,y+i);
            }
        }

        return pixel;
    }

    //If the convolution is a nxm pad it by (n/2)-1 on left and right, (m/2)-1 on up and down

    private FImage padImage(FImage image){

        int yOffset = Math.floorDiv(kernel.length, 2);
        int xOffset = Math.floorDiv(kernel[0].length, 2);

        FImage paddedImage = new FImage(kernel[0].length-1 + image.width, kernel.length-1 + image.height);

        for (int y=0; y<paddedImage.getHeight(); y++) {
            for(int x=0; x<paddedImage.getWidth(); x++) {
                if(x < xOffset || x >= image.width + xOffset || y < yOffset || y >= image.height + yOffset){
                    //DoNothing
                } else{
                    paddedImage.pixels[y][x] = image.getPixel(x-xOffset, y-yOffset);
                }
            }
        }


        return paddedImage;
    }
}
