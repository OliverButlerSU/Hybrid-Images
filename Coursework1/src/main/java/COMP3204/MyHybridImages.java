package COMP3204;

import static cern.clhep.PhysicalConstants.pi;

import org.openimaj.image.MBFImage;

public class MyHybridImages {

    /**
     * Compute a hybrid image combining low-pass and high-pass filtered images
     *
     * @param lowImage the image to which apply the low pass filter
     * @param lowSigma the standard deviation of the low-pass filter
     * @param highImage the image to which apply the high pass filter
     * @param highSigma the standard deviation of the low-pass component of computing the high-pass
     *     filtered image
     * @return the computed hybrid image
     */
    public static MBFImage makeHybrid(
            MBFImage lowImage, float lowSigma, MBFImage highImage, float highSigma) {
        // implement your hybrid images functionality here.
        // Your submitted code must contain this method, but you can add
        // additional static methods or implement the functionality through
        // instance methods on the `MyHybridImages` class of which you can create
        // an instance of here if you so wish.
        // Note that the input images are expected to have the same size, and the output
        // image will also have the same height & width as the inputs.
        return lowImage;
    }

    public static float[][] makeGaussianKernel(float sigma) {
        // Use this function to create a 2D gaussian kernel with standard deviation sigma.
        // The kernel values should sum to 1.0, and the size should be floor(8*sigma+1) or
        // floor(8*sigma+1)+1 (whichever is odd) as per the assignment specification.
        int kernelSize = (int) Math.floor(8*sigma+1);
        if(kernelSize %2 == 0){
            kernelSize++;
        }

        float[][] kernel = new float[kernelSize][kernelSize];

        int yOffset = Math.floorDiv(kernel.length, 2);
        int xOffset = Math.floorDiv(kernel[0].length, 2);

        for(int y = 0; y<kernelSize; y++){
            for(int x = 0; x < kernelSize; x++){
                kernel[y][x] = calculateGaussianValue(x-xOffset, y-yOffset, sigma);
                System.out.println(kernel[y][x]);
            }
        }
        return kernel;
    }

    public static float calculateGaussianValue(int x, int y, float sigma){
        return (float) (1 / (2 * pi * sigma * sigma) * Math.exp(- (x*x + y*y)/(2*sigma*sigma)));
    }

    public static void main(String[] args){
        makeGaussianKernel(1f);
    }
}
