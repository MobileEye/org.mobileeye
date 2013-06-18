package org.mobileeye.ai.objects.impl.opencv.test;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mobileeye.ai.objects.ObjectRecognition;
import org.mobileeye.ai.objects.RecognizedObject;
import org.mobileeye.ai.objects.impl.opencv.ObjectRecognitionImpl;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

/**
 * Rather than testing specific functionality of ObjectRecognitionImpl, this class
 * provides a simple set of tests so that a developer can visualize the match results.
 * 
 * @author Aakash
 */
public class ObjectRecognitionImplTest {

	private static final String OBJECTS_FOLDER_NAME = "objects";
	private static final String SCENES_FOLDER_NAME = "scenes";
	
	private ObjectRecognition<Mat, String> objectRecognition;
	
	@BeforeClass
	public static void loadLibrary() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	@Before
	public void setup() {
		objectRecognition = new ObjectRecognitionImpl();
	}
	
	public void testRecognizeObjects(final String testFolder, final double minMatchingThreshold) {
		// Adding recognizable objects
		File[] objects = new File(testFolder + OBJECTS_FOLDER_NAME).listFiles();
		for(File obj : objects) {
			objectRecognition.addRecognizableObject(obj.getAbsolutePath());
		}
		
		// Recognizing objects in scenes
		File[] scenes = new File(testFolder + SCENES_FOLDER_NAME).listFiles();
		for(File sceneFile : scenes) {
			Mat scene = Highgui.imread(sceneFile.getAbsolutePath());
			List<RecognizedObject<String>> recognizedObjects =
					objectRecognition.recognizeObjects(scene, minMatchingThreshold);
			ObjectRecognitionImpl.drawRecognizedObjectAndSaveInAFile(scene, recognizedObjects, sceneFile.getParentFile().getAbsolutePath() + sceneFile.getName() + "-result.png");
			Assert.assertTrue(true);
		}
	}
	
	@Test
	public void testRecognizeObjects() {
		testRecognizeObjects("src/test/resources/test1/", 0.01);
		testRecognizeObjects("src/test/resources/test2/", 0.01);
		testRecognizeObjects("src/test/resources/test3/", 0.01);
	}
}
