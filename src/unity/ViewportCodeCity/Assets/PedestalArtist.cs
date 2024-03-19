using System;
using System.Collections.Generic;
using System.Linq;
using System.Numerics;
using TDLN.CameraControllers;
using UnityEngine;
using Vector3 = UnityEngine.Vector3;

public class PedestalArtist : MonoBehaviour, IArtist
{
    public GameObject ground;

    public float edgeMargin = 0.3f;

    public float scaleAdjust = 2f;

    public float groundHeight = 0.5f;

    private float maxX;
    private float maxZ;

    /* Coordinate transformation applied to each dimension in
     BuildingArtist (provides coordinates of box's center given
     the coordinates in WorldData */
    private float getCenterCoord(float r, float rSize)
    {
        return r + rSize / 2;
    }

    private float getBoundingDimMag(float r, float rSize)
    {
        return Math.Abs(getCenterCoord(r, rSize)) + 0.5f * rSize;
    }

    public void Draw()
    {
         /* Obtain building information from repository. */
         WorldData worldData = BuildingDataRepository.Repository.WorldData;
         List<BuildingInfo> buildings = worldData.BuildingInfos;
         
         /* Determine the largest coordinate in both the X and Y (in Unity, Z) directions. */
         
         maxX = buildings.Max(building => getBoundingDimMag(building.locX, building.dimX)); 
         maxZ = buildings.Max(building => getBoundingDimMag(building.locY, building.dimY));
         
         Vector3 scaleVec = new Vector3(scaleAdjust * maxX + edgeMargin, 1, scaleAdjust * maxZ + edgeMargin);
        
         Debug.Log($"scaleVec is {scaleVec}");
         
         // /* Scale the ground to accommodate for that largest coordinate. Include the margin. */
         ground.transform.localScale = scaleVec;
         ground.transform.position = new Vector3(0, -groundHeight, 0);
    }
}
