package COMP3204;

import org.openimaj.image.FImage;
import org.openimaj.image.processor.SinglebandImageProcessor;

public class MyConvolution implements SinglebandImageProcessor<Float, FImage> {

    private final float[][] kernel;

    public MyConvolution(float[][] kernel) {
        this.kernel = kernel;
    }

    @Override
    public void processImage(FImage image) {
        //Create the padded image
        FImage paddedImage = padImage(image);

        //For each pixel in the image, calculate the convolution using the kernel and set that to its new position
        for (int y=0; y<image.getHeight(); y++) {
            for(int x=0; x<image.getWidth(); x++) {
                image.pixels[y][x] = calculateConvolution(paddedImage, x ,y);
            }
        }
    }

    /**
     * Apply a convolution multiplication at position x y in an image
     * @param paddedImage input image padded with black pixels
     * @param x x position of pixel
     * @param y y position of pixel
     * @return float value of the convolution multiplication
     */
    private float calculateConvolution(FImage paddedImage, int x, int y){
        //Create sum of pixel value
        float pixel = 0f;

        //Iterate over the kernel
        for(int i = 0; i < kernel.length; i++){
            for(int j = 0; j < kernel[i].length; j++){
                //Sum the multiplication of kernel position and the padded image positon with offset
                pixel += kernel[kernel.length-i-1][kernel[0].length-j-1] * paddedImage.getPixel(x+j,y+i);
            }
        }

        return pixel;
    }

    /**
     * Pad an image with black pixels such that applying a convolutional layer will result in the same sized image
     * @param image Input image
     * @return Padded image with black pixels
     */
    private FImage padImage(FImage image){

        //Calculate the offsets of both the x and y coordinates
        int yOffset = Math.floorDiv(kernel.length, 2);
        int xOffset = Math.floorDiv(kernel[0].length, 2);

        //Create a new image of kernel length + original image size for both axis
        FImage paddedImage = new FImage(kernel[0].length-1 + image.width, kernel.length-1 + image.height);

        //Iterate over the new image pixels
        for (int y=0; y<paddedImage.getHeight(); y++) {
            for(int x=0; x<paddedImage.getWidth(); x++) {


                if(x < xOffset || x >= image.width + xOffset || y < yOffset || y >= image.height + yOffset){
                    //If the position is in the border padding, do nothing (keep pixel black)
                } else{
                    //Else calculate the offset from the original image and set that pixel to the new image
                    paddedImage.pixels[y][x] = image.getPixel(x-xOffset, y-yOffset);
                }
            }
        }


        return paddedImage;
    }
}
