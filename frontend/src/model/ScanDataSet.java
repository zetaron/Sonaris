package model;

public class ScanDataSet {

	public ScanDataSet(int scanId, int distance, int rotation, float x, float y, float vehicleRotation) {
	
		mScanId = scanId;
		mDistance = distance;
		mRotation = rotation;
		mVehiclePositionX = x;
		mVehiclePositionY = y;
		mVehicleRotation = vehicleRotation;
	}
	
	public float[] GetVehiclePosition() {
		return new float[] { mVehiclePositionX, mVehiclePositionY };
	}
	
	public float[] GetAbsolutePosition() {
		float a;
		
		return new float[] {
		};
	}
	
	public float GetVehicleRotation() {
		return mVehicleRotation;
	}
	
	public int GetScanId() {
		return mScanId;
	}
	
	public boolean IsScanId(int scanId) {
		return (mScanId==scanId);
	}
	
	public int GetRotation() {
		return mRotation;
	}
	
	public int GetDistance() {
		return mDistance;
	}
	
	public float GetObstacleSize() {
		return mDistance/10F;
	}
	
	
	
	private int mScanId, mRotation, mDistance;
	private float mVehiclePositionX, mVehiclePositionY, mVehicleRotation;
}
