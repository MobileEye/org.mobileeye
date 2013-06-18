package org.mobileeye.ai.objects;

/**
 * 
 * @author Aakash
 *
 * @param <D> type of data to be stored with this object. For example, an Image
 */
public class RecognizedObject<D> {

	private String name;
	private BoundingBox boundingBox;
	private float confidence;
	private D objectData;
	
	public RecognizedObject(final String name, final BoundingBox boundingBox,
			final float confidence, final D objectData) {
		this.name = name;
		this.boundingBox = boundingBox;
		this.confidence = confidence;
		this.objectData = objectData;
	}

	public String getName() {
		return name;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public float getConfidence() {
		return confidence;
	}

	public D getObjectData() {
		return objectData;
	}
}
