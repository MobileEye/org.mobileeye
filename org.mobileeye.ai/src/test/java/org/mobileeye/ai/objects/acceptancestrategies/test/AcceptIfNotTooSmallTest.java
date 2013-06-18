package org.mobileeye.ai.objects.acceptancestrategies.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mobileeye.ai.objects.BoundingBox;
import org.mobileeye.ai.objects.Point2d;
import org.mobileeye.ai.objects.RecognitionAcceptanceStrategy;
import org.mobileeye.ai.objects.RecognizedObject;
import org.mobileeye.ai.objects.acceptancestrategies.AcceptIfNotTooSmall;

public class AcceptIfNotTooSmallTest {
	
	private RecognitionAcceptanceStrategy<Object> acceptanceStrategy;
	
	@Before
	public void setup() {
		acceptanceStrategy = new AcceptIfNotTooSmall<Object>(1.0f);
	}

	@Test
	public void tooSmallObjectFails() {
		boolean result = acceptanceStrategy.isAcceptable(new RecognizedObject<Object>(
				"test-too-small",
				new BoundingBox(
						new Point2d(0.0,  0.0),
						new Point2d(0.9,  0.0),
						new Point2d(0.9,  0.9),
						new Point2d(0.0,  0.9)),
				1.0f, new Object()));
		Assert.assertFalse(result);
	}
	
	@Test
	public void acceptableObjectMinRange() {
		boolean result = acceptanceStrategy.isAcceptable(new RecognizedObject<Object>(
				"test-acceptable-min-range",
				new BoundingBox(new Point2d(0.0,  0.0),
						new Point2d(1.0,  0.0),
						new Point2d(1.0,  1.0),
						new Point2d(0.0,  1.0)),
				1.0f, new Object()));
		Assert.assertTrue(result);
	}

}
