package COMP3204;


import java.io.File;
import java.io.IOException;
import org.junit.Test;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.resize.ResizeProcessor;

public class ConvolutionHybridTests {

	@Test
	public void convTests() throws IOException {
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

	@Test
	public void hybridTest() throws IOException {
		MBFImage highimage = ImageUtilities.readMBF(new File("hybrid-images/data/cat.bmp"));
		MBFImage lowimage = ImageUtilities.readMBF(new File("hybrid-images/data/dog.bmp"));

		MBFImage hybrid = MyHybridImages.makeHybrid(lowimage, 4, highimage, 12);
		DisplayUtilities.display(hybrid);
	}

	public static void main(String[] args) throws IOException {
		MBFImage highimage = ImageUtilities.readMBF(new File("hybrid-images/HansungNewNew.bmp"));
		MBFImage lowimage = ImageUtilities.readMBF(new File("hybrid-images/Xiaohao.bmp"));
		DisplayUtilities.display(highimage.process(new ResizeProcessor(0.9f)));
		DisplayUtilities.display(lowimage.process(new ResizeProcessor(0.9f)));

		//high low 6 10
		MBFImage hybrid3 = MyHybridImages.makeHybrid(lowimage, 6f, highimage, 10f);
		hybrid3 = hybrid3.process(new ResizeProcessor(2f));
		DisplayUtilities.display(hybrid3);
		hybrid3 = hybrid3.process(new ResizeProcessor(0.5f));
		DisplayUtilities.display(hybrid3);
		hybrid3 = hybrid3.process(new ResizeProcessor(0.5f));
		DisplayUtilities.display(hybrid3);
		hybrid3 = hybrid3.process(new ResizeProcessor(0.5f));
		DisplayUtilities.display(hybrid3);
	}
}
