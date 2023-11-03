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

        // Create low/high kernels
        float[][] lowImageKernel = makeGaussianKernel(lowSigma);
        float[][] highImageKernel = makeGaussianKernel(highSigma);

        // Initialise the convolution class with the kernels
        MyConvolution lowConvolution = new MyConvolution(lowImageKernel);
        MyConvolution highConvolution = new MyConvolution(highImageKernel);

        // Run the both low pass frequency filters on the images
        MBFImage lowPassFilter = lowImage.process(lowConvolution);
        MBFImage highPassFilter = highImage.process(highConvolution);

        // Return the final image being the sum of the low and high pass filters
        // It was said in the teams chat you can use the .add and .subtract function
        // so I'm assuming im allowed to do this?
        return lowPassFilter.add(highImage.subtract(highPassFilter));
    }

    /**
     * Create a Gaussian distribution kernel
     *
     * @param sigma Standard deviation for the Gaussian distribution
     * @return kernel
     */
    private static float[][] makeGaussianKernel(float sigma) {
        // Create the kernel size, ensuring it is off
        int kernelSize = (int) Math.floor(8 * sigma + 1);
        if (kernelSize % 2 == 0) {
            kernelSize++;
        }

        float[][] kernel = new float[kernelSize][kernelSize];

        int yOffset = Math.floorDiv(kernel.length, 2);
        int xOffset = Math.floorDiv(kernel[0].length, 2);

        // Calculate the Gaussian value at each x y position in the kernel
        for (int y = 0; y < kernelSize; y++) {
            for (int x = 0; x < kernelSize; x++) {
                kernel[y][x] = calculateGaussianValue(x - xOffset, y - yOffset, sigma);
            }
        }
        return kernel;
    }

    /**
     * Calculate the Gaussian value at a specific x y value
     *
     * @param x The x location of the pixel
     * @param y The y location of the pixel
     * @param sigma The standard deviation
     * @return Gaussian value
     */
    private static float calculateGaussianValue(int x, int y, float sigma) {
        return (float)
                (1 / (2 * pi * sigma * sigma) * Math.exp(-(x * x + y * y) / (2 * sigma * sigma)));
    }
}
