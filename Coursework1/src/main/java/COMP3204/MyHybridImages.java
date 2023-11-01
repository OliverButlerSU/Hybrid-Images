package COMP3204;

import static cern.clhep.PhysicalConstants.pi;

import java.io.File;
import java.io.IOException;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
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
        float[][] lowImageKernel = makeGaussianKernel(lowSigma);
        float[][] highImageKernel = makeGaussianKernel(highSigma);

        MyConvolution lowConvolution = new MyConvolution(lowImageKernel);
        MyConvolution highConvolution = new MyConvolution(highImageKernel);

        MBFImage lowPassFilter = lowImage.process(lowConvolution);
        MBFImage highPassFilter = highImage.process(highConvolution);

        return lowPassFilter.add(highImage.subtract(highPassFilter));
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
            }
        }
        return kernel;
    }

    public static float calculateGaussianValue(int x, int y, float sigma){
        return (float) (1 / (2 * pi * sigma * sigma) * Math.exp(- (x*x + y*y)/(2*sigma*sigma)));
    }

    public static void main(String[] args) throws IOException {
        MBFImage highimage = ImageUtilities.readMBF(new File("hybrid-images/data/cat.bmp"));
        MBFImage lowimage = ImageUtilities.readMBF(new File("hybrid-images/data/dog.bmp"));

        MBFImage hybrid = makeHybrid(lowimage, 4, highimage, 12);
        DisplayUtilities.display(hybrid);
    }
}
