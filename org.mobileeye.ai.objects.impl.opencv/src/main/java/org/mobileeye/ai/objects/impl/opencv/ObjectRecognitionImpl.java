/**
 * Copyright (c) Aakash Polra. 2013.
 * 
 * Licensed under The Non-Profit Open Software License version 3.0 (NPOSL-3.0).
 * Full license terms are available on the Open Source Initiative website.
 * http://opensource.org/licenses/NPOSL-3.0
 */
package org.mobileeye.ai.objects.impl.opencv;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.mobileeye.ai.objects.BoundingBox;
import org.mobileeye.ai.objects.ObjectRecognition;
import org.mobileeye.ai.objects.Point2d;
import org.mobileeye.ai.objects.RecognitionAcceptanceStrategy;
import org.mobileeye.ai.objects.RecognizedObject;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.KeyPoint;
import org.opencv.highgui.Highgui;

/**
 * Object Recognition using feature points matching and OpenCV library.
 * 
 * @author Aakash Polra
 */
public class ObjectRecognitionImpl implements ObjectRecognition<Mat, String> {

	private FeatureDetector featureDetector;
	private DescriptorExtractor descriptorExtractor;
	private DescriptorMatcher descriptorMatcher;
	
	private final List<RecognizableObject> recognizableObjects;
	
	/**
	 * Constructs a default instance using the following values:
	 * featureDetector: {@link FeatureDetector#SURF}
	 * descriptorExtractor: {@link DescriptorExtractor#SURF}
	 * descriptorMatcher: {@link DescriptorMatcher#FLANNBASED}
	 * 
	 * @see {@link #ObjectRecognitionImpl(FeatureDetector, DescriptorExtractor, DescriptorMatcher)}
	 */
	public ObjectRecognitionImpl() {
		this(FeatureDetector.create(FeatureDetector.SURF),
				DescriptorExtractor.create(DescriptorExtractor.SURF),
				DescriptorMatcher.create(DescriptorMatcher.FLANNBASED));
	}
	
	/**
	 * Initializes the object using given feature detector, descriptor extractor
	 * and description matcher objects.
	 * Ensure you use the compatible values of these objects. For example, some detectors
	 * and extractors work on BGR images and some matchers only on greyscale images.
	 */
	public ObjectRecognitionImpl(final FeatureDetector featureDetector,
			final DescriptorExtractor descriptorExtractor,
			final DescriptorMatcher descriptorMatcher) {
		this.featureDetector = featureDetector;
		this.descriptorExtractor = descriptorExtractor;
		this.descriptorMatcher = descriptorMatcher;
		this.recognizableObjects = new LinkedList<RecognizableObject>();
	}
	
	/**
	 * Adds a new recognizable object from the given image file.
	 */
	public void addRecognizableObject(String fileName) {
		addRecognizableObjectFromFile(fileName);
	}
	
	protected void addRecognizableObjectFromFile(final String fileName) {
		final Mat object = Highgui.imread(fileName, Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		final MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
		final Mat objectDescriptors = new Mat();

		this.featureDetector.detect(object, objectKeyPoints);
		this.descriptorExtractor.compute(object, objectKeyPoints, objectDescriptors);

		final List<Mat> objectList = new ArrayList<Mat>(1);
		objectList.add(objectDescriptors);

		this.descriptorMatcher.add(objectList);
		final String identifier = fileName.substring(fileName.replaceAll("\\\\", "/").lastIndexOf('/') + 1, fileName.lastIndexOf('.'));	// '\\\\' regex to replace '\' in windows paths
		this.recognizableObjects.add(
			new RecognizableObject(identifier, object, objectKeyPoints, objectDescriptors));
	}

	@Override
	public List<RecognizedObject<String>> recognizeObjects(Mat scene, RecognitionAcceptanceStrategy<String> acceptanceStrategy) {
		
		for(RecognizableObject r : recognizableObjects) {
			r.clearGoodMatches();
		}
		
		// Detect key-points
		MatOfKeyPoint sceneKeyPoints = new MatOfKeyPoint();
		this.featureDetector.detect(scene, sceneKeyPoints);
		
		// Calculate descriptors (feature vectors)
		Mat sceneDescriptors = new Mat();
		this.descriptorExtractor.compute(scene, sceneKeyPoints, sceneDescriptors);
		
		final KeyPoint[] sceneKeyPointsArray = sceneKeyPoints.toArray();
		
		// Matching descriptor vectors using a matcher
		List<MatOfDMatch> matches = new ArrayList<MatOfDMatch>();
		this.descriptorMatcher.train();
		this.descriptorMatcher.knnMatch(sceneDescriptors, matches, 2);
		
		// Find good matches
		final float nndrRatio = 0.64f;	// I came up with this value through experimentation I think
										// NNDR = nearest neighbor distance ratio
		for( int i = 0; i < matches.size(); i++ ) {
			DMatch m1 = matches.get(i).toArray()[0];
			DMatch m2 = matches.get(i).toArray()[1];
			if (m1.distance < nndrRatio * m2.distance) {
				recognizableObjects.get(m1.imgIdx).addGoodMatch(m1);
			}
		}
		
		// Results
		final List<RecognizedObject<String>> recognizedObjects = new LinkedList<RecognizedObject<String>>();
		for (RecognizableObject recognizableObject : recognizableObjects) {
			
			final DMatch[] goodMatchesArray = recognizableObject.getGoodMatchesAsArray();
			final KeyPoint[] objectKeyPointsArray = recognizableObject.getKeyPoints().toArray();
			
			// The following two lines can be used to get an image that has point-to-point matching drawn
//			Mat feature2dResultant = new Mat();
//			Features2d.drawMatches(scene, sceneKeyPoints, recognizableObject.getImage(), recognizableObject.getKeyPoints(), new MatOfDMatch(goodMatchesArray), feature2dResultant, Scalar.all(-1), Scalar.all(-1), new MatOfByte(), Features2d.NOT_DRAW_SINGLE_POINTS);
			
			System.out.println("Matches: " + matches.size() + "; Good matches: " + goodMatchesArray.length);	// TODO: LOG#debug instead of println
			
			if (goodMatchesArray.length < 4) {	// matches less than 4 will result in an exception when calling Calib3d.findHomography()
				System.out.println("No features detected for: " + recognizableObject.getIdentifier() + ". Ignoring homography.");	// TODO: Log instead of System.out.println
				continue;
			}
			
			final double matchPercent = goodMatchesArray.length / (double)recognizableObject.getKeyPoints().rows();
			
			// Localize the object
			Point[] objectPoints = new Point[goodMatchesArray.length];
			Point[] scenePoints = new Point[goodMatchesArray.length];
			for( int i = 0; i < goodMatchesArray.length; i++ ) {
				//-- Get the keypoints from the good matches
				objectPoints[i] = objectKeyPointsArray[goodMatchesArray[i].trainIdx].pt;
				scenePoints[i] = sceneKeyPointsArray[goodMatchesArray[i].queryIdx].pt;	// FIXME: sometimes queryIdx is greater than sceneKeyPointsArray.length
			}
			Mat homographyMat = null;
			homographyMat = Calib3d.findHomography(new MatOfPoint2f(objectPoints), new MatOfPoint2f(scenePoints), Calib3d.RANSAC, 2);	// this throws exception if points.size() is less than 4.

			//-- Get the corners from the image_1 ( the object to be "detected" )
			final int objectColumns = recognizableObject.getImage().cols();
			final int objectRows = recognizableObject.getImage().rows();

			Point[] objectCorners = new Point[4];
			objectCorners[0] = new Point(0, 0);
			objectCorners[1] = new Point(objectColumns, 0);
			objectCorners[2] = new Point(objectColumns, objectRows);
			objectCorners[3] = new Point(0, objectRows);

			MatOfPoint2f sceneCornersMat = new MatOfPoint2f();
			Core.perspectiveTransform(new MatOfPoint2f(objectCorners), sceneCornersMat, homographyMat);
			Point[] sceneCorners = sceneCornersMat.toArray();
	
			final BoundingBox objectBoundingBox = new BoundingBox(
					new Point2d(sceneCorners[0].x, sceneCorners[0].y),
					new Point2d(sceneCorners[1].x, sceneCorners[1].y),
					new Point2d(sceneCorners[2].x, sceneCorners[2].y),
					new Point2d(sceneCorners[3].x, sceneCorners[3].y));
			
			RecognizedObject<String> recognizedObject = new RecognizedObject<String>(recognizableObject.getIdentifier(), objectBoundingBox,
					(float)matchPercent, recognizableObject.getIdentifier());
			
			if (acceptanceStrategy.isAcceptable(recognizedObject)) {
				recognizedObjects.add(recognizedObject);
			} else {
				// TODO : Log? possibly at trace level.
			}
		}
		return recognizedObjects;
	}
	
	public static void drawRecognizedObjectAndSaveInAFile(final Mat scene, final List<RecognizedObject<String>> recognizedObjects, final String fileName) {
		final Mat resultantImage = new Mat();
		scene.copyTo(resultantImage);
		
		for (RecognizedObject<String> recognizedObject : recognizedObjects) {
			
			Point p1 = convertToOpenCVPoint(recognizedObject.getBoundingBox().getPoint1());
			Point p2 = convertToOpenCVPoint(recognizedObject.getBoundingBox().getPoint2());
			Point p3 = convertToOpenCVPoint(recognizedObject.getBoundingBox().getPoint3());
			Point p4 = convertToOpenCVPoint(recognizedObject.getBoundingBox().getPoint4());
			
			//-- Draw lines between the corners (the mapped object in the scene - image_2 )
			Core.line(resultantImage, p1, p2, new Scalar(0, 255, 0), 4);
			Core.line(resultantImage, p2, p3, new Scalar(0, 255, 0), 4);
			Core.line(resultantImage, p3, p4, new Scalar(0, 255, 0), 4);
			Core.line(resultantImage, p4, p1, new Scalar(0, 255, 0), 4);
			
			Core.putText(resultantImage, recognizedObject.getName() + "(" + (int)(recognizedObject.getConfidence()*100) + "%)", new Point(p1.x + 5, p1.y + 18), Core.FONT_HERSHEY_PLAIN, 1, new Scalar(0, 255, 0), 1);
		}
		Highgui.imwrite(fileName, resultantImage);
	}
	
	private static Point convertToOpenCVPoint(Point2d p) {
		return new Point(p.getX(), p.getY());
	}
}
